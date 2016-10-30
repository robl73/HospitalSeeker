/**
 * Created by andrew on 14.05.16.
 */
$(document).ready(function () {
    // show edit page
    $('#editUserModal').on('show.bs.modal', function (e) {
        var urlArray = $(e.relatedTarget).data('id').split(',');
        var url = urlArray[0] + '&' + urlArray[1];
        $.ajax({
            type: 'GET',
            url: url,
            success: function (response) {
                $('#editUserModal').html(response);
            }
        });
    });

    //back to the top
    $('section').prepend('<a href="#top" class="back-to-top">Back to Top</a>');
    $(window).scroll(function () {
        if ($(window).scrollTop() > 0) {
            $('a.back-to-top').fadeIn('slow');
        } else {
            $('a.back-to-top').fadeOut('slow');
        }
    });

    $('a.back-to-top').click(function (event) {
        event.preventDefault();
        $('html, body').animate({
            scrollTop: 0
        }, "slow");
    });

    //select item per page
    var $select = $('#doctorPerPage');
    $select.change(function () {
        $( "#searchForm" ).submit();
    });

    //select searchBy
    var $searchBy = $('#searchBy');
    var $searchInput = $('#search');
    var searchByStorage = sessionStorage.getItem('searchBy');
    var inputSearchStorage = sessionStorage.getItem('inputSearch');
    var inputSearchValueStorage = sessionStorage.getItem('inputSearchValue');

    //save input values in search input
    $('#searchButton').click(function () {
        // doSearch();
        sessionStorage.setItem('inputSearchValue', $searchInput.val());
    });
    $searchInput.val(inputSearchValueStorage);

    // dynamic change DTO filed  select - input
    if (searchByStorage) {
        $searchBy.val(searchByStorage).attr('selected', 'selected');
        $searchInput.attr('name', inputSearchStorage);
    }


    $searchBy.change(function () {
        var searchField = this.options[this.selectedIndex];
        var searchValue = searchField.value;
        var searchText = searchField.text;
        var selectedValue = $searchBy.find(':selected').val();
        $searchInput.attr("name", searchValue).attr("placeholder", searchText);
        sessionStorage.setItem('searchBy', selectedValue);
        sessionStorage.setItem('inputSearch', selectedValue);
    });

    //reset all search field
    $('#clearButton').click(function (event) {
        var hospitalId = $('#hospitalId').val();
        var url = '/manage/hospitals/'+hospitalId+'/manageDoctors/search';
        $.get(url, function (data) {});
        sessionStorage.clear();
         $('#doctorPerPage').val(10);
        $('#pref-specializationBy').val('');
        $searchInput.val('');
        window.location.reload();
    });

    //alert windows (success or error after editing user)
    $('.messageAfterEdit').delay(5000).fadeOut("slow");

    //modal window for delete
    $('#deleteDoctorModal').on('show.bs.modal', function (e) {
        var deleteMessage = getMessage('manager.doctorstable.modal.delete.message');
        $('.content').addClass('blur');
        var Selection = $(e.relatedTarget).data('values').split(",");
        var actionPrefix = Selection[0];
        var doctorId = Selection[1];
        var email = Selection[2];
        $(this).find('#deleteButton').attr('href', actionPrefix + doctorId);
        $('.debug-url').html( deleteMessage + ' ' + '<strong>' + email + ' ?' + '</strong>');
    });
    $('#deleteDoctorModal').on('hide.bs.modal', function (e) {
        $('.content').removeClass('blur');
    });

    $('#deleteButton').click(function (event) {
        $('#clearButton').click();
    })

    //modal window empty list
    var pat = location.pathname.split("/");
    if (($('#allDoctors >tbody >tr').children().length == 0)&&(pat[5]==="search")) {
        $("#emptyListModal").modal();
    }
    $('#emptyListModal').on('show.bs.modal', function (e) {
        $('.content').addClass('blur');
    });
    $('#emptyListModal').on('hide.bs.modal', function (e) {
        $('.content').removeClass('blur');
        $('#clearButton').click();
    });


    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    $("#menu-close").click(function (e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    $(function () {
        $('a[href*="#"]:not([href="#"])').click(function () {
            if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') || location.hostname == this.hostname) {
                var target = $(this.hash);
                target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
                if (target.length) {
                    $('html,body').animate({
                        scrollTop: target.offset().top
                    }, 1000);
                    return false;
                }
            }
        });
    });

    $("#deleteButton").click(function (e) {
        $('#floatingCirclesG').modal('show');
    });

});

