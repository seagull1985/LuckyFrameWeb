<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用例日志步骤明细</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />

<style  type="text/css">
.title{
background-color: #CCCCCC;
background:#CCCCCC;
}
</style>
</head>

<body>
<div id="descriptiondiv" style="position:absolute;display:none;z-index:99900;width: 500px;">
<table width="100%"  align="center" class="rect"  style="background-color:rgba(240,255,240,0.8);table-layout:fixed;">
    <tr>
      <td id="descriptionid" valign="top" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;color:#FF9224;font-size:11pt">
      </td>
    </tr>
</table>
</div>

<sf:form method="post" modelAttribute="testLogdetail">

<table width="100%" align="right" class="log" style="table-layout: fixed" id="logtable">
<tr  class="title">
	 <th width="5%" height="25" align="center" valign="middle" class="title" nowrap="nowrap" >序列</th>
      <th width="12%" height="25" align="center" valign="middle"  class="title" nowrap="nowrap" >步骤</th>
      <th width="65%" height="25" align="center" valign="middle" nowrap="nowrap"  class="title" >日志明细</th>
      <th width="10%" height="25" align="center" valign="middle" class="title" nowrap="nowrap"  >用例执行时间</th>
      <th width="8%" height="25" align="center" valign="middle" class="title"  nowrap="nowrap" >日志等级</th>
    </tr>
			<c:forEach var="t" items="${list }"  begin="0" step="1" varStatus="i" >
				<tr>
				<td height="20" align="center">${i.index+1}&nbsp;</td>
				  <td height="20" align="center">
				  <div style=" width:80px;word-wrap:nowrap;word-break:normal; ">步骤_${t.step }</div>&nbsp;
				  </td>
				   <c:if test="${t.logGrade==\"info\"}">
				   <td height="20" align="left" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis" onmouseout="hiddenPic();" onmousemove="description(this);">${t.detail}</td>
				   </c:if>
				   <c:if test="${t.logGrade!=\"info\"}">
				   <td height="20" align="left" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;color:#ff0000" onmouseout="hiddenPic();" onmousemove="description(this);">${t.detail}</td>
				   </c:if>  
				  <td height="20"><fmt:formatDate value="${t.logtime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				  <td height="20" align="center">${t.logGrade}&nbsp;
				   <c:if test="${t.imgname!=\"\" }">
				    <a href="javascript:window.open('/logDetail/showImage.do?filename=${t.imgname}.png')">错误截图</a>
				   </c:if> 
				 </td>
			  </tr>
			</c:forEach>
		</table>
<center>
        <div  id="pagelist" align="center" style="margin-top:10px;"  >
          <c:if test="${allRows==0 }"> 
          	<font color="#FF0000" >没有记录!</font>
          </c:if> 
          </div>
          </center>
</sf:form>

<script type="text/javascript">

	function goBack(){
		document.getElementById("testLogdetail").action="/caseDetail/list.do";
		document.getElementById("testLogdetail").submit();
		return true;
	}

	var length=0;
	var lengthnew=0;
	function description(obj){
		length = document.body.scrollHeight;
		document.getElementById("descriptiondiv").style.left = (obj.offsetLeft+300)+"px"; 
		document.getElementById("descriptiondiv").style.top = (obj.offsetTop-20)+"px"; 
		document.getElementById("descriptionid").innerHTML = obj.innerHTML.replace(/(^\s*)|(\s*$)/g, "");		
		document.getElementById("descriptiondiv").style.display = "block"; 
		lengthnew = document.body.scrollHeight;
 		if(length!=lengthnew){
 			var tophight = document.getElementById("descriptiondiv").offsetHeight; 
 			document.getElementById("descriptiondiv").style.display = "none";
 			document.getElementById("descriptiondiv").style.left = (obj.offsetLeft+300)+"px"; 
 			document.getElementById("descriptiondiv").style.top = (obj.offsetTop-tophight+50)+"px"; 
 			document.getElementById("descriptiondiv").style.display = "block"; 
		} 

		} 
	
	function hiddenPic(){ 
		document.getElementById("descriptiondiv").style.display = "none"; 
		} 
	

</script>
</body>
</html>
