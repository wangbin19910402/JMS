<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>
        登陆页面
    </title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/x-admin.css" media="all">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <script src="/layui/layui.js" charset="utf-8"></script>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/common.js"></script>
    <script src="/js/x-admin.js"></script>
</head>

<body style="background-color: #393D49">
<div class="x-box">
    <div class="x-top">
    </div>
    <div class="x-mid">
        <div class="x-avtar">
            <img src="./images/logo.png" alt="">
        </div>
        <div class="input">
            <form name="input" action="/adminUser/login.htm" method="post">
                <div class="layui-form-item x-login-box">
                    <label for="userName" class="layui-form-label">
                        <i class="layui-icon">&#xe612;</i>
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="userName" name="userName" required="" lay-verify="userName"
                               autocomplete="off" placeholder="用户名" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item x-login-box">
                    <label for="password" class="layui-form-label">
                        <i class="layui-icon">&#xe628;</i>
                    </label>
                    <div class="layui-input-inline">
                        <input type="password" id="password" name="password" required="" lay-verify="password"
                               autocomplete="off" placeholder="密码" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item" id="loginbtn">
                    <button class="layui-btn" onclick="ajaxLogin();" lay-submit="">
                        登 录
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<p style="color:#fff;text-align: center;">wangbin test shiro </p>

<script>
    function ajaxLogin() {
        var userName = $("#userName").val();
        if (userName == '') {
            layer.alert("请输入用户名", {icon: 2});
            return;
        }
        var password = $("#password").val();
        if (password == '') {
            layer.alert("箐输入密码", {icon: 2});
            return;
        }

        $("form").submit();
    }

    layui.use(['layer'], function () {
        $ = layui.jquery;
        var layer = layui.layer;
    });
</script>
</body>

</html>