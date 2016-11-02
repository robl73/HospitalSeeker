package com.hospitalsearch.service;

import com.hospitalsearch.dto.PatientDetailDTO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.PatientInfo;
import com.hospitalsearch.entity.UserDetail;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ruslan on 13.09.16.
 */
@Component
public interface PatientInfoService {

    void save(PatientInfo patientInfo);

    void delete(PatientInfo patientInfo);

    void update(PatientInfo patientInfo);

    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    PatientInfo getById(Long id);

    PatientInfo getByUserDetailId(Long id);

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<PatientInfo> getAll();

    PatientInfo add(PatientInfo patientInfo);

    PatientDetailDTO getPatientProfileByEmail(String email, PatientDetailDTO patientDetailDTO);

    @Transactional
    void updatePatientProfile(PatientDetailDTO patientDetailDTO);
}
