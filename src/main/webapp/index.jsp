<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LuckyFrame-首页</title>
</head>
<body>
	<div style="font-size: 20px">
		<%@ include file="/head.jsp"%>
	</div>
	<!-- /.navbar -->

	<!-- Header -->
	<header id="head">
	<div class="container">
		<div class="row">
			<h1 class="lead" style="font-size:84px;font-family:STXingkai;">简单    专业    高效</h1>
			<p class="tagline"></p>
			<p>
				<!-- <a class="btn btn-default btn-lg" role="button">MORE INFO</a> -->
				<a class="btn btn-action btn-lg" role="button"
					href="javascript:window.open('http://www.luckyframe.cn')">了解更多</a>
			</p>
		</div>
	</div>
	</header>
	<!-- /Header -->

	<!-- Intro -->
	<div class="container text-center">
		<br> <br>
		<h2 class="thin">一站式自动化测试平台，让我们的工作更简单、专业、高效</h2>
		<p class="text-muted" style="font-size: 20px;text-align:left;">
			&nbsp;&nbsp;&nbsp;&nbsp;LuckyFrame是为测试团队量身打造的综合质量管理平台，全纬度支持接口自动化、UI自动化。
			主要包含测试计划管理、测试用例管理、任务调度管理、任务执行进度概况、用例执行情况以及日志定位、项目执行图表统计等功能。
			同时为使QA的工作更加系统化、信息化，平台还提供了质量管理相关功能，支持相关人员对项目质量数据进行收集分析。
		</p>
	</div>
	<!-- /Intro-->

	<!-- Highlights - jumbotron -->
	<div class="jumbotron top-space">
		<div class="container">

			<h3 class="text-center thin">LuckyFrame 能做的不止于此...</h3>

			<div class="row">
				<div class="col-md-3 col-sm-6 highlight">
					<div class="h-caption">
						<h4>
							<i class="fa fa-cogs fa-5"></i>使用便捷
						</h4>
					</div>
					<div class="h-body text-center"
						style="text-align: left; font-size: 20px">
						<p>HTTP+Socket+Web UI免编码，自定义测试驱动桩关键字驱动，灵活的调度配置方式，强大的问题定位支持。</p>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 highlight">
					<div class="h-caption">
						<h4>
							<i class="fa fa-tasks fa-5"></i>调度灵活
						</h4>
					</div>
					<div class="h-body text-center"
						style="text-align: left; font-size: 20px">
						<p>Web-Client分布式测试，客户端多线程运行，线程数任意配置，Web端负责基本信息管理展示，Client负责用例执行。
						</p>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 highlight">
					<div class="h-caption">
						<h4>
							<i class="fa fa-file-text-o fa-5"></i>界面清新
						</h4>
					</div>
					<div class="h-body text-center"
						style="text-align: left; font-size: 20px">
						<p>整套Web系统基于Bootstrap以及多种此风格插件，自动化特有用例设计结构以及编辑方式，支持在线调试用例。</p>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 highlight">
					<div class="h-caption">
						<h4>
							<i class="fa fa-check-circle-o fa-5"></i>质量管理
						</h4>
					</div>
					<div class="h-body text-center"
						style="text-align: left; font-size: 20px">
						<p>提供项目质量相关数据收集、统计 、分析，让测试人员的工作从单纯的自动化往质量方面做得更广泛，更专业。</p>
					</div>
				</div>
			</div>
			<!-- /row  -->

		</div>
	</div>
	<div>
		<%@ include file="/footer.jsp"%>
	</div>

	<script type="text/javascript">
		Date.prototype.Format = function(fmt) { //author: meizz 
			var o = {
				"M+" : this.getMonth() + 1, //月份 
				"d+" : this.getDate(), //日 
				"h+" : this.getHours(), //小时 
				"m+" : this.getMinutes(), //分 
				"s+" : this.getSeconds(), //秒 
				"q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
				"S" : this.getMilliseconds()
			//毫秒 
			};
			if (/(y+)/.test(fmt))
				fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
						.substr(4 - RegExp.$1.length));
			for ( var k in o)
				if (new RegExp("(" + k + ")").test(fmt))
					fmt = fmt.replace(RegExp.$1,
							(RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
									.substr(("" + o[k]).length)));
			return fmt;
		}

		var date = new Date().Format("yyyy-MM-dd");

		function down() {
			var url = '/testJobs/down.do?startDate=' + date;
			window.open(url);
		}

		function showDiv() {
			var status = document.getElementById("loginstatus").value;
			if (status == "false") {
				if (window.confirm("你未登录哦，要先去首页登录吗？")) {
					var url = '../progressus/signin.jsp';
					window.location.href = url;
				} else {
					return false;
				}
			} else {
				var url = 'userInfo/list.do';
				window.location.href = url;
			}
		}
	</script>
</body>
</html>