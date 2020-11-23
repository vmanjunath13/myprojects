import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SoundSystemList")

public class SoundSystemList extends HttpServlet {

	/* Sound Systems Page Displays all the Sound Systems and their Information in SoundSystem Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,SoundSystem> allsoundsystems = new HashMap<String,SoundSystem> ();

		try{
		     allsoundsystems = MySqlDataStoreUtilities.getSoundSystems();
		}
		catch(Exception e)
		{
			
		}

		/* Checks the Sound Systems type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, SoundSystem> hm = new HashMap<String, SoundSystem>();
		
		if(ProductName==null)
		{
			hm.putAll(allsoundsystems);
			name = "";
		}
		else
		{
		  if(ProductName.equals("sony"))
		  {
			for(Map.Entry<String,SoundSystem> entry : allsoundsystems.entrySet())
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
			for(Map.Entry<String,SoundSystem> entry : allsoundsystems.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Bose"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Bose";
		  }
		  else if(ProductName.equals("logitech"))
		  {
			for(Map.Entry<String,SoundSystem> entry : allsoundsystems.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Logitech"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Logitech";
		  }
		  else if(ProductName.equals("polk"))
		  {
			for(Map.Entry<String,SoundSystem> entry : allsoundsystems.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Polk"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Polk";
		  }
		  else if(ProductName.equals("insignia"))
		  {
			for(Map.Entry<String,SoundSystem> entry : allsoundsystems.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Insignia"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Insignia";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Sound Systems and Sound Systems information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+name+" SoundSystem Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, SoundSystem> entry : hm.entrySet()){
			SoundSystem soundsystem = entry.getValue();
			if(i%2==1) pw.print("<tr>");
			// style='border-bottom: 1px solid #333;'
			pw.print("<td><br><br><div id='shop_item'>");
			pw.print("<h3>"+soundsystem.getName()+"</h3>");
			//pw.print("<strong>"+ "$" + soundsystem.getPrice() + "</strong><ul>");
			pw.print("<ul><li id='item'><img src='images/soundsystem/"+soundsystem.getImage()+"' alt='' style='width:200px;height:200px;'/></li>");
			pw.print("<strong style='font-size:18px;'>"+ "$" + soundsystem.getPrice() + "</strong>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='soundsystems'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Sound Systems'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+soundsystem.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Sound Systems'>"+
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
