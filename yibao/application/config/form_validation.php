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
			)
			/*'category' => array
			(
			)*/
		)






?>