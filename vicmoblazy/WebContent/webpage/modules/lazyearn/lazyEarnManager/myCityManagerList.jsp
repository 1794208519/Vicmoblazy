<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
</head>
<style>
.th1{
	width: 88px;
	max-width: 88px;
	min-width: 50px
	
}
.th2{
	width: 468px;
	max-width: 468px;
	min-width: 250px
	
}
.th3{
	width: 335px;
	max-width: 335px;
	min-width: 150px
	
}
.th4{
	width: 136px;
	max-width: 136px;
	min-width: 100px
	
}
.th5{
	width: 136px;
	max-width: 136px;
	min-width: 100px
	
}
.th6{
	width: 136px;
	max-width: 136px;
	min-width: 100px
	
}
.th7{
	width: 283px;
	max-width: 283px;
	min-width: 150px
	
}
</style>
<body>

	<div class="divControl">
		<div class="card">
			<ul class="nav nav-tabs customtab" role="tablist">
				<li class="nav-item"><a class="nav-link active"
					data-toggle="tab" href="#home2" role="tab"> <span
						class="hidden-sm-up"><i class="ti-home"></i></span> <span
						class="hidden-xs-down">城市管理</span>
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

						<form action="" method="post" style="width: 100%" id="searchForm"
							onsubmit="return false;">
							<input type="hidden" id="accountId" name="accountId"> <input
								id="pageNo" name="pageNo" type="hidden" value="1" /> <input
								id="pageSize" name="pageSize" type="hidden" value="10" />
							<button type="button"
								class="btn waves-effect waves-light btn-info"
								onclick="search(0)" style="float: right; margin-left: 15px">搜索</button>
							<input autocomplete="off" type="text" maxlength="25"
								class="form-control height-control col-4" placeholder="请输入省份\城市\运营商搜索
								id="searchFund" name="searchFund" value="" style="float: right;"
								onclick="keyLogin()"
								onkeyup="this.value=this.value.replace(/^ +| +$/g,'')" /> <select
								class="custom-select" id="selectmainDevices" name="maindevices"
								style="float: right; margin-right: 20px">
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
								<th class="tableControl th3">懒懒设备号</th>
								<th class="tableControl th4">省份</th>
								<th class="tableControl th5">城市</th>
								<th class="tableControl th6">运营商</th>
								<th class="tableControl th7">操作</th>
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
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">添加城市</h4>
				</div>
				<div class="modal-body">
					<form action="" method=post name=form1 class="form-horizontal"
						id="cityform" onsubmit="return false">
						<input type="hidden" id="cityId" name="cityId" value="">
						<div class="form-group row">
							<label for="inputEmail3"
								class="col-sm-3 text-right control-label col-form-label">城市选择*</label>
							<div class="col-sm-9">
								<table>
									<tr>
										<div data-toggle="distpicker" id="dispicker">
											<div class="form-group">
												<select name="province" class="custom-select col-sm-9"
													id="province">
												</select>
											</div>
											<div class="form-group">
												<select name="city" class="custom-select col-sm-9" id="city">
												</select>
											</div>
										</div>
									</tr>
								</table>
							</div>
						</div>
						<div class="form-group row " style="margin-top: -25px">
							<label for="inputEmail3"
								class="col-sm-3 text-right control-label col-form-label">运营商*</label>
							<div class="col-sm-9">
								<table class=" col-sm-12">
									<tr>
										<td><select class="custom-select col-sm-9" id="operator"
											name="operator">
												<option value="运营商" selected="selected">—— 运营商 ——</option>
												<option value="联通">联通</option>
												<option value="移动">移动</option>
												<option value="电信">电信</option>
										</select></td>
									</tr>
								</table>
							</div>
						</div>

						<div class="form-group row">
							<label for="inputEmail3"
								class="col-sm-3 text-right control-label col-form-label">设备号</label>
							<div class="col-sm-9">
								<!-- 	<input autocomplete="off" type="text" class="form-control"
									name="devices" id="devices" value=""> -->
								<select class="custom-select col-sm-9" id="devices"
									name="devices">
									<option value="" selected>请选择设备号...</option>
								</select>
							</div>
						</div>

						<div class="modal-footer modal_top">
							<button type="button" class="btn btn-default waves-effect"
								data-dismiss="modal">关闭</button>
							<button type="submit"
								class="btn btn-danger waves-effect waves-light" onclick="save()">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script src="${ctxStatic}/distpicker/distpicker.data.js"></script>
	<script src="${ctxStatic}/distpicker/distpicker.js"></script>
	<script src="${ctxStatic}/distpicker/main.js"></script>
	<script type="text/javascript">
		$(function() {
			//先调下数据(默认为第一页开始，每页十个)
			search(-1);
			layer.load();//加载层
			currentDevices();
			currentMainDevices();
		});
		//数据显示
		function search(type) {
			if (type == 0) {
				$.cookie('searchWord', $('#searchFund').val());
			} else if (type == -1) {
				$.cookie('searchWord', "");
			}
			var data = $('#searchForm').serialize() + '&selectedDevice='
					+ $('#selectDevices').val()
			$
					.ajax({
						type : "POST",
						url : "${ctx}/lazyearn/AutoCity/findList",
						data : data,
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
												var trs = '<tr id="'+ li.cityId +'">'
														+ '<td class="tableControl th1"><input type="checkbox" name="subChk" class="check" id="square-checkbox-1" value="'+ li.cityId +'" data-checkbox="icheckbox_square-blue"></td>'
														+ '<td class="tableControl th2">'
														+ li.devices
														+ '</td>'
														+ '<td class="tableControl th3">'
														+ li.maindevices
														+ '</td>'
														+ '<td class="tableControl th4">'
														+ li.province
														+ '</td>'
														+ '<td class="tableControl th5">'
														+ li.city
														+ '</td>'
														+ '<td class="tableControl th6">'
														+ li.operator
														+ '</td>'
														+ '<td class="tableControl th7">'
														+ '<button type="button" title="查看" class="btn btn-info btn-circle btn-xs" onclick="seek('
														+ li.cityId
														+ ')" style="margin-right:3px;"><i class="fa fa-search-plus"></i> </button>'
														+ '<button type="button" title="编辑" class="btn btn-info btn-circle btn-xs" onclick="edit('
														+ li.cityId
														+ ')" style="margin-right:3px;"><i class="fa fa-edit"></i> </button>'
														+ '<button type="button" title="删除" class="btn btn-danger btn-circle btn-xs" onclick="del('
														+ li.cityId
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
			/* Distpicker.DEFAULTS */
			var ss0 = document.getElementById('province');
			ss0[0].selected = true;//选中
			var ss1 = document.getElementById('city');
			ss1[0].selected = true;//选中
			var ss2 = document.getElementById('devices');
			ss2[0].selected = true;//选中
			var ss3 = document.getElementById('operator');
			ss3[0].selected = true;//选中
			show();
		}

		//单个删除方法
		function del(cityId) {
			layer.alert('确认删除？', {
				skin : 'layui-layer-lan',
				icon : 3,
				btn : [ '确认', '取消' ],
				closeBtn : 0
			//按钮
			}, function() {
				$.ajax({
					type : "POST",
					url : "${ctx}/lazyearn/AutoCity/delete",
					data : {
						cityId : cityId,
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
					url : "${ctx}/lazyearn/AutoCity/deleteAll",
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
			var province = $("#province").find("option:selected").text();
			var city = $("#city").find("option:selected").text();

			if ($("#city").find("option:selected").text() == null
					|| $("#city").find("option:selected").text() == ""
					|| $("#operator").find("option:selected").text() == null
					|| $("#operator").find("option:selected").text() == ""
					|| $("#province").find("option:selected").text() == null
					|| $("#province").find("option:selected").text() == ""
					|| $("#operator").find("option:selected").text() == "—— 运营商 ——"
					|| $("#city").find("option:selected").text() == "—— 市 ——"
					|| $("#province").find("option:selected").text() == "—— 省 ——") {
				alert("请确认城市选择和运营商");
			} else {
				var flag = true;
				layer.alert('确认保存？', {
					skin : 'layui-layer-lan',
					icon : 3,
					btn : [ '确认', '取消' ],
					closeBtn : 0
				//按钮
				}, function() {
					if (flag) {
						flag = false;
						$(".btn-info").attr('disabled', true);
						$.ajax({
							type : "POST",
							url : "${ctx}/lazyearn/AutoCity/save",
							data : $('#cityform').serialize(),
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
								} else if (data == -2) {
									layer.msg('该设备已分配！', {
										icon : 2
									});
									dismiss();
								}
								$(".btn-info").attr('disabled', false);
							}
						});
					}
				});
			}
		}
		var flag = 1;
		//点击编辑是跳转至表单提交页
		function edit(cityId) {
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/AutoCity/edit",
				data : {
					cityId : cityId
				},
				dataType : "json",
				success : function(data) {

					if (data != null) {
						var isselect = false;
						$("#province").val(data.province);
						$("#province").trigger("change");
						$("#city").val(data.city);
						$("#city").trigger("change");
						$('#cityId').val(data.cityId);
						$('#operator').val(data.operator);
						var ss = document.getElementById('devices');
						for (var i = 0; i < ss.options.length; i++) {
							if (data.devices == ss[i].text) {
								ss[i].selected = true;//选中
								isselect = true;
							}
						}
						if (!isselect) {
							ss[0].selected = true;//选中
						}
						/* $('#devices').val(data.devices); */
					}
					$("#responsive-modal").modal("show");
				}
			});
		}

		//点击查看是跳转至表单提交页
		function seek(cityId) {
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/AutoCity/edit",
				data : {
					cityId : cityId
				},
				dataType : "json",
				success : function(data) {
					if (data != null) {
						var isselect = false;
						$("#province").val(data.province);
						$("#province").trigger("change");
						$("#city").val(data.city);
						$("#city").trigger("change");
						$('#cityId').val(data.cityId);
						$('#operator').val(data.operator);
						var ss = document.getElementById('devices');
						for (var i = 0; i < ss.options.length; i++) {
							if (data.devices == ss[i].text) {
								ss[i].selected = true;//选中
								isselect = true;
							}
						}
						if (!isselect) {
							ss[0].selected = true;//选中
						}
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
		//查询当前所有主设备号
		function currentMainDevices() {
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/IntelligentManager/findMainDevices",
				data : {

				},
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
		$("#selectmainDevices").change(
				function() {
					var checkText = $("#selectmainDevices").find(
							"option:selected").val();//获取Select选择的value

					if (checkText == '请选择设备号...') {
						$('#selectmainDevices').val('');
					} else {
						$('#selectmainDevices').val(checkText);
					}
					search(0);
				});
		//查询当前所有子设备号
		function currentDevices() {
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/IntelligentManager/findChildDevices",
				dataType : "json",
				success : function(data) {
					$.each(data, function(i, li) {
						var select = '<option value="'+li.partdevices+'">'
								+ li.partdevices + '</option>';
						$('#devices').append(select);
					});
				}
			});
		}
		$("#searchFund").keydown(function(e) {
			if (e.keyCode == 13) {//触发键盘事件enter 防止冒泡产生
				search(0);
				return false;
			}
		});
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
				url : "${ctx}/lazyearn/AutoCity/edit",
				data : {
					cityId : checkedId
				},
				dataType : "json",
				success : function(data) {
					if (data != null) {
						var isselect = false;
						$("#province").val(data.province);
						$("#province").trigger("change");
						$("#city").val(data.city);
						$("#city").trigger("change");
						$('#cityId').val(data.cityId);
						$('#operator').val(data.operator);
						var ss = document.getElementById('devices');
						for (var i = 0; i < ss.options.length; i++) {
							if (data.devices == ss[i].text) {
								ss[i].selected = true;//选中
								isselect = true;
							}
						}
						if (!isselect) {
							ss[0].selected = true;//选中
						}
					}
					$("#responsive-modal").modal("show");
				}
			});
		}
	</script>
</body>
</html>