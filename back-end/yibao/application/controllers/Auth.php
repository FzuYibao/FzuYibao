<?php
header("content-type:text/html;charset=utf-8");         //设置编码

defined('BASEPATH') OR exit('No direct script access allowed');

// use \Firebase\JWT\JWT;
// include('D:wamp64/www/yibao/third_party/JWT.php');


class Auth extends CI_Controller {


	function __construct()
	{
		parent::__construct();
		$this->load->model('user_model');
	}



	public function login()
	{

		try
		{


				$sno = $this->input->post('sno',true);
				$password = $this->input->post('password',true);
				// $sno = '031502212';
				// $password = '250514';

				if(isset($sno) && isset($password))
				{
					$this->load->helper('post_login');
					$user=post_login($sno,$password);
					if($user===false)
					{
						// echo 
						throw new Exception("账号或密码错误，请重新登录，或与学院教学办联系！");
					}else  //登录教务处成功
					{

						if($this->user_model->get_user($sno) == false) //此时数据库未有该学生的数据
						{

							$data = array(
								'sno' => $sno,
								'phone' => $user['phone'],
								'user_name' => $user['user_name']
							);

							$uid = $this->user_model->add_user($data);//对学生数据进行插入
							if($uid === false)
							{
								// echo ;
								throw new Exception('数据库插入失败，请重新尝试');

							}

						}

						//不论是否已经注册 都返回数据
						
						//uid为0说明已经注册 查询uid
						// if($uid == 0)
						// {
						// 	$uid = $this->user_model->get_uid($sno);
						// 	// print_r($uid);
						// 	echo $uid = $uid[0]['uid'].'<br>';
						// }
						$key = "salt:let's encrypt";
						$token = array(
						"uid"		=> $sno,
					    "exp"       => $_SERVER['REQUEST_TIME']+604800
					    // "exp"       => $_SERVER['REQUEST_TIME']-1

						);
						// echo $jwt = JWT::encode($token, $key);
			   			$this->load->library('JWT');
			   			$objJWT = new JWT();
						$jwt = $objJWT->encode($token, $key);
						$message = '登录成功';
						$error_code = 0;
						$user['jwt'] = $jwt;
						$data['user'] = $user;

						echo_success($data);
				


					}

				}
		}
		catch(Exception $e)
		{

			echo_failure(40001,$e->getMessage());
			return;
		}

	}


}
