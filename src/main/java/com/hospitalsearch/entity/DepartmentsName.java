/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.search.annotations.DocumentId;

/**
 *
 * @author Admin
 */

//@Embeddable
@Entity
@Table(name = "departmenstname")
public class DepartmentsName implements Serializable {
    
    @Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departmentname_gen")
        @SequenceGenerator(name = "departmentname_gen", sequenceName = "departmentname_id_seq", initialValue = 1, allocationSize = 1)
	private Long id;
    
//    @Column(name="departmentname")   
    private String name;
    
 //   @Column(name="departmentspecialization")
    private String specialization;
    
 //   @Column(name="departmentnumber")
    private String number;
    
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    public DepartmentsName() {
       
    }
    
    
    
    
    
}
