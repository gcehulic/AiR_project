<?php


class DbConnect {
  private $connection;
  
  public function __construct() {
    
  }
  
  function connect(){
    include_once 'Config.php';
    $this->connection = new mysqli(DB_HOST,DB_USERNAME,DB_PASSWORD,DB_NAME);
    if (mysqli_connect_errno()) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
    return $this->connection;
  }
}
