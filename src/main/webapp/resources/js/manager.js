var scheduler;
var idDoctorInfo;
var workScheduler = null;
var isSaved = false;
var startHours = 0;
var endHours = 23;
var timeout;

$(document).ready(function() {
    blockDays();
    init();
    $('#workWeekSize').change(workWeekSizeChanged);
    $('#workDayBeginAt').change(workDayBeginChanged);
    $('#workDayEndAt').change(workDayEndChanged);
    $('#saveData').click(save);
});

function init() {
    scheduler.config.limit_time_select = true;
    scheduler.config.lightbox.sections = [
        {name:"description", height: 130, map_to: "text", type: "textarea" , focus: true},
        {name:"recurring", height: 300, type: "recurring", map_to: "rec_type", button: "recurring"}
    ];
    console.log(scheduler.config.lightbox.sections);
    scheduler.config.time_step = 60;
    scheduler.config.collision_limit = 1;
    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.init('scheduler_here', new Date(), "week");
    idDoctorInfo = document.getElementById("did").textContent;
    $.ajax({
        type: "GET",
        async: false,
        url: "getWorkScheduler?id=" + idDoctorInfo,
        dataType: "json",
        contentType: "application/json",
        mimeType: "application/json",
        success: function (data) {
            if (data != null) {
                appSize = data.app_size;
                weekSize = data.week_size;
                dayStart = data.day_start;
                dayEnd = data.day_end;
                workScheduler = data;
            }
        },
        error: function () {
        }
    });

    var appSize;
    var dayStart;
    var dayEnd;
    var weekSize;

    if (! (jQuery.isEmptyObject(workScheduler))) {
        $("#workWeekSize option").each(function (item, i) {
            if (weekSize == i.value) {
                $("#workWeekSize").val(i.value);
            }
        });

        $("#workDayBeginAt option").each(function (item, i) {
            if (dayStart == i.value) {
                $("#workDayBeginAt").val(i.value);
            }
        });

        $("#workDayEndAt option").each(function (item, i) {
            if (dayEnd == i.value) {
                $("#workDayEndAt").val(i.value);
            }
        });

        $("#appointmentTime option").each(function (item, i) {
            if (appSize == i.value.substring(0, 2)) {
                $("#appointmentTime").val(i.value);
            }
        });

        workDayBeginChanged();
        workDayEndChanged();
        isSaved = true;
        scheduler.parse(workScheduler.events, "json");
    } else {
        $('#workDayBeginAt option:first').prop('selected', true);
        $('#workDayEndAt option:last').prop('selected', true);
    }

    selectWeekSize();

    scheduler.attachEvent("onEventCollision", function() {
        return true;
    });

    scheduler.attachEvent("onBeforeEventChanged", function() {
        isSaved = false;
        return true;
    });

    scheduler.attachEvent("onEventDeleted", function() {
        isSaved = false;
        return true;
    });
}

function showInfoSuccess() {
    showMessage('.showInfoSuccess');
}

function showInfoError() {
    showMessage('.showInfoError');
}

function showMessage(selec) {
    if ($(selec).is(':visible')) {
        $(selec).slideUp(0);
        clearTimeout(timeout);
    }
    $(selec).slideDown(300, function() {
        timeout = setTimeout(function() {
            $(selec).slideUp(300)
        }, 4000);
    });
}

function blockDays() {
    var block_id = null;
    scheduler.attachEvent("onBeforeViewChange", function(old_mode, old_date, mode, date) {
        if(block_id) scheduler.deleteMarkedTimespan(block_id);
        var from = scheduler.date[mode + "_start"](new Date(date));
        var to = new Date(Math.min(+new Date(), +scheduler.date.add(from, 1, mode)));
        block_id = scheduler.addMarkedTimespan({
            start_date: from,
            end_date: to,
            css: "gray_section",
            type: "discount"
        });
        return true;
    });
    scheduler.config.limit_start = new Date();
    scheduler.config.limit_end = new Date(9999, 1, 1);
    setInterval(function() {
        scheduler.config.limit_start = new Date();
    }, 1000 * 60);
}

function workWeekSizeChanged() {
    selectWeekSize();
    isSaved = false;
}

function selectWeekSize() {
    var selected = $("#workWeekSize").val();
    var ignore = [];
    if (selected == 5) {
        ignore.push(0);
        ignore.push(6);
    } else if(selected == 6) {
        ignore.push(0);
    }
    ignoreDays(ignore);
}

function save() {
    scheduler.updateView();
    if (isSaved) {
        showInfoSuccess();
        return;
    }
    isSaved = true;
    // $.ajaxSetup({
    //     headers: {
    //         'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
    //     }
    // });
    var events = JSON.parse(scheduler.toJSON());
    var workSchedulerConfig = new Object();
    workSchedulerConfig.week_size = $("#workWeekSize").val();
    workSchedulerConfig.day_start = $("#workDayBeginAt").val();
    workSchedulerConfig.day_end = $("#workDayEndAt").val();
    workSchedulerConfig.app_size = $("#appointmentTime").val();
    workSchedulerConfig.events = events;
    $.ajax({
        type: "POST",
        async: true,
        url: "supplyWorkScheduler?doctorId=" + idDoctorInfo,
        data: JSON.stringify(workSchedulerConfig),
        dataType: "text",
        contentType: 'application/json',
        success: function(data) {
            showInfoSuccess();
        },
        error: function (e) {
            isSaved = false;
        }
    });
}

$(window).bind('beforeunload', function(e) {
    if (isSaved) {
        e = null;
    } else {
        return true;
    }
});

function ignoreDays(days) {
    var ignore;
    if (days.length > 0) {
        ignore = function (date) {
            for (var i = 0; i < days.length; ++i) {
                if (date.getDay() == days[i]) {
                    return true;
                }
            }
        };
    } else {
        ignore = false;
    }
    scheduler.ignore_month = ignore;
    scheduler.ignore_week = ignore;
    scheduler.setCurrentView();
}

function workDayBeginChanged() {
    var start = parseInt($('#workDayBeginAt').val());
    startHours = start;
    var end = parseInt($('#workDayEndAt option:last').val());
    showRange('workDayEndAt', start + 1, end);
    scheduler.config.first_hour = start;
    scheduler.setCurrentView();
    isSaved = false;
}

function workDayEndChanged() {
    var start = parseInt($('#workDayBeginAt option:first').val());
    var end = parseInt($('#workDayEndAt').val());
    endHours = end;
    showRange('workDayBeginAt', start, end);
    scheduler.config.last_hour = end;
    scheduler.setCurrentView();
    isSaved = false;
}

function showRange(elementName, begin, end) {
    hideOptionElements(elementName, 0, begin);
    showOptionElements(elementName, begin, end);
    hideOptionElements(elementName, end, 24);
}

function hideOptionElements(elementName, begin, end) {
    for (var i = begin; i < end; ++i) {
        $('#' + elementName + ' option[value=' + i + ']').hide();
    }
}

function showOptionElements(elementName, begin, end) {
    for (var i = begin; i <= end; ++i) {
        $('#' + elementName + ' option[value=' + i + ']').show();
    }
}
