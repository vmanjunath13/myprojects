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
	static DBCollection ProviderReviews;
	public static DBCollection getConnection()
	{
		MongoClient mongo;
		mongo = new MongoClient("localhost", 27017);

		DB db = mongo.getDB("CustomerReviews");
		ProviderReviews= db.getCollection("ProviderReviews");	
		return ProviderReviews; 
	}

	public static String insertReview(String username,String PName,String PType,String RName,String RFees,String  Rzip,String RCity,String RState,String UAge,String UGender,String UOccupation,String RRating,String RDate,String RText)
	{
		try
		{		
			getConnection();
			BasicDBObject doc = new BasicDBObject("title", "ProviderReviews").
				append("CustomerId", username).
				append("ProviderName", PName).
				append("ProviderFees", RFees).
				append("ProviderType", PType).
				append("CustomerName", RName).
				append("CustomerZIP", Rzip).
				append("CustomerCity", RCity).
				append("CustomerState", RState).
				append("CustomerAge", UAge).
				append("CustomerGender", UGender).
				append("CustomerOccupation", UOccupation).
				append("ReviewRating",Integer.parseInt(RRating)).
				append("ReviewDate", RDate).
				append("ReviewText", RText);
			ProviderReviews.insert(doc);
			
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
			DBCursor cursor = ProviderReviews.find();
			reviews=new HashMap<String, ArrayList<Review>>();
			while (cursor.hasNext())
			{
				BasicDBObject obj = (BasicDBObject) cursor.next();				

			   if(!reviews.containsKey(obj.getString("ProviderName")))
				{	
					ArrayList<Review> arr = new ArrayList<Review>();
					reviews.put(obj.getString("ProviderName"), arr);
				}
				ArrayList<Review> listReview = reviews.get(obj.getString("ProviderName"));	
					
				Review review =new Review(obj.getString("CustomerId"),obj.getString("ProviderName"),obj.getString("ProviderFees"),obj.getString("ProviderType"),obj.getString("CustomerName"),obj.getString("CustomerZIP"),obj.getString("CustomerCity"),obj.getString("CustomerState"),obj.getString("CustomerAge"),obj.getString("CustomerGender"),obj.getString("CustomerOccupation"),obj.getString("ReviewRating"),obj.getString("ReviewDate"),obj.getString("ReviewText"));
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


	public static ArrayList <Mostreviewedzip> mostreviewedZip()
	{
		ArrayList <Mostreviewedzip> mostreviewedzip = new ArrayList <Mostreviewedzip> ();
		try{
		  
			getConnection();
    		DBObject groupProviders = new BasicDBObject("_id","$CustomerZIP"); 
			groupProviders.put("count",new BasicDBObject("$sum",1));
			DBObject group = new BasicDBObject("$group",groupProviders);
			DBObject limit=new BasicDBObject();
    		limit=new BasicDBObject("$limit",5);
	  
			DBObject sortFields = new BasicDBObject("count",-1);
			DBObject sort = new BasicDBObject("$sort",sortFields);
			AggregationOutput output = ProviderReviews.aggregate(group,sort,limit);
    		for (DBObject res : output.results()) 
    		{
				String zipcode =(res.get("_id")).toString();
	        	String count = (res.get("count")).toString();	
	        	Mostreviewedzip mostrvwdzip = new Mostreviewedzip(zipcode,count);
				mostreviewedzip.add(mostrvwdzip);
	  		}
		}
		catch (Exception e)
		{ 
			System.out.println(e.getMessage());
		}
      	return mostreviewedzip;
	}


	public static ArrayList <Mostreviewed> mostreviewedProviders()
	{
		ArrayList <Mostreviewed> mostreviewed = new ArrayList <Mostreviewed> ();
		try
		{  
    		getConnection();
    		DBObject groupProviders = new BasicDBObject("_id","$ProviderName"); 
			groupProviders.put("count",new BasicDBObject("$sum",1));
			DBObject group = new BasicDBObject("$group",groupProviders);
			DBObject limit=new BasicDBObject();
    		limit=new BasicDBObject("$limit",5);
	  
			DBObject sortFields = new BasicDBObject("count",-1);
			DBObject sort = new BasicDBObject("$sort",sortFields);
			AggregationOutput output = ProviderReviews.aggregate(group,sort,limit);
	  
    		for (DBObject res : output.results()) 
    		{	  
				String providername =(res.get("_id")).toString();
        		String count = (res.get("count")).toString();	
        		Mostreviewed mostrvwd = new Mostreviewed(providername,count);
				mostreviewed.add(mostrvwd);
	  		}
		}
		catch (Exception e)
		{ 
			System.out.println(e.getMessage());
		}
      	return mostreviewed;
  	}


  	public static  ArrayList <Bestrating> topProviders()
  	{
		ArrayList <Bestrating> Bestrate = new ArrayList <Bestrating> ();
		try
		{
			getConnection();
			DBObject groupProviders = new BasicDBObject("_id","$ProviderName"); 
			groupProviders.put("average",new BasicDBObject("$avg","$ReviewRating"));
			DBObject group = new BasicDBObject("$group",groupProviders);
			DBObject limit=new BasicDBObject();
    		limit=new BasicDBObject("$limit",5);

    		DBObject sortFields = new BasicDBObject("average",-1);
			DBObject sort = new BasicDBObject("$sort",sortFields);
			AggregationOutput output = ProviderReviews.aggregate(group,sort,limit);

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
            dbObjIdMap.put("CustomerZIP", "$CustomerZIP");
            dbObjIdMap.put("ProviderName", "$ProviderName");
            DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
            groupFields.put("count", new BasicDBObject("$sum", 1));
            DBObject group = new BasicDBObject("$group", groupFields);

            DBObject projectFields = new BasicDBObject("_id", 0);
            projectFields.put("CustomerZIP", "$_id");
            projectFields.put("ProviderName", "$ProviderName");
            projectFields.put("reviewCount", "$count");
            DBObject project = new BasicDBObject("$project", projectFields);

            DBObject sort = new BasicDBObject();
            sort.put("reviewCount", -1);

            DBObject orderby = new BasicDBObject();            
            orderby = new BasicDBObject("$sort",sort);
            

            AggregationOutput aggregate = ProviderReviews.aggregate(group, project, orderby);

            for (DBObject result : aggregate.results()) {

                BasicDBObject obj = (BasicDBObject) result;
                Object o = com.mongodb.util.JSON.parse(obj.getString("CustomerZIP"));
                BasicDBObject dbObj = (BasicDBObject) o;
                Review review = new Review(dbObj.getString("ProviderName"), dbObj.getString("CustomerZIP"), obj.getString("reviewCount"), null);
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