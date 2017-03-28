<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>	
<link href="/css/style.css" rel="stylesheet" type="text/css" />

<title>项目版本信息</title>
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

	
<style type="text/css">
<!--
html,body {height:100%; margin:0px; font-size:12px;}

.mydiv {
background-color: #D1BDC7;
border: 1px solid #C5C9C7;
text-align: center;
line-height: 40px;
font-size: 12px;
font-weight: bold;
z-index:999;
width: 300px;
height: 100px;
left:50%;
top:50%;
margin-left:-150px!important;/*FF IE7 该值为本身宽的一半 */
margin-top:-60px!important;/*FF IE7 该值为本身高的一半*/
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
.popIframe {
filter:alpha(opacity=0);/*IE*/
opacity:0;/*FF*/
}
-->
</style>
	
	</head>

<body onload="init()">
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 

	<header id="head" class="secondary"></header>

<div id="descriptiondiv" style="position:absolute;display:none;z-index:99900;width: 500px;">
<table width="100%"  align="center" class="rect"  style="background-color:rgba(240,255,240,0.9);table-layout:fixed;">
    <tr>
      <td id="descriptionid" valign="top" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;color:#FF9224;font-size:11pt">
      </td>
    </tr>
</table>
</div>

	<!-- container -->
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">质量管理</li>
			<li class="active">版本信息</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">版本信息</h1>
			</header>
					
	 <sf:form method="post" modelAttribute="projectversion">
	 	<!-- <input type="hidden" name="versionid" id="versionid" value=""/> -->
		<input name="page" id="page" type="hidden"  />
	<!-- 	<input name="startactually_launchdate" id="startactually_launchdate" type="hidden"  /> -->
																
		<div align="right"></div>
		<table width="90%" align="center" class="rect" height=40 cellPadding=1
			border=1 bordercolor="#CCCCCC">
			<tr>
			<td width="10%" align="left" valign="middle" >
			<font size="2" color="black">项目名称:&nbsp;&nbsp;</font>
    	<sf:select path="projectid" width="10%">
   	   <sf:option value="0">全部</sf:option>
     <c:forEach var="p" items="${projects }">
	  <sf:option value="${p.projectid}">${p.projectname}</sf:option>
	  </c:forEach>
    </sf:select>
    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" color="black">&nbsp;&nbsp;上线日期:&nbsp;&nbsp;</font>
<sf:input path="startactually_launchdate" id="startactually_launchdate" onclick="WdatePicker({isShowClear:false,isShowClear:true,readOnly:true});" 
readonly="true" style="width:100px" value="" />&nbsp;至
<sf:input path="endactually_launchdate" id="endactually_launchdate" onclick="WdatePicker({isShowClear:false,isShowClear:true,readOnly:true});" 
readonly="true" style="width:100px" value="" />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input name="button" style="width:100px;height:26px" type="submit" class="button gray" id="button" align="right" value="上线列表查询" />&nbsp;&nbsp;&nbsp;&nbsp;  
    &nbsp;&nbsp;&nbsp;&nbsp;<a href="../zentao/list.do" style="text-decoration: none;"> <span	class="btnold STYLE1" style="width: 70px;background:#FFA54F;border:#FFA54F;">禅道报表</span></a>   
    </td>
						    
				<td width="3%" align="right" valign="middle"><a
					 href="#"  onclick="showDiv('${t.versionid}','4')" style="text-decoration: none;"> <span
						class="btnold STYLE1"  style="width: 70px;background:#FFA54F;border:#FFA54F;" > 添加计划</span>
				</a>&nbsp;&nbsp;<a href="#" onclick="showDiv('${t.versionid}','3')"  style="text-decoration: none;"> <span
						class="btnold STYLE1"  style="width: 70px;background:#FFA54F;border:#FFA54F;" > 添加版本 </span>
				</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				
			</tr>
		</table>

		<table width="90%" align="center" class="rect" height=40 cellPadding=1
			border=1 bordercolor="#CCCCCC">
			<td width="80%" align="left" valign="middle" >
			<font size="2" color="black">查询日期段:&nbsp;&nbsp;</font>
<input name="start_date" id="start_date" onclick="WdatePicker({isShowClear:false,isShowClear:true,readOnly:true});"  style="width:100px" value="" />&nbsp;至
<input name="end_date" id="end_date" onclick="WdatePicker({isShowClear:false,isShowClear:true,readOnly:true});" style="width:100px" value="" />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
    <a	href="#"  onclick="getoprdate()"  style="text-decoration: none;"> <span
						class="btnold STYLE1" style="width: 70px;">生成项目质量对比图</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a	href="javascript:window.location.href='/projectVersion/showreport.do?startdate='+$('#start_date').val()+
	  '&enddate='+$('#end_date').val();" style="text-decoration: none;"> <span	class="btnold STYLE1" style="width: 70px;"> 项目版本质量报表</span></a>
				</td>

					
		<tr>
		<td width="10%" align="left" valign="middle" >
			<font size="2" color="black">项目名称:&nbsp;&nbsp;</font>
    	<select name="projectidshow" id="projectidshow" width="10%">
     <c:forEach var="p" items="${projects }">
	  <option value="${p.projectid}">${p.projectname}</option>
	  </c:forEach>
    </select>&nbsp;&nbsp;
    <font size="2" color="black">日期段:&nbsp;&nbsp;</font>
<input name="onepro_startdate" id="onepro_startdate" onclick="WdatePicker({isShowClear:false,isShowClear:true,readOnly:true});"  style="width:100px" value="" />&nbsp;至
<input name="onepro_enddate" id="onepro_enddate" onclick="WdatePicker({isShowClear:false,isShowClear:true,readOnly:true});" style="width:100px" value="" />
   &nbsp;&nbsp;&nbsp;&nbsp;

	<a	href="javascript:window.location.href='/projectVersion/linechart_html5.do?projectid='+$('#projectidshow').val()+'&startdate='+$('#onepro_startdate').val()+
	  '&enddate='+$('#onepro_enddate').val();" 
	target=_blank style="text-decoration: none;"> <span	class="btnold STYLE1" style="width: 70px;"> 单个项目版本质量趋势图</span></a>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<a	href="javascript:window.location.href='/projectVersion/onepro_barcharthtml5.do?projectid='+$('#projectidshow').val()+'&startdate='+$('#onepro_startdate').val()+
	  '&enddate='+$('#onepro_enddate').val();" 
	target=_blank style="text-decoration: none;"> <span	class="btnold STYLE1" style="width: 70px;"> 单个项目版本质量柱状图</span></a>
    </td>
    </tr>
	</table>
	   <table width="100%" align="center" class="bordered" style="table-layout: fixed" >  <!--  限定宽度，后面带省略号，此style属性必有 -->
         <tr>                
				<th width="10%" height="40" nowrap="nowrap" >项目名称</th>
				<th width="10%" height="40" nowrap="nowrap" >版本状态</th>
				<th width="10%" height="40" nowrap="nowrap" >版本号</th>
				<th width="10%" height="40" nowrap="nowrap" >版本达成日期</th>
				<th width="10%" height="40" nowrap="nowrap" >项目进度偏差</th>
				<th width="10%" height="40" nowrap="nowrap" >需求达成率</th>
				<th height="40" nowrap="nowrap" >版本说明</th>
				<th width="12%" height="40" nowrap="nowrap" >操作</th>
		  </tr>
		  <c:forEach var="t" items="${splist}" begin="0" step="1"
				varStatus="i">
			  <tr>	
   			      <td height="25" align="center">${t.sectorProjects.projectname }</td>
				  
				   <c:if test="${t.versiontype==0 }">
				  <td height="25" align="center" style="color:#FFA54F;">计划执行中...&nbsp;</td>
				  </c:if>
				  <c:if test="${t.versiontype==1 }">
				  <td height="25" align="center" style="color:#00DB00;">已完成</td>
				  </c:if>
				  
				  <c:choose>
				  <%-- <c:when test="${fn:containsIgnoreCase(t.zt_versionlink, 'http://')}"> --%>
				  <c:when test="${fn:length(t.zt_versionlink)>0}">
				  <td height="25" align="center"><a	href="http://10.211.19.75/zentao/project-task-${t.zt_versionlink }.html" target=_blank style="text-decoration: none;color:#0000FF;">${t.versionnumber }</a>&nbsp;</td>
				  </c:when>
				  <c:otherwise>
				  <td height="25" align="center">${t.versionnumber }&nbsp;</td>
				  </c:otherwise>
				  </c:choose>
				  
				  <td height="25" align="center">${t.actually_launchdate }&nbsp;</td>
				  
				  <c:if test="${t.protime_deviation!=null }">
				  <td height="25" align="center">${t.protime_deviation }%&nbsp;</td>
				  </c:if>
				  <c:if test="${t.protime_deviation==null }">
				  <td height="25" align="center">数据缺失,无法计算</td>
				  </c:if>
				  
				  <c:choose>
				  <c:when test="${t.plan_demand!=0&&t.actually_demand!=0}">
				  <td height="25" align="center" ><fmt:formatNumber value="${(t.actually_demand/t.plan_demand)*100 }" pattern="#0.00"/>%&nbsp;</td>
				  </c:when>
				  <c:otherwise>
				  <td height="25" align="center" >0%&nbsp;</td>
				  </c:otherwise>
				  </c:choose>		  		  
				  
				  <td height="25" width="40" align="center" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis" onmouseout="hiddenPic();" onmousemove="description(this);">${t.imprint }</td>
				  
				  <td height="25" align="center" style="word-break:break-all">
				  <a href="#" onclick="showDiv('${t.versionid}','1')" style="cursor: pointer;"><u>修改</u></a>&nbsp;
				<%--     <a href="/projectVersion/${t.versionid}/delete.do" style="cursor: pointer;"><u>删除</u></a>&nbsp;--%>
				<a href="#" onclick="showDiv('${t.versionid}','2')" style="cursor: pointer;"><u>删除</u></a>
				<a href="/projectVersion/show/${t.versionid}.do" style="cursor: pointer;"><u>详情</u></a> 
				 <c:if test="${fn:length(t.zt_versionlink)>0&&t.versiontype==1}">
				  <a href="javascript:window.location.href='/zentao/list.do?versionid=${t.zt_versionlink}'" style="cursor: pointer;"><u>禅道任务</u></a>
				  </c:if>				
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
	
	
	function setPage(page)
	{
		if(page==1){
			document.getElementById("page").value=1;
		}else{
			document.getElementById("page").value=page;
		}
		document.getElementById("projectversion").submit();
		return true;
	}
	
	function description(obj){ 
		document.getElementById("descriptiondiv").style.left = (obj.offsetLeft+80)+"px"; 
		document.getElementById("descriptiondiv").style.top = (obj.offsetTop+270)+"px"; 
		document.getElementById("descriptionid").innerHTML = obj.innerHTML;
		
		document.getElementById("descriptiondiv").style.display = "block"; 
		//alert("left2:"+document.getElementById("descriptiondiv").style.left+"  top2:"+document.getElementById("descriptiondiv").style.top);
		} 
	
	function hiddenPic(){ 
		document.getElementById("descriptiondiv").style.display = "none"; 
		} 
	
	function init(){
		if('${message}'!=''){
			if('${message}'=='添加成功'){
				alert("添加成功,请返回查询！");
			}else{
				alert('${message}');
			}
		}
		
	}
	
	</script>	
	
	<script type="text/javascript">
	function closeDiv(){
		document.getElementById('popDiv').style.display='none';
		document.getElementById('bg').style.display='none';
	}
	
	function showDiv(verid,opr){
		var status = document.getElementById("loginstatus").value;
		if(status=="false"){
			if(window.confirm("你未登录哦，要先去首页登录吗？")){
				var url = '/progressus/signin.jsp';
				window.location.href=url;
			}else{
				return false; 
			} 
			//document.getElementById('popDiv').style.display='block';
			//document.getElementById('bg').style.display='block';		
		}else{
			if(opr=="1"){
				var url = '/projectVersion/update.do?versionid='+verid;
				window.location.href=url;
		    }else if(opr=="2"){
		    	if(window.confirm("你确认要删除吗？")){ 
					var url = '/projectVersion/delete.do?versionid='+verid;
					window.location.href=url;
		    		return true; 
		    		}else{ 
		    		return false; 
		    		}
		    }else if(opr=="3"){
		    	var url = '/projectVersion/add.do';
		    	window.location.href=url;
		    }else if(opr=="4"){
		    	var url = '/projectVersion/addplan.do';
		    	window.location.href=url;
		    }else{ 
		       alert("操作码有误，请联系软件质量室相关人员！"); 
		       return false;
		    }
		}
		
	}
	
	function projectlineshow(){
	      var select = document.getElementById("projectidshow");
	      var index = select.selectedIndex;
		  var projectvalue = select.options[index].value;
		if(projectvalue!=0){
			var url = '/projectVersion/'+projectvalue+'/launchlineshow.do';
			window.location.href=url;
	    }else{ 
	       alert("所选项目有误，请重新输入！"); 
	       return false;
	    }
	}
	
	function getoprdate(){
		startdate = document.getElementById("start_date").value;
		enddate = document.getElementById("end_date").value;
		if(startdate>enddate){
			alert("开始日期大于结束日期，请重新选择！"); 
		}else{
			var url = '/projectVersion/barchart_html5.do?start_date='+startdate+'&end_date='+enddate;
			window.location.href=url;
		}
	}

</script>	
</body>
</html>