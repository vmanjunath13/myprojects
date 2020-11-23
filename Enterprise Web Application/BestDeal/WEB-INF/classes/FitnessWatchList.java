import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FitnessWatchList")

public class FitnessWatchList extends HttpServlet {

	/* Fitness Watches Page Displays all the Fitness Watches and their Information in FitnessWatch Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,FitnessWatch> allfitnesswatches = new HashMap<String,FitnessWatch> ();

		try{
			allfitnesswatches = MySqlDataStoreUtilities.getFitnessWatches();
		}
		catch(Exception e)
		{

		}

		/* Checks the Fitness Watches type */
				
		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, FitnessWatch> hm = new HashMap<String, FitnessWatch>();
		
		if(ProductName==null)
		{
			hm.putAll(allfitnesswatches);
			name = "";
		}
		else
		{
		  if(ProductName.equals("fitbit"))
		  {
			for(Map.Entry<String,FitnessWatch> entry : allfitnesswatches.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Fitbit"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Fitbit";
		  }
		  else if(ProductName.equals("teslasz"))
		  {
			for(Map.Entry<String,FitnessWatch> entry : allfitnesswatches.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Teslasz"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Teslasz";
		  }
		  else if(ProductName.equals("lintelek"))
		  {
			for(Map.Entry<String,FitnessWatch> entry : allfitnesswatches.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Lintelek"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Lintelek";
		  }
		  else if(ProductName.equals("letsfit"))
		  {
			for(Map.Entry<String,FitnessWatch> entry : allfitnesswatches.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Letsfit"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Letsfit";
		  }
		  else if(ProductName.equals("yamay"))
		  {
			for(Map.Entry<String,FitnessWatch> entry : allfitnesswatches.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Yamay"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Yamay";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Fitness Watches and Fitness Watches information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+name+" Fitness Watch Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, FitnessWatch> entry : hm.entrySet()){
			FitnessWatch fitnesswatch = entry.getValue();
			if(i%2==1) pw.print("<tr>");
			pw.print("<td><br><br><div id='shop_item'>");
			pw.print("<h3>"+fitnesswatch.getName()+"</h3>");
			//pw.print("<strong>"+ "$" + fitnesswatch.getPrice() + "</strong><ul>");
			pw.print("<ul><li id='item'><img src='images/fitnesswatch/"+fitnesswatch.getImage()+"' alt='' style='width:200px;height:200px;'/></li>");
			pw.print("<strong style='font-size:18px;'>"+ "$" + fitnesswatch.getPrice() + "</strong>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='fitnesswatches'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Fitness Watch'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+fitnesswatch.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Fitness Watch'>"+
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
