<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" type="text/javascript"
	src="/js/My97DatePicker/WdatePicker.js"></script>	
	
<title>流程检查计划</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
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
.mypassdiv {
background-color: #D1BDC7;
border: 1px solid #C5C9C7;
text-align: center;
line-height: 40px;
font-size: 12px;
font-weight: bold;
z-index:999;
width: 300px;
height: 140px;
left:50%;
top:50%;
margin-left:-150px!important;/*FF IE7 该值为本身宽的一半 */
margin-top:-60px!important;/*FF IE7 该值为本身高的一半*/
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

<body>
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
    
	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container" style="width:auto;font-size:14px">
		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">质量管理</li>
			<li class="active"><a href="../flowCheck/list.do">流程检查信息</a></li>
			<li class="active">流程检查计划</li>
		</ol>

		<div class="row">	
			<!-- Article main content -->
		<article class="col-sm-9 maincontent" style="width:100%;">
		 <header class="page-header">
				<h1 class="page-title" style="text-align:center;">流程检查计划</h1>

			</header>
	
<input type="hidden" id="loginstatus" name="loginstatus" value=""/>
<input name="planid" id="planid" type="hidden"  />
<div id="popDiv" class="mypassdiv" style="display:none;margin: 15px 0 0 0;">
   <div style="margin:10px 0 0 20px;color:#ffffff;font-family:'微软雅黑'; font-size: 20px;text-align:center;">				
			将计划转成检查结果？
  	</div>
  	是否生成新的检查   <select name="checkid" id="checkid" >
    </select><br> 
	<input class="btnold STYLE1" id="qd" name="qd" value="好的" onclick="tocheck();" style="margin: 5px 0 0 0;"/>&nbsp;&nbsp;&nbsp;
	<input class="btnold STYLE1" name="qx" value="放弃" onclick="closeDiv();"/>
</div>
	 <div id="bg" class="bg" style="display:none;"></div>
	 <table width="100%" align="center">
	 <tr> 
	 <td align="center"><a href="#" onclick="showDiv(0,1)"  style="text-decoration: none;"> 
	      <span class="btnold STYLE1"  style="width: 70px;background:#FFA54F;border:#FFA54F;"> 增加检查计划</span>
				</a></td>
				</tr>
	 </table>
	 <sf:form method="post" modelAttribute="planflowcheck">
		<input name="page" id="page" type="hidden"  />					
		<div align="right"></div>	
	   <table width="100%" align="center" class="bordered">
          <tr bgcolor="#B9DCFF">                
				<th width="8%" height="40" nowrap="nowrap" >项目名称</th>
				<th width="10%" height="40" nowrap="nowrap" >版本号</th>
				<th width="8%" height="40" nowrap="nowrap" >检查阶段</th>
				<th width="15%" height="40" nowrap="nowrap" >检查节点</th>
				<th width="30%" height="40" nowrap="nowrap" >检查内容</th>
				<th width="5%" height="40" nowrap="nowrap" >计划检查日期</th>
				<th width="10%" height="40" nowrap="nowrap" >操作</th>
		  </tr>
		  <c:forEach var="t" items="${splist}" begin="0" step="1"
				varStatus="i">
			  <tr>	  
				  <td height="25" align="center">${t.sectorProjects.projectname }&nbsp;</td>				  
				  <td height="25" align="center">${t.versionnum }&nbsp;</td>
				  <td height="25" align="center">${t.checkphase }&nbsp;</td>
				  <td height="25" align="center">${t.checknode }&nbsp;</td>
				  <td height="25" align="center">${t.checkentryid }&nbsp;</td>
				  <td height="25" align="center">${t.plandate }&nbsp;</td>
				  <td height="25" align="center" style="word-break:break-all">
			         <a href="#" onclick="showDiv('${t.id}','3')" style="cursor: pointer;"><u>修改</u></a>&nbsp;
			         <a href="#" onclick="showDiv('${t.id}','2')" style="cursor: pointer;"><u>删除</u></a>&nbsp;
			         <a href="#" onclick="showDiv('${t.id}','99')" style="cursor: pointer;"><u>去检查</u></a>
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
			document.getElementById("planflowcheck").submit();
			return true;
		}
		return false;
	}
	
	function backPageCheck(page)
	{
		
		if(${page < allPage})
		{
			document.getElementById("page").value=page;
			document.getElementById("planflowcheck").submit();
			//$("#projectversion").submit();
			return true;
		}			
		return false;
	}
	
	
	function setPage(page){
		if(page==1){
			document.getElementById("page").value=1;
		}else{
			document.getElementById("page").value=page;
		}
		document.getElementById("planflowcheck").submit();
		return true;
	}
	
	function showDiv(id,opr){
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
			var url = '/planflowCheck/add.do';
			window.location.href=url;
	    }else if(opr=="2"){
	    	if(window.confirm("你确认要删除吗？")){ 
	        	var url = '/planflowCheck/delete.do?id='+id;
	    		window.location.href=url;
	    		return true; 
	    		}else{ 
	    		return false; 
	    		}
	    }else if(opr=="3"){
			var url = '/planflowCheck/update.do?id='+id;
			window.location.href=url;
	    }else if(opr=="99"){
			getcheckid(id);
			document.getElementById('popDiv').style.display='block';
			document.getElementById('bg').style.display='block';
	    }
	  }
	}
	
	function closeDiv(){
		document.getElementById('popDiv').style.display='none';
		document.getElementById('bg').style.display='none';
	}
	
	function getcheckid(id){
		var url ="/planflowCheck/getcheckid.do?id="+id;
		document.getElementById("planid").value = id;
	     jQuery.getJSON(url,null,function call(result){
	       var options = "";
	       options = "<option value=\"0\">新增检查次数</option>";
	  	   jQuery.each(result.data, function(i, checkid){
	  		  options +=  "<option value='"+checkid+"'>"+"第"+checkid+"次检查"+"</option>";
	  	      }); 
	  	      jQuery("#checkid").html(options);
	      });
	}
	
	function tocheck(){
		var checkid = document.getElementById("checkid").value;
		var url = '/planflowCheck/tocheck.do?id='+document.getElementById("planid").value+'&checkid='+checkid;
		window.location.href=url;
	}
	</script>
</body>
</html>