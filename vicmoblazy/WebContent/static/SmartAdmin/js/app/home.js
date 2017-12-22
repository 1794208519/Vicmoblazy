// PAGE RELATED SCRIPTS
// destroy generated instances
// pagedestroy is called automatically before loading a new page
// only usable in AJAX version!
var pagedestroy = function () {
    viewModel = null;//消亡ViewModel类
}

//View Model 类定义
var ViewModel = function () {
    var self = this;
    //当前Role的真实名
    self.RoleNames = $.Roles;
    //当前时间
    self.CurrentDate = new Date().format("yyyy年MM月dd日");
    //当前用户信息
    self.Current = {};
    //核心数据对象 Core -延迟加载
    self.Core = {};
    //列表和编辑界面转换控制
    self.EditMode = ko.observable(false);
    //预定列表
    self.OrderList = ko.observableArray();
    //当前需要处理的Order
    self.Order = {};
    //添加一个新的Order
    self.AddOrder = function (order) {
        var existingOrder = self.OrderList().filter(function (item) { return item.Id == order.Id});
        if (existingOrder.length  == 0)
            self.OrderList.unshift(order);
    }
    //删除一个已经存在的Order
    self.RemoveOrder = function (order) {
        var existingOrder = self.OrderList().filter(function (item) { return item.Id == order.Id });
        if (existingOrder.length > 0)
            self.OrderList.remove(existingOrder[0]);
    }
    //打开预定处理对话框
    self.ShowOrderDialog = function (order) {
        self.Order = order;
        $.get("ajax/order-modal.html", function (html) {
            var $modal = $(html);
            $modal.showModal({
                show: function () {
                    //绑定Order数据
                    $modal.find(".modal-dialog").eziKOBind(self);
                }
            });
        });
    }
    //确认预定
    self.ConfirmOrder = function () {
        var order = self.Order;
        var orderDes = "[" + order.CustomerName + "] " + order.ProviderName + " - " + order.ServiceName;
        ezi.msg.confirm("是否确认该预定: " + orderDes, "", function () {
           
            ezi.ajax({
                url: API_ORDER_CONFIRM + "?orderid=" + order.Id,
                success: function (result) {
                    if (result.status) {
                        self.OrderList.remove(order);
                        ezi.msg.smallbox(orderDes + "<p><strong>已确认!</strong></p>");
                    } else {
                        ezi.msg.warning(result.message);
                    }
                }
            });//ajax
        });
    }
    //取消预定
    self.CancelOrder = function () {
        var order = self.Order;
        var orderDes = "[" + order.CustomerName + "] " + order.ProviderName + " - " + order.ServiceName;
        ezi.msg.confirm("是否取消该预定: " + orderDes, "", function () {

            ezi.ajax({
                url: API_ORDER_CANCEL + "?orderid=" + order.Id,
                success: function (result) {
                    if (result.status) {
                        self.OrderList.remove(order);
                        ezi.msg.smallbox(orderDes + "<p><strong>已取消!</strong></p>");
                    } else {
                        ezi.msg.warning(result.message);
                    }
                }
            });//ajax
          
        });
    }
};

//ViewModel实例
var viewModel = new ViewModel();

// 入口函数
var pagefunction = function () {
    //设置当前页面的导航(没有菜单)
    drawBreadCrumb(["首页", "工作台"]);

    $.when(
    //远程获取新的业务对象,然后绑定为KO对象
    ezi.getjson(API_USER_GETCURRENT,
        function (result) {
            viewModel.Current = ko.mapping.fromJS(result);
            viewModel.Core = ko.mapping.fromJS(result);
            $("#divCore").eziKOBind(viewModel);
            //初始化验证条件
            $("#formEdit").eziValidate("normal");
        })
    ).done(function () {
        //绑定预定列表
        ezi.getjson(API_ORDER_GETPENDING,
          function (result) {
              viewModel.OrderList(result);
          })
    });
};

//进入当前用户的编辑界面
var editCurrentUser = function () {
    viewModel.EditMode(true);
}

//保存当前用户
var saveCurrentUser = function (event) {

    if (!$("#formEdit").valid()) return;
    var data = ezi.ko.getJSObject(viewModel.Core);//从ViewModel中获取获取当前填写的数据

    //如果这个对象返回不是NULL,才进行保存
    if (data) {
        //开始触发保存行为
        //是新建行为或者对象不相等
        ezi.postjson(API_USER_SAVE, data, function (result) {
            if (result.status) {
                ezi.msg.success("当前用户信息更新成功!", "", function () {
                    ko.mapping.fromJS(result.content, {}, viewModel.Current);
                    $("#txtName").text(result.content.Name);//更新外页的用户名
                    viewModel.EditMode(false);
                });
            }
            else
                ezi.msg.warning(result.message);
        });//postjson
    }//if data
}

//从用户的编辑界面返回工作台
var backToHome = function () {
    viewModel.EditMode(false);
}


