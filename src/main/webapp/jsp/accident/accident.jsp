<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>生产故障信息</title>
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
			<li class="active">生产故障</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">生产故障信息</h1>
			</header>
			
			<div class="panel-body" style="padding-bottom: 0px;">
				<div class="panel panel-default">
					<div class="panel-heading">查询条件</div>
					<div class="panel-body">
						<div class="form-group" style="margin-top: 15px">
							<label class="control-label" for="txt_search_project" style="float: left;">项目名称:</label>
							<div class="col-sm-3" style="float: left;">
								<select class="form-control" id="search_project"
									onchange="searchproject()">
									<c:forEach var="p" items="${projects }">
										<option value="${p.projectid}">${p.projectname}</option>
									</c:forEach>
								</select>
							</div>
							
							<label class="control-label" for="txt_search_accstatus" style="float: left;">事故状态:</label>
							<div class="col-sm-2" style="float: left;">
								<select class="form-control" id="search_accstatus" onchange="searchaccstatus()">
									<option value="00">全部</option>
									<option value="已发生-初始状态">已发生-初始状态</option>
									<option value="已发生-跟踪中-未处理">已发生-跟踪中-未处理</option>
									<option value="已发生-跟踪中-处理中">已发生-跟踪中-处理中</option>
									<option value="跟踪处理完成">跟踪处理完成</option>
								</select>
							</div>
							
							<label class="control-label" for="txt_search_date" style="float: left;">发生日期:&nbsp;&nbsp;&nbsp;&nbsp;</label>
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

				<div id="toolbar" class="btn-group">
					<button id="btn_addacc" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;新增故障
					</button>
					<button id="btn_editacc" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;编辑故障
					</button>
					<button id="btn_delacc" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;删除故障
					</button>
					<button id="btn_detacc" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>&nbsp;故障详情
					</button>
					<button id="btn_upacc" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>&nbsp;上传故障附件
					</button>
					<button id="btn_reports" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>&nbsp;故障报表
					</button>
				</div>
				<table id="tb_accident"></table>
				
								<!-- 模态框示例（Modal） -->
				<div class="modal fade" id="reports" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">事故原因饼图统计</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">								
									<div class="form-group">
										<label class="control-label col-sm-3" for="txt_search_project">项目名称:</label>
										<div class="input-group col-md-5">
											<select class="form-control" id="choose_project">
												<c:forEach var="p" items="${projects }">
													<option value="${p.projectid}">${p.projectname}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-sm-3" for="txt_choose_date">日期选择:</label>
										<div class="input-group date form_date col-md-7">
											<input type="text" class="form-control" name="start" id="cBeginTime" readonly /> 
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span> 
 											<span class="input-group-addon">至</span> 
											<input type="text"	class="form-control" name="end" id="cEndTime" readonly />
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
										</div>
									</div>

								  <div class="form-group">
											<label for="clientip" class="col-sm-3 control-label">统计方式</label>
											<div class="input-group col-md-5">
												<select class="form-control" name="reportstype" id="reportstype" onchange="choosereports()">
														<option value="count">按累计次数统计</option>
														<option value="sumimpact">按累计影响时间统计</option>
												</select>
											</div>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button class="btn btn-primary" onclick="goreports()">查看</button>
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
			$('#search_project').val('${projectid }');
			
			$('#qBeginTime').datetimepicker({
				format: 'yyyy-mm-dd',
		        language:  'zh-CN',
			    todayBtn : "linked",  
			    autoclose : true,  
			    todayHighlight : true,
			    forceParse: 0,
			    weekStart: 1,
			    minView: "month",//设置只显示到月份
			    startView: 2,
			    endDate : new Date(),
			    clearBtn:true,
			}).on('changeDate',function(e){  
			    var startTime = e.date;  
			    $('#qEndTime').datetimepicker('setStartDate',startTime);
				//1.初始化Table
				var oTable = new TableInit();
				$('#tb_accident').bootstrapTable('destroy');
				oTable.Init();
			});  
			//结束时间：  
			$('#qEndTime').datetimepicker({
				format: 'yyyy-mm-dd',
		        language:  'zh-CN',
			    todayBtn : "linked",  
			    autoclose : true,  
			    todayHighlight : true,
			    forceParse: 0,
			    minView: "month",//设置只显示到月份
			    weekStart: 1,
			    endDate : new Date(),
			    clearBtn:true,
			}).on('changeDate',function(e){  
			    var endTime = e.date;  
			    $('#qBeginTime').datetimepicker('setEndDate',endTime);
				//1.初始化Table
				var oTable = new TableInit();
				$('#tb_accident').bootstrapTable('destroy');
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
				$('#tb_accident').bootstrapTable({
					url : '/accident/list.do', //请求后台的URL（*）
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
					columns : [{
						checkbox : true,
						width : '3%',
					}, {
						field : 'versionid',
						title : 'versionid',
						visible : false
					},  {
						field : 'projectid',
						title : 'projectid',
						visible : false
					}, {
						field : 'sectorProjects.projectname',
						title : '项目名称',
						width : '10%',
					}, {
						field : 'accstatus',
						title : '事故状态',
						width : '8%',
						formatter : function(value,
								row, index) {
							if(value=='跟踪处理完成'){
							    return '<font style="color:#00DB00">跟踪处理完成</font>';					
							}else if(value=='已发生-初始状态'){
								return '<font style="color:#FFA54F">已发生-初始状态</font>';
							}else{
								return '<font style="color:#95CACA">'+value+'</font>';
							}						
						}
					}, {
						field : 'acclevel',
						title : '事故等级',
						width : '7%',
					}, {
						field : 'eventtime',
						title : '发生时间',
						width : '10%',
					}, {
						field : 'causaltype',
						title : '事故原因类型',
						width : '7%',
					}, {
						field : 'accdescription',
						title : '事故描述',
						width : '45%',
					}, {
						field : 'resolutiontime',
						title : '解决时间',
						width : '10%',
					}],
				});
			};
			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					limit : params.limit, //页面大小
					offset : params.offset, //页码偏移量
					search : params.search, //搜索参数
					projectid : $('#search_project').val(), //项目ID
					accstatus : $('#search_accstatus').val(), //事故状态
					startDate: $('#qBeginTime').val(), //查询日期段
					endDate: $('#qEndTime').val(), //查询日期段
				};
				return temp;
			};

			return oTableInit;
		};
		
		var searchproject = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_accident').bootstrapTable('destroy');
			oTable.Init();
		};

		var searchaccstatus = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_accident').bootstrapTable('destroy');
			oTable.Init();
		};
		
		btn_addacc.onclick = function() {
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

			var url = '/accident/add.do';
			window.location.href = url;
		}
		
		btn_editacc.onclick=function(){
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
         
            var id = $.map($('#tb_accident').bootstrapTable('getSelections'), function (row) {
                return row.id;
                 });
            if(id.length == 1 ){
    			var url = '/accident/update.do?id='+id;
    			window.location.href=url;
            }else{
            	toastr.warning('您有且只能选择一条故障记录哦！'); 
            }
	    }
		
		btn_delacc.onclick=function(){
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
	        deleteItem($('#tb_accident'), selectIndex, true);
	    }
	    
	    function deleteItem($table, selectIndex, reLoad){
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                      return row.id;
                       }); 
	        if(ids.length != 0 ){
	        	if(confirm("真的要删除选择的故障记录吗?")){
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
	            toastr.warning('请选取要删除的故障记录！');
	        }
	    }
	    
		btn_detacc.onclick=function(){
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
         
            var id = $.map($('#tb_accident').bootstrapTable('getSelections'), function (row) {
                return row.id;
                 });
            if(id.length == 1 ){
    			var url = '/accident/show.do?id='+id;
    			window.location.href=url;
            }else{
            	toastr.warning('您有且只能选择一条故障记录哦！'); 
            }
	    }
		
		btn_upacc.onclick=function(){
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
         
            var id = $.map($('#tb_accident').bootstrapTable('getSelections'), function (row) {
                return row.id;
                 });
            
            var accstatus = $.map($('#tb_accident').bootstrapTable('getSelections'), function (row) {
                return row.accstatus;
                 });
            
            if(id.length == 1 ){
            	if(accstatus!='跟踪处理完成'){
            		toastr.warning('只有【跟踪处理完成】状态的故障记录才能上传附件！'); 
            		return false;
            	}
    			var url = '/accident/to_upload.do?id='+id;
    			window.location.href=url;
            }else{
            	toastr.warning('您有且只能选择一条故障记录哦！'); 
            }
	    }
		
		btn_reports.onclick = function() {
			$('#cBeginTime').datetimepicker({
				format: 'yyyy-mm-dd',
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
			    $('#cEndTime').datetimepicker('setStartDate',startTime);
			});  
			//结束时间：  
			$('#cEndTime').datetimepicker({
				format: 'yyyy-mm-dd',
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
			    $('#cBeginTime').datetimepicker('setEndDate',endTime);
			});
			
			$('#cBeginTime').datetimepicker('setDate',new Date(new Date()-7*24*60*60*1000));
			$('#cEndTime').datetimepicker('setDate',new Date(new Date()-1000));			
			
			$("#reports").modal('show');
		}
		
		function goreports() {
			var reportstype=$('#reportstype').val();
			var startdate=$('#cBeginTime').val();
			var enddate=$('#cEndTime').val();
			var projectid=$('#choose_project').val();
			var url = '/accident/piechart_html5.do?pie_startdate='+startdate+' 00:00:00&pie_enddate='+enddate+' 23:59:59&projectid='+projectid+'&type='+reportstype;
			window.location.href=url;
		}
	</script>
</body>
</html>