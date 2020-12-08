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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthhub","root","root");							
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
		HashMap<String,Provider> data;
		data = getData();
		
 	    Iterator it = data.entrySet().iterator();	
        while (it.hasNext()) 
	    {
            Map.Entry pi = (Map.Entry)it.next();
			if(pi!=null)
			{
				Provider p = (Provider)pi.getValue();             
				//System.out.println("searchId: " + searchId);
                if (p.getName().toLowerCase().startsWith(searchId))
                {
	                sb.append("<provider>");
	                sb.append("<id>" + p.getId() + "</id>");
	                sb.append("<providerName>" + p.getName() + "</providerName>");
	                sb.append("</provider>");
                }
			}
       }
	   
	   return sb;
	}
	
	public static HashMap<String,Provider> getData()
	{
		HashMap<String,Provider> hm = new HashMap<String,Provider>();
		try
		{
			getConnection();
		    String selectprovider = "select * from  providerslist";
		    PreparedStatement pst = conn.prepareStatement(selectprovider);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{	
				Provider p = new Provider();
				p.setType(rs.getString("type"));
				p.setId(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setFees(rs.getDouble("fees"));
				p.setImage(rs.getString("image"));
				p.setSpeciality(rs.getString("speciality"));
				p.setStAddress(rs.getString("staddress"));
				p.setCity(rs.getString("city"));
				p.setState(rs.getString("state"));
				p.setZipCode(rs.getString("zipcode"));
				p.setExperience(rs.getString("experience"));
				p.setLatitude(rs.getString("latitude"));
				p.setLongitude(rs.getString("longitude"));
				hm.put(rs.getString("id"), p);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hm;			
	}


	public static void storeData(HashMap<String,Provider> providerdata)
	{
		try
		{
		
			getConnection();
				
			String insertIntoProviderQuery = "INSERT INTO provider(id,name,staddress,city,state,zipcode,speciality,image,experience,fees,type,latitude,longitude) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";	
			for(Map.Entry<String, Provider> entry : providerdata.entrySet())
			{	
				PreparedStatement pst = conn.prepareStatement(insertIntoProviderQuery);
				//set the parameter for each column and execute the prepared statement
				pst.setString(1,entry.getValue().getId());
				pst.setString(2,entry.getValue().getName());
				pst.setString(3,entry.getValue().getStAddress());
				pst.setString(4,entry.getValue().getCity());
				pst.setString(5,entry.getValue().getState());
				pst.setString(6,entry.getValue().getZipCode());
				pst.setString(7,entry.getValue().getSpeciality());
				pst.setString(8,entry.getValue().getImage());
				pst.setString(9,entry.getValue().getExperience());
				pst.setDouble(10,entry.getValue().getFees());
				pst.setString(11,entry.getValue().getType());
				pst.setString(12,entry.getValue().getLatitude());
				pst.setString(13,entry.getValue().getLongitude());
				pst.execute();
			}
		}
		catch(Exception e)
		{	
	
		}		
	}

}
