import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TvList")

public class TvList extends HttpServlet {

	/* Tvs Page Displays all the Tvs and their Information in Tv Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Tv> alltvs = new HashMap<String,Tv> ();

		try{
			System.out.println("inside try");
		    	alltvs = MySqlDataStoreUtilities.getTVs();
		}
		catch(Exception e)
		{
			
		}

		/* Checks the Tvs type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, Tv> hm = new HashMap<String, Tv>();
		
		if(ProductName==null)
		{
			hm.putAll(alltvs);
			name = "";
		}
		else
		{
		  if(ProductName.equals("sony"))
		  {
			for(Map.Entry<String,Tv> entry : alltvs.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Sony"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Sony";
		  }
		  else if(ProductName.equals("lg"))
		  {
			for(Map.Entry<String,Tv> entry : alltvs.entrySet())
				{
				if(entry.getValue().getRetailer().equals("LG"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "LG";
		  }
		  else if(ProductName.equals("samsung"))
		  {
			for(Map.Entry<String,Tv> entry : alltvs.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Samsung"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Samsung";
		  }
		  else if(ProductName.equals("insignia"))
		  {
			for(Map.Entry<String,Tv> entry : alltvs.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Insignia"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Insignia";
		  }
		  else if(ProductName.equals("vizio"))
		  {
			for(Map.Entry<String,Tv> entry : alltvs.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Vizio"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Vizio";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Tvs and Tvs information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+name+" TV Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Tv> entry : hm.entrySet()){
			Tv tv = entry.getValue();
			if(i%2==1) pw.print("<tr>");
			//style='border-bottom: 1px solid #333;'
			pw.print("<td><br><br><div id='shop_item'>");
			pw.print("<h3 style='font-size:18px;font-weight:bold;'>"+tv.getName()+"</h3>");
			//pw.print("<strong>"+ "$" + tv.getPrice() + "</strong><ul>");
			pw.print("<ul><li id='item'><img src='images/tv/"+tv.getImage()+"' alt='' style='width:200px;height:200px;'/></li>");
			pw.print("<strong style='font-size:18px;'>"+ "$" + tv.getPrice() + "</strong>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='tvs'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='TV'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+tv.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='TV'>"+
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
