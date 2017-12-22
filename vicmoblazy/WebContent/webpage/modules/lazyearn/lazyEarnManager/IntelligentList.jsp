<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
</head>
<body onkeydown="keySearch();">
<div class="divControl">
		<div class="card">
			<ul class="nav nav-tabs customtab" role="tablist">
				<li class="nav-item">
					<a class="nav-link active" data-toggle="tab" href="#home2" role="tab">
						<span class="hidden-sm-up"><i class="ti-home"></i></span> 
						<span class="hidden-xs-down">智能回复</span>
					</a>
				</li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane p-20 active" id="home2" role="tabpanel">
				    
				    <div class="row button-group col-3" style="float:left;margin-bottom:8px;">
				 		<button type="button" class="btn btn-info" onclick="add()"><i class="fa fa-plus-circle"></i> 添加</button>
				 		<button type="button" class="btn btn-info" onclick="edit1()"><i class="fa fa fa-edit"></i> 编辑</button>
				 		<button type="button" class="btn btn-danger" onclick="deleteAll()"><i class="fa fa fa-times"></i> 删除</button>
				 	</div>
				 	
				 	<div class="row col-9" style="float: right;">
						<form action="" method="post" onsubmit="return false;" style="width: 100%" id="searchForm">
					        <input id="pageNo" name="pageNo" type="hidden" value="1" /> 
					        <input id="pageSize" name="pageSize" type="hidden" value="10" />
					        <input type="hidden" id="selectedDevice" name="selectedDevice" value="">  
							<button id="keysearch" type="button" class="btn waves-effect waves-light btn-info" onclick="search(0)" style="float: right; margin-left: 15px">搜索</button>
							
							<input autocomplete="off" type="text" class="form-control height-control col-4" placeholder="请输入关键字/内容" id="searchFund" name="searchFund" value="" style="float: right;"/>
							<select class="custom-select" id="selectDevices" data-style="btn-info btn-outline-info" style="float: right;margin-right: 20px">
							    <option value="" selected>请选择设备号...</option>
						    </select>
						</form>
					</div>
					
					<table id="example23" class="display nowrap table table-hover table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr>
							    <th style="width: 100px;" class="tableControl"><input type="checkbox" class="check" id="ischange" data-checkbox="icheckbox_square-blue"></th>
								<th class="tableControl">主控设备</th>
								<th class="tableControl">关键词</th>
								<th class="tableControl">回复内容</th>
								<th class="tableControl">创建时间</th>
								<th class="tableControl">操作</th>
							</tr>
						</thead>
						<tbody id="tbody">
						</tbody>
					</table>
					<!-- 底部分页按钮 -->  
                    <ul class="pagination pull-right" id="page"></ul>
				</div>
				<div class="tab-pane  p-20" id="profile2" role="tabpanel">2</div>
				<div class="tab-pane p-20" id="messages2" role="tabpanel">3</div>
			</div>
		</div>
	</div>
	<!-- 跳转隐藏form表单 -->
	<form action="" method="post" id="jump" hidden="hidden">
		<input type="text" id="codeIdVal" name="codeId" />
	</form>
	<script type="text/javascript">
	    $(function(){ 
		   //先调下数据(默认为第一页开始，每页十个)
		   currentDevices();
		   search(-1);
		   layer.load();//加载层
		 });
	    
	    //enter 搜索
	    function keySearch(){
	    	if(event.keyCode==13){
	    		document.getElementById("keysearch").click();
	    	}
	    }
       //数据显示
	   function search(type){
    	    var url1 = "${ctx}/lazyearn/IntelligentManager/findList";
		    if(type == 0){
				$.cookie('searchWord', $('#searchFund').val());
			} else if(type == -1) {
				$.cookie('searchWord', "");
			}
		  $.ajax({
			type : "POST",
			url : url1,
			data: $('#searchForm').serialize(),
			dataType : "json",
			success : function(result) {
				layer.closeAll('loading');//去除加载层
				//先清空
				$("#tbody").empty();
				//再添加
				$.each(result.list, function(i, li) {
					var keyword = li.keyword;
					var text = li.text;
					var devices = li.devices;
					if(keyword.length > 10){
						keyword = li.keyword.substr(0,10) + "...";
					}
					if(text.length > 10){
						text = li.text.substr(0,10) + "...";
					}
					if(devices == null || devices == '' || devices == 'null'){
						devices = "未绑定设备"
					}
					var trs = '<tr id="'+ li.codeTextId +'">'
					        + '<td style="width: 100px;" class="tableControl"><input type="checkbox" name="subChk" class="check" id="square-checkbox-1" data-checkbox="icheckbox_square-blue" value="'+ li.codeTextId +'"></td>'
							+ '<td class="tableControl">'+ devices +'</td>'
							+ '<td class="tableControl">'+ keyword +'</td>'
							+ '<td class="tableControl">'+ text +'</td>'
							+ '<td class="tableControl">'+ li.creatime +'</td>'
							+ '<td class="tableControl">'
							+'<button type="button" title="查看" class="btn btn-info btn-circle btn-xs" onclick="seek('+ li.codeTextId +')" style="margin-right:3px;"><i class="fa fa-search-plus"></i> </button>'
							+'<button type="button" title="编辑" class="btn btn-info btn-circle btn-xs" onclick="edit('+ li.codeTextId +')" style="margin-right:3px;"><i class="fa fa-edit"></i> </button>'
							+'<button type="button" title="删除" class="btn btn-danger btn-circle btn-xs" onclick="del('+ li.codeTextId +')"><i class="fa fa-times"></i> </button>'
						    +'</td>'
							+'</tr>'
					$("#tbody").append(trs);	
				 }); 
				//分页 
				$.jqPaginator('#page',{
					totalPages : result.totalPage,
					visiblePages : 5,
					currentPage : result.pageNo,
					first : '<li class="first"><a href="javascript:;"><i class="fa fa-angle-double-left"></i></a></li>',
					prev : '<li class="prev"><a href="javascript:;"><i class="fa fa-angle-left"></i></a></li>',
					next : '<li class="next"><a href="javascript:;"><i class="fa fa-angle-right"></i></a></li>',
					last : '<li class="last"><a href="javascript:;"><i class="fa fa-angle-double-right"></i></a></li>',
					page : '<li class="page"><a href="javascript:;">{{page}}</a></li>',
					onPageChange : function(num, type) {
						$('#pageNo').val(num);
						//当事件类型为change时再调用此方法，若为init事件表示刚开始加载页面
						if (type == "change") {
					        if($.cookie('searchWord') != $('#searchFund').val()){
								$('#searchFund').val("");
								$.cookie('searchWord', ""); 
					            search(1);
							}else{
								search(1);
							}
					    }
						$('.check').iCheck('uncheck');//翻页的时候清楚选中的checkbox
					}
				});
				//单页时不显示分页
				if(result.totalPage == 1){
					$('#page').hide();
				} else {
					$('#page').show();
				}
			}
		});
	 }
     //查询当前所有主设备号
	 function currentDevices(){
		  $.ajax({
	      	 type: "POST",
	      	 url: "${ctx}/lazyearn/IntelligentManager/findMainDevices",
	           data: {
	            	
	           },
	         dataType: "json",
	         success: function(data){
	          	$.each(data,function(i, li) {
	          	   var select = '<option value="'+li.maindevices+'">'+ li.maindevices+'</option>';
	          	   $('#selectDevices').append(select);
			    }); 
	          }
	  	   }); 
	 }
	 //获取select选中的文本和value
	 $("#selectDevices").change(function(){
		 var checkText=$("#selectDevices").find("option:selected").val();//获取Select选择的value
		 if(checkText=='请选择设备号...'){
			 $('#selectedDevice').val('');
		}else{
			 $('#selectedDevice').val(checkText);
		}
		 search(0);
	 }); 
	 //转换时间
	 function getDateStr(DBTime){
		var date = new Date(DBTime);
		var year = date.getFullYear();
		var month = (date.getMonth()+1 > 9) ? (date.getMonth()+1) : ('0'+(date.getMonth()+1));
		var day = (date.getDate() > 9) ? (date.getDate()) : ('0'+date.getDate());
		var hour = (date.getHours() > 9) ? (date.getHours()) : ('0'+date.getHours());
		var minute = (date.getMinutes() > 9) ? (date.getMinutes()) : ('0'+date.getMinutes());
		var second = (date.getSeconds() > 9) ? (date.getSeconds()) : ('0'+date.getSeconds());
		var dateStr = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
		return dateStr;
	  }
	 //跳转表单
	 function add(){
		 $('#jump').attr("action", "${ctx}/lazyearn/IntelligentManager/jumpForm");	
		 $('#jump').submit();
	 }
	 
	//单个删除方法
    function del(codeTextId){
    	layer.alert('确认删除？', {
			  skin: 'layui-layer-lan',icon:3,btn: ['确认','取消'] ,closeBtn:0 //按钮
			}, function(){
				$.ajax({
					type : "POST",
					url : "${ctx}/lazyearn/IntelligentManager/delete",
					data : {
						codeTextId : codeTextId,
					},
					dataType : "json",
					success : function(result) {
						if(result > 0){
							search();
							layer.msg('删除成功！', { time : 1000, icon : 1 });
						}
					}
				});
		});
	}

	//批量删除
	function deleteAll(){
		// 判断是否至少选择一项 
		var checkedNum = $("input[name='subChk']:checked").length; 
		if(checkedNum == 0) { 
			layer.alert('请选择至少一项！', {
				  skin: 'layui-layer-lan',icon:0,anim: 0});
			return; 
		}
		var checkedList = new Array(); 
		$("input[name='subChk']:checked").each(function() { 
		      checkedList.push($(this).val()); 
		}); 
		layer.alert('确认删除？', {
			  skin: 'layui-layer-lan',icon:3,btn: ['确认','取消'] ,closeBtn:0 //按钮
			}, function(){
				$.ajax({
					type : "POST",
					url : "${ctx}/lazyearn/IntelligentManager/deleteAll",
					data : {
						'delitems':checkedList.toString(),
					},
					dataType : "json",
					success : function(result) {
						if(result > 0){
							search();
							$('.check').iCheck('uncheck'); 
							layer.msg('删除成功！', { time : 1000, icon : 1 });
						}
					}
				});
		}); 
	}
	//点击编辑是跳转至表单提交页
	function edit(codeTextId){
		if(codeTextId != -1){
			$('#codeIdVal').val(codeTextId);
		}
		$('#jump').attr("action", "${ctx}/lazyearn/IntelligentManager/jumpForm");	
		$('#jump').submit();
	}
	//点击查看是跳转至表单提交页
	function seek(codeTextId){
		if(codeTextId != -1){
			$('#codeIdVal').val(codeTextId);
		}
		$('#jump').attr("action", "${ctx}/lazyearn/IntelligentManager/jumpForm");	
		$('#jump').submit();
	}
	//checkbox监听
	$('#ischange').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		$('.check').iCheck('check');
	});
	$('#ischange').on('ifUnchecked', function(event){ //ifUnchecked 事件应该在插件初始化之前绑定 
		$('.check').iCheck('uncheck');
	});
	//点击上方的编辑
	function edit1(){
		// 判断是否至少选择一项 
		var checkedNum = $("input[name='subChk']:checked").length; 
		if(checkedNum == 0) { 
			layer.alert('请选择需要编辑的！', {
				  skin: 'layui-layer-lan',icon:0,anim: 0});
			return; 
		}
		if(checkedNum >1) { 
		    layer.alert('至多选择一条！', {
				  skin: 'layui-layer-lan',icon:0,yes:function(index, layero){
		               $('.check').iCheck('uncheck');
		               layer.close(index);//如果设定了yes回调，需进行手工关闭
				       }
				  });
			return; 
		}
		var checkedId; 
		$("input[name='subChk']:checked").each(function() { 
			checkedId = $(this).val(); 
		}); 
		if(checkedId != -1){
			$('#codeIdVal').val(checkedId);
		}
		$('#jump').attr("action", "${ctx}/lazyearn/IntelligentManager/jumpForm");	
		$('#jump').submit();
	  }
   </script>
	
</body>
</html>