/**
 * 
 */
$(document).ready(function(){
	$("#pref-perpage").children().each(function(){

		if($(this).hasClass('selected')){
			$(this).attr('selected','selected');
		}
	});
	
	$("#search-hospital-form").validator();
});