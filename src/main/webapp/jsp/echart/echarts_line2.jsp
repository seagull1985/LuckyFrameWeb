<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图形数据展示</title>

</head>
<body>
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
    
	<header id="head" class="secondary"></header>
	
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="height:600px"></div>
	<div id='canvasDiv'  class='canvasDiv' style="display:block;" >
	<input type="hidden" id="title" name="title" value="${title}"/>
	</div>
    <!-- ECharts单文件引入 -->
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
    <script type="text/javascript">
	//debugger;
        // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                var loadingTicket;
                var effect = ['Loadding','不要催我，慢跑中','正在加载中','数据都去哪儿了'];
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
                	        text: '堆叠区域图'
                	    },
                	    tooltip : {
                	        trigger: 'axis',
                	        axisPointer: {
                	            type: 'cross',
                	            label: {
                	                backgroundColor: '#6a7985'
                	            }
                	        }
                	    },
                	    legend: {
                	        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
                	    },
                	    toolbox: {
                	        feature: {
                	            saveAsImage: {}
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
                	            data : ['周一','周二','周三','周四','周五','周六','周日']
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value'
                	        }
                	    ],
                	    series : [
                	        {
                	            name:'邮件营销',
                	            type:'line',
                	            stack: '总量',
                    	        markPoint: {
                    	            data: [
                    	                {type: 'max', name: '最大值'}
                    	            ],
                    	            animationDelay: 1000,
                    	            animationDuration: 1000
                    	        },
                	            areaStyle: {normal: {}},
                	            data:[120, 132, 101, 134, 90, 230, 210]
                	        },
                	        {
                	            name:'联盟广告',
                	            type:'line',
                	            stack: '总量',
                	            areaStyle: {normal: {}},
                	            data:[220, 182, 191, 234, 290, 330, 310]
                	        },
                	        {
                	            name:'视频广告',
                	            type:'line',
                	            stack: '总量',
                	            areaStyle: {normal: {}},
                	            data:[150, 232, 201, 154, 190, 330, 410]
                	        },
                	        {
                	            name:'直接访问',
                	            type:'line',
                	            stack: '总量',
                	            areaStyle: {normal: {}},
                	            data:[320, 332, 301, 334, 390, 330, 320]
                	        },
                	        {
                	            name:'搜索引擎',
                	            type:'line',
                	            stack: '总量',
                	            label: {
                	                normal: {
                	                    show: true,
                	                    position: 'top'
                	                }
                	            },
                	            areaStyle: {normal: {}},
                	            data:[820, 932, 901, 934, 1290, 1330, 1320]
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
        );
    </script>
</body>
</html>