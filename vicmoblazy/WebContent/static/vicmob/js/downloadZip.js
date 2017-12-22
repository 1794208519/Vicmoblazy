function downLoad(type, minaId) {
	if(type == 5){
		$.ajax({
			url : "${ctx}/onlineShop/platform/getPlatForm",
			type : "post",
			dataType : "json",
			data : {"minaId":minaId},
			success : function(data) {
				if(data == null){
					layer.alert('请先选择小程序模板！', {skin:'layui-layer-red',closeBtn:0,icon:7});
				} else if(data.modelNum == null || data.modelNum == ""){
					layer.alert('请先选择小程序模板！', {skin:'layui-layer-red',closeBtn:0,icon:7});
				} else {
					$.ajax({
						url : "/VicmobMINA/vicmob/zip/findMina",
						type : "post",
						dataType : "json",
						data : {"minaId":minaId},
						success : function(data) {
							if(parseInt(data.minaid)>0){//绑定小程序的APPSECRET
								if(data.appsecret != null && data.appsecret!=""){
									downLoadStart(type);//下载	
									layer.closeAll('loading');//去除加载层
								}else{
									layer.alert('请返回主页绑定appSecret！', {skin:'layui-layer-red',closeBtn:0,icon:7});
								}												
							}
						},
						error:function(xhr, type, errorThrown) {
							layer.alert('网络出错啦！', {skin:'layui-layer-red',closeBtn:0,icon:2});
						}
					});
				}
			},
			error:function(xhr, type, errorThrown) {
				layer.alert('网络出错啦！', {skin:'layui-layer-red',closeBtn:0,icon:2});
			}
		});
	} else {
		$.ajax({
			url : "/VicmobMINA/vicmob/zip/findMina",
			type : "post",
			dataType : "json",
			data : {"minaId":minaId},
			success : function(data) {
				if(parseInt(data.minaid)>0){//绑定小程序的APPSECRET
					if(data.appsecret != null && data.appsecret!=""){
						downLoadStart(type);//下载	
						layer.closeAll('loading');//去除加载层
					}else{
						layer.alert('请返回主页绑定appSecret！', {skin:'layui-layer-red',closeBtn:0,icon:7});
					}												
				}
			},
			error:function(xhr, type, errorThrown) {
				layer.alert('网络出错啦！', {skin:'layui-layer-red',closeBtn:0,icon:2});
			}
		});
	}
};


function downLoadStart(type){
	$.ajax({
		type : "POST",
		url : "/VicmobMINA/vicmob/zip/message",
		data : {
			mina_type : type,
		},
		dataType : "json",
		success : function(data) {
			console.log(data);
			// 获取压缩后的路径
			var zipUrl = data.zipUrl;
			window.location.href = zipUrl;
		}
	});
}