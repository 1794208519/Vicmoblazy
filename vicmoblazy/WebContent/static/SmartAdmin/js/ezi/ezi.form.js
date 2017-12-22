/**
* Ezi 的一些Form元素的封装类基本库.
* 一般基于JqueryUI和Bootstrap的一些组件
*
**/
(function ($, window, document) {

    'use strict'

    //支持普通的selectbox.
    //use  $("").select2array(array);
    //array is something like :  [{id=1,text='Name1',group=''} ....];
    $.fn.selectarray = function (array) {
        var $this = $(this);
        var title = $this.attr("title");
        if (title) $this.append('<option value="" class="text-muted">' + title + '</options>');
        //calculate the group of the array
        var groups = [];
        for (var i in array) if (groups.indexOf(array[i].group) == -1)groups.push(array[i].group);
        //make the options
        if (groups.length == 1)
            for (var i in array) $this.append('<option value="' + array[i].id + '">' + array[i].text + '</options>');
        else if (groups.length > 1) 
            for (var g in groups) {
                var optgroup = $('<optgroup label="' + groups[g] + '"></optgroup>');
                var subarray = array.filter(function (item) { return item.group == groups[g]; });
                for (var i in subarray) optgroup.append('<option value="' + subarray[i].id + '">' + subarray[i].text + '</options>');
                $this.append(optgroup);
            }
    }

    //auto complete extension, need auto complete support bu jquery UI.
    //use  $("").autocompletearray(data);
    //data is something like :  ["c++", "java", "php", "coldfusion", "javascript", "asp", "ruby"];
    $.fn.autocompletearray =  function (data) {
        var $this = $(this);
        var opt = $.extend(
            { "minLength": 0, "select": function (event, ui) { $this.attr("value", ui.item.value); alert($this.val()); } }, { source: data });
        var result = $this.autocomplete(opt);
        //Add arrow to the control for show list for all.
        $('<i class="icon-append fa fa-chevron-down" style="cursor:pointer" onclick="$(this).siblings(\'input\').autocomplete(\'search\', \'\')"></i>').insertBefore($this);
        return result;
    }

    //select2 extension, need select2 support .
    //use  $("").select2array(array);
    //array is something like :  [{id=1,text='Name1',group=''} ....];
    $.fn.select2array = function (array,id,text) {
        var $this = $(this);
        id = id || "id";
        text = text || "text";

        if (array == undefined)
            return $this.select2({ width: '100%', allowClear: true });
        else if ($this.is("select")) {
            var title = $this.attr("title");
            if (title) $this.append('<option value="" class="text-muted">' + title + '</options>');

            //calculate the group of the array
            var groups = [];
            for (var i in array) if (groups.indexOf(array[i].group) == -1) groups.push(array[i].group);
            //make the options
            if (groups.length == 1)
                for (var i in array) $this.append('<option value="' + array[i][id] + '">' + array[i][text] + '</options>');
            else if (groups.length > 1) {
                for (var g in groups) {
                    var optgroup = $('<optgroup label="' + groups[g] + '"></optgroup>');
                    var subarray = array.filter(function (item) { return item.group == groups[g]; });
                    for (var i in subarray) optgroup.append('<option value="' + subarray[i][id] + '">' + subarray[i][text] + '</options>');
                    $this.append(optgroup);
                }
            }
            return $this.select2({ width: '100%', allowClear: true });
    }
        else
            return $this.select2({ data: array, width: '100%', allowClear: true});
    }

    //显示加载中的按钮,loading: true为显示加载状态,false为恢复原状态. loadingtext为加载的文字显示.
    $.fn.toggleLoading = function (loading, loadingtext) {
        var $this = $(this);
        var text = loadingtext || "加载中...";
        if (loading) {
            $this.data("_html", $this.html());
            $this.html("<i class='fa fa-refresh fa-spin'></i> " + text);
            $this.addClass("disabled");
        } else {
            var html = $this.data("_html");
            if (html) $this.html(html);
            $this.removeClass("disabled");
            $this.removeData("_html");
        }
    }

    //显示modal对话框.
    //options: { show,shown,hide,hidden} 对应四个事件
    //{destory: bool} 为true的时候隐藏销毁,否则保留
    $.fn.showModal = function (options) {
        var $this = $(this);
        if ($.type(options) === "string") {
            $this.modal(options);
        } else {
            options = $.extend({ destory: true }, options);

            if (options.show) $this.on('show.bs.modal', options.show);
            if (options.shown) $this.on('shown.bs.modal', options.shown);
            if (options.hide) $this.on('hide.bs.modal', options.hide);
            $this.on('hidden.bs.modal', function () {
                if (options.hidden) options.hidden();
                if (options.destory) $this.remove();
            });

            $this.modal({
                keyboard: false,
                backdrop: false,
                show: true
            });
        }
    }

    //禁用/启用一个form对象,参数为true为禁用
    $.fn.toggleDisable = function (disable) {
        var $this = $(this);
        if (disable)
            $this.addClass("disabled");
        else
            $this.removeClass("disabled");
    }

    //daterangepicker2中文封装
    $.fn.daterangepicker2 = function (options,callback) {
        var $this = $(this);
        var defaultOpt = {
            // startDate: moment().startOf('day'),
            //endDate: moment(),
            minDate: '2015-01-01',    //最小时间
            maxDate: moment(), //最大时间为今日
            dateLimit: {
                days: 365
            }, //起止时间的最大间隔
            showDropdowns: true,
            showWeekNumbers: false, //是否显示第几周
            timePicker: true, //是否显示小时和分钟
            timePickerIncrement: 60, //时间的增量，单位为分钟
            timePicker12Hour: false, //是否使用12小时制来显示时间
            //ranges: {
            //    //'最近1小时': [moment().subtract('hours',1), moment()],
            //    '今日': [moment().startOf('day'), moment()],
            //    '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
            //    '最近7日': [moment().subtract('days', 6), moment()],
            //    '最近30日': [moment().subtract('days', 29), moment()]
            //},
            linkedCalendars:false,
            opens: 'right', //日期选择框的弹出位置
            buttonClasses: ['btn btn-default'],
            applyClass: 'btn-small btn-primary blue',
            cancelClass: 'btn-small',
            format: 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
            separator: ' to ',
            locale: {
                applyLabel: '确定',
                cancelLabel: '取消',
                fromLabel: '起始时间',
                toLabel: '结束时间',
                customRangeLabel: '自定义',
                daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                    '七月', '八月', '九月', '十月', '十一月', '十二月'],
                firstDay: 1
            }
        };
        options = $.extend(defaultOpt, options);
        $this.daterangepicker(options, callback);
    }
 
})(jQuery, window, document);
