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
	
	<title>项目评审记录</title>
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
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">质量管理</li>
			<li class="active">项目评审记录</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">项目评审记录</h1>
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
<td width="3%" height="30" align="center" valign="baseline"><h1>评审工作说明</h1></td>
<table width="100%" align="center" class="bordered" height="200px;">
    <tr>
      <td width="20%" height="5" style="text-align:center;background-color:#A3D1D1;"><font color="#C48888" size="3">  </font></b></td>
      <td width="80%" style="text-align:center;background-color:#A3D1D1;"><font color="#C48888" size="3">说明</font></td>
      </tr>
       <tr>
      <td width="20%" height="5" style="text-align:center;"><b>评审对象</b></td>
      <td width="80%"><font color="#FF0000">开发人员：</font><br><font color="#FF0000">测试人员：</font></td>
      </tr>
       <tr>
      <td width="20%" height="5" style="text-align:center;"><b>评审时间</b></td>
      <td width="80%">项目经理拟定，并通知QA</td>
      </tr>
      <tr>
      <td width="20%" height="5" style="text-align:center;"><b>记录人</b></td>
      <td width="80%">QA</td>
      </tr>
       <tr>
      <td width="20%" height="5" style="text-align:center;"><b>与会人员</b></td>
      <td width="80%">项目经理、开发工程师、测试工程师、QA、必要时邀请其余资深技术人员。</td>
      </tr>
      <tr>
      <td width="20%" height="5" style="text-align:center;"><b>操作要求</b></td>
      <td width="80%">1、QA在评审会议当场跟随评审进度进行记录；<br>2、评审会结束前，将记录单展示给与会人员确认；<br>3、会后跟进修改情况，并更新状态；<br>
       4、前期根据项目计划进行代码评审的记录，后期慢慢扩大评审对象及进行日期。</td>
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
	 
	 <sf:form method="post" modelAttribute="review">
		<input name="page" id="page" type="hidden"  />					
		<div align="right"></div>
		
		<table width="100%" align="center" class="rect" height=40 cellPadding=1 border=1 bordercolor="#CCCCCC">
		  <tr>
		  <td width="25%" align="left" valign="middle"><font size="2"
					color="black">项目名称:&nbsp;</font> <sf:select path="projectid" width="30%">
						<sf:option value="0">全部</sf:option>
						<c:forEach var="p" items="${projects }">
							<sf:option value="${p.projectid}">${p.projectname}</sf:option>
						</c:forEach>
					</sf:select> 
					
		   <font size="2" color="black">版本号:&nbsp;</font>
					<sf:input path="version" id="version" style="width:150px;"/>
						
		   <font size="2" color="black">评审时间:&nbsp;</font>
					<sf:input path="review_startdate" id="review_startdate" onclick="WdatePicker({isShowClear:false,readOnly:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});"
						readonly="true" style="width:100px" value="" />&nbsp;至 <sf:input path="review_enddate" id="review_enddate"
						onclick="WdatePicker({isShowClear:false,readOnly:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" readonly="true" style="width:100px" value="" />
					&nbsp;&nbsp;
				<input name="button" style="width:100px;height:26px" type="submit" class="button gray" id="button" align="right" value="查询" />
					
				</td>
		</tr>
		<tr>
		<td width="25%" align="center" valign="middle">
		<a href="#"  onclick="showDiv('0','1')" style="text-decoration: none;"> 
		      <span class="btnold STYLE1"  style="width: 70px;background:#FFA54F;border:#FFA54F;" > 添加记录</span>
				</a>&nbsp;
				<a href="#" onclick="openDiv()" style="text-decoration: none;">
                <span class="btnold STYLE1"  style="width: 70px;" > 评审说明</span>
                </a>
                </td>
		</tr>
		  </table> 
		
	   <table width="100%" align="center" class="bordered" style="table-layout: fixed">
          <tr bgcolor="#B9DCFF">                
				<th width="6%" height="40" nowrap="nowrap"  >项目名称</th>
				<th width="6%" height="40" nowrap="nowrap"  >项目版本</th>
				<th width="5%" height="40" nowrap="nowrap"  >评审类型</th>
				<th width="5%" height="40" nowrap="nowrap"  >评审时间</th>
				<th width="4%" height="40" nowrap="nowrap"  >发现问题数</th>
				<th width="5%" height="40" nowrap="nowrap"  >已更正问题数</th>
				<th width="4%" height="40" nowrap="nowrap"  >评审结果</th>
				<th width="5%" height="40" nowrap="nowrap"  >最后确认日期</th>
				<th width="18%" height="40" nowrap="nowrap"  >备注</th>
				<th width="6%" height="40" nowrap="nowrap"  >操作</th>
		  </tr>
		  <c:forEach var="t" items="${splist}" begin="0" step="1" varStatus="i">
				<tr>
					<td height="25" align="center">${t.sectorProjects.projectname }</td>
					<td height="25" align="center">${t.version }</td>
					<td height="25" align="center">${t.review_type }</td>
					<td height="25" align="center">${t.review_date }</td>
					<td height="25" align="center">${t.bug_num }</td>
					<td height="25" align="center">${t.repair_num }</td>
					<td height="25" align="center">${t.review_result }</td>
					<td height="25" align="center">${t.confirm_date }</td>
					<td height="25" align="center" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis" 
				onmouseout="hiddenPic();" onmousemove="description(this);">${t.remark }</td>
					<%-- <td height="25" align="center">${t.remark }</td> --%>
					<td height="25" align="center" style="word-break: break-all">
						<a href="#" onclick="showDiv('${t.id}','2')"
						style="cursor: pointer;"><u>修改</u></a>&nbsp; <a href="#"
						onclick="showDiv('${t.id}','3')" style="cursor: pointer;"><u>删除</u></a>&nbsp;
						<a href="/reviewinfo/list.do?reviewid=${t.id}" style="cursor: pointer;"><u>详情</u></a>
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
			document.getElementById("review").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("review").submit();
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
		document.getElementById("review").submit();
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
	
	function showDiv(rewid,opr){
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
			var url = '/reviewinfo/add.do';
			window.location.href=url;
	    }else if(opr=="2"){
			var url = '/review/update.do?id='+rewid;
			window.location.href=url;
	    }else if(opr=="3"){
	    	if(window.confirm("你确认要删除吗？")){ 
				var url = '/review/delete.do?id='+rewid;
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
		document.getElementById("descriptiondiv").style.top = (obj.offsetTop+160)+"px"; 
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