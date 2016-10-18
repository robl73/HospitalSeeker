package com.hospitalsearch.dto;

import com.hospitalsearch.util.Category;
import com.hospitalsearch.util.Specialization;

import java.util.List;

/**
 * Created by Lesia Koval on 13.09.2016.
 */
public class DoctorSearchDTO {

    private Long doctorId;
    private String imagePath;
    private String firstName;
    private String lastName;
    private String email;
    private Specialization specialization;
    private Category category;
    private List<String> hospitalsName;

    public DoctorSearchDTO() {
    }

    public DoctorSearchDTO(Long doctorId, String imagePath, String firstName, String lastName, String email,
                           Specialization specialization, Category category, List<String> hospitalsName) {
        this.doctorId = doctorId;
        this.imagePath = imagePath;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.specialization = specialization;
        this.category = category;
        this.hospitalsName = hospitalsName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public List<String> getHospitalsName() {
        return hospitalsName;
    }

    public void setHospitalsName(List<String> hospitalsName) {
        this.hospitalsName = hospitalsName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "DoctorSearchDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                ", category=" + category + '\'' +
                ", hospitalName='" + hospitalsName + '\'' +
                '}';
    }
}
