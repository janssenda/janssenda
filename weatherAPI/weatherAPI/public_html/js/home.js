/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    var zipCode = "55414";
    var apiKey = "d446ade3c095f0739178c40ed40583be";

    getCurrentWeather(zipCode, apiKey);
    getFutureWeather(zipCode, apiKey);

});

function getFutureWeather(zipCode, apiKey) {


    var callA = "http://api.openweathermap.org/data/2.5/forecast?zip=";
    var callB = zipCode + ",us&APPID=" + apiKey;
    var call = callA + callB;

    $.ajax({

        type: "GET",
        url: call,
        success: function (weatherData) {


            $.each(weatherData.list, function (index, weekDay) {

                var date = weekDay.dt_text;
                var type = weekDay.weather[0].main;
                var desc = weekDay.weather[0].description;
                var iconCode = weekDay.weather[0].icon;

                var temp = weekDay.main.temp * 9 / 5 - 459.67;
                var pressure = (weekDay.main.pressure * 100) / 1000;
                var humidity = weekDay.main.humidity;
                var temp_min = weekDay.main.temp_min * 9 / 5 - 459.67;
                var temp_max = weekDay.main.temp_max * 9 / 5 - 459.67;


                var divID = "#day" + (index + 1);


                var iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
                var imgCode = '<img id="wicon" src="' + iconUrl + '"></img>';

                var infoString = date + "<br>";
                infoString += imgCode + " " + type + "<br/>";
                infoString += "H " 
                        + Number(temp_max).toFixed(0) 
                        + " &deg;-- L " 
                        + Number(temp_min).toFixed(0)+" &deg;F";
                
                $(divID).append(infoString);

                





            });


        },
        error: function () {
            alert("FAILURE");
        }
    });





}

function getCurrentWeather(zipCode, apiKey) {



    var callA = "http://api.openweathermap.org/data/2.5/weather?zip=";
    var callB = zipCode + ",us&APPID=" + apiKey;
    var call = callA + callB;

    $.ajax({

        type: "GET",
        url: call,
        success: function (weatherData) {

            var city = weatherData.name;
            var temp = weatherData.main.temp * 9 / 5 - 459.67;
            var pressure = (weatherData.main.pressure * 100) / 1000;
            var humidity = weatherData.main.humidity;
            var temp_min = weatherData.main.temp_min * 9 / 5 - 459.67;
            var temp_max = weatherData.main.temp_max * 9 / 5 - 459.67;


            var lon = weatherData.coord.lon;
            var late = weatherData.coord.lat;

            var type = weatherData.weather[0].main;
            var desc = weatherData.weather[0].description;
            var iconCode = weatherData.weather[0].icon;


            var windSpd = weatherData.wind.speed * 2.23694;
            var windDir = weatherData.wind.deg;




            var iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
            var imgCode = '<img id="wicon" src="' + iconUrl + '"></img>';


            $("#weatherHeading").append(city);
            $("#image").append(imgCode);
            $("#wtype").append(type);
            $("#desc").append(desc);

            var current = "Temperature: " + Number(temp).toFixed(2) + " &deg;F" + "<br>";
            current += "Pressure: " + Number(pressure).toFixed(2) + " kPa" + "<br>";
            current += "Humidity: " + Number(humidity).toFixed(2) + " %" + "<br>";
            current += "Wind: " + Number(windSpd).toFixed(2) + " mph" + "<br>";


            $("#currentConditions").append(current);

//            $("#console").append(output);



        },
        error: function () {
            alert("FAILURE");
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

