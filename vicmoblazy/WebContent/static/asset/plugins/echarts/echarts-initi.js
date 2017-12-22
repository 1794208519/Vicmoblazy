// ============================================================== 
// Bar chart option
// ============================================================== 


// ============================================================== 
// Line chart
// ============================================================== 

function randomData() {
    return Math.round(Math.random() * 1000);
}
var dom = document.getElementById("main");
 
var mytempChart = echarts.init(dom);
var app = {};
option = {
    title: {
        text: '硬件销量',
        subtext: '',
        left: 'center'
    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['懒赚']
    },
    visualMap: {
        min: 0,
        max: 2500,
        left: 'left',
        top: 'bottom',
        text: ['高', '低'], // 文本，默认为数值文本
		color:['#20a0ff','#D2EDFF'],
        calculable: true
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
            dataView: {
            	show:false,
                readOnly: false
            },
            restore: {
            	show:false
            },
            saveAsImage: {
            	show:true
            }
        }
    },
    series: [{
        name: '懒赚',
        type: 'map',
        mapType: 'china',
        roam: false,
        label: {
            normal: {
                show: true
            },
            emphasis: {
                show: true
            }
        },
        data: [{
            name: '北京',
            value: randomData()
        }, {
            name: '天津',
            value: randomData()
        }, {
            name: '上海',
            value: randomData()
        }, {
            name: '重庆',
            value: randomData()
        }, {
            name: '河北',
            value: randomData()
        }, {
            name: '河南',
            value: randomData()
        }, {
            name: '云南',
            value: randomData()
        }, {
            name: '辽宁',
            value: randomData()
        }, {
            name: '黑龙江',
            value: randomData()
        }, {
            name: '湖南',
            value: randomData()
        }, {
            name: '安徽',
            value: randomData()
        }, {
            name: '山东',
            value: randomData()
        }, {
            name: '新疆',
            value: randomData()
        }, {
            name: '江苏',
            value: randomData()
        }, {
            name: '浙江',
            value: randomData()
        }, {
            name: '江西',
            value: randomData()
        }, {
            name: '湖北',
            value: randomData()
        }, {
            name: '广西',
            value: randomData()
        }, {
            name: '甘肃',
            value: randomData()
        }, {
            name: '山西',
            value: randomData()
        }, {
            name: '内蒙古',
            value: randomData()
        }, {
            name: '陕西',
            value: randomData()
        }, {
            name: '吉林',
            value: randomData()
        }, {
            name: '福建',
            value: randomData()
        }, {
            name: '贵州',
            value: randomData()
        }, {
            name: '广东',
            value: randomData()
        }, {
            name: '青海',
            value: randomData()
        }, {
            name: '西藏',
            value: randomData()
        }, {
            name: '四川',
            value: randomData()
        }, {
            name: '宁夏',
            value: randomData()
        }, {
            name: '海南',
            value: randomData()
        }, {
            name: '台湾',
            value: randomData()
        }, {
            name: '香港',
            value: randomData()
        }, {
            name: '澳门',
            value: randomData()
        }]
    }]
};
if (option && typeof option === "object") {
    mytempChart.setOption(option, true), $(function() {
            function resize() {
                setTimeout(function() {
                    mytempChart.resize()
                }, 100)
            }
            $(window).on("resize", resize), $(".sidebartoggler").on("click", resize)
        });
}

var dome = document.getElementById("starts");
 
var mytempChart = echarts.init(dome);
option = null;
option = {
   
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['附近的人','通讯录','一键开始','联系人','群好友','公众号','智能回复','朋友圈','一键发消息','聊天记录','白名单','自动换号','开启定时'],
		selectedMode: 'single'
    },
    toolbox: {
        show : true,
        feature : {
            magicType : {show: false, type: ['line', 'bar']},
            restore : {show: false},
            saveAsImage : {show: true}
        }
    },
    color: ["#55ce63", "#009efb","#FFC125","#0000FF","#008B00","#00CED1","#383838","#551A8B","#CD0000","#EED2EE","#9A32CD","#CD919E","#080808"],
    calculable : true,
    xAxis : [
        {
            type : 'category',

            boundaryGap : false,
            data : ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日']
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLabel : {
                formatter: '{value}'
            }
        }
    ],

    series : [
        {
            name:'附近的人',
            type:'line',
            color:['#000'],
            data:[,,11, 11, 15, 13],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            },        
            markLine : {
                data : [
                    {type : 'average', name: 'Average'}
                ]
            }
        },
        {
            name:'通讯录',
            type:'line',
            data:[1, 2, 2, 5, 3, 2, 0],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
		{
            name:'一键开始',
            type:'line',
            data:[10,5, 1, 22, 6, 9, 13],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
			{
            name:'联系人',
            type:'line',
            data:[1,2,3,4,5,6,7],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
			{
            name:'群好友',
            type:'line',
            data:[9,8,7,8,22,5,9],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
			{
            name:'公众号',
            type:'line',
            data:[9,8,7,6,5,4,3],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
			{
            name:'智能回复',
            type:'line',
            data:[7,6,5,4,3,2,1],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
			{
            name:'朋友圈',
            type:'line',
            data:[9,22,16,12,18,17,0],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
			{
            name:'一键发消息',
            type:'line',
            data:[11,11,11,11,15,5,9],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
			{
            name:'聊天记录',
            type:'line',
            data:[8,6,3,7,6,1,5],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
			{
            name:'白名单',
            type:'line',
            data:[1,9,7,5,6,9,1],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
			{
            name:'自动换号',
            type:'line',
            data:[2,2,3,6,8,7,8],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        },
			{
            name:'开启定时',
            type:'line',
            data:[1,8,12,13,17,5,7],
            markPoint : {
                 data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.3)',
                        shadowBlur: 10,
                        shadowOffsetX: 8,
                        shadowOffsetY: 8 
                    }
                }
            }
        }
    ]
};

if (option && typeof option === "object") {
    mytempChart.setOption(option, true), $(function() {
            function resize() {
                setTimeout(function() {
                    mytempChart.resize()
                }, 100)
            }
            $(window).on("resize", resize), $(".sidebartoggler").on("click", resize)
        });
}

// ============================================================== 
// Pie chart option
// ============================================================== 

//============================================================== 
//Pie chart option
//============================================================== 
var pieChart = echarts.init(document.getElementById('pie-chart'));

//specify chart configuration item and data
option = {

 tooltip : {
     trigger: 'item',
     formatter: "{a} <br/>{b} : {c} ({d}%)"
 },
 legend: {
     x : 'center',
     y : 'bottom',
     data:['附近的人','通讯录','一键开始','联系人','群好友','公众号','智能回复','朋友圈','一键发消息','聊天记录','白名单','自动换号','开启定时']
 },
 toolbox: {
     show : true,
     feature : {
         
         dataView : {show: false, readOnly: false},
         magicType : {
             show: true, 
             type: ['pie', 'funnel']
         },
         restore : {show: false},
         saveAsImage : {show: true}
     }
 },
 color: ["#55ce63", "#009efb","#FFC125","#0000FF","#008B00","#00CED1","#383838","#551A8B","#CD0000","#EED2EE","#9A32CD","#CD919E","#080808"],
 calculable : true,
 series : [
     {
         name:'Radius mode',
         type:'pie',
         radius : [20, 110],
         center : ['20%', 200],
         roseType : 'radius',
         width: '40%',       // for funnel
         max: 40,            // for funnel
         itemStyle : {
             normal : {
                 label : {
                     show : false
                 },
                 labelLine : {
                     show : false
                 }
             },
             emphasis : {
                 label : {
                     show : true
                 },
                 labelLine : {
                     show : true
                 }
             }
         },
        
         data:[
             {value:10, name:'附近的人'},
             {value:5, name:'通讯录'},
             {value:15, name:'一键开始'},
             {value:25, name:'联系人'},
             {value:20, name:'群好友'},
             {value:35, name:'公众号'},
             {value:30, name:'智能回复'},
             {value:40, name:'朋友圈'},
             {value:15, name:'一键发消息'},
             {value:25, name:'聊天记录'},
             {value:20, name:'白名单'},
             {value:35, name:'自动换号'},
             {value:30, name:'开启定时'}
         ]
     },
     {
         name:'Area mode',
         type:'pie',
         radius : [30, 110],
         center : ['50%', 200],
         roseType : 'area',
         x: '50%',               // for funnel
         max: 40,                // for funnel
         sort : 'ascending',     // for funnel
         data:[
        	 {value:10, name:'附近的人'},
             {value:5, name:'通讯录'},
             {value:15, name:'一键开始'},
             {value:25, name:'联系人'},
             {value:20, name:'群好友'},
             {value:35, name:'公众号'},
             {value:30, name:'智能回复'},
             {value:40, name:'朋友圈'},
             {value:15, name:'一键发消息'},
             {value:25, name:'聊天记录'},
             {value:20, name:'白名单'},
             {value:35, name:'自动换号'},
             {value:30, name:'开启定时'}
         ]
     },
     {
         name:'Area mode',
         type:'pie',
         radius : [30, 110],
         center : ['80%', 200],
         roseType : 'area',
         x: '50%',               // for funnel
         max: 40,                // for funnel
         sort : 'ascending',     // for funnel
         data:[
        	 {value:10, name:'附近的人'},
             {value:5, name:'通讯录'},
             {value:15, name:'一键开始'},
             {value:25, name:'联系人'},
             {value:20, name:'群好友'},
             {value:35, name:'公众号'},
             {value:30, name:'智能回复'},
             {value:40, name:'朋友圈'},
             {value:15, name:'一键发消息'},
             {value:25, name:'聊天记录'},
             {value:20, name:'白名单'},
             {value:35, name:'自动换号'},
             {value:30, name:'开启定时'}
         ]
     }
 ]
};
                 
                 

//use configuration item and data specified to show chart
pieChart.setOption(option, true), $(function() {
         function resize() {
             setTimeout(function() {
                 pieChart.resize()
             }, 100)
         }
         $(window).on("resize", resize), $(".sidebartoggler").on("click", resize)
     });
