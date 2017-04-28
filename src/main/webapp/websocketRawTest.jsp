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
	
	
</head>
<body>
	<div>
		<div  style='border:1px solid red;'>
			<label>MEssage</label><input name='message'/>
			<button id='sendMessage'>SEND</button>
		</div>
		
		
		<div style='border:1px solid blue;'>
			<lable>Get Subscribe:</lable>
			<button id='getSub'>Get SubScribe</button>
		</div>
		
		<button id='mockInner'>Test Inner:</button>
	</div>
	
	<script type="text/javascript">
		
		
		$(function(){
			
			var sock=null;
			var client=null;
			
			var url="<%=basePath%>websocket"
			
			
			$('#sendMessage').on('click',function(){
				alert('version-1')
				alert('# sock:'+sock+' #clietn:'+client)
				if(sock==null && client==null){
					
					 sock=new SockJS(url);
					 client=Stomp.over(sock);
					
					client.connect(null,null,function(){
						alert('Client connect success')
						
						var message=$('input[name="message"]').val();
						client.send("/message/test",{},message);
						
					},function(err){

					})
					
					
				}else{
					alert('send Directlly')
					
					var message=$('input[name="message"]').val();
					client.send("/message/test",{},message);
					
				}	
				
			})
			
			$('#getSub').on('click',function(){
					alert('Get SUbscribe.')	
					
					
					if(sock==null && client==null){
						
						 sock=new SockJS(url);
						 client=Stomp.over(sock);
						
						client.connect(null,null,function(){
							alert('Client connect success')
							
							var headers=null;
							
							client.subscribe("/topic/test",function(msg){

									
								},headers)
							
						},function(err){
							
						})
						
						
					}else{
						alert('Subscribe directlly')
						
						var headers={};
						
						client.subscribe("/topic/test",function(msg){

							
						},headers)
						
					}	
					
					
					
					
			})

			
		})
		
		
			
		$('#mockInner').on('click',function(){
			
			$.ajax({
				url:'<%=basePath%>testMsgTemplate',
				type:'post',
				success:function(d){
					
					//console.log('Ajax Success');
				}
				
			});
			
		});	
			
			//sock.send('test')
			//sock.close()
	</script>
</body>
</html>