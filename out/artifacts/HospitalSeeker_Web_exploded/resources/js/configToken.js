$(document)
		.ready(
				function() {

					$('#button-cancelToken').mousedown(function() {
						window.location.href = '../';
						return false;
					});



					

					$('#configTokenForm')
							.validate(
									{
										rules : {
											'configs[0].value' : {
												required : true,
												number : true,
												range : [ 12, 48 ]
											},
											'configs[1].value' : {
												required : true,
												number : true,
												range : [ 24, 72 ]
											},
											'configs[2].value' : {
												required : true,
												number : true,
												range : [ 24, 168 ]
											},
											'configs[3].value' : {
												required : true,
												number : true,
												range : [ 102400, 9999999 ]
											}

										},

										 errorElement: "i",
									        errorPlacement: function (error, element) {
									            error.addClass("help-block");

									            element.parents(".form-group").addClass("has-feedback");

									            if (element.prop("type") === "checkbox") {
									                error.insertAfter(element.parent("label"));
									            } else {
									                error.insertAfter(element);
									            }

									            if (!element.next("span")[0]) {
									                $("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
									            }
									        },
									        success: function (label, element) {
									            if (!$(element).next("span")[0]) {
									                $("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
									            }
									        },
									        highlight: function (element, errorClass, validClass) {
									            $(element).parents(".form-group").addClass("has-error").removeClass("has-success");
									            $(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
									        },
									        unhighlight: function (element, errorClass, validClass) {
									            $(element).parents(".form-group").addClass("has-success").removeClass("has-error");
									            $(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
									        }
									    });

									});
