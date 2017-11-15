<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>检查计划转成检查结果</title>

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
			<li class="active"><a href="../flowCheck/load.do">流程检查信息</a></li>
			<li class="active"><a href="../planflowCheck/load.do">流程检查计划</a></li>
			<li class="active">修改流程检查计划</li>
		</ol>
		
         <input type="hidden" id="loginstatus" name="loginstatus" value=""/>
         
		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">修改流程检查计划</h1>

			</header>
			
<sf:form method="post" modelAttribute="flowcheck">
  <input name="checkid" id="checkid" value="0" type="hidden"  />
  <input name="stateupdate" id="stateupdate" type="hidden"  />
  <input name="updatedate" id="updatedate" type="hidden"  />
  <input name="projectphasename" id="projectphasename" type="hidden"  />
  <input name="phasenodename" id="phasenodename" type="hidden"  />
  <input name="checkentryname" id="checkentryname" type="hidden"  />
  <table width="70%"  align="center" class="rect"  frame="box" cellPadding=5 border=1 style="background-color:rgba(240,255,240,0.5);">
     <tr>
				<td height="30" align="left">项目名称</td>
			  <td height="30" >&nbsp;&nbsp;
              <sf:select path="projectid" id="projectid" class="easyui-combobox"  required="true" validType="selectValueRequired['#projectid']"  missingMessage="项目名必选" invalidMessage="项目名必选"  >            
                   <sf:option value="0">请选择</sf:option>
                 <c:forEach var="p" items="${projects}">
                  <sf:option value="${p.projectid}">${p.projectname}</sf:option>
                  </c:forEach>
                </sf:select>   </td>
                
               <td height="30" align="left">版本号</td>
			  <td height="30" colspan="1"><sf:input path="versionnum"
						id="versionnum" 
						cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,30]'"  missingMessage="版本号" invalidMessage="版本号"  />&nbsp;<sf:errors path="versionnum"
						cssClass="error_msg" /> </td>
		</tr>
	<tr>
	<td height="30" align="left">项目检查阶段</td>		
   <td height="30" colspan="3">&nbsp;&nbsp; 
       	<sf:select path="projectphase" id="projectphase" onChange="getArea('node')" onFocus="getArea('node')" width="10%" >
     <c:forEach var="p" items="${phaselist }">
	  <sf:option value="${p[0]}">${p[1]}</sf:option>
	  </c:forEach>
    </sf:select>         
   
    &nbsp; <sf:select path="phasenode" id="phasenode" onChange="getArea('entry')" onFocus="getArea('entry')" width="10%">
   	   <c:forEach var="p" items="${nodelist }">
	  <sf:option value="${p[0]}">${p[1]}</sf:option>
	  </c:forEach>
    </sf:select>       
               
    &nbsp; <sf:select path="checkentry" id="checkentry"   width="10%">
   	   <c:forEach var="p" items="${entrylist }">
	  <sf:option value="${p[0]}">${p[1]}</sf:option>
	  </c:forEach>
    </sf:select>       
   </td>
   </tr>
   
   <tr>
   <td height="30" align="left">检查结果</td>
   <td height="30" >&nbsp;&nbsp; 
   <sf:select path="checkresult" id="checkresult"  width="10%" class="easyui-combobox" >
   	   <sf:option value="通过">通过</sf:option>
   	   <sf:option value="未通过">未通过</sf:option>
<%--    	   <sf:option value="部分通过">部分通过</sf:option> --%>
    </sf:select>
    </td>
    
   <td height="30" align="left">检查日期</td>
   	<td height="30" colspan="1">&nbsp;&nbsp;
   	<sf:input path="checkdate" id="checkdate"
		onclick="WdatePicker({isShowClear:false,readOnly:true});"
		 readonly="true" style="width:100px" value="${checkdate}" />&nbsp;&nbsp;
		 </td>
   </tr>
   
    <tr>
	<td height="30" align="left">检查描述</td>
    <td height="30" colspan="3">&nbsp;&nbsp;
    <sf:textarea cols="100" rows="5" path="checkdescriptions" id="checkdescriptions"
		cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,199]'" 
		 missingMessage="版本描述不能为空" invalidMessage="版本描述内容过长" />&nbsp;<sf:errors path="checkdescriptions"
					cssClass="error_msg" /></td>
			</tr>
			
	<tr>
	<td height="30" align="left">备注</td>
	 <td height="30" colspan="4">&nbsp;&nbsp;
	 <sf:textarea cols="100" rows="5" path="remark"  id="remark" 						
	cssClass="easyui-validatebox"	data-options="validType:'minLength[0,99]'"  />&nbsp;<sf:errors path="remark"
			cssClass="error_msg" /></td>
			</tr>
			
			
	<tr>
	<td width="194" height="30">&nbsp;</td>
	<td width="40%" height="30" align="center"><input name="addBtn" type="submit"
					class="button gray" id="addBtn" value="添加" /></td>
	<td  align="center" colspan="2"><a
		href="/planflowCheck/load.do" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">返 回</span></a></td>	         
		  </tr>		
   </table>
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
			 
			
			
			$("#flowcheck").form({
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

	//为了避免jquery中的'$'与其它的'$'冲突,在此将jquery中的'$'重命名为'jQuery'
	var jQuery=$;
	/* //初始化数据
	jQuery(document).ready(function(){	   
	 getPhase();
	 });
	
	//取所有阶段
	 function getPhase(){ 
	 //&callback=?"注意这个是为了解决跨域访问的问题   
	 var url ="/flowCheck/getcheckinfo.do?phaseId=0&nodeId=0";  
	 jQuery.getJSON(url,null,function call(result){   
	 setPhase(result); 
	 }); */
	 
	 //显示或隐藏激活卡
	 jQuery("#actionCardChk").click(function(){
	 if(jQuery("#actionCardChk").attr("checked")==true){
	    jQuery("#actionCardDiv").show();
	   }else{
	     jQuery("#actionCardDiv").hide();
	 }
	  });
	 
/* 	//设置项目阶段
	 function setPhase(result){
      var phase = document.getElementById("toPhase");
      jQuery.each(result.data, function(i, phase){
      var value = phase.id;
	  var text = phase.name;
	  var option = new Option(text,value);
	  phase.options.add(option);         
	    });    
	    } */
	    
	 function printdata(data) {
	        var tt="";
	        jQuery.each(data, function(k, v) {
	            tt += k + "：" + v + "%";
	        })
	        alert(tt);
	}
	    
    //按上级ID取子列表
	 function getArea(name){
    	if( name=='node' ){
//	    clearSel(); //清空节点	    
	    if(jQuery("#projectphase").val() == "") return;
	    var phaseid = jQuery("#projectphase").val();
	    
	     var url ="/flowCheck/getcheckinfo.do?phaseId="+phaseid+"&nodeId=0";
	     jQuery.getJSON(url,null,function call(result){
	    	 clearSel();
	    	 setNode(result); 
	      });
	     
		    if(jQuery("#phasenode").val() == "") return;
		    var nodeid = jQuery("#phasenode").val();
		    
		     var url ="/flowCheck/getcheckinfo.do?nodeId="+nodeid+"&phaseId="+phaseid;
		      jQuery.getJSON(url,null,function call(result){	    	  
		    	  clearSel();
		    	  setEntry(result); 
		       });
     
	    }else if(name=='entry'){
	//      clearSel(document.getElementById("entry")); //清空内容
	    if(jQuery("#projectphase").val() == "") return;
	    var phaseid = jQuery("#projectphase").val();
		 
	    if(jQuery("#phasenode").val() == "") return;
	    var nodeid = jQuery("#phasenode").val();
	    
	     var url ="/flowCheck/getcheckinfo.do?nodeId="+nodeid+"&phaseId="+phaseid;
	      jQuery.getJSON(url,null,function call(result){	    	  
	    	  clearSel();
	    	  setEntry(result); 
	       });
	      
	   }

	  }
    
	  //当改变阶段时设置节点
	 function setNode(result){	    
  	   var options = "";
	   jQuery.each(result.data, function(i, node){
		  options +=  "<option value='"+node[0]+"'>"+node[1]+"</option>";
	      }); 
	      jQuery("#phasenode").html(options);
	    }
	  
	//当改变节点时设置检查内容
	 function setEntry(result){  
	   var options = "";
	   jQuery.each(result.data, function(i, entry){
		  options +=  "<option value='"+entry[0]+"'>"+entry[1]+"</option>";  
	     }); 
	   jQuery("#checkentry").html(options);
	   }
  
	 // 清空下拉列表
     function clearSel(){  
	  while(jQuery("#checkentry").length>1){
		  $("#checkentry option[index='1']").remove();
	//	 document.getElementById("checkentry").options.remove("1"); 
	    }
	   }
	 
	</script>
</body>
</html>
