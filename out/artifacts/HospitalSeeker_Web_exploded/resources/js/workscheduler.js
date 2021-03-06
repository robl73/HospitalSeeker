$(document).ready(function () {
    var today = new Date();
    today.setHours(0);
    blockDateTo(today);
    var promise = getData('getWorkSchedulerByPrincipal');
    promise.success(function (data) {
        if (data != null && data.events != null) {
            init(data);
        } else {
            $('#mySchedulerErrorModal').modal('show');
        }
    });
    promise.error(function () {
        $('#mySchedulerErrorModal').modal('show');
    });
});

function init(data) {
    var firstDate = new Date();
    var principal = $('#principal').text();
    var schedulerConfig = {};
    scheduler.config.drag_create = false;
    schedulerConfig.appSize = data.app_size;
    schedulerConfig.weekSize = data.week_size;
    schedulerConfig.dayStart = data.day_start;
    schedulerConfig.dayEnd = data.day_end;
    var today = new Date();
    var zones;
    var lastDate = today;
    firstDate = new Date(data.events[0].start_date);
    data.events.forEach(function (item) {
        var workDay = item.start_date.substring(0, 10);
        var hourLast = item.end_date.substring(11, 13);
        var hourOne = item.start_date.substring(11, 13);
        var current = new Date(workDay);
        current.setHours(parseInt(hourLast));
        if (current > today) {
            if (firstDate > current) firstDate = current;
            if (item['event_length'] != null) {
                var d = new Date(item.end_date);
                if (lastDate <  d) {
                    lastDate = d;
                }
                var count = item['event_length'];
                zones = [0, hourOne * 60, hourOne * 60 + count / 60, 24 * 60];
                for (var i = 0; i <= daydiff(new Date(item.start_date), d); ++i) {
                    scheduler.addMarkedTimespan({
                        days: current,
                        zones: zones,
                        css: "green_section"
                    });
                    current = new Date(current.getFullYear(), current.getMonth(), current.getDate() + 1);
                }
            } else {
                zones = [0, hourOne * 60, hourLast * 60, 24 * 60];
                scheduler.addMarkedTimespan({
                    days: new Date(workDay),
                    zones: zones,
                    css: "green_section"
                });
            }
            if (current > lastDate) {
                lastDate = current;
            }
        }
    });
    blockDateTo(firstDate);
    blockDateFrom(new Date(lastDate.getFullYear(), lastDate.getMonth(), lastDate.getDate() + 1));
    var step = schedulerConfig.appSize;
    scheduler.config.hour_size_px = (60 / step) * 44;
    scheduler.config.time_step = step;
    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.config.details_on_dblclick = true;
    scheduler.config.details_on_create = true;
    scheduler.ignore_month = ignoreDays(selectedWeekSize(schedulerConfig.weekSize));
    scheduler.ignore_week = scheduler.ignore_month;
    scheduler.attachEvent("onBeforeDrag", function() {return false;});
    scheduler.attachEvent("onClick", function() {return false;});
    scheduler.config.details_on_dblclick = true;
    scheduler.config.dblclick_create = false;
    scheduler.config.first_hour = schedulerConfig.dayStart;
    scheduler.config.last_hour = schedulerConfig.dayEnd;
    scheduler.config.limit_time_select = true;
    scheduler.init('scheduler_here', null, "week");
    scheduler.load('getAppointmentsByDoctor?doctor='+principal,'json');
    var dp = new dataProcessor("supplyAppointment?id=" + 0 + "&principal="+principal);
    dp.init(scheduler);
    scheduler.updateView(new Date());
}

var html = function (id) {
    return document.getElementById(id);
};

var ev;

scheduler.showLightbox = function (id) {
    var tex_local_from = getMessage('workscheduler.modal.appointment.time.from');
    var tex_local_to = getMessage('workscheduler.modal.appointment.time.to');
    $('#myModal').modal('show');
    ev = scheduler.getEvent(id);
    scheduler.startLightbox(id, html("myModal"));
    $('#date').text(new Date(ev.start_date).toLocaleDateString() + ' ' + tex_local_from + ' '+
        new Date(ev.start_date).toLocaleTimeString().replace(':00', '') + ' ' + tex_local_to + ' ' +
        new Date(ev.end_date).toLocaleTimeString().replace(':00', ''));
    html("description").value = ev.text;
    var nameAndDescription = ev.text.split("-");
    $('#patientName').text(nameAndDescription[0]);
    $('#theReasonForVisit').text(nameAndDescription[1]);
    $('#cancelAppointmentHeader').text('Cancel appointment of ' + ' ' + nameAndDescription[0]);
    $('#cardButton').val(ev.id)
};

function onCancelAppointment() {
    $('#cancelAppointmentModal').modal('hide');
}

function cancelAppointment() {
    changeModalCont()
}

function changeModalCont() {
    setTimeout(closeFirst, 50);
    setTimeout(openSecond, 600);
}

function closeFirst() {
    $('#modalBodyFirst').slideToggle('slow');
    $('#modalHeaderWithDate').slideToggle('slow');
    $('#modalHeaderForCancel').slideToggle('slow');
}

function openSecond() {
    $('#modalBodySecond').slideToggle('slow');
    $('#okBtn').slideToggle(10);
    $('#batOne').slideToggle('slow');
    $('#canBtn').slideToggle('slow');
}

function startModal() {
    setTimeout(changeModalContentFirstStep, 500);
    setTimeout(changeModalContentSecondStep, 4000);
}

function changeModalContentFirstStep() {
    $('#modalBodySecond').attr('hidden', 'hidden');
    $('#modalBodyFirst').slideToggle(500);
    $('#modal-header').slideToggle(500);
    $('#modal-footer').slideToggle(500);
    $('#modalBodySuccess').slideToggle(500);

}

function changeModalContentSecondStep() {
    $('#myModal').modal('hide');
    changeModalContentFirstStep();
}

$(document).keyup(function (e) {
    if (e.keyCode == 27) { // escape key maps to keycode `27`
        close_form();
    }
});

function save_form() {
    blockAppointmensAdd();
    ev = scheduler.getEvent(scheduler.getState().lightbox_id);
    scheduler.endLightbox(true, html("my_form"));
}
function close_form() {
    if ($("#modalHeaderForCancel").is(':visible')) {
        cancelAppointment()
    }
    scheduler.endLightbox(false, html("my_form"));
}

function delete_event() {
    var event_id = scheduler.getState().lightbox_id;
    scheduler.endLightbox(false, html("cancelAppointmentModal"));
    scheduler.deleteEvent(event_id);
}
function blockAppointmensAdd() {
    scheduler.config.readonly = true;
}

function dismissMyModal(event) {
    if ($(event.target).is('#myModal')) {
        close_form();
    }
}

function onCancelAppointment() {
    var principal = $('#principal').text();
    var succesMassegeStart = getMessage('workscheduler.modal.appointment.cancel.success.begin');
    var succesMassegeEnd = getMessage('workscheduler.modal.appointment.cancel.success.end');
    cancelAppointment();
    startModal();
    var reason = $('#cancelReason').val();
    sendMassage(reason, ev.id, principal);
    setTimeout(delete_event, 3000);
    $('#cancelMassageText').text(succesMassegeStart +' ' + ev.text.split('-')[0] + ' ' + succesMassegeEnd);
}

function getCard(appointmnetId) {
    window.location.href = "appointmentId?appointmentId=" + appointmnetId;
}
