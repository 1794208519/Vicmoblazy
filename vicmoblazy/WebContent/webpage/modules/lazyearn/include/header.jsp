<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<!-- 头部 -->
<header class="topbar">

	<nav class="navbar top-navbar navbar-expand-md navbar-light">
		<!-- 头部左侧图标logo -->
		<div class="navbar-header">
			<a class="navbar-brand" href="#"> <b><img
					src="${ctxStatic}/asset/images/logo-light-icon.png" alt="homepage"
					class="light-logo"/>
			</b> <span> <img
					src="${ctxStatic}/asset/images/logo-light-text.png"
					class="light-logo" alt="homepage" />
			</span>
			</a>
		</div>
		<!-- 头部右侧部分 -->
		<div class="navbar-collapse">
			<ul class="navbar-nav mr-auto mt-md-0">
				<!-- 控制左侧左右移动 -->
				<li class="nav-item"><a
					class="nav-link sidebartoggler hidden-sm-down text-muted waves-effect waves-dark"
					href="javascript:void(0)"> <i class="ti-menu"></i>
				</a></li>
			</ul>

			<ul class="navbar-nav my-lg-0">
				<!-- 顶部右侧个人中心 -->
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle text-muted waves-effect waves-dark"
					href="" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <img
						src="${ctxStatic}/asset/images/users/1.jpg" alt="user"
						class="profile-pic" /></a>
					<div class="dropdown-menu dropdown-menu-right scale-up">
						<ul class="dropdown-user">
							<li>
								<div class="dw-user-box">
									<div class="u-img">
										<img src="${ctxStatic}/asset/images/users/1.jpg" alt="user">
									</div>
									<div class="u-text">
										<h4>管理员</h4>
										<p class="text-muted">Ncky@vicob.com</p>
									</div>
								</div>
							</li>
							<li role="separator" class="divider"></li>
							<!-- 横线 -->
							<li><a href="#"><i class="ti-user"></i><span style="margin-left: 10px; font-size: 15px;">个人中心</span></a></li>
							<li><a href="#"><i class="ti-settings"></i><span style="margin-left: 10px; font-size: 15px;">权限升级</span></a></li>
							<li role="separator" class="divider"></li>
							<li><a href="${ctx}/logout"><i class="fa fa-power-off"></i><span style="margin-left: 10px; font-size: 15px;">退出系统</span></a></li>
						</ul>
					</div></li>
				<!-- 右侧语言 -->
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle text-muted waves-effect waves-dark"
					href="" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <i class="flag-icon flag-icon-cn"></i></a>
					<div class="dropdown-menu dropdown-menu-right scale-up">
						<a class="dropdown-item" href="#"><i
							class="flag-icon flag-icon-in"></i> India</a> <a
							class="dropdown-item" href="#"><i
							class="flag-icon flag-icon-fr"></i> French</a> <a
							class="dropdown-item" href="#"><i
							class="flag-icon flag-icon-cn"></i> China</a> <a
							class="dropdown-item" href="#"><i
							class="flag-icon flag-icon-de"></i> Dutch</a>
					</div></li>
			</ul>
		</div>
	</nav>
</header>
<!-- 底部 -->
<footer class="footer">版权所有 微炫客信息技术有限公司 © 2017-2020</footer>