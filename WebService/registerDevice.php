<?php

require_once 'DbOperation.php';
 $response = array(); 
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $token = $_POST['token'];
 $email = $_POST['email'];
 
 $db = new DbOperation(); 
 
 $result = $db->registerDevice($email,$token);
 
 if($result == 0){
 $response['error'] = false; 
 $response['message'] = 'Device registered successfully';
 }elseif($result == 2){
 $response['error'] = true; 
 $response['message'] = 'Device token already registered';
 }elseif($result == 3){
 $response['error'] = true; 
 $response['message'] = 'Device token updated';
 }else{
 $response['error'] = true;
 $response['message']='Device not registered';
 }
 }else{
 $response['error']=true;
 $response['message']='Invalid Request...';
 }
 
 echo json_encode($response);
