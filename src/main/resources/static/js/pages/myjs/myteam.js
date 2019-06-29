$(document).ready(function () {
    $(".player-exchange").click(function () {
        var upid=$(this).data("upid");
        var name=$(this).data("name");
        $("#exchange_from").text(name);
        $("#exchange_from").data("fromid",upid);
        $("#exchange_to").empty();
        $.ajax({
            type: "POST",
            url: "http://localhost:8888/getteamplayer",
            data:{},
            dataType: "json",
            success: function (data) {
                $.each(data,function (idx,obj) {
                    if(obj.upid!=upid)
                        $("#exchange_to").append("<option value='"+obj.upid+"'>"+obj.basicPlayerDTO.chname+"</option>")
                })
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.statusText);
            }
        });
    });

    //换人
    $("#exchange-btn").click(function () {
        var fromid=$("#exchange_from").data("fromid");
        var toid=$("#exchange_to").val();
        $.ajax({
            type: "POST",
            url: "http://localhost:8888/exchangeplayer",
            data:{
                exchangefrom:fromid,
                exchangeto:toid
            },
            dataType: "json",
            success: function (data) {
                if(data==1){
                    alert("换人成功！");
                    window.location.reload();
                }else{
                    alert("换人失败！");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.statusText);
            }
        });
    });

    //解雇
    $(".fire-btn").click(function () {
        var upid=$(this).data("upid");
        $.ajax({
            type: "POST",
            url: "http://localhost:8888/fireplayer",
            data:{
               upid:upid
            },
            dataType: "json",
            success: function (data) {
                if(data==1){
                    alert("解雇成功！");
                    window.location.reload();
                }else{
                    alert("解雇失败！");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.statusText);
            }
        });
    });
});