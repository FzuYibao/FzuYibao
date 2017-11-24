<?php

class Wants_model extends CI_Model
{

    public function __construct(){  
        parent::__construct(); 
        $this->load->database();   
    }  


    public function show_wants_by_wid($wid){
        $limit = 10;
        $query = $this->db->get_where('want',array('wid' => $wid));
        $query = $query->result_array()[0];//只有一行数据
        // foreach ($query->result_array() as $key => $value) {
        //     $query = $value;
        // }
        // print_r($query);
        
        
        /*获取用户昵称*/
        $this->db->select('sno');
        $this->db->where('wid', $wid);
        $sno = $this->db->get('want');
        $sno = $sno->result_array();
        foreach ($sno as $key => $value) {
            $sno = $value['sno'];
        }

        /*获取用户图片和昵称*/
        $this->db->select('nickname,avatar_path');
        $this->db->from('user');
        $this->db->join('want', 'user.sno = want.sno');
        $result = $this->db->get();
        $result = $result->result_array();
        foreach ($result as $key => $value) {
            $nickname = $value['nickname'];
            $avatar_path = $value['avatar_path'];
        }

        $query['nickname'] = $nickname;
        $query['avatar_path'] = $avatar_path;


        /*获取需求标签*/
        $tag = $this->db->get_where('wants_tag',array('wid' => $wid));
        $tag = $tag->result_array();
        $tag = array_column($tag, 'wtag_description');//仅需要提取标签内容一列

        $query['tag'] = $tag;


        

        /*获取需求图片*/
        $photo = $this->db->get_where('want_image',array('wid' => $wid));
        $photo = $photo->result_array();
        $photo = array_column($photo, 'path');//仅需要提取路径一列
        $query['photo'] = $photo;
        
        // print_r($query);
        return $query;
    }

    public function show_wants_by_sno($sno, $type, $page){
        // $type为0,查看他发布的需求，1为查看收藏
        $limit = 10;
        if($type == 0){
            $this->db->order_by('wid', 'DESC');
            $wids = $this->db->get_where('want', array('sno' =>  $sno), $limit, $page*$limit);
            // $wids = $wids->result_array();
        }else{
            $this->db->order_by('wid', 'DESC');
            $wids = $this->db->get_where('want_collection', array('sno' =>  $sno), $limit, $page*$limit);
        }
        $wids = $wids->result_array();

        $wids = array_column($wids, 'wid');

        $res[0] = "暂无信息";
        for ($i=0; $i < count($wids) ; $i++) { 

            $query = $this->db->get_where('want',array('wid' => $wids[$i]));
            $query = $query->result_array()[0];//只有一行数据
            
            /*获取需求标签*/
            $tag = $this->db->get_where('wants_tag',array('wid' => $wids[$i]));
            $tag = $tag->result_array();
            $tag = array_column($tag, 'wtag_description');//仅需要提取标签内容一列

            $query['tag'] = $tag;

            /*获取需求图片*/
            $photo = $this->db->get_where('want_image',array('wid' => $wids[$i]));
            $photo = $photo->result_array();
            $photo = array_column($photo, 'path');//仅需要提取路径一列
            $query['photo'] = $photo;
            
            // print_r($query);
            $res[$i] = $query;
        }
        return $res;
    }


    public function select_all_wants($page)  
    {   
        //查询的数目
        $limit = 10;
        $this->db->order_by('wid', 'DESC');
        $wids = $this->db->get('want', $limit, $page*$limit);//$page为传过来的页数
        $wids = $wids->result_array();
        $wids = array_column($wids, 'wid');
        $res[0] = "暂无信息";
        for ($i=0; $i < count($wids) ; $i++) { 
            $res[$i] = $this->show_wants_by_wid($wids[$i]);
        }
        return $res;
    } 

    public function show_one_want($wid, $SNO){

        $query = $this->db->get_where('want',array('wid' => $wid));
        $query = $query->result_array();//只有一行数据
        if (!$query) {
            return;
        }
        foreach ($query as $key => $value) {
            $query = $value;
        }
        
        
        
        /*获取用户昵称*/
        $this->db->select('sno');
        $this->db->where('wid', $wid);
        $sno = $this->db->get('want');
        $sno = $sno->result_array();
        foreach ($sno as $key => $value) {
            $sno = $value['sno'];
        }

        /*获取用户图片和昵称*/
        $this->db->select('nickname,avatar_path');
        $this->db->from('user');
        $this->db->join('want', 'user.sno = want.sno');
        $result = $this->db->get();
        $result = $result->result_array();
        foreach ($result as $key => $value) {
            $nickname = $value['nickname'];
            $avatar_path = $value['avatar_path'];
        }

        $query['nickname'] = $nickname;
        $query['avatar_path'] = $avatar_path;


        /*获取需求标签*/
        $tag = $this->db->get_where('wants_tag',array('wid' => $wid));
        $tag = $tag->result_array();
        $tag = array_column($tag, 'wtag_description');//仅需要提取标签内容一列

        $query['tag'] = $tag;

        

        /*获取需求图片*/
        $photo = $this->db->get_where('want_image',array('wid' => $wid));
        $photo = $photo->result_array();
        $photo = array_column($photo, 'path');//仅需要提取路径一列
        $query['photo'] = $photo;

        /*获取是否收藏*/
        $this->db->where('wid',$wid);
        $this->db->where('sno',$SNO);
        $is_collection = $this->db->get('want_collection');
        $is_collection = $is_collection->result_array();
        if ($is_collection) {
                $is_collection = 'true';
        }else{
            $is_collection = 'false';
        }
        $query['is_collection'] = $is_collection;
        
        // print_r($query);
        return $query;
    }


    public function insert($sno, $title, $type, $time, $description, $price, $wtag_description)  
    {         
        $data = array(
            'sno' => $sno,
            'title' => $title,
            'type' => $type,
            'time' => $time,
            'description' => $description,
            'price' => $price
        );

        //插入want表
        if ($this->db->insert('want', $data)) {
            $wid = $this->db->insert_id(); 
        } else {
            $message = "插入需求表失败";
            $error_code = "40210";
            echo_failure($error_code,$message);
            return;
        }


        $data = array(
            'wid' => $wid,
            'wtag_description' => $wtag_description
        );
        //插入want_tag
        if ($this->db->insert('wants_tag', $data)) {
            return $wid;
        } else {
            $message = "添加需求标签失败";
            $error_code = "40210";
            echo_failure($error_code,$message);
            return;
        }
        
        
    }  

    public function add_wants_image($data){
        // print_r($data);
        
        if ($query = $this->db->insert('want_image', $data)) {
            return $query;
        } else {
            $message = "添加需求图片失败";
            $error_code = "40210";
            echo_failure($error_code,$message);
            return false;
        }
        
        // $query = $this->db->insert('want_image', $data);
        // return $query ? $this->db->insert_id():false;
    }

    public function insert_wants_collection($sno, $wid){

        $data = array(
            'wid' => $wid,
            'sno' => $sno
        );

        //判断是否存在此商品
        $this->db->where('wid',$wid);
        $query = $this->db->get('want');
        $query = $query->result_array();

        if (!$query) {
            return 0;
        }

        //判断是否已经被收藏过
        $this->db->where('wid',$wid);
        $query = $this->db->get('want_collection');
        $query = $query->result_array();


        if ($query) {
            return 0;
        }


        $this->db->insert('want_collection', $data);
        return $this->db->affected_rows();
        
    }

    
    
      
    public function delete($sno, $wid, $type){
        // $type为0,删除他发布的需求，1为删除收藏
        if ($type == 0) {
            /*获取图片路径，并删除*/
            $this->db->where('wid',$wid);
            $query = $this->db->get('want_image');
            $query = $query->result_array();
            foreach ($query as $key) {
                $path = $key['path'];
                unlink($path);
            }


            $this->db->where('wid',$wid);
            $this->db->delete('wants_tag');

            $this->db->where('wid',$wid);
            $this->db->delete('want_image');

            $this->db->where('wid',$wid);
            $this->db->delete('want_collection');

            $this->db->where('wid',$wid);
            $this->db->delete('want');
            
            return $this->db->affected_rows();
        }else{
            $this->db->where('wid',$wid);
            $this->db->where('sno',$sno);
            $this->db->delete('want_collection');
            return $this->db->affected_rows();
        }
        
    }


}
?>  