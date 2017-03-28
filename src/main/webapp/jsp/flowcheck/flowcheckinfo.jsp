<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
<title>项目流程检查详情</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/js/easyui/themes/default/easyui.css" rel="stylesheet"	type="text/css" />
<script language="JavaScript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>	

<style type="text/css">
<!--
.STYLE1 {
	color: #ffffff
}
.STYLE2 {
	color: #FF0000
}
.STYLE6 {color: #FF0000; font-size: 12px; }
-->
</style>


<style type="text/css">
<!--
html,body {height:100%; margin:0px; font-size:12px;}
.mydiv {
background-color: #F0F8FF;
border: 2px solid #C5C9C7;
text-align: center;
font-size: 12px;
font-weight: bold;
z-index:999;
width: auto;
top:10%;
left:10%;
height: 400px;
margin-left:-10px!important;/*FF IE7 该值为本身宽的一半 */
margin-top:50px!important;/*FF IE7 该值为本身高的一半*/
margin-top:0px;
position:fixed!important;/* FF IE7*/
position:absolute;/*IE6*/
_top:       expression(eval(document.compatMode &&
            document.compatMode=='CSS1Compat') ?
            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/

}

.bg,.popIframe 	{
background-color: #666; display:none;
width: 100%;
height: 100%;
left:0;
top:0;/*FF IE7*/
filter:alpha(opacity=10);/*IE*/
opacity:0.4;/*FF*/
z-index:1;
position:fixed!important;/*FF IE7*/
position:absolute;/*IE6*/
_top:       expression(eval(document.compatMode &&
            document.compatMode=='CSS1Compat') ?
            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);
}
-->
</style>	

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
			<li class="active"><a href="/flowCheck/list.do">流程检查信息</a></li>
			<li class="active">流程检查详情</li>
		</ol>
		
         <input type="hidden" id="loginstatus" name="loginstatus" value=""/>
         
		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">${projectname }第${checkid }次流程检查详情</h1>

			</header>
			
<div id="descriptiondiv" style="position:absolute;display:none;z-index:99900;width: 500px;">
<table width="100%"  align="center" class="rect"  style="background-color:rgba(240,255,240,0.9);table-layout:fixed;">
    <tr>
      <td id="descriptionid" valign="top" style="white-space:pre;overflow:hidden;word-break:break-all;word-wrap:break-word;color:#FF9224;font-size:11pt">
      </td>
    </tr>
</table>
</div>

<div id="bg" class="bg" style="display:none;"></div>

<div id="addDiv" class="mydiv" style="display:none;overflow-y:auto;width: 80%;">

<td width="3%" height="30" align="center" valign="baseline"><h2>增加检查项</h2></td>
<sf:form method="post" modelAttribute="flowcheck">
  <input name="checkid" id="checkid" value="0" type="hidden"  />
  <input name="stateupdate" id="stateupdate" type="hidden"  />
  <input name="updatedate" id="updatedate" type="hidden"  />
  <input name="projectphasename" id="projectphasename" type="hidden"  />
  <input name="phasenodename" id="phasenodename" type="hidden"  />
  <input name="checkentryname" id="checkentryname" type="hidden"  />
  <input name="projectid" id="projectid" value="0" type="hidden"  />
  <input name="page" id="page" type="hidden"  />
  <table width="80%"  align="center" class="rect"  frame="box" cellPadding=5 border=1 style="background-color:rgba(240,255,240,0.5);">
	<tr>
	<td width="30%" height="30" align="left">项目检查阶段</td>		
   <td height="30" colspan="3" align="left">&nbsp;&nbsp;
       	<sf:select path="projectphase" id="projectphase" onChange="getArea('node')" onFocus="getArea('node')" width="10%" >
   	   <sf:option value="0">请选择项目阶段...</sf:option>
     <c:forEach var="p" items="${phaselist }">
	  <sf:option value="${p[0]}">${p[1]}</sf:option>
	  </c:forEach>
    </sf:select>         
   
    &nbsp; <sf:select path="phasenode" id="phasenode" onChange="getArea('entry')"  onFocus="getArea('entry')" width="10%">
   	   <sf:option value="0">请选择阶段节点...</sf:option>
    </sf:select>       
               
    &nbsp; <sf:select path="checkentry" id="checkentry"   width="10%">
   	   <sf:option value="0">请选择检查内容...</sf:option>
    </sf:select>       
   </td>
   </tr>
   
   <tr>
   <td height="30" align="left">检查结果</td>
   <td height="30" align="left">&nbsp;&nbsp; 
   <sf:select path="checkresult" id="checkresult"  width="10%" class="easyui-combobox" >
   	   <sf:option value="通过">通过</sf:option>
   	   <sf:option value="未通过">未通过</sf:option>
<%--    	   <sf:option value="部分通过">部分通过</sf:option> --%>
    </sf:select>
    </td>
    
   <td height="30" align="left">检查日期</td>
   	<td height="30" colspan="1" align="left">&nbsp;&nbsp;
   	<sf:input path="checkdate" id="checkdate"
		onclick="WdatePicker({isShowClear:false,readOnly:true});"
		 readonly="true" style="width:100px" value="${checkdate}" />&nbsp;&nbsp;
		 </td>
   </tr>
   
    <tr>
	<td height="30" align="left">检查描述</td>
    <td height="30" colspan="3" align="left">&nbsp;&nbsp;
    <sf:textarea cols="100" rows="5" path="checkdescriptions" id="checkdescriptions"
		cssClass="easyui-validatebox" required="true"  data-options="validType:'minLength[0,199]'" 
		 missingMessage="版本描述不能为空" invalidMessage="版本描述不能为空"  />&nbsp;<sf:errors path="checkdescriptions"
					cssClass="error_msg" /></td>
			</tr>
			
	<tr>
	<td height="30" align="left">备注</td>
	 <td height="30" colspan="4" align="left">&nbsp;&nbsp;
	 <sf:textarea cols="100" rows="5" path="remark"  id="remark"						
		data-options="validType:'minLength[0,99]'"  />&nbsp;<sf:errors path="remark"
			cssClass="error_msg" /></td>
			</tr>
			
			
	<tr>
	<td width="194" height="30">&nbsp;</td>
	<td width="40%" height="30" align="center"><input name="addBtn" type="submit"
					class="button gray" id="addBtn" value="添加" /></td>
	<td  align="center" colspan="2"><a
		href="#" onclick="closeaddDiv()"  style="text-decoration: none;" ><span class="btnold STYLE1"  style="width:70px; margin-bottom:10px;">返 回</span></a></td>	 
		        
		  </tr>		
   </table>
</sf:form>
</div>

<div id="popDiv" class="mydiv" style="display:none;overflow-y:auto;width: 80%;">

<td width="3%" height="30" align="center" valign="baseline"><h2>本次检查未涉及项</h2></td>

<table width="100%" align="center" class="bordered" >
   <tr bgcolor="#B9DCFF">
        <th width="5%"  height="20px" nowrap="nowrap" style="background-color:#8DB6CD">选择</th>  
        <th width="10%" height="20px" nowrap="nowrap" style="background-color:#8DB6CD">ID</th>                
		<th width="10%" height="20px" nowrap="nowrap" style="background-color:#8DB6CD">项目阶段</th>
		<th width="10%" height="20px" nowrap="nowrap" style="background-color:#8DB6CD">阶段节点</th>
		<th width="20%" height="20px" nowrap="nowrap" style="background-color:#8DB6CD">检查内容</th>
  </tr>
		  <c:forEach var="t" items="${checkinfoMap}" begin="0" step="1"
				varStatus="i">
			  <tr>
			      <td height="10px" align="center" ><input type="checkbox" name="box" id="box${i.index + 1 } " value="${t[0] }" checked/></td>
				  <td height="10px" align="center" >${i.index + 1 }&nbsp;</td>		
				  <td height="10px" align="center" >${t[1] }&nbsp;</td>	
				  <td height="10px" align="center" >${t[2] }&nbsp;</td>
				  <td height="10px" align="center" ><font size="3" color="red">${t[3] }&nbsp;</font></td>
			  </tr>
		  </c:forEach>
		  
		</table>
		<table width="100%" align="center" class="bordered">
		<tr>
		<td height="25" align="center"><font size="3" color="red">定义检查共${checksum }项，未添加项${unchecksum }，添加内容覆盖率${checksumper }%！</font></td>
		</tr>
		<tr>
				<td width="5%" align="center" valign="middle" >&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="closeDiv()"  style="text-decoration: none;"> 
				  <span	class="btnold STYLE1"  style="width: 120px;"> 关闭 </span>
				</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="pladdinfo('${projectid}','${checkid}','${versionnum}')"  style="text-decoration: none;">
                  <span	class="btnold STYLE1"  style="width: 120px;background:#FFA54F;border:#FFA54F;"> 自动生成 </span>
				</a>
				</td>

				</tr>
		</table>

</div>



<div align="center" style="width: 100%">
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="0">

		  <tr>
		  	<td width="3%" align="right" valign="middle"><a
					 href="#" onclick="openDiv()"  style="text-decoration: none;"> <span
						class="btnold STYLE1"  style="width: 70px;"> 查看检查未涉及项 </span>
				</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>				
		    <td width="1%" align="left" valign="middle"><a
					 href="#" onclick="showDiv(1,3)"  style="text-decoration: none;"> <span
						class="btnold STYLE1"  style="width: 70px;background:#FFA54F;border:#FFA54F;"> 增加检查项</span>
				</a></td>
				</tr>
		</table>
 </div>
	 
	 <sf:form method="post" modelAttribute="flowcheck">
		<input name="page" id="page" type="hidden"  />
																
		<div align="right"></div>
	   <table width="100%" align="center" class="bordered" style="table-layout: fixed">   <!--  限定宽度，后面带省略号，此style属性必有 -->
          <tr bgcolor="#B9DCFF">                
				<th width="10%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">项目阶段</th>
				<th width="10%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">阶段节点</th>
				<th width="25%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">检查内容</th>
				<th width="8%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">检查结果</th>
				<th width="10%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">检查日期</th>
				<th width="10%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">状态更新</th>
				<th width="10%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">更新日期</th>
				<th width="25%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">检查描述</th>
				<th width="12%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">备注</th>
				<th width="10%" height="40" nowrap="nowrap" bgcolor="#B9DCFF">操作</th>
		  </tr>
		  <c:forEach var="t" items="${splist}" begin="0" step="1"
				varStatus="i">
			  <tr>
			      <td height="25" align="center">${mapphase[t.projectphase]}&nbsp;</td>				  
				  <td height="25" align="center">${mapnode[t.phasenode]}&nbsp;</td>
				  <%-- <td height="25" align="center">${t.checkdate }&nbsp;</td> --%>
				  <td height="25" align="center">${mapentry[t.checkentry]}&nbsp;</td>			  
				  <td height="25" align="center">${t.checkresult }&nbsp;</td>
				  <td height="25" align="center">${t.checkdate }&nbsp;</td>					  
				  <td height="25" align="center">${t.stateupdate }&nbsp;</td>		
				  <td height="25" align="center">${t.updatedate }&nbsp;</td>
				  <td height="25" align="center" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis" onmouseout="hiddenPic();" onmousemove="description(this);">${t.checkdescriptions }&nbsp;</td>	
				  <td height="25" align="center" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis" onmouseout="hiddenPic();" onmousemove="description(this);">${t.remark }&nbsp;</td>
			      <td height="25" align="center" style="word-break:break-all">
			         <a href="#" onclick="showDiv('${t.id}','1')" style="cursor: pointer;"><u>修改</u></a>&nbsp;
			         <a href="#" onclick="showDiv('${t.id}','2')" style="cursor: pointer;"><u>删除</u></a>
			         </td>
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
					<font color="#FF0000">没有记录!</font>				</c:if>
			</div>
			<br/><br/>
			</center>
						
		</sf:form>
		     	<p>&nbsp;</p>
	</article>
	</div> 
	</div>
	
	<script type="text/javascript">
	function frontPageCheck(page)
	{
		if(${page > 1})
		{
			document.getElementById("page").value=page;
			document.getElementById("flowcheck").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{	
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("flowcheck").submit();
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
			document.getElementById("flowcheck").submit();
		return true;
	}
	
	function description(obj){ 
		document.getElementById("descriptiondiv").style.left = (obj.offsetLeft+80)+"px"; 
		document.getElementById("descriptiondiv").style.top = (obj.offsetTop+130)+"px"; 
		document.getElementById("descriptionid").innerHTML = obj.innerHTML;
		
		document.getElementById("descriptiondiv").style.display = "block"; 
		//alert("left2:"+document.getElementById("descriptiondiv").style.left+"  top2:"+document.getElementById("descriptiondiv").style.top);
		} 
	
	function hiddenPic(){ 
		document.getElementById("descriptiondiv").style.display = "none"; 
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
//按上级ID取子列表
function getArea(name){
	if( name=='node' ){
   clearSel(); //清空节点	    
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
  
function closeDiv(){
	document.getElementById('popDiv').style.display='none';
	document.getElementById('bg').style.display='none';
}  
function openDiv(){
	document.getElementById('popDiv').style.display='block';
	document.getElementById('bg').style.display='block';
} 
function closeaddDiv(){
	document.getElementById('addDiv').style.display='none';
	document.getElementById('bg').style.display='none';
	document.getElementById('passDiv').style.display='none';
}  
function openaddDiv(){
	document.getElementById('addDiv').style.display='block';
	document.getElementById('bg').style.display='block';
}

function showDiv_backup(projectid,checkid,checkentry,opr){
	document.getElementById("divcheckentry").value=checkentry;
	document.getElementById("divprojectid").value=projectid;
	document.getElementById("divcheckid").value=checkid;
	document.getElementById("opr").value=opr;
	document.getElementById('passDiv').style.display='block';
	document.getElementById('bg').style.display='block';
}
function closepassDiv(){
	document.getElementById('passDiv').style.display='none';
	document.getElementById('bg').style.display='none';
}

function pladdinfo(proid,checkid,verid){	
	var status = document.getElementById("loginstatus").value;
	if(status=="false"){
		if(window.confirm("你未登录哦，要先去登录吗？")){
			var url = '/progressus/signin.jsp';
			window.location.href=url;
		}else{
			return false; 
		}
	}else{
	var url ="/userlogin/permissionboolean.do?permissioncode=fc_1";
    jQuery.getJSON(url,null,function call(result){
    	if(result.data[0]==null){
    		alert("你好，当前用户无权限操作增加检查信息，请联系软件质量室！");
			return false;
    	}else if(result.data[0]=="true"){
    		var str=document.getElementsByName("box");
    		var objarray=str.length;
    		var idstr="";
    		for (i=0;i<objarray;i++)
    		{
    		  if(str[i].checked == true)
    		  {
    			  idstr+=str[i].value+"|";
    		  }
    		}
    		if(idstr == "")
    		{
    		  alert("请先选择要添加的检查流程！");
    		  return false;
    		}
    		else
    		{
            	var url = '/flowCheck/supplementdetail.do?projectid='+proid+'&checkid='+checkid+'&version='+verid+'&idstr='+idstr;
        		window.location.href=url;
    			return true;
    		}
    		
		 }else{
			alert("你好，当前用户无权限操作增加检查信息，请联系软件质量室！");
			return false;
		   }
   });
  }
}

function showDiv(id,opr){
	var status = document.getElementById("loginstatus").value;
	if(status=="false"){
		if(window.confirm("你未登录哦，要先去首页登录吗？")){
			var url = '../progressus/signin.html';
			window.location.href=url;
		}else{
			return false; 
		}
	}else{
	if(opr=="1"){
		var url = '/flowCheck/update.do?id='+id;
		window.location.href=url;
    }else if(opr=="2"){
    	if(window.confirm("你确认要删除吗？")){ 
        	var url = '/flowCheck/delete.do?id='+id;
    		window.location.href=url;
    		return true; 
    		}else{ 
    		return false; 
    		}
    }else if(opr=="3"){	
    	var url ="/userlogin/permissionboolean.do?permissioncode=fc_1";
        jQuery.getJSON(url,null,function call(result){
        	if(result.data[0]==null){
        		alert("你好，当前用户无权限操作增加检查信息，请联系软件质量室！");
    			return false;
        	}else if(result.data[0]=="true"){
        		openaddDiv();
    			return true;
    		 }else{
    			alert("你好，当前用户无权限操作增加检查信息，请联系软件质量室！");
    			return false;
    		   }
       });
    }
  }
}
</script>	
</body>
</html>