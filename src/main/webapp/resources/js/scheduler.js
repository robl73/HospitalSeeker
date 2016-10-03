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

function blockFrom(blockType, from) {
    var block_id = null;
    scheduler.attachEvent("onBeforeViewChange", function(old_mode, old_date, mode, date) {
        if(block_id) scheduler.deleteMarkedTimespan(block_id);
        var from = scheduler.date[mode + "_start"](from);
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
}