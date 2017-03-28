<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" type="text/javascript"
	src="/js/My97DatePicker/WdatePicker.js"></script>	
<title>平台操作日志</title>
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
	<div class="container" style="width:auto;">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">关于</li>
			<li class="active">平台日志</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">平台操作日志</h1>
			</header>
	 
	 <sf:form method="post" modelAttribute="oplog">
		<input name="page" id="page" type="hidden"  />					
		<div align="right"></div>
	     <table width="100%" align="center" class="rect" height=40 cellPadding=1 border=1 bordercolor="#CCCCCC">
		  <tr>
				<td width="10%" align="left" valign="middle" ><font size="2"
					color="black">项目名称:&nbsp;&nbsp;</font> <sf:select path="projectid" width="10%">
						<sf:option value="0">全部</sf:option>
						<c:forEach var="p" items="${projects }">
							<sf:option value="${p.projectid}">${p.projectname}</sf:option>
						</c:forEach>
					</sf:select> &nbsp;&nbsp;&nbsp;<font size="2" color="black">时间:&nbsp;&nbsp;</font>
                 <input name="starttime" id="starttime" class="Wdate" onclick="WdatePicker({isShowClear:false,readOnly:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"  style="width:200px;height:90%" value="" />&nbsp;至
                 <input name="endtime" id="endtime" class="Wdate" onclick="WdatePicker({isShowClear:false,readOnly:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" style="width:200px;height:90%" value="" />
					&nbsp;&nbsp;&nbsp;
					<font size="2" color="black">操作人员:&nbsp;&nbsp;</font>
					<sf:input path="operationer" id="operationer" />&nbsp;&nbsp;&nbsp;
					<font size="2" color="black">描述(模糊查询):&nbsp;&nbsp;</font>
					<sf:input path="operation_description" id="operation_description" style="width:150px;"/>&nbsp;&nbsp;&nbsp;
					<input name="button" style="width:100px;height:26px" type="submit" class="button gray" id="button" align="right"
					value="日志查询" /></td>
				<!--  onClick="javascript:window.open('/flowCheck/add.do','_blank')"  -->
				 </tr>
		</table>
		
	   <table width="100%" align="center" class="bordered">
          <tr bgcolor="#B9DCFF">                
				<th width="5%" height="40" nowrap="nowrap" >项目名称</th>
				<th width="40%" height="40" nowrap="nowrap" >操作描述</th>
				<th width="5%" height="40" nowrap="nowrap" >操作账号</th>
				<th width="10%" height="40" nowrap="nowrap">操作时间</th>
		  </tr>
		  <c:forEach var="t" items="${splist}" begin="0" step="1"
				varStatus="i">
			  <tr>	  
				  <td height="25" align="center">${t.sectorProjects.projectname }&nbsp;</td>				  
				  <td height="25" align="center">${t.operation_description }&nbsp;</td>
				  <td height="25" align="center">${t.operationer }&nbsp;</td>
				  <td height="25" align="center">${t.operation_time }&nbsp;</td>		
			  </tr>
		  </c:forEach>
		</table>
<center>
			<div id="pagelist" align="center" style="font-size:15px;font-weight:600">
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
			document.getElementById("oplog").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("oplog").submit();
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
		document.getElementById("oplog").submit();
		return true;
	}

	</script>
	</body>
</html>