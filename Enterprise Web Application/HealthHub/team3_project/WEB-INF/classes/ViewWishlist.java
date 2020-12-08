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

@WebServlet("/ViewWishlist")

public class ViewWishlist extends HttpServlet {
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);	
			session.setAttribute("msg_src", "ViewWishlist");
			session.setAttribute("login_msg", "Please Login to View your Orders");
			response.sendRedirect("Login");
			return;
		}
		String username=utility.username();
		User user=utility.getUser();
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='ViewWishlist' action='ViewWishlist' method='get'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Wishlist</a>");
		pw.print("</h2><div class='entry'>");

		if(request.getParameter("cancel").equals("Delete"))
		{
			if(request.getParameter("wishlistName") != null)
			{
				String wishlistName = request.getParameter("wishlistName");
				String CId = request.getParameter("CId");
				HashMap<String,String[]> wishlists = new HashMap<String, String[]>();
				try
				{
					wishlists = MySqlDataStoreUtilities.selectWishlist(CId);
				}
				catch(Exception e)
				{
				
				}
				String msg = "";
				for(Map.Entry<String, String[]> entry : wishlists.entrySet())
				{	
					String[] wishlist = entry.getValue();
					int flag = 0;
					if(wishlist[1].equals(wishlistName) && wishlist[0].equals(CId))
					{
						flag = 1;
						msg = MySqlDataStoreUtilities.deleteWishlist(CId, wishlistName);
						if(msg.equals("Successfully deleted wishlist")) 
						{
							pw.print("<h4 style='color:red'>Your Selected Wishlist is Deleted</h4>");
						}
					}
					if(flag == 1){
						break;
					}
				}
			}
			else
			{
				pw.print("<h4 style='color:red'>Please select any wishlist</h4>");
			}
		}
		pw.print("</form></div></div></div>");		
		pw.print("<br><form action='Wishlist' method='get'>");
		pw.print("<input type='submit' value='Go to Wishlist' class='btnbuy'>");
		pw.print("</form>");
		utility.printHtml("Footer.html");
	}

}


