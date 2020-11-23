import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;

@WebServlet("/InventoryGraph")

public class InventoryGraph extends HttpServlet {

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		displayLogin(request, response, pw, true);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}


	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");

		pw.print("<div id='content'><div class='post'><h2 class='title meta' style='text-align:center; padding-top:15px;'>");
		pw.print("<a style='font-size: 32px;'>Bar Chart of Products and its Count</a>");
		pw.print("</h2><div class='entry'>");

		try
		{
			Map<String,Product> pMap = MySqlDataStoreUtilities.getInventoryProducts();

			pw.print("<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>");
            pw.print("<script type='text/javascript'>");
    		pw.print("google.charts.load('current', {packages: ['corechart', 'bar']});");
    		pw.print("google.charts.setOnLoadCallback(drawBasic);");
    		pw.print("function drawBasic() {");
    		pw.print("var data = google.visualization.arrayToDataTable([");
    		pw.print("['Product Name', 'Total Items'],");

			Product pr = new Product();
            for(String prodID: pMap.keySet())
            {
               pr = (Product)pMap.get(prodID);
               String name = pr.getproductName();
               int quantity = pr.getcount();
               pw.print("[' " +name+ " ', "+quantity+ "],");
            }

            pw.print("]);");
            pw.print("var options = {");
    		pw.print("title: 'Product Names and the Total number of items available',");
    		pw.print("chartArea: {width: '55%', height: 1500},");
    		pw.print("fontSize: 12,");
    		pw.print("bar:{groupWidth: '70%'},");
    		pw.print("hAxis: {");
    		pw.print("title: 'Total number of products',");
    		pw.print("minValue: 0");
    		pw.print("},");
    		pw.print("vAxis: {");
    		pw.print("title: 'Product Name'");
    		pw.print("}");
    		pw.print("};");
    		pw.print("var chart = new google.visualization.BarChart(document.getElementById('chart_div'));");
    		pw.print("chart.draw(data, options);");
    		pw.print("}");
            pw.print("</script>");
            pw.print("<div id='chart_div' style='width:730px; height:1600px; padding-top:20px;'></div>");

		}
		catch(Exception e)
		{
	
		}

		pw.print("</div></div></div>");	
		utility.printHtml("Footer.html");
	}

}
