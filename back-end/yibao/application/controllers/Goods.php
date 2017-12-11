<?php

defined('BASEPATH') OR exit('No direct script access allowed');
date_default_timezone_set("Asia/Shanghai");

/**
* 
*/
class Goods extends MY_Controller 
{
	
	public function __construct()
	{
		parent::__construct();
		$this->load->model('Goods_model');
	}

	function index()
	{

	}

	function add_goods()
	{	
		try
		{   
			$this->load->library('form_validation');
			if($this->form_validation->run('add_goods_info') != false)
			{
				/*接收数据*/
				$data = array (
				    'goods_name' => $this->input->post('goods_name',true),
					'type' => $this->input->post('type',true),
					'status' => $this->input->post('status',true),
					'description' => $this->input->post('description',true),
					'price' => $this->input->post('price',true),
					'sno' => $this->sno,
					'time' => date("Y-m-d H:i:s")
				);
				$tag = $this->input->post('tag',true);
				if(empty($data['description']))
				{
					$data['description'] = "暂无信息描述";
				}

				if($data['price'] == '￥')
				{
					throw new Exception("商品价格不可为空",1);
				}

				/*插入数据库*/
				$gid = $this->Goods_model->add_goods($data);
				if($gid == false)
				{
					throw new Exception("商品信息插入错误", 1);
				}
				else
				{
					/*插入商品标签*/
					$res = $this->add_goods_tag($tag,$gid);
					if($res == false)
					{
						delete_goods($gid);
						throw new Exception("商品标签插入错误！", 1);
					}

					/*上传商品图片*/
					$res = $this->add_goods_image($gid);
					if($res == false)
					{
						delete_goods($gid);
						throw new Exception("商品图片插入错误！", 1);
					}

					$this->db->cache_delete('Goods','show_goods_by_content');

					/*插入成功，返回商品详细信息*/
					$json['goods'] = $this->Goods_model->show_all_goods($this->sno, $gid);
					echo_success($json);
					
				}
			}
			else
			{
				$message = trim(strip_tags(validation_errors())) ;
				throw new Exception($message, 1);
			}

		}
		catch(Exception $e)
		{
			echo_failure(40101,$e->getMessage());
		}

	}

	function add_goods_tag($tag,$gid)
	{
		if($tag == "")
		{
			return true;
		}
		$data['gid'] = $gid;

		/*处理标签,根据逗号划分*/
		$tags = explode(',', $tag);
		for($i = 0; $i < count($tags); $i++)
		{
			$data['gtag_description'] = $tags[$i];
			$gtag_id = $this->Goods_model->add_goods_tag($data);
			if($gtag_id == false)
			{
			    return false;
			}
		}
		return true;

	}

	function add_goods_image($gid)
	{
		
		$this->load->library('upload');

		$i = 0;
		foreach($_FILES as $key => $val)
		{
			/*组合$config*/
			$i++;
            $config['upload_path'] = '/usr/share/nginx/html/yibao/public/goods/';
            //$config['upload_path'] = './public/goods_photo/';
            $config['allowed_types'] = 'jepg|jpg|png';
            $config['file_name'] = $this->sno.time().$i;//命名方式为学号+时间+第几张图片
            $config['max_size'] = 10240;
            //$config['max_width'] = 1024;
            //$config['max_height'] = 768;

            /*图片上传到固定文件下，将路径插入数据库*/
            $this->upload->initialize($config);
            if (! $this->upload->do_upload($key)) 
            {
            	//$message = strip_tags($this->upload->display_errors());上传错误原因
            	return false;
            }
            else
            {
            	$data['gid'] = $gid;
            	$file_info = $this->upload->data();

                $data['path'] = 'public/goods/'.$file_info['orig_name'];
            	
            	$gimage_id = $this->Goods_model->add_goods_image($data);
			    if($gimage_id == false)
			    {
			        return false;
			    } 	
            }
        }
        return true;
	}
	
	/*商品发布错误，回退*/
	private function delete_goods($gid)
	{
		$res = $this->Goods_model->delete_goods($gid);
	}

	/*商品删除接口*/
	function delete_all_goods()
	{
		try
		{
			$gid = $this->input->post('gid');

			/*判断商品ID是否存在*/
			$res = $this->Goods_model->gid_is_exist($gid);
			if($res == false)
			{
				throw new Exception("商品ID不存在！", 1);
			}

			/*删除数据库信息*/
			$res = $this->Goods_model->delete_all_goods($gid);
			if($res == false)
			{
				throw new Exception("数据库操作错误！", 1);
			}
			
			/*返回此人发布所有商品*/
			/*此处bug，将多个表名构成的数组去delete，再调用show_goods_by_sno得不到数据*/
			$sno = $this->sno;
			$json['goods'] = $this->Goods_model->show_goods_by_sno($sno, 0, 0);
			echo_success($json);
			
		}
		catch(Exception $e)
		{
			echo_failure(40102,$e->getMessage());
		}
		
	}

	/*显示商品详细信息*/
	function show_all_goods()
	{
		try
		{
			$this->load->library('form_validation');
			$this->form_validation->set_rules('gid','gid','required');

			/*验证输入*/
			if($this->form_validation->run() != false)
			{
				$sno = $this->sno;
				$gid = $this->input->post('gid');

				/*判断商品是否存在*/
				$res = $this->Goods_model->gid_is_exist($gid);
				if($res == false)
				{
					throw new Exception("商品ID不存在!", 1);
				}

				/*获取商品信息*/
				$json['goods'] = $this->Goods_model->show_all_goods($sno,$gid);
				echo_success($json);
			}
			else
			{
				$message = trim(strip_tags(validation_errors()));
				throw new Exception($message, 1);
			}
			
		}
		catch(Exception $e)
		{
			echo_failure(40103,$e->getMessage());
		}
	}

	/*根据学号显示商品的接口*/
	function show_goods_by_sno()
	{
		try
		{
			$this->load->library('form_validation');
			$this->form_validation->set_rules('type','type','required|in_list[0,1]');//必须在0，1之间
			$this->form_validation->set_rules('page','page','required|integer');//必须为整数

			/*验证输入*/
			if($this->form_validation->run() != false)
			{
	            $sno = $this->sno;
				$type = $this->input->post('type');//选择查看当前用户发布还是收藏的商品，发布为0，收藏为1。
				$page = $this->input->post('page');

				$json['goods'] = $this->Goods_model->show_goods_by_sno($sno,$type,$page);
				echo_success($json);
			}
			else
			{
				$message = trim(strip_tags(validation_errors()));
				throw new Exception($message, 1);
			}
			
		}
		catch(Exception $e)
		{
			echo_failure(40104,$e->getMessage());
		}
		
	}

	/*根据类别显示商品接口*/
	function show_goods_by_type()
	{
		try
		{
			$this->load->library('form_validation');
			$this->form_validation->set_rules('type','type','required|greater_than[0]');//必须大于0
			$this->form_validation->set_rules('page','page','required|integer');//必须为整数

			/*验证输入*/
			if($this->form_validation->run() != false)
			{
		        $type = $this->input->post('type');//根据商品类别
				$page = $this->input->post('page');

				$json['goods'] = $this->Goods_model->show_goods_by_type($type,$page);
				echo_success($json);
			}
			else
			{
				$message = trim(strip_tags(validation_errors()));
				throw new Exception($message, 1);
			}
			
		}
		catch(Exception $e)
		{
			echo_failure(40105,$e->getMessage());
		}
		
	}

	/*根据商品名或者商品描述查询*/
	function show_goods_by_content()
	{
		try
		{
			$this->load->library('form_validation');
			$this->form_validation->set_rules('content','content','required|max_length[20]');
			$this->form_validation->set_rules('page','page','required|integer');//必须为整数

			/*验证输入*/
			if($this->form_validation->run() != false)
			{
				$content = $this->input->post('content');
				$page = $this->input->post('page');

			    //$this->db->cache_off();
				$json['goods'] = $this->Goods_model->show_goods_by_content($content,$page);
				if($json['goods'] == false)
				{
					throw new Exception("商品查询内容请求调用分词网站超时!", 1);
				}
				echo_success($json);
			}
			else
			{
				$message = trim(strip_tags(validation_errors()));
				throw new Exception($message, 1);
			}
			
		}
		catch(Exception $e)
		{
			echo_failure(40106,$e->getMessage());
		}
		
	}

	/*添加收藏接口*/
	function add_goods_collection()
	{
		try
		{
			$this->load->library('form_validation');
			$this->form_validation->set_rules('gid','gid','required');

			/*验证输入*/
			if($this->form_validation->run() != false)
			{
				$data = array(
					'sno' => $this->sno,
					'gid' => $this->input->post('gid')
				);
				/*判断商品是否存在*/
				$res = $this->Goods_model->gid_is_exist($data['gid']);
				if($res == false)
				{
					throw new Exception("商品ID不存在！", 1);
				}

				/*判断当前商品是否收藏过*/
				$res = $this->Goods_model->is_goods_collection($data);
				if($res != 0 )
				{
					throw new Exception("该商品已经收藏过！", 1);
				}

				/*插入数据库*/
				$res = $this->Goods_model->add_goods_collection($data);
				if($res == false)
				{
					throw new Exception("商品收藏信息插入数据库错误！", 1);
				}
				
				$json['goods'] = $data;
				echo_success($json);		
			}
			else
			{
				$message = trim(strip_tags(validation_errors()));
				throw new Exception($message, 1);
			}
			
		}
		catch(Exception $e)
		{
			echo_failure(40107,$e->getMessage());
		}

	}

	function delete_goods_collection()
	{
		try
		{
			$this->load->library('form_validation');
			$this->form_validation->set_rules('gid','gid','required');

			/*验证输入*/
			if($this->form_validation->run() != false)
			{
				$data = array(
					'sno' => $this->sno,
					'gid' => $this->input->post('gid')
				);

				/*从数据库中删除*/
				$res = $this->Goods_model->delete_goods_collection($data);
				if($res == false)
				{
		            throw new Exception("数据库操作错误！", 1);
				}

				$sno = $this->sno;
				$json['goods'] = $this->Goods_model->show_goods_by_sno($sno, 1, 0);
				echo_success($json);
				
			}
			else
			{
				$message = trim(strip_tags(validation_errors()));
				throw new Exception($message, 1);
			}
			
		}
		catch(Exception $e)
		{
			echo_failure(40108,$e->getMessage());
		}

	}

	
}
   
?>