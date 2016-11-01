package com.hospitalsearch.dto;

import com.hospitalsearch.entity.Hospital;

/**
 * Created by ruslan on 12.10.16.
 */
public class NameHospitalsByManagerDTO {

    private Long id;

    private String name;

    public NameHospitalsByManagerDTO() {
    }

    public NameHospitalsByManagerDTO(Hospital hospital) {
        this.id = hospital.getId();
        this.name = hospital.getName();
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NameHospitalsByManagerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
