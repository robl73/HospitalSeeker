package com.hospitalsearch.dao.impl;

import java.util.List;

import com.hospitalsearch.entity.DoctorInfo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.DepartmentDAO;
import com.hospitalsearch.entity.Department;

/**
 * Created by deplague on 5/11/16.
 */
@Repository
public class DepartmentDAOImpl extends GenericDAOImpl<Department,Long> implements DepartmentDAO{

    @Autowired
    public DepartmentDAOImpl(SessionFactory factory){
        this.setSessionFactory(factory);
    }

    @Override
    public List<Department> findDepartmentByDoctorId(Long id){
//        List questions = sess.createCriteria(Question.class)
//                .createAlias("QuestionTag", "qt")
//                .createAlias("qt.Tags", "t")
//                .add( Restrictions.eq("t.name", "hibernate") )
//                .list();

        Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Department.class)
                .createAlias("doctors", "depAlias")
                //.createAlias("depAlias.departments", "docAlias")
                .add(Restrictions.eq("depAlias.id", id));
        return (List<Department>) criteria.list();
    }

    @Override
    public List<Department> findByHospitalId(Long id) {
        return ((List<Department>) getHibernateTemplate()
                .findByCriteria(DetachedCriteria.forClass(Department.class).add(Restrictions.eq("hospital.id",id))));
    }
}
