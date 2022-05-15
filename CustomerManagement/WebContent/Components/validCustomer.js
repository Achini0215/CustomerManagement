$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateCustomerForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidCustomerIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "CustomerManageAPI",  
			type : type,  
			data : $("#formCustomer").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onCustomerSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onCustomerSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#Customer").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidCustomerIDSave").val("");  
	$("#formCustomer")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidCustomerIDSave").val($(this).closest("tr").find('#hidCustomerIDUpdate').val());     
	$("#CustomerName").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#CustomerAddress").val($(this).closest("tr").find('td:eq(1)').text());
	$("#CustomerNIC").val($(this).closest("tr").find('td:eq(2)').text());
	$("#CustomerEmail").val($(this).closest("tr").find('td:eq(3)').text());
	$("#CustomerPNO").val($(this).closest("tr").find('td:eq(4)').text());
	    
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "CustomerManageAPI",   
		type : "DELETE",   
		data : "uID=" + $(this).data("uid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onCustomerDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onCustomerDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#Customer").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateCustomerForm() 
{  
	// CustomerName  
	if ($("#CustomerName").val().trim() == "")  
	{   
		return "Insert CustomerName.";  
	}

	// CustomerAddress------------------------  
	if ($("#CustomerAddress").val().trim() == "")  
	{   
		return "Insert CustomerAddress.";  
	} 
	
	// CustomerNIC------------------------  
	if ($("#CustomerNIC").val().trim() == "")  
	{   
		return "Insert CustomerNIC.";  
	}
	
	// CustomerEmail------------------------  
	if ($("#CustomerEmail").val().trim() == "")  
	{   
		return "Insert CustomerEmail.";  
	}
	
	// CustomerPNO------------------------  
	if ($("#CustomerPNO").val().trim() == "")  
	{   
		return "Insert CustomerPNO.";  
	}
	

	return true; 
}

