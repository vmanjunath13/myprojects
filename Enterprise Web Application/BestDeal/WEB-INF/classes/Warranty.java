import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Warranty")

public class Warranty extends HttpServlet {

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


	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<h2 class='title meta' style='text-align:center;'><a style='font-size:32px; color:#333333;'>Warranty</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:650px; margin:25px; margin-left: auto;margin-right: auto;'>");
		pw.print("<p style='font-size:34px; color:#333333; text-align:center;'>Protect It Today. Use it for years!</p>");
		pw.print("<p style='font-size:30px; color:#333333; text-align:center;'>Drops..Spills..Accidents..Covered</p>");
		pw.print("<p style='font-size:24px; color:#333333; text-align:center;'>Covers drops & spills | No deductables and hidden fees | 24/7 Customer Support</p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center;'>Do you want to buy Warranty?</p>");
		pw.print("<form method='post' style='text-align:center' action='Cart'>" +
					"<input type='hidden' name='name' value='Warranty'>"+
					"<input type='hidden' name='type' value='warranty'>"+
					"<input type='hidden' name='maker' value='warranty'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form>");
		pw.print("</td></td>");
		pw.print("</div></div>");

		utility.printHtml("Footer.html");
	}

}
