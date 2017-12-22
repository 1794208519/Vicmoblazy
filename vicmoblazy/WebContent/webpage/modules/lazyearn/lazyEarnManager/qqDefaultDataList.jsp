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
        <div class="card" style="margin-bottom: -30px">
				<div class="tab-pane p-20 active" id="profile2" role="tabpanel"
					aria-expanded="true">
					<!-- QQ单条 -->
					<div id="qqSingleList" hidden="hidden">
						<form class="form" method="post" onsubmit="return false" onkeydown="if(event.keyCode==13)return false;"
							id="qqSingleForm">
							<input type="hidden" id="qqDefaultDataId" name="qqDefaultDataId" />
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>设备号</label>
								<div class="col-9 controls">
									<select class="custom-select col-12 qq-info" id="selectDevices">
									<option value="" selected>请选择设备号...</option>
								</select>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>QQ附近人打招呼人数</label>
								<div class="col-9 controls">
								<div>
								    <input type="text" name="qqNearbyNum" id="qqNearbyNum"
										class="form-control height-control qq-info" 
										value="">
								</div>
								<div class="form-control-feedback" style="color:#888;"><small>值为整数,1-80之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>QQ附近人打招呼内容</label>
								<div class="col-9 controls">
								<div>
								   <input type="text" name="qqNearbyContent" id="qqNearbyContent"
										class="form-control height-control qq-info" maxlength="50"
										value="">
								</div>
								   <div class="form-control-feedback" style="color:#888;"><small>1-50个字符</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>QQ加好友人数</label>
								<div class="col-9 controls">
								<div>
								   <input type="text" name="qqFriendNum" id="qqFriendNum"
										class="form-control height-control qq-info" 
										value="">
								</div>
								   <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-80之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>QQ起始群</label>
								<div class="col-9 controls">
								<div>
								 <input type="text" name="qqGroupfriendNum"
										id="qqGroupfriendNum" class="form-control height-control qq-info"
										 value="">
								</div>
								  <div class="form-control-feedback" style="color:#888;"><small>值为整数,1-10之间</small></div>
								</div>
							</div>
							<div class="form-group row">
								<label for="example-text-input" class="col-2 col-form-label"><span class="text-danger">*&nbsp;</span>QQ一键发消息内容</label>
								<div class="col-9 controls">
									<input type="text" name="qqAkeyContent" id="qqAkeyContent"
										class="form-control height-control qq-info" value="">
								</div>
							</div>
							<input type="hidden" id="qqDevices" name="qqDevices" value="">
							<div class="form-group row">
								<label class="col-2 col-form-label"></label>
								<div class="col-9">
									<div class="text-xs-right" style="text-align: right;">
									    <button hidden="hidden" id="qqSingleAdd" type="button" class="btn btn-info" onclick="add(0)">添加</button>
										<button hidden="hidden" id="qqSingleEdit" type="button" class="btn btn-info"
											onclick="qqEdit()">编辑</button>
										<button hidden="hidden" id="qqSingleSave" type="submit" class="btn btn-info"
											onclick="qqSave()">保存</button>
										<button hidden="hidden" id="cancel" type="button" class="btn btn-inverse" onclick="qqSingleCancel()">取消</button>
									</div>
								</div>
							</div>
						</form>
					</div>
					<!-- QQ多条 -->
					<div id="qqMoreList" hidden="hidden">
						<div class="row button-group col-9" style="float: left;margin-bottom:8px;">
				 		<button type="button" class="btn btn-info" onclick="add()"><i class="fa fa-plus-circle"></i> 添加</button>
				 		<button type="button" class="btn btn-info" onclick="edit1()"><i class="fa fa fa-edit"></i> 编辑</button>
				 		<button type="button" class="btn btn-danger" onclick="deleteAll()"><i class="fa fa fa-times"></i> 删除</button>
				 	</div>
				 	<div class="row col-3" style="float: right;">
						<form action="" method="post" style="width: 100%" id="qqSearchForm">
							<input type="hidden"  id="qqDefaultDataId1" name="qqDefaultDataId1">
					        <input id="pageNo" name="pageNo" type="hidden" value="1" /> 
					        <input id="pageSize" name="pageSize" type="hidden" value="10" />  
							<button hidden="hidden" type="button" class="btn waves-effect waves-light btn-info" onclick="qqSearch(0)" style="float: right; margin-left: 15px">搜索</button>
							<input hidden="hidden" autocomplete="off" type="text" class="form-control height-control col-4" placeholder="请输入关键字" id="qqSearchFund" name="qqSearchFund" value="" style="float: right;"/>
							<select class="custom-select col-2" id="qqMainDevices" name="qqMainDevices" style="float: right;">
									<option value="" selected>请选择设备号...</option>
							</select>
						</form>
					</div>
						<table id="example23"
							class="display nowrap table table-hover table-striped table-bordered"
							cellspacing="0" width="100%">
							<thead>
								<tr>
									<th class="tableControl"><input type="checkbox"
										class="check" id="ischange"
										data-checkbox="icheckbox_square-blue"></th>
									<th class="tableControl">设备号</th>
									<th class="tableControl">QQ附近人打招呼人数</th>
									<th class="tableControl">QQ附近人打招呼内容</th>
									<th class="tableControl">QQ加好友人数</th>
									<th class="tableControl">QQ起始群</th>
									<th class="tableControl">QQ一键发消息内容</th>
									<th class="tableControl">操作</th>
								</tr>
							</thead>
							<tbody id="tqqbody">
							</tbody>
						</table>
						<!-- 底部分页按钮 -->
						<ul class="pagination pull-right" id="page"></ul>
					</div>
				</div>
			</div>
	<!-- 跳转隐藏form表单 -->
	<form action="" method="post" id="jump" hidden="hidden">
		<input type="text" id="fundIdVal" name="codeId" />
		<!-- <input type="hidden" name="tab" id="tab" value=""> -->
	</form>
	<script type="text/javascript">
		$(function() {
			//先调下数据(默认为qq第一页开始，每页十个)
			qqSearch(-1);
			layer.load();//加载层
			currentDevices();
			//表单验证
			$('#qqSingleForm').validate({
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
		//qq查询数据
		function qqSearch(type) {
			if(type == 0){
				$.cookie('searchWord', $('#qqSearchFund').val());	
			}else if(type == -1){
				$.cookie('searchWord', "");
			}
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/AutoQQDefaultData/findList",
				data: $('#qqSearchForm').serialize(),
				dataType : "json",
				success : function(result) {
					layer.closeAll('loading');//去除加载层
					//先清空
					$("#tqqbody").empty();
					if(result.list.length <= 1&&type == -1){
							//只有一条数据时，显示form表单，隐藏多条表，增删改查按钮，搜索框
							$('#qqSingleList').removeAttr("hidden");
						    $('#qqMoreList').prop("hidden",true);
						    
							if(result.list.length==0){
								$('#qqSingleSave').removeAttr("hidden");
								$('#cancel').removeAttr("hidden");
								$('#qqSingleAdd').prop("hidden",true);
								$('#qqSingleEdit').prop("hidden",true);
								
								$('#selectDevices').val('');
								$('#qqNearbyNum').val('');
								$('#qqNearbyContent').val('');
								$('#qqFriendNum').val('');
								$('#qqGroupfriendNum').val('');
								$('#qqAkeyContent').val('');
							}else{
								$('#qqSingleAdd').removeAttr("hidden");
								$('#qqSingleEdit').removeAttr("hidden");
								$('#qqSingleSave').prop("hidden",true);
								$('#cancel').prop("hidden",true);
							if(result.list[0].qqDefaultDataId != -1){
								$('#qqDefaultDataId').val(result.list[0].qqDefaultDataId);
							}
							$.ajax({
								type : "POST",
								url : "${ctx}/lazyearn/AutoQQDefaultData/singleForm",
								data : $('#qqSingleForm').serialize(),
								dataType : "json",
								success : function(result) {
									if(result != null){
										$('#qqDevices').val(result.devices);
										$('#selectDevices').val(result.devices);
										$('#qqNearbyNum').val(result.qqNearbyNum);
										$('#qqNearbyContent').val(result.qqNearbyContent);
										$('#qqFriendNum').val(result.qqFriendNum);
										$('#qqGroupfriendNum').val(result.qqGroupfriendNum);
										$('#qqAkeyContent').val(result.qqAkeyContent);
										$(".qq-info").attr('disabled', true);
									}
								}
							});
						  }
							
						}else{
							$('#qqMoreList').removeAttr("hidden");
							$('#qqSingleList').prop("hidden",true);
							//再添加
							$.each(result.list, function(i, li) {
								var qqNearbyContent=li.qqNearbyContent;
								var qqAkeyContent=li.qqAkeyContent;
								
								if(qqNearbyContent!=null&&qqNearbyContent.length>10){
									qqNearbyContent=li.qqNearbyContent.substr(0,10)+"...";
								}
								if(qqAkeyContent!=null&&qqAkeyContent.length>10){
									qqAkeyContent=li.qqAkeyContent.substr(0,10)+"...";
								}
							
								var trs2 = '<tr id="'+ li.qqDefaultDataId +'">'
						        + '<td class="tableControl"><input type="checkbox" name="subChk" class="check" id="square-checkbox-1" value="'+ li.qqDefaultDataId +'" data-checkbox="icheckbox_square-blue"></td>'
						        + '<td class="tableControl">'+ li.devices +'</td>'
						        + '<td class="tableControl">'+ li.qqNearbyNum +'</td>'
								+ '<td class="tableControl">'+ qqNearbyContent +'</td>'
								+ '<td class="tableControl">'+ li.qqFriendNum +'</td>'
								+ '<td class="tableControl">'+ li.qqGroupfriendNum +'</td>'
								+ '<td class="tableControl">'+ qqAkeyContent +'</td>'
								+ '<td class="tableControl">'
								+'<button type="button" title="查看" class="btn btn-info btn-circle btn-xs" onclick="seek('+ li.qqDefaultDataId +')" style="margin-right:3px;"><i class="fa fa-search-plus"></i> </button>'
								+'<button type="button" title="编辑" class="btn btn-info btn-circle btn-xs" onclick="edit('+ li.qqDefaultDataId +')" style="margin-right:3px;"><i class="fa fa-edit"></i> </button>'
								+'<button type="button" title="删除" class="btn btn-danger btn-circle btn-xs" onclick="del('+ li.qqDefaultDataId +')"><i class="fa fa-times"></i> </button>'
							    +'</td>'
								+'</tr>'
						$("#tqqbody").append(trs2);
							 });
						}
					
					 
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
						        if($.cookie('searchWord') != $('#qqSearchFund').val()){
									$('#qqSearchFund').val("");
									$.cookie('searchWord', ""); 
									qqSearch(1);
								}else{
									qqSearch(1);
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
			
			})
		 
		}
		
		//单条编辑
		function qqEdit() {
			$(".qq-info").attr('disabled', false);
			$('#qqSingleSave').removeAttr("hidden");
			$('#cancel').removeAttr("hidden");
			$('#qqSingleAdd').prop("hidden",true);
			$('#qqSingleEdit').prop("hidden",true);
		}
		//单条保存
		function qqSave(){
			if($("#selectDevices").val() == 0||$("#qqDevices").val() == null || $("#qqDevices").val() == ""){
				layer.msg('请选择设备号！', { icon : 0 });
				return;
			}
			var isValid = $("#qqSingleForm").valid();
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
							  data : $('#qqSingleForm')
							  .serialize(),
							  dataType : "json",
							  success : function(data) {
								if (data > 0) {
								layer.msg('保存成功', {
								icon : 1
								});
								qqSearch(-1);
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
            else{
			
		         }
		}
		//单条取消
		function qqSingleCancel() {
			layer.alert('确认放弃本次操作？',{ skin : 'layui-layer-lan',icon : 3,btn : [ '确认', '取消' ],closeBtn : 0},
					function() {
				//取消验证
				var validator = $("#qqSingleForm").validate({
			    });  
				validator.resetForm(); 
				qqSearch(-1);
				layer.msg('已取消！', { time : 1000, icon : 1 });
			});
			
		}
		
		//添加跳转表单
		 function add(){
			 $('#jump').attr("action", "${ctx}/lazyearn/AutoQQDefaultData/jumpForm");	
			 $('#jump').submit();
		 }
	 //单个删除方法
	    function del(qqDefaultDataId){
	    	layer.alert('确认删除？', {
				  skin: 'layui-layer-lan',icon:3,btn: ['确认','取消'] ,closeBtn:0 //按钮
				}, function(){
					$.ajax({
						type : "POST",
						url : "${ctx}/lazyearn/AutoQQDefaultData/delete",
						data : {
							qqDefaultDataId : qqDefaultDataId,
						},
						dataType : "json",
						success : function(result) {
							if(result > 0){
								qqSearch(-1);
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
						url : "${ctx}/lazyearn/AutoQQDefaultData/deleteAll",
						data : {
							'delitems':checkedList.toString(),
						},
						dataType : "json",
						success : function(result) {
							if(result > 0){
								qqSearch(-1);
								$('.check').iCheck('uncheck'); 
								layer.msg('删除成功！', { time : 1000, icon : 1 });
							}
						}
					});
			}); 
		}
		//点击编辑是跳转至表单提交页
		function edit(qqDefaultDataId){
			if(qqDefaultDataId != -1){
				$('#fundIdVal').val(qqDefaultDataId);
			}
			$('#jump').attr("action", "${ctx}/lazyearn/AutoQQDefaultData/jumpForm");	
			$('#jump').submit();
		}
		//点击查看是跳转至表单提交页
		function seek(qqDefaultDataId){
			if(qqDefaultDataId != -1){
				$('#fundIdVal').val(qqDefaultDataId);
			}
			$('#jump').attr("action", "${ctx}/lazyearn/AutoQQDefaultData/jumpForm");	
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
				$('#fundIdVal').val(checkedId);
			}
			$('#jump').attr("action", "${ctx}/lazyearn/AutoQQDefaultData/jumpForm");	
			$('#jump').submit();
		  }
		
		//判断输入框数字大小
		function check(obj,size){	
			var value = $(obj).val();
			if(obj.value!=obj.value.replace(/\D/g,'')||value>size){
				layer.msg('只能输入整数且不能大于'+size, { time : 1500, icon : 0 });
		    	obj.value='';
			 }else if(value==0){
				layer.msg('内容不能为0或空', { time : 1500, icon : 0 });
				obj.value = '';
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
	          	    	var select = '<option value="'+li.maindevices+'">'+ li.maindevices+'</option>';
	          	    	$('#selectDevices').append(select);
	          	    	var deviceName = $('#qqDevices').val();
	          	    	if(deviceName == null || deviceName == ""){
	            	    	
	            	    }else{
	            	    	if(li.maindevices == deviceName){
	            	    		$("#selectDevices").val(deviceName);
	            	    	}
	            	    } 
	          	       //头部
	          	    	var select = '<option value="'+li.maindevices+'">'+ li.maindevices+'</option>';
	          	    	$('#qqMainDevices').append(select);
			        });  
	           }
	  	    }); 
		}
		//获取select选中的文本和value
		$("#selectDevices").change(function(){
			var checkText=$("#selectDevices").find("option:selected").val();//获取Select选择的Text
			$('#qqDevices').val(checkText);
		}); 
		//多条查询
		$("#qqMainDevices").change(function(){
			var checkText=$("#qqMainDevices").find("option:selected").val();//获取Select选择的value
			 if(checkText=='请选择设备号...'){
				 $('#qqSearchFund').val("");
			}else{
				 $('#qqSearchFund').val(checkText);
				
			}
			 qqSearch(0);
		}); 
	</script>
</body>
</html>