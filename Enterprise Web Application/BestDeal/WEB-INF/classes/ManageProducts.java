import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ManageProducts")

public class ManageProducts extends HttpServlet {

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		displayLogin(request, response, pw, true);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}


	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<h2 class='title meta' style='text-align:center; padding-top:20px;'><a style='font-size:32px; color:#333333;'>Manage Products</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; height:500px; margin:25px; margin-left: auto;margin-right: auto;'>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center;'><a href='AddProducts'>Add Product</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center;'><a href='UpdateProducts'>Update Product</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center;'><a href='DeleteProducts'>Delete Product</a></p>");		
		
		pw.print("</div></div>");
		utility.printHtml("Footer.html");
	}

}
