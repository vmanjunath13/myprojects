import java.io.*;


public class Mostreviewed
{
	String providername ;
	String count;
	
	public  Mostreviewed(String providername, String count)
	{	
		this.providername = providername;
		this.count = count;
	}


	public String getProductname(){
	 return providername;
	}

	public String getCount() {
	 return count;
	}
	
}