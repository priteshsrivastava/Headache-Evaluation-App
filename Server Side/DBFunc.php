<?php
//Run file in browser: http://192.168.43.152/DBFunc.php

class DBFunc
{
    //Database connection link
    private $con;
    private $objDBConn;
 
    //Class constructor
    function __construct(){
        //Getting the DBConn.php file
        require_once dirname(__FILE__) . '/DBConn.php';
 
        //Creating a DBConn object to connect to the database
        $this->objDBConn = new DBConn();
 
        //Initializing our connection link of this class
        //by calling the method getDBConnection of DBConn class
        $this->con = $this->objDBConn->getDBConnection();
    }
	
	

	/*
	* The readAll operation
	* When this method is called it is returning all the existing record of the database
	*/
	function dbReadAllPatientTestBasicInfo(){
		$stmt = $this->con->prepare("SELECT t_id, p_id, dr_id, test_st_date, test_end_date, is_test_completed FROM patient_test_basic_info order by p_id, t_id ASC");
		$stmt->execute();
		$stmt->bind_result($t_id, $p_id, $dr_id, $test_st_date, $test_end_date, $is_test_completed);
		
		$objPatientTestBasicInfoList = array(); 
		
		while($stmt->fetch()){
			$objPatientTestBasicInfo  = array();
			$objPatientTestBasicInfo['t_id'] = $t_id; 
			$objPatientTestBasicInfo['p_id'] = $p_id; 
			$objPatientTestBasicInfo['dr_id'] = $dr_id; 
			$objPatientTestBasicInfo['test_st_date'] = $test_st_date; 
			$objPatientTestBasicInfo['test_end_date'] = $test_end_date; 
			$objPatientTestBasicInfo['is_test_completed'] = $is_test_completed;
			
			array_push($objPatientTestBasicInfoList, $objPatientTestBasicInfo); 
		}
		
		return $objPatientTestBasicInfoList; 
	}

	
	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function dbAddPatientTestBasicInfo($t_id, $p_id, $dr_id, $test_st_date, $test_end_date, $is_test_completed){
		$stmt = $this->con->prepare("INSERT INTO patient_test_basic_info (t_id, p_id, dr_id, test_st_date, test_end_date, is_test_completed) VALUES (?, ?, ?, ?,?,?)");

		if($test_end_date === ""){
			$test_end_date = null;
		}
		$stmt->bind_param("issssi", $t_id, $p_id, $dr_id, $test_st_date, $test_end_date, $is_test_completed);
		if($stmt->execute()){
			return true; 
		}
		return false; 

		// $sql = "INSERT INTO patient_test_basic_info (t_id, p_id, dr_id, test_st_date, test_end_date, is_test_completed) VALUES (?, ?, ?, ?,?,?)";
		// if($stmt = mysqli_prepare($this->con, $sql)){
		// 	if($test_end_date === ""){
		// 		$test_end_date=null;
		// 	}
		// 	//String specify the data types of the corresponding bind variables
		// 	// b — binary (such as image, PDF file, etc.)
		// 	// d — double (floating point number)
		// 	// i — integer (whole number)
		// 	// s — string (text)
		// 	mysqli_stmt_bind_param($stmt, "issssi", $t_id, $p_id, $dr_id, $test_st_date, $test_end_date, $is_test_completed);
		// 	if(mysqli_stmt_execute($stmt)){
		//         //echo "Records inserted successfully.";
		//         return true;
		//     } else{
		//         //echo "ERROR: Could not execute query: $sql. " . mysqli_error($this->con);
		//         return false;
		//     }

		// } else{
		//     //echo "ERROR: Could not prepare query: $sql. " . mysqli_error($this->con);
		//     return false;
		// }
	}


	/*
	* The update operation
	* When this method is called the record with the given id is updated with the new given values
	*/
	function dbUpdatePatientTestBasicInfo($t_id, $p_id, $dr_id, $test_st_date, $test_end_date, $is_test_completed){
		$stmt = $this->con->prepare("UPDATE patient_test_basic_info SET  dr_id=?, test_st_date=?, test_end_date=?, is_test_completed=? WHERE t_id=? and p_id = ?");
		$stmt->bind_param("sssiis", $dr_id, $test_st_date, $test_end_date, $is_test_completed, $t_id, $p_id);
		if($stmt->execute()){
			return true; 
		}
		return false; 
	}
	
	
	/*
	* The delete operation
	* When this method is called record is deleted for the given id 
	*/
	function dbDeletePatientTestBasicInfo($t_id, $p_id){
		$stmt = $this->con->prepare("DELETE FROM patient_test_basic_info WHERE t_id=? and p_id = ? ");
		$stmt->bind_param("is", $t_id, $p_id);
		if($stmt->execute()){
			return true; 
		}
		
		return false; 
	}

	/*
	* The deleteAll operation
	* When this method is called record is deleted for the given p_id 
	*/
	function dbDeleteAllPatientTestBasicInfo($p_id){
		$stmt = $this->con->prepare("DELETE FROM patient_test_basic_info WHERE p_id = ? ");
		$stmt->bind_param("s", $p_id);
		if($stmt->execute()){
			return true; 
		}
		
		return false; 
	}


	/*
	* The readAll operation
	* When this method is called it is returning all the existing record of the database
	*/
	function dbReadAllTestDetailData(){
		$stmt = $this->con->prepare("SELECT t_id, p_id, lvl, lvl_status, que_ans_id, lvl_result_headache_cat FROM test_detail_data order by p_id, t_id, lvl ASC");
		$stmt->execute();
		$stmt->bind_result($t_id, $p_id, $lvl, $lvl_status, $que_ans_id, $lvl_result_headache_cat);
		
		$objTestDetailDataList = array(); 
		
		while($stmt->fetch()){
			$objTestDetailData  = array();
			$objTestDetailData['t_id'] = $t_id; 
			$objTestDetailData['p_id'] = $p_id; 
			$objTestDetailData['lvl'] = $lvl; 
			$objTestDetailData['lvl_status'] = $lvl_status; 
			$objTestDetailData['que_ans_id'] = $que_ans_id; 
			$objTestDetailData['lvl_result_headache_cat'] = $lvl_result_headache_cat;
			
			array_push($objTestDetailDataList, $objTestDetailData); 
		}
		return $objTestDetailDataList; 
	}

   /*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function dbAddTestDetailData($t_id, $p_id, $lvl, $lvl_status, $que_ans_id, $lvl_result_headache_cat){
		$stmt = $this->con->prepare("INSERT INTO test_detail_data (t_id, p_id, lvl, lvl_status, que_ans_id, lvl_result_headache_cat) VALUES (?, ?, ?, ?, ?, ?)");

		$stmt->bind_param("isdsdi", $t_id, $p_id, $lvl, $lvl_status, $que_ans_id, $lvl_result_headache_cat);
		if($stmt->execute()){
			return true; 
		}
		return false; 
	}

   /*
	* The deleteAll operation
	* When this method is called record is deleted for the given p_id 
	*/
	function dbDeleteAllTestDetailData($p_id){
		$stmt = $this->con->prepare("DELETE FROM test_detail_data WHERE p_id = ? ");
		$stmt->bind_param("s", $p_id);
		if($stmt->execute()){
			return true; 
		}	
		return false; 
	}



	/*
	* The readAll operation
	* When this method is called it is returning all the existing record of the database
	*/
	function dbReadAllPatientResponseData(){
		$stmt = $this->con->prepare("SELECT t_id, p_id, lvl, que_group_id, que_id,response_yes_no,response_str FROM patient_response_data order by p_id, t_id ASC");
		$stmt->execute();
		$stmt->bind_result($t_id, $p_id, $lvl, $que_group_id, $que_id,$response_yes_no,$response_str);
		
		$objPatientResponseDataList = array(); 
		
		while($stmt->fetch()){
			$objPatientResponseData  = array();
			$objPatientResponseData['t_id'] = $t_id; 
			$objPatientResponseData['p_id'] = $p_id; 
			$objPatientResponseData['lvl'] = $lvl; 
			$objPatientResponseData['que_group_id'] = $que_group_id; 
			$objPatientResponseData['que_id'] = $que_id; 
			$objPatientResponseData['response_yes_no'] = $response_yes_no;
			$objPatientResponseData['response_str'] = $response_str;
			
			array_push($objPatientResponseDataList, $objPatientResponseData); 
		}
		return $objPatientResponseDataList; 
	}

	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function dbAddPatientResponseData($t_id, $p_id, $lvl, $que_group_id, $que_id,$response_yes_no,$response_str){
		$stmt = $this->con->prepare("INSERT INTO patient_response_data (t_id, p_id, lvl, que_group_id, que_id,response_yes_no,response_str) VALUES (?, ?, ?, ?, ?, ?, ?)");

		$stmt->bind_param("isdidis", $t_id, $p_id, $lvl, $que_group_id, $que_id,$response_yes_no,$response_str);
		if($stmt->execute()){
			return true; 
		}
		return false; 
	}

   /*
	* The deleteAll operation
	* When this method is called record is deleted for the given p_id 
	*/
	function dbDeleteAllPatientResponseData($p_id){
		$stmt = $this->con->prepare("DELETE FROM patient_response_data WHERE p_id = ? ");
		$stmt->bind_param("s", $p_id);
		if($stmt->execute()){
			return true; 
		}
		
		return false; 
	}


	/*
	* The readAll operation
	* When this method is called it is returning all the existing record of the database
	*/
	function dbReadPatientDetailInfo($p_id){
		$objPatientDetailInfo  = array();
		//initialize blank object
		$objPatientDetailInfo['p_id'] = ""; 
		$objPatientDetailInfo['pwd'] = ""; 
		$objPatientDetailInfo['name'] = ""; 
		$objPatientDetailInfo['dob'] = "";  
		$objPatientDetailInfo['gender'] = "";  
		$objPatientDetailInfo['address'] = ""; 
		$objPatientDetailInfo['mobile_no'] = 0;
		$objPatientDetailInfo['adhaar_no'] = 0; 
		$objPatientDetailInfo['email'] = "";  
		$objPatientDetailInfo['health_history'] = ""; 
		$objPatientDetailInfo['image']= "";

		$stmt = $this->con->prepare("SELECT p_id, pwd, name, dob, gender, address, mobile_no, adhaar_no, email, health_history, image FROM patient_detail_info where p_id= ? ");
		$stmt->bind_param("s", $p_id);
		//$stmt->execute();
		if($stmt->execute()){
			// echo "after execute, before file_put_contents. " ;
		}else {
			die('dbReadPatientDetailInfo execute() failed: '.$stmt->error);
		}
		$stmt->bind_result($p_id, $pwd, $name, $dob, $gender, $address, $mobile_no, $adhaar_no, $email, $health_history, $image);
		
		while($stmt->fetch()){
			
			$objPatientDetailInfo['p_id'] = $p_id; 
			$objPatientDetailInfo['pwd'] = $pwd; 
			$objPatientDetailInfo['name'] = $name; 
			$objPatientDetailInfo['dob'] = $dob; 
			$objPatientDetailInfo['gender'] = $gender; 
			$objPatientDetailInfo['address'] = $address;
			$objPatientDetailInfo['mobile_no'] = $mobile_no;
			$objPatientDetailInfo['adhaar_no'] = $adhaar_no; 
			$objPatientDetailInfo['email'] = $email; 
			$objPatientDetailInfo['health_history'] = $health_history;
			
			header('Content-type: image/jpeg');
			$objPatientDetailInfo['image'] = base64_encode(file_get_contents('http://localhost:8080/'.$image));
			
		}
		return $objPatientDetailInfo; 
	}

	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function dbAddPatientDetailInfo($p_id, $pwd, $name, $dob, $gender, $address, $mobile_no, $adhaar_no, $email, $health_history, $image){
		
		 $ImageName = "$p_id.jpg";
		 $ImagePath = "Photo_Upload/$ImageName";
 
		 //echo "ImagePath. " . $ImagePath;
		$stmt = $this->con->prepare("INSERT INTO patient_detail_info (p_id, pwd, name, dob, gender, address, mobile_no, adhaar_no, email, health_history, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");


		$stmt->bind_param("ssssssiisss", $p_id, $pwd, $name, $dob, $gender, $address, $mobile_no, $adhaar_no, $email, $health_history, $ImagePath);
		if($stmt->execute()){
			// echo "after execute, before file_put_contents. " ;
			file_put_contents($ImagePath,base64_decode($image));
			//echo "after file_put_contents. " ;
			return true; 
		}else {
			die('dbAddPatientDetailInfo execute() failed: '.$stmt->error);
		}
		return false; 
	}



	/*
	* The Destructor operation
	* When DBFunc object goes out of scope, connection closed. 
	*/
	function __destruct() {
	    $this->objDBConn->closeDBConnection($this->con);
	}
}


?>