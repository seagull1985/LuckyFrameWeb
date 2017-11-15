<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" type="text/javascript"
	src="/js/My97DatePicker/WdatePicker.js"></script>


<title>禅道任务</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {
	color: #ffffff
}

.STYLE2 {
	color: #FF0000
}

.STYLE6 {
	color: #FF0000;
	font-size: 12px;
}
-->
</style>

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
			<li class="active">质量管理</li>
			<li class="active"><a href="/projectVersion/load.do">版本信息</a></li>
			<li class="active">禅道任务</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">禅道任务</h1>
			</header>
			
	<sf:form method="post" modelAttribute="zt">
		<input name="page" id="page" type="hidden" />
		<input name="versionid" id="versionid" value=0 type="hidden" />
		<input name="finishedby" id="finishedby" type="hidden" />
		<br>
		<table width="100%" align="center" class="bordered">
			<tr>
				<th width="10%" height="30" nowrap="nowrap"
					style="background-color: #8DB6CD; border-right: 1px solid #ccc;"">版本名称</th>
				<th width="25%" height="30" nowrap="nowrap"
					style="background-color: #8DB6CD; border-bottom: 1px solid #ccc;">任务名称</th>
				<th width="10%" height="30" nowrap="nowrap"
					style="background-color: #8DB6CD; border-bottom: 1px solid #ccc;">任务完成时间</th>
				<th width="5%" height="30" nowrap="nowrap"
					style="background-color: #8DB6CD; border-bottom: 1px solid #ccc;">计划完成日期</th>
				<th width="5%" height="30" nowrap="nowrap"
					style="background-color: #8DB6CD; border-bottom: 1px solid #ccc;">计划工时</th>
				<th width="5%" height="30" nowrap="nowrap"
					style="background-color: #8DB6CD; border-bottom: 1px solid #ccc;">实际工时</th>
				<th width="5%" height="30" nowrap="nowrap"
					style="background-color: #8DB6CD; border-bottom: 1px solid #ccc;">完成人</th>
			</tr>
			<c:forEach var="t" items="${zttasklist}" begin="0" step="1"
				varStatus="i">
				<tr>
					<td height="25" align="center">${t.versionname }&nbsp;</td>
					<c:choose>
						<c:when test="${t.delaystatus!=1}">
							<td height="25" align="center" style="color: #FF0000;">${t.taskname }&nbsp;</td>
						</c:when>
						<c:otherwise>
							<td height="25" align="center">${t.taskname }&nbsp;</td>
						</c:otherwise>
					</c:choose>
					<td height="25" align="center">${t.assignedDate }&nbsp;</td>
					<td height="25" align="center">${t.deadline }&nbsp;</td>
					<td height="25" align="center">${t.estimate }&nbsp;</td>
					<td height="25" align="center">${t.consumed }&nbsp;</td>
					<td height="25" align="center">${t.finishedname }&nbsp;</td>
				</tr>
			</c:forEach>
		</table>
		<center>
			<div id="pagelist" align="center">
				<c:if test="${allRows!=0 }">
					<ul>
						<li><a href="#" onclick="return setPage(1)">首页 </a></li>
						<li><a href="#" onclick="return frontPageCheck(${page-1});">上一页</a></li>
						<li><a href="#" onclick="return backPageCheck(${page+1});">下一页</a></li>
						<li><a href="#" onclick="return setPage(${allPage})">末页</a></li>
						<li>第${page}页</li>
						<li>共${allRows}条</li>
						<li>共${allPage}页</li>
					</ul>
				</c:if>
				<c:if test="${allRows==0 }">
					<font color="#FF0000">没有记录!</font>
				</c:if>
			</div>
			<br />
			<br />
		</center>
	</sf:form>
	  			<p>&nbsp;</p>
	</article>
	</div> 
	</div>
	
	<script type="text/javascript">
	function frontPageCheck(page)
	{
		if(${page > 1})
		{
			document.getElementById("page").value=page;
			document.getElementById("zt").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("zt").submit();
			//$("#projectversion").submit();
			return true;
		}			
		return false;
	}
	
	
	function setPage(page){
		if(page==1){
			document.getElementById("page").value=1;
		}else{
			document.getElementById("page").value=page;
		}
		document.getElementById("zt").submit();
		return true;
	}
	</script>
</body>
</html>