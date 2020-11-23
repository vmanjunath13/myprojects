import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;

@WebServlet("/Payment")

public class Payment extends HttpServlet {
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();		
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			String strDate = formatter.format(date);
			try {
				c.setTime(formatter.parse(strDate));
			} catch(ParseException e) {
				e.printStackTrace();
			}
			
			c.add(Calendar.DAY_OF_MONTH, 14);
			String finalDate = formatter.format(c.getTime());
			Utilities utility = new Utilities(request, pw);
			if(!utility.isLoggedin())
			{	
				HttpSession session = request.getSession(true);
				session.setAttribute("msg_src", "Payment");
				session.setAttribute("login_msg", "Please Login to Pay");
				response.sendRedirect("Login");
				return;
			}
			
			String storeAddress=request.getParameter("storeAddress");
			String homeAddress=request.getParameter("homeAddress");
			
			System.out.print(storeAddress);
			System.out.print(homeAddress);
			
			if(!storeAddress.isEmpty() || !homeAddress.isEmpty())
			{
				Random rand = new Random(); 
				int confirmationId = rand.nextInt(100);
				int orderId=utility.getOrderPaymentSize();
				
				utility.printHtml("Header.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
				pw.print("<a style='font-size: 24px;'>Order Confirmation</a>");
				pw.print("</h2><div class='entry'>");		
				pw.print("<br><br><p style='font-size:16px; color:#333333; text-align:center;'>Thank You for shopping with us!!!");
				pw.print("<br>Your Order Id#: "+(orderId));
				pw.print("<br>Your Order Delivery Confirmation#: "+(confirmationId) +"</p><br><br>");
				pw.print("<img src='images/site/thumbsup.jpg' alt='ThumpsUp' width='250' height='250' style='display: block; margin-left: auto;margin-right: auto;'>");
				
				if(storeAddress.substring(0,8).equals("BestDeal")) {
					pw.print("<p style='font-size:16px; color:#333333; text-align:center; padding-top:25px;'> Your order will be ready to be picked up within two hours from your selected address.</p>");
					pw.print("<p style='font-size:16px; color:#333333; text-align:center; padding-top:5px;'> Address: " + storeAddress + "</p>");
					pw.print("<p style='font-size:16px; color:#333333; text-align:center;'>We will hold your order upto two weeks from today.</p>");
				} else {		
					pw.print("<p style='font-size:16px; color:#333333; text-align:center; padding-top:40px;'> Your estimated delivery date is <strong>"+(finalDate)+"</strong></p>");
				}
				pw.print("<p style='font-size:16px; color:#333333; text-align:center; padding-top:30px;'>You can cancel your order within 5 days only if at all you change your mind.</p>");
				pw.print("</div></div></div>");		
				utility.printHtml("Footer.html");
			}else
			{
				utility.printHtml("Header.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
				pw.print("<a style='font-size: 24px;'>Order</a>");
				pw.print("</h2><div class='entry'>");
				pw.print("<h4 style='color:red'>Please enter all required details</h4>");
				pw.print("</h2></div></div></div>");		
				utility.printHtml("Footer.html");
			}	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		
	}
}
