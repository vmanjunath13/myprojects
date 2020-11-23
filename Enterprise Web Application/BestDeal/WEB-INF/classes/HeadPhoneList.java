import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HeadPhoneList")

public class HeadPhoneList extends HttpServlet {

	/* Headphones Page Displays all the Headphones and their Information in HeadPhone Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,HeadPhone> allheadphones = new HashMap<String,HeadPhone> ();

		try{
			allheadphones = MySqlDataStoreUtilities.getHeadPhones();
		}
		catch(Exception e)
		{

		}

		/* Checks the Headphones type  */
				
		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, HeadPhone> hm = new HashMap<String, HeadPhone>();
		
		if(ProductName==null)
		{
			hm.putAll(allheadphones);
			name = "";
		}
		else
		{
		  if(ProductName.equals("sony"))
		  {
			for(Map.Entry<String,HeadPhone> entry : allheadphones.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Sony"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Sony";
		  }
		  else if(ProductName.equals("bose"))
		  {
			for(Map.Entry<String,HeadPhone> entry : allheadphones.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Bose"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Bose";
		  }
		  else if(ProductName.equals("sennheiser"))
		  {
			for(Map.Entry<String,HeadPhone> entry : allheadphones.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Sennheiser"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Sennheiser";
		  }
		  else if(ProductName.equals("plantronics"))
		  {
			for(Map.Entry<String,HeadPhone> entry : allheadphones.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Plantronics"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Plantronics";
		  }
		  else if(ProductName.equals("jabra"))
		  {
			for(Map.Entry<String,HeadPhone> entry : allheadphones.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Jabra"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Jabra";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Headphones and Headphones information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+name+" Headphone Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, HeadPhone> entry : hm.entrySet()){
			HeadPhone headphone = entry.getValue();
			if(i%2==1) pw.print("<tr>");
			//style='border-bottom: 1px solid #333;'
			pw.print("<td><br><br><div id='shop_item'>");
			pw.print("<h3>"+headphone.getName()+"</h3>");
			//pw.print("<strong>"+ "$" + headphone.getPrice() + "</strong><ul>");
			pw.print("<ul><li id='item'><img src='images/headphone/"+headphone.getImage()+"' alt='' style='width:200px;height:200px;'/></li>");
			pw.print("<strong style='font-size:18px;'>"+ "$" + headphone.getPrice() + "</strong>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='headphones'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Head Phones'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+headphone.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Head Phones'>"+
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
