import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Data")

public class Data extends HttpServlet {

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
		pw.print("<div class='entry'>"
				+ "<div style='margin:25px; margin-left: auto;margin-right: auto;'>"
				+ "<h2 class='title meta' style='text-align:center; padding-top:10px;'><a style='font-size:32px; color:#333333;'>Analyze the Data</a></h2>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center; margin-top:10px; line-height:1.0em;'><a href='DataAnalytics'>Data Analytics</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center; line-height:1.0em;'><a href='DataVisualization'>Data Visualization</a></p>");
		pw.print("</div></div>");
		
		pw.print("<div class='entry'>"
				+ "<div style='margin:25px; margin-left: auto;margin-right: auto;'>"
				+ "<h2 class='title meta' style='text-align:center; padding-top:10px;'><a style='font-size:32px; color:#333333;'>Inventory Information</a></h2>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center; margin-top:10px; line-height:1.0em;'><a href='InventoryReport'>List of all Products</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center; line-height:1.0em;'><a href='InventoryGraph'>Inventory Graph Report</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center; line-height:1.0em;'><a href='ProductsOnSale'>Products On Sale</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center; line-height:1.0em;'><a href='ProductsRebate'>Products having Manufacturer Rebate</a></p>");
		pw.print("</div></div>");
		
		
		pw.print("<div class='entry'>"
				+ "<div style='margin:25px; margin-left: auto;margin-right: auto;'>"
				+ "<h2 class='title meta' style='text-align:center; padding-top:10px;'><a style='font-size:32px; color:#333333;'>Sales Report</a></h2>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center; margin-top:10px; line-height:1.0em;'><a href='SalesReport'>Product List with Sold Quantity</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center; line-height:1.0em;'><a href='SalesGraph'>Sales Graph Report</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center; line-height:1.0em;'><a href='DailySales'>Total Sales by Date</a></p>");
		
		pw.print("</div></div><br><br><br>");
		utility.printHtml("Footer.html");
	}

}
