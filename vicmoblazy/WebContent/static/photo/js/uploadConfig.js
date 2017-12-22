(function() {
	var filePath = $('#fileUrl').val();
	// 单张图片
	var singleImageObjs = document.getElementsByClassName("singleGraph");
	for (var i = 0; i < singleImageObjs.length; i++) {
		var defaultVal = singleImageObjs[i].defaultValue;
		var imgPath = filePath + singleImageObjs[i].defaultValue;
		if(defaultVal.indexOf("http") >= 0 ){
			imgPath = defaultVal;
		} else {
			imgPath = filePath + defaultVal;
		}
		if(singleImageObjs[i].defaultValue != "" && singleImageObjs[i].defaultValue != "null"){
			var imgHtml = '<div id="' + singleImageObjs[i].id + 'img">&nbsp;&nbsp;&nbsp;<img src="' + imgPath
				+ '" style="width:100px; margin-top:10px">' + '&nbsp;&nbsp;<a href="javascript:void(0);" onclick = "singleImgDel('
				+ singleImageObjs[i].id + ')">×</a></div>';

			singleImageObjs[i].parentNode.insertAdjacentHTML('afterend', imgHtml);
		}
		
	}
	// 多张图片
	var multipleImageObjs = document.getElementsByClassName("multipleGraph");
	for (var i = 0; i < multipleImageObjs.length; i++) {
		var imgPaths = multipleImageObjs[i].defaultValue;
		if(imgPaths != "" && imgPaths != "null"){
			var imgPathArray = imgPaths.split("|");
			var imgHtml = '<div id="'+  multipleImageObjs[i].id +'img">';
			for (var j = 1; j < imgPathArray.length; j++) {
				var imgPath = "";
				if(imgPathArray[j].indexOf("http") >= 0 ){
					imgPath = imgPathArray[j];
				} else {
					imgPath = filePath + imgPathArray[j];
				}
				imgHtml = imgHtml +'<div id="'+ multipleImageObjs[i].id + j +'img">&nbsp;&nbsp;&nbsp;<img id="'+ multipleImageObjs[i].id + j +'" src="'+ imgPath +'" style="width:100px; margin-top:10px">'
				+ '&nbsp;&nbsp;<a href="javascript:void(0);" onclick = "multipleImgDel('+ multipleImageObjs[i].id +','+j+')">×</a></div>'
			}
			imgHtml = imgHtml + '</div>';
			multipleImageObjs[i].parentNode.insertAdjacentHTML('afterend', imgHtml);
		}
	}
	// 单张音频
	var singleAudioObjs = document.getElementsByClassName("singleAudio");
	for (var i = 0; i < singleAudioObjs.length; i++) {
		
		if(singleAudioObjs[i].defaultValue != "" && singleAudioObjs[i].defaultValue != "null"){
			var defaultVal = singleAudioObjs[i].defaultValue;
			var audioArray = defaultVal.split("/");
			var audioName = audioArray[audioArray.length -1];
			
			var insertHtml = '<div id="'+ singleAudioObjs[i].id +'file">&nbsp;&nbsp;&nbsp;<span  style="margin-top:10px">'+ audioName +'</span>'
					   + '&nbsp;&nbsp;<a href="javascript:void(0);" onclick = "singleFileDel(' + singleAudioObjs[i].id + ')">×</a></div>';

			singleAudioObjs[i].parentNode.insertAdjacentHTML('afterend', insertHtml);
		}
		
	}
})(jQuery)

// 打开上传弹窗
function lopen(type, objId, num) {
	var layObjs = $('.layui-layer-close');
	if(layObjs.length == 0){
		var fileStr = $("#" + objId).val();
		if(num == 1){
			if (fileStr == "") {
				layOpen(type, objId, num);
			} else {
				layer.msg('已达到上限，删除后再上传！', { icon : 0 });
			}
		} else {
			var fileStrs = fileStr.split('|');
			var len = fileStrs.length - 1;
			if(len < num){
				layOpen(type, objId, num);
			} else {
				layer.msg('已达到上限，删除后再上传！', { icon : 0 });
			}
		}
	}
	
}

function layOpen(type, objId, num){
	$('#upType').val(type);// 将上传类型存到父页面隐藏域
	$('#upid').val(objId);// 将点击按钮识别存到父页面隐藏域
	$('#num').val(num);// 将上传限制存到父页面隐藏域 （表示可以上传图片的数量，大于0）
	var titles = "";
	if (type == 'upImg') {
		titles = "图片";
	} else if (type == 'upMusic') {
		titles = "音乐";
	} else if (type == 'upVideo') {
		titles = "视频";
	}
	layer.open({
		type : 2,
		title : titles,
		area : [ '800px', '700px' ],
		fixed : false, // 不固定
		maxmin : true,
		content : '/VicmobLazyEarn/webpage/modules/tools/fileUpload.jsp'// 打开的页面
	});
	$(".layui-layer-title").addClass("layuiLayerTitle");
}


// 单个文件的删除
function singleFileDel(upObj) {
	var imgId = "#" + upObj.id + "file";
	var id = "#" + upObj.id;
	$(imgId).remove();// 去除img框
	$(id).val("");// 将input框的内容变为空
}


// 多个文件删除
function multipleFileDel(obj, index) {
	var id = obj.id;
	var imgId = id + index;
	var imgObjId = id + index + "file";
	var str = $("#"+imgId).attr("src");
	str = "|" + str.replace($('#fileUrl').val(), "");
	var totleStr = $('#' + id).val();
	totleStr = totleStr.replace(str, "");
	$('#' + id).val(totleStr);
	$('#' + imgObjId).remove();
}

//单张图片的删除
function singleImgDel(upObj) {
	var imgId = "#" + upObj.id + "img";
	var id = "#" + upObj.id;
	$(imgId).remove();// 去除img框
	$(id).val("");// 将input框的内容变为空
}


// 多张图片删除
function multipleImgDel(obj, index) {
	var id = obj.id;
	var imgId = id + index;
	var imgObjId = id + index + "img";
	var str = $("#"+imgId).attr("src");
	str = "|" + str.replace($('#fileUrl').val(), "");
	var totleStr = $('#' + id).val();
	totleStr = totleStr.replace(str, "");
	$('#' + id).val(totleStr);
	$('#' + imgObjId).remove();
}
