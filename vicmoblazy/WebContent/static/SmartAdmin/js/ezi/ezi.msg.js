/**
* Webezi 的消息显示基本库.
* 基于SmartAdmin框架
*
**/
(function ($, window) {
    'use strict'

    //初始化ezi js基础类
    if (!window.ezi) window.ezi = function () { };

    ezi.msg = function () { };

    //nfc message functions
    ezi.msg.warning = function (message, comment, yesFn) {
        if ($.isEmptyObject(comment)) {
            comment = '请修改数据以后重新提交';
        }
        $.SmartMessageBox({
            title: '<i class="fa fa-warning" style="color:#ffff00"></i> 警告: ' + message,
            content: comment,
            buttons: '[*确认]'
        }, function (buttonPress) {
            if (buttonPress == '确认') {
                if (yesFn) yesFn();
            }
        });
    }

    ezi.msg.businessError = function (message, comment, yesFn) {
        if ($.isEmptyObject(comment)) {
            comment = '请检查数据以后重新提交';
        }
        $.SmartMessageBox({
            title: '<i class="fa fa-exclamation-circle" style="color:#ffff00"></i> 业务警告: ' + message,
            content: comment,
            buttons: '[*确认]'
        }, function (buttonPress) {
            if (buttonPress == '确认') {
                if (yesFn) yesFn();
            }
        });
    }

    ezi.msg.systemError = function (message, comment, yesFn) {
        if ($.isEmptyObject(comment)) {
            comment = '请重试或者联系系统管理员.';
        }
        $.SmartMessageBox({
            title: '<i class="fa fa-times" style="color:#ff0000"></i> 系统错误: ' + message,
            content: comment,
            buttons: '[*确认]'
        }, function (buttonPress) {
            if (buttonPress == '确认') {
                if (yesFn) yesFn();
            }
        });
    }

    ezi.msg.success = function (message, comment, callback) {
        if ($.isEmptyObject(comment)) {
            comment = '操作成功,请点击确认继续.';
        }
        $.SmartMessageBox({
            title: '<i class="fa fa-check" style="color:#008000"></i> 成功: ' + message,
            content: comment,
            buttons: '[*确认]'
        }, function (buttonPress) {
            if (callback) callback();
        });
    };

    ezi.msg.confirm = function (message, comment, yesFn, noFn) {
        if ($.isEmptyObject(comment)) {
            comment = '该操作不能回滚,请确认后执行.';
        }
        $.SmartMessageBox({
            title: '<i class="fa fa-question-circle" style="color:#00800"></i> 确认: ' + message,
            content: comment,
            buttons: '[!取消][*确认]'
        }, function (buttonPress) {
            if (buttonPress == '确认') {
                if (yesFn) yesFn();
            } else {
                if (noFn) noFn();
            }
        });
    }

    //Bigbox messagebox
    ezi.msg.bigbox = function (title, message,index) {
        $.bigBox({
            title: title,
            content: message,
            color: "#C46A69",
            icon: "fa fa-warning shake animated",
            number: index + "",
            timeout: 10000
        });
    }

    //Small box message box
    ezi.msg.smallbox = function (title, message) {
        $.smallBox({
            title: title,
            content: message,
            color: "#5384AF",
            timeout: 5000,
            icon: "fa fa-bell"
        });
    }

})(jQuery, window);
