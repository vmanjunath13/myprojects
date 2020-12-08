import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubmitUserReview")

public class SubmitUserReview extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    Utilities utility= new Utilities(request, pw);
		storeReview(request, response);
	}

	protected void storeReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {
            response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
			if(!utility.isLoggedin()){
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to add items to cart");
				response.sendRedirect("Login");
				return;
			}

            String PName=request.getParameter("PName");		
            String PType=request.getParameter("PType");
			String RName=request.getParameter("RName");
			String RFees=request.getParameter("RFees");
            String Rzip=request.getParameter("Rzip");
            String RCity=request.getParameter("RCity");
			String RState=request.getParameter("RState");
			String UAge = request.getParameter("UAge");
			String UGender = request.getParameter("UGender");
			String UOccupation = request.getParameter("UOccupation");
			String RRating = request.getParameter("RRating");
			String RDate = request.getParameter("RDate");
			String RText = request.getParameter("RText");
			String message = utility.storeReview(PName,PType,RName,RFees,Rzip,RCity,RState,UAge,UGender,UOccupation,RRating,RDate,RText);				     

			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<form name ='Cart' action='CheckOut' method='post'>");
	        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Review</a>");
			pw.print("</h2><div class='entry'>");
	      	if(message.equals("Successfull"))
	      	pw.print("<h2 style='color:#333333'>Review for &nbsp"+PName+" Stored </h2>");
	        else
			pw.print("<h2 style='color:#333333'>Mongo Db is not up and running </h2>");
	                
			pw.print("</div></div></div>");		
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
