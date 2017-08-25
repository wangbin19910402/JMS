var currentPagepath,pathName,pos,localhostPath,projectName ;
$(function(){
    currentPagepath=location.href;
    pathName = window.document.location.pathname;
    pos = currentPagepath.indexOf(pathName);
    localhostPath = currentPagepath.substring(0,pos);
    projectName = pathName.substring(0,pathName.substr(1).indexOf("/")+1);
})

function x_admin_show(title,url,w,h){
    if (title == null || title == '') {
        title=false;
    };
    if (url == null || url == '') {
        url="404.html";
    };
    if (w == null || w == '') {
        w=800;
    };
    if (h == null || h == '') {
        h=($(window).height() - 50);
    };
    layer.open({
        type: 2,
        area: [w+'px', h +'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade:0.4,
        title: title,
        content: url
    });
}

/*关闭弹出框口*/
function x_admin_close(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}