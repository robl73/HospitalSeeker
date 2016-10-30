$(document).ready(function() {

	$(function() {

		$('#datetimepicker').datepicker({
			todayHighlight:true,
			startDate : new Date(),
			orientation : 'auto bottom',
			dateFormat : 'dd/mm/yy',
			altField : '#thealtdate',
			altFormat : 'yy-mm-dd',
			autoclose : true,
			todayHighlight : true,
			format : 'dd/mm/yyyy',
			todayHighlight : true

		});

	});
	
});