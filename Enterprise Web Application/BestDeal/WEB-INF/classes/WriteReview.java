

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

        String PName=request.getParameter("name");		
        String PCategory=request.getParameter("type");
        String MName=request.getParameter("maker");
 		String PPrice=request.getParameter("price");
			      
      // on filling the form and clicking submit button user will be directed to submit review page
        utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='WriteReview' action='SubmitUserReview' method='post'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta' style='text-align:center; padding-top:15px;'>");
		pw.print("<a style='font-size: 24px;'>Review this Product</a>");
		pw.print("</h2><div class='entry'>");
        pw.print("<table class='gridtable' style='padding-top:30px;'>");

		pw.print("<tr><td style='font-size:14px; width:25%;'> <strong>Product Name</strong> </td><td style='font-size:14px;'><strong>");
		pw.print(":"+PName+"</strong>");
		pw.print("<input type='hidden' name='PName' value='"+PName+"'>");
		pw.print("</td></tr>");

	    pw.print("<tr><td style='font-size:14px; width:25%;'> <strong>Product Category</strong></td><td style='font-size:14px;'><strong>");
        pw.print(":"+PCategory+"</strong>");
	    pw.print("<input type='hidden' name='PCategory' value='"+PCategory+"'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='font-size:14px; width:25%;'> <strong>Product Price</strong></td><td style='font-size:14px;'><strong>");
        pw.print(":"+PPrice+"</strong>");
	    pw.print("<input type='hidden' name='PPrice' value='"+PPrice+"'>");
		pw.print("</td></tr>");		
        
        pw.print("<tr><td style='font-size:14px; width:25%;'><strong>Manufacturer Name</strong></td><td style='font-size:14px;'><strong>");
        pw.print(":"+MName+"</strong>");
		pw.print("<input type='hidden' name='MName' value='"+MName+"'>");
        pw.print("</td></tr><table>");
		
		pw.print("<table><tr></tr><tr></tr><tr><td style='text-align:right; font-size:13px;'>Retailer Name*:</td>");
		pw.print("<td><input type='text' style='height:10px;' name='RName' required></td>");  
		pw.print("</tr>");
	
		pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>Retailer Zip*:</td>");
		pw.print("<td> <input type='text' style='height:10px;' name='Rzip' required> </td>");
        pw.print("</tr>");		

		pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>Retailer City*:</td>");
		pw.print("<td> <input type='text' style='height:10px;' name='RCity' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>Retailer State*:</td>");
		pw.print("<td> <input type='text' style='height:10px;' name='RState' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>Product On Sale*:</td>");
		pw.print("<td> <input type='text' style='height:10px;' name='ProductOnSale' required> </td>");
        pw.print("</tr>");	

        pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>Manufacturer Rebate*:</td>");
		pw.print("<td> <input type='text' style='height:10px;' name='MRebate' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>User ID*:</td>");
		pw.print("<td> <input type='text' style='height:10px;' name='UID' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>User Age*:</td>");
		pw.print("<td> <input type='text' style='height:10px;' name='UAge' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>User Gender*:</td>");
		pw.print("<td> <input type='text' style='height:10px;' name='UGender' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>User Occupation*:</td>");
		pw.print("<td> <input type='text' style='height:10px;' name='UOccupation' required> </td>");
        pw.print("</tr>");

        pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>Review Rating*:</td>");
        pw.print("<td>");
		pw.print("<select name='RRating'>");
		pw.print("<option value='1' selected>1</option>");
		pw.print("<option value='2'>2</option>");
		pw.print("<option value='3'>3</option>");
		pw.print("<option value='4'>4</option>");
		pw.print("<option value='5'>5</option>");  
		pw.print("</td></tr>");						

		pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>Review Date*:</td>");
		pw.print("<td><input type='date' style='height:10px;' name='RDate' required></td>");
        pw.print("</tr>");				

		pw.print("<tr>");
		pw.print("<td style='text-align:right; font-size:13px;'>Review Text*:</td>");
		pw.print("<td><textarea name='RText' rows='4' cols='50'></textarea></td></tr>");
		pw.print("<tr><td></td><td colspan='2'><input type='submit' class='btnbuy' name='SubmitReview' value='SubmitReview'></td></tr></table>");

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
