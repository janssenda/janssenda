
$(document).ready(function () {

      testPage();

});



function testPage(){


    var baseURL = "https://lb.511mn.org/mnlb/cameras/camera.jsf;jsessionid=7EHC_g5oVp3AFbj9QaxsxBmmzwN5ArIya0HyvpBq.ip-10-4-75-78?id=";
    var page = "https://lb.511mn.org/mnlb/cameras/camera.jsf;jsessionid=7EHC_g5oVp3AFbj9QaxsxBmmzwN5ArIya0HyvpBq.ip-10-4-75-78?id=408";


    $.ajax({
        url:page,
        type:'GET',
        success: function(data){
            console.log($(data).find('#cam-0-img').attr("src"));
            console.log($(data).find("img[alt='Google Static Map Image']").attr("src"));
        }
    });


}

function parseFullCameraCsv() {
    $.get("http://thenewcarag.com/roadwatch/camera_list.csv", function (data) {
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
            img.src = "http://video.dot.state.mn.us/video/image/metro/" + cr[0] + ".jpg";
            camList[k] = {cnum: cr[0], cloc: cr[1], cdesc: cr[2], cimg: img};

        });

        loadImagesAdv(camList);

    });
}