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


@WebServlet("/AddWishlist")
public class AddWishlist extends HttpServlet {
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		HttpSession session = request.getSession(true);	
		
		User user = utility.getUser();
		String username = user.getName();
		String PName = request.getParameter("maker");
		String PId = request.getParameter("pid");
		String PType = request.getParameter("type");
		String PAddress = request.getParameter("address");
		
		String allwishlists="";
		String msg="";
		
		try
		{
			allwishlists = MySqlDataStoreUtilities.getWishlist(username, PName, PId, PType);
		}
		catch(Exception e)
		{

		}
			
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Wishlist Confirmation</a>");
		pw.print("</h2><div class='entry'>");
				
		if(allwishlists.toLowerCase().equals("yes"))
		{
			pw.print("<p style='font-size: 16px;color:#006666;padding-top:10px;font-weight:bold'>Already "+ PName + "is present in your wishlist!!!</p>");
		}
		else
		{
			msg = MySqlDataStoreUtilities.insertWishlist(username, PName, PId, PType, PAddress);
			pw.print("<p style='font-size: 16px;color:#006666;padding-top:10px;font-weight:bold'>" + PName + " is added to your wishlist!!!</p>");
		} 			
		pw.print("</form></div></div></div>");		
		pw.print("<br><form action='Wishlist' method='get'>");
		pw.print("<input type='submit' value='Go to Wishlist' class='btnbuy'>");
		utility.printHtml("Footer.html");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	}
 
}