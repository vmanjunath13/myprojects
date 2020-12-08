import java.io.*;

public class Bestrating
{
	String providername ;
	String rating;
	
	public  Bestrating(String providername, String rating)
	{
		this.providername = providername ;
		this.rating = rating;
	}


	public String getProductname(){
		return providername;
	}

	public String getRating() {
		return rating;
	}
	
}