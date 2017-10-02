/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    resetAll();
    loadMoneyHandler();
    loadPurchaseHandler();
    loadEffects();
});


function timeReset() {
    setTimeout(resetAll, 2000);
}

function timeResetMsg() {
    setTimeout(resetMessages, 2000);
}

function resetAll() {
    var defMsg = "Please insert money";
    $("#serverError").hide();
    getAllItems();
    $("#msgTxt").html("");
    $("#totalMoney").val(0.00.toFixed(2));
    $("#blkTxt").html(defMsg);
    $("#currentItem").val(-1);
    $("#changeDisplay").html("");
    $("#itemDisplay").val("");
    blinkMsg();
}

function resetMessages() {
    var defMsg = "Please insert money";
    $("#serverError").hide();
    getAllItems();
    $("#msgTxt").html("");
    $("#blkTxt").html(defMsg);
    $("#currentItem").val(-1);
    $("#itemDisplay").val("");
    blinkMsg();
}


function blinkMsg() {
    $('#blkTxt').fadeOut(1);
    setTimeout(function () {
        $('#blkTxt').fadeIn(1);
    }, 1000);
    setTimeout(blinkMsg, 2000);// or blinkMe();
}

function loadEffects() {
    el = $("#itemAreaMainDiv");

    el.on("mouseenter", ".item", function () {
        $(this).css({"box-shadow": "0px 0px 10px 0px rgba(76,255,183,1)"});
    });

    el.on("mouseleave", ".item", function () {
        $(this).css({"box-shadow": ""});
    });

    el.on("mousedown",".item",function(){
        el.find("div.itemInner").css({"box-shadow": ""});
        $(this).css({"box-shadow": "0px 0px 30px 0px rgba(0,0,0,1)"});
    });

    el.on("mouseup",".item",function(){
        $(this).find("div.itemInner").css({"box-shadow": "0px 0px 20px 0px rgba(76,255,183,1)"});
        $(this).css({"box-shadow": "0px 0px 10px 0px rgba(76,255,183,1)"});
    });

    $("#purchaseButton").click(function () {

        $("#tubeLeft, #tubeRight").css({
            "-webkit-filter": "drop-shadow(0px 0px 12px #900)",
            "filter": "drop-shadow(0px 0px 12px #900)"
        });

        setTimeout(resetFX,1000);


    });

    function resetFX (){
        $("#tubeLeft, #tubeRight").css({"-webkit-filter": "drop-shadow(0px 0px 8px #222)",
            "filter": "drop-shadow(0px 0px 8px #222)"})
    };

}












