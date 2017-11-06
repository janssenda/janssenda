/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    getNewsFeed();

});


function getNewsFeed() {
    var call = "http://localhost:8080/sightings?limit=10";
    $.ajax({
        type: "GET",
        url: call,
        success: function (results) {
           printResults(results);
        },
        error: function () {
            alert("fail")
        }
    });
}

function printResults(results){

    $.each(results, function (index, res) {
        var d = moment(res.sightingTime);

        var resDiv = "<div class = 'row text-left feeddiv'>" +
            "<div class='col-sm-4'><span class='toprow'>"+
            "<a href='./manage.html?cat=sightings&id=" + res.sightingID + "'>" +
            "Sighing ID: "+res.sightingID + "</a>"+ "</span>" +
            "<br/><span class='label'>Time: </span>" + d.format("MMM Do YY, h:mm a") +
            "<br/><span class='label'><a href='./manage.html?cat=locations&id=" + res.locID + "'>Location ID: " + res.locID + "</a></span>" +
            "<br/><span class='label'>Country: </span>" + res.loc.country +
            "<br/><span class='label'>City: </span>" + res.loc.city + ", " + res.loc.state +
            "<br/><span class='label'>Zipcode: </span>" + res.loc.zip +
            "<br/><br/><span class='label'>Street Address:</span><br/>" + res.loc.address +
            "<br/><br/><span class='label'>Latitude: </span>" + res.loc.latitude +
            "<br/><span class='label'>Longitude: </span>" + res.loc.longitude +"</div>";

        resDiv += "<div class='col-sm-4'><span class='toprow'>Heroes Present</span><br/>";

           $.each(res.sightingHeroes, function (index, hero) {
              resDiv += "<span class='label'><a href='./manage.html?cat=heroes&id=" + hero.heroID + "'>" + hero.heroName + "</a></span><br/>"
           });

        var mapID = "map"+index;
        resDiv += "</div><div class='col-sm-4 map-box' id='"+mapID+"'></div>" +
            " </div><br/>";
        $("#newsfeed").append(resDiv);

        initMap(mapID, res.loc.latitude, res.loc.longitude);

    });
}




function initMap(mapID, latitude, longitude) {
    var uluru = {lat: latitude, lng: longitude};
    var map = new google.maps.Map(document.getElementById(mapID), {
        zoom: 8,
        center: uluru
    });
    var marker = new google.maps.Marker({
        position: uluru,
        map: map
    });


}
