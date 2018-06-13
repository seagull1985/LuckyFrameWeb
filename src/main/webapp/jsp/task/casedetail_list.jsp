<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>用例执行列表</title>
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
			<h1 class="page-title" style="text-align: center;">用例日志查询</h1>
			</header>
			
			<div class="panel-body" style="padding-bottom: 0px;">
				<div class="panel panel-default">
					<div class="panel-heading">查询条件</div>
					<div class="panel-body">
						<div class="form-group" style="margin-top: 15px">
							
							<div class="col-md-12">
							<label class="control-label" for="txt_search_case" style="float: left;">用例查询条件:</label>
							<div class="select-group  col-md-2">
								<select class="form-control" id="search_pro"
									onchange="searchproject()">
									<c:forEach var="p" items="${projects }">
										<option value="${p.projectid}">${p.projectname}</option>
									</c:forEach>
								</select>
								</div>
								
                                <div class="input-group date form_date col-md-3" id="datepicker" style="float: left;">  
                                  <input type="text" class="form-control" name="start" id="qBeginTime" readonly/>
                                  <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar"></span></span>
                                  <span class="input-group-addon">至</span>  
                                  <input type="text" class="form-control" name="end" id="qEndTime" readonly/>
                                  <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            
							<div class="select-group col-md-3" style="float: left;">
								<select class="form-control" id="search_task"  onchange="searchtask()">
									<c:forEach var="t" items="${tasks }">
										<option value="${t[0]}">${t[1]}</option>
									</c:forEach>
								</select>
								</div>
							
							
							<div class="select-group col-md-1" style="float: left;">
								<select class="form-control" id="search_status"	onchange="searchstatus()">
                                   <option value="">全部</option>
                                   <option value="0">成功</option>
                                   <option value="1">失败</option>
                                   <option value="2">锁定</option>
                                   <option value="4">未执行</option>
								</select>
								</div>
							</div>
							</div>
							
						</div>
					</div>
				</div>

				<div id="toolbar" class="btn-group">
					<button id="btn_run" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-play" aria-hidden="true"></span>执行所选用例
					</button>
					<button id="btn_allrun" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-forward" aria-hidden="true"></span>执行全部非成功用例
					</button>
				</div>
				<table id="tb_testcase"></table>

			</div>
			</article>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#search_pro').val('${projectid }');
			$('#search_status').val('${status }');
			$('#search_task').val('${taskid }');
			
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
			    searchproject();
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
			    searchproject();
			});
	
			$('#qBeginTime').datetimepicker('setDate',new Date(Date.parse('${date }'.replace(/-/g,   "/"))));
			$('#qEndTime').datetimepicker('setDate',new Date(Date.parse('${date }'.replace(/-/g,   "/"))));
			
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_testcase').bootstrapTable({
									url : '/caseDetail/list.do', //请求后台的URL（*）
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
									columns : [
											{
												checkbox : true,
												width : '5%',
												formatter : function(value,
														row, index) {
													if(row.casestatus==0){
										                return {  
										                    disabled : "disabled",//设置是否可用
										                }; 
													}
 
												}
											},
											{
												field : 'id',
												title : 'id',
												visible : false
											},
											{
												field : 'caseno',
												title : '用例编号',
												width : '10%',
											},
											{
												field : 'casename',
												title : '用例名称',
												width : '65%',
											},
											{
												field : 'casetime',
												title : '执行时间',
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
												field : 'casestatus_str',
												title : '用例状态',
												width : '10%',
												formatter : function(value,
														row, index) {
													if(row.casestatus==0){
														return '<font style="color:#00bf5f">'+value+'</font>';
													}else if(row.casestatus==1){
														return '<font style="color:#ff0000">'+value+'</font>';
													}else{
														return '<font style="color:#FF7F00">'+value+'</font>';
													}
												}
											}],
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
					projectid : $('#search_pro').val(), //项目ID
					startDate: $('#qBeginTime').val(), //查询日期段
					endDate: $('#qEndTime').val(), //查询日期段
					status: $('#search_status').val(), //查询状态
					taskId: $('#search_task').val(), //查询状态
				};
				return temp;
			};
			
			//初始化子表格(无线循环)
			oTableInit.InitSubTable = function(index, row, $detail) {
				var caseid = row.id;
				var cur_table = $detail.html('<table></table>').find('table');
				$(cur_table).bootstrapTable({
					url : '/logDetail/list.do',
					method : 'get',
					queryParams : {
						caseId : caseid,
					},
					ajaxOptions : {
						caseId : caseid,
					},
					clickToSelect : false,
					detailView : false,//父子表
					uniqueId : "logid",
					columns : [{
						field : 'logid',
						title : '序号',
						width : '3%',
						formatter : function(value, row, index) {  
							return '<p align="center">'+(index+1)+'</p>';  
				         } 
					},  
					{
						field : 'step',
						title : '步骤编号',
						width : '4%',
						formatter : function(value,
								row, index) {
							if(value!="ending"){
								return "步骤_"+value;
							}else{
								return '<font style="color:#00bf5f">'+value+'</font>';
							}
							
						}
					}, {
						field : 'detail',
						title : '日志打印',
						width : '75%',
						cellStyle : function (value, row, index, field) {
							if(row.logGrade!="info"){
								  return {
									    css: {"color": "#ff0000"}
									  };
							 }else{
								  return {
									    css: {"color": "#00bf5f"}
									  };
							 }
							}
					}, {
						field : 'logtime',
						title : '打印时间',
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
					}, {
						field : 'logGrade',
						title : '日志级别',
						width : '8%',
						formatter : function(value,
								row, index) {
							if(row.imgname==""){
								if(value!="info"){
									if(row.detail.indexOf('测试结果：')>0){
										return '<font style="color:#ff0000">'+value+'</font>&nbsp;&nbsp;&nbsp;&nbsp;'+
										'<a href="#" onclick="updateStep('+row.logid+')">同步结果</a> ';
									}else{
									    return '<font style="color:#ff0000">'+value+'</font>';
									}
								}else{
									return '<font style="color:#00bf5f">'+value+'</font>';
								}								
							}else{
								return '<font style="color:#ff0000">'+value+'</font>&nbsp;&nbsp;&nbsp;&nbsp;'+
								'<a href="javascript:window.open(\'/logDetail/showImage.do?filename='+ row.imgname+ '.png&logid='+row.logid+'\')">错误截图</a> ';
							}
						}
					}, ],
					//无线循环取子表，直到子表里面没有记录
					onExpandRow : function(index, row, $Subdetail) {
						oInit.InitSubTable(index, row, $Subdetail);
					},

				});

			};

			return oTableInit;
		};

		var searchproject = function() {
			var startDate= $('#qBeginTime').val();
			var endDate= $('#qEndTime').val();
			var projid=$('#search_pro').val();
			var taskName=document.getElementById('search_task');
			var aaa=null;
			$.ajax({
				type : "post",
				dataType:"json",
				contentType:"application/x-www-form-urlencoded:charset=UTF-8",
				async:false, //关闭异步，使用同步机制
				url:encodeURI("/caseDetail/getTastNameList.do?startDate="+startDate+"&projid="+projid+"&endDate="+endDate),
				success:function(json){
					for(var i=taskName.options.length;i>=0;i--)
					{
						taskName.options.remove(i);   
					}
					
					if(json!=""){
						for(var i=0;i<json.length;i++)
						{		
		                        var option = new Option(json[i][1],json[i][0]);    
								taskName.options.add(option); 
						} 
					}else{
							var option = new Option("没有任务","99999999");    
							taskName.options.add(option); 
					}

				},
				error:function(){
					toastr.error('请稍后再试!');
				}
				
			});

			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_testcase').bootstrapTable('destroy');
			oTable.Init();			
		};
		
		var searchstatus = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_testcase').bootstrapTable('destroy');
			oTable.Init();
		};
		
		var searchtask = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_testcase').bootstrapTable('destroy');
			oTable.Init();
		};
		
		btn_run.onclick=function(){
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
	        selecteItem($('#tb_testcase'), selectIndex, true);
	    }
	    
	    function selecteItem($table, selectIndex, reLoad){
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                      return row.id;
                       }); 
            console.log(ids);
	        if(ids.length != 0 ){
	        	if(confirm("真的要执行所选用例吗?")){
		                $.ajax({
		                   type: "POST",
		                   cache:false,
		                   async : true,
		                   dataType : "json",
		                   url:  "runCase.do",
		                   contentType: "application/json", //必须有
		                   data: JSON.stringify({"caseids":ids}),
		                   success: function(data, status){
		                           if (data.status == "success"){
		                               toastr.success(data.ms);
		                              if(reLoad){
		                                  $table.bootstrapTable('refresh');
		                              }
		                           }else{
		                        	   toastr.info(data.ms);
		                           }
		                   },error:function()
		                    {
		                        toastr.error('执行用例出错啦！');
		                    }
		                });
	            }    
	        }else{
	            toastr.warning('请选取要执行的用例！'); 
	        }
	    }
	    
	    btn_allrun.onclick=function(){
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
			
        	if(confirm("真的要执行全部非成功用例吗?")){
                $.ajax({
                   type: "POST",
                   cache:false,
                   async : true,
                   dataType : "json",
                   url:  "runCase.do",
                   contentType: "application/json", //必须有
                   data: JSON.stringify({"caseids":["ALLFAIL", $('#search_task').val()]}),
                   success: function(data, status){
                           if (data.status == "success"){
                               toastr.success(data.ms);
                              if(reLoad){
                                  $table.bootstrapTable('refresh');
                              }
                           }else{
                        	   toastr.info(data.ms);
                           }
                   },error:function()
                    {
                        toastr.error('执行用例出错啦！');
                    }
                });
        }  
	    }
	    
	    function updateStep(logid){
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
			
        	if(confirm("你确定要把此步骤【测试结果】更新到【用例步骤中的预期结果】吗?")){
                $.ajax({
                   type: "POST",
                   cache:false,
                   async : true,
                   dataType : "json",
                   url:  "/logDetail/updateStepResult.do",
                   contentType: "application/json", //必须有
                   data: JSON.stringify({"logid":logid}),
                   success: function(data, status){
                           if (data.status == "success"){
                               toastr.success(data.ms);
                              if(reLoad){
                                  $table.bootstrapTable('refresh');
                              }
                           }else{
                        	   toastr.info(data.ms);
                           }
                   },error:function()
                    {
                        toastr.error('更新用例预期结果出错啦！');
                    }
                });
        }  
	    }
	    
	    function add0(m){return m<10?'0'+m:m }
	</script>
</body>
</html>