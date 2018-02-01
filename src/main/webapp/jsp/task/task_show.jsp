<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>调度任务详情</title>

<link href="/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {	font-size: 12px;
	color: #ffffff;
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
	<div class="container" style="width:auto;">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">UTP</li>
			<li class="active"><a href="/testJobs/load.do">调度任务</a></li>
			<li class="active">调度任务详情</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">调度任务详情</h1>
			</header>
		
	
<div id="mydiv"  >	
 	<table width="70%"  height=454 border=1  align="center" cellPadding=1 cellspacing="0" bordercolor="#CCCCCC" class="rect">
  
    <tr>
      <td width="20%" height="40">计划名称</td>
      <td width="534" height="40">${taskjob.taskName}        </td>
      </tr>
     <tr>
      <td width="20%" height="40">自动化类型</td>
      <c:if test="${taskjob.extype==0 }">
		 <td height="25" style="color:#FFA54F;">接口自动化</td>
	  </c:if>
	  <c:if test="${taskjob.extype==1 }">
		 <td height="25" style="color:#FFA54F;">Web自动化</td>
	  </c:if>
	  <c:if test="${taskjob.extype==2 }">
		 <td height="25" style="color:#FFA54F;">移动端自动化</td>
	  </c:if>
    </tr>
    <tr>
      <td width="20%">客户端IP </td>
      <td>${taskjob.clientip}</td>
    </tr>
      
    <tr>
      <td width="20%" height="40">项目名（testlink中）</td>
      <td height="40">${taskjob.planproj}</td>
    </tr>
    <tr>
		<td height="30" align="left" valign="top">计划名（testlink中）</td>
	 	<td height="30" colspan="3">${taskjob.testlinkname }</td>
	</tr>
    <tr>
      <td width="20%" height="40">计划描述</td>
      <td height="40">${taskjob.remark}</td>
    </tr>
    <tr>
      <td width="20%" height="40">执行次数</td>
    <td height="40">
         <c:if test="${taskjob.taskType=='O' }">
                    只执行一次                    </c:if>
                    <c:if test="${taskjob.taskType=='D' }">
                   	每天执行                    </c:if>      </td>
    </tr>
    <%-- 
    <tr>
      <td height="40">执行日期</td>
      <td height="40">${taskjob.date" id="date" class="Wdate" onClick="WdatePicker({isShowClear:false})">      </td>
    </tr>
    <tr>
      <td height="40">时间</td>
      <td height="40">${taskjob.time" id="time3"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm:ss',isShowClear:false})" class="Wdate"/>      </td>
    </tr>
  - <tr>
      <td>执行</td>
      <td><select name="taskType" id="taskType">
        <option value="O">一次</option>
        <option value="D">每天</option>
      <option value="W">每周</option>
        <option value="M">每月</option>
      </select>
      </td>
    </tr>--%>
    	<tr >
              <td height="30" align="left">线程数</td>
			  <td height="30" >${taskjob.threadCount}
			    (线程数控制在20以内)</td>
		  </tr>
    
     <tr>    
      <td width="20%" height="40">执行结果是否发送邮件</td>
    <td height="40">
         <c:if test="${taskjob.isSendMail=='0' }">
                 	不发送                    </c:if>
                    <c:if test="${taskjob.isSendMail=='1' }">
                   	发送【收件人：${taskjob.emailer }】                    </c:if>      </td>
    </tr>
    
         <tr>     
     <td width="20%" height="40">项目在计划任务中是否自动构建</td>
    <td height="40">
         <c:if test="${taskjob.isbuilding=='0' }">
                 	不自动构建                    </c:if>
                    <c:if test="${taskjob.isbuilding=='1' }">
                   	自动构建【构建项目：${taskjob.buildname }】                    </c:if>      </td>
    </tr>
    
     <tr>     
     <td width="20%" height="40">TOMCAT在计划任务中是否自动重启</td>
    <td height="40">
         <c:if test="${taskjob.isrestart=='0' }">
                 	不自动重启                    </c:if>
                    <c:if test="${taskjob.isrestart=='1' }">
                   	自动重启【重启命令：${taskjob.restartcomm }】                    </c:if>      </td>
    </tr>
    
    <tr>
      <td width="20%" height="40">开始时间</td>
      <td height="40">${taskjob.startDate}
        
        ${taskjob.startTime}</td>
    </tr>
    <tr>
      <td width="20%" height="40">结束时间</td>
      <td height="40">${taskjob.endDate}
        
        ${taskjob.endTime}</td>
    </tr>
    <tr>
      <td width="20%" height="40">&nbsp;</td>
      <td height="40" align="center"><a
						href="/testJobs/load.do" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">返 回</span></a></td>
    </tr>
  </table>
 
</div>
<p>&nbsp;</p>
	</article>
	</div>
	</div>
</body>
</html>
