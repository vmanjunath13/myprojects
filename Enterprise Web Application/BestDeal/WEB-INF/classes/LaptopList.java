import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LaptopList")

public class LaptopList extends HttpServlet {

	/* Laptops Page Displays all the Laptops and their Information in Laptop Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Laptop> alllaptops = new HashMap<String,Laptop> ();

		try{
			alllaptops = MySqlDataStoreUtilities.getLaptops();
		}
		catch(Exception e)
		{

		}

		/* Checks the Laptops type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, Laptop> hm = new HashMap<String, Laptop>();
		
		if(ProductName==null)
		{
			hm.putAll(alllaptops);
			name = "";
		}
		else
		{
		  if(ProductName.equals("hp"))
		  {
			for(Map.Entry<String,Laptop> entry : alllaptops.entrySet())
				{
				if(entry.getValue().getRetailer().equals("HP"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "HP";
		  }
		  else if(ProductName.equals("dell"))
		  {
			for(Map.Entry<String,Laptop> entry : alllaptops.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Dell"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Dell";
		  }
		  else if(ProductName.equals("apple"))
		  {
			for(Map.Entry<String,Laptop> entry : alllaptops.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Apple"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Apple";
		  }
		  else if(ProductName.equals("lenovo"))
		  {
			for(Map.Entry<String,Laptop> entry : alllaptops.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Lenovo"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Lenovo";
		  }
		  else if(ProductName.equals("asus"))
		  {
			for(Map.Entry<String,Laptop> entry : alllaptops.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Asus"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Asus";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Laptops and Laptops information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+name+" Laptop Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Laptop> entry : hm.entrySet()){
			Laptop laptop = entry.getValue();
			if(i%2==1) pw.print("<tr>");
			// style='border-bottom: 1px solid #333;'
			pw.print("<td><br><br><div id='shop_item'>");
			pw.print("<h3>"+laptop.getName()+"</h3>");
			//pw.print("<strong>"+ "$" + laptop.getPrice() + "</strong><ul>");
			pw.print("<ul><li id='item'><img src='images/laptop/"+laptop.getImage()+"' alt='' style='width:200px;height:200px;'/></li>");
			pw.print("<strong style='font-size:18px;'>"+ "$" + laptop.getPrice() + "</strong>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='laptops'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Laptops'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+laptop.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Laptops'>"+
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
