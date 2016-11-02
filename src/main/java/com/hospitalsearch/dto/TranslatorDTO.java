/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.dto;

import com.hospitalsearch.entity.DepartmentInfo;

/**
 *
 * @author Admin
 */
public class TranslatorDTO {
    
    private Long hospitalid;
    private Long departmentid;
    private Long languageid;
    private DepartmentInfo departmentinfo;

    public TranslatorDTO() {
         this.hospitalid = new Long(0);
        this.departmentid = new Long(0);
        this.languageid = new Long(0);
        this.departmentinfo = new DepartmentInfo();
    }
    
    
    public TranslatorDTO(Long hospitalid, Long departmentid, Long languageid, DepartmentInfo departmentinfo) {
        this.hospitalid = hospitalid;
        this.departmentid = departmentid;
        this.languageid = languageid;
        this.departmentinfo = departmentinfo;
    }

    public TranslatorDTO(Long hospitalid, Long departmentid, Long languageid) {
        this.hospitalid = hospitalid;
        this.departmentid = departmentid;
        this.languageid = languageid;
    }
    

    public Long getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(Long hospitalid) {
        this.hospitalid = hospitalid;
    }

    public Long getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Long departmentid) {
        this.departmentid = departmentid;
    }

    public Long getLanguageid() {
        return languageid;
    }

    public void setLanguageid(Long languageid) {
        this.languageid = languageid;
    }

    public DepartmentInfo getDepartmentinfo() {
        return departmentinfo;
    }

    public void setDepartmentinfo(DepartmentInfo departmentinfo) {
        this.departmentinfo = departmentinfo;
    }

    
    
    
}
