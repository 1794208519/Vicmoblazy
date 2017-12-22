<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
</head>
<body>
	<div class="divControl">
		<div class="card" style="margin-bottom: -0px;border-bottom: 0px;">

			<ul class="nav nav-tabs customtab" role="tablist">
				<li class="nav-item" onclick="jump(0)"><a class="nav-link"
					data-toggle="tab" href="" role="tab" aria-expanded="true"
					id="wxDataCenter" target="myIframe"> <span class="hidden-sm-up"><i
							class="ti-home"></i></span> <span class="hidden-xs-down">微信数据交互中心</span>
				</a></li>
				<li class="nav-item" onclick="jump(1)"><a class="nav-link"
					data-toggle="tab" href="" role="tab" aria-expanded="false"
					id="qqDataCenter" target="myIframe"> <span class="hidden-sm-up"><i
							class="ti-user"></i></span> <span class="hidden-xs-down">QQ数据交互中心</span>
				</a></li>
			</ul>
		</div>
		<iframe frameborder="0" class="mframe"
			style="width: 100%; height: 100%;" id="myIframe" name="myIframe"></iframe>
	</div>
	<script type="text/javascript">
		$(function() {
			//先调下数据(默认为微信第一页开始，每页十个)
			jump(0);
		});
		//懒赚菜单点击
		function jump(type) {
			if (type == 0) {
				$("a").removeClass("active");
				// 添加active
				$('#wxDataCenter').addClass("active");
				window.myIframe.location.href = '${ctx}/lazyearn/AutoWXDefaultData/jumpList';
			}
			if (type == 1) {
				$("a").removeClass("active");
				// 添加active
				$('#qqDataCenter').addClass("active");
				window.myIframe.location.href = '${ctx}/lazyearn/AutoQQDefaultData/jumpList';
			}
		}
	</script>
</body>
</html>