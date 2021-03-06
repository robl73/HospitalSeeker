/**
 * Created by andrew on 14.05.16.
 */
$(document).ready(function () {
//generate class on sort buttons
    var sortDto = $('#sortDto');
    var ascDto = $('#ascDto');
    var btnId = document.getElementById(sortDto.val());
    if (ascDto.val() == 'true') {
        btnId.setAttribute('class', 'btn sorter sorter-asc pull-right');
    } else {
        btnId.setAttribute('class', 'btn sorter sorter-desc pull-right');
    }

});

//sort buttons
function sort(event) {

    var loc = location.pathname;
    var pat = loc.match(/(\/).*(\/)/g);
    var status = $('#status').val();
    var id = document.getElementById(event.target.id);
    var hospitalId = $('#hospitalId').val();
    var sortDto = $('#sortDto');
    var ascStorage = sessionStorage.getItem('ascStorage');

    sortDto.val(event.target.id);
    ascStorage == null ? ascStorage = true : ascStorage;
    ascStorage == "true" ? sessionStorage.setItem('ascStorage', "false") : sessionStorage.setItem('ascStorage', "true");
    if ($('#searchDto').length) {
        $("#ascDto").val(ascStorage);
        id.setAttribute('form', 'searchForm');
    } else {
        $("#ascDto").val(ascStorage);
        if (pat == "/admin/") {
            loc = "?status="+status;
            window.location.replace(loc + '&sort=' + sortDto.val() + '&asc=' + ascStorage);
        }else {
            window.location.replace(loc + '?sort=' + sortDto.val() + '&asc=' + ascStorage);
        }

    }
}

