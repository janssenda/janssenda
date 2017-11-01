$(document).ready(function () {

    inputmanager();
    loadfielddata();

});


function loadfielddata(){
    var powerlist = {"Choose...": "choose"};
    var listname = "powers";

    var call = "http://localhost:8080/" + listname + "?load=false";
    $.ajax({
        type: "GET",
        url: call,
        success: function (results) {
            $.each(results, function (index, res) {
                console.log(res.powerName);
                powerlist[res.powerName] = res.powerID;
            });
            setfields($("#heroPowers"), powerlist);
        },
        error: function () {
            alert("fail")
        }
    });

    var orglist = {
        "Name": "name",
        "Type": "type",
        "Hero ID": "id",
        "Location": "locID",
        "Description": "desc"
    };


    setfields($("#heroOrgs"), orglist);

}

function setfields($id, objects){
    $id.empty(); // remove old options
    $.each(objects, function (key, value) {
        $id.append($("<option></option>")
            .attr("value", value).text(key));
    });
}

function inputmanager () {

    $("#category").change(function () {
        var category = $("#category").val();
        hideforms();
        switch (category) {
            case "heroes": $("#hero-form").show(); break;
            case "orgs": $("#org-form").show(); break;
            case "headquarters": $("#headquarters-form").show(); break;
            case "powers": $("#power-form").show(); break;
            case "locations": $("#location-form").show(); break;
            case "sightings": $("#sighting-form").show(); break;
        }
    });


}

function hideforms(){
    $("#hero-form").hide();
    $("#org-form").hide();
    $("#headquarters-form").hide();
    $("#sighting-form").hide();
    $("#power-form").hide();
    $("#location-form").hide();
}


function callforlist(listname){

    // var call = "http://localhost:8080/" + listname + "?load=false";
    // return $.ajax({
    //     type: "GET",
    //     url: call,
    //     success: function (results) {
    //         $.each(results, function (index, res) {
    //             console.log(res.powerID);
    //             // powerlist[res.powerName] = res.powerId;
    //         });
    //         return results;
    //     },
    //     error: function () {
    //         alert("fail")
    //     }
    // });

}