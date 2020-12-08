import com.google.gson.Gson;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import com.mongodb.AggregationOutput;


@WebServlet("/GoogleMapSearch")
public class GoogleMapSearch extends HttpServlet {

    static DBCollection ProductReviews;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayPage(request, response, pw);
    }

    protected void displayPage(HttpServletRequest request, HttpServletResponse response, PrintWriter pw)
            throws ServletException, IOException {

        Utilities utility = new Utilities(request, pw);
        String zipCode = request.getParameter("zipCode");
		String pType = request.getParameter("pType");
		//System.out.println("pType in GS:" + pType);
		String[] latlon = MySqlDataStoreUtilities.getLatLong(zipCode); 
		HashMap<String,Provider> allproviders = new HashMap<String,Provider> ();
		try
		{
			allproviders = MySqlDataStoreUtilities.getProviders(pType, zipCode);
		}
		catch(Exception e)
		{

		}
		String temp = "";
		String address = "";
		for(Map.Entry<String, Provider> entry : allproviders.entrySet()){
			Provider provider = entry.getValue();
			address = provider.getStAddress() + ", " + provider.getCity() + ", " + provider.getState() + ", " + provider.getZipCode();
			temp += "{\"name\": \"" + provider.getName() + "\", ";
			temp += "\"id\": \"" + provider.getId() + "\",";
			temp += "\"speciality\": \"" + provider.getSpeciality() + "\",";
			temp += "\"latitude\": \"" + provider.getLatitude() + "\",";
			temp += "\"longitude\": \"" + provider.getLongitude() + "\",";
			temp += "\"address\": \"" + address + "\"},";
		}
		
		temp = temp.substring(0, temp.length()-1);
		temp = "[" + temp + "]";
		
		utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta' style='text-align:center;'><a style='font-size: 24px;'>" + pType + "'s Near " + zipCode + "</a></h2>"
                + "<div class='entry'>");        
		pw.println("<div id='chart_div'></div>");
        pw.println("<script src='https://polyfill.io/v3/polyfill.min.js?features=default'></script>");
		pw.println("<script type='text/javascript'>"
		 + "var lat = " + latlon[0] + ";"
		 + "var lon = " + latlon[1] + ";"
		 + "var tempStr = '" + temp + "';"
		 + "</script>");
        pw.println("<script type='text/javascript' src='GoogleMap.js'></script>");
		pw.println("<script type='text/javascript'" 
		+ "src='https://maps.googleapis.com/maps/api/js?key=AIzaSyD5Hoi7vH1_EdwkGGTVXVOCNiJBhMiNUVU&callback=initMap&libraries=visualization&v=weekly'"
		+ "defer></script>");
		/*pw.println("<div id='floating-panel'><button onclick='toggleHeatmap()'>Toggle Heatmap</button>" 
		+ "<button onclick='changeGradient()'>Change gradient</button><button onclick='changeRadius()'>Change radius</button>"
		+ "<button onclick='changeOpacity()'>Change opacity</button></div>");*/
		pw.println("<div id='map' style='height: 500px !important'></div>");
		pw.println("</div></div></div>");
        utility.printHtml("Footer.html");

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try 
        {
            ArrayList<Review> reviews = MongoDBDataStoreUtilities.selectReviewForChart();
            ArrayList<Review> topReviewsPerCity = getTop3InEveryCity(reviews);
            
            String reviewJson = new Gson().toJson(topReviewsPerCity);

            response.setContentType("application/JSON");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(reviewJson);

        } 
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
        }

    }

    //This method takes all the reviews and returns top 3 review in every city;
    private static ArrayList<Review> getTop3InEveryCity(ArrayList<Review> reviewList){

        //sorting the list in ascending order;
        Collections.sort(reviewList, new Comparator<Review>(){
            public int compare(Review r1, Review r2){
                return Integer.parseInt(r2.getRRating()) - Integer.parseInt(r1.getRRating());
            }
        });

       HashMap<String,Review> reviewMap = new HashMap<>();

       //Get list of cities in all reviews;
       ArrayList<String> zipCodeList = new ArrayList<>();
       for(Review review:reviewList){
            String zipCode = review.getRzip();
            if(!(zipCodeList.contains(zipCode))){
                zipCodeList.add(zipCode);
            }
       } 

       //get top 3 reviews for every city;
       ArrayList<Review> top3Reviews = new ArrayList<>();
       for(String zipCode:zipCodeList){
            ArrayList<Review> top3ReviewsCity = new ArrayList<>();
            for(Review review:reviewList){
                if(review.getRzip().equals(zipCode) && top3ReviewsCity.size()<3){
                    top3ReviewsCity.add(review);
                }
            }
            top3Reviews.addAll(top3ReviewsCity); 
       }

        return top3Reviews;
    }

}
