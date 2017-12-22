<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>

<!-- 图片相关的隐藏域 -->
	<input type='hidden' name="savePicPath"  value="${savePicPath}/wom/web" id = "savePicPath"/>  
	<input type='hidden' name="fileUrl"  value="${picUrl}" id = "fileUrl"/>  
	<!-- 图片上传类型隐藏域(上传的为音频或者图像等赋值) -->
	<input type="hidden" id="upType" value="" />
	<!-- 图片上传字段隐藏域（一张页面的不同个图片上传的识别赋值） -->
	<input type="hidden" id="upid" value="" />
	<input type="hidden" id="num" value="" />