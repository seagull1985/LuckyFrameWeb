<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑用例步骤</title>

<style type="text/css">
<!--
.STYLE1 {
	font-size: 12px;
	color: #ffffff;
}

-->
.error_msg {
	font-size: 12px;
	color: #f00;
}

.tip {
	font-size: 12px;
	color: blue;
}
</style>


</head>

<body onload="init()">
	<div>
		<%@ include file="/head.jsp"%>
	</div>
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width: auto; font-size: 14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active"><a href="/projectCase/load.do">用例管理</a></li>
			<li class="active">编辑步骤</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">编辑步骤</h1>
			</header>

			<div class="col-md-10 col-md-offset1 col-sm-8 col-sm-offset-2"
				style="margin-left: 8%">
				<div class="panel panel-default">
					<div class="panel-body">
						<h3 class="thin text-center">用例【${casesign}】编辑步骤</h3>

						<sf:form modelAttribute="casesteps" method="post" id="casesteps"
							onsubmit="return check_form()">
							<input name="caseid" id="caseid" type="hidden" />
							<input name="projectid" id="projectid" type="hidden" />
							<div class="form-group">
								<table class="table table-striped" id="steptable">
									<thead>
										<tr>
											<th>步骤</th>
											<th>包 | 定位路径</th>
											<th>方法 | 操作</th>
											<th>参数</th>
											<th>步骤动作</th>
											<th>预期结果</th>
											<th>类型</th>
											<th>备注</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="steptbody">
										<c:forEach var="t" items="${steps}" begin="0" step="1"
											varStatus="i">
											<tr id="steprow-${i.count}">
												<td width="3%" style="vertical-align: middle;"><sf:label
														class="form-control" path="stepnum" id="stepnum"
														value="${t.stepnum }" />
												</td>
												<td width="18%"><sf:input type="text"
														class="form-control" path="path" id="path${i.count}"
														value="${t.path }" /></td>
												<td width="13%"><sf:input type="text"
														class="form-control" path="operation"
														id="operation${i.count}" value="${t.operation }" /></td>
												<td width="15%"><sf:input type="text"
														class="form-control" path="parameters"
														id="parameters${i.count}" value="${t.parameters }" /></td>
												<td width="10%"><sf:input type="text"
														class="form-control" path="action" id="action${i.count}"
														value="${t.action }" /></td>
												<td width="13%"><sf:input type="text"
														class="form-control" path="expectedresult"
														id="expectedresult${i.count}" value="${t.expectedresult }" /></td>
												<td width="10%"><sf:select type="text"
														class="form-control" align-items="left" path="steptype"
														id="steptype${i.count}">
														<c:if test="${t.steptype==0 }">
                                                      <sf:option
																value="0" selected="selected">接口</sf:option>
															<sf:option value="1">Web UI</sf:option>
														</c:if>
                                                <c:if
															test="${t.steptype==1 }">
															<sf:option value="0">接口</sf:option>
															<sf:option value="1" selected="selected">Web UI</sf:option>
														</c:if> 
												</sf:select></td>
												<td width="10%"><sf:input type="text"
														class="form-control" path="remark" id="remark${i.count}"
														value="${t.remark }" /></td>
												<td width="8%" style="vertical-align: middle;"><a
													class="fa fa-plus-circle fa-5"
													style="font-size: 20px; cursor: pointer;"
													onclick="addsteps(this)"></a> <a
													class="fa fa-minus-circle fa-5"
													style="font-size: 20px; cursor: pointer;"
													onclick="delsteps(this)"></a> <a
													class="fa fa-arrow-up fa-5"
													style="font-size: 20px; cursor: pointer;"
													onclick="upsteps(this)"></a> <a
													class="fa fa-arrow-down fa-5"
													style="font-size: 20px; cursor: pointer;"
													onclick="downsteps(this)"></a></td>
													<sf:input path="id" id="id${i.count}" type="hidden" value="${t.id }" />
											</tr>

										</c:forEach>
									</tbody>
								</table>
							</div>
							</br>
							<div class="row">
								<div class="col-lg-4 text-center" style="width: 100%">
									<button class="btn btn-action" type="submit">保 存</button>
								</div>
							</div>
						</sf:form>
					</div>
				</div>

			</div>

			<p>&nbsp;</p>
			</article>
		</div>
	</div>

	<script type="text/javascript">
		function init() {
			if ('${steps}'.size == 0) {
				document.getElementById("steptbody").rows[0].cells[0].innerHTML = 1;
				document.getElementById("steptbody").rows[0].cells[0].value = 1;
			} else {
				var oTable = document.getElementById("steptbody");
				for (var i = 0; i < oTable.rows.length; i++) {
					oTable.rows[i].cells[0].innerHTML = (i + 1);
					oTable.rows[i].cells[0].value = (i + 1);
				}
			}

			if ('${message}' != '') {
				if ('${message}' == '添加成功') {
					toastr.success('添加成功,请返回查询！');
				} else {
					toastr.info('${message}');
				}
			}
		}

		$(document).ready(
				function() {
					$('#casesteps').bootstrapValidator({
						message : '当前填写信息无效！',
						//live: 'submitted',
						feedbackIcons : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							path : {
								message : '【包 | 定位路径】无效！',
								validators : {
									notEmpty : {
										message : '【包 | 定位路径】不能为空'
									},
									stringLength : {
										min : 2,
										max : 100,
										message : '【包 | 定位路径】长度必须在2~100个字符区间'
									}
								}
							},
							operation : {
								message : '【方法 | 操作】无效！',
								validators : {
									notEmpty : {
										message : '【方法 | 操作】不能为空'
									},
									stringLength : {
										min : 2,
										max : 100,
										message : '【方法 | 操作】长度必须在2~100个字符区间'
									}
								}
							},
							parameters : {
								message : '【参数】无效！',
								validators : {
									stringLength : {
										min : 0,
										max : 500,
										message : '【参数】长度必须小于500个字符'
									}
								}
							},
							action : {
								message : '【步骤动作】无效！',
								validators : {
									stringLength : {
										min : 0,
										max : 50,
										message : '【步骤动作】长度必须小于50个字符'
									}
								}
							},
							expectedresult : {
								message : '【预期结果】无效！',
								validators : {
									stringLength : {
										min : 0,
										max : 2000,
										message : '【预期结果】长度必须小于2000个字符'
									}
								}
							},
							remark : {
								message : '【备注】无效！',
								validators : {
									stringLength : {
										min : 0,
										max : 200,
										message : '【备注】长度必须小于200个字符'
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
								$form.find('.alert').html('步骤创建成功！');
							});
				});

		// 提交表单
		function check_form() {
			var oTable = document.getElementById("steptbody");
			var json = "";
			for (var i = 0; i < oTable.rows.length; i++) {
				var index = i + 1
				json = json + "{\"stepnum\":" + oTable.rows[i].cells[0].value
						+ ",";
				json = json + "\"path\":\"" + $("#path" + index).val() + "\",";
				json = json + "\"operation\":\"" + $("#operation" + index).val()
						+ "\",";
				json = json + "\"parameters\":\""
						+ $("#parameters" + index).val() + "\",";
				json = json + "\"action\":\"" + $("#action" + index).val() + "\",";
				json = json + "\"expectedresult\":\""
						+ $("#expectedresult" + index).val() + "\",";
				json = json + "\"steptype\":" + $("#steptype" + index).val()+ ",";
				json = json + "\"id\":" + $("#id" + index).val()+ ",";
				json = json + "\"caseid\":\""+ '${caseid}' + "\",";
				json = json + "\"projectid\":\""+ '${projectid}' + "\",";
				json = json + "\"remark\":\"" + $("#remark" + index).val() + "\"}";
				if (i != oTable.rows.length - 1) {
					json = json + ",";
				}
			}
			json = "[" + json + "]"
			// 异步提交数据到action页面
			$.ajax({
				type : "POST",
				cache : false,
				async : true,
				dataType : "json",
				url : "editsteps.do",
				contentType : "application/json", //必须有
				data : JSON.stringify(json),
				success : function(data, status) {
 				if (data.status == "success") {
 					toastr.success(data.ms);
		    			var url = '/projectCase/load.do';
		    			window.location.href=url;
					}else{
						toastr.info(data.ms);
					}
				},
				error : function() {
					toastr.error(data);
				}
			});

			return false;
		}

		function addsteps(obj) {
			if (obj == null)
				return;
			var parentTD = obj.parentNode; //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
			var parentTR = parentTD.parentNode;
			var clonedNode = parentTR.cloneNode(true); // 克隆节点
			var oTable = document.getElementById("steptbody");
			clonedNode.setAttribute("id", "steprow-" + (oTable.rows.length + 1)); // 修改一下id 值，避免id 重复
			var o = clonedNode.childNodes;
			console.log(o);
/* 			o[5].childNodes[0].setAttribute("id", "2222"); */
			parentTR.parentNode.appendChild(clonedNode); // 在父节点插入克隆的节点 

			for (var i = 0; i < oTable.rows.length; i++) {
				var index = i + 1
				oTable.rows[i].cells[0].innerHTML = (i + 1);
				oTable.rows[i].cells[0].value = (i + 1);
				oTable.rows[i].cells[1].childNodes[0].setAttribute("id","path"+index);
				oTable.rows[i].cells[2].childNodes[0].setAttribute("id","operation"+index);
				oTable.rows[i].cells[3].childNodes[0].setAttribute("id","parameters"+index);
				oTable.rows[i].cells[4].childNodes[0].setAttribute("id","action"+index);
				oTable.rows[i].cells[5].childNodes[0].setAttribute("id","expectedresult"+index);
				oTable.rows[i].cells[6].childNodes[0].setAttribute("id","steptype"+index);
				oTable.rows[i].cells[7].childNodes[0].setAttribute("id","remark"+index);
			}
		}

		function delsteps(obj) {
			if (obj == null)
				return;
			var oTable = document.getElementById("steptbody");
			if (oTable.rows.length < 2)
				return;

			var parentTD = obj.parentNode; //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
			var parentTR = parentTD.parentNode;
			var parentTBODY = parentTR.parentNode;
			parentTBODY.removeChild(parentTR);

			for (var i = 0; i < oTable.rows.length; i++) {
				var index = i + 1
				oTable.rows[i].cells[0].innerHTML = (i + 1);
				oTable.rows[i].cells[0].value = (i + 1);
				oTable.rows[i].cells[1].childNodes[0].setAttribute("id","path"+index);
				oTable.rows[i].cells[2].childNodes[0].setAttribute("id","operation"+index);
				oTable.rows[i].cells[3].childNodes[0].setAttribute("id","parameters"+index);
				oTable.rows[i].cells[4].childNodes[0].setAttribute("id","action"+index);
				oTable.rows[i].cells[5].childNodes[0].setAttribute("id","expectedresult"+index);
				oTable.rows[i].cells[6].childNodes[0].setAttribute("id","steptype"+index);
				oTable.rows[i].cells[7].childNodes[0].setAttribute("id","remark"+index);
			}
		}

		function upsteps(obj) {
			var self = this
			if (obj == null)
				return;
			var parentTD = obj.parentNode; //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
			var parentTR = parentTD.parentNode;
			var parentTBODY = parentTR.parentNode;
			if (parentTR.rowIndex > 1) {
				self.swapTr(parentTR,
						self.steptable.rows[parentTR.rowIndex - 1]);
				for (var i = 0; i < parentTBODY.rows.length; i++) {
					var index = i + 1
					parentTBODY.rows[i].cells[0].innerHTML = (i + 1);
					parentTBODY.rows[i].cells[0].value = (i + 1);
					parentTBODY.rows[i].cells[1].childNodes[0].setAttribute("id","path"+index);
					parentTBODY.rows[i].cells[2].childNodes[0].setAttribute("id","operation"+index);
					parentTBODY.rows[i].cells[3].childNodes[0].setAttribute("id","parameters"+index);
					parentTBODY.rows[i].cells[4].childNodes[0].setAttribute("id","action"+index);
					parentTBODY.rows[i].cells[5].childNodes[0].setAttribute("id","expectedresult"+index);
					parentTBODY.rows[i].cells[6].childNodes[0].setAttribute("id","steptype"+index);
					parentTBODY.rows[i].cells[7].childNodes[0].setAttribute("id","remark"+index);
				}
			}
		}

		function downsteps(obj) {
			var self = this
			if (obj == null)
				return;
			var parentTD = obj.parentNode; //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
			var parentTR = parentTD.parentNode;
			var parentTBODY = parentTR.parentNode;
			if (parentTR.rowIndex < parentTBODY.rows.length) {
				self.swapTr(parentTR,
						self.steptable.rows[parentTR.rowIndex + 1]);
				for (var i = 0; i < parentTBODY.rows.length; i++) {
					var index = i + 1
					parentTBODY.rows[i].cells[0].innerHTML = (i + 1);
					parentTBODY.rows[i].cells[0].value = (i + 1);
					parentTBODY.rows[i].cells[1].childNodes[0].setAttribute("id","path"+index);
					parentTBODY.rows[i].cells[2].childNodes[0].setAttribute("id","operation"+index);
					parentTBODY.rows[i].cells[3].childNodes[0].setAttribute("id","parameters"+index);
					parentTBODY.rows[i].cells[4].childNodes[0].setAttribute("id","action"+index);
					parentTBODY.rows[i].cells[5].childNodes[0].setAttribute("id","expectedresult"+index);
					parentTBODY.rows[i].cells[6].childNodes[0].setAttribute("id","steptype"+index);
					parentTBODY.rows[i].cells[7].childNodes[0].setAttribute("id","remark"+index);
				}
			}
		}

		function swapTr(tr1, tr2) { //交换tr1和tr2的位置 
			var target = (tr1.rowIndex < tr2.rowIndex) ? tr2.nextSibling : tr2;
			var tBody = tr1.parentNode
			tBody.replaceChild(tr2, tr1);
			tBody.insertBefore(tr1, target);
		}
	</script>
</body>
</html>
