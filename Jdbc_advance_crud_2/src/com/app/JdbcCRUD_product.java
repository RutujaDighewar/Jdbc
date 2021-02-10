/**
 * 
 */
package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Rutuja
 *
 */
public class JdbcCRUD_product {
	private static Connection con = null;
	private static String URL = "jdbc:mysql://localhost:3306/clc";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	private boolean flag = Boolean.FALSE;
	Scanner sc = new Scanner(System.in);

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void createProduct() {
		try (Statement smt = con.createStatement();) {
			smt.executeUpdate("create table product(id int auto_increment, name varchar(45), price int , primary key(id))");
			System.out.println("Table created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertProduct() {
		try (Statement smt = con.createStatement();) {
			System.out.println("How many product you want to add ?");
			int num = sc.nextInt();
			for (int i = 0; i < num; i++) {
				Product p = new Product();
				System.out.println("Enter name");
				p.setName(sc.next());
				System.out.println("Enter price");
				p.setPrice(sc.nextInt());
				smt.executeUpdate(
						"insert into product (name , price) values('" + p.getName() + "','" + p.getPrice() + "')");
				flag = Boolean.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == Boolean.TRUE) {
			System.out.println("Product successfully inserted...");
		} else {
			System.out.println("Product not saved...!");
		}
	}

	public void updateProduct() {
		System.out.println();
		try (Statement smt = con.createStatement();) {
			Product p = new Product();
			System.out.println("Which row want to update");
			int id = sc.nextInt();
			System.out.println("Enter name");
			String name = sc.next();
			int result = smt.executeUpdate("update product set name='" + name + "' where id=" + id + "");
			
			if (result > 0) {
				System.out.println("Updated successfully...");
			} else {
				System.out.println("Update failed...Try again...!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteProduct() {
		System.out.println();
		try(Statement smt=con.createStatement();){
			System.out.println("Which row want to delete");
			int id=sc.nextInt();
			int result=smt.executeUpdate("delete from product where id="+id+"");
			if(result > 0) {
				System.out.println("Delete successfully...!");
			}else {
				System.out.println("Delete failed...Try again...!");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void selectProduct() {
		System.out.println();
		List<Product> products=new ArrayList<Product>();
		try(Statement smt=con.createStatement();){
		 ResultSet rs=smt.executeQuery("select * from product");
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
		System.out.println("Product id    Product name     Product price");
		for(Product p : products) {
			System.out.println(p.getId()+"\t\t"+p.getName()+"\t\t"+p.getPrice());
		}

	}
	
	public void maxId() {
		try(Statement smt=con.createStatement();){
			ResultSet rs=smt.executeQuery("select max(id) from product");
			if(rs.next()) {
				System.out.println();
				System.out.println("maximum id = "+rs.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void minId() {
		try(Statement smt = con.createStatement();){
			ResultSet rs=smt.executeQuery("select min(id) from product");
			if(rs.next()) {
				System.out.println();
				System.out.println("minimum id = "+rs.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void averageId() {
		try(Statement smt=con.createStatement();){
			ResultSet rs=smt.executeQuery("select avg(id) from product");
			if(rs.next()) {
				System.out.println();
				System.out.println("average id = "+rs.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JdbcCRUD_product crud = new JdbcCRUD_product();
		// crud.createProduct();
		crud.insertProduct();
		crud.selectProduct();
		crud.updateProduct();
		crud.selectProduct();
		crud.deleteProduct();
		crud.selectProduct();
		crud.maxId();
		crud.minId();
		crud.averageId();
	}

}
