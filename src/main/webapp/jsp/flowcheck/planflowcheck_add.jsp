<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程检查计划添加</title>

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
			<li class="active"><a href="../flowCheck/load.do">流程检查信息</a></li>
			<li class="active"><a href="../planflowCheck/load.do">流程检查计划</a></li>
			<li class="active">增加流程检查计划</li>
		</ol>
		
         <input type="hidden" id="loginstatus" name="loginstatus" value=""/>
         
		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">增加流程检查计划</h1>

			</header>
			
			
	<sf:form modelAttribute="planflowcheck" method="post" onsubmit="return validTime(this)">

		<table width="70%"  align="center" class="rect"  frame="box" cellPadding=5 border=1 style="background-color:rgba(240,255,240,0.5);">
  
<tr>
			<td height="30" align="left">项目名称</td>
			  <td height="30" >&nbsp;&nbsp;
              <sf:select path="projectid" id="projectid" class="easyui-combobox"  required="true" validType="selectValueRequired['#projectid']"  missingMessage="项目名必选" invalidMessage="项目名必选"  >            
                   <sf:option value="0">请选择</sf:option>
                 <c:forEach var="p" items="${projects}">
                  <sf:option value="${p.projectid}">${p.projectname}</sf:option>
                  </c:forEach>
                </sf:select> </td>
			<td height="30" align="left">版本号</td>
			  <td height="30" >&nbsp;&nbsp;<sf:input path="versionnum"
						id="versionnum" 
						cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,20]'"  missingMessage="版本号不能为空" invalidMessage="版本号不能为空"  />&nbsp;<sf:errors path="versionnum"
						cssClass="error_msg" /> </td>
			</tr>
				<tr>
	<td height="30" align="left">项目检查阶段</td>		
   <td height="30" colspan="3">&nbsp;&nbsp; 
       	<sf:select path="checkphase" id="checkphase" onChange="getArea('node')" onFocus="getArea('node')" width="10%" >
   	   <sf:option value="0">请选择项目阶段...</sf:option>
     <c:forEach var="p" items="${phaselist }">
	  <sf:option value="${p[0]}">${p[1]}</sf:option>
	  </c:forEach>
    </sf:select>         
   
    &nbsp; <sf:select path="checknode" id="checknode" onChange="getArea('entry')" onFocus="getArea('entry')" width="10%">
   	   <sf:option value="0">请选择阶段节点...</sf:option>
    </sf:select>       
               
    &nbsp; <sf:select path="checkentryid" id="checkentryid"   width="10%">
   	   <sf:option value="0">请选择检查内容...</sf:option>
    </sf:select>       
   </td>
   </tr>
<tr>
   <td height="30" align="left">计划检查日期</td>
   	<td height="30" colspan="1">&nbsp;&nbsp;
   	<sf:input path="plandate" id="plandate"
		onclick="WdatePicker({isShowClear:false,readOnly:true});"
		 readonly="true" style="width:100px" value="${plandate}" />&nbsp;&nbsp;
		 </td>
			</tr>

			<tr>
			  <td height="30" colspan="4"><div class="error_msg">${message}</div></td>
			</tr>
		  
		  	<tr>
				<td width="194" height="30">&nbsp;</td>
			  <td width="40%" height="30" align="center"><input name="addBtn" type="submit"
					class="button gray" id="addBtn" value="添加" /></td>
	          <td  align="center" colspan="2"><a
						href="/planflowCheck/load.do" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">返 回</span></a></td>
	         
		  </tr>
            
		</table>
	  <p></p>
	</sf:form>
   	<p>&nbsp;</p>
	</article>
	</div> 
    </div>
    
    <script type="text/javascript">
jQuery(function loginboolean(){
	var url ="/userlogin/loginboolean.do";
	$.ajax({
		type:"GET",
		url:url,
		cache:false,
		dataType:"json",
		success:function (result){
	    	if(result.data[0]==null){
	    		document.getElementById("loginstatus").value = "false";
			    document.getElementById("logoutbt").style.display = "block"; 
				document.getElementById("loginbt").style.display = "none";
				document.getElementById("loginmess").style.display = "none";
				return false;
	    	}else if(result.data[0]=="login"){
	    		document.getElementById("loginstatus").value = "true";
	    		document.getElementById("loginmess").innerHTML = result.data[2]+"("+result.data[1]+")&nbsp;&nbsp;欢迎您！";
				 document.getElementById("logoutbt").style.display = "none"; 
				 document.getElementById("loginbt").style.display = "block";
				 document.getElementById("loginmess").style.display = "block";
				 return true;
			 }else{
				document.getElementById("loginstatus").value = "false";
			    document.getElementById("logoutbt").style.display = "block"; 
				document.getElementById("loginbt").style.display = "none";
				document.getElementById("loginmess").style.display = "none";
				return false;
			   }
		}
		});
});

function logout(){
	if(confirm('确定要注销吗？')==true){
		 document.getElementById("loginbt").innerHTML = "注销中...";
	     var url ="/userlogin/userlogout.do";
	     jQuery.getJSON(url,null,function call(result){
	  		   if(result.data[0]=="true"){
				 document.getElementById("logoutbt").style.display = "block"; 
			     document.getElementById("loginbt").style.display = "none";
			     document.getElementById("loginmess").style.display = "none";
			     document.getElementById("loginbt").innerHTML = "注销";
	  			 return true;
	  		   }else{
	  			alert("注销失败！");
	  			return false;
	  		   }
	      });
	}
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
	
    //按上级ID取子列表
	 function getArea(name){
   	if( name=='node' ){
//	    clearSel(); //清空节点	    
	    if(jQuery("#checkphase").val() == "") return;
	    var phaseid = jQuery("#checkphase").val();
	    
	     var url ="/flowCheck/getcheckinfo.do?phaseId="+phaseid+"&nodeId=0";
	     jQuery.getJSON(url,null,function call(result){
	    	 clearSel();
	    	 setNode(result); 
	      });
	     
		    if(jQuery("#checknode").val() == "") return;
		    var nodeid = jQuery("#checknode").val();
		    
		     var url ="/flowCheck/getcheckinfo.do?nodeId="+nodeid+"&phaseId="+phaseid;
		      jQuery.getJSON(url,null,function call(result){	    	  
		    	  clearSel();
		    	  setEntry(result); 
		       });
    
	    }else if(name=='entry'){
	//      clearSel(document.getElementById("entry")); //清空内容
	    if(jQuery("#checkphase").val() == "") return;
	    var phaseid = jQuery("#checkphase").val();
		 
	    if(jQuery("#checknode").val() == "") return;
	    var nodeid = jQuery("#checknode").val();
	    
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
	      jQuery("#checknode").html(options);
	    }
	  
	//当改变节点时设置检查内容
	 function setEntry(result){  
	   var options = "";
	   jQuery.each(result.data, function(i, entry){
		  options +=  "<option value='"+entry[0]+"'>"+entry[1]+"</option>";  
	     }); 
	   jQuery("#checkentryid").html(options);
	   }
 
	 // 清空下拉列表
    function clearSel(){  
	  while(jQuery("#checkentryid").length>1){
		  $("#checkentryid option[index='1']").remove();
	//	 document.getElementById("checkentry").options.remove("1"); 
	    }
	   }
	 
	</script>
</body>
</html>
