import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VoiceAssistantList")

public class VoiceAssistantList extends HttpServlet {

	/* Voice Assistant Page Displays all the Voice Assistant and their Information in VoiceAssistant Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,VoiceAssistant> allvoiceassistants = new HashMap<String,VoiceAssistant> ();

		try{
			allvoiceassistants = MySqlDataStoreUtilities.getVoiceAssistants();
		}
		catch(Exception e)
		{

		}

		/* Checks the Voice Assistant type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, VoiceAssistant> hm = new HashMap<String, VoiceAssistant>();
		
		if(ProductName==null)
		{
			hm.putAll(allvoiceassistants);
			name = "";
		}
		else
		{
		  if(ProductName.equals("amazon"))
		  {
			for(Map.Entry<String,VoiceAssistant> entry : allvoiceassistants.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Amazon"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Amazon";
		  }
		  else if(ProductName.equals("google"))
		  {
			for(Map.Entry<String,VoiceAssistant> entry : allvoiceassistants.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Google"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Google";
		  }
		  else if(ProductName.equals("apple"))
		  {
			for(Map.Entry<String,VoiceAssistant> entry : allvoiceassistants.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Apple"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Apple";
		  }
		  else if(ProductName.equals("bose"))
		  {
			for(Map.Entry<String,VoiceAssistant> entry : allvoiceassistants.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Bose"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Bose";
		  }
		  else if(ProductName.equals("jb;"))
		  {
			for(Map.Entry<String,VoiceAssistant> entry : allvoiceassistants.entrySet())
				{
				if(entry.getValue().getRetailer().equals("JBL"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "JBL";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Voice Assistant and Voice Assistant information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+name+" Voice Assistant Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, VoiceAssistant> entry : hm.entrySet()){
			VoiceAssistant voiceassistant = entry.getValue();
			if(i%2==1) pw.print("<tr>");
			pw.print("<td><br><br><div id='shop_item'>");
			pw.print("<h3>"+voiceassistant.getName()+"</h3>");
			//pw.print("<strong>"+ "$" + voiceassistant.getPrice() + "</strong><ul>");
			pw.print("<ul><li id='item'><img src='images/voiceassistant/"+voiceassistant.getImage()+"' alt='' style='width:200px;height:200px;'/></li>");
			pw.print("<strong style='font-size:18px;'>"+ "$" + voiceassistant.getPrice() + "</strong>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='voiceassistants'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Voice Assistant'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+voiceassistant.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Voice Assistant'>"+
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
