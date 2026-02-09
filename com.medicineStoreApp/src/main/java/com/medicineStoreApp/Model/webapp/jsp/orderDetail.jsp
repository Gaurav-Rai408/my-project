<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.medicineStoreApp.Model.Order"%>
<%@ page import="com.medicineStoreApp.Model.CartItem"%>
<%@ page import="com.medicineStoreApp.dbHandler.DbHandler"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Orders</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
        integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <style>
        .order-row {
            background-color: #f8f9fa;
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 10px;
        }
        .order-header {
            font-weight: bold;
            font-size: 1.2em;
            margin-bottom: 10px;
        }
        .btn-danger {
            margin-left: 10px;
        }
    </style>
</head>
<body>

<%

    String userID = (String) session.getAttribute("userID");
    String userName = (String) session.getAttribute("userName");

    if (userID == null) {
        // Redirect to login if session is invalid
        response.sendRedirect("login.jsp");
        return;
    }

    DbHandler dbHandler = new DbHandler();
    List<Order> orders = dbHandler.getOrdersByUser(userID);
%>

<div class="container mt-4">
    <div class="row border border-secondary p-3 mb-3" style="border-radius: 10px;">
        <div class="col-3">
            <h3 class="text-secondary">Your Orders</h3>
        </div>
        <div class="col-3">
            <h3>Hello, <%= userName %></h3>
        </div>
        <div class="col-3">
            <form action="c" method="post">
                <input type="hidden" name="command" value="goToHome">
                <input type="submit" class="btn btn-secondary" value="Go to Home">
            </form>
          
        </div>
         <div class="col-3 ">
            <form action="c" method="post" class="d-inline">
                <input type="hidden" value="logout" name="command">
                <input class="btn btn-danger" value="Logout" type="submit">
            </form>
        </div>
    </div>

    <% if (orders != null && !orders.isEmpty()) { %>
        <div class="list-group">
            <% for (Order order : orders) { %>
                <div class="order-row list-group-item">
                    <div class="order-header">Order ID: <%= order.getOrderId() %> | Status: <%= order.getStatus() %></div>
                    <p>Total Amount: &#8377; <%= order.getTotalAmount() %></p>
                    <p>Shipping Address: <%= order.getShippingAddress() %></p>
                    
                    <h5>Order Details:</h5>
                    <ul class="list-group">
                        <% for (CartItem item : order.getOrderItems()) { %>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-md-3">
                                        <img src="medicinesIMG/<%= item.getProduct().getImageUrl() %>" alt="Product Image" class="img-fluid">
                                    </div>
                                    <div class="col-md-6">
                                        <h6><%= item.getProduct().getProductName() %></h6>
                                        <p>Quantity: <%= item.getQuantity() %></p>
                                        <p>Price per item: &#8377; <%= item.getProduct().getPrice() %></p>
                                    </div>
                                </div>
                            </li>
                        <% } %>
                    </ul>
                </div>
            <% } %>
        </div>
    <% } else { %>
        <h4>No orders found.</h4>
    <% } %>
</div>

</body>
</html>
