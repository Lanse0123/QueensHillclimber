-- Initialize database
source Initialize.sql

-- Your SQL statement goes here
SELECT RegisteredName, Height, BirthDate
FROM Horse

WHERE (Height >= 15 AND Height <= 16)

OR (BirthDate >= '2020-01-01');


CREATE TABLE Movie(
    ID SMALLINT UNSIGNED,
    Title VARCHAR(50),
    Rating CHAR(4),
    ReleaseDate DATE,
    Budget DECIMAL(8,2)
);

SHOW TABLES;


SELECT *
FROM Country
WHERE (Continent = 'Africa') AND (Region = 'Central' OR Region = 'Eastern');


SELECT CountryName, Population, IndepYear
FROM Country
WHERE IndepYear IS NOT null
ORDER BY Population DESC;



SELECT productCode, productName, LEFT(productName, 4) as year
FROM products
WHERE productLine = 'Vintage Cars' AND LEFT(productName, 4) IN ('1940', '1941')
ORDER BY year DESC,
productName ASC;



SELECT customerName, city, state, postalCode, concat(city, ',', COALESCE(state, ' '), ' ', postalCode)
FROM customers
WHERE 1;




INSERT INTO titanic_2 (id, passenger, age, fare, date_of_birth)
VALUES (1, 'Mr.Owen Harris Brand', 22, 7.25, '1890-12-12');

INSERT INTO titanic_2 (id, passenger, age, fare, date_of_birth)
VALUES (25, 'Lanse', 20, 65.12, '2005-1-25');

UPDATE titanic_2
set age = 25,
fare = 8
WHERE id = 20



UPDATE titanic_2
set age = age - 2,
fare = fare + 2.63
WHERE age = 35


UPDATE titanic_2
set date_of_birth = '1877-1-8'
WHERE id = 50


UPDATE titanic_2
set age = age - 2,
fare = fare + 2.63
WHERE year_of_birth AFTER '1880-1-1'



CREATE TABLE sweet_reviews(
    Name VARCHAR(50),
    sweet_type VARCHAR(50),
    last_eaten DATE,
    Rating DECIMAL(3,1),
    Review TEXT
);


ALTER TABLE sweet_reviews RENAME COLUMN sweet_reviews TO anythingElse

ALTER TABLE sweet_reviews DROP COLUMN sweet_reviews

DROP TABLE sweet_reviews;







drop table if exists sweet_reviews;

create table if not exists sweet_reviews
(
	id int not null,
	name varchar(100),
	sweet_type varchar(50),
	last_eaten date,
	rating decimal(3,1),
	review varchar(1000),
	manufacturer varchar(100)
);



INSERT INTO sweet_reviews (id,name, sweet_type, last_eaten, rating, review)
VALUES (1, 'Oreos', 'Baked', '2015-01-11', 1.2, 'Tasted like cardboard');

INSERT INTO sweet_reviews (id,name, sweet_type, last_eaten, rating, review)
VALUES (1, 'Oreos', 'Baked', '2023-07-11', 4.0, 'Delicious');

INSERT INTO sweet_reviews (id,name, sweet_type, last_eaten, rating, review)
VALUES (1, 'Oreos', 'Baked', '2015-01-11', 3.5, 'They were ok');

INSERT INTO sweet_reviews (id,name, sweet_type, last_eaten, rating, review)
VALUES (1, 'Oreos', 'Baked', '2023-05-15', 4.0, 'Delicious');
Sweet-Review-Refresh.sql
Displaying Sweet-Review-Refresh.sql.





INSERT INTO Horse (registeredName,Breed, Height, BirthDate) VALUES
  ('Babe', 'Quarter Horse', 15.3, '2015-02-10');

  INSERT INTO Horse (registeredName,Breed, Height, BirthDate) VALUES
  ('Independence', 'Holsteiner', 16.0, '2017-03-13');

  INSERT INTO Horse (registeredName,Breed, Height, BirthDate) VALUES
  ('Ellie', 'Saddlebred', 15.0, '2016-12-22');

  INSERT INTO Horse (registeredName,Breed, Height, BirthDate) VALUES
  (NULL, 'Egyptian Arab', 14.9, '2019-10-12');





UPDATE Horse
set Height = 15.6
WHERE ID = 2;

UPDATE Horse
set RegisteredName = 'Lady Luck',
BirthDate = '2015-5-1'
WHERE ID = 4;

UPDATE Horse
set Breed = NULL
WHERE BirthDate >= '2016-12-22';

SELECT * FROM Horse;



DELETE FROM Horse
WHERE ID = 5 OR Breed = 'Paint' OR Breed = 'Holsteiner' OR BirthDate < '2013-3-13';

SELECT * FROM HORSE;


CREATE TABLE Horse (
    ID SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    RegisteredName VARCHAR(15) NOT NULL,
    Breed VARCHAR(20) NOT NULL,
    Height DECIMAL(3,1) CHECK (Height >= 10.0 AND Height <= 20.0),
    BirthDate DATE CHECK (BirthDate >= '2015-01-01')
);




CREATE TABLE Student (
    ID SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(20) NOT NULL,
    LastName VARCHAR(30) NOT NULL,
    Street VARCHAR(50) NOT NULL,
    City VARCHAR(20) NOT NULL,
    State CHAR(2) NOT NULL DEFAULT 'TX',
    Zip MEDIUMINT UNSIGNED NOT NULL,
    Phone CHAR(10) NOT NULL,
    Email VARCHAR(30) NOT NULL UNIQUE
);


SELECT account_id, open_date, last_activity_date
FROM account
WHERE last_activity_date > '2024-7-15' AND last_activity_date < '2025-11-25';



SELECT CountryName, SurfaceArea
FROM Country
WHERE (LEFT(CountryName, 1) = 'T' AND Population > 1000000)
OR (RIGHT(CountryName, 1) = 'N' AND LifeExpectancy < 75)
OR !(CountryName LIKE '%East%');



SELECT Continent, sum(SurfaceArea) AS sumSurfaceArea, avg(Population) AS avgPopulation
FROM Country
WHERE Continent = 'Africa' OR Continent = 'Asia'
GROUP BY Continent
ORDER BY sumSurfaceArea ASC;


SELECT CountryName
FROM Country
WHERE Region != 'North America' AND CountryName in (
    SELECT CountryName
	FROM CountryLanguage
	WHERE SpokenLanguage = 'English'
);



CREATE TABLE Student (
    ID SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Major VARCHAR(30),
    Age SMALLINT NOT NULL,
    GPA DECIMAL(2,1),
    Birthday DATE
);


UPDATE Student
set GPA = 3.5
WHERE GPA IS NULL;


ALTER TABLE Student
ADD COLUMN Minor VARCHAR(50);


DELETE FROM Student
-- only month can be surrounded by 2 dashes, so if month is 5 or MAY, delete the student
WHERE Birthday = '%-5-%'


SELECT RegisteredName, Height
FROM Horse
WHERE Height > (
    SELECT AVG(Height)
    FROM Horse
)
ORDER BY Height ASC;


SELECT Year, COUNT(*)
FROM Movie
GROUP BY Year
ORDER BY COUNT(*) DESC;



SELECT *
FROM customer c
JOIN account a on c.cust_id = a.cust_id;



select a.cust_id,i.fname,i.lname,product_cd, avail_balance
from account a
left join individual i on a.cust_id = i.cust_id;

select i.fname,i.lname, sum(avail_balance)
from account a
left join individual i on a.cust_id = i.cust_id
group by i.fname,i.lname;

select a.cust_id,b.name,product_cd, avail_balance
from account a
left join business b on a.cust_id = b.cust_id;

select a.cust_id,COALESCE (concat(i.lname,', ',i.fname),b.name),product_cd, avail_balance
from account a
left join individual i on a.cust_id = i.cust_id
left join business b on a.cust_id = b.cust_id


select e.emp_id,i.fname,i.lname,superior_emp_id, supervisorFirstName, supervisorLastName
from employee e
left join individual i on e.cust_id = i.cust_id;


SELECT w.wine_type, wr.region_id, wy.winery_name
FROM wine w
JOIN winery wy ON wy.winery_id = w.winery_id
JOIN wine_region wr ON wr.region_id = wy.region_id
ORDER BY w.wine_type ASC, wr.region_id ASC, wy.winery_name ASC;


SELECT w.wine_type, wr.region_name, wy.winery_name, w.wine_name, w.wine_id
FROM wine w
JOIN winery wy ON wy.winery_id = w.winery_id
JOIN wine_region wr ON wr.region_id = wy.region_id
JOIN wine_variety wv ON w.wine_id
JOIN wine_grape_variety wg ON wg.variety_id = wv.variety_id
WHERE wg.variety IN ('Merlot', 'Cabernet')
ORDER BY w.wine_type ASC, wr.region_id ASC, wy.winery_name ASC;


subquery JOIN is like a local variable


CREATE TABLE temp_wine_region (
region_id int NOT NULL PRIMARY KEY,
region_name VARCHAR(100)
)


CREATE TABLE temp_wine_region2
AS SELECT = FROM wine.wine_region


SELECT e.FirstName AS Employee, m.FirstName AS Manager
FROM Employee AS e
INNER JOIN Employee AS m ON e.ManagerID = m.ID
ORDER BY e.FirstName;


SELECT ls.LessonDateTime, ls.HorseID, s.FirstName, s.LastName
FROM LessonSchedule AS ls
INNER JOIN Student AS s ON ls.StudentID = s.ID
ORDER BY ls.LessonDateTime ASC, ls.HorseID ASC;


SELECT  ls.LessonDateTime, s.FirstName, s.LastName, h.RegisteredName
FROM LessonSchedule AS ls
INNER JOIN Horse AS h ON ls.HorseID = h.ID
LEFT JOIN Student AS s ON ls.StudentID = s.ID
WHERE DATE(ls.LessonDateTime) = '2020-02-01'
ORDER BY ls.LessonDateTime ASC, h.RegisteredName ASC;


SELECT wy.winery_name, w.wine_name, w.year, sum(wi.on_hand) as on_hand_per_year
FROM wine w
JOIN winery wy ON wy.winery_id = w.winery_id
JOIN wine_inventory wi ON wi.wine_id = w.wine_id
GROUP BY wy.winery_name, w.wine_name, w.year;


select wv.winery_name, w.wine_name,w.year
,sum(on_hand) OVER (PARTITION BY wv.winery_name,w.wine_name,w.year) as on_hand_inventory_per_year
,sum(on_hand) OVER (PARTITION BY wv.winery_name,w.wine_name) as on_hand_inventory
from wine w
join winery wv on w.winery_id = wv.winery_id
join wine_inventory wi on w.wine_id = wi.wine_id



ALTER TABLE address
DROP COLUMN phone;

CREATE TABLE phone (
    phone_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    country_code INTEGER UNSIGNED NOT NULL,
    phone_number INTEGER UNSIGNED NOT NULL,
    phone_type VARCHAR(12),
    PRIMARY KEY (phone_id)
);

ALTER TABLE store
ADD COLUMN phone_id INTEGER UNSIGNED UNIQUE,
ADD CONSTRAINT fk_store_phone
    FOREIGN KEY (phone_id) REFERENCES phone(phone_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE staff
ADD COLUMN phone_id INTEGER UNSIGNED UNIQUE,
ADD CONSTRAINT fk_staff_phone
    FOREIGN KEY (phone_id) REFERENCES phone(phone_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE customer
ADD COLUMN phone_id INTEGER UNSIGNED UNIQUE,
ADD CONSTRAINT fk_customer_phone
    FOREIGN KEY (phone_id) REFERENCES phone(phone_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;


SELECT CONCAT(wt.title, ' ', wc.firstname, ' ', wc.surname) AS customerName, wc.address, wc.city, wc.state, wc.zipcode, wcty.country
FROM wine_customer wc
JOIN wine_titles wt ON wt.title_id = wc.title_id
JOIN wine_countries wcty ON wcty.country_id = wc.country_id;


SELECT CONCAT(wc.firstname, ' ', wc.surname) AS customerName, CONCAT('$', FORMAT(sum(wi.qty * wi.price),2)) AS sale
FROM wine_orders wo
JOIN wine_customer wc ON wc.cust_id = wo.cust_id
JOIN wine_items wi ON wi.order_id = wo.order_id AND wi.cust_id = wo.cust_id
WHERE wo.date > '2000-01-01' AND wo.date < '2000-12-31'
GROUP BY customerName
ORDER BY customerName ASC;


SELECT CONCAT(wc.firstname, ' ', wc.surname) AS customerName, sum(wi.qty) AS orderTotal
FROM wine_orders wo
JOIN wine_customer wc ON wc.cust_id = wo.cust_id
JOIN wine_items wi ON wi.order_id = wo.order_id AND wi.cust_id = wo.cust_id
GROUP BY customerName
ORDER BY orderTotal ASC


INSERT INTO sec_user(user_id, username, password, email, first_name, last_name, home_folder_id)
VALUES(1, 'Cindy Williams', 'try', 'confidentialcallibrator@yahoo.com', 'Cindy', 'Williams', 4);

INSERT INTO sec_user(user_id, username, password, email, first_name, last_name, home_folder_id)
VALUES(2, 'Najela Moreija', 'again.', 'jfelder@egon.egu', 'Najela', 'Moreija', 57);

INSERT INTO sec_group(group_id, group_name)
VALUES(1, 'TeamA');

INSERT INTO sec_group(group_id, group_name)
VALUES(2, 'TeamB');

-- insert cindy into teamA
INSERT INTO sec_group_users(user_id, group_id)
VALUES(1, 1);

-- insert najela into teamA
INSERT INTO sec_group_users(user_id, group_id)
VALUES(2, 1);

-- insert najela into teamB
INSERT INTO sec_group_users(user_id, group_id)
VALUES(2, 2);

INSERT INTO sec_folder(folder_id, folder_name, parent_folder)
VALUES(1, 'Cindy William''s Folder', 303);

INSERT INTO sec_folder(folder_id, folder_name, parent_folder)
VALUES(1, 'Najela Moreija''s Folder', 303);

































































































































