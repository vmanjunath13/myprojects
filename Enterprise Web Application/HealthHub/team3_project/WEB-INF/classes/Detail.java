import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@WebServlet("/Detail")

public class Detail extends HttpServlet {

	/* Providers Page Displays all the Providers and their Information in Provider Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String id = request.getParameter("ID");
		HashMap<String,Provider> allproviders = new HashMap<String,Provider> ();

		try
		{
			allproviders = MySqlDataStoreUtilities.getDetails(id);
		}
		catch(Exception e)
		{

		}
		
		/* Header, Left Navigation Bar are Printed.

		All the Providers and Providers information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		Random rand = new Random(); 
		int rnum = rand.nextInt(10);
		if(allproviders.size() == 0) {
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>Information</h2><div class='entry'>");
			pw.print("<span><h4>Please note the selected provider is removed from system. Kindly look for different options.</h4></span>");
			pw.print("</div></div></div>");
		} else {
			for(Map.Entry<String, Provider> entry : allproviders.entrySet())
			{
				Provider provider = entry.getValue();
				pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
				String pname = provider.getName().substring(0, 1).toUpperCase() + provider.getName().substring(1);
				String ptype = provider.getType();
				String paddress = provider.getStAddress() + ", " + provider.getCity() + ", " + provider.getState() + ", " + provider.getZipCode();
				int pexp = (Integer.parseInt(provider.getExperience())) + rnum;
				if(ptype.equals("Doctor")) {
					pw.print("<a style='font-size: 32px;color: #000 !important;'>Dr. " + pname + "</a>");
				} else {
					pw.print("<a style='font-size: 32px;color: #000 !important;'>" + pname + "</a>");
				} 
				pw.print("</h2><div class='entry'><table style='width:100%'>");
				pw.print("<tr><td><img src='images/" + provider.getType().toLowerCase() + "/" + provider.getImage() + "' alt='' style='height:140px;width:140px'/></td>");
				pw.print("<td><span><h4>"+ provider.getSpeciality() + "</h4></span>");
				if(ptype.equals("Hospital")) {
					pw.print("<span><h4>"+ pexp + " years of medical service providers</h4></span>");
				} else {
					pw.print("<span><h4>"+ provider.getExperience() + " years experience overall</h4></span>");
				}
				pw.print("<span><h4>"+ provider.getStAddress() + "</h4></span>");
				pw.print("<span><h4>"+ provider.getCity() + ", " + provider.getState() + ", " + provider.getZipCode() + "</h4></span>");
				if(provider.getFees() != 0.0) {
					if(ptype.equals("Doctor") || ptype.equals("Hospital")) {
						pw.print("<span><h4>$"+ provider.getFees() + " Consultation fee</h4></span></td>");
					} else if(ptype.equals("HealthClub")) {
						pw.print("<span><h4>$"+ provider.getFees() + " Enrollment fee</h4></span></td>");
					} else {
						pw.print("<span><h4>$"+ provider.getFees() + " Insurance fee</h4></span></td>");
					}					
				}
				pw.print("<td><div><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+pname+"'>"+
						"<input type='hidden' name='type' value='"+provider.getType()+"'>"+
						"<input type='hidden' name='maker' value='"+pname+"'>"+
						"<input type='submit' value='View Review' class='btnreview'></form>");
				pw.print("</br>");
				pw.print("<form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+pname+"'>"+
						"<input type='hidden' name='type' value='"+provider.getType()+"'>"+
						"<input type='hidden' name='maker' value='"+pname+"'>"+
						"<input type='submit' value='Write Review' class='btnreview'></form>");	
				pw.print("</br>");
				pw.print("<form method='post' action='AddWishlist'>"+"<input type='hidden' name='name' value='"+pname+"'>"+
						"<input type='hidden' name='type' value='"+provider.getType()+"'>"+
						"<input type='hidden' name='maker' value='"+pname+"'>"+
						"<input type='hidden' name='pid' value='"+provider.getId()+"'>"+
						"<input type='hidden' name='address' value='"+paddress+"'>"+
						"<input type='submit' value='Add to Wishlist' class='btnreview'></form>");	
				pw.print("</br>");
				pw.print("<form method='post' action='Appointment'>" +
						"<input type='hidden' name='id' value='"+provider.getId()+"'>"+
						"<input type='hidden' name='name' value='"+pname+"'>"+
						"<input type='hidden' name='type' value='"+provider.getType()+"'>"+
						"<input type='submit' class='btnbuy' value='Book an Apointment Now'></form></td>");
				pw.print("</tr></table>");
				pw.print("</br></br><span><h6>Due to Covid19, All in-person visit are made through appointments only!</h6></span>");
				pw.print("</div></div></div></div>");
			}		
		}
		//pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
		
	}

}
