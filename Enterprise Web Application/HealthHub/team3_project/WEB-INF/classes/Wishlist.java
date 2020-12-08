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

@WebServlet("/Wishlist")

public class Wishlist extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayWishlist(request, response);
	}

	/* Display Wishlist Details of the Customer (Name and Usertype) */

	protected void displayWishlist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try
         {  
           response.setContentType("text/html");
			if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);	
				session.setAttribute("msg_src", "Wishlist"); 
				session.setAttribute("login_msg", "Please Login to add items to wishlist");
				response.sendRedirect("Login");
				return;
			}
			HttpSession session=request.getSession(); 	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Wishlist</a>");
			pw.print("</h2><div class='entry'>");
			User user = utility.getUser();
			String cid = user.getName();
			pw.print("<table class='gridtable' style='padding-top:30px;'><tr><td style='font-size:14px;width:25%;color:#006666;'> <strong>User Name:</strong> </td><td style='font-size:14px;color:#006666;width:25%;'><strong>" + cid + "</strong>");
			pw.print("</td></tr></table>");
			HashMap<String,String[]> wishlists = new HashMap<String, String[]>();
			try
			{
				wishlists = MySqlDataStoreUtilities.selectWishlist(cid);
			}
			catch(Exception e)
			{
			
			}	
			pw.print("<table class='gridtable'>");
			pw.print("<form method='get' action='ViewWishlist'>");	
			if(wishlists.size() != 0) {
				int i = 1; int size= wishlists.size();
				for(Map.Entry<String, String[]> entry : wishlists.entrySet())
				{
					if(i%2==1) pw.print("<tr>");
					String[] wishlist = entry.getValue();	
					pw.print("<td style='font-size:14px;text-align:left;color:#006666;width:50%;border:none !important;padding-right:70px !important;'><input type='radio' name='wishlistName' value='"+wishlist[1]+"' style='margin-right:9px;'>"
					+ "<a href='Detail?ID=" + wishlist[2] + "'><b>"  + wishlist[1] + "</b></a>"	
					+ "<input type='hidden' name='CId' value='"+wishlist[0]+"'>"
					+ "<br>" + wishlist[3] + "<br>" + wishlist[4] + "<br></td>");	
					if(i%2==0 || i == size) pw.print("</tr><br>");
					i++;
					
				}
				pw.print("</br><tr><input type='submit' name='cancel' value='Delete' class='btnbuy' style='margin-bottom:30px;'></tr>");
				
			}
			else
			{
				pw.print("<h4 style='color:red'>There are no saved items to see in wishlist.</h4>");
			}		
			pw.print("</table>");			
			pw.print("</div></div></div>");		
			utility.printHtml("Footer.html");	        	
		}
		catch(Exception e)
		{
		}		
	}
}
