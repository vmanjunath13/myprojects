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

@WebServlet("/DailySales")

public class DailySales extends HttpServlet {

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
		utility.printHtml("LeftNavigationBar.html");

		pw.print("<div id='content'><div class='post'><h2 class='title meta' style='text-align:center; padding-top:15px;'>");
		pw.print("<a style='font-size: 32px;'>Total Sales by Date</a>");
		pw.print("</h2><div class='entry'>");

		try
		{
			Map<String,Product> pMap = MySqlDataStoreUtilities.getDailySales();

			pw.print("<table  class='gridtable' style='padding-top:20px'>");
			pw.print("<tr>");
			pw.print("<td style='text-align:center;' style='width:20%;'><strong>Date</strong></td>");
			pw.print("<td style='text-align:center;'><strong>Total Sales</strong></td>");

			for (Map.Entry<String,Product> entry : pMap.entrySet()) 
			{
				Product product = entry.getValue();
				Double total = Math.round(product.gettotal_sales() * 100.0) / 100.0;
				pw.print("<tr>");			
				pw.print("<td style='font-size:14px; text-align:center;' style='width:20%;'>"+product.getdates()+"</td>");			
				pw.print("<td style='font-size:14px; text-align:center;'>$"+total+"</td>");
				pw.print("</tr>");
			
			}
			pw.print("</table>");

		}
		catch(Exception e)
		{
	
		}

		pw.print("</div></div></div>");	
		utility.printHtml("Footer.html");
	}

}
