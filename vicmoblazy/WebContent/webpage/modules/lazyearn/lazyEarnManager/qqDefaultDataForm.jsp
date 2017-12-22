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
					<h4 class="card-title" id="topTitle"></h4>
					<form class="form" method="post" onsubmit="return false" onkeydown="if(event.keyCode==13)return false;"
						id="qqMoreForm">
						<input type="hidden" name="tab" id="tab" value="${tab }">
						<input type="hidden" id="qqDefaultDataId" name="qqDefaultDataId"
							value="${autoQQDefaultData.qqDefaultDataId}">
						   <div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>设备号</label>
								<div class="col-9 controls">
								<select class="custom-select col-12" id="selectDevices">
									<option value="" selected>请选择设备号...</option>
								</select>
							</div>
							</div>
						<div id="qqForm">
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>QQ附近人打招呼人数</label>
								<div class="col-9 controls">
								<div>
								    <input type="text" name="qqNearbyNum" id="qqNearbyNum"
										class="form-control height-control" 
										value="${autoQQDefaultData.qqNearbyNum }">
								</div>
								<div class="form-control-feedback" style="color:#888;"><small>值为整数,1-80之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>QQ附近人打招呼内容</label>
								<div class="col-9 controls">
								<div>
								   <input type="text" name="qqNearbyContent" id="qqNearbyContent"
										class="form-control height-control" maxlength="50"
										value="${autoQQDefaultData.qqNearbyContent }">
								</div>
								   <div class="form-control-feedback" style="color:#888;"><small>1-50个字符</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>QQ加好友人数</label>
								<div class="col-9 controls">
								<div>
								   <input type="text" name="qqFriendNum" id="qqFriendNum"
										class="form-control height-control" 
										value="${autoQQDefaultData.qqFriendNum }">
								</div>
								   <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-80之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>QQ起始群</label>
								<div class="col-9 controls">
								<div>
								 <input type="text" name="qqGroupfriendNum"
										id="qqGroupfriendNum" class="form-control height-control"
										 value="${autoQQDefaultData.qqGroupfriendNum }">
								</div>
								  <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-10之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>QQ一键发消息内容</label>
								<div class="col-9 controls">
									<input type="text" name="qqAkeyContent" id="qqAkeyContent"
										class="form-control height-control" value="${autoQQDefaultData.qqAkeyContent }">
								</div>
							</div>
						</div>
						<input type="hidden" id="qqDevices" name="qqDevices" value="${autoQQDefaultData.devices }">
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
		if($("#qqDefaultDataId").val() == "" || $("#qqDefaultDataId").val() == null){
			$('#topTitle').html("添加QQ默认数据");
		} else {
			$('#topTitle').html("编辑QQ默认数据");
		}
	
		currentDevices();
		//表单验证
		$('#qqMoreForm').validate({
			 rules:{
				 qqNearbyNum:{
					 required:true,
					 checkDigits:true,
					 range:[1,80]
				 },
				 qqNearbyContent:{
					 required:true,
					 checkContext:true,
					 maxlength:50
				 },
				 qqFriendNum:{
					 required:true,
					 checkDigits:true,
					 range:[1,80]
				 },
				 qqGroupfriendNum:{
					 required:true,
					 checkDigits:true,
					 range:[1,10]
				 },
				 qqAkeyContent:{
					 required:true,
					 checkContext:true,
				 }
			 },
			 messages:{
				 qqNearbyNum:{
					 required:"附近人打招呼人数不能为空",
					 range: "请输入一个介于 1~80 之间的值"
				 },
				 qqNearbyContent:{
					 required:"附近人打招呼内容不能为空",
					 maxlength:"附近人打招呼内容不能超过50个字符"
				 },
				 qqFriendNum:{
					 required:"QQ加好友人数不能为空",
					 range: "请输入一个介于 1~80 之间的值"
				 },
				 qqGroupfriendNum:{
					 required:"QQ起始群不能为空",
					 range: "请输入一个介于 1~10 之间的值"
				 },
				 qqAkeyContent:{
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
	  function check(obj,size){	
		var value = $(obj).val();
		if(obj.value!=obj.value.replace(/\D/g,'')||value>size){
			layer.msg('只能输入数字且不能大于'+size, { time : 1500, icon : 0 });
	    	obj.value='';
		  }
        }
		//保存方法
		function save() {
			if($("#selectDevices").val() == 0||$("#qqDevices").val() == null || $("#qqDevices").val() == ""){
				layer.msg('请选择设备号！', { icon : 0 });
				return;
			}
			var isValid = $("#qqMoreForm").valid();
			var flag = true;
			if(isValid){
				layer.alert('确认保存？',
					{skin : 'layui-layer-lan',icon : 3,btn : [ '确认', '取消' ],closeBtn : 0},
					function() {
						if(flag){
							flag=false;
							$(".btn-info").attr('disabled', true);
							$.ajax({
							  type : "POST",
							  url : "${ctx}/lazyearn/AutoQQDefaultData/save",
							  data : $('#qqMoreForm')
							  .serialize(),
							  dataType : "json",
							  success : function(data) {
								if (data > 0) {
								layer.msg('保存成功', {
								icon : 1
								});
								$('#jump').attr("action","${ctx}/lazyearn/AutoQQDefaultData/jumpList");
								$('#jump').submit();
							  } else if (data == -1) {
								layer.msg('保存失败！', {
								icon : 2
							  });
							 }
							   $(".btn-info").attr('disabled',false);
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
	      	  url: "${ctx}/lazyearn/AutoQQDefaultData/findMainDevices",
	            data: {
	            	
	            },
	            dataType: "json",
	            success: function(data){
	          	    $.each(data,function(i, li) {
	          	    	var select = '<option value="'+(i+1)+'">'+ li.maindevices+'</option>';
	          	    	$('#selectDevices').append(select);
	          	    	var deviceName = $('#qqDevices').val();
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
			$('#qqDevices').val(checkText);
			var checkValue=$("#selectDevices").val();  //获取Select选择的Value
		}); 
		//取消返回界面
		function cancel() {
			layer.alert('确认放弃本次操作？',{ skin : 'layui-layer-lan',icon : 3,btn : [ '确认', '取消' ],closeBtn : 0},
					function() {
				$(".btn-inverse").attr('disabled', true);
				$('#jump').attr("action","${ctx}/lazyearn/AutoQQDefaultData/jumpList");
				$('#jump').submit();
					})
		      }
</script>
</body>
</html>