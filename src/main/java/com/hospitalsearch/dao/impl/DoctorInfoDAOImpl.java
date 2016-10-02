package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dto.DoctorDTO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.util.Page;
import org.apache.lucene.queryparser.classic.ParseException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorInfoDAOImpl extends GenericDAOImpl<DoctorInfo, Long> implements DoctorInfoDAO {

    @Autowired
    public DoctorInfoDAOImpl(SessionFactory factory) {
        this.setSessionFactory(factory);
    }

    @Override
    public List<DoctorDTO> findByDepartmentId(Long id) {
        return (List<DoctorDTO>) getHibernateTemplate()
        		.findByNamedParam("select new com.hospitalsearch.dto.DoctorDTO(u.id, u.firstName, u.lastName, u.imagePath, d.specialization) from DoctorInfo d join d.userDetails u join d.departments dep where dep.id = :id ", "id", id);
    }

    @Override
    public List<DoctorDTO> findByManagerId(Long id) {
        List list = getSessionFactory().getCurrentSession().createCriteria(DoctorInfo.class, "doctor")
                .createAlias("doctor.userDetails", "userDetail")
                .createAlias("doctor.departments", "department")
                .createAlias("department.hospital", "hospital")
                .createAlias("hospital.managers", "manager")
                .setProjection(Projections.projectionList()
                        .add(Projections.property("userDetail.id"), "userDetailId")
                        .add(Projections.property("userDetail.firstName"), "firstName")
                        .add(Projections.property("userDetail.lastName"), "lastName")
                        .add(Projections.property("userDetail.imagePath"), "imagePath")
                        .add(Projections.property("doctor.specialization"), "specialization")
                )
                .add(Restrictions.eq("manager.id", id))
                .setResultTransformer(Transformers.aliasToBean(DoctorDTO.class))
                .list();
        return (List<DoctorDTO>) list;
    }

    @Override
    public Long getIdByUserDetail(Long userDetailId) {
        return (Long) getSessionFactory().getCurrentSession().createCriteria(DoctorInfo.class)
                .add(Restrictions.eq("userDetails.id", userDetailId))
                .setProjection(Projections.property("id"))
                .uniqueResult();
    }

    public static final String[] DOCTOR_PROJECTION = new String[]{"userDetails.user.email", "userDetails.firstName", "userDetails.lastName"};
    @Override
    public Page<DoctorInfo> advancedDoctorSearch(String query) throws ParseException, InterruptedException{
        FullTextSession session = Search.getFullTextSession(this.getSessionFactory().openSession());
        session.createIndexer(DoctorInfo.class).startAndWait();
        session.close();
        return new Page<DoctorInfo>(getSessionFactory(), query, DOCTOR_PROJECTION, DoctorInfo.class);
    }

    @Override
    public DoctorInfo getByEmail(String email) {
        Query query = getSessionFactory().getCurrentSession().createQuery("select doc from DoctorInfo doc where doc.userDetails.user.email = :email");
        query.setParameter("email", email);
        return (DoctorInfo) query.uniqueResult();
    }

}
