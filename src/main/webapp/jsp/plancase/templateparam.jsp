<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑协议模板内容</title>

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

<body>
	<div>
		<%@ include file="/head.jsp"%>
	</div>
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width: auto; font-size: 14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active"><a href="/projectprotocolTemplate/load.do">测试协议模板</a></li>
			<li class="active">编辑协议模板内容</li>
		</ol>

	<div class="modal fade" id="sample_params" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title" id="myModalLabel">协议模板参数示例：</h4>
							</div>
							<div class="modal-body">
							  <form class="form-horizontal">
								<div class="form-group">
								    <label class="col-sm-12" style="font-weight:normal">如果您是form表单方式或是URL方式提交参数，直接按参数分隔，普通类型选择String，文件类型选择File对象即可。</label>
								    <label class="col-sm-12" style="font-weight:normal"></label>
								    <label class="col-sm-12" style="font-weight:normal"></label>
								    <label class="col-sm-12" style="font-weight:normal">如果您是RESTful API方式，以Json格式请求，请参照如下格式：</label>
									<label class="col-sm-12" style="font-weight:normal"><b>Json格式示例：</b>{"params0":"v0","params1":{"test1":"pv1","test2":50221},"params2":[{"test1":"pv1","test2":1212},{"test1":"pv2","test2":1414}]}</label>
								    <label class="col-sm-12" style="font-weight:normal"></label>
								    <label class="col-sm-12" style="font-weight:normal"></label>
								    <label class="col-sm-12" style="font-weight:normal"><b>模板参数填写如下：</b></label>
								    <label class="col-sm-12" style="font-weight:normal">第一个参数：参数名：params0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;默认值：v0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参数类型：String</label>
								    <label class="col-sm-12" style="font-weight:normal">第二个参数：参数名：params1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;默认值：{"test1":"pv1","test2":50221}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参数类型：JSON对象</label>
								    <label class="col-sm-12" style="font-weight:normal">第三个参数：参数名：params2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;默认值：[{"test1":"pv1","test2":1212},{"test1":"pv2","test2":1414}]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参数类型：JSONARR对象</label>
								</div>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>
				
		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">编辑测试协议模板内容</h1>
			</header>

			<div class="col-md-12 col-sm-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<h3 class="thin text-center">协议模板基本信息</h3>
						<table class="table table-striped" id="templatetable">
							<tr>
							    <td width="20%" style="font-weight:bold;">项目名称：${ptemplate.projectname}</td>
								<td width="20%" style="font-weight:bold;">模板名称：${ptemplate.name}</td>
								<td width="50%" style="font-weight:bold;">请求头域：${ptemplate.headmsg}</td>
								<td width="10%" style="font-weight:bold;">协议类型：${ptemplate.protocoltype}</td>
							</tr>
							<tr>
							    <td style="font-weight:bold;">编码格式：${ptemplate.contentencoding}</td>
							    <td style="font-weight:bold;">备注：${ptemplate.remark}</td>
							    <td style="font-weight:bold;">证书路径：${ptemplate.cerpath}</td>
								<td style="font-weight:bold;">超时时间：${ptemplate.connecttimeout}</td>								
							</tr>
						</table>

                        <h3 class="thin text-center">协议模板参数</h3>
                        <p style="text-align: left"><a href="javascript:void(0);" onclick="sample()">参数示例</a></p>
						<form id="templateparams" onsubmit="return check_form()">
							<div class="form-group">
								<table class="table table-striped" id="paramtable">
									<thead>
										<tr>
											<th>参数名</th>
											<th>参数默认值</th>
											<th>参数类型</th>
										</tr>
									</thead>
									<tbody id="paramtbody">
										<c:forEach var="t" items="${templateparams}" begin="0" step="1"
											varStatus="i">
											<tr id="paramrow-${i.count}">
												<td width="15%"><input type="text" class="form-control"
													name="paramname" id="paramname${i.count}"
													value="${t.paramname }" /></td>
												<td width="71%"><input type="text" class="form-control"
													name="param" id="param${i.count}" value="${t.param }" /> <input
													id="id${i.count}" type="hidden" value="${t.id }" /></td>
												<td width="10%"><select class="form-control"
													name="paramtype" id="paramtype${i.count}">
														<c:choose>
															<c:when test="${t.paramtype=='1' }">
																<option value="0">String</option>
														        <option value="1" selected = "selected">JSON对象</option>
														        <option value="2">JSONARR对象</option>
														        <option value="3">File对象</option>
														        <option value="4">Number</option>
														        <option value="5">Boolean</option>
                                                            </c:when>
                                                            <c:when test="${t.paramtype=='2' }">
																<option value="0">String</option>
														        <option value="1">JSON对象</option>
														        <option value="2" selected = "selected">JSONARR对象</option>
														        <option value="3">File对象</option>
														        <option value="4">Number</option>
														        <option value="5">Boolean</option>
                                                            </c:when>
                                                            <c:when test="${t.paramtype=='3' }">
																<option value="0">String</option>
														        <option value="1">JSON对象</option>
														        <option value="2">JSONARR对象</option>
														        <option value="3" selected = "selected">File对象</option>
														        <option value="4">Number</option>
														        <option value="5">Boolean</option>
                                                            </c:when>
                                                            <c:when test="${t.paramtype=='4' }">
																<option value="0">String</option>
														        <option value="1">JSON对象</option>
														        <option value="2">JSONARR对象</option>
														        <option value="3">File对象</option>
														        <option value="4" selected = "selected">Number</option>
														        <option value="5">Boolean</option>
                                                            </c:when>
                                                            <c:when test="${t.paramtype=='5' }">
																<option value="0">String</option>
														        <option value="1">JSON对象</option>
														        <option value="2">JSONARR对象</option>
														        <option value="3">File对象</option>
														        <option value="4">Number</option>
														        <option value="5" selected = "selected">Boolean</option>
                                                            </c:when>
															<c:otherwise>
																<option value="0" selected = "selected">String</option>
														        <option value="1">JSON对象</option>
														        <option value="2">JSONARR对象</option>
														        <option value="3">File对象</option>
														        <option value="4">Number</option>
														        <option value="5">Boolean</option>
                                                            </c:otherwise>
														</c:choose>
												</select></td>

												<td width="4%" style="vertical-align: middle;"><a
													class="fa fa-plus-circle fa-5"
													style="font-size: 20px; cursor: pointer;"
													onclick="addparam(this)"></a> <a
													class="fa fa-minus-circle fa-5"
													style="font-size: 20px; cursor: pointer;"
													onclick="delparam(this)"></a>
													</td>													
											</tr>
											
										</c:forEach>
									</tbody>
								</table>
							</div>
							</br>
							<div class="row">
								<div class="col-lg-4 text-center" style="width: 100%">
									<button class="btn btn-action" id="submit" type="submit">保 存</button>
								</div>
							</div>
						</form>
					</div>
				</div>

			</div>

			<p>&nbsp;</p>
			</article>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(
				function() {
					$('#paramtable').bootstrapValidator({
						message : '当前填写信息无效！',
						//live: 'submitted',
						feedbackIcons : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							paramname : {
								message : '【参数名】无效！',
								validators : {
									notEmpty : {
										message : '【参数名】不能为空'
									},
									stringLength : {
										min : 2,
										max : 50,
										message : '【参数名】长度必须在2~50个字符区间'
									}
								}
							},
							param : {
								message : '【参数默认值】无效！',
								validators : {
									stringLength : {
										min : 0,
										max : 2000,
										message : '【参数默认值】长度必须在0~2000个字符区间'
									}
								}
							},
						}
					}).on(
							'success.form.bv',
							function(e) {
								// Prevent submit form
								e.preventDefault();
								var $form = $(e.target), validator = $form
										.data('bootstrapValidator');
								$form.find('.alert').html('参数创建成功！');
							});
				});
		
		String.prototype.replaceAll = function(s1,s2) { 
		    return this.replace(new RegExp(s1,"gm"),s2); 
		}

		// 提交表单
		function check_form() {
			$("#submit").attr("disabled",true);
			var oTable = document.getElementById("paramtbody");
			var json = "";
			for (var i = 0; i < oTable.rows.length; i++) {
				var index = i + 1
				json = json + "{\"id\":" + $("#id" + index).val()+ ",";
				json = json + "\"paramname\":\"" + $("#paramname" + index).val().replaceAll("\"", "&quot;") + "\",";
				json = json + "\"param\":\"" + $("#param" + index).val().replaceAll("\"", "&quot;")	+ "\",";
				json = json + "\"paramtype\":\"" + $("#paramtype" + index).val().replaceAll("\"", "&quot;")	+ "\",";
				json = json + "\"templateid\":\"" + '${ptemplate.id}' + "\"}";
				if (i != oTable.rows.length - 1) {
					json = json + ",";
				}
			}
			json = "[" + json + "]";
			// 异步提交数据到action页面
			$.ajax({
				type : "POST",
				cache : false,
				async : true,
				dataType : "json",
				url : "editparam.do",
				contentType : "application/json", //必须有
				data : JSON.stringify(json),
				success : function(data, status) {
 				if (data.status == "success") {
 					$("#submit").attr("disabled",false);
 					toastr.success(data.ms);
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

		function addparam(obj) {
			if (obj == null)
				return;
			var parentTD = obj.parentNode; //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
			var parentTR = parentTD.parentNode;
			var clonedNode = parentTR.cloneNode(true); // 克隆节点
			var oTable = document.getElementById("paramtbody");
			clonedNode.setAttribute("id", "paramrow-" + (oTable.rows.length + 1)); // 修改一下id 值，避免id 重复
			var o = clonedNode.childNodes;
			parentTR.parentNode.appendChild(clonedNode); // 在父节点插入克隆的节点 

			for (var i = 0; i < oTable.rows.length; i++) {
				var index = i + 1
 				oTable.rows[i].cells[0].childNodes[0].setAttribute("id","paramname"+index);
				oTable.rows[i].cells[1].childNodes[0].setAttribute("id","param"+index);
				oTable.rows[i].cells[2].childNodes[0].setAttribute("id","paramtype"+index);
			}
		}

		function delparam(obj) {
			if (obj == null)
				return;
			var oTable = document.getElementById("paramtbody");
			if (oTable.rows.length < 2)
				return;

			var parentTD = obj.parentNode; //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
			var parentTR = parentTD.parentNode;
			var parentTBODY = parentTR.parentNode;
			parentTBODY.removeChild(parentTR);

			for (var i = 0; i < oTable.rows.length; i++) {
				var index = i + 1
				oTable.rows[i].cells[0].childNodes[0].setAttribute("id","paramname"+index);
				oTable.rows[i].cells[1].childNodes[0].setAttribute("id","param"+index);
				oTable.rows[i].cells[2].childNodes[0].setAttribute("id","paramtype"+index);
			}
		}
		
		function sample(){
			$("#sample_params").modal('show');
		}
	</script>
</body>
</html>
