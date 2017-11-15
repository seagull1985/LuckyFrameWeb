<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评审信息添加</title>

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
			<li class="active"><a href="/review/load.do">项目评审记录</a></li>
			<li class="active">增加评审记录</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">增加评审记录</h1>
			</header>   
			
	<sf:form modelAttribute="reviewinfo" method="post" onsubmit="return validTime(this)">
        <input type="hidden" id="confirm_date" name="confirm_date" value=""/>
        
		<table width="70%"  align="center" class="rect"  frame="box" cellPadding=5 border=1 style="background-color:rgba(240,255,240,0.5);">
  
<tr>
			<td height="30" align="left">项目名称</td>
			  <td height="30" >
              <sf:select path="projectid" id="projectid" class="easyui-combobox"  required="true" validType="selectValueRequired['#projectid']"  missingMessage="项目名必选" invalidMessage="项目名必选"  >            
                   <sf:option value="0">请选择</sf:option>
                 <c:forEach var="p" items="${projects}">
                  <sf:option value="${p.projectid}">${p.projectname}</sf:option>
                  </c:forEach>
                </sf:select> </td>
             <td height="30" align="left">版本号</td>
			  <td height="30" colspan="1"><sf:input path="version"  id="version" 
						data-options="validType:'minLength[0,50]'"  missingMessage="版本号" invalidMessage="版本号"  />&nbsp;<sf:errors path="version"
						cssClass="error_msg" /> </td>
			</tr>
		  <tr>
			<td height="30" align="left">评审时间</td>
			 <td height="30" colspan="1"><sf:input path="review_date" id="review_date"  cssClass="easyui-validatebox" required="true"  missingMessage="评审时间不能为空"
			  	onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'});"
						readonly="true" style="width:130px" value="${review_date}" /> <sf:errors path="review_date"
					cssClass="error_msg" /></td>

		  	<td height="30" align="left">评审类型</td>
			  <td height="30"> 
			  	<sf:select path="review_type" id="review_type" class="easyui-combobox"  required="true" validType="selectValueRequired['#review_type']">   
			  	<sf:option value="代码评审">代码评审</sf:option>         
                   <sf:option value="文档评审">文档评审</sf:option>
                   <sf:option value="设计评审">设计评审</sf:option>
                   <sf:option value="需求评审">需求评审</sf:option>
                   <sf:option value="用例评审">用例评审</sf:option>
                   <sf:option value="其他技术评审">其他技术评审</sf:option>
                    </sf:select> 
						</td>
						
			  </tr>
			<tr>
			<td height="30" align="left">评审对象</td>
			<td height="30" colspan="3"><sf:input path="review_object"  id="review_object" 
						data-options="validType:'minLength[0,100]'"  missingMessage="评审对象" invalidMessage="评审对象"  />&nbsp;<sf:errors path="review_object"
						cssClass="error_msg" /> </td>
			
<%-- 			<td height="30" align="left" type="hidden">最后确认日期</td>
			 <td height="30" colspan="1" type="hidden"><sf:input path="confirm_date" id="confirm_date"  cssClass="easyui-validatebox" required="true"  missingMessage="最后确认日期不能为空"
			  	onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'});"
						readonly="true" style="width:130px" value="${review_date}" /> <sf:errors path="confirm_date"
					cssClass="error_msg" /></td>	 --%>		 
			</tr>
			<tr>
			<td height="30" align="left">责任人</td>
			<td height="30" colspan="1"><sf:input path="duty_officer"  id="duty_officer" 
						data-options="validType:'minLength[0,50]'"  missingMessage="责任人" invalidMessage="责任人"  />&nbsp;<sf:errors path="duty_officer"
						cssClass="error_msg" /> </td>
						
			<td height="30" align="left">状态</td>
			  <td height="30"> 
			  	<sf:select path="status" id="status" class="easyui-combobox"  required="true" validType="selectValueRequired['#status']">   
			  	<sf:option value="新建">新建</sf:option>         
                   <sf:option value="修复中">修复中</sf:option>
                   <sf:option value="已修复">已修复</sf:option>
                    </sf:select> 
						</td>
			</tr>

			<tr>
		  	<td height="30" align="left">结论确认人</td>
			  <td height="30" colspan="1"><sf:input path="result_Confirmor"  id="result_Confirmor" 
						data-options="validType:'minLength[0,50]'"  missingMessage="结论确认人" invalidMessage="结论确认人"  />&nbsp;<sf:errors path="result_Confirmor"
						cssClass="error_msg" /> </td>
	
			<td height="30" align="left">评审结论</td>
			  <td height="30"> 
			  	<sf:select path="review_result" id="review_result" class="easyui-combobox"  required="true" validType="selectValueRequired['#review_result']">   
			  	<sf:option value="评审通过">评审通过</sf:option>         
                   <sf:option value="待修改确认">待修改确认</sf:option>
                   <sf:option value="待修改再评审">待修改再评审</sf:option>
                    </sf:select> 
						</td>
		      </tr>	  
			<tr>
			<td height="30" align="left">问题描述</td>
			  <td height="30" colspan="4"><sf:textarea cols="100" rows="5" path="bug_description"	id="bug_description"						
				cssClass="easyui-validatebox"  data-options="validType:'minLength[0,249]'"   invalidMessage="问题描述内容非法"  />&nbsp;<sf:errors path="bug_description"
						cssClass="error_msg" /></td>
			</tr>
			<tr>
			<td height="30" align="left">改正措施</td>
			  <td height="30" colspan="4"><sf:textarea cols="100" rows="5" path="corrective"	id="corrective"						
				cssClass="easyui-validatebox"  data-options="validType:'minLength[0,249]'"   invalidMessage="改正措施内容非法"  />&nbsp;<sf:errors path="corrective"
						cssClass="error_msg" /></td>
			</tr>
						<tr>
			<td height="30" align="left">备注</td>
			  <td height="30" colspan="4"><sf:textarea cols="100" rows="5" path="remark"	id="remark"						
				cssClass="easyui-validatebox"  data-options="validType:'minLength[0,249]'"   invalidMessage="备注内容非法"  />&nbsp;<sf:errors path="remark"
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
						href="/review/load.do" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">返 回</span></a></td>
	         
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
