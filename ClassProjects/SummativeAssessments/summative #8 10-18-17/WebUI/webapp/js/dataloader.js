


function loadHeroData(data) {
    var hero = data[0];
    var powerIdList = {};
    var orgIdList = {};

    $("#heroName").val(hero.heroName);
    $("#heroType").val(hero.heroType);
    $("#heroDescription").val(hero.description);
    $("#heroId").val(hero.heroID);

    $.each(hero.heroPowers, function (index, p) {
        powerIdList[p.powerID] = p.powerName;
        $("#powerlist").append("<div id=" + "powers" + p.powerID + ">"
            + "<a target='_blank' href='./manage.html?cat=powers&id=" + p.powerID + "'>"
            + p.powerName +
            "</a>&nbsp &nbsp <button id='" + p.powerName + "' " +
            "value='" + p.powerID + "' name='del' class='btn-circle'></button></div>");
    });

    $.each(hero.heroOrgs, function (index, o) {
        orgIdList[o.orgID] = o.orgName;
        $("#orglist").append("<div id=" + "orgs" + o.orgID + ">"
            + "<a target='_blank' href='./manage.html?cat=orgs&id=" + o.orgID + "'>"
            + o.orgName +
            "&nbsp &nbsp <button id='" + o.orgName + "' " +
            "value='" + o.orgID + "' name='del' class='btn-circle'></button></div>");

    });

    manageformlistener(orgIdList, powerIdList, {}, {}, {}, {}, []);
}

function loadOrgData(data) {
    var org = data[0];
    var headqIdList = {};
    var heroIdList = {};

    $("#orgName").val(org.orgName);
    $("#orgDescription").val(org.description);
    $("#orgId").val(org.orgID);

    $.each(org.members, function (index, m) {
        heroIdList[m.heroID] = m.heroName;
        $("#herolist").append("<div id=" + "heroes" + m.heroID + ">"
            + "<a target='_blank' href='./manage.html?cat=heroes&id=" + m.heroID + "'>"
            + m.heroName +
            "&nbsp &nbsp <button id='" + m.heroName + "' " +
            "value='" + m.heroID + "' name='del' class='btn-circle'></button></div>");
    });

    $.each(org.orgHeadQ, function (index, hq) {
        headqIdList[hq.headQID] = hq.headQName;
        $("#headqlist").append("<div id=" + "headquarters" + hq.headQID + ">"
            + "<a target='_blank' href='./manage.html?cat=headquarters&id=" + hq.headQID + "'>"
            + hq.headQName +
            "&nbsp &nbsp <button id='" + hq.headQName + "' " +
            "value='" + hq.headQID + "' name='del' class='btn-circle'></button></div>");
    });

    manageformlistener({}, {}, heroIdList, headqIdList, {}, {}, []);
}

function loadHeadquartersData(data) {
    var headq = data[0];
    var hqOrgIdList = {};
    var contactIdList = [];

    $("#headqName").val(headq.headQName);
    $("#hqAddress").val(headq.headQAdress);
    $("#hqDescription").val(headq.description);
    $("#headqId").val(headq.headQID);

    $.each(headq.orgList, function (index, o) {
        hqOrgIdList[o.orgID] = o.orgName;
        $("#hqorglist").append("<div id=" + "hq-orgs" + o.orgID + ">"
            + "<a target='_blank' href='./manage.html?cat=orgs&id=" + o.orgID + "'>"
            + o.orgName +
            "&nbsp &nbsp <button id='" + o.orgName + "' " +
            "value='" + o.orgID + "' name='del' class='btn-circle'></button></div>");
    });

    $.each(headq.contactList, function (index, c) {
        contactIdList.push(c.email);
        $("#contactlist").append("<div id='e" + index + "'>"
            + c.email +
            "&nbsp &nbsp <button  " +
            "value='e" + index + "' name='del-email' class='btn-circle'></button></div>");
    });

    manageformlistener({}, {}, {}, {}, hqOrgIdList, {}, contactIdList);
}

function loadPowerData(data) {
    var power = data[0];

    $("#powerName").val(power.powerName);
    $("#powerDescription").val(power.description);
    $("#powerId").val(power.powerID);

    $.each(power.heroList, function (index, h) {
        $("#powerherolist").append("<span class='label'><a href='./manage.html?cat=heroes&id=" + h.heroID
            + "'>" + h.heroName + "</a></span><br/>");
    });

    manageformlistener({}, {}, {}, {}, {}, {}, []);
}

function loadSightingData(data) {
    var sighting = data[0];
    var sightingHeroIdList = {};

    var d = moment(sighting.sightingTime);

    $("#slocId").val(sighting.locID);
    $("#sightingdate").val(d.format("YYYY-MM-DDThh:mm"));
    $("#sightingId").val(sighting.sightingID);

    $.each(sighting.sightingHeroes, function (index, h) {
        sightingHeroIdList[h.heroID] = h.heroName;
        $("#sightingherolist").append("<div id=" + "sighting-herolist" + h.heroID + ">"
            + "<a target='_blank' href='./manage.html?cat=heroes&id=" + h.heroID + "'>"
            + h.heroName +
            "&nbsp &nbsp <button id='" + h.heroName + "' " +
            "value='" + h.heroID + "' name='del' class='btn-circle'></button></div>");
    });

    loadlist("locations","address","locID","#locations");

    manageformlistener({}, {}, {}, {}, {}, sightingHeroIdList, {});
}

function loadLocationData(data) {
    var location = data[0];

    $("#locName").val(location.locName);
    $("#locId").val(location.locID);
    $("#latitude").val(location.latitude);
    $("#longitude").val(location.longitude);
    $("#country").val(location.country);
    $("#city").val(location.city);
    $("#state").val(location.state);
    $("#address").val(location.address);
    $("#zip").val(location.zip);
    $("#locDescription").val(location.description);


    $.each(location.locSightings, function (index, s) {
        var d = moment(s.sightingTime);
        $("#sightinglist").append("<span class='label'><a href='./manage.html?cat=sightings&id=" + s.sightingID
            + "'>" + d.format("MMM Do YY, h:mm a") + "</a></span><br/>");
    });

    manageformlistener({}, {}, {}, {}, {}, {}, {});
}



function loadfielddata() {
    loadlist("powers","powerName","powerID","#powers");
    loadlist("orgs","orgName","orgID","#orgs");
    loadlist("heroes","heroName","heroID", "#heroes");
    loadlist("headquarters","headQName","headQID","#headquarters");
    loadlist("orgs","orgName","orgID","#hq-orgs");
    loadlist("heroes","heroName","heroID", "#sighting-herolist");
    loadlist("locations","address","locID","#locations");
}

function loadlist(listname,val,key,selectid) {
    $("#"+listname).empty();
    var list = {"Choose...": "choose"};
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/"+listname+"?load=false",
        success: function (results) {
            var conc = "";

            $.each(results, function (index, res) {
                if (listname === "locations"){
                    conc = res["locID"] + " - ";
                }

                list[conc + res[val]] = res[key];
            });

            var id = $(selectid);
            id.empty();
            $.each(list, function (key, value) {
                id.append($("<option></option>")
                    .attr("value", value).text(key));
            });

            if (listname === "locations"){
                $('#locations').prop('selectedIndex', $("#slocId").val());
            }

        },
        error: function () {alert("fail")}
    });
}