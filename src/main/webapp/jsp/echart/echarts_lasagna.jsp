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
	<input type="hidden" id="title1" name="title1" value="${title1}"/>
	<input type="hidden" id="title2" name="title2" value="${title2}"/>
	<input type="hidden" id="title3" name="title3" value="${title3}"/>
	<input type="hidden" id="unit" name="unit" value="${unit}"/>
	</div>
    <!-- ECharts单文件引入 -->
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
    <script type="text/javascript">
    var data = ${gdata};
    var title1 = document.getElementById("title1").value;
    var title2 = document.getElementById("title2").value;
    var title3 = document.getElementById("title3").value;
    var unit = document.getElementById("unit").value;
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
                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
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
                	        text: title1,
                	        subtext: title2,
                	        x:'center',
                	    },
                	    tooltip : {
                	        trigger: 'item',
                	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                	    },
                	    legend: {
                	        orient : 'vertical',
                	        x : 'left',
                	        data:data,
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : false,
                	    series : (function (){
                	        var series = [];
                	        for (var i = 0; i < 30; i++) {
                	            series.push({
                	                name:title3+title2,
                	                type:'pie',
                	                itemStyle : {normal : {
                	                    label : {show : i > 28},
                	                    labelLine : {show : i > 28, length:20}
                	                }},
                	                radius : [i * 4 + 40, i * 4 + 43],
                	                data: data,
                	                /*    data:[
                	                    {value: i * 128 + 80,  name:'Chrome'},
                	                    {value: i * 64  + 160,  name:'Firefox'},
                	                    {value: i * 32  + 320,  name:'Safari'},
                	                    {value: i * 16  + 640,  name:'IE9+'},
                	                    {value: i * 8  + 1280, name:'IE8-'}
                	                ] */
                	            })
                	        }
                	        series[0].markPoint = {
                	            symbol:'emptyCircle',
                	            symbolSize:series[0].radius[0],
                	            effect:{show:true,scaleSize:12,color:'rgba(250,225,50,0.8)',shadowBlur:10,period:30},
                	            data:[{x:'50%',y:'50%'}]
                	        };
                	        return series;
                	    })()
                	};
                	setTimeout(function (){
                	    var _ZR = myChart.getZrender();
                	    var TextShape = require('zrender/shape/Text');
                	    // 补充千层饼
                	    _ZR.addShape(new TextShape({
                	        style : {
                	            x : _ZR.getWidth() / 2,
                	            y : _ZR.getHeight() / 2,
                	            color: '#666',
                	            text : title3,
                	            textAlign : 'center'
                	        }
                	    }));
                	    _ZR.addShape(new TextShape({
                	        style : {
                	            x : _ZR.getWidth() / 2 + 200,
                	            y : _ZR.getHeight() / 2,
                	            brushType:'fill',
                	            color: 'orange',
                	            text : 'Source By LuckyFrame',
                	            textAlign : 'left',
                	            textFont:'normal 20px 微软雅黑'
                	        }
                	    }));
                	    _ZR.refresh();
                	}, 2000);
        
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