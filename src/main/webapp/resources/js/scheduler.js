/**
 * Created by george on 10/3/16.
 */

function blockPast(blockType) {
    var block_id = null;
    scheduler.attachEvent("onBeforeViewChange", function(old_mode, old_date, mode, date) {
        if(block_id) scheduler.deleteMarkedTimespan(block_id);
        var from = scheduler.date[mode + "_start"](new Date(date));
        var to = new Date(Math.min(+new Date(), + scheduler.date.add(from, 1, mode)));
        block_id = scheduler.addMarkedTimespan({
            start_date: from,
            end_date: to,
            css: "gray_section",
            type: blockType
        });
        return true;
    });
    scheduler.config.limit_start = new Date();
    scheduler.config.limit_end = new Date(9999, 1, 1);
    setInterval(function() {
        scheduler.config.limit_start = new Date();
    }, 1000 * 60);
}

function getLastDate(date, mode) {
    if (mode == 'week') {
        return new Date(date.getFullYear(), date.getMonth(), date.getDate() + 7);
    }
    if (mode == 'month') {
        return new Date(date.getFullYear(), date.getMonth() + 1, 8);
    }
    return new Date(date.getFullYear(), date.getMonth(), date.getDate() + 1, 0);
}

function getFirstDate(date, mode) {
    if (mode == 'day') {
        return new Date(date.getFullYear(), date.getMonth(), date.getDate() - 1);
    }
    return new Date(date.getFullYear(), date.getMonth(), -7)
}

function blockDateFrom(from) {
    var block_id = null;

    scheduler.attachEvent("onBeforeViewChange", function(old_mode, old_date, mode, date) {
        if(block_id) scheduler.deleteMarkedTimespan(block_id);
        block_id = scheduler.addMarkedTimespan({
            start_date: from,
            end_date: getLastDate(date, mode),
            zones: "fullday",
            type:  "dhx_time_block"
        });
        return true;
    });
}

function blockDateTo(to) {
    var block_id = null;

    scheduler.attachEvent("onBeforeViewChange", function(old_mode, old_date, mode, date) {
        if(block_id) scheduler.deleteMarkedTimespan(block_id);
        block_id = scheduler.addMarkedTimespan({
            start_date: getFirstDate(date, mode),
            end_date: to,
            zones: "fullday",
            type:  "dhx_time_block"
        });
        return true;
    });
}

function selectedWeekSize(selected) {
    var ignore = [];
    if (selected == 5) {
        ignore.push(0);
        ignore.push(6);
    } else if(selected == 6) {
        ignore.push(0);
    }
    return ignore;
}

function ignoreDays(days) {
    var ignore;
    if (days.length > 0) {
        return function (date) {
            for (var i = 0; i < days.length; ++i) {
                if (date.getDay() == days[i]) {
                    return true;
                }
            }
        };
    } else {
        return false;
    }
}

function daydiff(first, second) {
    return Math.round((second-first)/(1000*60*60*24));
}