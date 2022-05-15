<%@ page import="com.customerManage"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>customer Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/validcustomer.js"></script> 
</head>
<body>

		<div class="container"> 
		<div class="row">  
			<div class="col"> 
				<h1>customer Management</h1>
				<br>
					<form id="formcustomer" name="formcustomer" method="post" action="customer.jsp">  
						customer Name:  
	 	 				<input id="customerName" name="customerName" type="text"  class="form-control form-control-sm">
						<br>customer Address:   
	  					<input id="customerAddress" name="customerAddress" type="text" class="form-control form-control-sm" >   
	  					<br>customer NIC:   
	  					<input id="customerNIC" name="customerNIC" type="text"  class="form-control form-control-sm">
						<br>customer Email:
						<input id="customerEmail" name="customerEmail" type="text"  class="form-control form-control-sm">
						<br>customer Phone Number:
						<input id="customerPNO" name="customerPNO" type="text"  class="form-control form-control-sm">
						<br>
						
						<div id="alertSuccess" class="alert alert-success"> </div>				
				   		<div id="alertError" class="alert alert-danger"></div>
				   		 
						<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
						<input type="hidden" id="hidcustomerIDSave" name="hidcustomerIDSave" value=""> 
					</form>
					
					
				   <br>
					<div id="customer">
						<%
						customerManage customerObj = new customerManage();
						out.print(customerObj.readcustomer());
						%>
					</div>
					
					 
				</div>
			</div>
	</div>

</body>
</html>