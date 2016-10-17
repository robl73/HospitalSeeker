
function submitForm(){
    if (getLocale() == 'ua') {
        $.extend( $.validator.messages, {
            required: 'Це поле необхідно заповнити.',
            maxlength: $.validator.format( 'Будь ласка, введіть не більше {0} символів.' )
        });
    }



    $.validator.addMethod("regex", function(value, element, regexpr) {
        return regexpr.test(value);});
    if (getLocale() == 'ua'){
        var phone = 'Не валідний формта. Приклад: +38 (095) 435-7132';
        var birthDate= 'Не валідний формат. Приклад: 1993-07-21';
        var gender= 'Не валідний формат. Має бути MALE або FEMALE';
        var firstName = 'Не валідний формат. Приклад: Solomon';
        var lastName = 'Не валідний формат. Приклад: Kane';
        var weight = 'Не валідний формат. Введіть число';
        var height = 'Не валідний формат. Введіть число';
        var bloodType = 'Не валідний формат. Приклад: A+';
        var eyeColor = 'Не валідний формат. Приклад: Green';
        var hairColor = 'Не валідний формат. Приклад: Green';
        var email = 'Не валідний формат. Приклад: patient@hospitals.ua';
        var allergies = 'Не валідний формат. Приклад: Dust Allergy';
        var currentMedication = 'Не валідний формат. Приклад: Antibiotics';
        var heartProblems = 'Не валідний формат. Має бути YES або NO';
        var diabetes = 'Не валідний формат. Має бути YES або NO';
        var epilepsy = 'Не валідний формат. Має бути YES або NO';
        var restrictions = 'Не валідний формат. Приклад: Деякі обмеження';



    }else{
        var phone = 'Not valid. +38 (095) 435-7132';
        var birthDate= 'Not valid. Example: 1993-07-21';
        var gender= 'Not valid. Must be MALE or FEMALE';
        var firstName = 'Not valid. Example: Solomon';
        var lastName = 'Not valid. Example: Kane';
        var weight = 'Not valid. Input number';
        var height = 'Not valid. Input number';
        var bloodType = 'Not valid. Example: A+';
        var eyeColor = 'Not valid. Example: Green';
        var hairColor = 'Not valid. Example: Green';
        var email = 'Not valid. Example: patient@hospitals.ua';
        var allergies = 'Not valid. Example: Dust Allergy';
        var currentMedication = 'Not valid. Example: Antibiotics';
        var heartProblems = 'Not valid. Must be YES or NO';
        var diabetes = 'Not valid. Must be YES or NO';
        var epilepsy = 'Not valid. Must be YES or NO';
        var restrictions ='Not valid. Example: Some restrictions';
    }

    $('#detailForm').validate({
        rules: {
            firstName: {
                required: true,
                regex: /^[A-Z][a-z]+$/,
                maxlength: 50
            },
            lastName: {
                required: true,
                regex: /^[A-Z][a-z]+$/,
                maxlength: 50
            },
            birthDate: {
                required: true,
                regex: /[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])/
            },
            gender: {
                required: true,
                regex: /^MALE|FEMALE/
            },
            address: {
                required: true
            },
            phone: {
                required: true,
                regex: /^\+38 \(\d{3}\) \d{3}-\d{4}$/
            },
            weight: {
                required: true,
                regex: /^[0-9]{1,4}$/,
                maxlength: 15
            },
            height: {
                required: true,
                regex: /^[0-9]{1,4}$/,
                maxlength: 15
            },
            bloodType: {
                required: true,
                regex: /^(A|B|AB|O)[+-]$/
            },
            eyeColor: {
                required: true,
                regex: /^[A-Z][a-z]+$/,
                maxlength: 20
            },
            hairColor: {
                required: true,
                regex: /^[A-Z][a-z]+$/,
                maxlength: 20
            },
            email: {
                required: true,
                regex: /^[A-Za-z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$/,
                maxlength: 20
            },
            allergies: {
                required: true,
                regex: /^[A-Z][a-z]+$/,
                maxlength: 100
            },
            currentMedication: {
                required: true,
                regex: /^[A-Z][a-z]+$/,
                maxlength: 100
            },
            heartProblems: {
                required: true,
                regex: /^YES|NO/
            },
            diabetes: {
                required: true,
                regex: /^YES|NO/
            },
            epilepsy: {
                required: true,
                regex: /^YES|NO/
            },
            restrictions: {
                required: true,
                regex: /^[A-Z][a-z]+$/,
                maxlength: 100
            }



        },

        messages:{
            firstName:{
                regex: firstName
            },

            lastName:{
                regex: lastName
            },

            birthDate: {
                regex: birthDate
            },
            gender: {
                regex: gender
            },
            phone: {
                regex: phone
            },
            weight: {
                regex: weight
            },
            height: {
                regex: weight
            },
            bloodType: {
                regex: bloodType
            },
            eyeColor: {
                regex: eyeColor
            },
            hairColor: {
                regex: eyeColor
            },
            email: {
                regex: email
            },
            allergies: {
                regex: allergies
            },
            currentMedication: {
                regex: currentMedication
            },
            heartProblems: {
                regex: heartProblems
            },
            diabetes: {
                regex: diabetes
            },
            epilepsy: {
                regex: epilepsy
            },
            restrictions: {
                regex: restrictions
            }

        }
    });

if($('#detailForm').valid()){
    var str = $("#detailForm").serialize();
    $.ajax({

        type:"post",
        data:str,
        url:jsContextPath +"save/detail",
        async: false,
        success: function(response){
            $('#myModal').html(response);
            $('#birthDate').datepicker({
                format: 'yyyy-mm-dd',
                endDate: "0d"
            });

            $('#phone').bfhphone({
                format: '+38 (ddd) ddd-dddd'

            });
        }
    });
}
};

function real(){
    alert('YOU ARE THE BEST DEVELOPER EVER! With best regards Ch-039');
};

function showPage(){
    $.ajax({ type: 'GET', url: jsContextPath +'user/detail', contentType: 'application/json' , success: function(response) {
        $('#myModal').html(response);

    }})};

function showProfile(userId){
    $.ajax({ type: 'GET', url: jsContextPath +'user/profile?userId='+userId, success: function(response) {
        $('#profileModal').html(response);
    }})};

function editProfile(){
    $.ajax({ type: 'GET', url: jsContextPath +'user/detail?edit=true',contentType: 'application/json', success: function(response){
        $('#myModal').html(response);

        $('#birthDate').datepicker({
            format: 'yyyy-mm-dd',
            endDate: "0d"
        });

        $('#phone').bfhphone({
            format: '+38 (ddd) ddd-dddd'
        });

        if (document.getElementById('imagePath').value) {
            $('#image-uploaded').attr('src', jsContextPath + 'images/avatar/' + document.getElementById('imagePath').value);
        }

        var divModal = document.createElement('div');
        divModal.setAttribute('id', 'div-modal');
        document.body.appendChild(divModal);
        $('#div-modal').load(jsContextPath + 'modalupload.html');

    }})};







