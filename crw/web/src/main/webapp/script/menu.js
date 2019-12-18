// 被点中的菜单标红,并默认展开
function showMenu(){
    //获取path路径
    var pathAddress = window.location.pathname;
    // 进行判断
    var alink = $(".list-group a[href*='"+pathAddress+"']");
    alink.css("color","red");
    //去除样式类
    alink.parent().parent().parent().removeClass("tree-closed");
    alink .parent().parent().show();

}