import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HealthInsuranceList")

public class HealthInsuranceList extends HttpServlet {

	/* HealthInsurances Page Displays all the HealthInsurances and their Information in HealthInsurance Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,HealthInsurance> allinsurances = new HashMap<String,HealthInsurance> ();

		try{
			allinsurances = MySqlDataStoreUtilities.getHealthInsurances();
		}
		catch(Exception e)
		{

		}

		HashMap<String, HealthInsurance> hm = new HashMap<String, HealthInsurance>();
		
		hm.putAll(allinsurances);
		
		/* Header, Left Navigation Bar are Printed.

		All the HealthInsurances and HealthInsurances information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #000 !important;'>HealthInsurance Lists</a>");
		pw.print("</h2><div class='entry'><table class='gridtable'>");
		int i = 1; int size = hm.size();
		for(Map.Entry<String, HealthInsurance> entry : hm.entrySet()){
			HealthInsurance healthinsurance = entry.getValue();
			String type = healthinsurance.getType();
			if (type.equalsIgnoreCase("HealthInsurance")) {
				if(i%2==1) pw.print("<tr>");
				String dname = healthinsurance.getName().substring(0, 1).toUpperCase() + healthinsurance.getName().substring(1);
				pw.print("<td class='vtd'><a href='Detail?ID=" + healthinsurance.getId() + "'><b>" + dname + "</b></a><br>" + healthinsurance.getSpeciality() + "<br>" + healthinsurance.getExperience() + " years experience overall<br>"
				+ healthinsurance.getStAddress() + ", " + healthinsurance.getCity() + ", " + healthinsurance.getState() + ", " + healthinsurance.getZipCode() + "<br>"
				+ healthinsurance.getFees() + "$ Insurance Fee");
				pw.print("<form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+healthinsurance.getName()+"'>"+
					"<input type='hidden' name='type' value='"+healthinsurance.getType()+"'>"+
					"<input type='hidden' name='maker' value='"+healthinsurance.getName()+"'>"+
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
