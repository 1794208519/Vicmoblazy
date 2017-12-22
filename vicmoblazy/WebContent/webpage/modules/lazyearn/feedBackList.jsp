<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
 <style type="text/css">
 .th1{
	width: 62px;
	max-width: 62px;
	min-width: 50px
	
}
.th2{
	width: 334px;
	max-width: 334px;
	min-width: 150px
	
}
.th3{
	width: 335px;
	max-width: 335px;
	min-width: 150px
	
}
.th4{
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
						class="hidden-xs-down">我的反馈</span>
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
								class="form-control height-control col-4" placeholder="请输入标题\内容搜索"
								id="searchFund" name="searchFund" value="" style="float: right;" />
				
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
								<th class="tableControl th2">客户账户</th>
								<th class="tableControl th3">主题</th>
								<th class="tableControl th4">内容</th>
								<th class="tableControl th5">操作</th>
							</tr>
						</thead>
						<tbody id="tbody">
						</tbody>
					</table>
					<!-- 底部分页按钮 -->
					<ul class="pagination pull-right" id="page"></ul>
				</div>
				
			</div>


		</div>
	</div>
	<form action="" method="post" id="jump" hidden="hidden">
		<input type="text" id="fundIdVal" name="feedbackId" />
		<!-- <input type="hidden" name="tab" id="tab" value=""> -->
	</form>
<script type="text/javascript">
		var idata = new Array();
		$(function() {
			
			search(-1);
			layer.load();//加载层

		});
		
		//enter搜索
		function keySearch() {
			if (event.keyCode == 13) { //回车键的键值为13  
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
						url : "${ctx}/lazyearn/feedback/findList",
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
												var trs = '<tr id="'+ li.feedbackId +'">'
														+ '<td class="tableControl th1"><input type="checkbox" name="subChk" class="check" id="square-checkbox-1" value="'+ li.feedbackId +'" data-checkbox="icheckbox_square-blue"></td>'
														+ '<td class="tableControl th2">'
														+ li.name
														+ '</td>'
														+ '<td class="tableControl th3">'
														+ li.title
														+ '</td>'
														+ '<td class="tableControl th4">'
														+ li.content
														+ '</td>'
														+ '<td class="tableControl th5">'
														+ '<button type="button" title="查看" class="btn btn-info btn-circle btn-xs" onclick="seek(\''
														+ li.feedbackId
														+ '\')" style="margin-right:3px;"><i class="fa fa-search-plus"></i> </button>'
														+ '<button type="button" title="编辑" class="btn btn-info btn-circle btn-xs" onclick="edit('
														+ li.feedbackId
														+ ')" style="margin-right:3px;"><i class="fa fa-edit"></i> </button>'
														+ '<button type="button" title="删除" class="btn btn-danger btn-circle btn-xs" onclick="del('
														+ li.feedbackId
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
			 $('#jump').attr("action", "${ctx}/lazyearn/feedback/jumpForm");	
			 $('#jump').submit();
		}

		//单个删除方法
		function del(feedbackId) {
			layer.alert('确认删除？', {
				skin : 'layui-layer-lan',
				icon : 3,
				btn : [ '确认', '取消' ],
				closeBtn : 0
			//按钮
			}, function() {
				$.ajax({
					type : "POST",
					url : "${ctx}/lazyearn/feedback/delete",
					data : {
						feedbackId : feedbackId,
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
					url : "${ctx}/lazyearn/feedback/deleteAll",
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
		
		//点击编辑是跳转至表单提交页
		function edit(feedbackId) {

			if(feedbackId != -1){
				$('#fundIdVal').val(feedbackId);
			}
			$('#jump').attr("action", "${ctx}/lazyearn/feedback/jumpForm");	
			$('#jump').submit();
		}
		//点击查看是跳转至表单提交页
		function seek(feedbackId) {
			if(feedbackId != -1){
				$('#fundIdVal').val(feedbackId);
			}
			$('#jump').attr("action", "${ctx}/lazyearn/feedback/jumpForm");	
			$('#jump').submit();
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
		
	</script>
</body>
</html>