import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddProducts")

public class AddProducts extends HttpServlet {

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
		pw.print("<h2 class='title meta' style='text-align:center; padding-top:20px;'><a style='font-size:32px; color:#333333; text-align:center;'>Add Product</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");

		pw.print("<form name ='AddProduct' action='ProductCrud' method='get'>");

		pw.print("<p style='font-size:20px; font-weight:bold; color:#333333; text-align:center; padding-top:10px;'>Enter Product Details</p>");
		pw.print("<table>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Product Type*:</td>");
		pw.print("<td style='height:18px;'><select name='ProductType'><option value=''>---- Please Select----</option>");
		pw.print("<option value='TV'>TV</option>");
		pw.print("<option value='Sound System'>Sound System</option>");
		pw.print("<option value='Phone'>Phone</option>");
		pw.print("<option value='Laptop'>Laptop</option>");
		pw.print("<option value='Voice Assistant'>Voice Assistant</option>");
		pw.print("<option value='Fitness Watch'>Fitness Watch</option>");
		pw.print("<option value='Smart Watch'>Smart Watch</option>");
		pw.print("<option value='HeadPhone'>HeadPhone</option>");
		pw.print("<option value='Virtual Reality'>Virtual Reality</option>");
		pw.print("<option value='Pet Tracker'>Pet Tracker</option>");
		pw.print("<option value='Accessory'>Accessory</option></select>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
     	pw.print("ProductID*:</td>");
		pw.print("<td><input type='text' style='height:20px;' name='Id' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
     	pw.print("Product Name*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='productName' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
     	pw.print("Product Price*:</td>");
		pw.print("<td><input type='number' style='height:15px;' placeholder='Enter numeric data' step='0.01' name='productPrice' required>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
     	pw.print("Product Image*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='productImage' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Product Manufacturer*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='productManufacturer' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Product Condition*:</td>");
		pw.print("<td><input type='text' style='height:15px;' name='productCondition' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;'>");
		pw.print("Product Discount*:</td>");
		pw.print("<td><input type='number' style='height:15px;' step='0.01' placeholder='Enter numeric data' name='productDiscount' required>");
		pw.print("</td></tr><br><br>");

		pw.print("<tr><td style='text-align:right; font-size:18px;' required>");
     	pw.print("Rebate Amount*:</td>");
		pw.print("<td><input type='number' style='height:15px;' step='0.01' placeholder='Enter numeric data' name='rebateAmount' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:18px;' required>");
     	pw.print("Product Count*:</td>");
		pw.print("<td><input type='number' style='height:15px;' placeholder='Enter numeric data' name='count' required>");
		pw.print("</td></tr>");

		pw.print("<tr><td colspan='2' style='text-align:center;'>");
		pw.print("<input type='submit' name='button' value='Add Product' class='btnbuy'>");
		pw.print("</td></tr></table></form>");
		pw.print("</div></div>");
		
		utility.printHtml("Footer.html");
	}

}
