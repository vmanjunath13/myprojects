import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;

@WebServlet("/InventoryReport")

public class InventoryReport extends HttpServlet {

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		displayLogin(request, response, pw, true);
	}

	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		String ptype = request.getParameter("PType");
		
		pw.print("<div id='content'><div class='post'>");
		if(ptype == null) {
			pw.print("<form method='post'>");
			pw.print("<p style='font-size:14px;color:#006666;font-weight:bold;margin-left:160px;'>Please select one of the providers from the dropdown menu below</p>");
			pw.print("<select name='PType' style='height:30px;font-size:15px;color:#006666;margin-left:350px;font-weight:bold;'>");
			pw.print("<option style='font-size:15px;' value=''>---Please Select---</option>");
			pw.print("<option style='font-size:12px;' value='Doctor'>Doctor</option>");
			pw.print("<option style='font-size:12px;' value='Hospital'>Hospital</option>");
			pw.print("<option style='font-size:12px;' value='HealthClub'>Health Club</option>");
			pw.print("<option style='font-size:12px;' value='HealthInsurance'>Health Insurance</option>");
			pw.print("<option style='font-size:12px;' value='Pharmacy'>Pharmacy</option></select>");
			pw.print("<input type='submit' name='button' value='Submit' class='btnbuy' style='margin-top:20px;'></form>");
		}
		else 
		{
			pw.print("<h2 class='title meta' style='text-align:center; padding-top:15px;'>");
			pw.print("<a style='font-size: 32px;'>List of all " + ptype + "s</a>");
			pw.print("</h2><div class='entry'>");

			try
			{
				Map<String,Provider> pMap = MySqlDataStoreUtilities.getInventoryProviders();

				pw.print("<table  class='gridtable' style='padding-top:20px'>");
				
				int i=1; int k=1; int size = pMap.size();
				String flag = "No";
				for (Map.Entry<String,Provider> entry : pMap.entrySet()) 
				{
					Provider provider = entry.getValue();
					if (provider.getType().equals(ptype)) {
						if(k==1) {
							pw.print("<tr>");
							pw.print("<td style='text-align:center;font-size:13px;color:#006666;width:20%;'><strong>" + ptype + " Name</strong></td>");
							pw.print("<td style='text-align:center;font-size:13px;color:#006666;'><strong>" + ptype + " Type</strong></td>");
							pw.print("<td style='text-align:center;font-size:13px;color:#006666;'><strong>" + ptype + " Experience</strong></td><td></td>");
							pw.print("<td style='text-align:center;font-size:13px;color:#006666;width:20%;'><strong>" + ptype + " Name</strong></td>");
							pw.print("<td style='text-align:center;font-size:13px;color:#006666;'><strong>" + ptype + " Type</strong></td>");
							pw.print("<td style='text-align:center;font-size:13px;color:#006666;'><strong>" + ptype + " Experience</strong></td><td></td></tr>");
							k++;
							flag = "Yes";
						} 
						
						if(i%2==1) pw.print("<tr>");			
						pw.print("<td style='font-size:14px; text-align:center;' style='width:20%;'>"+provider.getName()+"</td>");			
						pw.print("<td style='font-size:14px; text-align:center;'>"+provider.getType()+"</td>");
						pw.print("<td style='font-size:14px; text-align:center;'>"+provider.getExperience()+"</td><td></td>");
						if(i%2==0 || i == size) pw.print("</tr>");
						i++;
					}
				}
				if (flag.equals("No")) {
					pw.print("<p style='font-size:15px;margin-left:250px;color:#006666;font-weight:bold;'>There are no " + ptype + "s in the database</p>");
				}
				pw.print("</table>");

			}
			catch(Exception e)
			{
		
			}

			pw.print("</div>");
		}
		pw.print("</div></div>");
		utility.printHtml("Footer.html");
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}

}
