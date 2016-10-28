/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.entity;

import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author Admin
 */
@Entity
@Table(name="translator")
public class Translator implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translator_gen")
    @SequenceGenerator(name = "translator_gen", sequenceName = "translator_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Translator)) {
            return false;
        }
        Translator other = (Translator) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hospitalsearch.entity.Translator[ id=" + id + " ]";
    }
    
    @Embedded DepartmentInfo departmentinfo;

    public DepartmentInfo getDepartmentinfo() {
        return departmentinfo;
    }

    public void setDepartmentinfo(DepartmentInfo departmentinfo) {
        this.departmentinfo = departmentinfo;
    }
    
@NaturalId
@ManyToOne
private Hospital hospitalID;


@NaturalId
@ManyToOne
private Department departmentID;

@NaturalId
@ManyToOne
private Language languageID;

    public Hospital getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(Hospital hospitalID) {
        this.hospitalID = hospitalID;
    }

    public Department getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Department departmentID) {
        this.departmentID = departmentID;
    }

    public Language getLanguageID() {
        return languageID;
    }

    public void setLanguageID(Language languageID) {
        this.languageID = languageID;
    }

    public Translator() {
    }







}