<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>任务概况图表</title>

</head>
<body>
	<div>  
        <%@ include file="/head.jsp" %>
    </div> 
    
	<header id="head" class="secondary"></header>
    
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="height:600px; background-color:#F2F2F2;"></div>
	<div id='canvasDiv'  class='canvasDiv' style="display:block;" >
	<input type="hidden" id="title" name="title" value="${title}"/>
	</div>
	
    <!-- ECharts单文件引入 -->
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
    <script type="text/javascript">
	//debugger;
	var taskid = ${taskid};
	var jobname = ${jobname};
	var casetotal = ${casetotal};
	var casesuc = ${casesuc};
	var casefail = ${casefail};
	var caselock = ${caselock};
	var casenoex = ${casenoex};
	var createtime = ${createtime};

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
                	var labelBottom = {
                	    normal : {
                	        color: '#D1D1D1',
                	        label : {
                	            show : true,
                	            position : 'center'
                	        },
                	        labelLine : {
                	            show : false
                	        }
                	    },
                	    emphasis: {
                	        color: 'rgba(0,0,0,0)'
                	    }
                	};
                	var labelBottom2 = {
                    	    normal : {
                    	        color: '#EBEBEB',
                    	        label : {
                    	            show : false,
                    	            position : 'center'
                    	        },
                    	        labelLine : {
                    	            show : false
                    	        }
                    	    },
                    	    emphasis: {
                    	        color: 'rgba(0,0,0,0)'
                    	    }
                    	};
                	var labelBottom3 = {
                    	    normal : {
                    	        color: '#F2F2F2',
                    	        label : {
                    	            show : false,
                    	            position : 'center'
                    	        },
                    	        labelLine : {
                    	            show : false
                    	        }
                    	    },
                    	    emphasis: {
                    	        color: 'rgba(0,0,0,0)'
                    	    }
                    	};

                	var radius = [40, 75];
                	option = {
                	    backgroundColor: 'rgba(205,205,180,0.2)',
                	    legend: {
                	        x : 1200,
                	        y : 40,
                	        data:['成功','失败','锁定','无响应']
                	    },
                	    title : {
                	        text: '任务概况图表',
                	        subtext: 'From LuckyFrame',
                	        x: 'center',
                	        y : 15
                	    },
                	    tooltip : {
                	        trigger: 'item',
                	        formatter: "{a}{b} : {c} ({d}%)"
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            dataView : {show: true, readOnly: false},
                	            magicType : {
                	                show: true, 
                	                type: ['pie', 'funnel'],
                	                option: {
                	                    funnel: {
                	                        width: '20%',
                	                        height: '30%',
                	                        itemStyle : {
                	                            normal : {
                	                                label : {
                	                                    formatter : function (params){
                	                                        return 'other\n' + params.value + '%\n'
                	                                    },
                	                                    textStyle: {
                	                                        baseline : 'middle'
                	                                    }
                	                                }
                	                            },
                	                        } 
                	                    }
                	                }
                	            },
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    series : [
                	        {
                	            type : 'pie',
                	            center : ['10%', '30%'],
                	            radius : radius,
                	            x: '0%', // for funnel
                	            itemStyle : {
                            	    normal : {
                            	        label : {
                            	            formatter : '用例共\n'+casetotal[0]+'条', 
                            	            textStyle: {
                            	            	fontSize: '14',
                            	            	fontWeight: '500',
                            	            	color: '#999999',
                            	                baseline : 'middle'
                            	            }
                            	        },
                            	    },
                	            },
                	            data : [
                	                    {name:'失败', value:casefail[0], itemStyle : labelBottom},
                    	                {name:'锁定', value:caselock[0], itemStyle : labelBottom2},
                    	                {name:'无响应', value:casenoex[0], itemStyle : labelBottom3},
                    	                {name:'成功', value:casesuc[0],itemStyle : {
                                	    normal : {
                                	    	color: '#5ee55e',
                                	        label : {
                                	            show : true,
                                	            position : 'top',
                                	            formatter : jobname[0]+'\n'+createtime[0],
                                	            textStyle: {
                                	            	color: '#2B2B2B',
                                	            	fontSize: '14',
                                	            	fontWeight: '500',
                                	                baseline : 'middle',
                                	                align : 'center'
                                	            }
                                	        },
                                	        labelLine : {
                                	            show : false
                                	        }
                                	    }
                                	}
                	                }
                	            ]
                	        },
                	        {
                	            type : 'pie',
                	            center : ['30%', '30%'],
                	            radius : radius,
                	            x:'20%', // for funnel
                	            itemStyle : {
                            	    normal : {
                            	        label : {
                            	            formatter : '用例共\n'+casetotal[1]+'条',
                            	            textStyle: {
                            	            	fontSize: '14',
                            	            	fontWeight: '500',
                            	            	color: '#999999',
                            	                baseline : 'middle' ,
                            	            }
                            	        }
                            	    }
                	            },
                	            data : [
                	                    {name:'失败', value:casefail[1], itemStyle : labelBottom},
                    	                {name:'锁定', value:caselock[1], itemStyle : labelBottom2},
                    	                {name:'无响应', value:casenoex[1], itemStyle : labelBottom3},
                    	                {name:'成功', value:casesuc[1],itemStyle : {
                                    	    normal : {
                                    	    	color: '#68adf2',
                                    	        label : {
                                    	            show : true,
                                    	            position : 'top',
                                    	            formatter : jobname[1]+'\n'+createtime[1],
                                    	            textStyle: {
                                    	            	fontSize: '14',
                                    	            	fontWeight: '500',
                                    	            	color: '#2B2B2B',
                                    	                baseline : 'middle',
                                    	                align : 'center'
                                    	            }
                                    	        },
                                    	        labelLine : {
                                    	            show : false
                                    	        }
                                    	    }
                                    	}
                    	                }
                    	            ]
                	        },
                	        {
                	            type : 'pie',
                	            center : ['50%', '30%'],
                	            radius : radius,
                	            x:'40%', // for funnel
                	            itemStyle : {
                            	    normal : {
                            	        label : {
                            	            formatter : '用例共\n'+casetotal[2]+'条',
                            	            textStyle: {
                            	            	fontSize: '14',
                            	            	fontWeight: '500',
                            	            	color: '#999999',
                            	                baseline : 'middle'
                            	            }
                            	        }
                            	    }
                	            },
                	            data : [
                    	                {name:'失败', value:casefail[2], itemStyle : labelBottom},
                    	                {name:'锁定', value:caselock[2], itemStyle : labelBottom2},
                    	                {name:'无响应', value:casenoex[2], itemStyle : labelBottom3},
                    	                {name:'成功', value:casesuc[2],itemStyle : {
                                    	    normal : {
                                    	    	color: '#8B3626',
                                    	        label : {
                                    	            show : true,
                                    	            position : 'top',
                                    	            formatter : jobname[2]+'\n'+createtime[2],
                                    	            textStyle: {
                                    	            	fontSize: '14',
                                    	            	fontWeight: '500',
                                    	            	color: '#2B2B2B',
                                    	                baseline : 'middle',
                                    	                align : 'center'
                                    	            }
                                    	        },
                                    	        labelLine : {
                                    	            show : false
                                    	        }
                                    	    }
                                    	}
                    	                }
                    	            ]
                	        },
                	        {
                	            type : 'pie',
                	            center : ['70%', '30%'],
                	            radius : radius,
                	            x:'60%', // for funnel
                	            itemStyle : {
                            	    normal : {
                            	        label : {
                            	            formatter : '用例共\n'+casetotal[3]+'条', 
                            	            textStyle: {
                            	            	fontSize: '14',
                            	            	fontWeight: '500',
                            	            	color: '#999999',
                            	                baseline : 'middle'
                            	            }
                            	        }
                            	    }
                	            },
                	            data : [
                    	                {name:'失败', value:casefail[3], itemStyle : labelBottom},
                    	                {name:'锁定', value:caselock[3], itemStyle : labelBottom2},
                    	                {name:'无响应', value:casenoex[3], itemStyle : labelBottom3},
                    	                {name:'成功', value:casesuc[3],itemStyle : {
                                    	    normal : {
                                    	    	color: '#FFC125',
                                    	        label : {
                                    	            show : true,
                                    	            position : 'top',
                                    	            formatter : jobname[3]+'\n'+createtime[3],
                                    	            textStyle: {
                                    	            	fontSize: '14',
                                    	            	fontWeight: '500',
                                    	            	color: '#2B2B2B',
                                    	                baseline : 'middle',
                                    	                align : 'center'
                                    	            }
                                    	        },
                                    	        labelLine : {
                                    	            show : false
                                    	        }
                                    	    }
                                    	}
                    	                }
                    	            ]
                	        },
                	        {
                	            type : 'pie',
                	            center : ['90%', '30%'],
                	            radius : radius,
                	            x:'80%', // for funnel
                	            itemStyle : {
                            	    normal : {
                            	        label : {
                            	            formatter : '用例共\n'+casetotal[4]+'条', 
                            	            textStyle: {
                            	            	fontSize: '14',
                            	            	fontWeight: '500',
                            	            	color: '#999999',
                            	                baseline : 'middle'
                            	            }
                            	        }
                            	    }
                	            },
                	            data : [
                    	                {name:'失败', value:casefail[4], itemStyle : labelBottom},
                    	                {name:'锁定', value:caselock[4], itemStyle : labelBottom2},
                    	                {name:'无响应', value:casenoex[4], itemStyle : labelBottom3},
                    	                {name:'成功', value:casesuc[4],itemStyle : {
                                    	    normal : {
                                    	    	color: '#B2DFEE',
                                    	        label : {
                                    	            show : true,
                                    	            position : 'top',
                                    	            formatter : jobname[4]+'\n'+createtime[4],
                                    	            textStyle: {
                                    	            	fontSize: '14',
                                    	            	fontWeight: '500',
                                    	            	color: '#2B2B2B',
                                    	                baseline : 'middle',
                                    	                align : 'center'
                                    	            }
                                    	        },
                                    	        labelLine : {
                                    	            show : false
                                    	        }
                                    	    }
                                    	}
                    	                }
                    	            ]
                	        },
                	        {
                	            type : 'pie',
                	            center : ['10%', '75%'],
                	            radius : radius,
                	            y: '55%',   // for funnel
                	            x: '0%',    // for funnel
                	            itemStyle : {
                            	    normal : {
                            	        label : {
                            	            formatter : '用例共\n'+casetotal[5]+'条',
                            	            textStyle: {
                            	            	fontSize: '14',
                            	            	fontWeight: '500',
                            	            	color: '#999999',
                            	                baseline : 'middle'
                            	            }
                            	        }
                            	    }
                	            },
                	            data : [
                    	                {name:'失败', value:casefail[5], itemStyle : labelBottom},
                    	                {name:'锁定', value:caselock[5], itemStyle : labelBottom2},
                    	                {name:'无响应', value:casenoex[5], itemStyle : labelBottom3},
                    	                {name:'成功', value:casesuc[5],itemStyle : {
                                    	    normal : {
                                    	    	color: '#EEA9B8',
                                    	        label : {
                                    	            show : true,
                                    	            position : 'top',
                                    	            formatter : jobname[5]+'\n'+createtime[5],
                                    	            textStyle: {
                                    	            	fontSize: '14',
                                    	            	fontWeight: '500',
                                    	            	color: '#2B2B2B',
                                    	                baseline : 'middle',
                                    	                align : 'center'
                                    	            }
                                    	        },
                                    	        labelLine : {
                                    	            show : false
                                    	        }
                                    	    }
                                    	}
                    	                }
                    	            ]
                	},
                	{
                	            type : 'pie',
                	            center : ['30%', '75%'],
                	            radius : radius,
                	            y: '55%',   // for funnel
                	            x:'20%',    // for funnel
                	            itemStyle : {
                            	    normal : {
                            	        label : {
                            	            formatter : '用例共\n'+casetotal[6]+'条',
                            	            textStyle: {
                            	            	fontSize: '14',
                            	            	fontWeight: '500',
                            	            	color: '#999999',
                            	                baseline : 'middle'
                            	            }
                            	        }
                            	    }
                	            },
                	            data : [
                    	                {name:'失败', value:casefail[6], itemStyle : labelBottom},
                    	                {name:'锁定', value:caselock[6], itemStyle : labelBottom2},
                    	                {name:'无响应', value:casenoex[6], itemStyle : labelBottom3},
                    	                {name:'成功', value:casesuc[6],itemStyle : {
                                    	    normal : {
                                    	    	color: '#CDAD00',
                                    	        label : {
                                    	            show : true,
                                    	            position : 'top',
                                    	            formatter : jobname[6]+'\n'+createtime[6],
                                    	            textStyle: {
                                    	            	fontSize: '14',
                                    	            	fontWeight: '500',
                                    	            	color: '#2B2B2B',
                                    	            	baseline : 'middle',
                                    	                align : 'center'
                                    	            }
                                    	        },
                                    	        labelLine : {
                                    	            show : false
                                    	        }
                                    	    }
                                    	}
                    	                }
                    	            ]
                	        },
                	        {
                	            type : 'pie',
                	            center : ['50%', '75%'],
                	            radius : radius,
                	            y: '55%',   // for funnel
                	            x:'40%', // for funnel
                	            itemStyle : {
                            	    normal : {
                            	        label : {
                            	            formatter : '用例共\n'+casetotal[7]+'条',
                            	            textStyle: {
                            	            	fontSize: '14',
                            	            	fontWeight: '500',
                            	            	color: '#999999',
                            	                baseline : 'middle'
                            	            }
                            	        }
                            	    }
                	            },
                	            data : [
                    	                {name:'失败', value:casefail[7], itemStyle : labelBottom},
                    	                {name:'锁定', value:caselock[7], itemStyle : labelBottom2},
                    	                {name:'无响应', value:casenoex[7], itemStyle : labelBottom3},
                    	                {name:'成功', value:casesuc[7],itemStyle : {
                                    	    normal : {
                                    	    	color: '#9457d6',
                                    	        label : {
                                    	            show : true,
                                    	            position : 'top',
                                    	            formatter : jobname[7]+'\n'+createtime[7],
                                    	            textStyle: {
                                    	            	fontSize: '14',
                                    	            	fontWeight: '500',
                                    	            	color: '#2B2B2B',
                                    	            	baseline : 'middle',
                                    	                align : 'center'
                                    	            }
                                    	        },
                                    	        labelLine : {
                                    	            show : false
                                    	        }
                                    	    }
                                    	}
                    	                }
                    	            ]
                	        },
                	        {
                	            type : 'pie',
                	            center : ['70%', '75%'],
                	            radius : radius,
                	            y: '55%',   // for funnel
                	            x:'60%', // for funnel
                	            itemStyle : {
                            	    normal : {
                            	        label : {
                            	            formatter : '用例共\n'+casetotal[8]+'条',
                            	            textStyle: {
                            	            	fontSize: '14',
                            	            	fontWeight: '500',
                            	            	color: '#999999',
                            	                baseline : 'middle'
                            	            }
                            	        }
                            	    }
                	            },
                	            data : [
                    	                {name:'失败', value:casefail[8], itemStyle : labelBottom},
                    	                {name:'锁定', value:caselock[8], itemStyle : labelBottom2},
                    	                {name:'无响应', value:casenoex[8], itemStyle : labelBottom3},
                    	                {name:'成功', value:casesuc[8],itemStyle : {
                                    	    normal : {
                                    	    	color: '#6B8E23',
                                    	        label : {
                                    	            show : true,
                                    	            position : 'top',
                                    	            formatter : jobname[8]+'\n'+createtime[8],
                                    	            textStyle: {
                                    	            	fontSize: '14',
                                    	            	fontWeight: '500',
                                    	            	color: '#2B2B2B',
                                    	            	baseline : 'middle',
                                    	                align : 'center'
                                    	            }
                                    	        },
                                    	        labelLine : {
                                    	            show : false
                                    	        }
                                    	    }
                                    	}
                    	                }
                    	            ]
                	        },
                	        {
                	            type : 'pie',
                	            center : ['90%', '75%'],
                	            radius : radius,
                	            y: '55%',   // for funnel
                	            x:'80%', // for funnel
                	            itemStyle : {
                            	    normal : {
                            	        label : {
                            	            formatter : '用例共\n'+casetotal[9]+'条',
                            	            textStyle: {
                            	            	fontSize: '14',
                            	            	fontWeight: '500',
                            	            	color: '#999999',
                            	                baseline : 'middle'
                            	            }
                            	        }
                            	    }
                	            },
                	            data : [
                    	                {name:'失败', value:casefail[9], itemStyle : labelBottom},
                    	                {name:'锁定', value:caselock[9], itemStyle : labelBottom2},
                    	                {name:'无响应', value:casenoex[9], itemStyle : labelBottom3},
                    	                {name:'成功', value:casesuc[9],itemStyle : {
                                    	    normal : {
                                    	    	color: '#CD661D',
                                    	        label : {
                                    	            show : true,
                                    	            position : 'top',
                                    	            formatter : jobname[9]+'\n'+createtime[9],
                                    	            textStyle: {
                                    	            	fontSize: '14',
                                    	            	fontWeight: '500',
                                    	            	color: '#2B2B2B',
                                    	            	baseline : 'middle',
                                    	                align : 'center'
                                    	            }
                                    	        },
                                    	        labelLine : {
                                    	            show : false
                                    	        }
                                    	    }
                                    	}
                    	                }
                    	            ]
                	        }
                	    ]
                	};
                	
                //设置图型点击链接
                var ecConfig = require('echarts/config');	 
                function eConsole(param) {
            		var status;
            		if(param.dataIndex==3){
            			status = 0;
					}else if(param.dataIndex==0){
						status = 1;
					}else if(param.dataIndex==1){
						status = 2;
					}else if(param.dataIndex==2){
						status = 4;
					}
            		window.location.href='/caseDetail/load.do?taskId='+taskid[param.seriesIndex]+'&status='+status;
                	}
                myChart.on(ecConfig.EVENT.CLICK, eConsole);	
                
                //加载页面
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