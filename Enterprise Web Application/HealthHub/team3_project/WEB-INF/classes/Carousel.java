/* This code is used to implement carousel feature in Website. Carousels are used to implement slide show feature. This  code is used to display 
all the accessories related to a particular product. This java code is getting called from cart.java. The product which is added to cart, all the 
accessories realated to product will get displayed in the carousel.*/
  
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;	

			
			
public class Carousel{
			
	public String  carouselfeature(Utilities utility){
				
		ProviderRecommenderUtility prvdRecUtility = new ProviderRecommenderUtility();
		
		StringBuilder sb = new StringBuilder();
		String myCarousel = null;	
		String name = null;
		HashMap<String,String> prdRecmMap = new HashMap<String,String>();
		prdRecmMap = prvdRecUtility.readOutputFile();
		
		int l = 0;
		for(String user: prdRecmMap.keySet())
		{
			if(user.equals(utility.username()))
			{
				String providers = prdRecmMap.get(user);
				providers = providers.replace("[","");
				providers = providers.replace("]","");
				providers = providers.replace("\"", " ");
				ArrayList<String> providersList = new ArrayList<String>(Arrays.asList(providers.split(",")));
		        myCarousel = "myCarousel"+l;
					
				sb.append("<div class='post' style='margin-top:600px;margin-left:360px;height:500px;'><h2 class='title meta'>");
				sb.append("<a style='font-size: 24px;'>"+""+" Recommended Providers</a>");
				
				sb.append("</h2>");

				sb.append("<div class='container'>");
				/* Carousels require the use of an id (in this case id="myCarousel") for carousel controls to function properly.
				The .slide class adds a CSS transition and animation effect, which makes the items slide when showing a new item.
				Omit this class if you do not want this effect. 
				The data-ride="carousel" attribute tells Bootstrap to begin animating the carousel immediately when the page loads. 
		 
				*/
				sb.append("<div class='carousel slide' id='"+myCarousel+"' data-ride='carousel'>");
				
				/*The slides are specified in a <div> with class .carousel-inner.
				The content of each slide is defined in a <div> with class .item. This can be text or images.
				The .active class needs to be added to one of the slides. Otherwise, the carousel will not be visible.
				*/
				sb.append("<div class='carousel-inner' style='height:400px;margin-top:30px;'>");
						
				int k = 0;
				
				for(String prod : providersList)
				{
					prod = prod.replace("'", "");
					String[] prodObj = new String[5];
					prodObj = ProviderRecommenderUtility.getProvider(prod.trim());
					
					if (k==0)
					{
						
						sb.append("<div class='item active'><div class='col-md-6' style = 'border :1px solid #cfd1d3;'>");
					}
					else
					{
						sb.append("<div class='item'><div class='col-md-6' style = 'border :1px solid #cfd1d3';>");
					}
					sb.append("<div id='shop_item'><h2 class='title meta' style='padding-bottom: 70px;'>");
					sb.append("<a style='font-size: 32px;color: #000 !important;'>Dr. "+prodObj[1]+"</a></h2>");
					sb.append("<h4><strong>"+prodObj[3]+"</strong></h4>");
					sb.append("<h4><strong>"+prodObj[4]+"</strong></h4><ul>");
					sb.append("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+prodObj[1]+"'>"+
						"<input type='hidden' name='type' value='"+prodObj[3]+"'>"+
						"<input type='hidden' name='maker' value='"+prodObj[1]+"'>"+
						"<input type='submit' value='View Review' class='btnreview'></form></li>");
					sb.append("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+prodObj[1]+"'>"+
						"<input type='hidden' name='type' value='"+prodObj[3]+"'>"+
						"<input type='hidden' name='maker' value='"+prodObj[1]+"'>"+
						"<input type='submit' value='Write Review' class='btnreview'></form></li>");
					sb.append("<li><form method='post' action='AddWishlist'>"+"<input type='hidden' name='name' value='"+prodObj[1]+"'>"+
						"<input type='hidden' name='type' value='"+prodObj[3]+"'>"+
						"<input type='hidden' name='maker' value='"+prodObj[1]+"'>"+
						"<input type='hidden' name='pid' value='"+prodObj[2]+"'>"+
						"<input type='hidden' name='address' value='"+prodObj[4]+"'>"+
						"<input type='submit' value='Add to Wishlist' class='btnreview'></form></li>");
					sb.append("<li><form method='post' action='Appointment'>" +
						"<input type='hidden' name='id' value='"+prodObj[2]+"'>"+
						"<input type='hidden' name='name' value='"+prodObj[1]+"'>"+
						"<input type='hidden' name='type' value='"+prodObj[3]+"'>"+
						"<input type='submit' class='btnbuy' value='Book an Apointment Now'></form></li></ul>");
					sb.append("</div></div>");
					sb.append("</div>");
				
					k++;
					
				}
				
			
				sb.append("</div>");
				/*		The "Left and right controls" part:
					This code adds "left" and "right" buttons that allows the user to go back and forth between the slides manually.
				The data-slide attribute accepts the keywords "prev" or "next", which alters the slide position relative to its current position.
				*/
				sb.append("<a class='left carousel-control' href='#"+myCarousel+"' data-slide='prev' style = 'width : 5% ;background-color:#D7e4ef; opacity :1'>"+
						"<span class='glyphicon glyphicon-chevron-left' style = 'color :red'></span>"+
						"<span class='sr-only'>Previous</span>"+
						"</a>");
				sb.append("<a class='right carousel-control' href='#"+myCarousel+"' data-slide='next' style = 'width : 5% ;background-color:#D7e4ef; opacity :1'>"+
						"<span class='glyphicon glyphicon-chevron-right' style = 'color :red'></span>"+

							"<span class='sr-only'>Next</span>"+
							"</a>");

			
				sb.append("</div>");
			
				sb.append("</div></div>");
				sb.append("");
				l++;
			
				}
			}
			return sb.toString();
		}
	}

	