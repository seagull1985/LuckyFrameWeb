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
							<label class="control-label col-sm-1"
								for="txt_search_project">项目名称:</label>
							<div class="col-sm-3">
								<select class="form-control" id="search_project"
									onchange="searchproject()">
									<c:forEach var="p" items="${projects }">
										<option value="${p.projectid}">${p.projectname}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group" style="margin-top: 15px">
							<label class="control-label col-sm-1"
								for="txt_search_module">用例集:</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="module_tree"
									placeholder="点击选择或编辑用例集" onclick="showMenu()" /> <input
									type="hidden" id="search_module" value="0" />
								<div id="menuContent2" class="menuContent"
									style="display: none; position: absolute; width: 95%; border: 1px solid rgb(170, 170, 170); z-index: 10; background-color: rgba(51, 204, 255, 0.8);">
									<ul id="treeDemo" class="ztree"
										style="margin-top: 0; width: 160px; height: auto;"></ul>
								</div>

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
					<button id="btn_copy" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-share" aria-hidden="true"></span>&nbsp;复制用例
					</button>
					<button id="btn_edit" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;编辑步骤
					</button>
					<button id="btn_params" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-globe" aria-hidden="true"></span>&nbsp;公共参数
					</button>
				</div>
				<table id="tb_projectcase"></table>
				
				<!--增加用例模态框 -->
				<form method="post" action="" class="form-horizontal" role="form"
					id="form_data" onsubmit="return check_form()" style="margin: 20px;">
					<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="modellabelh">用例信息</h4>
								</div>
								<div class="modal-body">
									<form class="form-horizontal" role="form">
									<input name="id" id="id" value="0" type="hidden"/>
										<div class="form-group">
											<label for="projectid" class="col-sm-3 control-label">项目名称</label>
											<div class="col-sm-9">
												<select class="form-control" name="projectid" id="projectid"
													onchange="clearModule()">
													<c:forEach var="p" items="${projects }">
														<option value="${p.projectid}">${p.projectname}</option>
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="form-group">
											<label for="moduleid" class="col-sm-3 control-label">测试集</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" id="modulename"
													name="modulename" placeholder="点击选择用例集"
													onclick="showMenuAddCase()" /><input class="form-control"
													type="hidden" name="moduleid" id="moduleid" />
												<div id="menuContent" class="menuContent"
													style="display: none; position: absolute; width: 90%; border: 1px solid rgb(170, 170, 170); z-index: 10; background-color: rgba(51, 204, 255, 0.8);">
													<ul id="treeDemo1" class="ztree"
														style="margin-top: 0; width: 80px; height: auto;"></ul>
												</div>
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
													for="casetype">&nbsp;&nbsp;Web自动化&nbsp;&nbsp;&nbsp;&nbsp;</label><input
													type="radio" class="form-control"
													style="width: 25px; height: 25px; float: left; cursor: pointer;"
													name="casetype" id="casetype" value="4" /> <label
													style="float: left; margin-top: 8px; cursor: pointer;"
													for="casetype">&nbsp;&nbsp;移动端自动化</label>
											</div>
										</div>

										<div class="form-group">
											<label for="remark" class="col-sm-3 control-label">备注</label>
											<div class="col-sm-9">
												<textarea class="form-control" name="remark" id="remark"
													placeholder="备注"></textarea>
											</div>
										</div>
									</form>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">关闭</button>
									<button type="submit" id="submit" class="btn btn-primary" disabled="disabled">提交</button>
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
		//alert(genJsonConfig());
		$(function() {
			$("#projectid option[value='99']").remove();
			$('#search_project').val('${projectid }');
			if(${projectid}!=99){
				$('#projectid').val('${projectid }');
				$.fn.zTree.init($("#treeDemo"), genJsonConfig());
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
									return '用例名称不能小于2个字符';
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
							}, {
								value : "4",
								text : "移动端自动化"
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
									 toastr.success(data.ms);
								}else{
									$('#tb_projectcase').bootstrapTable('refresh');
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
							emptytext : "【包路径|定位路径】为空",
							validate : function(value) {
								if (value.length > 200)
									return '包路径|定位路径不能超过200个字符';
//								if (value.length < 2)
//									return '包路径|定位路径不能小于2个字符';
							}
						}
					}, {
						field : 'operation',
						title : '方法名|操作',
						editable : {
							type : 'text',
							title : '方法名|操作',
							emptytext : "【方法名|操作】为空",
							validate : function(value) {
								if (value.length > 100)
									return '方法名|操作不能超过100个字符';
								if (value.length < 2)
									return '方法名|操作不能小于2个字符';
							}
						}
					}, {
						field : 'parameters',
						title : '参数',
						editable : {
							type : 'text',
							title : '参数',
							emptytext : "【参数】为空",
							validate : function(value) {
								if (value.length > 500)
									return '参数不能超过500个字符';
//								if (value.length < 2)
//									return '参数不能小于2个字符';
							}
						}
					}, {
						field : 'action',
						title : '步骤动作',
						editable : {
							type : 'text',
							title : '步骤动作',
							emptytext : "【步骤动作】为空",
							validate : function(value) {
								if (value.length > 50)
									return '步骤动作不能超过50个字符';
//								if (value.length < 2)
//									return '步骤动作不能小于2个字符';
							}
						}
					}, {
						field : 'expectedresult',
						title : '预期结果',
						formatter : function(value,
								row, index) {
								return value+' ';
						},
						editable : {
							type : 'text',
							title : '预期结果',
							emptytext : "【预期结果】为空",
							validate : function(value) {
								if (value.length > 2000)
									return '预期结果不能超过2000个字符';
//								if (value.length < 2)
//									return '预期结果不能小于2个字符';
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
							}, {
								value : "2",
								text : "HTTP"
							}, {
								value : "3",
								text : "SOCKET"
							}, {
								value : "4",
								text : "移动端自动化"
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
							title : '备注',
							emptytext : "【备注】为空",
							validate : function(value) {
								if (value.length > 200)
									return '备注不能超过200个字符';
//								if (value.length < 2)
//									return '备注不能小于2个字符';
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
						
 						if(field=='expectedresult'){
 							var str=row.expectedresult.split("");
							if(str[str.length-1]==' '){
								row.expectedresult=row.expectedresult.substring(0,row.expectedresult.length-1);
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
									toastr.success(data.ms);
								}else{
									$('#cur_table').bootstrapTable('refresh');
									toastr.info(data.ms);									
								}
							},
							error : function() {
								 toastr.error('编辑失败!');
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
			//1.初始化Table
			$("#module_tree").val('');
			$('#search_module').val('0');
			$.fn.zTree.init($("#treeDemo"), genJsonConfig());
			var oTable = new TableInit();
			$('#tb_projectcase').bootstrapTable('destroy');
			oTable.Init();
		};
		
	    // 提交表单
	    function check_form()
	    {
	    	$('#form_data').data('bootstrapValidator').validate();  
	    	  if(!$('#form_data').data('bootstrapValidator').isValid()){  
	    		 return ;  
	    	  } 
	    	  
	        var form_data = $('#form_data').serialize();
	        $.param(form_data);

	        var id=$('#id').val();
	        
		        // 异步提交数据到action页面
		        $.ajax(
		                {
		                    url: "caseadd.do?copyid="+id,
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
		                            $("#submit").attr("disabled",false);
		                            toastr.success(data.ms);
		                        }else{
		                            $("#tip").html("<span style='color:red'>失败，请重试</span>");
		                            toastr.info(data.ms);
		                        }
		                    },
		                    error:function()
		                    {
		                    	toastr.error('添加用例出错!');
		                    },
		                    complete:function()
		                    {
		                       /*  $('#addModal').hide(); */
		                    }
		                });

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
			
			if($('#search_project').val()!=99){
				$('#projectid').val($('#search_project').val());
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = treeObj.getSelectedNodes();
				if(nodes.length>0){
					$("#modulename").val(nodes[0].name);
					$("#moduleid").val(nodes[0].id);
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
	        	toastr.warning('请选取要删除的任务！'); 
	        }
	    }
	    
	    btn_copy.onclick=function(){
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

            var row = $.map($('#tb_projectcase').bootstrapTable('getSelections'), function (row) {
                return row;
                 });

            if(row.length == 1 ){
	        	if(confirm("确定要复制此条用例吗?")){
	        		$("#projectid").val(row[0].projectid);
	        		$("#modulename").val(row[0].modulename);
	        		$("#moduleid").val(row[0].moduleid);        		
	        		$("#name").val("COPY "+row[0].name);
	        		$("input[id='casetype'][value='"+row[0].casetype+"']").attr("checked",true);
	        		$("#remark").val(row[0].remark);
	        		$("#id").val(row[0].id);
	        		$("#addModal").modal('show');
            }
            }else{
            	toastr.warning('要复制用例有且只能选择一条记录哦！'); 
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
            	toastr.warning('要进行步骤编辑有且选择一条用例哦！'); 
            }

	    }
	    
	    btn_params.onclick=function(){
    		var url = '/publicCaseParams/load.do';
    		window.location.href=url;
	    }
	//=========================================================================================		
		function genJsonConfig(){
			var setting = {
					async: {
						enable: true,
						url:"/projectCase/getmodulelist.do",
						autoParam:["id", "name=n", "level=lv"],
						otherParam:{"projectid":$('#search_project').val()},
						dataFilter: filter
					},
					view: {expandSpeed:"",
						addHoverDom: addHoverDom,
						removeHoverDom: removeHoverDom,
						selectedMulti: false
					},
					edit: {
						enable: true
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					callback: {
						beforeRemove: beforeRemove,
						beforeRename: beforeRename,
						beforeClick: beforeClick
					}
				}; 
			
			return setting;
		}

		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		
		function beforeClick(treeId, treeNode) {
			$("#module_tree").val(treeNode.name);
			$("#search_module").val(treeNode.id);
			var oTable = new TableInit();
			$('#tb_projectcase').bootstrapTable('destroy');
			oTable.Init();
			hideMenu();
		}
		
		function beforeRemove(treeId, treeNode) {
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
			
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			if(null==treeNode.pId){
				$.fn.zTree.init($("#treeDemo"), genJsonConfig());
				 toastr.warning('项目名称不能删除!'); 
				 return false;
			}
			
			if(confirm("确认删除 测试集 -- " + treeNode.name + " 吗？")){
				var dataSend = JSON.stringify({"id":treeNode.id});
                $.ajax({
	                   type: "POST",
	                   cache:false,
	                   async : true,
	                   dataType : "json",
	                   url:  "moduledel.do",
	                   data: dataSend,
	                   success: function(data, status){
	                           if (data.status == "success"){
	                        	   toastr.success(data.ms);
	                           }else{
	                        	   $.fn.zTree.init($("#treeDemo"), genJsonConfig());
	                        	   toastr.info(data.ms);
	                           }
	                   },error:function()
	                    {
	                	    $.fn.zTree.init($("#treeDemo"), genJsonConfig());
	                	    toastr.error('删除出错!');
	                    }
	                });
			}else{
				$.fn.zTree.init($("#treeDemo"), genJsonConfig());
			}
		}
		
		function beforeRename(treeId, treeNode, newName) {
			if (newName.length == 0) {
				setTimeout(function() {
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.cancelEditName();
					toastr.warning('节点名称不能为空！');
				}, 0);
				return false;
			}else{
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
				
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.selectNode(treeNode);
				if(null==treeNode.pId){
					$.fn.zTree.init($("#treeDemo"), genJsonConfig());
					 toastr.warning('项目名称不能编辑！');
					 return false;
				}
				var dataSend = JSON.stringify({"id":treeNode.id,"oldName":treeNode.name,"pid":treeNode.pId,"name":newName,"projectid":$('#search_project').val()});
                $.ajax({
	                   type: "POST",
	                   cache:false,
	                   async : true,
	                   dataType : "json",
	                   url:  "moduleadd.do",
	                   data: dataSend,
	                   success: function(data, status){
	                           if (data.status == "success"){
	                        	   treeNode.editName(newName);
/* 	                        	   $.fn.zTree.destroy("treeDemo");
	                        	   $.fn.zTree.init($("#treeDemo"), genJsonConfig()); */
	                        	   toastr.success(data.ms);
	                           }else{
	                        	   $.fn.zTree.init($("#treeDemo"), genJsonConfig());
	                        	   toastr.info(data.ms);
	                           }
	                   },error:function()
	                    {
	                	   $.fn.zTree.init($("#treeDemo"), genJsonConfig());
	                        toastr.error('保存出错!');
	                    }
	                });
			}
			return true;
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var dataSend = JSON.stringify({"id":0,"oldName":"新建测试集节点","pid":treeNode.id,"name":"新建测试集节点","projectid":$('#search_project').val()});
                $.ajax({
	                   type: "POST",
	                   cache:false,
	                   async : true,
	                   dataType : "json",
	                   url:  "moduleadd.do",
	                   data: dataSend,
	                   success: function(data, status){
	                           if (data.status == "success"){
	               				   zTree.addNodes(treeNode, {id:data.id, pId:treeNode.id, name:"新建测试集节点"});
	                        	   toastr.success(data.ms);
	                           }else{
	                        	   $.fn.zTree.init($("#treeDemo"), genJsonConfig());
	                        	   toastr.info(data.ms);
	                           }
	                   },error:function()
	                    {
	                	   $.fn.zTree.init($("#treeDemo"), genJsonConfig());
	                        toastr.error('保存出错!');
	                    }
	                });
				return false;
			});
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		

		//显示菜单
		function showMenu() {
		    $("#menuContent2").css({ left: "15px", top: "34px" }).slideDown("fast");
		    $("body").bind("mousedown", onBodyDown);
		}
		//隐藏菜单
		function hideMenu() {
		    $("#menuContent2").fadeOut("fast");
		    $("body").unbind("mousedown", onBodyDown);
		}
		
		function onBodyDown(event) {
		    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent2" || event.target.id == "module_tree" || $(event.target).parents("#menuContent2").length > 0)) {
		        hideMenu();
		    }
		}
		
		//========================新增用例 module树=================================================================		
		function genJsonConfigAddCase(){
			var setting = {
					async: {
						enable: true,
						url:"/projectCase/getmodulelist.do",
						autoParam:["id", "name=n", "level=lv"],
						otherParam:{"projectid":$('#projectid').val()},
						dataFilter: filterAddCase
					},
					view: {expandSpeed:"",
						selectedMulti: false
					},
					edit: {
						enable: false
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					callback: {
						beforeClick: beforeClickAddCase
					}
				}; 
			
			return setting;
		}

		function filterAddCase(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		
		function beforeClickAddCase(treeId, treeNode) {
			$("#modulename").val(treeNode.name);
			$("#moduleid").val(treeNode.id);
			hideMenuAddCase();
		}		

		//显示菜单
		function showMenuAddCase() {
		    $("#menuContent").css({ left: "15px", top: "34px" }).slideDown("fast");
		    $("body").bind("mousedown", onBodyDown);
		    $.fn.zTree.init($("#treeDemo1"), genJsonConfigAddCase());
		}
		//隐藏菜单
		function hideMenuAddCase() {
		    $("#menuContent").fadeOut("fast");
		    $("body").unbind("mousedown", onBodyDown);
		}
		
		function onBodyDownAddCase(event) {
		    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || event.target.id == "modulename" || $(event.target).parents("#menuContent").length > 0)) {
		        hideMenu();
		    }
		}
		
		function clearModule(){
			$("#modulename").val("");
			$("#moduleid").val("");
		}
	</script>
</body>
</html>