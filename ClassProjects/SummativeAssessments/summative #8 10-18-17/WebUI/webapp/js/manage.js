$(document).ready(function () {



    var stringcat = getParameterByName("cat");
    var stringid = getParameterByName("id");
    if (stringcat === null){
        formdisplaymanager("heroes");
    } else {
        formdisplaymanager(stringcat);
        loadEditForm(stringcat, stringid);
    }




});

function loadEditForm(cat, id){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/"+cat+"?id="+id,
        success: function (data) {
            switch (cat){
                case "heroes": loadHeroData(data); break;
                case "orgs": break;
                case "powers": break;
                case "locations": break;
                case "headquarters": break;
                case "sightings": break;
            }
        },
        error: function () {alert("fail")}
    });
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

function manageformlistener(orgIdList, powerIdList) {
    loadfielddata();


    $("#powers").change(function () {changelist($("#powerlist"), powerIdList, "powers")});
    $("#powerlist-div").on("click", "button[name='del']", function () {
        listbutton(powerIdList, "powers", $(this).val(), $(this).attr('id'));
    });

    $("#orgs").change(function () {changelist($("#orglist"), orgIdList, "orgs")});
    $("#orglist-div").on("click", "button[name='del']", function () {
        listbutton(orgIdList, "orgs", $(this).val(), $(this).attr('id'));
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
        hero["heroType"] = $("#heroType").val();
        hero["description"] = $("#heroDescription").val();
        hero["heroPowers"] = plist;
        hero["heroOrgs"] = olist;

        postData("heroes",hero);

    });

    $("#hero-remove").click(function () {
        //deleteData("heroes?id=");
    });



    function postData(urlname, data){
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/"+urlname,
            data: JSON.stringify(data),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"},
            success: function () {
                clearHeroForm();
            },
            error: function () {alert("fail")}
        });
    }

    function deleteData(urlname){
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/"+urlname,
            success: function () {},
            error: function () {alert("fail")}
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


}

function clearHeroForm(){
    $("#heroName").val("");
    $("#heroType").val("Default");
    $("#heroDescription").val("");
    $("#powerlist").empty();
    $("#orglist").empty();
    loadlist("powers","powerName","powerID");
    loadlist("orgs","orgName","orgID");
}

function loadfielddata() {
    loadlist("powers","powerName","powerID");
    loadlist("orgs","orgName","orgID");
}

function loadlist(listname,val,key) {
    $("#"+listname).empty();
    var list = {"Choose...": "choose"};
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/"+listname+"?load=false",
        success: function (results) {

            $.each(results, function (index, res) {
                list[res[val]] = res[key];
            });

            var $id = $("#"+listname);
            $id.empty(); // remove old options
            $.each(list, function (key, value) {
                $id.append($("<option></option>")
                    .attr("value", value).text(key));
            });
        },
        error: function () {alert("fail")}
    });
}

function formdisplaymanager(editcat) {
    $("#category").change(function () {
        var category = $("#category").val();
        hideforms();
        switch (category) {
            case "heroes":
                $("#heroes-form").show();
                break;
            case "orgs":
                $("#orgs-form").show();
                break;
            case "headquarters":
                $("#headquarters-form").show();
                break;
            case "powers":
                $("#powers-form").show();
                break;
            case "locations":
                $("#locations-form").show();
                break;
            case "sightings":
                $("#sightings-form").show();
                break;
        }
    });
    if (editcat !== null){
        hideforms();
        $("#"+editcat+"-form").show();
        $('#category').val(editcat).prop('selected', true);
    }
}

function hideforms() {
    $("#heroes-form").hide();
    $("#orgs-form").hide();
    $("#headquarters-form").hide();
    $("#sightings-form").hide();
    $("#powers-form").hide();
    $("#locations-form").hide();
}

