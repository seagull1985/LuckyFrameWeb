<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目版本添加</title>


<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/js/easyui/themes/default/easyui.css" rel="stylesheet"
	type="text/css" />
<link href="/js/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript"
	src="/js/My97DatePicker/WdatePicker.js"></script>
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
	src="/js/My97DatePicker/WdatePicker.js"></script>
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
			<li class="active">质量管理</li>
			<li class="active"><a href="/projectVersion/load.do">版本信息</a></li>
			<li class="active">添加版本计划信息</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">添加版本计划信息</h1>
			</header>

	<sf:form modelAttribute="projectversion" method="post" onsubmit="return validTime(this)">
	<input name="human_cost" id="human_cost" type="hidden"  />
	<input name="human_costdev" id="human_costdev" type="hidden"  />
	<input name="human_costtest" id="human_costtest" type="hidden"  />
	<input name="per_dev" id="per_dev" type="hidden"  />
	<input name="per_test" id="per_test" type="hidden"  />
    <input name="devtime_deviation" id="devtime_deviation" type="hidden"  />
	<input name="devdelay_days" id="devdelay_days" type="hidden"  />
	<input name="testtime_deviation" id="testtime_deviation" type="hidden"  />
	<input name="testdelay_days" id="testdelay_days" type="hidden"  />
	<input name="code_DI" id="code_DI" type="hidden"  />
	<input name="actually_launchdate" id="actually_launchdate" type="hidden"  />
    <input name="actually_demand" value="0" id="actually_demand" type="hidden"  />
    <input name="codestandard_zd" id="codestandard_zd" value="0" type="hidden"  />
    <input name="codestandard_yz" id="codestandard_yz" value="0" type="hidden"  />
    <input name="codestandard_zy" id="codestandard_zy" value="0" type="hidden"  />
    <input name="codeline" id="codeline" value="0" type="hidden"  />
    <input name="bug_zm" id="bug_zm" value="0" type="hidden"  />
    <input name="bug_yz" id="bug_yz" value="0" type="hidden"  />
    <input name="bug_yb" id="bug_yb" value="0" type="hidden"  />
    <input name="bug_ts" id="bug_ts" value="0" type="hidden"  />
    <input name="actually_devstart" id="actually_devstart" type="hidden"  />
    <input name="actually_devend" id="actually_devend" type="hidden"  />
    <input name="actually_teststart" id="actually_teststart" type="hidden"  />
    <input name="actually_testend" id="actually_testend" type="hidden"  />
    <input name="qualityreview" id="qualityreview" type="hidden"  />
    <input name="versiontype" id="versiontype" value="0" type="hidden"  />
    <input name="perdemand" id="perdemand" value="0" type="hidden"  />
    

		<table width="70%"  align="center" class="rect"  frame="box" cellPadding=5 border=1 style="background-color:rgba(240,255,240,0.5);">
  
<tr>
				<td height="30" align="left">项目名称</td>
			  <td height="30" colspan="3">
              <sf:select path="projectid" id="projectid" class="easyui-combobox"  required="true" validType="selectValueRequired['#projectid']"  missingMessage="项目名必选" invalidMessage="项目名必选"  >            
                   <sf:option value="0">请选择</sf:option>
                 <c:forEach var="p" items="${projects}">
                  <sf:option value="${p.projectid}">${p.projectname}</sf:option>
                  </c:forEach>
                </sf:select>                        </td>
			</tr>
			<tr>
			<td height="30" align="left">版本号</td>
			  <td height="30" ><sf:input path="versionnumber"
						id="versionnumber" 
						cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,20]'"  missingMessage="版本号不能为空" invalidMessage="版本号不能为空"  />&nbsp;<sf:errors path="versionnumber"
						cssClass="error_msg" /> </td>
			<td height="30" align="left">版本链接</td>
			  <td height="30" ><sf:input path="zt_versionlink" id="zt_versionlink"  style="width:320PX"
						/>&nbsp;</td>
		  </tr>
		  <tr>
				<td height="30" align="left">计划上线需求数</td>
			  <td height="30" colspan="1"><sf:input path="plan_demand"
						id="plan_demand" 
						cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,20]'"  missingMessage="计划上线需求数" invalidMessage="计划上线需求数"  />&nbsp;<sf:errors path="plan_demand"
						cssClass="error_msg" /> 
						</td>
		  </tr>
			<tr>
		  	<td height="30" align="left">投入开发人员</td>
			  <td height="30" colspan="1"><sf:input path="dev_member"
						id="dev_member" 
						cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,50]'"  missingMessage="投入开发人员" invalidMessage="投入开发人员"  />&nbsp;<sf:errors path="dev_member"
						cssClass="error_msg" /> </td>
			<td height="30" align="left">投入测试人员</td>
			  <td height="30" colspan="1"><sf:input path="test_member"
						id="test_member" 
						cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,50]'"  missingMessage="投入测试人员" invalidMessage="投入测试人员"  />&nbsp;<sf:errors path="test_member"
						cssClass="error_msg" /> </td>
			  </tr>		   
		       
		  <tr>
				<td height="30" align="left">计划达成日期</td>
							<td height="30" colspan="3"><sf:input path="plan_launchdate" id="plan_launchdate"
									onclick="WdatePicker({isShowClear:false,readOnly:true});"
									readonly="true" style="width:100px" value="${plan_launchdate}" />&nbsp;&nbsp;</td>
									
<%-- 				<td height="30" align="left">实际达成日期</td>
							<td height="30" colspan="1"><sf:input path="actually_launchdate" id="actually_launchdate"
									onclick="WdatePicker({isShowClear:false,readOnly:true});"
									readonly="true" style="width:100px" value="${actually_launchdate}" />&nbsp;&nbsp;</td> --%>
		  </tr>
		   <tr>
				<td height="30" align="left">计划开发开始日期</td>
							<td height="30" colspan="1"><sf:input path="plan_devstart" id="plan_devstart"
									onclick="WdatePicker({isShowClear:false,readOnly:true});"
									readonly="true" style="width:100px" value="${plan_devstart}" />&nbsp;&nbsp;</td>
									
				<td height="30" align="left">计划开发结束日期</td>
							<td height="30" colspan="1"><sf:input path="plan_devend" id="plan_devend"
									onclick="WdatePicker({isShowClear:false,readOnly:true});"
									readonly="true" style="width:100px" value="${plan_devend}" />&nbsp;&nbsp;</td>
		    </tr>

		    <tr>
				<td height="30" align="left">计划测试开始日期</td>
							<td height="30" colspan="1"><sf:input path="plan_teststart" id="plan_teststart"
									onclick="WdatePicker({isShowClear:false,readOnly:true});"
									readonly="true" style="width:100px" value="${plan_teststart}" />&nbsp;&nbsp;</td>
									
				<td height="30" align="left">计划测试结束日期</td>
							<td height="30" colspan="1"><sf:input path="plan_testend" id="plan_testend"
									onclick="WdatePicker({isShowClear:false,readOnly:true});"
									readonly="true" style="width:100px" value="${plan_testend}" />&nbsp;&nbsp;</td>
		    </tr>

		  <tr>
				<td height="30" align="left">版本说明</td>
			  <td height="30" colspan="3"><sf:textarea cols="100" rows="5" path="imprint"
						id="imprint"
						cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,500]'"  missingMessage="版本描述不能为空" invalidMessage="版本描述不能为空"  
						 />&nbsp;<sf:errors path="imprint"
						cssClass="error_msg" /></td>
			</tr>

			 <tr>
				<td height="30" align="left">备注</td>
			  <td height="30" colspan="4"><sf:textarea cols="100" rows="5" path="remark"
						id="remark"		cssClass="easyui-validatebox" 					
						data-options="validType:'minLength[0,248]'"  invalidMessage="备注内容非法"  />&nbsp;<sf:errors path="remark"
						cssClass="error_msg" /></td>
			</tr>
			<tr>
			  <td height="30" colspan="4"><div class="error_msg">${message}</div></td>
			</tr>
		  
		  	<tr>
				<td width="194" height="30">&nbsp;</td>
			  <td width="40%" height="30" align="center"><input name="addBtn" type="submit"
					class="button gray" id="addBtn" value="添加" /></td>
	          <td  align="center" colspan="2"><a
						href="/projectVersion/load.do" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">返 回</span></a></td>
	         
		  </tr>
            
		</table>
	  <p></p>
	</sf:form>
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
			 
			
			
			$("#projectversion").form({
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
			alert("项目名必选");
			return  false;
		}
		return true;
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
