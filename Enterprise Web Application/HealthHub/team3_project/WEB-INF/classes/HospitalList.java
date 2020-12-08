import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HospitalList")

public class HospitalList extends HttpServlet {

	/* Hospitals Page Displays all the Hospitals and their Information in Hospital Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Hospital> allhospitals = new HashMap<String,Hospital> ();

		try{
			allhospitals = MySqlDataStoreUtilities.getHospitals();
		}
		catch(Exception e)
		{

		}

		String HospitalName = request.getParameter("name");
		HashMap<String, Hospital> hm = new HashMap<String, Hospital>();
		
		if(HospitalName==null)
		{
			hm.putAll(allhospitals);
		}
		/* Header, Left Navigation Bar are Printed.

		All the Hospitals and Hospitals information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #000 !important;'>Hospital Lists</a>");
		pw.print("</h2><div class='entry'><table class='gridtable'>");
		int i = 1; int size = hm.size();
		for(Map.Entry<String, Hospital> entry : hm.entrySet()){
			Hospital hospital = entry.getValue();
			String type = hospital.getType();
			if (type.equalsIgnoreCase("Hospital")) {
				if(i%2==1) pw.print("<tr>");
				String dname = hospital.getName().substring(0, 1).toUpperCase() + hospital.getName().substring(1);
				pw.print("<td class='vtd' style='width:60%;'><a href='Detail?ID=" + hospital.getId() + "'><b>" + dname + "</b></a><br>" + hospital.getSpeciality() + "<br>#" + hospital.getExperience() + " ranking in the state<br>"
				+ hospital.getStAddress() + ", " + hospital.getCity() + ", " + hospital.getState() + ", " + hospital.getZipCode() + "<br>"
				+ hospital.getFees() + "$ Consulatation Fee");
				pw.print("<form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+hospital.getName()+"'>"+
					"<input type='hidden' name='type' value='"+hospital.getType()+"'>"+
					"<input type='hidden' name='maker' value='"+hospital.getName()+"'>"+
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
