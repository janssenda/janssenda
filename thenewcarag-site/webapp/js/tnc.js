$(document).ready(function () {


modallistener();
navListener();
});


function modallistener(){
    var modal = $("#myModal")[0];
    var modalImg = $("#modalImg")[0];
    var captionText = $("#caption")[0];

    $(".close").click(function(){
        modal.style.display = "none"
    });

    $("#brand").click(function () {
        modal.style.display = "none"
    });

    $(document).on("click", ".post-image", function () {
        modal.style.display = "block";
        modalImg.src =this.src.substring(0,this.src.length-10)+".png";
        captionText.innerHTML = this.alt;
    });
}

function navListener() {
    var menu = $("#dropmenu");
    menu.hover(function () {
        menu.addClass("open");
    });
    menu.mouseleave(function () {
        menu.removeClass("open");
    });

    var navbutton = $(".navbutton");
    navbutton.mouseenter(function () {
       //navbutton.addClass("open");
        navbutton.addClass("buttonbg");

    });
    navbutton.mouseleave(function () {
        // navbutton.removeClass("open");
        navbutton.removeClass("buttonbg");
    });
}