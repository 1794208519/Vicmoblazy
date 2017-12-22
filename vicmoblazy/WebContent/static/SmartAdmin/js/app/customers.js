// PAGE RELATED SCRIPTS
// destroy generated instances
// pagedestroy is called automatically before loading a new page
// only usable in AJAX version!
var pagedestroy = function () {
    viewModel = null;//消亡ViewModel类
    //销毁监视对象
    if (subscriptionProvince) subscriptionProvince.dispose();
    if (subscriptionCity) subscriptionCity.dispose();
    if (agent) agent.dispose();
}

//View Model 类定义
var ViewModel = function () {
    var self = this;

    //是否是新建的Super.-用于个别文字显示,非核心函数
    self.IsNew = function () {
        return self.Core.Id() == 0;
    };

    self.UpdateInfo = function() {
        return self.Core.UpdateUserName() + " (" + self.Core.UpdateDateTime() + ")";
    };
    
    //代理商
    self.AgentList = [];

    //省份列表
    self.ProvinceList = [];
    //城市列表
    self.CityList = ko.observableArray();
    //区域列表
    self.DistrictList = ko.observableArray();

    //核心数据对象 Core -延迟加载
    self.Core = {};

    //空的核心对象,用于绑定空表单
    self.Empty = {};

    self.EditingCustomer = null;

    //保存正在修改的数据原始的省份-用于下拉框验证
    self.OrginalProvince = null;

    //当前正在处理挂失的卡对象
    self.LossCard = null;

    //当前执行充值的客户对象
    self.RechageCustomer = null;

    //核心函数-支持createCrudController
    //列表和编辑界面转换控制
    self.EditMode = ko.observable(false);
    //当前KO业务对象转JS对象
    self.ToJS = function (data) {
        //检查卡号
        //if (self.Core.MemberCardNo() == "") {
        //    ezi.msg.warning("请至少添加一张优惠卡");
        //    return null;
        //}
        //else
        return ezi.ko.getJSObject(self.Core);
    };
    //获取一个业务对象的名称,用于显示
    self.GetName = function (data) {
        return data.Name;
    };
    //用一个业务对象来触发修改事件
    self.Update = function (data) {
        self.EditingCustomer = data;
        self.OrginalProvince = data.Province;
        ko.mapping.fromJS(data, {}, self.Core);//把指定对象数据映射到ViewModel
        $(':password').val("");//清除所有密码框
    };

    //新建一个业务对象
    self.New = function () {
        $(':password').val("");//清除所有密码框
        self.OrginalProvince = self.Empty.Province;
        ko.mapping.fromJS(self.Empty, {}, self.Core);//把空数据映射到ViewModel
    };

    //优惠卡的处理函数
    self.addMemberCard = function () {
        ezi.getjson(API_CUSTOMER_CREATEVIPCARDHISTORY,
               function (data) {
                   self.Core.MemberCardNo.push(data);
                   $('#formEdit').eziValidate("refresh");//重新设定验证
               })
    }

    //简单的删除会员卡
    self.removeMemberCard = function (card) {
        self.Core.MemberCardNo.remove(card);
    };

    //显示挂失会员卡对话框
    self.showLossMemberCard = function (card) {
        self.LossCard = card;
        $('#lossCardModal').showModal({
            destory:false,
            show: function () {
                $('#lossCardModal .modal-title').text("通过短信验证挂失会员卡: " + card.VIPNumber());
                $('#lossCardVerifyCode').val("");
            }
        });
    };

    //处理挂失会员卡对话框
    self.handleLossMemberCard = function () {
        var code = $('#lossCardVerifyCode').val();
        var customer = self.EditingCustomer;
        var lossCard = self.LossCard;
        ezi.msg.confirm("是否确认挂失此卡? 验证码:" + code, "", function () {
            ezi.postjson(API_CUSTOMER_DISABLEDVIPCARD_SMS, { customerId: customer.Id, CardNo: lossCard.VIPNumber(), VerifyCode: code }, function (result) {
                if (result.status) {
                    self.Core.MemberCardNo.remove(self.LossCard);
                    self.EditingCustomer.MemberCardNo.pop();
                    self.LossCard = null;
                    ezi.msg.success("挂失成功");
                    self.EditMode(false);
                } else {
                    ezi.msg.warning(result.message);
                }
            }); //ajax
        });
    };

    //发送验证码
    self.sendLossCardVerifyCode = function () {
        ezi.ajax({
            url: API_CUSTOMER_LOSSCARD_SMS + "?mobile=" + self.Core.Mobile(),
            success: function (result) {
                if (result.status) {
                    ezi.msg.success("验证码已发送到: " + self.Core.Mobile());
                } else {
                    ezi.msg.warning(result.message);
                }
            }
        });//ajax
    };

    //显示充值对话框
    self.showRecharge = function (e) {
        e.preventDefault();
        var $row = $(e.currentTarget).closest("tr");
        var table = $("#dtMain").DataTable();
        var tableRow = table.row($row);
        self.RowIndex = tableRow.index();
        self.RechageCustomer = tableRow.data();

        $('#rechargeModal').showModal({
            destory: false,
            show: function () {
                $('#rechargeModal .modal-title').text("给当前客户充值: " + self.RechageCustomer.Name);
                $('#rechargeAgentSelect').empty();
                $('#rechargeAgentSelect').select2array(self.AgentList, "Id", "Name");
                $("#formRecharge").eziValidate("reset");
            }
        });
    }

    //执行充值对话框
    self.handleRecharge = function(){
        if (!$("#formRecharge").valid()) return;

        var table = $("#dtMain").DataTable();
        var customer = self.RechageCustomer;
        $('#rechargeModal').showModal("hide");
        var agent = $("#rechargeAgentSelect").val();
        var point = $("#rechargeCouponPoint").val();
        ezi.msg.confirm("是否确认给客户[" + self.RechageCustomer.Name + "]充值 " + point+" 点?", "", function () {
            ezi.postjson(API_CUSTOMER_AGENT, { customerId: customer.Id, agentId: agent, amount: point }, function(result) {
                    if (result.status) {
                        table.updateRow(self.RowIndex, result.content);
                    } else {
                        ezi.msg.warning(result.message);
                    }
                    $("#rechargeAgentSelect").val("");
                    $("#rechargeCouponPoint").val("");
                }
            ); //ajax
        });
    }
};
//ViewModel实例
var viewModel = new ViewModel();
//省份和城市的监视对象
var subscriptionProvince, subscriptionCity;
//代理商
var agent;

//构建基于KO的增删改控制引擎
var controller = ezi.createCrudController({
    'viewmodel': viewModel,//ViewModel
    'editform': "#formEdit",//主formID
    'datatable': "#dtMain",//主列表ID
    'deleteurl': API_CUSTOMER_DELETE,//删除对象API
    'saveurl': API_CUSTOMER_SAVE//保存对象API
});

// 入口函数
var pagefunction = function () {
    
    $.when(
            //获取省份信息,由于要加载省份列表,必须在KO绑定前执行!
            ezi.getjson(API_COMMON_GETPROVINCES,
                function(provinces) {
                    viewModel.ProvinceList = provinces;
                }),
                
            //获取代理商列表
            ezi.getjson(API_AGENT_GETALL,
                function (agent) {
                    viewModel.AgentList = agent;
                })
    ).done(
        function() {

            //远程获取新的业务对象,然后绑定为KO对象
            ezi.getjson(API_CUSTOMER_CREATE,
                function(result) {
                    viewModel.Empty = result;
                    viewModel.Core = ko.mapping.fromJS(result);
                    $("#divCore").eziKOBind(viewModel);

                    //初始化验证条件
                    $("#formEdit").eziValidate("normal");
                    $("#formRecharge").eziValidate("normal");
                    
                    //监控省份,来触发省份的选择选择函数
                    subscriptionProvince = viewModel.Core.Province.subscribe(
                        function (newValue) {
                            //获取城市信息,并绑定
                            ezi.getjson(API_COMMON_GETCITIES + "?province=" + escape(newValue),
                                function(cities) {
                                    viewModel.CityList(cities);
                                    if (viewModel.OrginalProvince != newValue) {
                                        viewModel.Core.City("");
                                        viewModel.Core.District("");
                                        viewModel.OrginalProvince = "";
                                    }
                                });
                        });

                    //监控城市,来触发区域的选择选择函数
                    subscriptionCity = viewModel.Core.City.subscribe(
                        function (newValue) {
                            //获取城市信息,并绑定
                            ezi.getjson(API_COMMON_GETDISCTRICTS + "?city=" + escape(newValue),
                                function(districts) {
                                    viewModel.DistrictList(districts);
                                });
                        });
                });
            
            /* 绑定列表 - 远程获取数据,但是假分页 ;*/
            ezi.getjson(API_CUSTOMER_GETALL, function(result) {
                var table = $('#dtMain').eziDataTable({
                    "autoWidth": false,
                    "pageLength": 12,
                    //这里不是ServeSide=false(默认),所以ajax仅仅获取一次数据
                    "data": result,
                    //columns定义获取的数据和列的对应关系
                    "columns": [{ "data": "Name" }, { "data": "IdentityCard" }, { "data": "Position" }, { "data": "Company" }, { "data": "TotalPeople" }, { "data": "TotalOutput" }, { "data": "Address" }, { "data": "Mobile" }, { "data": "MemberCardNo" }, { "data": "Agent" }, { "data": "CouponPoint" }, { "data": "BalanceAmount" }, { "data": "Id" }],
                    "columnDefs": [
                        {
                            // The `data` parameter refers to the data for the cell (defined by the
                            // `data` option, which defaults to the column being worked with, in
                            // this case `data: 0`.
                            "searchable": false,
                            "orderable": false,
                            "render": function(data, type, row, meta) {
                                //meta: row : current row index; col: current column index, data is the data of the cell
                                var content = '<a  href="#" class="btn btn-success btn-xs rechargebtn"><i class="fa fa-money"></i></a> ';
                                content += '<a  href="#" class="btn btn-primary btn-xs editbtn"><i class="fa fa-pencil"></i></a> ';
                                content += '<a href="#" class="btn btn-danger btn-xs deletebtn"><i class="fa fa-minus"></i></a>';
                                return content;
                            },
                            "targets": -1 //最后一格
                        },
                        {
                            "searchable": true,
                            "orderable": false,
                            "render": function (data, type, row, meta) {
                                var content = "";
                                for (var i in data) {
                                    var style = "success";
                                    content += ('<span class="label label-' + style + '">' + data[i].VIPNumber + '</span> ');
                                }
                                return content;
                            },
                            "targets": 8 //第8格
                        },
                        {
                            "searchable": true,
                            "orderable": false,
                            "render": function (data, type, row, meta) {
                                var content = '<ul>';
                                for (var i in data) {
                                    content += '<li><strong>' + data[i].AgentName + '</strong>  充值金额：' + data[i].Amount + '</li>';
                                }
                                content += '</ul>';
                                return content;
                            },
                            "targets": 9 //第9格
                        }
                    ],
                    "initComplete": function(settings, json) {
                        //处理列表加载完毕以后的事件
                        //绑定edit和delete按钮事件
                        $('#divTable').on("click.editbtn", ".editbtn", controller.edit);
                        $('#divTable').on("click.deletebtn", ".deletebtn", controller.delete);
                        $('#divTable').on("click.rechargebtn", ".rechargebtn", viewModel.showRecharge);
                        //加入新建按钮
                        $('#divTable div.dt-new').html('<a href="javascript:controller.create();" class="btn btn-success pull-right"><i class="fa fa-plus"></i> 新建客户</a>');
                    }
                });
            });
        });//done

};

