function manageformlistener(orgIdList, powerIdList, heroIdList, headqIdList,
                            hqOrgIdList, contactList) {
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
        "value='" + sel.val() + "' name='del' class='btn-circle'>x</button></div>");
    sel.remove();
}