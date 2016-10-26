/**
 * Created by george on 10/16/16.
 */

(function () {

    var doctorId;
    var feedbacksCount = 6;
    var feedbacks;

    $('document').ready(function () {
        console.log(location.pathname);
        feedbacks = $('.feedbacks');
        var row = buildRow(firstPart);
        feedbacks.append(row);
        doctorId = $('#doctorId').val();
        $('#sendFeedback').click(sendFeedback);
        $('.fixed-panel-body').click(showModalFeedback);
        showMoreFeedbacks();
    });

    function sendFeedback() {
        var sendButton = $(this);
        sendButton.prop('disabled', true);
        var message = $('#input-feedback').val();
        var sendData = {
            'message': message,
            'doctorId': doctorId
        };
        var promise = $.post('/doctor/feedback', sendData);
        promise.success(function () {
            $('form[name="input-form"]').hide();
        });
        promise.error(function () {
            sendButton.prop('disabled', false);
        });
    }

    function showModalFeedback(event) {
        var panel = $(event.target).parent().closest('div');
        $('#modal-body-text').text($(event.target).text());
        $('#modal-title-text').text(panel.find('.panel-heading').text());
        $('#feedbackModal').modal('show');
    }

    function showMoreFeedbacks() {
        var rowNumber = 1;
        var formatter = $('#format').val();

        var sendData = {
            'doctorId': doctorId,
            'rowCount': feedbacksCount
        };

        $('#show-more').click(function () {
            console.log($('meta[name="csrf-token"]').attr('content'));
            ++rowNumber;
            sendData.rowNumber = rowNumber;
            var promise = $.get('../doctor/showMoreFeedbacks', sendData);
            promise.success(function (data) {
                var row = buildRow(data.feedbacks, formatter);
                feedbacks.append(row);
            });
        });
    }

    function buildRow(feedbacks, formatter) {
        var row = $('<div>', {'class': 'row'});
        feedbacks.forEach(function (item) {
            var col = $('<div>', {'class': 'col-sm-2 col-md-2 col-lg-2 col-centered'});
            col.append(buildPanel(item, formatter));
            row.append(col);
        });
        return row;
    }

    function buildPanel(feedback, dateFormat) {
        var panel = $('<div>', {'class': 'panel panel-default'});
        var heading = $('<div>', {'class': 'panel-heading'});
        var body = $('<div>', {'class': 'panel-body fixed-panel-body'});
        var footer = $('<div>', {'class': 'panel-footer'});
        var clock = $('<div>', {'class': 'fa fa-clock-o'});
        var date = $('<span>');

        console.log(feedback.date);

        // date.append($.format.date(feedback.date, dateFormat));
        date.append(feedback.date);
        clock.append(date);
        footer.append(clock);
        body.append(feedback.message);
        heading.append(feedback.producerFirstName);
        heading.append(' ');
        heading.append(feedback.producerLastName);
        panel.append(heading);
        panel.append(body);
        panel.append(footer);
        return panel;
    }

})();
