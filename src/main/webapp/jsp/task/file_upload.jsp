<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>上传测试驱动桩</title>
<style type="text/css">
<!--
.STYLE6 {
	color: #FF0000;
	font-size: 12px;
}
-->
</style>

<link href="/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	function init(){
		if('${message}'!=''){
			alert('${message}');
		}
	}
	
</script>
</head>

<body  onload="init()">
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width:auto;">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">UTP</li>
			<li class="active"><a href="../testJobs/list.do">调度配置</a></li>
			<li class="active">上传测试驱动桩</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">上传测试驱动桩</h1>
			</header>
			
<form action="/testJobs/upload.do?clientip=${clientip}" method="post" enctype="multipart/form-data">
  <table width="100%" align="center" class="rect" height=40 cellPadding=1
			border=1 bordercolor="#CCCCCC">
  <tr>
    <td colspan="2" align="center" style="font-size:26px;color:red;">您将把测试驱动JAR包上传至 ${clientip} 的客户端</td>
    </tr>
  <tr>
    <td width="18%" height="40">请选择文件</td>
    <td width="82%" height="40"><input type="file" name="file" />
      <span class="STYLE6">（只能上传.jar的文件！）</span></td>
  </tr>
  <tr>
    <td height="40">&nbsp;</td>
    <td height="40"><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
        <tr>
          <td width="31%" height="40"><input type="submit" value="上传" /></td>
          <td width="69%" height="40" class="STYLE6">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="55%" height="53" valign="bottom"><span class="STYLE6">温馨提示：</span></td>
    </tr>
    <tr>
      <td valign="middle"><p class="STYLE6">1、若各自项目中的自动化用例代码有更新，将项目重新打包后上传至（10.211.19.55）服务器D:\web_task\TestFrame\lib目录。</p>
          <p class="STYLE6">2、重新运行即是最新的代码。</p>
      <p class="STYLE6">3、打包项目的名称最好与项目名一致。</p>
      <p class="STYLE6">４、如更新的项目中有新加入的jar包。【请把新加入的jar包】</p>
      <p class="STYLE6">以下以TestSettle项目为例，打包上传至服务器中的TestFrame项目中。</p>
      <p class="STYLE6">a、目前（2015-06-03为止）项目中的已经打好的包 有以下（以下项目打包文件名以此文件名为准）：</p>
      <p class="STYLE6"><img src="../pic/test_upload/lib.png" width="373" height="119" /></p>
      <p class="STYLE6">b、打包流程如下：根据箭头的流程来打包：</p>
      <p class="STYLE6"><img src="../pic/test_upload/1.png" width="728" height="466" /></p>
       <p class="STYLE6"><img src="../pic/test_upload/2.png" width="531" height="552" /></p>
        <p class="STYLE6"><img src="../pic/test_upload/3.png" width="563" height="694" /></p>
         <!-- <p class="STYLE6"><img src="../pic/test_upload/4.png" width="32" height="32" /></p> -->
          <p class="STYLE6"><img src="../pic/test_upload/5.png" width="563" height="694" /></p>
           <p class="STYLE6"><img src="../pic/test_upload/6.png" width="563" height="694" /></p>
            <p class="STYLE6"><img src="../pic/test_upload/7.png" width="563" height="694" /></p>
             <p class="STYLE6"><img src="../pic/test_upload/8.png" width="900" height="500" /></p>
              <p class="STYLE6">&nbsp;</p>
               <p class="STYLE6"><img src="../pic/test_upload/10.png" width="900" height="646" /></p>
      
      
      </td>
    </tr>
  </table>
  <img src="../pic/test_upload/11.png" width="874" height="355" />
  <p>&nbsp;</p>
</form>
	</article>
	</div>  
	</div>
</body>
</html>
