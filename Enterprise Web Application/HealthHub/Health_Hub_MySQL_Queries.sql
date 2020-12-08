use healthhub;

create table wishlist(
username varchar(40),
pname varchar(40),
pid varchar(40),
ptype varchar(40),
paddress varchar(40));

create table bookinglist(
fullname varchar(40),
date varchar(10),
time varchar(10),
pname varchar(40),
pid varchar(40)
crn varchar(20),
cid varchar(20),
ptype varchar(40));

create table latlonlist(
latitude varchar(20),
longitude varchar(20),
zipcode varchar(5),
primary key(zipcode)); 

create table providerslist(
id varchar(40),
name varchar(40),
staddress varchar(50),
city varchar(30),
state varchar(10),
zipcode varchar(5),
speciality varchar(40),
image varchar(40),
experience varchar(40),
fees double,
type varchar(30),
latitude varchar(20),
longitude varchar(20),
primary key(id)
);

CREATE TABLE registration (
username varchar(40) DEFAULT NULL,
password varchar(40) DEFAULT NULL,
repassword varchar(40) DEFAULT NULL,
firstname varchar(40) DEFAULT NULL,
lastname varchar(40) DEFAULT NULL,
email varchar(40) DEFAULT NULL,
usertype varchar(40) DEFAULT NULL
);

