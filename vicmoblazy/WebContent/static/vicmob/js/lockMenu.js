
//选中被单击的菜单并改变颜色
$(function() {	
	var urlstr = location.href;
	var urlstatus=false;
	$(".left-menu a").each(function () {  
	  if (urlstr.indexOf($(this).prop('href')) > -1&&$(this).prop('href')!='') {	
	    $(this).parent().prop("style","background-color:orange;color:#fff;");
	    $(this).prop("style","color:#fff;");
	    $(this).children().prop("style","color:#fff;");
	    urlstatus = true;
	  } else {
	    $(this).parent().removeProp("style","background-color:orenge;color:#fff;");
	  }
	});
});	