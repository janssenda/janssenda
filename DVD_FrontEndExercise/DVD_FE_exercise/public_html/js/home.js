/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    $("#inputError").hide();
    var units = $("#unitsel").val();
    var zipCode = "55407";
    var apiKey = "d446ade3c095f0739178c40ed40583be";

    getCurrentWeather(zipCode, apiKey, units);
    getFutureWeather(zipCode, apiKey, units);


    $("#goButton").click(function () {
        //$("#console").append($("#unitsel").val());

        var units = $("#unitsel").val();
        $("#inputError").hide();
        var zip = $("#zip").val();

        getCurrentWeather(zip, apiKey, units);
        getFutureWeather(zip, apiKey, units);

    });



});

function getFutureWeather(zipCode, apiKey, units) {


    var callA = "http://api.openweathermap.org/data/2.5/forecast?zip=";
    var callB = zipCode + ",us&units=" + units + "&APPID=" + apiKey;
    var call = callA + callB;

    $.ajax({

        type: "GET",
        url: call,
        success: function (weatherData) {
            var i = 1;

            $.each(weatherData.list, function (index, weekDay) {

                if (index % 8 === 0) {


                    date = new Date(weekDay.dt_txt);

                    var type = weekDay.weather[0].main;
                    var desc = weekDay.weather[0].description;
                    var iconCode = weekDay.weather[0].icon;

                    var temp = weekDay.main.temp;
                    var pressure = (weekDay.main.pressure * 100) / 1000;
                    var humidity = weekDay.main.humidity;
                    var temp_min = weekDay.main.temp_min;
                    var temp_max = weekDay.main.temp_max;

                    var iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
                    var imgCode = '<img id="wicon" src="' + iconUrl + '"></img>';

                    date = moment(date).format("dddd, MMM Do");

                    var infoString = date + "<br>";
                    infoString += imgCode + " " + type + "<br/>";
                    infoString += "H "
                            + Number(temp_max).toFixed(0)
                            + " &deg;-- L "
                            + Number(temp_min).toFixed(0) + " &deg;";

                    var units = $("#unitsel").val();
                    if (units === "metric") {
                        infoString += "C";
                    } else {
                        infoString += "F";
                    }


                    var divID = "#day" + (i);
                    i += 1;
                    $(divID).html(infoString);

                }
            });


        },
        error: function () {
            $("#inputError").show();
        }
    });





}

function getCurrentWeather(zipCode, apiKey, units) {



    var callA = "http://api.openweathermap.org/data/2.5/weather?zip=";
    var callB = zipCode + ",us&units=" + units + "&APPID=" + apiKey;
    var call = callA + callB;

    $.ajax({

        type: "GET",
        url: call,
        success: function (weatherData) {
            $("#console").text(call);
            var city = weatherData.name;
            var temp = weatherData.main.temp;
            var pressure = (weatherData.main.pressure * 100) / 1000;
            var humidity = weatherData.main.humidity;
            var temp_min = weatherData.main.temp_min;
            var temp_max = weatherData.main.temp_max;


            var lon = weatherData.coord.lon;
            var late = weatherData.coord.lat;

            var type = weatherData.weather[0].main;
            var desc = weatherData.weather[0].description;
            var iconCode = weatherData.weather[0].icon;


            var windSpd = weatherData.wind.speed * 2.23694;
            var windDir = weatherData.wind.deg;




            var iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
            var imgCode = '<img id="wicon" src="' + iconUrl + '"></img>';


            $("#weatherHeading").html("Current weather in " + city);
            $("#image").html(imgCode);
            $("#wtype").html(type);
            $("#desc").html(desc);

            var units = $("#unitsel").val();
            if (units === "metric") {
                var unitLabel = "C";
            } else {
                var unitLabel = "F";
            }

            var current = "Temperature: " + Number(temp).toFixed(2) + " &deg;"+unitLabel + "<br>";
            current += "Pressure: " + Number(pressure).toFixed(2) + " kPa" + "<br>";
            current += "Humidity: " + Number(humidity).toFixed(2) + " %" + "<br>";
            current += "Wind: " + Number(windSpd).toFixed(2) + " mph" + "<br>";


            $("#currentConditions").html(current);





        },
        error: function () {

        }
    });
}
;

function printDailyData(day, parameters) {


    var divID = "#day" + day;


    var iconUrl = "http://openweathermap.org/img/w/" + parameters[0] + ".png";
    var imgCode = '<img id="wicon" src="' + parameters[0] + '"></img>';

    var infoString = parameters[1] + "<br>";
    infoString += imgCode + " " + parameters[2];
    infoString += "H " + parameters[4] + " /L " + parameters[5];


    $(day).append(infoString);

}

