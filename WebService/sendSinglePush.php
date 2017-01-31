<?php

require 'Firebase.php';
require 'Push.php';
require 'DbOperation.php';

$response = array();
$db = new DbOperation();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
  if(isset($_POST['title']) && isset($_POST['message']) && isset($_POST['email'])){

    $push = new Push($_POST['title'], $_POST['message']);
    $mPushNotification = $push->getPush();
    $devicetoken = $db->getTokenByEmail($_POST['email']);
 
 //creating firebase class object 
 $firebase = new Firebase(); 
 
 //sending push notification and displaying result 
 echo $firebase->send($devicetoken, $mPushNotification);
 }else{
 $response['error']=true;
 $response['message']='Parameters missing';
 }
}else{
 $response['error']=true;
 $response['message']='Invalid request';
}
 
echo json_encode($response);
  
