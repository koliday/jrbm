$(document).ready(function () {

    // 生成微信二维码，工具类，不是我提供，从腾讯下载的
    // 127.0.0.1/loginServlet
    var obj = new WxLogin({
        id:"wxscan",
        appid:"wx7287a60bb700fd21",
        scope:"snsapi_login",
        redirect_uri:"http://www.txjava.cn/wxlogin"
    });
});
