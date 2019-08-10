$(document).ready(function () {
    var cart = [];
    getServerCart();
    // MallAlert.init();

    $(".add_to_cart_btn").click(function () {
        var item_selected = $(this);
        var productId = item_selected.data("productid");
        addItem(item_selected);
        addItemAlert(item_selected);
    });



    $(document).on("click", ".add-amount", function () {
        var productId = $(this).data("productid");
        addAmount(productId);
        updateCart();
    });
    $(document).on("click", ".minus-amount", function () {
        var productId = $(this).data("productid");
        minusAmount(productId);
        updateCart();
    });


    $("#placeOrder").click(function () {
        $.ajax({
            type: "POST",
            url: "http://localhost:8888/createOrder",
            async:false,
            data: JSON.stringify(cart),
            dataType: "json",
            contentType:"application/json;charset=utf-8",
            success: function (data) {
                var orderId=data;
                alert("创建订单："+orderId);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.statusText);
            }
        });
    });

    function addAmount(productId) {
        updateServerCart(productId, null, null, null, null, null, 1);
        updateCart();
    }

    function minusAmount(productId) {
        updateServerCart(productId, null, null, null, null, null, -1);
        updateCart();
    }

    function addItem(item_selected) {
        var productId = item_selected.data("productid");
        var productName = item_selected.data("productname");
        var productImg = item_selected.data("productimg");
        var priceCoin = parseInt(item_selected.data("pricecoin"));
        var priceDiamond = parseInt(item_selected.data("pricediamond"));
        var priceStr = item_selected.data("pricestr");
        var amount = 1;
        updateServerCart(productId,productName,productImg,priceCoin,priceDiamond,priceStr,amount);
        updateCart();
    }

    function updateCart() {
        var totalPriceCoin = 0;
        var totalPriceDiamond = 0;
        var itemCount=0;
        $("#cart_list").html("");
        $.each(cart, function (index, item) {
            itemCount++;
            $("#cart_list").append("<div class=\"kt-widget4__item\">\n" +
                "                <div class=\"kt-widget4__pic kt-widget4__pic--pic\">\n" +
                "                    <img src=\"/media/users/100_2.jpg\" alt=\"\">\n" +
                "                </div>\n" +
                "                <div class=\"kt-widget4__info\">\n" +
                "                    <a href=\"#\" class=\"kt-widget4__username\">" + item.productName + "</a>\n" +
                "                    <p class=\"kt-widget4__text\">" + item.priceStr + "</p>\n" +
                "                </div>\n" +
                "                <div style=\"margin:0 auto\">\n" +
                "                    <a  href=\"javascript:;\" class=\"btn btn-sm btn-clean btn-icon btn-icon-md minus-amount\" data-productId=\"" + item.productId + "\">\n" +
                "                        <i class=\"la la-minus-circle\"></i>\n" +
                "                    </a>\n" +
                "                    <span>" + item.amount + "</span>\n" +
                "                    <a href=\"javascript:;\" class=\"btn btn-sm btn-clean btn-icon btn-icon-md add-amount\" data-productId=\"" + item.productId + "\">\n" +
                "                        <i class=\"la la-plus-circle\"></i>\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "            </div>");
            if (item.priceCoin != NaN)
                totalPriceCoin += item.amount * item.priceCoin;
            if (item.priceDiamond != NaN)
                totalPriceDiamond += item.amount * item.priceDiamond;
        });
        $("#price_coin").text(totalPriceCoin + " JR币");
        $("#price_diamond").text(totalPriceDiamond + " JR钻");
        $("#cart_item_count").text(itemCount);
    }

    function updateServerCart(productId, productName, productImg, priceCoin, priceDiamond, priceStr, amount) {
        var currentcart={
            userId:-1,
            teamId:-1,
            productId: productId,
            productName: productName,
            productImg: productImg,
            priceCoin: priceCoin,
            priceDiamond: priceDiamond,
            priceStr: priceStr,
            amount: amount
        };

        $.ajax({
            type: "POST",
            url: "http://localhost:8888/updateCart",
            async:false,
            data: JSON.stringify(currentcart),
            dataType: "json",
            contentType:"application/json;charset=utf-8",
            success: function (data) {
                cart=data;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.statusText);
            }
        });
    }



    function getServerCart() {
        $.ajax({
            type: "POST",
            url: "http://localhost:8888/getCart",
            async:false,
            data: {},
            dataType: "json",
            contentType:"application/json;charset=utf-8",
            success: function (data) {
                if(data!=""){
                    cart = data;
                    updateCart();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.statusText+"!");
            }
        });
    }


    function addItemAlert(item_selected) {
        toastr.options = {
            "closeButton": false,
            "debug": false,
            "newestOnTop": false,
            "progressBar": false,
            "positionClass": "toast-top-right",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "200",
            "hideDuration": "1000",
            "timeOut": "3000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        };
        toastr.info("将 "+item_selected.data("productname")+" 添加至购物车！")
    }



});
