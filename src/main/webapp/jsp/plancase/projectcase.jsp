<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>用例管理</title>
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
			<li class="active">用例管理</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">用例管理</h1>
			</header>

			<div class="panel-body" style="padding-bottom: 0px;">
				<div class="panel panel-default">
					<div class="panel-heading">查询条件</div>
					<div class="panel-body">
						<div class="form-group" style="margin-top: 15px">
							<label class="control-label col-sm-1" style="width:6%" for="txt_search_project">项目名称:</label>
							<div class="col-sm-3">
								<select class="form-control" id="search_project"
									onchange="searchproject()" onFocus="searchproject()">
									<c:forEach var="p" items="${projects }">
										<option value="${p.projectid}">${p.projectname}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group" style="margin-top: 15px">
							<label class="control-label col-sm-1" style="width:6%" for="txt_search_module">用例集:</label>
							<div class="col-sm-3">
								<select class="form-control" id="search_module" onchange="searchmodule()" >
                                      <option value="0">全部</option>
								</select>
							</div>
						</div>

					</div>
				</div>

				<div id="toolbar" class="btn-group">
					<button id="btn_add" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;新增用例
					</button>
					<button id="btn_delete" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;删除用例
					</button>
					<button id="btn_edit" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;编辑步骤
					</button>
					<button id="btn_addmodule" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>&nbsp;增加用例集
					</button>
				</div>
				<table id="tb_projectcase"></table>

				<!-- 模态框增加用例（Modal） -->
				<form method="post" action="" class="form-horizontal" role="form"
					id="form_data" onsubmit="return check_form()" style="margin: 20px;">
					<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">用例信息</h4>
								</div>
								<div class="modal-body">
									<form class="form-horizontal" role="form">
										<div class="form-group">
											<label for="projectid" class="col-sm-3 control-label">项目名称</label>
											<div class="col-sm-9">
												<select class="form-control" name="projectid" id="projectid"
													onchange="getModule(1)" onFocus="getModule(1)">
													<c:forEach var="p" items="${projects }">
														<option value="${p.projectid}">${p.projectname}</option>
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="form-group">
											<label for="moduleid" class="col-sm-3 control-label">用例集</label>
											<div class="col-sm-9">
												<select class="form-control" name="moduleid" id="moduleid">
													<option value="0">全部</option>
												</select>
											</div>
										</div>

										<div class="form-group">
											<label for="name" class="col-sm-3 control-label">用例名称</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" name="name"
													id="name" placeholder="用例名称">
											</div>
										</div>
										<div class="form-group">
											<label for="casetype" class="col-sm-3 control-label">用例类型</label>
											<div class="col-sm-9">
												<input type="radio" class="form-control"
													style="width: 25px; height: 25px; float: left; cursor: pointer;"
													name="casetype" id="casetype" value="0" checked="checked" />
												<label
													style="float: left; margin-top: 8px; cursor: pointer;"
													for="casetype">
													&nbsp;&nbsp;接口自动化&nbsp;&nbsp;&nbsp;&nbsp; </label> <input
													type="radio" class="form-control"
													style="width: 25px; height: 25px; float: left; cursor: pointer;"
													name="casetype" id="casetype" value="1" /> <label
													style="float: left; margin-top: 8px; cursor: pointer;"
													for="casetype">&nbsp;&nbsp;UI自动化</label>
											</div>
										</div>

										<div class="form-group">
											<label for="remark" class="col-sm-3 control-label">备注</label>
											<div class="col-sm-9">
												<textarea class="form-control" name="remark" id="remark"
													placeholder="备注">

                                </textarea>
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


				<!-- 增加测试用例集 -->
				<form method="post" action="" class="form-horizontal" role="form"
					id="Module_data" onsubmit="return check_moduleform()"
					style="margin: 20px;">
					<div class="modal fade" id="addModule" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">用例集信息</h4>
								</div>
								<div class="modal-body">
									<form class="form-horizontal" role="form">
										<div class="form-group">
											<label for="projectid" class="col-sm-3 control-label">项目名称</label>
											<div class="col-sm-9">
												<select class="form-control" name="mprojectid"
													id="mprojectid">
													<c:forEach var="p" items="${projects }">
														<option value="${p.projectid}">${p.projectname}</option>
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="form-group">
											<label for="modulename" class="col-sm-3 control-label">用例集名称</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" name="modulename"
													id="modulename" placeholder="用例集名称">
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
			$("#projectid option[value='99']").remove();
			$("#mprojectid option[value='99']").remove();
			$('#search_project').val('${projectid }');
			if(${projectid }!=99){
				$('#projectid').val('${projectid }');
				$('#mprojectid').val('${projectid }');
				getModule(0);
			}
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_projectcase').bootstrapTable({
					url : '/projectCase/list.do', //请求后台的URL（*）
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
					detailView : true, //是否显示父子表
					columns : [ {
						checkbox : true
					}, {
						field : 'id',
						title : 'id',
						visible : false
					}, {
						field : 'projectid',
						title : 'projectid',
						visible : false
					}, {
						field : 'projectindex',
						title : 'projectindex',
						visible : false
					}, {
						field : 'projectname',
						title : '项目名称',
						width : '10%',
					}, {
						field : 'sign',
						title : '用例编号',
						width : '6%',
					}, {
						field : 'name',
						title : '用例名称',
						width : '30%',
						editable : {
							type : 'text',
							title : '用例名称',
							emptytext : "无用例名称",
							validate : function(value) {
								if (!$.trim(value))
									return '用例名称不能为空';
								if (value.length > 200)
									return '用例名称不能超过200个字符';
								if (value.length < 2)
									return '用例名称不能适于2个字符';
							}
						}
					}, {
						field : 'casetype',
						title : '用例类型',
						width : '10%',
						editable : {
							type : 'select',
							title : '用例类型',
							source : [ {
								value : "0",
								text : "接口自动化"
							}, {
								value : "1",
								text : "Web自动化"
							} ]
						}
					}, {
						field : 'time',
						title : '更新时间',
						width : '10%',
					}, {
						field : 'modulename',
						title : '所属模块',
						width : '9%',
					}, {
						field : 'operationer',
						title : '更新人员',
						width : '5%',
					}, {
						field : 'remark',
						title : '备注',
						width : '20%',
						editable : {
							type : 'textarea',
							title : '备注',
							emptytext : "无备注",
							validate : function(value) {
								if (value.length > 200)
									return '备注不能超过200个字符';
							}
						}
					} ],
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
						
						$('#tb_projectcase').bootstrapTable("resetView");
						$.ajax({
							type : "post",
							url : "/projectCase/update.do",
							data : row,
							dataType : 'JSON',
							success : function(data, status) {
								if (data.status == "success") {
									alert(data.ms);
								}else{
									$('#tb_projectcase').bootstrapTable('refresh');
									alert(data.ms);									
								}
							},
							error : function(data, status) {
								alert("编辑失败!");
							},
							complete : function() {

							}

						});
					},
					//注册加载子表的事件。注意下这里的三个参数！
					onExpandRow : function(index, row, $detail) {
						oTableInit.InitSubTable(index, row, $detail);
					}
				});
			};
			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					limit : params.limit, //页面大小
					offset : params.offset, //页码偏移量
					search : params.search, //搜索参数
					projectid : $('#search_project').val(), //项目ID
					moduleid : $('#search_module').val(), //模块ID
				};
				return temp;
			};

			//初始化子表格(无线循环)
			oTableInit.InitSubTable = function(index, row, $detail) {
				var parentid = row.id;
				var cur_table = $detail.html('<table></table>').find('table');
				$(cur_table).bootstrapTable({
					url : '/projectCasesteps/list.do',
					method : 'get',
					queryParams : {
						strParentID : parentid
					},
					ajaxOptions : {
						strParentID : parentid
					},
					clickToSelect : true,
					detailView : false,//父子表
					uniqueId : "ID",
					pageSize : 10,
					pageList : [ 10, 25 ],
					columns : [ {
						checkbox : true,
						visible : false
					}, {
						field : 'stepnum',
						title : '步骤编号'
					}, {
						field : 'path',
						title : '包路径|定位路径',
						editable : {
							type : 'text',
							title : '包路径|定位路径',
							validate : function(value) {
								if (!$.trim(value))
									return '包路径|定位路径不能为空';
								if (value.length > 100)
									return '包路径|定位路径不能超过100个字符';
								if (value.length < 2)
									return '包路径|定位路径不能适于2个字符';
							}
						}
					}, {
						field : 'operation',
						title : '方法名|操作',
						editable : {
							type : 'text',
							title : '包路径|定位路径',
							validate : function(value) {
								if (!$.trim(value))
									return '包路径|定位路径不能为空';
								if (value.length > 100)
									return '包路径|定位路径不能超过100个字符';
								if (value.length < 2)
									return '包路径|定位路径不能适于2个字符';
							}
						}
					}, {
						field : 'parameters',
						title : '参数',
						editable : {
							type : 'text',
							title : '包路径|定位路径',
							validate : function(value) {
								if (!$.trim(value))
									return '包路径|定位路径不能为空';
								if (value.length > 500)
									return '包路径|定位路径不能超过500个字符';
								if (value.length < 2)
									return '包路径|定位路径不能适于2个字符';
							}
						}
					}, {
						field : 'action',
						title : '步骤动作',
						editable : {
							type : 'text',
							title : '包路径|定位路径',
							validate : function(value) {
								if (!$.trim(value))
									return '包路径|定位路径不能为空';
								if (value.length > 50)
									return '包路径|定位路径不能超过50个字符';
								if (value.length < 2)
									return '包路径|定位路径不能适于2个字符';
							}
						}
					}, {
						field : 'expectedresult',
						title : '预期结果',
						editable : {
							type : 'text',
							title : '包路径|定位路径',
							validate : function(value) {
								if (!$.trim(value))
									return '包路径|定位路径不能为空';
								if (value.length > 200)
									return '包路径|定位路径不能超过200个字符';
								if (value.length < 2)
									return '包路径|定位路径不能适于2个字符';
							}
						}
					}, {
						field : 'steptype',
						title : '步骤类型',
						editable : {
							type : 'select',
							title : '步骤类型',
							source : [ {
								value : "0",
								text : "接口自动化"
							}, {
								value : "1",
								text : "Web自动化"
							} ]
						}
					}, {
						field : 'time',
						title : '更新时间'
					}, {
						field : 'operationer',
						title : '更新人员'
					}, {
						field : 'remark',
						title : '备注',
						editable : {
							type : 'text',
							title : '包路径|定位路径',
							validate : function(value) {
								if (!$.trim(value))
									return '包路径|定位路径不能为空';
								if (value.length > 200)
									return '包路径|定位路径不能超过200个字符';
								if (value.length < 2)
									return '包路径|定位路径不能适于2个字符';
							}
						}
					}, {
						field : 'id',
						title : 'id',
						visible : false
					}, {
						field : 'projectid',
						title : '项目ID',
						visible : false
					}, {
						field : 'caseid',
						title : '用例ID',
						visible : false
					}, ],
					//无线循环取子表，直到子表里面没有记录
					onExpandRow : function(index, row, $Subdetail) {
						oInit.InitSubTable(index, row, $Subdetail);
					},

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
						
						$('#cur_table').bootstrapTable("resetView");
						$.ajax({
							type : "post",
							url : "/projectCasesteps/update.do",
							data : row,
							dataType : 'JSON',
							success : function(data, status) {
								if (data.status == "success") {
									alert(data.ms);
								}else{
									$('#cur_table').bootstrapTable('refresh');
									alert(data.ms);									
								}
							},
							error : function() {
								alert('编辑失败');
							},
							complete : function() {

							}

						});
					},
				});

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
							name : {
								message : '用例名称无效！',
								validators : {
									notEmpty : {
										message : '用例名称不能为空'
									},
									stringLength : {
										min : 2,
										max : 200,
										message : '用例名称长度必须在2~200个字符区间'
									}
								}
							},
							remark : {
								message : '用例备注无效！',
								validators : {
									stringLength : {
										min : 0,
										max : 200,
										message : '用例备注长度必须小于200个字符'
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
								$form.find('.alert').html('用例创建成功！');
							});
				});

		var searchproject = function() {
			getModule(0);
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_projectcase').bootstrapTable('destroy');
			oTable.Init();
		};

		var searchmodule = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_projectcase').bootstrapTable('destroy');
			oTable.Init();
		};
		
	    // 提交表单
	    function check_form()
	    {
	        var form_data = $('#form_data').serialize();
	        $.param(form_data); 
	        // 异步提交数据到action页面
	        $.ajax(
	                {
	                    url: "caseadd.do",
	                    data:form_data,
	                    type: "post",
	                    dataType : 'JSON',
	                    beforeSend:function()
	                    {
	                        $("#tip").html("<span style='color:blue'>正在处理...</span>");
	                        return true;
	                    },
	                    success:function(data, status)
	                    {
	                        if(data.status == "success")
	                        {
	                            $("#tip").html("<span style='color:blueviolet'>恭喜，添加用例成功！</span>");
	                            // document.location.href='system_notice.php'
	                            alert(data.ms);
	                            location.reload();
	                        }else{
	                            $("#tip").html("<span style='color:red'>失败，请重试</span>");
	                            alert(data.ms);
	                            location.reload();
	                        }
	                    },
	                    error:function()
	                    {
	                        alert('请求出错');
	                        location.reload();
	                    },
	                    complete:function()
	                    {
	                        $('#addModal').hide();
	                    }
	                });

	        return false;
	    }
	    
	    // 提交表单
	    function check_moduleform()
	    {
	        var form_data = $('#Module_data').serialize();
	        $.param(form_data); 
	        // 异步提交数据到action页面
	        $.ajax(
	                {
	                    url: "moduleadd.do",
	                    data:form_data,
	                    type: "post",
	                    dataType : 'JSON',
	                    beforeSend:function()
	                    {
	                        $("#tip").html("<span style='color:blue'>正在处理...</span>");
	                        return true;
	                    },
	                    success:function(data, status)
	                    {
	                        if(data.status == "success")
	                        {
	                            $("#tip").html("<span style='color:blueviolet'>恭喜，添加用例集成功！</span>");
	                            // document.location.href='system_notice.php'
	                            alert(data.ms);
	                            location.reload();
	                        }else{
	                            $("#tip").html("<span style='color:red'>失败，请重试</span>");
	                            alert(data.ms);
	                            location.reload();
	                        }
	                    },
	                    error:function()
	                    {
	                        alert('请求出错');
	                        location.reload();
	                    },
	                    complete:function()
	                    {
	                        $('#addModule').hide();
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
			
			getModule(1);
	    	$("#addModal").modal('show');
	    }
	    
	    btn_addmodule.onclick=function(){
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
	    	$("#addModule").modal('show');
	    }
	    
	    $(function () { $('#addModal').on('hide.bs.modal', function () {
	        // 关闭时清空edit状态为add
	        location.reload();
	    })
	    });	    
	    
	    $(function () { $('#addModule').on('hide.bs.modal', function () {
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
	        deleteItem($('#tb_projectcase'), selectIndex, true);
	    }
	    
	    function deleteItem($table, selectIndex, reLoad){
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                      return row.id;
                       }); 
	        if(ids.length != 0 ){
	        	if(confirm("真的要删除用例吗?")){
		                $.ajax({
		                   type: "POST",
		                   cache:false,
		                   async : true,
		                   dataType : "json",
		                   url:  "delete.do",
		                   contentType: "application/json", //必须有
		                   data: JSON.stringify({"caseids":ids}),
		                   success: function(data, status){
		                           if (data.status == "success"){
		                               $table.bootstrapTable('hideRow', {index:selectIndex});
		                               alert(data.ms);
		                              if(reLoad){
		                                  $table.bootstrapTable('refresh');
		                              }
		                           }else{
		                        	   alert(data.ms);
		                           }
		                   },error:function()
		                    {
		                        alert('删除出错');
		                    }
		                });
	            }    
	        }else{
	            alert('请选取要删除的数据行！');
	        }
	    }
	    
	    btn_edit.onclick=function(){
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
            var ids = $.map($('#tb_projectcase').bootstrapTable('getSelections'), function (row) {
                return row.id;
                 });
            if(ids.length == 1 ){
    			var url = '/projectCasesteps/stepadd.do?caseid='+ids;
    			window.location.href=url;
            }else{
            	alert('要进行用例编辑只能选择一条用例哦！');
            }

	    }

	    //按上级ID取子列表
		 function getModule(type){
//		    clearSel(); //清空节点
            var projectid;
            if(type==0){
            	if(jQuery("#search_project").val() == ""||jQuery("#search_project").val() == "99") return;
            	var projectid = jQuery("#search_project").val();
            }else if(type==1){
    		    if(jQuery("#projectid").val() == ""||jQuery("#projectid").val() == "99") return;
    		    var projectid = jQuery("#projectid").val();
            }else{
            	return;
            }
		     var url ="/projectCase/getmodulelist.do?projectid="+projectid;
		     jQuery.getJSON(url,null,function call(result){
		    	 clearSel(type);
		    	 setPlan(result,type); 
		      });
	    
		    }
	    
		  //设置子列表
		 function setPlan(result,type){	    
	  	   var options = "";
	  	   options +=  "<option value='0'>全部</option>";
		   jQuery.each(result.data, function(i, node){
			  options +=  "<option value='"+node.id+"'>"+node.modulename+"</option>";
		      }); 
			  if(type==0){
		  	      console.log(options);
				  jQuery("#search_module").html(options);
			  }else{
				  jQuery("#moduleid").html(options);
			  }		      
		    }
		  
		 // 清空下拉列表
	     function clearSel(type){  
	    	if(type==0){
		  while(jQuery("#search_module").length>1){
			  $("#search_module option[index='1']").remove();
		//	 document.getElementById("checkentry").options.remove("1"); 
		    }
	    	}else{
	  		  while(jQuery("#moduleid").length>1){
				  $("#moduleid option[index='1']").remove();
			//	 document.getElementById("checkentry").options.remove("1"); 
			    }
	    	}
		   }
	</script>
</body>
</html>