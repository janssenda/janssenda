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
            case "heroes":updateSelections(heroOptions);break;
            case "orgs":updateSelections(orgOptions);break;
            case "headquarters":updateSelections(headQOptions);break;
            case "powers":updateSelections(powerOptions);break;
            case "locations":updateSelections(locOptions);break;
            case "sightings":updateSelections(sightingOptions);break;
        }
    });

    $("#searchbutton").click(function () {
        queryforresults()
    });


}

function queryforresults() {
    var category = $("#category").val();
    var method = $("#subcategory").val();
    var terms = $("#searchterms").val();

    var call = "http://localhost:8080/" + category + "?" + method + "=" + terms;

console.log(call);
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
        case "heroes": heroResultsTable(results); break;
        case "orgs": orgsResultsTable(results); break;
        case "headquarters": headQResultsTable(results); break;
        case "powers": break;
        case "locations": break;
        case "sightings": break;
    }
}


function heroResultsTable(results) {
    var resultstable =
        "<table id='resultstable' class='table-hover table-sm table-bordered' style='width: 100%'>" +
        "<thead> <tr> <th>ID</th> <th>Name</th> <th>Type</th> " +
        "<th>Powers</th> <th>Organizations</th> <th>Description</th> " +
        "</tr> </thead> <tbody>";

    $.each(results, function (index, res) {
        resultstable += "<tr><th scope='row'>" + res.heroID + "</th>" +
          "<td>"+ res.heroName +"</td>" +
          "<td>"+ res.heroType +"</td>" +
          "<td>"+ res.heroPowers[0].powerName +"</td>" +
          "<td>"+ res.heroOrgs[0].orgName +"</td>" +
          "<td>"+ res.description +"</td></tr>";
    });

    resultstable += "</tbody> </table> <hr/>";
    $("#resultsdiv").html(resultstable);
}


function orgsResultsTable(results) {

    var resultstable =
        "<table id='resultstable' class='table-hover table-sm table-bordered' style='width: 100%'>" +
        "<thead> <tr> <th>ID</th> <th>Name</th> <th>Description</th> " +
        "<th>Members</th> </tr> </thead> <tbody>";

    $.each(results, function (index, res) {
        resultstable += "<tr><th scope='row'>" + res.orgID + "</th>" +
            "<td>"+ res.orgName +"</td>" +
            "<td>"+ res.description +"</td>" +
            "<td>"+ res.members[0].heroName +"</td></tr>";
    });

    resultstable += "</tbody> </table> <hr/>";
    $("#resultsdiv").html(resultstable);
}

function headQResultsTable(results) {

    var resultstable =
        "<table id='resultstable' class='table-hover table-sm table-bordered' style='width: 100%'>" +
        "<thead> <tr> <th>ID</th> <th>Name</th> <th>Address</th> " +
        "<th>Description</th><th>Organization</th><th>Contact</th> </tr> </thead> <tbody>";


    $.each(results, function (index, res) {
        var org = null;
        try {
            org = res.orgList[0].orgName
        } catch (err){
        }

        resultstable += "<tr><th scope='row'>" + res.headQID + "</th>" +
            "<td>"+ res.headQName +"</td>" +
            "<td>"+ res.headQAdress +"</td>" +
            "<td>"+ res.description +"</td>" +
            "<td>"+ org +"</td>" +
            "<td>"+ res.contactList[0].email +"</td></td></tr>";
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