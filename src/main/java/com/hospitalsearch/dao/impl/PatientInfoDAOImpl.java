package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.PatientInfoDAO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.PatientInfo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by ruslan on 13.09.16.
 */
@Repository("patientinfoDAO")
public class PatientInfoDAOImpl extends GenericDAOImpl<PatientInfo, Long> implements PatientInfoDAO{

    @Autowired
    public PatientInfoDAOImpl(SessionFactory sessionFactory) {
        super();
        this.setSessionFactory(sessionFactory);
    }

    @Override
    public PatientInfo add(PatientInfo patientInfo) {
        PatientInfo patientInfoFromDB = (PatientInfo) this.getSessionFactory().getCurrentSession().merge(patientInfo);
        return patientInfoFromDB;
    }
}
