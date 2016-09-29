package com.hospitalsearch.dao;

import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.PatientInfo;
import org.springframework.stereotype.Component;

/**
 * Created by ruslan on 13.09.16.
 */
@Component
public interface PatientInfoDAO extends GenericDAO<PatientInfo, Long> {

    PatientInfo add(PatientInfo patientInfo);

    PatientInfo getByUserDetailId(Long id);

}
