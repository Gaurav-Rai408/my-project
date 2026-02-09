<%@ page import="java.util.List" %>
<%@ page import="com.medicineStoreApp.Model.CartItem" %>
<%@ page import="com.medicineStoreApp.dbHandler.DbHandler" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" 
        integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="cart.css">
    <title>Cart</title>
    <style>
        img {
            height: 100px;
            width: 100px;
        }
        .cart-row {
            background-color: #f8f9fa;
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 10px;
        }
        .btn-danger {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<%
    HttpSession session2 = request.getSession();
    String userID = (String) session2.getAttribute("userID");
    String userName = (String) session2.getAttribute("userName");
   String userAdd = (String) session2.getAttribute("userAdd")!= null ? (String) session2.getAttribute("userAdd"): "";
    if (userID == null) {
        response.sendRedirect("login.jsp");
    }

    DbHandler dbHandler = new DbHandler();
    List<CartItem> cartItems = dbHandler.getCartItemsByUserId(userID);
    if (cartItems != null) {
        session.setAttribute("cartItems", cartItems);
    }
    double totalPrice = 0;

    if (cartItems != null && !cartItems.isEmpty()) {
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
    }
%>

<div class="container mt-3">
    <div class="row border border-secondary custom-border p-3 rounded mb-3">
        <div class="col-md-4 text-center">
            <h3>Your Cart</h3>
        </div>
        <div class="col-md-4 text-center">
            <h3>Hello, <%=userName%></h3>
        </div>
        <div class="col-md-4 d-flex justify-content-around align-items-center">
            <form action="c" method="post">
                <input type="hidden" value="goToHome" name="command">
                <input type="submit" value="Go to Home" class="btn btn-secondary">
            </form>
            <form action="c" method="post">
                <input type="hidden" name="command" value="goToOrders">
                <input type="submit" value="View Orders" class="btn btn-secondary">
            </form>
            <form action="c" method="post">
                <input type="hidden" value="logout" name="command">
                <input type="submit" value="Logout" class="btn btn-danger">
            </form>
        </div>
    </div>

    <h2>Your Cart Items</h2>
    <div class="row">
        <div class="col-12">
            <% if (cartItems != null && !cartItems.isEmpty()) { %>
                <div class="list-group">
                    <% for (CartItem item : cartItems) { %>
                        <div class="cart-row list-group-item">
                            <div class="row">
                                <div class="col-lg-2 col-md-3 col-sm-12">
                                    <img src="medicinesIMG/<%=item.getProduct().getImageUrl()%>" alt="Product Image" class="img-fluid">
                                </div>
                                <div class="col-lg-6 col-md-5 col-sm-12">
                                    <h5><%=item.getProduct().getProductName() %></h5>
                                    <p>Quantity: <%=item.getQuantity()%></p>
                                    <p>Price per item: &#8377; <%=item.getProduct().getPrice()%></p>
                                    <p>Total Price: &#8377; <%=item.getTotalPrice()%></p>
                                </div>
                                <div class="col-lg-4 col-md-4 col-sm-12 d-flex align-items-center justify-content-end">
                                    <form action="c" method="post"  >
                                        <input type="hidden" name="command" value="removeFromCart">
                                        <input type="hidden" name="cartItemId" value="<%=item.getCartId()%>">
                                        <input type="submit" class="btn btn-danger" value="Remove">
                                    </form>

                                </div>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <h4 class="text-center mt-3">Your cart is empty.</h4>
            <% } %>
        </div>
    </div>

    <% if (totalPrice > 0) { %>
        <div class="row mt-4">
            <div class="col-12 text-end">
                <h4>Total Price: &#8377; <%=totalPrice %></h4>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col-12">
                <form action="c" method="post" class="d-flex align-items-center" id="checkoutItem">
                    <div class="input-group">
                       <div class="input-group">
                            <label>
                               <span >Shipping Address</span>
                                </label>
                         <input type="text" name="address" class="form-control form-control-md" >
                         </div>
                        <div class="input-group mt-5">
                            <p class="mb-1">
                                <span style="color:green">Pay &#8377; <%= totalPrice %> on</span> 
                                <span style="color:red">1234567890@yml</span> 
                                <span>and enter Transaction ID below</span>
                            </p>
                            <input type="text" name="transactionID" class="form-control form-control-md" placeholder="Enter Transaction ID">
                        </div>
                    </div>
                    <div class="col-4 d-flex justify-content-end"  >
                        <input type="submit" class="btn btn-success btn-lg" value="Proceed to Checkout">
                    </div>
                    <input type="hidden" name="command" value="checkout">
                    <input type="hidden" name="userId" value="<%=userID%>">
                </form>
                                                  
            </div>
        </div>
    <% } %>
</div>

</body>
  <script type="text/javascript">
   const form = document.getElementById("checkoutItem");
  form.addEventListener("submit", function (e) {
    e.preventDefault();
    
  a= validate();
 if(a===true){
	 form.submit();  
 }
  });
   function validate() {
    const transactionID = document.getElementsByName("transactionID")[0];
    const address = document.getElementsByName("address")[0];
    if (!address.value.trim()) {
        alert("Please enter shipping address");
       
        return false;
      }
    if (!transactionID.value.trim()) {
      alert("transactionID should not be empty , Please pay and enter transaction ID");
     
      return false;
    }
   
    return true;
  }
   </script>
</html>
