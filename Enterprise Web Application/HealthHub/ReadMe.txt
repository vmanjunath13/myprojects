                       				*****************************************************************************    
								          Project - HEALTHHUB - User Doccument 
						*****************************************************************************

Team-3
------
Vaishnavi Manjunath – A20446043 
Chandu Chinta - A20450371

------------------------------------------------------------------------------------------------------------------------

1) Total lines of code written:  
Java - 5431
Python - 106
HTML - 136
JavaScript - 856

------------------------------------------------------------------------------------------------------------------------

2) Features from the assignment implemented in project:
	2.1 User Account/Profile/Transaction management & MySQL ---------------- IMPLEMENTED
	2.2 Recommender -------------------------------------------------------- IMPLEMENTED
	2.3 Twitter matches ---------------------------------------------------- IMPLEMENTED
	2.4 Analytics & Visual Reports ----------------------------------------- IMPLEMENTED
	2.5 Reviews & Trending & MongoDB --------------------------------------- IMPLEMENTED
	2.6 Auto-Complete Search feature --------------------------------------- IMPLEMENTED
	2.7 Google MAPS - Near ME search feature ------------------------------- IMPLEMENTED
	2.8 Knowledge Graph Searches & Neo4J ----------------------------------- IMPLEMENTED

Brief Description of above implemented features:
	a) A servlet based Web Application ---------------------------------------------------------- IMPLEMENTED
	b) Support of different user roles - user, admin -------------------------------------------- IMPLEMENTED
	   admin - add/update/delete a providers, view reports and graph charts
	   user - search hospitals, doctors, healthclubs, pharmacy, insurances based on zipcode, add/delete these to/from wishlist, 
                  book/cancel/view appointments 
	c) Implementation of CRUD operations in mySQL database -------------------------------------- IMPLEMENTED
	d) Interface to the NoSQL database - mongoDB ------------------------------------------------ IMPLEMENTED 
	e) Submit reviews and store them in mongoDB. View the reviews in our application------------- IMPLEMENTED
	f) Trending and Data Analytics Feature ------------------------------------------------------ IMPLEMENTED
	g) Providers reports available only to the admin -------------------------------------------- IMPLEMENTED
	h) Bar graph reports availabe only to the admin --------------------------------------------- IMPLEMENTED
	i) Search Auto-Completion feature ----------------------------------------------------------- IMPLEMENTED
	j) Use of API to show the data fetched in real time ----------------------------------------- IMPLEMENTED
	k) All new code added for MySQL are placed in MySQLDataStoreUtilities.java ------------------ IMPLEMENTED
	l) All new code added for mongoDB are placed in MongoDBDataStoreUtilities.java--------------- IMPLEMENTED
	m) Implentation of JS, Java, Python, SQL, NoSQL, Tomcat, AJAX, HTML, CSS, Servlets, Neo4J --- IMPLEMENTED
	n) Carousel and Recommendation feature ------------------------------------------------------ IMPLEMENTED

3) Instructions on how to deploy and run the project:
	1) Copy "team3_project" folder to the tomcat webapp folder.
	2) Run the MySQL commands given in the file my_sql.txt in mySQL workbench
	3) Start the MySQL database with database name as healthhub.
	4) Start tomcat server
	5) Start the MongoDB server with database name as CustomerReviews 
	   and collections name as ProviderReviews
	6) In order to start the application open the browser and type http://localhost/team3_project

 	Note:
	To compile Java files, open the command prompt, go to C:\apache-tomcat-7.0.34\webapps\project\WEB-INF\classes folder and run the command javac *.java

------------------------------------------------------------------------------------------------------------------

Role Information:

There are 3 types of user roles:
	1) User
	2) Admin


1) User
	- Create his/her user profile
	- Search Providers 
	   1. Based on ZIP code in Header Nav links
           2. Based on Name in Search bar
           3. Based on complete list in Sidebar Nav links
	- View the detailed information about Providers
	- Write/View customer provided reviews for the providers 
	- Add/Delete the providers to/from wishlist  
	- Book/Cancel appointment and also view list of all appointments in Account page
	- Can view Trending page to check most reviewed providers by name, most reviewed providers by zipcode, highest rated providers
	- Can view real time trending tweets using Trending News on Twitter link in Sidebar
	   1. Tweets can be seen based on keywords
	   2. Tweets can be seen based on screenname

2) Admin
	- View all the providers posted in the application HEALTHHUB 
	- Analyse the data of each providers
	  1. Based on Provider Name
	  2. Based on Review Ratings
	  3. Based on Fees
	  4. Based on Zip Code
	  5. Based on City
	- Also analyse the data of each providers based on combination of above filters
	- View the chart or bar graph of each providers and their number of review ratings provided by users
	- View the bar graph of each providers and their experience in their respective field 

------------------------------------------------------------------------------------------------------------------------

The MSQL table creatiion details are given in the mySQL file.

------------------------------------------------------------------------------------------------------------------------

Project Folder Description:
	Team_3
	1. "team3_project" folder:
	    - images -> contains all the images required as part of this application
	    - Python -> contains all python script files 
	    - WEB-INF -> contains all java files and java class files
	    - HTML files
 	    - Javascript files
	    - csv files generated from python notebook
	    - Tweet text files generated from python script
	2. MySQL query file - having all the queries to create table required as part of application
	3. Recording #1 & Recording #2 -> Screen recording of our complete application (Two recordings are there as we had restriction from Screen-O-Matic while recording.)
	4. Team_3_phase1.pptx and Team_3_phase2.pptx - presentation of two phases which were given
	5. Output.pdf - Output file with all screenshots capturing all the functionalities and features
	6. ReadMe.txt - text file with detailed description of whole project
	


