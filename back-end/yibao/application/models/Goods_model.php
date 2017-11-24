<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Goods_model extends CI_model
{
	//const self::TBL_USER = 'goods';
	function __construct()
	{
		# code...
	}
	
	function add_goods($data)
	{
		$query= $this->db->insert('goods',$data);
		return $query? $this->db->insert_id():flase;
	}

	function add_goods_tag($data)
	{
		$query= $this->db->insert('goods_tag',$data);
		return $query? $this->db->insert_id():flase;
	}

	function add_goods_image($data)
	{
		$query= $this->db->insert('goods_image',$data);
		return $query? $this->db->insert_id():flase;
	}

	/*商品发布错误，用来删除已经插入数据*/
	function delete_goods($gid)
	{
		/*获取图片路径，并删除*/
		$this->db->where('gid',$gid);
		$query = $this->db->get('goods_image');
		$query = $query->result_array();
		foreach ($query as $key) 
		{
			$path = $key['path'];
			unlink($path);//删除函数
		}

		$tables = array('goods_tag','goods_image','goods');
		$this->db->where('gid',$gid);
		$query = $this->db->delete($tables);
		return $query;

	}

	/*商品删除接口调用函数*/
	function delete_all_goods($gid)
	{
		/*删除商品实际目录下图片*/
		$this->db->where('gid',$gid);
		$query = $this->db->get('goods_image');
		$query = $query->result_array();
		foreach ($query as $key) 
		{
			$path = $key['path'];
			unlink($path);
		}

		$this->db->where('gid',$gid);
		$query1 = $this->db->delete('goods_collection');
		$this->db->where('gid',$gid);
		$query2 = $this->db->delete('goods_tag');
		$this->db->where('gid',$gid);
		$query3 = $this->db->delete('goods_image');
		$this->db->where('gid',$gid);
		$query4 = $this->db->delete('goods');

		if($query1 == false || $query2 == false || $query3 == false || $query4 == false )
		{
			return false;
		}
		//$tables = array('goods_collection','goods_tag','goods_image','goods');
		/*
		$query = $this->db->delete($tables);
		$res = $this->db->affected_rows();
		if($res == 0)
		{
			return false;
		}
		*/
		return true;
		
	}

	function show_all_goods($sno,$gid)
	{
		//$this->db->cache_on();
		/*获取商品信息*/
		$query = $this->db->get_where('goods',array('gid' => $gid));
		$query = $query->result_array()[0];//只有一行数据
		
		/*根据用户名获取昵称，头像路径*/
		$res = $this->db->get_where('user',array('sno' => $sno));
		$res = $res->result_array()[0];
		$query['nickname'] = $res['nickname'];
		$query['avatar_path'] = $res['avatar_path'];

		/*判断是否收藏*/
		$is_collection = $this->db->get_where('goods_collection',array('sno' => $sno, 'gid' => $gid));
		$is_collection = $is_collection->num_rows();
		if($is_collection == 0)
		{
			$query['is_collection'] = "false";
		}
		else
		{
			$query['is_collection'] = "true";
		}
		
		/*获取商品标签*/
		$tag = $this->db->get_where('goods_tag',array('gid' => $gid));
		$tag = $tag->result_array();
		$tag = array_column($tag, 'gtag_description');//仅需要提取标签内容一列
		$query['tag'] = $tag;

		/*获取商品图片*/
		$photo = $this->db->get_where('goods_image',array('gid' => $gid));
		$photo = $photo->result_array();
		$photo = array_column($photo, 'path');//仅需要提取路径一列
		$query['photo'] = $photo;
		if(count($photo) == 0)
		{
			$query['photo'][0] = "public/goods/default.jpg";
		}
		
		//$this->db->cache_off();
		//print_r($query);
		return $query;
	}

	/*依据gid获取商品部分信息*/
	function show_goods_by_gid($gid)
	{
		/*获取商品信息*/
		$query = $this->db->get_where('goods',array('gid' => $gid));
		$query = $query->result_array()[0];//只有一行数据
		
		/*根据用户名获取昵称，头像路径*/
		$res = $this->db->get_where('user',array('sno' => $query['sno']));
		$res = $res->result_array()[0];
		$query['nickname'] = $res['nickname'];
		$query['avatar_path'] = $res['avatar_path'];
		
		/*获取商品标签*/
		$tag = $this->db->get_where('goods_tag',array('gid' => $gid));
		$tag = $tag->result_array();
		$tag = array_column($tag, 'gtag_description');//仅需要提取标签内容一列
		$query['tag'] = $tag;

		/*获取商品图片*/
		$photo = $this->db->get_where('goods_image',array('gid' => $gid));
		$photo = $photo->result_array();
		$photo = array_column($photo, 'path');//仅需要提取路径一列
		$query['photo'] = $photo;
		/*如果没有图片，使用默认图片*/
		if(count($photo) == 0)
		{
			$query['photo'][0] = "public/goods/default.jpg";
		}
		
		//print_r($query);
		return $query;

	}

	function show_goods_by_sno($sno,$type,$page)
	{
		/*$type为0,查看他发布的商品，为1查看收藏,每次只取10条数据*/
		$limit = 10;
		$this->db->where('sno =',$sno);
		$this->db->order_by('gid', 'DESC');
		if($type == 0)
		{
			$query = $this->db->get('goods', $limit, $page*$limit);
		}
		else
		{
			$query = $this->db->get('goods_collection', $limit, $page*$limit);
		}

		$gids = $query->result_array();
		$gids = array_column($gids, 'gid');

		$res[0] = "暂无信息";
		for ($i=0; $i < count($gids); $i++) 
		{ 
			$res[$i] = $this->Goods_model->show_goods_by_gid($gids[$i]);
		}
		
		return $res;
	}

	function show_goods_by_type($type,$page)
	{

		/*先得到类型范围，如12300，可选范围为12300到12399*/
		$string = (string)$type;
		$range = 1;
		for ($i=strlen($string) - 1; $i > 0 ; $i--) 
		{ 
			if($string[$i] != 0)
			{
				$range--;
				break;
			}
			$range *= 10;
		}
		
		$limit = 10;//每次取10条数据
		$this->db->order_by('gid', 'DESC');
		$this->db->where('type >=', $type);
		$this->db->where('type <=', $type + $range);

		$query = $this->db->get('goods', $limit, $page*$limit)->result_array();
		$gids = array_column($query, 'gid');

		$res[0] = "暂无信息";
		for ($i=0; $i < count($gids); $i++) 
		{ 
			$res[$i] = $this->Goods_model->show_goods_by_gid($gids[$i]);
		}
		return $res;

	}

	function show_goods_by_content($content,$page)
	{
		/*调用分词接口，将content划分为多个关键词*/
		$words = $this->Goods_model->get_json_decode($content);
		if ($words == false) 
		{
			return false;
		} 
		//print_r($words);

		/*根据商品名,商品描述,商品标签模糊查询*/
		$limit = 10;//每次查询10条数据
		$this->db->distinct();
		$this->db->select('goods.gid');
		$this->db->order_by('goods.gid', 'DESC');
		foreach ($words as $key => $value) 
		{
			$this->db->or_like('goods_name',$value,'both');
			$this->db->or_like('description', $value, 'both');
			$this->db->or_like('gtag_description', $value, 'both');
		}		
		$this->db->join('goods_tag', 'goods_tag.gid = goods.gid');
		$query = $this->db->get('goods', $limit, $page*$limit)->result_array();
		//echo $this->db->last_query();
		$gids = array_column($query, 'gid');

		$res[0] = "暂无信息";
		for ($i=0; $i < count($gids); $i++) 
		{ 
			$res[$i] = $this->Goods_model->show_goods_by_gid($gids[$i]);
		}
		//print_r($res);

		return $res;
		
	}

	/*将content分词*/
	function get_json_decode($content)
	{
	    // 初始化curl
	    $ch = curl_init();
	    //SCWS(简易中文分词)基于HTTP/POST的分词API
	    $url = "http://www.xunsearch.com/scws/api.php";

	    // 设置URL参数 
	    curl_setopt($ch, CURLOPT_URL, $url);
	    // 设置CURL允许执行的最长秒数
	    curl_setopt($ch, CURLOPT_TIMEOUT, 5);
	    // 要求CURL返回数据
	    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	    //设置参数
	    $post_data = array (
	        'data' => $content,
	        'respond' => 'json',// 响应结果格式为json
	        'ignore' => 'yes'//忽略标点符号
	    );
	    curl_setopt($ch, CURLOPT_POSTFIELDS, $post_data);

	    // 执行请求
	    $result = curl_exec( $ch );
	    // 获取http状态
	    $http_code = curl_getinfo( $ch, CURLINFO_HTTP_CODE );

	    if ($http_code != 200) 
	    {
	        return false;
	    }
	    //取得返回的结果转换成对象,json_decode($result);
	    //取得返回的结果,转换成数组
	    $data = json_decode( $result,true);
	    $data = $data['words'];

	    //提取word一列
	    $words = array_column($data, 'word');
	    // 关闭CURL
	    curl_close ( $ch );
	    return $words;
	}

	function add_goods_collection($data)
	{
		$query= $this->db->insert('goods_collection',$data);
		return $query;
		//return $query? $this->db->insert_id():flase;
	}

	function is_goods_collection($data)
	{
		$query = $this->db->get_where('goods_collection',$data);
		$query = $query->num_rows();
		return $query;
	}

	function delete_goods_collection($data)
	{
		$query = $this->db->delete('goods_collection',$data);
		//echo $this->db->last_query();
		return $query;
	}

	function gid_is_exist($gid)
	{
		$query = $this->db->get_where('goods',array('gid' => $gid));
		$query = $query->num_rows();
		if($query == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}


}

?>