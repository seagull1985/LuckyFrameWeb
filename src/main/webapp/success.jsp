<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>提示信息</title>
<style type="text/css">
<!--
.STYLE1 {color: #CC0000}
-->
</style>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
    
	<header id="head" class="secondary"></header>    
	<div class="container" style="width:auto;">
		<ol class="breadcrumb">
			<li><a href="/index.jsp">主页</a></li>
			<li class="active">操作提示</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">操作提示</h1>
			</header>
			
<div align="center" style="margin-top:10px; padding:10px 200px;" >
 <table width="400" border="0" align="center" cellspacing="0">
      <tr>
        <td width="100" height="100" align="center"><img src="../../pic/sussess.png" width="100" height="100"></td>
              </tr>
               <tr>
        <td width="269" height="100" align="center">&nbsp;&nbsp;&nbsp;
          <h2 class="STYLE1" style="align:center"> ${message}</h2></td>
      </tr>
   </table>
 <p>&nbsp;</p>
 <p><a href="${url}" class="btnold"  style="text-decoration:none;" >确定</a></p>
</div>

     <p>&nbsp;</p>
	</article>
	</div>
	</div>
</body>
</html>