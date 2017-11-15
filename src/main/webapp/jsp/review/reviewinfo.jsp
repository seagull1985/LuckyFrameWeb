<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>评审记录详情</title>
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
			<li class="active"><a href="/review/load.do">项目评审记录</a></li>
			<li class="active">评审详细信息</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">评审详细信息</h1>
			</header>

			<div class="panel-body" style="padding-bottom: 0px;">
				<div class="panel panel-default">
					<div class="panel-heading">评审基本信息</div>
					<div class="panel-body">
						<div class="form-group" style="margin-top: 15px">
							<label class="control-label col-sm-4" for="txt_review">项目名称：&nbsp;${review.sectorProjects.projectname}</label>
							<label class="control-label col-sm-4" for="txt_review">项目版本：&nbsp;${review.version}</label>
                            <label class="control-label col-sm-4" for="txt_review">评审日期：&nbsp;${review.review_date}</label></br>
                            <label class="control-label col-sm-4" for="txt_review">评审类型：&nbsp;${review.review_type}</label>
							<label class="control-label col-sm-4" for="txt_review">评审对象：&nbsp;${review.review_object}</label>
						</div>
					</div>
				</div>

				<div id="toolbar" class="btn-group">
					<button id="btn_addreviewinfo" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;添加记录
					</button>
					<button id="btn_editreviewinfo" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;编辑记录
					</button>
					<button id="btn_delreviewinfo" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;删除记录
					</button>
				</div>
				<table id="tb_reviewinfo"></table>

			</div>
			</article>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_reviewinfo').bootstrapTable({
					url : '/reviewinfo/list.do?reviewid=${reviewid}', //请求后台的URL（*）
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
					search : false, //是否显示表格搜索，此搜索会进服务端
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
						field : 'review_id',
						title : 'review_id',
						visible : false
					}, {
						field : 'bug_description',
						title : '问题描述',
						width : '15%',
					}, {
						field : 'corrective',
						title : '改正措施',
						width : '10%',
					}, {
						field : 'status',
						title : '问题状态',
						width : '10%',
					}, {
						field : 'duty_officer',
						title : '责任人',
						width : '7%',
					}, {
						field : 'confirm_date',
						title : '最后确认日期',
						width : '8%',
					}],
				});
			};

			return oTableInit;
		};

		btn_addreviewinfo.onclick = function() {
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

			var url = '/reviewinfo/add.do?reviewid=${reviewid}';
			window.location.href = url;
		}
		
		btn_editreviewinfo.onclick=function(){
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
         
            var id = $.map($('#tb_reviewinfo').bootstrapTable('getSelections'), function (row) {
                return row.id;
                 });
            if(id.length == 1 ){
    			var url = '/reviewinfo/update.do?id='+id;
    			window.location.href=url;
            }else{
            	toastr.warning('您有且只能选择一条记录编辑哦！'); 
            }
	    }
		
		btn_delreviewinfo.onclick=function(){
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
	        deleteItem($('#tb_reviewinfo'), selectIndex, true);
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