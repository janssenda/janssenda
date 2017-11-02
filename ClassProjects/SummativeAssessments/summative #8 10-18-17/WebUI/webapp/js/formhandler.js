


$(document).ready(function () {
    var stringcat = getParameterByName("cat");
    var stringid = getParameterByName("id");

    if (stringcat === null){
        formdisplaymanager("heroes");
        manageformlistener({},{},{},{},{},{},[]);
    } else {
        formdisplaymanager(stringcat);
        loadEditForm(stringcat, stringid);
    }

});

function postData(urlname, data){
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/"+urlname,
        data: JSON.stringify(data),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"},
        success: function () {clearForm();},
        error: function () {alert("fail")}
    });
}

function deleteData(urlname){
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/"+urlname,
        success: function () {clearForm();},
        error: function () {alert("fail")}
    });
}


function loadEditForm(cat, id){
    $(".removebutton").show();
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/"+cat+"?id="+id,
        success: function (data) {
            switch (cat){
                case "heroes": loadHeroData(data); break;
                case "orgs": loadOrgData(data); break;
                case "powers": loadPowerData(data); break;
                case "locations": break;
                case "headquarters": loadHeadquartersData(data); break;
                case "sightings": loadSightingData(data); break;
            }
        },
        error: function () {alert("fail")}
    });
}

function clearForm(){
    $(".removebutton").hide();
    switch ($("#category").val()) {
        case "heroes": clearHeroForm(); break;
        case "orgs": clearOrgsForm(); break;
        case "headquarters": clearHeadquartersForm(); break;
        case "powers": clearPowersForm(); break;
        case "sightings": clearSightingsForm(); break;
        case "locations":  clearLocationsForm(); break;
    }
}

function clearHeroForm(){
    $("#heroId").val("");
    $("#heroName").val("");
    $("#heroType").val("Default");
    $("#heroDescription").val("");
    $("#powerlist").empty();
    $("#orglist").empty();
    loadlist("powers","powerName","powerID","#powers");
    loadlist("orgs","orgName","orgID","#orgs");
}

function clearOrgsForm(){
    $("#orgId").val("");
    $("#orgName").val("");
    $("#orgDescription").val("");
    $("#herolist").empty();
    $("#headqlist").empty();
    loadlist("heroes","heroName","heroID", "#heroes");
    loadlist("headquarters","headQName","headQID","#headquarters");
}

function clearHeadquartersForm(){
    $("#headqId").val("");
    $("#headqName").val("");
    $("#hqDescription").val("");
    $("#hqAddress").val("");
    $("#hqorglist").empty();
    $("#contactlist").empty();
    loadlist("orgs","orgName","orgID","#hq-orgs");
}

function clearPowersForm(){
    $("#powerId").val("");
    $("#powerName").val("");
    $("#powerDescription").val("");
    $("#powerherolist").empty();
}

function clearSightingsForm(){
    $("#sightingId").val("");
    $("#sightingdate").val("");
    $("#sightingherolist").empty();
    loadlist("heroes","heroName","heroID", "#sighting-herolist");
    loadlist("locations","address","locID","#locations");
}

function clearLocationsForm(){
    $("#locId").val("");
    $("#locName").val("");
    $("#address").val("");
    $("#locDescription").val("");
    $("#country").val("");
    $("#city").val("");
    $("#state").val("");
    $("#zip").val("");
    $("#latitude").val("");
    $("#longitude").val("");
    $("#sightinglist").empty();
}


function formdisplaymanager(editcat) {
    var category = $("#category");
    category.change(function () {
        hideforms();
        switch (category.val()) {
            case "heroes": $("#heroes-form").show(); break;
            case "orgs": $("#orgs-form").show(); break;
            case "headquarters":  $("#headquarters-form").show(); break;
            case "powers": $("#powers-form").show(); break;
            case "locations": $("#locs-form").show(); break;
            case "sightings": $("#sightings-form").show(); break;
        }
    });
    if (editcat !== null){
        hideforms();
        $("#"+editcat+"-form").show();
        category.val(editcat).prop('selected', true);
    }
}

function hideforms() {
    $("#heroes-form").hide();
    $("#orgs-form").hide();
    $("#headquarters-form").hide();
    $("#sightings-form").hide();
    $("#powers-form").hide();
    $("#locs-form").hide();
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}