<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <title>
        删除角色权限
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
<div class="x-nav-search">
        <div class="layui-form-pane" style="margin-top: 15px;">
            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <input type="text" name="permissionName" id="permissionName" placeholder="请输入权限名称"
                           autocomplete="off" class="layui-input" value=${searchInfo.str_param_0}>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="url" id="url" placeholder="请输入权限地址" autocomplete="off" class="layui-input"
                           value=${searchInfo.str_param_1}>
                </div>
                <div class="layui-input-inline" style="width:80px">
                    <button class="layui-btn" onclick="search();">搜索</button>
                </div>
            </div>
        </div>
</div>

<div class="x-body">
    <div class="layui-form">
        <input type="hidden" name="roleId" value="${roleId}">
        <table class="layui-table">
            <colgroup>
                <col width="50">
                <col width="150">
                <col width="200">
            </colgroup>
            <thead>
            <tr>
                <th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
                <th>权限名称</th>
                <th>权限地址</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${allPer != null}">
                <c:forEach items="${allPer}" var="item" varStatus="status">
                    <tr>
                        <td>
                            <input type="checkbox" value="${item.id}" name="permissionCheck" lay-skin="primary">
                        </td>
                        <td>${item.permissionName}</td>
                        <td>${item.url}</td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit="" lay-filter="save">提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</div>
<script>

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

        form.on('submit(save)', function(data){
            //获取选中的权限
            var perIds = "";
            $("input:checkbox[name='permissionCheck']:checked").each(function () {
                perIds += $(this).val()+",";
            });
            $.ajax({
                type: "POST",
                url: localhostPath+"/role-per-releation/save-or-delete.htm",
                traditional: true,
                data: {
                    roleId : parseInt(data.field.roleId),
                    perIds : perIds,
                    isSave : false
                },
                dataType: "json",
                success: function(res){
                    if (res.code == 0 ) {
                        layer.alert(res.message, {icon: 1},function () {
                            window.parent.location.reload();
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                        });
                    } else {
                        layer.alert(res.message, {icon: 2});
                    }

                }
            });
            return false;
        });
    });

    function search(){
        var permissionName = $("#permissionName").val();
        var url = $("#url").val();
        var directUrl = localhostPath + "/role-per-releation/del-list.htm?roleId=" + ${roleId} + "&permissionName=" + permissionName + "&url" + url;
        window.location.href = directUrl;
    }

</script>
</body>
</html>