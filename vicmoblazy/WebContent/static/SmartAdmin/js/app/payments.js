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

    //是否是新建的Super.-用于个别文字显示,非核心函数
    self.IsNew = function () {
        return self.Core.Id() == 0;
    };

    self.PayProvider = null;

    //打款弹出框
    self.showPayModal = function(e) {
        e.preventDefault();
        //利用当前控件的父tr定位行元素是性能最高的方式
        var $row = $(e.currentTarget).closest("tr");
        var table = $("#dtMain").DataTable();
        var tableRow = table.row($row);
        self.RowIndex = tableRow.index();
        self.PayProvider = tableRow.data();
        
        $('#payModal').showModal({
            destory: false,
            show: function () {
                $('#ProviderName').text(self.PayProvider.ProviderName);
                if (self.PayProvider.AccountNumber != null) {
                    $('#AccountNumber').text(self.PayProvider.AccountNumber);
                }
                if (self.PayProvider.AccountName != null) {
                    $('#AccountName').text(self.PayProvider.AccountName);
                }
                $('#ActualPay').val(self.PayProvider.RebatePay);
                $("#formRecharge").eziValidate("reset");
            }
        });
    }

    //打款
    self.PayMoney = function (e) {
        if (!$("#formRecharge").valid()) return;
        $('#payModal').showModal("hide");
        var table = $("#dtMain").DataTable();
        var provider = self.PayProvider;
        var actualPay = $("#ActualPay").val();
        ezi.msg.confirm("请确认是否为商户 [" + provider.ProviderName + "]，帐号 [" + provider.AccountNumber + "]，开户名 [" + provider.AccountName + "] 打款 [" + actualPay + "] 元？", "", function () {
            ezi.postjson(API_PAYMENT_PAYTOPROVIDER, { PaymentID: provider.Id, ActualFee: actualPay },
                function (result) {
                    if (result.status) {
                        table.updateRow(self.RowIndex, result.content);
                        ezi.msg.smallbox("<p><strong>已打款!</strong></p>");
                    } else {
                        ezi.msg.warning(result.message);
                    }
                }); //ajax
        });
    };

    //确认预定
    self.SearchData = function () {
        debugger;
        var table = $("#dtMain").DataTable();
        var name = $("#searchName").val();
        table.column(2).search(name).draw();
    }

    //核心数据对象 Core -延迟加载
    self.Core = {};

    //空的核心对象,用于绑定空表单
    self.Empty = {};

    //核心函数-支持createCrudController
    //列表和编辑界面转换控制
    self.EditMode = ko.observable(false);
    //当前KO业务对象转JS对象
    self.ToJS = function (data) {
        return ezi.ko.getJSObject(self.Core);
    };
    //获取一个业务对象的名称,用于显示
    self.GetName = function (data) {
        return data.Name;
    };
    //用一个业务对象来触发修改事件
    self.Update = function (data) {
        ko.mapping.fromJS(data, {}, self.Core);//把指定对象数据映射到ViewModel
    };
    //新建一个业务对象
    self.New = function () {
        ko.mapping.fromJS(self.Empty, {}, self.Core);//把空数据映射到ViewModel
    };
};
//ViewModel实例
var viewModel = new ViewModel();

//构建基于KO的增删改控制引擎
var controller = ezi.createCrudController({
    'viewmodel': viewModel,//ViewModel
    'datatable': "#dtMain"//主列表ID
});

// 入口函数
var pagefunction = function () {
    
    $.when(
    ).done(
        /* 绑定列表 - 远程获取数据,但是假分页 ;*/
        //ezi.getjson(API_PAYMENT_GETALL, function (result) {
        function(){
            var table = $('#dtMain').eziDataTable({
                "autoWidth": false,
                //"pageLength" : 20,
                //这里不是ServeSide=false(默认),所以ajax仅仅获取一次数据
                "ajax": {
                    "url": API_PAYMENT_GETALL,
                    "type": "GET",
                    "dataSrc": '',
                     "xhrFields": { "withCredentials": true }//跨域必须的设置
                },
                //columns定义获取的数据和列的对应关系
                "columns": [{ "data": "CustomerName" }, { "data": "CustomerMobile" }, { "data": "ProviderName" }, { "data": "ServiceName" }, { "data": "PayTime" }, { "data": "PayPoint" }, { "data": "Rebate" }, { "data": "FullBank" }, { "data": "RebatePay" }, { "data": "ActualPay" }, { "data": "Status" }, { "data": "GeneratedStatus" }, { "data": "GeneratedData" }, { "data": "Id" }],
                "columnDefs": [
                    {
                        "searchable": false,
                        "orderable": true,
                        "render": function (data, type, row, meta) {
                            var content = '<span class="label label-warning"> ￥' + data + '</span> ';
                            return content;
                        },
                        "targets": 5 
                    },
                    {
                        "searchable": false,
                        "orderable": true,
                        "render": function (data, type, row, meta) {
                            var content = '<span class="label label-primary"> ￥' + data + '</span> ';
                            return content;
                        },
                        "targets": 8
                    },
                    {
                        "searchable": false,
                        "orderable": true,
                        "render": function (data, type, row, meta) {
                            var content = '<span class="label label-success"> ￥' + data + '</span> ';
                            return content;
                        },
                        "targets": 9
                    },
                    {
                        "searchable": true,
                        "orderable": false,
                        "render": function (data, type, row, meta) {
                            var style = "danger";
                            var value = "未打款";
                            if (data == "Y") {
                                style = "success";
                                value = "已打款";
                            }
                            return '<span class="label label-' + style + '"> ' + value + '</span> ';
                        },
                        "targets": 11
                    },
                    {
                        // The `data` parameter refers to the data for the cell (defined by the
                        // `data` option, which defaults to the column being worked with, in
                        // this case `data: 0`.
                        "searchable": false,
                        "orderable": false,
                        "render": function (data, type, row, meta) {
                            if (row.GeneratedStatus == "N") {
                                //meta: row : current row index; col: current column index, data is the data of the cell
                                var content = '<button class="btn btn-primary btn-xs paymoney"><i class="fa fa-money"></i> 打款</button> ';
                                return content;
                            } else {
                                return "";
                            }
                        },
                        "targets": -1 //最后一格
                    },
                ],
                "initComplete": function (settings, json) {
                    //处理列表加载完毕以后的事件
                    $('#divTable').on("click.paymoney", ".paymoney", viewModel.showPayModal);

                    //加入三个状态按钮按钮
                    var btns = '<button onclick="fliterPayment(\'未打款\');" class="btn btn-danger pull-right" style="margin-left:5px" id="btnStatusN">未打款</button>';
                    btns += '<button onclick="fliterPayment(\'已打款\');" class="btn btn-success pull-right" style="margin-left:5px" id="btnStatusY">已打款</button>';
                    btns += '<button onclick="fliterPayment(\'\');" class="btn btn-default pull-right disabled" style="margin-left:5px" id="btnStatusAll">全部</button>';
                    btns += '<button onclick="download();" class="btn btn-default pull-right"  id="btnDownload">导出</button>';

                    //添加时间选择框
                    btns += '<div class="input-group"><span class="input-group-addon"><i class="fa fa-calendar"></i></span><input class="form-control" id="dateRange" type="text" placeholder="预定时间范围" style="width:200px"></div>';
                    $('#divTable div.dt-new').html(btns);

                    //绑定预定时间框
                    $("#dateRange").daterangepicker2({
                            timePicker: false,
                            timePickerIncrement: 30,
                            format: "YYYY-MM-DD HH:mm"
                        },function(start, end, label) {//格式化日期显示框
                            reloadPaymentByDateRange(start, end);
                        }
                    );
                }
            });
   
        }
    );//done

};

//根据状态显示过滤的列表
var fliterPayment = function (status) {
    var table = $("#dtMain").DataTable();
    table.column(11).search(status).draw();
    if (status == "未打款") {
        payStatus = 'N';
    }
    else if (status == "已打款") {
        payStatus = 'Y';
    } else {
        payStatus = '';
    }
    $("#btnStatusN").toggleDisable(status == "未打款");
    $("#btnStatusY").toggleDisable(status == "已打款");
    $("#btnStatusAll").toggleDisable(status == "");
}

//根据选择的时间区间过滤列表
var reloadPaymentByDateRange = function(start, end) {
    //这里建议重新读数据了 
    var startDate = start.format("YYYY-MM-DD HH:mm:ss");
    var endDate = end.format("YYYY-MM-DD HH:mm:ss");
    startTime = startDate;
    endTime = endDate;
    
    //alert(startDate + " - " + endDate);

    var url = API_PAYMENT_GETALL + "?startTime=" + startDate + "&endTime=" + endDate;
    var table = $("#dtMain").DataTable();
    table.ajax.url(url).load();
    fliterPayment('');
}

var currentDate = new Date();
var startTime = currentDate.getFullYear() + "-" + (currentDate.getMonth() + 1) + "-" + currentDate.getDate() + " 0:0:0";
var endTime = currentDate.getFullYear() + "-" + (currentDate.getMonth() + 1) + "-" + currentDate.getDate() + " 23:59:59";
var payStatus = "";
var payId = "";
download = function () {
    payId = "";
    var table = $('#dtMain').DataTable();
    var rows = table.rows({ search: 'applied' }).data();
    if (rows.length === 0) {
        ezi.msg.warning("没有数据可以导出，重新选择！");
        return;
    }
    for (var i = 0; i < rows.length; i++) {
        payId += rows[i].Id + ",";
    }
    var start = startTime.substr(startTime.lastIndexOf('-') + 1, 2);
    var end = endTime.substr(endTime.lastIndexOf('-') + 1, 2);
    if (start === end) {
        downloadFile();
    } else {
        ezi.msg.confirm("您导出的时间区限大于一天，是否继续？", "", function () {
        downloadFile();
        });
    }
};

downloadFile = function() {
    ezi.ajax({
        url: API_PAYMENT_DOENLOAD + "?startTime=" + startTime + "&endTime=" + endTime + "&status=" + payStatus + "&id=" + payId.substring(0, payId.length - 1),
        success: function (result) {
            if (result.status) {
                var file = result.content;
                $('#download').attr('href', API_APP_BASE + file);
                $('#spDown').click();
                ezi.msg.smallbox("<p><strong>导出成功！</strong></p>");
            } else {
                ezi.msg.warning(result.message);
            }
        }
    }); //ajax
}