/**
 * Created by george on 10/16/16.
 */

(function () {
    console.log('anonymus function');
    $('document').ready(function () {
        console.log('ready');

        $('#sendFeedback').click(function () {
            var message = $('#input-feedback').val();
            var doctorId = $('#doctorId').val();
            var sendData = {
                'message': message,
                'doctorId': doctorId
            };
            // console.log('id is ' + doctorId);
            var promise = $.post('/doctor/feedback', sendData);
            promise.success(function (data) {
                console.log('success ' + data);
                location.reload();
            });
            promise.error(function (e) {
                console.log('error ' + e);
            });
        });

    });
})();