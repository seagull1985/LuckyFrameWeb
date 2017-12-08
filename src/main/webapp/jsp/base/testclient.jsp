<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>客户端管理</title>
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
			<li class="active">客户端管理</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align:center;">客户端管理</h1>
			</header>
			
			<div class="panel-body" style="padding-bottom: 0px;">

				<div id="toolbar" class="btn-group">
					<button id="btn_add" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增客户端
					</button>
					<button id="btn_update" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑客户端
					</button>
					<button id="btn_delete" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除客户端
					</button>
				</div>
				<table id="tb_testclient"></table>

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
									<h4 class="modal-title" id="myModalLabel">客户端信息</h4>
								</div>
								<div class="modal-body" style="height:400px; overflow:scroll;">
									<form class="form-horizontal" role="form">
									<input name="id" id="id" value="0" type="hidden"/>									
										<div class="form-group">
											<label for="name" class="col-sm-3 control-label">客户端名称</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" name="name"
													id="name" placeholder="客户端名称">
											</div>
										</div>
										<div class="form-group">
											<label for="clientip" class="col-sm-3 control-label">客户端IP</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" name="clientip"
													id="clientip" placeholder="客户端IP">
											</div>
										</div>
										<div class="form-group">
											<label for="clientip" class="col-sm-12 control-label" style="color:blue">
											客户端根目录下存放驱动桩JAR的路径,多个路径以;分隔 </label>
										</div>
										<div class="form-group">
											<label for="clientip" class="col-sm-3 control-label">驱动桩路径</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" name="clientpath"
													id="clientpath" placeholder="默认示例/TestDriven 多个路径以;分隔 " value="/TestDriven">
											</div>
										</div>
										<div class="form-group">
											<label for="checkinterval" class="col-sm-3 control-label">心跳间隔</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" name="checkinterval"
													id="checkinterval" placeholder="单位:秒">
											</div>
										</div>
										<div class="form-group">
											<label for="projectper" class="col-sm-3 control-label">使用项目</label>
											<div class="col-sm-9">
									         <c:forEach items="${projectlist}" var="t">
									          <label style="float: left; margin-top: 8px; cursor: pointer;">${t.projectname}&nbsp;&nbsp;&nbsp;&nbsp;
									          <input type="checkbox" class="form-control" style="width: 20px; margin-top: -1px; height: 20px; float: left; cursor: pointer;" name="projectper" id="projectper${t.projectid}" value="${t.projectid}" >
								             </label>
								             </c:forEach>
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
									<button type="submit" class="btn btn-primary" disabled="disabled">提交</button>
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
				$('#tb_testclient').bootstrapTable({
					url : '/testClient/list.do', //请求后台的URL（*）
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
					uniqueId : "id", //每一行的唯一标识，一般为主键列
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
						field : 'projectper',
						title : '使用项目',
						visible : false
					}, {
						field : 'name',
						title : '客户端名称',
						width : '10%',
					}, {
						field : 'clientip',
						title : '客户端IP',
						width : '8%',
					}, {
						field : 'clientpath',
						title : '驱动桩路径',
						width : '10%',
					}, {
						field : 'projectpername',
						title : '使用项目',
						width : '43%',
					},  {
						field : 'checkinterval',
						title : '心跳间隔',
						width : '6%',
						align : 'center',
						formatter : function(value,
								row, index) {
							return value+" 秒";
						}
					},  {
						field : 'status',
						title : '客户端状态',
						width : '8%',
						align : 'center',
						formatter : function(value,
								row, index) {
							if(value==0){
								return '<span class="glyphicon glyphicon-ok-sign" style="color: rgb(0, 175, 0);"> 状态正常</span>';
							}else if(value==1){
								return '<span class="glyphicon glyphicon-minus-sign" style="color: rgb(199, 0, 0);"> 状态异常</span>';
							}else{
								return '<span class="glyphicon glyphicon-question-sign" style="color: rgb(255, 168, 0); font-size: 14px;"> 状态未知</span>';
							}
						}
					}, {
						field : 'remark',
						title : '备注',
						width : '12%',
					}],
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
							name : {
								message : '客户端名称无效！',
								validators : {
									notEmpty : {
										message : '客户端名称不能为空'
									},
									stringLength : {
										min : 2,
										max : 20,
										message : '客户端名称长度必须在2~20个字符区间'
									}
								}
							},
							clientip : {
								message : '客户端IP无效！',
								validators : {
									notEmpty : {
										message : '客户端IP不能为空'
									},
									ip : {
										message : '客户端IP格式验证错误'
									}
								}
							},
							clientpath : {
								message : '驱动桩路径无效！',
								validators : {
									notEmpty : {
										message : '驱动桩路径不能为空'
									},
									stringLength : {
										min : 2,
										max : 100,
										message : '驱动桩路径长度必须在2~100个字符区间'
									}
								}
							},
							checkinterval : {
								message : '心跳间隔时间无效！',
								validators : {
									notEmpty : {
										message : '心跳间隔时间不能为空'
									},
									numeric: {message: '心跳间隔时间只能输入数字'},
				                    callback: {  
				                         message: '心跳间隔时间最大59秒',  
				                         callback: function(value, validator) {
				                        	 if(value>59){
						                            return false; 
				                        	 }else{
				                        		 return true;
				                        	 }
				                         }  
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
								$form.find('.alert').html('客户端创建成功！');
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
	                            $("#tip").html("<span style='color:blueviolet'>"+data.ms+"</span>");
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
	                       /*  $('#addModal').hide(); */
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
	    
	    btn_update.onclick=function(){
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
			
            var row = $.map($('#tb_testclient').bootstrapTable('getSelections'), function (row) {
                return row;
                 });
            
            if(row.length == 1 ){
            	$("#id").val(row[0].id);
            	$("#name").val(row[0].name);
            	$("#clientip").val(row[0].clientip);
            	$("#clientpath").val(row[0].clientpath);
            	$("#checkinterval").val(row[0].checkinterval);
            	$("#remark").val(row[0].remark);
            	
            	var projectpers=row[0].projectper.split(",");
            	for (i=0;i<projectpers.length-1 ;i++ ) 
            	{
            		$("#projectper"+projectpers[i]).attr('checked', true);
            	}
            	$("#addModal").modal('show');
            }else{
            	toastr.warning('要编辑客户端有且只能选择一条记录哦！'); 
            }
	    }
	    
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
	        deleteItem($('#tb_testclient'), selectIndex, true);
	    }
	    
	    function deleteItem($table, selectIndex, reLoad){
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                      return row.id;
                       }); 
	        if(ids.length != 0 ){
	        	if(confirm("真的要删除客户端吗?")){
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