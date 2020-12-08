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
	private String PType;
	private String RName;
	private String RFees;
	private String Rzip;
	private String RCity;
	private String RState;
	private String UAge;
	private String UGender;
	private String UOccupation;
	private String RRating;
	private String RDate;
	private String RText;
	
	public Review (String userName, String PName, String PType, String RName, String RFees, String Rzip, String RCity, String RState, String UAge, String UGender, String UOccupation, String RRating, String RDate, String RText){
		this.userName=userName;
		this.PName=PName;
		this.PType=PType;
		this.RName=RName;
		this.RFees=RFees;
		this.Rzip=Rzip;
		this.RCity=RCity;
		this.RState=RState;
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

	public String getPType() {
		return PType;
	}

	public void setPType(String PType) {
		this.PType = PType;
	}

	public String getRName() {
		return RName;
	}

	public void setRName(String RName) {
		this.RName = RName;
	}
	
	public String getRFees() {
		return RFees;
	}

	public void setRFees(String RFees) {
		this.RFees = RFees;
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
