<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" type="text/javascript"
	src="/js/My97DatePicker/WdatePicker.js"></script>	

	
	<title>项目版本统计</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {
	color: #ffffff
}
.STYLE2 {
	color: #FF0000
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

	<!-- container -->
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">质量管理</li>
			<li class="active"><a href="/projectVersion/load.do">版本信息</a></li>
			<li class="active">项目版本质量报表</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">项目版本质量报表</h1>
			</header>

	   <table width="100%" align="center" class="bordered">
	   <tr> 
	   <th rowspan="2" width="10%" height="30" nowrap="nowrap" style="background-color:#8DB6CD;border-right:1px solid #ccc;"">项目名称</th>
	   <th colspan="3" height="30" nowrap="nowrap" style="background-color:#8DB6CD;border-bottom:1px solid #ccc;">需求完成情况(时间段内总计)</th>
	   <th colspan="3" height="30" nowrap="nowrap" style="background-color:#8DB6CD;border-bottom:1px solid #ccc;">版本完成情况(时间段内总计)</th>
	   <th colspan="2" height="30" nowrap="nowrap" style="background-color:#8DB6CD;border-bottom:1px solid #ccc;">工作量统计(时间段内总计)</th>
	   <th colspan="5" height="30" nowrap="nowrap" style="background-color:#8DB6CD;border-bottom:1px solid #ccc;">测试结果(时间段内总计)</th>
	    </tr>
          <tr>                
		<!-- <th width="10%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">项目名称</th> -->
				<th width="8%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">计划完成需求数</th>
				<th width="8%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">实际完成需求数</th>
				<th width="8%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">需求达成率</th>
				<th width="8%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">计划上线版本</th>
				<th width="8%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">按时完成版本</th>
				<th width="8%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">版本达成率</th>
				<th width="8%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">开发工作量</th>
				<th width="8%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">测试工作量</th>				
				<th width="10%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">转测试打回次数</th>
				<th width="5%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">致命BUG</th>
				<th width="5%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">严重BUG</th>
				<th width="5%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">一般BUG</th>
				<th width="5%" height="40" nowrap="nowrap" style="background-color:#8DB6CD;">提示BUG</th>
		  </tr>
		  <c:forEach var="t" items="${reportlist}" begin="0" step="1"
				varStatus="i">
			  <tr>
			  <td height="25" align="center">${t[0] }&nbsp;</td>				  				  
				  <td height="25" align="center">${t[1] }&nbsp;</td>
				  <td height="25" align="center">${t[2] }&nbsp;</td>			  
				  <td height="25" align="center">${t[12] }%&nbsp;</td>
				  <td height="25" align="center"><fmt:formatNumber value="${t[3]+t[4] }" />&nbsp;</td>	
				  <td height="25" align="center">${t[4] }&nbsp;</td>
				  <td height="25" align="center">${t[13] }%&nbsp;</td>
				  <td height="25" align="center">${t[10] }&nbsp;<font color="#95CACA" size="1">（人天）</font></td>
				  <td height="25" align="center">${t[11] }&nbsp;<font color="#95CACA" size="1">（人天）</font></td>
				  <td height="25" align="center">${t[5] }&nbsp;</td>
				  <td height="25" align="center">${t[6] }&nbsp;</td>
				  <td height="25" align="center">${t[7] }&nbsp;</td>
				  <td height="25" align="center">${t[8] }&nbsp;</td>
				  <td height="25" align="center">${t[9] }&nbsp;</td>		
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
			</div>
			<br/><br/>
			</center>
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
			document.getElementById("projectversion").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("projectversion").submit();
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
		document.getElementById("projectversion").submit();
		return true;
	}
	</script>	
</body>
</html>