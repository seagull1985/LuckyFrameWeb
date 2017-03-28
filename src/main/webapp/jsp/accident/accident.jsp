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
	
<title>生产故障信息</title>
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

	<style type="text/css">
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
	
</style>
	</head>

<body>
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
    
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width:auto;font-size:14px;">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">质量管理</li>
			<li class="active">生产故障</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">生产故障信息</h1>
			</header>    
		
<div id="descriptiondiv" style="position:absolute;display:none;z-index:99900;width: 400px;">
<table width="100%"  align="center" class="rect"  style="background-color:rgba(240,255,240,0.9);table-layout:fixed;">
    <tr>
      <td id="descriptionid" valign="top" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;color:#FF9224;font-size:11pt">
      </td>
    </tr>
</table>
</div>

<div id="showaccident" class="mydiv" style="display:none;overflow-y:auto;width: 80%;align:center">
<br/>
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

<div id="bg" class="bg" style="display:none;"></div>

	 <sf:form method="post" modelAttribute="accident">
		<input name="page" id="page" type="hidden"  />
		<input name="filename" id="filename" type="hidden"  />					
		<div align="right"></div>
	     <table width="100%" align="center" class="rect" height=40 cellPadding=1 border=1 bordercolor="#CCCCCC">
		  <tr>

				<td width="25%" align="left" valign="middle"><font size="2"
					color="black">项目名称:&nbsp;</font> <sf:select path="projectid" width="10%">
						<sf:option value="0">全部</sf:option>
						<c:forEach var="p" items="${projects }">
							<sf:option value="${p.projectid}">${p.projectname}</sf:option>
						</c:forEach>
					</sf:select> &nbsp;&nbsp;
				
				<font size="2" color="black">事故状态:&nbsp;</font>	
			  	<sf:select path="accstatus" id="accstatus" class="easyui-combobox"  required="true" validType="selectValueRequired['#accstatus']"  missingMessage="事故状态" invalidMessage="事故状态"  >            
                   <sf:option value="00">全部</sf:option>
                   <sf:option value="已发生-初始状态">已发生-初始状态</sf:option>
                   <sf:option value="已发生-跟踪中-未处理">已发生-跟踪中-未处理</sf:option>
                   <sf:option value="已发生-跟踪中-处理中">已发生-跟踪中-处理中</sf:option>
                   <sf:option value="跟踪处理完成">跟踪处理完成</sf:option>
                  </sf:select> 					
					
					<font size="2" color="black">&nbsp;&nbsp;发生时间:&nbsp;</font>
					<sf:input path="accstarttime" id="accstarttime" onclick="WdatePicker({isShowClear:false,readOnly:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"
						readonly="true" style="width:150px" value="" />&nbsp;至 <sf:input path="accendtime" id="accendtime"
						onclick="WdatePicker({isShowClear:false,readOnly:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true" style="width:150px" value="" />
					&nbsp;&nbsp;
				<input name="button" style="width:100px;height:26px" type="submit" class="button gray" id="button" align="right" value="查询" />
					</td>
				 </tr>
				 <tr>
	             <td width="25%" align="left" svalign="middle" >
	             <font size="2"	color="black">项目名称:&nbsp;</font> 
	             <select name="pieprojectid" id="pieprojectid">
						<option value="0">全部</option>
						<c:forEach var="p" items="${projects }">
							<option value="${p.projectid}">${p.projectname}</option>
						</c:forEach>
					</select> &nbsp;&nbsp;
					
			     <font size="2" color="black">发生时间:&nbsp;</font>
                 <input name="pie_startdate" id="pie_startdate" onclick="WdatePicker({isShowClear:false,readOnly:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"  style="width:100px" value="" />&nbsp;至
                 <input name="pie_enddate" id="pie_enddate" onclick="WdatePicker({isShowClear:false,readOnly:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" style="width:100px" value="" />
                 &nbsp;&nbsp;
                 <input type="radio" name="type" id="type" value="count" checked="checked">按累计次数
                 <input type="radio" name="type" id="type" value="sumimpact">按累计影响时间
                 &nbsp;&nbsp;&nbsp;&nbsp;
				
	<a	href="javascript:window.location.href='/accident/piechart_html5.do?pie_startdate='+$('#pie_startdate').val()+
	  '&pie_enddate='+$('#pie_enddate').val()+'&projectid='+$('#pieprojectid').val()+'&type='+getrediovalue();" 
           style="text-decoration: none;"> <span	class="btnold STYLE1" style="width: 70px;"> 事故原因饼图</span></a>
				</td>
				 </tr>
				 <tr>
				  <td width="5%" align="center" valign="middle"><a
					 href="#"  onclick="showDiv('0','1')" style="text-decoration: none;"> <span
						class="btnold STYLE1"  style="width: 70px;background:#FFA54F;border:#FFA54F;" > 添加记录</span>
				</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="openDiv()"  style="text-decoration: none;color:blue;">查看事故级别定义</a>
				</td>
				 </tr>
		</table> 
		
	   <table width="100%" align="center" class="bordered" style="table-layout: fixed">
          <tr bgcolor="#B9DCFF">                
				<th width="6%" height="40" nowrap="nowrap"  >项目名称</th>
				<th width="8%" height="40" nowrap="nowrap"  >事故状态</th>
				<th width="5%" height="40" nowrap="nowrap"  >事故等级</th>
				<th width="7%" height="40" nowrap="nowrap"  >发生时间</th>
				<th width="9%" height="40" nowrap="nowrap"  >事故原因类型</th>
				<th width="14%" height="40" nowrap="nowrap"  >事故描述</th>
				<th width="6%" height="40" nowrap="nowrap"  >解决时间</th>
				<th width="12%" height="40" nowrap="nowrap"  >操作</th>
		  </tr>
		  <c:forEach var="t" items="${splist}" begin="0" step="1"
				varStatus="i">
			  <tr>
			  <td height="25" align="center">${t.sectorProjects.projectname }</td>	
			  <c:if test="${t.accstatus=='已发生-初始状态' }">
				  <td height="25" align="center" style="color:#FFA54F;">${t.accstatus }</td>
				</c:if>
				<c:if test="${t.accstatus!='已发生-初始状态'&&t.accstatus!='跟踪处理完成' }">
				  <td height="25" align="center" style="color:#95CACA;">${t.accstatus }</td>
				</c:if>	
				<c:if test="${t.accstatus=='跟踪处理完成' }">
				  <td height="25" align="center" style="color:#00DB00;">${t.accstatus }</td>
				</c:if>				  				  

				  <td height="25" align="center">${t.acclevel }</td>			  
				  <td height="25" align="center">${t.eventtime }</td>
				  <td height="25" align="center">${t.causaltype }</td>	
				  <td height="25" align="center" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis" onmouseout="hiddenPic();" onmousemove="description(this);">${t.accdescription }</td>
				  <td height="25" align="center">${t.resolutiontime }</td>	
				  <td height="25" align="center" style="word-break:break-all">
				  <a href="#" onclick="showDiv('${t.id}','2')" style="cursor: pointer;"><u>修改</u></a>&nbsp;
				   <a href="#" onclick="showDiv('${t.id}','3')" style="cursor: pointer;"><u>删除</u></a>&nbsp;
				    <a href="/accident/show.do?id=${t.id}" style="cursor: pointer;"><u>详情</u></a>
				   <c:if test="${t.accstatus=='跟踪处理完成'&&t.filename==null }">
				   <a href="#" onclick="javascript:window.location.href='/accident/to_upload.do?id='+${t.id}" style="cursor: pointer;"><u>上传附件</u></a>  
				   </c:if>
				   <c:if test="${t.accstatus=='跟踪处理完成'&&t.filename!=null }">
				   <a href="#" onclick="javascript:window.location.href='/accident/to_upload.do?id='+${t.id}" style="cursor: pointer;"><u>重传附件</u></a>  
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
			document.getElementById("accident").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("accident").submit();
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
		document.getElementById("accident").submit();
		return true;
	}
	function showDiv_backup(accid,opr){
		document.getElementById("accid").value=accid;
		document.getElementById("opr").value=opr;
		document.getElementById('passDiv').style.display='block';
		document.getElementById('bg').style.display='block';
	}
	function closepassDiv(){
		document.getElementById('passDiv').style.display='none';
		document.getElementById('bg').style.display='none';
	}
	function showDiv(accid,opr){
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
			var url = '/accident/add.do';
			window.location.href=url;
	    }else if(opr=="2"){
			var url = '/accident/update.do?id='+accid;
			window.location.href=url;
	    }else if(opr=="3"){
	    	if(window.confirm("你确认要删除吗？")){ 
				var url = '/accident/delete.do?id='+accid;
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
	
	function description(obj){ 
		document.getElementById("descriptiondiv").style.left = (obj.offsetLeft+80)+"px"; 
		document.getElementById("descriptiondiv").style.top = (obj.offsetTop+200)+"px"; 
		document.getElementById("descriptionid").innerHTML = obj.innerHTML;
		
		document.getElementById("descriptiondiv").style.display = "block"; 
		//alert("left2:"+document.getElementById("descriptiondiv").style.left+"  top2:"+document.getElementById("descriptiondiv").style.top);
		} 
	
	function hiddenPic(){ 
		document.getElementById("descriptiondiv").style.display = "none"; 
		} 
	
	function openDiv(){
		document.getElementById('showaccident').style.display='block';
		document.getElementById('bg').style.display='block';
	}
	
	function closeDiv(){
		document.getElementById('showaccident').style.display='none';
		document.getElementById('bg').style.display='none';
	}
	
	function getrediovalue(){
	var type=document.getElementsByName("type");
	  for(var i=0;i<type.length;i++){ 
	    if(type[i].checked){  
	      return type[i].value;  
	      break;
	    }  
	  }  
	}
	</script>
	</body>	
</html>