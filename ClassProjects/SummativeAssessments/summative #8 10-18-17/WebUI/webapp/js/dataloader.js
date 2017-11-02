


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


    manageformlistener(orgIdList, powerIdList, {}, {}, {}, {});
}

function loadfielddata() {
    loadlist("powers","powerName","powerID","#powers");
    loadlist("orgs","orgName","orgID","#orgs");
    loadlist("heroes","heroName","heroID", "#heroes");
    loadlist("headquarters","headQName","headQID","#headquarters");
    loadlist("orgs","orgName","orgID","#hq-orgs");
}

function loadlist(listname,val,key,selectid) {
    $("#"+listname).empty();
    var list = {"Choose...": "choose"};
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/"+listname+"?load=false",
        success: function (results) {

            $.each(results, function (index, res) {
                list[res[val]] = res[key];
            });

            var id = $(selectid); //$("#"+listname);
            id.empty();
            $.each(list, function (key, value) {
                id.append($("<option></option>")
                    .attr("value", value).text(key));
            });
        },
        error: function () {alert("fail")}
    });
}