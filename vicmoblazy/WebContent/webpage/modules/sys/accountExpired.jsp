<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>微炫客信息平台</title>
	<meta name="keywords" content=" " />
	<meta name="description" content="公众平台 ， " />
	<link rel="shortcut icon" href="${ctxStatic}/images/vicmob_logo.ico" />
	<link href="${ctxStatic}/resource/css/bootstrap.min.css?v=20170218" rel="stylesheet">
	<link href="${ctxStatic}/resource/css/common.css?v=20170218" rel="stylesheet">
	<script type="text/javascript">
	if(navigator.appName == 'Microsoft Internet Explorer'){
		if(navigator.userAgent.indexOf("MSIE 5.0")>0 || navigator.userAgent.indexOf("MSIE 6.0")>0 || navigator.userAgent.indexOf("MSIE 7.0")>0) {
			alert('您使用的 IE 浏览器版本过低, 推荐使用 Chrome 浏览器或 IE8 及以上版本浏览器.');
		}
	}
	</script>
	<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
		<!-- BOOTSTRAP JS -->
	<script src="${ctxStatic}/SmartAdmin/js/bootstrap/bootstrap.min.js"></script>
	<!-- JQUERY VALIDATE -->
	<script src="${ctxStatic}/SmartAdmin/js/plugin/jquery-validate/jquery.validate.js"></script>
	<!-- 引入layer插件 美化弹出 -->
	<script src="${ctxStatic}/layer-v2.0/layer/layer.js"></script>	
	<script src="${ctxStatic}/layer-v2.0/layer/laydate/laydate.js"></script>
	
<script type="text/javascript">
</script>

<style>
	.deg180 {
			transform: rotate(180deg);
			-ms-transform: rotate(180deg); /* IE 9 */
			-moz-transform: rotate(180deg); /* Firefox */
			-webkit-transform: rotate(180deg); /* Safari 和 Chrome */
			-o-transform: rotate(180deg); /* Opera */
			}
	.page_content{
			float:left; padding:0; width:840px;
		}
</style>
</head>
<body class="">
	<div class="loader" style="display:none">
		<div class="la-ball-clip-rotate">
			<div></div>
		</div>
	</div>
<div class="head">
	<nav class="navbar navbar-default" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand">
					<img src="${ctxStatic}/images/vicmob.png" class="pull-left" width="170px" height="50px">
					<!-- <span class="version">1.2.6</span> -->
				</a>
			</div>
			<div class="collapse navbar-collapse">
				<!-- <ul class="nav navbar-nav navbar-left">
				<li><a href="./index.php?c=home&a=welcome&" >小程序</a></li>
					<li><a href="./index.php?c=account&a=manage&account_type=1" >系统管理</a></li>
				</ul> -->
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="wi wi-user color-gray"></i>${loginname} <span class="caret"></span></a>
					<ul class="dropdown-menu color-gray" role="menu">
					<!-- 	<li class="divider"></li> -->
						<li>
							<a href="${ctx}/logout"><i class="fa fa-sign-out color-gray"></i> 退出系统</a>
						</li>
						</ul>
					</li>
				</ul>
			</div>
					</div>
	</nav>
</div>
<div class="main">
	<div class="container">
		<div class="panel panel-cut" id="js-account-display" ng-controller="AccountDisplay">
	<div class="panel-heading">
		<span>您的账户已过期！</span>
		  <!-- <span style="margin-left:10%;"><i class="fa fa-info-circle"></i> 温馨提示：
			<span class="text-strong" style="color:red;"><i id="message"></i></span> -->
		</span> 
		<span class="pull-right">
			
		</span>
	</div>
	<div class="panel-body">
				<div class="cut-header">
			  <div class="page-toolbar row m-b-sm m-t-sm">
   	 	<div class="col-sm-6" style="margin-left:25%">        
           <div class="alert alert-warning" style="padding:6px 12px;"><i class="fa fa-info-circle"></i> 温馨提示：
			当前账号有效期限：<span class="text-strong" style="color:red;">${starttime} ~~ ${endtime}</span>
		</div>        
        </div>    
    </div>
		</div>
		<div class="clearfix"></div>
		<div style="font-size:18px;color:orange;text-align:center;height:500px">
			<span>尊敬的用户，您的账户已到期，如需续用，请联系平台客服！</span>
		</div>
		<div class="text-right">
					</div>
		<!-- <ul ng-if="!accountList" style="text-align:center;width:100%"><span ng-if="!accountList">暂无数据</span></ul> -->
	</div>
	<div class="panel-footer"></div>
</div>
			</div>
		</div>

	<div class="container-fluid footer text-center" role="footer">	
		 
		<br/>
		<span>www.vicmob.com</span>	
	</div>
		</body>
</html>