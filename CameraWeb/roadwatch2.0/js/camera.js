$(document).ready(function () {

    $("#date").append("Time: " + moment().format("LLL"));

  //  parseCsv();
  //  search();

});





function search() {

    search_query("");

    $("#search_button").click(function () {
        $(".cam-a").hide();
        search_query($("#search_box").val());
    });
}

function search_query(terms) {
    if (terms !== undefined && terms.trim() !== "") {
        var termArr = terms.split(" ");
        $.each(termArr, function (k, v) {
            $("a[name*='" + v.trim().toLowerCase() + "']").show();
        });

    } else {
        $(".cam-a").show();

    }
}

function parseCsv() {
    $.get("https://thenewcarag.com/roadwatch/camera_list.csv", function (data) {
        var rows = data.split("\n");
        var camList = [];
        rows.splice(0, 1);

        $.each(rows, function (k, v) {

            var cr = v.split(",");
            var img = new Image();
            img.onerror = function () {
            };
            img.onabort = function () {
            };
            img.src = "https://video.dot.state.mn.us/video/image/metro/" + cr[0] + ".jpg";
            camList[k] = {cnum: cr[0], cloc: cr[1], cdesc: cr[2], cimg: img};

        });

        loadImagesAdv(camList);

    });
}

function loadImagesAdv(camList) {

    $.each(camList, function (k, v) {


        v.cimg.onload = function () {
            var box = $("#image-box");
            var searchstring = v.cloc.toLowerCase() + v.cdesc.toLowerCase() + v.cnum.toLocaleLowerCase();

            box.append("<a class='cam-a' id='cam" + k + "' name='" + searchstring + "' " +
                "href='" + v.cimg.src + "' target='_blank'><div class='cam-image' style='background-image: url(" + v.cimg.src + ")'>" +
                "<div class='tdiv'>" + v.cnum + ": " + v.cloc + "<br/>" + v.cdesc + "</div></div></a>");
            var cam = $("#cam" + k);
            cam.hide();

            var terms = $("#search_box").val().toLowerCase();
            if (terms === undefined || terms === "") {
                cam.show();
            } else {
                var termArr = terms.split(" ");
                $.each(termArr, function (k, v) {
                    if (searchstring.search(v.trim()) >= 0) {
                        cam.show();
                    }
                });
            }
        };


    });
}





