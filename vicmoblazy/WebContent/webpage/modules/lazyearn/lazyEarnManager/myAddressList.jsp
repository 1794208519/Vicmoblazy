<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>

<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>

<style type="text/css">
.modal_top {
	border-top: none;
	padding: 4px;
}

.table-striped tbody tr:nth-of-type(odd) {
	
}
td.tableControl{
	line-height: 40px
}
td.check{
margin-top: 10px
}
#l-map {
	height: 500px;
	width: 100%;
}

#r-result {
	width: 100%;
	position: absolute;
	top: 20px;
	left: 20px;
}

#result_list {
	padding-left: 0px;
	background: white;
	position: absolute;
	top: 50px;
	left: 20px;
}
/*隐藏地图上的一段文字  */
.BMap_stdMpPan,.BMap_stdMpZoom .BMap_button,.BMap_stdMpSliderBgTop,.BMap_stdMpSliderBar,.BMap_stdMpSliderBgBot,.anchorBL{

width: 0 !important;

height: 0 !important;

}

.anchorBL img{

width: 0 !important;

}
.th1{
	width: 90px;
	max-width: 90px;
	min-width: 50px
	
}
.th2{
	width: 250px;
	max-width: 250px;
	min-width: 100px
	
}
.th3{
	width: 495px;
	max-width: 495px;
	min-width: 250px
	
}
.th4{
	width: 465px;
	max-width: 465px;
	min-width: 180px
	
}
.th5{
	width: 280px;
	max-width: 280px;
	min-width: 100px
	
}
</style>
</head>
<body onkeydown="keySearch();">
	<div class="divControl">
		<div class="card">
			<ul class="nav nav-tabs customtab" role="tablist">
				<li class="nav-item"><a class="nav-link active"
					data-toggle="tab" href="#home2" role="tab"> <span
						class="hidden-sm-up"><i class="ti-home"></i></span> <span
						class="hidden-xs-down">我的地址</span>
				</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane p-20 active" id="home2" role="tabpanel">
					<div class="row button-group col-3"
						style="float: left; margin-bottom: 8px;">
						<button type="button" class="btn btn-info" onclick="add()">
							<i class="fa fa-plus-circle"></i> 添加
						</button>
						<button type="button" class="btn btn-info" onclick="edit1()">
							<i class="fa fa fa-edit"></i> 编辑
						</button>
						<button type="button" class="btn btn-danger" onclick="deleteAll()">
							<i class="fa fa fa-times"></i> 删除
						</button>
					</div>
					<div class="row col-9" style="float: right;">
						<form action="" method="post" onsubmit="return false" style="width: 100%" id="searchForm">
							<input id="pageNo" name="pageNo" type="hidden" value="1" /> <input
								id="pageSize" name="pageSize" type="hidden" value="10" />
							<button type="button" id="keysearch"
								class="btn waves-effect waves-light btn-info"
								onclick="search(0)" style="float: right; margin-left: 15px">搜索</button>
						  	<input autocomplete="off" type="text"
								class="form-control height-control col-4" placeholder="请输入赚赚\地址搜索"
								id="searchFund" name="searchFund" value="" style="float: right;" />
								<select class="custom-select" id="selectmainDevices" name="maindevices" style="float: right;margin-right: 20px">
									<option value="0" selected>请选择设备号...</option>
								</select>
						</form>
					</div>
					<table id="example23"
						class="display nowrap table table-hover table-striped table-bordered"
						cellspacing="0" width="100%">
						<thead>
							<tr>
								<th class="tableControl th1"><input type="checkbox"
									class="check" id="ischange"
									data-checkbox="icheckbox_square-blue"></th>
								<th class="tableControl th2">赚赚设备号</th>
								<th class="tableControl th3">地址</th>
								<th class="tableControl th4">懒懒设备号</th>
								<th class="tableControl th5">操作</th>
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
		<input type="text" id="fundIdVal" name="sid" />
	</form>
	<!-- 弹框 -->
	<div id="responsive-modal" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
		<div style="max-width:800px;" class="modal-dialog" >
			<div class="modal-content" style="max-width:800px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">添加地址</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" method="post" onsubmit="return false" onkeydown="if(event.keyCode==13)return false;"
						id="dataform">
						<input type="hidden" id="dataId" name="dataId" value="" /> <input
							type="hidden" id="lat" name="lat" value="百度" /> <input
							type="hidden" id="lng" name="lng" value="百度" /> 
						<!--百度地图 --> 						
						<div>
							<div id="l-map" >
							
							</div>
							<div id="r-result">
								
								<input type="text" id="suggestId" size="20" name="address"
									style="width: 250px;" value="百度" autocomplete="off" placeholder="请输入地址搜索"/>
								<button type="button" class="btn btn-default waves-effect"
									onclick="searchmap()">搜索</button>
									  <input autocomplete="off" type="text" class="form-control" name="devices" id="devices" placeholder="请输入设备号" style="width: 200px" value="" list="ide">
                                 <datalist id="ide" >

								</datalist>    
								
								
							</div>
			
							<div>
								<ul id="result_list"></ul>
							</div>
							<img alt="" src="${ctxStatic}/asset/images/coordinate.png" style="text-align: center;width:30px;height: 30px;margin-top: 230px;position: absolute;margin-left: 380px;top: 0px;left: 0px"> 
							
						</div>

						<div class="modal-footer modal_top">
							<button type="button" class="btn btn-default waves-effect"
								data-dismiss="modal">关闭</button>
							<button type="submit"
								class="btn btn-danger waves-effect waves-light" style="margin-left: 10px" onclick="save()">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			//先调下数据(默认为第一页开始，每页十个)
			search(-1);
			layer.load();//加载层		
			currentMainDevices();
			currentNumDevices();
		});
		
		 //enter搜索
	    function keySearch(){  
	    	   if (event.keyCode==13){  //回车键的键值为13  
	    	      document.getElementById("keysearch").click(); 
	    	   }  
	    	}  
		//数据显示
		function search(type) {
			if (type == 0) {
				$.cookie('searchWord', $('#searchFund').val());
			} else if (type == -1) {
				$.cookie('searchWord', "");
			}
			$
					.ajax({
						type : "POST",
						url : "${ctx}/lazyearn/AutoData/findList",
						data : $('#searchForm').serialize(),
						dataType : "json",
						success : function(result) {
							layer.closeAll('loading');//去除加载层
							//先清空
							$("#tbody").empty();
							//再添加
							$
									.each(
											result.list,
											function(i, li) {
												var trs = '<tr id="'+ li.dataId +'">'
														+ '<td class="tableControl th1"><input type="checkbox" name="subChk" class="check" id="square-checkbox-1" value="'+ li.dataId +'" data-checkbox="icheckbox_square-blue"></td>'
														+ '<td class="tableControl th2">'
														+ li.devices
														+ '</td>'
														+ '<td class="tableControl th3">'
														+ li.address
														+ '</td>'
														+ '<td class="tableControl th4">'
														+ li.maindevices
														+ '</td>'
														+ '<td class="tableControl th5">'
														+ '<button type="button" title="查看" class="btn btn-info btn-circle btn-xs" onclick="seek('
														+ li.dataId
														+ ')" style="margin-right:3px;"><i class="fa fa-search-plus"></i> </button>'
														+ '<button type="button" title="编辑" class="btn btn-info btn-circle btn-xs" onclick="edit('
														+ li.dataId
														+ ')" style="margin-right:3px;"><i class="fa fa-edit"></i> </button>'
														+ '<button type="button" title="删除" class="btn btn-danger btn-circle btn-xs" onclick="del('
														+ li.dataId
														+ ')"><i class="fa fa-times"></i> </button>'
														+ '</td>' + '</tr>'
												$("#tbody").append(trs);
											});
							//分页 
							$
									.jqPaginator(
											'#page',
											{
												totalPages : result.totalPage,
												visiblePages : 5,
												currentPage : result.pageNo,
												first : '<li class="first"><a href="javascript:;"><i class="fa fa-angle-double-left"></i></a></li>',
												prev : '<li class="prev"><a href="javascript:;"><i class="fa fa-angle-left"></i></a></li>',
												next : '<li class="next"><a href="javascript:;"><i class="fa fa-angle-right"></i></a></li>',
												last : '<li class="last"><a href="javascript:;"><i class="fa fa-angle-double-right"></i></a></li>',
												page : '<li class="page"><a href="javascript:;">{{page}}</a></li>',
												onPageChange : function(num,
														type) {
													$('#pageNo').val(num);
													//当事件类型为change时再调用此方法，若为init事件表示刚开始加载页面
													if (type == "change") {
														if ($
																.cookie('searchWord') != $(
																'#searchFund')
																.val()) {
															$('#searchFund')
																	.val("");
															$
																	.cookie(
																			'searchWord',
																			"");
															search(1);
														} else {
															search(1);
														}
													}
													$('.check').iCheck(
															'uncheck');//翻页的时候清楚选中的checkbox
												}
											});
							//单页时不显示分页
							if (result.totalPage == 1) {
								$('#page').hide();
							} else {
								$('#page').show();
							}
						}
					});
		}

		//转换时间
		function getDateStr(DBTime) {
			var date = new Date(DBTime);
			var year = date.getFullYear();
			var month = (date.getMonth() + 1 > 9) ? (date.getMonth() + 1)
					: ('0' + (date.getMonth() + 1));
			var day = (date.getDate() > 9) ? (date.getDate()) : ('0' + date
					.getDate());
			var hour = (date.getHours() > 9) ? (date.getHours()) : ('0' + date
					.getHours());
			var minute = (date.getMinutes() > 9) ? (date.getMinutes())
					: ('0' + date.getMinutes());
			var second = (date.getSeconds() > 9) ? (date.getSeconds())
					: ('0' + date.getSeconds());
			var dateStr = year + "-" + month + "-" + day + " " + hour + ":"
					+ minute + ":" + second;
			return dateStr;
		}
		//跳转表单
		function add() {
			
			show();
		}

		//单个删除方法
		function del(dataId) {
			layer.alert('确认删除？', {
				skin : 'layui-layer-lan',
				icon : 3,
				btn : [ '确认', '取消' ],
				closeBtn : 0
			//按钮
			}, function() {
				$.ajax({
					type : "POST",
					url : "${ctx}/lazyearn/AutoData/delete",
					data : {
						dataId : dataId,
					},
					dataType : "json",
					success : function(result) {
						if (result > 0) {
							search();
							layer.msg('删除成功！', {
								time : 1000,
								icon : 1
							});
						}
					}
				});
			});
		}

		//批量删除
		function deleteAll() {
			// 判断是否至少选择一项 
			var checkedNum = $("input[name='subChk']:checked").length;
			if (checkedNum == 0) {
				layer.alert('请选择至少一项！', {
					skin : 'layui-layer-lan',
					icon : 0,
					anim : 0
				});
				return;
			}
			var checkedList = new Array();
			$("input[name='subChk']:checked").each(function() {
				checkedList.push($(this).val());
			});
			layer.alert('确认删除？', {
				skin : 'layui-layer-lan',
				icon : 3,
				btn : [ '确认', '取消' ],
				closeBtn : 0
			//按钮
			}, function() {
				$.ajax({
					type : "POST",
					url : "${ctx}/lazyearn/AutoData/deleteAll",
					data : {
						'delitems' : checkedList.toString(),
					},
					dataType : "json",
					success : function(result) {
						
						 if (result > 0) { 
							search();
							$('.check').iCheck('uncheck');
							layer.msg('删除成功！', {
								time : 1000,
								icon : 1
							});
						 } 
					}
				});
			});
		}
		//保存表单
		function save() {
			if ($("#dataId").val() == null || $("#dataId").val() == ""
			) {
				if ($("#suggestId").val() == null || $("#suggestId").val() == ""
				) {
					layer.msg('请输入地址！', {
						icon : 2
					});
					$("#lat").val("");
					$("#lng").val("");
					return ;
				}else{
					layer.alert('确认保存？', {
						skin : 'layui-layer-lan',
						icon : 3,
						btn : [ '确认', '取消' ],
						closeBtn : 0
					//按钮
					}, function() {
						$(".btn-info").attr('disabled', true);
						$.ajax({
							type : "POST",
							url : "${ctx}/lazyearn/AutoData/save",
							data : $('#dataform').serialize(),
							dataType : "json",
							success : function(data) {
								if (data > 0) {
									layer.msg('保存成功', {
										icon : 1,
										time : 1000,
										closeBtn : 0
									});
									
									dismiss();
									search();
								} else if (data == -1) {
									layer.msg('保存失败！', {
										icon : 2
									});
								}
								$(".btn-info").attr('disabled', false);
							}
						});
					});
				}
			}else{
				if ($("#suggestId").val() == null || $("#suggestId").val() == ""
				) {
					if ($("#devices").val() == null || $("#devices").val() == ""
					) {
						layer.msg('地址与设备号不能同时为空', {
							icon : 2
						});
						return ;
					}
					
				}
				layer.alert('确认保存？', {
					skin : 'layui-layer-lan',
					icon : 3,
					btn : [ '确认', '取消' ],
					closeBtn : 0
				//按钮
				}, function() {
					$(".btn-info").attr('disabled', true);
					$.ajax({
						type : "POST",
						url : "${ctx}/lazyearn/AutoData/save",
						data : $('#dataform').serialize(),
						dataType : "json",
						success : function(data) {
							if (data > 0) {
								layer.msg('保存成功', {
									icon : 1,
									time : 1000,
									closeBtn : 0
								});
								
								dismiss();
								search();
							} else if (data == -1) {
								layer.msg('保存失败！', {
									icon : 2
								});
							}
							$(".btn-info").attr('disabled', false);
						}
					});
				});
				
			}

		}
		//点击编辑是跳转至表单提交页
		function edit(dataId) {
			
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/AutoData/edit",
				data : {
					dataId : dataId,
				},
				dataType : "json",
				success : function(data) {
					if (data != null) {
						$('#suggestId').val(data.address);
						/*  var  ss = document.getElementById('selectDevices');
						 var isselect=false;
						for (var i = 0; i < ss.options.length; i++) {
							if(data.devices ==ss[i].text){
								ss[i].selected = true;//选中
								isselect=true;
							}
						} 
						if (!isselect) {
							ss[0].selected = true;//选中
						} */
						$('#devices').val(data.devices);
						$('#dataId').val(data.dataId);
						myFun1(data.longitude,data.latitude);
					}
					$("#responsive-modal").modal("show");
				}
			});
		}
		//点击查看是跳转至表单提交页
		function seek(dataId) {
			
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/AutoData/edit",
				data : {
					dataId : dataId,
				},
				dataType : "json",
				success : function(data) {
					if (data != null) {
						$('#suggestId').val(data.address);
						/*  var  ss = document.getElementById('selectDevices');
						 var isselect=false;
						for (var i = 0; i < ss.options.length; i++) {
							if(data.devices ==ss[i].text){
								ss[i].selected = true;//选中
								isselect=true;
							}
						} 
						if (!isselect) {
							ss[0].selected = true;//选中
						} */
						$('#devices').val(data.devices);
						$('#dataId').val(dataId);
						myFun1(data.longitude,data.latitude);
						
					}
					$("#responsive-modal").modal("show");
				}
			});
		}
		//checkbox监听
		$('#ischange').on('ifChecked', function(event) { //ifCreated 事件应该在插件初始化之前绑定 
			$('.check').iCheck('check');
		});
		$('#ischange').on('ifUnchecked', function(event) { //ifUnchecked 事件应该在插件初始化之前绑定 
			$('.check').iCheck('uncheck');
		});
		//弹框显示
		function show() {
			
			//清空表单数据
			$(".modal-body form input").each(function() {
				$(this).val('');
			});
			$("#responsive-modal").modal("show");
		}
		//弹框隐藏
		function dismiss() {
			$("#responsive-modal").modal("hide");
		}
		//点击上方的编辑
		function edit1() {
			
			// 判断是否至少选择一项 
			var checkedNum = $("input[name='subChk']:checked").length;
			if (checkedNum == 0) {
				layer.alert('请选择需要编辑的！', {
					skin : 'layui-layer-lan',
					icon : 0,
					anim : 0
				});
				return;
			}
			if (checkedNum > 1) {
				layer.alert('至多选择一条！', {
					skin : 'layui-layer-lan',
					icon : 0,
					yes : function(index, layero) {
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
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/AutoData/edit",
				data : {
					dataId : checkedId
				},
				dataType : "json",
				success : function(data) {
					if (data != null) {
						$('#suggestId').val(data.address);
						/*  var  ss = document.getElementById('selectDevices');
						 var isselect=false;
						for (var i = 0; i < ss.options.length; i++) {
							if(data.devices ==ss[i].text){
								ss[i].selected = true;//选中
								isselect=true;
							}
						} 
						if (!isselect) {
							ss[0].selected = true;//选中
						} */
						$('#devices').val(data.devices);
						myFun1(data.longitude,data.latitude);
					}
					$("#responsive-modal").modal("show");
				}
			});
		}
		//查询当前所有子设备号
		function currentNumDevices() {
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/IntelligentManager/findChildNumDevices",
				dataType : "json",
				success : function(data) {
					$.each(data, function(i, li) {
						var select = '<option value="'+li.partdevices+'">'
								+ li.partdevices + '</option>';
						$('#ide').append(select);
					});
				}
			});
		}
		
		//查询当前所有子设备号
		function currentMainDevices() {
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/IntelligentManager/findMainDevices",
				dataType : "json",
				success : function(data) {
					$.each(data, function(i, li) {
						var select = '<option value="'+li.maindevices+'">'
								+ li.maindevices + '</option>';
						$('#selectmainDevices').append(select);
					});
					search(-1);
				}
			});
		}
		 //获取select选中的文本和value
		 $("#selectmainDevices").change(function(){
			 var checkText=$("#selectmainDevices").find("option:selected").val();//获取Select选择的value
			if(checkText=='请选择设备号...'){
				 $('#selectmainDevices').val('');
			}else{
				 $('#selectmainDevices').val(checkText);
			}
			 search(0);
		 }); 
	</script>
	<script type="text/javascript">
		// 百度地图API功能
		var map = new BMap.Map("l-map",{enableMapClick: false});
		map.centerAndZoom("无锡", 18); // 初始化地图,设置城市和地图级别。  
		map.enableScrollWheelZoom(true);
		var geoc = new BMap.Geocoder();  
		/* var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
			if(this.getStatus() == BMAP_STATUS_SUCCESS){
				var mk = new BMap.Marker(r.point);
				map.addOverlay(mk);
				map.panTo(r.point);
				var cp=new BMap.Point(r.point.lng, r.point.lat);
				geoc.getLocation(cp, function(rs){
					   var addComp = rs.address;
					   $("#suggestId").val(addComp);
					   $("#lat").val(rs.point.lat);
					   $("#lng").val(rs.point.lng);
				   });
				$("#suggestId").val(r.title);
				
			}
			       
		},{enableHighAccuracy: true}) */
		
		map.addEventListener("dragend", function showInfo(){
			   var cp = map.getCenter();
			 
			   geoc.getLocation(cp, function(rs){
				   var addComp = rs.address;
				   $("#suggestId").val(addComp);
				   $("#lat").val(rs.point.lat);
				   $("#lng").val(rs.point.lng);
			   });
			});
		function setPlace(myValue) {
			map.clearOverlays(); //清除地图上所有覆盖物

			function myList() {
				$("#result_list").empty();
				var num = local.getResults().getCurrentNumPois();
				if (num >= 5) {
					num = 5;
				} else {
					num = local.getResults().getCurrentNumPois();
				}
				for (var i = 0; i < num; i++) {

					var e = local.getResults().getPoi(i);
					var title = e.title;
					var Str = '<li id="mapli_'
							+ i
							+ '"  onmouseup="mouseUp('
							+ e.point.lng
							+ ','
							+ e.point.lat
							+ ',\''
							+ title
							+ '\')" style="margin: 2px 0px; padding: 0px; cursor: pointer; overflow: hidden;list-style:none;background:white;border-bottom:1px;border-bottom-color:#aaaaaa"><i class="route-icon"></i>'
							+ local.getResults().getPoi(i).title + '</li>'
					$("#result_list").append(Str);

				}

			}
			var local = new BMap.LocalSearch(map, { //智能搜索
				onSearchComplete : myList
			});
			local.search(myValue);
		}
		function myFun1(lng, lat) {
			//   //获取第一个智能搜索的结果
			map.clearOverlays(); //清除地图上所有覆盖物
			
			var point = new BMap.Point(lng, lat);
			var point1 = new BMap.Point(lng-0.0035, lat-(-0.002));
			map.centerAndZoom(point1, 18);    
			/* map.addOverlay(new BMap.Marker(point)); //添加标注 */
			/* map.setCenter(point);  */
			/* map.panTo(point); */
		}
		function myFun(lng, lat) {
			//   //获取第一个智能搜索的结果
			map.clearOverlays(); //清除地图上所有覆盖物
			
			var point = new BMap.Point(lng, lat);
			map.centerAndZoom(point, 18);    
			/* map.addOverlay(new BMap.Marker(point)); //添加标注 */
			
		}
		function mouseUp(lng, lat, title) {
			$("#lat").val(lat);
			$("#lng").val(lng);
			$("#suggestId").val(title);
			$("#result_list").empty();
			myFun(lng, lat);
		}
		function searchmap() {
			var myValue = $("#suggestId").val();
			setPlace(myValue);
		}
		
	</script>
</body>

</html>