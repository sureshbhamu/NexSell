package com.NexSell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class User {

	
		@PostMapping("/api/register")
		public String register(String name, String email, String password, String address, String phone, String type) {
		    try {
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineEcommerce", "root", "Pknice99@");

		        String query;
		        if(type.equals("customer")) {
		            query = "INSERT INTO customers (name, email, password, shipping_address, phone) VALUES (?, ?, ?, ?, ?)";
		        } else if(type.equals("seller")) {
		            query = "INSERT INTO sellers (name, email, password, address, phone) VALUES (?, ?, ?, ?, ?)";
		        } else {
		            return "Invalid user type";
		        }

		        PreparedStatement pstmt = con.prepareStatement(query);
		        pstmt.setString(1, name);
		        pstmt.setString(2, email);
		        pstmt.setString(3, password);
		        pstmt.setString(4, address);
		        pstmt.setString(5, phone);

		        int i = pstmt.executeUpdate();

		        if (i > 0) {
		            return "You have been registered successfully as a " + type;
		        } else {
		            return "Error occurred while registering";
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return "";
		}

		
		
		
	
		
		
		@GetMapping("/api/login")
		public String login(String email,String password, String type) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineEcommerce", "root", "Pknice99@");
			Statement stmt= con.createStatement();
			String query="";
			if(type.equals("customer")) {
	            query = "select password from customers where email='"+email+"'";
	        } else if(type.equals("seller")) {
	            query = "select password from sellers where email='"+email+"'";
	        } else {
	            return "Invalid user type";
	        }
			ResultSet rs=stmt.executeQuery(query);
			if(rs.next()) {
				String pwd=rs.getString("password");
				if(pwd.equals(password)) {
					return"you are valid user";
				}else {
					return"password is wrong";
				}
					
			}
			else {
				return"you are not registered, first registered youself";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
			return "";
		}
		
		
		
		
		

		@PostMapping("/api/customer/update")
		public String updateCustomer(String name, String email, String password, String shipping_address, String phone) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineEcommerce", "root", "Pknice99@");
				String query = "UPDATE customers SET name = ?, password = ?, shipping_address = ?, phone = ? WHERE email = ?";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, password);
				pstmt.setString(3, shipping_address);
				pstmt.setString(4, phone);
				pstmt.setString(5, email);
				int i = pstmt.executeUpdate();
				if (i > 0) {
					return "Your record has been updated successfully";
				} else {
					return "Error occurred while updating your record";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}
		
		
		
		
		
		
		
		
		
		
		

		@PostMapping("/api/seller/update")
		public String updateSeller(String name, String email, String password, String address, String phone) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineEcommerce", "root", "Pknice99@");
				String query = "UPDATE sellers SET name = ?, password = ?, address = ?, phone = ? WHERE email = ?";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, password);
				pstmt.setString(3, address);
				pstmt.setString(4, phone);
				pstmt.setString(5, email);
				int i = pstmt.executeUpdate();
				if (i > 0) {
					return "Your record has been updated successfully";
				} else {
					return "Error occurred while updating your record";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}

		
		
		
		
		
		

		
		
		@DeleteMapping("/api/delete")
		public String deleteCustomer(String email,String type) {
		    try {
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineEcommerce", "root", "Pknice99@");
		        String query = "DELETE FROM customers WHERE email = ?";
		        if(type.equals("customer")) {
		            query = "DELETE FROM customers WHERE email = ?";
		        } else if(type.equals("seller")) {
		            query = "DELETE FROM sellers WHERE email = ?";
		        } else {
		            return "Invalid user type";
		        }
		        PreparedStatement pstmt = con.prepareStatement(query);
		        pstmt.setString(1, email);
		        int i = pstmt.executeUpdate();
		        if (i > 0) {
		            return "Your record has been deleted successfully";
		        } else {
		            return "Error occurred while deleting your record";
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return "";
		}

		

		
		
		@GetMapping("/api/product/search")
		public Map searchProduct(String Name) {
		    try {
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineEcommerce", "root", "Pknice99@");
		        String query = "SELECT * FROM products WHERE name LIKE ?";
		        PreparedStatement pstmt = con.prepareStatement(query);
		        pstmt.setString(1, "%" + Name + "%");
		        ResultSet rs = pstmt.executeQuery();
		        ArrayList l= new ArrayList();
		        while (rs.next()) {
		        	Map map=new HashMap();
					map.put("Product ID:" , rs.getString("product_id"));
					map.put("Name:" , rs.getString("name"));
					map.put("Description:" , rs.getString("description"));
					map.put("Price:" , rs.getString("price")); 
					l.add(map);
		        }
		        Map data=new HashMap();
		        data.put("ProductDetail",l);
		        return data;
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return null;
		}


		
		

		
		
			@GetMapping("/api/forget")
			public String forget(String email, String type) {                          
				
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineEcommerce", "root","Pknice99@");
				Statement stmt=con.createStatement();
				String query;
				String table;
		        if(type.equals("customer")) {
		            query = "select * from customers where email='"+email+"'";
		            table="customers";
		        } else if(type.equals("seller")) {
		            query = "select * from sellers where email='"+email+"'";
		            table="sellers";
		        } else {
		            return "Invalid user type";
		        }
				ResultSet rs=stmt.executeQuery(query);
				if(rs.next()) {
					Random random= new Random();  
					int otp=random.nextInt(99999);
					System.out.println("otp "+otp);
					PreparedStatement stmt1=con.prepareStatement("update "+table+" set otp=? where email='"+email+"'");
					stmt1.setInt(1, otp);
					int i=stmt1.executeUpdate();
					System.out.println("no of statement "+1 );
					if(i>0) {
						return "your otp is generated";
					}
					else {
						return "try again";
					}
				}
				else {
					return "invalid email id";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				
				return "";
			}
			
			
		
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

		
		
		
		
		@PostMapping("/api/add-to-cart")
		public String addToCart(int customerId, int productId, int quantity) {
		    try {
		 
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineEcommerce", "root", "Pknice99@");


		        String cartQuery = "SELECT id, quantity FROM cart WHERE customer_id = ? AND product_id = ?";
		        PreparedStatement cartStmt = con.prepareStatement(cartQuery);
		        cartStmt.setInt(1, customerId);
		        cartStmt.setInt(2, productId);
		        ResultSet cartResult = cartStmt.executeQuery();

		        int cartId = 0;
		        int cartQuantity = 0;

		        if (cartResult.next()) {

		            cartId = cartResult.getInt("id");
		            cartQuantity = cartResult.getInt("quantity") + quantity;
		            String updateCartQuery = "UPDATE cart SET quantity = ? WHERE id = ?";
		            PreparedStatement updateCartStmt = con.prepareStatement(updateCartQuery);
		            updateCartStmt.setInt(1, cartQuantity);
		            updateCartStmt.setInt(2, cartId);
		            int i = updateCartStmt.executeUpdate();

		            if (i > 0) {
		                return "Cart updated successfully";
		            }
		        } else {
		         
		            String insertCartQuery = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
		            PreparedStatement insertCartStmt = con.prepareStatement(insertCartQuery);
		            insertCartStmt.setInt(1, customerId);
		            insertCartStmt.setInt(2, productId);
		            insertCartStmt.setInt(3, quantity);
		            int i = insertCartStmt.executeUpdate();

		            if (i > 0) {
		                return "Product added to cart successfully";
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return "Failed to add product to cart";
		}

		
		@PostMapping("/api/orders")
		public String addOrder(int customerId, String productName, int quantity, int productId, double price) {
		    try {       
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineEcommerce", "root", "Pknice99@");
		        String customerQuery = "SELECT  shipping_address FROM customers WHERE customer_id = ?";
		        PreparedStatement customerStmt = con.prepareStatement(customerQuery);
		        customerStmt.setInt(1, customerId);
		        ResultSet customerResult = customerStmt.executeQuery();
		        String shippingAddress = "";
		        if (customerResult.next()) {
		            shippingAddress = customerResult.getString("shipping_address");
		        }
		        String orderQuery = "INSERT INTO orders (customer_id, productName, quantity,  shipping_address) VALUES (?, ?, ?, ?)";
		        PreparedStatement orderStmt = con.prepareStatement(orderQuery);
		        orderStmt.setInt(1, customerId);
		        orderStmt.setString(2, productName);
		        orderStmt.setInt(3, quantity);
		        orderStmt.setString(4, shippingAddress);
		        int i = orderStmt.executeUpdate();
		        if (i > 0) {
		            String orderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES ((SELECT MAX(order_id) FROM orders), ?, ?, ?)";
		            PreparedStatement orderItemStmt = con.prepareStatement(orderItemQuery);
		            orderItemStmt.setInt(1, productId);
		            orderItemStmt.setInt(2, quantity);
		            orderItemStmt.setDouble(3, price);
		            int j = orderItemStmt.executeUpdate();

		            if (j > 0) {
		                return "Order added successfully";
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return "Failed to add order";
		}



		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

}
