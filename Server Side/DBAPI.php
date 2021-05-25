<?php

//this is if you are using different different origins/servers in your localhost, * to be update with the right address when it comes to production
header('Access-Control-Allow-Origin: *');
//this is if you are specifying content-type in your axios request
header("Access-Control-Allow-Headers: Content-Type");

// echo "<pre>";
// print_r($_POST);
// echo "</pre>";

    //getting the DBFunc class
	require_once dirname(__FILE__) .'/DBFunc.php';

 
	//function validating all the paramters are available
	//we will pass the required parameters to this function 
	function isTheseParametersAvailable($params){
		//assuming all parameters are available 
		$available = true; 
		$missingparams = ""; 
		
		foreach($params as $param){
			if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
				$available = false; 
				$missingparams = $missingparams . ", " . $param; 
			}
		}
		
		//if parameters are missing 
		if(!$available){
			$response = array(); 
			$response['error'] = true; 
			$response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';
			
			//displaying error
			echo json_encode($response);
			
			//stopping further execution
			die();
		}
	}
	
	//an array to display response
	$response = array();
	
	//if it is an api call 
	//that means a get parameter named api call is set in the URL 
	//and with this parameter we are concluding that it is an api call

	if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){

			//the READALL operation
			//if the call is dbReadAllPatientTestBasicInfo()
			//Call in browser: http://192.168.43.152/DBAPI.php?apicall=dbReadAllPatientTestBasicInfo
			case 'dbReadAllPatientTestBasicInfo':
				$db = new DBFunc();
				$response['error'] = false; 
				$response['message'] = 'ReadAllPatientTestBasicInfo successfully completed';
				$response['objPatientTestBasicInfoList'] = $db->dbReadAllPatientTestBasicInfo();
			break; 
			
			//the Add operation
			//if the api call value is 'dbAddPatientTestBasicInfo'
			//we will create a record in the database
			//
			case 'dbAddPatientTestBasicInfo':
				//first check the parameters required for this request are available or not 
				isTheseParametersAvailable(array('t_id','p_id','test_st_date'));

				//creating a new dboperation object
				$db = new DBFunc();

				//creating a new record in the database
				$result = $db->dbAddPatientTestBasicInfo(
					$_POST['t_id'],
					$_POST['p_id'],
					$_POST['dr_id'],
					$_POST['test_st_date'],
					$_POST['test_end_date'],
					$_POST['is_test_completed']
				);
 
				//if the record is created adding success to response
				if($result){
					//record is created means there is no error
					$response['error'] = false; 
 
					//in message we have a success message
					$response['message'] = 'PatientTestBasicInfo added successfully';
 
					// //and we are getting all the heroes from the database in the response
					// $response['heroes'] = $db->getHeroes();
				}else{
 
					//if record is not added that means there is an error 
					$response['error'] = true; 
 
					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 

			//the AddAll operation
			//if the api call value is 'dbAddAllPatientTestBasicInfo'
			//we will create records in the database
			//
			case 'dbAddAllPatientTestBasicInfo':
				//creating a new dboperation object
				$db = new DBFunc();
				$resultAddAll=false;
				$postdata = file_get_contents("php://input"); 
				//echo "postdata :>>".$postdata;
   				$data = json_decode($postdata,true);
				//echo "param data:>>".$data;
				if(is_array($data)){
					//echo "ArrayList is array.";
					$resultAddAll=true;
					foreach ($data as $key) {
						$t_id=$key['t_id'];
						//echo "Adding t_id:".$t_id;
						$p_id=$key['p_id'];
						//echo "Adding p_id:".$p_id;
						$dr_id=$key['dr_id'];
						$test_st_date=$key['test_st_date'];
						$test_end_date=$key['test_end_date'];
						$is_test_completed=$key['is_test_completed'];

						// //first check the parameters required for this request are available or not 
						// isTheseParametersAvailable(array('t_id','p_id','test_st_date'));

						//creating a new record in the database
						$result = $db->dbAddPatientTestBasicInfo(
							$t_id,
							$p_id,
							$dr_id,
							$test_st_date,
							$test_end_date,
							$is_test_completed
						);
						if($result===false){
							$resultAddAll=false;
						}

					}
				}else{
					//echo "ArrayList is not an array.";
					//if record is not in array format that means there is an error 
					$response['error'] = true; 
 
					//and we have the error message
					$response['message'] = 'Invalid format for AddAllPatientTestBasicInfo';
				}
				
				//if the record is created adding success to response
				if($resultAddAll){
					//record is created means there is no error
					$response['error'] = false; 
 
					//in message we have a success message
					$response['message'] = 'PatientTestBasicInfo AddAll successful';
 
					// //and we are getting all the heroes from the database in the response
					// $response['heroes'] = $db->getHeroes();
				}else{
 
					//if record is not added that means there is an error 
					$response['error'] = true; 
 
					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 
			
			//the update operation
			//if the api call value is 'dbUpdatePatientTestBasicInfo'
			//we will update a record in the database
			//
			case 'dbUpdatePatientTestBasicInfo':
				//first check the parameters required for this request are available or not 
				isTheseParametersAvailable(array('t_id','p_id','test_st_date'));

				//creating a new dboperation object
				$db = new DBFunc();

				//update a new record in the database
				$result = $db->dbUpdatePatientTestBasicInfo(
					$_POST['t_id'],
					$_POST['p_id'],
					$_POST['dr_id'],
					$_POST['test_st_date'],
					$_POST['test_end_date'],
					$_POST['is_test_completed']
				);
 
				//if the record is created adding success to response
				if($result){
					//record is created means there is no error
					$response['error'] = false; 
 
					//in message we have a success message
					$response['message'] = 'PatientTestBasicInfo updated successfully';
 
				}else{
 
					//if record is not updated that means there is an error 
					$response['error'] = true; 
 
					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 
			
			
			//the delete operation
			case 'dbDeletePatientTestBasicInfo':
 				//for the delete operation we are getting a GET parameter from the url having the primary key of the record to be deleted
				//echo "Deleting(post) t_id:".$_POST['t_id']." p_id:".$_POST['p_id'];
				
				if( isset($_POST['t_id']) && isset($_POST['p_id']) ){
					$db = new DBFunc();

					if($db->dbDeletePatientTestBasicInfo($_POST['t_id'],
					$_POST['p_id'])){
						$response['error'] = false; 
						$response['message'] = 'PatientTestBasicInfo deleted successfully';
						// $response['heroes'] = $db->getHeroes();
					}else{
						$response['error'] = true; 
						$response['message'] = 'Some error occurred please try again';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Nothing to delete, provide an Primary Key please';
				}
			break; 

			//the deleteAll operation
			case 'dbDeleteAllPatientTestBasicInfo':
 				//for the delete operation we are getting a GET parameter from the url having the primary key of the record to be deleted
				//echo "Deleting(post)  p_id:".$_POST['p_id'];
				
				if( isset($_POST['p_id']) ){
					$db = new DBFunc();

					if($db->dbDeleteAllPatientTestBasicInfo($_POST['p_id'])){
						$response['error'] = false; 
						$response['message'] = 'PatientTestBasicInfo deleted successfully';
						// $response['heroes'] = $db->getHeroes();
					}else{
						$response['error'] = true; 
						$response['message'] = 'Some error occurred please try again';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Nothing to delete, provide an Key please';
				}
			break; 


			//the READALL operation
			//if the call is dbReadAllTestDetailData()
			//Call in browser: http://192.168.43.152/DBAPI.php?apicall=dbReadAllTestDetailData
			case 'dbReadAllTestDetailData':
				$db = new DBFunc();
				$response['error'] = false; 
				$response['message'] = 'ReadAllTestDetailData successfully completed';
				$response['objTestDetailDataList'] = $db->dbReadAllTestDetailData();
			break; 

			//the AddAll operation
			//if the api call value is 'dbAddAllTestDetailData'
			//we will create records in the database
			case 'dbAddAllTestDetailData':
				//creating a new dboperation object
				$db = new DBFunc();
				$resultAddAll=false;
				$postdata = file_get_contents("php://input"); 
				$data = json_decode($postdata,true);
				if(is_array($data)){
					$resultAddAll=true;
					foreach ($data as $key) {
						$t_id=$key['t_id'];
						$p_id=$key['p_id'];
						$lvl=$key['lvl'];
						$lvl_status=$key['lvl_status'];
						$que_ans_id=$key['que_ans_id'];
						$lvl_result_headache_cat=$key['lvl_result_headache_cat'];
						//creating a new record in the database
						$result = $db->dbAddTestDetailData(
							$t_id, $p_id, $lvl, $lvl_status, $que_ans_id, $lvl_result_headache_cat
						);
						if($result===false){
							$resultAddAll=false;
						}

					}
				}else{
					//echo "ArrayList is not an array. Format error";
					$response['error'] = true; 
 
					//and we have the error message
					$response['message'] = 'Invalid format for AddTestDetailData';
				}
				
				//if the record is created adding success to response
				if($resultAddAll){
					//record is created means there is no error
					$response['error'] = false; 
 
					//in message we have a success message
					$response['message'] = 'TestDetailData AddAll successful';
					
				}else{
 
					//if record is not added that means there is an error 
					$response['error'] = true; 
 
					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 

			//the deleteAll operation
			case 'dbDeleteAllTestDetailData':
 				//for the delete operation we are getting a GET parameter from the url having the p_id of the records to be deleted
				
				if( isset($_POST['p_id']) ){
					$db = new DBFunc();

					if($db->dbDeleteAllTestDetailData($_POST['p_id'])){
						$response['error'] = false; 
						$response['message'] = 'TestDetailData deleted successfully';	
					}else{
						$response['error'] = true; 
						$response['message'] = 'Some error occurred please try again';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Nothing to delete, provide an Key please';
				}
			break; 


			//the READALL operation
			//if the call is dbReadAllPatientResponseData()
			//Call in browser: http://192.168.43.152/DBAPI.php?apicall=dbReadAllPatientResponseData
			case 'dbReadAllPatientResponseData':
				$db = new DBFunc();
				$response['error'] = false; 
				$response['message'] = 'ReadAllPatientResponseData successfully completed';
				$response['objPatientResponseDataList'] = $db->dbReadAllPatientResponseData();
			break; 

			//the AddAll operation
			//if the api call value is 'dbAddAllPatientResponseData'
			//we will create records in the database
			case 'dbAddAllPatientResponseData':
				//creating a new dboperation object
				$db = new DBFunc();
				$resultAddAll=false;
				$postdata = file_get_contents("php://input"); 
				$data = json_decode($postdata,true);
				if(is_array($data)){
					$resultAddAll=true;
					foreach ($data as $key) {
						$t_id=$key['t_id'];
						$p_id=$key['p_id'];
						$lvl=$key['lvl'];
						$que_group_id=$key['que_group_id'];
						$que_id=$key['que_id'];
						$response_yes_no=$key['response_yes_no'];
						$response_str=$key['response_str'];
						//creating a new record in the database
						$result = $db->dbAddPatientResponseData($t_id, $p_id, $lvl, $que_group_id, $que_id,$response_yes_no,$response_str);
						if($result===false){
							$resultAddAll=false;
						}
					}
				}else{
					//echo "ArrayList is not an array. Format error";
					$response['error'] = true; 
 
					//and we have the error message
					$response['message'] = 'Invalid format for AddPatientResponseData';
				}
				
				//if the record is created adding success to response
				if($resultAddAll){
					//record is created means there is no error
					$response['error'] = false; 
 
					//in message we have a success message
					$response['message'] = 'PatientResponseData AddAll successful';
					
				}else{
 
					//if record is not added that means there is an error 
					$response['error'] = true; 
 
					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 

			//the deleteAll operation
			case 'dbDeleteAllPatientResponseData':
 				//for the delete operation we are getting a GET parameter from the url having the p_id of the records to be deleted
				
				if( isset($_POST['p_id']) ){
					$db = new DBFunc();

					if($db->dbDeleteAllPatientResponseData($_POST['p_id'])){
						$response['error'] = false; 
						$response['message'] = 'PatientResponseData deleted successfully';	
					}else{
						$response['error'] = true; 
						$response['message'] = 'Some error occurred please try again';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Nothing to delete, provide an Key please';
				}
			break; 

			case 'dbReadPatientDetailInfo':
				//echo "pid to read:".$_POST['p_id'];
				//echo "pid to read:".$_GET['p_id'];
				if( isset($_POST['p_id']) ){
					$db = new DBFunc();
					$response['error'] = false; 
					$response['message'] = 'ReadPatientDetailInfo successfully completed';
					$response['objPatientDetailInfo'] = $db->dbReadPatientDetailInfo($_POST['p_id']);
				}else{
					$response['error'] = true; 
					$response['message'] = 'Nothing to Read, provide an Key please';
				}
			break; 
			
			//the Add operation
			//if the api call value is 'dbAddPatientTestBasicInfo'
			//we will create a record in the database
			//
			case 'dbAddPatientDetailInfo':
				//first check the parameters required for this request are available or not 
				isTheseParametersAvailable(array('p_id','pwd', 'name', 'dob'));

				//creating a new dboperation object
				$db = new DBFunc();

				//creating a new record in the database
				$result = $db->dbAddPatientDetailInfo(
					$_POST['p_id'], 
					$_POST['pwd'],
					$_POST['name'],
					$_POST['dob'], 
					$_POST['gender'],
					$_POST['address'],
					$_POST['mobile_no'],
					$_POST['adhaar_no'],
					$_POST['email'], 
					$_POST['health_history'],
					$_POST['image']
				);
 
				//if the record is created adding success to response
				if($result){
					//record is created means there is no error
					$response['error'] = false; 
 
					//in message we have a success message
					$response['message'] = 'PatientDetailInfo added successfully';
 
					// //and we are getting all the heroes from the database in the response
					// $response['heroes'] = $db->getHeroes();
				}else{
 
					//if record is not added that means there is an error 
					$response['error'] = true; 
 
					//and we have the error message
					$response['message'] = 'Some error occurred please try again';
				}
				
			break; 



		} //end switch
		
	}else{
		//if it is not api call 
		//pushing appropriate values to response array 
		$response['error'] = true; 
		$response['message'] = 'Invalid DB API Call';
	}
	
	//displaying the response in json structure 
	echo json_encode($response);



	?>