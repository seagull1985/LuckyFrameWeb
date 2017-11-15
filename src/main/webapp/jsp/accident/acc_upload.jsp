<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
<!--
.STYLE6 {
	color: #FF0000;
	font-size: 12px;
}
-->
</style>

<link href="/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body  onload="init()">
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
	<header id="head" class="secondary"></header>
	<!-- container -->
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">质量管理</li>
			<li class="active"><a href="/accident/load.do">生产故障</a></li>
			<li class="active">生产故障报告文档上传</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">生产故障报告文档上传</h1>
			</header>  
			
<form action="/accident/upload.do?id=${id}" method="post" enctype="multipart/form-data">
  <table width="100%" align="center" class="rect" height=40 cellPadding=1
			border=1 bordercolor="#CCCCCC">
  <tr>
    <td width="30%" height="40">请选择文件<span class="STYLE6">（只支持word或是pdf的文件！）</span></td>
    <td width="70%" height="40"><input type="file" name="file"/></td>
  </tr>
  <tr>
    <td height="40">&nbsp;</td>
    <td height="40"><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
        <tr>
          <td height="40"><input type="submit" class="button gray"  value="上传" /></td>
          <td height="40" class="STYLE6">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
     <p>&nbsp;</p>
	</article>
	</div>
	  </div>

<script type="text/javascript">
	function init(){
		if('${message}'!=''){
			alert('${message}');
		}
	}
</script>
</body>
</html>
