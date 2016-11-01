/**
 * Created by george on 10/27/16.
 */

(function() {
    var feedbacks;
    var statuses = ['NEW'];
    var firstPage = 1;
    var count = 10;
    var allFeedbacks = [];
    var isMoreFeedbacks = true;

    $('document').ready(function () {
        feedbacks = $('.feedbacks');
        allFeedbacks = JSON.parse(firstPart);
        if (allFeedbacks.length == 0) {
            $('#noFeedbacks').show();
        } else {
            showData(allFeedbacks);
        }
        $('.content').css('padding-top', $('.navbar').height() + 10);
        $(document).on('click', '#searchButton', search);
        $(document).on('change', 'input[name=status]', changedStatuses);
        $(document).on('mousedown', '.approve', approveFeedback);
        $(document).on('click', '.ignore', ignoreFeedback);
        pagination();
        $(document).ajaxStart(function () {
            $('#loading-image').show();
        });
        $(document).ajaxStop(function () {
            $('#loading-image').hide();
        });
        $('#loading-image').hide();
    });

    function changedStatuses() {
        var checkedStatusess = [];
        $('input[name=status]:checked').each(function () {
            checkedStatusess.push($(this).val());
        });
        if ($(statuses).not(checkedStatusess).length === 0 && $(checkedStatusess).not(statuses).length === 0) {
            $('#searchButton').removeClass('btn-danger');
            $('#searchButton').addClass('btn-default');
        } else {
            $('#searchButton').removeClass('btn-default');
            $('#searchButton').addClass('btn-danger');
        }
        $('#searchButton').prop('disabled', ($('input[name=status]:checked').length == 0));
    }

    function search() {
        $('#searchButton').removeClass('btn-danger');
        $('#searchButton').addClass('btn-default');
        isMoreFeedbacks = true;
        allFeedbacks = [];
        statuses = [];
        $('input[name=status]:checked').each(function () {
            statuses.push($(this).val());
        });
        firstPage = 1;
        var result = load(statuses, count, firstPage);
        feedbacks.empty();
        result.success(function(data) {
            showData(data);
        });
    }

    function load(statuses, count, firstPage) {
        var sendData = {
            countOfPage: count,
            firstPage: firstPage,
            statuses: statuses
        };
        return $.get('../showMoreFeedbacks', sendData);
    }

    function addNewFeedbacks(data) {
        data.forEach(function (item) {
            feedbacks.append(buildPanel(item));
        });
    }

    function showData(data) {
        if (data.length == 0) {
            $('#noFeedbacks').show();
            return;
        }
        $('#noFeedbacks').hide();
        addNewFeedbacks(data);
    }

    function pagination() {
        var sendData = {
            countOfPage: count
        };

        $(window).scroll(function() {
            if($(window).scrollTop() + $(window).height() == $(document).height() && isMoreFeedbacks) {
                firstPage += count;
                sendData.firstPage = firstPage;
                sendData.statuses = statuses;
                var promise = $.get('../showMoreFeedbacks', sendData);
                promise.success(function(data) {
                    if (data.length != 0) {
                        console.log(allFeedbacks);
                        data.forEach(function(item) {
                            allFeedbacks.push(item);
                        });
                        console.log(allFeedbacks);
                        addNewFeedbacks(data);
                        return;
                    }
                    isMoreFeedbacks = false;
                });
            }
        });
    }

    function approveFeedback() {
        if (!$(this).hasClass('active')) {
            var panel = $(this).closest('.panel');
            changeStatus(panel, 'OK');
        }
    }

    function ignoreFeedback() {
        var panel = $(this).closest('.panel');
        changeStatus(panel, 'BAD');
    }

    function changeStatus(panel, status) {
        var feedbackId = panel.attr('id');
        var sendData = {
            'feedbackId': feedbackId,
            'status': status
        };
        var promise = $.post('../changeFeedbackStatus', sendData);
        promise.success(function () {
            panel.removeClass('panel-success');
            panel.removeClass('panel-danger');
            if (status == 'OK') {
                panel.addClass('panel-success');
            } else {
                panel.addClass('panel-danger');
            }
            if ($('input[name=autoremove]:checked').length > 0 && $.inArray(status, statuses) == -1) {
                panel.fadeOut('fast', 'swing');
            }
        });
        promise.error();
    }

    function buildPanel(feedback) {
        var panel = $('<div>', {class: 'panel', id: feedback.id});
        var heading = $('<div>', {class: 'panel-heading clearfix'});
        var producer = $('<span>', {class: 'pull-left'});
        var date = $('<span>');
        var clock = $('<div>', {class: 'fa fa-clock-o pull-right'});
        var body = $('<div>', {class: 'panel-body'});
        var footer = $('<div>', {class: 'panel-footer clearfix'});
        var buttonGroup = $('<div>', {class: 'btn-group', 'data-toggle': 'buttons'});
        var labelOk = $('<label>', {class: 'btn btn-default approve'});
        var labelBlock = $('<label>', {class: 'btn btn-default ignore'});
        var ok = $('<input>', {type: 'radio', name: 'decide', value: 'OK'});
        var block = $('<input>', {type: 'radio', name: 'decide', value: 'BAD'});
        var consumer = $('<span>', {class: 'pull-right'});

        if (feedback.status == 'NEW') {
            panel.addClass('panel-primary');
        } else if (feedback.status == 'OK') {
            labelOk.addClass('active');
            panel.addClass('panel-success');
        } else {
            labelBlock.addClass('active');
            panel.addClass('panel-danger');
        }

        labelOk.append(ok);
        labelOk.append('OK');
        buttonGroup.append(labelOk);
        labelBlock.append(block);
        labelBlock.append('BLOCK');
        buttonGroup.append(labelBlock);
        consumer.append(feedback.consumerFirstName);
        consumer.append(' ');
        consumer.append(feedback.consumerLastName);
        date.append(feedback.feedbackDTOForAll.date);
        clock.append('\xa0');
        clock.append(date);
        footer.append(buttonGroup);
        footer.append(consumer);
        body.append(feedback.feedbackDTOForAll.message);
        producer.append(feedback.feedbackDTOForAll.producerFirstName);
        producer.append(' ');
        producer.append(feedback.feedbackDTOForAll.producerLastName);
        heading.append(producer);
        heading.append(clock);
        panel.append(heading);
        panel.append(body);
        panel.append(footer);
        return panel;
    }

})();