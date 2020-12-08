import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Hospital")

/* 
	Hospital class contains class variables name,address,image,speciality,experience,fees.

	Hospital class has a constructor with Arguments name,address,image,speciality,experience,fees.
	  
	Hospital class contains getters and setters for name,address,image,speciality,experience,fees.
*/

public class Hospital extends HttpServlet{
	private String id;
	private String name;
	private String staddress;
	private String city;
	private String state;
	private String zipcode;
	private String image;
	private String speciality;
	private String experience;
	private String type;
	private double fees;
	private String latitude;
	private String longitude;
	
	public Hospital(String name, String staddress, String city, String state, String zipcode, String speciality, String image, String experience, double fees, String type, String latitude, String longitude){
		this.name=name;
		this.staddress=staddress;
		this.city=city;
		this.state=state;
		this.zipcode=zipcode;
		this.image=image;
		this.speciality=speciality;
		this.experience = experience;
		this.fees = fees;
		this.type = type;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Hospital(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getFees() {
		return fees;
	}
	public void setFees(double fees) {
		this.fees = fees;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getStAddress() {
		return staddress;
	}
	public void setStAddress(String staddress) {
		this.staddress = staddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipcode;
	}
	public void setZipCode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}