package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.UserDetailDAO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    private UserDetailDAO dao;

    @Autowired
    private PatientCardService patientCardService;

    @Override
    public void save(UserDetail newUserDetail) {
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
        userDetail.setPatientCard(patientCard);
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
