<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>平台操作日志</title>
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
			<li class="active"><a href="/userInfo/load.do">用户管理</a></li>
			<li class="active">平台操作日志</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">平台操作日志</h1>
			</header>

			<div class="panel-body" style="padding-bottom: 0px;">
				<div class="panel panel-default">
					<div class="panel-heading">查询条件</div>
					<div class="panel-body">
						<div class="form-group" style="margin-top: 15px">
							
							<div class="col-md-12">
						<label class="control-label col-sm-1" style="width: 6%"
								for="txt_search_project">项目名称:</label>
							<div class="col-sm-3">
								<select class="form-control" id="search_project"
									onchange="searchproject()">
									<option value="0">全部日志</option>
									<c:forEach var="p" items="${projects }">
										<option value="${p.projectid}">${p.projectname}</option>
									</c:forEach>
								</select>
							</div>
								
						     <label class="control-label" for="txt_search_job" style="float: left;">日期段:&nbsp;&nbsp;&nbsp;&nbsp;</label>
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
				</div>

				<div id="toolbar" class="btn-group">
					<button id="btn_del" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除任务
					</button>
				</div>
				<table id="tb_olog"></table>

			</div>
			</article>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {	
			$('#search_project').val('${projectid }');
			
			$('#qBeginTime').datetimepicker({
				format: 'yyyy-mm-dd hh:mm:ss',
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
			    $('#qEndTime').datetimepicker('setStartDate',startTime);
				//1.初始化Table
				var oTable = new TableInit();
				$('#tb_olog').bootstrapTable('destroy');
				oTable.Init();
			});  
			//结束时间：  
			$('#qEndTime').datetimepicker({
				format: 'yyyy-mm-dd hh:mm:ss',
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
			    $('#qBeginTime').datetimepicker('setEndDate',endTime);
				//1.初始化Table
				var oTable = new TableInit();
				$('#tb_olog').bootstrapTable('destroy');
				oTable.Init();
			});
			
			$('#qBeginTime').val('${startdate }');
			$('#qEndTime').val('${enddate }');
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_olog')
						.bootstrapTable(
								{
									url : '/operationLog/list.do', //请求后台的URL（*）
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
												field : 'id',
												title : 'id',
												visible : false
											},
											{
												field : 'sectorProjects.projectname',
												title : '项目名称',
												width : '15%',
											},
											{
												field : 'operation_description',
												title : '操作描述',
												width : '65%',
											},
											{
												field : 'operationer',
												title : '操作人',
												width : '10%',
											},
											{
												field : 'operation_time',
												title : '操作时间',
												width : '10%',
											},],
								});
			};
			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					limit : params.limit, //页面大小
					offset : params.offset, //页码偏移量
					search : params.search, //搜索参数
					projectid : $('#search_project').val(), //项目ID
					startTime: $('#qBeginTime').val(), //查询日期段
					endTime: $('#qEndTime').val(), //查询日期段
				};
				return temp;
			};

			return oTableInit;
		};

		var searchproject = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_olog').bootstrapTable('destroy');
			oTable.Init();
		};
	</script>
</body>
</html>