





$(document).ready(function () {

    var nothide = $("#mainInfoDiv");

    $("#pageContent").children().not(nothide).hide();
    //$("div").show();
//    $("#pageContent").show().children();



    cityShowHide("main");
    cityShowHide("akron");
    cityShowHide("minneapolis");
    cityShowHide("louisville");


    $("tr").has("td").hover(function () {
        $(this).css("background-color", "WhiteSmoke");
    },
            function () {
                $(this).css("background-color", "");
            })

});

function cityShowHide(city) {


    $("#" + city + "Button").click(function () {
        $("#pageContent").children().hide();
        $("#mainInfoDiv").hide();
        $("#" + city + "InfoDiv").show();
        $("#" + city + "Weather").hide();
    });


    $("#" + city + "WeatherButton").click(function () {

        if ($("#" + city + "Weather").is(":visible")) {
            $("#" + city + "Weather").hide();
        } else {
            $("#" + city + "Weather").show();
        }

    });
}