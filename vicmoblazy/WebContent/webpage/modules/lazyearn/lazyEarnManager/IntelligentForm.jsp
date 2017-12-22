<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
<style type="text/css">
.addRes{
   margin-top:14px;
   font-size:14px;
}
.addRes img{
   margin-top:-2px;
}
.delete{

}
/* input.error{
border: 1px solid red; 
} */
.error{
   color:red;
   font-size:10px!important;
   margin-bottom:0rem;
}
input{
   font-size:13px!important;
}
</style>
</head>
<body style="line-height: 1.3;">
	<div class="row" style="padding-top: 30px">
		<div class="col-sm-12">
			<div class="card">
				<div class="card-body">
					<h4 class="card-title" id="addIntelligent"></h4>
					<form class="form" method="post" onsubmit="return false"
						id="codeTextForm">
						<input type="hidden" name="codeTextId" id="codeTextId" class="form-control" value="${vicCodeText.codeTextId }">
						<div class="form-group row">
							<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>关键词</label>
							<div class="col-9 controls">
							  <div>
								  <input type="text" name="keyword"
									  id="keyword" class="form-control" maxlength="20"
									  value="${vicCodeText.keyword }" onkeyup="checkLength(this,20)">
							  </div>   
							  <div class="form-control-feedback" style="color:#888;"><small>2-20个字符，不全为数字，建议以中文为主</small></div>
							</div>
						</div>
						<div class="form-group row">
							<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>回复内容</label>
							<div class="col-9 controls">
							   <div id="responseContent"></div>
							   <a href="javascript:;" class="addRes" onclick="addMore()">
							       <img alt="" src="${ctxStatic}/asset/images/addInteligent.png">添加回复
							   </a>
							</div>
						</div>
						<div class="form-group row">
							<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>设备号</label>
							<div class="col-9 controls">
								<select class="custom-select col-12" id="selectDevices" name="selectDevices">
									<option value="0" selected>请选择设备号...</option>
								</select>
							</div>
						</div>
						<div class="form-group row">
							<label for="example-text-input" class="col-2 col-form-label">文本类型(默认)</label>
							<div class="col-9 controls">
							    <div>
								    <input autocomplete="off" disabled="disabled" type="text" name="type1"
									    id="type1" class="form-control height-control"
									    value="text">
								</div>
							    <div class="form-control-feedback" style="color:#888;"><small>默认text</small></div>
							</div>
						</div>
						<div class="form-group row">
							<label for="example-text-input" class="col-2 col-form-label">是否开启数据共享</label>
							<div class="col-9 controls switchery-demo m-b-30">
								<input type="checkbox" class="js-switch" data-color="#ffbc34" ${vicCodeText.dataShareFlag ? "checked" : ""}/>
							</div>
						</div>
						<div class="form-group row">
							<label for="example-text-input" class="col-2 col-form-label">上传图片</label>
							<div class="col-9 controls">
								<div id="" class="input-group">
						              <input type="text" id="thumb" name="thumb" class="form-control"
					                  value="" readonly="readonly">
					                  <a href="javascript:;"><span class="input-group-addon" style="border:1px solid #ffbc34;background-color:#ffbc34;color:#fff;" onclick="lopen('upImg','thumb',5)">上传图片</span></a>
					            </div>
					            <div class="form-control-feedback" style="color:#888;"><small>每张图片建议尺寸750*280，大小在1M以内；最多上传5张图片</small></div>
							</div>
						</div>
						<input type="hidden" id="switchCheck" name="switchCheck" value="0">
						<input type="hidden" id="type" name="type" value="">
						<input type="hidden" id="selectedDevice" name="selectedDevice" value="${vicCodeText.devices }">
						<input type="hidden" id="textIds" name="textIds" value="">
						<input type="hidden" id="codeTextIds" name="codeTextIds" value="">
						<input type="hidden" id="codeTextDelIds" name="codeTextDelIds" value="">
						<div class="form-group row">
							<label class="col-2 col-form-label"></label>
							<div class="col-9">
								<div class="text-xs-right" style="text-align: right;">
									<button type="submit" class="btn btn-info" onclick="save()" id="saveForm">保存</button>
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
	<!-- 图片相关隐藏域 -->
	<%@ include file="/webpage/modules/lazyearn/include/photo.jsp"%>
	
	<script type="text/javascript">
		if (navigator.appName == 'Microsoft Internet Explorer') {
			if (navigator.userAgent.indexOf("MSIE 5.0") > 0
					|| navigator.userAgent.indexOf("MSIE 6.0") > 0
					|| navigator.userAgent.indexOf("MSIE 7.0") > 0) {
				alert('您使用的 IE 浏览器版本过低, 推荐使用 Chrome 浏览器或 IE8 及以上版本浏览器.');
			}
		}
	</script>
	<!-- 下载 -->
	<script src="${ctxStatic}/vicmob/js/downloadZip.js"></script>

	<script src="${ctxStatic }/photo/js/uploadConfig.js"></script>
	
	<script type="text/javascript">
	var responseNum = 0;//回复内容计数
	var texts = new Array();//保存的回复内容集合
	var ids = new Array();//保存的id集合
    $(function(){ 
		//改变title文字
		if($("#codeTextId").val() == "" || $("#codeTextId").val() == null){
			$('#addIntelligent').html("添加智能回复");
			showDefault();
		} else {
			$('#addIntelligent').html("编辑智能回复");
			currentIntelligent();
		}
		currentDevices();
        // Switchery初始化
        var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
        $('.js-switch').each(function() {
            new Switchery($(this)[0], $(this).data());
        });
        //监听switchbox改变
        $('.js-switch').change(function() {
        	if($('.js-switch').is(":checked")){
        		$('#switchCheck').val('1');
        		layer.msg('开启！', { icon : 0 });
        	}else{
        		$('#switchCheck').val('0');
        		layer.msg('关闭！', { icon : 0 });
        	}
        });
        //form表单验证
        $('#codeTextForm').validate({
        	rules:{
        		keyword:{
        			required:true,
        			minlength:2,
        			maxlength:20,
        			checkNum:true,
        		},
        		text0:{
        			required:true,
        			maxlength:500,
        		},
        		text1:{
        			required:true,
        			maxlength:500,
        		},
        		text2:{
        			required:true,
        			maxlength:500,
        		},
        		text3:{
        			required:true,
        			maxlength:500,
        		},
        		text4:{
        			required:true,
        			maxlength:500,
        		},
        	},
        	messages:{
        		keyword:{
        			required: "关键词不能为空！",  
        			minlength: "关键词至少为2位字符",
					maxlength: "关键词最多为20位字符", 
        		},
        		text0:{
        			required: "回复内容不能为空！",
        			maxlength: "回复内容最多为500位字符！"
        		},
        		text1:{
        			required: "回复内容不能为空！",
        			maxlength: "回复内容最多为500位字符！"
        		},
        		text2:{
        			required: "回复内容不能为空！",
        			maxlength: "回复内容最多为500位字符！"
        		},
        		text3:{
        			required: "回复内容不能为空！",
        			maxlength: "回复内容最多为500位字符！"
        		},
        		text4:{
        			required: "回复内容不能为空！",
        			maxlength: "回复内容最多为500位字符！"
        		},
        	},
        	errorPlacement : function(error, element) {
        		//error是错误提示元素span对象  element是触发错误的input对象
        		//element是验证未通过的当前表单元素,error为错误后的提示信息 
        		if(element.attr("name")=="text0" || element.attr("name")=="text1" || element.attr("name")=="text2" || element.attr("name")=="text3" || element.attr("name")=="text4"){
        			error.appendTo(element.parent().parent()); 
        		}else{
        			error.appendTo(element.parent()); 
        		}
        	},
            //是否在获取焦点时验证  
            //onfocusout:false,  
            //是否在敲击键盘时验证  
            //onkeyup:false,  
            //提交表单后，（第一个）未通过验证的表单获得焦点 ,true:获取焦点;false:不获取焦点; 
            focusInvalid:false,  
            //当未通过验证的元素获得焦点时，移除错误提示  
            focusCleanup:true, 
        });
        //自定义正则表达示验证方法 
        $.validator.addMethod("checkQQ",function(value,element,params){
        	var checkQQ = /^[1-9][0-9]{4,19}$/;  
            return this.optional(element)||(checkQQ.test(value));         	
        },"请输入正确的QQ号码！");
        $.validator.addMethod("checkNum",function(value,element,params){
        	var checkNum = /([\u4e00-\u9fa5a-zA-Z]+[0-9]*)/;//能输入中文、 英文、 数字 但不能是纯数字
        	return this.optional(element)||(checkNum.test(value));
        },"请输入正确的关键词，不能全为数字哦！");
    });
	
	//默认回复内容为1条
	function showDefault(){
		 if(responseNum == 0){
		    var html = '<div id="option_parent'+ responseNum +'">'
		             + '<div id="option'+ responseNum +'" class="input-group">'
		             + '<input type="hidden" name="codeTextId'+ responseNum +'" id="codeTextId'+ responseNum +'" class="form-control" value="">'
		    		 + '<input type="text" name="text'+ responseNum +'" id="text'+ responseNum +'" class="form-control Intelligent"'
		    	     + 'maxlength="500" onkeyup="checkLength(this,500)" value=""></div>'
		    	     + '</div>';
			$('#responseContent').append(html);
			responseNum++;
		 }
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
          	    	var select = '<option value="'+(i+1)+'">'+ li.maindevices+'</option>';
          	    	$('#selectDevices').append(select);
          	    	var deviceName = $('#selectedDevice').val();
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
		$('#selectedDevice').val(checkText);
		var checkValue=$("#selectDevices").val();  //获取Select选择的Value
	}); 
	//查询当前关键词对应的设备号下的回复内容
	function currentIntelligent(){
		var keyword = $('#keyword').val();
		var devices = $('#selectedDevice').val();
		if(keyword != ""  && keyword != null){
		    $.ajax({
	        	  type: "POST",
	        	  url: "${ctx}/lazyearn/IntelligentManager/findIntelligentResponse",
	              data: $('#codeTextForm').serialize(),
	              dataType: "json",
	              success: function(data){
	            	    if(data.length>1){
	            	    	$.each(data,function(i, li) {
	            	    		if(i == 0){
				            		var str = '<div id="option_parent'+ i +'">'
				            		        + '<div id="option'+ i +'" class="input-group">'
		            		                + '<input type="hidden" name="codeTextId'+ i +'" id="codeTextId'+ i +'" class="form-control" value="'+ li.codeTextId +'">'
						                    + '<input type="text" name="text'+ i +'" id="text'+ i +'" class="form-control Intelligent"'
					                        + 'maxlength="500" onkeyup="checkLength(this,500)" value="'+ li.text +'">'
					                        + '<a href="javascript:;"><span class="input-group-addon mdi mdi-delete-circle delete" onclick="delOption1('+ i +')"></span></a></div>'
					                        + '</div>';
	            	    		}else{
	            	    			var str = '<div id="option_parent'+ i +'">'
	            	    				    + '<div id="option'+ i +'" class="input-group" style="margin-top:10px;">'
		            		                + '<input type="hidden" name="codeTextId'+ i +'" id="codeTextId'+ i +'" class="form-control" value="'+ li.codeTextId +'">'
						                    + '<input type="text" name="text'+ i +'" id="text'+ i +'" class="form-control Intelligent"'
					                        + 'maxlength="500" onkeyup="checkLength(this,500)" value="'+ li.text +'">'
					                        + '<a href="javascript:;"><span class="input-group-addon mdi mdi-delete-circle delete" onclick="delOption1('+ i +')"></span></a></div>'
					                        + '</div>';
	            	    		}
								$('#responseContent').append(str);
								responseNum++;
			            	});
	            	    }else{
	            	    	$.each(data,function(i, li) {
			            		var str = '<div id="option_parent'+ i +'">'
			            		        + '<div id="option'+ i +'" class="input-group">'
			            		        + '<input type="hidden" name="codeTextId'+ i +'" id="codeTextId'+ i +'" class="form-control" value="'+ li.codeTextId +'">'
							            + '<input type="text" name="text'+ i +'" id="text'+ i +'" class="form-control Intelligent"'
						                + 'maxlength="500" onkeyup="checkLength(this,500)" value="'+ li.text +'"></div>'
						                + '</div>';
								$('#responseContent').append(str);
								responseNum++;
			            	});
	            	    }
	             }
	    	 }); 
		}
	}
	
	//通过键盘输入事件，实时监听输入框长度
	function checkLength(obj,length){
		var value = $(obj).val().length;
		if(value>=length){
			layer.msg('最多'+length+'个字符哦！', {time : 1500,icon : 0,anim: 6});
		}
	}
	
	//保存方法
	function save(){
		getText();
		for (var i= 0 ;i < responseNum;i++) {
			var textId = "text"+i;
			if($('#'+textId).val().trim() == null || $('#'+textId).val().trim() == ""){//jquery在验证的时候会自动去空格，空字符串是不会影响的
				return;
			}
		}
		if($("#keyword").val().trim()== null || $("#keyword").val().trim()==""){	   
			return;
		}else if($("#keyword").val().length<2){
			return;
		}
		if($("#selectDevices").val() == 0 || $("#selectDevices").val() == "0"){
			layer.msg('请选择设备号！', { time : 3000,icon : 0 ,anim: 6});
			return;
		}
		var isValid = $("#codeTextForm").valid();
		var flag = true;
		if(isValid){
			layer.confirm('确认保存？', {
				skin: 'layui-layer-lan',icon:3,btn: ['确认','取消'] ,closeBtn:0 //按钮
				},function(){
					if(flag){
						flag = false;
						$(".btn-info").attr('disabled', true);
					    $.ajax({
			        	  type: "POST",
			        	  url: "${ctx}/lazyearn/IntelligentManager/save",
			              data: $('#codeTextForm').serialize(),
			              dataType: "json",
			              success: function(data){
			            	 if(data > 0){
			            		 layer.msg('保存成功', { icon : 1});
			            		 $('#jump').attr("action", "${ctx}/lazyearn/IntelligentManager/jumpList");	
			     				 $('#jump').submit();
			            	 }else if(data==-1){
			            		 layer.msg('保存失败！', { icon : 2,anim: 6 });
			            	 }else if(data==-2){
			            		 layer.alert('当前关键词对应的回复内容已超过上限（5条）!请重新添加!', {
			       				  skin: 'layui-layer-lan',icon:0,anim: 6});
			            	 }
			            	 $(".btn-info").attr('disabled', false);
			               }
			            }); 
					 }
			});
		}
	}
	//取消,返回列表页面
	function cancel(){
		layer.confirm('确认放弃本次操作？', { icon : 3 ,closeBtn : 0,
			 skin : 'layui-layer-lan',btn: ['确认','取消']},function(){
				 window.location.href = "${ctx}/lazyearn/IntelligentManager/jumpList";
			 });
	}
	//获取回复内容具体信息（用于保存）
	function getText(){
		texts.length = 0;//先清空
		ids.length = 0;
		
		//循环页面，将回复内容具体信息保存在数组中
		for (var i= 0 ;i < responseNum;i++) {
			var textId = "text"+i;
			var id = "codeTextId"+i;
			if($('#'+textId).val() == "" || $('#'+textId).val() == null || $('#'+textId).val() == "null"){
				
			}else{
				texts.push($('#'+textId).val());
				ids.push($('#'+id).val());
			}
		}
		$("#textIds").val(texts.toString());
		$("#codeTextIds").val(ids.toString());
		$("#type").val('text');
	}
	//动态加载更多
	function addMore(){
		if(responseNum <5){
			var html = '<div id="option_parent'+ responseNum +'">'
			         + '<div id="option'+ responseNum +'" class="input-group" style="margin-top:10px;">'
			         + '<input type="hidden" name="codeTextId'+ responseNum +'" id="codeTextId'+ responseNum +'" class="form-control" value="">'
				     + '<input type="text" name="text'+ responseNum +'" id="text'+ responseNum +'" class="form-control Intelligent"'
			         + 'maxlength="500" onkeyup="checkLength(this,500)" value="">'
			         + '<a href="javascript:;"><span class="input-group-addon mdi mdi-delete-circle delete" onclick="delOption('+ responseNum +')"></span></a></div>'
			         + '</div>';
		    $('#responseContent').append(html);
		    responseNum++;
		}else{
			layer.msg('最多添加5条！', {time : 2500,icon : 0,anim: 6});
		}
	
	}
	//删除回复输入框
	function delOption(index){
		if(index == responseNum-1){//若删除的是最后一个,则直接删除
			$('#option_parent'+index).remove();
		} else {
			var optionNum = responseNum-1;//若删除的是中间的某一个，则把后面的都移到前面，并删除最后一个
			for (var i= index ;i < optionNum;i++) {
				var j = i + 1;
				$('#text'+i).val($('#text'+j).val());
			}
			$('#option_parent'+optionNum).remove();
		}
		layer.msg("删除", {time : 1500,icon : 0});
		responseNum -- ;
	}
	//删除回复内容
	function delOption1(index){
		var objId = "codeTextId" + index;
		var delId = $('#'+objId).val();
		if(delId == null || delId == ""){//如果删除id为空，说明是新增还未保存的数据，直接执行删除输入框
			delOption(index);
			return;
		}
		if(index == responseNum-1){//若删除的是最后一个,则直接删除
			layer.alert('确认删除？', {
				  skin: 'layui-layer-lan',icon:3,btn: ['确认','取消'] ,closeBtn:0 //按钮
				}, function(){
					$.ajax({
						type : "POST",
						url : "${ctx}/lazyearn/IntelligentManager/delete",
						data : {
							codeTextId : delId,
						},
						dataType : "json",
						success : function(result) {
							if(result > 0){
								$('#option_parent'+index).remove();
								layer.msg('删除成功！', { time : 1000, icon : 1 });
								responseNum -- ;
							}else{
								layer.msg('刪除失败！', { icon : 2 ,anim: 6});	
							}
						}
					});
			});
		} else {
			layer.msg("删除"+index+"成功", {time : 1500,icon : 0});
			var optionNum = responseNum-1;//若删除的是中间的某一个，则把后面的都移到前面，并删除最后一个
			layer.alert('确认删除？', {
				  skin: 'layui-layer-lan',icon:3,btn: ['确认','取消'] ,closeBtn:0 //按钮
				}, function(){
					$.ajax({
						type : "POST",
						url : "${ctx}/lazyearn/IntelligentManager/delete",
						data : {
							codeTextId : delId,
						},
						dataType : "json",
						success : function(result) {
							if(result > 0){
								for (var i= index ;i < optionNum;i++) {
									var j = i + 1;
									$('#codeTextId'+i).val($('#codeTextId'+j).val());
									$('#text'+i).val($('#text'+j).val());
								}
								$('#option_parent'+optionNum).remove();
								layer.msg('删除成功！', { time : 1000, icon : 1 });
								responseNum -- ;
							}else{
								layer.msg('刪除失败！', { icon : 2 });	
							}
						}
					});
			});
		}	
	}
	
	</script>
</body>
</html>