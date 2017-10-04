$(document).ready(function () {


    $("h1,h2").css({"text-align": "center"});
    $(".myBannerHeading").addClass("page-header");
    $(".myBannerHeading").removeClass("myBannerHeading");
    $("#yellowHeading").text("Yellow Team");
    $("#orangeHeading").css({"background-color": "orange"});
    $("#yellowHeading").css({"background-color": "yellow"});
    $("#redHeading").css({"background-color": "red"});
    $("#blueHeading").css({"background-color": "blue"});
    $("#yellowTeamList").append(
            "<ul><li>Joseph Banks</li><li>Simon Jones</li></ul>");
    
    $("#oops").hide();
    
    $("#footerPlaceholder").remove();
    
    var message = "Danimae Janssen \njanssenda@gmail.com";
    
    $("footer").text(message).css({"font": "24px courier", "white-space": "pre-line"});


});