
function loadMoneyHandler(){

    initializeMoney();
    addMoney();


}

function addMoney() {

    var money = $("#totalMoney");

    $("#addDollarButton").click(function () {
        money.val((Number(money.val()) + 1.00).toFixed(2));
    });
    $("#addQuarterButton").click(function () {
        money.val((Number(money.val()) + 0.25).toFixed(2));
    });
    $("#addDimeButton").click(function () {
        money.val((Number(money.val()) + 0.10).toFixed(2));
    });
    $("#addNickelButton").click(function () {
        money.val((Number(money.val()) + 0.05).toFixed(2));
    });

}

function initializeMoney() {
    $("#totalMoney").val(0.00.toFixed(2));
}

function displayChange(change) {

    changetext = change.quarters + "x0.25,  " + change.dimes + "x0.10 <br/>" +
        change.nickels + "x0.05,  " + change.pennies + "x0.01 <br/>";
    $("#changeDisplay").html(changetext);

}

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