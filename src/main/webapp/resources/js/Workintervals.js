var nextAvailableTime;
var isPresentUserDetails;
var principal;

$(document).ready(function () {
    principal = $('#principal').text();
    var did = document.getElementById("1").textContent;
    var begin;
    var end;
    var events;
    var dayOfAppointment;
    var schedulerConfig = {};
    var lastDate = new Date();
    var firstDate = new Date();
    var deleteMarkedTimespan = [];
    var addMarkedTimespan = [];
    $.ajax({
        type: "GET",
        async: false,
        url: "getWorkScheduler?id=" + did,
        dataType: "json",
        contentType: "application/json",
        mimeType: "application/json",
        success: function (data) {
            if (data != null) {
                schedulerConfig.appSize = data.app_size;
                schedulerConfig.weekSize = data.week_size;
                schedulerConfig.dayStart = data.day_start;
                schedulerConfig.dayEnd = data.day_end;
                events = data.events;
            }
            var today = new Date();
            today.setHours(0);
            today.setMinutes(0);
            today.setSeconds(0);
            firstDate = new Date(data.events[0].start_date);
            events.forEach(function (item) {
                var workDay = item.start_date.substring(0, 10);
                var hourOne = item.start_date.substring(11, 13);
                var hourLast = item.end_date.substring(11, 13);
                var zones;
                var current = new Date(workDay);
                var temp = new Date(workDay);
                if (hourLast != '00') {
                    temp.setHours(parseInt(hourLast));
                } else {
                    temp.setHours(23);
                    temp.setMinutes(59);
                }
                if (temp > today) {
                    if (firstDate > temp) firstDate = temp;
                    if (item['event_length'] != null && item.rec_type != 'none') {
                        var d = new Date(item.end_date);
                        if (lastDate <  d) {
                            lastDate = d;
                        }
                        var count = item['event_length'];
                        zones = [hourOne * 60, hourOne * 60 + count / 60];
                        for (var i = 0; i <= daydiff(new Date(item.start_date), d); ++i) {
                            deleteMarkedTimespan.push({
                                days: current,
                                zones: zones,
                                css: "green_section"
                            });
                            current = new Date(current.getFullYear(), current.getMonth(), current.getDate() + 1);
                        }
                    } else {
                        zones = [hourOne * 60, hourLast * 60];
                        var tem = (item.event_pid != null) ? addMarkedTimespan : deleteMarkedTimespan;
                        console.log(item);
                        tem.push({
                            days: new Date(workDay),
                            zones: zones,
                            css: "green_section"
                        });
                    }
                    if (temp > lastDate) {
                        lastDate = temp;
                    }
                }
            });
        },
        error: function () {
            $('#mySchedulerErrorModal').modal('show');
        }
    });
    if (events.length > 0) {
        blockDateTo(firstDate);
        blockDateFrom(lastDate);
        firstDate.setHours(0);
        scheduler.addMarkedTimespan({
            start_date: firstDate,
            end_date: lastDate,
            zones: 'fullday',
            css: "green_section"
        });
        deleteMarkedTimespan.forEach(function (e) {
            scheduler.deleteMarkedTimespan(e);
        });
        addMarkedTimespan.forEach(function (e) {
            scheduler.addMarkedTimespan(e);
        });
    } else {
        blockDateTo(firstDate);
        blockDateFrom(firstDate);
    }
    var day = new Date().getDate() - 1;
    var month = new Date().getMonth();
    var year = new Date().getFullYear();
    var step = schedulerConfig.appSize;
    scheduler.config.time_step = step;
    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.config.details_on_dblclick = true;
    scheduler.config.details_on_create = true;
    scheduler.ignore_month = ignoreDays(selectedWeekSize(schedulerConfig.weekSize));
    scheduler.ignore_week = scheduler.ignore_month;
    $.ajax({
        type: "GET",
        async: false,
        url: "getAppointments?id=" + did,
        dataType: "json",
        contentType: "application/json",
        mimeType: "application/json",
        success: function (data) {
            data.forEach(function (item, i) {
                dayOfAppointment = data[i].start_date.substring(0, 10);
                var start_date_hour = parseInt(data[i].start_date.substring(11, 13));
                var start_date_minutes = parseInt(data[i].start_date.substring(14, 16));
                var end_date_hour = parseInt(data[i].end_date.substring(11, 13));
                var end_date_minutes = parseInt(data[i].end_date.substring(14, 16));
                var minutes = (start_date_hour * 60 + start_date_minutes) / step;
                var minutes2 = (end_date_hour * 60 + end_date_minutes) / step;
                begin = minutes;
                end = minutes2;
                scheduler.blockTime(new Date(dayOfAppointment), [begin * step, end * step]);
            });
        }
    });
    scheduler.config.readonly = (!$('#patient').val());
    scheduler.config.first_hour = schedulerConfig.dayStart;
    scheduler.config.last_hour = schedulerConfig.dayEnd;
    scheduler.config.limit_start = new Date(year, month, day);
    scheduler.init('scheduler_here', null, "week");
    var dp = new dataProcessor("supplyAppointment?id=" + did + "&principal=" + principal);
    dp.init(scheduler);
    $('.content').css('padding-top', $('.navbar').height() + 10);
    var dhxData = $('.dhx_cal_data');
    dhxData.height(dhxData[0].scrollHeight);
    $('.dhx_cal_container').height(dhxData[0].scrollHeight + $('.dhx_multi_day').height() + $('.dhx_cal_header').height() + $('.dhx_cal_navline').height() + 15);
});

var html = function (id) {
    return document.getElementById(id);
};

scheduler.showLightbox = function (id) {
    var tex_local_from = getMessage('workscheduler.modal.appointment.time.from');
    var tex_local_to = getMessage('workscheduler.modal.appointment.time.to');
    $('#myModal').modal('show');

    var ev = scheduler.getEvent(id);
    ev.end_date = new Date(ev.start_date.getTime() + scheduler.config.time_step * 60000);
    scheduler.startLightbox(id, html("myModal"));
    var validationErrorMessage = getMessage('modal.workscheduler.validation.error');
    var validationTimeMassage = getMessage('modal.workscheduler.validation.freetime');
    var validationProfileMassage = getMessage('modal.workscheduler.validation.profile');

    validateAppointment(ev) ? showModal() : showErrorModal();
    function showErrorModal(){
        $('#validationErrorMassage').text(validationErrorMessage + ' ' + validationTimeMassage
            + ' ' + nextAvailableTime.substr(11));
        $('#myAppointmentValidationErrorModal').modal('show');
    }
    function showModal() {
        if (!isPresentUserDetails) {
            $('#validationErrorMassage').text(validationProfileMassage);
            $('#myAppointmentValidationErrorModal').modal('show');
            return;
        }
        $('#appointmentModal').modal('show');
        $('#appointmentModal').css(top,  0);
    }

    scheduler.startLightbox(id, html("appointmentModal"));
    $('#date').text(new Date(ev.start_date).toLocaleDateString() + ' ' + tex_local_from + ' '+
        new Date(ev.start_date).toLocaleTimeString().replace(':00', '') + ' ' + tex_local_to + ' ' +
        new Date(ev.end_date).toLocaleTimeString().replace(':00', ''));
    $('#doctorName').text($('#profDoctorsName').text());
    html("TheReasonForVisit").focus();
};

function save_form() {
    blockAppointmensAdd();
    var ev = scheduler.getEvent(scheduler.getState().lightbox_id);
    ev.text = html("TheReasonForVisit").value;
    scheduler.endLightbox(true, html("my_form"));
}

function close_form() {
    location.reload();
    scheduler.endLightbox(false, html("my_form"));
}

function closeErrorModal() {
    close_form();
    location.reload();
}

function delete_event() {
    // var event_id = scheduler.getState().lightbox_id;
    scheduler.endLightbox(false, html("my_form"));
    scheduler.deleteEvent(ev.id);
}

function blockAppointmensAdd() {
    location.reload();
    scheduler.config.readonly = true;
}

scheduler.attachEvent("onLimitViolation", function (id, obj) {
    dhtmlx.message('The appointment is not allowed at this time');
});

function dismissMyModal(event) {
    if ($(event.target).is('#appointmentModal')) {
        close_form();
    }
}

function startModal() {

    if ($('#TheReasonForVisit').val().length >= 150) {
        $('#lengthError').attr('style', 'display:block');
        return;
    }

    setTimeout(changeModalContentFirstStep, 500);
    setTimeout(changeModalContentSecondStep, 4000);
}

function changeModalContentFirstStep() {
    $('#modb').slideToggle(500);
    $('#modal-header').slideToggle(500);
    $('#modal-footer').slideToggle(500);
    $('#modbs').slideToggle(500);
}

function changeModalContentSecondStep() {
    save_form();
    $('#appointmentModal').modal('hide');
}

function closeModal() {
    changeModalContentSecondStep();
    $('#appointmentModal').modal('hide');
    $('#appointmentModal').modal('hide');
    $.modal.close();
}

$(document).keyup(function (e) {
    if (e.keyCode == 27) { // escape key maps to keycode `27`
        close_form();
    }
});

function validateAppointment(ev) {
    ev.principal = principal;
    var result = false;
    $.ajax({
        type: "GET",
        async: false,
        url: "validate?ev="+JSON.stringify(ev),
        dataType: "json",
        contentType: "application/json",
        mimeType: "application/json",
        success: function (data) {
            isPresentUserDetails = data.fullUserDetailPresent;
            nextAvailableTime = data.nextAvailableTime;
            result = data.result;
        },
        error: function (data) {
            // alert("error" + data)
        }
    });
    return result;
}

