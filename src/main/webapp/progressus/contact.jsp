<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>联系我们</title>
	
</head>
<body>
	<div>  
        <%@ include file="/head.jsp" %>
    </div>
    
    	<!-- /.navbar -->

	<header id="head" class="secondary"></header>
	<!-- container -->
	<div class="container">

		<ol class="breadcrumb">
			<li><a href="/">主页</a></li>
			<li class="active">关于</li>
			<li class="active">联系我们</li>
		</ol>

		<div class="row">
			
			<!-- Article main content -->
			<article class="col-sm-9 maincontent">
				<header class="page-header">
					<h1 class="page-title">联系我们</h1>
				</header>
				
				<p>
					<span id="mes" style="color:#242424; font-size: 18px;">项目组一：</span><span id="mes" style="color:#B8B8B8; font-size: 16px;">张三&nbsp;&nbsp;李四&nbsp;&nbsp;</span>
					<br><span id="mes" style="color:#242424; font-size: 18px;">项目组二：</span><span id="mes" style="color:#B8B8B8; font-size: 16px;">王五&nbsp;&nbsp;赵六&nbsp;&nbsp;</span>
				</p>
				<br>

			</article>
			<!-- /Article -->
			
			<!-- Sidebar -->
			<aside class="col-sm-3 sidebar sidebar-right">

				<div class="widget">
					<h4>LuckyFrame官网</h4>
					<address>
						<a href="javascript:window.open('http://www.luckyframe.cn')">http://www.luckyframe.cn</a>
					</address>
					<h4>LuckyFrame官方交流群</h4>
					<address>
						QQ:487954492 
						<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=ecd7cafef5202bb071b599948e7a7827122c87680f187fc96663a025096316bd"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="LuckyFrame自动化测试" title="LuckyFrame自动化测试"></a>
					</address>
				</div>

			</aside>
			<!-- /Sidebar -->

		</div>
	</div>	<!-- /container -->
	 	<div style="height:300px">  
     <p>&nbsp;</p>
    </div>
    <div>  
        <%@ include file="/footer.jsp" %>
    </div>
</body>
</html>