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
	function init() {
		if ('${message}' != '') {
			toastr.info('${message}');
		}
	}
</script>
</head>

<body onload="init()">
	<div>
		<%@ include file="/head.jsp"%>
	</div>
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width: auto;">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">UTP</li>
			<li class="active"><a href="../testJobs/load.do">调度配置</a></li>
			<li class="active">上传测试驱动桩</li>
		</ol>

		<div class="row">
			<!-- Article main content -->
			<article class="col-sm-9 maincontent" style="width:100%;">
			<header class="page-header">
			<h1 class="page-title" style="text-align: center;">上传测试驱动桩</h1>
			</header>

			<form action="/testJobs/upload.do?clientip=${clientip}" method="post"
				enctype="multipart/form-data">
				<table width="100%" align="center" class="rect" height=40
					cellPadding=1 border=1 bordercolor="#CCCCCC">
					<tr>
						<td colspan="2" align="center"
							style="font-size: 26px; color: red;">您将把测试驱动JAR包上传至
							${clientip} 的客户端</td>
					</tr>
					<tr>
						<td height="30" align="left" width="18%">测试驱动桩在客户端项目的路径</td>
						<td height="30" width="82%">
						<select name="clientpath" id="clientpath" style="width:280px">
								<c:forEach var="p" items="${pathlist}">
									<option value="${p}">${p}</option>
								</c:forEach>
							</select></td>
					</tr>
					<tr>
						<td width="18%" height="40">请选择文件</td>
						<td width="82%" height="40"><input type="file" name="file" />
							<span class="STYLE6">（只能上传.jar的文件！）</span></td>
					</tr>
					<tr>
						<td height="40">&nbsp;</td>
						<td height="40"><table width="100%" border="0" align="left"
								cellpadding="0" cellspacing="0">
								<tr>
									<td width="31%" height="40"><input type="submit"
										value="上传" /></td>
									<td width="69%" height="40" class="STYLE6">&nbsp;</td>
								</tr>
							</table></td>
					</tr>
				</table>
				<table width="98%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="55%" height="53" valign="bottom"><span
							class="STYLE6">温馨提示：</span></td>
					</tr>
					<tr>
						<td valign="middle"><p class="STYLE6">1、若各自项目中的自动化驱动桩代码有更新，将项目重新打包后上传至对应的客户端IP即可，此JAR包将会自动保存至客户端的LIB目录下</p>
							<p class="STYLE6">2、重新运行即是最新的代码。</p>
							<p class="STYLE6">3、打包项目的名称最好与项目名一致。</p>
							<p class="STYLE6">4、如果出现方法或是类找不到，那么有可能是您的LIB目录中存在方法或是类冲突的情况，可以使用findJAR.cmd进行排除</p>
							<p class="STYLE6">5、打包流程如下：根据箭头的流程来打包：</p>
							<p class="STYLE6">
								<img src="../pic/test_upload/1.png" width="728" height="466" />
							</p>
							<p class="STYLE6">
								<img src="../pic/test_upload/2.png" width="531" height="552" />
							</p>
							<p class="STYLE6">
								<img src="../pic/test_upload/3.png" width="563" height="694" />
							</p> <!-- <p class="STYLE6"><img src="../pic/test_upload/4.png" width="32" height="32" /></p> -->
							<p class="STYLE6">
								<img src="../pic/test_upload/5.png" width="563" height="694" />
							</p>
							<p class="STYLE6">
								<img src="../pic/test_upload/6.png" width="563" height="694" />
							</p>
							<p class="STYLE6">
								<img src="../pic/test_upload/7.png" width="563" height="694" />
							</p>
							<p class="STYLE6">
								<img src="../pic/test_upload/8.png" width="900" height="500" />
							</p></td>
					</tr>
				</table>
				<p>&nbsp;</p>
			</form>
			</article>
		</div>
	</div>
</body>
</html>
