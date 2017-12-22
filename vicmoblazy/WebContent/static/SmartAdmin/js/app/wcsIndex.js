	$(document).ready(function() {
		
		checkURL();		
		
		LoadMenu();
	});
	
	function LoadMenu()
	{
		$("#left-panel").find("li.active").each(function() {
			$(this).parents("ul").slideDown(235);
			$(this).parents("ul").parent("li").find("b:first").html("<em class=\"fa fa-minus-square-o\"></em>");
			$(this).parents("ul").parent("li").addClass("open");
		});
	}
	
	function RefreshData() {
		ezi.msg.confirm("您确认要刷新页面？", " ", function() {
			dataListShow();
		});
	}