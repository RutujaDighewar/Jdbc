/**
 * 
 */
package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Rutuja
 *
 */
public class JdbcPrepareStatement {
	private static Connection con = null;
	private static String URL = "jdbc:mysql://localhost:3306/clc";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	private boolean flag = Boolean.TRUE;
	Scanner sc = new Scanner(System.in);

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertProduct() {
		try (PreparedStatement ps = con.prepareStatement("insert into product (name, price) values (?,?)");) {
			System.out.println("How many products want to add ?");
			int num = sc.nextInt();
			for (int i = 0; i < num; i++) {
				Product p = new Product();
				System.out.println("Enter name");
				p.setName(sc.next());
				System.out.println("Enter price");
				p.setPrice(sc.nextInt());

				ps.setString(1, p.getName());
				ps.setInt(2, p.getPrice());
				ps.executeUpdate();

				flag = Boolean.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == Boolean.TRUE) {
			System.out.println("Product inserted...");
		} else {
			System.out.println("Product not saved...!");
		}
	}

	public void selectProduct() {
		System.out.println();
		List<Product> products=new ArrayList<Product>();
		try(PreparedStatement ps=con.prepareStatement("select * from product");){
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
			Product p=new Product();
			p.setId(rs.getInt(1));
			p.setName(rs.getString(2));
			p.setPrice(rs.getInt(3));
			products.add(p);
			}
	}catch(Exception e) {
		e.printStackTrace();
	}
	for(Product p : products) {
		System.out.println(p.getId()+"\t"+p.getName()+"\t"+p.getPrice());
	}	
	}
	
	public void updateProduct() {
		System.out.println();
		try(PreparedStatement ps=con.prepareStatement("update product set name=? where id=?");){
		System.out.println("Which row want to update ?");
		int id=sc.nextInt();
		System.out.println("Enter name");
		String name=sc.next();
		ps.setInt(2,id);
		ps.setString(1, name);
	    int result =ps.executeUpdate();
	    if(result>0) {
	    	System.out.println("Updated successfully...");
	    }else {
	    	System.out.println("Update failed...Try again...!");
	    }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProudct() {
		System.out.println();
		try(PreparedStatement ps=con.prepareStatement("delete from product where id=?");){
			System.out.println("Which row want to delete ?");
			int id=sc.nextInt();
			ps.setInt(1,id);
			int result=ps.executeUpdate();
			if(result > 0) {
				System.out.println("Deleted successfully...");
			}else {
				System.out.println("Delete failed...Try again...!");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		JdbcPrepareStatement jps=new JdbcPrepareStatement();
		jps.insertProduct();
		jps.selectProduct();
		jps.updateProduct();
		jps.selectProduct();
		jps.deleteProudct();
		jps.selectProduct();
	}

}
