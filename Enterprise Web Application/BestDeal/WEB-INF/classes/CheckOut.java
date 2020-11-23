import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    Utilities Utility = new Utilities(request, pw);
		storeOrders(request, response);
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
        HttpSession session=request.getSession(); 

		//get the order product details	on clicking submit the form will be passed to submitorder page	
		
	    String userName = session.getAttribute("username").toString();
        String orderTotal = request.getParameter("orderTotal");
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='CheckOut' action='StorePickup' method='post'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<p style='font-size: 16px;color:#106dc4;padding-top:20px;font-weight:bold'>User Id:  " + userName +"</p>");
		pw.print("<p style='font-size: 16px;color:#106dc4;padding-top:20px;font-weight:bold'>Customer Name:  " + userName +"</p>");
		pw.print("</td></tr>");
		pw.print("<table  class='gridtable'><tr><td style='font-weight:bold'>Product Name</td>");
		pw.print("<td style='font-weight:bold'>Price</td>");
		pw.print("<td style='font-weight:bold'>Manufacturer</td>");
		pw.print("<td style='font-weight:bold'>Category</td>");
		// for each order iterate and display the order name price
		
		for (OrderItem oi : utility.getCustomerOrders()) 
		{
			pw.print("<tr><td style='font-size: 14px;'>"+ oi.getName()+"</td>");
			pw.print("<td style='font-size: 14px;'>$" +oi.getPrice()+"</td>");
			pw.print("<td style='font-size: 14px;'>" +oi.getRetailer()+"</td>");
			pw.print("<td style='font-size: 14px;'>" +oi.getCategory()+"</td>");
			if (oi.getName() != "Warrenty"){
					pw.print("<td style='font-size: 14px; text-align:center;'><a href='Warranty'>Buy Warranty </a></td></tr>");}
			pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
			pw.print("<input type='hidden' name='orderRet' value='"+oi.getRetailer()+"'>");
			pw.print("<input type='hidden' name='orderCat' value='"+oi.getCategory()+"'>");
			pw.print("</td></tr>");
		}
		
		pw.print("<tr><th>");
        pw.print("Total Cost</th><th>$"+orderTotal);
		pw.print("<input type='hidden' name='orderTotal' value='"+orderTotal+"'>");
		pw.print("</th></tr></table>");
		pw.print("<p style='font-size:16px; font-weight:bold; color:#106dc4; padding-top:25px;'>Enter Checkout Details</p>");
		pw.print("<table>");	

		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("FirstName*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='FirstName' maxlength='20'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("LastName*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='LastName' maxlength='20'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("Address*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='userAddress' maxlength='60'>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("City*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='City'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("State*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='State'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("ZIP/Postal*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='zip' maxlength='5'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("Phone Number*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='PhoneNumber' maxlength='10'>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("Your Age*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='age' maxlength='10'>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("Your Occupation*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='occupation' maxlength='10'>");
		pw.print("</td></tr>");
		pw.print("</td></tr></table>");

		pw.print("<p style='font-size:16px; font-weight:bold; color:#106dc4; padding-top:25px;'>Enter Card Details</p>");
		pw.print("<table>");
		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("Credit/Name on Card*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='CardName'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("Credit/Debit Card Number*: </td>");
		pw.print("<td><input type='text' style='height:14px;' name='creditCardNo'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:left; font-size:14px;'>");
	    pw.print("Expiration Date*: </td>");
		pw.print("<td><input type='text' placeholder='MM' style='width:25px; height:14px;' name='Month'> / <input type='text' placeholder='YYYY' style='width:35px;height:14px;' name='Year'>");
        pw.print("</td></tr>");

        pw.print("<tr><td style='text-align:left; font-size:14px;'>");
     	pw.print("CVV*: </td>");
		pw.print("<td><input type='text' placeholder='CVV' style='width:30px; height:14px;' name='cvv'>");
		pw.print("</td></tr></table>");
		
		pw.print("<table><p style='font-size:16px; font-weight:bold; color:#106dc4; padding-top:25px;'>Delivery Options</p>");
		pw.print("<div><tr><td><input type='radio' name='delivery' value='Store Pick-up' checked> Store Pick-up </br></td></tr>");
		pw.print("<tr><td><input type='radio' name='delivery' value='Home Delivery'> Home Delivery (Shipping Cost: $5.99)</br></td></tr></div>");
		pw.print("</table><br><br><br>");		
		
		pw.print("<table><tr><td colspan='2' style='text-align:center;'>");
		pw.print("<input type='submit' name='CheckOut' value='CheckOut' class='btnbuy'>");
        pw.print("</td></tr></table></form>");
		pw.print("</div></div></div>");				
		utility.printHtml("Footer.html");
	    }
        catch(Exception e)
		{
         System.out.println(e.getMessage());
		}  			
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	}
}
