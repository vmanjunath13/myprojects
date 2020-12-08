import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Trending")

public class Trending extends HttpServlet {

	ArrayList <Mostreviewed> mostreviewed = new ArrayList <Mostreviewed> ();
    ArrayList <Mostreviewedzip> mostreviewedzip = new ArrayList <Mostreviewedzip> ();
	ArrayList <Bestrating> bestrated = new ArrayList <Bestrating> ();

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		mostreviewed = MongoDBDataStoreUtilities.mostreviewedProviders();
		mostreviewedzip = MongoDBDataStoreUtilities.mostreviewedZip();
		bestrated = MongoDBDataStoreUtilities.topProviders();

		

		String name = "Trending";
		

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #000 !important;'>Trending</a></h2></div></div>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size:20px;color:#006666;'><strong>Best Providers</strong></a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		Iterator itr2 = bestrated.iterator();
        while(itr2.hasNext()) {
        Bestrating best = (Bestrating)itr2.next();
 		pw.print("<tr>");
		pw.print("<td border: 1px style='font-size:14px;padding-left:230px;width:50%;'>");
		pw.print(best.getProductname());
		pw.print("</td>");
		pw.print("<td border: 1px style='width:25%;font-size:14px'>");
		
		String cutString = "";
		int length = best.getRating().length();
		if (length <= 3){
			cutString = best.getRating().substring(0, 3);
		}
		else{
			cutString = best.getRating().substring(0, 4);
		}
		pw.print(cutString);
		pw.print("</td>");
		pw.print("</tr>");
        }
        pw.print("<tr><td></td><td></td></tr>");
		pw.print("</table></div></div></div>");	
		
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 20px;color:#006666;'><strong>Most Reviewed Providers by Zipcode</strong></a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		Iterator itr1 = mostreviewedzip.iterator();
        while(itr1.hasNext()) {
        Mostreviewedzip mostzip = (Mostreviewedzip)itr1.next();
 		pw.print("<tr>");
		pw.println("<td border: 1px style='font-size:14px;padding-left:230px;width:50%;'>");
		
		pw.println(mostzip.getZipcode());
		pw.println("</td>");
		pw.println("<td border: 1px style='width:25%;font-size:14px'>");
		pw.println(mostzip.getCount());
		pw.println("</td>");
		pw.println("</tr>");
        }
        pw.print("<tr><td></td><td></td></tr>");
		pw.print("</table></div></div></div>");	
		
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 20px;color:#006666;'><strong>Most Reviewed Providers</strong></a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		
        Iterator itr = mostreviewed.iterator();
        while(itr.hasNext()) {
        Mostreviewed most = (Mostreviewed)itr.next();
 		pw.println("<tr>");
		pw.println("<td border: 1px style='font-size:14px;padding-left:230px;width:50%;'>");
		pw.println(most.getProductname());
		pw.println("</td>");
		pw.println("<td border: 1px style='width:25%;style=font-size:14px'>");
		pw.println(most.getCount());
		pw.println("</td>");
		pw.println("</tr>");
        }
        pw.print("<tr><td></td><td></td></tr>");
		pw.print("</table></div></div></div>");
		
	//	pw.print("</table></div></div></div>");	
		utility.printHtml("Footer.html");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
