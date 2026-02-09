<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
	<style type="text/css">
	body{
	background-color: SkyBlue;
	}
	.blue{
	background-color: silver;
	}
	.white{
	color: silver;
	}
	label {
	color: black;
	font-size: 15px;
	font-family: sans-serif;
}
span{
color: red;q
}
	button {
      margin-left: 85%;
      border: none;
      background-color: silver;
      margin-bottom: 2%;
   } 
   .a{
   display: block;
   }
   .b{
   display: none;
   }
   .error{
      
    margin-left: 60%;
    color: red
   }
   
	
	</style>
	
	
	
<title>E-DrugStore</title>
</head>
<body >
  
<div class="container mt-5 text-center">
   <div class="a" id="loginform">
      <div class="row card text-center blue">
       <h3 class="m-auto">LOGIN </h3>
      </div>
      <div class="row card ">
      <form action="c" id="loginForm" method="post">
    
       <input type="hidden" name="command" value="doLogin">
         <div class=" mt-3 ">
          <label>NAME</label><span>*</span>
          <input type="text" name="username" placeholder="Username" class="form-control m-auto" style="width: 30%; ">
         </div>
         <div class=" mt-3">
          <label>PASSWORD</label><span>*</span>
          <input type="password" name="password" placeholder="Password" class="form-control m-auto" style="width: 30%;">
         </div>
        
         <div class=" mt-3 ">
          <input type="submit" name="Login" value="LOGIN" class="btn btn-primary m-auto" >
         </div>
         <div class="mt-3">
          <button id="loginbutton" >REGISTER</button>
         </div>
      </form>   
     </div>   
   </div>
   
   
   <div id="registrationform" class="b">
       <div class="row card text-center blue">
       <h3 class="m-auto">REGISTRATION </h3>
       </div>
     <div class="row card ">
      <form action="c" id="RegisterForm" method="post">
        <div class=" mt-3 ">
          <label>NAME</label><span>*</span>
          <input type="hidden" value="doRegister" name="command">
          <input type="text" name="username1" placeholder="Enter Your Name" class="form-control m-auto" style="width: 30%; ">
        </div>
        <div class=" mt-3">
          <label> Create password</label><span>*</span>
          <input type="password" name="password1" placeholder="Create New Password" class="form-control m-auto" style="width: 30%;">
        </div>
        
         <div class=" mt-3 ">
         <label>Phone No</label><span>*</span>
          <input pattern="[6-9]{1}[0-9]{9}" type="text" name="phone1" placeholder="Enter Number" class="form-control m-auto" style="width: 30%;">
          
        </div>
        <div class=" mt-3 ">
        <label>Email</label><span>*</span>
          <input type="text" name="mail1" placeholder="Enter email" class="form-control m-auto" style="width: 30%; ">
        </div>
       <div class="mt-3">
    <label class="mr-2">Gender: </label>&nbsp;
    
    <div class="form-check form-check-inline">
        <input class="form-check-input" type="radio" id="genderMale" value="male" name="gender1">
        <label class="form-check-label" for="genderMale">Male</label>
    </div>&nbsp;

    <div class="form-check form-check-inline">
        <input class="form-check-input" type="radio" id="genderFemale" value="female" name="gender1">
        <label class="form-check-label" for="genderFemale">Female</label>
    </div>&nbsp;

    <div class="form-check form-check-inline">
        <input class="form-check-input" type="radio" id="genderOthers" value="others" name="gender1">
        <label class="form-check-label" for="genderOthers">Others</label>
    </div>
</div>

         <div class=" mt-3 ">
          <input type="submit"  value="Register" class="btn btn-danger m-auto ">
        </div>
        
        <div class="mt-3">
          <button id="registerbutton" >LOGIN</button>
        </div>     
     </form>  
    </div>
   </div>
 </div>
<script type="text/javascript">



  const loginform = document.getElementById("loginform");
  const registrationform = document.getElementById("registrationform");
  const loginbutton = document.getElementById("loginbutton");
  const registerbutton = document.getElementById("registerbutton");

  loginbutton.addEventListener('click', function(e) {
    e.preventDefault();
   console.log("add something")
    loginform.style.display = "none";
    registrationform.style.display = "block";
  });

  registerbutton.addEventListener('click', function(e) {
    e.preventDefault();
    loginform.style.display = "block";
    registrationform.style.display = "none";
  });
</script>
 <script type="text/javascript">
   const form2 = document.getElementById("RegisterForm");
   form2.addEventListener("submit", function (e) {
     e.preventDefault();
     
    b= validate2();
    
    if(b===true){
 	   form2.submit();   
    }
    });
    function validate2() {
    	const username1 = document.getElementsByName("username1")[0];
        const password1 = document.getElementsByName("password1")[0];
        const phone1 = document.getElementsByName("phone1")[0];
        const mail1 = document.getElementsByName("mail1")[0];
        const gender1 = document.getElementsByName("gender1")[0];
		if(username1.value.trim()===""){
			alert("error")
			alert("name should not be empty");
		  return false;
	    }
        if(password1.value.trim()===""){
        	alert("password should not be empty");
	      return false;
        }
        if(mail1.value.trim()===""){
        	alert("Email should not be empty");
         return false;
        }
        if(phone1.value.trim()===""){
        	alert("phone should not be empty");
         return false;
        }
       if(gender1.value.trim()===""){
    	   alert("select the Gender");
         return false;
        }
        return true;
     
   }
   </script>
<script type="text/javascript">
   const form = document.getElementById("loginForm");
  form.addEventListener("submit", function (e) {
    e.preventDefault();
    
  a= validate();
 if(a===true){
	 form.submit();  
 }
  });
   function validate() {
    const userName = document.getElementsByName("username")[0];
    const userPass = document.getElementsByName("password")[0];

    if (userName.value.trim() === "") {
    	alert("error")
      alert("name should not be empty");
     
      return false;
    }
    if (userPass.value.trim() === "") {
      alert("password should not be empty");
     
      return false;
    }
    return true;
  }
   </script>

</body>
</html>
