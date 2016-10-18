/**
 * Created by george on 10/3/16.
 */

function getData(url) {

    return $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });

}
