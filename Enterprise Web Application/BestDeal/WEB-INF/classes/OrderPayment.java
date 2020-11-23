import java.io.IOException;
import java.io.*;


/* 
	OrderPayment class contains class variables username,ordername,price,image,address,creditcardno.

	OrderPayment  class has a constructor with Arguments username,ordername,price,image,address,creditcardno
	  
	OrderPayment  class contains getters and setters for username,ordername,price,image,address,creditcardno
*/

public class OrderPayment implements Serializable{
	private int orderId;
	private String userName;
	private String orderName;
	private double orderPrice;
	private String userAddress;
	private String creditCardNo;
	private String FirstName;
	private String LastName;
	private String City;
	private String State;
	private String zip;
	private String PhoneNumber;
	private String CardName;
	private String Month;
	private String Year;
	private String cvv;
	private String purchaseDate;
	private String shipDate;
	
	
	public OrderPayment(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditCardNo,String FirstName,String LastName,String City, String State, String zip, String PhoneNumber, String CardName, String Month, String Year, String cvv, String purchaseDate, String shipDate){
		this.orderId=orderId;
		this.userName=userName;
		this.orderName=orderName;
	 	this.orderPrice=orderPrice;
		this.userAddress=userAddress;
	 	this.creditCardNo=creditCardNo;
		this.FirstName=FirstName;
	 	this.LastName=LastName;
	 	this.City=City;
	 	this.State=State;
	 	this.zip=zip;
	 	this.PhoneNumber=PhoneNumber;
	 	this.CardName=CardName;
	 	this.Month=Month;
	 	this.Year=Year;
	 	this.cvv=cvv;
		this.purchaseDate=purchaseDate;
		this.shipDate=shipDate;
		}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String FirstName) {
		this.FirstName = FirstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String LastName) {
		this.LastName = LastName;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String City) {
		this.City = City;
	}

	public String getState() {
		return State;
	}

	public void setState(String State) {
		this.State = State;
	}

	public String getzip() {
		return zip;
	}

	public void setzip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String PhoneNumber) {
		this.PhoneNumber = PhoneNumber;
	}

	public String getCardName() {
		return CardName;
	}

	public void setCardName(String CardName) {
		this.CardName = CardName;
	}

	public String getMonth() {
		return Month;
	}

	public void setMonth(String Month) {
		this.Month = Month;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String Year) {
		this.Year = Year;
	}

	public String getcvv() {
		return cvv;
	}

	public void setcvv(String cvv) {
		this.cvv = cvv;
	}
	
	public String getpurchaseDate() {
		return purchaseDate;
	}

	public void setpurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public String getshipDate() {
		return shipDate;
	}

	public void setshipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	
}
