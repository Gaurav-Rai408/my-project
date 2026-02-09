<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="javax.servlet.annotation.WebServlet"%>
<%@page import="com.medicineStoreApp.Model.Product"%>
<%@page import="com.medicineStoreApp.*"%>
<%@page import="com.medicineStoreApp.dbHandler.DbHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%
if(session.getAttribute("u.name")==null||session.getAttribute("u.phone")==null){	
	  
	//session.invalidate();

	// Set cache control headers
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
	response.sendRedirect("http://localhost:9090/com.bookstoreApp/");
 } %> --%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
    if (performance.navigation.type === 2) {
        // Page loaded from cache, redirect to login page
        window.location.href = "http://192.168.10.74:9090/com.bookstoreApp/";
    }
</script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
	<link rel="stylesheet" href="bookhomepage.css">
<title>BookStore</title>
<style type="text/css">
img{
height: 400px;
width: 300px;
}
.custom{
height: 400px;
width: 300px;
overflow: auto;
}
.navv1 {
	color: Red;
}
.navv {
	animation: colorChange 4s infinite;
}
.mb1{
margin-top: 20%;
}

@keyframes colorChange{ 
0%{
	color: red;
	background-color: yellow;
	
}
10%
{
  color:voilet; 
    background-color: white;
    border-radius:20px;
}
20%
{

color: green; 
background-color: pink;
border-radius:20px;
}
30%
{
color:yellow;
background-color: blue;
border-radius:20px;
}
40%
{
color:red;   
background-color: yellow;
border-radius:20px;
}
50%
{
color:white;   
background-color: black;
border-radius:20px;
}
70%
{
color:pink;  
background-color: blue;
border-radius:20px;
}
100%
{
color:black;
background-color: white;
}
}
.image{
background-color: silver;
}
#nameHead{
    background-color: aqua;
}
.rowADD{
height: 2px;
background-color: silver;
}
#setIMG{
height: 30px;
width: 30px;
background-color: silver;
}
.profDisp{
display:none;
height: 50%px;
width: 50%;
position: absolute;
top: 100%;
left: 30%;

background-color: fuchsia;
border-radius: 20px;
}
</style>
</head>
<body>
<%
	
	 HttpSession session2 =  request.getSession();
	String userID =  (String) session2.getAttribute("userID");
	String userName = " Hello "+ (String) session2.getAttribute("userName");
	// response.sendRedirect("http://localhost:8080/com. /");
 %>

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="mx-5">
		<div class="row">

			<div class="col m-auto">
			<div>
			
			<img src="medicinesIMG/medicineStore.jpg" alt="Image not available" class="img-fluid" style="max-height: 40px;">
			
			</div>	 
			</div>


			<div class="col m-auto">
				<h3> <%=userName%></h3>
			</div>
			<div class="col m-auto">
				<div class="form-inline my-2 my-lg-0 d-flex m-auto">
					<input class=" mr-sm-2 form-control w-auto" type="search" placeholder="Search"
						aria-label="Search">
					<button class="btn btn-outline-success mx-3" type="submit">Search</button>
				</div>
			</div>
			<div class="col m-auto">
			<form action="c" method="post">
			<input type="hidden" name="command" value="goToCart" />
			<%-- <input type="hidden" name="customerId" value="<%=userID%>"/> --%>
				<input type="submit" class="ViewCart btn btn-secondary" value="View Cart">
			</form>
			</div>
			<div class="col m-auto">
			<form action="c" method="post">
			<input type="hidden" name="customerId" value="<%=userID%>"/>
			<input type="hidden" name="command" value="goToOrders" />
				<input type="submit" class="ViewCart btn btn-secondary" value="view Orders">
			</form>
			</div>

             <div class="col m-auto ">
             <form action="c" method="post">
                <input type="hidden" value="logout" name="command">
                
				<input class=" btn btn-danger" value="Logout" type="submit">
			</form>
			</div>

		</div>
	</div>
	</nav>
	<div class="row" style="background-color: silver;">
    
      <div class="col text-center"><h1>Welcome to Homepage</h1></div>           
      </div>

<%
    DbHandler cd = new DbHandler();
    List<Product> products = cd.getAllProducts();

    for (Product product : products) {
       // String category = cd.getCategoryById(product.getCategoryId()); // Assuming you have a method to get the category by ID
%>
     <div class="row py-4 border-bottom m-2">
        <div class="col-lg-3 col-md-4 col-sm-12 d-flex align-items-center justify-content-center">
            <img src="medicinesIMG/<%=product.getImageUrl()%>" alt="Image not available" class="img-fluid" style="max-height: 150px;">
        </div>
        
        <div class="col-lg-6 col-md-5 col-sm-12">
            <h2 class="product-name mb-3"><%=product.getProductName() %></h2> 
        
            <div class="mb-3">
                <h4>Price: &#8377; <%= product.getPrice()%></h4>
            </div>

            <div class="d-flex flex-column flex-sm-row">
                <div class="mb-3 mb-sm-0">
                    <% if (product.getStock() >= 1) { %>
                        <button class="btn btn-primary">Available</button>
                    <% } else { %>
                        <button class="btn btn-danger">Not Available</button>
                    <% } %>
                        <% if (product.getStock() <= 5) { %>
                        <small >only <%= product.getStock()  %> qty available</small>
                    <% }  %>
                </div>

                <div class="ms-sm-3 mb-3 mb-sm-0">
                    <form action="c" method="post">
                        <input type="hidden" name="customerId" value="<%=userID%>" />
                         <input type="hidden" name="qty" value="<%=1%>" />
                          <input type="hidden" name="price" value="<%=product.getPrice()%>" />
                        <input type="hidden" name="command" value="addToCart">
                        <input type="hidden" name="p_id" value="<%=product.getProductId() %>">
                          <% if (product.getStock() >= 1) { %>
                        <input class="btn btn-success" type="submit" value="Add To Cart">
                           <% } %>
                    </form>
                </div>

                <div class="ms-sm-3">
                    <button class="btn btn-secondary">Reviews</button>
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
%>


	
	
</body>
</html>