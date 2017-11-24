
<?php 
date_default_timezone_set('PRC');
if ( ! defined('BASEPATH')) exit('No direct script access allowed');


//控制器要用My_Controller
//使用$this->sno
//输出echo_success(&data)
//echo_failure($message,$error_code) 
class Wants extends My_Controller {




public  function __construct(){  
    parent::__construct();  
    $this->load->library('form_validation');
    $this->load->model('Wants_model'); 
}  


public function insert_wants_image($wid)
{   
    
    $this->load->library('upload');
    $i = 0;
    foreach($_FILES as $key=>$val)
    {
        //组合$config
        $config['upload_path'] = '/usr/share/nginx/html/yibao/public/wants/';
        $config['allowed_types'] = 'jepg|jpg|png';
        // $config['file_name'] = $this->sno.time();
        $config['file_name'] = $this->sno.time() . $i;
        $config['max_size'] = 10240;
        // $config['max_width'] = 1024;
        // $config['max_height'] = 768;
        $i ++ ;
        $this->upload->initialize($config);
        if (! $this->upload->do_upload($key)) 
        {
            $message = strip_tags($this->upload->display_errors());
            $error_code = "40210";
            echo_failure($message,$error_code);
            return false;
        }
        else
        {
            $data['wid'] = $wid;
            $file_info = $this->upload->data();
            $data['path'] = 'public/wants/'.$file_info['orig_name'];
            
            $gimage_id = $this->Wants_model->add_wants_image($data);
            if($gimage_id == false)
            {
                return false;
            }
            
            // echo $data['path'];
            // print_r($info);
        }
    }
    return true;

}  


public function insert_wants(){  

    $this->form_validation->set_rules('title', 'title', 'required');
    $this->form_validation->set_rules('type', 'type', 'required');
    $this->form_validation->set_rules('description', 'description', 'required');
    $this->form_validation->set_rules('price', 'price', 'required');
    $this->form_validation->set_rules('wtag_description', 'wtag_description', 'required');

    if ($this->form_validation->run() == FALSE)
    {
        $message = trim(strip_tags(validation_errors()));
        $error_code = "40210";
        echo_failure($error_code, $message);
        return;
    }

    $sno = $this->sno;
    // $sno = $this->input->post('sno',true);
    $title = $this->input->post('title',true);
    $type = $this->input->post('type',true);
    $time = date("Y-m-d H:i:s");
    $description = $this->input->post('description',true);
    $price = $this->input->post('price',true);
    $wtag_description = $this->input->post('wtag_description',true);


    if($wid = $this->Wants_model->insert($sno, $title, $type, $time, $description, $price, $wtag_description)){
        if($this -> insert_wants_image($wid)) {
            $this -> show_all_wants();
        }else{
            $this->Wants_model->delete($sno,$wid,0);

        }
        
        // echo_success($data);
    }else{
        $message = "添加需求失败";
        $error_code = "40210";
        echo_failure($error_code, $message);
    }

}  



public function insert_wants_collection(){
    $this->form_validation->set_rules('wid', 'wid', 'required');

    if ($this->form_validation->run() == FALSE)
    {
        $message = trim(strip_tags(validation_errors()));
        $error_code = "40220";
        echo_failure($error_code, $message);
        return;
    }

    $sno = $this->sno;
    // $sno = $this->input->post('sno',true);
    $wid = $this->input->post('wid',true);
    $page = $this->input->post('page',true);


    if($this->Wants_model->insert_wants_collection($sno, $wid)){
        $data['wants'] = $this->Wants_model->show_wants_by_sno($sno, '1', $page);
        echo_success($data);
    }else {
            $message = "添加需求收藏失败";
            $error_code = "40220";
            echo_failure($error_code,$message);
        }
}

public function delete_wants(){

    $sno = $this->sno;
    $wid = $this->input->post('wid',true);
    $type = $this->input->post('type',true);
    $page = $this->input->post('page',true);

    $this->form_validation->set_rules('type', 'type', 'required');

    if ($this->form_validation->run() == FALSE)
    {
        $message = trim(strip_tags(validation_errors()));
        $error_code = "40240";
        echo_failure($error_code, $message);
        return;
    }


    if($this->Wants_model->delete($sno,$wid,$type)){
        $data['wants'] = $this->Wants_model->show_wants_by_sno($sno, $type, $page);
        echo_success($data);
    }else{
        $message = "删除需求失败";
        $error_code = "40230";
        echo_failure($error_code,$message);
    }
}

public function show_person_wants(){  

    $sno = $this->sno;
    // $sno = $this->input->post('sno',true);

    $type = $this->input->post('type',true);
    $page = $this->input->post('page',true);

    $this->form_validation->set_rules('type', 'type', 'required');

    if ($this->form_validation->run() == FALSE)
    {
        $message = trim(strip_tags(validation_errors()));
        $error_code = "40240";
        echo_failure($error_code, $message);
        return;
    }


    // 验证不为空
    // $this->form_validation->set_rules('id','学号','required');

    if($data['wants'] = $this->Wants_model->show_wants_by_sno($sno, $type, $page)){
        echo_success($data);
    }else{
        $message = "查询个人需求失败";
        $error_code = "40240";
        echo_failure($error_code,$message);
    }
    

      
}

public function show_all_wants(){  
    $sno = $this->sno;
    $page = $this->input->post('page',true);

    if($data['wants'] = $this->Wants_model->select_all_wants($page)){
        echo_success($data);
    }else{
        $message = "查询全部需求失败";
        $error_code = "40250";
        echo_failure($error_code,$message);
    }

}

public function show_one_want(){  
    $SNO = $this->sno;
    $wid = $this->input->post('wid',true);

    $this->form_validation->set_rules('wid', 'wid', 'required');

    if ($this->form_validation->run() == FALSE)
    {
        $message = trim(strip_tags(validation_errors()));
        $error_code = "40260";
        echo_failure($error_code, $message);
        return;
    }

    if($data['wants'] = $this->Wants_model->show_one_want($wid, $SNO)){
        echo_success($data);
    }else{
        $message = "查询需求详情失败";
        $error_code = "40260";
        echo_failure($error_code,$message);
    }

}



}  
?>  