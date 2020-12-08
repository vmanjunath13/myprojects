import java.io.*;


public class Mostreviewedzip
{
	String zipcode ;
	String count;

	public  Mostreviewedzip(String zipcode,String count)
	{
		
		this.zipcode = zipcode;
		this.count = count;
	}


	public String getZipcode(){
	 return zipcode;
	}

	public String getCount () {
	 return count;
	}
}