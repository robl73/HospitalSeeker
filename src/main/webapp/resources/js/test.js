$(document).ready(function() {

	$(function() {

		$('#datetimepicker').datepicker({
			todayHighlight:true,
			startDate : new Date(),
			orientation : 'auto bottom',
			altField : '#thealtdate',
			altFormat : 'yy-mm-dd',
			autoclose : true,
			todayHighlight : true,
			format: 'yyyy-mm-dd',
			todayHighlight : true

		});

	});
	
});