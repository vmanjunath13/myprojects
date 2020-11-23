						*****************************************************************************    
									BEST DEAL Manual - Assignment 5
						*****************************************************************************


Deployment and server intiation information

    1. Start tomcat server by executing the startup.bat file.
    2. Copy "BestDeal" folder to the tomcat webapps folder.
    3. In order to start the application open the browser and type http://localhost/BestDeal
    4. Start the MangoDB server with mongod.exe and mongo.exe with database name as UserReviews and collection name as ProductReviews
    5. Start the MySQL database with database name as bestdealdatabase and table names as registration, customerorders and productdetails.
----------------------------------------------------------------------------------------------------------------------------------------------

Note:

To compile Java files, we use this command:

javac -cp "C:\apache-tomcat-7.0.34\lib\mongo-java-driver-3.2.2.jar;C:\apache-tomcat-7.0.34\lib\servlet-api.jar;C:\apache-tomcat-7.0.34\lib\mysql-connector-java-5.1.23-bin.jar;C:\apache-tomcat-7.0.34\lib\gson-2.6.2.jar;" *.java						
----------------------------------------------------------------------------------------------------------------------------------------------

_____________________________________________________________________________

Role Information
______________________________________________________________________________
There are three ROLES:
1.Store Manager
2.Customer
3.Salesman
____________________________________________________________________________

Store Manager:
______________________________________________________________________________

1. Store Manager can add/update/delete product
2. Product added by Store Manager can be viewed by logging in as customer or salesman.
3. Store Manager can check the product inventory graph and details(Report).
4. Store Manager can check which are the products on sale and the products with rebate amount. 
5. Store Manager can check the product sales graph and report.
6. Store Manager can check the daily sales report.
_____________________________________________________________________________

Customer:
______________________________________________________________________________

First, Create a new user account as Customer 

Customer can,

1. login to the website and view product
2. Browse different products and add item to cart 
3. Update cart items by increasing the number of products
4. Customers will be able to write a review on particular product
5. Proceed and Check out from shopping cart by providing the user info
6. Can select in-store pickup or home delivery
7. View the order 
8. Cancel the order
9. view Trending products
__________________________________________________________________________

Salesman:
______________________________________________________________________________

First, Create a new user account as Salesman 

Salesman can,

1. login to the website and view product
2. Browse different products and add item to cart 
3. Update cart items by increasing the number of products
3. Customers will be able to write a review on particular product
4. Proceed and Check out from shopping cart by providing the user info
5. View the order 
6. Cancel the order
6. view Trending products
7. Create/Add a new user for the application.

------------------------------------------------------------------------------------

------------------------------------------------------
MySql Queries to Create Database and tables
------------------------------------------------------
CREATE DATABASE bestdealdatabase;

USE bestdealdatabase;

CREATE TABLE Registration(
username varchar(40),
password varchar(40),
repassword varchar(40),
usertype varchar(40));


CREATE TABLE CustomerOrders(
OrderId integer,
userName varchar(40),
orderName varchar(40),
orderPrice double,
userAddress varchar(40),
creditCardNo varchar(40),
FirstName varchar(40),
LastName varchar(40),
City varchar(40),
State varchar(40),
zip varchar(40),
PhoneNumber varchar(40),
CardName varchar(40),
Month varchar(40),
Year varchar(40),
cvv varchar(40),
purchaseDate varchar(10),
shipDate varchar(10),
Primary key(OrderId,userName,orderName)
);


CREATE TABLE Productdetails
(
ProductType varchar(20), 
Id varchar(40), 
productName varchar(40), 
productPrice double, 
productImage varchar(40), 
productManufacturer varchar(40), 
productCondition varchar(40), 
productDiscount double, 
rebateAmount double,
count integer, 
Primary key(Id)
);

CREATE TABLE storedetails
(
Id varchar(40), 
storeId varchar(40), 
street varchar(40), 
city varchar(40), 
state varchar(40), 
zipcode varchar(40), 
Primary key(Id)
);

CREATE TABLE transactions 
(
loginID varchar(40) not null,
Customer_Name varchar(40) not null,
Customer_Age integer not null,
Customer_Occupation varchar(40) not null,
CreditCardNumber varchar(40) not null,
Order_ID integer,
Order_Date varchar(10) not null,
Expected_Delivery_Date varchar(10) not null,
Actual_Delivery_Date varchar(10) not null,
Product_ID varchar(40) not null,
Product_Name varchar(40) not null,
Category varchar(40) not null,
Manufacturer varchar(40) not null,
Review_Rating integer,
Delivery_Tracking_ID varchar(40) not null,
Delivery_Type varchar(40) not null,
Delivery_Zip_Code varchar(40) not null,
Transaction_Status varchar(40) not null,
Order_Returned varchar(40) not null,
Order_Delivered_on_Time varchar(40) not null
);

---------------------------------------------------
MongoDB Queries
---------------------------------------------------

use UserReviews
db.createCollection("ProductReviews")
db.ProductReviews.find()

---------------------------------------------------
New features in this assignment:
---------------------------------------------------


1. Run the BestBuyDealMatches.ipynb and ProductRecommender.ipynb files using Jupyter Notebook to generate the required text and csv files
2. Based on these files deals and recommendations will be shown in content screen and cart page respectively
3. Recommendations shown can also be added to cart directly, reviews can also be shown and can also be written as well



