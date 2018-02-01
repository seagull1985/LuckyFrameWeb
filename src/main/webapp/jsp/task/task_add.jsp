<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>调度任务添加</title>

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

<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/js/easyui/themes/default/easyui.css" rel="stylesheet"
	type="text/css" />
<link href="/js/easyui/themes/icon.css" rel="stylesheet" type="text/css" />

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
			<li class="active"><a href="/testJobs/load.do">调度任务</a></li>
			<li class="active">添加调度任务</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">添加调度配置</h1>
			</header>
			
	<sf:form modelAttribute="taskjob" method="post"	onsubmit="return validTime(this);">
		<table width="70%" align="center" class="rect" height=582
			cellPadding=1 border=1 bordercolor="#CCCCCC">

			<tr>
				<td height="30" align="left">计划名称</td>
				<td height="30" colspan="3"><sf:input path="taskName" id="taskName"
						cssClass="easyui-validatebox" required="true"
						data-options="validType:'minLength[0,50]'"
						missingMessage="任务名称不能为空" invalidMessage="任务名称不能为空" />&nbsp;<sf:errors
						path="taskName" cssClass="error_msg" /></td>
			</tr>
			<tr>
				<td height="30" align="left">自动化类型</td>
				<td height="30" colspan="3">
				<sf:radiobutton path="extype" id="extype" value="0" onclick="isShow5('0')" /> 接口自动化&nbsp;&nbsp;&nbsp;&nbsp; 
				<sf:radiobutton	path="extype" id="extype" value="1" onclick="isShow5('1')" /> Web自动化&nbsp;&nbsp;&nbsp;&nbsp;
				<sf:radiobutton	path="extype" id="extype" value="2" onclick="isShow5('2')" /> 移动自动化
				</td>
			</tr>
			<tr id="uiclientipdis" style="display: none">
				<td height="30" align="left">UI自动化浏览器类型</td>
				<td height="30" colspan="3">
				<sf:radiobutton path="browsertype" id="browsertype" value="0" /> IE浏览器&nbsp;&nbsp;&nbsp;&nbsp; 
				<sf:radiobutton	path="browsertype" id="browsertype" value="1" /> 火狐浏览器&nbsp;&nbsp;&nbsp;&nbsp;
				<sf:radiobutton	path="browsertype" id="browsertype" value="2" /> 谷歌浏览器&nbsp;&nbsp;&nbsp;&nbsp;
				<sf:radiobutton	path="browsertype" id="browsertype" value="3" /> Edge浏览器</td>
			</tr>
			<tr>
				<td height="30" align="left">项目类型</td>
				<td height="30" colspan="3">
				<sf:radiobutton	path="projecttype" id="projecttype" value="0" onclick="isShow6('0')" /> 系统项目&nbsp;&nbsp;&nbsp;&nbsp;
				<sf:radiobutton path="projecttype" id="projecttype" value="1" onclick="isShow6('1')" /> TestLink项目 
				</td>
			</tr>
			<tr id="testlinkpro">
				<td height="30" align="left">项目名（testlink中）</td>
				<td height="30" colspan="3"><sf:select path="planproj"
						id="planproj" onChange="getClient(1)" onFocus="getClient(1)">
						<sf:option value="0">请选择</sf:option>
						<c:forEach var="p" items="${projects}">
							<sf:option value="${p.projectid}">${p.projectname}</sf:option>
						</c:forEach>
					</sf:select></td>
			</tr>
			<tr id="testlinkplan">
				<td height="30" align="left" valign="top">计划名（testlink中）</td>
				<td height="30" colspan="3"><sf:textarea cols="50" rows="5"
						path="testlinkname" id="testlinkname" /></td>
			</tr>
			<tr id="pro"  style="display: none">
				<td height="30" align="left">项目名</td>
				<td height="30" colspan="3"><sf:select path="projectid"
						id="projectid" onChange="getPlan()">
						<sf:option value="0">请选择</sf:option>
						<c:forEach var="p" items="${sysprojects}">
							<sf:option value="${p.projectid}">${p.projectname}</sf:option>
						</c:forEach>
					</sf:select></td>
			</tr>
			<tr id="plan"  style="display: none">
				<td height="30" align="left">测试计划</td>
				<td height="30" colspan="3"><sf:select path="planid" id="planid" width="20%">
   	               <sf:option value="0">请选择测试计划...</sf:option>
                       </sf:select></td>
			</tr>
			<tr>
				<td height="30" align="left">客户端IP</td>
				<td height="30" colspan="3"><sf:select path="clientip" id="clientip" width="20%" onChange="getClientpath()" onFocus="getClientpath()">
   	               <sf:option value="0">请选择执行客户端...</sf:option>
                       </sf:select></td>
			</tr>
			<tr>
				<td height="30" align="left">客户端驱动桩路径</td>
				<td height="30" colspan="3"><sf:select path="clientpath" id="clientpath" width="20%">
   	               <sf:option value="0">请选择客户端驱动桩路径...</sf:option>
                       </sf:select></td>
			</tr>
			<tr>
				<td height="30" align="left" valign="top">调度任务描述</td>
				<td height="30" colspan="3"><sf:textarea cols="50" rows="5"
						path="remark" id="remark" /></td>
			</tr>
			<tr>
				<td width="162" height="30" align="left">执行次数</td>
				<td height="30" colspan="2" valign="middle"><sf:radiobutton
						path="taskType" value="O" onclick="isShow('O')" />一次 <sf:radiobutton
						path="taskType" value="D" onclick="isShow('D')" />每天 <%--  <sf:radiobutton path="taskType" value="H" onclick="isShow('H')" />每小时
                <sf:radiobutton path="taskType" value="F" onclick="isShow('F')" />每分钟
                <sf:radiobutton path="taskType" value="S" onclick="isShow('S')" />每秒
                <sf:radiobutton path="taskType" value="Z" onclick="isShow('Z')" /> 自定义   --%>
				</td>
				<%-- 	     <td width="450" height="30" align="left" valign="middle"><div id="time_div" style=" display:none;">每隔<sf:input path="time" style=" width:50px;" cssClass="easyui-validatebox" required="true"  data-options="validType:'minTime[0,60]'"  missingMessage="时间格式：天数在1-30之间，时分秒应在1-60之间" invalidMessage="时间格式：天数在1-30之间，时分秒应在1-60之间"  />
                     <select name="timeType" id="timeType">
						<option value="D">天</option>
						<option value="H">小时</option>
                        <option value="F">分钟</option>
						<option value="S">秒</option>
			  </select></div></td> --%>
			</tr>
			<tr>
				<td height="30" align="left">线程数</td>
				<td height="30" colspan="3"><sf:input path="threadCount"
						id="threadCount" cssClass="easyui-validatebox" required="true"
						missingMessage="线程数在1-20之间" invalidMessage="线程数在1-20之间" />
					(线程数控制在20以内,UI自动化默认为1，填写其他数字无效！)</td>
			</tr>
			<tr>
				<td height="30" align="left">超时时间</td>
				<td height="30" colspan="3"><sf:input path="timeout"
						id="timeout" cssClass="easyui-validatebox" required="true"
						missingMessage="超时时间在1-120分钟之间" invalidMessage="超时时间在1-120分钟之间" />
					(超时时间控制在1-120分钟之间)，针对接口测试有效</td>
			</tr>
			<tr>
				<td height="30" align="left">执行结果是否发送邮件</td>
				<td height="30" colspan="3"><sf:radiobutton path="isSendMail"
						value="0" onclick="isShow2('0')" />不发送 <sf:radiobutton
						path="isSendMail" value="1" onclick="isShow2('1')" />发送</td>
			</tr>
			<tr id="tr_send" style="display: none;">
				<td height="30" align="left">收件人</td>
				<td height="30" colspan="3"><sf:textarea cols="50" rows="5"
						path="emailer" id="emailer" style=" width:300px;" /> (英文分号（;）隔开)</td>
			</tr>

			<tr>
				<td height="30" align="left">计划任务中是否自动构建项目</td>
				<td height="30" colspan="3"><sf:radiobutton path="isbuilding"
						value="0" onclick="isShow3('0')" />不自动构建 <sf:radiobutton
						path="isbuilding" value="1" onclick="isShow3('1')" />自动构建</td>
			</tr>
			<tr id="bd_send" style="display: none">
				<td height="30" align="left">项目构建名称</td>
				<td height="30" colspan="3"><sf:textarea cols="50" rows="5"
						path="buildname" id="buildname" style=" width:300px;" />
					(英文分号（;）隔开)</td>
			</tr>

			<tr>
				<td height="30" align="left">计划任务中是否自动重启TOMCAT</td>
				<td height="30" colspan="3"><sf:radiobutton path="isrestart"
						value="0" onclick="isShow4('0')" />不自动重启 <sf:radiobutton
						path="isrestart" value="1" onclick="isShow4('1')" />自动重启</td>
			</tr>
			<tr id="restartcmmond" style="display: none">
				<td height="30" align="left">自动重启命令</td>
				<td height="30" colspan="3"><sf:textarea cols="200" rows="5"
						path="restartcomm" id="restartcomm" style=" width:600px;" /><br />
				<div style="font-size: 12px; color: blue">格式：服务器IP;服务器用户名;服务器密码;ssh端口;Shell命令;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;例：10.211.19.72;pospsettle;pospsettle;22;cd
						/home/pospsettle/tomcat-7.0-7080/bin&&./restart.sh;</div></td>
			</tr>

			<tr>
				<td height="32">Cron表达式:</td>
				<td height="32"><sf:input path="startTimestr" id="startTimestr"
						cssClass="easyui-validatebox" required="true"
						data-options="validType:'minLength[0,50]'"
						missingMessage="Cron表达式不能为空" invalidMessage="Cron表达式不能为空" value="0 0 08 * * ?"/></td>
				<td height="32" colspan="2"><div
						style="font-size: 12px; color: blue">（必填，Cron表达式(如&quot;0/10
						* * ? * * *&quot;，每10秒中执行调试一次)，对使用者要求比较，要会写Cron表达式） 注意hour只支持00-23点</div></td>
			</tr>

			<!--	<tr>
				<td height="30" align="left">开始日期</td>
			  <td height="30" colspan="3"><sf:input path="startDate"
						id="startDate" class="Wdate" 
						onclick="WdatePicker({isShowClear:false,minDate:'%y-%M-#{%d}'})" />&nbsp;<sf:errors
						path="startDate" cssClass="error_msg" /></td>
			</tr>
			<tr>
				<td height="30" align="left">开始时间</td>
			  <td height="30" colspan="3"><sf:input path="startTime"
						id="startTime"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:00',isShowClear:false})"
						class="Wdate" /> &nbsp; <sf:errors path="startTime"
						cssClass="error_msg" /></td>
			</tr>
			
            <tr>
				<td height="30" colspan="4"><div align="center">
						<span class="STYLE1">选择一次时，以下填写信息均无效。</span>&nbsp;
					</div></td>
			</tr>
			<tr>
				<td height="30" align="left">不设置结束日期</td>
			  <td height="30" colspan="3"><sf:checkbox path="noEndDate"
						id="noEndDate" value="0" /> <span class="tip">不设置结束日期请勾选（勾选后以下信息填写无效）</span>				</td>
			</tr>
			<tr>
				<td height="30" align="left">结束日期<br /></td>
		  <td height="30" colspan="3"><sf:input path="endDate"
						id="endDate" class="Wdate"
						onclick="WdatePicker({isShowClear:false,minDate:'%y-%M-#{%d}'})" />					</td>
			</tr>
			<tr>
				<td height="30" align="left">结束时间</td>
			  <td height="30" colspan="3"><sf:input path="endTime"
						id="endTime3"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:00:00'})"
						class="Wdate" /> </td>
			</tr>-->
			<tr>
				<td height="32" align="left">&nbsp;</td>
				<td height="32" colspan="3"><div class="error_msg">${message}</div></td>
			</tr>
			<tr>
				<td height="30">&nbsp;</td>
				<td width="194" height="30"><input name="addBtn" type="submit"
					class="button gray" id="addBtn" value="添 加" /></td>
				<td colspan="2"><a href="/testJobs/load.do"><span
						class="btnold STYLE1" style="width: 70px; margin-bottom: 10px;">返
							回</span></a></td>

			</tr>


			<tr>
				<td height="32" colspan="4"><div
						style="font-size: 12px; color: blue">
						字段 允许值 允许的特殊字符 <br /> 秒 0-59 , - * / <br /> 分 0-59 , - * / <br />
						小时 0-23 , - * / <br /> 日期 1-31 , - * ? / L W C <br /> 月份 1-12 或者
						JAN-DEC , - * / <br /> 星期 1-7 或者 SUN-SAT , - * ? / L C # <br />
						年（可选） 留空, 1970-2099 , - * / <br /> <br /> 表达式 意义 <br /> &quot;0
						0 12 * * ?&quot; 每天中午12点触发 <br /> &quot;0 15 10 ? * *&quot;
						每天上午10:15触发 <br /> &quot;0 15 10 * * ?&quot; 每天上午10:15触发 <br />
						&quot;0 15 10 * * ? *&quot; 每天上午10:15触发 <br /> &quot;0 15 10 * *
						? 2005&quot; 2005年的每天上午10:15触发 <br /> &quot;0 * 14 * * ?&quot;
						在每天下午2点到下午2:59期间的每1分钟触发 <br /> &quot;0 0/5 14 * * ?&quot;
						在每天下午2点到下午2:55期间的每5分钟触发 <br /> &quot;0 0/5 14,18 * * ?&quot;
						在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 <br /> &quot;0 0-5 14 * *
						?&quot; 在每天下午2点到下午2:05期间的每1分钟触发 <br /> &quot;0 10,44 14 ? 3
						WED&quot; 每年三月的星期三的下午2:10和2:44触发 <br /> &quot;0 15 10 ? *
						MON-FRI&quot; 周一至周五的上午10:15触发 <br /> &quot;0 15 10 15 * ?&quot;
						每月15日上午10:15触发 <br /> &quot;0 15 10 L * ?&quot; 每月最后一日的上午10:15触发
						<br /> &quot;0 15 10 ? * 6L&quot; 每月的最后一个星期五上午10:15触发 <br />
						&quot;0 15 10 ? * 6L 2002-2005&quot;
						2002年至2005年的每月的最后一个星期五上午10:15触发 <br /> &quot;0 15 10 ? *
						6#3&quot; 每月的第三个星期五上午10:15触发 <br /> <br /> 特殊字符 意义 <br /> *
						表示所有值； <br /> ? 表示未说明的值，即不关心它为何值； <br /> - 表示一个指定的范围； <br /> ,
						表示附加一个可能值； <br /> / 符号前表示开始时间，符号后表示每次递增的值； <br />
						L(&quot;last&quot;) (&quot;last&quot;) &quot;L&quot;
						用在day-of-month字段意思是 &quot;这个月最后一天&quot;；用在 day-of-week字段, 它简单意思是
						&quot;7&quot; or &quot;SAT&quot;。 如果在day-of-week字段里和数字联合使用，它的意思就是
						&quot;这个月的最后一个星期几&quot; – 例如： &quot;6L&quot; means
						&quot;这个月的最后一个星期五&quot;.
						当我们用“L”时，不指明一个列表值或者范围是很重要的，不然的话，我们会得到一些意想不到的结果。 <br />
						W(&quot;weekday&quot;)
						只能用在day-of-month字段。用来描叙最接近指定天的工作日（周一到周五）。例如：在day-of-month字段用“15W”指“最接近这个
						月第15天的工作日”，即如果这个月第15天是周六，那么触发器将会在这个月第14天即周五触发；如果这个月第15天是周日，那么触发器将会在这个月第
						16天即周一触发；如果这个月第15天是周二，那么就在触发器这天触发。注意一点：这个用法只会在当前月计算值，不会越过当前月。“W”字符仅能在
						day-of-month指明一天，不能是一个范围或列表。也可以用“LW”来指定这个月的最后一个工作日。<br /> #
						只能用在day-of-week字段。用来指定这个月的第几个周几。例：在day-of-week字段用&quot;6#3&quot;指这个月第3个周五（6指周五，3指第3个）。如果指定的日期不存在，触发器就不会触发。
						<br /> C 指和calendar联系后计算过的值。例：在day-of-month
						字段用“5C”指在这个月第5天或之后包括calendar的第一天；在day-of-week字段用“1C”指在这周日或之后包括calendar的第一天。<br />
					</div></td>
			</tr>
		</table>
		<p></p>
		</p>
	</sf:form>
	<p>&nbsp;</p>
	</article>
	</div>
</div>

<script type="text/javascript">
		function valid(f){
			return true;
		}
		
		function goBack(){
		document.getElementById("taskjob").action="/testJobs/load.do";
		document.getElementById("taskjob").submit();
		return true;
	}
	
	$(function(){
		$("input[name=projecttype][value=0]").attr("checked",true);
		isShow6('0');
			
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
			 
			
			
			$("#taskjob").form({
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
			document.getElementById('tr_send').style.display = 'table-row'
		}else{
			document.getElementById('tr_send').style.display='none';
			document.getElementById('emailer').value="";
		}
		type=isSend;
	}
	
	function  isShow3(isSend){
		if(isSend=='1'){
			document.getElementById('bd_send').style.display='block';
			document.getElementById('bd_send').style.display = 'table-row'
		}else{
			document.getElementById('bd_send').style.display='none';
			document.getElementById('buildname').value="";
		}
		type=isSend;
	}
	
	function  isShow4(isSend){
		if(isSend=='1'){
			document.getElementById('restartcmmond').style.display='block';
			document.getElementById('restartcmmond').style.display = 'table-row'
		}else{
			document.getElementById('restartcmmond').style.display='none';
			document.getElementById('restartcomm').value="";
		}
		type=isSend;
	}
	
	function  isShow5(isSend){
		if(isSend=='1'){
			document.getElementById('uiclientipdis').style.display='block';
			document.getElementById('uiclientipdis').style.display = 'table-row'
		    document.getElementById('browsertype').checked = true
		}else{
			document.getElementById('uiclientipdis').style.display='none';
		}
		type=isSend;
	}
	
	function  isShow6(isSend){
		if(isSend=='0'){
			clearClient();
			jQuery("#projectid option[value='99']").remove();
			document.getElementById('testlinkpro').style.display='none';
			document.getElementById('testlinkplan').style.display='none';
			document.getElementById('pro').style.display='block';
			document.getElementById('pro').style.display = 'table-row';
			document.getElementById('plan').style.display='block';
			document.getElementById('plan').style.display = 'table-row';
		}else{
			clearClient();
			document.getElementById('pro').style.display='none';
			document.getElementById('plan').style.display='none';
			document.getElementById('testlinkpro').style.display='block';
			document.getElementById('testlinkplan').style.display='block';
			document.getElementById('testlinkpro').style.display = 'table-row';
		    document.getElementById('testlinkplan').style.display='table-row';
		}
		type=isSend;
	}
	
	function  validTime(f){
		//var taskType=document.getElementsByName('taskType').value;
		/**
		if(taskType=='Z'){
			var timeType=document.getElementById('timeType');
			var type=timeType.options[timeType.selectedIndex].value;
			var time=document.getElementById('time').value;
			if(type=='D'){
				if(time>30 || time<1){
					alert("天数在1-30之间");return false;
				}
			}else{
				if(time>60 || time<1){
					alert("时分秒在1-60之间");return false;
				}
			}
		}
		**/
		var threadCount=document.getElementById('threadCount').value;
		if(threadCount.isInt()==false){
			toastr.warning('线程必须为数字'); 
			return  false;
		}
		if(threadCount<1  || threadCount>20){
			toastr.warning('线程数在1-20之间'); 
			return  false;
		}
		return true;
	}
	
	
	
	function init(){
		if('${message}'!=''){
			if('${message}'=='添加成功'){
				alert('添加成功,请返回查询！');
			}else{
				alert('${message}');
			}
		}
		
	}

    //按上级ID取子列表
	 function getPlan(){
//	    clearSel(); //清空节点	    
	    if(jQuery("#projectid").val() == "") return;
	    var projectid = jQuery("#projectid").val();
	    var url ="/projectPlan/getplanlist.do?projectid="+projectid;
	    getClient(0);
	     jQuery.getJSON(url,null,function call(result){
	    	 clearSel();
	    	 setPlan(result); 
	      });
    
	    }
    
	  //设置子列表
	 function setPlan(result){	    
  	   var options = "";
	   jQuery.each(result.data, function(i, node){
		  options +=  "<option value='"+node.id+"'>"+node.name+"</option>";
	      }); 
	      jQuery("#planid").html(options);
	    }
	  
	 // 清空下拉列表
     function clearSel(){  
	  while(jQuery("#planid").length>1){
		  $("#planid option[index='1']").remove();
	//	 document.getElementById("checkentry").options.remove("1"); 
	    }
	   }
	 
     //按上级ID取客户端列表
	 function getClient(type){
		 var projectid=0
    	 if(type==0){
    		if(jQuery("#projectid").val() == "") return;
    		projectid = jQuery("#projectid").val();
    	 }else{
    		 if(jQuery("#planproj").val() == "") return;
     		 projectid = jQuery("#planproj").val(); 		 
    	 }
	     var url ="/testClient/getclientlist.do?projectid="+projectid;
	     jQuery.getJSON(url,null,function call(result){
	    	 clearClient();
	    	 setClient(result); 
	      });
	    }
     
	  //设置子列表
	 function setClient(result){	    
  	   var options = "";
	   jQuery.each(result.data, function(i, node){
		   if(node.status==0){
		       options +=  "<option style='color:green' value='"+node.clientip+"'>【"+node.name+"】"+node.clientip+" 【客户端状态正常】</option>";
		   }else if(node.status==1){
			   options +=  "<option style='color:red' value='"+node.clientip+"'>【"+node.name+"】"+node.clientip+" 【客户端状态异常】</option>";
		   }else{
			   options +=  "<option style='color:yellow' value='"+node.clientip+"'>【"+node.name+"】"+node.clientip+" 【客户端状态未知】</option>";
		   }
	      }); 
	      jQuery("#clientip").html(options);
		  getClientpath();
	    }
	  
	 // 清空下拉列表
     function clearClient(){
	  while(jQuery("#clientip").length>1){
		  $("#clientip option[index='1']").remove();
	//	 document.getElementById("checkentry").options.remove("1"); 
	    }
	   }
	 
	    //按上级ID取子列表
	 function getClientpath(){
//	    clearSel(); //清空节点	    
	    if(jQuery("#clientip").val() == "0") return;
	    var clientip = jQuery("#clientip").val();
	    var url ="/testClient/getclientpathlist.do?clientip="+clientip;
	     jQuery.getJSON(url,null,function call(result){
	    	 clearSel();
	    	 setClientpath(result); 
	      });
    
	    }
    
	  //设置子列表
	 function setClientpath(result){	    
  	   var options = "";
	   jQuery.each(result.data, function(i, node){
		  options +=  "<option value='"+node+"'>"+node+"</option>";
	      }); 
	      jQuery("#clientpath").html(options);
	    }
	  
	 // 清空下拉列表
     function clearSel(){  
	  while(jQuery("#clientpath").length>1){
		  $("#clientpath option[index='1']").remove();
	//	 document.getElementById("checkentry").options.remove("1"); 
	    }
	   }
	</script>
</body>
</html>
