/**
 * Created by george on 10/16/16.
 */

(function () {

    var doctorId;
    var feedbacksCount = 6;
    var feedbacks;

    $('document').ready(function () {
        feedbacks = $('.feedbacks');
        var row = buildRow(JSON.parse(firstPart));
        feedbacks.append(row);
        doctorId = $('#doctorId').val();
        $('#sendFeedback').click(sendFeedback);
        // $('.fixed-panel-body').click(showModalFeedback);
        $(document).on('click', '.fixed-panel-body', showModalFeedback);
        showMoreFeedbacks();
        if ($('#isScheduler').val() === 'false') {
            $('#addAppointment').addClass('disabled');
        } else {
            $('#addAppointment').click(function () {
                location.href = '../doctor/' + doctorId + '/scheduler';
            });
        }
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
        console.log('click');
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

        $('#showMore').click(function () {
            ++rowNumber;
            sendData.rowNumber = rowNumber;
            var promise = $.get('../doctor/showMoreFeedbacks', sendData);
            promise.success(function (data) {
                var row = buildRow(data.feedbacks, formatter);
                feedbacks.append(row);
                console.log(data.feedbacks[0]);
                if (data.isMore == false) {
                    $('#showMore').hide();
                }
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
        var clock = $('<span>', {'class': 'fa fa-clock-o'});
        var date = $('<span>');

        date.append(feedback.date);
        footer.append(clock);
        footer.append(' ');
        footer.append(date);
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
