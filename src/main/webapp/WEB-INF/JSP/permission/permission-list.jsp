<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <title>
        权限管理
    </title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="/css/x-admin.css" media="all">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <script src="/layui/layui.js" charset="utf-8"></script>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/common.js"></script>
    <script src="/js/x-admin.js"></script>
</head>
<body>
<div class="x-nav">
    <span class="layui-breadcrumb">
      <a><cite>首页</cite></a>
      <a><cite>管理员管理</cite></a>
      <a><cite>权限管理</cite></a>
    </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新"><i class="layui-icon"
                                                                        style="line-height:30px">ဂ</i></a>
</div>

<div class="x-nav-search">
    <div class="layui-form-pane" style="margin-top: 15px;">
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input type="text" name="permissionName" id="permissionName" placeholder="请输入权限名称"
                       autocomplete="off" class="layui-input" value=${pager.searchInfo.str_param_0}>
            </div>
            <div class="layui-input-inline">
                <input type="text" name="url" id="url" placeholder="请输入权限地址" autocomplete="off" class="layui-input"
                       value=${pager.searchInfo.str_param_1}>
            </div>
            <div class="layui-input-inline" style="width:80px">
                <button class="layui-btn" onclick="search();"><i class="layui-icon">&#xe615;</i>搜索</button>
            </div>
        </div>
    </div>
</div>

<div class="x-body">
    <xblock>
        <button class="layui-btn layui-btn-danger" onclick="delAll();"><i class="layui-icon">&#xe640;</i>批量删除</button>
        <button class="layui-btn" onclick="permission_add('添加权限',localhostPath+'/permission/add.htm','500','300')"></i>
            <i class="layui-icon">&#xe608;</i>添加
        </button>
        <span class="x-right" style="line-height:40px">共有数据：${pager.itemSize} 条</span>
    </xblock>

    <div class="layui-form">
        <table class="layui-table">
            <colgroup>
                <col width="50">
                <col width="150">
                <col width="200">
                <col width="100">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
                <th>权限名称</th>
                <th>权限地址</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${pager.items != null}">
                <c:forEach items="${pager.items}" var="item" varStatus="status">
                    <tr>
                        <td>
                            <input type="checkbox" value="${item.id}" name="checkRole" lay-skin="primary">
                        </td>
                        <td>${item.permissionName}</td>
                        <td>${item.url}</td>
                        <td class="td-manage">
                            <a title="编辑" href="javascript:;"
                               onclick="permission_edit('编辑',localhostPath+'/permission/update.htm?id='+${item.id},'500','300')"
                               class="layui-btn layui-btn-small layui-btn-normal" style="text-decoration:none">编辑</a>
                            <a title="删除" href="javascript:;" onclick="permission_del(this,${item.id})"
                               class="layui-btn layui-btn-small layui-btn-danger" style="text-decoration:none">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
    <div id="page"></div>
</div>
<script>

    layui.use('laypage', function () {
        $ = layui.jquery;//jquery
        laypage = layui.laypage;//分页
        groupnum =
        ${pager.pageNum}>=
        5 ? 5 : ${pager.pageNum};

        laypage({
            cont: 'page'//id
            , curr: ${pager.pageIndex}
            , pages: ${pager.pageNum} //总页数
            , skip: true //是否支持跳转
            , groups: groupnum//连续显示分页数
            , jump: function (obj, first) {
                //得到了当前页，用于向服务端请求对应数据
                var curr = obj.curr;
                if (!first) { //一定要加此判断，否则初始时会无限刷新
                    pageJump(curr);
                }
            }
        });
    });


    layui.use('form', function () {
        var $ = layui.jquery, form = layui.form();
        //全选
        form.on('checkbox(allChoose)', function (data) {
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
            child.each(function (index, item) {
                item.checked = data.elem.checked;
            });
            form.render('checkbox');
        });

    });

    function search(){
        var permissionName = $("#permissionName").val();
        var url = $("#url").val();
        var directUrl = localhostPath + "/permission/list.htm?permissionName=" + permissionName + "&url=" + url;
        window.location.href = directUrl;
    }

    //分页跳转
    function pageJump(pageIndex) {
        var permissionName = $("#permissionName").val();
        var url = $("#url").val();
        var directUrl = localhostPath + "/permission/list.htm?permissionName=" + permissionName + "&url=" + url + "&pageIndex=" + pageIndex;
        window.location.href = directUrl;
    }

    //批量删除提交
    function delAll() {
        var ids = new Array();
        $("input:checkbox[name='checkPermission']:checked").each(function () {
            ids.push(parseInt($(this).val()));
        })
        if (ids.length <= 0) {
            layer.msg('请至少选择一项', {icon: 7});
            return;
        }
        layer.confirm('确认要删除吗？', function () {
            $.ajax({
                type: "POST",
                url: localhostPath + "/permission/ajax-delete-by-ids.htm",
                traditional: true,
                data: {
                    ids: ids
                },
                dataType: "json",
                success: function (res) {
                    if (res.code == 0) {
                        layer.alert(res.message, {icon: 1}, function () {
                            window.location.reload();
                        });
                    } else {
                        layer.alert(res.message, {icon: 2});
                    }

                }
            });
        });
    }
    /*添加*/
    function permission_add(title, url, w, h) {
        x_admin_show(title, url, w, h);
    }


    //编辑
    function permission_edit(title, url, w, h) {
        x_admin_show(title, url, w, h);
    }
    /*删除*/
    function permission_del(obj, id) {
        layer.confirm('确认要删除吗？', function (index) {
            $.ajax({
                type: "POST",
                url: localhostPath + "/permission/ajax-delete.htm",
                data: {
                    id: id
                },
                dataType: "json",
                success: function (res) {
                    if (res.code == 0) {
                        layer.alert(res.message, {icon: 1}, function () {
                            window.location.reload();
                        });
                    } else {
                        layer.alert(res.message, {icon: 2});
                    }

                }
            });
        });
    }
</script>
</body>
</html>