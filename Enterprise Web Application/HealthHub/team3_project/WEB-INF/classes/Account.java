import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.*;

@WebServlet("/Account")

public class Account extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayAccount(request, response);
	}

	/* Display Account Details of the Customer (Name and Usertype) */

	protected void displayAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try
         {  
           response.setContentType("text/html");
			if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);	
				session.setAttribute("msg_src", "Account"); 
				session.setAttribute("login_msg", "Please Login to add items to cart");
				response.sendRedirect("Login");
				return;
			}
			HttpSession session=request.getSession(); 	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Account</a>");
			pw.print("</h2><div class='entry'>");
			User user=utility.getUser();
			String cid = user.getName();
			pw.print("<table class='gridtable' style='padding-top:30px;'><tr><td style='font-size:14px;width:25%;color:#006666;'> <strong>User Name:</strong> </td><td style='font-size:14px;color:#006666;width:25%;'><strong>" + cid + "</strong>");
			pw.print("</td></tr></table><br><br>");
			HashMap<String,String[]> bookings = new HashMap<String, String[]>();
			try
			{
				bookings = MySqlDataStoreUtilities.selectBooking(cid);
			}
			catch(Exception e)
			{
			
			}
			
			if(bookings.size() != 0) {
				pw.print("<table class='gridtable'><tr><th></th>");
				pw.print("<th style='text-align:center;'>ID</th>");
				pw.print("<th style='text-align:center;'>Your Full Name</th>");				
				pw.print("<th style='text-align:center;'>Doctor Name</th>");
				pw.print("<th style='text-align:center;'>Appointment Date</th>");
				pw.print("<th style='text-align:center;'>Appointment Time</th>");	
				pw.print("<th style='text-align:center;'>Appointment At/With</th></tr>");	
				
				for(Map.Entry<String, String[]> entry : bookings.entrySet())
				{
					String[] booking = entry.getValue();	
					pw.print("<form method='get' action='ViewBooking'>");
					pw.print("<tr>");			
					pw.print("<td><input type='radio' name='bookingName' value='"+booking[0]+"'></td>");			
					pw.print("<td style='font-size:14px; text-align:center;'>"+booking[3]+"</td>");
					pw.print("<td style='font-size:14px; text-align:center;'>"+booking[4]+"</td>");
					pw.print("<td style='font-size:14px; text-align:center;'>"+booking[0]+"</td>");
					pw.print("<td style='font-size:14px; text-align:center;'>"+booking[1]+"</td>");
					pw.print("<td style='font-size:14px; text-align:center;'>"+booking[2]+"</td>");
					pw.print("<td style='font-size:14px; text-align:center;'>"+booking[5]+"</td>");
					pw.print("<td><input type='submit' name='Order' value='Cancel Booking' class='btnbuy'></td>");
					pw.print("</tr>");
					pw.print("<input type='hidden' name='CId' value='"+booking[3]+"'>");
					pw.print("</form>");	
					
				}
				pw.print("</table>");
			}
			else
			{
				pw.print("<h4 style='color:red'>There are no appointments.</h4>");
			}					
			pw.print("</div></div></div>");		
			utility.printHtml("Footer.html");	        	
		}
		catch(Exception e)
		{
		}		
	}
}
