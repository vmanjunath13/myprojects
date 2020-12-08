import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteProviders")

public class DeleteProviders extends HttpServlet {

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
		pw.print("<h2 class='title meta' style='text-align:center; padding-top:20px;'><a style='font-size:32px; color:#333333; text-align:center;'>Delete Provider</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");

		pw.print("<form name ='DeleteProvider' action='ProviderCrud' method='get'>");

		pw.print("<p style='font-size:20px; font-weight:bold; color:#333333; text-align:center; padding-top:10px;'>Enter Provider Details</p>");
		pw.print("<table style='margin: auto;'>");	

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
     	pw.print("ProviderID*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='PId'>");
		pw.print("</td></tr>");

		pw.print("<tr><td colspan='2' style='text-align:center; padding-top:10px;'>");
		pw.print("<input type='submit' name='button' value='Delete Provider' class='btnbuy'>");
		pw.print("</td></tr></table></form>");
		pw.print("</div></div>");
		pw.print("<br><br><br><br><br>");

		utility.printHtml("Footer.html");
	}

}
