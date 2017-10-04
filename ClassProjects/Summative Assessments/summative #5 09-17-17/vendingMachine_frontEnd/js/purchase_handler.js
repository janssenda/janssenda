function loadPurchaseHandler() {


    // Listens for click on the purchase button.  Calls the item
    // vend service for the item currently loaded in the item ID box
    $("#purchaseButton").click(function () {

        $("#blkTxt").html("");
        var itemId = $("#currentItem").val();
        var totalMoney = $("#totalMoney").val();


        if (totalMoney < 0.01) {
            $("#msgTxt").html("Add money!!");
            timeResetMsg();
        } else {
            if (itemId < 0) {
                $("#msgTxt").html("Choose an item!!");
                timeResetMsg();
            } else {
                vendItem(itemId, totalMoney);
            }
        }
    });


    // Triggers the change breakdown
    $("#changeButton").click(function () {
        var totalMoney = $("#totalMoney");
        if (totalMoney.val() > 0) {

            $("#blkTxt").html("");
            displayChange(countChange(totalMoney.val()));
            totalMoney.val(0.00.toFixed(2));

            $("#msgTxt").html("Thank you!!! ");

            timeReset();
        }
    });

}

