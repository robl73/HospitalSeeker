package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.PatientInfoDAO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.PatientInfo;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.PatientInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ruslan on 13.09.16.
 */
@Service
@Transactional
public class PatientInfoServiceImpl implements PatientInfoService {

    final static Logger log = Logger.getLogger(PatientInfoService.class);

    @Autowired
    private PatientInfoDAO patientInfoDAO;

    @Override
    public void save(PatientInfo patientInfo) {
        patientInfoDAO.save(patientInfo);
    }

    @Override
    public void delete(PatientInfo patientInfo) {
        patientInfoDAO.delete(patientInfo);
    }

    @Override
    public void update(PatientInfo patientInfo) {
        patientInfoDAO.update(patientInfo);
    }

    @Override
    public PatientInfo getById(Long id) {
        return patientInfoDAO.getById(id);
    }

    @Override
    public PatientInfo getByUserDetailId(Long id) {
        return patientInfoDAO.getByUserDetailId(id);
    }

    @Override
    public List<PatientInfo> getAll() {
        return patientInfoDAO.getAll();
    }

    @Override
    public PatientInfo add(PatientInfo patientInfo) {
        try{
            patientInfo = patientInfoDAO.add(patientInfo);
            log.info("Save patientinfo" + patientInfo);
        }catch (Exception e){
            log.error("Saving patientinfo" + patientInfo);
        }
        return patientInfo;
    }
}
