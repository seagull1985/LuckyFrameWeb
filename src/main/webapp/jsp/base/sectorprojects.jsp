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
	
<title>项目管理</title>
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

	<!-- container -->
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active"><a href="/userInfo/list.do">用户管理</a></li>
			<li class="active">项目管理</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">项目管理</h1>
			</header>
			
 <sf:form method="post" modelAttribute="sectorprojects">
 <input name="page" id="page" type="hidden"  />	
 		<table width="100%" align="center" class="rect" height=40 cellPadding=1 border=1 bordercolor="#CCCCCC">
		  <tr>
		  <td width="25%" align="center" valign="middle">
            <a href="#" onclick="showDiv(0,1)" style="text-decoration: none;"> 
			<span class="btnold STYLE1" style="width:70px;background:#FFA54F;border:#FFA54F;"> 添加项目</span></a>			
		  </td>
		</tr>
		  </table>
		  
	   <table width="100%" align="center" class="bordered">
                <tr bgcolor="#B9DCFF">
				<th width="5%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">项目编号</th>
				<th width="35%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">项目名称</th>
				<th width="10%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">项目经理</th>
				<th width="10%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">项目类型</th>
				<th width="10%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">所属部门</th>
				<th width="10%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">部门经理</th>
				<th width="20%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">操作</th>
		  </tr>
		  <c:forEach var="t" items="${splist}" begin="0" step="1"
				varStatus="i">
			  <tr>
				  <td height="25" style="text-align:center">${t.projectid }&nbsp;</td>
				  <td height="25" >${t.projectname }&nbsp;</td>
				  <td height="25" >${t.projectmanager }&nbsp;</td>
				  <c:if test="${t.projecttype==0 }">
				  <td height="25" >质量管理项目&nbsp;</td>
				  </c:if>
				  <c:if test="${t.projecttype==1 }">
				  <td height="25" >自动化管理项目(testlink)&nbsp;</td>
				  </c:if>
				  <td height="25" >${t.secondarySector.departmentname }&nbsp;</td>
				  <td height="25" >${t.secondarySector.departmenthead }&nbsp;</td>
				  <td height="25" style="word-break: break-all">
						<a href="#" onclick="showDiv('${t.projectid}','2')"
						style="cursor: pointer;"><u>修改</u></a>&nbsp; <a href="#"
						onclick="showDiv('${t.projectid}','3')" style="cursor: pointer;"><u>删除</u></a>&nbsp;
					</td>
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
	
	function showDiv(proid,opr){
		var status = document.getElementById("loginstatus").value;
		if(status=="false"){
			if(window.confirm("你未登录哦，要先去首页登录吗？")){
				var url = '/progressus/signin.jsp';
				window.location.href=url;
			}else{
				return false; 
			} 	
		}else{
		if(opr=="1"){
			var url = '/sectorProjects/add.do';
			window.location.href=url;
	    }else if(opr=="2"){
			var url = '/sectorProjects/update.do?projectid='+proid;
			window.location.href=url;
	    }else if(opr=="3"){
	    	if(window.confirm("将会把项目关联的操作日志一起删除，确定要删除吗？")){ 
				var url = '/sectorProjects/delete.do?projectid='+proid;
				window.location.href=url;
	    		return true; 
	    		}else{ 
	    		return false; 
	    		}
	    }else{ 
	       alert("操作码有误，是否有非法操作，请联系软件质量室！"); 
	       return false;
	      }
		}
	}
	</script>
</body>
</html>