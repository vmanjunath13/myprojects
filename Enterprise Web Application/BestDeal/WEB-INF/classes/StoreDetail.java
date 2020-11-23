import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StoreDetail")


public class StoreDetail extends HttpServlet{
	private int id;
	private String storeId;
	private String street;
	private String city;
	private String state;
	private String zipCode;
	
	public StoreDetail(int id, String storeId, String street, String city, String state, String zipCode){
		this.id=id;
		this.storeId=storeId;
		this.street=street;
		this.state=state;
		this.city=city;
		this.zipCode = zipCode;
	}
	
	public StoreDetail(){
		
	}
	
	public int getId() {
		return id;
	}
	
	public String getStoreId() {
		return storeId;
	}
	
	public String getStreet() {
		return street;
	}
	
	public String getState() {
		return state;
	}
	
	public String getCity() {
		return city;
	}

	public String getzipCode() {
		return zipCode;
	}
	
	public String getStoreAddress() {
		return storeId + "\n" + street + "\n" + city + ", " + state + ", " + zipCode;
	}
	
}
