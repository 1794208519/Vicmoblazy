<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
<meta charset="UTF-8">
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/photo/css/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/photo/css/style.css" />
<script src="${ctxStatic}/asset/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/asset/layer-v3.0.3/layer/layer.js"></script>
<script type="text/javascript" src="${ctxStatic}/photo/js/webuploader.js"></script>
<script type="text/javascript" src="${ctxStatic}/photo/js/upload.js"></script>
</head>
<body>
	 <div id="wrapper">
		 <div id="container">
				<div id="uploader" >
					 <div class="queueList">
						<div id="dndArea" class="placeholder">
							<div id="filePicker"></div>
							<p></p>
							<br>
						</div>
					 </div> 
					 <div class="statusBar" style="display: none;">
						<div class="progress">
							<span class="text">0%</span>
							<span class="percentage"></span>
						</div>
						<div class="info"></div>
						<div class="btns">
							<div id="filePicker2"></div>
							<div class="uploadBtn">开始上传</div>
						</div>
					</div> 
				</div>
		  </div>
	</div>
</body>

</html>