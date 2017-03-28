<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<link rel="stylesheet" type="text/css" href="/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="/js/easyui/themes/default/progressbar.css" />
<link rel="stylesheet" type="text/css" href="/js/easyui/themes/icon.css" />
<!-- <link rel="stylesheet" type="text/css" href="../js/easyui/demo/demo.css"> -->
<script language="JavaScript" type="text/javascript"	src="/js/My97DatePicker/WdatePicker.js"></script>

<title>任务查询</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
 	window.onload = function init(){
 		var idlist = eval('${idlist}');
	  	 for(var i=0;i<"${idlistsize}";i++){
	 	 	var url ="/tastExecute/progressdata.do?id="+idlist[i];
	 		$.ajax({
	 			type:"GET",
	 			url:url,
	 			cache:false,
	 			dataType:"json",
	 			success:function (result){
	 		    	if(result.data[1]!=null&&result.data[1]<=100&&result.data[1]>0){		
	 		    		$('#'+result.data[0]).progressbar('setValue', result.data[1]);
	 		    		document.getElementById("casetotal"+result.data[0]).innerText = result.data[7];
	 		    		if(result.data[1]==100){
	 		    			document.getElementById("finishtime"+result.data[0]).innerText = result.data[2];
	 		    			document.getElementById("casesucc"+result.data[0]).innerText = result.data[3];
	 		    			document.getElementById("casefail"+result.data[0]).innerText = result.data[4];
	 		    			document.getElementById("caselock"+result.data[0]).innerText = result.data[5];
	 		    			document.getElementById("casenoex"+result.data[0]).innerText = result.data[6];
	 		    		}
	 					return true;
	 		    	}
	 			}
	 			});
	 	 	}
  	setTimeout(arguments.callee, 3000);
	} 
</script>	
</head>

<body >
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
	<header id="head" class="secondary"></header>    
	<div class="container" style="width:auto;">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">UTP</li>
			<li class="active">任务查询</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">任务查询</h1>
			</header>

<sf:form  method="post"  modelAttribute="testTastexcute">
<input name="projName" id="projName" type="hidden"  />
<input name="createTime1" id="createTime1" type="hidden" value=""  />
<input name="flag" id="flag" type="hidden" value="1"  />
<table width="90%"  align="center" class="rect"  cellPadding=1 border=1 bordercolor="#CCCCCC">
  <tr>
    <td width="11%" height="20" style="font-size:15px;font-weight:600">执行时间</td>
    <td width="33%" height="20" valign="middle" style="font-size:15px;font-weight:600">&nbsp;&nbsp;<sf:input path="startDate" 	class="Wdate" style="height:95%" onclick="WdatePicker({isShowClear:false})"  />&nbsp;至&nbsp;<sf:input path="endDate" 	class="Wdate" style="height:95%" onclick="WdatePicker({isShowClear:false,readOnly:true})"  /></td>
    <td width="10%" height="20" style="font-size:15px;font-weight:600">调度名称</td>
    <td height="30" colspan="2" style="font-size:15px;font-weight:600">&nbsp;
    	<sf:select path="jobid">
   	   <sf:option value="0">全部</sf:option>
     <c:forEach var="job" items="${jobs }"  begin="0" step="1" varStatus="i" >
	  <sf:option value="${job[0]}">【${job[2]}】—${job[1]}</sf:option>
	  </c:forEach>
    </sf:select>    </td>
    </tr>
  <tr>
    <td height="30" style="font-size:15px;font-weight:600">任务编号</td>
    <td height="30" style="font-size:15px;font-weight:600">&nbsp;&nbsp;<sf:input path="taskId" /></td>
    <td style="font-size:15px;font-weight:600">运行状态</td>
    <td style="font-size:15px;font-weight:600">&nbsp;&nbsp;<sf:select path="taskStatus">
     <sf:option value="">全部</sf:option>
      <sf:option value="0">未执行</sf:option>
       <sf:option value="1">执行中</sf:option>
       <sf:option value="2">执行成功</sf:option>
       <sf:option value="3">调起失败</sf:option>
    </sf:select>
    &nbsp;</td>
    <td width="24%" height="30" align="center">
    <input	name="button" type="submit" class="button gray" id="button" value="查询" />&nbsp;&nbsp;
    <a	href="#" onclick="showDiv('888888','batch')" style="text-decoration: none;"> <span
						class="btnold STYLE1" style="width: 70px;">批量删除</span></a>&nbsp;&nbsp;
    </td>
    </tr>
</table>
&nbsp;&nbsp;&nbsp;&nbsp;<span class="error_msg" >${message}</span>
<input name="page" id="page" type="hidden"  />
<%--<sf:input path="jobid" />--%>
  <table width="92%" align="center" class="bordered">
    <tr bgcolor="#B9DCFF">
      <th width="4%" height="45" align="center" bgcolor="#B9DCFF">全部<br><input type="checkbox" onclick="selk(this)"/></th>
      <th height="45" align="center">任务名称</th>
      <th height="45" align="center">所属项目</th>
      <th height="45" align="center">执行时间</th>
      <th height="45" align="center">结束时间</th>
      <th height="45" align="center">运行状态</th>
       <th width="4%" height="25" align="center" nowrap="nowrap">总用例</th>
          <th width="4%" height="25" align="center"nowrap="nowrap"> 成功 </th>
          <th width="4%" height="25" align="center"nowrap="nowrap"> 失败 </th>
          <th width="4%" height="25" align="center"nowrap="nowrap"> 锁定 </th>
          <th width="4%" height="25" align="center"nowrap="nowrap">未执行</th>
      <th height="45" align="center">操作</th>
     
    </tr>
	   <c:forEach var="t" items="${list }"   begin="0" step="1" varStatus="i"> 
	    <tr>
	      <td style="text-align:center"><input type="checkbox" name="deletebox" value="${t.id}" /></td>
	      <td height="25" align="center"><a  href="#" onclick="list(${t.id},'${t.testJob.planproj}','${t.createTime}')">${t.taskId }</a>&nbsp;</td>
	      <td height="25" align="center">${t.testJob.planproj }&nbsp;</td>
	      <td height="25" align="center"><fmt:formatDate value="${t.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	      <td height="25" align="center" id="finishtime${t.id}"><fmt:formatDate value="${t.finishtime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	      <c:if test="${t.taskStatus==1||t.taskStatus==0 }">
	      <td height="25" align="center" width="8%" style="text-align:center;"><div id="${t.id}" class="easyui-progressbar" style="width:100%;height:100%;text-align:center">${t.taskStatus_str}</div></td>
	      </c:if>
	      <c:if test="${t.taskStatus!=1&&t.taskStatus!=0 }">
	      <td height="25" align="center" width="8%" style="text-align:center">${t.taskStatus_str}</td>
	      </c:if>
	      <td width="5%" height="25" style="text-align:center" id="casetotal${t.id}">${t.casetotalCount}</td>
          <td width="4%" height="25" style="text-align:center;color:#00bf5f" id="casesucc${t.id}">${t.casesuccCount}</td>
          <td width="4%" height="25" style="text-align:center;color:#ff0000" id="casefail${t.id}">${t.casefailCount}</td>
          <td width="4%" style="text-align:center;color:#FF7F00" id="caselock${t.id}">${t.caselockCount}</td>
          <td width="4%" height="25" style="text-align:center;color:#FFD39B" id="casenoex${t.id}">${t.casenoexecCount}</td>
          <td align="center" style="text-align:center"><a href="#" onclick="showDiv('${t.id}','${t.taskId }')"><img src="../pic/delete.png"
						width="20" height="20" border="0" title="删除" /></a></td>
	    </tr>
    </c:forEach>
  </table>

      
<center>
 		 <div  id="pagelist" align="center" >
    	 <c:if test="${allRows!=0 }">  
			<ul>	 
            		
              <li> <a href="#" onclick="return setPage(${jobid },1)">首页 </a></li>
              <li>  <a href="#" onclick="return frontPageCheck(${jobid },${page-1});">上一页</a></li>
              <li>  <a href="#" onclick="return backPageCheck(${jobid },${page+1});">下一页</a></li>
              <li>  <a href="#" onclick="return setPage(${jobid },${allPage})">末页</a></li>
          	  <li> 第${page}页 </li>
              <li>共${allRows}条</li>
            <li>共${allPage}页</li>
          </ul>
     	 </c:if>
          <c:if test="${allRows==0 }"> 
          	<font color="#FF0000">没有记录!</font>
          </c:if> 

  </div>
 	</center>
        
</sf:form>

<%--
<center>
				总共有${allPage}页, 当前是第${page}页 <a href="/tastExecute/list.do/${jobid }/1"><font
					 color="blue">首页</font> </a> &nbsp;<a onclick="goPage(${jobid},${page-1})"
					href="/tastExecute/list.do/${jobid }/${page-1}"
					onclick="return frontPageCheck();"><font  color="red">上一页</font>
				</a>&nbsp; <a href="/tastExecute/list.do/${jobid }/${page+1}"
					onclick="return backPageCheck();"><font  color="red">下一页</font>
				</a>&nbsp; <a href="/tastExecute/list.do/${jobid }/${allPage}"><font 
					color="blue">末页</font> </a>
	</center>--%>
<p>&nbsp;</p>
	</article>
	</div>
	</div>
	
<script type="text/javascript">
	function list(taskid,projName,time){
		document.getElementById("testTastexcute").action="/caseDetail/list.do";
		document.getElementById("taskId").value=taskid;
		document.getElementById("testTastexcute").submit();
	}

function frontPageCheck(jobid,page)
	{
		if(${page > 1})
		{
			document.getElementById("page").value=page;
			document.getElementById("testTastexcute").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(jobid,page)
	{
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("testTastexcute").submit();
			return true;
		}			
		return false;
	}
	
	
		function setPage(jobid,page)
	{
		if(page==1){
			document.getElementById("page").value=1;
		}else{
			document.getElementById("page").value=page;
		}
			document.getElementById("testTastexcute").submit();
		return true;
	}
	
	
	function delbatch(){
		var _id = document.getElementsByName("deletebox");
		var id;
		for(var i=0;i<_id.length;i++){
			if(_id[i].checked){
				id = _id[i];
			}
		}
		if(confirm('批量删除会将选中的任务用例和日志全部删除！耗时较长，请耐心等待！\r\n确定要删除吗？')==true){
			document.getElementById("testTastexcute").action="/tastExecute/deletebatch.do";
			document.getElementById("testTastexcute").submit();
			return true;
		}
	}
	
	
	function del(id,name){
		if(confirm('即将删除任务下的所有用例和日志！请谨慎操作！\r\n确定要删除 '+name+' 吗？')==true){
			document.getElementById("testTastexcute").action="/tastExecute/delete.do?id="+id;
			document.getElementById("testTastexcute").submit();
			return true;
		}
	}
	
	function showPic1(id){
			document.getElementById("testTastexcute").action="/tastExecute/"+id+"/showPic1.do";
			document.getElementById("testTastexcute").submit();
	}
	
	function selk(sel) 
	{
		var _id = document.getElementsByName("deletebox");
		if (sel.checked) {
		 for (var i = 0; i < _id.length; i ++) 
		 {
			if (_id[i].type == "checkbox") _id[i].checked = true;
		 }
		 
		}else
		{
		  for (var i = 0; i < _id.length; i ++) 
		 {
			if (_id[i].type == "checkbox") _id[i].checked = false;
		 }
		}
		return;
	}
	
	function showDiv(id,name){
		var status = document.getElementById("loginstatus").value;
		if(status=="false"){
			if(window.confirm("你未登录哦，要先去登录吗？")){
				var url = '/progressus/signin.jsp';
				window.location.href=url;
			}else{
				return false; 
			} 	
		}else if(id=="888888"&&name=="batch"){
			delbatch();
		}else{
			del(id,name);
		}		
	}
	
</script>
</body>
</html>
