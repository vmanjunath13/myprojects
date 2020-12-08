import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProviderCrud")

public class ProviderCrud extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request, pw);
			String action = request.getParameter("button");
			
			String msg = "good";
			String PType="", PId="", PName="", PImage="", PExp="", PSpeciality="", PStAdrress="", PCity="", PState="", PZipcode="", PLat="", PLong="";
			Double PPrice=0.0;


			HashMap<String,Doctor> alldoctors = new HashMap<String,Doctor> ();
			HashMap<String,Hospital> allhospitals = new HashMap<String,Hospital> ();
			HashMap<String,HealthClub> allhealthclubs = new HashMap<String,HealthClub> ();
			HashMap<String,HealthInsurance> allhealthinsurances=new HashMap<String,HealthInsurance>();
			HashMap<String,Pharmacy> allpharmacys=new HashMap<String,Pharmacy>();
			
			PType = request.getParameter("PType");
			
			if (action.equals("Add Provider") || action.equals("Update Provider"))
			{	
				PType = request.getParameter("PType");
				PId = request.getParameter("PId");
				PName = request.getParameter("PName"); 
				PPrice = Double.parseDouble(request.getParameter("PPrice"));
				PImage = request.getParameter("PImage");
				PExp = request.getParameter("PExp");
				PSpeciality = request.getParameter("PSpeciality");
				PStAdrress = request.getParameter("PStAdrress");
				PCity = request.getParameter("PCity");
				PState = request.getParameter("PState");
				PZipcode = request.getParameter("PZipcode");
				PLat = request.getParameter("PLat");
				PLong = request.getParameter("PLong"); 
			}
			else
			{
				PId = request.getParameter("PId");
			}	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			
			if(action.equals("Add Provider"))
			{
			  	if(PType.equals("Doctor")){
					alldoctors = MySqlDataStoreUtilities.getDoctors();
					if(alldoctors.containsKey(PId)){
						msg = "Doctor already available";  
					}	  
				}
				else if(PType.equals("Hospital")){
					allhospitals = MySqlDataStoreUtilities.getHospitals();
					if(allhospitals.containsKey(PId)){
						msg = "Hospital already available";
					}
				}
				else if (PType.equals("Pharmacy")){
					allpharmacys = MySqlDataStoreUtilities.getPharmacys();
					if(allpharmacys.containsKey(PId)){
						msg = "Pharmacy already available";
					}
				}
				else if (PType.equals("Health Insurance")){
					allhealthinsurances = MySqlDataStoreUtilities.getHealthInsurances();
					if(allhealthinsurances.containsKey(PId)){
						msg = "Health Insurance already available";
					}
				}
				else if (PType.equals("Health Club")){
					allhealthclubs = MySqlDataStoreUtilities.getHealthClubs();
					if(allhealthclubs.containsKey(PId)){
						msg = "Health Club already available";
					}
				}	

				if (msg.equals("good"))
				{  
					try
					{
						msg = MySqlDataStoreUtilities.insertProvider(PId,PName,PStAdrress,PCity,PState,PZipcode,PSpeciality,PImage,PExp,PPrice,PType,PLat,PLong);
					}
					catch(Exception e)
					{ 
						msg = "Provider cannot be inserted";
					}
					msg = PType + " has been successfully added";
				}					
			}
			else if(action.equals("Update Provider"))
			{
				if(PType.equals("Doctor")){
					alldoctors = MySqlDataStoreUtilities.getDoctors();
					if(!alldoctors.containsKey(PId)){
						msg = "Doctor already available";  
					}	  
				}
				else if(PType.equals("Hospital")){
					allhospitals = MySqlDataStoreUtilities.getHospitals();
					if(!allhospitals.containsKey(PId)){
						msg = "Hospital already available";
					}
				}
				else if (PType.equals("Pharmacy")){
					allpharmacys = MySqlDataStoreUtilities.getPharmacys();
					if(!allpharmacys.containsKey(PId)){
						msg = "Pharmacy already available";
					}
				}
				else if (PType.equals("Health Insurance")){
					allhealthinsurances = MySqlDataStoreUtilities.getHealthInsurances();
					if(!allhealthinsurances.containsKey(PId)){
						msg = "Health Insurance already available";
					}
				}
				else if (PType.equals("Health Club")){
					allhealthclubs = MySqlDataStoreUtilities.getHealthClubs();
					if(!allhealthclubs.containsKey(PId)){
						msg = "Health Club already available";
					}
				}	
				
				if (msg.equals("good"))
				{		
					try
					{
						msg = MySqlDataStoreUtilities.updateProvider(PId,PName,PStAdrress,PCity,PState,PZipcode,PSpeciality,PImage,PExp,PPrice,PType,PLat,PLong);
					}
					catch(Exception e)
					{ 
						msg = "Provider cannot be updated";
					}
					msg = PType + " has been successfully updated";
				} 
			}
			else if(action.equals("Delete Provider"))
			{
				System.out.println("Inside deleteProvider" + PId);
				if (msg.equals("good"))
				{			
					try
					{  
						msg = MySqlDataStoreUtilities.deleteProvider(PId);
						System.out.println("Inside deleteProvider" + msg);
					}
					catch(Exception e)
					{ 
						msg = "Provider cannot be deleted";
					}
					msg = "Provider has been successfully deleted";
				}
				else
				{
					msg = "Provider not available";
				}
			}
			
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Operation</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<h4 style='color:#006666'>"+msg+"</h4>");
			pw.print("</div></div></div>");
			utility.printHtml("Footer.html");
			
	}
}