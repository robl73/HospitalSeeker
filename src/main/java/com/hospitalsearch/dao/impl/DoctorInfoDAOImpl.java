package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dto.DoctorDTO;
import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.dto.ViewForManagerDTO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.util.Page;
import org.apache.lucene.queryparser.classic.ParseException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
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
        		.findByNamedParam("select new com.hospitalsearch.dto.DoctorDTO(d.id, u.firstName, u.lastName, u.imagePath, d.specialization) from DoctorInfo d join d.userDetails u join d.departments dep where dep.id = :id ", "id", id);
    }

    @Override
    public List<DoctorSearchDTO> findByManagerAndHospitalId(Long hospitalId, Long managerId, ViewForManagerDTO viewForManagerDTO) {
        Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(DoctorInfo.class, "doctor")
                .createAlias("doctor.userDetails", "userDetail")
                .createAlias("userDetail.user", "user")
                .createAlias("doctor.departments", "department")
                .createAlias("department.hospital", "hospital")
                .createAlias("hospital.managers", "manager");
                filterCriteriaForPagination(viewForManagerDTO,criteria);
                setProjectionByHospitalAndManager(criteria,hospitalId, managerId);
                criteria.setResultTransformer(Transformers.aliasToBean(DoctorSearchDTO.class));
        return (List<DoctorSearchDTO>)criteria.list() ;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DoctorSearchDTO> searchDoctorsForManager(ViewForManagerDTO viewForManagerDTO, Long hospitalId, Long managerId) {
        Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(DoctorInfo.class, "doctor")
                .createAlias("doctor.userDetails", "userDetail")
                .createAlias("userDetail.user", "user")
                .createAlias("doctor.departments", "department")
                .createAlias("department.hospital", "hospital")
                .createAlias("hospital.managers", "manager");
        setProjectionByHospitalAndManager(criteria,hospitalId, managerId);
        criteria.add(Restrictions.like("doctor.specialization", viewForManagerDTO.getSpecialization().toString(), MatchMode.ANYWHERE).ignoreCase());
        if (viewForManagerDTO.getAllField() != null) {
            criteria.add(searchInAllFields(viewForManagerDTO.getAllField()));
            filterCriteriaForPagination(viewForManagerDTO, criteria);
            setProjectionByHospitalAndManager(criteria,hospitalId, managerId);
            criteria.setResultTransformer(Transformers.aliasToBean(DoctorSearchDTO.class));
            return (List<DoctorSearchDTO>) criteria.list();
        }
        criteria.add(searchInChosenField(viewForManagerDTO));
        filterCriteriaForPagination(viewForManagerDTO, criteria);
        setProjectionByHospitalAndManager(criteria,hospitalId, managerId);
        criteria.setResultTransformer(Transformers.aliasToBean(DoctorSearchDTO.class));
        return (List<DoctorSearchDTO>) criteria.list();
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

    private void setProjectionByHospitalAndManager(Criteria criteria, Long hospitalId, Long managerId){
        criteria .setProjection(Projections.projectionList()
                .add(Projections.property("doctor.id"), "doctorId")
                .add(Projections.property("userDetail.firstName"), "firstName")
                .add(Projections.property("userDetail.lastName"), "lastName")
                .add(Projections.property("user.email"), "email")
                .add(Projections.property("doctor.specialization"), "specialization")
                .add(Projections.property("doctor.category"), "category")
        )
                .add(Restrictions.eq("hospital.id",hospitalId))
                .add(Restrictions.eq("manager.id", managerId));
    }

    //search in all fields
    private Disjunction searchInAllFields(String value) {
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.ilike("user.email", value, MatchMode.ANYWHERE));
        disjunction.add(Restrictions.ilike("userDetail.firstName", value, MatchMode.ANYWHERE));
        disjunction.add(Restrictions.ilike("userDetail.lastName", value, MatchMode.ANYWHERE));
        disjunction.add(Restrictions.ilike("doctor.specialization", value.toLowerCase().toUpperCase(), MatchMode.ANYWHERE));
        return disjunction;
    }

    //search in all chosen fields
    private Disjunction searchInChosenField(ViewForManagerDTO viewForManagerDTO) {
        return Restrictions.or(Restrictions.like("user.email", viewForManagerDTO.getEmail(), MatchMode.ANYWHERE).ignoreCase(),
                Restrictions.like("userDetail.firstName", viewForManagerDTO.getFirstName(), MatchMode.ANYWHERE).ignoreCase(),
                Restrictions.like("userDetail.lastName", viewForManagerDTO.getLastName(), MatchMode.ANYWHERE).ignoreCase(),
                Restrictions.like("doctor.specialization", viewForManagerDTO.getSpecialization().toString(), MatchMode.ANYWHERE).ignoreCase());
    }

    /**
     * @param viewForManagerDTO
     * @param criteria      sorting field id in view must be the same to field of DoctorInfo class (or UserDetails and User with alias)
            *                      default sort - ASC by email
     */
    //get pagination
    private void filterCriteriaForPagination(ViewForManagerDTO viewForManagerDTO, Criteria criteria) {

       // get total pages
        Long countPages = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();

        //get total rows
        Integer totalPages = (int) Math.ceil( countPages / viewForManagerDTO.getPageSize()*1.0);
        if (totalPages < viewForManagerDTO.getCurrentPage()) {
            viewForManagerDTO.setCurrentPage(1);
        }
        //set total pages
        viewForManagerDTO.setTotalPage(totalPages);

        //get pagination
        criteria.setFirstResult((viewForManagerDTO.getCurrentPage() - 1) * viewForManagerDTO.getPageSize());
        criteria.setMaxResults(viewForManagerDTO.getPageSize());

        //get sort
        if (viewForManagerDTO.getAsc()) {
            criteria.addOrder(Order.asc(viewForManagerDTO.getSort()));
        } else {
            criteria.addOrder(Order.desc(viewForManagerDTO.getSort()));
        }
    }

    public DoctorInfo getByUserDetailId(Long userDetailId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("select d from DoctorInfo d where d.userDetails.id = :userDetailId");
        query.setParameter("userDetailId", userDetailId);
        return (DoctorInfo) query.uniqueResult();
    }
}
