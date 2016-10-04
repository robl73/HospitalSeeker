/**
 * Created by george on 10/3/16.
 */

function getData(url) {
    return $.ajax({
        type: "GET",
        // async: false,
        url: url,
        dataType: "json",
        contentType: "application/json",
        mimeType: "application/json",
        // success: function (data) {
        //     suc(data);
        // },
        // error: function (e) {
        //     err(e);
        // }
    });
}
