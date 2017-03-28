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


	<title>评审记录详情</title>
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
margin-top:-10px!important;/*FF IE7 该值为本身高的一半*/
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
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">质量管理</li>
			<li class="active"><a href="/review/list.do">项目评审记录</a></li>
			<li class="active">评审详细信息</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">评审详细信息</h1>
			</header>   
			
<div id="descriptiondiv" style="position:absolute;display:none;z-index:99900;width: 400px;">
<table width="100%"  align="center" class="rect"  style="background-color:rgba(240,255,240,0.9);table-layout:fixed;">
    <tr>
      <td id="descriptionid" valign="top" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;color:#FF9224;font-size:11pt">
      </td>
    </tr>
</table>
</div>

<div id="bg" class="bg" style="display:none;"></div>

	<div align="center" style="width: 100%">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
		  <tr>
		  <td width="25%" height="40"><b><font color="#95CACA" size="3">项目名称：</font></b>${review.sectorProjects.projectname}</td>
		  <td width="25%" height="40"><b><font color="#95CACA" size="3">版本号： </font></b>${review.version}</td>
          <td width="50%" height="40"><b><font color="#95CACA" size="3">评审日期： </font></b>${review.review_date}</td>
		  </tr>
		  <tr>
		  <td width="25%" height="40"><b><font color="#95CACA" size="3">评审类型： </font></b>${review.review_type}</td>
		  <td width="25%" height="40"><b><font color="#95CACA" size="3">评审对象：  </font></b>${review.review_object}</td>
		  <td width="50%" valign="middle"><a href="#"  onclick="showDiv(${reviewid },'1')" style="text-decoration: none;"> 
		      <span class="btnold STYLE1"  style="width: 70px;background:#FFA54F;border:#FFA54F;" > <b>添加记录</b></span>
				</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		  </tr>
		</table>

	 </div>
	 
	 <sf:form method="post" modelAttribute="reviewinfo">
		<input name="page" id="page" type="hidden"  />					
		<div align="right"></div>
		
	   <table width="100%" align="center" class="bordered" style="table-layout: fixed">
          <tr bgcolor="#B9DCFF">                
				<th width="30%" height="40" nowrap="nowrap"  >问题描述</th>
				<th width="30%" height="40" nowrap="nowrap"  >改正措施</th>
				<th width="10%" height="40" nowrap="nowrap"  >问题状态</th>
				<th width="10%" height="40" nowrap="nowrap"  >责任人</th>
				<th width="10%" height="40" nowrap="nowrap"  >最后确认日期</th>
				<th width="10%" height="40" nowrap="nowrap"  >操作</th>
		  </tr>
		  <c:forEach var="t" items="${splist}" begin="0" step="1" varStatus="i">
				<tr>
				<td height="25" align="center" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis" 
				onmouseout="hiddenPic();" onmousemove="description(this);">${t.bug_description }</td>
				<td height="25" align="center" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis" 
				onmouseout="hiddenPic();" onmousemove="description(this);">${t.corrective }</td>
					<td height="25" align="center">${t.status }</td>
					<td height="25" align="center">${t.duty_officer }</td>
					<td height="25" align="center">${t.confirm_date }</td>
					<td height="25" align="center" style="word-break: break-all">
						<a href="#" onclick="showDiv('${t.id}','2')"
						style="cursor: pointer;"><u>修改</u></a>&nbsp;
						 <a href="#" onclick="showDiv('${t.id}','3')" style="cursor: pointer;"><u>删除</u></a>&nbsp;
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
			document.getElementById("reviewinfo").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("reviewinfo").submit();
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
		document.getElementById("reviewinfo").submit();
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
	
	function showDiv(id,opr){
		var status = document.getElementById("loginstatus").value;
		if(status=="false"){
			if(window.confirm("你未登录哦，要先去首页登录吗？")){
				var url = '/';
				window.location.href=url;
			}else{
				return false; 
			} 	
		}else{
		if(opr=="1"){
			var url = '/reviewinfo/add.do?reviewid='+id;
			window.location.href=url;
	    }else if(opr=="2"){
			var url = '/reviewinfo/update.do?id='+id;
			window.location.href=url;
	    }else if(opr=="3"){
	    	if(window.confirm("你确认要删除吗？")){ 
				var url = '/reviewinfo/delete.do?id='+id;
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
	
	function description(obj){ 
		document.getElementById("descriptiondiv").style.left = (obj.offsetLeft+100)+"px"; 
		document.getElementById("descriptiondiv").style.top = (obj.offsetTop+200)+"px"; 
		document.getElementById("descriptionid").innerHTML = obj.innerHTML;
		
		document.getElementById("descriptiondiv").style.display = "block"; 
		//alert("left2:"+document.getElementById("descriptiondiv").style.left+"  top2:"+document.getElementById("descriptiondiv").style.top);
		} 
	
	function hiddenPic(){ 
		document.getElementById("descriptiondiv").style.display = "none"; 
		} 
	</script>	
</body>
</html>