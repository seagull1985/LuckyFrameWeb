<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>项目评审记录</title>
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
			<li class="active">项目评审记录</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">项目评审记录</h1>
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

							<label class="control-label" for="txt_search_date"
								style="float: left;">评审日期:&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<div class="input-group date form_date col-md-3" id="datepicker"
								style="float: left;">
								<input type="text" class="form-control" name="start"
									id="qBeginTime" readonly /> <span class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span></span> <span
									class="input-group-addon">至</span> <input type="text"
									class="form-control" name="end" id="qEndTime" readonly /> <span
									class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span></span>
							</div>
						</div>
					</div>
				</div>

				<div id="toolbar" class="btn-group">
					<button id="btn_addreview" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;增加评审
					</button>
					<button id="btn_reviewdetail" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>&nbsp;评审详情
					</button>
					<button id="btn_reviewedit" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;编辑评审
					</button>
					<button id="btn_reviewdel" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;删除评审
					</button>
				</div>
				<table id="tb_review"></table>

			</div>
			</article>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#search_project').val('${projectid }');

			$('#qBeginTime').datetimepicker({
				format : 'yyyy-mm-dd',
				language : 'zh-CN',
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true,
				forceParse : 0,
				weekStart : 1,
				minView : "month",//设置只显示到月份
				startView : 2,
				endDate : new Date(),
				clearBtn : true,
			}).on('changeDate', function(e) {
				var startTime = e.date;
				$('#qEndTime').datetimepicker('setStartDate', startTime);
				//1.初始化Table
				var oTable = new TableInit();
				$('#tb_review').bootstrapTable('destroy');
				oTable.Init();
			});
			//结束时间：  
			$('#qEndTime').datetimepicker({
				format : 'yyyy-mm-dd',
				language : 'zh-CN',
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true,
				forceParse : 0,
				minView : "month",//设置只显示到月份
				weekStart : 1,
				endDate : new Date(),
				clearBtn : true,
			}).on('changeDate', function(e) {
				var endTime = e.date;
				$('#qBeginTime').datetimepicker('setEndDate', endTime);
				//1.初始化Table
				var oTable = new TableInit();
				$('#tb_review').bootstrapTable('destroy');
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
				$('#tb_review').bootstrapTable({
					url : '/review/list.do', //请求后台的URL（*）
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
					columns : [ {
						checkbox : true,
						width : '3%',
					}, {
						field : 'id',
						title : 'id',
						visible : false
					}, {
						field : 'projectid',
						title : 'projectid',
						visible : false
					}, {
						field : 'sectorProjects.projectname',
						title : '项目名称',
						width : '15%',
					}, {
						field : 'version',
						title : '版本号',
						width : '10%',
					}, {
						field : 'review_type',
						title : '评审类型',
						width : '10%',
					}, {
						field : 'review_date',
						title : '评审时间',
						width : '7%',
					}, {
						field : 'bug_num',
						title : '发现问题',
						width : '8%',
					}, {
						field : 'repair_num',
						title : '已更正问题',
						width : '8%',
					}, {
						field : 'review_result',
						title : '评审结果',
						width : '10%',
					}, {
						field : 'confirm_date',
						title : '最后确认日期',
						width : '7%',
					}, {
						field : 'remark',
						title : '备注',
						width : '25%',
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
					startDate : $('#qBeginTime').val(), //查询日期段
					endDate : $('#qEndTime').val(), //查询日期段
				};
				return temp;
			};

			return oTableInit;
		};

		var searchproject = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_review').bootstrapTable('destroy');
			oTable.Init();
		};

		btn_addreview.onclick = function() {
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

			var url = '/reviewinfo/add.do';
			window.location.href = url;
		}
		
		btn_reviewdetail.onclick=function(){
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
         
            var id = $.map($('#tb_review').bootstrapTable('getSelections'), function (row) {
                return row.id;
                 });
            if(id.length == 1 ){
    			var url = '/reviewinfo/load.do?reviewid='+id;
    			window.location.href=url;
            }else{
            	toastr.warning('您有且只能选择一条评审记录查看详情哦！'); 
            }
	    }
		
		btn_reviewedit.onclick=function(){
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
         
            var id = $.map($('#tb_review').bootstrapTable('getSelections'), function (row) {
                return row.id;
                 });
            if(id.length == 1 ){
    			var url = '/review/update.do?id='+id;
    			window.location.href=url;
            }else{
            	toastr.warning('您有且只能选择一条评审记录编辑哦！'); 
            }
	    }
		
		btn_reviewdel.onclick=function(){
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
			
	        var selectIndex = $('input[name="btSelectItem"]:checked ').val();
	        deleteItem($('#tb_review'), selectIndex, true);
	    }
	    
	    function deleteItem($table, selectIndex, reLoad){
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                      return row.id;
                       }); 
	        if(ids.length != 0 ){
	        	if(confirm("真的要删除选择的评审记录吗?")){
		                $.ajax({
		                   type: "POST",
		                   cache:false,
		                   async : true,
		                   dataType : "json",
		                   url:  "delete.do",
		                   contentType: "application/json", //必须有
		                   data: JSON.stringify({"ids":ids}),
		                   success: function(data, status){
		                           if (data.status == "success"){
		                               $table.bootstrapTable('hideRow', {index:selectIndex});
		                               toastr.success(data.ms);
		                              if(reLoad){
		                                  $table.bootstrapTable('refresh');
		                              }
		                           }else{
		                        	   toastr.info(data.ms);
		                           }
		                   },error:function()
		                    {
		                	   toastr.error('删除出错!');
		                    }
		                });
	            }    
	        }else{
	            toastr.warning('请选取要删除的评审记录！');
	        }
	    }
	</script>
</body>
</html>