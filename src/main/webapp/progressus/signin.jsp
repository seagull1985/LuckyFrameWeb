<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户登录</title>
<script type="text/javascript">
function pressKey() {
	if ((event.keyCode == 13)) {
		login();
	}
}
</script>
</head>
<body>
	<div style="font-size:20px">  
        <%@ include file="/head.jsp" %>
    </div> 
    	<!-- /.navbar -->

	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container">

		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">用户登录</li>
		</ol>

		<div class="row"  style="font-size:20px">
			
			<!-- Article main content -->
			<article class="col-xs-12 maincontent">
				<header class="page-header">
					<h1 class="page-title">登 录</h1>
				</header>
				
				<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
					<div class="panel panel-default">
					
					    <div id="login" class="panel-body" style="display:none;height:280px">
					       <h3 class="thin text-center">欢迎您使用质量平台！</h3>
					       <label><a id="userloginname" style="color:#EEAD0E;font-family:'微软雅黑'; font-size: 22px;"></a>&nbsp;&nbsp;&nbsp;&nbsp;您好，您在使用LuckyFrame的过程中，有任何不爽或是不满意的地方，
					       或是对我们有好的建议都可以直接加入进入<a href="javascript:window.open('http://www.luckyframe.cn')">LuckyFrame官网</a>或是加入QQ交流群487954492与我们沟通。
					   <br/>感谢支持！
					       将在 <span id="mes" style="color:#ff0000; font-size: 15px;">3</span> 秒钟后<a id="userloginname" href="javascript:history.back();" style="font-family:'微软雅黑'; font-size: 15px;">返回上一页面</a>!
					       </label>
					    </div>
					    
						<div id="logout" class="panel-body">
							<h3 class="thin text-center">输入您的账号以及密码</h3>
							<p class="text-center text-muted">本平台暂时不提供注册功能，如果您没有账号(忘记账号密码)，请联系管理员为您分配。</p>
							<hr>
							
							<div>
								<div class="top-margin">
									<label>账号 <span class="text-danger">*</span></label>
									<input id="username" type="text" class="form-control">
								</div>
								<div class="top-margin">
									<label>密码 <span class="text-danger">*</span></label>
									<input id="password" type="password" class="form-control" onkeypress="return pressKey()">
								</div>

								<hr>

								<div class="row">
 								<div class="col-lg-8">
										<!-- <b><a href="">Forgot password?</a></b> -->
									<label id="message"  class="text-danger" style="display:none;"></label>	
									</div>
									<div class="col-lg-4 text-right">
										<button id="denglu" class="btn btn-action" onclick="login()">登&nbsp;&nbsp;&nbsp;录</button>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				
			</article>
			<!-- /Article -->

		</div>
	</div>	<!-- /container -->
	
<script type="text/javascript">
var i = 3; 
var intervalid; 

jQuery(function loginboolean(){
	var url ="/userlogin/loginboolean.do";
	$.ajax({
		type:"GET",
		url:url,
		cache:false,
		dataType:"json",
		success:function (result){
	    	if(result.data[0]==null){	    	
			    document.getElementById("logout").style.display = "block"; 
				document.getElementById("login").style.display = "none";
			    document.getElementById("logoutbt").style.display = "block"; 
				document.getElementById("loginbt").style.display = "none";
				document.getElementById("loginmess").style.display = "none";
				document.getElementById("username").focus();
				return false;
	    	}else if(result.data[0]=="login"){
	    		 document.getElementById("loginmess").innerHTML = result.data[2]+"("+result.data[1]+")&nbsp;&nbsp;欢迎您！";
				 document.getElementById("logout").style.display = "none"; 
				 document.getElementById("login").style.display = "block";
				 document.getElementById("logoutbt").style.display = "none"; 
			     document.getElementById("loginbt").style.display = "block";
			     document.getElementById("loginmess").style.display = "block";
				 document.getElementById("userloginname").innerHTML = result.data[2]+"("+result.data[1]+")";
				 return true;
			 }else{
			    document.getElementById("logout").style.display = "block"; 
				document.getElementById("login").style.display = "none";
			    document.getElementById("logoutbt").style.display = "block"; 
				document.getElementById("loginbt").style.display = "none";
				document.getElementById("loginmess").style.display = "none";
				document.getElementById("username").focus();
				return false;
			   }
		}
		});
});
	
	function login(){
		 if($("#username").val()==""){ 
			document.getElementById("message").innerHTML = "用户名不能为空！"
			document.getElementById("message").style.display = "block"; 
	        document.getElementById("username").focus();
	        return false;
		 } 
		 if($("#password").val()==""){
				document.getElementById("message").innerHTML = "密码不能为空！"
				document.getElementById("message").style.display = "block"; 
		        document.getElementById("password").focus();
		        return false;
			 }
		 document.getElementById("denglu").innerHTML = "登录中...";
	     var url ="/userlogin/userlogin.do?username="+$("#username").val()+"&password="+$("#password").val();
	 	$.ajax({
			type:"GET",
			url:url,
			cache:false,
			dataType:"json",
			success:function (result){
		  		   if(result.data[0]=="true"){
		  			     document.getElementById("loginmess").innerHTML = result.data[2]+"("+result.data[1]+")&nbsp;&nbsp;欢迎您！";
		    			 document.getElementById("logout").style.display = "none"; 
		    			 document.getElementById("login").style.display = "block";
						 document.getElementById("logoutbt").style.display = "none"; 
					     document.getElementById("loginbt").style.display = "block";
					     document.getElementById("loginmess").style.display = "block";
		    			 document.getElementById("userloginname").innerHTML = result.data[2]+"("+result.data[1]+")";
		    			 document.getElementById("denglu").innerHTML = "登 &nbsp;&nbsp;&nbsp;录";
		    			 intervalid = setInterval("fun()", 1000); 
		    			 return true;
		    		   }else{
		    			alert(result.data[1]);
		    			document.getElementById("username").focus();
		    			document.getElementById("denglu").innerHTML = "登 &nbsp;&nbsp;&nbsp;录";
		    			return false;
		    		   }
			}
			});
	}
	
	function logout(){
		if(confirm('确定要注销吗？')==true){
			 document.getElementById("loginbt").innerHTML = "注销中...";
		     var url ="/userlogin/userlogout.do";
		     jQuery.getJSON(url,null,function call(result){
		  		   if(result.data[0]=="true"){
		  			 document.getElementById("logout").style.display = "block"; 
		  			 document.getElementById("login").style.display = "none";
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
	

	function fun() {
		if (i == 0) {
			window.history.back();
			clearInterval(intervalid);
		}
		document.getElementById("mes").innerHTML = i;
		i--;
	}
</script>
</body>

</html>