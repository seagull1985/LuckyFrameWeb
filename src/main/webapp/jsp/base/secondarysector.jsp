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
	
<title>测试二级部门</title>
	
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
			document.getElementById("secondarysector").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("secondarysector").submit();
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
			document.getElementById("secondarysector").submit();
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
	 
	   <table width="100%" align="center" class="bordered">
<tr bgcolor="#B9DCFF">
				<th height="40" nowrap="nowrap" bgcolor="#B9DCFF">部门ID</th>
				<th height="40" nowrap="nowrap">部门负责人</th>
				<th height="40" nowrap="nowrap" bgcolor="#B9DCFF">部门名称</th>
		  </tr>
		  <c:forEach var="t" items="${sslist}" begin="0" step="1"
				varStatus="i">
			  <tr>
				  <td height="25" align="center">${t.sectorid }&nbsp;</td>
				  <td height="25" align="center">${t.departmenthead }&nbsp;</td>
				  <td height="25" align="center">${t.departmentname }&nbsp;</td>

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
</body>
</html>