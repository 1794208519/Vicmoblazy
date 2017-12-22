<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
<!-- summernotes CSS -->
<link href="${ctxStatic}/asset/plugins/summernote/dist/summernote.css"
	rel="stylesheet" />
<script
	src="${ctxStatic}/asset/plugins/summernote/dist/summernote.min.js"></script>
</head>
<body>
	<form class="form-horizontal" method="post"
		onkeydown="if(event.keyCode==13)return false;" onsubmit="return false"
		id="feedbackform" action="" style="margin-top: 25px">

		<div class="card">
			<input type="hidden" id="feedbackId" name="feedbackId" value="">
			<input type="hidden" id="content" name="content" value="">
			<div >
				<label class="col-sm-3 text-left control-label col-form-label">主题*</label>
				<div class="col-sm-12">
					<input autocomplete="off" type="text" class="form-control"
						name="title" id="title" placeholder="请输入反馈的主题" required
						data-validation-required-message="这是必填项" value="${feedBack.title}">
				</div>
			</div>
			<div >
				<label class="col-sm-3 text-left control-label col-form-label">内容*</label>
				<div style="padding-left: 15px;padding-right: 15px">
					<div class="summernote col-sm-12" >
						${feedBack.content}
					</div>
				</div>
			</div>
			<div class="modal-footer modal_top">
				<button type="button" class="btn btn-default waves-effect"
					data-dismiss="modal">关闭</button>
				<button type="submit"
					class="btn btn-danger waves-effect waves-light"
					style="margin-left: 10px" onclick="save()">保存</button>
			</div>
		</div>

	</form>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			
			$('.summernote').summernote({
				 height: 350, // set editor height
		            minHeight: null, // set minimum height of editor
		            maxHeight: null, // set maximum height of editor
		            focus: false // set focus to editable area after initializing summernote  
					});
			
		});
		//保存方法
		function save() {
			var flag = true;
				layer.alert('确认保存？',
						{ skin : 'layui-layer-lan',icon : 3,btn : [ '确认', '取消' ],closeBtn : 0},
						function() {
							if(flag){
								flag = false;
								var str=$('.note-editable').html().trim();
								$("#content").val(str);
								$(".btn-info").attr('disabled', true);
								$.ajax({
								type : "POST",
								url : "${ctx}/lazyearn/feedback/save",
								data : $('#feedbackform').serialize(),
								dataType : "json",
								success : function(data) {
								  if (data > 0) {
									layer.msg('保存成功', {icon : 1});
									$('#jump').attr("action","${ctx}/lazyearn/feedback/jumpList");
									$('#jump').submit();
								} else if (data == -1) {
									layer.msg('保存失败！', {icon : 2});
								}
								    $(".btn-info").attr('disabled', false);
								 }
							  });
							}
							
						});
		}
	</script>
</body>
</html>