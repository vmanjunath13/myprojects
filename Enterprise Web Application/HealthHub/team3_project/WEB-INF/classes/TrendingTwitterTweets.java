import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/TrendingTwitterTweets")
public class TrendingTwitterTweets extends HttpServlet
{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession();
		Utilities utility = new Utilities(request,pw);

		String TOMCAT_HOME = System.getProperty("catalina.home");

		String screenname = request.getParameter("screenname");
		String querystring = request.getParameter("querystring");

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #000 !important;'>Trending Tweets on Twitter</a></h2>");

		if(querystring == "")
		{
			System.out.println("Inside screenname");
			//exectue the python file to get the tweets by passing the screenname or the querystring as parameter
			ProcessBuilder pb = new ProcessBuilder("python", "\"" + TOMCAT_HOME + "\"" + "\\webapps\\team4_2\\Python\\TrendingTweets.py",""+screenname,""+"ScreenName");
			java.lang.Process process = pb.start();

			//wait for the completion of python code execution
			try
			{
				Thread.sleep(1500);
			}
			catch(InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}

			//read the tweets from the text file and display
			BufferedReader reader = new BufferedReader(new FileReader (new File(TOMCAT_HOME + "\\webapps\\team4_2\\TweetsByScreenName.txt")));
			String line=reader.readLine();
			while(line!=null)
			{
			  pw.print("<p style='padding-left:20px; color:#00B3B3; font-size:16px;'>" + line + "</p></br>");
			  line = reader.readLine();
			}
			reader.close();
		}
		else if(screenname == "")
		{
			System.out.println("Inside querystring");
			ProcessBuilder pb = new ProcessBuilder("python", "\"" + TOMCAT_HOME + "\"" + "\\webapps\\team4_2\\Python\\TrendingTweets.py",""+querystring,""+"QueryString");
			java.lang.Process process = pb.start();

			try
			{
				Thread.sleep(1500);
			}
			catch(InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}

			BufferedReader reader = new BufferedReader(new FileReader (new File(TOMCAT_HOME + "\\webapps\\team4_2\\Tweets.txt")));
			String line=reader.readLine();
			while(line!=null)
			{
			  pw.print("<p style='padding-left:20px; color:#00B3B3; font-size:16px;'>" + line + "</p></br>");
			  line = reader.readLine();
			}
			reader.close();
		}

		pw.print("</div></div>");
		utility.printHtml("Footer.html");
	}
}
