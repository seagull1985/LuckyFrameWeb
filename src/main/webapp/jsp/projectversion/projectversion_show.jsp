<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目版本详情</title>

<link href="/js/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="/js/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/easyui/locale/easyui-lang-zh_CN.js"></script>

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
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">质量管理</li>
			<li class="active"><a href="/projectVersion/load.do">版本信息</a></li>
			<li class="active">项目版本详情</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">项目版本详情</h1>
			</header>
			
<div id="mydiv"  >
 	<table width="70%"  align="center" class="rect"  frame="box" cellPadding=5 border=1 style="background-color:rgba(240,255,240,0.5);table-layout:fixed;">
  
    <tr>
      <td width="20%" height="40" style="white-space:nowrap;overflow:hidden;word-break:break-all;"><b>项目名称：</b></td>
      <td width="20%" height="40">${projectversion.sectorProjects.projectname}</td>
      <td width="20%"><b>版本号：</b></td>
    <%--   <td width="20%">${projectversion.versionnumber}</td> --%>
       <c:choose>
		<c:when test="${fn:containsIgnoreCase(projectversion.zt_versionlink, 'http://')}">
	    <td height="25" ><a href=${projectversion.zt_versionlink } target=_blank style="text-decoration: none;color:#0000FF;">${projectversion.versionnumber }</a>&nbsp;</td>
		</c:when>
		<c:otherwise>
		<td height="25" >${projectversion.versionnumber }&nbsp;</td>
		</c:otherwise>
		</c:choose>
      </tr>      
    <tr>
      <td width="20%" height="40"><b>计划上线需求数：</b></td>
      <td height="40">${projectversion.plan_demand}</td>
		<td height="30" ><b>实际上线需求数：</b></td>
	 	<td height="30" >${projectversion.actually_demand}</td>
	</tr>
	 <tr>
      <td width="20%" height="40" style="white-space:nowrap;overflow:hidden;word-break:break-all;"><b>代码规范问题(阻断)：</b></td>
      <td height="40">${projectversion.codestandard_zd}&nbsp;&nbsp;&nbsp;&nbsp; <font color="#95CACA" size="1">以SONAR平台统计数据为准</font></td>
      <td width="20%" height="40"><b>代码规范问题(严重)：</b></td>
      <td height="40">${projectversion.codestandard_yz}&nbsp;&nbsp;&nbsp;&nbsp; <font color="#95CACA" size="1">以SONAR平台统计数据为准</font></td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>代码规范问题(主要)：</b></td>
      <td height="40">${projectversion.codestandard_zy}&nbsp;&nbsp;&nbsp;&nbsp; <font color="#95CACA" size="1">以SONAR平台统计数据为准</font></td>
      <td width="20%" height="40"><b>代码变动行数：</b></td>
      <td height="40">${projectversion.codeline} &nbsp;&nbsp;&nbsp;&nbsp;<font color="#95CACA" size="1">单位：千行</font></td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>测试用例数：</b></td>
    <td height="40">${projectversion.testcasenum}</td>
      <td width="20%" height="40"><b>转测试打回次数：</b></td>
      <td height="40">${projectversion.changetestingreturn}</td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>投入开发人员：</b></td>
      <td height="40">${projectversion.dev_member}</td>
      <td width="20%" height="40"><b>投入测试人员：</b></td>
      <td height="40">${projectversion.test_member}</td>
    </tr>
    <tr>
      <td width="20%" height="40" ><b>投入人力资源：</b></td>
      <td height="40" >${projectversion.human_cost}&nbsp;&nbsp;&nbsp;&nbsp;  <font color="#95CACA" size="1">（人/天）</font></td>
      <td width="20%" height="40" ><b>需求达成率：</b></td>
      <td height="40" >${projectversion.perdemand}%&nbsp;&nbsp;&nbsp;&nbsp;  <font color="#95CACA" size="1">（实际需求/计划需求）</font></td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>开发平均生产率：</b></td>
      <td height="40">${projectversion.per_dev}&nbsp;&nbsp;&nbsp;&nbsp;  <font color="#95CACA" size="1">代码千行   人/天</font></td>
      <td width="20%" height="40"><b>测试平均生产率：</b></td>
      <td height="40">${projectversion.per_test}&nbsp;&nbsp;&nbsp;&nbsp;  <font color="#95CACA" size="1">用例数（执行）   人/天</font></td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>开发工期偏差：</b></td>
      <c:if test="${projectversion.devtime_deviation!=null }">
      <td height="40">${projectversion.devtime_deviation}% </c:if>
      <c:if test="${projectversion.devtime_deviation==null }">
      <td height="40">暂时无法计算 </c:if><br><font color="#95CACA" size="1">计算公式：（实际工期-计划工期）/计划工期</font></td>
      <td width="20%" height="40"><b>开发延期天数：</b></td>
      <td height="40">${projectversion.devdelay_days}&nbsp;&nbsp;天<br>  <font color="#95CACA" size="1">计算公式：实际完成日期-计划完成日期</font></td>
    </tr>
     <tr>
      <td width="20%" height="40"><b>测试工期偏差：</b></td>
      <c:if test="${projectversion.devtime_deviation!=null }">
      <td height="40">${projectversion.testtime_deviation}% </c:if>
      <c:if test="${projectversion.testtime_deviation==null }">
      <td height="40">暂时无法计算 </c:if><br>  <font color="#95CACA" size="1">计算公式：（实际工期-计划工期）/计划工期</font></td>
      <td width="20%" height="40"><b>测试延期天数：</b></td>
      <td height="40">${projectversion.testdelay_days}&nbsp;&nbsp;天<br>  <font color="#95CACA" size="1">计算公式：实际完成日期-计划完成日期</font></td>
    </tr>
      <tr>
      <td width="20%" height="40"><b>项目进度偏差：</b></td>
      <c:if test="${projectversion.devtime_deviation!=null }">
      <td height="40">${projectversion.protime_deviation}%</c:if>
      <c:if test="${projectversion.protime_deviation==null }">
      <td height="40">暂时无法计算 </c:if><br>  <font color="#95CACA" size="1">计算公式：（实际上线日期-计划上线日期）/总工期</font></td>
      <td width="20%" height="40"><b>项目上线延期天数：</b></td>
      <td height="40">${projectversion.prodelay_days}&nbsp;&nbsp;天<br>  <font color="#95CACA" size="1">计算公式：实际上线日期-计划上线日期</font></td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>致命级别问题</b></td>
      <td height="40">${projectversion.bug_zm} <font color="#95CACA" size="1">个</font></td>
      <td width="20%" height="40"><b>严重级别问题</b></td>
      <td height="40">${projectversion.bug_yz} <font color="#95CACA" size="1">个</font></td>
    </tr>
        <tr>
      <td width="20%" height="40"><b>一般级别问题</b></td>
      <td height="40">${projectversion.bug_yb} <font color="#95CACA" size="1">个</font></td>
      <td width="20%" height="40"><b>提示级别问题</b></td>
      <td height="40">${projectversion.bug_ts} <font color="#95CACA" size="1">个</font></td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>版本缺陷DI：</b></td>
      <td height="40" colspan="3">${projectversion.code_DI}&nbsp;&nbsp;&nbsp;&nbsp;<font color="#95CACA" size="1">按千行代码计算</font></td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>计划达成日期：</b></td>
      <td height="40">${projectversion.plan_launchdate}</td>
      <td width="20%" height="40"><b>实际达成日期：</b></td>
      <td height="40">${projectversion.actually_launchdate}</td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>计划开发开始日期：</b></td>
      <td height="40">${projectversion.plan_devstart}</td>
      <td width="20%" height="40"><b>计划开发结束日期：</b></td>
      <td height="40">${projectversion.plan_devend}</td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>实际开发开始日期：</b></td>
      <td height="40">${projectversion.actually_devstart}</td>
      <td width="20%" height="40"><b>实际开发结束日期：</b></td>
      <td height="40">${projectversion.actually_devend}</td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>计划测试开始日期：</b></td>
      <td height="40">${projectversion.plan_teststart}</td>
      <td width="20%" height="40"><b>计划测试结束日期：</b></td>
      <td height="40">${projectversion.plan_testend}</td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>实际测试开始日期：</b></td>
      <td height="40">${projectversion.actually_teststart}</td>
      <td width="20%" height="40"><b>实际测试结束日期：</b></td>
      <td height="40">${projectversion.actually_testend}</td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>版本说明：</b></td>
      <td height="40" valign="top" colspan="3" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;">${projectversion.imprint}</td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>质量回溯结果：</b></td>
      <td height="40" valign="top" colspan="3" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;">${projectversion.qualityreview}</td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>备注：</b></td>
      <td height="40" valign="top" colspan="3" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;">${projectversion.remark}</td>
    </tr>
    <tr>
      <td height="40" colspan="4" align="center"><a
						href="/projectVersion/load.do" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">返 回</span></a></td>
    </tr>
  </table>
 
</div>
	  			<p>&nbsp;</p>
	</article>
	</div> 
	</div>
</body>
</html>
