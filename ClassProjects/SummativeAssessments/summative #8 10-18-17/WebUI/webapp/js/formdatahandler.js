function manageformlistener(orgIdList, powerIdList, heroIdList, headqIdList,
                            hqOrgIdList, sightingHeroIdList, contactIdList) {
    loadfielddata();

    $("#powers").change(function () {changelist($("#powerlist"), powerIdList, "powers")});
    $("#powerlist-div").on("click", "button[name='del']", function () {
        listbutton(powerIdList, "powers", $(this).val(), $(this).attr('id')); });

    $("#orgs").change(function () {changelist($("#orglist"), orgIdList, "orgs")});
    $("#orglist-div").on("click", "button[name='del']", function () {
        listbutton(orgIdList, "orgs", $(this).val(), $(this).attr('id')); });

    $("#heroes").change(function () {changelist($("#herolist"), heroIdList, "heroes")});
    $("#herolist-div").on("click", "button[name='del']", function () {
        listbutton(heroIdList, "heroes", $(this).val(), $(this).attr('id')); });

    $("#headquarters").change(function () {changelist($("#headqlist"), headqIdList, "headquarters")});
    $("#headqlist-div").on("click", "button[name='del']", function () {
        listbutton(headqIdList, "headquarters", $(this).val(), $(this).attr('id')); });

    $("#hq-orgs").change(function () {changelist($("#hqorglist"), hqOrgIdList, "hq-orgs")});
    $("#hqorglist-div").on("click", "button[name='del']", function () {
        listbutton(hqOrgIdList, "hq-orgs", $(this).val(), $(this).attr('id')); });

    $("#sighting-herolist").change(function () {changelist($("#sightingherolist"), sightingHeroIdList, "sighting-herolist")});
    $("#sighting-herolist-div").on("click", "button[name='del']", function () {
        listbutton(sightingHeroIdList, "sighting-herolist", $(this).val(), $(this).attr('id')); });

    $("#emailbutton").click(function () {
        var field = $("#hqemail").val();

        for (var i = 0; i < contactIdList.length; i++){
            if (contactIdList[i] === field){
                return;
            }
        }

        contactIdList.push(field);
        $("#contactlist").append("<div id='e" + contactIdList.length + "'>" + field +
            "&nbsp &nbsp <button  " +
            "value='e" + contactIdList.length + "' name='del-email' class='btn-circle'></button></div>");



    });

    $("#contactlist-div").on("click", "button[name='del-email']", function () {
        var contactEmailId = $(this).val();
        var newList = [];
        var idlist = [];

        for (var i = 0; i < contactIdList.length; i++){
            var email = $.trim($("#"+contactEmailId).text().split(" ")[0]);

            if ($.trim(contactIdList[i]) !== email){
                newList.push(contactIdList[i]);
                idlist.push(i);
            }
        }

        contactIdList = newList;
        $("#"+contactEmailId).remove();

    });



    $("#hero-submit").click(function () {
        var plist = [];
        $.each(powerIdList, function (k, v) {
            var p = {"powerID": k, "powerName": v};
            plist.push(p);
        });

        var olist = [];
        $.each(orgIdList, function (k, v) {
            var o = {"orgID": k, "orgName": v};
            olist.push(o);
        });

        var hero = {"heroName": $("#heroName").val()};
        hero["heroID"] = $("#heroId").val();
        hero["heroType"] = $("#heroType").val();
        hero["description"] = $("#heroDescription").val();
        hero["heroPowers"] = plist;
        hero["heroOrgs"] = olist;
        postData("heroes",hero);
    });

    $("#hero-remove").click(function () {
        deleteData("heroes?id="+$("#heroId").val());
    });





    $("#org-submit").click(function () {
        var mlist = [];
        $.each(heroIdList, function (k, v) {
            var h = {"heroID": k, "heroName": v};
            mlist.push(h);
        });

        var hqlist = [];
        $.each(headqIdList, function (k, v) {
            var hq = {"headQID": k, "headQName": v};
            hqlist.push(hq);
        });

        var org = {"orgName" : $("#orgName").val()};
        org["orgID"] = $("#orgId").val();
        org["description"] = $("#orgDescription").val();
        org["members"] = mlist;
        org["orgHeadQ"] = hqlist;
        postData("orgs",org);
    });

    $("#org-remove").click(function () {
        deleteData("orgs?id="+$("#orgId").val());
    });


    $("#power-submit").click(function () {
        var power = {"powerName" : $("#powerName").val()};
        power["powerID"] = $("#powerId").val();
        power["description"] = $("#powerDescription").val();
        postData("powers",power);
    });

    $("#power-remove").click(function () {
        deleteData("powers?id="+$("#powerId").val());
    });




    $("#headquarters-submit").click(function () {
        var clist = [];
        $.each(contactIdList, function (k, v) {
            var c = {"headQID": $("#headqId").val(), "email": v};
            clist.push(c);
        });

        var olist = [];
        $.each(hqOrgIdList, function (k, v) {
            var o = {"orgID": k, "orgName": v};
            olist.push(o);
        });

        var headquarters = {"headQName" : $("#headqName").val()};
        headquarters["headQID"] = $("#headqId").val();
        headquarters["description"] = $("#hqDescription").val();
        headquarters["contactList"] = clist;
        headquarters["orgList"] = olist;
        postData("headquarters",headquarters);
    });

    $("#headquarters-remove").click(function () {
        deleteData("headquarters?id="+$("#headqId").val());
    });


    $("#sighting-submit").click(function () {
        var hlist = [];
        $.each(sightingHeroIdList, function (k, v) {
            var h = {"heroID": k, "heroName": v};
            hlist.push(h);
        });

        var d = moment($("#sightingdate").val());

        var sighting = {"sightingID" : $("#sightingId").val()};
        sighting["sightingTime"] = d.format("YYYY-MM-DD hh:mm:ss");
        sighting["locID"] = $("#locations").val();
        sighting["sightingHeroes"] = hlist;
        postData("sightings",sighting);


    });

    $("#sighting-remove").click(function () {
        deleteData("sightings?id="+$("#sightingId").val());
    });


    $("#loc-submit").click(function () {

        var location = {"locName" : $("#locName").val()};
        location["locID"] = $("#locId").val();
        location["latitude"] = $("#latitude").val();
        location["longitude"] = $("#longitude").val();
        location["country"] = $("#country").val();
        location["city"] = $("#city").val();
        location["state"] = $("#state").val();
        location["address"] = $("#address").val();
        location["zip"] = $("#zip").val();
        location["description"] = $("#locDescription").val();
        postData("locations",location);

    });

    $("#loc-remove").click(function () {
        deleteData("locations?id="+$("#locId").val());
    });



}

function listbutton(ilist, slist, id, name) {
    delete ilist[id];
    $("#" + slist + id).remove();
    $("#" + slist).append($("<option></option>")
        .attr("value", id).text(name));
}

function changelist(vlist, ilist, selid) {
    delete ilist[""];
    var sel = $("#" + selid + " option:selected");
    ilist[sel.val()] = sel.text();
    vlist.append("<div id=" + selid + sel.val() + ">" + sel.text() +
        "&nbsp &nbsp <button id='" + sel.text() + "' " +
        "value='" + sel.val() + "' name='del' class='btn-circle'></button></div>");
    sel.remove();
}