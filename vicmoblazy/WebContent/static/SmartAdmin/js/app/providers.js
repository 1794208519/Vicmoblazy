// PAGE RELATED SCRIPTS

//View Model 类定义
var ViewModel = function () {
    var self = this;

    //当前用户是否是管理员或者会计
    self.IsAminOrAccount = function () {
        return $.Roles.indexOf("会计") > -1 || $.Roles.indexOf("管理员") > -1;
    };

    //是否是新建.-用于个别文字显示,非核心函数
    self.IsNew = function () {
        return self.Core.Id() == 0;
    };

    self.IsAdmin = function (name) {
        return name == "管理员";
    };

    //是否是新建.-用于个别文字显示,非核心函数
    self.IsAdminEdit = function (name) {
        var isCreate = false;
        if (name == "管理员" && self.Core.Id() == 0) {
            isCreate = true;
        }
        return isCreate;
    };

    self.UpdateInfo = function() {
        return self.Core.UpdateUserName() + " (" + self.Core.UpdateDateTime() + ")";
    };

    //省份列表
    self.ProvinceList = [];
    //城市列表
    self.CityList = ko.observableArray();
    //区域列表
    self.DistrictList = ko.observableArray();

    //商户大类列表
    self.ProviderTypeList = [];
 
    //核心数据对象 Core -延迟加载
    self.Core = {};

    //空的核心对象,用于绑定空表单
    self.Empty = {};
    
    //保存正在修改的数据原始的省份-用于下拉框验证
    self.OrginalProvince = null;

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
        self.OrginalProvince = data.Province;
        ko.mapping.fromJS(data, {}, self.Core);//把指定对象数据映射到ViewModel
        $(':password').val("");//清除所有密码框
        //初始化图片显示
        $("#imgBig").attr("src", data.BigImage);
        $("#imgSmall").attr("src", data.SmallImage);
    };
    //新建一个业务对象
    self.New = function () {
        self.OrginalProvince = self.Empty.Province;
        ko.mapping.fromJS(self.Empty, {}, self.Core);//把空数据映射到ViewModel
        $(':password').val("");//清除所有密码框
        //清除所有图片
        $("#imgBig").attr("src", "");
        $("#imgSmall").attr("src", "");
    };
};
//ViewModel实例
var viewModel = new ViewModel();

//省份和城市的监视对象
var subscriptionProvince, subscriptionCity;

// destroy generated instances
// pagedestroy is called automatically before loading a new page
// only usable in AJAX version!
var pagedestroy = function () {
    viewModel = null;//消亡ViewModel类
    //销毁监视对象
    if (subscriptionProvince) subscriptionProvince.dispose();
    if (subscriptionCity) subscriptionCity.dispose();
}

//构建基于KO的增删改控制引擎
var controller = ezi.createCrudController({
    'viewmodel': viewModel,//ViewModel
    'editform': "#formEdit",//主formID
    'datatable': "#dtMain",//主列表ID
    'deleteurl': API_PROVIDER_DELETE,//删除对象API
    'saveurl': API_PROVIDER_SAVE,//保存对象API
    'deleteSubMessage': "当前商户下所有商品将被全部删除，请谨慎操作!"
});

// 入口函数
var pagefunction = function () {

    $.when(

           //获取省份信息,由于要加载省份列表,必须在KO绑定前执行!
          ezi.getjson(API_COMMON_GETPROVINCES,
                function (provinces) {
                    viewModel.ProvinceList = provinces;
                }),
          //获取商品大类型数据!
          ezi.getjson(API_COMMON_GETPROVIDERTYPES,
                function (data) {
                    viewModel.ProviderTypeList = data;
                })

    ).done(function () {

        //远程获取新的业务对象,然后绑定为KO对象
        ezi.getjson(API_PROVIDER_CREATE,
            function (result) {
                //修复TypeIDBug
                result.TypeId = [];

                viewModel.Empty = result;
                viewModel.Core = ko.mapping.fromJS(result);
                $("#divCore").eziKOBind(viewModel);
                //初始化验证条件
                $("#formEdit").eziValidate("normal");

                //监控省份,来触发省份的选择选择函数
                subscriptionProvince = viewModel.Core.Province.subscribe(
                    function (newValue) {
                        //获取城市信息,并绑定
                        ezi.getjson(API_COMMON_GETCITIES + "?province=" + escape(newValue),
                            function (cities) {
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
                            function (districts) {
                                viewModel.DistrictList(districts);
                            });
                    });

                //初始化图像上传控件
                $("#bigImageUpload").eziFileUpload({
                    url: API_FILE_UPLOAD,
                    done: function (result) {
                        var tempName = result[0].TempName;
                         //把文件绑定到KO对象
                        viewModel.Core.BigImageName(tempName);
                        //保存文件信息,显示图片
                        $("#imgBig").attr("src", API_FILE_GETIMAGE + "?filename=" + tempName)
                    },
                    progress: function (progress) {
                        $("#progressUploadBig").css('width', progress + '%');
                    }
                });

                $("#smallImageUpload").eziFileUpload({
                    url: API_FILE_UPLOAD,
                    done: function (result) {
                        var tempName = result[0].TempName;
                        //把文件绑定到KO对象
                        viewModel.Core.SmallImageName(tempName);
                        //保存文件信息,显示图片
                        $("#imgSmall").attr("src", API_FILE_GETIMAGE + "?filename=" +tempName)
                    },
                    progress: function (progress) {
                        $("#progressUploadSmall").css('width', progress + '%');
                    }
                });

            });

        /* 绑定列表 - 远程获取数据,但是假分页 ;*/
        ezi.getjson(API_PROVIDER_GETALL, function (result) {
            var table = $('#dtMain').eziDataTable({
                "autoWidth": false,
                "pageLength": 12,
                //这里不是ServeSide=false(默认),所以ajax仅仅获取一次数据
                "data": result,
                //columns定义获取的数据和列的对应关系
                "columns": [{ "data": "SmallImage" }, { "data": "Name" }, { "data": "Login" }, { "data": "TypeArray" }, { "data": "Province" }, { "data": "City" }, { "data": "District" }, { "data": "Address" }, { "data": "Phone" }, { "data": "Description", "searchable": false }, { "data": "Id" }],
                "columnDefs": [{
                    // The `data` parameter refers to the data for the cell (defined by the
                    // `data` option, which defaults to the column being worked with, in
                    // this case `data: 0`.
                    "searchable": false,
                    "orderable": false,
                    "render": function (data, type, row, meta) {
                        //meta: row : current row index; col: current column index, data is the data of the cell
                        var content = '<a  href="#" class="btn btn-primary btn-xs editbtn"><i class="fa fa-pencil"></i></a>';
                        content += ' <a href="#" class="btn btn-info btn-xs subbtn"><i class="fa fa-table"></i></a>';
                        content += ' <a href="#" class="btn btn-danger btn-xs deletebtn"><i class="fa fa-minus"></i></a>';
                        return content;
                    },
                    "targets": -1 //最后一格
                },
                {
                    "searchable": false,
                    "orderable": false,
                    "render": function (data, type, row, meta) {
                        return '<img src="' + data + '" alt="无" width="20" height="20">';
                    },
                    "targets": 0 //第一格
                },
                {
                    "searchable": true,
                    "orderable": false,
                    "render": function (data, type, row, meta) {
                        var content = "";
                        for (var i in data) {
                            var style = "primary";
                            content += ('<span class="label label-' + style + '">' + data[i].ProviderTypeName + '</span> ');
                        }
                        return content;
                    },
                    "targets": 3 //第4格
                }
                ],
                "initComplete": function (settings, json) {
                    //处理列表加载完毕以后的事件
                    //绑定edit和delete按钮事件
                    $('#divTable').on("click.editbtn", ".editbtn", controller.edit);
                    $('#divTable').on("click.deletebtn", ".deletebtn", controller.delete);
                    $('#divTable').on("click.subbtn", ".subbtn", toServiceList);
                    //加入新建按钮
                    $('#divTable div.dt-new').html('<a href="javascript:controller.create();" class="btn btn-success pull-right"><i class="fa fa-plus"></i> 新建商户</a>');
                }
            });

        })

    });//done
 
};

//转移到商品管理界面
var toServiceList = function (e) {
    e.preventDefault();
    var $row = $(e.currentTarget).closest("tr");
    var data = $('#dtMain').DataTable().row($row).data();
    location.hash = "#ajax/services.html?" + data.Id;
}

