<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
	


<title>自动化调度管理中心</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {
	color: #ffffff
}

.STYLE6 {
	color: #FF0000;
	font-size: 12px;
}
-->
</style>
</head>

<body onload="init()" >
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width:auto;">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">UTP</li>
			<li class="active">调度配置</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">调度配置</h1>
			</header>

	<sf:form method="post" modelAttribute="taskjob">
		<input type="hidden" name="jobid" id="jobid" />
		<div align="right"></div>
		<table width="90%" align="center" class="rect" height="40" cellPadding="1"
			border="1" bordercolor="#CCCCCC">
			<tr>
				<td width="35%" align="left" valign="middle"><table
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12%" height="30" style="font-size:15px;font-weight:600">&nbsp;时间:&nbsp;&nbsp;
							<sf:input path="startDate" id="startDate"
									onclick="WdatePicker({isShowClear:false,readOnly:true});"
									readonly="true" class="Wdate" style="width:120px;height:95%" value="${startDate}" />&nbsp;&nbsp;
							<sf:select path="clientip" id="clientip">
								<c:forEach var="iplist" items="${iplist }" begin="0" step="1" varStatus="i">
									<sf:option value="${iplist}">${iplist}</sf:option>
								</c:forEach>
							</sf:select> 
							<a href="#" onclick="down()" style="text-decoration: none;"> <span class="btnold STYLE1"> 错误日志查看</span></a>
							&nbsp;&nbsp;
							<a href="#" onclick="showDiv(0,7)" ><span
									class="btnold STYLE1" > 测试项目上传</span></a> 
									</td>
						</tr>
					</table></td>
</tr>
<tr>
<td width="20%" align="left" style="font-size:15px;font-weight:600">&nbsp;任务调度名称:&nbsp;&nbsp;<sf:input path="taskName" />
				&nbsp;调度策略:&nbsp;&nbsp;<sf:select path="taskType" style="width:100px">
						<sf:option value="">全部</sf:option>
						<sf:option value="O">执行一次</sf:option>
						<sf:option value="D">每天</sf:option>
					</sf:select> &nbsp;
					<input name="button" type="submit" class="button gray" id="button" style="height:27.6px"
					value="查 询" onclick="list()" /> &nbsp;&nbsp;<a
					href="#" onclick="showDiv(0,6)" style="text-decoration: none;"> <span
						class="btnold STYLE1" style="width: 70px;"> 添 加</span>
				</a></td>


			</tr>
		</table>
		<input name="page" id="page" type="hidden" />
		<br />
		<table width="100%" align="center" class="bordered">
			<tr>
				<th width="2%" height="40" nowrap="nowrap" >序号</th>
				<th width="10%" height="40" nowrap="nowrap" >操作</th>
				<th width="15%" height="40" nowrap="nowrap" >调度名称</th>
				<th width="25%" height="40" nowrap="nowrap" >【项目名】 计划名
				</th>
				<th width="8%" height="40" nowrap="nowrap" >调度策略</th>
				<th width="12%" height="40" nowrap="nowrap" >开始日期</th>
				<th width="10%" height="40" nowrap="nowrap" >执行时间表达式</th>
				<th width="8%" height="40" nowrap="nowrap" >客户端IP</th>
				<th width="5%" height="40" nowrap="nowrap" >线程数</th>
				<th width="5%" height="40" nowrap="nowrap" >状态</th>
			</tr>
			<c:forEach var="t" items="${tjlist }" begin="0" step="1"
				varStatus="i">
				<tr>
					<td height="25" align="center">${i.index+1}&nbsp;</td>
									<td height="25" align="center" style="text-align:center;"><a href="#"
						onclick="showDiv(${t.id},1)" style="cursor: pointer;"><img src="../pic/run.png"
						width="20" height="20" border="0" title="立即运行" />
					</a><a href="/testJobs/show.do?id=${t.id}"><img src="../pic/detail.png"
						width="20" height="20" border="0" title="详情" /></a>
						<c:if test="${t.showRun==true }">
							<c:if test="${t.state==0 }">
								<a href="#" onclick="showDiv(${t.id},2)" style="cursor: pointer;"><img src="../pic/run.png"
						width="20" height="20" border="0" title="启动" /></a>	        </c:if>
							<c:if test="${t.state==1 }">
								<a href="#" onclick="showDiv(${t.id},3)" style="cursor: pointer;"><img src="../pic/close.png"
						width="20" height="20" border="0" title="关闭" /></a>	      </c:if>
						</c:if> <c:if test="${t.showRun==false }">
	   		  已失效
	     </c:if> <a href="#" onclick="showDiv(${t.id},4)" style="cursor: pointer;"><img src="../pic/update.png"
						width="20" height="20" border="0" title="修改" />
					</a><a href="#" onclick="showDiv(${t.id},5)"><img src="../pic/delete.png"
						width="20" height="20" border="0" title="删除" /></a></td>
					<td height="25" align="center"><a onclick="goTaskList(${t.id})"	style="cursor: pointer;">${t.taskName }</a></td>
					<%-- 			        <td height="25" align="center"><a href="/tastExecute/list.do?${t.id}">${t.name }</a></td> --%>
					<td height="25" align="center">【<b>${t.planproj }</b>】${t.testlinkname }&nbsp;</td>
					<td height="25" align="center"><c:if
							test="${t.taskType=='O' }">
                    只执行一次                    </c:if> <c:if
							test="${t.taskType=='D' }">
               	  每天执行                    </c:if></td>
					<td height="25" align="center"><c:if
							test="${t.showRun==false }">
							<span style="color: blue">${t.startDate }&nbsp;
								${t.startTime }</span>
						</c:if> <c:if test="${t.showRun==true }">
	   		 ${t.startDate }&nbsp; ${t.startTime }
          </c:if></td>
					<td height="25" align="center">${t.startTimestr}</td>
					<td height="25" align="center">${t.clientip}</td>
					<td height="25" align="center">${t.threadCount}</td>
					<td height="25" align="center">${t.state_str}</td>
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
	function down(){		
		var date=document.getElementById("startDate").value;
		var clientip=document.getElementById("clientip").value;
		var url = '/testJobs/down.do?startDate='+date+'&clientip='+clientip;
		window.open(url);
/* 		document.getElementById("taskjob").action="/testJobs/down.do";
		document.getElementById("taskjob").submit(); */
	}
	
	function list(){
		startdate = document.getElementById("start_date").value;
		document.getElementById("taskjob").action="/testJobs/list.do";
		document.getElementById("taskjob").submit();
	}
	
	function del(id){
		if(confirm('确定要删除此条记录吗？')==true){
			document.getElementById("taskjob").action="/testJobs/delete.do?id="+id;
			document.getElementById("taskjob").submit();
		}
	}
	
	
	
	function frontPageCheck(page)
	{
		if(${page > 1})
		{
			document.getElementById("page").value=page;
			document.getElementById("taskjob").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("taskjob").submit();
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
			document.getElementById("taskjob").submit();
		return true;
	}
	
	
	function goBack(){
		document.getElementById("taskjob").action="/testJobs/add.do";
		document.getElementById("taskjob").submit();
		return true;
	}
	
	
	function startNow(id){
		$.ajax({
			type : "post",
			dataType:"text",
			async:true,
			url:encodeURI("/testJobs/startNow.do?id="+id),
			success:function(json){
				alert(json);
			},
			error:function(){
				alert("系统异常，请稍后再试!");
			}
	
		});

	}
	
	function run(id){
		$.ajax({
			type : "post",
			dataType:"text",
			async:true,
			url:encodeURI("/testJobs/run.do?id="+id),
			success:function(json){
				alert(json);
			},
			error:function(){
				alert("系统异常，请稍后再试!");
			}
	
		});
	}
	
	function remove(id){
		$.ajax({
			type : "post",
			dataType:"text",
			async:true,
			url:encodeURI("/testJobs/remove.do?id="+id),
			success:function(json){
				alert(json);
			},
			error:function(){
				alert("系统异常，请稍后再试!");
			}
	
		});

	}
	function goTaskList(jobid){
		document.getElementById("taskjob").action="/tastExecute/list.do";
		document.getElementById("jobid").value=jobid;
		document.getElementById("taskjob").submit();
		return true;
	}
	
	function permissionboolean(code,tastid){
	var url ="/userlogin/permissionboolean.do?permissioncode="+code;
    jQuery.getJSON(url,null,function call(result){
    	if(result.data[0]==null){
    		alert("你好，当前用户无权限进行此操作，请联系软件质量室！");
			return false;
    	}else if(result.data[0]=="true"){
    		if(code=="tast_run"){
    			run(tastid);
    		}else if(code=="tast_remove"){
    			remove(tastid);
    		}else if(code=="tast_ex"){
    			startNow(tastid);
    		}
			return true;
		 }else{
			alert("你好，当前用户无权限进行此操作，请联系软件质量室！");
			return false;
		   }
       });
	}

	function showDiv(tastid,opr){
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
				permissionboolean("tast_ex",tastid);
		    }else if(opr=="2"){
		    	permissionboolean("tast_run",tastid);
		    }else if(opr=="3"){
		    	permissionboolean("tast_remove",tastid);
		    }else if(opr=="4"){		    	
				var url = '/testJobs/update.do?id='+tastid;
				window.location.href=url;	
		    }else if(opr=="5"){		    	
		    	del(tastid);
		    }else if(opr=="6"){		    	
				var url = '/testJobs/add.do';
				window.location.href=url;
		    }else if(opr=="7"){
		    	var clientip=document.getElementById("clientip").value
				var url = '/testJobs/to_upload.do?clientip='+clientip;
				window.location.href=url;
		    }else{ 
		       alert("操作码有误，请联系软件质量室相关人员！"); 
		       return false;
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
</body>
</html>
