/**
 * Created by george on 10/16/16.
 */

(function () {
    $('document').ready(function () {
        $('#sendFeedback').click(function () {
            var message = $('#input-feedback').val();
            var doctorId = $('#doctorId').val();
            var sendData = {
                'message': message,
                'doctorId': doctorId
            };
            var promise = $.post('/doctor/feedback', sendData);
            promise.success(function (data) {
                location.reload();
            });
            promise.error(function (e) {
            });
        });

        $('.fixed-panel').click(function (event) {
            var panel = $(event.target).parent().closest('div');
            $('#modal-body-text').text($(event.target).text());
            $('#modal-title-text').text(panel.find('.panel-heading').text());
            $('#feedbackModal').modal('show');
        });
    });
})();