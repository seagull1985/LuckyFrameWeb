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
<title>项目流程检查信息</title>
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
			<li class="active">项目流程检查信息</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">项目流程检查信息</h1>
			</header>
	
<div id="bg" class="bg" style="display:none;"></div>					
	 <sf:form method="post" modelAttribute="flowcheck">
		<input name="page" id="page" type="hidden"  />					
		<div align="right"></div>
	     <table width="100%" align="center" class="rect" height=40 cellPadding=1 border=1 bordercolor="#CCCCCC">
		  <tr>
				<td width="10%" align="left" valign="middle"><font size="2"
					color="black">项目名称:&nbsp;&nbsp;</font> <sf:select path="projectid" width="10%">
						<sf:option value="0">全部</sf:option>
						<c:forEach var="p" items="${projects }">
							<sf:option value="${p.projectid}">${p.projectname}</sf:option>
						</c:forEach>
					</sf:select> &nbsp;&nbsp;<font size="2" color="black">检查日期:&nbsp;&nbsp;</font>
					<sf:input path="checkstartdate" id="checkstartdate" onclick="WdatePicker({isShowClear:false,isShowClear:true,readOnly:true});"
						readonly="true" style="width:100px" value="" />&nbsp;至 <sf:input path="checkenddate" id="checkenddate"
						onclick="WdatePicker({isShowClear:false,isShowClear:true,readOnly:true});" readonly="true" style="width:100px" value="" />
					&nbsp;&nbsp; <input name="button" style="width:100px;height:26px"
					type="submit" class="button gray" id="button" align="right"
					value="检查记录查询" /></td>
	
				<!--  onClick="javascript:window.open('/flowCheck/add.do','_blank')"  -->
				 </tr>
			<tr>
			<td width="10%" align="left" valign="middle" colspan="3">
			<font size="2" color="black">查询日期段:&nbsp;&nbsp;</font>
				 <input name="perstartdate" id="perstartdate" onclick="WdatePicker({isShowClear:false,readOnly:true});"
						readonly="readonly" style="width:100px" value="" />&nbsp;至 <input name="perenddate" id="perenddate"
						onclick="WdatePicker({isShowClear:false,readOnly:true});" readonly="readonly" style="width:100px" value="" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                 <a href="#" onclick="javascript:window.location.href='/flowCheck/barchart_html5.do?checkstartdate='+$('#perstartdate').val()+'&checkenddate='+$('#perenddate').val()" style="text-decoration: none;"> <span
						class="btnold STYLE1" style="width: 70px; ">生成数据图表 </span>
				</a>&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="javascript:window.location.href='/flowCheck/report_count.do?checkstartdate='+$('#perstartdate').val()+'&checkenddate='+$('#perenddate').val()" style="text-decoration: none;"> <span
						class="btnold STYLE1" style="width: 70px; ">生成统计报表 </span>
				</a>&nbsp;&nbsp;</td>
				 </tr>
				 <tr>
				 <td width="5%" align="center" style="vertical-align:middle">
				<a	href="javascript:window.location.href='/planflowCheck/list.do';" ><span class="btnold STYLE1" style="width: 70px; background: #FFA54F; border: #FFA54F;">查看流程检查计划</span>
				</a>&nbsp;&nbsp;
				<a href="#" onclick="showDiv();" style="text-decoration: none;"> <span
						class="btnold STYLE1" style="width: 70px; background: #FFA54F; border: #FFA54F;">添加过程检查信息 </span>
				</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
				 </tr>
		</table>
		
	   <table width="100%" align="center" class="bordered">
          <tr bgcolor="#B9DCFF">                
				<th width="10%" height="40" nowrap="nowrap" >项目名称</th>
				<th width="10%" height="40" nowrap="nowrap" >版本号</th>
				<th width="10%" height="40" nowrap="nowrap" >第几次检查</th>
				<th width="10%" height="40" nowrap="nowrap" >首次检查日期</th>
				<th width="10%" height="40" nowrap="nowrap" >已检查项</th>
				<th width="10%" height="40" nowrap="nowrap" >合格项</th>
				<th width="10%" height="40" nowrap="nowrap" >不合格项</th>
				<th width="10%" height="40" nowrap="nowrap" >未检查项</th>
				<th width="10%" height="40" nowrap="nowrap" >一次性合格率</th>
		  </tr>
		  <c:forEach var="t" items="${splist}" begin="0" step="1"
				varStatus="i">
			  <tr>
			  <td height="25" align="center"><a href="/flowCheck/projectchecklist.do?projectid=${t[0] }&checkid=${t[1] }&versionnum=${t[6] }" style="cursor: pointer;"><u>${t[8] }</u></a></td>
				  
				  <td height="25" align="center"><a href="#" onclick="openDiv('${t[6] }','${t[0] }')"  style="text-decoration: none;color:blue;">${t[6] }&nbsp;</a></td>				  
				  <td height="25" align="center">第${t[1] }次&nbsp;</td>
				  <%-- <td height="25" align="center">${t.checkdate }&nbsp;</td> --%>
				  <td height="25" align="center">${t[2] }&nbsp;</td>			  
				  <td height="25" align="center">${t[3] }&nbsp;</td>
				  <td height="25" align="center">${t[4] }&nbsp;</td>	
				  <td height="25" align="center">${t[5] }&nbsp;</td>
				  <td height="25" align="center">${t[9] }&nbsp;</td>	
				  <td height="25" align="center">${t[7] }%&nbsp;</td>		
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
			document.getElementById("flowcheck").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("flowcheck").submit();
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
		document.getElementById("flowcheck").submit();
		return true;
	}
	
	function openDiv(verold,proid){
		var status = document.getElementById("loginstatus").value;
		if(status=="false"){
			if(window.confirm("你未登录哦，要先去登录吗？")){
				var url = '/progressus/signin.jsp';
				window.location.href=url;
			}else{
				return false; 
			}
		}else{
		var version=prompt("请输入修改的版本号",verold);
		if (version!=null && version!=""){
			var url = '/flowCheck/updateversion.do?versionold='+verold+'&versionnew='+version+'&projectid='+proid;
		    $.getJSON(url,null,function call(result){
		        var tt="";
		        $.each(result, function(k, v) {
		            tt += v;
		        })
                alert(tt);
		      });
		}
		}
	}
	
	function showDiv(){
		var status = document.getElementById("loginstatus").value;
		if(status=="false"){
			if(window.confirm("你未登录哦，要先去登录吗？")){
				var url = '/progressus/signin.jsp';
				window.location.href=url;
			}else{
				return false; 
			}
		}else{
			var url = '/flowCheck/add.do';
			window.location.href=url;
		}
	}
	</script>
</body>
</html>