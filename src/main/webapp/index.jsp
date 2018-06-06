<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LuckyFrame-首页</title>

<style type="text/css">
.ibox {
    clear: both;
    margin-bottom: 10px;
    margin-top: 0;
    padding: 0;
}

.ibox-title {
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    background-color: #E8E8E8;
    border-color: #778899;
    -webkit-border-image: none;
    -o-border-image: none;
    border-image: none;
    border-style: solid solid none;
    border-width: 4px 0 0;
    color: inherit;
    margin-bottom: 0;
    padding: 14px 15px 7px;
    min-height: 48px;
}

.ibox-content {
    background-color: #F7F5F4;
    color: inherit;
    padding: 15px 20px 20px;
    border-color: #e7eaec;
    -webkit-border-image: none;
    -o-border-image: none;
    border-image: none;
    border-style: solid solid none;
    border-width: 1px 0;
}

</style>
</head>
<body class="gray-bg">
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
	<div class="container text-center wrapper wrapper-content" style="margin-top:20px;">
    <div class="row">
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <span class="label label-success pull-right">持续</span>
                    <h5>自动化运行</h5>
                </div>
                <div class="ibox-content">
                    <h1 id="taskcount" class="no-margins text-success"><i class="fa fa-spinner fa-spin"></i></h1>
                    <div class="stat-percent font-bold text-success" id="taskper">0 <i class="fa fa-cog fa-spin"></i>
                    </div>
                    <small>执行任务</small>
                </div>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <span class="label label-primary pull-right">规模</span>
                    <h5>用例库</h5>
                </div>
                <div class="ibox-content">
                    <h1 class="no-margins text-danger" id="addcasecount"><i class="fa fa-spinner fa-spin"></i></h1>
                    <div class="stat-percent font-bold text-danger" id="addcase">0条 <i class="fa fa-cog fa-spin"></i>
                    </div>
                    <small>30天内更新</small>
                </div>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <span class="label label-warning pull-right">计算</span>
                    <h5>每天节省</h5>
                </div>
                <div class="ibox-content">
                    <h1 class="no-margins text-warning" id="logcount"><i class="fa fa-spinner fa-spin"></i></h1>
                    <div class="stat-percent font-bold text-warning" id="logper">0 <i class="fa fa-cog fa-spin"></i>
                    </div>
                    <small>日志统计</small>
                </div>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <span class="label label-info pull-right">统计</span>
                    <h5>用例执行</h5>
                </div>
                <div class="ibox-content">
                    <h1 class="no-margins text-info" id="casecount"><i class="fa fa-spinner fa-spin"></i></h1>
                    <div class="stat-percent font-bold text-info" id="caseper">0% <i class="fa fa-cog fa-spin"></i>
                    </div>
                    <small>成功率</small>
                </div>
            </div>
        </div>
    </div>
    <!-- 图表展示 -->
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
<!--                 <div class="ibox-title">
                    <h5>近30天用例执行概况</h5>
                </div> -->
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-12">
                           <div id="main" style="width: 100%;height:300px;"></div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

	</div>
	<!-- /Intro-->

	<!-- Highlights - jumbotron -->
	<div class="jumbotron top-space" style="margin-top:10px">
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

    <script src="/progressus/assets/js/echarts.min.js"></script>
	<script type="text/javascript">
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
		
		jQuery(function getshowdata(){
			var url ="/logDetail/getindexdata.do";
			$.ajax({
				type:"GET",
				url:url,
				cache:false,
				dataType:"json",
				success:function (result){
					document.getElementById("taskcount").innerHTML = result.taskdata[0]+" 天";
					document.getElementById("taskper").innerHTML = result.taskdata[1]+" 个 <i class=\"fa fa-tasks\"></i>";
					document.getElementById("casecount").innerHTML = result.casedata[0]+" 条";
					document.getElementById("caseper").innerHTML = result.casedata[1]+" % <i class=\"fa fa-bolt\"></i>";
					document.getElementById("logcount").innerHTML = result.logdata[0]+" 人";
					document.getElementById("logper").innerHTML = result.logdata[1]+" 条 <i class=\"fa fa-indent\"></i>";
					document.getElementById("addcasecount").innerHTML = result.caseadddata[0]+" 条";
					document.getElementById("addcase").innerHTML = result.caseadddata[1]+" 条 <i class=\"fa fa-thumbs-up\"></i>";
				  }
				});
		});
		
        // 路径配置
/*         require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        }); */
        
        var casetotal;
        var casesuc;
        var casefail;
        var caselock;
        var casenoex;
        var casedate;
		jQuery(function getshowdata(){
			var url ="/logDetail/getindexreport.do";
			$.ajax({
				type:"GET",
				url:url,
				async: false,
				cache:false,
				dataType:"json",
				success:function (result){
					casetotal = result.casetotal;
					casesuc = result.casesuc;
					casefail = result.casefail;
					caselock = result.caselock;
					casenoex = result.casenoex;
					casedate = result.casedate;
					inireport();
				  }
				});
		});
		
        // 使用
        function inireport() {
                // 基于准备好的dom，初始化echarts图表
                var myChart = echarts.init(document.getElementById('main')); 
                var loadingTicket;
                var effect = ['Loadding','努力奔跑中','正在加载中','正在获取数据'];
                var random = Math.floor(Math.random()*effect.length);

                myChart.showLoading({
                    text : effect[random],
                    effect : effect[random],
                    textStyle : {
                        fontSize : 20
                    }
                });
                
                var option = {
                	    title: {
                	        text: '30天用例执行概况图'
                	    },
                	    tooltip : {
                	        trigger: 'axis',
                	        axisPointer: {
                	            type: 'cross',
                	            label: {
                	                backgroundColor: '#8B4513'
                	            }
                	        }
                	    },
                	    legend: {
                	        data:['总用例','成功用例','失败用例','锁定用例','未执行']
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    grid: {
                	        left: '3%',
                	        right: '4%',
                	        bottom: '3%',
                	        containLabel: true
                	    },
                	    xAxis : [
                	        {
                	            type : 'category',
                	            boundaryGap : false,
                	            data : casedate
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value'
                	        }
                	    ],
                	    series : [
                      	   {
                    	       name:'总用例',
                    	       type:'line',
                    	       markPoint: {
                   	            data: [
                   	                {type: 'max', name: '最大执行数'}
                   	            ], 
                   	            animationDelay: 1000,
                   	            animationDuration: 1000
                   	           },
                   	        lineStyle: {
                                normal: {
                                    width: 1,
                                    color: {
                                        type: 'linear',
                                        x: 0,
                                        y: 0,
                                        x2: 1,
                                        y2: 0,
                                        colorStops: [{
                                            offset: 0, color: 'grey' // 0% 处的颜色
                                        }, {
                                            offset: 1, color: 'blue' // 100% 处的颜色
                                        }],
                                        globalCoord: false // 缺省为 false
                                    },
                                    opacity: 0.9
                                }
                            },
                            areaStyle: {
                                normal: {
                                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                        offset: 0,
                                        color: 'rgba(0, 236, 212, 0.3)'
                                    }, {
                                        offset: 0.8,
                                        color: 'rgba(0, 236, 212, 0)'
                                    }], false),
                                    shadowColor: 'rgba(0, 0, 0, 0.1)',
                                    shadowBlur: 10
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: 'rgb(0,136,212)',
                                    borderColor: 'rgba(0,136,212,0.2)',
                                    borderWidth: 5
                                }
                            },
                    	       data:casetotal
                    	    },
                	        {
                	            name:'成功用例',
                	            type:'line',
                	            markPoint: {
                       	            data: [
                       	                {type: 'max', name: '最大成功数'}
                       	            ],
                       	            animationDelay: 1000,
                       	            animationDuration: 1000
                       	        },
                       	     lineStyle: {
                    	            normal: {
                    	                width: 1,
                    	                color: {
                    	                    type: 'linear',
                    	                    x: 0,
                    	                    y: 0,
                    	                    x2: 1,
                    	                    y2: 0,
                    	                    colorStops: [{
                    	                        offset: 0, color: 'grey' // 0% 处的颜色
                    	                    }, {
                    	                        offset: 1, color: 'green' // 100% 处的颜色
                    	                    }],
                    	                    globalCoord: false // 缺省为 false
                    	                },
                    	                opacity: 0.9
                    	            }
                    	        },
                    	        areaStyle: {
                    	            normal: {
                    	                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    	                    offset: 0,
                    	                    color: 'rgba(137, 189, 27, 0.4)'
                    	                }, {
                    	                    offset: 0.8,
                    	                    color: 'rgba(137, 189, 27, 0.1)'
                    	                }], false),
                    	                shadowColor: 'rgba(0, 0, 0, 0.1)',
                    	                shadowBlur: 10
                    	            }
                    	        },
                    	        itemStyle: {
                    	            normal: {
                    	                color: 'rgb(137,189,27)',
                    	                borderColor: 'rgba(137,189,2,0.27)',
                    	                borderWidth: 5

                    	            }
                    	        },
                	            data:casesuc
                	        },
                	        {
                	            name:'失败用例',
                	            type:'line',
                	            markPoint: {
                       	            data: [
                       	                {type: 'min', name: '最小失败数'}
                       	            ],
                       	            animationDelay: 1000,
                       	            animationDuration: 1000
                       	        },
                       	     lineStyle: {
                                 normal: {
                                     width: 1,
                                     color: {
                                         type: 'linear',
                                         x: 0,
                                         y: 0,
                                         x2: 1,
                                         y2: 0,
                                         colorStops: [{
                                             offset: 0, color: 'grey' // 0% 处的颜色
                                         }, {
                                             offset: 1, color: 'red' // 100% 处的颜色
                                         }],
                                         globalCoord: false // 缺省为 false
                                     },
                                     opacity: 0.9
                                 }
                             },
                             areaStyle: {
                                 normal: {
                                     color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                         offset: 0,
                                         color: 'rgba(219, 50, 51, 0.3)'
                                     }, {
                                         offset: 0.8,
                                         color: 'rgba(219, 50, 51, 0)'
                                     }], false),
                                     shadowColor: 'rgba(0, 0, 0, 0.1)',
                                     shadowBlur: 10
                                 }
                             },
                             itemStyle: {
                                 normal: {

                                     color: 'rgb(219,50,51)',
                                     borderColor: 'rgba(219,50,51,0.2)',
                                     borderWidth: 5
                                 }
                             },
                	            data:casefail
                	        },
                	        {
                	            name:'锁定用例',
                	            type:'line',
                	            data:caselock
                	        },
                	        {
                	            name:'未执行',
                	            type:'line',
                	            data:casenoex
                	        }
                	    ]
                	};
        		
            	clearTimeout(loadingTicket);
            	loadingTicket = setTimeout(function (){
            	    myChart.hideLoading();
            	    myChart.setOption(option);
            	},2200);
            // 为echarts对象加载数据 
           // myChart.setOption(option); 
            }
	</script>
</body>
</html>