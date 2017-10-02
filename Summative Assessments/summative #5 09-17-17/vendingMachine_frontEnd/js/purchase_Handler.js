function loadPurchaseHandler() {


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


    $("#changeButton").click(function () {
        var totalMoney = $("#totalMoney");
        if (totalMoney.val() > 0) {

            $("#blkTxt").html("");
            displayChange(countChange(totalMoney.val()));
            totalMoney.val(0.00.toFixed(2));

            $("#msgTxt").html("Thank you!!! ");

            // $("#myModal").modal("show");
            // $('#myModal').on('hide.bs.modal', function () {
                timeReset();
            // });
        }
    });

}

