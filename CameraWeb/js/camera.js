$(document).ready(function () {

    $("#date").append("Time: " + moment().format("LLL"));

    loadImages();

});

function refreshImages() {
    setTimeout(function () {
        $("#image-box").empty();
        loadImages();
        refreshImages();
    }, 5000);
}

function loadImages(){
    var imgArray = [];
    var keyArr = [];
    var count = 620;
    var i = 0;
    var loc = ["35W", "94E"];
    var cLoc = loc[0];

    while (count < 856) {
        if (count === 627
            || count === 833
            || count === 847) {
            count++
        }

        img = new Image();
        img.src = "http://video.dot.state.mn.us/video/image/metro/C" + count + ".jpg";
        imgArray[i] = img;

        if (count === 633) {
            count = 831;
            cLoc = loc[1];
        }

        keyArr[i] = cLoc + ": " + count;
        count++;
        i++;
    }

    $.each(imgArray, function (k, v) {
        v.onload = function () {
            var box = $("#image-box");
            box.append("<a href='"+v.src+"' target='_blank'><div class='cam-image' style='background-image: url(" + v.src + ")'>" +
                "<div class='tdiv'>" + keyArr[k] + "</div></div></a>");
        };
    });

    //refreshImages();
}
