<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>流程检查计划</title>
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
			<li class="active"><a href="../flowCheck/load.do">流程检查信息</a></li>
			<li class="active">流程检查计划</li>
		</ol>
		
        <input name="planid" id="planid" type="hidden"  />
		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">流程检查计划</h1>
			</header>
			
			<div class="panel-body" style="padding-bottom: 0px;">

				<div id="toolbar" class="btn-group">
					<button id="btn_addplan" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;新增检查计划
					</button>
					<button id="btn_updateplan" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;编辑检查计划
					</button>
					<button id="btn_delplan" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;删除检查计划
					</button>
					<button id="btn_gocheck" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-check" aria-hidden="true"></span>&nbsp;去检查
					</button>
				</div>
				<table id="tb_planflowcheck"></table>
				
								<!-- 模态框示例（Modal） -->
				<div class="modal fade" id="mtocheck" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">将计划转成检查结果</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
								  <div class="form-group">
											<label for="clientip" class="col-sm-3 control-label">检查轮次</label>
											<div class="input-group col-md-5">
												<select class="form-control" name="stocheck" id="stocheck">
												</select>
											</div>
										</div>

								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button class="btn btn-primary" onclick="tocheck()">去检查</button>
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
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_planflowcheck').bootstrapTable({
					url : '/planflowCheck/list.do', //请求后台的URL（*）
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
					columns : [{
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
						field : 'versionnum',
						title : '版本号',
						width : '15%',
					}, {
						field : 'checkphase',
						title : '检查阶段',
						width : '15%',
					}, {
						field : 'checknode',
						title : '检查节点',
						width : '15%',
					}, {
						field : 'checkentryid',
						title : '检查内容',
						width : '25%',
					}, {
						field : 'plandate',
						title : '计划检查日期',
						width : '12%',
					}],
				});
			};

			return oTableInit;
		};

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

			var url = '/planflowCheck/add.do';
			window.location.href = url;
		}
		
		btn_updateplan.onclick=function(){
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
         
            var id = $.map($('#tb_planflowcheck').bootstrapTable('getSelections'), function (row) {
                return row.id;
                 });
            if(id.length == 1 ){
    			var url = '/planflowCheck/update.do?id='+id;
    			window.location.href=url;
            }else{
            	toastr.warning('您有且只能选择一条检查计划哦！'); 
            }
	    }
		
		btn_delplan.onclick=function(){
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
	        deleteItem($('#tb_planflowcheck'), selectIndex, true);
	    }
	    
	    function deleteItem($table, selectIndex, reLoad){
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                      return row.id;
                       }); 
	        if(ids.length != 0 ){
	        	if(confirm("真的要删除检查计划吗?")){
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
	            toastr.warning('请选取要删除的检查计划！');
	        }
	    }
	    
	    btn_gocheck.onclick = function() {
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

            var id = $.map($('#tb_planflowcheck').bootstrapTable('getSelections'), function (row) {
                return row.id;
                 });
            if(id.length == 1 ){
            	getcheckid(id);
            	$("#mtocheck").modal('show');
            }else{
            	toastr.warning('您有且只能选择一条计划去检查哦！'); 
            }
			
		}
		
		function getcheckid(id){
			var url ="/planflowCheck/getcheckid.do?id="+id;
			document.getElementById("planid").value = id;
		     jQuery.getJSON(url,null,function call(result){
		       var options = "";
		       options = "<option value=\"0\">新增检查次数</option>";
		  	   jQuery.each(result.data, function(i, checkid){
		  		  options +=  "<option value='"+checkid+"'>"+"第"+checkid+"次检查"+"</option>";
		  	      }); 
		  	      jQuery("#stocheck").html(options);
		      });
		}
		
		function tocheck(){
			var checkid = document.getElementById("stocheck").value;
			var url = '/planflowCheck/tocheck.do?id='+document.getElementById("planid").value+'&checkid='+checkid;
			window.location.href=url;
		}
	</script>
</body>
</html>