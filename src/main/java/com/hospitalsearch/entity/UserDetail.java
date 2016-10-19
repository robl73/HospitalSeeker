package com.hospitalsearch.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospitalsearch.service.annotation.Date;
import com.hospitalsearch.util.Gender;
import com.hospitalsearch.util.Disease;

@Entity
@Table(name = "userdetail")
@Indexed
@Cache(region="entityCache",usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserDetail{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userdetail_gen")
    @SequenceGenerator(name = "userdetail_gen", sequenceName = "userdetail_id_seq", initialValue = 1, allocationSize = 1)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @Column(name="imagepath")
    private String imagePath;


    @Column(name="firstname")
    @Size(max = 50, message = "Not valid, not more 50 symbols")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Solomon")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String firstName;

    @Column(name="lastname")
    @Size(max = 50, message = "Not valid, not more 50 symbols")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Kane")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String lastName;

    @Column(name="birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Date(message = "Not valid format")
    private LocalDate birthDate;

    @Column(name="phone")
    @Pattern(regexp = "^\\+38 \\(\\d{3}\\) \\d{3}-\\d{4}", message = "Not valid. Ex: +38 (095) 435-7132")
    private String phone;

    @Column(name="address")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String address;

    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    @com.hospitalsearch.service.annotation.Gender(message = "Not valid format")
    private Gender gender;

    @Column(name="height")
   // @Pattern(regexp = "d{1,3}", message = "Not valid. Only numbers")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String height;

    @Column(name="weight")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String weight;

    @Column(name="bloodType")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String bloodType;

    @Column(name="eyeColor")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String eyeColor;

    @Column(name="hairColor")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String hairColor;

    @Column(name="email")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String email;

    @Column(name="allergies")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String allergies;

    @Column(name="currentMedication")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String currentMedication;

    @Column(name="heartProblems")
    @Enumerated(EnumType.STRING)
    @com.hospitalsearch.service.annotation.Disease(message = "Not valid format")
    private Disease heartProblems;

    @Column(name="diabetes")
    @Enumerated(EnumType.STRING)
    @com.hospitalsearch.service.annotation.Disease(message = "Not valid format")
    private Disease diabetes;

    @Column(name="epilepsy")
    @Enumerated(EnumType.STRING)
    @com.hospitalsearch.service.annotation.Disease(message = "Not valid format")
    private Disease epilepsy;

    @Column(name="restrictions")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String restrictions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_detail_id")
    private List<RelativesInfo> relativesInfos;

    @OneToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "user_id")
    @IndexedEmbedded
    private User user;

    public UserDetail() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Disease getHeartProblems() {
        return heartProblems;
    }

    public void setHeartProblems(Disease heartProblems) {
        this.heartProblems = heartProblems;
    }

   public Disease getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(Disease diabetes) {
        this.diabetes = diabetes;
    }

    public Disease getEpilepsy() {
        return epilepsy;
    }

    public void setEpilepsy(Disease epilepsy) {
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

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                '}';
    }
}
