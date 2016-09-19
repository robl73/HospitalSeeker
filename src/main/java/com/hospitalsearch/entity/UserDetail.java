package com.hospitalsearch.entity;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospitalsearch.service.annotation.Date;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import com.hospitalsearch.util.Gender;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "userdetail")
@Cache(region="entityCache",usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserDetail{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userdetail_gen")
    @SequenceGenerator(name = "userdetail_gen", sequenceName = "userdetail_id_seq", initialValue = 1, allocationSize = 1)
    @JsonIgnore
    private Long id;

    @Column(name="firstname")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Solomon")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String firstName;

    @Column(name="lastname")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Kane")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String lastName;

    @Pattern(regexp = "^\\+38 \\(\\d{3}\\) \\d{3}-\\d{4}", message = "Not valid. Ex: +38 (095) 435-7132")
    private String phone;

    @Column(name="birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Date(message = "Not valid format")
    private LocalDate birthDate;

    @JsonIgnore
    @Column(name="imagepath")
    private String imagePath;

    @Enumerated(EnumType.STRING)
    @com.hospitalsearch.service.annotation.Gender(message = "Not valid format")
    private Gender gender;

    private String address;

    @OneToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="doctorinfo_id")
    @ContainedIn
    @JsonIgnore
    private DoctorInfo doctorInfo;

    @OneToOne(cascade= CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="patientinfo_id")
    @JsonIgnore
    private PatientInfo patientInfo;

    @OneToOne
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

    public DoctorInfo getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
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
