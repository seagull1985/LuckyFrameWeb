<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>项目流程检查详情</title>
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
			<li class="active"><a href="/flowCheck/load.do">流程检查信息</a></li>
			<li class="active">流程检查详情</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">${projectname }第${checkid }次流程检查详情</h1>
			</header>
			
			<div class="panel-body" style="padding-bottom: 0px;">

				<div id="toolbar" class="btn-group">
					<button id="btn_addcheck" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;增加检查
					</button>
					<button id="btn_editcheck" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;编辑检查
					</button>
					<button id="btn_delcheck" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;删除检查
					</button>
				</div>
				<table id="tb_flowcheckinfo"></table>
				
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
									<h4 class="modal-title" id="myModalLabel">增加检查项</h4>
								</div>
								<div class="modal-body">
									<form class="form-horizontal" role="form">
									<input name="checkid" id="checkid" value="${checkid }" type="hidden"  />
									<input name="projectid" id="projectid" value="${projectid }" type="hidden"  />
									<input name="versionnum" id="versionnum" value="${versionnum }" type="hidden"  />
										<div class="form-group">
											<label for="projectphase" class="col-sm-3 control-label">项目检查阶段</label>
											<div class="col-sm-9">
												<select class="form-control" name="projectphase" id="projectphase" onChange="getArea('node')" onFocus="getArea('node')">
	   	                                          <option value="0">请选择项目阶段...</option>
                                                   <c:forEach var="p" items="${phaselist }">
	                                                 <option value="${p[0]}">${p[1]}</option>
	                                               </c:forEach>
												</select>
											</div>
										</div>
										
										<div class="form-group">
											<label for="phasenode" class="col-sm-3 control-label">项目检查节点</label>
											<div class="col-sm-9">
												<select class="form-control" name="phasenode" id="phasenode" onChange="getArea('entry')" onFocus="getArea('entry')">
												   <option value="0">请选择阶段节点...</option>
												</select>
											</div>
										</div>

										<div class="form-group">
											<label for="checkentry" class="col-sm-3 control-label">项目检查内容</label>
											<div class="col-sm-9">
												<select class="form-control" name="checkentry" id="checkentry">
												  <option value="0">请选择检查内容...</option>
												</select>
											</div>
										</div>
										
										<div class="form-group">
											<label for="checkresult" class="col-sm-3 control-label">检查结果</label>
											<div class="col-sm-9">
												<select class="form-control" name="checkresult" id="checkresult">
                                                      	   <option value="通过">通过</option>
   	                                                       <option value="未通过">未通过</option>
												</select>
											</div>
										</div>
										
									<div class="form-group">
										<label for="checkdate" class="col-md-3 control-label">检查日期</label>
										<div class="col-sm-9">
										<div class="input-group date form_date col-sm-5">
											<input name="checkdate" id="checkdate" class="form-control" type="text" readonly>
					                        <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar"></span></span>
										</div>
										</div>
									</div>
									
									<div class="form-group">
											<label for="checkdescriptions" class="col-sm-3 control-label">检查描述</label>
											<div class="col-sm-9">
												<textarea class="form-control" name="checkdescriptions" id="checkdescriptions"
													placeholder="检查描述"></textarea>
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
									<button type="submit" class="btn btn-primary" disabled="disabled">添加</button>
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
				$('#tb_flowcheckinfo').bootstrapTable({
					url : '/flowCheck/listinfo.do?projectid=${projectid }&checkid=${checkid }&versionnum=${versionnum }', //请求后台的URL（*）
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
						field : 'projectphase',
						title : '项目阶段',
						width : '10%',
					}, {
						field : 'phasenode',
						title : '阶段节点',
						width : '10%',
					}, {
						field : 'checkentry',
						title : '检查内容',
						width : '20%',
					}, {
						field : 'checkresult',
						title : '检查结果',
						width : '5%',
					}, {
						field : 'checkdate',
						title : '检查日期',
						width : '7%',
					}, {
						field : 'stateupdate',
						title : '状态更新',
						width : '7%',
					}, {
						field : 'updatedate',
						title : '更新日期',
						width : '5%',
					},{
						field : 'checkdescriptions',
						title : '检查描述',
						width : '18%',
					},{
						field : 'remark',
						title : '备注',
						width : '18%',
					}],
				});
			};

			return oTableInit;
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
			
			$("#checkdate").datetimepicker({
				format: 'yyyy-mm-dd',
		        language:  'zh-CN',
		        startDate : new Date(new Date()-365*24*60*60*1000),
		        endDate : new Date(),
		        weekStart: 1,
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				minView: 2,
				forceParse: 0
		    });
			
			$('#checkdate').datetimepicker('setDate',new Date(new Date()-1000));
			$("#addModal").modal('show');
		}
		
		btn_editcheck.onclick=function(){
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
         
            var id = $.map($('#tb_flowcheckinfo').bootstrapTable('getSelections'), function (row) {
                return row.id;
                 });
            if(id.length == 1 ){
    			var url = '/flowCheck/update.do?id='+id;
    			window.location.href=url;
            }else{
            	toastr.warning('您有且只能选择一条检查记录哦！'); 
            }
	    }
		
		btn_delcheck.onclick=function(){
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
	        deleteItem($('#tb_flowcheckinfo'), selectIndex, true);
	    }
	    
	    function deleteItem($table, selectIndex, reLoad){
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                      return row.id;
                       }); 
	        if(ids.length != 0 ){
	        	if(confirm("真的要删除选择的检查记录吗?")){
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
	            toastr.warning('请选取要删除的检查记录！');
	        }
	    }
	    
		//按上级ID取子列表
		function getArea(name){
			if( name=='node' ){
		   clearSel(); //清空节点	    
		   if(jQuery("#projectphase").val() == "") return;
		   var phaseid = jQuery("#projectphase").val();
		   
		    var url ="/flowCheck/getcheckinfo.do?phaseId="+phaseid+"&nodeId=0";
		    jQuery.getJSON(url,null,function call(result){
		   	 clearSel();
		   	 setNode(result); 
		     });
		    
			    if(jQuery("#phasenode").val() == "") return;
			    var nodeid = jQuery("#phasenode").val();
			    
			     var url ="/flowCheck/getcheckinfo.do?nodeId="+nodeid+"&phaseId="+phaseid;
			      jQuery.getJSON(url,null,function call(result){	    	  
			    	  clearSel();
			    	  setEntry(result); 
			       });

		   }else if(name=='entry'){
//		      clearSel(document.getElementById("entry")); //清空内容
		   if(jQuery("#projectphase").val() == "") return;
		   var phaseid = jQuery("#projectphase").val();
			 
		   if(jQuery("#phasenode").val() == "") return;
		   var nodeid = jQuery("#phasenode").val();
		   
		    var url ="/flowCheck/getcheckinfo.do?nodeId="+nodeid+"&phaseId="+phaseid;
		     jQuery.getJSON(url,null,function call(result){	    	  
		   	  clearSel();
		   	  setEntry(result); 
		      });
		     
		  }

		 }		

		 //当改变阶段时设置节点
		function setNode(result){	    
			   var options = "";
		  jQuery.each(result.data, function(i, node){
			  options +=  "<option value='"+node[0]+"'>"+node[1]+"</option>";
		     }); 
		     jQuery("#phasenode").html(options);
		   }
		 
		//当改变节点时设置检查内容
		function setEntry(result){  
		  var options = "";
		  jQuery.each(result.data, function(i, entry){
			  options +=  "<option value='"+entry[0]+"'>"+entry[1]+"</option>";  
		    }); 
		  jQuery("#checkentry").html(options);
		  }
		  
		// 清空下拉列表
		function clearSel(){  
		 $("#projectphase option[value='0']").remove();
		 while(jQuery("#checkentry").length>1){
			  $("#checkentry option[index='1']").remove();
//			 document.getElementById("checkentry").options.remove("1"); 
		   }
		  }
		
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
							projectphase : {
								message : '检查阶段无效！',
								validators : {
									notEmpty : {
										message : '检查阶段不能为空'
									},
									callback: {
										message: '必须选择检查阶段',
										callback: function(value, validator) {
										if (value == 0) {
										  return false;
										} else {
									      return true;
										}
									 }
                                   }
								}
							},
							phasenode : {
								message : '检查节点无效！',
								validators : {
									notEmpty : {
										message : '检查节点不能为空'
									},
									callback: {
										message: '必须选择检查节点',
										callback: function(value, validator) {
										if (value == 0) {
										  return false;
										} else {
									      return true;
										}
									 }
                                   }
								}
							},
							checkentry : {
								message : '检查内容无效！',
								validators : {
									notEmpty : {
										message : '检查内容不能为空'
									},
									callback: {
										message: '必须选择检查内容',
										callback: function(value, validator) {
										if (value == 0) {
										  return false;
										} else {
									      return true;
										}
									 }
                                   }
								}
							},
							checkdate : {
								message : '检查日期无效！',
								validators : {
									notEmpty : {
										message : '检查日期不能为空'
									}
								}
							},
							checkdescriptions : {
								message : '检查描述无效！',
								validators : {
									notEmpty : {
										message : '检查描述不能为空'
									},
									stringLength : {
										min : 2,
										max : 200,
										message : '检查描述长度必须在2~200个字符区间'
									}
								}
							},
							remark : {
								message : '备注无效！',
								validators : {
									stringLength : {
										min : 0,
										max : 100,
										message : '模板备注长度必须小于100个字符'
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
								$form.find('.alert').html('检查创建成功！');
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
	                    url: "adddetail.do",
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
	                            $("#tip").html("<span style='color:blueviolet'>恭喜，添加检查成功！</span>");
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
	               /*          $('#addModal').hide(); */
	                    }
	                });

	        return false;
	    }
	    
	    $(function () { $('#addModal').on('hide.bs.modal', function () {
	    	// 关闭时清空edit状态为add
	    	location.reload();
	    })
	    });
	</script>
</body>
</html>