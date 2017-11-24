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
		$this->load->model('User_model');

	}

	public function get_info()
	{
		// $sno = $this->sno;
		try
		{

			$this->load->library('form_validation');
			$this->form_validation->set_rules('sno','sno','required');
			if($this->form_validation->run() )
			{
				$sno = $this->input->post('sno',true);
				$data['info'] = $this->User_model->get_info($sno);
				echo_success($data);
			}else 
			{
					$message = trim(strip_tags(validation_errors())) ;
					throw new Exception($message, 1);			
			}
		
		}
		catch(Exception $e)
		{
			echo_failure(40004,$e->getMessage());

		}

		
	}


	public function update_avatar()
	{
		try
		{

		
			$config['upload_path'] = '/usr/share/nginx/html/yibao/public/avatars/';
			$config['allowed_types'] = 'jpg|png|jpeg';
			$config['max_size'] = '10240';

			$config['file_name']= $this->sno.time();//文件名设置
			$this->load->library('upload',$config);

			if($this->upload->do_upload('avatar'))
			{
				#上传成功 处理缩略图
				$file_info=$this->upload->data();
				//$data['avatar_path'] = $file_info['full_path'];
				$data['avatar_path'] = 'public/avatars/'.$file_info['orig_name'];
				// print_r($file_info);
				if($this->User_model->update_avatar($this->sno,$data))
				{
					// echo '上传成功';

					$json['avatar']['avatar_path'] = $data['avatar_path'];
					echo_success($json);
				}else
				{
					throw new Exception("插入数据库失败，请重新尝试", 1);
					
				}
			}else
			{
					throw new Exception(strip_tags($this->upload->display_errors()));
			}

		}
		catch(Exception $e)
		{
			echo_failure(40003,$e->getMessage());
		}
	}

	public function update_user_info()
	{

		$this->load->library('form_validation');

		try
		{


			foreach ($_POST as $key => $value) {
				# code...
				// echo $key.': '.$value.'<br>';
				// // $this->form_validation->set_rules($key,$key,'required');
				// // if(!$this->form_validation->run())
				// // {
				// // 	$data[$key] = $this->input->post($key,true);
				// // 	print_r($data);
				// // }else
				// // {
				// // 	// throw new Exception(validation_errors(), 1);
				// // }

				$data[$key] = $this->input->post($key,true);
				

				if($data[$key] == NULL )
				{
					throw new Exception("{$key} 不能为空 ", 1);
					
				}
				
				if(isset($data['nickname']))
				{
					if($this->User_model->get_nickname($data['nickname'])!=false)
					{
						throw new Exception("昵称已存在，请重新想一个吧", 1);

					}

				}

				if($key == 'phone')
				{
					// if(strlen($data[$key])>11 )
					// 	throw new Exception("phone number's length is too large", 1);
					if(!preg_match("/^1[34578]\d{9}$/", $data[$key])){
						throw new Exception("这是一个无效的电话号码", 1);
						
					}
						
				}


			}

			unset($data['jwt']);
		//	if($this->User_model->update_user_info($this->sno,$data))
		//	{
		//		// echo "success";
		//		$user = $this->User_model->get_info($this->sno);
		//		$json['user'] = $user[0];
		//		echo_success($json);
		//	}else
		//	{
		//	throw new Exception("数据库操作失败，请重新尝试", 1);
				
		//	}
	

			if(isset($data['nickname'])|| isset($data['phone']))

			{
				if($this->User_model->update_user_info($this->sno,$data))
				{
					// echo "success";
					$user = $this->User_model->get_info($this->sno);
					$json['user'] = $user[0];
					echo_success($json);
				}else
				{
					throw new Exception("数据库操作失败，请重新尝试", 1);
					
				}
			}else
			{
				throw new Exception("昵称和电话必须有一个必填", 1);
				
			}



		}
		catch(Exception $e)
		{
			echo_failure(40002,$e->getMessage());
		}


		// print_r($data);
		// print_r($data);
		// // $this->form_validation->set_data($data);
		// if($this->form_validation->run('update_user_info'))
		// {
		// 	echo '111';
		// }else
		// {
		// 	echo validation_errors() ;
		// 	echo '000';
		// }


		// $this->User_model->update_info($this->sno,$data);

	}
}



?>