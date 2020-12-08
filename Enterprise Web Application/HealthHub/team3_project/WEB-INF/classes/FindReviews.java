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
	static DBCollection ProviderReviews;

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
		
		String ProviderName = request.getParameter("ProviderName");
		int ProviderFees = Integer.parseInt(request.getParameter("ProviderFees")); 	
    	int ReviewRating = Integer.parseInt(request.getParameter("ReviewRating"));
		String compareRating = request.getParameter("compareRating");
		String comparePrice = request.getParameter("comparePrice");
		String CustomerCity = request.getParameter("CustomerCity");
		String CustomerZIP = request.getParameter("CustomerZIP");
		
		String[] filters = request.getParameterValues("queryCheckBox");
		String[] extraSettings = request.getParameterValues("extraSettings");
		String dataGroupBy=request.getParameter("dataGroupBy");
		
		boolean noFilter = false;
		boolean filterByProvider = false;
		boolean filterByPrice = false;
		boolean filterByRating = false;
		ProviderReviews = MongoDBDataStoreUtilities.getConnection();
		BasicDBObject query = new BasicDBObject();
		boolean groupBy = false;
		boolean filterByCity = false;
		boolean groupByCity = false;
		boolean groupByProvider = false;
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
						countOnly = true;				
						break;
					case "GROUP_BY":	
						//Can add more grouping conditions here
						if(groupByDropdown.equals("GROUP_BY_CITY"))
						{
							groupByCity = true;
						}
						else if(groupByDropdown.equals("GROUP_BY_PROVIDER"))
						{
							groupByProvider = true;
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
					case "ProviderName":							
						filterByProvider = true;
						if(!ProviderName.equals("ALL_PRODUCTS")){
							query.put("ProviderName", ProviderName);
						}						
						break;
											
					case "ProviderFees":
						filterByPrice = true;
						if (comparePrice.equals("EQUALS_TO")) 
						{
							query.put("ProviderFees", ProviderFees);
						}
						else if(comparePrice.equals("GREATER_THAN"))
						{
							query.put("ProviderFees", new BasicDBObject("$gt", ProviderFees));
						}
						else if(comparePrice.equals("LESS_THAN"))
						{
							query.put("ProviderFees", new BasicDBObject("$lt", ProviderFees));
						}
						
						break;
					
					case "CustomerZIP":
						filterByZip = true;	
						query.put("CustomerZIP", CustomerZIP);
						break;					
					
					case "CustomerCity": 
						filterByCity = true;
						if(!CustomerCity.equals("All") && !groupByCity)
						{
							query.put("CustomerCity", CustomerCity);
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
				groupfield="CustomerCity";
				groupFields = new BasicDBObject("_id", 0);
				groupFields.put("_id", "$CustomerCity");
				groupFields.put("count", new BasicDBObject("$sum", 1));
				groupFields.put("ProviderName", new BasicDBObject("$push", "$ProviderName"));
				groupFields.put("review", new BasicDBObject("$push", "$ReviewText"));
				groupFields.put("rating", new BasicDBObject("$push", "$ReviewRating"));
				groupFields.put("ProviderFees", new BasicDBObject("$push", "$ProviderFees"));
				groupFields.put("CustomerCity", new BasicDBObject("$push", "$CustomerCity"));
				groupFields.put("CustomerZIP", new BasicDBObject("$push", "$CustomerZIP"));

				group = new BasicDBObject("$group", groupFields);

				projectFields = new BasicDBObject("_id", 0);
				projectFields.put("value", "$_id");
				projectFields.put("ReviewValue", "$count");
				projectFields.put("Provider", "$ProviderName");
				projectFields.put("User", "$Username");
				projectFields.put("Reviews", "$review");
				projectFields.put("Rating", "$rating");
			    projectFields.put("ProviderFees", "$ProviderFees");
			    projectFields.put("CustomerCity", "$CustomerCity");
			    projectFields.put("CustomerZIP", "$CustomerZIP");

				project = new BasicDBObject("$project", projectFields);
				
				aggregate = ProviderReviews.aggregate(group, project);
												
					//Construct the page content
					
			}
			else if(groupByProvider)
			{	
					
				groupfield="ProviderName";
				groupFields = new BasicDBObject("_id", 0);
				groupFields.put("_id", "$ProviderName");
				groupFields.put("count", new BasicDBObject("$sum", 1));
				groupFields.put("review", new BasicDBObject("$push", "$ReviewText"));
				groupFields.put("rating", new BasicDBObject("$push", "$ReviewRating"));
				groupFields.put("ProviderName", new BasicDBObject("$push", "$ProviderName"));
				groupFields.put("ProviderFees", new BasicDBObject("$push", "$ProviderFees"));
				groupFields.put("CustomerCity", new BasicDBObject("$push", "$CustomerCity"));
				groupFields.put("CustomerZIP", new BasicDBObject("$push", "$CustomerZIP"));

				group = new BasicDBObject("$group", groupFields);

				projectFields = new BasicDBObject("_id", 0);
				projectFields.put("value", "$_id");
				projectFields.put("ReviewValue", "$count");
				projectFields.put("Provider", "$ProviderName");
				projectFields.put("Reviews", "$review");
				projectFields.put("Rating", "$rating");
				projectFields.put("ProviderFees", "$ProviderFees");
				projectFields.put("CustomerCity", "$CustomerCity");
			    projectFields.put("CustomerZIP", "$CustomerZIP");

				project = new BasicDBObject("$project", projectFields);
				
				aggregate = ProviderReviews.aggregate(group, project);				
					
			}
			else if(groupByZip)
			{	
					
				groupfield="CustomerZIP";
				groupFields = new BasicDBObject("_id", 0);
				groupFields.put("_id", "$CustomerZIP");
				groupFields.put("count", new BasicDBObject("$sum", 1));
				groupFields.put("review", new BasicDBObject("$push", "$ReviewText"));
				groupFields.put("rating", new BasicDBObject("$push", "$ReviewRating"));
				groupFields.put("ProviderName", new BasicDBObject("$push", "$ProviderName"));
				groupFields.put("ProviderFees", new BasicDBObject("$push", "$ProviderFees"));
				groupFields.put("CustomerCity", new BasicDBObject("$push", "$CustomerCity"));
				groupFields.put("CustomerZIP", new BasicDBObject("$push", "$CustomerZIP"));

				group = new BasicDBObject("$group", groupFields);

				projectFields = new BasicDBObject("_id", 0);
				projectFields.put("value", "$_id");
				projectFields.put("ReviewValue", "$count");
				projectFields.put("Provider", "$ProviderName");
				projectFields.put("Reviews", "$review");
				projectFields.put("Rating", "$rating");
				projectFields.put("ProviderFees", "$ProviderFees");
				projectFields.put("CustomerCity", "$CustomerCity");
			    projectFields.put("CustomerZIP", "$CustomerZIP");

				project = new BasicDBObject("$project", projectFields);
				
				aggregate = ProviderReviews.aggregate(group, project);				
					
			}			 
		}	
		else
		{
			dbCursor= ProviderReviews.find(query);
		}
				
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta' style='text-align:center; padding-top:10px;'>");
		pw.print("<a style='font-size: 24px;'>Data Analytics on Review</a>");
		pw.print("</h2><div class='entry'>");
		if(groupBy == true)
		{		
			pw.print("<table class='gridtable'>");
			constructGroupByContent(aggregate, pw, dataGroupBy);	
	    	pw.print("</table>");
		}		
		else if(groupBy != true)
		{
			constructTableContent(dbCursor, pw);
		}
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	}
	
	public void constructGroupByContent(AggregationOutput aggregate, PrintWriter pw, String dataGroupBy)
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
				BasicDBList providerList = (BasicDBList) bobj.get("Provider");
				BasicDBList providerReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				BasicDBList CustomerCity = (BasicDBList) bobj.get("CustomerCity");
				BasicDBList zipcode = (BasicDBList) bobj.get("CustomerZIP");
				BasicDBList ProviderFees = (BasicDBList) bobj.get("ProviderFees");

				pw.print("<tr><td>"+ bobj.getString("value")+"</td></tr>");					
			
				while (detailcount < providerList.size()) 
				{
					tableData = "<tr rowspan = \"3\"><td style='font-size:13px;'> Provider: "+providerList.get(detailcount)+"</br>"
							+   "Rating: "+rating.get(detailcount)+"</br>"
							+   "Price: "+ProviderFees.get(detailcount)+"</br>"
							+   "Customer City: "+CustomerCity.get(detailcount)+"</br>"
							+   "Customer ZIP: "+zipcode.get(detailcount)+"</br>"
							+	"Review: "+providerReview.get(detailcount)+"</td></tr>";
												
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
			
			tableData = "<tr><td></td><td></td></tr><tr><td style='width:25%; font-size:13px; font-weight:bold;'>Name </td><td 		style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("ProviderName") + "</td></tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>Rating</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("ReviewRating") + "</td></tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>Fees</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("ProviderFees") + "</td></tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>User City</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("CustomerCity") + "</td></tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>User ZIP</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("CustomerZIP")+"</td><tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>Date</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("ReviewDate") + "</td></tr>"
			+ "<tr><td style='width:25%; font-size:13px; font-weight:bold;'>Review Text</td><td style='font-size:13px;'>&nbsp&nbsp" + bobj.getString("ReviewText")+"</td><tr>";
			
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
