<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>系统管理</title>
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
			<li class="active">用户管理</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">用户管理</h1>
			</header>

			<div class="panel-body" style="padding-bottom: 0px;">

				<div id="toolbar" class="btn-group">
					<button id="btn_adduser" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;添加用户
					</button>
					<button id="btn_addrole" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-heart-empty" aria-hidden="true"></span>&nbsp;添加角色
					</button>
					<button id="btn_authority" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>&nbsp;权限管理
					</button>
					<button id="btn_client" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>&nbsp;客户端管理
					</button>
					<button id="btn_project" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-list" aria-hidden="true"></span>&nbsp;项目管理
					</button>
					<button id="btn_dpmt" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>&nbsp;部门管理
					</button>
					<button id="btn_olog" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>&nbsp;操作日志
					</button>
				</div>
				<table id="tb_user"></table>

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
				$('#tb_user')
						.bootstrapTable(
								{
									url : '/userInfo/list.do', //请求后台的URL（*）
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
												field : 'username',
												title : '姓名',
												width : '10%',
											},
											{
												field : 'usercode',
												title : '用户名',
												width : '10%',
											},
											{
												field : 'role',
												title : '角色',
												width : '50%',
											},
											{
												field : 'secondarySector.departmentname',
												title : '所属部门',
												width : '10%',
											},
											{
												field : 'projectname',
												title : '默认项目',
												width : '10%',
											},
											{
												field : 'id',
												title : '操作',
												width : '10%',
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
													return e +'&nbsp;&nbsp;&nbsp;&nbsp;'+ d;
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
				};
				return temp;
			};

			return oTableInit;
		};

		btn_adduser.onclick = function() {
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

			var url = '/userInfo/add.do';
			window.location.href = url;
		}

		btn_addrole.onclick = function() {
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

			var url = '/userInfo/roleadd.do';
			window.location.href = url;
		}
		
		btn_authority.onclick = function() {
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

			var url = '/userInfo/role.do';
			window.location.href = url;
		}

		btn_client.onclick = function() {
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

			var url = '/testClient/load.do';
			window.location.href = url;
		}
		
		btn_project.onclick = function() {
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

			var url = '/sectorProjects/load.do';
			window.location.href = url;
		}
		
		btn_dpmt.onclick = function() {
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

			var url = '/secondarySector/load.do';
			window.location.href = url;
		}
		
		btn_olog.onclick = function() {
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

			var url = '/operationLog/load.do';
			window.location.href = url;
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

			if (confirm("真的要删除此用户吗?")) {
				$.ajax({
					type : "POST",
					cache : false,
					async : true,
					dataType : "json",
					url : "delete.do",
					data : {
						"userid" : id
					},
					success : function(data, status) {
						if (data.status == "success") {
							$('#tb_user').bootstrapTable('hideRow', {
								index : selectIndex
							});
							toastr.success(data.ms);
							if (reLoad) {
								$('#tb_user').bootstrapTable('refresh');
							}
						} else {
							toastr.info(data.ms);
						}
					},
					error : function() {
						toastr.error('删除出错!');
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

			var url = '/userInfo/update.do?id=' + id;
			window.location.href = url;
		}
	</script>
</body>
</html>