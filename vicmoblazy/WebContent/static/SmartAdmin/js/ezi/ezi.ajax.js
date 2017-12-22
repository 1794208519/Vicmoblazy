/**
* Webezi 的ajax基本库.
* 基于返回的类为AjaxError类,另外需要webezi.msg库的支持
* 特殊参数
   jsondata: 默认为null, 当赋予一个JS对象,让ajax以json方式提交这个对象.可以是数组,在服务器要对应同结构的对象.
   homeiferror: //默认为false,如果是true 如果Ajax报错,允许自动转移转页到首页
**/
(function ($,window) {
    'use strict'

    //$.Support.Cors = true;

    //初始化ezi js基础类
    if (!window.ezi) window.ezi = function () { };
    ezi.homeurl = ezi.homeurl || "/login.html";

    //Ajax 直接获取Json的封装类
    ezi.getjson = function(url,callback){
        return ezi.ajax({
            url: url,
            success : callback
        });
    }

    //Ajax 直接提交Json的封装类
   ezi.postjson = function (url, json, callback) {
       return ezi.ajax({
                url: url,
                success: callback,
                jsondata: json
            });
   }  

    //Aajx ezi封装类
    ezi.ajax = function (options) {
    	
    	 $("#whole").loadingmask("操作中，请稍后。。。");
        //合并参数
        var defaultOpt = {
            type: "GET",
            dataType: "json",
            contentType: "application/x-www-form-urlencoded",
            jsondata: null,
            homeiferror: true//如果Ajax报错,允许转页到首页
        }
 
        options = $.extend(defaultOpt, options);

        //处理jsondata
        if (options.jsondata != null) {
            options.type = "POST";
            //options.contentType = "application/json";
            options.data = options.jsondata;
        }

        return $.ajax({
            type: options.type,
            url: options.url,
            contentType: options.contentType,
            dataType: options.dataType,
            data: options.data,
            //加入该配置允许Ajax在某些浏览器读写Cookie
            xhrFields: { withCredentials: true },
            crossDomain: true == !(document.all),
            beforeSend: function (xhr) {
                if (options.beforeSend) {
                    options.beforeSend(xhr);                    
                }
                
               
            },
            success: function (dataResult, textStatus, xhr) {
                if (options.success) {
                    options.success(dataResult);
                } 
            },
            error: function (error) {
                //自定义处理
                if (options.error != null)
                    options.error(error);
                else
                    ezi.ajaxerror(error,options.homeiferror);
            },
            complete: function (xhr) {
                if (options.complete) {
                    options.complete(xhr);
                }
                
                $('#whole').unloadingmask();
                
            }
        });       
    }    


    //内置的ajax错误处理函数
    ezi.ajaxerror = function (error,homeiferror) {
    	
    	homeiferror=false;        
        //if (typeof homeiferror == undefined) homeiferror = true;
        try {
            //处理ajax的验证返回
            if (error.status == 404) {
                ezi.msg.warning('访问被拒绝', '请重新登录.', function () {
                   if (homeiferror) top.location = ezi.homeurl; //回到首页
                });
                return;
            }

           //一般错误从返回体中获取错误信息
          var obj = JSON.parse(error.responseText);
          if (obj.StatusCode == 401) {
              ezi.msg.warning(obj.Message, '非法访问,请重新登录.', function () {
                    if (homeiferror) top.location = ezi.homeurl; //回到首页
                });
          } else if (obj.StatusCode == 100) {
                  ezi.msg.warning(obj.Message, '逻辑错误,请修改数据以后重新提交.', function () {
                });
            } else
            {
                ezi.msg.systemError(obj.Message, "系统内部错误,请重新尝试" , function () {
                    if (homeiferror) top.location = ezi.homeurl; //回到首页
                });
            }
        } catch (e) {
            alert("系统无法连接,请联系系统管理员.");
            console.log("Core Error occurs: " + e);
            console.log("Response Text: " + error.responseText);
        }
    }


})(jQuery, window);
