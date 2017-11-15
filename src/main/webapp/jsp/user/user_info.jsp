<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户信息</title>
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

<body>
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">个人信息</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">个人信息</h1>
			</header>   
			
					<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
					<div class="panel panel-default">

					<div class="panel-body">
							<h3 class="thin text-center">个人资料</h3>

							<div class="top-margin" style="font-size:16px">
								<label>姓名：&nbsp;&nbsp;&nbsp;&nbsp;${userinfo.username }</label>
							</div>
							<div class="top-margin" style="font-size:16px">
								<label>用户名：&nbsp;&nbsp;&nbsp;&nbsp;${userinfo.usercode }</label>
							</div>
							<div class="top-margin" style="font-size:16px">
								<label>所属部门：&nbsp;&nbsp;&nbsp;&nbsp;${userinfo.secondarySector.departmentname }</label>
							</div>
							<div class="top-margin" style="font-size:16px">
							<label>所属角色：&nbsp;&nbsp;&nbsp;&nbsp;</label>
									<c:forEach items="${rolearr}" var="r">
									<label id="${r[0]}" >${r[1]}、</label>
								</c:forEach>
							</div>
							<div class="top-margin" style="font-size:16px">
								<label>权限范围：&nbsp;&nbsp;&nbsp;&nbsp;${userauth }</label>
							</div>
							
							<div class="row top-margin">
									<div class="col-lg-4 text-center" style="width:900px">
										<a href="../userInfo/updatepw.do" 
										style="text-decoration: none;"> <span class="btn btn-action" > 修改密码</span></a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="../userInfo/updateproject.do" 
										style="text-decoration: none;"> <span class="btn btn-action" > 切换默认项目</span></a>		
									</div>
								</div>
								
						</div>
					</div>

				</div>
				
  	<p>&nbsp;</p>
	</article>
	</div>  
    </div>
</body>
</html>
