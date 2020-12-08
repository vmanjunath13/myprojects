import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;
import java.util.Random;


@WebServlet("/Booking")
public class Booking extends HttpServlet {
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		HttpSession session = request.getSession(true);	
		
		User user = utility.getUser();
		String FullName = request.getParameter("custname");
		String Date = request.getParameter("aptdate");
		String Time = request.getParameter("apttime");
		String PName = request.getParameter("pname");
		String PId = request.getParameter("pid");
		String PType = request.getParameter("ptype");
		int CrnId;
		
		String allbookings="";
		String msg="";
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String tdate = formatter.format(date);
		tdate = tdate.substring(0,4) + tdate.substring(5,7) + tdate.substring(8);
		int td = Integer.parseInt(tdate);
		String Date1 = Date.substring(0,4) + Date.substring(5,7) + Date.substring(8);
		int dt = Integer.parseInt(Date1);
		
		try
		{
			allbookings = MySqlDataStoreUtilities.getBooking(FullName, Date, Time, PId);
		}
		catch(Exception e)
		{

		}
			
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Booking Confirmation</a>");
		pw.print("</h2><div class='entry'>");
				
		if(allbookings.toLowerCase().equals("yes"))
		{
			pw.print("<p style='font-size: 16px;color:#006666;padding-top:10px;font-weight:bold'>Already booking is done for this time. Please select different date and time!!!</p>");
			pw.print("<form action='Appointment' method='post'><input type='submit' class='btnbuy' value='Go Back' style='margin-top:10px;height: 40px;width: 80px;'></p></form>");
		}
		else
		{
			Random rand = new Random(); 
			CrnId = rand.nextInt(1000);
			String Crn = Integer.toString(CrnId);
			String userid = user.getName();
			if(dt > td) {
				msg = MySqlDataStoreUtilities.insertBooking(FullName, Date, Time, PName, PId, Crn, userid, PType);
			//System.out.println(msg);
			}
			pw.print("<p style='font-size: 16px;color:#006666;padding-top:10px;font-weight:bold'>Your Booking is confirmed!!!</p>");
			pw.print("<p style='font-size: 16px;color:#006666;padding-top:5px;font-weight:bold'>Confirmation ID: " + Crn +"</p>");
			pw.print("<p style='font-size: 16px;color:#006666;padding-top:5px;font-weight:bold'>Appointment Date: " + Date +"</p>");
			pw.print("<p style='font-size: 16px;color:#006666;padding-top:5px;font-weight:bold'>Appointment Time: " + Time +"</p>");
		} 			
		pw.print("</div></div></div>");				
		utility.printHtml("Footer.html");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	}
 
}