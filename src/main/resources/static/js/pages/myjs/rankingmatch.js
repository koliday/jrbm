$(document).ready(function () {




    if(window.WebSocket){
        socket=new WebSocket("ws://localhost:9999/websocket");
        socket.onopen=function(event){

        }
        socket.onmessage = function(event) {
            var datas=event.data;
            if(datas<1){
                alert("匹配错误！");
            }else{
                $("#startmatching-btn").css("visibility", "hidden");

                $("#opponent").text("您的对手为："+datas+"，是否确认进入比赛？");
                $("#opponent").css("visibility", "visible");
                $("#match-confirm-btn").css("visibility", "visible");
            }
            clearInterval(timer);
            $("#startmatching-btn").attr("class","btn btn-block btn-outline-success");
            $("#startmatching-btn").text("开始匹配");
            status=0;
        }
        socket.onclose=function(event){

        }
    }else{
        alert("浏览器不支持WebSocket哦！");
    }

    var timer;
    var status=0;//0-未匹配，1-匹配中（未悬浮），2-匹配中（悬浮）
    $("#startmatching-btn").hover(function () {
        if(status==1){
            $(this).attr("class","btn btn-block btn-danger");
            $(this).text("停止匹配");
            status=2;
        }
    },function () {
        if(status==2){
            $(this).attr("class","btn btn-block btn-outline-danger");
            $("#startmatching-btn").text("匹配中："+time);
            status=1;
        }
    });
    $("#startmatching-btn").click(function () {
        if(status==1 || status==2){
            togglematching();
            clearInterval(timer);
            $(this).attr("class","btn btn-block btn-outline-success");
            $(this).text("开始匹配");
            status=0;
            return;
        }
        togglematching();
        time="";
        $(this).text("匹配中："+time);
        status=1;
        $(this).attr("class","btn btn-block btn-outline-danger");

        initime=0;
        timer=setInterval(starttiming,1000);
    });

    var initime=0;
    var time="00:00";
    function starttiming() {
        initime++;
        var minute=Math.floor(initime / 60);
        var second=initime % 60;
        time="";
        if(minute == 0){
            time+="00:";
        }else if(minute<10){
            time+="0"+minute+":";
        }else{
            time+=minute+":";
        }
        if(second == 0){
            time+="00";
        }else if(second<10){
            time+="0"+second;
        }else{
            time+=second;
        }
        if(status==1)
            $("#startmatching-btn").text("匹配中："+time);
    }

    function togglematching() {
        $.ajax({
            type: "POST",
            url: "http://localhost:8888/startmatching",
            data: {

            },
            dataType: "json",
            success: function (data) {
                if(data<1){
                    alert("加入匹配错误！");
                }else{
                    socket.send(data);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.statusText);
            }
        });
    }



    $("#match-confirm-btn").click(function () {
        $.ajax({
            type: "POST",
            url: "http://localhost:8888/getteamplayer",
            data: {

            },
            dataType: "json",
            success: function (data) {
                //加载主力
                for(var i=0;i<5;i++){
                    $("#home-starter").prepend("<div class=\"kt-notification-v2\">\n" +
                        "                                        <a href=\"#\" class=\"kt-notification-v2__item\">\n" +
                        "                                            <div class=\"kt-notification-v2__item-icon\">\n" +
                        "                                                <i class=\"flaticon-bell kt-font-success\"></i>\n" +
                        "                                            </div>\n" +
                        "                                            <div class=\"kt-notification-v2__itek-wrapper\">\n" +
                        "                                                <div class=\"kt-notification-v2__item-title\">\n" +
                        "\n" +data[i].basicPlayerDTO.chname+
                        "                                                </div>\n" +
                        "                                                <div class=\"kt-notification-v2__item-desc\">\n" +
                        "\n" +data[i].basicPlayerDTO.enname+
                        "                                                </div>\n" +
                        "                                            </div>\n" +
                        "                                        </a>\n" +
                        "\n" +
                        "                                    </div>")
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.statusText);
            }
        });
    });
});


