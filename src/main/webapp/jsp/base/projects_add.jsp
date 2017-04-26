<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目添加</title>
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
			<li class="active"><a href="/sectorProjects/list.do">项目管理</a></li>
			<li class="active">添加项目</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">添加项目</h1>
			</header>   
	
					<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
					<div class="panel panel-default">
						<div class="panel-body">
							<h3 class="thin text-center">添加一个项目</h3>

							<sf:form  modelAttribute="sectorprojects" method="post">
								<div class="top-margin">
									<label>项目名称 <span class="text-danger">*</span></label>
									<sf:input type="text" class="form-control" path="projectname" id="projectname"/>
								</div>
								<div class="top-margin">
									<label>项目经理 <span class="text-danger">*</span></label>
									<sf:input type="text" class="form-control" path="projectmanager" id="projectmanager"/>
								</div>
								<div class="top-margin">
								<label>所属部门 <span class="text-danger">*</span></label>
								<sf:select type="text" class="form-control"
									path="secondarySector.sectorid"
									id="secondarySector.sectorid">
									<sf:option value="0">请选择</sf:option>
									<c:forEach var="p" items="${secondarysector}">
										<sf:option value="${p.sectorid}">${p.departmentname}</sf:option>
									</c:forEach>
								</sf:select>
							</div>
							<div class="top-margin">
									<label>项目类型 <span class="text-danger">*</span></label><br/>								
									<sf:radiobutton path="projecttype" id="projecttype" value="0" /> 质量管理项目&nbsp;&nbsp;&nbsp;&nbsp; 
				                    <sf:radiobutton	path="projecttype" id="projecttype" value="1" /> 自动化管理项目(testlink)
								</div>

</br>
								<div class="row">
									<div class="col-lg-4 text-center" style="width:900px">
										<button class="btn btn-action" type="submit">添加项目</button>
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
