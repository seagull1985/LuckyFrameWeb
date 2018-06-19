<%@ page contentType="text/html; charset=utf-8" language="java"
		 import="java.sql.*" errorPage="" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>编辑用例步骤</title>

</head>

<body>
<div>
	<%@ include file="/head.jsp" %>
</div>
<header id="head" class="secondary"></header>

<!-- container -->
<div class="container" style="width: auto; font-size: 14px">
	<ol class="breadcrumb">
		<li><a href="/">主页</a></li>
		<li class="active"><a href="/projectCase/load.do">用例管理</a></li>
		<li class="active">编辑步骤</li>
	</ol>

	<div class="modal fade" id="sample_params" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title" id="myModalLabel">用例步骤说明：</h4>
							</div>
							<div class="modal-body">
							  <form class="form-horizontal">
								<div class="form-group">
								    <label class="col-sm-12" style="font-weight:normal"><b>整体说明：</b>调试按钮，只有用例类型为【接口测试】才可用。详细使用方法可以参考官网<a href="http://www.luckyframe.cn/book/yhsc/syschyy-24.html">用户手册</a></label>
								    <label class="col-sm-12" style="font-weight:normal"><b>Web UI&移动端UI说明：</b>当你的步骤类型为Web UI时，定位路径是指元素的定位路径，如果没有可为空;操作列点击输入框会自动联想系统内置的方法，
								    并显示在下拉列表中，选择即可;参数列是针对方法的，比如你用到sendkeys方法,那么参数列就是sendkeys需要输入的值;步骤动作一般是指当前步骤操作完成后，等待多少时间，格式为：10*Wait;预期结果列一般留空，
								    输入值时填写格式为“check(属性=属性值)”，用于检查步骤动作执行完成后，对应的元素是否存在,更多细节可以参考<a href="http://www.luckyframe.cn/allwz/29.html">官网文章</a></label>
								    <label class="col-sm-12" style="font-weight:normal"></label>
								    <label class="col-sm-12" style="font-weight:normal"><b>自定义驱动接口说明：</b>当你的步骤类型为接口时，包路径是测试类的包路径，不能为空;方法列就是需要调用的测试方法名;
								    参数列是针对方法的，测试方法需要传几个参数就填写几个参数，中间用|分隔;步骤动作一般是指当前步骤操作完成后，等待多少时间，格式为：10*Wait;预期结果是用来匹配测试方法返回值的;更多细节可以参考<a href="http://www.luckyframe.cn/allwz/testdrver-34.html">官网文章</a></label>
								    <label class="col-sm-12" style="font-weight:normal"></label>
								    <label class="col-sm-12" style="font-weight:normal"><b>HTTP接口说明：</b>当你的步骤类型为HTTP时，定位路径是HTTP测试请求的URL地址，不能为空;方法列是HTTP请求的类型;
								    参数列是针对协议模板的，可以对模板中的默认值进行替换，需要替换多个参数时，中间用|分隔;步骤动作一般是指当前步骤操作完成后，等待多少时间，格式为：10*Wait;预期结果是用来匹配HTTP请求返回值的;更多细节可以参考<a href="http://www.luckyframe.cn/allwz/httpcs-16.html">官网文章</a></label>
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
				<h1 class="page-title" style="text-align: center;">${casename}</h1>
			</header>

			<div class="col-md-12 col-sm-12">
				<div class="panel panel-default">
					<div class="panel-body">
					<header class="page-header">
						<h3 class="thin text-center">用例【${casesign}】编辑步骤</h3>
						<p style="text-align: left"><a href="javascript:void(0);" onclick="sample()">步骤说明</a></p>
					</header>
						<sf:form modelAttribute="casesteps" method="post" id="casesteps" onsubmit="return check_form()">
							<input name="caseid" id="caseid" type="hidden"/>
							<input name="projectid" id="projectid" type="hidden"/>
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
								<c:forEach var="t" items="${steps}" begin="0" step="1" varStatus="i">
									<tr id="steprow-${i.count}">
										<td width="3%" style="vertical-align: middle;">
											<sf:label class="form-control" path="stepnum" id="stepnum${i.count}" value="${t.stepnum}"/>
										</td>
										<td width="18%">
											<div class="form-group"><sf:input type="text" class="form-control" path="path" id="path${i.count}" value="${t.path}"/></div>
										</td>
										<td width="13%">
											<div class="form-group">
												<div class="input-group" style="width:100%;">
													<sf:input type="text" class="form-control" path="operation" id="operation${i.count}" value="${t.operation}"/>
													<div class="input-group-btn">
														<ul class="dropdown-menu dropdown-menu-right" role="menu"></ul>
													</div>
												</div>
											</div>
										</td>
										<td width="17%">
											<div class="form-group"><sf:input type="text" class="form-control" path="parameters" id="parameters${i.count}" value="${t.parameters}"/></div>
										</td>
										<td width="10%">
											<div class="form-group">
												<div class="input-group" style="width:100%;">
													<sf:input type="text" class="form-control" path="action" id="action${i.count}" value="${t.action}"/>
													<div class="input-group-btn">
														<ul class="dropdown-menu dropdown-menu-right" role="menu"></ul>
													</div>
												</div>
											</div>
										</td>
										<td width="13%">
											<div class="form-group"><sf:input type="text" class="form-control" path="expectedresult" id="expectedresult${i.count}" value="${t.expectedresult}"/></div>
										</td>
										<td width="8%">
											<div class="form-group">
												<sf:select type="text" class="form-control" align-items="left" path="steptype" id="steptype${i.count}" onChange="getObIndex(this)">                           
													<option value="0" <c:if test="${t.steptype==0 }"> selected="selected"</c:if>>接口</option>
													<option value="1" <c:if test="${t.steptype==1 }"> selected="selected"</c:if>>Web UI</option>
													<option value="2" <c:if test="${t.steptype==2 }"> selected="selected"</c:if>>HTTP</option>
													<option value="3" <c:if test="${t.steptype==3 }"> selected="selected"</c:if>>SOCKET</option>
													<option value="4" <c:if test="${t.steptype==4 }"> selected="selected"</c:if>>移动端</option>
												</sf:select>
											</div>
										</td>
										<td width="10%">
											<div class="form-group"><sf:input type="text" class="form-control" path="remark" id="remark${i.count}" value="${t.remark}"/></div>
										</td>
										<td width="8%" style="vertical-align: middle;">
											<a class="fa fa-plus-circle fa-5" style="font-size: 20px; cursor: pointer;" onclick="addsteps(this)"></a>
											<a class="fa fa-minus-circle fa-5" style="font-size: 20px; cursor: pointer;" onclick="delsteps(this)"></a>
											<a class="fa fa-arrow-up fa-5" style="font-size: 20px; cursor: pointer;" onclick="upsteps(this)"></a>
											<a class="fa fa-arrow-down fa-5" style="font-size: 20px; cursor: pointer;" onclick="downsteps(this)"></a>
										</td>
										<sf:input path="id" id="id${i.count}" type="hidden" value="${t.id}"/>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							</br>
							<div class="row">
								<div class="col-lg-4 text-center" style="width: 100%">
									<button class="btn btn-action" style="background:#00bf5f" id="save" type="submit">保 存</button>
									<button class="btn btn-action" id="showdebugb" onclick="showdebug()" type="button">调 试</button>
								</div>
							</div>
						</sf:form>
					</div>
				</div>

				<!-- 模态框示例（Modal） -->
				<div class="modal fade" id="debug" tabindex="-1" role="dialog"
					 aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;
								</button>
								<h4 class="modal-title" id="myModalLabel">请选择用例调试所在客户端</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
									<div class="form-group">
										<label for="clientipfordebug" class="col-sm-3 control-label">客户端IP：</label>
										<div class="input-group col-md-7">
											<select class="form-control" name="clientipfordebug" id="clientipfordebug" onChange="getClientpath()" onFocus="getClientpath()">
												<c:forEach var="iplist" items="${iplist }">
													<option value="${iplist.clientip}">${iplist.projectper}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="clientpathfordebug" class="col-sm-3 control-label">驱动桩路径：</label>
										<div class="input-group col-md-7">
											<select class="form-control" name="clientpathfordebug" id="clientpathfordebug">
											</select>
										</div>
									</div>
								</form>
							</div>
							<div id="autoDown" class="modal-footer" style=" background-color:#F0F8FF;height: 300px;width:100%;overflow-y:scroll;text-align:left;">
							</div>

							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button class="btn btn-primary" onclick="debugcase()">运行</button>
								&nbsp;&nbsp;&nbsp;&nbsp;<span id="tip"> </span>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>
			</div>
			<p>&nbsp;</p>
		</article>
	</div>
</div>
<script src="/progressus/assets/js/bootstrap-suggest.min.js"></script>
<script type="text/javascript">
    $(function () {
        if ('${steps}'.size == 0) {
            document.getElementById("steptbody").rows[0].cells[0].innerHTML = 1;
            document.getElementById("steptbody").rows[0].cells[0].value = 1;
        } else {
            var oTable = document.getElementById("steptbody");
            for (var i = 0; i < oTable.rows.length; i++) {
                initSuggest((i + 1));
                oTable.rows[i].cells[0].innerHTML = (i + 1);
                oTable.rows[i].cells[0].value = (i + 1);
            }
        }

        if ('${casetype}' != 0) {
            $("#showdebugb").attr("disabled", "true");
        }
    });

    $(function () {
        $('#casesteps').bootstrapValidator({
            message: '当前填写信息无效！',
            //live: 'submitted',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                path: {
                    message: '【包 | 定位路径】无效！',
                    validators: {
                        stringLength: {
                            min: 0,
                            max: 200,
                            message: '【包 | 定位路径】长度必须小于200个字符'
                        }
                    }
                },
                operation: {
                    message: '【方法 | 操作】无效！',
                    validators: {
                        notEmpty: {
                            message: '【方法 | 操作】不能为空'
                        },
                        stringLength: {
                            min: 2,
                            max: 100,
                            message: '【方法 | 操作】长度必须在2~100个字符区间'
                        }
                    }
                },
                parameters: {
                    message: '【参数】无效！',
                    validators: {
                        stringLength: {
                            min: 0,
                            max: 500,
                            message: '【参数】长度必须小于500个字符'
                        }
                    }
                },
                action: {
                    message: '【步骤动作】无效！',
                    validators: {
                        stringLength: {
                            min: 0,
                            max: 50,
                            message: '【步骤动作】长度必须小于50个字符'
                        }
                    }
                },
                expectedresult: {
                    message: '【预期结果】无效！',
                    validators: {
                        stringLength: {
                            min: 0,
                            max: 2000,
                            message: '【预期结果】长度必须小于2000个字符'
                        }
                    }
                },
                remark: {
                    message: '【备注】无效！',
                    validators: {
                        stringLength: {
                            min: 0,
                            max: 200,
                            message: '【备注】长度必须小于200个字符'
                        }
                    }
                }
            }
        }).on('success.form.bv', function (e) {
            // Prevent form submission
            e.preventDefault();
            // Get the form instance
            var $form = $(e.target);
            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');
            $form.find('.alert').html('步骤创建成功！');
        });
    });

    String.prototype.replaceAll = function (s1, s2) {
        return this.replace(new RegExp(s1, "gm"), s2);
    }

    // 提交表单
    function check_form() {
        var casesteps = $('#casesteps');
        casesteps.data('bootstrapValidator').validate();
        if (! casesteps.data('bootstrapValidator').isValid()) {
            return;
        }

        var oTable = document.getElementById("steptbody");
        var json = "";
        for (var i = 0; i < oTable.rows.length; i++) {
            var index = i + 1;
            json = json + "{\"stepnum\":" + oTable.rows[i].cells[0].value + ",";
            json = json + "\"path\":\"" + $("#path" + index).val().replaceAll("\"", "&quot;") + "\",";
            json = json + "\"operation\":\"" + $("#operation" + index).val().replaceAll("\"", "&quot;") + "\",";
            json = json + "\"parameters\":\"" + $("#parameters" + index).val().replaceAll("\"", "&quot;") + "\",";
            json = json + "\"action\":\"" + $("#action" + index).val().replaceAll("\"", "&quot;") + "\",";
            json = json + "\"expectedresult\":\"" + $("#expectedresult" + index).val().replaceAll("\"", "&quot;") + "\",";
            json = json + "\"steptype\":" + $("#steptype" + index).val() + ",";
            json = json + "\"id\":" + $("#id" + index).val() + ",";
            json = json + "\"caseid\":\"" + '${caseid}' + "\",";
            json = json + "\"projectid\":\"" + '${projectid}' + "\",";
            json = json + "\"remark\":\"" + $("#remark" + index).val().replaceAll("\"", "&quot;") + "\"}";
            if (i !== oTable.rows.length - 1) {
                json = json + ",";
            }
        }
        json = "[" + json + "]";

        // 异步提交数据到action页面
        $.ajax({
            type: "POST",
            cache: false,
            async: true,
            dataType: "json",
            url: "editsteps.do",
            contentType: "application/json", //必须有
            data: JSON.stringify(json),
            success: function (data, status) {
                if (data.status == "success") {
                    toastr.success(data.ms);
                } else {
                    toastr.info(data.ms);
                }
                $('#save').attr('disabled', false);
            },
            error: function () {
                toastr.error(data);
            }
        });

        return true;
    }

    function addsteps(obj) {
        if (obj === null)
            return;
        var parentTD = obj.parentNode; //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
        var parentTR = parentTD.parentNode;
        var parentTB = parentTR.parentNode;
        var clonedNode = parentTR.cloneNode(true); // 克隆节点
        if (parentTB.lastChild === parentTR) {
            parentTB.appendChild(clonedNode);
        } else {
            parentTB.insertBefore(clonedNode, parentTR.nextSibling);
        }
        var oTable = document.getElementById("steptbody");
        var begin = parentTR.sectionRowIndex + 1;
        for (var i = begin; i < oTable.rows.length; i++) {
            var index = i + 1;
            oTable.rows[i].setAttribute("id", "steprow-" + index);
            oTable.rows[i].cells[0].innerHTML = (i + 1);
            oTable.rows[i].cells[0].value = (i + 1);
            oTable.rows[i].cells[1].children[0].children[0].setAttribute("id", "path" + index);
            var opob = oTable.rows[i].cells[2].children[0].children[0];
            if (opob.children[0].tagName.toLocaleLowerCase() === 'input') {
                opob.children[0].setAttribute("id", "operation" + index);
            } else {
                opob.children[1].setAttribute("id", "operation" + index);
            }
            oTable.rows[i].cells[3].children[0].children[0].setAttribute("id", "parameters" + index);
            var aob = oTable.rows[i].cells[4].children[0].children[0];
            if (aob.children[0].tagName.toLocaleLowerCase() === 'input') {
                aob.children[0].setAttribute("id", "action" + index);
            } else {
                aob.children[1].setAttribute("id", "action" + index);
            }
            oTable.rows[i].cells[5].children[0].children[0].setAttribute("id", "expectedresult" + index);
            oTable.rows[i].cells[6].children[0].children[0].setAttribute("id", "steptype" + index);
            oTable.rows[i].cells[7].children[0].children[0].setAttribute("id", "remark" + index);
        }
        initSuggest(begin + 1);
        clearCells(begin + 1);
        addfield();
    }

    function delsteps(obj) {
        if (obj === null)
            return;
        var parentTD = obj.parentNode; //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
        var parentTR = parentTD.parentNode;
        var parentTB = parentTR.parentNode;
        var begin = parentTR.sectionRowIndex;
        var oTable = document.getElementById("steptbody");
        if (oTable.rows.length < 2) {
            return;
        }
        else {
            parentTB.removeChild(parentTR);
        }
        for (var i = begin; i < oTable.rows.length; i++) {
            var index = i + 1;
            oTable.rows[i].setAttribute("id", "steprow-" + index);
            oTable.rows[i].cells[0].innerHTML = (i + 1);
            oTable.rows[i].cells[0].value = (i + 1);
            oTable.rows[i].cells[1].children[0].children[0].setAttribute("id", "path" + index);
            var opob = oTable.rows[i].cells[2].children[0].children[0];
            if (opob.children[0].tagName.toLocaleLowerCase() === 'input') {
                opob.children[0].setAttribute("id", "operation" + index);
            } else {
                opob.children[1].setAttribute("id", "operation" + index);
            }
            oTable.rows[i].cells[3].children[0].children[0].setAttribute("id", "parameters" + index);
            var aob = oTable.rows[i].cells[4].children[0].children[0];
            if (aob.children[0].tagName.toLocaleLowerCase() === 'input') {
                aob.children[0].setAttribute("id", "action" + index);
            } else {
                aob.children[1].setAttribute("id", "action" + index);
            }
            oTable.rows[i].cells[5].children[0].children[0].setAttribute("id", "expectedresult" + index);
            oTable.rows[i].cells[6].children[0].children[0].setAttribute("id", "steptype" + index);
            oTable.rows[i].cells[7].children[0].children[0].setAttribute("id", "remark" + index);
        }
    }

    function upsteps(obj) {
//        var self = this;
        if (obj === null)
            return;
        var parentTD = obj.parentNode; //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
        var parentTR = parentTD.parentNode;
        var oTable = document.getElementById("steptbody");
        if (parentTR.sectionRowIndex >= 1) {
            swapTr(parentTR, oTable.rows[parentTR.sectionRowIndex - 1]);
            for (var i = parentTR.sectionRowIndex; i <= parentTR.sectionRowIndex + 1; i++) {
                var index = i + 1;
                oTable.rows[i].setAttribute("id", "steprow-" + index);
                oTable.rows[i].cells[0].innerHTML = (i + 1);
                oTable.rows[i].cells[0].value = (i + 1);
                oTable.rows[i].cells[1].children[0].children[0].setAttribute("id", "path" + index);
                var opob = oTable.rows[i].cells[2].children[0].children[0];
                if (opob.children[0].tagName.toLocaleLowerCase() === 'input') {
                    opob.children[0].setAttribute("id", "operation" + index);
                } else {
                    opob.children[1].setAttribute("id", "operation" + index);
                }
                oTable.rows[i].cells[3].children[0].children[0].setAttribute("id", "parameters" + index);
                var aob = oTable.rows[i].cells[4].children[0].children[0];
                if (aob.children[0].tagName.toLocaleLowerCase() === 'input') {
                    aob.children[0].setAttribute("id", "action" + index);
                } else {
                    aob.children[1].setAttribute("id", "action" + index);
                }
                oTable.rows[i].cells[5].children[0].children[0].setAttribute("id", "expectedresult" + index);
                oTable.rows[i].cells[6].children[0].children[0].setAttribute("id", "steptype" + index);
                oTable.rows[i].cells[7].children[0].children[0].setAttribute("id", "remark" + index);
            }
        }
    }

    function downsteps(obj) {
//        var self = this;
        if (obj === null)
            return;
        var parentTD = obj.parentNode; //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
        var parentTR = parentTD.parentNode;
        var oTable = document.getElementById("steptbody");
        if (parentTR.sectionRowIndex < oTable.rows.length - 1) {
            swapTr(parentTR, oTable.rows[parentTR.sectionRowIndex + 1]);
            for (var i = parentTR.sectionRowIndex - 1; i <= parentTR.sectionRowIndex; i++) {
                var index = i + 1;
                oTable.rows[i].setAttribute("id", "steprow-" + index);
                oTable.rows[i].cells[0].innerHTML = (i + 1);
                oTable.rows[i].cells[0].value = (i + 1);
                oTable.rows[i].cells[1].children[0].children[0].setAttribute("id", "path" + index);
                var opob = oTable.rows[i].cells[2].children[0].children[0];
                if (opob.children[0].tagName.toLocaleLowerCase() === 'input') {
                    opob.children[0].setAttribute("id", "operation" + index);
                } else {
                    opob.children[1].setAttribute("id", "operation" + index);
                }
                oTable.rows[i].cells[3].children[0].children[0].setAttribute("id", "parameters" + index);
                var aob = oTable.rows[i].cells[4].children[0].children[0];
                if (aob.children[0].tagName.toLocaleLowerCase() === 'input') {
                    aob.children[0].setAttribute("id", "action" + index);
                } else {
                    aob.children[1].setAttribute("id", "action" + index);
                }
                oTable.rows[i].cells[5].children[0].children[0].setAttribute("id", "expectedresult" + index);
                oTable.rows[i].cells[6].children[0].children[0].setAttribute("id", "steptype" + index);
                oTable.rows[i].cells[7].children[0].children[0].setAttribute("id", "remark" + index);
            }
        }
    }

    function swapTr(tr1, tr2) { //交换tr1和tr2的位置
        var target = (tr1.rowIndex < tr2.rowIndex) ? tr2.nextSibling : tr2;
        var tBody = tr1.parentNode;
        tBody.replaceChild(tr2, tr1);
        tBody.insertBefore(tr1, target);
    }

    function clearCells(index) {
        $("#path" + index).val('');
        $("#operation" + index).val('');
        $("#parameters" + index).val('');
        $("#action" + index).val('');
        $("#expectedresult" + index).val('');
        $("#remark" + index).val('');
    }

    function initSuggest(index) {
        var stepindex = $('#steptype' + index + ' option:selected');
        var steptypetext = stepindex.text();
        var steptype = stepindex.val();
        if (steptype === '2' || steptype === '3') {
            initSuggestOperation(index, steptype);
            initSuggestAction(index, steptypetext);
        } else if (steptype === '1' || steptype === '4') {
            $("#action" + index).bsSuggest("destroy");
            initSuggestOperation(index, steptype);
        } else {
            $("#operation" + index).bsSuggest("destroy");
            $("#action" + index).bsSuggest("destroy");
        }
    }

    function addfield() {
        var casesteps = $('#casesteps');
        casesteps.bootstrapValidator('addField', 'path');
        casesteps.bootstrapValidator('addField', 'operation');
        casesteps.bootstrapValidator('addField', 'parameters');
        casesteps.bootstrapValidator('addField', 'action');
        casesteps.bootstrapValidator('addField', 'expectedresult');
        casesteps.bootstrapValidator('addField', 'remark');
    }

    function getObIndex(ob) {
        var id = $(ob).attr("id").replaceAll("steptype", "");
        initSuggest(id);
    }

    function initSuggestAction(index, steptypetext) {
        var actionindex = $("#action" + index);
        actionindex.bsSuggest("destroy");
        actionindex.bsSuggest({
            url: "/projectprotocolTemplate/cgetPTemplateList.do?projectid=${projectid}&steptype=" + steptypetext,
            /*effectiveFields: ["userName", "shortAccount"],
            searchFields: [ "shortAccount"],*/
            effectiveFieldsAlias: {name: "模板名称", protocoltype: "协议类型", operationer: "更新人员"},
            ignorecase: true,
            showHeader: true,
            showBtn: false,     //不显示下拉按钮
            delayUntilKeyup: true, //获取数据的方式为 firstByUrl 时，延迟到有输入/获取到焦点时才请求数据
            idField: "name",
            keyField: "name",
            clearable: false
        }).on('onDataRequestSuccess', function (e, result) {

        }).on('onSetSelectValue', function (e, keyword, data) {
            // $('#casesteps').data('bootstrapValidator').updateStatus(actionindex, 'NOT_VALIDATED', null).validateField(actionindex);
        }).on('onUnsetSelectValue', function () {

        });
    }

    function initSuggestOperation(index, steptype) {
        var operationindex = $("#operation" + index);
        operationindex.bsSuggest("destroy");
        operationindex.bsSuggest({
            url: "/projectprotocolTemplate/cgetStepParamList.do?parentid=0&steptype=" + steptype + "&fieldname=operation",
            /*effectiveFields: ["userName", "shortAccount"],
            searchFields: [ "shortAccount"],*/
            effectiveFieldsAlias: {name: "操作", description: "描述"},
            ignorecase: true,
            showHeader: true,
            showBtn: false,     //不显示下拉按钮
            delayUntilKeyup: true, //获取数据的方式为 firstByUrl 时，延迟到有输入/获取到焦点时才请求数据
            idField: "name",
            keyField: "name",
            clearable: false
        }).on('onDataRequestSuccess', function (e, result) {

        }).on('onSetSelectValue', function (e, keyword, data) {
            $('#casesteps').data('bootstrapValidator').updateStatus(operationindex, 'NOT_VALIDATED', null).validateField(operationindex);
        }).on('onUnsetSelectValue', function () {

        });

    }

    function showdebug() {
        $("#debug").modal('show');
        getClientpath();
    }

    function debugcase() {
        // 异步提交数据到action页面
        $.ajax({
            type: "POST",
            cache: false,
            async: true,
            dataType: "json",
            url: "debugcase.do?casesign=${casesign}" + "&clientip=" + $("#clientipfordebug").val() + "&clientpath=" + $("#clientpathfordebug").val(),
            contentType: "application/json", //必须有
            data: {},
            success: function (data, status) {
                if (data.status == "success") {
                    toastr.success(data.ms);
                    refreshlog();
                } else if (data.status == "info") {
                    toastr.info(data.ms);
                } else if (data.status == "warning") {
                    toastr.warning(data.ms);
                } else if (data.status == "error") {
                    toastr.error(data.ms);
                }
            },
            error: function () {
                toastr.error(data);
            }
        });
    }

    //ajax
    function refreshlog() {
        $.ajax({
            type: "POST",
            cache: false,
            async: true,
            dataType: "json",
            url: "refreshlog.do?casesign=${casesign}",
            contentType: "application/json", //必须有
            data: {},
            success: function (data, status) {
                if (data.status == "success") {
                    document.getElementById('autoDown').innerHTML = data.ms;
                    document.getElementById('autoDown').scrollTop = document.getElementById('autoDown').scrollHeight;
                } else if (data.status == "info") {
                    document.getElementById('autoDown').innerHTML = data.ms;
                    document.getElementById('autoDown').scrollTop = document.getElementById('autoDown').scrollHeight;
                    toastr.info("调试程序运行完成！");
                    clearTimeout(t);
                } else if (data.status == "warning") {
                    toastr.warning(data.ms);
                } else if (data.status == "error") {
                    toastr.error(data.ms);
                }
            },
            error: function () {
                toastr.error(data);
            }
        });

        var t = setTimeout("refreshlog()", 1500);
    }


    //按上级ID取子列表
    function getClientpath() {
//		    clearSel(); //清空节点
        if (jQuery("#clientipfordebug").val() == "") return;
        var clientip = jQuery("#clientipfordebug").val();
        var url = "/testClient/getclientpathlist.do?clientip=" + clientip;
        jQuery.getJSON(url, null, function call(result) {
            clearSel();
            setClientpath(result);
        });

    }

    //设置子列表
    function setClientpath(result) {
        var options = "";
        jQuery.each(result.data, function (i, node) {
            options += "<option value='" + node + "'>" + node + "</option>";
        });
        jQuery("#clientpathfordebug").html(options);
    }

    // 清空下拉列表
    function clearSel() {
        while (jQuery("#clientpathfordebug").length > 1) {
            $("#clientpathfordebug option[index='1']").remove();
            //	 document.getElementById("checkentry").options.remove("1");
        }
    }
    
	function sample(){
		$("#sample_params").modal('show');
	}
</script>
</body>
</html>
