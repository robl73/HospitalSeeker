/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.entity;


import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */

@Embeddable
//@Table(name="departmentinfo")
public class DepartmentInfo implements Serializable {
     
//    @Column(name="departmentname")
    @NotNull(message="this field can not be empty")
    @Size(min = 3, max = 20, message="you must enter from 3 to 20 chars")
    @Pattern(regexp="(\\p{Alpha}){3,20}", message="you must enter from 3 to 20 chars only")
    private String name;
    
 //   @Column(name="departmentspecialization")
    @Size(min = 0, max = 20, message="you may enter up to 20 chars")
    @Pattern(regexp="(\\p{Alpha}){0,20}", message="you may enter up to 20 chars only")
    private String specialization;
    
 //   @Column(name="departmentdescription")
    @Size(min = 0, max = 200, message="you may enter up to 200 chars")
    @Pattern(regexp="(\\p{Alpha}){0,200}", message="you may enter up to 200 chars only")
    private String description;

    public DepartmentInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
    
}
