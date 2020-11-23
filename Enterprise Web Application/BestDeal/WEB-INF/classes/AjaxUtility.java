import java.io.*;

import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.text.*;

import java.sql.*;

import java.io.IOException;
import java.io.*;



public class AjaxUtility {
	StringBuffer sb = new StringBuffer();
	boolean namesAdded = false;
	static Connection conn = null;
    static String message;
	public static String getConnection()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");							
			message="Successfull";
			return message;
		}
		catch(SQLException e)
		{
			message="unsuccessful";
		    return message;
		}
		catch(Exception e)
		{
			message="unsuccessful";
		    return message;
		}
	}
	
	public  StringBuffer readdata(String searchId)
	{	
		HashMap<String,Product> data;
		data=getData();
		
 	    Iterator it = data.entrySet().iterator();	
        while (it.hasNext()) 
	    {
            Map.Entry pi = (Map.Entry)it.next();
			if(pi!=null)
			{
				Product p=(Product)pi.getValue();             
				//System.out.println("searchId: " + searchId);
                if (p.getproductName().toLowerCase().startsWith(searchId))
                {
	                sb.append("<product>");
	                sb.append("<id>" + p.getId() + "</id>");
	                sb.append("<productName>" + p.getproductName() + "</productName>");
	                sb.append("</product>");
                }
			}
       }
	   
	   return sb;
	}
	
	public static HashMap<String,Product> getData()
	{
		HashMap<String,Product> hm=new HashMap<String,Product>();
		try
		{
			getConnection();
			
		    String selectproduct="select * from  Productdetails";
		    PreparedStatement pst = conn.prepareStatement(selectproduct);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{	Product p = new Product();
				p.setProductType(rs.getString("ProductType"));
				p.setId(rs.getString("Id"));
				p.setproductName(rs.getString("productName"));
				p.setproductPrice(rs.getDouble("productPrice"));
				p.setproductImage(rs.getString("productImage"));
				p.setproductManufacturer(rs.getString("productManufacturer"));
				p.setproductCondition(rs.getString("productCondition"));
				
				hm.put(rs.getString("Id"), p);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hm;			
	}


	public static void storeData(HashMap<String,Product> productdata)
	{
		try
		{
		
			getConnection();
				
			String insertIntoProductQuery = "INSERT INTO product(productId,productName,image,retailer,productCondition,productPrice,productType,discount) "
			+ "VALUES (?,?,?,?,?,?,?,?);";	
			for(Map.Entry<String, Product> entry : productdata.entrySet())
			{	

				PreparedStatement pst = conn.prepareStatement(insertIntoProductQuery);
				//set the parameter for each column and execute the prepared statement
				pst.setString(1,entry.getValue().getId());
				pst.setString(2,entry.getValue().getproductName());
				pst.setString(3,entry.getValue().getproductImage());
				pst.setString(4,entry.getValue().getproductManufacturer());
				pst.setString(5,entry.getValue().getproductCondition());
				pst.setDouble(6,entry.getValue().getproductPrice());
				pst.setString(7,entry.getValue().getProductType());
				pst.setDouble(8,entry.getValue().getproductDiscount());
				pst.execute();
			}
		}
		catch(Exception e)
		{	
	
		}		
	}

}
