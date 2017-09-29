/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {


    var numItems = 9;

    listAllItems();




});



function listAllItems(numItems){


    for (var j = 0; j < 3; j++) {

        var row_header = "<div class='row text-center'>";
        var colDiv = "<div class='col-sm-4 item-container'>";
        var rowContent = row_header;

        for (var i = 0; i < 3; i++) {

            // build the item
            var item = "XXXX";

            rowContent += colDiv + "<div class='item'>" + item + "</div>" +"</div>"

        }

        rowContent += "</div>";
        console.log(rowContent);

        $("#itemAreaMainDiv").append(rowContent);


    }
}



