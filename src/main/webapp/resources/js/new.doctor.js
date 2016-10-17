/**
 * Created by ruslan on 04.10.16.
 */
$(document).ready(function () {

    var patternEmailM = getMessage('registration.message.error.patternEmailM');

    var patternFirstNameM = getMessage('manager.message.error.firstname');

    var patternLastNameM = getMessage('manager.message.error.lastname');

    var patternEducationM = getMessage('manager.message.error.education');

    var patternAddressM = getMessage('manager.message.error.address');

    var whitespaceM = getMessage('registration.message.error.whitespaceM');

    $.validator.addMethod("patternEmail", function (value, element) {
        return this.optional(element) || /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i.test(value);
    });

    $.validator.addMethod("patternFirstName", function (value, element) {
        return this.optional(element) || /^([A-Za-z]{1,40}|[А-ЯІЄЇа-яєії]{1,40})$/i.test(value);
    });

    $.validator.addMethod("patternLastName", function (value, element) {
        return this.optional(element) || /^([A-Za-z-\s]{1,40}|[А-ЯІЄЇа-яєії\s]{1,40})$/i.test(value);
    });

    $.validator.addMethod("whitespace", function (value, element) {
        return this.optional(element) || /^\S+$/i.test(value);
    });

    $.validator.addMethod("patternEducation", function (value, element) {
        return this.optional(element) || /^[a-zA-Z0-9\s,'-]*$/i.test(value);
    });

    $.validator.addMethod("patternAddress", function (value, element) {
        return this.optional(element) || /^[a-zA-Z0-9\s,'-]*$/i.test(value);
    });

    $("#registerNewUser").validate({
        rules: {
            email: {
                required: true,
                email: true,
                whitespace: "Y",
                patternEmail: "Y",
            },
            firstName: {
                required: true,
                patternFirstName: "Y",
            },
            lastName: {
                required: true,
                patternLastName: "Y",
            },
            education: {
                required: true,
                minlength: 6,
                maxlength: 100,
                patternEducation: "Y",
            },
            address: {
                required: true,
                minlength: 6,
                maxlength: 100,
                patternAddress: "Y",
            },
        },
        messages: {
            email: {
                whitespace: whitespaceM,
                patternEmail: patternEmailM
            },
            firstName: {
                patternFirstName: patternFirstNameM
            },
            lastName: {
                patternLastName: patternLastNameM
            },
            patternEducation: {
                patternEducation: patternEducationM
            },
            patternAddress: {
                patternAddress: patternAddressM
            },
        },
        errorElement: "i",
        errorPlacement: function (error, element) {
            // Add the `help-block` class to the error element
            error.addClass("help-block");

            // Add `has-feedback` class to the parent div.form-group
            // in order to add icons to inputs
            element.parents(".form-group").addClass("has-feedback");

            if (element.prop("type") === "checkbox") {
                error.insertAfter(element.parent("label"));
            } else {
                error.insertAfter(element);
            }

            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if (!element.next("span")[0]) {
                $("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
            }
        },
        success: function (label, element) {
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if (!$(element).next("span")[0]) {
                $("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).parents(".form-group").addClass("has-error").removeClass("has-success");
            $(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
            $('#floatingCirclesG').modal('hide');
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parents(".form-group").addClass("has-success").removeClass("has-error");
            $(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
        }
    });

    $("#registerSubmit").click(function (e) {
        $('#floatingCirclesG').modal('show');
    });

    $('#floatingCirclesG').modal({
        keyboard: false
    });

    $('#email').focus(function () {
        $('#errorEmail').attr("hidden", true);
    });

    $('#firstName').focus(function () {
        $('#errorFirstName').attr("hidden", true);
    });

    $('#lastName').focus(function () {
        $('#errorLastName').attr("hidden", true);
    });

    $('#education').focus(function () {
        $('#errorEducation').attr("hidden", true);
    });

    $('#address').focus(function () {
        $('#errorAddress').attr("hidden", true);
    });

});
