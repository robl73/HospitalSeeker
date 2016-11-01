/**
 * Created by Volodymyr on 25.10.2016.
 */

function submitPatientForm(){

    if (getLocale() == 'ua') {
        $.extend( $.validator.messages, {
            required: 'Це поле необхідно заповнити.',
            maxlength: $.validator.format( 'Будь ласка, введіть не більше {0} символів.' )
        });
    }


    $.validator.addMethod("regex", function(value, element, regexpr) {
        return regexpr.test(value);});
    if (getLocale() == 'ua'){
        var relativePhone = 'Не валідний формат. Приклад: +38 (095) 435-7132';
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
        var relativePhone = 'Not valid. +38 (095) 435-7132';
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

    $('#patientProfileForm').validate({
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
            relativePhone: {
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
            /*heartProblems: {
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
            },*/
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
            relativePhone: {
                regex: relativePhone
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
            allergies: {
                regex: allergies
            },
            currentMedication: {
                regex: currentMedication
            },
           /* heartProblems: {
                regex: heartProblems
            },
            diabetes: {
                regex: diabetes
            },
            epilepsy: {
                regex: epilepsy
            },*/
            restrictions: {
                regex: restrictions
            }

        }
    });

    if($('#patientProfileForm').valid()){
        var str = $("#patientProfileForm").serialize();

        $.ajax({
            type:"post",
            data:str,
            url:jsContextPath +"user/patientProfile",
            async: false,
            success: function(response){
                $('#myModal').html(response);
                $('#birthDate').datepicker({
                    format: 'yyyy-mm-dd',
                    endDate: "0d"
                });

                $('#relativePhone').bfhphone({
                    format: '+38 (ddd) ddd-dddd'
                });
            }
        });
    }
};


function  showPatientPage(){
    $.ajax({ type: 'GET', url: jsContextPath + 'user/patientProfile', contentType: 'application/json' , success: function(response) {
        $('#myModal').html(response);
    }})};

function editPatientProfile(){
    $.ajax({ type: 'GET', url: jsContextPath +'user/patientProfile?edit=true',contentType: 'application/json', success: function(response){
        $('#myModal').html(response);

        $('#birthDate').datepicker({
            format: 'yyyy-mm-dd',
            endDate: "0d"
        });

        $('#relativePhone').bfhphone({
            format: '+38 (ddd) ddd-dddd'
        });

        if (document.getElementById('imagePath').value) {
            $('#image-uploaded').attr('src', jsContextPath + 'images/avatar/' + document.getElementById('imagePath').value);
        }

        var divModal = document.createElement('div');
        divModal.setAttribute('id', 'div-modal');
        document.body.appendChild(divModal);
        $('#div-modal').load(jsContextPath + 'modalupload.html');

    }})

};

