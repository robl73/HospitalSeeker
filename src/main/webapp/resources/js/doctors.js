/**
 * Created by lesia on 12.09.2016.
 */
$(document).ready(function(){
    $("#pref-perpage").children().each(function(){

        if($(this).hasClass('selected')){
            $(this).attr('selected','selected');
        }
    });

    $("#search-doctor-form").validator();
});
