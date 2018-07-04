<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>测试任务调度</title>
</head>

<body>
	<div>
		<%@ include file="/head.jsp"%>
	</div>

	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width: auto; font-size: 14px;">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">UTP</li>
			<li class="active">任务调度</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">测试任务调度</h1>
			</header>
			
			<div class="panel-body" style="padding-bottom: 0px;">
				<div class="panel panel-default">
					<div class="panel-heading">查询条件</div>
					<div class="panel-body">
						<div class="form-group" style="margin-top: 15px">
							<label class="control-label col-sm-1" for="txt_search_project">项目名称:</label>
							<div class="col-sm-3">
								<select class="form-control" id="search_project"
									onchange="searchproject()">
									<c:forEach var="p" items="${projects }">
										<option value="${p.projectid}">${p.projectname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>

				<div id="toolbar" class="btn-group">
					<button id="btn_add" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增调度
					</button>
					<button id="btn_log" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>查看日志
					</button>
					<button id="btn_upload" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-upload" aria-hidden="true"></span>上传JAR
					</button>
				</div>
				<table id="tb_testjob"></table>

				<!-- 模态框示例（Modal） -->
				<div class="modal fade" id="ModalLog" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">查看客户端日志</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
								  <div class="form-group">
											<label for="clientip" class="col-sm-3 control-label">客户端IP：</label>
											<div class="input-group col-md-8">
												<select class="form-control" name="clientip" id="clientip">
													<c:forEach var="iplist" items="${iplist }">
														<option value="${iplist.clientip}">${iplist.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										
									<div class="form-group">
										<label for="dtp_input" class="col-md-3 control-label">选择日志日期：</label>
										<div class="input-group date form_date col-md-5">
											<input id="dtp_input" class="form-control" type="text" value="${date }" readonly> 
					                        <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar"></span></span>
										</div>
									</div>

								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button class="btn btn-primary" onclick="downlog()">查看</button>
								&nbsp;&nbsp;&nbsp;&nbsp;<span id="tip"> </span>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>
				
				<!-- 模态框示例（Modal） -->
				<div class="modal fade" id="upload" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">上传测试驱动JAR至客户端</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
								  <div class="form-group">
											<label for="clientip" class="col-sm-3 control-label">客户端IP：</label>
											<div class="input-group col-md-8">
												<select class="form-control" name="clientipforupload" id="clientipforupload">
													<c:forEach var="iplist" items="${iplist }">
														<option value="${iplist.clientip}">${iplist.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>

								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button class="btn btn-primary" onclick="upload()">去上传</button>
								&nbsp;&nbsp;&nbsp;&nbsp;<span id="tip"> </span>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>

			</div>
			</article>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#search_project').val('${projectid }');
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_testjob')
						.bootstrapTable(
								{
									url : '/testJobs/list.do', //请求后台的URL（*）
									method : 'get', //请求方式（*）
									toolbar : '#toolbar', //工具按钮用哪个容器
									striped : true, //是否显示行间隔色
									cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
									pagination : true, //是否显示分页（*）
									sortable : false, //是否启用排序
									sortOrder : "asc", //排序方式
									queryParams : oTableInit.queryParams,//传递参数（*）
									sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
									pageNumber : 1, //初始化加载第一页，默认第一页
									pageSize : 10, //每页的记录行数（*）
									pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
									search : true, //是否显示表格搜索，此搜索会进服务端
									strictSearch : true,
									showColumns : false, //是否显示所有的列
									showRefresh : true, //是否显示刷新按钮
									minimumCountColumns : 2, //最少允许的列数
									clickToSelect : true, //是否启用点击选中行
									height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
									uniqueId : "ID", //每一行的唯一标识，一般为主键列
									showToggle : false, //是否显示详细视图和列表视图的切换按钮
									cardView : false, //是否显示详细视图
									detailView : false, //是否显示父子表
									columns : [
											{
												field : 'projectid',
												title : 'projectid',
												visible : false
											},
											{
												field : 'taskName',
												title : '调度名称',
												width : '18%',
												formatter : function(value,
														row, index) {
													return '<a href="/tastExecute/load.do?jobid='
															+ row.id
															+ '">'
															+ value + '</a> ';
												}
											},
											{
												field : 'planproj',
												title : '项目名称',
												width : '10%',
											},
											{
												field : 'testlinkname',
												title : '计划名称',
												width : '17%',
											},
											{
												field : 'taskType',
												title : '调度策略',
												width : '6%',
												formatter : function(value,
														row, index) {
													if (value == 'O') {
														return "只执行一次";
													}
													if (value == 'D') {
														return "按调度执行";
													}
												}
											},
											{
												field : 'runTime',
												title : '开始时间',
												width : '10%',
												formatter : function(value,
														row, index) {
													var now = new Date(value);
												    var year=now.getFullYear();    
												    var month=now.getMonth()+1;    
												    var date=now.getDate();    
												    var hour=now.getHours();    
												    var minute=now.getMinutes();    
												    var second=now.getSeconds();    
												     //var time= year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;
												    var time=year+'-'+add0(month)+'-'+add0(date)+' '+add0(hour)+':'+add0(minute)+':'+add0(second);
												    return time;
												}
											},
											{
												field : 'startTimestr',
												title : '执行时间表达式',
												width : '8%',
											},
											{
												field : 'clientip',
												title : '客户端IP',
												width : '7%',
												formatter : function(value,
														row, index) {
													if(value.indexOf("**0")>-1){
														return '<font style="color:#00bf5f">'+value.substring(0,value.indexOf("**0"))+'</font>';
													}else if(value.indexOf("**1")>-1){
														return '<font style="color:#ff0000">'+value.substring(0,value.indexOf("**1"))+'</font>';
													}else if(value.indexOf("**2")>-1){
														return '<font style="color:#FF7F00">'+value.substring(0,value.indexOf("**1"))+'</font>';
													}else{
														return '<font style="color:#FF7F00">'+value+'</font>';
													}
												}
											},
											{
												field : 'threadCount',
												title : '线程数',
												width : '5%',
											},
											{
												field : 'state_str',
												title : '状态',
												width : '7%',
											},
											{
												field : 'id',
												title : '操作',
												width : '12%',
												align : 'center',
												formatter : function(value,
														row, index) {
													var e = '<a href="#" mce_href="#" onclick="edit(\''
															+ index
															+ '\',\''
															+ row.id
															+ '\')">编辑</a> ';
													var d = '<a href="#" mce_href="#" onclick="del(\''
															+ index
															+ '\',\''
															+ row.id
															+ '\')">删除</a> ';
													var f = '<a href="#" mce_href="#" onclick="show(\''
															+ index
															+ '\',\''
															+ row.id
															+ '\')">详情</a> ';
													var g = '<a href="#" mce_href="#" onclick="permissionboolean(\'tast_ex\',\''
															+ row.id
															+ '\')">执行</a> ';
													var h;
													if (row.showRun == true) {
														if (row.state == 0)
															h = '<a href="#" mce_href="#" onclick="permissionboolean(\'tast_run\',\''
																	+ row.id
																	+ '\')">启动</a> ';
														if (row.state == 1)
															h = '<a href="#" mce_href="#" onclick="permissionboolean(\'tast_remove\',\''
																	+ row.id
																	+ '\')">关闭</a> ';
													} else {
														h = '<lable>已失效</lable> ';
													}

													return e + d + f + g + h;
												}
											} ],
								});
			};
			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					limit : params.limit, //页面大小
					offset : params.offset, //页码偏移量
					search : params.search, //搜索参数
					projectid : $('#search_project').val(), //项目ID
				};
				return temp;
			};

			return oTableInit;
		};

		var searchproject = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_testjob').bootstrapTable('destroy');
			oTable.Init();
		};

		btn_add.onclick = function() {
			var status = document.getElementById("loginstatus").value;
			if (status == "false") {
				if (window.confirm("你未登录哦，要先去登录吗？")) {
					var url = '/progressus/signin.jsp';
					window.location.href = url;
					return true;
				} else {
					return false;
				}
			}

			var url = '/testJobs/add.do';
			window.location.href = url;
		}

		btn_upload.onclick = function() {
			var status = document.getElementById("loginstatus").value;
			if (status == "false") {
				if (window.confirm("你未登录哦，要先去登录吗？")) {
					var url = '/progressus/signin.jsp';
					window.location.href = url;
					return true;
				} else {
					return false;
				}
			}

			$("#upload").modal('show');
		}
		
		btn_log.onclick = function() {
			$(".form_date").datetimepicker({
				format: 'yyyy-mm-dd',
		        language:  'zh-CN',
		        startDate : new Date(new Date()-365*24*60*60*1000),
		        endDate : new Date(),
		        weekStart: 1,
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				minView: 2,
				forceParse: 0
		    });
			$("#ModalLog").modal('show');
		}

		function downlog(){		
			var date=document.getElementById("dtp_input").value;
			var clientip=document.getElementById("clientip").value;
			var url = '/testJobs/down.do?startDate='+date+'&clientip='+clientip;
			window.open(url);
			$("#ModalLog").modal('hide');
		}
		
		function upload(){
	    	var clientip=document.getElementById("clientipforupload").value
			var url = '/testJobs/to_upload.do?clientip='+clientip;
			window.location.href=url;
			$("#upload").modal('hide');
		}
		
		function del(selectIndex, id) {
			var status = document.getElementById("loginstatus").value;
			if (status == "false") {
				if (window.confirm("你未登录哦，要先去登录吗？")) {
					var url = '/progressus/signin.jsp';
					window.location.href = url;
					return true;
				} else {
					return false;
				}
			}

			if (confirm("真的要删除此调度任务吗?")) {
				$.ajax({
					type : "POST",
					cache : false,
					async : true,
					dataType : "json",
					url : "delete.do",
					data : {
						"jobid" : id
					},
					success : function(data, status) {
						if (data.status == "success") {
							$('#tb_testjob').bootstrapTable('hideRow', {
								index : selectIndex
							});
							toastr.success(data.ms);
							if (reLoad) {
								$('#tb_testjob').bootstrapTable('refresh');
							}
						} else {
							toastr.info(data.ms);
						}
					},
					error : function() {
						toastr.error('删除出错');
					}
				});
			}
		}

		function edit(selectIndex, id) {
			var status = document.getElementById("loginstatus").value;
			if (status == "false") { 
 				if (window.confirm("你未登录哦，要先去登录吗？")) {
					var url = '/progressus/signin.jsp';
					window.location.href = url;
					return true;
				} else {
					return false;
				}
			}

			var url = '/testJobs/update.do?id=' + id;
			window.location.href = url;
		}

		function show(selectIndex, id) {
			var url = '/testJobs/show.do?id=' + id;
			window.location.href = url;
		}

		function permissionboolean(code, id) {
			var url = "/userlogin/permissionboolean.do?permissioncode=" + code;
			jQuery.getJSON(url, null, function call(result) {
				if (result.data[0] == null) {
					toastr.warning('你好，当前用户无权限进行此操作，请联系管理员！'); 
					return false;
				} else if (result.data[0] == "true") {
					if (code == "tast_run") {
						$.ajax({
							type : "post",
							dataType : "text",
							async : true,
							url : encodeURI("/testJobs/run.do?id=" + id),
							success : function(json) {
								toastr.success(json);
							},
							error : function() {
								toastr.error('系统异常，请稍后再试!');
							}

						});
					} else if (code == "tast_remove") {
						$.ajax({
							type : "post",
							dataType : "text",
							async : true,
							url : encodeURI("/testJobs/remove.do?id=" + id),
							success : function(json) {
								toastr.success(json);
							},
							error : function() {
								toastr.error('系统异常，请稍后再试!');
							}

						});
					} else if (code == "tast_ex") {
						$.ajax({
							type : "post",
							dataType : "text",
							async : true,
							url : encodeURI("/testJobs/startNow.do?id=" + id),
							success : function(json) {
								if(json.indexOf("失败") != -1){
									toastr.error(json);
								}else{
									toastr.success(json);
								}						
							},
							error : function() {
								toastr.error('系统异常，请稍后再试!');
							}
						});
					}
					return true;
				} else {
					toastr.warning('你好，当前用户无权限进行此操作，请联系管理员！'); 
					return false;
				}
			});
		}
		
		function add0(m){return m<10?'0'+m:m }
	</script>
</body>
</html>