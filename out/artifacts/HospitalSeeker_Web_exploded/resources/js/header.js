$(function() {

    $('a[href="#toggle-search"], .navbar-bootsnipp .bootsnipp-search .input-group-btn > .btn[type="reset"]').on('click', function(event) {
		event.preventDefault();
		$('.navbar-bootsnipp .bootsnipp-search .input-group > input').val('');
		$('.navbar-bootsnipp .bootsnipp-search').toggleClass('open');
		$('a[href="#toggle-search"]').closest('li').toggleClass('active');

		if ($('.navbar-bootsnipp .bootsnipp-search').hasClass('open')) {
			/* I think .focus dosen't like css animations, set timeout to make sure input gets focus */
			setTimeout(function() {
				$('.navbar-bootsnipp .bootsnipp-search .form-control').focus();
			}, 100);
		}
	});

	$(document).on('keyup', function(event) {
		if (event.which == 27 && $('.navbar-bootsnipp .bootsnipp-search').hasClass('open')) {
			$('a[href="#toggle-search"]').trigger('click');
		}

		if($('#search-hospital-form').valid()){
			$('#select_hospital_search_button').removeAttr('disabled');
		}else {$('#select_hospital_search_button').prop('disabled',true);}

		if($('#search-doctor-form').valid()){
			$('#select_doctor_search_button').removeAttr('disabled');
		}else {$('#select_doctor_search_button').prop('disabled',true);}

	});

});

$(document).ready(function(e){
	$('.search-panel .dropdown-menu').find('a').click(function(e) {
		e.preventDefault();
		var param = $(this).attr("href").replace("#","");
		var concept = $(this).text();
		$('.search-panel span#search_concept').text(concept);
		$('.input-group #search_param').val(param);

	});
	$("#select_hospital_search").click(function () {
		$('#select_doctor_search_button').prop('disabled',true);
		$('#select_hospital_search_button').removeAttr('disabled');
	});
	$("#select_doctor_search").click(function () {
		$('#select_hospital_search_button').prop('disabled',true);
		$('#select_doctor_search_button').removeAttr('disabled');
	});
	$('#search-hospital-form').validate({
		rules : {
			'q' : {
				minlength : 3,
				maxlength : 30
			}
		}
	});
	$('#search-doctor-form').validate({
		rules : {
			'd' : {
				minlength : 3,
				maxlength : 30
			}
		}
	});
});

