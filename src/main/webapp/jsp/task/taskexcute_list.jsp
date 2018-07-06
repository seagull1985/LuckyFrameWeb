<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务执行列表</title>
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
			<li class="active">UTP</li>
			<li class="active">任务查询</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">任务执行列表</h1>
			</header>
			
			<div class="panel-body" style="padding-bottom: 0px;">
				<div class="panel panel-default">
					<div class="panel-heading">查询条件</div>
					<div class="panel-body">
						<div class="form-group" style="margin-top: 15px">
							
							<div class="col-md-12">
							<label class="control-label" for="txt_search_job" style="float: left;">调度名称:</label>
							<div class="select-group  col-md-3">
								<select class="form-control" id="search_job"
									onchange="searchjob()">
									<option value="0">全部调度</option>
									<c:forEach var="job" items="${jobs }">
										<option value="${job[0]}">【${job[2]}】—${job[1]}</option>
									</c:forEach>
								</select>
								</div>
								
						     <label class="control-label" for="txt_search_job" style="float: left;">日期段:&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <div class="input-group date form_date col-md-3" id="datepicker" style="float: left;">  
                                  <input type="text" class="form-control" name="start" id="qBeginTime" readonly/>
                                  <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar"></span></span>
                                  <span class="input-group-addon">至</span>  
                                  <input type="text" class="form-control" name="end" id="qEndTime" readonly/>
                                  <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                                
                            <label class="control-label" for="txt_search_job" style="float: left;">&nbsp;&nbsp;&nbsp;&nbsp;任务状态:</label>
							<div class="select-group col-md-2" style="float: left;">
								<select class="form-control" id="search_status"	onchange="searchstatus()">
                                  <option value="">全部</option>
                                  <option value="0">未执行</option>
                                  <option value="1">执行中</option>
                                  <option value="2">执行成功</option>
                                  <option value="3">调起失败|超时</option>
								</select>
								</div>
							</div>
							
							
						</div>
					</div>
				</div>

				<div id="toolbar" class="btn-group">
					<button id="btn_del" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除任务
					</button>
				</div>
				<table id="tb_testexcute"></table>
				
			<div id="delModal" class="modal fade" data-keyboard="false"
                data-backdrop="static" data-role="dialog"
                      aria-labelledby="myModalLabel" aria-hidden="true">
            <div id="loading" class="loading">删除中,请稍候...</div>
        </div>

			</div>
			</article>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#search_job').val('${jobid }');
			
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
			}).on('changeDate',function(e){  
			    var startTime = e.date;  
			    $('#qEndTime').datetimepicker('setStartDate',startTime);
				//1.初始化Table
				var oTable = new TableInit();
				$('#tb_testexcute').bootstrapTable('destroy');
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
			    endDate : new Date()
			}).on('changeDate',function(e){  
			    var endTime = e.date;  
			    $('#qBeginTime').datetimepicker('setEndDate',endTime);
				//1.初始化Table
				var oTable = new TableInit();
				$('#tb_testexcute').bootstrapTable('destroy');
				oTable.Init();
			});
			
			$('#qBeginTime').datetimepicker('setDate',new Date(new Date()-7*24*60*60*1000));
			$('#qEndTime').datetimepicker('setDate',new Date(new Date()-1000));
			
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_testexcute')
						.bootstrapTable(
								{
									url : '/tastExecute/list.do', //请求后台的URL（*）
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
												checkbox : true,
												width : '3%',
											},
											{
												field : 'id',
												title : 'id',
												visible : false
											},
											{
												field : 'taskId',
												title : '任务名称',
												width : '25%',
												formatter : function(value,
														row, index) {
													return '<a href="/caseDetail/load.do?taskId='
															+ row.id
															+ '">'
															+ value + '</a> ';
												}
											},
											{
												field : 'testJob.planproj',
												title : '项目名称',
												width : '13%',
											},
											{
												field : 'createTime',
												title : '开始时间',
												width : '10%',
												formatter : function(value,
														row, index) {
													var now = new Date(value);
												    var year=now.getFullYear();    
												    var month=now.getMonth()+1;    
												    var date=now.getDate();    
												    var hour=now.getHours();    
												    var minute=now.getMinutes();    
												    var second=now.getSeconds();
												    var time=year+'-'+add0(month)+'-'+add0(date)+' '+add0(hour)+':'+add0(minute)+':'+add0(second);
												    return time;
												}
											},
											{
												field : 'finishtime',
												title : '结束时间',
												width : '10%',
												formatter : function(value,
														row, index) {
													var time='等待任务结束...';
													if(value != undefined){
														var now = new Date(value);
													    var year=now.getFullYear();    
													    var month=now.getMonth()+1;    
													    var date=now.getDate();    
													    var hour=now.getHours();    
													    var minute=now.getMinutes();    
													    var second=now.getSeconds();    
													    var time=year+'-'+add0(month)+'-'+add0(date)+' '+add0(hour)+':'+add0(minute)+':'+add0(second);
													}									
													return '<font id="finishtime' + row.id + '">'+time+'</font>';
												}
											},
											{
												field : 'taskStatus',
												title : '运行状态',
												width : '15%',
												align : 'center',
												formatter : function(value,
														row, index) {
													if(value==1||value==0){
												 	 	var hdiv = '<div class="progress progress-striped active progress-bar-warning" style="margin-bottom:0px">'+
														'<div id="progress'+row.id+'" class="progress-bar progress-bar-success" aria-valuemax="100"'+ 
														'aria-valuemin="0" aria-valuenow="0" style="width:0%;text-align:center">0%</div></div>';
														refreshProgress(row.id);
													    return hdiv;					
													}else{
														return row.taskStatus_str;
													}
													
												}
											},
											{
												field : 'casetotalCount',
												title : '总用例数',
												width : '5%',
												formatter : function(value,
														row, index) {
													return '<a href="/caseDetail/load.do?taskId='
															+ row.id
															+ '" id="casetotal'+row.id+'">'
															+ value + '</a> ';
												}
											},
											{
												field : 'casesuccCount',
												title : '成功',
												width : '5%',
												formatter : function(value,
														row, index) {
													return '<a href="/caseDetail/load.do?taskId='+ row.id+ '&status=0" id="casesucc'+row.id+'" style="color:#00bf5f">'	+ value + '</a> ';
												}
											},
											{
												field : 'casefailCount',
												title : '失败',
												width : '5%',
												formatter : function(value,
														row, index) {
													return '<a href="/caseDetail/load.do?taskId='+ row.id+ '&status=1" id="casefail'+row.id+'" style="color:#ff0000">'	+ value + '</a> ';
												}
											},
											{
												field : 'caselockCount',
												title : '锁定',
												width : '5%',
												formatter : function(value,
														row, index) {
													return '<a href="/caseDetail/load.do?taskId='+ row.id+ '&status=2" id="caselock'+row.id+'" style="color:#FF7F00">'	+ value + '</a> ';
												}
											},
											{
												field : 'casenoexecCount',
												title : '未执行',
												width : '5%',
												formatter : function(value,
														row, index) {
													return '<a href="/caseDetail/load.do?taskId='+ row.id+ '&status=4" id="casenoexec'+row.id+'">'	+ value + '</a> ';
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
					jobid : $('#search_job').val(), //项目ID
					startDate: $('#qBeginTime').val(), //查询日期段
					endDate: $('#qEndTime').val(), //查询日期段
					status: $('#search_status').val(), //查询状态
				};
				return temp;
			};

			return oTableInit;
		};

		var searchjob = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_testexcute').bootstrapTable('destroy');
			oTable.Init();
		};
		
		var searchstatus = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_testexcute').bootstrapTable('destroy');
			oTable.Init();
		};
		
	 	function refreshProgress(id){
		 	 	var url ="/tastExecute/progressdata.do?id="+id;
 		 		$.ajax({
		 			type:"GET",
		 			url:url,
		 			cache:false,
		 			dataType:"json",
		 			success:function (result){
		 		    	if(result.data[1]!=null&&result.data[1]<=100&&result.data[1]>0){
		 		    		document.getElementById("casetotal"+id).innerText = result.data[7];
		 		    		$('#progress'+id).css('width', result.data[1]+'%');
		 		    		document.getElementById("progress"+id).innerText  = result.data[1]+'%';
		 		    		$('#progress'+id).attr("aria-valuenow",result.data[1]);
		 		    	}
		 		    	if(result.data[1]==100){
		 		    		document.getElementById("finishtime"+id).innerText = result.data[2];
		 		    		document.getElementById("casesucc"+id).innerText = result.data[3];
		 		    		document.getElementById("casefail"+id).innerText = result.data[4];
		 		    		document.getElementById("caselock"+id).innerText = result.data[5];
		 		    		document.getElementById("casenoexec"+id).innerText = result.data[6];
		 		    		
		 		    		if(result.data[2]!=null){
		 		    			clearTimeout(t);
		 		    		}	 		    		
	 		    		}
		 			}
		 			});
		 		    var t=setTimeout("refreshProgress("+id+")", 3000);
		} 
	 	
	    btn_del.onclick=function(){
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
	        deleteItem($('#tb_testexcute'), selectIndex, true);
	    }
	    
	    function deleteItem($table, selectIndex, reLoad){
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                      return row.id;
                       }); 
	        if(ids.length != 0 ){
	        	if(confirm("真的要删除选择的任务吗?")){
	        		$('#delModal').modal('show');
		                $.ajax({
		                   type: "POST",
		                   cache:false,
		                   async : true,
		                   dataType : "json",
		                   url:  "delete.do",
		                   contentType: "application/json", //必须有
		                   data: JSON.stringify({"taskids":ids}),
		                   success: function(data, status){
		                           if (data.status == "success"){
		                               toastr.success(data.ms);
		                        	   $('#delModal').modal('hide');
		                               $table.bootstrapTable('hideRow', {index:selectIndex});
		                              if(reLoad){
		                                  $table.bootstrapTable('refresh');
		                              }
		                           }else{
		                        	   $('#delModal').modal('hide');
		                        	   toastr.info(data.ms);
		                           }
		                   },error:function()
		                    {
		                	   $('#myModal').modal('hide');
		                	   toastr.error('删除出错!');
		                    }
		                });
	            }    
	        }else{
	        	toastr.warning('请选取要删除的任务！'); 
	        }
	    }
	    
	    function add0(m){return m<10?'0'+m:m }
	</script>
</body>
</html>