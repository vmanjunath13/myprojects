import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;
import javax.servlet.http.HttpSession;
import java.io.*;
@WebServlet("/DataAnalytics")

public class DataAnalytics extends HttpServlet {
	static DBCollection myReviews;
	/* Trending Page Displays all the Consoles and their Information in Game Speed*/

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		displayLogin(request, response, pw, true);
	}
	
	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {
		Utilities utility = new Utilities(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View Reviews");
			response.sendRedirect("Login");
			return;
		}
						
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
			pw.print("<a style='font-size: 32px;'>Data Analytics on Review for " + ptype + "s</a>");
			pw.print("</h2><div class='entry'>");

			try
			{
				Map<String,Provider> pMap = MySqlDataStoreUtilities.getInventoryProviders();

				pw.print("<table  class='gridtable' style='padding-top:20px'>");
				
				int i=1; int k=1; int size = pMap.size();
				String flag = "No";
				pw.print("<table id='bestseller'>");
				pw.print("<form method='post' action='FindReviews'>");
				pw.print("<table id='bestseller'>");
				pw.print("<h3 style='font-size: 20px !important;color: #202078 !important;'>Select any number of options to find the required data</h3></br></br>");		
				pw.print("<tr>");
				pw.print("<td> <input type='checkbox' name='queryCheckBox' value='ProviderName'> Select </td>");
				pw.print("<td> Provider Name: </td>");
				pw.print("<td>");
				pw.print("<select name='ProviderName'>");
				pw.print("<option value='All_" + ptype + "'>All " + ptype + "s</option>");


				for (Map.Entry<String,Provider> entry : pMap.entrySet()) 
				{
					Provider provider = entry.getValue();
					if (provider.getType().equals(ptype)) {
						pw.print("<option value='" + provider.getName() + "'>" + provider.getName() + "</option>");
					}
				}
				pw.print("</select></td></tr>");
		
				pw.print("<tr>");
				pw.print("<td> <input type='checkbox' name='queryCheckBox' value='ProviderFees'> Select </td>");
				pw.print("<td> Provider Fees: </td>");
				pw.print("<td>");
				pw.print("<input type='number' name='ProviderFees' value = '0' size=10/></td>");
				pw.print("<td>");
				pw.print("<input type='radio' name='comparePrice' value='EQUALS_TO' checked> Equals <br>");
				pw.print("<input type='radio' name='comparePrice' value='GREATER_THAN'> Greater Than <br>");
				pw.print("<input type='radio' name='comparePrice' value='LESS_THAN'> Less Than");
				pw.print("</td></tr>");				
									
					
				pw.print("<tr><td> <input type='checkbox' name='queryCheckBox' value='ReviewRating'> Select </td>");
				pw.print("<td> Review Rating: </td>");
				pw.print("<td>");
				pw.print("<select name='ReviewRating'>");
				pw.print("<option value='1' selected>1</option>");
				pw.print("<option value='2'>2</option>");
				pw.print("<option value='3'>3</option>");
				pw.print("<option value='4'>4</option>");
				pw.print("<option value='5'>5</option>");
				pw.print("</td>");
				pw.print("<td>");
				pw.print("<input type='radio' name='compareRating' value='EQUALS_TO' checked> Equals <br>");
				pw.print("<input type='radio' name='compareRating' value='GREATER_THAN'> Greater Than"); 
				pw.print("</td></tr>");
					
				pw.print("<tr>");
				pw.print("<td> <input type='checkbox' name='queryCheckBox' value='CustomerCity'> Select </td>");
				pw.print("<td> Provider City: </td>");
				pw.print("<td>");
				pw.print("<input type='text' name='CustomerCity' /> </td>");						
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> <input type='checkbox' name='queryCheckBox' value='CustomerZIP'> Select </td>");
				pw.print("<td> Provider Zip code: </td>");
				pw.print("<td>");
				pw.print("<input type='text' name='CustomerZIP' /> </td>");
				pw.print("</tr>");

				pw.print("<tr><td>");	
				pw.print("<input type='checkbox' name='extraSettings' value='GROUP_BY'> Group By");
				pw.print("</td>");
				pw.print("<td>");
				pw.print("<select name='groupByDropdown'>");
				pw.print("<option value='GROUP_BY_CITY' selected>City</option>");
				pw.print("<option value='GROUP_BY_PROVIDER'>Provider Name</option>");
				pw.print("<option value='GROUP_BY_ZIP'>Zip Code</option>");                                        
				pw.print("</td><td>");
				pw.print("<input type='radio' name='dataGroupBy' value='Count' checked> Count <br>");
				pw.print("<input type='radio' name='dataGroupBy' value='Detail'> Detail <br>");
				pw.print("</td></tr>");	
				
				pw.print("<tr>");
				pw.print("<td colspan = '4'; style='text-align:center;'> <input type='submit' value='Find Data' class='btnbuy' /> </td>");
				pw.print("</tr>");
									
									
				pw.print("</table>");	
				pw.print("</div>");	
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
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
