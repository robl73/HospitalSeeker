package com.hospitalsearch.dto;

import com.hospitalsearch.service.annotation.UniqueEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ruslan on 27.09.16.
 */
public class NewDoctorRegistrationDTO {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String NAME_PATTERN = "([A-Z]{1}[a-z]{0,10})([\\\\s\\\\\\'-][A-Z]{1}[a-z]{0,10}){0,20}|([A-Z]{1}[a-z]{0,10})([' ']{1,10}[A-Z]{1}[a-z]{0,10}){0,20} " +
            "| ([А-ЯЄІЇ]{1}[а-яєії]{0,10})([\\\\s\\\\\\'-][А-ЯЄІЇ]{1}[а-яєії]{0,10}){0,20}|([А-ЯЄІЇ]{1}[а-яєії]{0,10})([' ']{0,5}[А-ЯЄІЇ]{1}[а-яєії]{0,10}){0,20}";

    @NotEmpty(message = "Please enter your email")
    @Email
    @UniqueEmail(message = "User with this email is already exists")
    @Pattern(regexp = EMAIL_PATTERN, message = "Please enter email in correct format.")
    private String email;

    @NotEmpty(message = "Plese enter your First Name")
    @Pattern(regexp = NAME_PATTERN, message = "Please enter First Name in correct format.")
    private String firstName;

    @NotEmpty(message = "Please enter your Last Name")
    @Pattern(regexp = NAME_PATTERN, message = "Please enter Last Name in correct format.")
    private  String lastName;

    private String imagePath;

    private String Education;

    private String Address;

    @Pattern(regexp = "^\\+38 \\(\\d{3}\\) \\d{3}-\\d{4}", message = "Not valid. Ex: +38 (095) 435-7132")
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getEducation() {
        return Education;
    }

    public String getAddress() {
        return Address;
    }

    public void setEducation(String education) {
        Education = education;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "NewDoctorRegistrationDTO{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", Education='" + Education + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }
}
