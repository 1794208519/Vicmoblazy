//记住上次打开的页面
$(document).ready(function() {
	//默认选中第一个
	  $("#sidebarnav").children("li:first-child").addClass("active");
	  $("#sidebarnav").children("li:first-child").children("a:first-child").attr("aria-expanded", true);
	  //默认打开硬件管理首页
	  jumpHd(0);
	  document.getElementById("hdManagerFirst").click();
	  if (document.getElementById("sidebarnav").firstChild.nextSibling.lastChild.firstChild.firstChild != null) {
		   //有下级菜单的点击
		  document.getElementById("sidebarnav").firstChild.nextSibling.lastChild.firstChild.firstChild.click();
	  } else {
		  //没有下级菜单的点击					
		  document.getElementById("sidebarnav").firstChild.nextSibling.firstChild.firstChild.click();					
	  }				
});
//懒赚菜单点击
function jump(type) {
	if (type == 0) {
		$("a").removeClass("active");
		// 添加active
		$('#myAccount').addClass("active");
		var innerTitle = document.getElementById('lazyEarn').innerHTML;
		var innerText = document.getElementById('myAccount').innerHTML;
		$("#indexTitle").html(innerTitle);
		$("#indexTip").html(innerText);
	}
	if (type == 1) {
		$("a").removeClass("active");
		// 添加active
		$('#myAddress').addClass("active");
		var innerTitle = document.getElementById('lazyEarn').innerHTML;
		var innerText = document.getElementById('myAddress').innerHTML;
		$("#indexTitle").html(innerTitle);
		$("#indexTip").html(innerText);
	}
	if (type == 2) {
		$("a").removeClass("active");
		// 添加active
		$('#dataCenter').addClass("active");
		var innerTitle = document.getElementById('lazyEarn').innerHTML;
		var innerText = document.getElementById('dataCenter').innerHTML;
		$("#indexTitle").html(innerTitle);
		$("#indexTip").html(innerText);
	}
	if (type == 3) {
		$("a").removeClass("active");
		// 添加active
		$('#IntelligentResponse').addClass("active");
		var innerTitle = document.getElementById('lazyEarn').innerHTML;
		var innerText = document.getElementById('IntelligentResponse').innerHTML;
		$("#indexTitle").html(innerTitle);
		$("#indexTip").html(innerText);
	}
	if (type == 4) {
		$("a").removeClass("active");
		// 添加active
		$('#cityManager').addClass("active");
		var innerTitle = document.getElementById('lazyEarn').innerHTML;
		var innerText = document.getElementById('cityManager').innerHTML;
		$("#indexTitle").html(innerTitle);
		$("#indexTip").html(innerText);
	}
	if (type == 5) {
		$("a").removeClass("active");
		// 添加active
		$('#whiteList').addClass("active");
		var innerTitle = document.getElementById('lazyEarn').innerHTML;
		var innerText = document.getElementById('whiteList').innerHTML;
		$("#indexTitle").html(innerTitle);
		$("#indexTip").html(innerText);
	}
}
//微粉菜单点击
function jumpW(type) {
	if (type == 0) {
		$("a").removeClass("active");
		// 添加active
		$('#accountManager').addClass("active");
		var innerTitle = document.getElementById('weChat').innerHTML;
		var innerText = document.getElementById('accountManager').innerHTML;
		$("#indexTitle").html(innerTitle);
		$("#indexTip").html(innerText);
	}
}
//硬件管理菜单点击
function jumpHd(type) {
	if (type == 0) {
		/* var html = "<a href=\"${ctx}/lazyearn/HardWareManager/jumpList\" id=\"hdManagerFirst\" target=\"myIframe\" value=\"硬件管理首页\">硬件管理首页</a>";
		$('#hdManagerClick').append(html); */
		$("a").removeClass("active");
		// 添加active
		$('#hdManagerFirst').addClass("active");
		var innerTitle = document.getElementById('hdManager').innerHTML;
		var innerText = document.getElementById('hdManagerFirst').innerHTML;
		$("#indexTitle").html(innerTitle);
		$("#indexTip").html(innerText);
	}
}
