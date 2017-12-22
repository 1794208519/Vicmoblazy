/**
* Ezi 的File Upload基本库.
* 存放一些比较通用的函数
*
**/
(function ($, window, document) {
    'use strict'

    //通过一个file控件,来绑定其上传文件的组件功能
    //options 包括
    //   url: 上传路径  start() 开始函数  done(result) 结束函数 progress(progress) 进度函数
    $.fn.eziFileUpload = function (options) {

        //var iframe = false;
        //if ($.browser.msie && $.browser.version < 10) {
        //    iframe = true;
        //}

        this.fileupload({
            url: options.url,
            dataType: 'json',
            autoUpload: false,
            //iframe: iframe,
            add: function (e, data) {
                //此处可以获得添加文件的一些信息name,size,type
                var file = data.files[0];
                if (file.size > 4194304) {
                    ezi.msg.warning("不能上传超过4MB的文件.");
                    uploadok = false;
                } else {
                    //$("#fileNote").text("大小: " + file.size + " bytes; 类型:" + file.type);
                    data.submit();
                }
            },
            start: function (e, data) {
                if (options.start) options.start();
            },
            done: function (e, data) {
                //上传完成
                //注意data和之前的data相同,里面也有file列表,data.result就是WebResponse返回的信息,比如api的返回内容.
                //可以使用data.result就是服务器返回的结果对象
                if (options.done) options.done(data.result);
            },
            progressall: function (e, data) {
                //更新上传状态
                var progress = parseInt(data.loaded / data.total * 100, 10);
                if (options.progress) options.progress(progress);
            },
            fail: function (e, data) {
                //失败处理-包括放开保存按钮
                ezi.msg.warning("文件上传失败,请重新选择文件.");
            }
        });//end of fileupload

        //设置用户验证兼容
        this.fileupload('option', {
            xhrFields: {
                withCredentials: true
            }
        });
    };

})(jQuery, window, document);
