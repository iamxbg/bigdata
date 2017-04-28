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
<title>Monitor-for angularJS</title>

</head>
<body>
		<div ><span id='monitorSpan' style='font-size:14px;font-weight: bold;'>成都今日監控</span></div>
		<ul>
		
			<li id='monitorData' class='monitor'>
				<div class='monitorLabel'><span>最近錯誤信息</span></div>
				<!-- 
				<div class='monitorView'>
					<ul id='metaInfo_label'>
						<li>最近更新時間</li>
						<li>上次更新時間</li>
						<li>錯誤總數</li>
						<li style="border-right:1px solid #cdcdcd;">新增錯誤數</li>
					</ul>
					<ul id='metaInfo_data'>
						<li><span ng-bind='getMetaInfo().refreshTime'></span></li>
						<li><span ng-bind='getMetaInfo().lastRefreshTime'></span></li>
						<li><span ng-bind='getMetaInfo().count'></span></li>
						<li style="border-right:1px solid #cdcdcd;"><span ng-bind='getMetaInfo().increment'></span></li>
					</ul>
				</div>
				 -->
				 <div class='monitorView'>
					<ul id='metaInfo_label'>
						<li><span class='mi_label'>最近統計時間</span><span class='mi_val' ng-bind='getMetaInfo().refreshTime'></span></li>
						<!-- <li><span class='mi_label'>上次更新時間</span><span class='mi_val' ng-bind='getMetaInfo().lastRefreshTime'></span></li> -->
						<li><span class='mi_label'>錯誤總數&nbsp;&nbsp;</span><span class='mi_val' ng-bind='getMetaInfo().count'></span></li>
						<li style="border-right:1px solid #cdcdcd;"><span class='mi_label'>新增錯誤數&nbsp;</span><span class='mi_val' ng-bind='getMetaInfo().increment'></span></li>
					</ul>
				</div>
			</li>
			<!-- 今日錯誤統計 -->
			<li id='monitorChart' class='monitor'>
				<div class='monitorLabel'><span class="todayError">成都今日錯誤統計</span></div>
				<div class='monitorView'>
					<div id='leftChart'>
			 			<div id='statusChart'>
			 				<svg id="fillgauge1" width="200" height="200" style='margin-left:90px;margin-top:35px;' onclick="gauge1.update(NewValue());"></svg>
			 			</div>
					 </div>
					 
					 <div id='rightChart'>
					 	<div id='todayErrLineChart'></div>
					 </div> 
				</div>
			</li>
			
			<!-- 全天錯誤曲綫圖 -->
			<!-- 
			<li id="errorChart"  class='monitor'>
				<div class='monitorLabel'><span class="allDayError">成都全天錯誤曲綫圖</span></div>
				<div class='monitorView'>
					<div id='barChart'></div>
				</div>
			</li>
			 -->
			<!-- 錯誤統計 by 類型 -->
			<li  class='monitor' style='width:49%; margin-right:2%;'>
				<div class='monitorLabel'><span class="allDayErrorBlue">成都錯誤類型錯誤統計</span></div>
				<div class='monitorView'>
					<div id='errMessageChart'></div>
				</div>
			</li>
			<!-- 錯誤統計 by 工站 -->
			<li  class='monitor' style='width:49%;'>
				<div class='monitorLabel'><span class="allDayErrorBlue">成都工站類型錯誤統計</span></div>
				<div id='hostnameChart' class='monitorView'>
				
				</div>
			</li>
			
		</ul>

</body>
</html>