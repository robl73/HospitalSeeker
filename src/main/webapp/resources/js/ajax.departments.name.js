$(document).ready(function() {
    $('#hospital').change(function(event) {
        var hospital = $("select#hospital").val();
        $.get('/departments', {
            hospitalId : hospital
        }, function(response) {

            var select = $('#departmen');
            select.find('option').remove();
            $.each(response, function(index, value) {
                $('<option>').val(value.id).text(value.name).appendTo(select);
            });
        });
    });
});