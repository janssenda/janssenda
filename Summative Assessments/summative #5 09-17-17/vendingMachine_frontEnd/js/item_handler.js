

function loadItem(itemId, displayId) {
    $("#itemDisplay").val(displayId);
    $("#currentItem").val(itemId);
}



function getAllItems() {
    var call = " http://localhost:8080/items";

    $.ajax({
        type: "GET",
        url: call,
        success: function (vendingInventory) {
            listAllItems(vendingInventory);
        },
        error: function () {
            alert("failure");
        }
    });
}

function vendItem(itemId, totalMoney) {
    var call = " http://localhost:8080/money/"
        + totalMoney + "/item/" + itemId;

    $.ajax({
        type: "GET",
        url: call,
        success: function (change) {
            $("#msgTxt").html("Thank you!!! ");
            displayChange(change);
            timeReset();
        },
        error: function (errmsg) {
            var msg = JSON.parse(errmsg.responseText).message;
            $("#msgTxt").html(msg);
            timeResetMsg();
        }
    });

}



function listAllItems(vendingInventory) {
    $("#itemAreaMainDiv").empty();
    var rowLetters = ["A", "B", "C", "D", "E", "F", "G"];
    var row_header = "<div class='row text-center'>";
    var colDiv = "<div class='col-sm-4 item-container'>";


    var numItems = vendingInventory.length;
    var numRows = 0;

    if (numItems % 3 === 0) {
        numRows = numItems / 3;
    } else {
        numRows = (numItems - numItems % 3) / 3 + 1;
    }

    var itemList = [];
    var letterIndex = 0;
    var numIndex = 0;

    $.each(vendingInventory, function (index, item) {

        numIndex += 1;
        if (index % 3 === 0 && index > 0) {
            letterIndex += 1;
            numIndex = 1;
        }

        var itemDisplayId = rowLetters[letterIndex] + numIndex;


        itemList[index] =
            "<a  class = 'itemBox'>" +
            "<div class='item rounded'>" +
            "<div class='itemInner'>" +
            "<div class='itemDisplayNumber' id='" + itemDisplayId + "'>" + itemDisplayId + "</div>" +
            "<div class='itemName'>" + item.name + "</div>" +
            "<div class='itemPrice'>$" + item.price.toFixed(2) + "</div>" +
            "<div class='itemQuantity'>Remaining: " + item.quantity + "</div>" +
            "<div class='itemNumber' id='" + item.id + "'>" + item.id + "</div>" +
            "</div></div></a>";
    });


    var index = 0;
    for (var j = 0; j < numRows; j++) {
        var rowContent = row_header;
        for (var i = 0; i < 3; i++) {
            // build the item

            rowContent += colDiv + itemList[index] + "</div>";

            index += 1;
        }
        rowContent += "</div>";
        $("#itemAreaMainDiv").append(rowContent);
    }

    $("#itemAreaMainDiv").append("<div style='text-align: left; color: #e7e8ea; font-size: 12px;" +
        "margin-bottom: -10px; margin-left: -5px;'>" +
        "Danimae Janssen - 2017</div>");

    $(".itemBox").on("click", function () {
        var itemId = $(this).find('div.itemNumber').attr('id');
        var displayId = $(this).find('div.itemDisplayNumber').attr('id');

        loadItem(itemId, displayId);
    });


}