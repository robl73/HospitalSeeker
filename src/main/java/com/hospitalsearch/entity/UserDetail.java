package com.hospitalsearch.entity;

import java.time.LocalDate;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospitalsearch.service.annotation.Date;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import com.hospitalsearch.util.Gender;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "userdetail")
@Indexed
@Cache(region="entityCache",usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserDetail{

    @Id
    @Column(name = "id")
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

    @MapsId
    @JoinColumn(name = "id")
    @OneToOne
//    @Fetch(FetchMode.SELECT)
//    @JoinColumn(name="user_id")
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
