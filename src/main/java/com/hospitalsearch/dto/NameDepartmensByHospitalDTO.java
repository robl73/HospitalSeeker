package com.hospitalsearch.dto;

import com.hospitalsearch.entity.Department;

/**
 * Created by ruslan on 12.10.16.
 */
public class NameDepartmensByHospitalDTO {

    private Long id;

    private String name;

    public NameDepartmensByHospitalDTO() {
    }

    public NameDepartmensByHospitalDTO(Department department) {
        this.id = department.getId();
        this.name = department.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NameDepartmensByHospitalDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
