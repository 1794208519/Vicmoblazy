<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>

<script type="text/javascript">
$.validator.setDefaults({
    /*关闭键盘输入时的实时校验*/
    onkeyup: null,
    /*添加校验成功后的执行函数--修改提示内容，并为正确提示信息添加新的样式(默认是valid)*/
    success: function(label){
        /*label的默认正确样式为valid，需要通过validClass来重置，否则这里添加的其他样式不能被清除*/
        label.text('').addClass('valid');
    },
    /*重写校验元素获得焦点后的执行函数--增加[1.光标移入元素时的帮助提示,2.校验元素的高亮显示]两个功能点*/
    onfocusin: function( element ) {
        this.lastActive = element;

        /*1.帮助提示功能*/
        this.addWrapper(this.errorsFor(element)).hide();
        var tip = $(element).attr('tip');
       
        if(tip && $(element).parent().children(".tip").length === 0){
        	
        		$(element).parent().append("<label class='tip'>" + tip + "</label>");
		
            
        }

        /*2.校验元素的高亮显示*/
       /* $(element).addClass('highlight');*/

        // Hide error label and remove error class on focus if enabled
        if ( this.settings.focusCleanup ) {
            if ( this.settings.unhighlight ) {
                this.settings.unhighlight.call( this, element, this.settings.errorClass, this.settings.validClass );
            }
            this.hideThese( this.errorsFor( element ) );
        }
    },
    /*重写校验元素焦点离开时的执行函数--移除[1.添加的帮助提示,2.校验元素的高亮显示]*/
    onfocusout: function( element ) {
        /*1.帮助提示信息移除*/
        $(element).parent().children(".tip").remove();

        /*2.校验元素高亮样式移除*/
        /*$(element).removeClass('highlight');*/

        /*3.替换下面注释的原始代码，任何时候光标离开元素都触发校验功能*/
        //this.element( element );

        if ( !this.checkable( element ) && ( element.name in this.submitted || !this.optional( element ) ) ) {
            this.element( element );
        }
    }
});

//自定义方法，完成手机号码的验证
//name:自定义方法的名称，method：函数体, message:错误消息
$.validator.addMethod("maindevices", function(value, element, param){
    //方法中又有三个参数:value:被验证的值， element:当前验证的dom对象，param:参数(多个即是数组)
    //alert(value + "," + $(element).val() + "," + param[0] + "," + param[1]);
    return new RegExp(/^[0-9A-Za-z]{6,25}$/).test(value);

}, "设备号由6~25位的数字、字母组合");

$.validator.addMethod("partdevices", function(value, element, param){
    //方法中又有三个参数:value:被验证的值， element:当前验证的dom对象，param:参数(多个即是数组)
    //alert(value + "," + $(element).val() + "," + param[0] + "," + param[1]);
    return new RegExp(/^[0-9A-Za-z]{6,25}$/).test(value);

}, "设备号由6~25位的数字、字母组合");
	$(function() {
		//让当前表单调用validate方法，实现表单验证功能
		$("#controlform").validate({
			debug : true,
			rules : { //配置验证规则，key就是被验证的dom对象，value就是调用验证的方法(也是json格式)
				maindevices : {
					required : true,
					maindevices : true
				},
				partdevices : {
					required : true,
					partdevices : true
				}
			},
			messages : {
				maindevices : {
					required : "请输入主控设备号"
				},
				partdevices : {
					required : "请输入分控设备号"

				}
			}
		});

	});
</script>
<style type="text/css">
.form-control:disabled{
     opacity: 1; 
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
.th1{
	width: 100px;
	max-width: 120px;
	min-width: 50px
	
}
.th2{
	width: 600px;
	max-width: 600px;
	min-width: 300px
	
}
.th3{
	width: 600px;
	max-width: 600px;
	min-width: 300px
	
}
.th4{
	width: 260px;
	max-width: 260px;
	min-width: 100px
	
}
</style>
</head>
<body onkeydown="keySearch();">
	<div class="divControl">
		<div class="card">
			<ul class="nav nav-tabs customtab" role="tablist">
				<li class="nav-item">
					<a class="nav-link active" data-toggle="tab" href="#home2" role="tab">
						<span class="hidden-sm-up"><i class="ti-home"></i></span> 
						<span class="hidden-xs-down">我的设备</span>
					</a>
				</li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane p-20 active" id="home2" role="tabpanel">
				    <div class="row button-group col-3" style="float: left;margin-bottom:8px;">
				 		<button type="button" class="btn btn-info" onclick="add()"><i class="fa fa-plus-circle"></i> 添加</button>
				 		<button type="button" class="btn btn-info" onclick="edit1()"><i class="fa fa fa-edit"></i> 编辑</button>
				 		<button type="button" class="btn btn-danger" onclick="deleteAll()"><i class="fa fa fa-times"></i> 删除</button>
				 	</div>
				 	<div class="row col-9" style="float: right;">
						<form action="" method="post" onsubmit="return false;" style="width: 100%" id="searchForm">
					        <input id="pageNo" name="pageNo" type="hidden" value="1" /> 
					        <input id="pageSize" name="pageSize" type="hidden" value="10" />  
							<button id="keysearch" type="button" class="btn waves-effect waves-light btn-info" onclick="search(0)" style="float: right; margin-left: 15px">搜索</button>
							<input autocomplete="off" type="text" class="form-control height-control col-4" placeholder="请输入懒懒/赚赚设备号搜索" id="searchFund" name="searchFund" value="" style="float: right;"/>
						</form>
					</div>
					<table id="example23" class="display nowrap table table-hover table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr>
							    <th class="tableControl th1"><input type="checkbox" class="check" id="ischange" data-checkbox="icheckbox_square-blue"></th>
								<th class="tableControl th2">懒懒设备号</th>
								<th class="tableControl th3">赚赚设备号</th>
								<th class="tableControl th4">操作</th>
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
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">添加设备</h4>				</div>
				<div class="modal-body">
				

				    <form class="form-horizontal" method="post" onkeydown="if(event.keyCode==13)return false;" onsubmit="return false" id="controlform">
				          <input type="hidden" id="controlId"  name="controlId" value="">
                          <div class="form-group row">
                               <label for="inputEmail3" class="col-sm-3 text-right control-label col-form-label">主控设备号*</label>
                               <div class="col-sm-9">
								     <select class="custom-select" id="selectmainDevices"  style="width: 100%"  onchange="document.getElementById('maindevices').value=this.options[this.selectedIndex].value">
									<option value="" selected>请选择设备号...</option>
								</select>
								 <input autocomplete="off" type="text" class="form-control" name="maindevices" id="maindevices" required 
									data-validation-required-message="这是必填项" value="" style="left: 15px; TOP: 0px; WIDTH: 80%;padding-right:40px;POSITION: absolute">
									<i class="mdi mdi-close" id="cls" style="font-size:20px;right: 18%; TOP: 0px; WIDTH: 20px;height:38px;padding-top:9px;padding-bottom:9px;POSITION: absolute"></i>
                               </div>
                           </div>
                           <div class="form-group row">
                                 <label for="inputEmail3" class="col-sm-3 text-right control-label col-form-label">分控设备号*</label>
                                 <div class="col-sm-9">
                                 
                                  <select class="custom-select" id="selectpartDevices"  style="width: 100%"  onchange="document.getElementById('partdevices').value=this.options[this.selectedIndex].value">
									<option value="" selected>请选择设备号...</option>
								</select>
								 <input autocomplete="off" type="text" class="form-control" name="partdevices" id="partdevices" required 
									data-validation-required-message="这是必填项" value="" style="left: 15px; TOP: 0px; WIDTH: 80%; padding-right:40px; POSITION: absolute">
                                 <i class="mdi mdi-close" id="partcls" style="font-size:20px;right: 18%; TOP: 0px; WIDTH: 20px;height:38px;padding-top:9px;padding-bottom:9px;POSITION: absolute"></i>
                                 </div>
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
	    $(function(){ 
		   //先调下数据(默认为第一页开始，每页十个)
		   search(-1);
		   layer.load();//加载层
		   currentDevices();
		   currentMainDevices();
		   document.getElementById("cls").style.visibility="hidden";
		   document.getElementById("partcls").style.visibility="hidden";		 });
	    document.getElementById("maindevices").addEventListener("keyup",function(){
	        if(this.value.length>0)
	        {
	            document.getElementById("cls").style.visibility="visible";
	            document.getElementById("cls").onclick=function()
	            {
	                document.getElementById("maindevices").value="";
	                document.getElementById("cls").style.visibility="hidden";
	            }
	        }else
	        {
	            document.getElementById("cls").style.visibility="hidden";
	        }
	    });
	    document.getElementById("partdevices").addEventListener("keyup",function(){
	        if(this.value.length>0)
	        {
	            document.getElementById("partcls").style.visibility="visible";
	            document.getElementById("partcls").onclick=function()
	            {
	                document.getElementById("partdevices").value="";
	                document.getElementById("partcls").style.visibility="hidden";
	            }
	        }else
	        {
	            document.getElementById("partcls").style.visibility="hidden";
	        }
	    });	    function keySearch(){  
	    	   if (event.keyCode==13){  //回车键的键值为13  
	    	      document.getElementById("keysearch").click(); 
	    	   }  
	    	}  
       //数据显示
	   function search(type){
		    if(type == 0){
				$.cookie('searchWord', $('#searchFund').val());
			} else if(type == -1) {
				$.cookie('searchWord', "");
			}  
		  $.ajax({
			type : "POST",
			url : "${ctx}/lazyearn/AutoControl/findList",
			data: $('#searchForm').serialize(),
			dataType : "json",
			success : function(result) {
				layer.closeAll('loading');//去除加载层
				//先清空
				$("#tbody").empty();
				//再添加
				$.each(result.list, function(i, li) {
					var trs = '<tr id="'+ li.controlId +'">'
					    + '<td class="tableControl th1"><input type="checkbox" name="subChk" class="check" id="square-checkbox-1" value="'+ li.controlId +'" data-checkbox="icheckbox_square-blue"></td>'
						+ '<td class="tableControl th2">'+ li.maindevices +'</td>'
						+ '<td class="tableControl th3">'+ li.partdevices +'</td>'
						+ '<td class="tableControl th4">'
						+'<button type="button" title="编辑" class="btn btn-info btn-circle btn-xs" onclick="edit('+ li.controlId +')" style="margin-right:3px;"><i class="fa fa-edit"></i> </button>'
						+'<button type="button" title="删除" class="btn btn-danger btn-circle btn-xs" onclick="del('+ li.controlId +')"><i class="fa fa-times"></i> </button>'
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
		 /* $('#jump').attr("action", "rest/fund/fundInfo");*/
		 /* $('#jump').submit(); */
		 $("#partdevices").removeAttr("disabled");
		 $("#selectpartDevices").removeAttr("disabled");
		 show();
	 }
	 //新增数据
	 function save(){
		 var isValid = $("#controlform").valid();
			if (isValid) {
				  layer.alert('确认保存？', {					  skin: 'layui-layer-lan',icon:3,btn: ['确认','取消'] ,closeBtn:0 //按钮
					},function(){
					  $(".btn-info").attr('disabled', true); 
					  if ($("#maindevices").val().length>25) {
						  layer.msg('主控设备号最多有25个字', {
								icon : 1,
								time : 1000,
								closeBtn : 0
							}); 
						  return;
						}
					  if ($("#partdevices").val().length>25) {
						  layer.msg('主控设备号最多有25个字', {
								icon : 1,
								time : 1000,
								closeBtn : 0
							}); 
						  return;
					}
				    $.ajax({
		        	  type: "POST",
		        	  url: "${ctx}/lazyearn/AutoControl/save",
		              data: $('#controlform').serialize(),
		              dataType: "json",
		              success : function(data) {
		            	 
							if (data > 0) {
								layer.msg('保存成功', {
									icon : 1,
									time : 1000,
									closeBtn : 0
								}); 
								dismiss();
								search();
								currentDevices();
								currentMainDevices();
								
							} else if (data == -1) {
								layer.msg('保存失败！', {
									icon : 2
								});
							}else if (data == -2) {
								layer.msg('该分控设备已存在！', {
									icon : 3
								});
							}
							$(".btn-info").attr('disabled', false);
						}
		    	   }); 
					
					  
			    }); 
			  }
	 }
		 
	//单个删除方法
    function del(controlId){
    	layer.alert('确认删除？', {
			  skin: 'layui-layer-lan',icon:3,btn: ['确认','取消'] ,closeBtn:0 //按钮
			}, function(){
				$.ajax({
					type : "POST",
					url : "${ctx}/lazyearn/AutoControl/delete",
					data : {
						controlId : controlId,
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
					url : "${ctx}/lazyearn/AutoControl/deleteAll",
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
	function edit(controlId){
		$.ajax({
			 type: "POST",
	      	  url: "${ctx}/lazyearn/AutoControl/edit",
	            data: {
	            	controlId: controlId
	            	},
	            dataType: "json",
	            success: function(data){
	          	if(data != null){
	          		$('#controlId').val(data.controlId);
	          		$('#maindevices').val(data.maindevices);
	          		$('#partdevices').val(data.partdevices);
	          		$("#partdevices").attr("disabled","disabled");
	          		$("#selectpartDevices").attr("disabled","disabled");
	          	}
	          	$("#responsive-modal").modal("show");
	           }
	  	   }); 
	}
	//点击查看是跳转至表单提交页
	function seek(controlId){
		$.ajax({
	      	  type: "POST",
	      	  url: "${ctx}/lazyearn/AutoControl/edit",
	            data: {
	            	controlId: controlId
	            	},
	            dataType: "json",
	            success: function(data){
	          	if(data != null){
	          		$('#controlId').val(data.controlId);
	          		$('#maindevices').val(data.maindevices);
	          		$('#partdevices').val(data.partdevices);
	          	}
	          	$("#responsive-modal").modal("show");
	           }
	  	   }); 
	}
	//checkbox监听
	$('#ischange').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		$('.check').iCheck('check');
	});
	$('#ischange').on('ifUnchecked', function(event){ //ifUnchecked 事件应该在插件初始化之前绑定 
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
		$.ajax({
			 type: "POST",
	      	  url: "${ctx}/lazyearn/AutoControl/edit",
	            data: {
	            	controlId: checkedId
	            	},
	            dataType: "json",
	            success: function(data){
	          	if(data != null){
	          		$('#controlId').val(data.controlId);
	          		$('#maindevices').val(data.maindevices);
	          		$('#partdevices').val(data.partdevices);
	          		$("#partdevices").attr("disabled","disabled");
	          		$("#selectpartDevices").attr("disabled","disabled");
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
					$('#selectpartDevices').append(select);
					
				});
			}
		});
	}
	//查询当前所有主设备号
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
	
			}
		});
	}
	
   </script>
</body>

</html>