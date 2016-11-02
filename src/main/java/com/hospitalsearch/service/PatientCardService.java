package com.hospitalsearch.service;

import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.User;

import org.springframework.stereotype.Component;

@Component
public interface PatientCardService {

    void remove(PatientCard patientCard);

    PatientCard getById(Long id);

    PatientCard add(PatientCard patientCard);
    PatientCard getByUser(User user);

}
