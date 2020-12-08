import java.io.IOException;
import java.io.PrintWriter;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {

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
				session.setAttribute("login_msg", "Please Login to view Review");
				response.sendRedirect("Login");
				return;
			}

			String PName=request.getParameter("name");
			HashMap<String, ArrayList<Review>> hm= MongoDBDataStoreUtilities.selectReview();
			String RRating = "";
			String RDate;
			
            utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
	
            pw.print("<div id='content'><div class='post'><h2 class='title meta' style='text-align:center; padding-top:15px;'>");
			pw.print("<a style='font-size: 24px;'>Customer Reviews</a>");
			pw.print("</h2><div class='entry'>");
			
			//if there are no reviews for product print no review else iterate over all the reviews using cursor and print the reviews in a table
			if(hm==null)
			{
				pw.println("<h2 style='color:#333333'>Mongo Db server is not up and running</h2>");
			}
			else
			{	
                if(!hm.containsKey(PName)){
					pw.println("<h2 style='color:#333333'>There are no reviews.</h2>");
				}	
				else
				{
					int count = 0;
					for (Review r : hm.get(PName)) 
				 	{	
				 		String temp="";
				 		String space = "";
				 		if(count == 0){
							pw.print("<p style='color:#333333; padding-top:20px; font-size:16px; margin-bottom:3px; line-height:0.5em;'><strong>Provider Name:</strong>"+"&nbsp&nbsp&nbsp&nbsp&nbsp<strong>"+r.getPName()+"</strong></p>");
							count++;
						}

						RRating = r.getRRating().toString();
						for(int i=0; i<Integer.parseInt(RRating); i++){
							temp += "<span>&#9733;</span>";
						}
						RDate = r.getRDate().toString();
						pw.print("<p style='color:#333333; font-size:18px; margin-bottom:3px; line-height:1.3em;'>"+r.getUserName()+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+temp+"</p>");
						pw.print("<p style='color:#333333; font-size:12px; margin-bottom:3px; line-height:1.8em;'>");
						pw.print(r.getUAge()+"&nbsp&nbsp&nbsp&nbsp&nbsp");
						pw.print(r.getUGender()+"&nbsp&nbsp&nbsp&nbsp&nbsp");
						pw.print(r.getUOccupation()+"&nbsp&nbsp&nbsp&nbsp&nbsp");
						pw.print(RDate);
						pw.print("</p>");

						pw.print("<p style='color:#262626; font-size:16px; margin-bottom:3px; line-height:1.3em;'>"+r.getRText()+"</p>");

						pw.print("<p style='color:#333333; font-size:12px; margin-bottom:3px; line-height:1.8em;'>");
						pw.print(r.getRName()+"&nbsp&nbsp&nbsp&nbsp&nbsp");
						pw.print(r.getRState()+"&nbsp&nbsp&nbsp&nbsp&nbsp");
						pw.print(r.getRCity()+"&nbsp&nbsp&nbsp&nbsp&nbsp");
						pw.print(r.getRzip()+"&nbsp&nbsp&nbsp&nbsp&nbsp");
						pw.print("</p><br>");
					}					
							
				}
			}	       
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
