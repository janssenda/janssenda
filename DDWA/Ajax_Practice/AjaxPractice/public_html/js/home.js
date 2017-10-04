/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/contacts",
        success: function (contactArray) {

            var contactsDiv = $("#allContacts");

            for (var i = 0; i < contactArray.length; i++) {
                var contactInfo = "<p>";

                contactInfo += "Name: " + contactArray[i].firstName + " " + contactArray[i].lastName + "<br>";
                contactInfo += "Company: " + contactArray[i].company + "<br>";
                contactInfo += "Email: " + contactArray[i].email + "<br>";
                contactInfo += "Phone: " + contactArray[i].phone + "<br>";
                contactInfo += "</p>";
                contactInfo += "<hr>";

                contactsDiv.append(contactInfo);
            }

//            $.each(contactArray, function (index, contact) {
//                var contactInfo = "<p>";
//
//                contactInfo += "Name: " + contact.firstName + " " + contact.lastName + "<br>";
//                contactInfo += "Company: " + contact.company + "<br>";
//                contactInfo += "Email: " + contact.email + "<br>";
//                contactInfo += "Phone: " + contact.phone + "<br>";
//                contactInfo += "</p>";
//                contactInfo += "<hr>";
//
//                contactsDiv.append(contactInfo);
//
//            });




        },
        error: function () {
            alert("FAILURE");
        }
    });

    $("#add-button").click(function () {

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/contact",
            data: JSON.stringify({
                firstName: $("#add-first-name").val(),
                lastName: $("#add-last-name").val(),
                company: $("#add-company").val(),
                phone: $("#add-phone").val(),
                email: $("#add-email").val()
            }),

            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"},
            "dataType": "json",

            success: function (contact) {

                var newContactDiv = $("#newContact");

                var contactInfo = "<p>";

                contactInfo += "Name: " + contact.firstName + " " + contact.lastName + "<br>";
                contactInfo += "Company: " + contact.company + "<br>";
                contactInfo += "Email: " + contact.email + "<br>";
                contactInfo += "Phone: " + contact.phone + "<br>";
                contactInfo += "</p>";
                contactInfo += "<hr>";

                newContactDiv.append(contactInfo);


            },
            error: function () {
                alert("FAILURE");
            }

        });
    });


});
    