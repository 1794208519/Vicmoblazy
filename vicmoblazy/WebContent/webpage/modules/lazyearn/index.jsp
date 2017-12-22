<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
<%-- <script src="${ctxStatic}/asset/common/js/index.js"></script> --%>
</head>
<body class="fix-header fix-sidebar card-no-border">
	<div id="main-wrapper">
		<%@ include file="/webpage/modules/lazyearn/include/header.jsp"%>
		<aside class="left-sidebar">
			<div class="scroll-sidebar">
				<!-- 左侧的头像 -->
				<div class="user-profile"
					style="background: url(${ctxStatic}/asset/images/background/user-info.jpg) no-repeat;">
					<!-- 用户头像 -->
					<div class="profile-img">
						<img src="${ctxStatic}/asset/images/users/profile.png" alt="user" />
					</div>
					<!-- 头像下的文字 -->
					<div class="profile-text">
						<!-- 用户名 -->
						<a href="#" class="dropdown-toggle u-dropdown"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="true">硬件管理系统</a>
						<!-- 用户名下的弹框 -->
						<div class="dropdown-menu animated flipInY">
							<a href="#" class="dropdown-item"> <i class="ti-user"></i> <span
								style="margin-left: 12px; font-size: 15px;">申请账号</span>
							</a> <a href="#" class="dropdown-item"> <i class="ti-settings"></i>
								<span style="margin-left: 12px; font-size: 15px;">权限升级</span>
							</a>
							<div class="dropdown-divider"></div>
							<a href="${ctx}/logout" class="dropdown-item"> <i
								class="fa fa-power-off"></i> <span
								style="margin-left: 12px; font-size: 15px;">退出系统</span>
							</a>
						</div>
					</div>
				</div>
				<!-- 左侧菜单 -->
				<nav class="sidebar-nav">
					<ul id="sidebarnav">
						<!-- <li class="nav-small-cap">硬件项目</li> -->
						<li><a class="has-arrow waves-effect waves-dark" href="#"
							aria-expanded="false"><i class="mdi mdi-brightness-7"></i><span
								class="hide-menu" id="hdManager">硬件管理</span></a>
							<ul aria-expanded="false" class="collapse">
								<li><a
									href="${ctx}/lazyearn/HardWareManager/jumpList"
									id="hdManagerFirst" target="myIframe"  onclick="jumpHd()">硬件管理首页</a></li>
							</ul></li>
						<li><a class="has-arrow waves-effect waves-dark" href="#"
							aria-expanded="false"><i
								class="mdi mdi-arrange-send-backward"></i><span
								class="hide-menu" id="lazyEarn">懒赚1.0</span></a>
							<ul aria-expanded="false" class="collapse">
								<li><a
									href="${ctx}/lazyearn/AutoControl/jumpList" id="myControl"
									target="myIframe" onclick="jump(this)">我的设备</a></li>
								<li><a
									href="${ctx}/lazyearn/AutoAccount/jumpList" id="myAccount"
									target="myIframe" onclick="jump(this)">我的账号</a></li>
								<li><a
									href="${ctx}/lazyearn/AutoData/jumpList" id="myAddress"
									target="myIframe" onclick="jump(this)">我的地址</a></li>
								<li><a
									href="${ctx}/lazyearn/AutoWXDefaultData/jumpallList"
									id="dataCenter" target="myIframe" onclick="jump(this)">数据交互中心</a></li>
								<li><a
									href="${ctx}/lazyearn/IntelligentManager/jumpList"
									id="IntelligentResponse" target="myIframe" onclick="jump(this)">智能回复</a></li>
								<li><a
									href="${ctx}/lazyearn/AutoCity/jumpList" id="cityManager"
									target="myIframe" onclick="jump(this)">城市管理</a></li>
								<li><a
								    href="${ctx}/lazyearn/WhiteList/jumpList" id="whiteList"
									target="myIframe" onclick="jump(this)">白名单管理</a></li>
							</ul></li>
						<li><a class="has-arrow waves-effect waves-dark" href="#"
							aria-expanded="false"><i class="mdi mdi-widgets"></i><span
								class="hide-menu" id="weChat">微粉</span></a>
							<ul aria-expanded="false" class="collapse">
								<li><a href="" id="accountManager"
									target="myIframe" onclick="jumpW(this)">账号管理</a></li>
							</ul></li>
							<li><a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false"
							 ><i class="mdi mdi-account-alert"></i><span
								class="hide-menu" >客户反馈</span></a>
								<ul aria-expanded="false" class="collapse">
								<li><a href="${ctx}/lazyearn/feedback/jumpList" id="feedback"
									target="myIframe" onclick="jumpfeek(this)">客户反馈</a></li>
							</ul>
							</li>
					</ul>
				</nav>
			</div>
		</aside>

		<!-- 右侧部分 -->
		<div class="page-wrapper">
			<!-- 右侧主体 -->
			<div class="container-fluid">
				<div class="row page-titles"
					style="box-shadow: inset 1px -1px 0px #dad3d3;">
					<div class="col-md-5 col-8 align-self-center">
						<h3 class="text-themecolor m-b-0 m-t-0" style="font-size: 18px"
							id="indexTitle"></h3>
						<ol class="breadcrumb">
							<li class="breadcrumb-item" id="indexTip"></li>
						</ol>
					</div>
				</div>
				<iframe frameborder="0" class="mframe"
					style="width: 100%; height: 100%" id="myIframe" name="myIframe"></iframe>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	    $(function(){
			//默认选中第一个
			$("#sidebarnav").children("li:first-child")
					.addClass("active");
			$("#sidebarnav").children("li:first-child")
					.children("a:first-child").attr(
							"aria-expanded", true);
			//默认打开硬件管理首页
			jumpHd();
			document.getElementById("hdManagerFirst").click();
			if (document.getElementById("sidebarnav").firstChild.nextSibling.lastChild.firstChild.firstChild != null) {
				//有下级菜单的点击
				document.getElementById("sidebarnav").firstChild.nextSibling.lastChild.firstChild.firstChild
						.click();
			} else {
				//没有下级菜单的点击					
				document.getElementById("sidebarnav").firstChild.nextSibling.firstChild.firstChild
						.click();
			}
	    });

		//懒赚菜单点击
		function jump(obj) {
			var id = obj.getAttribute("id");
        	$("a").removeClass("active");
			// 添加active
			$('#'+id).addClass("active");
			var innerTitle = document.getElementById('lazyEarn').innerHTML;
			var innerText = document.getElementById(id).innerHTML;
			$("#indexTitle").html(innerTitle);
			$("#indexTip").html(innerText);
		}
		//微粉菜单点击
		function jumpW(obj) {
			var id = obj.getAttribute("id");
			$("a").removeClass("active");
			// 添加active
			$('#'+id).addClass("active");
			var innerTitle = document.getElementById('weChat').innerHTML;
			var innerText = document.getElementById(id).innerHTML;
			$("#indexTitle").html(innerTitle);
			$("#indexTip").html(innerText);
			
		}
		//硬件管理菜单点击
		function jumpHd() {
			$("a").removeClass("active");
			// 添加active
			$('#hdManagerFirst').addClass("active");
			var innerTitle = document.getElementById('hdManager').innerHTML;
			var innerText = document.getElementById('hdManagerFirst').innerHTML;
			$("#indexTitle").html(innerTitle);
			$("#indexTip").html(innerText);
			
		}
		//客户反馈点击
		function jumpfeek() {
			$("a").removeClass("active");
			// 添加active
			$('#feedback').addClass("active");
			var innerTitle = document.getElementById('feedback').innerHTML;
			$("#indexTitle").html(innerTitle);
			$("#indexTip").html(innerTitle);
			
		}
	</script>
</body>
</html>