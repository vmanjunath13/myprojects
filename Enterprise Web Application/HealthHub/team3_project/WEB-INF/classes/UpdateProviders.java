import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdateProviders")

public class UpdateProviders extends HttpServlet {

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		displayLogin(request, response, pw, true);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}


	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the BestDeal Application. */
	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<h2 class='title meta' style='text-align:center; padding-top:20px;'><a style='font-size:32px; color:#333333; text-align:center;'>Update Provider</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");

		pw.print("<form name ='UpdateProvider' action='ProviderCrud' method='get'>");

		pw.print("<p style='font-size:20px; font-weight:bold; color:#333333; text-align:center; padding-top:10px;'>Enter Provider Details</p>");
		pw.print("<table>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Provider Type*:</td>");
		pw.print("<td style='height:18px;'><select name='PType'><option value=''>---- Please Select----</option>");
		pw.print("<option value='Doctor'>Doctor</option>");
		pw.print("<option value='Hospital'>Hospital</option>");
		pw.print("<option value='Health Insurance'>Health Insurance</option>");
		pw.print("<option value='Pharmacy'>Pharmacy</option>");
		pw.print("<option value='Health Club'>Health Club</option></select>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
     	pw.print("ProviderID*:</td>");
		pw.print("<td><input type='text' style='height:20px;' name='PId' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
     	pw.print("Provider Name*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PName' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
     	pw.print("Provider Fees*:</td>");
		pw.print("<td><input type='number' style='height:15px;' placeholder='Enter numeric data' step='0.01' name='PPrice' required>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
     	pw.print("Provider Image*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PImage' required>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Provider Experience*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PExp' required>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Provider Speciality*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PSpeciality' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Provider Street Address*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PStAdrress' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Provider City*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PCity' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Provider State*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PState' required>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Provider ZipCode*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PZipcode' required>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Provider Latitude*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PLat' required>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Provider Longitude*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PLong' required>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td colspan='2' style='text-align:center; padding-top:10px;'>");
		pw.print("<input type='submit' name='button' value='Update Provider' class='btnbuy' style='margin-top:20px;'>");
		pw.print("</td></tr></table></form>");
		pw.print("</div></div>");

		utility.printHtml("Footer.html");
	}

}
