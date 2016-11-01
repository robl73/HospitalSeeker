package com.hospitalsearch.dto;

/**
 * Created by Vladimir on 19.10.2016.
 */

import java.time.LocalDate;
import java.util.List;
import com.hospitalsearch.entity.RelativesInfo;
import com.hospitalsearch.service.annotation.Date;
import com.hospitalsearch.util.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PatientDetailDTO{

   private Long id;

    private String imagePath = "";

    @Size(max = 50, message = "Not valid, not more 50 symbols")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Kane")
    private String firstName = "";

    @Size(max = 50, message = "Not valid, not more 50 symbols")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Kane")
    private String lastName = "";

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Date(message = "Not valid format")
    private LocalDate birthDate;

   //@Pattern(regexp = "^\\+38 \\(\\d{3}\\) \\d{3}-\\d{4}", message = "Not valid. Ex: +38 (095) 435-7132")
    private String phone = "";

    private String address = "";

    @Enumerated(EnumType.STRING)
    @com.hospitalsearch.service.annotation.Gender(message = "Not valid format")
    private Gender gender;


    private String height = "";
    private String weight = "";
    private String bloodType = "";
    private String eyeColor = "";
    private String hairColor = "";
    private String email = "";
    private String allergies = "";
    private String currentMedication = "";
    private Boolean heartProblems = false;
    private Boolean diabetes = false;
    private Boolean epilepsy = false;
    private String restrictions = "";
   private List<RelativesInfo> relativesInfos = null;


    public PatientDetailDTO() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getCurrentMedication() {
        return currentMedication;
    }

    public void setCurrentMedication(String currentMedication) {
        this.currentMedication = currentMedication;
    }

    public Boolean getHeartProblems() {
        return heartProblems;
    }

    public void setHeartProblems(Boolean heartProblems) {
        this.heartProblems = heartProblems;
    }

    public Boolean getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(Boolean diabetes) {
        this.diabetes = diabetes;
    }

    public Boolean getEpilepsy() {
        return epilepsy;
    }

    public void setEpilepsy(Boolean epilepsy) {
        this.epilepsy = epilepsy;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public List<RelativesInfo> getRelativesInfos() {
        return relativesInfos;
    }

   public void setRelativesInfos(List<RelativesInfo> relativesInfos) {
        this.relativesInfos = relativesInfos;
    }

}