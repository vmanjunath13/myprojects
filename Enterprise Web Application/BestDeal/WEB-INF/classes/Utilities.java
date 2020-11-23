import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
			result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				String usertype = session.getAttribute("usertype").toString();
				System.out.print(usertype);
				switch (usertype){
					case "customer": // user
						result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>"
						+ "<li><a href='Cart'><span class='glyphicon'>Cart("+CartCount()+")</span></a></li></ul></div></div>";
					break;

					case "manager": //sales man
						result = result + "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
						+ "<li><a href='Registration'><span class='glyphicon'>AddUser</span></a></li>"
						+ "<li><a href='ManageProducts'><span class='glyphicon'>ManageProducts</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>"
						+ "<li><a href='Cart'><span class='glyphicon'>Cart("+CartCount()+")</span></a></li></ul></div></div>";
					break;

					case "retailer": //store manager
						result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a href='ManageProducts'><span class='glyphicon'>ManageProducts</span></a></li>"
						+ "<li><a href='Data'><span class='glyphicon'>DataAnalytics</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li></ul></div></div>";
					break;
				}
				// result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						// + "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						// + "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
						// + "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			}
			else
				result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
								+ "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>"
								+ "<li><a href='Cart'><span class='glyphicon'>Cart("+CartCount()+")</span></a></li></ul></div></div>";
				pw.print(result);
		} else
				pw.print(result);
	}
	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	} 

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		try
		{
			hm = MySqlDataStoreUtilities.selectUser();
		}
		catch(Exception e)
		{
		}
		User user = hm.get(username());
		return user;
	}
	
	
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		int size = 0;
		try
		{
			orderPayments = MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e)
		{

		}
		for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
				 size=entry.getKey();

		}
		return size;
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}
	
	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name,String type,String maker, String acc){
		if(!OrdersHashMap.orders.containsKey(username())){	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		HashMap<String,Tv> alltvs = new HashMap<String,Tv> ();
		HashMap<String,SoundSystem> allsoundsystems = new HashMap<String,SoundSystem> ();
		HashMap<String,Phone> allphones = new HashMap<String,Phone> ();
		HashMap<String,Laptop> alllaptops = new HashMap<String,Laptop> ();
		HashMap<String,VoiceAssistant> allvoiceassistants = new HashMap<String,VoiceAssistant> ();
		HashMap<String,FitnessWatch> allfitnesswatches = new HashMap<String,FitnessWatch> ();
		HashMap<String,SmartWatch> allsmartwatches = new HashMap<String,SmartWatch> ();
		HashMap<String,HeadPhone> allheadphones = new HashMap<String,HeadPhone> ();
		HashMap<String,PetTracker> allpettrackers = new HashMap<String,PetTracker> ();
		HashMap<String,VirtualReality> allvirtualrealitys = new HashMap<String,VirtualReality> ();
		
		if(type.equals("tv")){
			Tv tv;
			try
			{
				alltvs = MySqlDataStoreUtilities.getTVs();
			}
			catch(Exception e)
			{
			}
			tv = alltvs.get(name);
			OrderItem orderitem = new OrderItem(tv.getName(), tv.getPrice(), tv.getImage(), tv.getRetailer(), tv.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("soundsystems")){
			SoundSystem soundsystem;
			try
			{
				allsoundsystems = MySqlDataStoreUtilities.getSoundSystems();
			}
			catch(Exception e)
			{
			}
			soundsystem = allsoundsystems.get(name);
			OrderItem orderitem = new OrderItem(soundsystem.getName(), soundsystem.getPrice(), soundsystem.getImage(), soundsystem.getRetailer(), soundsystem.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("phones")){
			Phone phone = null;
			try
			{
				allphones = MySqlDataStoreUtilities.getPhones();
			}
			catch(Exception e)
			{
			}
			phone = allphones.get(name);
			OrderItem orderitem = new OrderItem(phone.getName(), phone.getPrice(), phone.getImage(), phone.getRetailer(), phone.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("laptops")){
			Laptop laptop = null;
			try
			{
				alllaptops = MySqlDataStoreUtilities.getLaptops();
			}
			catch(Exception e)
			{
			}
			laptop = alllaptops.get(name);
			OrderItem orderitem = new OrderItem(laptop.getName(), laptop.getPrice(), laptop.getImage(), laptop.getRetailer(), laptop.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("voiceassistants")){	
			VoiceAssistant voiceassistant = null;
			try
			{
				allvoiceassistants = MySqlDataStoreUtilities.getVoiceAssistants();
			}
			catch(Exception e)
			{
			}
			voiceassistant = allvoiceassistants.get(name); 
			OrderItem orderitem = new OrderItem(voiceassistant.getName(), voiceassistant.getPrice(), voiceassistant.getImage(), voiceassistant.getRetailer(), voiceassistant.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("fitnesswatches")){
			FitnessWatch fitnesswatch = null;
			try
			{
				allfitnesswatches = MySqlDataStoreUtilities.getFitnessWatches();
			}
			catch(Exception e)
			{
			}
			fitnesswatch = allfitnesswatches.get(name);
			OrderItem orderitem = new OrderItem(fitnesswatch.getName(), fitnesswatch.getPrice(), fitnesswatch.getImage(), fitnesswatch.getRetailer(), fitnesswatch.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("smartwatches")){
			SmartWatch smartwatch = null;
			try
			{
				allsmartwatches = MySqlDataStoreUtilities.getSmartWatches();
			}
			catch(Exception e)
			{
			}
			smartwatch = allsmartwatches.get(name);
			OrderItem orderitem = new OrderItem(smartwatch.getName(), smartwatch.getPrice(), smartwatch.getImage(), smartwatch.getRetailer(), smartwatch.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("headphones")){
			HeadPhone headphone = null;
			try
			{
				allheadphones = MySqlDataStoreUtilities.getHeadPhones();
			}
			catch(Exception e)
			{
			}
			headphone = allheadphones.get(name);
			OrderItem orderitem = new OrderItem(headphone.getName(), headphone.getPrice(), headphone.getImage(), headphone.getRetailer(), headphone.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("virtualrealitys")){
			VirtualReality virtualreality = null;
			try 
			{
				allvirtualrealitys = MySqlDataStoreUtilities.getVirtualRealitys();
			}
			catch(Exception e)
			{
			}
			virtualreality = allvirtualrealitys.get(name);
			OrderItem orderitem = new OrderItem(virtualreality.getName(), virtualreality.getPrice(), virtualreality.getImage(), virtualreality.getRetailer(), virtualreality.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("pettrackers")){
			PetTracker pettracker = null;
			try
			{
				allpettrackers = MySqlDataStoreUtilities.getPetTrackers();
			}
			catch(Exception e)
			{
			}
			pettracker = allpettrackers.get(name);
			OrderItem orderitem = new OrderItem(pettracker.getName(), pettracker.getPrice(), pettracker.getImage(), pettracker.getRetailer(), pettracker.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("accessories")){	
			Accessory accessory = SaxParserDataStore.accessories.get(name); 
			OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage(), accessory.getRetailer(), accessory.getCategory());
			orderItems.add(orderitem);
		}
		if(type.equals("warranty")){	 
			OrderItem orderitem = new OrderItem("Warranty", 100.0, "abc.jpg", "Warranty", "Warranty");
			orderItems.add(orderitem);
		}
		
	}
	// store the payment details for orders
	public void storePayment(int orderId, String orderName,double orderPrice,String userAddress,String creditCardNo,String FirstName,String LastName,
						String City, String State, String zip, String PhoneNumber, String CardName, String Month, String Year, String cvv, String purchaseDate, String shipDate){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		// get the payment details file
		try
		{
			orderPayments = MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e)
		{

		}
		if(orderPayments==null)
		{
			orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		}
		// if there exist order id already add it into same list for order id or create a new record with order id

		if(!orderPayments.containsKey(orderId)){
			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
			orderPayments.put(orderId, arr);
		}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo,
		FirstName,LastName,City,State,zip,PhoneNumber,CardName,Month,Year,cvv,purchaseDate,shipDate);
		listOrderPayment.add(orderpayment);	
			
			// add order details into file

			try
			{
				MySqlDataStoreUtilities.insertOrder(orderId,username(),orderName,orderPrice,userAddress,creditCardNo,FirstName,LastName,City,State,zip,PhoneNumber,CardName,Month,Year,cvv,purchaseDate,shipDate);
				MySqlDataStoreUtilities.UpdateInventory(orderName);
			}
			catch(Exception e)
			{
				System.out.println("inside exception file not written properly");
			}	
	}
	
	
	public void storeTransaction(String loginId, String custName, int age, String occupation, String creditCardNo, int orderId, String purchaseDate, String shipDate, String actualDate, String pId, String pName, String category, String retailer, int rating, String tId, String deliveryType, String zip, String txnStatus, String ordReturn, String OrdDel) {
		HashMap<Integer, ArrayList<Transaction>> orderTransactions= new HashMap<Integer, ArrayList<Transaction>>();
		// get the transactions details file
		try
		{
			orderTransactions = MySqlDataStoreUtilities.selectTransaction();
		}
		catch(Exception e)
		{

		}
		if(orderTransactions==null)
		{
			orderTransactions = new HashMap<Integer, ArrayList<Transaction>>();
		}
		// if there exist order id already add it into same list for order id or create a new record with order id

		if(!orderTransactions.containsKey(orderId)){
			ArrayList<Transaction> arr = new ArrayList<Transaction>();
			orderTransactions.put(orderId, arr);
		}
		ArrayList<Transaction> listTransaction = orderTransactions.get(orderId);		
		Transaction ordertransaction = new Transaction(loginId,custName,age,occupation,creditCardNo,orderId,purchaseDate,shipDate,actualDate,pId,pName,category,retailer,rating,tId,deliveryType,zip,txnStatus,ordReturn,OrdDel);
		listTransaction.add(ordertransaction);	
			
			// add order details into file

			try
			{
				MySqlDataStoreUtilities.insertTransaction(loginId,custName,age,occupation,creditCardNo,orderId,purchaseDate,shipDate,actualDate,pId,pName,category,retailer,rating,tId,deliveryType,zip,txnStatus,ordReturn,OrdDel);
			}
			catch(Exception e)
			{
				System.out.println("inside exception file not written properly");
			}	
	}


	public String storeReview(String PName,String PCategory,String PPrice,String MName,String RName,String  Rzip,String RCity,String RState,String ProductOnSale,
		String MRebate,String UID,String UAge,String UGender,String UOccupation,String RRating,String RDate,String RText){
		
		String message=MongoDBDataStoreUtilities.insertReview(username(),PName,PCategory,PPrice,MName,RName,Rzip,RCity,RState,ProductOnSale,MRebate,UID,UAge,UGender,UOccupation,RRating,RDate,RText);
		
		if(!message.equals("Successfull"))
		{ 
			return "UnSuccessfull";
		}
		else
		{	
			HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
			try
			{
				reviews=MongoDBDataStoreUtilities.selectReview();
			}
			catch(Exception e)
			{
				
			}
			if(reviews==null)
			{
				reviews = new HashMap<String, ArrayList<Review>>();
			}
				// if there exist product review already add it into same list for productname or create a new record with product name
				
			if(!reviews.containsKey(PName)){	
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(PName, arr);
			}
			ArrayList<Review> listReview = reviews.get(PName);		
			Review review = new Review(username(),PName,PCategory,PPrice,MName,RName,Rzip,RCity,RState,ProductOnSale,MRebate,UID,UAge,UGender,UOccupation,RRating,RDate,RText);
			listReview.add(review);	
				
				// add Reviews into database
			return "Successfull";	
		}
	}

	
	/* tvs Functions returns the Hashmap with all tvs in the store.*/

	public HashMap<String, Tv> getTvs(){
			HashMap<String, Tv> hm = new HashMap<String, Tv>();
			hm.putAll(SaxParserDataStore.tvs);
			return hm;
	}

	/* soundsystems Functions returns the Hashmap with all soundsystems in the store.*/

	public HashMap<String, SoundSystem> getSoundSystems(){
			HashMap<String, SoundSystem> hm = new HashMap<String, SoundSystem>();
			hm.putAll(SaxParserDataStore.soundsystems);
			return hm;
	}
	
	/* phones Functions returns the  Hashmap with all Phones in the store.*/

	public HashMap<String, Phone> getPhones(){
			HashMap<String, Phone> hm = new HashMap<String, Phone>();
			hm.putAll(SaxParserDataStore.phones);
			return hm;
	}
	
	/* laptops Functions returns the Hashmap with all Laptop in the store.*/

	public HashMap<String, Laptop> getLaptops(){
			HashMap<String, Laptop> hm = new HashMap<String, Laptop>();
			hm.putAll(SaxParserDataStore.laptops);
			return hm;
	}

	/* voiceassistants Functions returns the Hashmap with all VoiceAssistants in the store.*/

	public HashMap<String, VoiceAssistant> getVoiceAssistants(){
			HashMap<String, VoiceAssistant> hm = new HashMap<String, VoiceAssistant>();
			hm.putAll(SaxParserDataStore.voiceassistants);
			return hm;
	}

	/* fitnesswatches Functions returns the Hashmap with all FitnessWatch in the store.*/

	public HashMap<String, FitnessWatch> getFitnessWatches(){
			HashMap<String, FitnessWatch> hm = new HashMap<String, FitnessWatch>();
			hm.putAll(SaxParserDataStore.fitnesswatches);
			return hm;
	}

	/* smartwatches Functions returns the Hashmap with all SmartWatch in the store.*/

	public HashMap<String, SmartWatch> getSmartWatches(){
			HashMap<String, SmartWatch> hm = new HashMap<String, SmartWatch>();
			hm.putAll(SaxParserDataStore.smartwatches);
			return hm;
	}

	/* headphones Functions returns the Hashmap with all HeadPhone in the store.*/

	public HashMap<String, HeadPhone> getHeadPhones(){
			HashMap<String, HeadPhone> hm = new HashMap<String, HeadPhone>();
			hm.putAll(SaxParserDataStore.headphones);
			return hm;
	}

	/* virtualrealitys Functions returns the Hashmap with all virtualreality in the store.*/

	public HashMap<String, VirtualReality> getVirtualRealitys(){
			HashMap<String, VirtualReality> hm = new HashMap<String, VirtualReality>();
			hm.putAll(SaxParserDataStore.virtualrealitys);
			return hm;
	}
	
	/* pettrackers Functions returns the Hashmap with all pettracker in the store.*/

	public HashMap<String, PetTracker> getPetTrackers(){
			HashMap<String, PetTracker> hm = new HashMap<String, PetTracker>();
			hm.putAll(SaxParserDataStore.pettrackers);
			return hm;
	}
	
	/* getProducts Functions returns the Arraylist of tvs in the store.*/

	public ArrayList<String> getProductsTvs(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Tv> entry : getTvs().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of soundsystems in the store.*/

	public ArrayList<String> getProductsSoundSystems(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SoundSystem> entry : getSoundSystems().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of phones in the store.*/

	public ArrayList<String> getProductsPhones(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Phone> entry : getPhones().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of Laptops in the store.*/

	public ArrayList<String> getProductsLaptops(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Laptop> entry : getLaptops().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of VoiceAssistants in the store.*/
	
	public ArrayList<String> getProductsVoiceAssistants(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, VoiceAssistant> entry : getVoiceAssistants().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of FitnessWatches in the store.*/

	public ArrayList<String> getProductsFitnessWatches(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, FitnessWatch> entry : getFitnessWatches().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of SmartWatches in the store.*/

	public ArrayList<String> getProductsSmartWatches(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SmartWatch> entry : getSmartWatches().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of Headphones in the store.*/

	public ArrayList<String> getProductsHeadphones(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, HeadPhone> entry : getHeadPhones().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of VirtualRealitys in the store.*/

	public ArrayList<String> getProductsVirtualRealitys(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, VirtualReality> entry : getVirtualRealitys().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of PetTrackers in the store.*/

	public ArrayList<String> getProductsPetTrackers(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, PetTracker> entry : getPetTrackers().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
}
