import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DoctorList")

public class DoctorList extends HttpServlet {

	/* Doctors Page Displays all the Doctors and their Information in Doctor Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Doctor> alldoctors = new HashMap<String,Doctor> ();

		try{
			alldoctors = MySqlDataStoreUtilities.getDoctors();
		}
		catch(Exception e)
		{

		}

		/* Checks the Doctors type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String DoctorName = request.getParameter("name");
		HashMap<String, Doctor> hm = new HashMap<String, Doctor>();
		
		if(DoctorName==null)
		{
			hm.putAll(alldoctors);
			name = "";
		}
		/* Header, Left Navigation Bar are Printed.

		All the Doctors and Doctors information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #000 !important;'>Doctor Lists</a>");
		pw.print("</h2><div class='entry'><table class='gridtable'>");
		int i = 1; int size = hm.size();
		for(Map.Entry<String, Doctor> entry : hm.entrySet()){
			Doctor doctor = entry.getValue();
			String type = doctor.getType();
			if (type.equalsIgnoreCase("Doctor")) {
				if(i%2==1) pw.print("<tr>");
				String dname = doctor.getName().substring(0, 1).toUpperCase() + doctor.getName().substring(1);
				pw.print("<td class='vtd'><a href='Detail?ID=" + doctor.getId() + "'><b>Dr. " + dname + "</b></a><br>" + doctor.getSpeciality() + "<br>" + doctor.getExperience() + " years experience overall<br>"
				+ doctor.getStAddress() + ", " + doctor.getCity() + ", " + doctor.getState() + ", " + doctor.getZipCode() + "<br>"
				+ doctor.getFees() + "$ Consulatation Fee");
				pw.print("<form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+doctor.getName()+"'>"+
					"<input type='hidden' name='type' value='"+doctor.getType()+"'>"+
					"<input type='hidden' name='maker' value='"+doctor.getName()+"'>"+
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
