import java.sql.*;
import java.util.*;
                	
public class MySqlDataStoreUtilities
{
	static Connection conn = null;

	public static void getConnection()
	{

		try
		{
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthhub","root","root");
		}
		catch(Exception e)
		{
		
		}
	}

	public static void insertUser(String username,String password,String repassword,String firstname,String lastname,String email,String usertype)
	{
		try
		{
			getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,firstname,lastname,email,usertype) "
			+ "VALUES (?,?,?,?,?,?,?);";	
					
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,username);
			pst.setString(2,password);
			pst.setString(3,repassword);
			pst.setString(4,firstname);
			pst.setString(5,lastname);
			pst.setString(6,email);
			pst.setString(7,usertype);
			pst.execute();
		}
		catch(Exception e)
		{
		
		}	
	}

	public static HashMap<String,User> selectUser()
	{	
		HashMap<String,User> hm=new HashMap<String,User>();
		try 
		{
			getConnection();
			Statement stmt=conn.createStatement();
			String selectCustomerQuery="select * from  Registration";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while(rs.next())
			{	User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("email"),rs.getString("usertype"));
					hm.put(rs.getString("username"), user);
			}
		}
		catch(Exception e)
		{
		}
		return hm;			
	}

	public static String insertProvider(String id, String name, String staddress, String city, String state, String zipcode, String speciality, String image, String experience, Double fees, String type, String latitude, String longitude)
	{
		String msg = "Provider is added successfully";
		try
		{	
			getConnection();
			String insertIntoProvidersListQuery = "INSERT INTO providerslist(id,name,staddress,city,state,zipcode,speciality,image,experience,fees, type,latitude,longitude) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
			
			//System.out.println("inside insert doctor");
			
			PreparedStatement pst = conn.prepareStatement(insertIntoProvidersListQuery);
			pst.setString(1,id);
			pst.setString(2,name);
			pst.setString(3,staddress);
			pst.setString(4,city);
			pst.setString(5,state);
			pst.setString(6,zipcode);
			pst.setString(7,speciality);
			pst.setString(8,image);
			pst.setString(9,experience);
			pst.setDouble(10,fees);
			pst.setString(11,type);
			pst.setString(12,latitude);
			pst.setString(13,longitude);
			pst.execute();
		}
		catch(Exception e)
		{
			msg = "Provider cannot be added";
			e.printStackTrace();	
		}	
		return msg;
	}

	public static String updateProvider(String id, String name, String staddress, String city, String state, String zipcode, String speciality, String image, String experience, Double fees, String type, String latitude, String longitude)
	{
		String msg = "Provider is updated successfully";
		try{
			
			getConnection();
			String updateProviderQurey = "UPDATE providerslist SET id=?, name=?, staddress=?, city=?, state=?, zipcode=?, speciality=?, image=?, experience=?, fees=?, type=?, latitude=?, longitude=? where id =?;" ;   
			PreparedStatement pst = conn.prepareStatement(updateProviderQurey);
			pst.setString(1,id);
			pst.setString(2,name);
			pst.setString(3,staddress);
			pst.setString(4,city);
			pst.setString(5,state);
			pst.setString(6,zipcode);
			pst.setString(7,speciality);
			pst.setString(8,image);
			pst.setString(9,experience);
			pst.setDouble(10,fees);
			pst.setString(11,type);
			pst.setString(12,latitude);
			pst.setString(13,longitude);
			pst.setString(14,id);
			pst.executeUpdate();
		}
		catch(Exception e)
		{
			msg = "Provider cannot be updated";
			e.printStackTrace();	
		}
		return msg;	
	}


	public static String deleteProvider(String Id)
	{   
		String msg = "Provider is deleted successfully";
		try
		{
			System.out.println("Inside mysql deleteProvider " + Id);
			getConnection();
			String deleteproviderQuery = "Delete from providerslist where id=?";
			PreparedStatement pst = conn.prepareStatement(deleteproviderQuery);
			pst.setString(1,Id);
			pst.executeUpdate();
		}
		catch(Exception e)
		{
				msg = "Provider cannot be deleted";
				e.printStackTrace();
		}
		return msg;
	}

	public static HashMap<String,Doctor> getDoctors()
	{	
		HashMap<String,Doctor> hm=new HashMap<String,Doctor>();
		try 
		{
			getConnection();
			
			Statement stmt=conn.createStatement();
			String selectDoctor="select * from providerslist";
			ResultSet rs = stmt.executeQuery(selectDoctor);
			while(rs.next())
			{	
				Doctor doctor = new Doctor(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
				hm.put(rs.getString("Id"), doctor);
				doctor.setId(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{
		}
		return hm;			
	}

	public static HashMap<String,HealthInsurance> getHealthInsurances()
	{	
		HashMap<String,HealthInsurance> hm=new HashMap<String,HealthInsurance>();
		try 
		{
			getConnection();
			
			Statement stmt=conn.createStatement();
			String selectHealthInsurance="select * from providerslist";
			ResultSet rs = stmt.executeQuery(selectHealthInsurance);
			while(rs.next())
			{	
				HealthInsurance healthinsurance = new HealthInsurance(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
				hm.put(rs.getString("Id"), healthinsurance);
				healthinsurance.setId(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{
		}
		return hm;			
	}

	public static HashMap<String,Hospital> getHospitals()
	{	
		HashMap<String,Hospital> hm=new HashMap<String,Hospital>();
		try 
		{
			getConnection();
			
			Statement stmt=conn.createStatement();
			String selectDoctor="select * from providerslist";
			ResultSet rs = stmt.executeQuery(selectDoctor);
			while(rs.next())
			{	
				Hospital hospital = new Hospital(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
				hm.put(rs.getString("Id"), hospital);
				hospital.setId(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{
		}
		return hm;			
	}

	public static HashMap<String,HealthClub> getHealthClubs()
	{	
		HashMap<String,HealthClub> hm=new HashMap<String,HealthClub>();
		try 
		{
			getConnection();
			
			Statement stmt=conn.createStatement();
			String selectHealthClub="select * from providerslist";
			ResultSet rs = stmt.executeQuery(selectHealthClub);
			while(rs.next())
			{	
				HealthClub healthclub = new HealthClub(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
				hm.put(rs.getString("Id"), healthclub);
				healthclub.setId(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{
		}
		return hm;			
	}

	public static HashMap<String,Pharmacy> getPharmacys()
	{	
		HashMap<String,Pharmacy> hm=new HashMap<String,Pharmacy>();
		try 
		{
			getConnection();
			
			Statement stmt=conn.createStatement();
			String selectPharmacy="select * from providerslist";
			ResultSet rs = stmt.executeQuery(selectPharmacy);
			while(rs.next())
			{	
				Pharmacy pharmacy = new Pharmacy(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
				hm.put(rs.getString("Id"), pharmacy);
				pharmacy.setId(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{
		}
		return hm;			
	}

	public static String[] getLatLong(String zipCode)
	{   
		//System.out.println("Inside Mysql zipCode: " + zipCode);
		String latlon[] = new String[2];
		try
		{	
			getConnection();
			String getLatLongQuery = "select * from latlonlist where zipCode=?";
			PreparedStatement pst = conn.prepareStatement(getLatLongQuery);
			pst.setString(1, zipCode);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
			//	System.out.println("Inside while loop");
				latlon[0] = rs.getString("latitude");
				latlon[1] = rs.getString("longitude");
			}
		}
		catch(Exception e)
		{
		}
		//System.out.println("after while loop: " + latlon);
		return latlon;
	}

	public static HashMap<String,Provider> getProviders(String pType, String zipCode)
	{	
		//System.out.println("inside mysql getProviders");
		HashMap<String,Provider> hm=new HashMap<String,Provider>();
		try 
		{
			//System.out.println("pType in mysql:" + pType);
			getConnection();
			String selectProvider = "select * from providerslist where type=? and zipCode=?";
			PreparedStatement pst = conn.prepareStatement(selectProvider);
			pst.setString(1, pType);
			pst.setString(2, zipCode);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{	
				Provider provider = new Provider(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
				hm.put(rs.getString("Id"), provider);
				provider.setId(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{
		}
		return hm;			
	}

	public static HashMap<String,Provider> getDetails(String id)
	{	
		HashMap<String,Provider> hm=new HashMap<String,Provider>();
		try 
		{
			getConnection();
			String selectProvider = "select * from providerslist where id=?";
			PreparedStatement pst = conn.prepareStatement(selectProvider);
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{	
				Provider provider = new Provider(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
				hm.put(rs.getString("Id"), provider);
				provider.setId(rs.getString("Id"));
			}
		}
		catch(Exception e)
		{
		}
		return hm;			
	}

	public static HashMap<String,String[]> selectBooking(String cid)
	{   
		HashMap<String,String[]> hm = new HashMap<String,String[]>();
		int count=0;
		try
		{	
			getConnection();
			String getBookingQuery = "select * from bookinglist where cid=?";
			PreparedStatement pst = conn.prepareStatement(getBookingQuery);
			pst.setString(1, cid);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String bookings[] = new String[6];
				bookings[0] = rs.getString("pname");
				bookings[1] = rs.getString("date");
				bookings[2] = rs.getString("time");
				bookings[3] = rs.getString("crn");
				bookings[4] = rs.getString("fullname");
				bookings[5] = rs.getString("ptype");
				hm.put(rs.getString("crn"), bookings);
				count++;
			}
		}
		catch(Exception e)
		{
		}
		//System.out.println("mysql count: " + count);
		return hm;
	}

	public static String getBooking(String FullName, String Date, String Time, String PId)
	{	
		String res = "No";
		try 
		{
			getConnection();
			String selectBooking = "select * from bookinglist where fullname=? and date=? and time=? and pid=?";
			PreparedStatement pst = conn.prepareStatement(selectBooking);
			pst.setString(1, FullName);
			pst.setString(2, Date);
			pst.setString(3, Time);
			pst.setString(4, PId);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{	
				res = "Yes";
			}
		}
		catch(Exception e)
		{
		}
		return res;			
	}

	public static String insertBooking(String FullName, String Date, String Time, String PName, String PId, String Crn, String userid, String PType)
	{	
		String msg = "Successfully added appointment";
		try
		{	
			getConnection();
			String insertIntoBookingListQuery = "INSERT INTO bookinglist(fullname,date,time,pname,pid,crn,cid,ptype) "
			+ "VALUES (?,?,?,?,?,?,?,?);";
			
			PreparedStatement pst = conn.prepareStatement(insertIntoBookingListQuery);
			pst.setString(1,FullName);
			pst.setString(2,Date);
			pst.setString(3,Time);
			pst.setString(4,PName);
			pst.setString(5,PId);
			pst.setString(6,Crn);
			pst.setString(7,userid);
			pst.setString(8,PType);
			pst.execute();
		}
		catch(Exception e)
		{
			msg = "Error while adding appointment";
			e.printStackTrace();
		}				
		return msg;
	}

	public static String deleteBooking(String CId, String bookingName)
	{
		String msg = "Successfully deleted appointment";
		try
		{
			getConnection();
			String deleteBookingQuery ="Delete from bookinglist where crn=? and pname=?";
			PreparedStatement pst = conn.prepareStatement(deleteBookingQuery);
			pst.setString(1,CId);
			pst.setString(2,bookingName);
			pst.executeUpdate();
		}
		catch(Exception e)
		{
			msg = "Error while deleting appointment";
			e.printStackTrace();	
		}
		return msg;
	}

	public static String getWishlist(String username, String PName, String PId, String PType)
	{	
		String res = "No";
		try 
		{
			getConnection();
			String selectWishlist = "select * from wishlist where username=? and pname=? and pid=? and ptype=?";
			PreparedStatement pst = conn.prepareStatement(selectWishlist);
			pst.setString(1, username);
			pst.setString(2, PName);
			pst.setString(3, PId);
			pst.setString(4, PType);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{	
				res = "Yes";
			}
		}
		catch(Exception e)
		{
		}
		return res;			
	}

	public static String insertWishlist(String username, String PName, String PId, String PType, String PAddress)
	{	
		String msg = "Successfully added to wishlist";
		try
		{	
			getConnection();
			String insertIntoWishlistQuery = "INSERT INTO wishlist(username,pname,pid,ptype,paddress) "
			+ "VALUES (?,?,?,?,?);";
			
			PreparedStatement pst = conn.prepareStatement(insertIntoWishlistQuery);
			pst.setString(1,username);
			pst.setString(2,PName);
			pst.setString(3,PId);
			pst.setString(4,PType);
			pst.setString(5,PAddress);
			pst.execute();
		}
		catch(Exception e)
		{
			msg = "Error while adding to wishlist";
			e.printStackTrace();
		}				
		return msg;
	}

	public static HashMap<String,String[]> selectWishlist(String cid)
	{   
		HashMap<String,String[]> hm = new HashMap<String,String[]>();
		int count=0;
		try
		{	
			getConnection();
			String getWishlistQuery = "select * from wishlist where username=?";
			PreparedStatement pst = conn.prepareStatement(getWishlistQuery);
			pst.setString(1, cid);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String wishlists[] = new String[5];
				wishlists[0] = rs.getString("username");
				wishlists[1] = rs.getString("pname");
				wishlists[2] = rs.getString("pid");
				wishlists[3] = rs.getString("ptype");
				wishlists[4] = rs.getString("paddress");
				hm.put(rs.getString("pid"), wishlists);
				count++;
			}
		}
		catch(Exception e)
		{
		}
		return hm;
	}

	public static String deleteWishlist(String CId, String wishlistName)
	{
		String msg = "Successfully deleted wishlist";
		try
		{
			getConnection();
			String deleteBookingQuery ="Delete from wishlist where username=? and pname=?";
			PreparedStatement pst = conn.prepareStatement(deleteBookingQuery);
			pst.setString(1,CId);
			pst.setString(2,wishlistName);
			pst.executeUpdate();
		}
		catch(Exception e)
		{
			msg = "Error while deleting wishlist";
			e.printStackTrace();	
		}
		return msg;
	}
	
	
	
	public static HashMap<String, Provider> getInventoryProviders()
	{
		HashMap<String, Provider> providers = new HashMap<String, Provider>();
		try
		{
			getConnection();
			String selectProviderQuery ="select * from providerslist;";
			PreparedStatement pst1 = conn.prepareStatement(selectProviderQuery);
			ResultSet rs = pst1.executeQuery();
			while(rs.next())
			{
				Provider provider = new Provider();
				provider.setType(rs.getString("type"));
				provider.setId(rs.getString("id"));
				provider.setName(rs.getString("name"));
				provider.setFees(rs.getDouble("fees"));
				provider.setImage(rs.getString("image"));
				provider.setSpeciality(rs.getString("speciality"));
				provider.setStAddress(rs.getString("staddress"));
				provider.setCity(rs.getString("city"));
				provider.setState(rs.getString("state"));
				provider.setZipCode(rs.getString("zipcode"));
				provider.setExperience(rs.getString("experience"));
				provider.setLatitude(rs.getString("latitude"));
				provider.setLongitude(rs.getString("longitude"));
				providers.put(provider.getId(),provider);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return providers;
	}
}
