package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.SchedulerDAO;
import com.hospitalsearch.entity.Scheduler;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by vanytate on 9/9/16.
 */

@Repository
public class SchedulerDAOImpl extends GenericDAOImpl<Scheduler, Long> implements SchedulerDAO {

    @Autowired
    public SchedulerDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    public Scheduler getByDoctorId(Long doctorId) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Scheduler.class);
        criteria.add(Restrictions.eq("doctorInfo.id", doctorId));
        Scheduler scheduler = (Scheduler) criteria.uniqueResult();
        return scheduler;
    }
}
