<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>项目流程检查信息</title>
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
			<li class="active">项目流程检查信息</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">项目流程检查信息</h1>
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
							
							<label class="control-label" for="txt_search_date" style="float: left;">检查日期:&nbsp;&nbsp;&nbsp;&nbsp;</label>
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
					<button id="btn_addcheck" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;新增检查
					</button>
					<button id="btn_checkplan" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp;检查计划
					</button>
					<button id="btn_chart" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>&nbsp;统计报表
					</button>
				</div>
				<table id="tb_flowcheck"></table>
				
								<!-- 模态框示例（Modal） -->
				<div class="modal fade" id="checkchart" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">流程检查统计图表</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
								  <div class="form-group">
											<label for="selectre" class="col-sm-3 control-label">统计报表</label>
											<div class="input-group col-md-5">
												<select class="form-control" name="reportstype" id="reportstype">
														<option value="0">生成数据图表</option>
														<option value="1">生成数据报表</option>
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
				$('#tb_flowcheck').bootstrapTable('destroy');
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
				$('#tb_flowcheck').bootstrapTable('destroy');
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
				$('#tb_flowcheck').bootstrapTable({
					url : '/flowCheck/list.do', //请求后台的URL（*）
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
						field : 'projectid',
						title : 'projectid',
						visible : false
					}, {
						field : 'projectname',
						title : '项目名称',
						width : '15%',
						formatter : function(value,
								row, index) {
							return '<a href="/flowCheck/loadinfo.do?projectid='+row.projectid
									+'&checkid='+row.checkid+'&versionnum='+row.versionnum
									+'" style="text-decoration: none;color:#0000FF;">'+ value +'</a>';					
						}
					}, {
						field : 'versionnum',
						title : '版本号',
						width : '15%',
						editable : {
							type : 'text',
							title : '版本号',
							emptytext : "【版本号】为空",
							validate : function(value) {
								if (value.length > 30)
									return '版本号不能超过30个字符';
								if (value.length < 2)
									return '版本号不能小于2个字符';
							}
						}
					}, {
						field : 'checkid',
						title : '第几次检查',
						width : '10%',
						formatter : function(value,
								row, index) {
							if(value!=''){
							    return "第"+value+"次检查";					
							}else{
								return "数据异常";
							}						
						}
					}, {
						field : 'firstcheckdate',
						title : '首次检查日期',
						width : '10%',
					}, {
						field : 'checknum',
						title : '已检查项',
						width : '10%',
					}, {
						field : 'checksucnum',
						title : '合格项',
						width : '10%',
					}, {
						field : 'checkunsucnum',
						title : '不合格项',
						width : '10%',
					},{
						field : 'unchecknum',
						title : '未检查项',
						width : '10%',
					},{
						field : 'persuc',
						title : '一次性合格率',
						width : '10%',
					}],
					
					onEditableSave : function(field, row, oldValue, $el) {
						var status = document.getElementById("loginstatus").value;
						if(status=="false"){
							if(window.confirm("你未登录哦，要先去登录吗？")){
								var url = '/progressus/signin.jsp';
								window.location.href=url;
								return true; 
							}else{
								return false; 
							} 	
						}
						
						$('#tb_flowcheck').bootstrapTable("resetView");
						$.ajax({
							type : "post",
							url : "/flowCheck/updateversion.do",
							data : {"versionold":oldValue,"versionnew":row.versionnum,"projectid":row.projectid},
							dataType : 'JSON',
							success : function(data, status) {
								if (data.status == "success") {
									 toastr.success(data.ms);
								}else{
									$('#tb_flowcheck').bootstrapTable('refresh');
									toastr.info(data.ms);
								}
							},
							error : function(data, status) {
								toastr.error('编辑失败!');
							},
							complete : function() {

							}

						});
					},
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
			$('#tb_flowcheck').bootstrapTable('destroy');
			oTable.Init();
		};
	    
		btn_addcheck.onclick = function() {
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

			var url = '/flowCheck/add.do';
			window.location.href = url;
		}
		
		btn_checkplan.onclick = function() {
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

			var url = '/planflowCheck/load.do';
			window.location.href = url;
		}
		
		btn_chart.onclick = function() {
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
			
			$('#cBeginTime').datetimepicker('setDate',new Date(new Date()-365*24*60*60*1000));
			$('#cEndTime').datetimepicker('setDate',new Date(new Date()-1000));			

			$("#checkchart").modal('show');
		}
		
		function goreports() {
			var reportstype=$('#reportstype').val();
			var startdate=$('#cBeginTime').val();
			var enddate=$('#cEndTime').val();
			if(reportstype==0){
				var url = '/flowCheck/barchart_html5.do?checkstartdate='+startdate+'&checkenddate='+enddate;
				window.location.href=url;
			}else if(reportstype==1){
				var url = '/flowCheck/report_count.do?checkstartdate='+startdate+'&checkenddate='+enddate;
				window.location.href=url;
			}
		}
	</script>
</body>
</html>