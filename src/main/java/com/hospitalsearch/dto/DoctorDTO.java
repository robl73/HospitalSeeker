package com.hospitalsearch.dto;

/**
 * Created by vanytate on 9/21/16.
 */
public class DoctorDTO {

    private Long userDetailId;
    private String firstName;
    private String lastName;
    private String imagePath;
    private String specialization;

    public DoctorDTO(Long userDetailId, String firstName, String lastName, String imagePath, String specialization) {
        this.userDetailId = userDetailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imagePath = imagePath;
        this.specialization = specialization;
    }

    public DoctorDTO() {
    }

    public Long getUserDetailId() {
        return userDetailId;
    }

    public void setUserDetailId(Long userDetailId) {
        this.userDetailId = userDetailId;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
