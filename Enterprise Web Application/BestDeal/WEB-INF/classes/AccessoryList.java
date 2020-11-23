import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AccessoryList")

public class AccessoryList extends HttpServlet {

	/* Accessory Page Displays all the Accessories and their Information in Game Speed */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the Tv maker whether it is microsft or sony or nintendo 
		   Add the respective product value to hashmap  */

		String CategoryName = request.getParameter("maker");
//		String ConsoleName = request.getParameter("tv");
		HashMap<String, Tv> hm = new HashMap<String, Tv>();
			if(CategoryName.equals("sony"))
			{
				for(Map.Entry<String,Tv> entry : SaxParserDataStore.tvs.entrySet())
				{	
					if(entry.getValue().getRetailer().equals("Sony"))
					{
					 hm.put(entry.getValue().getId(),entry.getValue());
					}
				}
				
			}
			else if(CategoryName.equals("lg"))
			{	
				for(Map.Entry<String,Tv> entry : SaxParserDataStore.tvs.entrySet())
				{	
				  if(entry.getValue().getRetailer().equals("LG"))
				 { 
					hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			}
			else if(CategoryName.equals("samsung"))
			{
				for(Map.Entry<String,Tv> entry : SaxParserDataStore.tvs.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Samsung"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			}

				
		/* Header, Left Navigation Bar are Printed.

		All the Accessories and Accessories information are dispalyed in the Content Section

		and then Footer is Printed*/

		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+ CategoryName +": Accessorie Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= 2;
		for(Map.Entry<String, Tv> entry : hm.entrySet())
		{
			Tv tv = entry.getValue();
			for(Map.Entry<String, String> acc:tv.getAccessories().entrySet())
			{
		        
				Accessory accessory= SaxParserDataStore.accessories.get(acc.getValue());
				if(i%2==1) pw.print("<tr>");
				System.out.print(size);
				pw.print("<td><br><br><div id='shop_item'>");
				pw.print("<h3>"+accessory.getName()+"</h3>");
				pw.print("<strong style='font-size:18px;'>"+accessory.getPrice()+"$</strong><ul>");
				pw.print("<li id='item'><img src='images/accessories/"+accessory.getImage()+"' alt='' style='width:200px;height:200px;/></li>");
				pw.print("<li><form method='post' action='Cart'>" +
						"<input type='hidden' name='name' value='"+acc.getValue()+"'>"+
						"<input type='hidden' name='type' value='accessories'>"+
						"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
						"<input type='hidden' name='access' value='"+tv.getName()+"'>"+
						"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
				pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+acc+"'>"+
						"<input type='hidden' name='type' value='accessories'>"+
						"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
						"<input type='hidden' name='access' value='"+tv.getName()+"'>"+
						"<input type='submit' value='Write Review' class='btnreview'></form></li>");
				pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+acc+"'>"+
						"<input type='hidden' name='type' value='accessories'>"+
						"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
						"<input type='hidden' name='access' value='"+tv.getName()+"'>"+
						"<input type='submit' value='View Review' class='btnreview'></form></li>");
		
				pw.print("</ul></div></td>");
				if(i%2==0 || i == size) pw.print("</tr>");
				i++;
			
			}	
		}	
		pw.print("</table></div></div></div>");		
		utility.printHtml("Footer.html");
	}
}
