<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>微炫客硬件管理系统</title>
		<meta name="keywords" content="微信号营销平台,微信,微信公众平台" />
		<meta name="description" content="" />
		<link rel="shortcut icon" href="${ctxStatic}/images/vicmob_logo.ico" />
		<!---默认-->
		<link href="${ctxStatic}/login/css/bootstrap.min.css" rel="stylesheet">
		<link href="${ctxStatic}/login/css/font-awesome.min.css" rel="stylesheet">
		<link href="${ctxStatic}/login/css/common.css" rel="stylesheet">
		<script>
			var require = {
				urlArgs: 'v=2017071918'
			};
		</script>
<%-- 		<script src="${ctxStatic}/login/js/jquery-1.11.1.min.js"></script>
		<script src="${ctxStatic}/login/js/util.js"></script>
		<script src="${ctxStatic}/login/js/require.js"></script>
		<script src="${ctxStatic}/login/js/config.js"></script> --%>
		
				<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/common/jeeplus.js" type="text/javascript"></script>
		<!--[if lt IE 9]>
		<script src="js/html5shiv.min.js"></script>
		<script src="js/respond.min.js"></script>
	<![endif]-->
		<!--模板-->
		<link href="${ctxStatic}/login/css/bootstrap.min_1.css" rel="stylesheet">
		<link href="${ctxStatic}/login/css/bootstrap-reset.css" rel="stylesheet"> 
		<link href="${ctxStatic}/login/css/style.css" rel="stylesheet">
		<!-- <link href="css/style-responsive.css" rel="stylesheet"> -->
		<!--模板结束-->
		<script type="text/javascript">
			if(navigator.appName == 'Microsoft Internet Explorer') {
				if(navigator.userAgent.indexOf("MSIE 5.0") > 0 || navigator.userAgent.indexOf("MSIE 6.0") > 0 || navigator.userAgent.indexOf("MSIE 7.0") > 0) {
					alert('您使用的 IE 浏览器版本过低, 推荐使用 Chrome 浏览器或 IE8 及以上版本浏览器11.');
				}
			}

			window.sysinfo = {
				'siteroot': 'http://wx.vicmob.cn/',
				'siteurl': 'http://wx.vicmob.cn/web/index.php?c=user&a=login',
				'attachurl': 'http://wx.vicmob.cn/attachment/',
				'attachurl_local': 'http://wx.vicmob.cn/attachment/',
				'attachurl_remote': '',
				'cookie': {
					'pre': '526f_'
				}
			};
		</script>
				<script>
			if (window.top !== window.self) {
				window.top.location = window.location;
			}
		</script>
		<script type="text/javascript">
				$(document).ready(function() {
					$("#loginForm").validate({
						rules: {
							validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"},
							username: {required: true},
							password: {required:true}
						},
						messages: {
							username: {required: "请填写用户名."},password: {required: "请填写密码."},
							validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
						},
						errorLabelContainer: "#messageBox",
						errorPlacement: function(error, element) {
							error.insertAfter(element);
							/* error.appendTo($("#loginError").parent()); */
						} 
					});
				});
				// 如果在框架或在对话框中，则弹出提示并跳转到首页
				if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
					alert('未登录或登录超时。请重新登录，谢谢！');
					top.location = "${ctx}";
				}
		</script>
	</head>

	<body style="background: #6192f9; position: relative;">
		<style>
			@media screen and (max-width:1200px) {
				.login .panel.panel-default {
					width: 90%;
					min-width: 300px;
				}
			}
			
			@media screen and (min-width:768px) {
				.login .panel.panel-default {
					width: 70%;
				}
			}
			
			@media screen and (min-width:1200px) {
				.login .panel.panel-default {
					width: 20%;
				}
			}
			
			.text {
				font-size: 14px;
			}
		</style>

		<head>
			<link rel="stylesheet" href="${ctxStatic}/login/css/normalize-d720be9a3d.css">
			<link rel="stylesheet" href="${ctxStatic}/login/css/index-754e12ae87.css">

			<link type="text/css" rel="stylesheet" href="${ctxStatic}/login/css/normalize-d720be9a3d_1.css" />
			<link type="text/css" rel="stylesheet" href="${ctxStatic}/login/css/index-754e12ae87_1.css" />
			<link type="text/css" rel="stylesheet" href="${ctxStatic}/login/css/index_1000-cff549b5dc.css" />

		</head>
		<img src="${ctxStatic}/login/picture/yuan3.png" alt="" width="480" style="position: absolute; top: 0; left: 0;"/>
		<img src="${ctxStatic}/login/picture/yuan2.png" alt="" style="position: absolute; top: 20px; right: 500px;"/>
		<img src="${ctxStatic}/login/picture/yuan1.png" alt="" style="position: absolute; bottom: 0; right: 50px;"/>
		

		<div style="padding-top: 50px;">
			<div style="width: 100%; height: 838px; background: url(${ctxStatic}/login/picture/qq_05.png) no-repeat;">
				<div style="width: 1340px;" class="main">
					<div style="background: #fff; margin-top: 175px; border-radius: 5px; padding: 35px;" class="login-box">
						<img style="margin-bottom: 0;" class="login-logo" src="${ctxStatic}/images/vicmob.png">
						<form action="${ctx}/login" method="post" id="loginForm" role="form" onsubmit="return formcheck();">
							<input class="username" id="username" name="username" value="${username}" placeholder="用户名" type="text" style="margin-top: 30px; border-radius: 5px; background: url(${ctxStatic}/login/picture/login_icon1.png) 10px 50% no-repeat; background-color: #FFF1E8;">
							<input class="password" name="password" id="password" placeholder="密码" type="password" style="margin-top: 30px; margin-bottom: 15px; background: url(${ctxStatic}/login/picture/login_icon2.png) 10px 50% no-repeat; background-color: #FFF1E8; border-radius: 5px;">
							<label style="font-weight: 400;"  class="pull-right">
								<input  type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''} class="ace" />
								<span class="lbl"> 记住账号</span>
							</label>
							
					<div class="space"></div>
					<div>
					<span>&nbsp;</span>
					<c:if test="${isValidateCodeLogin}">
						<div class="input-group m-b text-muted validateCode">
						<label class="input-label mid" for="validateCode">验证码:</label>
						<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:5px;"/>
						</div>
					</c:if>
					</div>
					<div class="input-group" style="margin-top: 8px" >
						<span style="color:red;"><i><sys:message content="${message}"/></i></span>
					</div>
					
					
							
							<!--button class="btn-login" id="login_button">登录</button-->
							<input type="submit" name="submit" value="登录" class="btn-login" style="background-color:#FFA932; border-radius: 5px; margin-top: 35px; margin-bottom: 20px;" />
							<input name="token" value="3feabfdb" type="hidden" />
							<!--a class="forget" href="http://www.weimob.com/website/forget/forget1">忘记密码?</a-->
							<span class="reg">
        <a href="${ctx}/jumpList" style="color: #000000;">忘记密码 ? </a>|
        <a href="http://wx.vicmob.cn/web/index.php?c=user&a=register&" style="color: #FFA932;">立即注册</a>
      </span>
						</form>
					</div>
				</div>
			</div>
		</div>
		<p style="text-align: center; color: #fff;">版权所有 微炫客信息技术有限公司 © 2017-2020</p>
		
		
		<script type="text/javascript">
			window.jQuery || document.write("<script src='../assets/js/jquery.js'>"+"<"+"/script>");
		</script>
					<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='../assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<style>
		/* Validation */

			label.error {
			    color: #cc5965;
			    display: inline-block;
			    margin-left: 5px;
			}
			
			.form-control.error {
			    border: 1px dotted #cc5965;
			}
		</style>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
		$(document).ready(function() {
			 $(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			 });
			});
			
			
			
			//you don't need this, just used for changing background
			$(document).ready(function() {
			 $('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');
				
				e.preventDefault();
			 });
			 
			});
		</script>
	</body>
	
</html>