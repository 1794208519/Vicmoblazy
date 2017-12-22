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

    self.UpdateInfo = function () {
        return self.Core.UpdateUserName() + " (" + self.Core.UpdateDateTime() + ")";
    };
    
    //所偶Role的ListItem{id,text}的数组
    self.RoleList = ko.observableArray();

    //获取Role的显示名
    self.GetRoleName = function (id) {
        var filterRole = self.RoleList().filter(function (item) { return item.id == id; })
        return filterRole[0] ? filterRole[0].text : "";
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
        //检查Role
        if (self.Core.Roles() == "") {
            ezi.msg.warning("请至少选择一个角色");
            return null;
        }
        else
            return ezi.ko.getJSObject(self.Core);
    };
    //获取一个业务对象的名称,用于显示
    self.GetName = function (data) {
        return data.Name;
    };
    //用一个业务对象来触发修改事件
    self.Update = function (data) {
        ko.mapping.fromJS(data, {}, self.Core);//把指定对象数据映射到ViewModel
        $(':password').val("");//清除所有密码框
    };
    //新建一个业务对象
    self.New = function () {
        ko.mapping.fromJS(self.Empty, {}, self.Core);//把空数据映射到ViewModel
        $(':password').val("");//清除所有密码框
    };
};

//ViewModel实例
var viewModel = new ViewModel();

//构建基于KO的增删改控制引擎
var controller = ezi.createCrudController({
    'viewmodel': viewModel,//ViewModel
    'editform': "#formEdit",//主formID
    'datatable': "#dtMain",//主列表ID
    'deleteurl': API_USER_DELETE,//删除对象API
    'saveurl': API_USER_SAVE//保存对象API
});

// 入口函数
var pagefunction = function () {

    //远程获取新的业务对象,然后绑定为KO对象
    ezi.getjson(API_USER_CREATE,
        function (result) {
            viewModel.Empty = result;
            viewModel.Core = ko.mapping.fromJS(result);
            $("#divCore").eziKOBind(viewModel);
            //初始化验证条件
            $("#formEdit").eziValidate("normal");
        });

    $.when(
        //绑定Roles
        ezi.getjson(API_USER_GETALLROLE, function (result) {
            viewModel.RoleList(result);
        })
    ).done(function () {

        /* 绑定列表 - 远程获取数据,但是假分页 ;*/
        ezi.getjson(API_USER_GETALL, function (result) {

            var table = $('#dtMain').eziDataTable({
                "autoWidth": false,
                //这里不是ServeSide=false(默认),所以ajax仅仅获取一次数据
                "data": result,
                //columns定义获取的数据和列的对应关系
                "columns": [{}, { "data": "Login" }, { "data": "Name" }, { "data": "Email" }, { "data": "QQ" }, { "data": "Mobile" }, { "data": "Roles" }, { "data": "Id" }],
                "columnDefs": [{
                    // The `data` parameter refers to the data for the cell (defined by the
                    // `data` option, which defaults to the column being worked with, in
                    // this case `data: 0`.
                    "searchable": false,
                    "orderable": false,
                    "render": function (data, type, row, meta) {
                        //meta: row : current row index; col: current column index, data is the data of the cell
                        var content = '<a  href="#" class="btn btn-primary btn-xs editbtn"><i class="fa fa-pencil"></i></a> ';
                        content += '<a href="#" class="btn btn-danger btn-xs deletebtn"><i class="fa fa-minus"></i></a>';
                        return content;
                    },
                    "targets": -1 //最后一格
                },
                {
                    "searchable": false,
                    "orderable": false,
                    "render": function (data, type, row, meta) {
                        return '<img src="/img/male.png" alt="" width="20">';
                    },
                    "targets": 0 //第一格
                },
                {
                    "searchable": true,
                    "orderable": false,
                    "render": function (data, type, row, meta) {
                        var content = "";
                        for (var i in data) {
                            var style = data[i] == "admin" ? "warning" : "success";
                            content += ('<span class="label label-' + style + '">' + viewModel.GetRoleName(data[i]) + '</span> ');
                        }
                        return content;
                    },
                    "targets": 6 //第6格
                }
                ],
                "initComplete": function (settings, json) {
                    //处理列表加载完毕以后的事件
                    //绑定edit和delete按钮事件
                    $('#divTable').on("click.editbtn", ".editbtn", controller.edit);
                    $('#divTable').on("click.deletebtn", ".deletebtn", controller.delete);

                    //加入新建按钮
                    $('#divTable div.dt-new').html('<a href="javascript:controller.create();" class="btn btn-success pull-right"><i class="fa fa-plus"></i> 新建用户</a>');
                }
            });
        })
    });//done

};

