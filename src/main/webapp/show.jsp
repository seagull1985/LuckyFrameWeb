<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script Charset="utf-8" type="text/javascript"
	src="/js/jquery-1.7.2.js"></script>
<script language="JavaScript" type="text/javascript"
	src="/js/My97DatePicker/WdatePicker.js"></script>	
	
<title>项目版本信息对比</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {
	color: #ffffff
}
.STYLE6 {color: #FF0000; font-size: 12px; }
-->
</style>
	</head>
<body>
	<div>  
        <%@ include file="/head.jsp" %>
    </div>
    
    <header id="head" class="secondary"></header>    
	<div class="container" style="width:auto;">
		<ol class="breadcrumb">
			<li><a href="/index.jsp">主页</a></li>
			<li class="active">show</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">show</h1>
			</header>
			 
<table width="100%" align="center" class="bordered">
<tr>
	<td height="25" align="center">
	<img alt="" src="/pic/show/${filename }" width="1000" height="500" />
	</td>
	</tr>
	</table>
	
	<p>&nbsp;</p>
	</article>
	</div>
	</div>
</body>
</html>