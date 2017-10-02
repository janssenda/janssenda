

// Load item grabs the clicked item and displays it in the item display
// displayID is the letter-row value (e.g., A3), and the itemId (hidden)
// is the actual id for fetching from web server
function loadItem(itemId, displayId) {
    $("#itemDisplay").val(displayId);
    $("#currentItem").val(itemId);
}


// Retrieves all items from the server and sends them to
// the function listAllItems, which builds the rows
// Returns a server error if the call fails
function getAllItems() {
    var call = " http://localhost:8080/items";

    $.ajax({
        type: "GET",
        url: call,
        success: function (vendingInventory) {
            listAllItems(vendingInventory);
        },
        error: function () {
            $("#serverError").show();
        }
    });
}

// Asks the server to vend the requested item.  Sends a URL
// ajax request containing the itemId and the totalMoney
// currently stored in the money field.
function vendItem(itemId, totalMoney) {
    var call = " http://localhost:8080/money/"
        + totalMoney + "/item/" + itemId;

    $.ajax({
        type: "GET",
        url: call,

        // on success, returns change and a thank you message
        success: function (change) {
            $("#msgTxt").html("Thank you!!! ");
            displayChange(change);
            timeReset();
        },

        // if the call fails, the cause is ascertained.  If the server is still available,
        // the server side exception is displayed: insufficient funds or sold out.  If the
        // server can't be reached, the server error message is displayed
        error: function (errmsg, txtstatus) {

            if (errmsg.readyState === 4) {
                var msg = JSON.parse(errmsg.responseText).message;
                $("#msgTxt").html(msg);
            }
            else if (errmsg.readyState === 0) {
                $("#serverError").show();
            }

            timeResetMsg();
        }
    });

}

// Builds the HTML for the item listing.  There are three columns by N rows,
// where N is determined by the number of items returned by the server divided
// by 3. 3 rows are always displayed, with empty slows where no items were returned
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


    // Go through the returned item list and build a list of element objects.
    // Each object contains the complete HTML for one item
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

    // Blank place holder to be used if there are no more items
    var blankSpace = "<a  class = 'itemBox'>" +
    "<div class='item rounded'>" +
    "<div class='itemInner'>" +
    "<div class='itemDisplayNumber'>&nbsp</div>" +
    "<div class='itemName'>< No Item ></div>" +
    "<div class='itemPrice'>&nbsp</div>" +
    "<div class='itemQuantity'>&nbsp</div>" +
    "<div class='itemNumber' id='-1'>&nbsp</div>" +
    "</div></div></a>";


    // Go through the list of items and construct each row by taking them 3 at a time.
    var index = 0;
    for (var j = 0; j < numRows; j++) {
        var rowContent = row_header;
        for (var i = 0; i < 3; i++) {
            // build the item

            if (index > itemList.length){
                rowContent += colDiv + blankSpace + "</div>";
            } else {
                rowContent += colDiv + itemList[index] + "</div>";
            }

            index += 1;
        }
        rowContent += "</div>";
        
        // Finally, post the items to the main item grid
        $("#itemAreaMainDiv").append(rowContent);
    }


    $("#itemAreaMainDiv").append("<div style='text-align: left; color: #e7e8ea; font-size: 12px;" +
        "margin-bottom: -10px; margin-left: -5px;'>" +
        "Danimae Janssen - 2017</div>");


    // Listen for item clicks so they can be loaded
    $(".itemBox").on("click", function () {
        var itemId = $(this).find('div.itemNumber').attr('id');
        var displayId = $(this).find('div.itemDisplayNumber').attr('id');

        loadItem(itemId, displayId);
    });


}