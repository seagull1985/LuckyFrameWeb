<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>LuckyFrame质量之星</title>

</head>
<body>
	<div>
		<%@ include file="/head.jsp"%>
	</div>

	<header id="head" class="secondary"></header>

	<div class="modal fade" id="checkchart" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title" id="myModalLabel">质量之星积分策略：</h4>
							</div>
							<div class="modal-body">
							  <form class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-12" style="font-weight:normal"><b>测试用例：</b>编辑(1分)  添加(3分)  复制(1分)  新增|编辑用例集(1分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>用例执行：</b>单条执行(1分) 批量执行(1分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>协议模板：</b>添加(5分) 复制(2分) 修改(1分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>公共参数：</b>添加(3分) 修改(1分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>测试计划：</b>添加(3分) 修改(1分) 保存计划用例(2分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>执行任务：</b>删除(1分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>调度任务：</b>添加(5分) 启动(2分) 关闭(1分) 执行(2分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>客户端驱动jar包：</b>上传(5分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>流程检查：</b>明细添加(2分) 批量生成明细(2分) 添加(3分) 修改(1分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>流程检查计划：</b>添加(2分) 修改(1分) 计划转检查(1分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>版本信息：</b>添加(5分) 版本信息修改(1分) 版本计划添加成功(2分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>事故登记：</b>增加(10分) 修改(1分) 上传附件(2分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>客户端：</b>增加(5分) 修改(3分) 删除(1分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>项目：</b>增加(3分) 修改(1分)</label>
									<label class="col-sm-12" style="font-weight:normal"><b>其他管理类操作：</b>增加(1分)</label>
								</div>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>
				
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div id="main" style="height: 600px"></div>

	<!-- ECharts单文件引入 -->
	<script src="/progressus/assets/js/echarts.min.js"></script>
	<script type="text/javascript">
    // Make dynamic data.
    
                // 基于准备好的dom，初始化echarts图表
                var myChart = echarts.init(document.getElementById('main')); 
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
                
                var spirit = 'image://data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAALQAAAC0CAYAAAA9zQYyAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMDY3IDc5LjE1Nzc0NywgMjAxNS8wMy8zMC0yMzo0MDo0MiAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTUgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkZFNzQzQjZFRjREQjExRTc5RTQ5RDg3NDlBQkQyOTc5IiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkZFNzQzQjZGRjREQjExRTc5RTQ5RDg3NDlBQkQyOTc5Ij4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6RkU3NDNCNkNGNERCMTFFNzlFNDlEODc0OUFCRDI5NzkiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6RkU3NDNCNkRGNERCMTFFNzlFNDlEODc0OUFCRDI5NzkiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz6igu/gAAALhklEQVR42uyd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';

                var option;
        		jQuery(function getshowdata(){
        			var url ="/operationLog/getautostar.do";
        			$.ajax({
        				type:"GET",
        				url:url,
        				async: false,
        				cache:false,
        				dataType:"json",
        				success:function (result){
        					option = {
        			                   "title": {
        			                      "text": "LuckyFrame质量之星",
        			                      "subtext": "积分排行榜   From LuckyFrame",
        			                      "left": "center",
        			                      "y": "10",
        			                      "textStyle": {
        			                        "color": "#fff"
        			                      }
        			                    },
        			                    "backgroundColor": "#101a3c",
        			                    "grid": {
        			                      "left": "10%",
        			                      "top": "10%",
        			                      "bottom": "5%"
        			                    },
        			                    "tooltip": {
        			                      "trigger": "item",
        			                      "textStyle": {
        			                        "fontSize": 12
        			                      },
        			                      "formatter": "{b0}:{c0}"
        			                    },
        			                    "xAxis": {
        			                      "max": result.maxvalue,
        			                      "splitLine": {
        			                        "show": false
        			                      },
        			                      "axisLine": {
        			                        "show": false
        			                      },
        			                      "axisLabel": {
        			                        "show": false
        			                      },
        			                      "axisTick": {
        			                        "show": false
        			                      }
        			                    },
        			                    "yAxis": [
        			                      {
        			                        "type": "category",
        			                        "inverse": false,
        			                        "data": result.name,
        			                        "axisLine": {
        			                          "show": false
        			                        },
        			                        "axisTick": {
        			                          "show": false
        			                        },
        			                        "axisLabel": {
        			                          "margin": -4,
        			                          "textStyle": {
        			                            "color": "#fff",
        			                            "fontSize": 16.25
        			                          }
        			                        }
        			                      },
        			                    
        			                    ],
        			                    "series": [
        			                      {
        			                        "type": "pictorialBar",
        			                        "symbol": spirit,
        			                        "symbolRepeat": "fixed",
        			                        "symbolMargin": "5%",
        			                        "symbolClip": true,
        			                        "symbolSize": 22.5,
        			                        "symbolPosition": "start",
        			                        "symbolOffset": [
        			                          20,
        			                          0
        			                        ],
        			                        "symbolBoundingData": result.maxvalue,
        			                        "data":  result.integral,
        			                        "z": 10
        			                      },
        			                      {
        			                        "type": "pictorialBar",
        			                        "itemStyle": {
        			                          "normal": {
        			                            "opacity": 0.3
        			                          }
        			                        },
        			                        "label": {
        			                          "normal": {
        			                            "show": false
        			                          }
        			                        },
        			                        "animationDuration": 0,
        			                        "symbolRepeat": "fixed",
        			                        "symbolMargin": "5%",
        			                        "symbol": spirit,
        			                        "symbolSize": 22.5,
        			                        "symbolBoundingData": result.maxvalue,
        			                        "symbolPosition": "start",
        			                        "symbolOffset": [
        			                          20,
        			                          0
        			                        ],
        			                        "data":  result.integral,
        			                        "z": 5
        			                      }
        			                    ]
        			                };
        				  }
        				});
        		});             
                
                
            	clearTimeout(loadingTicket);
            	loadingTicket = setTimeout(function (){
            	    myChart.hideLoading();
            	    myChart.setOption(option);
            	},2200);

        	    myChart.on('click', function (params) {
        	    	$("#checkchart").modal('show');
        	    });
            // 为echarts对象加载数据 
           // myChart.setOption(option); 

    </script>
</body>
</html>