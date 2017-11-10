<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class User_model extends CI_Model
{
	const TBL_USER='user';


	public function get_user($sno)
	{

		$query = $this->db->get_where(self::TBL_USER,array('sno' => $sno));
		// $data=$this->db->get_where('admin',array('username'=> $username))->result_array();

		return $query->num_rows() > 0 ? true : false;
	}

	// public function get_uid($sno)
	// {
	// 	$this->db->select('uid');
	// 	$query = $this->db->get_where(self::TBL_USER,array('sno' => $sno));
	// 	return $query->result_array();

	// }


	//添加返回值是新插入记录的id 
	public function add_user($data)
	{
		$query= $this->db->insert(self::TBL_USER,$data);
		return $query? $this->db->insert_id():flase;
	}
}


?>