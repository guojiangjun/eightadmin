var prefix = "/eight/bazi"
$(function () {
    load();
});
var userid = $("#userid").val();

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                pageList: [20, 50, 100, 150, 'all'],
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 20, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                //导出功能设置（关键代码）
                exportDataType: 'all',//'basic':当前页的数据, 'all':全部的数据, 'selected':选中的数据
                showExport: false,  //是否显示导出按钮
                buttonsAlign: "right",  //按钮位置
                exportTypes: ['excel', 'xlsx'],  //导出文件类型，[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf']
                exportOptions: {//导出设置
                    fileName: '八字排盘',//下载文件名称
                    ignoreColumn: [0, 1, 7, 9, 10, 11],  //忽略某一列的索引
                },
                queryParams: function (params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset: params.offset,
                        userid: userid,
                        //userid:$('#searchName').val()
                        // username:$('#searchName').val()
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                    {
                        checkbox: true
                    }, {
                        field: 'userid',
                        title: '用户id'
                    },

                    {
                        field: 'no',
                        title: '序号'
                    }, {
                        field: 'baziid',
                        title: '八字Uid'
                    },

                    {
                        field: 'baziname',
                        title: '命主'
                    },
                    {
                        field: 'male',
                        title: '性别',
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "女";
                            } else {
                                return "男";
                            }
                        }
                    },
                    {
                        field: 'solardatetime',
                        title: '原始输入生成'
                    },
                    {
                        field: 'dateTime',
                        title: '公历出生日期'
                    },
                    {
                        field: 'lunardatetime',
                        title: '农历出生日期'
                    },
                    {
                        field: 'bazidata',
                        title: '八字详细数据',
                        formatter: function (value, index, row) {
                            var image = "<div><a href='#' onclick = 'bazidata(\"" + index.baziid + "\")'>查看数据</a></div>"

                            return image;
                        }
                    },
                    {
                        field: 'birthCity',
                        title: '出生地'
                    }, {
                        field: 'normalTaiyang',
                        title: '平太阳时'
                    },
                    {
                        field: 'realTaiyang',
                        title: '真太阳时'
                    },
                    {
                        field: 'remark',
                        title: '备注'
                    },
                    /*{
field : 'baziconfig',
title : '八字配置'
},*/

                    {
                        field: 'updatetime',
                        title: '更新时间'
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.baziid
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.baziid
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
                                + row.baziid
                                + '\')"><i class="fa fa-key"></i></a> ';
                            return e + d;
                        }
                    }]
            });
}

function reLoad() {
    if ($('#searchName').val() != '') {
        userid = $('#searchName').val();
    }
    $('#exampleTable').bootstrapTable('refresh');
}

function bazidata(baziid) {
    layer.open({
        type: 2,
        title: '八字数据',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['450px', '600px'],
        content: 'http://47.103.214.98:8089/#/pages/dataPage/dataPage?id=' + baziid // iframe的url
    });
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'baziid': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
}

function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['baziid'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}