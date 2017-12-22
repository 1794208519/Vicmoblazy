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

    //空的核心对象,用于处理确认和取消
    self.Order = {};

    //当前编辑的行索引
    self.RowIndex = 0;
  
    //打开预定处理对话框
    self.ShowOrderDialog = function (e) {
        e.preventDefault();
        //利用当前控件的父tr定位行元素是性能最高的方式
        var $row = $(e.currentTarget).closest("tr");
        var table = $("#dtMain").DataTable();
        var tableRow = table.row($row);
        self.RowIndex = tableRow.index();
        self.Order = tableRow.data();

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
        var table = $("#dtMain").DataTable();
        var order = self.Order;
        var orderDes = "[" + order.CustomerName + "] " + order.ProviderName + " - " + order.ServiceName;
        ezi.msg.confirm("是否确认该预定: " + orderDes, "", function () {

            ezi.ajax({
                url: API_ORDER_CONFIRM + "?orderid=" + order.Id,
                success: function (result) {
                    if (result.status) {
                        order.Status = "确认";
                        table.updateRow(self.RowIndex, order);
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
        var table = $("#dtMain").DataTable();
        var order = self.Order;
        var orderDes = "[" + order.CustomerName + "] " + order.ProviderName + " - " + order.ServiceName;
        ezi.msg.confirm("是否取消该预定: " + orderDes, "", function () {

            ezi.ajax({
                url: API_ORDER_CANCEL + "?orderid=" + order.Id,
                success: function (result) {
                    if (result.status) {
                        order.Status = "取消";
                        table.updateRow(self.RowIndex, order);
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
    
    $.when(
    ).done(
        /* 绑定列表 - 远程获取数据,但是假分页 ;*/
        //ezi.getjson(API_ORDER_GETALL, function (result)
        function(){
            var table = $('#dtMain').eziDataTable({
                "autoWidth": false,
                //这里不是ServeSide=false(默认),所以ajax仅仅获取一次数据
                "pageLength": 12,
                //"data": [],
                "ajax": {
                    "url": API_ORDER_GETALL,
                    "type": "GET",
                    "dataSrc": '',
                     "xhrFields": { "withCredentials": true }//跨域必须的设置
                 },
                //columns定义获取的数据和列的对应关系
                "columns": [{ "data": "CustomerName" }, { "data": "CustomerMobile" }, { "data": "ProviderName" }, { "data": "ServiceName" }, { "data": "SubmitTime","type":"date" }, { "data": "OrderTime" }, { "data": "Quantity" }, { "data": "TotalPeople" }, { "data": "Status" }, { "data": "Id" }],
                "columnDefs": [{
                    // The `data` parameter refers to the data for the cell (defined by the
                    // `data` option, which defaults to the column being worked with, in
                    // this case `data: 0`.
                    "searchable": false,
                    "orderable": false,
                    "render": function (data, type, row, meta) {
                        //meta: row : current row index; col: current column index, data is the data of the cell
                        var content = '<button class="btn btn-primary btn-xs viewbtn"><i class="fa fa-search"></i> 处理</button> ';
                        return content;
                    },
                    "targets": -1 //最后一格
                },
                {
                    "searchable": true,
                    "orderable": false,
                    "render": function (data, type, row, meta) {
                        var style = "primary";
                        if (data == "确认") style = "success";
                        if (data == "取消") style = "danger";
                        if (data == "关闭") style = "warning";
                        return '<span class="label label-'+style+'">' + data + '</span> ';
                      },
                        "targets": -2 //第-2格
                }
                ],
                "initComplete": function (settings, json) {

                    //处理列表加载完毕以后的事件
                    $('#divTable').on("click.viewbtn", ".viewbtn", viewModel.ShowOrderDialog);

                    //加入三个状态按钮按钮
                    var btns = '<button onclick="fliterOrders(\'关闭\');" class="btn btn-warning pull-right" style="margin-left:5px" id="btnStatusClose">关闭</button>';
                    btns += '<button onclick="fliterOrders(\'取消\');" class="btn btn-danger pull-right" style="margin-left:5px" id="btnStatusCancel">取消</button>';
                    btns += '<button onclick="fliterOrders(\'确认\');" class="btn btn-success pull-right" style="margin-left:5px"  id="btnStatusConform">确认</button>';
                    btns += '<button onclick="fliterOrders(\'开始\');" class="btn btn-primary pull-right" style="margin-left:5px"  id="btnStatusOrder">开始</button>';
                    btns += '<button onclick="fliterOrders(\'\');" class="btn btn-default pull-right disabled"  id="btnStatusAll">全部</button>';

                    //添加时间选择框
                    btns += '<div class="input-group"><span class="input-group-addon"><i class="fa fa-calendar"></i></span><input class="form-control" id="dateRange" type="text" placeholder="预定时间范围" style="width:200px"></div>';
                    $('#divTable div.dt-new').html(btns);

                    //绑定预定时间框
                    $("#dateRange").daterangepicker2({
                            timePicker: false,
                            timePickerIncrement: 30,
                            format: "YYYY-MM-DD HH:mm"
                        },function(start, end, label) {//格式化日期显示框
                            reloadOrdersByDateRange(start, end);
                        }
                    );
                }
            });
   
        }
        //)//ezi.getjson
    );//done

}

//根据状态显示过滤的列表
var fliterOrders = function(status)
{
    var table = $("#dtMain").DataTable();
     table.column( -2 ).search(status).draw();
  
    $("#btnStatusClose").toggleDisable(status == "关闭");
    $("#btnStatusCancel").toggleDisable(status == "取消");
    $("#btnStatusConform").toggleDisable(status == "确认");
    $("#btnStatusOrder").toggleDisable(status == "开始");
    $("#btnStatusAll").toggleDisable(status == "");
}

//根据选择的时间区间过滤列表
var reloadOrdersByDateRange = function (start,end) {
    //这里建议重新读数据了 
    var startDate = start.format("YYYY-MM-DD HH:mm:ss");
    var endDate = end.format("YYYY-MM-DD HH:mm:ss");
    //alert(startDate + " - " + endDate);

    var url = API_ORDER_GETALL + "?startTime=" + startDate + "&endTime=" + endDate;
    var table = $("#dtMain").DataTable();
    table.ajax.url(url).load();
}

