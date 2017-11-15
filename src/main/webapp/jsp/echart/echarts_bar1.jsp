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
	var data = ${gdata};
	//data = $.praseJSON(data);
	var labels = ${labels};
	var column = ${column};
	var title = document.getElementById("title").value;
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
                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
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
                	    title : {
                	        text: title,
                	        subtext: 'Source By LuckyFrame',
                	        x:'center'
                	    },
                	    tooltip : {
                	        trigger: 'axis'
                	    },
                	    legend: {
                	        data:column,
                	        y:45
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            magicType : {show: true, type: ['line', 'bar']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    xAxis : [
                	        {
                	            type : 'category',
                	            data : labels
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value'
                	        }
                	    ],
                	    series : data
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