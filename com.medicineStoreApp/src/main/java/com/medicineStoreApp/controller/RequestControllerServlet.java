package com.medicineStoreApp.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.medicineStoreApp.Model.CartItem;
import com.medicineStoreApp.dbHandler.DbHandler;

/**
 * 
 */
@WebServlet("/c")
public class RequestControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	  HttpSession session=null;
	    
		public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
			session=req.getSession();

			  String command = req.getParameter("command");
			  System.out.println("command"+command);
              if("logout".equals(command)) {
            	  session.invalidate();
            	  res.sendRedirect(DbHandler.url);
              }else if("doLogin".equals(command)){
				 doLogin(req,res);
			 }else if("addStock".equals(command)){
				    String prodId = req.getParameter("prod_id");
				    int stockQty = req.getParameter("qty")!=null&&!req.getParameter("qty").equals("")?Integer.valueOf(req.getParameter("qty")):0;
				 new DbHandler().addStockToProduct( prodId,stockQty);
				 req.getServletContext().getRequestDispatcher("/jsp/adminhomepage.jsp").include(req, res);
			 } else if("rejectOrder".equals(command)){
				 approveOrRejectOrder(req,res,false);
			 }
			 else if("approveOrder".equals(command)){
				 approveOrRejectOrder(req,res,true);
			 }
			 else if("checkout".equals(command)){
				 checkOut(req,res);
			 }else if("addProduct".equals(command)){
				 addProduct(req,res);
			 }else if("doRegister".equals(command)){
				 doRegister(req,res);
			 }
			 else if ("addToCart".equals(command)) {
		            addToCart(req, res);
		        } else if ("goToOrders".equals(command)) {
				 req.getServletContext().getRequestDispatcher("/jsp/orderDetail.jsp").include(req, res);
		        }	
			 else if ("goToCart".equals(command)) {
				 req.getServletContext().getRequestDispatcher("/jsp/cart.jsp").include(req, res);
		        }	 else if ("goToHome".equals(command)) {
				 req.getServletContext().getRequestDispatcher("/jsp/homepage.jsp").include(req, res);
		        }
		        else if ("removeFromCart".equals(command)) {
		        	   String cartItemId = req.getParameter("cartItemId");
		        	   DbHandler dbHandler = new DbHandler();
		        	   dbHandler.deleteCartItemByID(cartItemId);
					 req.getServletContext().getRequestDispatcher("/jsp/cart.jsp").include(req, res);
			      }
			 
		}
		
		private void approveOrRejectOrder(HttpServletRequest req, HttpServletResponse res ,boolean status) throws ServletException, IOException {
			  String order_id = req.getParameter("order_id");
			  new DbHandler().approveOrReject(order_id,status);
				req.getServletContext().getRequestDispatcher("/jsp/adminhomepage.jsp").include(req, res);
		}
		private void checkOut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		    String userId = req.getParameter("userId");
		    String address = req.getParameter("address");
		    String transactionID = req.getParameter("transactionID");
			try {
			    @SuppressWarnings("unchecked")
				List<CartItem> cartItems = (List<CartItem>) req.getSession().getAttribute("cartItems");
			       DbHandler dbHandler = new DbHandler();
	        	   dbHandler.saveOrder(userId,cartItems , address , transactionID);
			    req.getServletContext().getRequestDispatcher("/jsp/orderDetail.jsp").include(req, res);
			}catch (Exception e) {
				 req.getServletContext().getRequestDispatcher("/jsp/cart.jsp").include(req, res);
			}
		}
		
		  private void addToCart(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		        String customerId = req.getParameter("customerId");
		        String productId = req.getParameter("p_id");
		        int qty = req.getParameter("qty")!= null ? Integer.parseInt( req.getParameter("qty")) : 0;
		        float price = req.getParameter("price")!= null ? Float.parseFloat( req.getParameter("price")) : 0;

		        try {
		            DbHandler dbHandler = new DbHandler();
		            dbHandler.addToCart(customerId, productId , qty ,price); // Assuming you have this method
		          
		          //  res.sendRedirect("jsp/cart.jsp");
		            req.getServletContext().getRequestDispatcher("/jsp/cart.jsp").include(req, res);
		        } catch (SQLException e) {
		            e.printStackTrace();
		            res.sendRedirect("jsp/error.jsp");
		        }
		    }

		private void  addProduct(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			   String productName = req.getParameter("product_name");
		        double price = Double.parseDouble(req.getParameter("price"));
		        int stock = Integer.parseInt(req.getParameter("stock"));
		        String description = req.getParameter("description");
		        String imageUrl = req.getParameter("image_url");

		        try {
		            DbHandler dbHandler = new DbHandler();
		            // Call a method to handle product addition
		            dbHandler.addProduct(productName, price, stock, description, imageUrl);
		           // res.sendRedirect(DbHandler.url+"/jsp/adminhomepage.jsp");
		            req.getServletContext().getRequestDispatcher("/jsp/adminhomepage.jsp").include(req, res);
		        	//req.getServletContext().getRequestDispatcher("/jsp/adminhomepage.jsp").include(req, res);
		        } catch (SQLException e) {
		            e.printStackTrace();
		            res.sendRedirect("/jsp/error.jsp");
		        }

			
		}
		
		private void doRegister(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			String username=req.getParameter("username1");
			String password=req.getParameter("password1");	
			long phone=(Long.parseLong(req.getParameter("phone1")));
			String mail=req.getParameter("mail1");
			String gender=req.getParameter("gender1");
				try {
					DbHandler gd=new DbHandler();		
					  gd.injectRegisterForm(username,password,phone,mail,gender);
						//req.getRequestDispatcher("/jsp/booklogin.jsp").forward(req, res);
						res.sendRedirect(DbHandler.url);
					} catch ( SQLException e) {
						e.printStackTrace();					
						res.sendRedirect("/jsp/homepageError.jsp");
						
					}
			
		}
		
		private void  doLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			String username=req.getParameter("username");
			String res1="";
			String password=req.getParameter("password");
			
			if(username.equals("admin")&&password.equals("admin")){
				req.getServletContext().getRequestDispatcher("/jsp/adminhomepage.jsp").include(req, res);
			}else{
				
				try {
					DbHandler gd=new DbHandler();
					System.out.println("Hii");
					ResultSet rs=gd.getUserPassword(username,password);
					
					if(rs.next()){	
						
						res1+=rs.getString("password");
						String	phone=rs.getString("phone");
						String	name=rs.getString("username");
						String c_date=rs.getString("c_date");
						String mail=rs.getString("mail");
						String gender=rs.getString("gender");
						String address=rs.getString("address");

	                     if(password.equals(res1)){	
	                    	 session.setAttribute("userID", rs.getString("ID"));
	                    	 session.setAttribute("userName", name);
	                    	 session.setAttribute("userAdd", address);
							req.getServletContext().getRequestDispatcher("/jsp/homepage.jsp").include(req, res);
						}else{
							req.getServletContext().getRequestDispatcher("/").include(req, res);
								}
					}else{
						req.getServletContext().getRequestDispatcher("/").include(req, res);
					}
				} catch (Exception e) {
					e.printStackTrace();

				} 
			}
		}

}
