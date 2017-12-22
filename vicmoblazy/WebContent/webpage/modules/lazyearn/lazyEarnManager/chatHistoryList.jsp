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
</style>
<!-- 聊天记录 -->

</head>

<body class="fix-header fix-sidebar card-no-border mini-sidebar">
	<div class="page-wrapper" style="height: 740px;margin-left: 0px;margin: 30px auto 0 auto;width: 900px;">
		<div class="container-fluid" style="margin-left: 0px;">
			<div class="row">
				<div class="col-12">
					<div class="card m-b-0">
						<div class="chat-main-box">
							<!-- .chat-left-panel -->
							<div class="chat-left-aside">
								<div class="open-panel">
									<i class="ti-angle-right"></i>
								</div>
								<div class="chat-left-inner" style="height: 700px;">
									<div class="form-material">
										 <input class="form-control p-20" value="好友列表"
											type="button" style="text-align: left">
											<!-- <h4 class="box-title" style="display: inline">好友列表</h4> -->
									</div>
									<div class="slimScrollDiv"
										style="position: relative; overflow: hidden; width: auto; height: 100%;">
										<ul class="chatonline style-none "
											style="overflow: auto; width: auto; height: 620px;">
											<div id="tbody"></div>
										</ul>
										<div class="slimScrollBar"
											style="background: rgb(220, 220, 220) none repeat scroll 0% 0%; width: 5px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 697px;"></div>
										<div class="slimScrollRail"
											style="width: 5px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: rgb(51, 51, 51) none repeat scroll 0% 0%; opacity: 0.2; z-index: 90; right: 1px;"></div>
									</div>
								</div>
							</div>
							<!-- .chat-right-panel -->
							<div class="chat-right-aside" style="height: 700px;">
								<div class="chat-main-header">
									<div class="p-20 b-b">
										<h3 class="box-title" style="width: 200px;display: inline">聊天消息</h3>
										
										<div class="fc-button-group" id="datebefore" onclick="before()" style="display: inline;margin-left: 250px"><button type="button" class="fc-prev-button fc-button fc-state-default fc-corner-left"><span class="fc-icon fc-icon-left-single-arrow"></span></button></div>
										<h4 class="box-title" style="width: 200px;display: inline;margin-left:0px;;margin-top: -7px" id="test18" >2017-11-22</h4>
										<div class="fc-button-group" id="datelater" onclick="later()" style="margin-left: 0px;display: inline"><button type="button" class="fc-next-button fc-button fc-state-default fc-corner-right"><span class="fc-icon fc-icon-right-single-arrow"></span></button></div>
									</div>
							
								</div>
								<div class="chat-rbox">
									<div class="slimScrollDiv"
										style="position: relative; overflow: hidden; width: auto; height: 100%;">
										<ul id = "scrolldIV" class="chat-list p-20"
											style="overflow: auto; width: auto; height: 620px;">
											<div id="lbody"></div>
										</ul>
										<div class="slimScrollBar"
											style="background: rgb(220, 220, 220) none repeat scroll 0% 0%; width: 5px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 443.785px;"></div>
										<div class="slimScrollRail"
											style="width: 5px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: rgb(51, 51, 51) none repeat scroll 0% 0%; opacity: 0.2; z-index: 90; right: 1px;"></div>
									</div>
								</div>

							</div>
							<!-- .chat-right-panel -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${ctxStatic}/laydate/laydate.js"></script>
	<script type="text/javascript">
	var weChatId = ${requestScope.nickname};
	var weChatdate1 = getDateStr();
	var weChatFriends1 = "";
	var weChatId_ = 0;
		$(function() {
			search(-1);
			document.getElementById("test18").innerHTML = getDateStr();
			//searchLiaoTianXiangQing(weiXinHao,weiXinHaoYoustr);
			layer.load();//加载层
		});
		//数据显示  
		function search(type) {
			if (type == 0) {
				$.cookie('searchWord', $('#searchFund').val());
			} else if (type == -1) {
				$.cookie('searchWord', "");
			}
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/AutoChatHistory/findChatFriendsList",
			    data : {weChatId :weChatId },
				success : function(result) {
				weChatFriends1 = result[0];
				getRecentlyTime(weChatId,result[0])
				
                layer.closeAll('loading');//去除加载层
				//先清空
				$("#tbody").empty();
				//再添加
				$.each(result,function(i, li) {
					
				 	var trs = '<li >'
					+ '<div onclick="getRecentlyTime(\''+ weChatId+'\',\''+li +'\','+i+')"><a href="javascript:void(0)">'
					+ '<img src="'+"showImg?imgFile=icon_"+li+".jpg"+'" alt="user-img" class="img-circle">'
					+ ' <span id="weChatId_'+i+'">'+ li
					+ '<small class="text-success"></small></span></a>'
					+ '</div></li>' 
					$("#tbody").append(trs);
					});
				document.getElementById("weChatId_0").style.color="#f58293";
			}
			});
		}
		//获得最近时间
		function getRecentlyTime(weChatId,weChatFriends,i) {
			var wechat=document.getElementById("weChatId_"+i);
			var wechat1=document.getElementById("weChatId_"+weChatId_);
			if(wechat!=null){
				wechat1.style.color="#67757c";
				wechat.style.color="#f58293";
				weChatId_ = i;
			}
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/AutoChatHistory/getRecentlyTime",
			    data : {weChatId : weChatId,weChatFriends : weChatFriends},
				success : function(result) {
					weChatdate1 = setDateStr(result);
					document.getElementById("test18").innerHTML = getDateStr();
					searchChatDetails(weChatId,weChatFriends);
				}
			});
		}
		//获得聊天记录
		function searchChatDetails(weChatId,weChatFriends) {
			
		    weChatFriends1 = weChatFriends;
			$.ajax({
				type : "POST",
				url : "${ctx}/lazyearn/AutoChatHistory/searchChatDetails",
			    data : {weChatId : weChatId,weChatFriends : weChatFriends,weChatdate :weChatdate1},
				success : function(result) {
                layer.closeAll('loading');//去除加载层
				//先清空
				$("#lbody").empty();
				//再添加
				//alert(result);
				if(result==''||result==null){
					$("#lbody").append('<h6 style="margin-left: 180px;margin-top: -20px;color: #858585">当前日期没有聊天记录</h6>');
				}else {
					$.each(result,function(i, li) {
						var flag = li.chatContents.substring(0,4);
						if(li.chatFlag==="1"){
							var trs = 	'<li>'
								+'<div class="chat-img">'
								+'<img src="'+"showImg?imgFile=icon_"+li.chatFriends+".jpg"+'" alt="user">'
							    +'</div>'
							    +'<div class="chat-content">'
								+'<h5>'+li.chatFriends+'</h5>';
							if(flag==="img_"){
								trs += '<img style="width: 200px" src="'+"showImg?imgFile="+li.chatContents+'" alt="'+li.chatContents+'">';
							}else {
								trs += '<div class="box bg-light-info">'+li.chatContents+'</div>';
							}
							    trs +='</div>'
							    +'<div class="chat-time">'+li.chatTime.substring(0,19)+'</div>'
								+'</li>';
							$("#lbody").append(trs);
								
					}else if (li.chatFlag==="0") {
						var trs = '<li class="reverse">'
	                    +'<div class="chat-time">'+li.chatTime.substring(0,19)+'</div>'
	                    +'<div class="chat-content">'
	                    +'<h5>'+li.chatPeople+'</h5>';if(flag==="img_"){
							trs += '<img style="width: 200px" src="'+"showImg?imgFile="+li.chatContents+'" alt="'+li.chatContents+'">';
						}else {
							trs += '<div class="box bg-light-info">'+li.chatContents+'</div>';
						}
						    trs +='</div>'
	                    +'<div class="chat-img"><img src="'+"showImg?imgFile=icon_"+li.chatPeople+".jpg"+'" alt="user"></div>'
	                +'</li>'
	                $("#lbody").append(trs);
					}else {
						var trs = 	'<li>'
							+'<div class="chat-img">'
							+'<img src="'+"showImg?imgFile=icon_"+li.chatFlag+".jpg"+'" alt="user">'
						    +'</div>'
						    +'<div class="chat-content">'
							+'<h5>'+li.chatFlag+'</h5>';
						if(flag==="img_"){
							trs += '<img style="width: 200px" src="'+"showImg?imgFile="+li.chatContents+'" alt="'+li.chatContents+'">';
						}else {
							trs += '<div class="box bg-light-info">'+li.chatContents+'</div>';
						}
						    trs +='</div>'
						    +'<div class="chat-time">'+li.chatTime.substring(0,19)+'</div>'
							+'</li>'
							$("#lbody").append(trs);
					}
						});
					//scrollBoot();
				}
				}
			});
		}
		//前一天
		function before(){
			weChatdate1 = addDate(weChatdate1,-1);
			searchChatDetails(weChatId,weChatFriends1);
			document.getElementById("test18").innerHTML = weChatdate1;
		}
		//后一天
		function later(){
			weChatdate1 = addDate(weChatdate1,1);
			searchChatDetails(weChatId,weChatFriends1);
			document.getElementById("test18").innerHTML = weChatdate1;
		}
		//转换时间
		function getDateStr() {
			var date = new Date();
			var year = date.getFullYear();
			var month = (date.getMonth() + 1 > 9) ? (date.getMonth() + 1)
					: ('0' + (date.getMonth() + 1));
			var day = (date.getDate() > 9) ? (date.getDate()) : ('0' + date
					.getDate());
			var dateStr = year + "-" + month + "-" + day;
			return dateStr;
		}
		function setDateStr(DBTime) {
			var date = new Date(DBTime);
			var year = date.getFullYear();
			var month = (date.getMonth() + 1 > 9) ? (date.getMonth() + 1)
					: ('0' + (date.getMonth() + 1));
			var day = (date.getDate() > 9) ? (date.getDate()) : ('0' + date
					.getDate());
			var dateStr = year + "-" + month + "-" + day;
			return dateStr;
		}
		//加减时间
		function addDate(date,days){ 
			var d=new Date(date); 
			d.setDate(d.getDate()+days); 
			var month=d.getMonth()+1; 
			var day = d.getDate(); 
			if(month<10){ 
			month = "0"+month; 
			} 
			if(day<10){ 
			day = "0"+day; 
			} 
			var val = d.getFullYear()+"-"+month+"-"+day; 
			return val; 
		}
	  	/* function scrollBoot()
        {
            var div = document.getElementById('scrolldIV');
            div.scrollTop = div.scrollHeight;
            alert("滑动到最下面了吗？ "+div.scrollTop+", "+div.scrollHeight);
        } */
    
       
		laydate.render({
			elem: '#test18',
			value: new Date(),
			btns: ['now', 'confirm'],
			done: function(value, date, endDate){
			     weChatdate1 = value;
			     searchChatDetails(weChatId,weChatFriends1);
			}
		});
	
	</script>
	<script src="${ctxStatic}/asset/plugins/bootstrap-daterangepicker/daterangepicker.js"></script> 
</body>
</html>
