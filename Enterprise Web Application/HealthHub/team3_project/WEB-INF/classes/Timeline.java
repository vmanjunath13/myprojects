import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Timeline")
public class Timeline extends HttpServlet
{

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #000 !important;'>Trending Tweets on Twitter</a></h2>");
		pw.print("<h2 style='color:#006666 !important;font-size:20px;padding-left:20px;padding-top:30px;text-align:center;'>Search for trending tweets on Twitter based on Screen name or of any keyword</h2>");
		
		//display the text field to read the screenname or the query string regarding which the user wants to get the tweets abpw from Twitter
		pw.print("<form action='TrendingTwitterTweets'  method='get'>");
		pw.print("<input type='text' class='form-control' placeholder='Screen Name' name='screenname' style='width:200px; width: 200px;margin-left:300px;margin-top:20px;'>");
		pw.print("<input type='text' class='form-control' placeholder='Keyword' name='querystring' style='width:200px;width: 200px;margin-left:300px;margin-top:20px;'>");
		pw.print("<button class='btnbuy' type='submit' value='login' style='width:100px; margin-left:350px; margin-top:20px;'>Go</button>");
		pw.print("</form>");
		pw.print("</div></div></div>");
		utility.printHtml("Footer.html");
	}
}
