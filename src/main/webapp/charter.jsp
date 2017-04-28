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

<style type="text/css">
	.errCountInfo{
					width: 30%;
					line-height: 18px;
					margin-top: 10px;}
	.errCountInfo_key{
					color: #3C3C3C;
					font-size: 14px;
					margin-left: 1em;}
	.errCountInfo_val{ 
					margin-left: 8px;
					font-size: 16px;
					color: red;}
</style>

</head>
	<body>
			<div id='chartInfoInputs'>
				<ul class='chartInfoTypes'>
						<li><span id='chartInfoType_errMessage' class='chartInfoType'>按錯誤類型</span></li>
						<li><span id='chartInfoType_hostname' class='chartInfoType'>按工站類型</span></li>
				</ul>
			</div>
			<div id='chartParamInputs'>
					
					<ul class='chartTypes'>
						<li ng-click='setChartType("day")'  class='chartType'><span >每日錯誤監控</span></li>
						<li ng-click="setChartType('month')"  class='chartType'><span>錯誤統計月報</span></li>
						<li ng-click="setChartType('year')" class='chartType'><span>錯誤統計年報</span></li>
					</ul>
					<ul class='chartParams' >
						<li ng-show='getChartType()=="day"'  class='chartParam'>
							<span >日期</span><input id='dayDatepicker' />
						</li>
						<li  ng-show='getChartType()=="month" || getChartType()=="year"'  class='chartParam'>	
							<select  id='chart_year' ng-model='chartParam.year' >
								<option value='2016' >2016</option>
								<option value='2017' selected='selected'>2017</option>
							</select>
							<span >年</span>
							
							<select id='chart_month' ng-model='chartParam.month'  ng-show='getChartType()=="month"'>
								<option value='0'>1</option>
								<option value='1'>2</option>
								<option value='2'>3</option>
								<option value='3'>4</option>
								<option value='4'>5</option>
								<option value='5'>6</option>
								<option value='6'>7</option>
								<option value='7'>8</option>
								<option value='8'>9</option>
								<option value='9'>10</option>
								<option value='10'>11</option>
								<option value='11'>12</option>
							</select>
							<span     ng-show='getChartType()=="month"'>月</span>
						</li>

					</ul>
					<div id='drawChart' style='cursor:pointer;float:left;margin-left:10px;'><span>查詢</span></div>
				</div>
				<div class='chartLabel'>
					<span>信息統計</span>
				</div>
				<div id='chartInfos' style='overflow:auto;'>
					<ul>
						<li class='errCountInfo' ng-repeat='errCountInfo in getErrCountInfos()'>
							<span class='errCountInfo_key' ng-bind='errCountInfo.key'></span>
							<span class='errCountInfo_val' ng-bind='errCountInfo.value'></span>
						</li>
					</ul>
					
				</div>
				<div class='chartLabel'>
					<span class="statisticsIcon">圖示趨勢</span>
				</div>
				<div id='chartResult'>
							
				</div>
				
	</body>
</html>