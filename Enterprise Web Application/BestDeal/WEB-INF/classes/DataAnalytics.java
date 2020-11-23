import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;
import javax.servlet.http.HttpSession;
@WebServlet("/DataAnalytics")

public class DataAnalytics extends HttpServlet {
	static DBCollection myReviews;
	/* Trending Page Displays all the Consoles and their Information in Game Speed*/

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
				
		
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View Reviews");
			response.sendRedirect("Login");
			return;
		}
		
						
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta' style='text-align:center;'>");
		pw.print("<a style='font-size: 24px;'>Data Analytics on Review</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<h3 style='font-size: 20px !important;color: #202078 !important;'>Select any number of options to find the required data</h3></br></br>");
		pw.print("<table id='bestseller'>");
		pw.print("<form method='post' action='FindReviews'>");
	
 		pw.print("<table id='bestseller'>");
		pw.print("<tr>");
		pw.print("<td> <input type='checkbox' name='queryCheckBox' value='ProductName'> Select </td>");
        pw.print("<td> Product Name: </td>");
        pw.print("<td>");
         	pw.print("<select name='ProductName'>");
		   	pw.print("<option value='ALL_PRODUCTS'>All Products</option>");
          	pw.print("<option value='ASUS 15.6 Touch-Screen'>ASUS 15.6 Touch-Screen</option>");
			pw.print("<option value='ASUS VivoBook S15'>ASUS VivoBook S15</option>");
			pw.print("<option value='Apple IPhone 11 Pro'>Apple IPhone 11 Pro</option>");
			pw.print("<option value='Apple IPhone 11'>Apple IPhone 11</option>");
			pw.print("<option value='Apple Macbook Air'>Apple Macbook Air</option>");
			pw.print("<option value='Apple Macbook Pro'>Apple Macbook Pro</option>");
			pw.print("<option value='Apple Watch Series 3'>Apple Watch Series 3</option>");
			pw.print("<option value='Apple Watch Series 4'>Apple Watch Series 4</option>");
			pw.print("<option value='Bose Acoustics 5 Series'>Bose Acoustics 5 Series</option>");
			pw.print("<option value='Bose Quick Comfort'>Bose Quick Comfort</option>");
			pw.print("<option value='Bose Sound Link'>Bose Sound Link</option>");
			pw.print("<option value='Bose Wave System IV'>Bose Wave System IV</option>");
			pw.print("<option value='Dell XPS 13'>Dell XPS 13</option>");
			pw.print("<option value='Dell XPS 15'>Dell XPS 15</option>");
			pw.print("<option value='Echo Plus 2nd Gen'>Echo Plus 2nd Gen</option>");
			pw.print("<option value='Echo Studio'>Echo Studio</option>");
			pw.print("<option value='Echodot 3rd Gen'>Echodot 3rd Gen</option>");
			pw.print("<option value='FitBark GPS'>FitBark GPS</option>");
			pw.print("<option value='Fitbit Alta HR'>Fitbit Alta HR</option>");
			pw.print("<option value='Fitbit Inspire HR'>Fitbit Inspire HR</option>");
			pw.print("<option value='Fossil Sport'>Fossil Sport</option>");
			pw.print("<option value='Full Motion TV Wall Mount'>Full Motion TV Wall Mount</option>");
			pw.print("<option value='Google Home Max'>Google Home Max</option>");
			pw.print("<option value='Google Home Mini'>Google Home Mini</option>");
			pw.print("<option value='Google Pixel 2'>Google Pixel 2</option>");
			pw.print("<option value='Google Pixel XL'>Google Pixel XL</option>");
			pw.print("<option value='HP Envy X360'>HP Envy X360</option>");
			pw.print("<option value='HP Spectre X360'>HP Spectre X360</option>");
			pw.print("<option value='HTC VIVE Cosmos'>HTC VIVE Cosmos</option>");
			pw.print("<option value='HTC VIVE'>HTC VIVE</option>");
			pw.print("<option value='Home Pod'>Home Pod</option>");
			pw.print("<option value='Home Speaker 300'>Home Speaker 300</option>");
			pw.print("<option value='Homido V2 Virtual reality'>Homido V2 Virtual reality</option>");
			pw.print("<option value='Honor Magic Watch 2'>Honor Magic Watch 2</option>");
			pw.print("<option value='Insignia 2.0-Channel Mini'>Insignia 2.0-Channel Mini</option>");
			pw.print("<option value='Insignia 2.1-Channel 80W'>Insignia 2.1-Channel 80W</option>");
			pw.print("<option value='Insignia 55 4K UHD TV'>Insignia 55 4K UHD TV</option>");
			pw.print("<option value='Insignia 70 4K UHD TV'>Insignia 70 4K UHD TV</option>");
			pw.print("<option value='JBL Link'>JBL Link</option>");
			pw.print("<option value='Jabra Elite 45h'>Jabra Elite 45h</option>");
			pw.print("<option value='Jiobit Pet Tracker'>Jiobit Pet Tracker</option>");
			pw.print("<option value='LG C9 Series'>LG C9 Series</option>");
			pw.print("<option value='LG K50'>LG K50</option>");
			pw.print("<option value='LG K50S'>LG K50S</option>");
			pw.print("<option value='LG Nano 8 Series'>LG Nano 8 Series</option>");
			pw.print("<option value='LG b9 Series'>LG b9 Series</option>");
			pw.print("<option value='Lenovo ThinkPad L13'>Lenovo ThinkPad L13</option>");
			pw.print("<option value='Lenovo Yoga C940'>Lenovo Yoga C940</option>");
			pw.print("<option value='Letsfit'>Letsfit</option>");
			pw.print("<option value='Lintelek Activity Tracker HR'>Lintelek Activity Tracker HR</option>");
			pw.print("<option value='Lintelek Tracker HR'>Lintelek Tracker HR</option>");
			pw.print("<option value='Logitech Z313'>Logitech Z313</option>");
			pw.print("<option value='Logitech Z906'>Logitech Z906</option>");
			pw.print("<option value='Moto G Power'>Moto G Power</option>");
			pw.print("<option value='Moto G Stylus'>Moto G Stylus</option>");
			pw.print("<option value='Oculus Quest 2'>Oculus Quest 2</option>");
			pw.print("<option value='Oculus Rift S'>Oculus Rift S</option>");
			pw.print("<option value='PETFON GPS Pet Tracker'>PETFON GPS Pet Tracker</option>");
			pw.print("<option value='Plantronics BackBeat Go 810'>Plantronics BackBeat Go 810</option>");
			pw.print("<option value='Polk Audio - Blackstone TL1600'>Polk Audio - Blackstone TL1600</option>");
			pw.print("<option value='Remote control holder'>Remote control holder</option>");
			pw.print("<option value='Samsung 7 Series'>Samsung 7 Series</option>");
			pw.print("<option value='Samsung 8 Series'>Samsung 8 Series</option>");
			pw.print("<option value='Samsung Galaxy S10 Plus'>Samsung Galaxy S10 Plus</option>");
			pw.print("<option value='Samsung Galaxy S9'>Samsung Galaxy S9</option>");
			pw.print("<option value='Samsung Galaxy Watch Active'>Samsung Galaxy Watch Active</option>");
			pw.print("<option value='Samsung Gear S3'>Samsung Gear S3</option>");
			pw.print("<option value='Samsung Gear VR'>Samsung Gear VR</option>");
			pw.print("<option value='Samsung QLED'>Samsung QLED</option>");
			pw.print("<option value='Sennheiser HD 4.50'>Sennheiser HD 4.50</option>");
			pw.print("<option value='Sennheiser HD Pro'>Sennheiser HD Pro</option>");
			pw.print("<option value='Sony 55 Inch 4K'>Sony 55 Inch 4K</option>");
			pw.print("<option value='Sony 70 Inch 4K'>Sony 70 Inch 4K</option>");
			pw.print("<option value='Sony 75 Inch 4K'>Sony 75 Inch 4K</option>");
			pw.print("<option value='Sony Bravia Remote Case'>Sony Bravia Remote Case</option>");
			pw.print("<option value='Sony Compact Stereo'>Sony Compact Stereo</option>");
			pw.print("<option value='Sony Micro Hi-Fi Stereo'>Sony Micro Hi-Fi Stereo</option>");
			pw.print("<option value='Sony PlayStation VR'>Sony PlayStation VR</option>");
			pw.print("<option value='Sony Smartwatch 2'>Sony Smartwatch 2</option>");
			pw.print("<option value='Sony Smartwatch 3'>Sony Smartwatch 3</option>");
			pw.print("<option value='Sony WH-CH700N'>Sony WH-CH700N</option>");
			pw.print("<option value='Sony WH1000MX3'>Sony WH1000MX3</option>");
			pw.print("<option value='Tagg GPS'>Tagg GPS</option>");
			pw.print("<option value='Teslasz HR'>Teslasz HR</option>");
			pw.print("<option value='Teslasz I5 Plus'>Teslasz I5 Plus</option>");
			pw.print("<option value='Universal TV Stand Base'>Universal TV Stand Base</option>");
			pw.print("<option value='VIZIO 55'>VIZIO 55</option>");
			pw.print("<option value='VIZIO 65'>VIZIO 65</option>");
			pw.print("<option value='Whistle GO'>Whistle GO</option>");
			pw.print("<option value='YAMAY 2020 Ver'>YAMAY 2020 Ver</option>");
           

        pw.print("</td>");
		pw.print("<tr>");
		pw.print("<td> <input type='checkbox' name='queryCheckBox' value='ProductPrice'> Select </td>");
        pw.print("<td> Product Price: </td>");
        pw.print("<td>");
        pw.print("<input type='number' name='ProductPrice' value = '0' size=10/></td>");
		pw.print("<td>");
		pw.print("<input type='radio' name='comparePrice' value='EQUALS_TO' checked> Equals <br>");
		pw.print("<input type='radio' name='comparePrice' value='GREATER_THAN'> Greater Than <br>");
		pw.print("<input type='radio' name='comparePrice' value='LESS_THAN'> Less Than");
		pw.print("</td></tr>");				
                            
  			
		pw.print("<tr><td> <input type='checkbox' name='queryCheckBox' value='ReviewRating'> Select </td>");
        pw.print("<td> Review Rating: </td>");
        pw.print("<td>");
        pw.print("<select name='ReviewRating'>");
        pw.print("<option value='1' selected>1</option>");
        pw.print("<option value='2'>2</option>");
        pw.print("<option value='3'>3</option>");
        pw.print("<option value='4'>4</option>");
        pw.print("<option value='5'>5</option>");
        pw.print("</td>");
		pw.print("<td>");
		pw.print("<input type='radio' name='compareRating' value='EQUALS_TO' checked> Equals <br>");
		pw.print("<input type='radio' name='compareRating' value='GREATER_THAN'> Greater Than"); 
		pw.print("</td></tr>");
			
		pw.print("<tr>");
		pw.print("<td> <input type='checkbox' name='queryCheckBox' value='RetailerCity'> Select </td>");
        pw.print("<td> Retailer City: </td>");
        pw.print("<td>");
        pw.print("<input type='text' name='RetailerCity' /> </td>");						
        pw.print("</tr>");

		pw.print("<tr>");
		pw.print("<td> <input type='checkbox' name='queryCheckBox' value='RetailerZIP'> Select </td>");
        pw.print(" <td> Retailer Zip code: </td>");
        pw.print(" <td>");
        pw.print("<input type='text' name='RetailerZIP' /> </td>");
		pw.print("</tr>");

		pw.print("<tr><td>");	
		pw.print("<input type='checkbox' name='extraSettings' value='GROUP_BY'> Group By");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<select name='groupByDropdown'>");
        pw.print("<option value='GROUP_BY_CITY' selected>City</option>");
        pw.print("<option value='GROUP_BY_PRODUCT'>Product Name</option>");
        pw.print("<option value='GROUP_BY_ZIP'>Zip Code</option>");                                        
        pw.print("</td><td>");
		pw.print("<input type='radio' name='dataGroupBy' value='Count' checked> Count <br>");
		pw.print("<input type='radio' name='dataGroupBy' value='Detail'> Detail <br>");
		pw.print("</td></tr>");	
		
		pw.print("<tr>");
        pw.print("<td colspan = '4'; style='text-align:center;'> <input type='submit' value='Find Data' class='btnbuy' /> </td>");
        pw.print("</tr>");
							
							
		pw.print("</table>");	
		pw.print("</div></div></div>");			
		utility.printHtml("Footer.html");
			
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
