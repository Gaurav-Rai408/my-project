package com.medicineStoreApp.dbHandler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.medicineStoreApp.Model.CartItem;
import com.medicineStoreApp.Model.Customer;
import com.medicineStoreApp.Model.Order;
import com.medicineStoreApp.Model.Product;

import oracle.jdbc.OracleDriver;

public class DbHandler {

	public static String url = "http://localhost:8082/com.medicineStoreApp";
	static Connection con = null;

	public static Connection getConnectionAndDriver() throws SQLException {
		// String url="jdbc:oracle:thin:@192.168.10.74:1521:xe";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String pass = "system";
		DriverManager.registerDriver(new OracleDriver());
		con = DriverManager.getConnection(url, user, pass);
		return con;
	}

	public ResultSet getUserPassword(String username, String pswd) throws SQLException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		con = DbHandler.getConnectionAndDriver();
		String sql = "select * from userdata where mail=?";
		System.out.println("inside  getUserPassword1");
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;

	}

	public static Date getDbFormatDate() {

		return new java.sql.Date(new java.util.Date().getTime());
	}

	public void injectRegisterForm(String username, String password, Long phone, String mail, String gender)
			throws SQLException {
		String sql = "INSERT INTO userdata (C_DATE, USERNAME, PASSWORD, PHONE, MAIL, GENDER, ADDRESS) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			con = DbHandler.getConnectionAndDriver();
			con.setAutoCommit(false);

			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
				// java.sql.Date currentDate = new java.sql.Date(new
				// java.util.Date().getTime());
				pstmt.setDate(1, getDbFormatDate());
				pstmt.setString(2, username);
				pstmt.setString(3, password);
				pstmt.setLong(4, phone);
				pstmt.setString(5, mail);
				pstmt.setString(6, gender);
				pstmt.setString(7, " "); // Address (optional)

				// Execute the insert
				pstmt.executeUpdate();
				con.commit(); // Commit the transaction

			} catch (SQLException e) {
				con.rollback(); // Rollback on failure
				throw e; // Re-throw the exception
			} finally {
				con.setAutoCommit(true); // Reset auto-commit back to true
			}

		} catch (SQLException e) {
			e.printStackTrace(); // Log or handle the error
			throw e;
		}
	} 
	
	public void addToCart(String userId, String productId, int quantity, double price) throws SQLException {
	    // SQL queries
	    String selectSql = "SELECT quantity FROM cart WHERE user_id = ? AND product_id = ?";
	    String updateSql = "UPDATE cart SET quantity =  ?, price = ? WHERE user_id = ? AND product_id = ?";
	    String insertSql = "INSERT INTO cart (user_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

	    try {
	        // Get the connection
	        con = DbHandler.getConnectionAndDriver();
	        con.setAutoCommit(false); // Disable auto-commit for transactional safety

	        // Step 1: Check if the product is already in the cart
	        try (PreparedStatement selectStmt = con.prepareStatement(selectSql)) {
	            selectStmt.setString(1, userId);
	            selectStmt.setString(2, productId);
	            ResultSet rs = selectStmt.executeQuery();

	            if (rs.next()) {
	                // Step 2: If the product exists, update the quantity
	                int currentQuantity = rs.getInt("quantity") + 1;
	                System.out.println("currentQuantity"+currentQuantity);
	                try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
	                    updateStmt.setInt(1, currentQuantity); // Increment quantity
	                    updateStmt.setDouble(2, price); // Update price (if needed)
	                    updateStmt.setString(3, userId);
	                    updateStmt.setString(4, productId);
	                    updateStmt.executeUpdate();
	                }
	            } else {
	                // Step 3: If the product doesn't exist, insert a new row
	                try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
	                    insertStmt.setString(1, userId);
	                    insertStmt.setString(2, productId);
	                    insertStmt.setInt(3, 1);
	                    insertStmt.setDouble(4, price);
	                    insertStmt.executeUpdate();
	                }
	            }

	            con.commit(); // Commit transaction

	        } catch (SQLException e) {
	            con.rollback(); // Rollback in case of error
	            throw e;
	        } finally {
	            con.setAutoCommit(true); // Restore auto-commit
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}


	
    public List<Product> getAllProducts() throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
      
        List<Product> productList = new LinkedList<>();
        String sql = "SELECT * FROM product";

        try {
          con = DbHandler.getConnectionAndDriver();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Product product = new Product();
                
                product.setProductId(rs.getString("PRODUCT_ID"));
                product.setProductName(rs.getString("PRODUCT_NAME"));
                product.setPrice(rs.getFloat("PRICE"));
                product.setDescription(rs.getString("DESCRIPTION"));
                product.setImageUrl(rs.getString("IMAGE_URL"));
                product.setStock(rs.getInt("STOCK"));
                product.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                productList.add(product);
            }

            return productList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return productList;
    }
    
    public List<Customer> getAllCustomers() throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
      
        List<Customer> customerList = new LinkedList<>();
        String sql = "SELECT * FROM userdata";

        try {
          con = DbHandler.getConnectionAndDriver();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
            	Customer customer = new Customer();
                
            	 customer.setId(rs.getInt("ID"));
                 customer.setPassword(rs.getString("PASSWORD"));
                 customer.setPhone(rs.getString("PHONE"));
                 customer.setUsername(rs.getString("USERNAME"));
                 customer.setCreationDate(rs.getDate("C_DATE"));
                 customer.setEmail(rs.getString("MAIL"));
                 customer.setGender(rs.getString("GENDER"));
                 customer.setAddress(rs.getString("ADDRESS"));
                customerList.add(customer);
            }

            return customerList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return customerList;
    }
    
    
    public List<CartItem> getCartItemsByUserId(String userID) {
        List<CartItem> cartItems = new ArrayList<>(); 
        String query = "SELECT c.cart_id as cart_id, c.quantity as quantity, p.product_id as product_id, " +
                       "p.product_name as product_name, p.price as price, p.description, p.image_url, p.stock , p.created_at " +
                       "FROM cart c " +
                       "JOIN product p ON c.product_id = p.product_id " +
                       "WHERE c.user_id = ?";

        try (Connection conn = DbHandler.getConnectionAndDriver();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Retrieve product details
                String productId = rs.getString("product_id");  // Change from String to long/int
                String productName = rs.getString("product_name");
                float price = rs.getFloat("price");
                String description = rs.getString("description");
                String imageUrl = rs.getString("image_url");
                int stock = rs.getInt("stock");
                
                Product product = new Product(productId, productName, price, stock, description, imageUrl , rs.getTimestamp("created_at"));
                
                // Retrieve cart ID and quantity
                int cartID = rs.getInt("cart_id");  // Corrected column name
                int quantity = rs.getInt("quantity");
                
                // Create CartItem object
                CartItem cartItem = new CartItem(product, quantity, cartID);

                // Add to list
                cartItems.add(cartItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public void deleteCartItemByID(String cartItemId) {
        String deleteSql = "DELETE FROM cart WHERE cart_id = ?";
        try (Connection conn = getConnectionAndDriver(); 
             PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
             
            // Set the cartItemId parameter
            pstmt.setString(1, cartItemId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Cart item deleted successfully.");
            } else {
                System.out.println("No cart item found with the given ID.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting cart item: " + e.getMessage());
        }
    }

	public void addProduct(String productName, double price, int stock, String description, String imageUrl) 	throws SQLException {
		String sql = "INSERT INTO product (product_name, price, stock, image_url , description) VALUES ( ?, ?, ?, ? , ?)";

		try {
			con = DbHandler.getConnectionAndDriver();
			con.setAutoCommit(false);

			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
				  System.out.println(productName);
				  System.out.println(price);
				  System.out.println(stock);
				  System.out.println(imageUrl);
				  System.out.println(description);
				  
				pstmt.setString(1, productName);
				pstmt.setDouble(2, price);
				pstmt.setInt(3, stock);
				pstmt.setString(4, imageUrl);
				pstmt.setString(5, description);
				pstmt.executeUpdate();
				con.commit(); 

			} catch (SQLException e) {
		       e.printStackTrace();
		 		con.rollback(); 
				
			} finally {
				con.setAutoCommit(true);
			}

		} catch (SQLException e) {
			e.printStackTrace(); 
			throw e;
		}

	
		
	}
	
	public void saveOrder(String userId, List<CartItem> cartItems, String shippingAdd , String transactionID) throws SQLException {
	    Connection connection = null;
	    PreparedStatement orderStmt = null;
	    PreparedStatement itemStmt = null;
	    PreparedStatement updateStockStmt = null;
	    PreparedStatement clearCartStmt = null;
	    PreparedStatement updateUserSTMT = null;
	    ResultSet generatedKeys = null;

	    try {
	        connection = getConnectionAndDriver();
	        connection.setAutoCommit(false);
	        
	        String orderSQL = "INSERT INTO orders (user_id, status, total_amount, shipping_address , tranID) VALUES (?, ?, ?, ? , ?)";
	        orderStmt = connection.prepareStatement(orderSQL, new String[]{"order_id"});
	        orderStmt.setInt(1, Integer.parseInt(userId));
	        orderStmt.setString(2, "pending");
	        orderStmt.setDouble(3, calculateTotalAmount(cartItems));
	        orderStmt.setString(4, shippingAdd);
	        orderStmt.setString(5, transactionID);

	        int rowsInserted = orderStmt.executeUpdate();
	        generatedKeys = orderStmt.getGeneratedKeys();

	        if (rowsInserted > 0 && generatedKeys.next()) {
	            int orderId = generatedKeys.getInt(1);

	            String itemSQL = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
	            itemStmt = connection.prepareStatement(itemSQL);

	            String updateStockSQL = "UPDATE product SET stock = stock - ? WHERE product_id = ?";
	            updateStockStmt = connection.prepareStatement(updateStockSQL);

	            for (CartItem item : cartItems) {
	                itemStmt.setInt(1, orderId);
	                itemStmt.setString(2, item.getProduct().getProductId());
	                itemStmt.setInt(3, item.getQuantity());
	                itemStmt.setDouble(4, item.getProduct().getPrice());
	                itemStmt.addBatch();

	                // Update stock of the product
	                updateStockStmt.setInt(1, item.getQuantity());
	                updateStockStmt.setString(2, item.getProduct().getProductId());
	                updateStockStmt.addBatch();
	            }

	            itemStmt.executeBatch();
	            updateStockStmt.executeBatch();
	            connection.commit();

	            // Clear the cart for the specific user
	            String clearCartSQL = "DELETE FROM cart WHERE user_id = ?";
	            clearCartStmt = connection.prepareStatement(clearCartSQL);
	            clearCartStmt.setInt(1, Integer.parseInt(userId));
	            clearCartStmt.executeUpdate();                                        
	            String updateUserAddreddSQL = "UPDATE userdata SET address =  ? WHERE id = ?";

	             updateUserSTMT = connection.prepareStatement(updateUserAddreddSQL);
	             updateUserSTMT.setString(1, shippingAdd);
	             updateUserSTMT.setString(2, userId);
	             updateUserSTMT.executeUpdate();
	        }

	    } catch (SQLException e) {
	        if (connection != null) {
	            try {
	                connection.rollback();
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	        throw e;

	    } finally {
	        if (generatedKeys != null) {
	            generatedKeys.close();
	        }
	        if (orderStmt != null) {
	            orderStmt.close();
	        }
	        if (itemStmt != null) {
	            itemStmt.close();
	        }
	        if (updateStockStmt != null) {
	            updateStockStmt.close();
	        }
	        if (clearCartStmt != null) {
	            clearCartStmt.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }
	}



	
	    private double calculateTotalAmount(List<CartItem> cartItems) {
	        return cartItems.stream().mapToDouble(item -> item.getTotalPrice()).sum();
	    }

	    public List<Order> getOrdersByUser(String userId) {
	        List<Order> orders = new ArrayList<>();
	        String orderQuery = "SELECT o.tranid, o.order_id, o.status, o.total_amount, o.shipping_address " +
	                "FROM orders o WHERE o.user_id = ?"; // Assuming you have an 'orders' table

	        try (Connection connection = getConnectionAndDriver();
	             PreparedStatement orderStmt = connection.prepareStatement(orderQuery)) {
	             
	            orderStmt.setString(1, userId);
	            ResultSet orderRs = orderStmt.executeQuery();
	            
	            while (orderRs.next()) {
	                String orderId = orderRs.getString("order_id");
	                String status = orderRs.getString("status");
	                double totalAmount = orderRs.getDouble("total_amount");
	                String shippingAddress = orderRs.getString("shipping_address");
	                String tranID = orderRs.getString("tranid");
	                // Fetch the items for this order
	                List<CartItem> orderItems = getOrderItems(orderId);
	                System.out.println("tranID"+tranID);
	                // Create an Order object
	                Order order = new Order(orderId +"( Tran_ID "+tranID+" )", userId, status, totalAmount, shippingAddress, orderItems);
	                orders.add(order);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception (log it, rethrow it, or handle it based on your application's needs)
	        }
	        
	        return orders;
	    }
	    
	    public List<Order> getOrdersForAdmin() {
	        List<Order> orders = new ArrayList<>();
	        String orderQuery = "SELECT o.order_date, o.tranid, o.order_id, o.status, o.total_amount, o.shipping_address, u.username , u.id " +
	                "FROM orders o " +
	                "JOIN userdata u ON u.id = o.USER_ID " +
	                "ORDER BY o.order_id DESC"; 

	        try (Connection connection = getConnectionAndDriver();
	             PreparedStatement orderStmt = connection.prepareStatement(orderQuery)) {
	             
	            ResultSet orderRs = orderStmt.executeQuery();
	            
	            while (orderRs.next()) {
	                String orderId = orderRs.getString("order_id");
	                String username = orderRs.getString("username"); // Ensure you're fetching the username correctly
	                String id = orderRs.getString("ID");
	                String status = orderRs.getString("status");
	                double totalAmount = orderRs.getDouble("total_amount");
	                String shippingAddress = orderRs.getString("shipping_address");
	                String tranID = orderRs.getString("tranid");
	                String date = orderRs.getString("order_date");
	                // Fetch the items for this order
	                List<CartItem> orderItems = getOrderItems(orderId);
	                System.out.println("tranID: " + tranID);
	                // Create an Order object
	                Order order = new Order(orderId  , id + " (Tran_ID " + tranID + ")", status, totalAmount, shippingAddress, orderItems , username ,date );
	                order.setTranId(tranID);
	                orders.add(order);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception (log it, rethrow it, or handle it based on your application's needs)
	        }
	        
	        return orders;
	    }

	    private List<CartItem> getOrderItems(String orderId) {
	        List<CartItem> cartItems = new ArrayList<>();
	        String itemQuery = "SELECT oi.product_id, oi.quantity, oi.price, p.product_name, p.image_url " +
	                "FROM order_items oi " +  // Table that links orders and products
	                "JOIN product p ON oi.product_id = p.product_id " + // Joining with the products table to get product details
	                "WHERE oi.order_id = ?"; // Filtering by order_id

	        try (Connection connection = getConnectionAndDriver();
	             PreparedStatement itemStmt = connection.prepareStatement(itemQuery)) {
	            
	            itemStmt.setString(1, orderId);
	            ResultSet itemRs = itemStmt.executeQuery();
	            
	            while (itemRs.next()) {
	                String productId = itemRs.getString("product_id");
	                String productName = itemRs.getString("product_name");
	                float price = itemRs.getFloat("price");
	                int quantity = itemRs.getInt("quantity");
	                String imageUrl = itemRs.getString("image_url");

	                Product product = new Product();
	                product.setProductId(productId);
	                product.setProductName(productName);
	                product.setPrice(price);
	                product.setImageUrl(imageUrl); // Assuming you have an appropriate setter in your Product class

	                CartItem cartItem = new CartItem();
	                cartItem.setProduct(product);
	                cartItem.setQuantity(quantity);
	                
	                cartItems.add(cartItem); // Adding the cartItem to the list
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return cartItems;
	    }

	    public void approveOrReject(String order_id, boolean status) {
	        String newStatus = status ? "In Transit" : "Rejected";
	        String updateOrderQuery = "UPDATE orders SET status = ? WHERE order_id = ?";
	        String updateStockQuery = "UPDATE product SET stock = stock + ? WHERE product_id = ?";
	        
	        try (Connection connection = getConnectionAndDriver();
	             PreparedStatement updateOrderStmt = connection.prepareStatement(updateOrderQuery)) {
	             
	            // Update the order status
	            updateOrderStmt.setString(1, newStatus);
	            updateOrderStmt.setInt(2, Integer.parseInt(order_id)); // Ensure order_id is an integer
	            
	            int rowsUpdated = updateOrderStmt.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("Order status updated successfully.");

	                // Fetch the quantities and product IDs for the items in this order
	                List<CartItem> orderItems = getOrderItems(order_id); // Assuming this method retrieves order items

	                try (PreparedStatement updateStockStmt = connection.prepareStatement(updateStockQuery)) {
	                    for (CartItem item : orderItems) {
	                        updateStockStmt.setInt(1, item.getQuantity()); // Add the quantity back to stock
	                        updateStockStmt.setString(2, item.getProduct().getProductId()); // Ensure product_id is an integer
	                        updateStockStmt.addBatch();
	                    }
	                    updateStockStmt.executeBatch(); // Execute all updates in batch
	                }
	            } else {
	                System.out.println("Order not found or status not changed.");
	            }
	        } catch (SQLException e) {
	            System.err.println("SQL Error: " + e.getMessage());
	            e.printStackTrace();
	        } catch (NumberFormatException e) {
	            System.err.println("Invalid number format for order_id: " + order_id);
	            e.printStackTrace();
	        }
	    }

	    public void addStockToProduct(String prodId, int stockQty) {
	        String updateStockQuery = "UPDATE product SET stock = stock + ? WHERE product_id = ?";
	        
	        try (Connection connection = getConnectionAndDriver();
	             PreparedStatement updateStockStmt = connection.prepareStatement(updateStockQuery)) {
	            
	            // Set the parameters for the query
	            updateStockStmt.setInt(1, stockQty); // Amount to add to stock
	            updateStockStmt.setString(2, (prodId)); // Product ID
	            
	            // Execute the update
	            int rowsUpdated = updateStockStmt.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("Stock updated successfully for product ID: " + prodId);
	            } else {
	                System.out.println("No product found with ID: " + prodId);
	            }
	        } catch (SQLException e) {
	            System.err.println("SQL Error: " + e.getMessage());
	            e.printStackTrace();
	        } catch (NumberFormatException e) {
	            System.err.println("Invalid number format for product ID: " + prodId);
	            e.printStackTrace();
	        }
	    }







}
