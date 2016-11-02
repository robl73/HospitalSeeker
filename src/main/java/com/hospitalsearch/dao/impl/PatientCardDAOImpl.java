package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.PatientCardDAO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.PatientInfo;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository ("patientCardDAO")
public class PatientCardDAOImpl extends GenericDAOImpl<PatientCard, Long> implements PatientCardDAO {

    @Autowired
    public PatientCardDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    public PatientCard getByUser(User user) {
        UserDetail userDetail = user.getUserDetails();
        Query patientQuery = getSessionFactory().getCurrentSession().createQuery("select patient from PatientInfo patient where patient.userDetail.id = :userDetailId");
        patientQuery.setParameter("userDetailId", userDetail.getId());
        PatientInfo patientInfo = (PatientInfo) patientQuery.uniqueResult();
        return patientInfo.getPatientCard();
    }

    @Override
    public PatientCard add(PatientCard patientCard) {
        PatientCard patientCardFromDB = (PatientCard) this.getSessionFactory().getCurrentSession().merge(patientCard);
        return patientCardFromDB;
    }
}
