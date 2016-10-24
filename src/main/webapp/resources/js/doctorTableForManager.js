$(document).ready(function () {
//select item per page
    var $select = $('#doctorPerPage');
    $select.change(function () {
        var quantityUsers = this.options[this.selectedIndex].value;
        var setUrl = '/admin/users/setItemsPerPage/' + quantityUsers;
        $.get(setUrl, function (data) {
        });
    });

//select searchBy
    var $searchBy = $('#searchBy');
    var $searchInput = $('#search');
    var searchByStorage = sessionStorage.getItem('searchBy');
    var inputSearchStorage = sessionStorage.getItem('inputSearch');
    var inputSearchValueStorage = sessionStorage.getItem('inputSearchValue');

//save input values in search input
    $('#searchButton').click(function () {
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
        var pageSize = 10;
        var hospitalId = $('#hospitalId');
        var url = '/attachedHospitals/'+hospitalId+'/manageDoctors/setItemsPerPage/' + pageSize;
        $.get(url, function (data) {
        });
        sessionStorage.clear();
        $('#pref-roleby').val('');
        $searchInput.val('');
        window.location.reload();
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
});