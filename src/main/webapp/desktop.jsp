<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang='en' ng-app="bigdataDesktopApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bobcat日誌監控</title>
	<!-- boostrap css -->
	<link  rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap-theme.min.css"/>
	<link  rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap.min.css"/>
	<link  rel='stylesheet' type='text/css' href='<%=basePath%>css/desktop.css'/>
	<link  rel='stylesheet' type='text/css' href='<%=basePath%>css/page.css'>
	<link rel='stylesheet' type='text/css' href='<%=basePath%>js/jquery-ui/jquery-ui.min.css'>
	<link rel='stylesheet' type='text/css' href='<%=basePath%>js/jquery-ui/jquery-ui.structure.min.css'>
	<link rel='stylesheet' type='text/css' href='<%=basePath%>js/jquery-ui/jquery-ui.theme.min.css'>
	
	<!-- insert js -->
	<script type="text/javascript" src='<%=basePath%>js/angular.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/sockjs.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/jquery.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/jquery-ui/jquery-ui.min.js'/></script>
	<script type="text/javascript" src='<%=basePath%>js/bootstrap.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/stomp.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/echarts.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/d3.v3.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/liquidFillGauge.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/sankey.js'></script>
	
	<link  rel="stylesheet" style="text/css" href="<%=basePath%>css/bootstrap-table-expandable.css"/>
	<script type="text/javascript" src='<%=basePath%>js/bootstrap-table-expandable.js'></script>
	<style>

		#todayErrLineChart{
			width:65%;
			height:300px;
			float:left;
		}
		
		#lh_todayErrLineChart{
			width:65%;
			height:300px;
			float:left;
		}
		
		#statusChart{
			border-bottom: 1px solid #cdcdcd;
    		border-left: 1px solid #cdcdcd;
			width:35%;
			float:left;
			height:300px;
		}
		
		#lh_statusChart{
			border-bottom: 1px solid #cdcdcd;
    		border-left: 1px solid #cdcdcd;
			width:35%;
			float:left;
			height:300px;
		}
		
		#barChart{
			width:100%;
			height:200px;
			border: 1px solid #cdcdcd;
			border-top:none;
		}
		
		#lh_barChart{
			width:100%;
			height:200px;
			border: 1px solid #cdcdcd;
			border-top:none;
		}
		
		/*
			Sankey
		*/
		
		.node rect {
		  cursor: move;
		  fill-opacity: .9;
		  shape-rendering: crispEdges;
		}
		
		.node text {
		  pointer-events: none;
		  text-shadow: 0 1px 0 #fff;
		}
		
		.link {
		  fill: none;
		  stroke: #000;
		  stroke-opacity: .2;
		}
		
		.link:hover {
		  stroke-opacity: .5;
		}
		

	</style>
	
	<script>
		$(function(){
			$('#expandTabs').on('mouseover','#currentModule',function(){
				$('#expandTabs').css('overflow','visible');
			}).on('mouseleave',function(){
				$('#expandTabs').css('overflow','hidden');
			});
			
			$('.tabs .tab').on('mouseover',function(){
				//$(this).css('background','#DA5043');
			}).on('mouseleave',function(){
				//$(this).css('background','none');
			}).on('click',function(){
					//$(this).siblings().css('background','none')
					//$(this).css('background','skyblue')
					$(this).siblings().removeClass("tabActive");
					$(this).addClass("tabActive");
					
				var id=$(this).attr('id');
				$('.liContent').css('display','none');
				$('.options').css('display','none');
				if('dataTab'==id){
					$('#dataContent').css('display','block');
				}else if('chartTab'==id){
					$('#chartContent').css('display','block');
				}else if('monitorTab'==id){
					$('#monitorContent').css('display','block');
				}
			});
			
			
		})
	</script>
	<script language="javascript" type="text/javascript">
    function logout(){
        if (confirm("您確定要退出嗎？"))
            top.location = "../index.jsp";
         return false;
    }
	</script>

</head>
<body  ng-controller='NavigationController as naviCtrl'>
	<div id='header'>
		<div id='expandTabs'  >
			<div id='currentModule'><span></span></div>
		</div>
		<div navigation></div>
		<div id="userShow">
			<span>bobcat</span>
			<a onclick="logout()" href="javascript:window.opener=null;%20window.close();">退出</a>
		</div>
		
	</div>
	<div id='middle'>
		<div id='middlePlaceHolder'></div>
		<div id='middleLeftPlaceHolder'></div>
		<div id='middleLeft'>
			<div class='tabs'>
				<ul>
					<li id='monitorTab' class='tab tabActive'>
						<span class="monitorIcon monitorIconActive">實時監控</span>
					</li>
					<li id='dataTab' class='tab'>
						<span class="dataSearchIcon">數據查詢</span>
					</li>
					<li id='chartTab' class='tab'>
						<span class="chartIcon">錯誤圖表</span>
					</li>
					
				</ul>
			</div>
		</div>
		<div id='middleRight'>
			
			<!-- Dynamic Load Page Here1 -->
			<div class='tab_content'>
				<ul>
					<li id='monitorContent' class='liContent' style='display:block;'>
						<div monitor></div>
					</li>
					<li id='dataContent' class='liContent'>
						<div pager></div>
					</li>
					<li id='chartContent' class='liContent'>
						<charter />
						
						
						
					</li>
				</ul>
			</div>
		</div>
	</div>
	
		<script>
		require.config({
			paths:{
				echarts:'<%=basePath%>js/echarts'
				
			}
		});
		
		
		var todayErrMessageOption = {
							    tooltip : {
							        trigger: 'axis'
							    },
							    legend: {
							        data:['']
							    },
							    toolbox: {
							        show : false,
							        feature : {
							            mark : {show: true},
							            dataView : {show: true, readOnly: false},
							            magicType : {show: true, type: ['line', 'bar']},
							            restore : {show: true},
							            saveAsImage : {show: true}
							        }
							    },
							    calculable : true,
							    xAxis : [
							        {
							            type : 'category',
							            data :[]
							        }
							    ],
							    yAxis : [
							        {
							            type : 'value'
							        }
							    ],
							    series : [
							        {
							            name:'錯誤數量',
							            type:'bar',
							            data:[]
							        }
							    ]
							};
		//-----------------------------------------------------------------------------------------
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		var barChartOption = {
							    tooltip : {
							        trigger: 'axis',
							        axisPointer : {
							            type: 'shadow'
							        }
							    },
							    legend: {  
							    	data:[]
							    },
							    toolbox: {
							        show : false,
							        orient : 'vertical',
							        y : 'center',
							        feature : {
							            mark : {show: true},
							            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
							            restore : {show: true},
							            saveAsImage : {show: true}
							        }
							    },
							    calculable : true,
							    xAxis : [{
							    	type:'category',
							    	data:[]
							    }],
							    yAxis : [
							        {
							            type : 'value',
							            splitArea : {show : true}
							        }
							    ],
							    grid: {
							        x2:40
							    },
							    series : [
							       
							    ]
							};
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		var hourOption = {
								    tooltip : {
								        trigger: 'axis'
								    },
								    legend: {
								        data:['']
								    },
								    toolbox: {
								        show : false,
								        feature : {
								            mark : {show: true},
								            dataView : {show: true, readOnly: false},
								            magicType : {show: true, type: ['line', 'bar']},
								            restore : {show: true},
								            saveAsImage : {show: true}
								        }
								    },
								    calculable : true,
								    xAxis : [
								        {
								            type : 'category',
								            boundaryGap : false,
								            data : []
								        }
								    ],
								    yAxis : [
								        {
								            type : 'value',
								            axisLabel : {
								                formatter: '{value} '
								            }
								        }
								    ],
								    series : [
								              {
								                  name:'錯誤趨勢',
								                  type:'line',
								                  data:[],
								                  markPoint : {
								                      data : [
								                          {type : 'max', name: '最大值'},
								                      ]
								                  },
								                  markLine : {
								                      data : [
								                          {type : 'average', name: '平均值'}
								                      ]
								                  }
								              }
								    ]
								};
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		var hostnameOption = {
								    tooltip : {
								        trigger: 'axis'
								    },
								    legend: {
								        data:['']
								    },
								    toolbox: {
								        show : false,
								        feature : {
								            mark : {show: true},
								            dataView : {show: true, readOnly: false},
								            magicType : {show: true, type: ['line', 'bar']},
								            restore : {show: true},
								            saveAsImage : {show: true}
								        }
								    },
								    calculable : true,
								    xAxis : [
								        {
								            type : 'category',
								            data : [],
								            axisLabel:{
								            	rotate:0,
								            	interval:0
								            	
								            }
								        }
								    ],
								    yAxis : [
								        {
								            type : 'value'
								        }
								    ],
								    series : [
								        {
								            name:'錯誤數量',
								            type:'bar',
								            data:[],
								        }
								    ]
								};
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		var app=angular.module('bigdataDesktopApp',[]);

		//monitor Directive
		app.directive('monitor',['$interval','$q','$http','MonitorService','NavigationService',function($interval,$q,$http,MonitorService,NavigationService){
			
			
			var link=function($scope,elem,attrs,ctrl){
				
				
				require([
					    	'echarts',
					    	'echarts/chart/line',
					    	'echarts/chart/bar',
					    	'echarts/chart/pie',
					    	'echarts/chart/gauge',
						],
						function(ec){

							
							var barChart=ec.init(document.getElementById('barChart'))
								
							
								var area_url=NavigationService.area_url;	
								$.ajax({
									url:"<%=basePath%>"+area_url+"/chart/today/hour",
									type:'get',
									success:function(d){
										
										

										//----------------------------------------------------------------------------------

										// set barChart options					

										barChartOption.xAxis[0].data=d.xAxisLabels;

										var chart={
												name:'錯誤數量',
												type:'bar',	
												data:d.data
										}
										
										barChartOption.series.push(chart);


	                    				barChart.setOption(barChartOption);

									}
									
								});
	
				});
				
				
				var startuChart=null;
				
				require(['echarts',
						'echarts/chart/line',
						'echarts/chart/bar',
						'echarts/chart/pie',
						'echarts/chart/gauge'],function(ec){
					
					var hostnameChart=ec.init(document.getElementById('hostnameChart'))
					var errMessageChart=ec.init(document.getElementById('errMessageChart'))	
					var todayErrLineChart=ec.init(document.getElementById('todayErrLineChart'))
					

					var hourDefer=$scope.getErrGroupByHour();
						hourDefer.promise.then(function(d){

							var xAxisLabels=d.xAxisLabels;
	
							hourOption.xAxis[0].data=d.xAxisLabels;
							hourOption.series[0].data=d.data;

							todayErrLineChart.setOption(hourOption)
						});

						
					var hostnameDefer=$scope.getErrGroupByHostname();
						hostnameDefer.promise.then(function(data){
							
							var xAxisLabels=data.xAxisLabels;
							var numLabels=[];
							for(var n=0;n<xAxisLabels.length;n++){
								numLabels.push(xAxisLabels[n].substr(xAxisLabels[n].lastIndexOf('-')+1));
							}
							
							
							
							hostnameOption.xAxis[0].data=numLabels;
							hostnameOption.series[0].data=data.data;
							
							hostnameChart.setOption(hostnameOption)
						});

						
						var errMessageDefer=$scope.getErrGroupByErrMessage();
						errMessageDefer.promise.then(function(data){
							
							todayErrMessageOption.xAxis[0].data=data.xAxisLabels;
							todayErrMessageOption.series[0].data=data.data;
							
							
							errMessageChart.setOption(todayErrMessageOption)					
						});
						
					
				})
				
				var config1 = liquidFillGaugeDefaultSettings();
						config1.circleThickness = 0.15;
						config1.textVertPosition = 0.5;
						config1.waveAnimateTime = 1000;
						config1.textSize=0.8;
						config1.displayPercent=false;
						config1.maxValue=10000;
						config1.waveTextColor='#000000';
						config1.circleColor='#00BFFF';
						config1.waveColor='#00FFFF';
						config1.textColor='#045681';
	
						var gauge1 = loadLiquidFillGauge("fillgauge1", 0,config1);


			    		var area_url=NavigationService.area_url;
						$http.get("<%=basePath%>"+area_url+"/chart/today/count")
						.success(function(data){
							var count=data.count
							console.log('<<>> fresh count-CD:'+count);
							
							var increment=count-MonitorService.metaInfo.count;

							
							var date=new Date();
								var hour=date.getHours();
								var min=date.getMinutes();
								var sec=date.getSeconds();
								
								var oldTime=MonitorService.metaInfo.refreshTime.toString();
								
								var refreshTime="".concat(hour<10?"0"+hour:hour,":",min<10?"0"+min:min,":",sec<10?"0"+sec:sec)

							
								MonitorService.metaInfo={count:count,
									lastRefreshTime:oldTime,
									refreshTime:refreshTime,
									increment:increment}
								
								
								var newValue=count;

						    	var ratio=newValue/config1.maxValue;
						    	gauge1.update(newValue,ratio)   
						    	
						    	
						}).error(function(){
							
						});


				$interval(function(){
					
					var area_url=NavigationService.area_url;
					$http.get("<%=basePath%>"+area_url+"/chart/today/count")
						.success(function(data){
							var count=data.count;
							
							var increment=count-MonitorService.metaInfo.count;

							var date=new Date();
								var hour=date.getHours();
								var min=date.getMinutes();
								var sec=date.getSeconds();
								
								var oldTime=MonitorService.metaInfo.refreshTime.toString();
								
								var refreshTime="".concat(hour<10?"0"+hour:hour,":",min<10?"0"+min:min,":",sec<10?"0"+sec:sec)

							
							MonitorService.metaInfo={count:count,
									lastRefreshTime:oldTime,
									refreshTime:refreshTime,
									increment:increment}

								var newValue=count;

						    	var ratio=newValue/config1.maxValue;
						    	gauge1.update(newValue,ratio)   
						    	
						    	
						}).error(function(){
							
						});
					
					
					
				},300000);
				
				
			}
			
			return {
				link:link,
				templateUrl:'<%=basePath%>monitor.jsp',
				controller:'MonitorController'
				
			}
			
			
		}]).controller('MonitorController',['$scope','MonitorService','NavigationService',function($scope,MonitorService,NavigationService){
			
				var ctrl=this;
				
				$scope.getMetaInfo=function(){
					return MonitorService.metaInfo;
				}
				
				
				$scope.getErrGroupByHostname=function(){
					var area_url=NavigationService.area_url;
					var defer=MonitorService.getErrGroupByHostname(area_url);
						return defer;
				}

				$scope.getErrGroupByErrMessage=function(){
					var area_url=NavigationService.area_url;
					
					var defer=MonitorService.getErrGroupByErrMessage(area_url);
					return defer;
				}

				$scope.getErrGroupByHour=function(){
					var area_url=NavigationService.area_url;
					var defer=MonitorService.getErrGroupByHour(area_url);
					return defer;
				}

				
		}]).service('MonitorService',['$q','$http',function($q,$http){
				var serv=this;
					
					
				
					serv.metaInfo={
							refreshTime:"----",
							lastRefreshTime:"-----",
							count:0,
							increment:0
					}
					
					
					serv.getErrGroupByErrMessage=function(area_url){
						var defer=$q.defer();
						$http.get('<%=basePath%>'+area_url+'/chart/today/errMessage')
							.success(function(data){
								defer.resolve(data);
							});
						
						return defer;
					}
					
					serv.getErrGroupByHostname=function(area_url){
						var defer=$q.defer();
						$http.get('<%=basePath%>'+area_url+'/chart/today/hostname')
							.success(function(data){
								defer.resolve(data);
							});
						return defer;
					}
					
					serv.getErrGroupByHour=function(area_url){
						var defer=$q.defer();
						$http.get("<%=basePath%>"+area_url+"/chart/today/hour")
							.success(function(data){
								defer.resolve(data);
							});
						
						return defer;
					}
					
				
				

		}]);
		
		//Chart Directive
		app.directive('charter',['$q','$http','ChartService','NavigationService',function($q,$http,ChartService,NavigationService){
			
			
			var link=function($scope,elem,attrs,ctrl){

				(function($){	
					
					jQuery(elem).find('#dayDatepicker')
							.datepicker({'dateFormat':'yy/mm/dd'},jQuery.datepicker.regional[ "zh-TW" ]);
				})(jQuery);

				//---------------------------------------------
				var date=new Date();
			
				
				$(elem).find('.chartType').on('click',function(){
					$(this).closest('ul').find('.chartType').css({"background":"none","color":"#161616","border":"1px solid #AAAAAA"})
						.end().end().css({"background":"#D94F44","color":"#ffffff","border":"none"})
				});
				
				$(elem).find('.chartInfoType').on('click',function(){
						var id=$(this).attr('id');
						console.log('infoType:'+id)
						var chartInfoType=null;
						if(id=='chartInfoType_hostname'){
							ChartService.chartInfoType="Hostname";
							
						}else if(id=='chartInfoType_errMessage'){
							ChartService.chartInfoType="ErrMessage";
						}
						
						$(this).closest('ul').find('.chartInfoType').css({"background":"none","color":"#161616","border":"1px solid #AAAAAA"})
								.end().end().css({"background":"#D94F44","color":"#ffffff","border":"none"});
				});
				

				
				$(elem).find('#drawChart').on('click',function(){

					// package parameters			
					var chartType=$scope.getChartType();
					var chartInfoType=$scope.getChartInfoType();
					//go query

						var millis=(function($){return jQuery(elem).find('#dayDatepicker').datepicker('getDate')}(jQuery));
						
						
						var area_url=NavigationService.area_url;
						var url='<%=basePath%>'+area_url+'/chart/'+chartInfoType+'/'+chartType;
						
						url=url.concat('\/year=')
						.concat((chartType=='year'||chartType=='month')?$scope.chartParam.year:0)
						.concat('&month=')
						.concat((chartType=='month')?$scope.chartParam.month:0)
						.concat('&date=')
						.concat(chartType=='day'?millis.getTime():0);

								var defer=ChartService.drawChart(url);
								
								
									defer.promise.then(function(d){
									/*
										console.log('d:'+d)
										for(var k in d){
											console.log('k-name:'+k+'k-value:'+d[k])
										}
									*/	
										if(!d.legends || d.legends==""){
											alert('沒有數據!');return;
										}

										var chartWidth=$('#chartResult').css('width');
										var chartHeight=$('#chartResult').height('height');
									
										$('#chartResult').css({'width':chartWidth,'height':chartHeight});
										
											require([
										    	'echarts',
										    	'echarts/chart/line',
										    	'echarts/chart/bar'
											],
											function(ec){
	
													var myChart=ec.init(document.getElementById('chartResult'));
													
													//base init
													option={
															title:{},
															tooltip:{
																trigger:'axis',
																axisPointer:{
																	type:'shadow'
																}
															},
															legend:{
																x:'left',
																data:[],
																padding:[10,5,5,5]
															},
															toolbox:{
																show:false,
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
									
													
													option.xAxis[0].data=d.xAxisLabels;

													for(var z=0;z<d.legends.length;z++){
														
														var legend=d.legends[z];
														
														//generate line chart
														var lineChart={
																name:legend,
																data:d.seriesMap[legend],
																type:'line',
																smooth:true,
														}
														
														option.series.push(lineChart);
														
													}
													myChart.setOption(option);
													
													
													// set ChartService's Info value
													//var errCountInfo={};
													ChartService.errCountInfos=[];
													for(var l=0;l<d.legends.length;l++){
														var legend=d.legends[l];
														var count=0;
														var valAry=d.seriesMap[legend];
														for(var n=0;n<valAry.length;n++){
															count+=valAry[n];
														}
														
														var errCountInfo={'key':legend,'value':count};
														
														console.log('ErrCountInfo:'+errCountInfo)
														
														ChartService.errCountInfos.push(errCountInfo);
													}
													
												}
									);
											
											
								},function(){
			
								});
				});
				
			}
			
			return {
				link:link,
				templateUrl:'<%=basePath%>charter.jsp',
				controller:'ChartController'
			}
			
		}]).controller('ChartController',['$scope','ChartService',function($scope,ChartService){	
			
			$scope.chartParam={};
			
			$scope.getChartType=function(){	
				return ChartService.chartType;
			}
			
			$scope.getChartInfoType=function(){
				return ChartService.chartInfoType;
			}
			
			$scope.setChartType=function(chartType){
				ChartService.chartType=chartType;

			}

			
			$scope.getErrCountInfos=function(){
				return ChartService.errCountInfos;
			}
			
			
		}]).service('ChartService',['$q','$http',function($q,$http){
				var serv=this;	
					serv.chartType='day';
					serv.chartInfoType='ErrMessage'
					
					serv.drawChart=function(url){
					
						var defer=$q.defer();
						
						$http.get(url)
							.success(function(resp){
								defer.resolve(resp)
							}).error(function(resp,err){
								defer.reject(err);
							});

						//load picture
						return defer;
					}
					
					
		}]);
		
	
		// Pager Directive
		app.directive('pager',['PageService',function(PageService){
			
			
			var link=function($scope,elem,attrs,ctrl){
				
				
	
				(function($){
					
					jQuery(elem).find('#start_date')
							.datepicker({'dateFormat':'yy/mm/dd'},jQuery.datepicker.regional[ "zh-TW" ]);
							
					
					jQuery(elem).find('#end_date')
							.datepicker({'dateFormat':'yy/mm/dd'},jQuery.datepicker.regional[ "zh-TW" ]);
					
				})(jQuery);
				
				
				$(elem).find('.freshQuery').on('click',function(){
						var params=validAndGetParams();
						$scope.query('fresh',params);	
				})
				

				
				$(elem).find('.downloadExcel').on('click',function(){
						if($scope.page.totalCount && $scope.page.totalCount>1000){
							var goOn=confirm('總記錄條數大於1000,確認進行!?');
							if(goOn){
	
							}else{
								return;
							}
						}
						
					
				})
				
				
				$(elem).find('.d_pageResult').on('click','.dude',function(){
					//var $tr=$(this).closest('tr').next('tr')
					var $tr=$(this).next('tr');
					var $arrow=$(this).find('.table-expandable-arrow')
					if($tr.css('display')=='table-row'){
						$tr.css('display','none');
						$arrow.css('background-position','0px 0px');
					}else {
						$tr.css('display','table-row');
						$arrow.css('background-position','0px -16px');
					}
					
				})
				
				$(elem).find('#d_firstPage').on('click',function(){
					//alert('first page')
					var params=validAndGetParams();
						$scope.query('first',params);
				});
				
				$(elem).find('#d_prevPage').on('click',function(){
					//alert('prev page')
					var params=validAndGetParams();
						$scope.query('prev',params);
				})
				
				$(elem).find('#d_nextPage').on('click',function(){
					//alert('next page')
					var params=validAndGetParams();
						$scope.query('next',params);
				})
				
				$(elem).find('#d_lastPage').on('click',function(){
					var params=validAndGetParams();
						$scope.query('last',params);
				})

				
				function validAndGetParams(){
					return {
						'TS':$scope.queryFields.ts,
						'SN':$scope.queryFields.sn,
						'Description':$scope.queryFields.description,
						'start_date':(function($){return jQuery(elem).find('#start_date').datepicker('getDate')}(jQuery)),
						'start_time':$scope.queryFields.start_time,
						'end_date':(function($){return jQuery(elem).find('#end_date').datepicker('getDate')}(jQuery)),
						'end_time':$scope.queryFields.end_time
					};
				}
					
			}
			
			return{
				link:link,
				templateUrl:"<%=basePath%>page.jsp",
				controller:'PageController',


			}
		
		// Page Controller
		}]).controller('PageController',['$scope'
		                                ,'PageService'
		                                ,'NavigationService'
		                                ,function($scope,PageService,NavigationService){
			
			$scope.queryFields={};
			
			$scope.isQuerying=false;

			
			$scope.getQfdList=function(){
				return NavigationService.currentModule.qfdList;
			}
			
			var serv=PageService;
			
			$scope.page={
					pageIndex:0,
					totalPage:0,
					totalCount:0,
					pageSize:10
			};
			

			$scope.getPage=function(){
				return $scope.page;
			}
			

			
			$scope.query=function(action,params){
				
				if($scope.isQuerying){
					alert('數據正在查詢中，請稍待...')
					return;
				}
				
				// parse HH:mm string to milli sec...
				function parseTimeToMilliSec(timeStr){
					var HH=timeStr.split(':')[0];
					var mm=timeStr.split(':')[1];
					
					HH=(HH.charAt(0)=='0')?HH.charAt(1):HH;
					mm=(mm.charAt(0)=='0')?mm.charAt(1):mm;
					
					return HH.valueOf()*60*60*1000+mm.valueOf()*60*1000


				}
				
				
				var pageIndex=$scope.page.pageIndex;
				var pageSize=$scope.page.pageSize;

				var page=pageIndex?pageIndex:1;

				var totalPage=($scope.page.totalPage && $scope.page.totalPage!=0)?$scope.page.totalPage:0;
				var totalCount=($scope.page.totalCount && $scope.page.totalCount!=0)?$scope.page.totalCount:0;
				
				
				var area_url=NavigationService.area_url
				var rootUrl="<%=basePath%>"+area_url+"/page/";
					var url=rootUrl.concat('pageIndex=').concat(page)
								.concat('&pageSize=').concat(pageSize)
								.concat('&action=').concat(action)
								.concat('&totalPage=').concat(totalPage)
								.concat("&totalCount=").concat(totalCount);
					
					//Date Validator.
					
					if(params.start_date==undefined){
						alert('開始日期不為空!');return ;
					}
					if(params.end_date==undefined){
						alert('結束時間不為空!');return;
					}
					
					if(!/20[0-9][0-9]\/{(0[1-9])|(1[0-2])\/{(0[1-9])|([1-2][0-9])|(3[0-1])}}/.test(params.start_date)){
						alert('開始日期格式不正確!');return;
					}
					if(!/20[0-9][0-9]\/{(0[1-9])|(1[0-2])\/{(0[1-9])|([1-2][0-9])|(3[0-1])}}/.test(params.end_date)){
						alert('結束日期格式不正確!');return;
					}
					
					var startDate=Date.parse(params.start_date);
					var endDate=Date.parse(params.end_date);


					//Time Validator
					var startTime=params.start_time;
					var endTime=params.end_time;
					
					if(startTime==undefined || $.trim(startTime)==''){
						startTime='00:00';
					}else if(!/^[0-5][0-9]:[0-5][0-9]$/.test(startTime)){
						alert('開始時間格式錯誤!');return;
					}
					
					if(endTime==undefined || $.trim(endTime)==''){
						endTime='00:00';
					}else if(!/^[0-5][0-9]:[0-5][0-9]$/.test(endTime)){
						alert('結束時間格式錯誤!');return;
					}
					
					var parsedStartTime=parseTimeToMilliSec(startTime);
					var parsedEndTime=parseTimeToMilliSec(endTime);

					var startDatetime=startDate+parsedStartTime;
					var endDatetime=endDate+parsedEndTime;

					
					if(startDatetime>=endDatetime){
						alert('結束時間必須大於開始時間!');return;
					}
					if((endDatetime-startDatetime)/(24*60*60*1000)>7.0){
						alert('相隔時間不能大於7天!');return;
					}
					
					
					params.start_date=startDatetime;
					params.end_date=endDatetime;
					
					$scope.isQuerying=true;
					console.log('開始查詢!')
				var defer=serv.post(url,params);
					
					defer.promise.then(function(data){
						console.log('查詢完成!')
						$scope.page=data;
						$scope.isQuerying=false;
						
						if(data.totalCount==0){
							console.log('沒有數據!')
							alert('沒有數據!')
						}
					},function(){
						console.log('some errors')
					});
				
			}
	}]).service('PageService',function($http,$q){
		
			var serv=this;

			
			serv.post=function(url,data){
				
				var defer=$q.defer();
				
				$http.post(url,data)
					.success(function(resp,status){
						defer.resolve(resp);
					}).error(function(resp,status){

					});		
				
				return defer;
			}
			
			serv.get=function(url){
					var defer=$q.defer();
					
					$http.get(url)
						.success(function(resp,status){
							defer.resolve(resp);
						}).error(function(resp,status){
							
						});
					
					return defer;
			}

	});
	

	
	app.directive('navigation',['NavigationService','MonitorService','PageService','ChartService',function(NavigationService,MonitorService,PageService,ChartService){
		
		var link=function($scope,elem,attrs,ctrl){
			
			$(elem).find('#areaSelector').on('change',function(){
				
				alert($(this).val());
				
				NavigationService.area_url=$(this).val();
				
				//check which view is
				
				//if is page, clear data
				for(var m in NavigationService.currentModule){
					console.log('currMoudle: '+m+" : "+NavigationService.currentModule[m])
				}
				//else if is chart clear chart
				
				//else if is monitor , refresh monitor
				
				
			})
			
		}
		
		return {
			link:link,
			template:'<div style="float:left;margin-left:20px;margin-top:13px;"><select id="areaSelector"><option value="bobcat">成都</option><option value="lhbobcat">龍華</option></select></div>',
			controller:'NavigationController'
		}
		
		
	}]).controller('NavigationController',['$scope','NavigationService',function($scope,NavigationService){

		var url='<%=basePath%>module/findAllGroupByCs';
		
		var serv=NavigationService;
		
		$scope.getCurrentModule=function(){
			return serv.currentModule;
		}
		
		
		$scope.getModuleMap=function(){

			return serv.moduleMap;
		}
		
		
		$scope.loadSubModuleInfo=function(cs,cl){

			for(var n in NavigationService.moduleMap[cs]){
				var mod=NavigationService.moduleMap[cs][n];			
				if(mod.cl==cl){
					NavigationService.currentModule=mod;
					break;
				}
			}
			
		}
		
		var defer=NavigationService.post(url,{});
		
			defer.promise.then(function(resp){
				serv.moduleMap=resp;
			},function(){
				
			});
		
			
	}]).service('NavigationService',function($http,$q){
		
		var serv=this;

		
		serv.area_url='bobcat';
		
		serv.currentModule={};
		
		serv.moduleMap={};
		
		serv.post=function(url,data){
			var defer=$q.defer();
			$http.post(url,data)
				.success(function(resp){
					defer.resolve(resp);
				}).error(function(resp,status){
					
				})
				
			return defer;
		}
		
		serv.get=function(url){
			var defer=$q.defer();
			$http.get(url)
				.success(function(resp,status){
					defer.resolve(resp);
				}).error(function(resp,status){
					
				});
			
			return defer;
		}

	})

			
		</script>
</body>
</html>