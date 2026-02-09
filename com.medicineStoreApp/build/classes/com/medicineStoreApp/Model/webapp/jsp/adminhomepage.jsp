<%@ page import="java.util.List"%>
<%@ page import="com.medicineStoreApp.Model.Customer"%>
<%@ page import="com.medicineStoreApp.Model.Product"%>
<%@ page import="com.medicineStoreApp.Model.Order"%>
<%@ page import="com.medicineStoreApp.dbHandler.DbHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <style>
        .details-section {
            display: none;
            margin-top: 20px;
        }
        .backColor {
            background-color: #f0f8ff;
        }
        .product-image {
            max-height: 100px;
            width: auto;
        }
    </style>
    <script>
        function showSection(sectionId) {
            document.getElementById("customers-section").style.display = "none";
            document.getElementById("products-section").style.display = "none";
            document.getElementById("add-product-section").style.display = "none";
            document.getElementById("orders-section").style.display = "none";

            document.querySelectorAll('.details-section').forEach(section => {
                section.classList.remove("active", "backColor");
            });

            const selectedSection = document.getElementById(sectionId);
            if (selectedSection) {
                selectedSection.style.display = "block";
                selectedSection.classList.add("active", "backColor");
            }
        }
    </script>
</head>
<body>

    <div class="container">
          	<div class="row justify-content-start">
			<div class="col-1">
			<img src="medicinesIMG/medicineStore.jpg" alt="Image not available" class="img-fluid" style="max-height: 60px;">
			</div>
			<div class="col-6">
			 <h2>Management Dashboard</h2>
			</div>
			<div class="col-3 ">
            <form action="c" method="post" class="d-inline">
            <input type="hidden" value="logout" name="command">
            <input class="btn btn-danger" value="Logout" type="submit">
            </form>
            </div>
			 
			</div>
      

        <!-- Header Navigation -->
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link" href="javascript:void(0)" onclick="showSection('customers-section')">All Customers</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="javascript:void(0)" onclick="showSection('products-section')">All Products</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="javascript:void(0)" onclick="showSection('add-product-section')">Add Product</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="javascript:void(0)" onclick="showSection('orders-section')">All Orders</a>
            </li>
        </ul>

        <!-- Customers Section -->
        <div id="customers-section" class="details-section">
            <h3>All Customers</h3>
            <ul class="list-group">
                <%
                    DbHandler dbHandler = new DbHandler();
                    List<Customer> customers = dbHandler.getAllCustomers();

                    if (customers != null && !customers.isEmpty()) {
                        for (Customer customer : customers) {
                %>
                    <li class="list-group-item">
                        <h5><%= customer.getUsername() %></h5>
                        <p>Email: <%= customer.getEmail() %></p>
                        <p>Phone: <%= customer.getPhone() %></p>
                        <p>Address: <%= customer.getAddress() %></p>
                        <p>Gender: <%= customer.getGender() %></p>
                        <p>Created Date: <%= customer.getCreationDate() %></p>
                    </li>
                <%
                        }
                    } else {
                %>
                    <li class="list-group-item">No customers available.</li>
                <%
                    }
                %>
            </ul>
        </div>

        <!-- Products Section -->
        <div id="products-section" class="details-section">
            <h3>All Products</h3>
            <%
                List<Product> products = dbHandler.getAllProducts();

                if (products != null && !products.isEmpty()) {
                    for (Product product : products) {
            %>
            <div class="row py-4 border-bottom m-2">
                <div class="col-lg-3 col-md-4 col-sm-12 d-flex align-items-center justify-content-center">
                    <img src="medicinesIMG/<%=product.getImageUrl()%>" alt="Image not available" class="product-image">
                </div>
                <div class="col-lg-6 col-md-5 col-sm-12">
                    <h2 class="product-name mb-3"><%=product.getProductName() %></h2>
                    <div class="mb-3">
                        <h4>Price: &#8377; <%= product.getPrice() %></h4>
                    </div>
                    <div class="d-flex flex-column flex-sm-row">
                        <div class="mb-3 mb-sm-0">
                            <h4>Stock left : <%= product.getStock() %></h4>
                        </div>
                        <div class="ms-sm-3">
                        <form action="c" method="post">
                        <input type="number" name="qty" placeholder ="Enter stock to add">
                         <input type="hidden" name="prod_id" value="<%=product.getProductId() %>">
                        <input type="hidden" name="command" value="addStock">
                         <button   class="btn btn-secondary">Add Stock</button>
                        </form>
                           
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-12 text-start text-lg-end">
                    <h4>Description:</h4>
                    <p><%=product.getDescription() %></p>
                </div>
            </div>
            <% 
                } // End of for loop 
            } else { %>
                <div class="alert alert-info">No products available.</div>
            <% } %>
        </div>

        <!-- Add Product Section -->
        <div id="add-product-section" class="details-section">
            <h3>Add New Product</h3>
            <form action="c" method="post">
                <div class="mb-3">
                    <label for="productName" class="form-label">Product Name</label>
                    <input type="text" class="form-control" id="productName" name="product_name" required>
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">Price</label>
                    <input type="number" class="form-control" id="price" name="price" step="0.01" required>
                </div>
                <div class="mb-3">
                    <label for="stock" class="form-label">Stock</label>
                    <input type="number" class="form-control" id="stock" name="stock" min="0" required>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                </div>
                <div class="mb-3">
                    <label for="imageUrl" class="form-label">Product Image URL</label>
                    <input type="text" class="form-control" id="imageUrl" name="image_url">
                    <input type="hidden" name="command" value="addProduct">
                </div>
                <button type="submit" class="btn btn-primary">Add Product</button>
            </form>
        </div>

        <!-- All Orders Section -->
        <div id="orders-section" class="details-section">
            <h3>All Orders</h3>
            <%
                List<Order> orders = dbHandler.getOrdersForAdmin();

                if (orders != null && !orders.isEmpty()) {
                    for (Order order : orders) {
            %>
            <div class="border p-3 mb-3">
             <h5>User Name: <%= order.getName() %></h5>
                <h5>Order ID: <%= order.getOrderId() %></h5>
                    <h5>Ordered Date: <%= order.getOrderedTime() %></h5>
               <%--  <p>Customer: <%= order.getCustomerName() %></p> --%>
                <p>Total Amount: &#8377; <%= order.getTotalAmount() %></p>
                <p>Status: <%= order.getStatus() %></p>
                 <p>TransationID: <%= order.getTranID() %></p>
                <% if (order.getStatus().equalsIgnoreCase("pending")){ %>
                <div class="d-flex">
                    <form action="c" method="post" class="me-2">
                        <input type="hidden" name="order_id" value="<%= order.getOrderId() %>">
                        <input type="hidden" name="command" value="approveOrder">
                        <button type="submit" class="btn btn-success">Approve</button>
                    </form>
                    <form action="c" method="post">
                        <input type="hidden" name="order_id" value="<%= order.getOrderId() %>">
                        <input type="hidden" name="command" value="rejectOrder">
                        <button type="submit" class="btn btn-danger">Reject</button>
                    </form>
                </div>
                <%} else {
                	
                } %>
            </div>
            <% 
                } // End of for loop 
            } else { %>
                <div class="alert alert-info">No orders available.</div>
            <% } %>
        </div>

    </div>

    <!-- By default, show the customers section -->
    <script>
        showSection('customers-section');
    </script>
</body>
</html>
