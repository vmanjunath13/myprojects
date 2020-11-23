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
    Tv tv;
    SoundSystem soundsystem;
    Phone phone;
    Laptop laptop;
    VoiceAssistant voiceassistant;
    FitnessWatch fitnesswatch;
    SmartWatch smartwatch;
    HeadPhone headphone;
    VirtualReality virtualreality;
	PetTracker pettracker;
    Accessory accessory;

    String ProductType;
    String Id;
    String productName;
    Double productPrice;
    String productImage;
    String productManufacturer;
    String productCondition;
    Double productDiscount;
    Double manufacturerRebate;
    Integer count;

    static HashMap<String,Tv> tvs;
    static HashMap<String,SoundSystem> soundsystems;
    static HashMap<String,Phone> phones;
    static HashMap<String,Laptop> laptops;
    static HashMap<String,VoiceAssistant> voiceassistants;
    static HashMap<String,FitnessWatch> fitnesswatches;
    static HashMap<String,SmartWatch> smartwatches;
    static HashMap<String,HeadPhone> headphones;
    static HashMap<String,VirtualReality> virtualrealitys;
	static HashMap<String,PetTracker> pettrackers;
    static HashMap<String,Accessory> accessories;
    String consoleXmlFileName;
	HashMap<String,String> accessoryHashMap;
    String elementValueRead;
	String currentElement="";
    public SaxParserDataStore()
	{
	}
	public SaxParserDataStore(String consoleXmlFileName) {
    // this.consoleXmlFileName = consoleXmlFileName;
	this.consoleXmlFileName = consoleXmlFileName;
    tvs = new HashMap<String, Tv>();
    soundsystems = new HashMap<String, SoundSystem>();
	phones=new  HashMap<String, Phone>();
	laptops=new HashMap<String, Laptop>();
	voiceassistants=new HashMap<String, VoiceAssistant>();
	fitnesswatches = new HashMap<String, FitnessWatch>();
	smartwatches = new HashMap<String, SmartWatch>();
	headphones = new HashMap<String, HeadPhone>();
	virtualrealitys = new HashMap<String, VirtualReality>();
	pettrackers = new HashMap<String, PetTracker>();
	accessories=new HashMap<String, Accessory>();
	accessoryHashMap=new HashMap<String,String>();
	// accessoryHashMap=new HashMap<String,String>();
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
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("tv")) 
		{
			currentElement="tv";
			tv = new Tv();
            tv.setId(attributes.getValue("id"));
			ProductType = "TV";
			Id = attributes.getValue("id");
		}
		if (elementName.equalsIgnoreCase("soundsystem")) 
		{
			currentElement="soundsystem";
			soundsystem = new SoundSystem();
            soundsystem.setId(attributes.getValue("id"));
            ProductType = "Sound System";
            Id = attributes.getValue("id");
		}
		if (elementName.equalsIgnoreCase("phone"))
		{
			currentElement="phone";
			phone= new Phone();
            phone.setId(attributes.getValue("id"));
            ProductType = "Phone";
            Id = attributes.getValue("id");
        }
        if (elementName.equalsIgnoreCase("laptop"))
		{
			currentElement="laptop";
			laptop = new Laptop();
            laptop.setId(attributes.getValue("id"));
            ProductType = "Laptop";
            Id = attributes.getValue("id");
        }
		if (elementName.equalsIgnoreCase("voiceassistant"))
		{
			currentElement="voiceassistant";
			voiceassistant=new VoiceAssistant();
			voiceassistant.setId(attributes.getValue("id"));
			ProductType = "Voice Assistant";
            Id = attributes.getValue("id");
	    }
	    if (elementName.equalsIgnoreCase("fitnesswatch"))
		{
			currentElement="fitnesswatch";
			fitnesswatch=new FitnessWatch();
			fitnesswatch.setId(attributes.getValue("id"));
			ProductType = "Fitness Watch";
            Id = attributes.getValue("id");
	    }
	    if (elementName.equalsIgnoreCase("smartwatch"))
		{
			currentElement="smartwatch";
			smartwatch=new SmartWatch();
			smartwatch.setId(attributes.getValue("id"));
			ProductType = "Smart Watch";
            Id = attributes.getValue("id");
	    }
	    if (elementName.equalsIgnoreCase("headphone"))
		{
			currentElement="headphone";
			headphone=new HeadPhone();
			headphone.setId(attributes.getValue("id"));
			ProductType = "HeadPhone";
            Id = attributes.getValue("id");
	    }
	    if (elementName.equalsIgnoreCase("pettracker"))
		{
			currentElement="pettracker";
			pettracker=new PetTracker();
			pettracker.setId(attributes.getValue("id"));
			ProductType = "Pet Tracker";
			Id = attributes.getValue("id");
	    }
		if (elementName.equalsIgnoreCase("virtualreality"))
		{
			currentElement="virtualreality";
			virtualreality=new VirtualReality();
			virtualreality.setId(attributes.getValue("id"));
			ProductType = "Virtual Reality";
			Id = attributes.getValue("id");
	    }
	    if (elementName.equals("accessory") &&  !currentElement.equals("tv"))
		{
			currentElement="accessory";
			accessory=new Accessory();
			accessory.setId(attributes.getValue("id"));
			ProductType = "Accessory";
            Id = attributes.getValue("id");
	    }
    }
	// when xml end element is parsed store the data into respective hashmap for wearabletech,phones etc respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        if (element.equals("tv")) {
			tvs.put(tv.getId(),tv);
			return;
        }
        if (element.equals("soundsystem")) {
			soundsystems.put(soundsystem.getId(),soundsystem);
			return;
        }
        if (element.equals("phone")) {	  
			phones.put(phone.getId(),phone);
			return;
        }
        if (element.equals("laptop")) {	
			laptops.put(laptop.getId(),laptop);
			return;
        }
        if (element.equals("voiceassistant")){
			voiceassistants.put(voiceassistant.getId(),voiceassistant);       
			return; 
        }
        if (element.equals("fitnesswatch")){
			fitnesswatches.put(fitnesswatch.getId(),fitnesswatch);       
			return; 
        }
        if (element.equals("smartwatch")){
			smartwatches.put(smartwatch.getId(),smartwatch);       
			return; 
        }
        if (element.equals("headphone")){
			headphones.put(headphone.getId(),headphone);       
			return; 
        }
        if (element.equals("virtualreality")){
			virtualrealitys.put(virtualreality.getId(),virtualreality);       
			return; 
        }
		if (element.equals("pettracker")){
			pettrackers.put(pettracker.getId(),pettracker);       
			return; 
        }
        if (element.equals("accessory") && currentElement.equals("accessory")) {
			accessories.put(accessory.getId(),accessory);       
			return; 
        }
		if (element.equals("accessory") && currentElement.equals("tv")) 
		{
			accessoryHashMap.put(elementValueRead,elementValueRead);
		}
      	if (element.equalsIgnoreCase("accessories") && currentElement.equals("tv")) {
			tv.setAccessories(accessoryHashMap);
			accessoryHashMap=new HashMap<String,String>();
			return;
		}

        if (element.equalsIgnoreCase("image")) {
		    if(currentElement.equals("tv")) {
				tv.setImage(elementValueRead);
				productImage = elementValueRead;
		    }
			if(currentElement.equals("soundsystem")){
				soundsystem.setImage(elementValueRead);
				productImage = elementValueRead;
			}
        	if(currentElement.equals("phone")){
				phone.setImage(elementValueRead);
				productImage = elementValueRead;
        	}
            if(currentElement.equals("laptop")){
				laptop.setImage(elementValueRead);
				productImage = elementValueRead;
            }
            if(currentElement.equals("voiceassistant")){
				voiceassistant.setImage(elementValueRead);
				productImage = elementValueRead;
            }
			if(currentElement.equals("fitnesswatch")){
				fitnesswatch.setImage(elementValueRead);
				productImage = elementValueRead;
			}
			if(currentElement.equals("smartwatch")){
				smartwatch.setImage(elementValueRead);
				productImage = elementValueRead; 
			}
			if(currentElement.equals("headphone")){
				headphone.setImage(elementValueRead);
				productImage = elementValueRead;         
			}
			if (currentElement.equals("virtualreality")){
				virtualreality.setImage(elementValueRead);
				productImage = elementValueRead;
			}
			if (currentElement.equals("pettracker")){
				pettracker.setImage(elementValueRead);
				productImage = elementValueRead;
			}
			if(currentElement.equals("accessory")){
				accessory.setImage(elementValueRead);
				productImage = elementValueRead;         
			}
			return;
	    }


		if (element.equalsIgnoreCase("condition")) {
            if(currentElement.equals("tv")) {
				tv.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
			if(currentElement.equals("soundsystem")) {
				soundsystem.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
        	if(currentElement.equals("phone")) {
				phone.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
            if(currentElement.equals("laptop")) {
				laptop.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
            if(currentElement.equals("voiceassistant")) {
				voiceassistant.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
			if(currentElement.equals("fitnesswatch")) {
				fitnesswatch.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
			if(currentElement.equals("smartwatch")) {
				smartwatch.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
			if(currentElement.equals("headphone")) {
				headphone.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
			if(currentElement.equals("virtualreality")) {
				virtualreality.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
			if(currentElement.equals("pettracker")) {
				pettracker.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
			if(currentElement.equals("accessory")) {
				accessory.setCondition(elementValueRead);
				productCondition = elementValueRead;
			}
			return;  
		}

		if (element.equalsIgnoreCase("manufacturer")) {
            if(currentElement.equals("tv")) {
				tv.setRetailer(elementValueRead);
				productManufacturer = elementValueRead;
            }
			if(currentElement.equals("soundsystem")){
				soundsystem.setRetailer(elementValueRead);
				productManufacturer = elementValueRead;
			}
        	if(currentElement.equals("phone")){
				phone.setRetailer(elementValueRead);
				productManufacturer = elementValueRead;
        	}
            if(currentElement.equals("laptop")){
				laptop.setRetailer(elementValueRead);
				productManufacturer = elementValueRead;
            }
            if(currentElement.equals("voiceassistant")){
				voiceassistant.setRetailer(elementValueRead);
				productManufacturer = elementValueRead;
            }
			if(currentElement.equals("fitnesswatch")){
				fitnesswatch.setRetailer(elementValueRead);
				productManufacturer = elementValueRead;  
			}
			if(currentElement.equals("smartwatch")){
				smartwatch.setRetailer(elementValueRead);
				productManufacturer = elementValueRead; 
			}
			if(currentElement.equals("headphone")) {
				headphone.setRetailer(elementValueRead);
				productManufacturer = elementValueRead;
			}
			if(currentElement.equals("virtualreality")) {
				virtualreality.setRetailer(elementValueRead);
				productManufacturer = elementValueRead;
			}
			if(currentElement.equals("pettracker")) {
				pettracker.setRetailer(elementValueRead);
				productManufacturer = elementValueRead;
			}
			if(currentElement.equals("accessory")) {
				accessory.setRetailer(elementValueRead);
				productManufacturer = elementValueRead;
			}
			return;
		}

        if (element.equalsIgnoreCase("name")) {
            if(currentElement.equals("tv")){
				tv.setName(elementValueRead);
				productName = elementValueRead;
            }
			if(currentElement.equals("soundsystem")){
				soundsystem.setName(elementValueRead);
				productName = elementValueRead;
			}
        	if(currentElement.equals("phone")){
				phone.setName(elementValueRead);
				productName = elementValueRead;
        	}
            if(currentElement.equals("laptop")){
				laptop.setName(elementValueRead);
				productName = elementValueRead;
            }
            if(currentElement.equals("voiceassistant")){
				voiceassistant.setName(elementValueRead);
				productName = elementValueRead;
            }
			if(currentElement.equals("fitnesswatch")){
				fitnesswatch.setName(elementValueRead);
				productName = elementValueRead;  
			}
			if(currentElement.equals("smartwatch")){
				smartwatch.setName(elementValueRead);
				productName = elementValueRead;  
			}
			if(currentElement.equals("headphone")){
				headphone.setName(elementValueRead);
				productName = elementValueRead;
			}
			if(currentElement.equals("virtualreality")) {
				virtualreality.setName(elementValueRead);
				productName = elementValueRead;
			}
			if(currentElement.equals("pettracker")) {
				pettracker.setName(elementValueRead);
				productName = elementValueRead;
			}
			if(currentElement.equals("accessory")) {
				accessory.setName(elementValueRead);
				productName = elementValueRead;
			}
			return;
	    }
	
        if(element.equalsIgnoreCase("price")){
			if(currentElement.equals("tv")){
				tv.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("soundsystem")){
				soundsystem.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
			}
        	if(currentElement.equals("phone")){
				phone.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
        	}
            if(currentElement.equals("laptop")){
				laptop.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
            }
            if(currentElement.equals("voiceassistant")){
				voiceassistant.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
            }
			if(currentElement.equals("fitnesswatch")){
				fitnesswatch.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("smartwatch")){
				smartwatch.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("headphone")){
				headphone.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("virtualreality")) {
				virtualreality.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
        	}
			if(currentElement.equals("pettracker")) {
				pettracker.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("accessory")) {
				accessory.setPrice(Double.parseDouble(elementValueRead));
				productPrice = Double.parseDouble(elementValueRead);
			}
			return;
        }

        if (element.equalsIgnoreCase("rebateamount")) {
            if(currentElement.equals("tv")){
				manufacturerRebate = Double.parseDouble(elementValueRead);
            }
			if(currentElement.equals("soundsystem")){
				manufacturerRebate = Double.parseDouble(elementValueRead);
			}
        	if(currentElement.equals("phone")){
				manufacturerRebate = Double.parseDouble(elementValueRead);
        	}
            if(currentElement.equals("laptop")){
				manufacturerRebate = Double.parseDouble(elementValueRead);
            }
            if(currentElement.equals("voiceassistant")){
				manufacturerRebate = Double.parseDouble(elementValueRead);
            }
			if(currentElement.equals("fitnesswatch")){
				manufacturerRebate = Double.parseDouble(elementValueRead); 
			}
			if(currentElement.equals("smartwatch")){
				manufacturerRebate = Double.parseDouble(elementValueRead);  
			}
			if(currentElement.equals("headphone")){
				manufacturerRebate = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("virtualreality")) {
				manufacturerRebate = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("pettracker")) {
				manufacturerRebate = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("accessory")){
				manufacturerRebate = Double.parseDouble(elementValueRead);       
			}
			return;
	    }

        if (element.equalsIgnoreCase("discount")) {
            if(currentElement.equals("tv")){
				tv.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead);
            }
			if(currentElement.equals("soundsystem")){
				soundsystem.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead);
			}
        	if(currentElement.equals("phone")){
				phone.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead);
        	}
            if(currentElement.equals("laptop")){
				laptop.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead);
            }
            if(currentElement.equals("voiceassistant")){
				voiceassistant.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead);
            }
			if(currentElement.equals("fitnesswatch")){
				fitnesswatch.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead); 
			}
			if(currentElement.equals("smartwatch")){
				smartwatch.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead);  
			}
			if(currentElement.equals("headphone")){
				headphone.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("virtualreality")){
				virtualreality.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("pettracker")){
				pettracker.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead);
			}
			if(currentElement.equals("accessory")){
				accessory.setDiscount(Double.parseDouble(elementValueRead));
				productDiscount = Double.parseDouble(elementValueRead);       
			}
			return;
		}
			
		if (element.equalsIgnoreCase("count")) {
            if(currentElement.equals("tv")){
				count = Integer.parseInt(elementValueRead);
            }
			if(currentElement.equals("soundsystem")){
				count = Integer.parseInt(elementValueRead);
			}
        	if(currentElement.equals("phone")){
				count = Integer.parseInt(elementValueRead);
        	}
            if(currentElement.equals("laptop")){
				count = Integer.parseInt(elementValueRead);
            }
            if(currentElement.equals("voiceassistant")){
				count = Integer.parseInt(elementValueRead);
            }
			if(currentElement.equals("fitnesswatch")){
				count = Integer.parseInt(elementValueRead); 
			}
			if(currentElement.equals("smartwatch")){
				count = Integer.parseInt(elementValueRead);  
			}
			if(currentElement.equals("headphone")){
				count = Integer.parseInt(elementValueRead);
			}
			if(currentElement.equals("virtualreality")) {
				count = Integer.parseInt(elementValueRead);
			}
			if(currentElement.equals("pettracker")) {
				count = Integer.parseInt(elementValueRead);
			}
			if(currentElement.equals("accessory")){
				count = Integer.parseInt(elementValueRead);       
			}
			MySqlDataStoreUtilities.insertProduct(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,manufacturerRebate,count);
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
		new SaxParserDataStore(TOMCAT_HOME+"\\webapps\\BestDeal\\ProductCatalog.xml");
    } 
}
