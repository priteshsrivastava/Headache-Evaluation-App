Introduction:
-------------
HEMA (Headache Evaluation by Mobile App) is an Android-based Application that uses the ICHD-3 criteria on responses from the patient and efficiently classify headaches in different categories/sub-categories. 

The application makes use of branching logic to minimize duration of test.  Only the relevant questions are presented during the evaluation. Responses from the patient are processed with application and further automate the classification of headache in different categories/sub-categories. 

The questionnaires can be asked in Hindi and English language from the patient. Application provides facility not only to see test status/summary of patient but also to see status/summary of test which were conducted in past. Application can generate different types of report that can be used for further studies.


Technologies/Tools Used:
----------------------
1. Android Studio ver 4.0: IDE for developing android apps
2. SQLite: Embedded database software for local/client storage in application software
3. MySQL: Relational Database Management System to store/retrieve data
4. Apache: Web server to host REST services
5. Java: Used to code logic for app's functionality
6. PHP: Used for web-service implementation at web-server

I have installed AMPPS software stack at Mac platform which contains Apache, Mysql and PHP. 


Run Environment:
----------------
1. Code is divided into two parts:
	(a) Client App Side
	(b) Server Side
2. Copy the 'Server Side' files and folder under 'Ampps/www' directory.
3. Run Apache, Php and Mysql using ampps UI.
4. Open following url and create 'test' database:
	http://localhost:8080/phpmyadmin/server_databases.php
   Run test.sql script inside 'test' database. Here 8080 is the default port for Apache.
5. Change following variables in DBConn.php file as per your configuration:
	define('DB_HOST', 'localhost');
	define('DB_USER', 'root');
	define('DB_PASS', '<db_password>');
	define('DB_NAME', 'test');

   Web service call can be verified by following url:
	http://localhost:8080/DBAPI.php?apicall=dbReadAllPatientTestBasicInfo

6. Unzip and Open the Android project 'MyApplication' from 'Client App Side' folder using Android Studio.
7. Change WEB_SERVER_IP variable as per your configuration in server/DBWebServAPI.java file:
    static final String WEB_SERVER_IP="<IP>:8080";
8. Build and Run the App.
