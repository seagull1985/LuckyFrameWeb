<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LuckyFrame-首页</title>
</head>
<body>
	<div style="font-size:20px">  
        <%@ include file="/head.jsp" %>
    </div> 
	<!-- /.navbar -->

	<!-- Header -->
	<header id="head">
		<div class="container">
			<div class="row">
				<h1 class="lead">
				Simple, Professional and Efficient</h1>
				<p class="tagline" style="font-family:'微软雅黑';">一站式测试自动化、质量管理服务，让我们的工作更简单、专业、高效</p>
				<p><!-- <a class="btn btn-default btn-lg" role="button">MORE INFO</a> --> <a class="btn btn-action btn-lg" role="button" href="javascript:window.open('http://www.luckyframe.cn')">MORE INFO</a></p>
			</div>
		</div>
	</header>
	<!-- /Header -->

	<!-- Intro -->
	<div class="container text-center">
		<br> <br>
		<h2 class="thin">一站式测试自动化、质量管理服务，让我们的工作更简单、专业、高效</h2>
		<p class="text-muted" style="font-size:20px">
			&nbsp;&nbsp;&nbsp;&nbsp;LuckyFrame主要有任务调度管理、任务执行进度概况、用例执行情况以及日志定位、项目执行图表统计等功能，高度集成TestLink API,
			实现测试计划、测试用例、用例优先级管理，通过用例关键字对自动化进行基本的测试分层(框架层，用例层，脚本层，数据层)驱动管理。
			全纬度支持接口自动化、UI自动化、手机终端自动化。质量管理模块目的是为了使QA的工作更加系统化、信息化，同时也能使项目管理人员及时掌握项目整体动态。
			主要有版本信息、生产故障、项目流程规范检查、评审检查等方面。
		</p>
	</div>
	<!-- /Intro-->
		
	<!-- Highlights - jumbotron -->
	<div class="jumbotron top-space">
		<div class="container">
			
			<h3 class="text-center thin">我们能做的不止于此...</h3>
			
			<div class="row">
				<div class="col-md-3 col-sm-6 highlight">
					<div class="h-caption"><h4><i class="fa fa-cogs fa-5"></i>配置灵活</h4></div>
					<div class="h-body text-center" style="text-align:left;font-size:20px">
						<p>支持关键字自驱动、定时任务、用例运行优先级控制、邮件通知、自动构建jenkins、自动重启TOMCAT、线程数控制等功能，
						通过分层控制让自动化测试场景更灵活。</p>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 highlight">
					<div class="h-caption"><h4><i class="fa fa-flash fa-5"></i>运行更快</h4></div>
					<div class="h-body text-center" style="text-align:left;font-size:20px">
						<p>接口测试采用多线程运行，任务调度中最高可配置30个线程，在测试桩响应速度足够快的情况下，理论上一分钟时间内可测试1000条以上的用例。
						UI自动化以及APP自动化采用单线程server-client模式。
						</p>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 highlight">
					<div class="h-caption"><h4><i class="fa fa-inbox fa-5"></i>封装更全</h4></div>
					<div class="h-body text-center" style="text-align:left;font-size:20px">
						<p>UI自动化以及APP自动化采取关键字驱动全封装selenium以及APPIUM，使用UTP平台关键字就可以0代码基础完成自动化脚本，解决测试人员代码
						能力不足的短板。接口自动化采取JAVA反射机制，只需要简单完成驱动桩简单调用代码。</p>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 highlight">
					<div class="h-caption"><h4><i class="fa fa-check-circle-o fa-5"></i>质量管理 </h4></div>
					<div class="h-body text-center" style="text-align:left;font-size:20px">
						<p>检查项目实际进度与计划进度是否保持一致；检查项目过程中的活动是否与已定的规范和流程一致；记录质量事故并进行原因分析，纠正及预防的措施跟进。</p>
					</div>
				</div>
			</div> <!-- /row  -->
		
		</div>
	</div>
		<div>  
        <%@ include file="/footer.jsp" %>
    </div>

<script type="text/javascript">	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}

	var date = new Date().Format("yyyy-MM-dd");
	
	function down(){		
		var url = '/testJobs/down.do?startDate='+date;
		window.open(url);
	}
	
	function showDiv(){
		var status = document.getElementById("loginstatus").value;
		if(status=="false"){
			if(window.confirm("你未登录哦，要先去首页登录吗？")){
				var url = '../progressus/signin.html';
				window.location.href=url;
			}else{
				return false; 
			} 	
		}else{
			var url = 'userInfo/list.do';
			window.location.href=url;
		}
	}
	</script>
	</body>
</html>