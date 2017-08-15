<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>

<title>用例日志明细</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body onload="init()">
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width:auto;">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">UTP</li>
			<li class="active">用例&日志</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">用例&日志</h1>
			</header>
		
<sf:form method="post" modelAttribute="testCasedetail">

<input name="flag" id="flag" type="hidden" value="2"  />
 <table width="90%"  align="center" class="rect"  height="100px" cellPadding="1px" border="1px" bordercolor="#CCCCCC">
  <tr>
      <td width="10%" height="20" colspan="4" style="font-size:15px;font-weight:600;">
          项目名称
      &nbsp;&nbsp;<sf:select path="projName" id="projName" onchange="getTastList()">
          <%-- <sf:option value="">全部</sf:option> --%>
          <c:forEach var="proj" items="${projects }"  begin="0" step="1" varStatus="i" >
            <sf:option value="${proj.projectid}">${proj.projectname}</sf:option>
          </c:forEach>
        </sf:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   执行时间
        <%-- <sf:input path="startDate" 	class="Wdate" onblur="aa()"  onclick="WdatePicker({isShowClear:false,readOnly:true});"  /> --%>
        &nbsp;&nbsp;<sf:input path="startDate" 	class="Wdate" onclick="WdatePicker({isShowClear:false,readOnly:true})"  />&nbsp;至&nbsp;<sf:input path="endDate" class="Wdate" onclick="WdatePicker({isShowClear:false,readOnly:true})"  />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <span id="span_id">任务名称&nbsp;&nbsp;
         <sf:select path="taskId" id="taskId" style=" width:400px;">
         <c:if test="${size==0 }">
           <sf:option value="99999999">没有任务</sf:option>
                </c:if>
            <c:if test="${size>0 }">
            <%--   <sf:option value="0">全部</sf:option> --%>
                 <c:forEach var="task" items="${tasks }"  begin="0" step="1" varStatus="i" >
                  <sf:option value="${task[0]}">${task[1]}</sf:option>
                </c:forEach>
            </c:if>
      </sf:select> </span>     </td>
    </tr>
  <tr>
    <td width="80%" height="20" align="left" style="font-size:15px;font-weight:600">用例编号
    &nbsp;&nbsp;<input type="text" name="caseno" id="caseno" value="${caseno}" />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用例状态
       &nbsp;&nbsp;<sf:select path="casestatus" style=" width:200px;">
        <sf:option value="">全部</sf:option>
        <sf:option value="0">成功</sf:option>
        <sf:option value="1">失败</sf:option>
        <sf:option value="2">锁定</sf:option>
        <sf:option value="4">未执行</sf:option>
    </sf:select></td>
    <td width="20%" height="20" colspan="2" align="center">
    <input	name="button" type="submit" class="button gray" id="button" value="查询" />
&nbsp;&nbsp;
  <button  class="button gray" onclick="goBack()" >返回</button></td>
    </tr>
  </table>
  <input name="page" id="page" type="hidden"  />
<p>
</p>

	<table width="90%" align="center" class="bordered"><c:forEach var="t" items="${list}" begin="0" step="1" varStatus="i"></c:forEach>
  </table>
 
<center>
        <table width="90%" align="center" class="bordered">
          <tr>
            <th width="2%" align="center" bgcolor="#B9DCFF">选择</th>
            <th width="3%" height="40" align="center" bgcolor="#B9DCFF">序号</th>
           
            <th width="6%" align="center" bgcolor="#B9DCFF" nowrap="nowrap">用例编号</th>
            <th width="46%" height="40" align="center" bgcolor="#B9DCFF">用例名称</th>
            <th width="6%" height="40" align="center" bgcolor="#B9DCFF">用例版本</th>
            <th width="12%" height="40" align="center" bgcolor="#B9DCFF">用例执行时间</th>
            <th width="6%" height="40" align="center" bgcolor="#B9DCFF">用例状态</th>
            <th width="10%" align="center" bgcolor="#B9DCFF">操作</th>
          </tr>
          <c:forEach var="t" items="${list}" begin="0" step="1" varStatus="i">
            <tr>
              <td width="2%" nowrap="nowrap" align="center"><c:if test="${t.casestatus!=0 }">
                  <input type="checkbox" value="${t.caseno }%${t.caseversion}" onclick="" name="cases"/>
                </c:if>
                &nbsp;</td>
              <td width="3%" height="25" align="center"> ${i.index+1}&nbsp;</td>
              
              <td width="6%" align="center">${t.caseno }&nbsp;</td>
              <td width="46%" height="25">${t.casename }&nbsp;</td>
              <td width="6%" height="25" align="center">${t.caseversion }&nbsp;</td>
              <td width="12%" height="25"><fmt:formatDate value="${t.casetime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              <c:choose>
              <c:when test="${t.casestatus==0}">
              <td width="6%" height="25" style="text-align:center;color:#00bf5f">${t.casestatus_str}</td>
               </c:when>
               <c:when test="${t.casestatus==1}">
              <td width="6%" height="25" style="text-align:center;color:#ff0000">${t.casestatus_str}</td>
               </c:when>
               <c:otherwise>
              <td width="6%" height="25" style="text-align:center;color:#FF7F00">${t.casestatus_str}</td>
               </c:otherwise>
              </c:choose>
              <td width="10%" align="center">&nbsp;<span  onclick="showChild(this,'${t.id }')" style="cursor:pointer; color:blue;"><img src="../pic/detail.png"
						width="20" height="20" border="0" title="详情" /></span>&nbsp;
                  <c:if test="${t.casestatus!=0}"> <span onclick="showDiv('${t.id }','${t.testTaskexcute.testJob.taskName}','${t.testTaskexcute.createTime}',1)"  style="cursor:pointer; color:blue;" ><img src="../pic/run.png"
						width="20" height="20" border="0" title="执行" /></span> </c:if>              </td>
            </tr>
            <c:if test="${i.index+1==1}">
              <input name="caseId"  type="hidden" value="${t.id }"/>
            </c:if>
            <tr id="tr${t.id }" style="display:none">
              <td >&nbsp;</td>
              <td >&nbsp;</td>
              <td colspan="6" align="right" width="100%"><iframe id="fm${t.id }" frameborder="0" width="98%"
					height="300" src="" scrolling="auto"></iframe></td>
            </tr>
          </c:forEach>
        </table>
        
  </center>
		 <c:if test="${allRows!=0 }"> 
          <div style="width:90%; margin-left:60px;">
          <fieldset style="text-align:left; font-size:13px; "> 
          <legend style="font-size:13px; color:#666666">操作</legend>
            <input name="isSetAll"  type="hidden" value="false"/>
            <font color="green">全选本页用例</font><font color="green">
              <input type="radio" name="chf" value="0" onclick="selk(this)" />
            </font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">所有非成功状态的用例
          <input type="radio" name="ckAll" onclick="setIsCheck()" value="ALLFAIL"  checked="checked"/>
          </font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input name="button2" type="submit" class="button gray" onclick="showDiv(0,0,0,'2')"  id="button2" value="批量执行" />
          
          <br/>
          <font color="#FF6600">备注：<br/>
          勾选【全选本页用例】，以此为准，其他勾选选项无效；<br/>
          勾选【所有非成功状态的用例】，以此为准，其他勾选选项无效。
          </font>
          </fieldset>
      </div>
            </c:if> 
   <center>
          <div  id="pagelist" align="center" >
             <c:if test="${allRows!=0 }"> 
               <ul>	 
                  <li> <a href="#" onclick="return setPage('${taskId}',1)">首页 </a></li>
                  <li>  <a href="#" onclick="return frontPageCheck('${taskId}',${page-1});">上一页</a></li>
                  <li>  <a href="#" onclick="return backPageCheck('${taskId}',${page+1});">下一页</a></li>
                <li>  <a href="#" onclick="return setPage('${taskId}',${allPage})">末页</a></li>
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
	<p>&nbsp;</p>
	</article>
	</div>
</div>

<script type="text/javascript">

	function del(id){
		if(confirm('确定要删除此条记录吗？')==true){
			document.getElementById("taskjob").action="testJobs/"+id+"/delete.do";
			document.getElementById("taskjob").submit();
		}
	}
	function frontPageCheck(taskId,page)
	{
		if(${page > 1})
		{
			document.getElementById("page").value=page;
			document.getElementById("testCasedetail").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(taskId,page)
	{
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("testCasedetail").submit();
			return true;
		}			
		return false;
	}
	
	
	function setPage(taskId,page)
	{
		if(page==1){
			document.getElementById("page").value=1;
		}else{
			document.getElementById("page").value=page;
		}
		//alert(document.getElementById("page").value);
			document.getElementById("testCasedetail").submit();
		return true;
	}
	function goBack(){
		document.getElementById("taskId").value="";		
		document.getElementById("testCasedetail").action="/tastExecute/load.do";
		document.getElementById("testCasedetail").submit();
		return true;
	}

function getTastList(){
	var startDate=document.getElementById("startDate").value;
	var projNameType=document.getElementById('projName');
	var projid=projNameType.options[projNameType.selectedIndex].value;
	var tastName=document.getElementById('taskId');
	$.ajax({
		type : "post",
		dataType:"json",
		contentType:"application/x-www-form-urlencoded:charset=UTF-8",
        async:true,
		url:encodeURI("/caseDetail/getTastNameList.do?startDate="+startDate+"&projid="+projid),
		success:function(json){
			for(var i=tastName.options.length;i>=0;i--)
			{
				tastName.options.remove(i);   
			}
			if(json!=""){
				for(var i=0;i<json.length;i++)
				{			
                        var option = new Option(json[i][1],json[i][0]);    
						tastName.options.add(option); 
				}  
			}else{
					var option = new Option("没有任务","99999999");    
					tastName.options.add(option); 
			}
		},
		error:function(){
			alert("请稍后再试!");
		}
		
	});
}


function execCase(id,projName,createTime){
	$.ajax({
		type : "post",
		dataType:"text",
		contentType:"application/x-www-form-urlencoded:charset=UTF-8",
        async:true,
		url:encodeURI("/caseDetail/"+id+"/execCase.do"),
		data:"projName="+projName+"&createTime="+createTime,
		success:function(json){
			alert(json);
		},
		error:function(){
			alert("系统异常，请稍后再试!");
		}

	});

}


function execCaseBatch(){
	$.ajax({
		type : "post",
		dataType:"json",
		contentType:"application/x-www-form-urlencoded:charset=UTF-8",
        async:true,
		url:encodeURI("/caseDetail/"+id+"/execCase.do"),
		success:function(json){
			alert(json);
		},
		error:function(){
			alert("系统异常，请稍后再试!");
		}

	});

}

function showChild(img,c_id) {
		var tr = document.getElementById("tr" + c_id);
		var fm = document.getElementById("fm" + c_id);
		
		if (tr.style.display == "") {
			tr.style.display = "none";
			
		} else {
			tr.style.display = "";
			fm.src = "/logDetail/list/"+c_id+".do";
		}
	}
	
var count=0;
function aa(){
	count++;
	if(count==2){
		changeProjName();
		count=0;
	}
}

function changeProjName(){
	var projNameType=document.getElementById('projName');
	var startDate=document.getElementById('startDate');
	projNameType.fireEvent("onchange"); 
}

function showDiv(id,projName,createTime,opr){
	var status = document.getElementById("loginstatus").value;
	if(status=="false"){
		if(window.confirm("你未登录哦，要先去登录吗？")){
			var url = '/progressus/signin.jsp';
			window.location.href=url;
		}else{
			return false; 
		} 	
	}else{
		if(opr=="1"){
			var url ="/userlogin/permissionboolean.do?permissioncode=case_ex";
		    jQuery.getJSON(url,null,function call(result){
		    	if(result.data[0]==null){
		    		alert("你好，当前用户无权限进行此操作，请联系软件质量室！");
					return false;
		    	}else if(result.data[0]=="true"){
		    		execCase(id,projName,createTime);
					return true;
				 }else{
					alert("你好，当前用户无权限进行此操作，请联系软件质量室！");
					return false;
				   }
		       });			
		}else if(opr=="2"){
			pass();
		}
	}		
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
	function setIsCheck(){
		var f = document.getElementById("testCasedetail");
		
		if(f.ckAll.checked){
			f.isSetAll.value="true";
			f.chf.checked=false; 
			var _id = document.getElementsByName("cases");
			for (var i = 0; i < _id.length; i ++) 
			 {
				if (_id[i].type == "checkbox") _id[i].checked = false;
			 }
		}else{
			f.isSetAll.value="false";
		}
	}
	
	   
		function selk(sel) 
		{
			var _id = document.getElementsByName("cases");
			var f = document.getElementById("testCasedetail");
			f.ckAll.checked=false;
			f.isSetAll.value="false";
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
	
		function pass() {
	 	   var form = document.getElementById("testCasedetail");
		
	 	  if( form.ckAll.checked){
				if (confirm("将【所有非成功状态的用例】重新执行，您确定吗？")) {
					 form.flag.value="1";
					 form.action="/caseDetail/execCaseBatch.do";
					 form.button2.disabled=true;
					 form.submit();
				 return true;
				}
				 return false;
	 	  }else{
		 	 
				if (confirm("将选择的【非成功状态的用例】重新执行，您确定吗？")) {
					 form.action="/caseDetail/execCaseBatch.do";
					 form.button2.disabled=true;
					 form.submit();
					 return true;
				}
				 return false;
		 	  }
		}

</script>
</body>
</html>
