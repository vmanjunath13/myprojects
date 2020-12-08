import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserDataStore extends DefaultHandler {
    Doctor doctor;
    Hospital hospital;

	String Id;
	String name;
	String experience;
	String image;
	String speciality;
	String staddress;
	String state;
	String city;
	String zipcode;
	String type;
	String latitude;
	String longitude;
	Double fees;

    static HashMap<String,Doctor> doctors;
    static HashMap<String,Hospital> hospitals;
    String consoleXmlFileName;
	String elementValueRead;
	String currentElement="";
    public SaxParserDataStore()
	{
	}
	public SaxParserDataStore(String consoleXmlFileName) {
    	this.consoleXmlFileName = consoleXmlFileName;
		doctors = new HashMap<String, Doctor>();
		hospitals = new HashMap<String, Hospital>();
		parseDocument();
    }

   //parse the xml using sax parser to get the data
    private void parseDocument() 
	{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try 
		{
	    SAXParser parser = factory.newSAXParser();
	    parser.parse(consoleXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}

   
	
	// when xml start element is parsed store the id into respective hashmap for wearabletech,phones etc 
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException 
	{
		if (elementName.equals("doctor"))
		{
			currentElement="doctor";
			doctor=new Doctor();
			doctor.setId(attributes.getValue("id"));
			Id = attributes.getValue("id");
	    }
		if (elementName.equals("hospital"))
		{
			currentElement="hospital";
			hospital=new Hospital();
			hospital.setId(attributes.getValue("id"));
			Id = attributes.getValue("id");
	    }
    }
	
	// when xml end element is parsed store the data into respective hashmap for wearabletech,phones etc respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException 
	{
		if (element.equals("doctor")){
			doctors.put(doctor.getId(),doctor);       
			return; 
        }
		if (element.equals("hospital")){
			hospitals.put(hospital.getId(),hospital);       
			return; 
        }

        if (element.equalsIgnoreCase("image")) 
		{
			if (currentElement.equals("doctor")){
				doctor.setImage(elementValueRead);
				image = elementValueRead;
			}
			if (currentElement.equals("hospital")){
				hospital.setImage(elementValueRead);
				image = elementValueRead;
			}
			return;
	    }

        if (element.equalsIgnoreCase("name")) 
		{
			if(currentElement.equals("doctor")) {
				doctor.setName(elementValueRead);
				name = elementValueRead;
			}
			if(currentElement.equals("hospital")) {
				hospital.setName(elementValueRead);
				name = elementValueRead;
			}
			return;
	    }
		
		if (element.equalsIgnoreCase("speciality")) {
			if(currentElement.equals("doctor")){
				doctor.setSpeciality(elementValueRead);
				speciality = elementValueRead;
			}
			if(currentElement.equals("hospital")){
				hospital.setSpeciality(elementValueRead);
				speciality = elementValueRead;
			}
			return;
		}
		
		if (element.equalsIgnoreCase("experience")) {
			if(currentElement.equals("doctor")){
				doctor.setExperience(elementValueRead);
				experience = elementValueRead;
			}
			if(currentElement.equals("hospital")){
				hospital.setExperience(elementValueRead);
				experience = elementValueRead;
			}
			return;
		}
		
		if (element.equalsIgnoreCase("staddress")) {
            if(currentElement.equals("doctor")){
				doctor.setStAddress(elementValueRead);
				staddress = elementValueRead;
			}
			if(currentElement.equals("hospital")){
				hospital.setStAddress(elementValueRead);
				staddress = elementValueRead;
			}
			return;
		}
		
		if (element.equalsIgnoreCase("name")) {
			if(currentElement.equals("doctor")){
				doctor.setName(elementValueRead);
				name = elementValueRead;
			}
			if(currentElement.equals("hospital")){
				hospital.setName(elementValueRead);
				name = elementValueRead;
			}
			return;
		}
		
		if (element.equalsIgnoreCase("type")) {
			if(currentElement.equals("doctor")){
				doctor.setType(elementValueRead);
				type = elementValueRead;
			}
			if(currentElement.equals("hospital")){
				hospital.setType(elementValueRead);
				type = elementValueRead;
			}
			return;
		}
		
		
		if (element.equalsIgnoreCase("fees")) {
            if(currentElement.equals("doctor")){
				doctor.setFees(Double.parseDouble(elementValueRead));
				fees = Double.parseDouble(elementValueRead);       
			}
			if(currentElement.equals("hospital")){
				hospital.setFees(Double.parseDouble(elementValueRead));
				fees = Double.parseDouble(elementValueRead);       
			}
			return;
		}
		
		if (element.equalsIgnoreCase("latitude")) {
			if(currentElement.equals("doctor")){
				doctor.setType(elementValueRead);
				latitude = elementValueRead;
			}
			if(currentElement.equals("hospital")){
				hospital.setType(elementValueRead);
				latitude = elementValueRead;
			}
			return;
		}
		
		if (element.equalsIgnoreCase("longitude")) {
			if(currentElement.equals("doctor")){
				doctor.setType(elementValueRead);
				longitude = elementValueRead;
			}
			if(currentElement.equals("hospital")){
				hospital.setType(elementValueRead);
				longitude = elementValueRead;
			}
			MySqlDataStoreUtilities.insertProvider(Id,name,staddress,city,state,zipcode,speciality,image,experience,fees,type,latitude,longitude);
			return;
		}
	}
	//get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////
	
//call the constructor to parse the xml and get product details
 public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");	
		new SaxParserDataStore(TOMCAT_HOME+"\\webapps\\team4_2\\ProductCatalog.xml");
    } 
}
