"use strict";
jQuery(function () {
     getPageData();
});

function getPageData(){

    const baseURL = "https://lb.511mn.org/mnlb/cameras/camera.jsf?id=";
    var cameraList=[];
    var bloblist=[];


    for (var i = 1; i < 1000; i++) {

        $.ajax({
            url: baseURL + i,
            indexValue: i,
            type: 'GET',
            async: false,
            success: function (data) {
                var cam = {
                    "id": this.indexValue,
                    "src": [],
                    "latitude": "null",
                    "longitude": "null",
                    "camnum": "null",
                    "title": "null",
                    "description": "null",
                    "enabled": false
                };

                //################################################################
                // Obtain all of the SRC links for each camera ID
                //################################################################
                var baseid = 0;
                while (true) {

                    const camimg = "#cam-" + baseid + "-img";
                    const src = $(data).find(camimg).attr("src");

                    if (!(src === undefined || src === null || baseid > 5)) {

                        var srcString = src.toString().replace(/(\?id=)/gi, "/metro/C");

                        if (baseid === 0) {
                            var c = srcString.split("/");
                            cam.camnum = c[c.length - 1];
                            cam.enabled = true;
                            cam.src.push(srcString);

                        } else {
                            if (srcString !== cam.src[cam.src.length - 1]) {
                                cam.src.push(srcString);
                            }
                        }
                        baseid++;
                    } else {

                        // if there is no SRC, set to null
                        if (cam.src.length === 0){
                            cam.src.push("null");
                        }

                        break
                    }
                }

                //################################################################
                // Look for additional data (title, description, coordinates)
                //################################################################
                try {
                    var titleparts = $(data).find('#rptitle').text().split("\n");
                    cam.title = nullify((titleparts[0].trim() + " " + titleparts[1].trim()).replace(/@\S/gi,"@ "));

                } catch (e) {}

                try {
                    cam.description = nullify($(data).find('table .panelTitle').next().text().trim());
                } catch (e) {}

                try {
                    const googleLink = ($(data).find("img[alt='Google Static Map Image']").attr("src")).toString();
                    const tokens = googleLink.split(/(=|%2C|&)/i);
                    cam.latitude = nullify(tokens[2]);
                    cam.longitude = nullify(tokens[4]);
                } catch (e) {}


                //################################################################
                // Check if disabled cameras can be recovered
                //################################################################
                if (!cam.enabled) {
                    cam = validateCamera(cam, cameraList[this.indexValue - 1]);
                }

                //################################################################
                // Update camera arrays
                //################################################################

                // Global object list
                cameraList[this.indexValue] = cam;

                // List for blob output
                bloblist.push(pushString(cam));

                // Console verification
                console.log("Camera " + cam.id + ": " + cam.enabled.toString().replace("false","Disabled").replace("true","Enabled"));

            }
        });
    }

    var blob = new Blob([bloblist.join("")], {type: "text/plain;charset=utf-8"});
    alert("Finished...");
    window.saveAs(blob, "data.csv");

}

function pushString(cam) {
    const delim = ",";

    var lines = "";
    $.each (cam.src, function (k,v) {

        lines += cam.id + delim
            + cam.camnum + delim
            + cam.latitude + delim
            + cam.longitude + delim
            + cam.title + delim
            + cam.description + delim
            + cam.enabled + delim
            + v + "\n";

    });

    return lines;
}

function nullify(str){
    if (str === null ||
        str === undefined ||
        str.toString().trim() === "" ||
        str.toString().trim() === ":"){
        return "null";
    } else {
        return str.trim();
    }
}

function validateCamera(cam, lastcam){

    if (cam.title !== "null" && cam.camnum === "null"){
        var oldnum = lastcam.camnum;
        var oldsrc = lastcam.src[0];
        var newnum = "C" + (parseInt(oldnum.replace("C","")) + 1).toString();
        var newsrc = oldsrc.replace(oldnum,newnum);

        $.ajax({
            url: newsrc,
            type: 'GET',
            async: false,
            success: function () {
                cam.camnum = newnum;
                cam.enabled = true;
                cam.src[0] = newsrc;
            }
        });


    }

    return cam;
}
