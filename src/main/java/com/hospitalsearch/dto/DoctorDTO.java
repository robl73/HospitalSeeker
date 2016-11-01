package com.hospitalsearch.dto;

import com.hospitalsearch.util.Specialization;

/**
 * Created by george on 9/21/16.
 */
public class DoctorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String imagePath;
    private Specialization specialization;

    public DoctorDTO(Long id, String firstName, String lastName, String imagePath, Specialization specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imagePath = imagePath;
        this.specialization = specialization;
    }

    public DoctorDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
