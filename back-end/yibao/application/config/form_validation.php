<?php
    

	$config = array
	(
		'update_user_info' =>array
		(
			
			array(
					'field'  => 'nickname',
					'label'  => 'nickname',
					'rules'  =>'required'
				 ),
			array(
				'field'  => 'phone',
				'label'  => 'phone number',
				'rules'  =>'required|max_length[11]'
			 	)
		),
		/*'category' => array
		(
		)*/
		'add_message_info' =>array
		(
			array(
					'field'  => 'gid',
					'label'  => 'gid',
					'rules'  =>'required'
				 ),
			array(
					'field'  => 'parent_id',
					'label'  => 'parent_id',
					'rules'  =>'required'
				 ),
			array(
					'field'  => 'from_sno',
					'label'  => 'from_sno',
					'rules'  =>'required'
				 ),
			array(
					'field'  => 'to_sno',
					'label'  => 'to_sno',
					'rules'  =>'required'
				 ),

			array(
					'field'  => 'content',
					'label'  => 'content',
					'rules'  =>'required|max_length[300]'
				 )

			//gid,parent_id,from_sno,to_sno,content,message_time
		),
		'add_goods_info' => array
		(
			array(
				'field' => 'goods_name',
				'label' => 'goods_name',
				'rules' => 'required'
			),
			array(
				'field' => 'type',
				'label' => 'type',
				'rules' => 'required|greater_than[0]'
			),
			array(
				'field' => 'status',
				'label' => 'status',
				'rules' => 'required|in_list[0,1]'
			),
			array(
				'field' => 'price',
				'label' => 'price',
				'rules' => 'required'
			),
			array(
				'field' => 'description',
				'label' => 'description',
				'rules' => 'max_length[500]'
			),
		)
	);






?>