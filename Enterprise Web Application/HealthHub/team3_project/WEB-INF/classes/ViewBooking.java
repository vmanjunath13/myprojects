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

@WebServlet("/ViewBooking")

public class ViewBooking extends HttpServlet {
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);	
			session.setAttribute("msg_src", "ViewBooking");
			session.setAttribute("login_msg", "Please Login to View your Orders");
			response.sendRedirect("Login");
			return;
		}
		String username=utility.username();
		User user=utility.getUser();
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='ViewBooking' action='ViewBooking' method='get'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Booking</a>");
		pw.print("</h2><div class='entry'>");

		if(request.getParameter("Order").equals("Cancel Booking"))
		{
			if(request.getParameter("bookingName") != null)
			{
				String bookingName = request.getParameter("bookingName");
				String CId = request.getParameter("CId");
				HashMap<String,String[]> bookings = new HashMap<String, String[]>();
				try
				{
					bookings = MySqlDataStoreUtilities.selectBooking(username);
				}
				catch(Exception e)
				{
				
				}
				String msg = "";
				for(Map.Entry<String, String[]> entry : bookings.entrySet())
				{	
					String[] booking = entry.getValue();
					int flag = 0;
					if(booking[0].equals(bookingName) && booking[3].equals(CId))
					{
						flag = 1;
						msg = MySqlDataStoreUtilities.deleteBooking(CId, bookingName);
						System.out.println("msg: " + msg);
						if(msg.equals("Successfully deleted appointment")) 
						{
							pw.print("<h4 style='color:red'>Your Appointment is Cancelled</h4>");
						}
					}
					if(flag == 1){
						break;
					}
				}
			}
			else
			{
				pw.print("<h4 style='color:red'>Please select any Booking</h4>");
			}
		}
		pw.print("</form></div></div></div>");		
		pw.print("<br><form action='Account' method='get'>");
		pw.print("<input type='submit' value='Go to Account' class='btnbuy'>");
		pw.print("</form>");
		utility.printHtml("Footer.html");
	}

}


