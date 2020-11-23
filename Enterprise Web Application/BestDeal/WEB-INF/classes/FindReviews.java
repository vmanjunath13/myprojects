import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;
import javax.servlet.http.HttpSession;
@WebServlet("/FindReviews")

public class FindReviews extends HttpServlet {
	static DBCollection ProductReviews;

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
				
		
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View your Orders");
			response.sendRedirect("Login");
			return;
		}
		
		String ProductName = request.getParameter("ProductName");
		int ProductPrice = Integer.parseInt(request.getParameter("ProductPrice")); 	
    	int ReviewRating = Integer.parseInt(request.getParameter("ReviewRating"));
		String compareRating = request.getParameter("compareRating");
		String comparePrice = request.getParameter("comparePrice");
		String RetailerCity = request.getParameter("RetailerCity");
		String RetailerZIP = request.getParameter("RetailerZIP");
		
		String[] filters = request.getParameterValues("queryCheckBox");
		String[] extraSettings = request.getParameterValues("extraSettings");
		String dataGroupBy=request.getParameter("dataGroupBy");
		
		boolean noFilter = false;
		boolean filterByProduct = false;
		boolean filterByPrice = false;
		boolean filterByRating = false;
		ProductReviews=MongoDBDataStoreUtilities.getConnection();
		BasicDBObject query = new BasicDBObject();
		boolean groupBy = false;
		boolean filterByCity = false;
		boolean groupByCity = false;
		boolean groupByProduct = false;
		boolean filterByZip = false;
		boolean groupByZip = false;
		
		boolean countOnly = false;
		String groupByDropdown = request.getParameter("groupByDropdown");
		DBCursor dbCursor=null;
		DBObject match = null;
		DBObject groupFields = null;
		DBObject group = null;
		DBObject projectFields = null;
		DBObject project = null;
		AggregationOutput aggregate = null;
		String groupfield=null;			
			
		//Check for extra settings(Grouping Settings)
		if(extraSettings != null)
		{				
			//User has selected extra settings
			groupBy = true;
			for(int x = 0; x <extraSettings.length; x++)
			{
				switch (extraSettings[x])
				{						
					case "COUNT_ONLY":
						//Not implemented functionality to return count only
						countOnly = true;				
						break;
					case "GROUP_BY":	
						//Can add more grouping conditions here
						if(groupByDropdown.equals("GROUP_BY_CITY"))
						{
							groupByCity = true;
						}
						else if(groupByDropdown.equals("GROUP_BY_PRODUCT"))
						{
							groupByProduct = true;
						}
						else if(groupByDropdown.equals("GROUP_BY_ZIP"))
						{
							groupByZip = true;
						} 							
						break;
				}		
			}				
		}	
			
			
		if(filters != null && groupBy != true)
		{
			for (int i = 0; i < filters.length; i++) 
			{
				//Check what all filters are ON
				//Build the query accordingly
				switch (filters[i])
				{										
					case "ProductName":							
						filterByProduct = true;
						if(!ProductName.equals("ALL_PRODUCTS")){
							query.put("ProductName", ProductName);
						}						
						break;
											
					case "ProductPrice":
						filterByPrice = true;
						if (comparePrice.equals("EQUALS_TO")) 
						{
							query.put("ProductPrice", ProductPrice);
						}
						else if(comparePrice.equals("GREATER_THAN"))
						{
							query.put("ProductPrice", new BasicDBObject("$gt", ProductPrice));
						}
						else if(comparePrice.equals("LESS_THAN"))
						{
							query.put("ProductPrice", new BasicDBObject("$lt", ProductPrice));
						}
						
						break;
					
					case "RetailerZIP":
						filterByZip = true;	
						System.out.println("inside if");
						query.put("RetailerZIP", RetailerZIP);
						break;					
					
					case "RetailerCity": 
						filterByCity = true;
						if(!RetailerCity.equals("All") && !groupByCity)
						{
							query.put("RetailerCity", RetailerCity);
						}							
						break;
						
					case "ReviewRating":	
						filterByRating = true;
						if (compareRating.equals("EQUALS_TO")) 
						{
							query.put("ReviewRating", ReviewRating);
						}
						else
						{
							query.put("ReviewRating", new BasicDBObject("$gt", ReviewRating));
						}
						break;
												
					default:
						//Show all the reviews if nothing is selected
						noFilter = true;
						break;						
				}				
			}
		}
		else
		{
			//Show all the reviews if nothing is selected
			noFilter = true;
		}
			
				
		//Run the query 
		if(groupBy == true)
		{		
			//Run the query using aggregate function
		
			if(groupByCity)
			{
				groupfield="RetailerCity";
				groupFields = new BasicDBObject("_id", 0);
				groupFields.put("_id", "$RetailerCity");
				groupFields.put("count", new BasicDBObject("$sum", 1));
				groupFields.put("ProductName", new BasicDBObject("$push", "$ProductName"));
				groupFields.put("review", new BasicDBObject("$push", "$ReviewText"));
				groupFields.put("rating", new BasicDBObject("$push", "$ReviewRating"));
				groupFields.put("ProductPrice", new BasicDBObject("$push", "$ProductPrice"));
				groupFields.put("RetailerCity", new BasicDBObject("$push", "$RetailerCity"));
				groupFields.put("RetailerZIP", new BasicDBObject("$push", "$RetailerZIP"));

				group = new BasicDBObject("$group", groupFields);

				projectFields = new BasicDBObject("_id", 0);
				projectFields.put("value", "$_id");
				projectFields.put("ReviewValue", "$count");
				projectFields.put("Product", "$ProductName");
				projectFields.put("User", "$Username");
				projectFields.put("Reviews", "$review");
				projectFields.put("Rating", "$rating");
			    projectFields.put("ProductPrice", "$ProductPrice");
			    projectFields.put("RetailerCity", "$RetailerCity");
			    projectFields.put("RetailerZIP", "$RetailerZIP");

				project = new BasicDBObject("$project", projectFields);
				
				aggregate = ProductReviews.aggregate(group, project);
												
					//Construct the page content
					
			}
			else if(groupByProduct)
			{	
					
				groupfield="ProductName";
				groupFields = new BasicDBObject("_id", 0);
				groupFields.put("_id", "$ProductName");
				groupFields.put("count", new BasicDBObject("$sum", 1));
				groupFields.put("review", new BasicDBObject("$push", "$ReviewText"));
				groupFields.put("rating", new BasicDBObject("$push", "$ReviewRating"));
				groupFields.put("ProductName", new BasicDBObject("$push", "$ProductName"));
				groupFields.put("ProductPrice", new BasicDBObject("$push", "$ProductPrice"));
				groupFields.put("RetailerCity", new BasicDBObject("$push", "$RetailerCity"));
				groupFields.put("RetailerZIP", new BasicDBObject("$push", "$RetailerZIP"));

				group = new BasicDBObject("$group", groupFields);

				projectFields = new BasicDBObject("_id", 0);
				projectFields.put("value", "$_id");
				projectFields.put("ReviewValue", "$count");
				projectFields.put("Product", "$ProductName");
				projectFields.put("Reviews", "$review");
				projectFields.put("Rating", "$rating");
				projectFields.put("ProductPrice", "$ProductPrice");
				projectFields.put("RetailerCity", "$RetailerCity");
			    projectFields.put("RetailerZIP", "$RetailerZIP");

				project = new BasicDBObject("$project", projectFields);
				
				aggregate = ProductReviews.aggregate(group, project);				
					
			}
			else if(groupByZip)
			{	
					
				groupfield="RetailerZIP";
				groupFields = new BasicDBObject("_id", 0);
				groupFields.put("_id", "$RetailerZIP");
				groupFields.put("count", new BasicDBObject("$sum", 1));
				groupFields.put("review", new BasicDBObject("$push", "$ReviewText"));
				groupFields.put("rating", new BasicDBObject("$push", "$ReviewRating"));
				groupFields.put("ProductName", new BasicDBObject("$push", "$ProductName"));
				groupFields.put("ProductPrice", new BasicDBObject("$push", "$ProductPrice"));
				groupFields.put("RetailerCity", new BasicDBObject("$push", "$RetailerCity"));
				groupFields.put("RetailerZIP", new BasicDBObject("$push", "$RetailerZIP"));

				group = new BasicDBObject("$group", groupFields);

				projectFields = new BasicDBObject("_id", 0);
				projectFields.put("value", "$_id");
				projectFields.put("ReviewValue", "$count");
				projectFields.put("Product", "$ProductName");
				projectFields.put("Reviews", "$review");
				projectFields.put("Rating", "$rating");
				projectFields.put("ProductPrice", "$ProductPrice");
				projectFields.put("RetailerCity", "$RetailerCity");
			    projectFields.put("RetailerZIP", "$RetailerZIP");

				project = new BasicDBObject("$project", projectFields);
				
				aggregate = ProductReviews.aggregate(group, project);				
					
			}			 
		}	
		else
		{
		dbCursor= ProductReviews.find(query);
		}
				
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta' style='text-align:center; padding-top:10px;'>");
		pw.print("<a style='font-size: 24px;'>Data Analytics on Review</a>");
		pw.print("</h2><div class='entry'>");
		if(groupBy == true)
		{		
			pw.print("<table class='gridtable'>");
			constructGroupByContent(aggregate, pw,dataGroupBy);	
	    	pw.print("</table>");
		}		
		else if(groupBy != true)
		{
			constructTableContent(dbCursor, pw);
		}
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	}
	
	public void constructGroupByContent(AggregationOutput aggregate, PrintWriter pw,String dataGroupBy)
	{
		String tableData = "";
		int count=0;
		if(dataGroupBy.equals("Count"))
		{
			pw.print("<tr><td><strong>Name</strong></td><td><strong>Count</strong></td></tr>");					
			for (DBObject result : aggregate.results()) 
			{
				BasicDBObject bobj = (BasicDBObject) result;
				tableData = "<tr><td style='font-size:13px;'> "+bobj.getString("value")+"</td>&nbsp"+"<td style='font-size:13px; padding-left:30px;'>"+bobj.getString("ReviewValue")+"</td></tr>";
				pw.print(tableData);
				count++;
			}
		}
		
		if(dataGroupBy.equals("Detail"))
		{
			int detailcount=0;
			for (DBObject result : aggregate.results()) 
			{
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				BasicDBList RetailerCity = (BasicDBList) bobj.get("RetailerCity");
				BasicDBList zipcode = (BasicDBList) bobj.get("RetailerZIP");
				BasicDBList ProductPrice = (BasicDBList) bobj.get("ProductPrice");

				pw.print("<tr><td>"+ bobj.getString("value")+"</td></tr>");					
			
				while (detailcount < productList.size()) 
				{
					System.out.println("inside 1 inside 2"+productList.get(detailcount)+rating.get(detailcount));
					tableData = "<tr rowspan = \"3\"><td style='font-size:13px;'> Product: "+productList.get(detailcount)+"</br>"
							+   "Rating: "+rating.get(detailcount)+"</br>"
							+   "Price: "+ProductPrice.get(detailcount)+"</br>"
							+   "Retailer City: "+RetailerCity.get(detailcount)+"</br>"
							+   "Retailer ZIP: "+zipcode.get(detailcount)+"</br>"
							+	"Review: "+productReview.get(detailcount)+"</td></tr>";
												
					pw.print(tableData);
					detailcount++;	
				}	
				detailcount=0;	
				count++;	
			}	
		}
		if(count==0)
		{
			tableData = "<h2>No Data Found</h2>";
			pw.print(tableData);
		}

	}
	
	public void constructTableContent(DBCursor dbCursor,PrintWriter pw)
	{
		String tableData = "";
		pw.print("<table class='gridtable'>");
		   
		while (dbCursor.hasNext()) {		
			BasicDBObject bobj = (BasicDBObject) dbCursor.next();
			
			tableData = "<tr><td></td><td></td></tr><tr><td style='width:25%; font-size:13px; font-weight:bold;'>Name </td><td 		style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("ProductName") + "</td></tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>Rating</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("ReviewRating") + "</td></tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>Price</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("ProductPrice") + "</td></tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>Retailer City</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("RetailerCity") + "</td></tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>Date</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("ReviewDate") + "</td></tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>Review Text</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("ReviewText")+"</td><tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>Retailer ZIP</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("RetailerZIP")+"</td><tr>";
			
			pw.print(tableData);
		}
		pw.print("</table>");

		//No data found
		if(dbCursor.count() == 0){
			tableData = "<h2>No Data Found</h2>";
			pw.print(tableData);
		}
		
	}

}
