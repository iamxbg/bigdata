<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang='en'>
<head>
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
	<script type="text/javascript" src='<%=basePath%>js/echarts.js'></script>
	
	
</head>
<body>
		<div>
			<label>timeWidth:</label>
			<select name='timeWidth'>
				<option value='year'>year</option>
				<option value='month'>month</option>
				<option value='week'>week</option>
				<option value='day'>day</option>
				<option value='hour'>hour</option>
			</select>
			<label>timeSlice:</label>
			<select name='timeSlice'>
				<option value='month'>month</option>
				<option value='week'>week</option>
				<option value='day'>day</option>
				<option value='hour'>hour</option>
				<option value='minute'>minute</option>
			</select>
			
			<label>YEAR:</label><input name='year'/>
			<label>MONTH:</label><input name='month'/>
			<label>WEEK:</label><input name='week'/>
			<label>DAY:</label><input name='day'/>
			<label>HOUR:</label><input name='hour'/>
			
			<button id='draw'>DRAW</button>
		</div>
		<div id='myChart' style='width:1000px;height:500px;border:1px solid blue;'> </div>
		
		
		
		
		<script type="text/javascript">
		require.config({
			paths:{
				echarts:'<%=basePath%>js/echarts'
				
			}
		})
		
		
		$('#draw').on('click',function(){
			var timeWidth=$('select[name="timeWidth"]').val();
			var timeSlice=$('select[name="timeSlice"]').val();
			
			var year=$('input[name="year"]').val();
			var month=$('input[name="month"]').val();
			var week=$('input[name="week"]').val();
			var day=$('input[name="day"]').val();
			var hour=$('input[name="hour"]').val();
			
			console.log('timeWidth:'+timeWidth+' timeSlice:'+timeSlice+' year:'+year+' month:'+month+' day:'+day+'hour:'+hour);
			
			$.ajax({
				url:'<%=basePath%>bobcat/chart/ErrMessage',
				type:'post',
				data:{'timeWidth':timeWidth,
					'timeSlice':timeSlice,
					'year':year,
					'month':month,
					'day':day,
					'hour':hour
				},
				success:function(d){

					
					require([
						    	'echarts',
						    	'echarts/chart/line'
							],
							function(ec){
							
						
									var myChart=ec.init(document.getElementById('myChart'));
									
									//base init
									option={
											title:{
												text:'bobcat系統錯誤統計',
												subtext:'WhyNotSoSerious'
												
											},
											tooltip:{
												trigger:'axis',
												axisPointer:{
													type:'shadow'
												}
											},
											legend:{
												x:'right',
												data:[],
												padding:[10,5,5,5]
											},
											toolbox:{
												show:true,
												orient:'vertical',
												x:'right',
												y:'center',
												feature:{
													mark:{show:true},
													dataView:{show:true,readOnly:false},
													magicType:{show:true,type:['line','bar','stack','tiled']},
													restore:{show:true},
													saveAsImage:{show:true}
												}
											},
												calculable:true,
												xAxis:[
												       		{
												       			type:'category',
												       			boundaryGap:false,
												       			data:[]
												       			
												       		}
												       ],
												 yAxis:[
												        	{
												        		type:'value'
												        	}
												        ],
												        
												  series:[]
											
									}

									option.legend.data=d.legends;
									
									for(var l=0;l<d.xAxisLen;l++){
										option.xAxis[0].data.push(l+1);
									}
									
									for(var z=0;z<d.legends.length;z++){
										
										var legend=d.legends[z];
										
										var obj={
												name:legend,
												data:d.seriesMap[legend],
												type:'line'
										}
										
										option.series.push(obj);
										
									}
									
				
									myChart.setOption(option);
								}
					
			
					);
					
				}
				
			})
			
		})
		
	
		</script>
</body>
</html>