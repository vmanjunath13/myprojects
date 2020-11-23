import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SmartWatchList")

public class SmartWatchList extends HttpServlet {

	/* Smart Watches Page Displays all the Smart Watches and their Information in SmartWatch Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,SmartWatch> allsmartwatches = new HashMap<String,SmartWatch> ();

		try{
			allsmartwatches = MySqlDataStoreUtilities.getSmartWatches();
		}
		catch(Exception e)
		{

		}

		/* Checks the Smart Watches type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, SmartWatch> hm = new HashMap<String, SmartWatch>();
		
		if(ProductName==null)
		{
			hm.putAll(allsmartwatches);
			name = "";
		}
		else
		{
		  if(ProductName.equals("apple"))
		  {
			for(Map.Entry<String,SmartWatch> entry : allsmartwatches.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Apple"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Apple";
		  }
		  else if(ProductName.equals("samsung"))
		  {
			for(Map.Entry<String,SmartWatch> entry : allsmartwatches.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Samsung"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Samsung";
		  }
		  else if(ProductName.equals("sony"))
		  {
			for(Map.Entry<String,SmartWatch> entry : allsmartwatches.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Sony"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Sony";
		  }
		  else if(ProductName.equals("fossil"))
		  {
			for(Map.Entry<String,SmartWatch> entry : allsmartwatches.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Fossil"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Fossil";
		  }
		  else if(ProductName.equals("honor"))
		  {
			for(Map.Entry<String,SmartWatch> entry : allsmartwatches.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Honor"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Honor";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Smart Watches and Smart Watches information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+name+" Smart Watch Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, SmartWatch> entry : hm.entrySet()){
			SmartWatch smartwatch = entry.getValue();
			if(i%2==1) pw.print("<tr>");
			pw.print("<td><br><br><div id='shop_item'>");
			pw.print("<h3>"+smartwatch.getName()+"</h3>");
			//pw.print("<strong>"+ "$" + smartwatch.getPrice() + "</strong><ul>");
			pw.print("<ul><li id='item'><img src='images/smartwatch/"+smartwatch.getImage()+"' alt='' style='width:200px;height:200px;'/></li>");
			pw.print("<strong style='font-size:18px;'>"+ "$" + smartwatch.getPrice() + "</strong>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='smartwatches'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Smart Watches'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+smartwatch.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Smart Watches'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='View Review' class='btnreview'></form></li></ul>");
			pw.print("</ul></div></td>");
			if(i%2==0 || i == size) pw.print("</tr>");
			i++;
		}		
		pw.print("</table></div></div></div>");		
		utility.printHtml("Footer.html");
		
	}

}
