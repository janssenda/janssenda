$(document).ready(function () {

    searchHandler();

});

function searchHandler() {
    var heroOptions = {
        "Name": "name",
        "Type": "type",
        "Hero ID": "id",
        "Location": "locID",
        "Description": "desc"
    };

    var orgOptions = {
        "Name": "name",
        "Org ID": "id",
        "Description": "desc"
    };

    var headQOptions = {
        "Name": "name",
        "Headquarters ID": "id",
        "Description": "desc"
    };

    var locOptions = {
        "Name": "name",
        "Location ID": "id",
        "Country": "country",
        "State": "state",
        "City": "city",
        "Zipcode": "zip",
        "Hero ID": "heroID"
    };

    var sightingOptions = {
        "Sighting ID": "id",
        "Date": "date",
        "Location ID": "locID"
    };

    var powerOptions = {
        "Name": "name",
        "Power ID": "id",
        "Description": "desc"
    };

    updateSelections(heroOptions);


    $("#category").change(function () {
        var category = $("#category").val();
        switch (category) {
            case "heroes":
                updateSelections(heroOptions);
                break;
            case "orgs":
                updateSelections(orgOptions);
                break;
            case "headquarters":
                updateSelections(headQOptions);
                break;
            case "powers":
                updateSelections(powerOptions);
                break;
            case "locations":
                updateSelections(locOptions);
                break;
            case "sightings":
                updateSelections(sightingOptions);
                break;
        }
    });

    $("#subcategory").change(function () {

        if ($("#subcategory").val() === "date") {
            $("#searchterms").hide();
            $("#searchdate").show();
        } else {
            $("#searchterms").show();
            $("#searchdate").hide();
        }

    });

    $("#searchbutton").click(function () {
        queryforresults()
    });
}

function queryforresults() {
    var terms;
    var category = $("#category").val();
    var method = $("#subcategory").val();
    if (method === "date") {
        terms = $("#searchdate").val();
    } else {
        terms = $("#searchterms").val();
    }

    var call = "http://localhost:8080/" + category + "?" + method + "=" + terms;
    $.ajax({
        type: "GET",
        url: call,
        success: function (results) {
            printResults(results, category);
        },
        error: function () {
            alert("fail")
        }
    });
}

function printResults(results, category) {

    switch (category) {
        case "heroes":
            heroResultsTable(results);
            break;
        case "orgs":
            orgsResultsTable(results);
            break;
        case "headquarters":
            headQResultsTable(results);
            break;
        case "powers":
            powerResultsTable(results);
            break;
        case "locations":
            locationResultsTable(results);
            break;
        case "sightings":
            sightingResultsTable(results);
            break;
    }
}


function heroResultsTable(results) {


    var resultstable =
        "<table id='resultstable'>" +
        "<thead> <tr> <th class='header-th-id'>ID</th> <th>Name</th> <th>Type</th> " +
        "<th>Powers</th> <th>Organizations</th> <th>Description</th> " +
        "</tr> </thead> <tbody>";

    $.each(results, function (index, res) {

        resultstable += "<tr><td class='id-col'><a href='./manage.html?cat=heroes&id=" + res.heroID + "'>" + res.heroID + "</a></td>" +
            "<td><a href='./manage.html?cat=heroes&id=" + res.heroID + "'>" + res.heroName + "</a></td>" +
            "<td>" + res.heroType + "</td>" +
            "<td>";
        try {
            $.each(res.heroPowers, function (index, p) {
                resultstable += "<a href='./manage.html?cat=powers&id=" + p.powerID + "'>" + p.powerName + "</a>, ";
            });
        } catch (err) {}
        resultstable += "</td>" +

            "<td>";
        try {
            $.each(res.heroOrgs, function (index, org) {
                resultstable += "<a href='./manage.html?cat=orgs&id=" + org.orgID + "'>" + org.orgName + "</a>, ";
            });
        } catch (err) {}
        resultstable += "</td>" +

            "<td>" + res.description + "</td></tr>";
    });

    resultstable += "</tbody> </table> <hr/>";
    $("#resultsdiv").html(resultstable);
}


function orgsResultsTable(results) {

    var resultstable =
        "<table id='resultstable'>" +
        "<thead> <tr> <th class='header-th-id'>ID</th> <th>Name</th> <th>Description</th> " +
        "<th>Members</th> </tr> </thead> <tbody>";

    $.each(results, function (index, res) {
        resultstable += "<tr><td class='id-col'><a href='./manage.html?cat=orgs&id=" + res.orgID + "'>" + res.orgID + "</a></td>" +
            "<td><a href='./manage.html?cat=orgs&id=" + res.orgID + "'>" + res.orgName + "</a></td>" +
            "<td>" + res.description + "</td>" +
            "<td>";
        try {
            $.each(res.members, function (index, mem) {
                resultstable += "<a href='./manage.html?cat=heroes&id=" + mem.heroID + "'>" + mem.heroName + "</a>, ";
            });
        } catch (err) {}
        resultstable += "</td>" + "</tr>";
    });

    resultstable += "</tbody> </table> <hr/>";
    $("#resultsdiv").html(resultstable);
}

function headQResultsTable(results) {

    var resultstable =
        "<table id='resultstable'>" +
        "<thead> <tr> <th class='header-th-id'>ID</th> <th>Name</th> <th>Address</th> " +
        "<th>Description</th><th>Organization</th><th>Contact</th> </tr> </thead> <tbody>";


    $.each(results, function (index, res) {

        resultstable += "<tr><td class='id-col'><a href='./manage.html?cat=headquarters&id=" + res.headQID + "'>" + res.headQID + "</a></td>" +
            "<td><a href='./manage.html?cat=headquarters&id=" + res.headQID + "'>" + res.headQName + "</a></td>" +
            "<td>" + res.headQAdress + "</td>" +
            "<td>" + res.description + "</td>" +

            "<td>";
        try {
            $.each(res.orgList, function (index, org) {
                resultstable += "<a href='./manage.html?cat=orgs&id=" + org.orgID + "'>" + org.orgName + "</a>, ";
            });
        } catch (err) {}
        resultstable += "</td>" +

            "<td>" + res.contactList[0].email + "</td></td></tr>";
    });

    resultstable += "</tbody> </table> <hr/>";
    $("#resultsdiv").html(resultstable);
}


function locationResultsTable(results) {

    var resultstable =
        "<table id='resultstable'>" +
        "<thead> <tr> <th class='header-th-id'>ID</th> <th>Name</th> <th>Country</th> " +
        "<th>City</th><th>State</th><th>Address</th><th>Zip</th>" +
        "<th>Latitude</th><th>Longitude</th><th>Description</th> </tr> </thead> <tbody>";

    $.each(results, function (index, res) {
        resultstable += "<tr><td class='id-col'><a href='./manage.html?cat=locations&id=" + res.locID + "'>" + res.locID + "</a></td>" +
            "<td>" + res.locName + "</td>" +
            "<td>" + res.country + "</td>" +
            "<td>" + res.city + "</td>" +
            "<td>" + res.state + "</td>" +
            "<td>" + res.address + "</td>" +
            "<td>" + res.zip + "</td>" +
            "<td>" + res.latitude + "</td>" +
            "<td>" + res.longitude + "</td>" +
            "<td>" + res.description + "</td>" +
            "</td></tr>";
    });

    resultstable += "</tbody> </table> <hr/>";
    $("#resultsdiv").html(resultstable);
}

function sightingResultsTable(results) {

    var resultstable =
        "<table id='resultstable'>" +
        "<thead> <tr> <th class='header-th-id'>ID</th> <th>Location ID</th> " +
        "<th>Date </th><th>Heroes </th> </tr> </thead> <tbody>";

    $.each(results, function (index, res) {

        var d = moment(res.sightingTime);

        resultstable += "<tr><td class='id-col'><a href='./manage.html?cat=sightings&id=" + res.sightingID
            + "'>" + res.sightingID + "</a></td>" + "<td><a href='./manage.html?cat=locations&id=" + res.locID + "'>" + res.locID + "</a></td>" +
            "<td>" + d.format("MMM Do YY, h:mm a") + "</td><td>";

        try {
            $.each(res.sightingHeroes, function (index, hero) {
                resultstable += "<a href='./manage.html?cat=heroes&id=" + hero.heroID + "'>" + hero.heroName + "</a>, ";
            });
        } catch (err) {

        }

        resultstable += "</td></tr>";
    });

    resultstable += "</tbody> </table> <hr/>";
    $("#resultsdiv").html(resultstable);
}

function powerResultsTable(results) {

    var resultstable =
        "<table id='resultstable'>" +
        "<thead> <tr> <th class='header-th-id'>ID</th> <th>Name</th> " +
        "<th>Description</th></tr> </thead> <tbody>";

    $.each(results, function (index, res) {
        resultstable += "<tr><td class='id-col'><a href='./manage.html?cat=powers&id=" + res.powerID + "'>" + res.powerID + "</a></td>" +
            "<td><a href='./manage.html?cat=powers&id=" + res.powerID + "'>" + res.powerName + "</a></td>" +
            "<td>" + res.description + "</td>" +
            "</td></tr>";
    });

    resultstable += "</tbody> </table> <hr/>";
    $("#resultsdiv").html(resultstable);
}

function updateSelections(newObjects) {

    var $el = $("#subcategory");
    $el.empty(); // remove old options

    $.each(newObjects, function (key, value) {
        $el.append($("<option></option>")
            .attr("value", value).text(key));
    });


}