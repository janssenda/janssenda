/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var sentence = "I love learning software development";

for (var i = 0; i < sentence.length; i++) {
    if (sentence[i] !== " ") {
        console.log(sentence[i]);
    }
}

// Run a named function when the document is ready.
$(document).ready(handleReady);

// Run an anonymous function when the document is ready.
//$(document).ready(function () {
//    alert("Ready to go!");
//});

function handleReady() {



////    alert("Ready to go!");
////    $('#emptyDiv').append('p').text('A new paragraph of text...');
////    $('#emptyDiv').append('p').text('A new paragraph of text...');
////    $(".centercontent").append().text("THIS IS A TEST!!!");
//    $('#myDiv').css({'width': '400'});
//
//    $('H1').css('color', 'blue');
//    $('#newButton').addClass('btn btn-default');
//    $('H1').removeClass("text-center");
//
//    $("#newButton").on("click", function () {
//        $("h1").toggle("slow");
//    });
//
//    $("div").hover(
//            // in callback
//                    function () {
//                        $(this).css("background-color", "CornflowerBlue");
//                    },
//                    // out callback
//                            function () {
//                                $(this).css("background-color", "Grey");
//                            }
//                    );
//                }

}