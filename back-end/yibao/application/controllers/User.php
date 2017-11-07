<?php 		

defined('BASEPATH') OR exit('No direct script access allowed');

/**
* 
*/
class User extends MY_Controller
{
	
	function __construct()
	{
		# code...
		parent::__construct();

	}

	public function get_info()
	{
		// echo parent::get_token();
		echo $this->sno;

		echo 'get_info';
		// echo $this->token;
	}
}



?>