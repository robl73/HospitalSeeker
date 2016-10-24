package com.hospitalsearch.dto;


import com.hospitalsearch.service.annotation.Date;
import com.hospitalsearch.service.annotation.UniqueEmail;
import com.hospitalsearch.util.Category;
import com.hospitalsearch.util.Specialization;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan on 27.09.16.
 */
public class NewDoctorRegistrationDTO {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @NotEmpty(message = "Please enter your email")
    @Email
    @UniqueEmail(message = "User with this email is already exists")
    @Pattern(regexp = EMAIL_PATTERN, message = "Please enter email in correct format.")
    private String email;

    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Solomon")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Kane")
    private  String lastName;

    private String imagePath;

    private String Education;

    private String Address;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Date(message = "Not valid format")
    private LocalDate birthDate;

    @Pattern(regexp = "^\\+38 \\(\\d{3}\\) \\d{3}-\\d{4}", message = "Not valid. Ex: +38 (095) 435-7132")
    private String phone;

    private Boolean enabled = false;

    private List<NameHospitalsByManagerDTO> nameHospitals = new ArrayList<>();

    private Long nameHospitalId;

    private Long nameDepartmentsId;

 //   private List<Category> categorys = new ArrayList<>();

  //  private List<Specialization> specializations = new ArrayList<>();

    private Category category;

    private Specialization specialization;

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

    public List<NameHospitalsByManagerDTO> getNameHospitals() {
        return nameHospitals;
    }

    public void setNameHospitals(List<NameHospitalsByManagerDTO> nameHospitals) {
        this.nameHospitals = nameHospitals;
    }

    public Long getNameHospitalId() {
        return nameHospitalId;
    }

    public void setNameHospitalId(Long nameHospitalId) {
        this.nameHospitalId = nameHospitalId;
    }

    public Long getNameDepartmentsId() {
        return nameDepartmentsId;
    }

    public void setNameDepartmentsId(Long nameDepartmentsId) {
        this.nameDepartmentsId = nameDepartmentsId;
    }

//    public List<Category> getCategorys() {
//        return categorys;
//    }
//
//    public List<Specialization> getSpecializations() {
//        return specializations;
//    }

    public Category getCategory() {
        return category;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

//    public void setCategorys(List<Category> categorys) {
//        this.categorys = categorys;
//    }
//
//    public void setSpecializations(List<Specialization> specializations) {
//        this.specializations = specializations;
//    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
                ", phone='" + phone + '\'' +
                ", nameHospitals=" + nameHospitals +
                ", nameHospitalId=" + nameHospitalId +
                ", nameDepartmentsId=" + nameDepartmentsId +
                ", category='" + category + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
