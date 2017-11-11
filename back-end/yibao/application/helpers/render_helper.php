<?php
defined('BASEPATH') OR exit('No direct script access allowed');
/**

  打印不转义中文的json

  @param [array] $data

 */

function echo_success($data) {

	$json = array(
		'error_code' => 0,
		'data' => $data
	);
    echo json_encode ($json, JSON_UNESCAPED_UNICODE );

}


function echo_failure($error_code,$message) {

	$json = array(
		'error_code' => $error_code,
		'message' => $message
	);
    echo json_encode ( $json, JSON_UNESCAPED_UNICODE );

}




?>