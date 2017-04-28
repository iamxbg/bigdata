<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>大數據日誌查詢系統</title>
	<!-- bootstrap css -->
	<link  rel="stylesheet" style="text/css" href="<%=basePath%>css/bootstrap-theme.min.css"/>
	<link  rel="stylesheet" style="text/css" href="<%=basePath%>css/bootstrap.min.css"/>
	<!-- insert js  -->
	 
	<script type="text/javascript" src='<%=basePath%>js/angular.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/sockjs.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/jquery.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/bootstrap.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/stomp.js'></script>
	 
	
	<style>
		#header{
			background-color:#181D20;
			height:140px;
		}
		#header h1{ width:1200px; margin:0 auto; line-height:140px;}
		#header h1 img{ margin-left:10px; }
		.loginWrap{ background:#DA5043 url(images/bannerBg.jpg) center top no-repeat;}
		.loginCenter{width:1200px; margin:0px auto; padding:10px 20px;height: 434px;}
		.loginCenter .loginLeft{ width:529px; height:253px; background:url(images/leftPic.png) no-repeat; margin: 60px 0 0 60px; float:left;}
		#loginBox{
			width:348px;
			height:306px;
			background:url(images/formBoxBg.png) no-repeat;
			margin-top:50px;
			float:right;
			margin-right:90px;
		}
		
		#loginBoxLabel{
			height:30px;
			width:80%;
			margin:20px auto 0;
			font-size:18px;
			color:#181D21;
			font-family:Verdana,"Microsoft YaHei","微軟正黑體","微软繁黑体"; 
		}
		
		ul{
			list-style:none;
			margin-top:20px;
			width: 90%;
		}
		
		ul li{
			margin-bottom:15px;
		}
		
		ul li label{
			border:1px solid #AAAAAA;
			width:44px;
			height:36px;
			line-height:34px;
			float:left;
			text-align:center;
			border-top-left-radius:5px;
			border-bottom-left-radius:5px;
		}
		ul li input{
			border:1px solid #AAAAAA;
			height:36px;
			line-height:36px;
			border-top-right-radius:5px;
			border-bottom-right-radius:5px;
			width:216px;
    		border-left:none;
    		text-indent:6px;
    		display:inline-block;
		}
		.errMsg{ width:260px; margin-left:40px; color:red; font-size:12px;height: 20px;margin-bottom: 10px;font-family:Verdana,"Microsoft YaHei","微軟正黑體","微软繁黑体"; }
		
		button{
			width:260px;
			height:38px;
			line-height:38px;
			background-color:#DA5043;
			border:none;
			border-radius:5px;
			margin-left:40px;
			font-family:Verdana,"Microsoft YaHei","微軟正黑體","微软繁黑体";
			font-weight:bold;
		}
		#footer{}
		#footer p{ color:#000000; font-size:14px; text-align:center; line-height:48px;}
	</style>
	
</head>
<body>
	<div id='header'>
		<h1><img alt="" src="images/loginLogo.png"></h1>
	</div>
	<div class="loginWrap">
	<div class="loginCenter">
		<div class="loginLeft">	</div>
		<div id='loginBox' >
			<form method="post" action="<%=basePath%>login/desktop">		
				<div>
					<div id='loginBoxLabel'>賬戶登錄</div>
					<ul >
						<li>
							<label for="username"><img src="images/loginUserIcon.png" /></label>
							<input name='username' id="username" placeholder='請輸入用戶名'/>
						</li>
						<li>
							<label for="password" ><img src="images/loginPswIcon.png" /></label>
							<input type='password' name='password' id="password" placeholder='請輸入密碼'/>
						</li>
					</ul>
					<div class="errMsg"></div>
					<button><span style='color:#fff; font-size:16px;'>登&nbsp;&nbsp;&nbsp;&nbsp;錄</span></button>
				</div>
			</form>
		</div>
	</div>
	</div>
	
	<div id="footer">
		<p>版權所有 &copy; iDSBG 資訊科技處</p>
	</div>
</body>
</html>