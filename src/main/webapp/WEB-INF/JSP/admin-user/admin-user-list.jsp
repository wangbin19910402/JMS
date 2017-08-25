<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <meta charset="utf-8">
    <title>
        管理员管理
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
      <a><cite>管理员管理</cite></a>
    </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新"><i class="layui-icon"
                                                                        style="line-height:30px">ဂ</i></a>
</div>

<div class="x-nav-search">
    <div class="layui-form">
        <div class="layui-form-pane" style="margin-top: 15px;">
            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <select name="roleId" id="roleId" class="layui-unselect layui-form-select">
                        <option value="">请选择角色</option>
                        <c:forEach items="${roleList}" var="item" varStatus="status">
                            <c:choose>
                                <c:when test="${item.id == pager.searchInfo.int_param_0}">
                                    <option value="${item.id}" selected="">${item.roleName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${item.id}">${item.roleName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="userName" id="userName" placeholder="请输入用户名"
                           autocomplete="off" class="layui-input" value=${pager.searchInfo.str_param_0}>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="phone" id="phone" placeholder="请输入手机号" autocomplete="off"
                           class="layui-input"
                           value=${pager.searchInfo.str_param_1}>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="email" id="email" placeholder="请输入email" autocomplete="off"
                           class="layui-input"
                           value=${pager.searchInfo.str_param_2}>
                </div>

                <div class="layui-input-inline" style="width:80px">
                    <button class="layui-btn" onclick="search();"><i class="layui-icon">&#xe615;</i>搜索</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="x-body">
    <xblock>
        <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon">&#xe640;</i>批量删除</button>
        <button class="layui-btn" onclick="admin_add('添加用户',localhostPath+'/adminUser/add.htm','500','400')">
            <i class="layui-icon">&#xe608;</i>添加
        </button>
        <span class="x-right" style="line-height:40px">共有数据：${pager.itemSize} 条</span></xblock>
    <div class="layui-form">
        <table class="layui-table">
            <colgroup>
                <col width="3%">
                <col width="7%">
                <col width="10%">
                <col width="10%">
                <col width="12%">
                <col width="7%">
                <col width="12%">
                <col width="12%">
                <col width="25%">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th><input type="checkbox" name="" value="" lay-skin="primary" lay-filter="allChoose"></th>
                <th>用户名</th>
                <th>手机</th>
                <th>邮箱</th>
                <th>角色</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>更新时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${pager.items != null}">
                <c:forEach items="${pager.items}" var="item" varStatus="status">
                    <tr>
                        <td>
                            <input type="checkbox" value="${item.id}" name="checkAdmin" lay-skin="primary">
                        </td>
                        <td>${item.userName}</td>
                        <td>${item.phone}</td>
                        <td>${item.email}</td>
                        <td>${item.roleName}</td>
                        <td class="td-status">
                            <c:choose>
                                <c:when test="${item.state == 0}">
                                    <span class="layui-btn layui-btn-normal layui-btn-mini">正常</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="layui-btn layui-btn-normal layui-btn-mini">不正常</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatDate value="${item.createdTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td><fmt:formatDate value="${item.updatedTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td class="td-manage">
                            <a title="编辑" href="javascript:;"
                               onclick="admin_edit('编辑',localhostPath+'/adminUser/update.htm?id='+${item.id},'500','400')"
                               class="layui-btn layui-btn-small layui-btn-normal" style="text-decoration:none">编辑</a>
                            <a title="删除" href="javascript:;" onclick="admin_del(this,${item.id})"
                               class="layui-btn layui-btn-small layui-btn-danger" style="text-decoration:none">删除</a>
                            <a title="重置密码" href="javascript:;" onclick="password_reset(this,${item.id})"
                               class="layui-btn layui-btn-small layui-btn-normal" style="text-decoration:none">重置密码</a>
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
        form.render();
    });
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

    function search(){
        var roleId = $("#roleId").val();
        var userName = $("#userName").val();
        var phone = $("#phone").val();
        var email = $("#email").val();
        var directUrl = localhostPath + "/adminUser/list.htm?roleId=" + roleId + "&userName=" + userName + "&phone=" + phone + "&email=" + email;
        window.location.href = directUrl;
    }

    //分页跳转
    function pageJump(pageIndex) {
        var roleId = $("#roleId").val();
        var userName = $("#userName").val();
        var phone = $("#phone").val();
        var email = $("#email").val();
        var directUrl = localhostPath + "/adminUser/list.htm?roleId=" + roleId + "&userName=" + userName + "&phone=" + phone + "&email=" + email + "&pageIndex=" + pageIndex;
        window.location.href = directUrl;
    }

    /*添加*/
    function admin_add(title, url, w, h) {
        x_admin_show(title, url, w, h);
    }

    //编辑
    function admin_edit(title, url, id, w, h) {
        x_admin_show(title, url, w, h);
    }
    /*删除*/
    function admin_del(obj, id) {
        layer.confirm('确认要删除吗？', function (index) {
            $.ajax({
                type: "POST",
                url: localhostPath + "/adminUser/ajax-delete.htm",
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

    /*重置密码*/
    function password_reset(obj, id) {
        layer.confirm('确认要重置密码吗？', function (index) {
            $.ajax({
                type: "POST",
                url: localhostPath + "/adminUser/ajax-password-reset.htm",
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


    //批量删除提交
    function delAll() {
        var ids = new Array();
        $("input:checkbox[name='checkAdmin']:checked").each(function () {
            ids.push(parseInt($(this).val()));
        })
        if (ids.length <= 0) {
            layer.msg('请至少选择一项', {icon: 7});
            return;
        }
        layer.confirm('确认要删除吗？', function () {
            $.ajax({
                type: "POST",
                url: localhostPath + "/adminUser/ajax-delete-by-ids.htm",
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
</script>

</body>
</html>
