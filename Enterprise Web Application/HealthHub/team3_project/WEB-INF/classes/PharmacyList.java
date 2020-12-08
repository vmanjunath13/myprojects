import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PharmacyList")

public class PharmacyList extends HttpServlet {

	/* Pharmacys Page Displays all the Pharmacys and their Information in Pharmacy Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Pharmacy> allpharmacys = new HashMap<String,Pharmacy> ();

		try{
			allpharmacys = MySqlDataStoreUtilities.getPharmacys();
		}
		catch(Exception e)
		{

		}

		HashMap<String, Pharmacy> hm = new HashMap<String, Pharmacy>();
		
		hm.putAll(allpharmacys);
		
		/* Header, Left Navigation Bar are Printed.

		All the Pharmacys and Pharmacys information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #000 !important;'>Pharmacy Lists</a>");
		pw.print("</h2><div class='entry'><table class='gridtable'>");
		int i = 1; int size = hm.size();
		for(Map.Entry<String, Pharmacy> entry : hm.entrySet()){
			Pharmacy pharmacy = entry.getValue();
			String type = pharmacy.getType();
			if (type.equalsIgnoreCase("Pharmacy")) {
				if(i%2==1) pw.print("<tr>");
				String dname = pharmacy.getName().substring(0, 1).toUpperCase() + pharmacy.getName().substring(1);
				pw.print("<td class='vtd'><a href='Detail?ID=" + pharmacy.getId() + "'><b>" + dname + "</b></a><br>" + pharmacy.getSpeciality() + "<br>" + pharmacy.getExperience() + " years experience overall<br>"
				+ pharmacy.getStAddress() + ", " + pharmacy.getCity() + ", " + pharmacy.getState() + ", " + pharmacy.getZipCode());
				pw.print("<form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+pharmacy.getName()+"'>"+
					"<input type='hidden' name='type' value='"+pharmacy.getType()+"'>"+
					"<input type='hidden' name='maker' value='"+pharmacy.getName()+"'>"+
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
