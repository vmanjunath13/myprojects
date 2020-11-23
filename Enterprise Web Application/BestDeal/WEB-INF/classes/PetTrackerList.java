import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PetTrackerList")

public class PetTrackerList extends HttpServlet {

	/* PetTrackers Page Displays all the PetTrackers and their Information in PetTracker Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,PetTracker> allpettrackers = new HashMap<String,PetTracker> ();

		try{
			allpettrackers = MySqlDataStoreUtilities.getPetTrackers();
		}
		catch(Exception e)
		{

		}

		/* Checks the PetTrackers type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, PetTracker> hm = new HashMap<String, PetTracker>();
		
		if(ProductName==null)
		{
			hm.putAll(allpettrackers);
			name = "";
		}
		else
		{
		  if(ProductName.equals("whistle"))
		  {
			for(Map.Entry<String,PetTracker> entry : allpettrackers.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Whistle"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Whistle";
		  }
		  else if(ProductName.equals("fitbit"))
		  {
			for(Map.Entry<String,PetTracker> entry : allpettrackers.entrySet())
				{
				if(entry.getValue().getRetailer().equals("FitBit"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "FitBit";
		  }
		  else if(ProductName.equals("tagg"))
		  {
			for(Map.Entry<String,PetTracker> entry : allpettrackers.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Tagg"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Tagg";
		  }
		  else if(ProductName.equals("petfon"))
		  {
			for(Map.Entry<String,PetTracker> entry : allpettrackers.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Petfon"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Petfon";
		  }
		  else if(ProductName.equals("jiobit"))
		  {
			for(Map.Entry<String,PetTracker> entry : allpettrackers.entrySet())
				{
				if(entry.getValue().getRetailer().equals("JioBit"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "JioBit";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the PetTrackers and PetTrackers information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+name+" PetTracker Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, PetTracker> entry : hm.entrySet()){
			PetTracker pettracker = entry.getValue();
			if(i%2==1) pw.print("<tr>");
			// style='border-bottom: 1px solid #333;'
			pw.print("<td><br><br><div id='shop_item'>");
			pw.print("<h3>"+pettracker.getName()+"</h3>");
			//pw.print("<strong>"+ "$" + pettracker.getPrice() + "</strong><ul>");
			pw.print("<ul><li id='item'><img src='images/pettracker/"+pettracker.getImage()+"' alt='' style='width:200px;height:200px;'/></li>");
			pw.print("<strong style='font-size:18px;'>"+ "$" + pettracker.getPrice() + "</strong>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='pettrackers'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Pet Trackers'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+pettracker.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Pet Trackers'>"+
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
