import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@WebServlet("/ProviderData")
public class ProviderData extends HttpServlet {
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{

PrintWriter pw= response.getWriter();
response.setContentType("text/html");			
 pw.println("<html>");
 pw.println("<body>");

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'>");
		Provider data= (Provider)request.getAttribute("data");
		
		String pname = data.getName().substring(0, 1).toUpperCase() + data.getName().substring(1);
		String ptype = data.getType();
		String paddress = data.getStAddress() + ", " + data.getCity() + ", " + data.getState() + ", " + data.getZipCode();
		if(ptype.equals("Doctor")) {
			pw.print("<a style='font-size: 28px;color: #000 !important;padding-left: 360px !important;'>Dr. " + pname + "</a>");
		} else {
			pw.print("<a style='font-size: 28px;color: #000 !important;padding-left: 180px !important;'>" + pname + "</a>");
		} 
		pw.print("</h2><div class='entry'><table style='width:100%'>");
		pw.print("<tr><td><img src='images/" + data.getType().toLowerCase() + "/" + data.getImage() + "' alt='' style='height:140px;width:140px;border-radius: 58px 58px;'/></td>");
		pw.print("<td><span><h4>"+ data.getSpeciality() + "</h4></span>");
		if(ptype.equals("Hospital")) {
			pw.print("<span><h4>"+ data.getExperience() + " years of medical service providers</h4></span>");
		} else {
			pw.print("<span><h4>"+ data.getExperience() + " years experience overall</h4></span>");
		}
		pw.print("<span><h4>"+ data.getStAddress() + "</h4></span>");
		pw.print("<span><h4>"+ data.getCity() + ", " + data.getState() + ", " + data.getZipCode() + "</h4></span>");
		if(data.getFees() != 0.0) {
			if(ptype.equals("Doctor") || ptype.equals("Hospital")) {
				pw.print("<span><h4>$"+ data.getFees() + " Consultation fee at clinic</h4></span></td>");
			} else if(ptype.equals("HealthClub")) {
				pw.print("<span><h4>$"+ data.getFees() + " Enrollment fee</h4></span></td>");
			} else {
				pw.print("<span><h4>$"+ data.getFees() + " Insurance fee</h4></span></td>");
			}					
		}
		pw.print("<td><div><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+pname+"'>"+
				"<input type='hidden' name='type' value='"+data.getType()+"'>"+
				"<input type='hidden' name='maker' value='"+pname+"'>"+
				"<input type='submit' value='View Review' class='btnreview'></form>");
		pw.print("<form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+pname+"'>"+
				"<input type='hidden' name='type' value='"+data.getType()+"'>"+
				"<input type='hidden' name='maker' value='"+pname+"'>"+
				"<input type='submit' value='Write Review' class='btnreview'></form>");	
		pw.print("<form method='post' action='AddWishlist'>"+"<input type='hidden' name='name' value='"+pname+"'>"+
				"<input type='hidden' name='type' value='"+data.getType()+"'>"+
				"<input type='hidden' name='maker' value='"+pname+"'>"+
				"<input type='hidden' name='pid' value='"+data.getId()+"'>"+
				"<input type='hidden' name='address' value='"+paddress+"'>"+
				"<input type='submit' value='Add to Wishlist' class='btnreview'></form>");	
		pw.print("<form method='post' action='Appointment'>" +
				"<input type='hidden' name='id' value='"+data.getId()+"'>"+
				"<input type='hidden' name='name' value='"+pname+"'>"+
				"<input type='hidden' name='type' value='"+data.getType()+"'>"+
				"<input type='submit' class='btnbuy' value='Book an Apointment Now'></form></td>");
		pw.print("</tr></table></div></div></div></div>");

		utility.printHtml("Footer.html");
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	

}