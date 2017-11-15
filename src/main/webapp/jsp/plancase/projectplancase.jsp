<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>计划用例管理</title>
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
			<li class="active"><a href="/projectPlan/load.do">测试计划</a></li>
			<li class="active">计划用例管理</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">添加用例到计划【${planname}】</h1>
			</header>

			<div class="panel-body" style="padding-bottom: 0px;">
				<div class="panel panel-default">
					<div class="panel-heading">查询条件</div>
					<div class="panel-body">
						<div class="form-group" style="margin-top: 15px">
							<label class="control-label col-sm-1" style="width: 6%"
								for="txt_search_module">用例集:</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="module_tree"
									placeholder="点击选择用例集" onclick="showMenu()" />
								<input type="hidden" id="search_module" value="0"/>
								<div id="menuContent2" class="menuContent"
									style="display: none; position: absolute; width: 95%; border: 1px solid rgb(170, 170, 170); z-index: 10;background-color:rgba(51,204,255,0.8);">
									<ul id="treeDemo" class="ztree"
										style="margin-top: 0; width: 160px; height: auto;"></ul>
								</div>

							</div>							
						</div>
						
						<div class="form-group" style="margin-top: 15px">
							<label class="control-label col-sm-1"
								for="txt_search_project" style="width: 15%"><input id="onlypcase" name="onlypcase" type="checkbox" onchange="searchpcase()"/>&nbsp;&nbsp;显示计划中全部用例</label>
						</div>
						
					</div>
				</div>
				
				<div id="toolbar" class="btn-group">
					<button id="btn_edit" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>&nbsp;保存选项
					</button>
				</div>
				<table id="tb_projectplancase"></table>

			</div>
			</article>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$.fn.zTree.init($("#treeDemo"), genJsonConfig());
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_projectplancase').bootstrapTable({
					url : '/projectPlanCase/getCaseList.do?projectid=${projectid}&planid=${planid}', //请求后台的URL（*）
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
					pageSize : 25, //每页的记录行数（*）
					pageList : [ 25, 50, 100, 200], //可供选择的每页的行数（*）
					search : searchdis(), //是否显示表格搜索，此搜索会进服务端
					strictSearch : true,
					showColumns : false, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					height : 500, //行高，如果没有设置height属性，表格自动根据记录条数决定表格高度
					uniqueId : "ID", //每一行的唯一标识，一般为主键列
					showToggle : false, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					columns : [ {
					checkbox : true,
					formatter: function (value, row, index) {
					    if (row.checktype == 1)
					        return {
					            checked : true//设置选中
					        };
					}
					}, {
						field : 'id',
						title : 'id',
						visible : false
					}, {
						field : 'projectid',
						title : 'projectid',
						visible : false
					}, {
						field : 'priority',
						title : '执行优先级',
						width : '5%',
						editable : {
							type : 'text',
							title : '执行优先级',
							emptytext : "0",
							validate : function(value) {
								if (isNaN(value)) return '优先级必须是数字';
		                        var age = parseInt(value);
		                        if (age < 0) return '优先级必须是正整数';
								if (value.length > 8) return '优先级不能超过8个字符';
							}
						}
					}, {
						field : 'sign',
						title : '用例编号',
						width : '5%',
					}, {
						field : 'name',
						title : '用例名称',
						width : '50%'
					}, {
						field : 'modulename',
						title : '所属模块',
						width : '10%',
					}, {
						field : 'casetype',
						title : '用例类型',
						width : '10%',
						formatter: function (value, row, index) {
							if(value==0){
								return "接口自动化";
							}
							if(value==1){
								return "Web自动化";
							}
						}
					}, {
						field : 'remark',
						title : '备注',
						width : '20%',
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
						
						$('#tb_projectplancase').bootstrapTable("resetView");
						$.ajax({
							type : "post",
							url : "/projectPlanCase/update.do?planid=${planid}",
							data : row,
							dataType : 'JSON',
							contentType: "application/x-www-form-urlencoded; charset=utf-8", 
							success : function(data, status) {
								if (data.status == "success") {
									toastr.success(data.ms);
	                                    
	                                    var i=0;
	                                    var allTableData = $("#tb_projectplancase").bootstrapTable('getData');
	                                    for(;i<allTableData.length;i++){
	                                        if(allTableData[i].id==row.id){
	                                            break;
	                                        }
	                                    }
	                                    $('#tb_projectplancase').bootstrapTable('check',i);
								}else{
									$('#tb_projectplancase').bootstrapTable('refresh');
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
			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var onlypcase=0;
				if($('#onlypcase').is(':checked')) {
					onlypcase=1;
					$.fn.zTree.destroy();
					$("#module_tree").val(""); 
					$("#module_tree").attr("readOnly",true);
				 }else{
					$.fn.zTree.init($("#treeDemo"), genJsonConfig());
					$("#module_tree").attr("readOnly",false);
				 }
				
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					limit : params.limit, //页面大小
					offset : params.offset, //页码偏移量
					search : params.search, //搜索参数
					moduleid : $('#search_module').val(), //模块ID
					onlypcase : onlypcase, //是否只显示被选中用例
				};
				return temp;
			};

			return oTableInit;
		};
	    
		var searchpcase = function() {
			//1.初始化Table
			var oTable = new TableInit();
			$('#tb_projectplancase').bootstrapTable('destroy');
			oTable.Init();
		};
		
		var searchdis = function() {
			if($('#onlypcase').is(':checked')) {
				return false;
			}else{
				return true;
			}
		};

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
			
	        var selectIndex = $('input[name="btSelectItem"]:checked ').val();
	        saveItem($('#tb_projectplancase'), selectIndex, true);
	    }
	    
	    function saveItem($table, selectIndex, reLoad){
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                      return row.id;
                       }); 
	        	if(confirm("确认保存您选择的用例到计划中吗?")){
		                $.ajax({
		                   type: "POST",
		                   cache:false,
		                   async : true,
		                   dataType : "json",
		                   url:  "savePlanCase.do?planid=${planid}",
		                   contentType: "application/json", //必须有
		                   data: JSON.stringify({"caseids":ids}),
		                   success: function(data, status){
		                           if (data.status == "success"){
		                              if(reLoad){
		                            	  toastr.success('保存计划中的用例成功!');
		                                  $table.bootstrapTable('refresh');
		                              }
		                           }else{
		                        	   toastr.error('保存计划中的用例出错!');
		                           }
		                   },error:function()
		                    {
		                	   toastr.error('保存计划中的用例出错!');
		                    }
		                });
	            }    
	    }
	    
		//=========================================================================================		
		function genJsonConfig(){
			var setting = {
					async: {
						enable: true,
						url:"/projectCase/getmodulelist.do",
						autoParam:["id", "name=n", "level=lv"],
						otherParam:{"projectid":${projectid}},
						dataFilter: filter
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
			$('#tb_projectplancase').bootstrapTable('destroy');
			oTable.Init();
			hideMenu();
		}		

		//显示菜单
		function showMenu() {
			if($('#onlypcase').is(':checked')) {
				return false;
			}else{
			    $("#menuContent2").css({ left: "15px", top: "34px" }).slideDown("fast");
			    $("body").bind("mousedown", onBodyDown);
			}
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
	</script>
</body>
</html>