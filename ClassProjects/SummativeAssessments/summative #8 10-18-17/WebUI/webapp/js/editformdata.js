


function loadHeroData(data) {
    var hero = data[0];
    var powerIdList = {};
    var orgIdList = {};

    $("#heroName").val(hero.heroName);
    $("#heroType").val(hero.heroType);
    $("#heroDescription").val(hero.description);

    $.each(hero.heroPowers, function (index, p) {
        powerIdList[p.powerID] = p.powerName;
        $("#powerlist").append("<div id=" + "powers" + p.powerID + ">" + p.powerName +
            "&nbsp &nbsp <button id='" + p.powerName + "' " +
            "value='" + p.powerID + "' name='del' class='btn-circle'>x</button></div>");
    });

    $.each(hero.heroOrgs, function (index, o) {
        orgIdList[o.orgID] = o.orgName;
        $("#orglist").append("<div id=" + "orgs" + o.orgID + ">" + o.orgName +
            "&nbsp &nbsp <button id='" + o.orgName + "' " +
            "value='" + o.orgID + "' name='del' class='btn-circle'>x</button></div>");
    });


    manageformlistener(orgIdList, powerIdList);
}

