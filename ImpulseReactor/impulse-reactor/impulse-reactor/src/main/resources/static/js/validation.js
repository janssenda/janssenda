function validateHeroForm(){
    var error = false;
    var name = $("#heroName");
    var nameErrField = $("#heroNameErr");
    var type = $("#heroType");
    nameErrField.empty();
    $("#heroTypeErr").empty();

    if (name.val() === null || name.val() === ""){
        nameErrField.append("This field is required.");
        error = true;
    } else if (name.val().length < 3){
        nameErrField.append("Must be at least 3 characters");
        error = true;
    }

    if (type.val() === null || type.val() === ""){
        $("#heroTypeErr").append("This field is required.");
        error = true;
    }
    return error;
}

function validatePowerForm(){
    var error = false;
    var name = $("#powerName");
    var nameErrField = $("#powerNameErr");
    nameErrField.empty();

    if (name.val() === null || name.val() === ""){
        nameErrField.append("This field is required.");
        error = true;
    } else if (name.val().length < 3){
        nameErrField.append("Must be at least 3 characters");
        error = true;
    }
    return error;
}

function validateHeadquartersForm(contactList){
    var error = false;
    var name = $("#headqName");
    var loc = $("#hqAddress");

    var locErrField = $("#headQLocErr");
    var nameErrField = $("#headQNameErr");

    locErrField.empty();
    nameErrField.empty();
    $("#headQEmailErr").empty();


    if (name.val() === null || name.val() === ""){
        nameErrField.append("This field is required.");
        error = true;
    } else if (name.val().length < 3){
        nameErrField.append("Must be at least 3 characters");
        error = true;
    }

    if (loc.val() === null || loc.val() === ""){
        locErrField.append("This field is required.");
        error = true;
    } else if (loc.val().length < 3){
        locErrField.append("Must be at least 3 characters");
        error = true;
    }

    if (Object.keys(contactList).length <= 0){
        $("#headQEmailErr").append("At least one contact is required.");
    }

    return error;
}


function validateOrgForm(){
    var error = false;
    var name = $("#orgName");
    var nameErrField = $("#orgNameErr");
    nameErrField.empty();

    if (name.val() === null || name.val() === ""){
        nameErrField.append("This field is required.");
        error = true;
    } else if (name.val().length < 3){
        nameErrField.append("Must be at least 3 characters");
        error = true;
    }
    return error;
}

function validateSightingForm(heroList){
    var error = false;
    var date = $("#sightingdate");
    var loc = $("#locations");

    var dateErrField = $("#sightingDateErr");
    var locErrField = $("#sightingLocErr");

    dateErrField.empty();
    locErrField.empty();
    $("#sightingHeroErr").empty();


    if (date.val() === null || date.val() === ""){
        dateErrField.append("This field is incomplete. Time is required.");
        error = true;
    } else if (date.val().length < 11){
        dateErrField.append("Incomplete date");
        error = true;
    }

    if (loc.val() === null || loc.val() === "choose"){
        locErrField.append("This field is required.");
        error = true;
    }


    if (Object.keys(heroList).length <= 0){
        $("#sightingHeroErr").append("Sighting must have heroes");
    }
    return error;
}

function validateLocationForm(){
    var error = false;
    var lat = $("#latitude");
    var lon = $("#longitude");

    var latErrField = $("#latErr");
    var lonErrField = $("#lonErr");

    latErrField.empty();
    lonErrField.empty();

    if (lat.val() === null || lat.val() === ""){
        latErrField.append("This field is required.");
        error = true;
    } else if (Math.abs(lat.val()) > 90){
        latErrField.append("Invalid entry - range +/- 90deg");
        error = true;
    }

    if (lon.val() === null || lon.val() === ""){
        lonErrField.append("This field is required.");
        error = true;
    } else if (Math.abs(lon.val()) > 180){
        lonErrField.append("Invalid entry - range +/- 180deg");
        error = true;
    }
    return error;
}