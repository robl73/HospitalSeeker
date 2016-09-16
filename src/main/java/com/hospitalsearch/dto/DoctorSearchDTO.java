package com.hospitalsearch.dto;

import java.util.List;

/**
 * Created by Lesia Koval on 13.09.2016.
 */
public class DoctorSearchDTO {

    private String imagePath;
    private String firstName;
    private String lastName;
    private String email;
    private String specialization;
    private String category;
    private List<String> hospitalsName;

    public DoctorSearchDTO() {
    }

    public DoctorSearchDTO(String imagePath, String firstName, String lastName, String email, String specialization, String category, List<String> hospitalsName) {
        this.imagePath = imagePath;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.specialization = specialization;
        this.category = category;
        this.hospitalsName = hospitalsName;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<String> getHospitalsName() {
        return hospitalsName;
    }

    public void setHospitalsName(List<String> hospitalsName) {
        this.hospitalsName = hospitalsName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
