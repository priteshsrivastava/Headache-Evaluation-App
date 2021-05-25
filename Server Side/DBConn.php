<?php

//Declare the parameters to get the database conn
define('DB_HOST', 'localhost');
define('DB_USER', 'root');
define('DB_PASS', 'mysql123');
define('DB_NAME', 'test');

//Class DBConn
class DBConn {

	//Variable to store database link
	private $con;
 
	//Class constructor
	function __construct(){

	}
	
	//This method will connect to the database
	function getDBConnection() {
		//connecting to mysql database
		$this->con = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

		//Checking if any error occured while connecting
		// if (mysqli_connect_errno()) {
		// 	echo "Failed to connect with database: " . mysqli_connect_error();
		// }else{
		// 	echo "DB connected successfully.";
		// }
 
 		// Check connection
		if($this->con === false){
		    die("ERROR: Could not connect with database:" . mysqli_connect_error());
		}
		//finally returning the connection link 
		return $this->con;
	}
	function closeDBConnection($con) {
		// Close connection
		if($con === true){
			mysqli_close($con);
		}
	}
 
}

// //Creating a DBConn object to connect to the database
// $objDBConn = new DBConn();

// //Initializing our connection link of this class
// //by calling the method connect of DBConn class
// $dbConn = $objDBConn->getDBConnection();

?>