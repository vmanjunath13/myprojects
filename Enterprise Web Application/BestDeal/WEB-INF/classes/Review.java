import java.io.IOException;
import java.io.*;


/* 
	Review class contains class variables username,productname,reviewtext,reviewdate,reviewrating

	Review class has a constructor with Arguments username,productname,reviewtext,reviewdate,reviewrating
	  
	Review class contains getters and setters for username,productname,reviewtext,reviewdate,reviewrating
*/

public class Review implements Serializable{
	private String userName;
	private String PName;
	private String PCategory;
	private String PPrice;
	private String RName;
	private String Rzip;
	private String RCity;
	private String RState;
	private String ProductOnSale;
	private String MName;
	private String MRebate;
	private String UID;
	private String UAge;
	private String UGender;
	private String UOccupation;
	private String RRating;
	private String RDate;
	private String RText;
	
	public Review (String userName,String PName, String PCategory, String PPrice, String RName, String Rzip, String RCity, String RState, String ProductOnSale, String MName, String MRebate,
				String UID, String UAge, String UGender, String UOccupation, String RRating, String RDate, String RText){
		this.userName=userName;
		this.PName=PName;
		this.PCategory=PCategory;
		this.PPrice=PPrice;
		this.RName=RName;
		this.Rzip=Rzip;
		this.RCity=RCity;
		this.RState=RState;
		this.ProductOnSale=ProductOnSale;
		this.MName=MName;
		this.MRebate=MRebate;
		this.UID=UID;
		this.UAge=UAge;
		this.UGender=UGender;
		this.UOccupation=UOccupation;
		this.RRating=RRating;
		this.RDate=RDate;
		this.RText=RText;
	}
	
	public Review(String PName, String Rzip, String RRating, String RText) {
       this.PName = PName;
       this.Rzip = Rzip;
       this.RRating = RRating;
       this.RText = RText;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPName() {
		return PName;
	}

	public void setPName(String PName) {
		this.PName = PName;
	}

	public String getPCategory() {
		return PCategory;
	}

	public void setPCategory(String PCategory) {
		this.PCategory = PCategory;
	}

	public String getPPrice() {
		return PPrice;
	}

	public void setPPrice(String PPrice) {
		this.PPrice = PPrice;
	}

	public String getRName() {
		return RName;
	}

	public void setRName(String RName) {
		this.RName = RName;
	}

	public String getRzip() {
		return Rzip;
	}

	public void setRzip(String Rzip) {
		this.Rzip = Rzip;
	}

	public String getRCity() {
		return RCity;
	}

	public void setRCity(String RCity) {
		this.RCity = RCity;
	}

	public String getRState() {
		return RState;
	}

	public void setRState(String RState) {
		this.RState = RState;
	}

	public String getProductOnSale() {
		return ProductOnSale;
	}

	public void setProductOnSale(String ProductOnSale) {
		this.ProductOnSale = ProductOnSale;
	}

	public String getMName() {
		return MName;
	}

	public void setMName(String MName) {
		this.MName = MName;
	}

	public String getMRebate() {
		return MRebate;
	}

	public void setMRebate(String MRebate) {
		this.MRebate = MRebate;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String UID) {
		this.UID = UID;
	}

	public String getUAge() {
		return UAge;
	}

	public void setUAge(String UAge) {
		this.UAge = UAge;
	}

	public String getUGender() {
		return UGender;
	}

	public void setUGender(String UGender) {
		this.UGender = UGender;
	}

	public String getUOccupation() {
		return UOccupation;
	}

	public void setUOccupation(String UOccupation) {
		this.UOccupation = UOccupation;
	}

	public String getRRating() {
		return RRating;
	}

	public void setRRating(String RRating) {
		this.RRating = RRating;
	}

	public String getRDate() {
		return RDate;
	}

	public void setRDate(String RDate) {
		this.RDate = RDate;
	}

	public String getRText() {
		return RText;
	}

	public void setRText(String RText) {
		this.RText = RText;
	}

}
