"use strict";
jQuery(function () {
    listener();
    getState();
    initSliders();
    $('.hsb-box').mask('000');
    getConfigurations();

});


function initSliders() {
    $("#sat-slider").slider({
        orientation: "horizontal",
        min: -1,
        max: 101
    });

    $("#bright-slider").slider({
        orientation: "horizontal",
        min: -1,
        max: 101
    });

    $("#hue-slider").slider({
        orientation: "horizontal",
        min: -1,
        max: 366
    });

}

function getState() {

    const URL = "http://192.168.50.74/api/4n2F-KlHAgUiwi4DrLLsd6V0GmiQw1oKoR69811s/lights";

    $.ajax({
        url: URL,
        type: 'GET',
        contentType: 'application/json',
        success: function (data) {

            const br = Math.round(data[1].state.bri / 2.55);
            const sa = Math.round(data[1].state.sat / 2.55);
            const hu = Math.round(data[1].state.hue / 181.3333);

            $("#bright").val(br);
            $("#sat").val(sa);
            $("#hue").val(hu);
            $("#lightState").val(data[1].state.on);

            $("#picker-div").ColorPickerSetColor({b: br, h: hu, s: sa});

            $("#sat-slider").slider("value", sa);
            $("#hue-slider").slider("value", hu);
            $("#bright-slider").slider("value", br);


        }
    });


}


function listener() {

    $("#hue").keypress(function () {
        updatePicker()
    });
    $("#sat").keypress(function () {
        updatePicker()
    });
    $("#bright").keypress(function () {
        updatePicker()
    });

    $("#sat-slider").on("slide", function (event, ui) {
        $("#sat").val($("#sat-slider").slider("value"));
        updatePicker()
    });

    $("#hue-slider").on("slide", function (event, ui) {
        $("#hue").val($("#hue-slider").slider("value"));
        updatePicker()
    });

    $("#bright-slider").on("slide", function (event, ui) {
        $("#bright").val($("#bright-slider").slider("value"));
        updatePicker()
    });

    $("#send-hue").click(function () {
        for (let i = 1; i < 5; i++) {
            setLightState(i, true, $("#hue").val(), $("#sat").val(), $("#bright").val(), i);
        }
    });


    $("#power-button").click(function () {
        const newState = ($("#lightState").val() === "false");
        for (let i = 1; i < 5; i++) {
            setLightState(i, newState, $("#hue").val(), $("#sat").val(), $("#bright").val(), i);
        }
    });

    $('#picker-div').ColorPicker({
        flat: true,
        onChange: function (hsb, hex, rgb, el) {

            $("#hue").val(parseInt(hsb.h));
            $("#sat").val(parseInt(hsb.s));
            $("#bright").val(parseInt(hsb.b));

            $("#sat-slider").slider("value", parseInt(hsb.s));
            $("#hue-slider").slider("value", parseInt(hsb.h));
            $("#bright-slider").slider("value", parseInt(hsb.b));
        }
    });


    $("#send-hue-multi").click(function () {
        $("#light-table").find('tr').each(function (i, el) {
            let $tds = $(this).find('td'),
                id = $tds.eq(0).text();

            let hexBox = $('#light-' + id + '-hex');
            let enabledBox = $('#light-' + id + '-on');
            const c = tinycolor(hexBox.val()).toHsv();

            setLightState(id, enabledBox.prop('checked'), c.h, c.s*100, c.v*100);

        })
    });


}


function updatePicker() {

    const br = Math.round($("#bright").val());
    const hu = Math.round($("#hue").val());
    const sa = $("#sat-slider").slider("value");

    $("#picker-div").ColorPickerSetColor({b: br, h: hu, s: sa});

}


function setLightState(lightID, onState, hue, saturation, brightness, i) {

    let data = {
        "on": onState,
        "sat": Math.round(saturation * 2.55),
        "bri": Math.round(brightness * 2.55),
        "hue": Math.round(hue * 181.333)
    };
    const URL = "http://192.168.50.74/api/4n2F-KlHAgUiwi4DrLLsd6V0GmiQw1oKoR69811s/lights/" + lightID + "/state";

    $.ajax({
        url: URL,
        type: 'PUT',
        async: false,
        indexValue: i,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            if (i === 4) {
                location.reload()
            }
        }
    });
}

function getConfigurations() {

    const URL = "http://localhost:9000/lights";

    $.ajax({
        url: URL,
        type: 'get',
        async: false,
        contentType: 'application/json',
        success: function (data) {

            $.each(data, function (k, v) {

                const id = v.lightID;

                let row = "<tr>";
                row +=
                    '<td><span class="light-id-col">'+ id +'</span></td>' +
                    '<td><input type="checkbox" class="light-check" id="light-' + id + '-on" /></td> ' +
                    '<td># &nbsp<input class="hex-box" id="light-' + id + '-hex" /></td>' +
                    '<td><div class="pick-box" id="light-' + id + '-picker" >&nbsp</div></td>' +
                    '<td><input class="title-box" id="light-' + id + '-title" value="'+v.title+'"/></td>' +
                    '<td><input class="description-box" id="light-' + id + '-description" value="'+v.description+'"/></td>' +
                    '<td><span class="update-link" id="light-' + id + '-update">Send Update</span></td>'
                ;

                row += '</tr>';

                $("#light-table").append(row);

                $.ajax({
                    url: "http://192.168.50.74/api/4n2F-KlHAgUiwi4DrLLsd6V0GmiQw1oKoR69811s/lights/" + id,
                    type: 'GET',
                    async: false,
                    contentType: 'application/json',
                    success: function (data) {

                        const hexBox = $('#light-' + id + '-hex');
                        const enabledBox = $('#light-' + id + '-on');
                        const updateSwitch = $('#light-' + id + '-update');
                        const pickerBox = $("#light-" + id + "-picker");

                        const br = Math.round(data.state.bri / 2.55);
                        const sa = Math.round(data.state.sat / 2.55);
                        const hu = Math.round(data.state.hue / 181.3333);

                        let colorHSB = tinycolor("hsv("+ hu + "," + sa + "%," + br + "%)");

                        hexBox.val(colorHSB.toHex());
                        pickerBox.css({"background-color": "#" + colorHSB.toHex()});
                        pickerBox.ColorPicker({
                            color: colorHSB.toHex(),
                            onShow: function (colpkr) {
                                $(colpkr).fadeIn(500);
                                return false;
                            },
                            onHide: function (colpkr) {
                                $(colpkr).fadeOut(500);
                                return false;
                            },
                            onChange: function (hsb, hex, rgb) {
                                pickerBox.css('backgroundColor', '#' + hex);
                                hexBox.val(hex);
                            },
                        });

                        hexBox.mask('ZZZZZZ', {
                            translation: {
                                'Z': {
                                    pattern: /[a-fA-F0-9]/, optional: false
                                }
                            }
                        });


                        function setColors(){
                            pickerBox.ColorPickerSetColor(hexBox.val());
                            pickerBox.css({"background-color": '#'+hexBox.val()});
                        }

                        enabledBox.prop('checked', data.state.on);
                        enabledBox.click(function () {
                            onOff(id, enabledBox.prop('checked'));
                        });

                        updateSwitch.click(function () {
                            const c = tinycolor(hexBox.val()).toHsv();
                            setLightState(id, enabledBox.prop('checked'), c.h, c.s*100, c.v*100);
                        });

                        hexBox.keyup(function () { setColors()  });
                        hexBox.keydown(function () { setColors()  });
                        hexBox.change(function () { setColors()  });

                    }
                });


            });


        }
    });
}



function onOff(lightID,onState){
    const URL = "http://192.168.50.74/api/4n2F-KlHAgUiwi4DrLLsd6V0GmiQw1oKoR69811s/lights/" + lightID + "/state";

    let data = {
        "on": onState,
    };

    $.ajax({
        url: URL,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
        }
    });
}

