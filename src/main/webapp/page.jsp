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



</head>
<body>
	<div class='d_page clearfix'>
		
    	<div class='d_paramBox'>
    		<ul class='d_queryParams'>
    			<!-- 
    			<li  ng-repeat=' qfd in getQfdList() ' class='d_queryParam'><span ng-bind='qfd.desc'></span><input id='{{qfd.label}}' name='{{qfd.label}}'  class='{{qfd.type}}' /></li>
				 -->
				 <li  class='d_queryParam'>
				 		<span >開始</span>
				 		<span>日期</span><input class='date' id='start_date'   ng-model='queryFields.start_date'/>
				 		<span>時間</span><input class='time' id='start_time'  ng-model='queryFields.start_time' placeholder='00:00'/>
				 </li>
				 <li  class='d_queryParam'>
				 		<span >結束</span>
				 		<span>日期</span><input class='date' id='end_date'  ng-model='queryFields.end_date' />
				 		<span>時間</span><input class='time' id='end_time'  ng-model='queryFields.end_time' placeholder='00:00'/>
				 </li>
				 <li  class='d_queryParam'><span >TS</span><input id='ts'  ng-model='queryFields.ts'/></li>
				 <li  class='d_queryParam'><span>SN</span><input  id='sn' ng-model='queryFields.sn'/></li>
				 <li  class='d_queryParam' ><span>Description</span><input style='width:600px;'  id='description' ng-model='queryFields.description'/></li>
				<li>
					<button class='freshQuery' ng-click='freshQuery()'>&nbsp;查&nbsp;詢&nbsp;</button>	
				</li>
				
    		</ul>
    	</div>
    	<div class='d_pageResult'>
    		<table class="table table-expandable">
    			<thead>
    				<tr>
    					<th></th>
    					<th>Hostname</th>
    					<th>log_date</th>
    					<th>Log_Method</th>
    					<th>ErrMessage</th>
    					<th>RemoteAddr</th>
    					<th>TS</th>
    				</tr>
    			</thead>
    			<tbody>
    				<tr class='dude'  ng-repeat-start='rs in getPage().result'>
    					<td><div class="table-expandable-arrow"></div></td>
    					<td ng-bind='rs.hostname'></td>
    					<td ng-bind='rs.log_date'></td>
    					<td ng-bind='rs.Log_Method'></td>
    					<td ng-bind='rs.ErrMessage'></td>
    					<td ng-bind='rs.RemoteAddr'></td>
    					<td ng-bind='rs.TS'></td>
    				</tr>
    				<tr ng-repeat-end='rs in getPage().result' style='display:none;'>
    					<td colspan='7' style='background-color:#FFDED8;'>
    						<ul>
    							<li><strong>Description:</strong><p ng-bind='rs.Description'></p></li>
    							<li><strong>RequestURL:</strong><p ng-bind='rs.RequestURL'></p></li>
    							<li><strong>SN:</strong><p></p ng-bind='rs.SN'></li>
    							<li><strong>RemoteHost:</strong><p ng-bind='rs.RemoteHost'></p></li>
    						</ul>
    					</td>
    				</tr>
    			
    			</tbody>
    		</table>
    	</div>

    	<div class='d_pageChooser'>
    		<ul class='d_pageBtns'>
    			<li class='d_pageBtn'><span id='d_firstPage'>&nbsp;首&nbsp;頁&nbsp;</span></li>
    			<li class='d_pageBtn'><span id='d_prevPage'>前一頁</span></li>
    			<li class='d_pageBtn'><span id='d_nextPage'>后一頁</span></li>
    			<li class='d_pageBtn'><span id='d_lastPage'>&nbsp;尾&nbsp;頁&nbsp;</span></li>
    		</ul>
    		<ul class='d_pageInfos'>
    			<li class='d_pageInfo'><span class='d_pageNow' ng-bind='page.pageIndex'></span></li>
    			<li class='d_pageInfo'>&nbsp;/&nbsp;</li>
    			<li class='d_pageInfo'><span class='d_pageCount' ng-bind='page.totalPage'></span></li>
    			<li class='d_pageInfo'><span class='d_pageCount'>&nbsp;頁&nbsp;</span></li>
    			<li class='d_pageInfo'><span class='d_recordCount' ng-bind='page.totalCount'></span><span>條記錄</span></li>
    		</ul>
 
    	</div>
    </div>

</body>
</html>