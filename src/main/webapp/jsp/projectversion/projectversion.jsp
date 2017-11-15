<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>项目版本信息管理</title>
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
			<li class="active">质量管理</li>
			<li class="active">版本信息</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">项目版本信息管理</h1>
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
							
							<label class="control-label" for="txt_search_date" style="float: left;">上线日期:&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <div class="input-group date form_date col-md-3" id="datepicker" style="float: left;">  
                                  <input type="text" class="form-control" name="start" id="qBeginTime" readonly/>
                                  <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar"></span></span>
                                  <span class="input-group-addon">至</span>  
                                  <input type="text" class="form-control" name="end" id="qEndTime" readonly/>
                                  <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
						</div>
					</div>
				</div>

				<div id="toolbar" class="btn-group">
					<button id="btn_addplan" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;新增计划
					</button>
					<button id="btn_addversion" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>&nbsp;新增版本
					</button>
					<button id="btn_zdlist" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>&nbsp;禅道报表
					</button>
					<button id="btn_reports" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>&nbsp;项目报表
					</button>
				</div>
				<table id="tb_projectversion"></table>
				
								<!-- 模态框示例（Modal） -->
				<div class="modal fade" id="reports" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">项目版本信息统计</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
								  <div class="form-group">
											<label for="clientip" class="col-sm-3 control-label">统计报表</label>
											<div class="input-group col-md-5">
												<select class="form-control" name="reportstype" id="reportstype" onchange="choosereports()">
														<option value="0">生成项目质量对比图</option>
														<option value="1">项目版本质量报表</option>
														<option value="2">单个项目版本质量趋势图</option>
														<option value="3">单个项目版本质量柱状图</option>
												</select>
											</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-sm-3" for="txt_search_project">项目名称:</label>
										<div class="input-group col-md-5">
											<select class="form-control" id="choose_project">
												<c:forEach var="p" items="${projects }">
													<option value="${p.projectid}">${p.projectname}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-3" for="txt_choose_date">日期选择:</label>
										<div class="input-group date form_date col-md-7">
											<input type="text" class="form-control" name="start" id="cBeginTime" readonly /> 
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span> 
 											<span class="input-group-addon">至</span> 
											<input type="text"	class="form-control" name="end" id="cEndTime" readonly />
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
										</div>
									</div>

								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button class="btn btn-primary" onclick="goreports()">查看</button>
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
			
			$('#qBeginTime').datetimepicker({
				format: 'yyyy-mm-dd',
		        language:  'zh-CN',
			    todayBtn : "linked",  
			    autoclose : true,  
			    todayHighlight : true,
			    forceParse: 0,
			    weekStart: 1,
			    minView: "month",//设置只显示到月份
			    startView: 2,
			    endDate : new Date(),
			    clearBtn:true,
			}).on('changeDate',function(e){  
			    var startTime = e.date;  
			    $('#qEndTime').datetimepicker('setStartDate',startTime);
				//1.初始化Table
				var oTable = new TableInit();
				$('#tb_projectversion').bootstrapTable('destroy');
				oTable.Init();
			});  
			//结束时间：  
			$('#qEndTime').datetimepicker({
				format: 'yyyy-mm-dd',
		        language:  'zh-CN',
			    todayBtn : "linked",  
			    autoclose : true,  
			    todayHighlight : true,
			    forceParse: 0,
			    minView: "month",//设置只显示到月份
			    weekStart: 1,
			    endDate : new Date(),
			    clearBtn:true,
			}).on('changeDate',function(e){  
			    var endTime = e.date;  
			    $('#qBeginTime').datetimepicker('setEndDate',endTime);
				//1.初始化Table
				var oTable = new TableInit();
				$('#tb_projectversion').bootstrapTable('destroy');
				oTable.Init();
			});
			
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_projectversion').bootstrapTable({
					url : '/projectVersion/list.do', //请求后台的URL（*）
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
					columns : [{
						field : 'versionid',
						title : 'versionid',
						visible : false
					},  {
						field : 'projectid',
						title : 'projectid',
						visible : false
					}, {
						field : 'sectorProjects.projectname',
						title : '项目名称',
						width : '15%',
					}, {
						field : 'versiontype',
						title : '版本状态',
						width : '8%',
						formatter : function(value,
								row, index) {
							if(value==0){
							    return '<font style="color:#FFA54F">计划执行中...</font>';					
							}else if(value==1){
								return '<font style="color:#00DB00">已完成</font>';
							}else{
								return value;
							}						
						}
					}, {
						field : 'versionnumber',
						title : '版本号',
						width : '10%',
						formatter : function(value,
								row, index) {
							if(row.zt_versionlink!==""){
							    return value;					
							}else{
								return '<a href="http://${zentaoip }/zentao/project-task-${t.zt_versionlink }.html" target=_blank style="text-decoration: none;color:#0000FF;">'+ value +'</a>';
							}						
						}
					}, {
						field : 'actually_launchdate',
						title : '版本达成日期',
						width : '7%',
					}, {
						field : 'protime_deviation',
						title : '项目进度偏差',
						width : '10%',
						formatter : function(value,
								row, index) {
							if(value!=''){
							    return value+"%";					
							}else{
								return "数据缺失,无法计算";
							}						
						}
					}, {
						field : 'plan_demand',
						title : '需求达成率',
						width : '7%',
						formatter : function(value,
								row, index) {
							if(value!=0&&row.actually_demand!=0){
								var xqdcl=(row.actually_demand/value)*100;
							    return xqdcl.toFixed(2)+'%';					
							}else{
								return "0%";
							}						
						}
					}, {
						field : 'imprint',
						title : '版本说明',
						width : '33%',
					}, {
						field : 'id',
						title : '操作',
						width : '10%',
						align : 'center',
						formatter : function(value,
								row, index) {
							var e = '<a href="#" mce_href="#" onclick="edit(\''
									+ index
									+ '\',\''
									+ row.versionid
									+ '\')">编辑</a> ';
							var d = '<a href="#" mce_href="#" onclick="del(\''
									+ index
									+ '\',\''
									+ row.versionid
									+ '\')">删除</a> ';
							var f = '<a href="#" mce_href="#" onclick="show(\''
									+ index
									+ '\',\''
									+ row.versionid
									+ '\')">详情</a> ';
							var g = '<a href="#" mce_href="#" onclick="zdlist(\''
								    + index
								    + '\',\''
								    + row.versionid
								    + '\')">禅道</a> ';
							if (row.zt_versionlink!=""&&row.versiontype==1) {
								return e + d + f + g;
							} else {
								return e + d + f;
							}						
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
					startDate: $('#qBeginTime').val(), //查询日期段
					endDate: $('#qEndTime').val(), //查询日期段
				};
				return temp;
			};

			return oTableInit;
		};
		
		var searchproject = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_projectversion').bootstrapTable('destroy');
			oTable.Init();
		};
	    
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

			if (confirm("真的要删除此版本信息吗?")) {
				$.ajax({
					type : "POST",
					cache : false,
					async : true,
					dataType : "json",
					url : "delete.do",
					data : {
						"versionid" : id
					},
					success : function(data, status) {
						if (data.status == "success") {
							$('#tb_projectversion').bootstrapTable('hideRow', {
								index : selectIndex
							});
							toastr.success(data.ms);
							if (reLoad) {
								$('#tb_projectversion').bootstrapTable('refresh');
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

			var url = '/projectVersion/update.do?versionid=' + id;
			window.location.href = url;
		}

		function show(selectIndex, id) {
			var url = '/projectVersion/show.do?versionid=' + id;
			window.location.href = url;
		}
		
		function zdlist(selectIndex, id) {
			var url = '/zentao/list.do?versionid=' + id;
			window.location.href = url;
		}
		
		btn_addplan.onclick = function() {
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

			var url = '/projectVersion/addplan.do';
			window.location.href = url;
		}
		
		btn_addversion.onclick = function() {
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

			var url = '/projectVersion/add.do';
			window.location.href = url;
		}
		
		btn_zdlist.onclick = function() {
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

			var url = '/zentao/list.do';
			window.location.href = url;
		}
		
		btn_reports.onclick = function() {
			$('#cBeginTime').datetimepicker({
				format: 'yyyy-mm-dd',
		        language:  'zh-CN',
			    todayBtn : "linked",  
			    autoclose : true,  
			    todayHighlight : true,
			    forceParse: 0,
			    weekStart: 1,
			    minView: "month",//设置只显示到月份
			    startView: 2,
			    endDate : new Date(),
			}).on('changeDate',function(e){  
			    var startTime = e.date;  
			    $('#cEndTime').datetimepicker('setStartDate',startTime);
			});  
			//结束时间：  
			$('#cEndTime').datetimepicker({
				format: 'yyyy-mm-dd',
		        language:  'zh-CN',
			    todayBtn : "linked",  
			    autoclose : true,  
			    todayHighlight : true,
			    forceParse: 0,
			    minView: "month",//设置只显示到月份
			    weekStart: 1,
			    endDate : new Date()
			}).on('changeDate',function(e){  
			    var endTime = e.date;  
			    $('#cBeginTime').datetimepicker('setEndDate',endTime);
			});
			
			$('#cBeginTime').datetimepicker('setDate',new Date(new Date()-7*24*60*60*1000));
			$('#cEndTime').datetimepicker('setDate',new Date(new Date()-1000));			
			
			choosereports();
			$("#reports").modal('show');
		}
		
		function goreports() {
			var reportstype=$('#reportstype').val();
			var startdate=$('#cBeginTime').val();
			var enddate=$('#cEndTime').val();
			var projectid=$('#choose_project').val();
			if(reportstype==0){
				var url = '/projectVersion/barchart_html5.do?start_date='+startdate+'&end_date='+enddate;
				window.location.href=url;
			}else if(reportstype==1){
				var url = '/projectVersion/showreport.do?startdate='+startdate+'&enddate='+enddate;
				window.location.href=url;
			}else if(reportstype==2){
				var url = '/projectVersion/linechart_html5.do?projectid='+projectid+'&startdate='+startdate+'&enddate='+enddate;
				window.location.href=url;
			}else if(reportstype==3){
				var url = '/projectVersion/onepro_barcharthtml5.do?projectid='+projectid+'&startdate='+startdate+'&enddate='+enddate;
				window.location.href=url;
			}
		}
		
		function choosereports(){
			var reportstype=$('#reportstype').val();
			if(reportstype==0||reportstype==1){
				var isExit = false;
				for (var i = 0; i < $("#choose_project").size() ; i++) {
					if ($("#choose_project")[i].value == 99) {
					isExit = true;
					break;
					}
				}
				if(!isExit){
					$("#choose_project").append("<option value='99'>全部项目/未知项目</option>");
				}
				$('#choose_project').val(99);
				$("#choose_project").attr("disabled","disabled");
			}else{
				$("#choose_project").attr("disabled",false);
				$("#choose_project option[value='99']").remove();
			}

		}
	</script>
</body>
</html>