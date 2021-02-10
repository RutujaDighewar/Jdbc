/**
 * 
 */
package com.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.rmi.ssl.SslRMIClientSocketFactory;

import java.sql.CallableStatement;
import java.sql.ResultSet;

/**
 * @author Rutuja
 *
 */
public class CallableStatementTest {

	Scanner sc = new Scanner(System.in);
	private boolean flag = Boolean.FALSE;

	public void insertProduct() {
		try (CallableStatement cs = JdbcUtility.getConnection().prepareCall("{call insertProduct(?,?)}");) {
			System.out.println("How may products want to add ?");
			int num = sc.nextInt();
			for (int i = 0; i < num; i++) {
				Product p = new Product();
				System.out.println("Enter product name");
				p.setName(sc.next());
				System.out.println("Enter Price");
				p.setPrice(sc.nextInt());

				cs.setString(1, p.getName());
				cs.setInt(2, p.getPrice());

				cs.executeUpdate();
				flag = Boolean.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == Boolean.TRUE) {
			System.out.println("Product inserted successfully...!");
		} else {
			System.out.println("Product not saved...!");
		}
	}

	public void updateProduct() {
		try (CallableStatement cs = JdbcUtility.getConnection().prepareCall("{call updateProduct(?,?)}");) {
			System.out.println("Which row want to update ?");
			int id = sc.nextInt();
			System.out.println("Enter name");
			String name = sc.next();
			cs.setString(1, name);
			cs.setInt(2, id);
			int result = cs.executeUpdate();
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
		try (CallableStatement cs = JdbcUtility.getConnection().prepareCall("{call deleteProduct(?)}");) {
			System.out.println("Which row want to delete ?");
			int id = sc.nextInt();
			cs.setInt(1, id);
			int result = cs.executeUpdate();
			if (result > 0) {
				System.out.println("Deleted successfully...!");
			} else {
				System.out.println("Delete failed...Try again...!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectProduct() {
		List<Product> products = new ArrayList<Product>();
		try (ResultSet rs = JdbcUtility.executeQuery("{call selectProduct()}");) {
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setPrice(rs.getInt(3));
				products.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Product p : products) {
			System.out.println(p.getId() + "\t" + p.getName() + "\t" + p.getPrice());
		}
	}

	public void maxId() {
		try (ResultSet rs = JdbcUtility.executeQuery("{call maxId()}");) {
			if (rs.next()) {
				System.out.println("maximum id = "+rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void minId() {
		try(ResultSet rs=JdbcUtility.executeQuery("{call minId()}");){
			if(rs.next()) {
				System.out.println("Minimum id = "+rs.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CallableStatementTest cst = new CallableStatementTest();
		cst.insertProduct();
		cst.updateProduct();
		cst.deleteProduct();
		cst.selectProduct();
		cst.maxId();
		cst.minId();

	}

}
