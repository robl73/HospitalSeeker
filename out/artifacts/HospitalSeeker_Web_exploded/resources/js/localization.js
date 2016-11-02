/**
 * @author Oleksandr Mukonin
 * 
 */

var messages;

$.ajax({
	type: "POST",
	url: "getparams/messages",
	datatype: "json",
	contentType: 'application/json',
	mimeType: 'application/json',
	success: function(data) {
		messages = data;
	}
});

function getMessage(code) {
	return messages[code];
}

function getLocale() {
	var locale = document.cookie.replace(/(?:(?:^|.*;\s*)org\.springframework\.web\.servlet\.i18n\.CookieLocaleResolver\.LOCALE\s*\=\s*([^;]*).*$)|^.*$/, "$1");
	return locale ? locale : 'en';
}

$(window).load(function() {
	if (getLocale() == 'ua') {
		$.extend( $.validator.messages, {
			required: 'Це поле необхідно заповнити.',
			requiredGeo: 'Будь ласка, введіть адресу в поле або знайдіть це місце на карті.',
			remote: 'Будь ласка, введіть правильне значення.',
			email: 'Будь ласка, введіть коректну адресу електронної пошти.',
			url: 'Будь ласка, введіть коректний URL.',
			date: 'Будь ласка, введіть коректну дату.',
			dateISO: 'Будь ласка, введіть коректну дату у форматі ISO.',
			number: 'Будь ласка, введіть число.',
			digits: 'Вводите потрібно лише цифри.',
			regexpLetters: 'Може вміщувати тільки букви.',
			creditcard: 'Будь ласка, введіть правильний номер кредитної карти.',
			equalTo: 'Будь ласка, введіть таке ж значення ще раз.',
			extension: 'Будь ласка, виберіть файл з правильним розширенням.',
			maxlength: $.validator.format( 'Будь ласка, введіть не більше {0} символів.' ),
			minlength: $.validator.format( 'Будь ласка, введіть не менше {0} символів.' ),
			rangelength: $.validator.format( 'Будь ласка, введіть не менше {0} символів та не більше {1} символів.' ),
			range: $.validator.format( 'Будь ласка, введіть число від {0} до {1}.' ),
			max: $.validator.format( 'Будь ласка, введіть число, менше або рівно {0}.' ),
			min: $.validator.format( 'Будь ласка, введіть число, більше або рівно {0}.' ),
			address: 'Будь ласка, введіть адресу в полі або знайдіть цю локацію на карті.'
		} );
	}
	if (getLocale() == 'en') {
		$.extend( $.validator.messages, {
			required: 'This field is required.',
			requiredGeo: 'Please enter address in the field or find this location on the map.',
			//remote: '',
			//email: '',
			//url: '',
			//date: '',
			//dateISO: '',
			//number: '',
			//digits: '',
			regexpLetters: 'Contain only letters.',
		//	creditcard: '',
		//	equalTo: '',
		//	extension: '',
			maxlength: $.validator.format( 'Please enter not more than {0} symbols.' ),
			minlength: $.validator.format( 'Please enter at least {0} symbols' ),
			rangelength: $.validator.format( 'Please enter at least {0} symbols and not more than {1} symbols.' ),
			range: $.validator.format( 'Please enter a number from {0} to {1}' ),
		//	max: $.validator.format( '' ),
		//	min: $.validator.format( '' )
			address: 'Please enter address in the field or find this location on the map.',
		} );
	}
	
});