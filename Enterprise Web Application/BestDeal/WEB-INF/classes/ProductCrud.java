import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProductCrud")

public class ProductCrud extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request, pw);
			String action = request.getParameter("button");
			
			String msg = "good";
			String ProductType= "",Id="",productName="",productImage="",productManufacturer="",productCondition="",prod="";
			double productPrice=0.0,productDiscount = 0.0,rebateAmount=0.0;
			Integer count=0;

			HashMap<String,Tv> alltvs = new HashMap<String,Tv> ();
			HashMap<String,SoundSystem> allsoundsystems = new HashMap<String,SoundSystem> ();
			HashMap<String,Laptop> alllaptops = new HashMap<String,Laptop> ();
			HashMap<String,Phone> allphones=new HashMap<String,Phone>();
			HashMap<String,VoiceAssistant> allvoiceassistants=new HashMap<String,VoiceAssistant>();
			HashMap<String,FitnessWatch> allfitnesswatches=new HashMap<String,FitnessWatch>();
			HashMap<String,SmartWatch> allsmartwatches=new HashMap<String,SmartWatch>();
			HashMap<String,HeadPhone> allheadphones=new HashMap<String,HeadPhone>();
			HashMap<String,VirtualReality> allvirtualrealitys=new HashMap<String,VirtualReality>();
			HashMap<String,PetTracker> allpettrackers=new HashMap<String,PetTracker>();
		
			if (action.equals("Add Product") || action.equals("Update Product"))
			{	
				 ProductType = request.getParameter("ProductType");
				 Id   = request.getParameter("Id");
				 productName = request.getParameter("productName"); 
				 productPrice = Double.parseDouble(request.getParameter("productPrice"));
				 productImage = request.getParameter("productImage");
				 productManufacturer = request.getParameter("productManufacturer");
				 productCondition = request.getParameter("productCondition");
				 productDiscount = Double.parseDouble(request.getParameter("productDiscount"));
				 rebateAmount =  Double.parseDouble(request.getParameter("rebateAmount"));
			     count = Integer.parseInt(request.getParameter("count"));
			}
			else{
				Id   = request.getParameter("Id");
			}	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			
			if(action.equals("Add Product"))
			{
			  	if(ProductType.equals("TV")){
					alltvs = MySqlDataStoreUtilities.getTVs();
					if(alltvs.containsKey(Id)){
						msg = "Product already available";  
					}	  
				}
				else if(ProductType.equals("Sound System")){
					allsoundsystems = MySqlDataStoreUtilities.getSoundSystems();
					if(allsoundsystems.containsKey(Id)){
						msg = "Product already available";
					}
				}
				else if (ProductType.equals("Phone")){
					allphones = MySqlDataStoreUtilities.getPhones();
					if(allphones.containsKey(Id)){
						msg = "Product already available";
					}
				}
				else if (ProductType.equals("Laptop")){
					alllaptops = MySqlDataStoreUtilities.getLaptops();
					if(alllaptops.containsKey(Id)){
						msg = "Product already available";
					}
				}
				else if (ProductType.equals("Voice Assistant")){
					allvoiceassistants = MySqlDataStoreUtilities.getVoiceAssistants();
					if(allvoiceassistants.containsKey(Id)){
						msg = "Product already available";
					}
				}
				else if (ProductType.equals("Fitness Watch")){
					allfitnesswatches = MySqlDataStoreUtilities.getFitnessWatches();
					if(allfitnesswatches.containsKey(Id)){
						msg = "Product already available";
					}
				}
				else if (ProductType.equals("Smart Watch")){
					allsmartwatches = MySqlDataStoreUtilities.getSmartWatches();
					if(allsmartwatches.containsKey(Id)){
						msg = "Product already available";
					}
				}
				else if (ProductType.equals("HeadPhone")){
					allheadphones = MySqlDataStoreUtilities.getHeadPhones();
					if(allheadphones.containsKey(Id)){
						msg = "Product already available";
					}
				}
				else if (ProductType.equals("Virtual Reality")){
					allvirtualrealitys = MySqlDataStoreUtilities.getVirtualRealitys();
					if(allvirtualrealitys.containsKey(Id)){
						msg = "Product already available";
					}
				}
				else if (ProductType.equals("Pet Tracker")){
					allpettrackers = MySqlDataStoreUtilities.getPetTrackers();
					if(allpettrackers.containsKey(Id)){
						msg = "Product already available";
					}
				}
				

				if (msg.equals("good"))
				{  
					try
					{
						msg = MySqlDataStoreUtilities.addproducts(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,rebateAmount,count,prod);
					}
					catch(Exception e)
					{ 
						msg = "Product cannot be inserted";
					}
					msg = "Product has been successfully added";
				}					
			}
			else if(action.equals("Update Product"))
			{
				if(ProductType.equals("TV")){
					alltvs = MySqlDataStoreUtilities.getTVs();
					if(!alltvs.containsKey(Id)){
						msg = "Product not available";
					}	  
				}
				else if(ProductType.equals("Sound System")){
					allsoundsystems = MySqlDataStoreUtilities.getSoundSystems();
					if(!allsoundsystems.containsKey(Id)){
						msg = "Product not available";
					}  
				}
				else if(ProductType.equals("Phone")){
					allphones = MySqlDataStoreUtilities.getPhones();
					if(!allphones.containsKey(Id)){
						msg = "Product not available";
					}  
				}
				else if(ProductType.equals("Laptop")){
					alllaptops = MySqlDataStoreUtilities.getLaptops();
					if(!alllaptops.containsKey(Id)){
						msg = "Product not available";
					}  
				}
				else if(ProductType.equals("Voice Assistant")){
					allvoiceassistants = MySqlDataStoreUtilities.getVoiceAssistants();
					if(!allvoiceassistants.containsKey(Id)){
						msg = "Product not available";
					}  
				}
				else if(ProductType.equals("Fitness Watch")){
					allfitnesswatches = MySqlDataStoreUtilities.getFitnessWatches();
					if(!allfitnesswatches.containsKey(Id)){
						msg = "Product not available";
					}  
				}
				else if(ProductType.equals("Smart Watch")){
					allsmartwatches = MySqlDataStoreUtilities.getSmartWatches();
					if(!allsmartwatches.containsKey(Id)){
						msg = "Product not available";
					}  
				}
				else if(ProductType.equals("HeadPhone")){
					allheadphones = MySqlDataStoreUtilities.getHeadPhones();
					if(!allheadphones.containsKey(Id)){
						msg = "Product not available";
					}  
				}
				else if(ProductType.equals("Virtual Reality")){
					allvirtualrealitys = MySqlDataStoreUtilities.getVirtualRealitys();
					if(!allvirtualrealitys.containsKey(Id)){
						msg = "Product not available";
					}  
				}
				else if(ProductType.equals("Pet Tracker")){
					allpettrackers = MySqlDataStoreUtilities.getPetTrackers();
					if(!allpettrackers.containsKey(Id)){
						msg = "Product not available";
					}
				}
				
				if (msg.equals("good"))
				{		
					try
					{
						msg = MySqlDataStoreUtilities.updateproducts(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,rebateAmount,count);
					}
					catch(Exception e)
					{ 
						msg = "Product cannot be updated";
					}
					msg = "Product has been successfully updated";
				} 
			}
			else if(action.equals("Delete"))
			{
				msg = "bad";
				//System.out.println("id: " + Id);
				alltvs = MySqlDataStoreUtilities.getTVs();
				if(alltvs.containsKey(Id)){
					msg = "good"; 
				}
				allsoundsystems = MySqlDataStoreUtilities.getSoundSystems();
				if(allsoundsystems.containsKey(Id)){
					msg = "good"; 
				}
				allphones = MySqlDataStoreUtilities.getPhones();
				if(allphones.containsKey(Id)){
					msg = "good"; 
				}
				alllaptops = MySqlDataStoreUtilities.getLaptops();
				if(alllaptops.containsKey(Id)){
					msg = "good"; 
				}
				allvoiceassistants = MySqlDataStoreUtilities.getVoiceAssistants();
				if(allvoiceassistants.containsKey(Id)){
					msg = "good"; 
				}
				allfitnesswatches = MySqlDataStoreUtilities.getFitnessWatches();
				if(allfitnesswatches.containsKey(Id)){
					msg = "good"; 
				}
				allsmartwatches = MySqlDataStoreUtilities.getSmartWatches();
				if(allsmartwatches.containsKey(Id)){
					msg = "good"; 
				}
				allheadphones = MySqlDataStoreUtilities.getHeadPhones();
				if(allheadphones.containsKey(Id)){
					msg = "good"; 
				}
				allpettrackers = MySqlDataStoreUtilities.getPetTrackers();
				if(allpettrackers.containsKey(Id)){
					msg = "good";
				}
				allvirtualrealitys = MySqlDataStoreUtilities.getVirtualRealitys();
				if(allvirtualrealitys.containsKey(Id)){
					msg = "good";
				}
				
				if (msg.equals("good"))
				{			
					try
					{  
						msg = MySqlDataStoreUtilities.deleteproducts(Id);
					}
					catch(Exception e)
					{ 
						msg = "Product cannot be deleted";
					}
					msg = "Product has been successfully deleted";
				}
				else
				{
					msg = "Product not available";
				}
			}
				
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<h4 style='color:#333333'>"+msg+"</h4>");
			pw.print("</div></div></div>");		
			utility.printHtml("Footer.html");
			
	}
}