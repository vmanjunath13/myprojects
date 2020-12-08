import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Appointment")

public class Appointment extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		/* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/
		Utilities utility = new Utilities(request, pw);
		displayAppointment(request, response);
	}
	

/* displayAppointment Function shows the products that users has bought, these products will be displayed with Total Amount.*/

	protected void displayAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request,pw);

		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
		
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		
		
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Book Appointment</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<form method='post' action='Booking'>"
		+ "<p style='color:#000;font-size: 22px;font-family: inherit;margin-top: 20px;'><strong>Make an appointment</strong></p><br>"
		
		+ "<label for='appt' style='font-size:16px;margin-right:13px;font-weight:300;color:#000;'>Choose a date:</label><input type='date' id='appt' name='aptdate' style='width:25%;font-size:16px;margin-left:22px;' required><br>"
		
		+ "<label for='aptm' style='font-size:16px;margin-right: 11px;font-weight: 300;color:#000;'>Choose a time:</label>"
		+ "<select id='aptm' name='apttime' style='height:50%;width:25%;font-size:16px;margin-left:23px;' required><option value =''>----Please Select----</option>"
		+ "<option value='09:00'>09:00</option>"
		+ "<option value='10:00'>10:00</option>"
		+ "<option value='11:00'>11:00</option>"
		+ "<option value='12:00'>12:00</option>"
		+ "<option value='14:00'>14:00</option>"
		+ "<option value='15:00'>15:00</option>"
		+ "<option value='16:00'>16:00</option>"
		+ "<option value='17:00'>17:00</option>"
		+ "<option value='18:00'>18:00</option></select><br>"
		
		+ "<label for='apcm' style='margin-right:40px;font-weight: 300;color:#000;font-size:16px;'>Full Name:</label><input type='text' id='apcm' name='custname' placeholder='Enter your full name' style='margin-left:20px;height:50%;width:25%;font-size:16px;' required>"
		
		+ "<input type='hidden' name='pname' value='" + name + "'>" 
		+ "<input type='hidden' name='pid' value='" + id + "'>" 
		+ "<input type='hidden' name='ptype' value='" + type + "'>" 
		
		+ "<input type='submit' class='btnbuy' value='Confirm' style='margin-top:10px;height: 40px;width: 80px;'></form>");
		pw.print("<small style='font-size:15px;color:#000;'>Note: Office hours are 9am to 6pm</small>");		
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		displayAppointment(request, response);
	}
}
