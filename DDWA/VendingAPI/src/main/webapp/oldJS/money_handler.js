
// Initialize the money handling system
function loadMoneyHandler(){

    initializeMoney();
    addMoney();


}

// Handles the money button clicks and increments the stored
// value by the specified amount.  Changes the screen text
// to say "Make a selection"
function addMoney() {

    var money = $("#totalMoney");

    $("#addDollarButton").click(function () {
        money.val((Number(money.val()) + 1.00).toFixed(2));
        $("#msgTxt").html("Make a selection...");
        $("#blkTxt").html("");
    });
    $("#addQuarterButton").click(function () {
        money.val((Number(money.val()) + 0.25).toFixed(2));
        $("#msgTxt").html("Make a selection...");
        $("#blkTxt").html("");
    });
    $("#addDimeButton").click(function () {
        money.val((Number(money.val()) + 0.10).toFixed(2));
        $("#msgTxt").html("Make a selection...");
        $("#blkTxt").html("");
    });
    $("#addNickelButton").click(function () {
        money.val((Number(money.val()) + 0.05).toFixed(2));
        $("#msgTxt").html("Make a selection...");
        $("#blkTxt").html("");
    });

}

// Reset money
function initializeMoney() {
    $("#totalMoney").val(0.00.toFixed(2));
}

// Takes a money object and displays the contents in the form of
// change denominations.  Fractional cents are counted in pennies
// for completeness
function displayChange(change) {

    changetext = change.quarters + "x0.25,  " + change.dimes + "x0.10 <br/>" +
        change.nickels + "x0.05,  " + change.pennies + "x0.01 <br/>";
    $("#changeDisplay").html(changetext);

}

// Breaks down the change if the user presses the coin return button
function countChange(totalMoney) {
    totalMoney = 100 * totalMoney;

    var quarters = (totalMoney - totalMoney % 25) / 25;
    var remainder = totalMoney - quarters * 25;

    var dimes = (remainder - remainder % 10) / 10;
    remainder = remainder - dimes * 10;

    var nickels = (remainder - remainder % 5) / 5;
    var pennies = remainder - nickels * 5;


    return {
        "quarters": quarters,
        "dimes": dimes,
        "nickels": nickels,
        "pennies": pennies
    };


}