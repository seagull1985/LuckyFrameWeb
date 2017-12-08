<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>部门管理</title>
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
			<li class="active">部门管理</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align:center;">部门管理</h1>
			</header>
			
			<div class="panel-body" style="padding-bottom: 0px;">

				<div id="toolbar" class="btn-group">
					<button id="btn_add" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增部门
					</button>
					<button id="btn_delete" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除部门
					</button>
				</div>
				<table id="tb_secondarysector"></table>

				<!-- 模态框示例（Modal） -->
				<form method="post" action="" class="form-horizontal" role="form"
					id="form_data" onsubmit="return check_form()" style="margin: 20px;">
					<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">部门信息</h4>
								</div>
								<div class="modal-body">
									<form class="form-horizontal" role="form">									
										<div class="form-group">
											<label for="departmentname" class="col-sm-3 control-label">部门名称</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" name="departmentname"
													id="departmentname" placeholder="部门名称">
											</div>
										</div>
										<div class="form-group">
											<label for="departmenthead" class="col-sm-3 control-label">部门经理</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" name="departmenthead"
													id="departmenthead" placeholder="部门经理">
											</div>
										</div>

									</form>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">关闭</button>
									<button type="submit" class="btn btn-primary">提交</button>
									&nbsp;&nbsp;&nbsp;&nbsp;<span id="tip"> </span>
								</div>
							</div>
							<!-- /.modal-content -->
						</div>
						<!-- /.modal -->
					</div>
				</form>
				
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
				$('#tb_secondarysector').bootstrapTable({
					url : '/secondarySector/list.do', //请求后台的URL（*）
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
					uniqueId : "projectid", //每一行的唯一标识，一般为主键列
					showToggle : false, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					columns : [ {
						checkbox : true,
						width : '3%',
					}, {
						field : 'departmentname',
						title : '部门名称',
						width : '67%',
						editable : {
							type : 'text',
							title : '部门名称',
							emptytext : "无部门名称",
							validate : function(value) {
								if (!$.trim(value))
									return '部门名称不能为空';
								if (value.length > 20)
									return '部门名称不能超过20个字符';
								if (value.length < 2)
									return '部门名称不能少于2个字符';
							}
						}
					}, {
						field : 'departmenthead',
						title : '部门经理',
						width : '30%',
						editable : {
							type : 'text',
							title : '部门经理',
							emptytext : "无部门经理",
							validate : function(value) {
								if (!$.trim(value))
									return '部门经理不能为空';
								if (value.length > 20)
									return '部门经理不能超过20个字符';
								if (value.length < 2)
									return '部门经理不能少于2个字符';
							}
						}
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
						
						$('#tb_secondarysector').bootstrapTable("resetView");
						$.ajax({
							type : "post",
							url : "/secondarySector/update.do",
							data : row,
							dataType : 'JSON',
							success : function(data, status) {
								if (data.status == "success") {
									toastr.success(data.ms);
								}else{
									$('#tb_secondarysector').bootstrapTable('refresh');
									toastr.info(data.ms);
								}
							},
							error : function() {
								toastr.error('编辑失败!');
							},
							complete : function() {

							}

						});
					}
				});
			};
			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					limit : params.limit, //页面大小
					offset : params.offset, //页码偏移量
					search : params.search, //搜索参数
				};
				return temp;
			};

			return oTableInit;
		};

		$(document).ready(
				function() {
					$('#form_data').bootstrapValidator({
						message : '当前填写信息无效！',
						//live: 'submitted',
						feedbackIcons : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							departmentname : {
								message : '部门名称无效！',
								validators : {
									notEmpty : {
										message : '部门名称不能为空'
									},
									stringLength : {
										min : 2,
										max : 20,
										message : '部门名称长度必须在2~20个字符区间'
									}
								}
							},
							departmenthead : {
								message : '部门经理无效！',
								validators : {
									notEmpty : {
										message : '部门经理不能为空'
									},
									stringLength : {
										min : 2,
										max : 20,
										message : '部门经理长度必须在2~20个字符区间'
									}
								}
							}
						}
					}).on(
							'success.form.bv',
							function(e) {
								// Prevent submit form
								e.preventDefault();
								var $form = $(e.target), validator = $form
										.data('bootstrapValidator');
								$form.find('.alert').html('部门创建成功！');
							});
				});
	    
	    // 提交表单
	    function check_form()
	    {
	    	$('#form_data').data('bootstrapValidator').validate();  
	    	  if(!$('#form_data').data('bootstrapValidator').isValid()){  
	    		 return ;  
	    	  } 
	    	  
	        var form_data = $('#form_data').serialize();
	        $.param(form_data); 
	        // 异步提交数据到action页面
	        $.ajax(
	                {
	                    url: "add.do",
	                    data:form_data,
	                    type: "post",
	                    dataType : "json",
	                    beforeSend:function()
	                    {
	                        $("#tip").html("<span style='color:blue'>正在处理...</span>");
	                        return true;
	                    },
	                    success:function(data, status)
	                    {
	                        if(data.status == "success")
	                        {
	                            $("#tip").html("<span style='color:blueviolet'>恭喜，添加部门成功！</span>");
	                            // document.location.href='system_notice.php'
	                            toastr.success(data.ms);
	                        }else{
	                            $("#tip").html("<span style='color:red'>失败，请重试</span>");
	                            toastr.warning(data.ms); 
	                        }
	                    },
	                    error:function()
	                    {
	                    	toastr.error('请求出错!');
	                    },
	                    complete:function()
	                    {
	                      /*   $('#addModal').hide(); */
	                    }
	                });

	        return false;
	    }

	    btn_add.onclick=function(){
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
	    	$("#addModal").modal('show');
	    }
	    
	    $(function () { $('#addModal').on('hide.bs.modal', function () {
	    	// 关闭时清空edit状态为add
	    	location.reload();
	    })
	    });
	    
	    btn_delete.onclick=function(){
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
	        deleteItem($('#tb_secondarysector'), selectIndex, true);
	    }
	    
	    function deleteItem($table, selectIndex, reLoad){
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                      return row.sectorid;
                       }); 
	        if(ids.length != 0 ){
	        	if(confirm("真的要删除部门吗?")){
		                $.ajax({
		                   type: "POST",
		                   cache:false,
		                   async : true,
		                   dataType : "json",
		                   url:  "delete.do",
		                   contentType: "application/json", //必须有
		                   data: JSON.stringify({"seids":ids}),
		                   success: function(data, status){
		                           if (data.status == "success"){
		                               $table.bootstrapTable('hideRow', {index:selectIndex});
		                               toastr.success(data.ms);
		                              if(reLoad){
		                                  $table.bootstrapTable('refresh');
		                              }
		                           }else{
		                        	   toastr.warning(data.ms); 
		                           }
		                   },error:function()
		                    {
		                	   toastr.error('删除出错!');
		                    }
		                });
	            }    
	        }else{
	            toastr.warning('请选取要删除的数据行！'); 
	        }
	    }
	   
	</script>
</body>
</html>