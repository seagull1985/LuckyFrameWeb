<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改生产故障信息</title>

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
 src="../js/jslib.js"></script>

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
			<li class="active"><a href="/accident/load.do">生产故障</a></li>
			<li class="active">修改生产故障信息</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">修改生产故障信息</h1>
			</header>  
			
	<sf:form modelAttribute="accident" method="post" onsubmit="return validTime(this)">
        <input type="hidden" id="reporttime" name="reporttime" value="NULL"/>
        <input type="hidden" id="resolutioner" name="resolutioner" value="NULL"/>
        <input type="hidden" id="preventiver" name="preventiver" value="NULL"/>
        <input type="hidden" id="impact_time" name="impact_time" value="0"/>
        <input type="hidden" id="preventiveplandate" name="preventiveplandate" value="NULL"/>
        <input type="hidden" id="preventiveaccdate" name="preventiveaccdate" value="NULL"/>
		<table width="70%"  align="center" class="rect"  frame="box" cellPadding=5 border=1 style="background-color:rgba(240,255,240,0.5);">
  
<tr>
			<td height="30" align="left">所属项目</td>
			  <td height="30" >
              <sf:select path="projectid" id="projectid" class="easyui-combobox"  required="true" validType="selectValueRequired['#projectid']"  missingMessage="项目名必选" invalidMessage="项目名必选"  >            
                 <sf:option value="0">请选择</sf:option>
                 <c:forEach var="p" items="${projects}">
                  <sf:option value="${p.projectid}">${p.projectname}</sf:option>
                  </c:forEach>
                </sf:select> </td>
               <td height="30" align="left">事故等级</td>
			  <td height="30">
			  <sf:select path="acclevel" id="acclevel" class="easyui-combobox"  required="true" validType="selectValueRequired['#acclevel']"  missingMessage="事故等级" invalidMessage="事故等级"  >            
                   <sf:option value="五级及以下事故">五级及以下事故</sf:option>
                   <sf:option value="四级事故">四级事故</sf:option>
                   <sf:option value="三级事故">三级事故</sf:option>
                   <sf:option value="二级事故">二级事故</sf:option>
                   <sf:option value="一级事故">一级事故</sf:option>
                    </sf:select> 
						</td>
			</tr>
		  <tr>
				<td height="30" align="left">目前状态</td>
			  <td height="30" > 
			  	<sf:select path="accstatus" id="accstatus" class="easyui-combobox"  required="true" validType="selectValueRequired['#accstatus']"  missingMessage="事故状态" invalidMessage="事故状态"  >            
                   <sf:option value="已发生-初始状态">已发生-初始状态</sf:option>
                   <sf:option value="已发生-跟踪中-未处理">已发生-跟踪中-未处理</sf:option>
                   <sf:option value="已发生-跟踪中-处理中">已发生-跟踪中-处理中</sf:option>
                   <sf:option value="跟踪处理完成">跟踪处理完成</sf:option>
                    </sf:select> 
						</td>
			  <td height="30" align="left">事故报告人</td>
			  <td height="30" colspan="1"><sf:input path="reporter" id="reporter"  
						cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,20]'"  missingMessage="事故报告人不能为空" invalidMessage="事故报告人非法输入"  /> <sf:errors path="reporter"
						cssClass="error_msg" /> <sf:errors path="reporter" cssClass="error_msg" /></td>
		  </tr>
		  <tr>
				<td height="30" align="left">事故发生时间</td>
			  <td height="30" colspan="1"><sf:input path="eventtime" id="eventtime"  cssClass="easyui-validatebox" required="true"  missingMessage="事故出现时间不能为空"
			  	onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"
						readonly="true" style="width:130px" value="${eventtime}" /> <sf:errors path="eventtime"
					cssClass="error_msg" /></td>
			<td height="30" align="left">事故原因类型</td>
			  <td height="30" colspan="1"> 
			  	<sf:select path="causaltype" id="causaltype" class="easyui-combobox"  required="true" validType="selectValueRequired['#causaltype']">   
			  	<sf:option value="暂未选择">请选择</sf:option>         
                   <sf:option value="测试漏测">测试漏测</sf:option>
                   <sf:option value="紧急上线-未测试">紧急上线-未测试</sf:option>
                   <sf:option value="紧急上线-漏测">紧急上线-漏测</sf:option>
                   <sf:option value="开发私自上线-未测试">开发私自上线-未测试</sf:option>
                   <sf:option value="转测试-风险分析建议(文档)不全">转测试-风险分析建议(文档)不全</sf:option>
                   <sf:option value="生产数据人工处理">生产数据人工处理</sf:option>
                   <sf:option value="原因未知">原因未知</sf:option>
                   <sf:option value="需求或设计不合理">需求或设计不合理</sf:option>
                   <sf:option value="无法测试">无法测试</sf:option>
                   <sf:option value="系统配置异常">系统配置异常</sf:option>
                   <sf:option value="数据库异常">数据库异常</sf:option>
                   <sf:option value="网络异常">网络异常</sf:option>
                   <sf:option value="服务器硬件异常">服务器硬件异常</sf:option>
                   <sf:option value="渠道或外部网关异常">渠道或外部网关异常</sf:option>
                   <sf:option value="其它异常">其它异常</sf:option>
                    </sf:select> 
						</td>
			</tr>
			<tr>
				<td height="30" align="left">异常情况描述</td>
			  <td height="30" colspan="3"><sf:textarea cols="100" rows="5" path="accdescription"
						id="accdescription"
						cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,248]'"  missingMessage="事故描述不能为空" invalidMessage="事故描述非法输入"  
						 />&nbsp;<sf:errors path="accdescription"
						cssClass="error_msg" /></td>
			</tr>
			<tr>
				<td height="30" align="left">受影响范围</td>
			  <td height="30" colspan="3"><sf:textarea cols="100" rows="5" path="consequenceanalysis"  id="consequenceanalysis"
						cssClass="easyui-validatebox"  data-options="validType:'minLength[0,148]'"  invalidMessage="受影响范围内容非法"  
						 />&nbsp;<sf:errors path="consequenceanalysis"
						cssClass="error_msg" /></td>
			</tr>
			<tr>
				<td height="30" align="left">事故原因分析</td>
			  <td height="30" colspan="3"><sf:textarea cols="100" rows="5" path="causalanalysis"
						id="causalanalysis"  cssClass="easyui-validatebox"  data-options="validType:'minLength[0,248]'" invalidMessage="原因分析内容非法"  
						 />&nbsp;<sf:errors path="causalanalysis" cssClass="error_msg" /></td>
						 
			</tr>
			<tr>
				<td height="30" align="left">纠正处理过程<br/>(需点名责任人)</td>
			  <td height="30" colspan="3"><sf:textarea cols="100" rows="5" path="correctiveaction"  id="correctiveaction"
				cssClass="easyui-validatebox"  data-options="validType:'minLength[0,148]'"  invalidMessage="纠正措施内容非法"  
						 />&nbsp;<sf:errors path="correctiveaction"
						cssClass="error_msg" /></td>
			</tr>
			<tr>
<%-- 		  	<td height="30" align="left">解决人员</td>
			  <td height="30" colspan="1"><sf:input path="resolutioner"  id="resolutioner" 
						data-options="validType:'minLength[0,50]'"  missingMessage="解决人员" invalidMessage="解决人员"  />&nbsp;<sf:errors path="resolutioner"
						cssClass="error_msg" /> </td> --%>
			<td height="30" align="left">纠正处理完成时间</td>
			  <td height="30" colspan="1"><sf:input path="resolutiontime" id="resolutiontime" 
			  	onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"
						readonly="true" style="width:130px" value="${resolutiontime}" /> </td>
			  </tr>

<%-- 		   <tr>
		   	<td height="30" align="left">预防措施责任人</td>
			 <td height="30"><sf:input path="preventiver" id="preventiver" 
						data-options="validType:'minLength[0,20]'"  missingMessage="预防措施责任人" invalidMessage="预防措施责任人"  />&nbsp;<sf:errors path="preventiver"
						cssClass="error_msg" /> </td>
		    <td height="30" align="left">故障影响时间</td>
			  <td height="30"><sf:input path="impact_time" id="impact_time" style="IME-MODE: disabled; WIDTH: 120px;" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="10"  
						data-options="validType:'minLength[0,30]'"  missingMessage="故障影响时间" invalidMessage="故障影响时间"  />&nbsp;<sf:errors path="impact_time"
						cssClass="error_msg" /> <font color="#FF0000">请按分钟(单位)填写</font></td>
		       </tr>
		       
		       <tr>
		   	<td height="30" align="left">预防措施计划完成时间</td>
			  <td height="30" colspan="1"><sf:input path="preventiveplandate" id="preventiveplandate" 
			  	onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'});"
						readonly="true" style="width:130px" value="${preventiveplandate}" /> </td>
						
			<td height="30" align="left">预防措施实际完成时间</td>
			  <td height="30" colspan="1"><sf:input path="preventiveaccdate" id="preventiveaccdate" 
			  	onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'});"
						readonly="true" style="width:130px" value="${preventiveaccdate}" /> </td>
		       </tr> --%>
		      	  
			<tr>
				<td height="30" align="left">整改措施方案：<br/>(需点名责任人)</td>
			  <td height="30" colspan="4"><sf:textarea cols="100" rows="5" path="preventiveaction"	id="preventiveaction"						
						cssClass="easyui-validatebox"  data-options="validType:'minLength[0,148]'"   invalidMessage="预防措施内容非法" />&nbsp;<sf:errors path="preventiveaction"
						cssClass="error_msg" /></td>
			</tr>

			<tr>
			  <td height="30" colspan="4"><div class="error_msg">${message}</div></td>
			</tr>
		  
		  	<tr>
				<td width="194" height="30">&nbsp;</td>
			  <td width="40%" height="30" align="center"><input name="addBtn" type="submit"
					class="button gray" id="addBtn" value="修改" /></td>
	          <td  align="center" colspan="2"><a
						href="/accident/load.do" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">返 回</span></a></td>
	         
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
			 
			
			
			$("#accident").form({
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
			if('${message}'=='修改成功'){
				alert("修改成功,请返回查询！");
			}else{
				alert('${message}');
			}
		}
		
	}
	</script>
	</body>
</html>
