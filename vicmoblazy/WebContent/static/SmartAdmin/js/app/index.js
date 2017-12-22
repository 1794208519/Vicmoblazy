
$(document).ready(function () {

    //设定Refresh函数
    $('#refresh').on("click.refresh", function () {
        ezi.msg.confirm("是否立刻刷新当前页面?", "未保存的数据会在刷新操作后丢失,请先保存已修改数据!", function () {
            //触发当前菜单点击事件
            checkURL();
        });
    });

    //远程加载登陆信息
    ezi.getjson(API_ACCOUNT_GETCURRENTLOGIN, function (user) {
        //改为knockout bind - 必须用准确的ID定位到区域,避开Main Content区域
        $("#txtName").text(user.Name);
        $("#txtRoles").text(user.Roles);

        //用于在所有页面中使用的角色名!
        $.Roles = user.Roles;

        //绑定当前登录的菜单项
        $("#left-panel nav").bindMenus(user.Menus);
        checkURL();
        initApp.leftNav();

    });

    //加载项目配置信息
    ezi.getjson(API_APP_GETAPPINFO, function (app) {
        $("#appName").text(app.Name);
        $("#companyName").text(app.CompanyName);
        $("#appVersion").text(app.Name + " 内部版本: " + app.Version.Core);
    });

    //搜索当前的预定信息并弹出警告信息
    setTimeout(ShowOrderNotification, 10000);

})


//Logout函数
function Logout() {
    ezi.msg.confirm("是否确认登出系统?", "", function () {
        ezi.getjson(API_ACCOUNT_LOGOUT, function (result) {
            if (result) window.location = "/login.html";
        });
    });
}

//当前已经显示的提醒Order列表
$.PendingOrderList = [];
function ShowOrderNotification() {
    //绑定预定列表
    ezi.ajax({
        url: API_ORDER_GETPENDING,
        success: function (result) {

            //从已有的列表中对比当前列表,如果已经不存在了,则从已有列表中删除.
            for (var i = $.PendingOrderList.length - 1; i >= 0; i--) {
                var stillPendingOrder = result.filter(function (item) { return $.PendingOrderList[i].Id == item.Id });
                if (stillPendingOrder.length == 0) {
                    //如果当前页面是home.html,也同时删除这个order
                    if (viewModel != undefined && viewModel.RemoveOrder != undefined) {
                        viewModel.RemoveOrder($.PendingOrderList[i]);
                    }
                    $.PendingOrderList.splice(i, 1);
                }
            }

            //首先在返回的Order中寻找,如果在这个列表中存在pendingOrderList里面没有的Order显示提醒.
            var index = 1;
            for (var i in result) {
                var order = result[i];
                var existOrder = $.PendingOrderList.filter(function (item) { return item.Id == order.Id });
                if (existOrder.length == 0) {
                    $.PendingOrderList.push(order);
                    //如果当前页面是home.html,把新的order加入列表
                    if (viewModel != undefined && viewModel.AddOrder != undefined) {
                        viewModel.AddOrder(order);
                    }
                    var orderDes = "<a href='#ajax/home.html'>[" + order.CustomerName + "] " + order.ProviderName + " - " + order.ServiceName+"</a>";
                    ezi.msg.bigbox(orderDes, "请尽快处理客户的预定", index);
                    index++;
                }
            }
     
        },
        complete: function () {
            //无论是否成功,十秒再执行一次
            setTimeout(ShowOrderNotification, 10000);
        },
        error: function () {
            //把Error处理设为空,以免出现报警
        }
    });
}