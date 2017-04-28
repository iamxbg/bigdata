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
<title>Insert title here</title>
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
			border:1px solid red;
			height:40px;
		}
		
		#loginBox{
			width:420px;
			border:1px solid blue;
			margin-top:90px;
			padding:0px 0px 20px 0px;
		}
		
		#loginBoxLabel{
			background:gray;
			height:30px;
			width:100%;
		}
		
		ul{
			list-style:none;
			border:1px solid blue;
			padding:20px 10px;
			margin-top:20px;
		}
		
		ul li label{
			border:1px solid blue;
			padding:4px;
			position:relative;
			left:-10px;
		}
		ul li input{
			border:1px solid green;
			padding:4px;
		}
	</style>
	
</head>
<body>
	<div id='header'></div>
	<center>
	<div id='loginBox' >
		<form method="post" action="login/admin">		
			<div>
				<div id='loginBoxLabel'></div>
				<ul >
					<li>
						<label>USERNAME:</label>
						<input name='username'/>
					</li>
					<li>
						<label>PASSWORD:</label>
						<input name='password'/>
					</li>
				</ul>
				
				<button><span style='color:red;'>Login</span></button>
			</div>
		</form>
	</div>
	</center>
</body>
</html>