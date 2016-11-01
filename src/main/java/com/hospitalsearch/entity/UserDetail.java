package com.hospitalsearch.entity;

import java.time.LocalDate;

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
    private String firstName;

    @Column(name="lastname")
    @Size(max = 50, message = "Not valid, not more 50 symbols")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Kane")
    private String lastName;

    @Column(name="birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Date(message = "Not valid format")
    private LocalDate birthDate;

    @Column(name="phone")
   @Pattern(regexp = "^\\+38 \\(\\d{3}\\) \\d{3}-\\d{4}", message = "Not valid. Ex: +38 (095) 435-7132")
    private String phone;

    @Column(name="address")
    private String address;

    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    @com.hospitalsearch.service.annotation.Gender(message = "Not valid format")
    private Gender gender;

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
