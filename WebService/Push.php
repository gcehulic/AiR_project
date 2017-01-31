<?php

class Push {
  private $title;
  private $message;
  
  public function __construct($title, $message) {
    $this->title = $title;
    $this->message = $message;
  }
  
  public function getPush(){
    $res = array();
    $res["data"]["title"] = $this->title;
    $res["data"]["message"] = $this->message;
    $res["data"]["is_background"] = true;
    return $res;
  }
}
