<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
<%-- <script src="${ctxStatic}/asset/common/js/base64.js"></script> --%>
<script src="${ctxStatic}/asset/common/js/jquery.extend.js"></script>

<script type="text/javascript">
	$(function() {
		//让当前表单调用validate方法，实现表单验证功能
		$("#accountform").validate({
			debug : true,
			rules : { //配置验证规则，key就是被验证的dom对象，value就是调用验证的方法(也是json格式)
				account : {
					required : true,
					account : true
				},
				password : {
					required : true,
					password : true
				},
				devices : {
					required : true, //必填。如果验证方法不需要参数，则配置为true
					devices : true
				},
				nickname : {
					required : true, //必填。如果验证方法不需要参数，则配置为true
					rangelength : [1,30]
				}
			},
			messages : {
				account : {
					required : "请输入微信号"
				},
				password : {
					required : "请输入密码"

				},
				devices : {
					required : "请选择设备号"

				},
				nickname : {
					required : "请正确输入微信昵称",
					rangelength : $.validator.format("用户名长度在必须为：{0}-{1}之间")
				}
			}
		});

	});
</script>
<style type="text/css">
.modal_top {
	border-top: none;
	padding: 4px;
}

.table-striped tbody tr:nth-of-type(odd) {
	
}

label.error {
	float: left;
	height: 30px;
	line-height: 20px;
	font-size: 14px;
	text-align: left;
	margin-left: 5px;
	padding-left: 5px;
	color: red;
}

label.valid {
	display: none !important;
}

.th1 {
	width: 62px;
	max-width: 62px;
	min-width: 50px
}

.th2 {
	width: 334px;
	max-width: 334px;
	min-width: 150px
}

.th3 {
	width: 335px;
	max-width: 335px;
	min-width: 150px
}

.th4 {
	width: 200px;
	max-width: 200px;
	min-width: 100px
}

.th5 {
	width: 160px;
	max-width: 160px;
	min-width: 100px
}

.th6 {
	width: 285px;
	max-width: 285px;
	min-width: 120px
}

.th7 {
	width: 200px;
	max-width: 200px;
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
						class="hidden-xs-down">我的账号</span>
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

						<form action="" method="post" onsubmit="return false;"
							style="width: 100%" id="searchForm">
							<input id="pageNo" name="pageNo" type="hidden" value="1" /> <input
								id="pageSize" name="pageSize" type="hidden" value="10" />

							<button id="keysearch" type="button"
								class="btn waves-effect waves-light btn-info"
								onclick="search(0)" style="float: right; margin-left: 15px">搜索</button>
							<input autocomplete="off" type="text"
								class="form-control height-control col-4"
								placeholder="请输入赚赚\账户搜索" id="searchFund" name="searchFund"
								value="" style="float: right;" /> <select class="custom-select"
								id="selectmainDevices" name="maindevices"
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
								<th class="tableControl th2">懒懒设备号</th>
								<th class="tableControl th3">赚赚设备号</th>
								<th class="tableControl th4">账户名</th>
								<th class="tableControl th5">密码</th>
								<th class="tableControl th6">昵称</th>
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

					<h4 class="modal-title">添加账号</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" method="post"
						onkeydown="if(event.keyCode==13)return false;"
						onsubmit="return false" id="accountform"
						action="http://www.hao123.com">
						<input type="hidden" id="accountId" name="accountId" value="">
						<div class="form-group row">
							<label for="inputEmail3"
								class="col-sm-3 text-right control-label col-form-label">账户名*</label>
							<div class="col-sm-9">
								<input autocomplete="off" type="text" class="form-control"
									name="account" id="account" placeholder="请输入微信号暂不支持手机号登录"
									required data-validation-required-message="这是必填项" value="">
							</div>
						</div>
						<div class="form-group row">
							<label for="inputEmail3"
								class="col-sm-3 text-right control-label col-form-label">密码*</label>
							<div class="col-sm-9">
								<input autocomplete="off" type="text" class="form-control"
									name="password" id="password" required
									data-validation-required-message="这是必填项" value=""
									onpaste="return false" ondragenter="return false"
									onkeyup="this.value=check(this.value)">
							</div>
						</div>
						<div class="form-group row">

							<label for="inputEmail3"
								class="col-sm-3 text-right control-label col-form-label">赚赚设备号</label>
							<div class="col-sm-9">
								<input autocomplete="off" type="text" class="form-control"
									name="devices" id="devices" value="" list="ide">
								<datalist id="ide">

								</datalist>
							</div>
						</div>
						<div class="form-group row">
							<label for="inputEmail3"
								class="col-sm-3 text-right control-label col-form-label">昵称*</label>
							<div class="col-sm-9">
								<input autocomplete="off" type="text" class="form-control"
									name="nickname" id="nickname" required
									data-validation-required-message="这是必填项" value="">

							</div>
						</div>
						<div class="modal-footer modal_top">
							<button type="button" class="btn btn-default waves-effect"
								data-dismiss="modal">关闭</button>
							<button type="submit"
								class="btn btn-danger waves-effect waves-light"
								style="margin-left: 10px" onclick="save()">保存</button>
						</div>
					</form>
				</div>


			</div>
		</div>
	</div>


	<script type="text/javascript">
var idata=new Array();
	    $(function(){ 
		  
		   search(-1);
		   layer.load();//加载层
		   currentDevices();
		   currentMainDevices();
		   currentNumDevices();


});
	    var lock = 1;
	    //在松开按键时用脚本检查文本框的值，只保留Unicode编码在0和255之间的字符。 
	    function check(str){ 
	    	var temp="" 
	    	for(var i=0;i<str.length;i++) 
	    	     if(str.charCodeAt(i)>0&&str.charCodeAt(i)<255) 
	    	        temp+=str.charAt(i) 
	    	return temp 
	    	} 
	  
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
			$.ajax({
						type : "POST",
						url : "${ctx}/lazyearn/AutoAccount/findList",
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
												var trs = '<tr id="'+ li.accountId +'">'
														+ '<td class="tableControl th1"><input type="checkbox" name="subChk" class="check" id="square-checkbox-1" value="'+ li.accountId +'" data-checkbox="icheckbox_square-blue"></td>'
														+ '<td class="tableControl th2">'
														+ li.maindevices
														+ '</td>'
														+ '<td class="tableControl th3">'
														+ li.devices
														+ '</td>'
														+ '<td class="tableControl th4">'
														+ li.account
														+ '</td>'
														+ '<td class="tableControl th5">'
														+ li.password
														+ '</td>'
														+ '<td class="tableControl th6">'
														+ li.nickname
														+ '</td>'
														+ '<td class="tableControl th7">'
														+ '<button type="button" title="聊天记录" class="btn btn-info btn-circle btn-xs" onclick="chat(\''
														+ li.nickname
														+ '\')" style="margin-right:3px;"><i class="fa fa-search-plus"></i> </button>'
														+ '<button type="button" title="编辑" class="btn btn-info btn-circle btn-xs" onclick="edit('
														+ li.accountId
														+ ')" style="margin-right:3px;"><i class="fa fa-edit"></i> </button>'
														+ '<button type="button" title="删除" class="btn btn-danger btn-circle btn-xs" onclick="del('
														+ li.accountId
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

		//跳转聊天记录
		function chat(nickname) {
			/* window.open("${ctx}/lazyearn/AutoChatHistory/jumpList"); */

			window.location.href = '${ctx}/lazyearn/AutoChatHistory/jumpList?nickname="'
					+ nickname + '"';

		}

		//单个删除方法
		function del(accountId) {
			layer.alert('确认删除？', {
				skin : 'layui-layer-lan',
				icon : 3,
				btn : [ '确认', '取消' ],
				closeBtn : 0
			//按钮
			}, function() {
				$.ajax({
					type : "POST",
					url : "${ctx}/lazyearn/AutoAccount/delete",
					data : {
						accountId : accountId,
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
					url : "${ctx}/lazyearn/AutoAccount/deleteAll",
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
 			var flag=true;
			var isValid = $("#accountform").valid();
			if (isValid) {
				layer.alert('确认保存？', {
					skin : 'layui-layer-lan',
					icon : 3,
					btn : [ '确认', '取消' ],
					closeBtn : 0
				//按钮
				}, function() {
			if(flag){
				flag=false;
			layer.close();
			  $(".btn-info").attr('disabled', true);

		    $.ajax({
        	  type: "POST",
        	  url: "${ctx}/lazyearn/AutoAccount/save",
              
              data: $('#accountform').serialize(), 
              dataType: "json",
              success: function(data){
            	 if(data > 0){
            		 layer.msg('保存成功', { icon : 1,time : 1000,closeBtn: 0 });
            		 dismiss();
            		 search();
            	 }else if(data==-1){
            		 layer.msg('保存失败！', { icon : 2 });
            	 }else if (data == -2) {
            		 layer.msg('该分控设备不存在！', {
							icon : 3
						});
				}
				$(".btn-info").attr('disabled', false);
				}
			});
			}
		});
	}
	
}
//点击编辑是跳转至表单提交页
function edit(accountId) {

	$.ajax({
		type : "POST",
		url : "${ctx}/lazyearn/AutoAccount/edit",
		data : {
			accountId : accountId
		},
		dataType : "json",
		success : function(data) {
			if (data != null) {
				$('#accountId').val(data.accountId);
				$('#account').val(data.account);
				$('#password').val(data.password);
				$('#devices').val(data.devices);
				$('#nickname').val(data.nickname);

			}
			$("#responsive-modal").modal("show");
		}
	});
}
//点击查看是跳转至表单提交页
function seek(accountId) {
	$.ajax({
		type : "POST",
		url : "${ctx}/lazyearn/AutoAccount/edit",
		data : {
			accountId : accountId
		},
		dataType : "json",
		success : function(data) {
			if (data != null) {
				$('#accountId').val(data.accountId);
				$('#account').val(data.account);
				$('#password').val(data.password);
				$('#devices').val(data.devices);
				$('#nickname').val(data.nickname);

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
		url : "${ctx}/lazyearn/AutoAccount/edit",
		data : {
			accountId : checkedId
		},
		dataType : "json",
		success : function(data) {
			if (data != null) {
				$('#accountId').val(data.accountId);
				$('#account').val(data.account);
				$('#password').val(data.password);
				$('#devices').val(data.devices);
				$('#nickname').val(data.nickname);
			}
			$("#responsive-modal").modal("show");
		}
	});
}
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
				$('#selectDevices').append(select);

			});
		}
	});
}
//查询当前所有子设备号
function currentNumDevices() {
	$
			.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/IntelligentManager/findChildNumDevices",
				dataType : "json",
				success : function(data) {
					$
							.each(
									data,
									function(i, li) {
										var select = '<option value="'+li.partdevices+'" style="color: #67757c;font-size: 14px;">'
												+ li.partdevices
												+ '</option>';
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
</script>
</body>

</html>