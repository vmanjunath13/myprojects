import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;
                	
public class MongoDBDataStoreUtilities
{
	static DBCollection ProductReviews;
	public static DBCollection getConnection()
	{
		MongoClient mongo;
		mongo = new MongoClient("localhost", 27017);

		DB db = mongo.getDB("UserReviews");
		ProductReviews= db.getCollection("ProductReviews");	
		return ProductReviews; 
	}


	public static String insertReview(String username,String PName,String PCategory,String PPrice,String MName,String RName,String  Rzip,String RCity,String RState,String ProductOnSale,
		String MRebate,String UID,String UAge,String UGender,String UOccupation,String RRating,String RDate,String RText)
	{
		try
		{		
			getConnection();
			BasicDBObject doc = new BasicDBObject("title", "ProductReviews").
				append("Username", username).
				append("ProductName", PName).
				append("ProductCategory", PCategory).
				append("ProductPrice",(int) Double.parseDouble(PPrice)).
				append("ManufacturerName", MName).
				append("RetailerName", RName).
				append("RetailerZIP", Rzip).
				append("RetailerCity", RCity).
				append("RetailerState", RState).
				append("ProductOnSale", ProductOnSale).
				append("ManufacturerRebate", MRebate).
				append("UserID", UID).
				append("UserAge", UAge).
				append("UserGender", UGender).
				append("UserOccupation", UOccupation).
				append("ReviewRating",Integer.parseInt(RRating)).
				append("ReviewDate", RDate).
				append("ReviewText", RText);
			ProductReviews.insert(doc);
			
			return "Successfull";
		}
		catch(Exception e)
		{
			return "UnSuccessfull";
		}	
			
	}

	public static HashMap<String, ArrayList<Review>> selectReview()
	{	
		HashMap<String, ArrayList<Review>> reviews=null;

		try
		{

			getConnection();
			DBCursor cursor = ProductReviews.find();
			reviews=new HashMap<String, ArrayList<Review>>();
			while (cursor.hasNext())
			{
				BasicDBObject obj = (BasicDBObject) cursor.next();				

			   if(!reviews.containsKey(obj.getString("ProductName")))
				{	
					ArrayList<Review> arr = new ArrayList<Review>();
					reviews.put(obj.getString("ProductName"), arr);
				}
				ArrayList<Review> listReview = reviews.get(obj.getString("ProductName"));	
					
				Review review =new Review(obj.getString("Username"),obj.getString("ProductName"),obj.getString("ProductCategory"),obj.getString("ProductPrice"),obj.getString("ManufacturerName"),
					obj.getString("RetailerName"),obj.getString("RetailerZIP"),obj.getString("RetailerCity"),obj.getString("RetailerState"),obj.getString("ProductOnSale"),
					obj.getString("ManufacturerRebate"),obj.getString("UserID"),obj.getString("UserAge"),obj.getString("UserGender"),obj.getString("UserOccupation"),obj.getString("ReviewRating"),
					obj.getString("ReviewDate"),obj.getString("ReviewText"));
				//add to review hashmap
				listReview.add(review);
			}
			return reviews;
		}
		catch(Exception e)
		{
		 reviews=null;
		 return reviews;	
		}
	}


	public static ArrayList <Mostsoldzip> mostsoldZip()
	{
		ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
		try{
		  
			getConnection();
    		DBObject groupProducts = new BasicDBObject("_id","$RetailerZIP"); 
			groupProducts.put("count",new BasicDBObject("$sum",1));
			DBObject group = new BasicDBObject("$group",groupProducts);
			DBObject limit=new BasicDBObject();
    		limit=new BasicDBObject("$limit",5);
	  
			DBObject sortFields = new BasicDBObject("count",-1);
			DBObject sort = new BasicDBObject("$sort",sortFields);
			AggregationOutput output = ProductReviews.aggregate(group,sort,limit);
    		for (DBObject res : output.results()) 
    		{
				String zipcode =(res.get("_id")).toString();
	        	String count = (res.get("count")).toString();	
	        	Mostsoldzip mostsldzip = new Mostsoldzip(zipcode,count);
				mostsoldzip.add(mostsldzip);
	  		}
		}
		catch (Exception e)
		{ 
			System.out.println(e.getMessage());
		}
      	return mostsoldzip;
	}


	public static ArrayList <Mostsold> mostsoldProducts()
	{
		ArrayList <Mostsold> mostsold = new ArrayList <Mostsold> ();
		try{
		  
	  
    		getConnection();
    		DBObject groupProducts = new BasicDBObject("_id","$ProductName"); 
			groupProducts.put("count",new BasicDBObject("$sum",1));
			DBObject group = new BasicDBObject("$group",groupProducts);
			DBObject limit=new BasicDBObject();
    		limit=new BasicDBObject("$limit",5);
	  
			DBObject sortFields = new BasicDBObject("count",-1);
			DBObject sort = new BasicDBObject("$sort",sortFields);
			AggregationOutput output = ProductReviews.aggregate(group,sort,limit);
	  
    		for (DBObject res : output.results()) 
    		{	  
				String prodcutname =(res.get("_id")).toString();
        		String count = (res.get("count")).toString();	
        		Mostsold mostsld = new Mostsold(prodcutname,count);
				mostsold.add(mostsld);
	  		}
		}
		catch (Exception e)
		{ 
			System.out.println(e.getMessage());
		}
      	return mostsold;
  	}


  	public static  ArrayList <Bestrating> topProducts()
  	{
		ArrayList <Bestrating> Bestrate = new ArrayList <Bestrating> ();
		try
		{
			getConnection();
			DBObject groupProducts = new BasicDBObject("_id","$ProductName"); 
			groupProducts.put("average",new BasicDBObject("$avg","$ReviewRating"));
			DBObject group = new BasicDBObject("$group",groupProducts);
			DBObject limit=new BasicDBObject();
    		limit=new BasicDBObject("$limit",5);

    		DBObject sortFields = new BasicDBObject("average",-1);
			DBObject sort = new BasicDBObject("$sort",sortFields);
			AggregationOutput output = ProductReviews.aggregate(group,sort,limit);

			for (DBObject res : output.results()) 
    		{	  
				String prodcutnm =(res.get("_id")).toString();
        		String rating = (res.get("average")).toString();	
        		Bestrating best = new Bestrating(prodcutnm,rating);
				Bestrate.add(best);
	  		}
		}
		catch (Exception e)
		{ 
			System.out.println(e.getMessage());
		}
   		return Bestrate;
	}

	//Get all the reviews grouped by product and zip code;
	public static ArrayList<Review> selectReviewForChart() {
		
        ArrayList<Review> reviewList = new ArrayList<Review>();
        try {

            getConnection();
            Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
            dbObjIdMap.put("RetailerZIP", "$RetailerZIP");
            dbObjIdMap.put("ProductName", "$ProductName");
            DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
            groupFields.put("count", new BasicDBObject("$sum", 1));
            DBObject group = new BasicDBObject("$group", groupFields);

            DBObject projectFields = new BasicDBObject("_id", 0);
            projectFields.put("RetailerZIP", "$_id");
            projectFields.put("ProductName", "$ProductName");
            projectFields.put("reviewCount", "$count");
            DBObject project = new BasicDBObject("$project", projectFields);

            DBObject sort = new BasicDBObject();
            sort.put("reviewCount", -1);

            DBObject orderby = new BasicDBObject();            
            orderby = new BasicDBObject("$sort",sort);
            

            AggregationOutput aggregate = ProductReviews.aggregate(group, project, orderby);

            for (DBObject result : aggregate.results()) {

                BasicDBObject obj = (BasicDBObject) result;
                Object o = com.mongodb.util.JSON.parse(obj.getString("RetailerZIP"));
                BasicDBObject dbObj = (BasicDBObject) o;
                Review review = new Review(dbObj.getString("ProductName"), dbObj.getString("RetailerZIP"), obj.getString("reviewCount"), null);
                reviewList.add(review);
                
            }
            return reviewList;

        }

        catch (Exception e) 
		{
            reviewList = null;
            return reviewList;
        }

    }
}	