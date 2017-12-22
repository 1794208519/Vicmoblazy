<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
<style type="text/css">
.error{
   color:red;
   font-size:10px!important;
   margin-bottom:0rem;
}
</style>
</head>
<body>
	<div class="row" style="padding-top: 30px">
		<div class="col-sm-12">
			<div class="card">
				<div class="card-body">
					<h4 class="card-title" id="topTile"></h4>
					<form class="form" method="post" onsubmit="return false" onkeydown="if(event.keyCode==13)return false;"
						id="wxMoreForm" >
						<input type="hidden" name="tab" id="tab" value="${tab }">
						<input type="hidden" id="defaultDataId" name="defaultDataId"
							value="${autoDefaultData.defaultDataId}">
						<div class="form-group row">
							<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>设备号</label>
							<div class="col-9 controls">
								<select class="custom-select col-12" id="selectDevices">
									<option value="" selected>请选择设备号...</option>
								</select>
							</div>
						</div>
						<div id="wxForm">
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>附近人打招呼人数</label>
								<div class="col-9 controls">
								<div>
								   <input type="text" name="wxNearbyNum" id="wxNearbyNum"
										class="form-control height-control"
										value="${autoDefaultData.wxNearbyNum }">
								</div>
								   <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-80之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>附近人打招呼内容</label>
								<div class="col-9 controls">
								<div>
								    <input type="text" name="wxNearbyContent" id="wxNearbyContent"
										class="form-control height-control" maxlength="50"
										value="${autoDefaultData.wxNearbyContent }">
								</div>
									<div class="form-control-feedback" style="color:#888;"><small>1-50个字符</small></div>
								</div>
							</div>

							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>加好友人数</label>
								<div class="col-9 controls">
								<div>
								    <input type="text" name="wxFriendNum" id="wxFriendNum"
										class="form-control height-control"
										value="${autoDefaultData.wxFriendNum }">
								</div>
								    <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-80之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>起始群</label>
								<div class="col-9 controls">
								<div>
								   <input type="text" name="wxGroupfriendNum"
										id="wxGroupfriendNum" class="form-control height-control"
										value="${autoDefaultData.wxGroupfriendNum }">
								</div>
								   <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-10之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>公众号起始点</label>
								<div class="col-9 controls">
								<div>
								   <input type="text" name="wxPublicIndex" id="wxPublicIndex"
										class="form-control height-control"
										value="${autoDefaultData.wxPublicIndex }">
								</div>
								   <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-12之间</small></div>
								</div>
							</div>

							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>推送公众号个数</label>
								<div class="col-9 controls">
								<div>
								     <input type="text" name="wxPublicNum" id="wxPublicNum"
										class="form-control height-control"
										value="${autoDefaultData.wxPublicNum }">
								</div>
								     <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-12之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>推送好友数</label>
								<div class="col-9 controls">
								<div>
								    <input type="text" name="wxPublicfriendNum"
										id="wxPublicfriendNum" class="form-control height-control"
										value="${autoDefaultData.wxPublicfriendNum }">
								</div>
								    <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-200之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>漂流瓶内容</label>
								<div class="col-9 controls">
								<div>
								    <input type="text" name="wxDriftbottleContent"
										id="wxDriftbottleContent" class="form-control height-control"
										value="${autoDefaultData.wxDriftbottleContent }">
								</div>
								    <div class="form-control-feedback" style="color:#888;"><small>内容至少为5个字符</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>扔瓶子个数</label>
								<div class="col-9 controls">
								<div>
								    <input type="text" name="wxDriftbottleNum"
										id="wxDriftbottleNum" class="form-control height-control"
										value="${autoDefaultData.wxDriftbottleNum }">
								</div>
								   <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-20之间</small></div>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>朋友圈评论条数</label>
								<div class="col-9 controls">
								   <div>
							          <input type="text" name="wxCircleNum" id="wxCircleNum" 
										class="form-control height-control"
										value="${autoDefaultData.wxCircleNum }">	   
								   </div>
								      <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-60之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label">朋友圈评论内容</label>
								<div class="col-9 controls">
									<input type="text" name="wxCircleContent" id="wxCircleContent"
										class="form-control height-control" maxlength="9998"
										value="${autoDefaultData.wxCircleContent }">
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>一键发消息内容</label>
								<div class="col-9 controls">
									<input type="text" name="wxAkeyContent" id="wxAkeyContent"
										class="form-control height-control"
										value="${autoDefaultData.wxAkeyContent }">
								</div>
							</div>
						</div>
						<input type="hidden" id="wxDevice" name="wxDevice" value="${autoDefaultData.devices }">
						<div class="form-group row">
							<label class="col-2 col-form-label"></label>
							<div class="col-9">
								<div class="text-xs-right" style="text-align: right;">
									<button type="submit" class="btn btn-info" onclick="save()">保存</button>
									<button type="button" class="btn btn-inverse" onclick="cancel()">取消</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- 跳转隐藏form表单 -->
		<form action="" method="post" id="jump" hidden="hidden">
			<input type="text" id="codeIdVal" name="codeId" />
		</form>
	</div>
	<script type="text/javascript">
	$(function() {
		if($("#defaultDataId").val() == "" || $("#defaultDataId").val() == null){
			$('#topTile').html("添加微信默认数据");
		} else {
			$('#topTile').html("编辑微信默认数据");
		}
		currentDevices();
		//表单验证
		$('#wxMoreForm').validate({
			 rules:{
				 wxNearbyNum:{
					 required:true,
					 checkDigits:true,
					 range:[1,80]
				 },
				 wxNearbyContent:{
					 required:true,
					 checkContext:true,
					 maxlength:50
				 },
				 wxFriendNum:{
					 required:true,
					 checkDigits:true,
					 range:[1,80]
				 },
				 wxGroupfriendNum:{
					 required:true,
					 checkDigits:true,
					 range:[1,10]
				 },
				 wxPublicIndex:{
					 required:true,
					 checkDigits:true,
					 range:[1,12]
				 },
				 wxPublicNum:{
					 required:true,
					 checkDigits:true,
					 range:[1,12]
				 },
				 wxPublicfriendNum:{
					 required:true,
					 checkDigits:true,
					 range:[1,200]
				 },
				 wxDriftbottleContent:{
					 required:true,
					 checkContext:true,
					 minlength:5
				 },
				 wxDriftbottleNum:{
					 required:true,
					 checkDigits:true,
					 range:[1,20]
				 },
				 wxCircleNum:{
					 required:true,
					 checkDigits:true,
					 range:[1,60]
				 },
				 wxCircleContent:{
					 maxlength:9998
				 },
				 wxAkeyContent:{
					 required:true,
					 checkContext:true,
				 }
			 },
			 messages:{
				 wxNearbyNum:{
					 required:"附近人打招呼人数不能为空",
					 range: "请输入一个介于 1~80 之间的值"
				 },
				 wxNearbyContent:{
					 required:"附近人打招呼内容不能为空",
					 maxlength:"附近人打招呼内容不能超过50个字符"
				 },
				 wxFriendNum:{
					 required:"加好友人数不能为空",
					 range: "请输入一个介于 1~80 之间的值"
				 },
				 wxGroupfriendNum:{
					 required:"起始群不能为空",
					 range: "请输入一个介于 1~10 之间的值"
				 },
                 wxPublicIndex:{
                	 required:"公众号起始点不能为空",
					 range: "请输入一个介于 1~12 之间的值"
				 },
				 wxPublicNum:{
					 required:"推送公众号个数不能为空",
					 range: "请输入一个介于 1~12 之间的值"
				 },
				 wxPublicfriendNum:{
					 required:"推送好友数不能为空",
					 range: "请输入一个介于 1~200 之间的值"
				 },
				 wxDriftbottleContent:{
					 required:"漂流瓶内容不能为空",
					 minlength:"漂流瓶内容至少为5个字符"
				 },
				 wxDriftbottleNum:{
					 required:"扔瓶子个数不能为空",
					 range: "请输入一个介于 1~20 之间的值"
				 },
				 wxCircleNum:{
					 required:"朋友圈人数不能为空",
					 range: "请输入一个介于 1~60 之间的值"
				 },
				 wxCircleContent:{
					 maxlength:"朋友圈评论内容不能超过9998个字符"
				 },
				 wxAkeyContent:{
					 required:"一键发消息不能为空"
				 }
			 },
			     
			 errorPlacement : function(error, element) {
				 error.appendTo(element.parent())
			},
			 //是否在获取焦点时验证  
		        //onfocusout:false,  
		        //是否在敲击键盘时验证  
		        //onkeyup:false,  
		        //提交表单后，（第一个）未通过验证的表单获得焦点  
		        focusInvalid:false,  
		        //当未通过验证的元素获得焦点时，移除错误提示  
		        focusCleanup:true, 
		 });
		//自定义正则表达示验证方法  
	    $.validator.addMethod("checkDigits",function(value,element,params){  
	            var checkDigits =/^([1-9]\d*|0)(\.\d*[1-9])?$/;   
	            return this.optional(element)||(checkDigits.test(value));  
	        },"请输入不以0开头的整数"); 
		
	    $.validator.addMethod("checkContext",function(value,element){  
            return this.optional(element)||("null"!=value.trim());  
        },"请输入不为null的字符串"); 
	});
		//判断输入框数字大小
		function check(obj, size) {
			var value = $(obj).val();
			if (obj.value != obj.value.replace(/\D/g, '') || value > size) {
				layer.msg('只能输入整数且不能大于'+size, { time : 1500, icon : 0 });
				obj.value = '';
			}else if(value==0){
				layer.msg('内容不能为0或空', { time : 1500, icon : 0 });
				obj.value = '';
			}
		}
		//判断输入框文字长度
		function checkTextLen(obj,len) {
			var value = $(obj).val().replace(/(^\s*)|(\s*$)/g,"").length;
			if (value >=len) {
				layer.msg('内容不能大于' + len+"个字符", { time : 1500, icon : 0 });
			}
			
		}
		//保存方法
		function save() {
			if($("#selectDevices").val() == 0||$("#wxDevice").val() == null || $("#wxDevice").val() == ""){
				layer.msg('请选择设备号！', { icon : 0 });
				return;
			}
			var isValid = $("#wxMoreForm").valid();
			var flag = true;
			if(isValid){
				layer.alert('确认保存？',
						{ skin : 'layui-layer-lan',icon : 3,btn : [ '确认', '取消' ],closeBtn : 0},
						function() {
							if(flag){
								flag = false;
								$(".btn-info").attr('disabled', true);
								$.ajax({
								type : "POST",
								url : "${ctx}/lazyearn/AutoWXDefaultData/save",
								data : $('#wxMoreForm').serialize(),
								dataType : "json",
								success : function(data) {
								  if (data > 0) {
									layer.msg('保存成功', {icon : 1});
									$('#jump').attr("action","${ctx}/lazyearn/AutoWXDefaultData/jumpList");
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
			
		}
		
		//查询当前所有主设备号
		function currentDevices(){
		    $.ajax({
	      	  type: "POST",
	      	  url: "${ctx}/lazyearn/AutoWXDefaultData/findMainDevices",
	            data: {
	            	
	            },
	            dataType: "json",
	            success: function(data){
	          	    $.each(data,function(i, li) {
	          	    	var select = '<option value="'+(i+1)+'">'+ li.maindevices+'</option>';
	          	    	$('#selectDevices').append(select);
	          	    	var deviceName = $('#wxDevice').val();
	          	    	if(deviceName == null || deviceName == ""){
	            	    	
	            	    }else{
	            	    	if(li.maindevices == deviceName){
	            	    		$("#selectDevices").val(i+1);
	            	    	}
	            	    } 
			        });  
	           }
	  	    }); 
		}
		//获取select选中的文本和value
		$("#selectDevices").change(function(){
			var checkText=$("#selectDevices").find("option:selected").text();//获取Select选择的Text
			$('#wxDevice').val(checkText);
			var checkValue=$("#selectDevices").val();  //获取Select选择的Value
		}); 
		//取消返回界面
		function cancel() {
			layer.alert('确认放弃本次操作？',{ skin : 'layui-layer-lan',icon : 3,btn : [ '确认', '取消' ],closeBtn : 0},
					function() {
				$(".btn-inverse").attr('disabled', true);
				$('#jump').attr("action","${ctx}/lazyearn/AutoWXDefaultData/jumpList");
				$('#jump').submit();
					})
		      }
	</script>
</body>
</html>