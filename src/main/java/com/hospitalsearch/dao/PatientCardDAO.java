package com.hospitalsearch.dao;

import com.hospitalsearch.dao.GenericDAO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface PatientCardDAO extends GenericDAO<PatientCard, Long> {

    PatientCard add(PatientCard patientCard);

    PatientCard getByUser(User user);

}
