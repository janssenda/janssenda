


$(document).ready(function () {
    var stringcat = getParameterByName("cat");
    var stringid = getParameterByName("id");

    if (stringcat === null){
        formdisplaymanager("heroes");
        manageformlistener({},{},{},{},{},{},[]);
    } else if (stringid === "blank") {
        formdisplaymanager(stringcat);
        manageformlistener({},{},{},{},{},{},[]);
    } else {

        formdisplaymanager(stringcat);
        loadEditForm(stringcat, stringid);
    }

});

function postData(urlname, data){
    $("#success").hide();
    $("#failure").hide();
    $.ajax({
        type: "POST",
        url: "/hero/"+urlname,
        data: JSON.stringify(data),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"},
        success: function () {
            $("#success").show();
            clearForm();},
        error: function (errmsg, txtstatus) {
            var msg = JSON.parse(errmsg.responseText).message;
            $("#failure").html(msg);
            $("#failure").show();
           }
    });
}

function deleteData(urlname){
    $("#success").hide();
    $("#failure").hide();
    $.ajax({
        type: "DELETE",
        url: "/hero/"+urlname,
        success: function () {
            $("#success").show();
            clearForm();},
        error: function (errmsg, txtstatus) {
            var msg = JSON.parse(errmsg.responseText).message;
            $("#failure").html(msg);
            $("#failure").show();
        }
    });
}


function loadEditForm(cat, id){
    $("#success").hide();
    $("#failure").hide();
    $(".removebutton").show();
    $.ajax({
        type: "GET",
        url: "/hero/"+cat+"?id="+id,
        success: function (data) {
            switch (cat){
                case "heroes": loadHeroData(data); break;
                case "orgs": loadOrgData(data); break;
                case "powers": loadPowerData(data); break;
                case "locations": loadLocationData(data); break;
                case "headquarters": loadHeadquartersData(data); break;
                case "sightings": loadSightingData(data); break;
            }
        },
        error: function (errmsg, txtstatus) {
            var msg = JSON.parse(errmsg.responseText).message;
              console.log(msg);
        }
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
    $("#slocId").val(0);
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
            case "locations": $("#locations-form").show(); break;
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
    $("#success").hide();
    $("#failure").hide();
    $("#heroes-form").hide();
    $("#orgs-form").hide();
    $("#headquarters-form").hide();
    $("#sightings-form").hide();
    $("#powers-form").hide();
    $("#locations-form").hide();
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