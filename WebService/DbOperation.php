<?php


class DbOperation {
  private $connection;
  
  public function __construct() {
    require_once './DbConnect.php';
    $db = new DbConnect();
    $this->connection = $db->connect();
  }
  
  public function registerDevice($email, $token){
    if(!$this->isEmailSaved($email)){
        $stmt = $this->connection->prepare("INSERT INTO devices (email, token) VALUES (?,?) ");
        $stmt->bind_param("ss",$email,$token);
        if($stmt->execute())
            return 0; //success
        return 1; // failure
    }elseif($this->isEmailSaved($email) && strcmp($this->getTokenByEmail($email)[0],$token) != 0){
        $stmt = $this->connection->prepare("UPDATE devices SET token = ? where email = ? ");
        $stmt->bind_param("ss",$token,$email);
        return 3; //token updated
    } else{
        return 2; //email already exist
    }
  }
  
  private function isEmailSaved($email){
    $stmt = $this->connection->prepare("SELECT id from devices where email = ?");
    $stmt->bind_param("s",$email);
    $stmt->execute();
    $stmt->store_result();
    $num_rows = $stmt->num_rows;
    $stmt->close();
    return $num_rows > 0;
  }
  
  public function getAllTokens(){
    $stmt = $this->connection->prepare("SELECT token from devices");
    $stmt->execute();
    $result  = $stmt->get_result();
    $token = array();
    while($token = $result->fetch_assoc()){
      array_push($tokens,$token['token']);
    }
    return $tokens;
  }
  
  public function getTokenByEmail($email){
    $stmt = $this->connection->prepare("SELECT token FROM devices WHERE email = ?");
    $stmt->bind_param("s",$email);
    $stmt->execute();
    $stmt->bind_result($token);
    $stmt->fetch();
    return array($token); 
  }
  
  public function getAllDevices(){
    $stmt = $this->connection->prepare("SELECT * FROM devices");
    $stmt->execute();
    $result = $stmt->get_result();
    return $result; 
  }
  
  
}
	