package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.UserDetailDAO;
import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.PatientInfo;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.PatientInfoService;
import com.hospitalsearch.service.UserDetailService;
import com.hospitalsearch.util.Page;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailDAO dao;

    @Autowired
    private PatientCardService patientCardService;

    @Autowired
    private PatientInfoService patientInfoService;

    @Override
    public void save(UserDetail newUserDetail) {
        PatientCard patientCard = patientCardService.add(new PatientCard());
        PatientInfo patientInfo = patientInfoService.add(new PatientInfo());
        patientInfo.setPatientCard(patientCard);
//        newUserDetail.setPatientInfo(patientInfo);
        dao.save(newUserDetail);
    }

    @Override
    public void delete(UserDetail userDetail) {
        dao.delete(userDetail);
    }

    @Override
    public void update(UserDetail updatedUserDetail) {
        dao.update(updatedUserDetail);
    }

    @Override
    public UserDetail add(UserDetail userDetail) {
        PatientCard patientCard = patientCardService.add(new PatientCard());
        PatientInfo patientInfo = patientInfoService.add(new PatientInfo());
        patientInfo.setPatientCard(patientCard);
//        userDetail.setPatientInfo(patientInfo);
        userDetail = dao.add(userDetail);
        return userDetail;
    }
    @Override
    public UserDetail getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<UserDetail> getAll() {
        return dao.getAll();
    }
}
