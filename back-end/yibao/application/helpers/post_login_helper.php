<?php
header("Content-type:text/html;charset=utf-8");


defined('BASEPATH') OR exit('No direct script access allowed');

function post_login($sno,$password)
{

			header('content-type:text/html;charset=utf-8');    


			$post_data="muser=".$sno."&passwd=".$password;


			$url = "http://59.77.226.32/logincheck.asp";
			$User_Agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0";
			$Referer = "http://jwch.fzu.edu.cn/";

			$ch = curl_init();
			curl_setopt($ch, CURLOPT_URL, $url);		//设置URL
			curl_setopt($ch, CURLOPT_HEADER, true);		//设置显示响应头
			curl_setopt($ch,CURLOPT_USERAGENT,$User_Agent); //设置代理浏览器
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); //把CRUL获取的内容赋值到变量,不直接输出
			curl_setopt($ch,CURLOPT_REFERER,$Referer);	//设置来源网站
			curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);//设置重定向
			curl_setopt($ch, CURLOPT_POSTFIELDS, $post_data);//设置参数
			$content = curl_exec($ch);


			curl_close($ch);


			$header = explode(PHP_EOL, $content);
			foreach ($header as $each) {
				$preg_id = "/default.aspx\?id=(.*)/is";

				if(preg_match_all($preg_id, $each, $arr_id))
				{

					$id = $arr_id[1][0];
				}

				$preg_cookie = "/Set-Cookie:\s+(.*)/is";

				if(preg_match_all($preg_cookie, $each, $arr_cookie))
				{

					$cookie = $arr_cookie[1][0];

				}


			}

					if(isset($id))
					{


							$info_url = "http://59.77.226.35/jcxx/xsxx/StudentInformation.aspx?id=".$id;
							$User_Agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0";
							$Referer = "http://59.77.226.35/left.aspx?id=".$id;
							$info_url = trim($info_url);
							$Referer = trim($Referer);
							$headers = array(
    								'Content-type: application/x-www-form-urlencoded;charset=UTF-8'
							);
							$ch = curl_init();
							curl_setopt($ch, CURLOPT_URL, $info_url);			//设置URL
							curl_setopt($ch, CURLOPT_COOKIE, trim($cookie));			
							curl_setopt($ch, CURLOPT_HEADER, true);		//设置显示响应头
       					    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
							curl_setopt($ch, CURLINFO_HEADER_OUT, true);


							curl_setopt($ch,CURLOPT_USERAGENT,$User_Agent); //设置代理浏览器
							curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); //把CRUL获取的内容赋值到变量,不直接输出
							curl_setopt($ch,CURLOPT_REFERER,$Referer);		//设置来源网站
							$content = curl_exec($ch);
							// $x = curl_getinfo($ch);
							// print_r($x) ;

  					// 		  echo 'Curl error: ' . curl_error($ch);
							// if($errno = curl_errno($ch)) {
  					// 		 	 $error_message = curl_strerror($errno);
   				// 				 echo "cURL error ({$errno}):\n {$error_message}";
							// }



							curl_close($ch); 

							$content=strip_tags($content);//去除html标签
							$user = array();
							$user['sno'] = $sno;

							$preg_name = "/姓名(.*?)姓名/is";
							if(preg_match_all($preg_name, $content, $array))
							{
								$str = $array[1][0];
								$str = str_replace(PHP_EOL, '', $str);  
								$str = trim($str);
								$user['user_name'] = $str;

							}

							$preg_phone = "/本人电话\s+(.*?)\s+E-mail/is";
							if(preg_match_all($preg_phone, $content, $array))
							{
								$user['phone'] = $array[1][0];

							}

							$preg_major = "/专业名称\s+(.*?)\s+/is";
							if(preg_match_all($preg_major, $content, $array))
							{
								$user['major'] = $array[1][0];
							}

							$preg_grade = "/年级\s+(.*?)\s+/is";
							if(preg_match_all($preg_grade, $content, $array))
							{
								$user['grade'] = $array[1][0];
							}

							return $user;
					}else{
						return false;
					}

}



?>



