<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户信息修改</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/js/easyui/themes/default/easyui.css" rel="stylesheet"	type="text/css" />
<link href="/js/easyui/themes/icon.css" rel="stylesheet" type="text/css" />

<style type="text/css">
<!--
.STYLE1 {
	font-size: 12px;
	color: #ffffff;
}

-->
.error_msg {
	font-size: 12px;
	color: #f00;
}

.tip {
	font-size: 12px;
	color: blue;
}
</style>
<script language="JavaScript" type="text/javascript"
 src="/js/jslib.js"></script>


</head>

<body onload="init()">
	<div>  
        <%@ include file="/head.jsp" %>
    </div>
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active"><a href="/userInfo/load.do">用户管理</a></li>
			<li class="active">用户信息修改</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">修改用户信息</h1>
			</header>   
			
<%--	<sf:form modelAttribute="userinfo" method="post" onsubmit="return validTime(this)">
         
		<table width="70%"  align="center" class="rect"  frame="box" cellPadding=5 border=1 style="background-color:rgba(240,255,240,0.5);">
  
        <tr>
             <td height="30" align="left">用户名</td>
			  <td height="30" colspan="1"><sf:input path="usercode"  id="usercode" 
						data-options="validType:'minLength[0,20]'"  missingMessage="用户名" invalidMessage="用户名"  />&nbsp;<sf:errors path="usercode"
						cssClass="error_msg" /></td>
		</tr>
			<tr>
			  <td height="30" colspan="4"><div class="error_msg">${message}</div></td>
			</tr>
		  
		  	<tr>
			  <td width="40%" height="30" align="center"><input name="addBtn" type="submit"
					class="button gray" id="addBtn" value="添加" /></td>
	          <td  align="center" colspan="2"><a
						href="/review/list.do" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">返 回</span></a></td>
	         
		  </tr>
            
		</table>
	  <p></p> 
	</sf:form>--%>
					<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
					<div class="panel panel-default">
						<div class="panel-body">
							<h3 class="thin text-center">修改账户信息</h3>

							<sf:form  modelAttribute="userinfo" method="post" onsubmit="return validTime(this)">
								<div class="top-margin">
									<label>真实姓名 <span class="text-danger">*</span></label>
									<sf:input type="text" class="form-control" path="username" id="username"/>
								</div>
								<div class="top-margin">
									<label>用户名 <span class="text-danger">*</span></label>
									<sf:input type="text" class="form-control" path="usercode" id="usercode"/>
								</div>
								<div class="top-margin">
								<label>所属部门 <span class="text-danger">*</span></label>
								<sf:select type="text" class="form-control"
									path="sectorid"
									id="sectorid">
									<sf:option value="0">请选择</sf:option>
									<c:forEach var="p" items="${secondarysector}">
										<sf:option value="${p.sectorid}">${p.departmentname}</sf:option>
									</c:forEach>
								</sf:select>
							</div>
							<div class="top-margin">
								<label>默认项目</label>
								<sf:select type="text" class="form-control"
									path="projectid"
									id="projectid">
									<c:forEach var="p" items="${projects}">
										<sf:option value="${p.projectid}">${p.projectname}</sf:option>
									</c:forEach>
								</sf:select>
							</div>
							<div class="top-margin">
									<label>角色 <span class="text-danger">*</span></label><br/>
									<c:forEach items="${userrole}" var="r">
									<label>${r.role}</label>									
									<sf:checkbox path="role" id="role" value="${r.id}" />&nbsp;&nbsp;&nbsp;&nbsp;
								</c:forEach>
								</div>

								<div class="row top-margin">
									<div class="col-sm-6">
										<label>密码 <span class="text-danger">*</span></label>
										<sf:input type="password" path="password" id="password" class="form-control" />
									</div>
									<div class="col-sm-6">
										<label>密码确认 <span class="text-danger">*</span></label>
										<sf:input type="password" path="password" id="password" class="form-control" />
									</div>
								</div>

</br>
								<div class="row">
									<div class="col-lg-4 text-center" style="width:900px">
										<button class="btn btn-action" type="submit">保存</button>
									</div>
								</div>
							</sf:form>
						</div>
					</div>

				</div>
				
  	<p>&nbsp;</p>
	</article>
	</div>  
    </div>
    
    <script type="text/javascript">

		function valid(f){
			return true;
		}
	
		$(function(){
			
			$.extend($.fn.validatebox.defaults.rules, {    
			   	 minLength: {    
			        validator: function(value, param){    
			            return value.length > param[0] && value.length<= param[1];    
			        } 
			    },
				 minLength2: {    
			        validator: function(value, param){    
			            return value.length > param[0] && value.length<= param[1];    
			        } 
			    },
				 minTime: {    
			        validator: function(value, param){    
			            return value.length > param[0] && value.length<= param[1];    
			        } 
			    },
				selectValueRequired: {  
					validator: function(value,param){  
					//console.info($(param[0]).find("option:contains('"+value+"')").val()); 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '项目名必选'  
			} 
				 
			}); 
			 
			
			
			$("#reviewinfo").form({
				validate:true
			});


			
		});

	var taskType="O";
	function  isShow(type){
		if(type=='Z'){
			document.getElementById('time_div').style.display='block';
		}else{
			document.getElementById('time_div').style.display='none';
		}
		taskType=type;
	}
	
	
	var type="O";
	function  isShow2(isSend){
		if(isSend=='1'){
			document.getElementById('tr_send').style.display='block';
		}else{
			document.getElementById('tr_send').style.display='none';
			document.getElementById('emailer').value="";
		}
		type=isSend;
	}
	
	function  validTime(f){
		var projectid=document.getElementById('projectid').value;
		if(projectid.isInt()==0){
			toastr.warning('项目名必选！'); 
			return  false;
		}
		return true;
	}
	
	
	function init(){
		if('${message}'!=''){
			if ('${message}' == '添加成功') {
				toastr.success('添加成功,请返回查询！');
			} else {
				toastr.warning('${message}'); 
			}
		}
		
	}
	</script>
</body>
</html>
