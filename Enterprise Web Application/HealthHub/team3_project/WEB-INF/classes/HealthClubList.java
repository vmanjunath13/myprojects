import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HealthClubList")

public class HealthClubList extends HttpServlet {

	/* HealthClubs Page Displays all the HealthClubs and their Information in HealthClub Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,HealthClub> allclubs = new HashMap<String,HealthClub> ();

		try{
			allclubs = MySqlDataStoreUtilities.getHealthClubs();
		}
		catch(Exception e)
		{

		}
		
		HashMap<String, HealthClub> hm = new HashMap<String, HealthClub>();
		
		hm.putAll(allclubs);
		
		/* Header, Left Navigation Bar are Printed.

		All the HealthClubs and HealthClubs information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #000 !important;'>HealthClub Lists</a>");
		pw.print("</h2><div class='entry'><table class='gridtable'>");
		int i = 1; int size = hm.size();
		for(Map.Entry<String, HealthClub> entry : hm.entrySet()){
			HealthClub healthclub = entry.getValue();
			String type = healthclub.getType();
			if (type.equalsIgnoreCase("HealthClub")) {
				if(i%2==1) pw.print("<tr>");
				String dname = healthclub.getName().substring(0, 1).toUpperCase() + healthclub.getName().substring(1);
				pw.print("<td class='vtd'><a href='Detail?ID=" + healthclub.getId() + "'><b>" + dname + "</b></a><br>" + healthclub.getSpeciality() + "<br>" + healthclub.getExperience() + " years experience overall<br>"
				+ healthclub.getStAddress() + ", " + healthclub.getCity() + ", " + healthclub.getState() + ", " + healthclub.getZipCode() + "<br>"
				+ healthclub.getFees() + "$ Enrollment Fee");
				pw.print("<form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+healthclub.getName()+"'>"+
					"<input type='hidden' name='type' value='"+healthclub.getType()+"'>"+
					"<input type='hidden' name='maker' value='"+healthclub.getName()+"'>"+
					"<input type='submit' value='View Review' class='vbtn'></form></td>");
				if(i%2==0 || i == size) pw.print("</tr>");
				i++;
			}
			
		}
		pw.print("</table>");			
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
		
	}

}
