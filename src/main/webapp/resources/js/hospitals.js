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
function sendPageConfig(event){
		var itemPerPage = $('#pref-perpage option:selected').text();
		if(itemPerPage === "" || itemPerPage== null ){
			itemPerPage = "3";
		}	
		var currentSearchQuery = $("#pref-query").val();
		var type =$("#pref-type").val();
		var url = $('#path').val()+'/'+type+'?q='+currentSearchQuery+"$itemsPerPage="+itemPerPage;
		$.get(url,{
			'itemPerPage':itemPerPage,
                    },
			function(data){
				window.location.replace($('#path').val()+"/hospitals");
			});
		
	}