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


@WebServlet("/DataVisualization")
public class DataVisualization extends HttpServlet {

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
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta' style='text-align:center;'><a style='font-size: 24px;'>Data Visualization</a></h2>"
                + "<div class='entry'>");
            
        pw.print("<h4><button id='btnGetChartData'>View Chart</h4>");
        pw.println("<div id='chart_div'></div>");
        pw.println("</div></div></div>");
        pw.println("<script type='text/javascript' src=\"https://www.gstatic.com/charts/loader.js\"></script>");
        pw.println("<script type='text/javascript' src='DataVisualization.js'></script>");
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
