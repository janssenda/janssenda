/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    initialize();
    navbarListener();
    searchHandler();






});

function initialize() {
    $("#add-div").hide();
    $("#report-div").hide();
    $("#search-div").hide();
    $("#home-div").show();
}

function navbarListener () {

    var search = document.getElementById("search-link");
    var add = document.getElementById("add-link");
    var home = document.getElementById("home-link");
    var report = document.getElementById("report-link");
    var logo = document.getElementById("logo-link");

    search.onclick = function() {
        $("#home-div").hide();
        $("#add-div").hide();
        $("#report-div").hide();
        $("#search-div").show();
    };

    logo.onclick = function() {
        $("#add-div").hide();
        $("#report-div").hide();
        $("#search-div").hide();
        $("#home-div").show();
    };

    home.onclick = function() {
        $("#add-div").hide();
        $("#report-div").hide();
        $("#search-div").hide();
        $("#home-div").show();
    };

    add.onclick = function() {
        $("#home-div").hide();
        $("#report-div").hide();
        $("#search-div").hide();
        $("#add-div").show();
    };

    report.onclick = function() {
        $("#home-div").hide();
        $("#add-div").hide();
        $("#search-div").hide();
        $("#report-div").show();
    };

}