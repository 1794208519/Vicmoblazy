<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/webpage/include/taglib.jsp"%>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- Favicon icon -->
<link rel="icon" type="image/png" href="${ctxStatic}/asset/images/favicon.png">
<link rel="apple-touch-icon-precomposed" href="${ctxStatic}/asset/images/favicon.png">
<title>微炫客硬件管理系统</title>
<!-- Bootstrap Core CSS -->
<link href="${ctxStatic}/asset/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<%-- <link href="${ctxStatic}/asset/plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet"> --%>
<!-- Page plugins css -->
<%-- <link href="${ctxStatic}/asset/plugins/clockpicker/dist/jquery-clockpicker.min.css" rel="stylesheet"> --%>
<!-- Color picker plugins css -->
<%-- <link href="${ctxStatic}/asset/plugins/jquery-asColorPicker-master/css/asColorPicker.css" rel="stylesheet"> --%>
<!-- Date picker plugins css -->
<link href="${ctxStatic}/asset/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet" type="text/css" />
<!-- Daterange picker plugins css -->
<%-- <link href="${ctxStatic}/asset/plugins/timepicker/bootstrap-timepicker.min.css" rel="stylesheet"> --%>
<%-- <link href="${ctxStatic}/asset/plugins/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet"> --%>
<!-- chartist CSS -->
<link href="${ctxStatic}/asset/plugins/chartist-js/dist/chartist.min.css" rel="stylesheet">
<link href="${ctxStatic}/asset/plugins/chartist-js/dist/chartist-init.css" rel="stylesheet">
<link href="${ctxStatic}/asset/plugins/chartist-plugin-tooltip-master/dist/chartist-plugin-tooltip.css" rel="stylesheet">
<link href="${ctxStatic}/asset/plugins/css-chart/css-chart.css" rel="stylesheet">
<!-- Vector CSS -->
<link href="${ctxStatic}/asset/plugins/vectormap/jquery-jvectormap-2.0.2.css" rel="stylesheet" />
<!-- Calendar CSS -->
<link href="${ctxStatic}/asset/plugins/calendar/dist/fullcalendar.css" rel="stylesheet" />
<link href="${ctxStatic}/asset/plugins/switchery/dist/switchery.min.css" rel="stylesheet" />
<!-- 自定义select -->
<link href="${ctxStatic}/asset/plugins/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<!-- icheck -->
<link href="${ctxStatic}/asset/plugins/icheck/skins/all.css" rel="stylesheet">
<link href="${ctxStatic}/asset/common/css/style.css" rel="stylesheet">
<!-- You can change the theme colors from here -->
<link href="${ctxStatic}/asset/common/css/colors/blue.css" id="theme" rel="stylesheet">
<!-- icon -->
<link href="${ctxStatic}/agentSystem/css/iconfont.css" rel="stylesheet">
<!-- ====================================================================================== -->

<!-- Jquery -->
<script src="${ctxStatic}/asset/plugins/jquery/jquery.min.js"></script>
<script src="${ctxStatic}/asset/common/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctxStatic}/asset/common/js/jquery.validate.js"></script>
<!-- Bootstrap tether Core JavaScript -->
<script src="${ctxStatic}/asset/plugins/bootstrap/js/popper.min.js"></script>
<script src="${ctxStatic}/asset/plugins/bootstrap/js/bootstrap.min.js"></script>
<!--stickey kit -->
<script src="${ctxStatic}/asset/plugins/sticky-kit-master/dist/sticky-kit.min.js"></script>
<script src="${ctxStatic}/asset/plugins/sparkline/jquery.sparkline.min.js"></script>
<!-- Style switcher -->
<script src="${ctxStatic}/asset/plugins/styleswitcher/jQuery.style.switcher.js"></script>
<script src="${ctxStatic}/asset/plugins/switchery/dist/switchery.min.js"></script>

<!-- slimscrollbar scrollbar JavaScript -->
<script src="${ctxStatic}/asset/common/js/jquery.slimscroll.js"></script>
<!--Wave Effects -->
<script src="${ctxStatic}/asset/common/js/waves.js"></script>
<!--Menu sidebar -->
<script src="${ctxStatic}/asset/common/js/sidebarmenu.js"></script>
<!--Custom JavaScript -->
<script src="${ctxStatic}/asset/common/js/custom.js"></script>
<!-- This page plugins -->
<script src="${ctxStatic}/asset/common/js/jasny-bootstrap.js"></script>
<!-- echarts -->
<script src="${ctxStatic}/agentSystem/js/echarts-all.js"></script>
<script src="${ctxStatic}/agentSystem/js/echarts.min.js"></script>

<script src="${ctxStatic}/asset/plugins/moment/moment.js"></script>
<%-- <script src="${ctxStatic}/asset/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script> --%>
<!-- Clock Plugin JavaScript -->
<%-- <script src="${ctxStatic}/asset/plugins/clockpicker/dist/jquery-clockpicker.min.js"></script> --%>
<!-- Color Picker Plugin JavaScript -->
<%-- <script src="${ctxStatic}/asset/plugins/jquery-asColorPicker-master/libs/jquery-asColor.js"></script> --%>
<%-- <script src="${ctxStatic}/asset/plugins/jquery-asColorPicker-master/libs/jquery-asGradient.js"></script> --%>
<%-- <script src="${ctxStatic}/asset/plugins/jquery-asColorPicker-master/dist/jquery-asColorPicker.min.js"></script> --%>
<!-- 自定义select -->
<script src="${ctxStatic}/asset/plugins/bootstrap-select/bootstrap-select.min.js"></script>
<!-- 分页查询 -->
<script src="${ctxStatic}/asset/common/js/jqPaginator.js"></script>
<script src="${ctxStatic}/asset/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<!-- icheck -->
<script src="${ctxStatic}/asset/plugins/icheck/icheck.min.js"></script>
<script src="${ctxStatic}/asset/plugins/icheck/icheck.init.js"></script>
<script src="${ctxStatic}/asset/plugins/bootstrap/js/bootstrap-paginator.min.js"></script>
<!-- layer -->
<script type="text/javascript" src="${ctxStatic}/asset/layer-v3.0.3/layer/layer.js"></script>
<!-- 百度地图 -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=VOnFOTjx069C9fcxufKPg0MZTuAUEpa1"></script>
<!-- chartist chart -->
<script src="${ctxStatic}/asset/plugins/chartist-js/dist/chartist.min.js"></script>
<script src="${ctxStatic}/asset/plugins/chartist-plugin-tooltip-master/dist/chartist-plugin-tooltip.min.js"></script>
<script src="${ctxStatic}/asset/plugins/echarts/echarts-all.js"></script>
<!-- Vector map JavaScript -->
<script src="${ctxStatic}/asset/plugins/vectormap/jquery-jvectormap-2.0.2.min.js"></script>
<script src="${ctxStatic}/asset/plugins/vectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- Calendar JavaScript -->
<script src="${ctxStatic}/asset/plugins/moment/moment.js"></script>
<script src='${ctxStatic}/asset/plugins/calendar/dist/fullcalendar.min.js'></script>
<script src="${ctxStatic}/asset/plugins/calendar/dist/jquery.fullcalendar.js"></script>
<script src="${ctxStatic}/asset/plugins/calendar/dist/cal-init.js"></script>
<!-- sparkline chart -->
<script src="${ctxStatic}/asset/common/js/dashboard4.js"></script>