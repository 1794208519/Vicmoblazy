// PAGE RELATED SCRIPTS

//View Model 类定义
var ViewModel = function () {
    var self = this;

    //是否是新建的Super.-用于个别文字显示,非核心函数
    self.IsNew = function () {
        return self.Core.Id() == 0;
    };

    self.UpdateInfo = function () {
        return self.Core.UpdateUserName() + " (" + self.Core.UpdateDateTime() + ")";
    };

    //从页面参数中获取对应商家的ID,如果从商家列表点击过来,就有这个ID
    self.ProviderId = getPageParam();
    
    //商家类型列表
    self.ProviderTypeList = [];

    //商品类型列表
    self.ServiceTypeList = ko.observableArray();

    self.OrginalProviderType = null;
 
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
        self.Core.ProviderId = self.ProviderId;
        self.OrginalProviderType = data.ParentTypeId;
        ko.mapping.fromJS(data, {}, self.Core);//把指定对象数据映射到ViewModel
        //初始化图片显示
        $("#imgBig").attr("src", data.BigImage);
        $("#imgSmall").attr("src", data.SmallImage);
    };
    //新建一个业务对象
    self.New = function () {
        self.OrginalProviderType = "";
        ko.mapping.fromJS(self.Empty, {}, self.Core);//把空数据映射到ViewModel
        //清除所有图片
        $("#imgBig").attr("src", "");
        $("#imgSmall").attr("src", "");
    };
};
//ViewModel实例
var viewModel = new ViewModel();

//省份和城市的监视对象
var subscriptionProviderType;

// destroy generated instances
// pagedestroy is called automatically before loading a new page
// only usable in AJAX version!
var pagedestroy = function () {
    viewModel = null;//消亡ViewModel类
    if (subscriptionProviderType) subscriptionProviderType.dispose();
}

//构建基于KO的增删改控制引擎
var controller = ezi.createCrudController({
    'viewmodel': viewModel,//ViewModel
    'editform': "#formEdit",//主formID
    'datatable': "#dtMain",//主列表ID
    'deleteurl': API_SERVICE_DELETE,//删除对象API
    'saveurl': API_SERVICE_SAVE//保存对象API
});

// 入口函数
var pagefunction = function () {

    $.when(
        //获取商家大类
        ezi.getjson(API_PROVIDER_GETSINGLEPROVIDER + viewModel.ProviderId,
                function (data) {
                    viewModel.ProviderTypeList = data.TypeArray;
                })
    ).done(function () {

        //远程获取新的业务对象,然后绑定为KO对象
        ezi.getjson(API_SERVICE_CREATE,
            function (result) {
                viewModel.Empty = result;
                result.ProviderId = viewModel.ProviderId;
                viewModel.Core = ko.mapping.fromJS(result);
                $("#divCore").eziKOBind(viewModel);
                //初始化验证条件
                $("#formEdit").eziValidate("normal");

                //初始化图像上传空间
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
                        $("#imgSmall").attr("src", API_FILE_GETIMAGE + "?filename=" + tempName)
                    },
                    progress: function (progress) {
                        $("#progressUploadSmall").css('width', progress + '%');
                    }
                });

                //监控商家大类型,来触发商品小类型的选择函数
                subscriptionProviderType = viewModel.Core.ParentTypeId.subscribe(
                    function (newValue) {
                        //获取小类型信息,并绑定
                        ezi.getjson(API_COMMON_GETPROVIDERSUBTYPES + "?id=" + escape(newValue),
                            function (data) {
                                viewModel.ServiceTypeList(data);
                                if (viewModel.OrginalProviderType != newValue) {
                                    viewModel.Core.Type("");
                                    viewModel.OrginalProviderType = "";
                                }
                            });
                    });

            });

        /* 绑定列表 - 远程获取数据,但是假分页 ;*/
        ezi.getjson(API_SERVICE_GETALL + viewModel.ProviderId, function(result) {
            var table = $('#dtMain').eziDataTable({
                "autoWidth": false,
                "pageLength": 12,
                //这里不是ServeSide=false(默认),所以ajax仅仅获取一次数据
                "data": result,
                //columns定义获取的数据和列的对应关系
                "columns": [{ "data": "SmallImage" }, { "data": "Name" }, { "data": "UnitPrice" }, { "data": "Rebate" }, { "data": "TypeName" }, { "data": "Count" }, { "data": "OrderTotal" }, { "data": "Description" }, { "data": "Id" }],
                "columnDefs": [
                    {
                        // The `data` parameter refers to the data for the cell (defined by the
                        // `data` option, which defaults to the column being worked with, in
                        // this case `data: 0`.
                        "searchable": false,
                        "orderable": false,
                        "render": function(data, type, row, meta) {
                            //meta: row : current row index; col: current column index, data is the data of the cell
                            var content = '<a  href="#" class="btn btn-primary btn-xs editbtn"><i class="fa fa-pencil"></i></a>';
                            content += ' <a href="#" class="btn btn-danger btn-xs deletebtn"><i class="fa fa-minus"></i></a>';
                            return content;
                        },
                        "targets": -1 //最后一格
                    },
                    {
                        "searchable": false,
                        "orderable": false,
                        "render": function(data, type, row, meta) {
                            return '<img src="' + data + '" alt="无" width="20" height="20">';
                        },
                        "targets": 0 //第一格
                    }
                ],
                "initComplete": function(settings, json) {
                    //处理列表加载完毕以后的事件
                    //绑定edit和delete按钮事件
                    $('#divTable').on("click.editbtn", ".editbtn", controller.edit);
                    $('#divTable').on("click.deletebtn", ".deletebtn", controller.delete);

                    //加入新建和返回商家按钮
                    var btns = ' <a href="javascript:controller.create();" class="btn btn-success pull-right" style="margin-left:5px"><i class="fa fa-plus"></i> 新建商品</a>';
                    btns += '<a href="javascript:toProviderList();" class="btn btn-danger pull-right"><i class="fa fa-mail-reply"></i> 返回商家</a>';
                    $('#divTable div.dt-new').html(btns);
                }
            });

        });

    });//done

};

//转移到商品管理界面
var toProviderList = function (e) {
    location.hash = "#ajax/providers.html";
}