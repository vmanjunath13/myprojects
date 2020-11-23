import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PhoneList")

public class PhoneList extends HttpServlet {

	/* Trending Page Displays all the Phone and their Information in Samrt Portables*/

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Phone> allphones = new HashMap<String,Phone> ();

		try{
			allphones = MySqlDataStoreUtilities.getPhones();
		}
		catch(Exception e)
		{

		}

	/* Checks the Phone type whether it is microsft or apple or samsung */

		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, Phone> hm = new HashMap<String, Phone>();

		if (ProductName == null)	
		{
			hm.putAll(allphones);
			name = "";
		} 
		else 
		{
			if(ProductName.equals("apple")) 
			{	
				for(Map.Entry<String,Phone> entry : allphones.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Apple"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name ="Apple";
			}  
			else if (ProductName.equals("samsung")) 
			{
				for(Map.Entry<String,Phone> entry : allphones.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Samsung"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Samsung";
			}
			else if (ProductName.equals("nexus")) 
			{
				for(Map.Entry<String,Phone> entry : allphones.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Nexus"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Nexus";
			}
			else if (ProductName.equals("lg")) 
			{
				for(Map.Entry<String,Phone> entry : allphones.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("LG"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "LG";
			}
			else if (ProductName.equals("moto")) 
			{
				for(Map.Entry<String,Phone> entry : allphones.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Moto"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Moto";
			}
	    }

		/* Header, Left Navigation Bar are Printed.

		All the phones and tablet information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+ name+" Phone Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, Phone> entry : hm.entrySet()) {
			Phone Phone = entry.getValue();
			if (i % 2 == 1)
				pw.print("<tr>");
			// style='border-bottom: 1px solid #333;'
			pw.print("<td><br><br><div id='shop_item'>");
			pw.print("<h3>" + Phone.getName() + "</h3>");
			//pw.print("<strong>$" + Phone.getPrice() + "</strong><ul>");
			pw.print("<ul><li id='item'><img src='images/phone/"+ Phone.getImage() + "' alt='' style='width:200px;height:200px;'/></li>");
			pw.print("<strong style='font-size:18px;'>$" + Phone.getPrice() + "</strong>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='phones'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Phones'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+Phone.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Phones'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='View Review' class='btnreview'></form></li></ul>");
			pw.print("</ul></div></td>");
			if (i % 2 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");
	}
}
