<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色权限</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/js/easyui/themes/default/easyui.css" rel="stylesheet"	type="text/css" />
<link href="/js/easyui/themes/icon.css" rel="stylesheet" type="text/css" />

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
<script language="JavaScript" type="text/javascript"
 src="/js/jslib.js"></script>

</head>

<body onload="init()">
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active"><a href="/userInfo/load.do">用户管理</a></li>
			<li class="active">角色权限</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">角色权限</h1>
			</header>   
			
					<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
					<div class="panel panel-default">
						<div class="panel-body">
							<h3 class="thin text-center">配置角色权限</h3>

							<sf:form  modelAttribute="userrole" method="post" onsubmit="return validTime(this)">
								<div class="top-margin">
								<label style="color: blue">角色 <span class="text-danger">*</span></label>
								<sf:select type="text" class="form-control"
									path="role"	id="role" onChange="getAuth()">
									<sf:option value="0">请选择</sf:option>
									<c:forEach var="p" items="${rolelist}">
										<sf:option value="${p.id}">${p.role}</sf:option>
									</c:forEach>
								</sf:select>
							</div>
							
							<div class="top-margin">
									<label style="color: blue">项目权限<span class="text-danger">*</span></label><br/>
									<c:forEach items="${projectlist}" var="t">
									<label>${t.projectname}</label>									
									<sf:checkbox path="opprojectid" id="prolist${t.projectid}" value="${t.projectid}" />&nbsp;&nbsp;&nbsp;&nbsp;
								</c:forEach>
							</div>
								
							<div class="top-margin">
									<label style="color: blue">操作权限 <span class="text-danger">*</span></label><br/>
									<c:forEach items="${authoritylist}" var="r">
									<label>${r.module}_${r.auth_type}</label>									
									<sf:checkbox path="permission" id="rolelist${r.id}" value="${r.alias}" />&nbsp;&nbsp;&nbsp;&nbsp;
								</c:forEach>
								</div>

</br>
								<div class="row">
									<div class="col-lg-4 text-center" style="width:900px">
										<button class="btn btn-action" type="submit">保存</button>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="#" onclick="delrole()" style="text-decoration: none;"> 
			                                      <span class="btn btn-action" > 删除角色</span></a>	
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

	function getAuth() {
		uncheckAll();
		if ($("#role").val() == "") return;
		var roleid = $("#role").val();
		var url = "/userInfo/getauth.do?roleId=" + roleid;
		$.getJSON(url, null, function call(result) {
			$.each(result.permi, function(i, node) {
				//  $("#rolelist"+node).attr("checked",true);  jq操作选中有时间会失效，用原生JS可以，奇怪
				document.getElementById('rolelist'+node).checked = true;
			});
			$.each(result.oppro, function(i, node) {
				document.getElementById('prolist'+node).checked = true;
			});	
		});
	}
	
	function delrole() {
		if ($("#role").val() == "") return;
    	if(window.confirm("你确认要删除吗？")){ 
    		var roleid = $("#role").val();
    		window.location.href = '/userInfo/roledelete.do?id=' +roleid;
    		return true;
    		}else{ 
    		return false; 
    		}
	}

	function uncheckAll() {
		var check_permi = document.getElementsByName("permission");
		var check_oppro = document.getElementsByName("opprojectid");
		for (i = 0; i < check_permi.length; i++) {
			if (check_permi[i].type == "checkbox") {
				check_permi[i].checked = false;				
			}
		}
		for (i = 0; i < check_oppro.length; i++) {
			if (check_oppro[i].type == "checkbox") {
				check_oppro[i].checked = false;				
			}
		}
	}

	function init() {
		if ('${message}' != '') {
			if ('${message}' == '添加成功') {
				toastr.success('添加成功,请返回查询！');
			} else {
				getAuth();
				toastr.warning('${message}'); 
			}
		}

	}
</script>
</body>
</html>
