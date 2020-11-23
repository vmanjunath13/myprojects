import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VirtualRealityList")

public class VirtualRealityList extends HttpServlet {

	/* VirtualRealitys Page Displays all the VirtualRealitys and their Information in VirtualReality Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,VirtualReality> allvirtualrealitys = new HashMap<String,VirtualReality> ();

		try{
			allvirtualrealitys = MySqlDataStoreUtilities.getVirtualRealitys();
		}
		catch(Exception e)
		{

		}

		/* Checks the VirtualRealitys type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, VirtualReality> hm = new HashMap<String, VirtualReality>();
		
		if(ProductName==null)
		{
			hm.putAll(allvirtualrealitys);
			name = "";
		}
		else
		{
		  if(ProductName.equals("htc"))
		  {
			for(Map.Entry<String,VirtualReality> entry : allvirtualrealitys.entrySet())
				{
				if(entry.getValue().getRetailer().equals("HTC"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "HTC";
		  }
		  else if(ProductName.equals("oculus"))
		  {
			for(Map.Entry<String,VirtualReality> entry : allvirtualrealitys.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Oculus"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Oculus";
		  }
		  else if(ProductName.equals("samsung"))
		  {
			for(Map.Entry<String,VirtualReality> entry : allvirtualrealitys.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Samsung"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Samsung";
		  }
		  else if(ProductName.equals("homido"))
		  {
			for(Map.Entry<String,VirtualReality> entry : allvirtualrealitys.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Homido"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Homido";
		  }
		  else if(ProductName.equals("sony"))
		  {
			for(Map.Entry<String,VirtualReality> entry : allvirtualrealitys.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Sony"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Sony";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the VirtualRealitys and VirtualRealitys information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+name+" Virtual Reality Lists</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, VirtualReality> entry : hm.entrySet()){
			VirtualReality virtualreality = entry.getValue();
			if(i%2==1) pw.print("<tr>");
			//style='border-bottom: 1px solid #333;'
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+virtualreality.getName()+"</h3>");
			//pw.print("<strong>"+ "$" + virtualreality.getPrice() + "</strong><ul>");
			pw.print("<ul><li id='item'><img src='images/virtualreality/"+virtualreality.getImage()+"' alt='' style='width:200px;height:200px;'/></li>");
			pw.print("<strong style='font-size:18px;'>"+ "$" + virtualreality.getPrice() + "</strong>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='virtualrealitys'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Virtual Reality'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+virtualreality.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='Virtual Reality'>"+
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
