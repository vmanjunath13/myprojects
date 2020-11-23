import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;
import java.util.Random;


@WebServlet("/StorePickup")
public class StorePickup extends HttpServlet {
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		HashMap<String,StoreDetail> allstoredetails = new HashMap<String,StoreDetail> ();

		try{
			allstoredetails = MySqlDataStoreUtilities.getStoreDetails();
		}
		catch(Exception e)
		{

		}
		
	    Utilities utility = new Utilities(request, pw);
		HttpSession session = request.getSession(true);	
		HashMap<String, StoreDetail> hm = new HashMap<String, StoreDetail>();
		hm.putAll(allstoredetails);
		//System.out.println("hm:" + hm);
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String purchaseDate = formatter.format(date);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(formatter.parse(purchaseDate));
		} catch(ParseException e) {
			e.printStackTrace();
		}
		
		c.add(Calendar.DAY_OF_MONTH, 14);
		String shipDate = formatter.format(c.getTime());
			
		// get the payment details like credit card no address processed from cart servlet	
		String userName = session.getAttribute("username").toString();
		String userAddress=request.getParameter("userAddress");
		String creditCardNo=request.getParameter("creditCardNo");
		String FirstName=request.getParameter("FirstName");
		String LastName=request.getParameter("LastName");
		String City=request.getParameter("City");
		String State=request.getParameter("State");
		String zip=request.getParameter("zip");
		String PhoneNumber=request.getParameter("PhoneNumber");
		String CardName=request.getParameter("CardName");
		String Month=request.getParameter("Month");
		String Year=request.getParameter("Year");
		String cvv=request.getParameter("cvv");
		String delivery=request.getParameter("delivery");
		String age=request.getParameter("age");
		String occupation=request.getParameter("occupation");
		double shippingCost=5.99;
		double totalSales= 0.0;
		String actualDate = "";
		String txn_status = "";
		String order_ret = "";
		String order_deliv_ontime = "";
		
		if(!userAddress.isEmpty() && !creditCardNo.isEmpty() && !FirstName.isEmpty()  && !LastName.isEmpty() && !City.isEmpty() && !State.isEmpty()
			&& !zip.isEmpty() && !PhoneNumber.isEmpty() && !CardName.isEmpty() && !Month.isEmpty() && !Year.isEmpty() && !cvv.isEmpty() && !purchaseDate.isEmpty() && !shipDate.isEmpty() && !age.isEmpty() && !occupation.isEmpty())
		{
			//Random rand = new Random(); 
			//int orderId = rand.nextInt(100);
			int orderId = utility.getOrderPaymentSize()+1;

			//iterate through each order

			for (OrderItem oi : utility.getCustomerOrders())
			{	
				if(delivery.equals("Home Delivery")) {
					totalSales = oi.getPrice() + shippingCost;
				} else {
					totalSales = oi.getPrice();
				}
					
				if(totalSales > 1000.0) { 
					c.add(Calendar.DAY_OF_MONTH, 5);
					actualDate = formatter.format(c.getTime());
				}
				else {
					c.add(Calendar.DAY_OF_MONTH, 1);
					actualDate = formatter.format(c.getTime());
				}
					
				
				//set the parameter for each column and execute the prepared statement
				utility.storePayment(orderId,oi.getName(),oi.getPrice(),userAddress,creditCardNo,
				FirstName,LastName,City,State,zip,PhoneNumber,CardName,Month,Year,cvv,purchaseDate,shipDate);
				
				String custName = FirstName + " " + LastName;
				Random rand = new Random(); 
				int rating = rand.nextInt(6);
				String trackingId = Integer.toString(rand.nextInt(501));
				int choice = rand.nextInt(2);
				if(choice==0) {
					txn_status = "Approved";
					order_ret = "No";
					order_deliv_ontime = "Yes";
				}
				else {
					txn_status = "Disputed";
					order_ret = "Yes";
					order_deliv_ontime = "No";
				}
					
				
				utility.storeTransaction(userName, custName, Integer.parseInt(age), occupation, creditCardNo, orderId, purchaseDate, shipDate, actualDate, oi.getName(), oi.getName(), oi.getCategory(), oi.getRetailer(), rating, trackingId, delivery, zip, txn_status, order_ret, order_deliv_ontime);
			}

			//remove the order details from cart after processing
			
			OrdersHashMap.orders.remove(utility.username());		
		
			//get the order product details	on clicking submit the form will be passed to submitorder page	
			
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<form name ='StorePickup' action='Payment' method='post'>");		
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<p style='font-size: 16px;color:#106dc4;padding-top:10px;font-weight:bold'>Customer Name:  " + userName +"</p>");
			pw.print("<p style='font-size: 16px;color:#106dc4;padding-top:5px;font-weight:bold'>Total Sales Cost:  " + totalSales +"</p>");
			pw.print("<p style='font-size: 16px;color:#106dc4;font-weight:bold'>Selected Delivery Type:  " + delivery +"</p>");
			if(delivery.equals("Store Pick-up")) {
				pw.print("<div><p style='font-size:16px; font-weight:bold; color:#106dc4;'>Select a store to pick-up your order:</p>");
				pw.print("<table style='border:none !important;'>");
				int i = 1; int size= hm.size();
				System.out.println("size: " + size);
				for(String entry: hm.keySet()){
					if(i%2==1) pw.print("<tr>");
					pw.print("<td><div id='shop_item'>");
					StoreDetail storedetail = hm.get(entry);
					pw.print("<p style='font-size:13px !important;color:#106dc4 !important;'><input type='radio' name='storeAddress' value= '"+storedetail.getStoreAddress()+"'> " +
					storedetail.getStoreId()+"<br>"+
					storedetail.getStreet()+",<br>"+
					storedetail.getCity()+", "+storedetail.getState()+", "+storedetail.getzipCode()+"</p></div></td>");
					if(i%2==0 || i == size) pw.print("</tr>");
					i++;
				}
				pw.print("</table></div>");
			} else {
				String homeAddress = userAddress + ", " + City + ", " + State + ", " + zip;
				pw.print("<p style='font-size:16px; font-weight:bold; color:#106dc4;'>Please confirm your delivery address:</p>");
				pw.print("<p style='font-size: 13px;color:#106dc4;'><input type='hidden' name='storeAddress' value='homeAddress'><input type='hidden' name='homeAddress' value='home'>Delivery Address:  </br>" 
				+ homeAddress
				+ "</p>");
				pw.print("<div><table style='width: 25%; border-collapse: collapse;'><tr><td><input type='radio' name='addrConf' checked>Yes</td>");
				pw.print("<td><input type='radio' name='addrConf'>No</td></tr></table></div>");
			}
			
			pw.print("<br><br><table><tr><td colspan='2' style='text-align:center;'>");
			pw.print("<input type='submit' name='CheckOut' value='CheckOut' class='btnbuy'>");
			pw.print("</td></tr></table></form>");
			pw.print("</div></div></div>");				
			utility.printHtml("Footer.html");
		} 			
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	}
 
}