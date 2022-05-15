package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public class customerManage {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// this sample 1

			// Provide the correct details: DBServer/DBName, Customername, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/electro_grid?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	//Insert Customer
	public String insertCustomer(String customerName, String customerAddress, String customerNIC, String customerEmail, String customerPNO) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into customer(`uID`,`customerName`,`customerAddress`,`customerNIC`,`customerEmail`,`customerPNO`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, customerName);
			preparedStmt.setString(3, customerAddress);
			preparedStmt.setString(4, customerNIC);
			preparedStmt.setString(5, customerEmail);
			preparedStmt.setString(6, customerPNO);
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newCustomer = readCustomer(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";  

		} catch (Exception e) {
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Customer. Try again\"}";  
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Read Customer
	public String readCustomer() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Customer Name</th><th>Customer Address</th><th>Customer NIC</th><th>Customer Email</th><th>Customer Phone Number</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from Customer";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String uID = Integer.toString(rs.getInt("cID"));
				String CustomerName = rs.getString("CustomerName");
				String CustomerAddress = rs.getString("CustomerAddress");
				String CustomerNIC = rs.getString("CustomerNIC");
				String CustomerEmail = rs.getString("CustomerEmail");
				String CustomerPNO = rs.getString("CustomerPNO");

				// Add into the html table
				output += "<tr><td><input id='hidCustomerIDUpdate' name='hidCustomerIDUpdate' type='hidden' value='" + uID + "'>" + CustomerName + "</td>";
				output += "<td>" + CustomerAddress + "</td>";
				output += "<td>" + CustomerNIC + "</td>";
				output += "<td>" + CustomerEmail + "</td>";
				output += "<td>" + CustomerPNO + "</td>";
			
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='hidItemIDDelete' type='button' value='Remove' class='btnRemove btn btn-danger' data-cid='" + cID + "'>" + "</td></tr>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Customer.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Update Customer
	public String updateCustomer(String uID, String CustomerName, String CustomerAddress, String CustomerNIC, String CustomerEmail, String CustomerPNO) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE Customer SET CustomerName=?,CustomerAddress=?,CustomerNIC=?,CustomerEmail=?,CustomerPNO=?" + "WHERE uID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, CustomerName);
			preparedStmt.setString(2, CustomerAddress);
			preparedStmt.setString(3, CustomerNIC);
			preparedStmt.setString(4, CustomerEmail);
			preparedStmt.setString(5, CustomerPNO);
			preparedStmt.setInt(6, Integer.parseInt(uID));

			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newCustomer = readCustomer();    
			output = "{\"status\":\"success\", \"data\": \"" +  newCustomer + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the Customer.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 

	// Delete Customer
	public String deleteCustomer(String uID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from Customer where uID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(uID));

			// execute the statement
			preparedStmt.execute();
			con.close(); 
	 
			String  newCustomer = readCustomer();    
			output = "{\"status\":\"success\", \"data\": \"" +  newCustomer + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the Customer.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}

}

