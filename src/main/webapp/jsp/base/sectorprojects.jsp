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
	
	<title>项目信息</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {
	color: #ffffff
}
.STYLE6 {color: #FF0000; font-size: 12px; }
-->
</style>
<script type="text/javascript">
	function frontPageCheck(page)
	{
		if(${page > 1})
		{
			document.getElementById("page").value=page;
			document.getElementById("sectorprojects").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("sectorprojects").submit();
			return true;
		}			
		return false;
	}
	
	
	function setPage(page)
	{
		if(page==1){
			document.getElementById("page").value=1;
		}else{
			document.getElementById("page").value=page;
		}
			document.getElementById("sectorprojects").submit();
		return true;
	}
	function goVersionList(projectid){
		alter("fdsa");
		document.getElementById("sectorprojects").action="/projectVersion/list.do";
		document.getElementById("projectid").value=projectid;
		document.getElementById("sectorprojects").submit();
		return true;
	}
	
	</script>
	
	</head>

<body>	
	 <div align="center" style="width: 100%">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
			<td width="12%" height="30"align="left" valign="baseline"><h2>所属室信息</h2></td>
		  </tr>
		</table>
	 </div>
 <sf:form method="post" modelAttribute="sectorprojects">
	   <table width="100%" align="center" class="bordered">
                <tr bgcolor="#B9DCFF">
				<th height="40" nowrap="nowrap" bgcolor="#B9DCFF">项目编号</th>
				<th height="40" nowrap="nowrap">项目名称</th>
				<th height="40" nowrap="nowrap" bgcolor="#B9DCFF">项目经理</th>
				<th height="40" nowrap="nowrap" bgcolor="#B9DCFF">所属室</th>
				<th height="40" nowrap="nowrap" bgcolor="#B9DCFF">室经理</th>
		  </tr>
		  <c:forEach var="t" items="${splist}" begin="0" step="1"
				varStatus="i">
			  <tr>
				  <td height="25" align="center">${t.projectid }&nbsp;</td>
				  <td height="25" align="center">${t.projectname }&nbsp;</td>
				  <td height="25" align="center">${t.projectmanager }&nbsp;</td>
				  <td height="25" align="center">${t.secondarySector.departmentname }&nbsp;</td>
				  <td height="25" align="center">${t.secondarySector.departmenthead }&nbsp;</td>
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
					<font color="#FF0000">没有记录!</font>				</c:if>
			</div></center>
				</sf:form>
</body>
</html>