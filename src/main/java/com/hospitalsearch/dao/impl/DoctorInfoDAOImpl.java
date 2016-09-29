package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.entity.*;
import com.hospitalsearch.util.Page;
import org.apache.lucene.queryparser.classic.ParseException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorInfoDAOImpl extends GenericDAOImpl<DoctorInfo,Long> implements DoctorInfoDAO {

    @Autowired
    public DoctorInfoDAOImpl(SessionFactory factory) {
        this.setSessionFactory(factory);
    }

    @Override
    public List<UserDetail> findByDepartmentId(Long id) {
        return (List<UserDetail>) getHibernateTemplate()
        		.findByNamedParam("select u from UserDetail u join u.doctorsDetails d join d.departments dep where dep.id = :id ","id",id);
    }

    @Override
    public List<UserDetail> findByManagerId(Long id){
        return (List<UserDetail>) getSessionFactory().openSession().createCriteria(UserDetail.class, "u").
                createAlias("u.doctorsDetails", "doctorsDetails")
                .createAlias("doctorsDetails.departments", "department")
                .createAlias("department.hospital", "hospital")
                .createAlias("hospital.managers", "manager")
                .add(Restrictions.eq("manager.id",id)).list();
    }

    public static final String[] DOCTOR_PROJECTION = new String[]{"userDetails.user.email", "userDetails.firstName", "userDetails.lastName"};
    @Override
    public Page<DoctorInfo> advancedDoctorSearch(String query) throws ParseException, InterruptedException{
        FullTextSession session = Search.getFullTextSession(this.getSessionFactory().openSession());
        session.createIndexer(DoctorInfo.class).startAndWait();
        session.close();
        return new Page<DoctorInfo>(getSessionFactory(), query, DOCTOR_PROJECTION, DoctorInfo.class);
    }
}
