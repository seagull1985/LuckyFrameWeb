<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生产事故详情</title>

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
<style type="text/css">
<!--
html,body {height:100%; margin:0px; font-size:12px;}
.mydiv {
background-color: #F5FFE8;
border: 2px solid #C5C9C7;
text-align: center;
font-size: 12px;
font-weight: bold;
z-index:999;
width: auto;
top:10%;
left:10%;
height: 350px;
margin-left:-10px!important;/*FF IE7 该值为本身宽的一半 */
margin-top:50px!important;/*FF IE7 该值为本身高的一半*/
margin-top:0px;
position:fixed!important;/* FF IE7*/
position:absolute;/*IE6*/
_top:       expression(eval(document.compatMode &&
            document.compatMode=='CSS1Compat') ?
            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/

}

.bg,.popIframe 	{
background-color: #666; display:none;
width: 100%;
height: 100%;
left:0;
top:0;/*FF IE7*/
filter:alpha(opacity=10);/*IE*/
opacity:0.4;/*FF*/
z-index:1;
position:fixed!important;/*FF IE7*/
position:absolute;/*IE6*/
_top:       expression(eval(document.compatMode &&
            document.compatMode=='CSS1Compat') ?
            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);
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
			<li class="active"><a href="/accident/load.do">生产故障</a></li>
			<li class="active">生产故障详情信息</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">生产故障详情信息</h1>
			</header>  
			
<div id="bg" class="bg" style="display:none;"></div>

<div id="showaccident" class="mydiv" style="display:none;overflow-y:auto;width: 80%;align:center">
<td width="3%" height="30" align="center" valign="baseline"><h1>事故级别定义</h1></td>
<table width="100%" align="center" class="bordered" height="200px;">
    <tr>
      <td width="20%" height="5" style="text-align:center;background-color:#A3D1D1;"><font color="#C48888" size="3">事故级别</font></b></td>
      <td width="80%" style="text-align:center;background-color:#A3D1D1;"><font color="#C48888" size="3">级别定义</font></td>
      </tr>
       <tr>
      <td width="20%" height="5" style="text-align:center;"><b>一级事故</b></td>
      <td width="80%">系统<font color="#FF0000">大面积中断时间超过30分钟以上（含30分钟）</font>，因为系统故障造成极坏社会影响的。</td>
      </tr>
         <tr>
      <td width="20%" height="5" style="text-align:center;"><b>二级事故</b></td>
      <td width="80%">系统<font color="#FF0000">大面积中断时间在10-30分钟以内（含10 分钟）</font>。</td>
      </tr>
      <tr>
      <td width="20%" height="5" style="text-align:center;"><b>三级事故</b></td>
      <td width="80%">系统<font color="#FF0000">大面积中断时间在10分钟以内</font>。</td>
      </tr>
       <tr>
      <td width="20%" height="5" style="text-align:center;"><b>四级事故</b></td>
      <td width="80%">系统<font color="#FF0000">未造成大面积中断，小概率宕机事故</font>。</td>
      </tr>
      <tr>
      <td width="20%" height="5" style="text-align:center;"><b>五级及以下事故</b></td>
      <td width="80%">系统不涉及中断，<font color="#FF0000">系统一般问题级别以下</font>。</td>
      </tr>

		</table>
		<table width="100%" align="center" class="bordered"">
		      		<tr>
				<td width="10%" align="center" valign="middle" style="text-align:center;"><a
					 href="#" onclick="closeDiv()"  style="text-decoration: none;"> <span
						class="btnold STYLE1"  style="width: 420px;background-color:#A3D1D1;border:#A3D1D1"> 关闭 </span>
				</a></td>
				</tr>
		</table>
</div>
<div id="mydiv" style="width: 100%" >

 	<table width="70%"  align="center" class="rect"  frame="box" cellPadding=5 border=1 style="background-color:rgba(240,255,240,0.5);table-layout:fixed;">
  
    <tr>
      <td width="20%" height="40"><b>所属项目：</b></td>
      <td width="20%" height="40">${accident.sectorProjects.projectname}</td>
	  <td height="30" ><b>事故等级：</b></td>
	  <td height="30" >${accident.acclevel}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <a href="#" onclick="openDiv()"  style="text-decoration: none;color:#FF8000;">查看事故级别定义</a></td>
      </tr>      
    <tr>
      <td width="20%" height="40"><b>目前状态：</b></td>
      <td height="40" >${accident.accstatus}</td>
	  <td height="30" ><b>事故报告人：</b></td>
	  <td height="30" >${accident.reporter}</td>
	</tr>
    <tr>
      <td width="20%" height="40"><b>事故发生时间：</b></td>
      <td height="40" colspan="3" >${accident.eventtime}</td>
<%--       <td width="20%" height="40"><b>事故报告时间：</b></td>
      <td height="40">${accident.reporttime}</td> --%>
    </tr>
<%--     <tr>
      <td width="20%" height="40"><b>事故持续时间：</b></td>
      <td height="40">${accident.strtrouble_duration}</td>
      <td width="20%" height="40"><b>事故影响时间：</b></td>
      <td height="40">${accident.strimpact_time}</td>
    </tr> --%>
    <tr>
      <td width="20%" height="40"><b>事故描述：</b></td>
      <td height="40" valign="top" colspan="3" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;">${accident.accdescription}</td>
    </tr>  
    <tr>
      <td width="20%" height="40"><b>事故原因类型：</b></td>
      <td height="40" colspan="3">${accident.causaltype}</td>
    </tr>
    <tr>
      <td width="20%" height="40"><b>异常情况描述：</b></td>
      <td height="40" valign="top" colspan="3" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;">${accident.causalanalysis}</td>
    </tr>  
    <tr>
      <td width="20%" height="40"><b>受影响范围：</b></td>
      <td height="40" valign="top" colspan="3" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;">${accident.consequenceanalysis}</td>
    </tr> 
    <tr>
      <td width="20%" height="40"><b>纠正处理过程：</b></td>
      <td height="40" valign="top" colspan="3" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;">${accident.correctiveaction}</td>
    </tr> 
       
    <tr>
<%--       <td width="20%" height="40"><b>解决人员：</b></td>
      <td height="40">${accident.resolutioner}</td> --%>
      <td width="20%" height="40"><b>解决时间：</b></td>
      <td height="40" colspan="3" >${accident.resolutiontime}</td>
    </tr>
   <%--  <tr>
      <td width="20%" height="40"><b>预防措施实施人：</b></td>
      <td height="40" colspan="3">${accident.preventiver}</td>
    </tr>
    <tr>
      <td width="20%" height="40" style="white-space:nowrap;overflow:hidden;word-break:break-all;"><b>预防措施计划完成时间：</b></td>
      <td height="40">${accident.preventiveplandate}</td>
      <td width="20%" height="40" style="white-space:nowrap;overflow:hidden;word-break:break-all;"><b>预防措施实际完成时间：</b></td>
      <td height="40">${accident.preventiveaccdate}</td>
    </tr> --%>
        <tr>
      <td width="20%" height="40"><b>预防措施：</b></td>
      <td height="40" valign="top" colspan="3" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;">${accident.preventiveaction}</td>
    </tr> 
    <tr>
      <td height="40" colspan="4" align="center">
      <a href="/accident/load.do" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">返 回</span></a>
      <c:if test="${accident.filename!=null }">
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <a href="/upload/${accident.filename}" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">下载事故报告</span></a>
      </c:if>	
      </td>
    </tr>
  </table>
 
</div>
    				<p>&nbsp;</p>
	</article>
	</div>
	</div>

<script type="text/javascript">
function openDiv(){
	document.getElementById('showaccident').style.display='block';
	document.getElementById('bg').style.display='block';
}
function closeDiv(){
	document.getElementById('showaccident').style.display='none';
	document.getElementById('bg').style.display='none';
}

</script>
</body>
</html>
