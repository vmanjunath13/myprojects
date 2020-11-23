import java.io.IOException;
import java.io.*;


/* 
	Transaction class contains class variables username,ordername,price,image,address,creditcardno.

	Transaction  class has a constructor with Arguments username,ordername,price,image,address,creditcardno
	  
	Transaction  class contains getters and setters for username,ordername,price,image,address,creditcardno
*/

public class Transaction implements Serializable{
	private String loginId;
	private String custName;
	private int age;
	private String occupation;
	private String creditCardNo;
	private int orderId;
	private String purchaseDate;
	private String shipDate;
	private String actualDate;
	private String pId;
	private String pName;
	private String category;
	private String retailer;
	private int rating;
	private String tId;
	private String deliveryType;
	private String zip;
	private String txnStatus;
	private String ordReturn;
	private String OrdDel;
	
	public Transaction(String loginId, String custName, int age, String occupation, String creditCardNo, int orderId,String purchaseDate, String shipDate, String actualDate, String pId, String pName, String category, String retailer, int rating, String tId, String deliveryType, String zip, String txnStatus, String ordReturn, String OrdDel){
		this.loginId = loginId;
		this.custName=custName;
		this.age=age;
		this.occupation=occupation;
		this.creditCardNo=creditCardNo;
		this.orderId=orderId;
		this.purchaseDate=purchaseDate;
		this.shipDate=shipDate;
		this.actualDate=actualDate;
		this.pId=pId;
		this.pName=pName;
		this.category=category;
		this.retailer=retailer;
		this.rating=rating;
		this.tId=tId;
		this.deliveryType=deliveryType;
		this.zip=zip;
		this.txnStatus=txnStatus;
		this.ordReturn=ordReturn;
		this.OrdDel=OrdDel;
	}

	public Transaction() {
	}

	public String getLoginID() {
		return loginId;
	}

	public void setLoginID(String loginId) {
		this.loginId = loginId;
	}
	
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public int getCustomerAge() {
		return age;
	}

	public void setCustomerAge(int age) {
		this.age = age;
	}
	
	public String getCustomerOccupation() {
		return occupation;
	}

	public void setCustomerOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public String getcreditCardNo() {
		return creditCardNo;
	}

	public void setcreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}
	
	public int getOrderID() {
		return orderId;
	}

	public void setOrderID(int orderId) {
		this.orderId = orderId;
	}
	
	public String getOrderDate() {
		return purchaseDate;
	}

	public void setOrderDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public String getExpectedDeliveryDate() {
		return shipDate;
	}

	public void setExpectedDeliveryDate(String shipDate) {
		this.shipDate = shipDate;
	}
	
	public String getActualDeliveryDate() {
		return actualDate;
	}

	public void setActualDeliveryDate(String actualDate) {
		this.actualDate = actualDate;
	}
	
	public String getProductID() {
		return pId;
	}

	public void setProductID(String pId) {
		this.pId = pId;
	}
	
	public String getProductName() {
		return pName;
	}

	public void setProductName(String pName) {
		this.pName = pName;
	}
	
	public String getcategory() {
		return category;
	}

	public void setcategory(String category) {
		this.category = category;
	}
	
	public String getretailer() {
		return retailer;
	}

	public void setretailer(String retailer) {
		this.retailer = retailer;
	}
	
	public int getReviewRating() {
		return rating;
	}

	public void setReviewRating(int rating) {
		this.rating = rating;
	}
	
	public String getDeliveryTrackingID() {
		return tId;
	}

	public void setDeliveryTrackingID(String tId) {
		this.tId = tId;
	}
	
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	public String getDeliveryZipCode() {
		return zip;
	}

	public void setDeliveryZipCode(String zip) {
		this.zip = zip;
	}
	
	public String getTransactionStatus() {
		return txnStatus;
	}

	public void setTransactionStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	
	public String getOrderReturned() {
		return ordReturn;
	}

	public void setOrderReturned(String ordReturn) {
		this.ordReturn = ordReturn;
	}
	
	public String getOrderDeliveredonTime() {
		return OrdDel;
	}

	public void setOrderDeliveredonTime(String OrdDel) {
		this.OrdDel = OrdDel;
	}
}
