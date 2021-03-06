var geocoder = new google.maps.Geocoder();
var marker = new google.maps.Marker();

function initialize() {

	mapInit('googleMap');

	google.maps.event.addListener(map, 'click', function(event) {
		marker.setMap(null);
		placeMarker(event.latLng);
	});

	window.setTimeout(function() {
		$('.removable-i').fadeTo(1500, 0).slideUp(500, function() {
			$(this).remove();
		});
	}, 5000);

	window.setTimeout(function() {
		resetAddress();
	}, 500);

	disabledAddressFields(true);

	$('#addressGeo').keyup(function() {
		if ($(this).val().length != 0) {
			disabledAddressFields(false);
		} else {
			var addressAll = document.getElementById('addressGeo').value;
		}
	});

	$.validator.addMethod(

	'regexpLetters', function(value, element, regexpLetters) {
		var re = new RegExp(regexpLetters);
		return this.optional(element) || re.test(value);
	});

	$.validator.addMethod(

	'requiredGeo', function(value, element, param) {

		// Check if dependency is met
		if (!this.depend(param, element)) {
			return "dependency-mismatch";
		}
		if (element.nodeName.toLowerCase() === "select") {

			// Could be an array for select-multiple or a string, both are fine
			// this way
			var val = $(element).val();
			return val && val.length > 0;
		}
		if (this.checkable(element)) {
			return this.getLength(value, element) > 0;
		}
		return value.length > 0;
	});

	$('#form-hospital').validate({
		rules : {
			'addressGeo' : {
				requiredGeo : true,
				maxlength : 150
			},
			'address.country' : {
				required : true,
				minlength : 2,
				maxlength : 30,
				regexpLetters : /^[a-zA-Zа-яА-ЯёЁіІїЇєЄЫыЪъ'\-\s]+$/
			},
			'address.city' : {
				required : true,
				maxlength : 30,
				regexpLetters : /^[a-zA-Zа-яА-ЯёЁіІїЇєЄЫыЪъ'\-\s]+$/
			},
			'address.street' : {
				required : true,
				maxlength : 30
			},
			'address.building' : {
				required : true,
				maxlength : 10
			},
			'name' : {
				required : true,
				minlength : 5,
				maxlength : 70
			},
			'description' : {
				maxlength : 150
			}
		}
	});

	if (document.getElementById('imagePath').value) {
		$('#image-uploaded').attr(
				'src',
				jsContextPath + 'images/hospital/'
						+ document.getElementById('imagePath').value);
	}
}

function disabledAddressFields(boolean) {
	$('#button-fill').attr('disabled', boolean);
	$('#address\\.country').attr('disabled', boolean);
	$('#address\\.city').attr('disabled', boolean);
	$('#address\\.street').attr('disabled', boolean);
	$('#address\\.building').attr('disabled', boolean);
	$('#name').attr('disabled', boolean);
	$('#description').attr('disabled', boolean);
};

function resetAddress() {
	marker.setMap(null);

	var validator = $("#form-hospital").validate();
	validator.resetForm();

	if (document.getElementById('id').value != '') {
		placeMarker(new google.maps.LatLng(parseFloat(document
				.getElementById('latitude').value), parseFloat(document
				.getElementById('longitude').value)));
		disabledAddressFields(false);
	} else {
		disabledAddressFields(true);
	}
}

google.maps.event.addDomListener(window, 'load', initialize);

function geocodeAddress(geocoder, resultsMap) {
	var address = document.getElementById('addressGeo').value;
	geocoder
			.geocode(
					{
						'address' : address
					},
					function(results, status) {
						if (status === google.maps.GeocoderStatus.OK) {
							resultsMap.setCenter(results[0].geometry.location);
							marker.setMap(null);
							marker = new google.maps.Marker({
								map : resultsMap,
								position : results[0].geometry.location
							});
							document.getElementById('latitude').value = results[0].geometry.location
									.lat();
							document.getElementById('longitude').value = results[0].geometry.location
									.lng();
							$('#div-latlng :input').valid();
						} else {
							document.getElementById('geo-error').innerHTML = 'Error: '
									+ status;
							window.setTimeout(function() {
								$('.removable-geo').fadeTo(1500, 0).slideUp(
										500, function() {
											$(this).remove();
										});
							}, 5000);
						}
					});
}

function placeMarker(location) {
	google.maps.event.trigger(map, 'resize');
	marker.setMap(null);
	marker = new google.maps.Marker({
		position : location,
		map : map,
	});
	map.setCenter(location);
	geocoder
			.geocode(
					{
						'location' : location
					},
					function(results, status) {
						document.getElementById('addressGeo').value = results[0].formatted_address;
						document.getElementById('latitude').value = results[0].geometry.location
								.lat();
						document.getElementById('longitude').value = results[0].geometry.location
								.lng();
						disabledAddressFields(false);
						$('#div-latlng :input').valid();
					});
}

function check() {
	geocodeAddress(geocoder, map);
}

function fill() {
	var fullAddress = document.getElementById('addressGeo').value.split(',');
	document.getElementById('address.street').value = fullAddress[0].trim();
	document.getElementById('address.building').value = fullAddress[1].trim();
	document.getElementById('address.city').value = fullAddress[2].trim();
	document.getElementById('address.country').value = fullAddress[4].trim();
	$('#div-countrycity :input').valid();
}
