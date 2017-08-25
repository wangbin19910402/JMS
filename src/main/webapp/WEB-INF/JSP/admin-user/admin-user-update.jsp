<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <title>
        修改管理员
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
<div class="x-body">
    <form action="" method="post" class="layui-form layui-form-pane">
        <input type="hidden" name="id" value="${adminUser.id}">
        <div class="layui-form-item">
            <label for="userName" class="layui-form-label">
                <span class="x-red">*</span>用户名
            </label>
            <div class="layui-input-inline">
                <input type="text" id="userName" name="userName" required="" lay-verify="required"
                       autocomplete="off" class="layui-input" value="${adminUser.userName}">
            </div>
        </div>
        <div class="layui-form-item">
            <label for="roleId" class="layui-form-label">
                <span class="x-red">*</span>角色
            </label>
            <div class="layui-input-inline">
                <select name="roleId" id="roleId">
                    <option value="">请选择角色</option>
                    <c:forEach items="${roleList}" var="item" varStatus="status">
                        <c:choose>
                            <c:when test="${adminUser.roleId == item.id}">
                                <option value="${item.id}" selected="">${item.roleName}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${item.id}">${item.roleName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label for="phone" class="layui-form-label">
                <span class="x-red">*</span>联系电话
            </label>
            <div class="layui-input-inline">
                <input type="text" id="phone" name="phone" required="" lay-verify="phone"
                       autocomplete="off" class="layui-input" value="${adminUser.phone}">
            </div>
        </div>
        <div class="layui-form-item">
            <label for="email" class="layui-form-label">
                <span class="x-red">*</span>email
            </label>
            <div class="layui-input-inline">
                <input type="text" id="email" name="email" required="" lay-verify="email"
                       autocomplete="off" class="layui-input" value="${adminUser.email}">
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit="" lay-filter="save">提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </form>
</div>
<script>
    layui.use(['form','layer'], function(){
        $ = layui.jquery;
        var form = layui.form()
            ,layer = layui.layer;
        //监听提交
        form.on('submit(save)', function(data){
            $.ajax({
                type: "POST",
                url: localhostPath+"/adminUser/ajax-update.htm",
                data: data.field,
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
</script>
</body>
</html>
