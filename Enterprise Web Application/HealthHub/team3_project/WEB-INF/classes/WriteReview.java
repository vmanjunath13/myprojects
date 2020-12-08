import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/WriteReview")
	//once the user clicks writereview button from products page he will be directed
 	//to write review page where he can provide reqview for item rating reviewtext	
	
public class WriteReview extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    Utilities utility= new Utilities(request, pw);
		review(request, response);
	}
	
	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {       
        	response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
			if(!utility.isLoggedin()){
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to Write a Review");
				response.sendRedirect("Login");
				return;
			}

        String PName = request.getParameter("name");		
        String PType = request.getParameter("type");
        	      
		// on filling the form and clicking submit button user will be directed to submit review page
        utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='WriteReview' action='SubmitUserReview' method='post'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta' style='text-align:center;padding-top:15px;'>");
		pw.print("<a style='font-size:24px;'>Review this " + PType + "</a>");
		pw.print("</h2><div class='entry'>");
        pw.print("<table class='gridtable' style='padding-top:30px;'>");

		pw.print("<tr><td style='font-size:14px;width:25%;color:#006666;'> <strong>" + PType + " Name</strong> </td><td style='font-size:14px;color:#006666;'><strong>"
		+ PName + "</strong>");
		pw.print("<input type='hidden' name='PName' value='"+PName+"'>");
		pw.print("<input type='hidden' name='PType' value='"+PType+"'>");
		pw.print("</td></tr></table><br><br>");
		
		pw.print("<table><tr></tr><tr></tr><tr><td style='text-align:right;font-size:15px;color:#006666;'>Your Name*:</td>");
		pw.print("<td><input type='text' style='height:15px;margin-left:20px;' name='RName' required></td>");  
		pw.print("</tr>");
	
		pw.print("<tr>");
		pw.print("<td style='text-align:right;font-size:15px;color:#006666;'>Fees Charged*:</td>");
		pw.print("<td> <input type='text' style='height:15px;margin-left:20px;' name='RFees' required> </td>");
        pw.print("</tr>");

		pw.print("<tr>");
		pw.print("<td style='text-align:right;font-size:15px;color:#006666;'>Your Zip*:</td>");
		pw.print("<td> <input type='text' style='height:15px;margin-left:20px;' name='Rzip' required> </td>");
        pw.print("</tr>");		

		pw.print("<tr>");
		pw.print("<td style='text-align:right;font-size:15px;color:#006666;'>Your City*:</td>");
		pw.print("<td> <input type='text' style='height:15px;margin-left:20px;' name='RCity' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right;font-size:15px;color:#006666;'>Your State*:</td>");
		pw.print("<td> <input type='text' style='height:15px;margin-left:20px;' name='RState' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right;font-size:15px;color:#006666;'>Your Age*:</td>");
		pw.print("<td> <input type='text' style='height:15px;margin-left:20px;' name='UAge' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right;font-size:15px;color:#006666;'>Your Gender*:</td>");
		pw.print("<td> <input type='text' style='height:15px;margin-left:20px;' name='UGender' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right;font-size:15px;color:#006666;'>Your Occupation*:</td>");
		pw.print("<td> <input type='text' style='height:15px;margin-left:20px;' name='UOccupation' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right;font-size:15px;color:#006666;'>Review Rating*:</td>");
        pw.print("<td>");
		pw.print("<select name='RRating' style='height:15px;margin-left:20px;'>");
		pw.print("<option value='1' selected>1</option>");
		pw.print("<option value='2'>2</option>");
		pw.print("<option value='3'>3</option>");
		pw.print("<option value='4'>4</option>");
		pw.print("<option value='5'>5</option>");  
		pw.print("</td></tr>");						

		pw.print("<tr>");
		pw.print("<td style='text-align:right;font-size:15px;color:#006666;'>Review Date*:</td>");
		pw.print("<td><input type='date' style='height:15px;margin-left:20px;' name='RDate' required></td>");
        pw.print("</tr>");				

		pw.print("<tr>");
		pw.print("<td style='text-align:right;font-size:15px;color:#006666;'>Review Text*:</td>");
		pw.print("<td><textarea name='RText' style='margin-left:20px;' rows='4' cols='50'></textarea></td></tr>");
		pw.print("<tr><td></td><td colspan='2'><input type='submit' style='margin-top:20px;' class='btnbuy' name='SubmitReview' value='Submit Review'></td></tr></table>");

    	pw.print("</h2></div></div></div>");		
		utility.printHtml("Footer.html");                 	
        }
        catch(Exception e)
		{
        	System.out.println(e.getMessage());
		}  				
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();	
        }
}
