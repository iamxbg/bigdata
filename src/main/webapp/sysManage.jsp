<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang='en' ng-app='sysManageApp'>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<!-- bootstrap css -->
	<link rel='stylesheet' style='text/css' href='<%=basePath%>css/sysManage.css'/>
	<link  rel="stylesheet" style="text/css" href="<%=basePath%>css/bootstrap-theme.min.css"/>
	<link  rel="stylesheet" style="text/css" href="<%=basePath%>css/bootstrap.min.css"/>
	<!-- insert js  -->
	<script type="text/javascript" src='<%=basePath%>js/angular.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/sockjs.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/jquery.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/bootstrap.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/stomp.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/angular-route.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/json2.js'></script>
	<style>
			#chooseBox{
				width:60%;
				margin-left:20%;
				margin-top:20px;
				border:1px solid lime;	
				height:200px;
			}
			
			#chooseBox .unchoosedBox{
				width:30%;
				border:1px solid red;
				height:180px;
				margin-left:10%;
				margin-top:10px;
				float:left;
				
			}
			
			#chooseBox .chooseBtns{
				padding:4px;
				width:7%;
				margin-left:5%;
				margin-right:5%;
				border:1px solid lime;
				height:180px;
				margin-top:10px;
				float:left;
			}
			
			#chooseBox .chooseBtn{
				border:1px solid red;
				padding:2px;
				cursor:pointer;
			}
			
			#addChoose{
				margin-top:40px;
			}
			
			#removeChoose{
				margin-top:20px;
			}
			
			#chooseBox .choosedBox{
				width:30%;
				border:1px solid blue;
				height:180px;
				margin-top:10px;
				float:left;
			}
			
			.choosed,.unchoosed{
				border:1px solid lime;
				margin-top:10px;
				text-indent:1em;
				list-style:none;
				
			}
			
			.choosed span{
				border:1px solid red;
				cursor:pointer;
				letter-spacing:1px;
				padding:0px 6px;
				font-size:16px;
			}
			
			.unchoosed span{
				border:1px solid red;
				cursor:pointer;
				letter-spacing:1px;
				padding:0px 6px;
				font-size:16px;
			}
			
			#chooseBox .submit{
				margin-top:90px;
				margin-left:10px;
			}
		
	</style>
	
</head>
<body >
	<div id='header'>
		<div id='sysPic'></div>
	</div>
	<div id='middle' ng-controller='NavigationController as naviCtrl'>
		<div id='leftNav'>
			<ul >

					<li><a ng-click='naviCtrl.changeModule("user")' href='#/user'><img/><span>用戶管理</span></a></li>
					<li><a ng-click='naviCtrl.changeModule("module")' href='#/module'><img/><span>模組管理</span></a></li>

			</ul>
		</div>
		<div id='rightFrame'>

			<div id='tabs'>
			
				 <ul ng-if='naviCtrl.getStatus()=="module"'>
			 		<li><a href='#/module/addModule' ng-click='naviCtrl.setSubStatus("addModule")'><img /><span>添加模組</span></a></li>
			 		<li><a href='#/module/manageModule' ng-click='naviCtrl.setSubStatus("manageModule")'><img /><span>編輯模組</span></a></li>
				 </ul>
				 <ul ng-if='naviCtrl.getStatus()=="user"'>
				 	<li><a href='#/user/addUser' ng-click='naviCtrl.setSubStatus("addUser")'><img /><span>添加用戶</span></a></li>
			 		<li><a href='#/user/manageUser' ng-click='naviCtrl.setSubStatus("manageUser")'><img /><span>用戶管理</span></a></li>	
				 </ul>
			</div>

			<div id='content'>
				<div id='emptyContentTop'></div>
				<div id='emptyContentTrue'>

					<div ng-controller='ModuleController' style='border:1px solid lime; width:100%;'>
						<!-- Add Module -->
						<div  ng-if='naviCtrl.getStatus()=="module"'>
								<div ng-if="naviCtrl.getSubStatus()=='addModule'" id='subModule_module_addModule' class='subModule'>
									<div id='editModuleBaseInfoBtn' class='btn_add'><img /><span>基本信息</span></div>
									<div   id='editModuleBaseInfoBox' class='content_add'>
										<ul >
											<li>
												<div class='moduleBaseInfo'>
													<span>中文名稱</span>
													<input ng-model='baseInfo.desc' name='desc'/>
												</div>
											</li>
											<li>
												<div class='moduleBaseInfo'>
													<span>集合空間</span>
													<input ng-model='baseInfo.cs' name='collectionSpaceName'/>
												</div>
											</li>
											<li>
												<div class='moduleBaseInfo'>
													<span>集&nbsp;&nbsp;&nbsp;&nbsp;合&nbsp;&nbsp;&nbsp;</span>
													<input  ng-model='baseInfo.cl' name='collectionName'/>
												</div>
											</li>
											<li>	
												<div class='moduleBaseInfo'>
													<div ng-click='checkModuleExists()' id='submitBaseInfo'>&nbsp;確&nbsp;定&nbsp;</div>
												</div>
											</li>
											
										</ul>
									</div>
									<div  id='editPageQueryBtn' class='btn_add'><img /><span>查詢字段</span><span ng-show='getStatus()>=1'  id='addFieldDefine'>&nbsp;+&nbsp;</span></div>
									<div  ng-if='getStatus()>=1'  id='editPageQueryBox' class='content_add'>
										
										<ul>	
											<li class='notDataLi'>
												<div class='addLiBtn'>
													
												</div>
											</li>
											<li ng-if='f!="_id"' ng-repeat='f in queryFields'>
												<span class='fieldStatus'  style='cursor:pointer;'>未使用</span>
												<span class='fieldName'>{{f}}</span>
												<span style='visibility:hidden;border:1px solid red;margin-left:20px;cursor:pointer;' class='addMoreField'>&nbsp;+&nbsp;</span>
												<ul style='display:none;'>
													<li>
														<span>描述:</span><input class='fieldDesc' ng-value='f' />
														<span>類型:</span>
														<select name='fieldType'>
															<option value='timestamp'>時間戳</option>
															<option value='date'>日期</option>
															<option value='number'>數字</option>
															<option value='text'>字符串</option>
														</select>
														<span>符號：</span>
														<select name='fieldOpr'>
															<option value='>='>>=</option>
															<option value='<='><=</option>
															<option value='='>=</option>
															<option value='like'>like</option>
														</select>
														<span class='removeField' style='border:1px solid red;margin-left:20px;visibility:hidden;'>&nbsp;&nbsp;-&nbsp;&nbsp;</span>
													</li>
													
												</ul>
												
											</li>
										</ul>
										
										
									</div>
									
									<div id='editChartPlotBtn' class='btn_add'><img /><span>圖表繪製</span><span ng-show='getStatus()>=1'  id='addChartDefine'>&nbsp;+&nbsp;</span></div></div>
									<div  ng-if='getStatus()>=2 '    id='editChartPlotBox' class='content_add'>
										<ul>
											<li class='notDataLi'>
												<div class='addLiBtn'>
													
												</div>
											</li>
	
											<li>
												<select>
													<option value='0'>--圖表類型--</option>
													<option value='line'>折綫圖</option>
													<option value='pie'>餅圖</option>
													<option value='bar'>柱狀圖</option>
												</select>
											</li>
										</ul>
									</div>
									
										
								</div>
								
								
								
	<!-- Manage Module -->
								<div ng-if='naviCtrl.getSubStatus()=="manageModule"'  id='subModule_module_manageModule' class='subModule'>
									<div class='tablebox'>
										<table class='moduleTable'>
											<thead>
												<tr>
													<td>描述</td>
													<td>所在集合</td>
													<td>集合名稱</td>
													<td>操作</td>
												</tr>
											</thead>
											<tbody>
												<tr ng-repeat='module in getModules()'>
													<td>{{module.desc}}</td>
													<td>{{module.cs}}</td>
													<td>{{module.cl}}</td>
													<td>
														<span class='edit'>編輯</span>
													</td>
												</tr>
											</tbody>
											<tfoot></tfoot>
										</table>
									
									</div>
								</div>
						</div>
<!-- addUser -->
					<div ng-controller='UserController' ng-if='naviCtrl.getStatus()=="user"'>
						<div ng-if='naviCtrl.getSubStatus()=="addUser"' id='subModule_user_addUser' class='subModule'>
							
							<!-- Add user base info -->
							<div class='btn_add'><img />
								<span ng-if='getMode()=="add"'>添加用戶</span>
								<span ng-if='getMode()=="edit"'>編輯用戶</span>
								<span style='border: 1px solid blue;width: 50px;margin-right: 100px;cursor:pointer;float: right;' ng-click='deleteUser()' ng-if='getMode()=="edit"'>刪除</span>
							</div>	
							<div  class='content_add'>
									<ul ng-if='getMode()=="add"'>
										<li>
											<div>
												<span class='label_1'>工號:</span>
												<input ng-disabled='getStatus()>=1' ng-model='user.work_id' name='work_id'/>
												
											</div>
										</li>
										<li>
											<div>
												<span class='label_1'>用戶名:</span>
												<input ng-disabled='getStatus()>=1'  ng-model='user.username' name='username'/>
												
											</div>
										</li>
										<li>
											<div>
												<span class='label_1'>密碼:</span>
												<input ng-disabled='getStatus()>=1'  ng-model='user.password' name='password'/>
											</div>
										</li>
										<li>
											<div style='border: 1px solid red;margin-left: 200px;padding: 2px 3px;letter-spacing: 3px;width: 60px;'  ng-click='createUser()'>創建</div>
										</li>
									</ul>
									
									<ul ng-if='getMode()=="edit"'  >
										<li>
											<div>
												<span class='label_1'>工號:</span>
												<input   name='work_id'/>
												
											</div>
										</li>
										<li>
											<div>
												<span class='label_1'>用戶名:</span>
												<input  name='username'/>
												
											</div>
										</li>
										<li>
											<div>
												<span class='label_1'>密碼:</span>
												<input  name='password'/>
											</div>
										</li>
										<li>
											<div ng-if='getMode()=="add"' style='border: 1px solid red;margin-left: 200px;padding: 2px 3px;letter-spacing: 3px;width: 60px;'  ng-click='createUser()'>創建</div>
											<div ng-if='getMode()=="edit"' style='border: 1px solid red;margin-left: 200px;padding: 2px 3px;letter-spacing: 3px;width: 60px;'  ng-click='editUser()'>修改</div>
										</li>
									</ul>
							</div>
							
							<!-- add module to User -->
							<div  id='addUserModuleBtn' class='btn_add'><img /><span ng-if="getMode()=='add'">添加模組</span><span ng-if='getMode()=="edit"'>編輯模組</span></div>
							<div ng-if='(getMode()=="add" && getStatus()>=1  ) || getMode()=="edit" ' id='addModuleForUser' class='content_add' style='padding:5px 10px;'>
								
								<chooseBox />

							</div>
							
						</div>
						

<!-- userManage -->
							<div ng-if='naviCtrl.getSubStatus()=="manageUser"' id='subModule_user_manageUser' class='subModule'>
								<div class='pageBox'>
									<div class='queryParams'>
										<ul>
											<li><span>工號</span><input name='work_id' ng-model='query.user.work_id'/></li>
											<li><span>姓名</span><input name='name' ng-model='query.user.name'/></li>
											<li><span>用戶名</span><input name='username' ng-model='query.user.username'/></li>
											<li><span ng-click='queryUsers()' class='goQuery'>查詢</span></li>
										</ul>
									</div>
									<div class='queryResult'>
										<table>
											<thead>
												<tr>
													<td>工號</td>
													<td>用戶名</td>
													<td>操作</td>
												</tr>
											</thead>
											<tbody>
												<tr id='{{user._id}}' ng-repeat='user in getUsers()'>
													<td><span ng-bind='user.work_id'></span></td>
													<td><span ng-bind='user.username'></span></td>
													<td><span ng-click='editUser(user._id)' class='editBtn'>編輯</span></td>
												</tr>
											</tbody>
											<tfoot></tfoot>
										</table>
									</div>
									<div class='pageBar'>
										
									</div>
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id='emptyContentBottom'></div>
			</div>
		</div>
	</div>
	<!--  
	<div id='footer'>
	
	</div>
	-->
	<script type="text/javascript">
	

		

		$(function(){
			
				$('#content').on('click','.rmField',function(){
					$(this).closest('li').remove();	
				});
				
				
				$('#content').on('click','.fieldStatus',function(){
						$(this).data('use',!$(this).data('use'));
						if($(this).data('use')){
							$(this).text(' 使 用  ')
							$(this).css('background','lime');
							$(this).closest('li').css('background','lightgreen');
							$(this).closest('li').find('ul').css('display','block');
							$(this).closest('li').find('.addMoreField').css('visibility','visible');
						}else{
							$(this).text('未使用')
							$(this).css('background','none');
							$(this).closest('li').find('ul').css('display','none');
							$(this).closest('li').css('background','none');
							$(this).closest('li').find('.addMoreField').css('visibility','hidden');
							
						}
					
				});
				
				$('#content').on('click','.addMoreField',function(){
					
					$(this).closest('li').find('ul li:first').clone().appendTo($(this).closest('li').find('ul'));
					$(this).closest('li').find('ul').find('.removeField').css('visibility','visible');
					
				});
				
				$('#content').on('click','.removeField',function(){
					if($(this).closest('ul').find('li').length==2){
						$(this).closest('ul').find('.removeField').css('visibility','hidden');	
					}
					$(this).closest('li').remove();
					
				});
				
				$('#content').on('click','#addFieldDefine',function(){
					alert('add　Field');
					
					var newLi=($('<li>')
							.append($('<span>').text('字段名稱'))
							.append($('<input name="field"/>'))
							.append($('<span>').text('顯示名稱'))
							.append('<input name="desc"/>')
							.append($('<select>').attr('name','fieldType')
									.append($('<option>').attr('value',0).text('--字段類型--'))
									.append($('<option>').attr('value','text').text('文字'))
									.append($('<option>').attr('value','num').text('數字'))
									.append($('<option>').attr('value','date').text('日期'))
									.append($('<option>').attr('value','time').text('時間戳'))
									)
							.append($('<select>').attr('name','oprType')
									.append($('<option>').attr('value',0).text('--操作符號--'))
									.append($('<option>').attr('value','>=').text('>='))
									.append($('<option>').attr('value','<=').text('<='))
									.append($('<option>').attr('value','=').text('='))
									.append($('<option>').attr('value','like').text('like'))
									)
							 .append($('<span class="rmField" style="border:1px solid lime;cursor:pointer;"><font color="red">&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</font></span>'))
							);
					
					
								
				});
				
				$('#content').on('click','#addChartDefine',function(){
					alert('add Chart Define.')
					
					
					
				});
		});
		
		
			/*
					AngularJS
			*/
			var sysManageApp=angular.module('sysManageApp',[]);
			
			
//Module Controller
				sysManageApp.controller('ModuleController',function($scope,$http,ModuleService){

					
					 $scope.baseInfo={};
					 
					 $scope.queryFields=[];
					 
					 $scope.getModules=function(){
						 	return ModuleService.modules;
					 }
					
					 $scope.checkModuleExists=function(){
						 alert('檢查Module是否存在!');
						 
						 var cs=$scope.baseInfo.cs;
							var cl=$scope.baseInfo.cl;
							
							
							var checkUrl='<%=basePath%>sequoiadb/checkModuleExists/cs='
										.concat(cs)
										.concat('&cl=')
										.concat(cl);
							
							//
							$http.get(checkUrl)
								.success(function(data){
									
									$http.get('<%=basePath%>module/getFields/cs='+cs+'&cl='+cl)
										.success(function(resp,status){
											$scope.queryFields=resp;
										}).error(function(resp,status){
											
										});
									
									
									ModuleService.status=1;
									alert('exists!##')
												
								}).error(function(data,resp){
									alert('error or not exists!')
								});
						 
					 }
					 
					 $scope.createModule=function(){
							//valid status

							
							//檢查集合空間是否在系統中存在!
							
							$scope.baseInfo.name=$scope.baseInfo.cs+'.'+$scope.baseInfo.cl;
							
							var url='<%=basePath%>module/createModule';
							var defer=ModuleService.post(url,$scope.baseInfo);
							defer.promise.then(function(resp){
											alert('success');
											ModuleService.status=1;	
										}).then(function(resp){
												
										})
							
					 }
					 
					 $scope.findAllModules=function(){
						 var url='<%=basePath%>module/findAll';
						 
						 var defer=ModuleService.get(url)
						 	defer.promise.then(function(resp){
						 		alert('success!')
						 		ModuleService.modules=resp;
						 		showSubModuleView('module','manageModule')
						 	}).then(function(resp){
						 		alert('error')
						 	})
					 }
					 
					 
					$scope.getStatus=function(){
						 return ModuleService.status;
					 }
					
				})
				
				sysManageApp.service('ModuleService',function($http,$q){
					
					var service=this;
						service.status=0;
						service.modules=[];
					
					service.post=function(url,params){
						var defer=$q.defer();
						$http.post(url,params)
							.success(function(resp,status){
								defer.resolve(resp);
							}).error(function(resp,status){
								defer.reject(resp);
							});
						return defer;
					}	
					
					service.get=function(url){
						var defer=$q.defer();
							$http.get(url)
								.success(function(resp,status){
									defer.resolve(resp);
								}).error(function(resp,status){
									defer.reject(resp);
								});
							return defer;
					}
					
				});
			
				function showSubModuleView(module,subModule){
					$('.subModule').css('display','none')
					.end().find('#subModule_'+module+'_'+subModule).css('display','block');
				
				}
	
				
//Navigation Controller
			sysManageApp.controller('NavigationController',function($http,NavigationService,UserService,ModuleService){
					var ctrl=this;
						
						ctrl.changeModule=function(name){
							alert('change to '+name);
							NavigationService.status=name;
						}
						
						ctrl.loadSubModule=function(name,subname){
							
						}
						
						ctrl.getStatus=function(){
							return NavigationService.status;
						}
						
						ctrl.getSubStatus=function(){
							return NavigationService.subStatus;
							
						}
						
						ctrl.setSubStatus=function(name){
							
							alert('Set Sub status..')
							
							NavigationService.subStatus=name;
							if(name=='addUser'){
								UserService.mode='add';
							}if(name=='manageModule'){
								
								alert('Manage Module.')

								$http.get('<%=basePath%>module/findAll')
									.success(function(data){
										alert('success')
										ModuleService.modules=data;
									}).error(function(data,resp){
										alert('Error!')
									});
							}
							

							
						}
				});
				
				
				sysManageApp.service('NavigationService',function(){
					var service=this;
						service.status='user';
							service.subStatus='addUser';
						
				})
				
				
//User Controller 
			
				sysManageApp.controller('UserController',function($scope,UserService,ModuleService,NavigationService){

						$scope.user={};

						$scope.getEditUser=function(){
								return UserService.editUser;
							}
							
						$scope.deleteUser=function(){
							var _id=UserService.editUser._id;
							alert('_id:'+_id);
							var url='<%=basePath%>user/remove/'+_id;
							var defer=UserService.post(url);
								defer.promise.then(function(){
									alert('success');
								},function(){})
							
						}
						
						$scope.getMode=function(){
							return UserService.mode;
						}
							
						$scope.editUser=function(_id){
							alert('edit User');
							var url='<%=basePath%>user/findById/'+_id
							var defer=UserService.get(url);
								defer.promise.then(function(resp){

									UserService.mode='edit'
									NavigationService.subStatus='addUser';
									UserService.editUser=resp;
								},function(resp){
									
								});
						}
							
						$scope.queryUsers=function(){
							alert('@queryUsers')
							var url="<%=basePath%>user/findUsers/1";
							var defer=UserService.post(url,query.user);
								defer.promise.then(function(resp){
									UserService.manage.users=resp;
								},function(resp){
									
								})
								
						}

						
						$scope.getUsers=function(){
								return UserService.manage.users;
						}
							
						$scope.createUser=function(){
							//validate user info
							
							var url='<%=basePath%>user/addUser';

							var defer=UserService.post(url,$scope.user);
								defer.promise.then(function(resp,status){
									UserService.user=resp;
									UserService.status=1;
								},function(status){
									
								});
						}
						
						$scope.findAllModules=function(){
							var url='<%=basePath%>module/findAll';
							var defer=ModuleService.get(url);
								defer.promise.then(function(resp){

									UserService.chooseModules=resp;
								},function(status){
									
								})
						}
						

						
						$scope.getStatus=function(){
								return UserService.status;
						}
				});
				
				sysManageApp.service('UserService',function($http,$q){
					
					var service=this;
							
						service.editUser={};
					
					
					service.status=0;
					service.mode='add';

					service.post=function(url,params){
						var defer=$q.defer();
						$http.post(url,params)
							.success(function(resp,status){
								defer.resolve(resp);
							}).error(function(resp,status){
								defer.reject(resp);
							});
						return defer;
					}	
					
					service.get=function(url){
						var defer=$q.defer();
							$http.get(url)
								.success(function(resp,status){
									defer.resolve(resp);
								}).error(function(resp,status){
									defer.reject(resp);
								});
							return defer;
					}
				});
				
				
				sysManageApp.directive('choosebox',function(){
						
					return {
						restrict:'E',
						replace:true,
						link:function(scope,elems,attrs,ctrl){
							/*
							$(elems).find('.unchoosed').on('click',function(){
								alert('clicked')
								$(this).data('selected',!$(this).data('selected'));
								($(this).data('selected')==true)?$(this).css('background','lime'):$(this).css('background','none');
								
							})*/
							
							$(elems).find('.unchoosedBox').on('click','.unchoosed',function(){
								$(this).data('selected',!$(this).data('selected'));
								$(this).data('selected')?$(this).css('background','lime'):$(this).css('background','none');
							})
							
							$(elems).find('.choosedBox').on('click','.choosed',function(){
								$(this).data('selected',!$(this).data('selected'));
								$(this).data('selected')?$(this).css('background','lime'):$(this).css('background','none');
							})
							
							$(elems).find('#addChoose').on('click',function(){
								alert('addChoose')
								$(elems).find('.unchoosed').filter(function(){
									if($(this).data('selected')) return true;
									else return false;
								}).data('selected',false)
								.removeClass('unchoosed').addClass('choosed')
								.css('background','none').appendTo('.choosedBox');
							})
							
							$(elems).find('#removeChoose').on('click',function(){
								alert('removeChoose')
								$(elems).find('.choosed').filter(function(){
									if($(this).data('selected')) return true;
									else return false;
								}).data('selected',false)
								.removeClass('choosed').addClass('unchoosed')
								.css('background','none').appendTo('.unchoosedBox')
							});
							
							$(elems).find('.submit').on('click',function(){
								alert('submit');
								var _ids=[];
								$(elems).find('.choosed').each(function(i,elem){
									var _id=$(elem).attr('_id');
									//console.log(_id);
									_ids.push(_id);
								});
								
								scope.addModules(_ids);
							})
						
						},
						controller:'ChooseController',
						template:"<div id='chooseBox' >"+
									"<div class='unchoosedBox'>"+
										"<ul><li ng-repeat='module in unchoosed' class='unchoosed' _id=\'{{module._id}}\'} ><span  >{{module.name}}</span></li></ul>"+
									"</div>"+
									"<div class='chooseBtns'>"+
										"<div class='chooseBtn' id='addChoose' ><span>&nbsp;&nbsp;->&nbsp;</span></div>"+
										"<div class='chooseBtn' id='removeChoose' ><span>&nbsp;&nbsp;<-&nbsp;</span></div>"+
									"</div>"+
									"<div class='choosedBox'></div>"+
									"<button  class='submit'>&nbsp;提&nbsp;交&nbsp;</div>"+
								 "</div>"
						
					}
				}).controller('ChooseController',function($scope,$http,UserService){

						
					var url='<%=basePath%>module/findAll';
					$http.get(url)
						.success(function(data,resp){
							$scope.unchoosed=data;

						}).error(function(data,resp){
							
						});
					
					$scope.unchoosed=[];
					
					$scope.choosed=[];
					
					$scope.addModules=function(_ids){
						alert('modIds-Size:'+_ids.length)
						var map={
							'modIds':_ids,
							'userId':UserService.user._id
						}
						
						$http.post('<%=basePath%>user/addModules',map)
							.success(function(data,resp){
								
							}).error(function(data,resp){
								
							});
					}
											
				});
			
	</script>
</body>
</html>