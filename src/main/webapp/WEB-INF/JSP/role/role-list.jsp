<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <title>
        角色列表
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
              <a><cite>角色管理</cite></a>
            </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新"><i class="layui-icon"
                                                                        style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">

    <xblock>
        <button class="layui-btn layui-btn-danger" onclick="delAll();"><i class="layui-icon">&#xe640;</i>批量删除</button>
        <button class="layui-btn" onclick="role_add('添加角色',localhostPath+'/role/add.htm','500','300')"><i class="layui-icon">&#xe608;</i>添加</button>
        <span class="x-right" style="line-height:40px">共有数据：${roleSum} 条</span>
    </xblock>

    <div class="layui-form">
        <table class="layui-table">
            <colgroup>
                <col width="50">
                <col width="150">
                <col width="150">
                <col width="200">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
                <th>角色名</th>
                <th>描述</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${roleList != null}">
                <c:forEach items="${roleList}" var="item" varStatus="status">
                    <tr>
                        <td>
                            <input type="checkbox" value="${item.id}" name="checkRole" lay-skin="primary">
                        </td>
                        <td>${item.roleName}</td>
                        <td>${item.roleDesc}</td>
                        <td class="td-manage">
                            <a title="编辑" href="javascript:;"
                               onclick="role_edit('编辑',localhostPath+'/role/update.htm?id='+${item.id},'500','300')"
                               class="layui-btn layui-btn-small layui-btn-normal" style="text-decoration:none">编辑</a>
                            <a title="新增权限" href="javascript:;"
                               onclick="add_permission('新增权限',localhostPath+'/role-per-releation/add-list.htm?roleId='+${item.id},'700','400')"
                               class="layui-btn layui-btn-small layui-btn-normal" style="text-decoration:none">新增权限</a>
                            <a title="删除权限" href="javascript:;"
                               onclick="del_permission('删除权限',localhostPath+'/role-per-releation/del-list.htm?roleId='+${item.id},'700','400')"
                               class="layui-btn layui-btn-small layui-btn-normal" style="text-decoration:none">删除权限</a>
                            <a title="删除" href="javascript:;" onclick="role_del(this,${item.id})"
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
    layui.use(['laydate', 'element', 'laypage', 'layer', 'form'], function () {
        $ = layui.jquery;//jquery
        laydate = layui.laydate;//日期插件
        lement = layui.element();//面包导航
        laypage = layui.laypage;//分页
        layer = layui.layer;//弹出层

    });

    layui.use('form', function(){
        var $ = layui.jquery, form = layui.form();
        //全选
        form.on('checkbox(allChoose)', function(data){
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
            child.each(function(index, item){
                item.checked = data.elem.checked;
            });
            form.render('checkbox');
        });

    });

    //批量删除提交
    function delAll() {
        var ids = new Array();
        $("input:checkbox[name='checkRole']:checked").each(function () {
            ids.push(parseInt($(this).val()));
        })
        console.log(ids);
        if (ids.length <= 0) {
            layer.msg('请至少选择一项', {icon: 7});
            return;
        }
        layer.confirm('确认要删除吗？', function () {
            $.ajax({
                type: "POST",
                url: localhostPath + "/role/ajax-delete-by-ids.htm",
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
    function role_add(title, url, w, h) {
        x_admin_show(title, url, w, h);
    }

    //编辑
    function role_edit(title, url, w, h) {
        x_admin_show(title, url, w, h);
    }

    //配置权限
    function add_permission(title, url, w, h) {
        x_admin_show(title, url, w, h);
    }

    //配置权限
    function del_permission(title, url, w, h) {
        x_admin_show(title, url, w, h);
    }

    /*删除*/
    function role_del(obj, id) {
        layer.confirm('确认要删除吗？', function (index) {
            $.ajax({
                type: "POST",
                url: localhostPath + "/role/ajax-delete.htm",
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
