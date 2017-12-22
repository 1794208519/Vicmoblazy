<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
</head>
<body>
	    <div id="main-wrapper">
      
       
          
            <div class="container-fluid">
        
           
                <div class="row">
                    <!-- column -->
                    <div class="col-lg-12" style="width:100%;height:100%;">
                        <div class="card" style="height:100%">
                            <div class="card-body">
                                <h4 class="card-title">销量</h4>
                                <div id="main" style="width:100%; height:600px;"></div>
                            </div>
                        </div>
                    </div>
                    <!-- column -->
                </div>
              <div class="row" style="margin-top:20px;height:500px">
              <!-- column -->
                    <div class="col-lg-12" style="width:100%;height:100%;">
                        <div class="card" style="height:100%">
                            <div class="card-body">
                                <h4 class="card-title">启动次数</h4>
                                <div id="starts" style="width:100%; height:400px;"></div>
                            </div>
                        </div>
                    </div>
                    <!-- column -->
             </div>
             
            </div>
          
    </div>
	 <script type="text/javascript">
		$(document).ready(function() {
			layer.load();//加载层
			setTimeout("open()", 1500)//延时一秒
		});
		function open() {
			layer.closeAll('loading');//移除加载层
			/* $('#hdManager').removeAttr("hidden"); */
		}
	</script> 
	  
	 <!-- Chart JS -->
    <script src="${ctxStatic}/asset/plugins/echarts/echarts.min.js"></script>
    <script src="${ctxStatic}/asset/plugins/echarts/china.js"></script>
   <script src="${ctxStatic}/asset/plugins/echarts/echarts-initi.js"></script>
    <!-- ============================================================== -->
</body>
</html>