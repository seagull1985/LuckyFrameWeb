<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加角色权限</title>
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
			<li class="active">添加角色权限</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">添加角色权限</h1>
			</header>   
			
					<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
					<div class="panel panel-default">
						<div class="panel-body">
							<h3 class="thin text-center">添加角色权限</h3>

							<sf:form  modelAttribute="userrole" method="post" >
							<div class="top-margin">
								<label>角色名称<span class="text-danger">*</span></label>
								<sf:input type="text" class="form-control" path="role" id="role"/>
							</div>
							
							<div class="top-margin">
									<label style="color: blue">项目权限<span class="text-danger">*</span></label><br/>
									<c:forEach items="${projectlist}" var="t">
									<label>${t.projectname}</label>									
									<sf:checkbox path="opprojectid" id="prolist${t.projectid}" value="${t.projectid}" />&nbsp;&nbsp;&nbsp;&nbsp;
								</c:forEach>
							</div>
							
							<div class="top-margin">
									<label>权限 <span class="text-danger">*</span></label><br/>
									<c:forEach items="${authoritylist}" var="r">
									<label>${r.module}_${r.auth_type}</label>									
									<sf:checkbox path="permission" id="${r.id}" value="${r.alias}" />&nbsp;&nbsp;&nbsp;&nbsp;
								</c:forEach>
								</div>

</br>
								<div class="row">
									<div class="col-lg-4 text-center" style="width:900px">
										<button class="btn btn-action" type="submit">创建角色</button>
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
		if ('${message}' != '') {
			if ('${message}' == '添加成功') {
				toastr.success('添加成功,请返回查询！');
			} else {
				toastr.warning('${message}'); 
			}
		}

	}
</script>
</body>
</html>
