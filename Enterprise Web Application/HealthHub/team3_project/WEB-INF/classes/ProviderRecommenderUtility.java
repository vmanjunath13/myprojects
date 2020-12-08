import java.io.*;
import java.sql.*;
import java.io.IOException;
import java.util.*;

public class ProviderRecommenderUtility{
	
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

	public HashMap<String,String> readOutputFile()
	{	
		String TOMCAT_HOME = System.getProperty("catalina.home");
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		HashMap<String,String> prdRecmMap = new HashMap<String,String>();
		try {

            br = new BufferedReader(new FileReader(new File(TOMCAT_HOME+"\\webapps\\team4_2\\output.csv")));
            while ((line = br.readLine()) != null) 
			{
                // use comma as separator
				//System.out.println("recommender:" + line + "\n");
                String[] prvd_recm = line.split(cvsSplitBy,2);
				//System.out.println("recommender1:" + prvd_recm[0] + "\n");
				//System.out.println("recommender2:" + prvd_recm[1] + "\n");
				prdRecmMap.put(prvd_recm[0],prvd_recm[1]);
            }
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		
		return prdRecmMap;
	}
	
	public static String[] getProvider(String cid)
	{   
		String[] wishlists = new String[5];
		try
		{	
			String msg = getConnection();
			String getWishlistQuery = "select * from wishlist where pname=?";
			PreparedStatement pst = conn.prepareStatement(getWishlistQuery);
			pst.setString(1, cid);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				wishlists[0] = rs.getString("username");
				wishlists[1] = rs.getString("pname");
				wishlists[2] = rs.getString("pid");
				wishlists[3] = rs.getString("ptype");
				wishlists[4] = rs.getString("paddress");
			}
			rs.close();
			pst.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return wishlists;
	}
}