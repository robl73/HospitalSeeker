package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dto.DoctorDTO;
import com.hospitalsearch.dto.UserFilterDTO;
import com.hospitalsearch.entity.PatientInfo;
import com.hospitalsearch.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.Transform;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Objects.nonNull;

/**
 * @author Andrew Jasinskiy
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {

    private final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Autowired
    public UserDAOImpl(SessionFactory factory) {
        this.setSessionFactory(factory);
    }

    @Override
    public Boolean emailExists(String email) {
        return nonNull(getByEmail(email));
    }

    public void updateUser(User user) {
        this.getSessionFactory().getCurrentSession().merge(user);
    }

    public void update(User user) {
        super.update(user);
        this.getSessionFactory().getCurrentSession().flush();
    }

    @Override
    public User getByEmail(String email) {
        try {
              logger.info("getUserByEmail email: " + email);
            Criteria criteria = this.getSessionFactory()
                    .getCurrentSession()
                    .createCriteria(User.class);
            criteria.add(Restrictions.eq("email", email));
            return (User) criteria.uniqueResult();
        } catch (Exception e) {
            logger.error("Error get user by email: " + email + e);
            throw e;
        }
    }

    @Override
    public void changeStatus(long id) {
        User user = super.getById(id);
        user.setEnabled(!user.getEnabled());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsers(UserFilterDTO userFilterDTO) {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(User.class);
        filterCriteriaByStatus(userFilterDTO, criteria);
        filterCriteriaForPagination(userFilterDTO, criteria);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> searchUser(UserFilterDTO userFilterDTO) {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(User.class);
        filterCriteriaByStatus(userFilterDTO, criteria);

        //choose role
        criteria.add(Restrictions.like("roles.type", userFilterDTO.getRole(), MatchMode.ANYWHERE));
        if (userFilterDTO.getAllField() != null) {
            criteria.add(searchInAllFields(userFilterDTO.getAllField()));
            filterCriteriaForPagination(userFilterDTO, criteria);
            return criteria.list();
        }
        criteria.add(searchInChosenField(userFilterDTO));
        filterCriteriaForPagination(userFilterDTO, criteria);
        return criteria.list();
    }

    //utilities methods
    //get all users by status
    private void filterCriteriaByStatus(UserFilterDTO userFilterDTO, Criteria criteria) {
        if (userFilterDTO.getStatus().equals("all")) {
            criteria.add(Restrictions.conjunction());
        } else {
            criteria.add(Restrictions.eq("enabled", Boolean.parseBoolean(userFilterDTO.getStatus())));
        }
        criteria.createAlias("userRoles", "roles");
        criteria.createAlias("userDetails", "detail");
    }

    //search in all fields
    private Disjunction searchInAllFields(String value) {
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.ilike("email", value, MatchMode.ANYWHERE));
        disjunction.add(Restrictions.ilike("detail.firstName", value, MatchMode.ANYWHERE));
        disjunction.add(Restrictions.ilike("detail.lastName", value, MatchMode.ANYWHERE));
        return disjunction;
    }

    //search in all chosen fields
    private Disjunction searchInChosenField(UserFilterDTO userFilterDTO) {
        return Restrictions.or(Restrictions.like("email", userFilterDTO.getEmail(), MatchMode.ANYWHERE).ignoreCase(),
                Restrictions.like("detail.firstName", userFilterDTO.getFirstName(), MatchMode.ANYWHERE).ignoreCase(),
                Restrictions.like("detail.lastName", userFilterDTO.getLastName(), MatchMode.ANYWHERE).ignoreCase());
    }

    /**
     * @param userFilterDTO
     * @param criteria      sorting field id in view must be the same to field of User class (or UserDetails with alias)
     *                      default sort - ASC by email
     */
    //get pagination
    private void filterCriteriaForPagination(UserFilterDTO userFilterDTO, Criteria criteria) {

        //get total pages
        Long countPages = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();

        //get total rows
        Integer totalPages = (int) Math.ceil((double) countPages / userFilterDTO.getPageSize());
        if (totalPages < userFilterDTO.getCurrentPage()) {
            userFilterDTO.setCurrentPage(1);
        }
        criteria.setProjection(null);

        //set total pages
        userFilterDTO.setTotalPage(totalPages);

        //cut repeating rows
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        //get pagination
        criteria.setFirstResult((userFilterDTO.getCurrentPage() - 1) * userFilterDTO.getPageSize());
        criteria.setMaxResults(userFilterDTO.getPageSize());

        //get sort
        if (userFilterDTO.getAsc()) {
            criteria.addOrder(Order.asc(userFilterDTO.getSort()));
        } else {
            criteria.addOrder(Order.desc(userFilterDTO.getSort()));
        }

    }

    //Illia
    @Override
    public List<User> getByRole(String role) {
        Criteria criteria = this.currentSession().createCriteria(User.class, "user")
                .createAlias("user.userRoles", "userRoles").add(Restrictions.eq("userRoles.type", role))
                .createAlias("user.userDetails", "details");
        return criteria.list();
    }

    @Override
    public List<User> getByRole(String role, int pageNumber, int pageSize, String sortBy, Boolean order) {
        Query query = getSessionFactory().getCurrentSession().createQuery("select user from PatientInfo p left join p.userDetail details left join details.user user order by " + sortBy + (order ? " asc" : " desc"));
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return (List<User>) query.list();
    }

    @Override
    public List<User> searchByRole(String role, String search, int pageNumber, int pageSize, String sortBy, Boolean order) {
        Criteria criteria;
        if (sortBy.equals("firstName")) {
            criteria = prepareSearchByRole(role, search, pageNumber, pageSize, "details.firstName", order);
        } else if (sortBy.equals("lastName")) {
            criteria = prepareSearchByRole(role, search, pageNumber, pageSize, "details.lastName", order);
        } else {
            criteria = prepareSearchByRole(role, search, pageNumber, pageSize, "user.email", order);
        }

        return criteria.list();
    }

    @Override
    public Long countOfUsersByRole(String role) {
        List list = getHibernateTemplate().find("select count(*) from com.hospitalsearch.entity.PatientInfo");
        return (Long) list.get(0);
    }

    @Override
    public Long countOfUsersByRole(String role, String search) {
        Criteria criteria = roleCriteria(role, search);
        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();
        return count;
    }

    private Criterion notNullCriterion() {
        Criterion criterion = Restrictions.and()
//                .add(Restrictions.isNotNull("details.patientInfo"))
                .add(Restrictions.isNotNull("details.firstName"))
                .add(Restrictions.isNotNull("details.lastName"));
        return criterion;
    }

    private Criteria roleCriteria(String role, String search) {
        Criteria criteria = this.currentSession().createCriteria(User.class, "user")
                .add(Restrictions.isNotNull("user.userDetails"))
                .createAlias("user.userRoles", "userRoles")
                .add(Restrictions.eq("userRoles.type", role))
                .add(notNullCriterion())
                .createAlias("user.userDetails", "details")
                .add(searchCriterion(search));
        return criteria;
    }

    private Criterion searchCriterion(String search) {
        Criterion criterion = Restrictions.or(
                Restrictions.ilike("user.email", search, MatchMode.ANYWHERE),
                Restrictions.ilike("details.firstName", search, MatchMode.ANYWHERE),
                Restrictions.ilike("details.lastName", search, MatchMode.ANYWHERE)
        );
        return criterion;
    }

    private Criteria roleCriteria(String role) {
        Criteria criteria = this.currentSession().createCriteria(PatientInfo.class, "patient")
                .createAlias("patient.userDetail", "details")
//                .createAlias("details.user", "user")
                .setProjection(Projections.projectionList()
                .add(Projections.property("user.id"), "id")
                .add(Projections.property("user.email"), "email")
                .add(Projections.property("user.password"), "password")
                .add(Projections.property("user.enabled"), "enabled")
                .add(Projections.property("user.userDetails"), "userDetails")
                .add(Projections.property("user.userRoles"), "userRoles"));
//        Criteria criteria = this.currentSession().createCriteria(User.class, "user")
//                .add(Restrictions.isNotNull("user.userDetails"))
//                .createAlias("user.userRoles", "userRoles")
//                .add(Restrictions.eq("userRoles.type", role))
//                .createAlias("user.userDetails", "details")
//                .add(notNullCriterion());
        return criteria;
    }

    private Criteria prepareGetByRole(String role, int pageNumber, int pageSize, String sortBy, Boolean order) {
        Criteria criteria;
        if (order) {
            criteria = roleCriteria(role)
                    .addOrder(Order.asc(sortBy));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
        } else {
            criteria = roleCriteria(role)
                    .addOrder(Order.desc(sortBy));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
        }
        return criteria;
    }

    private Criteria prepareSearchByRole(String role, String search, int pageNumber, int pageSize, String sortBy, Boolean order) {
        Criteria criteria;
        if (order) {
            criteria = roleCriteria(role)
                    .add(searchCriterion(search)
                    ).addOrder(Order.asc(sortBy));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
        } else {
            criteria = roleCriteria(role)
                    .add(searchCriterion(search)
                    ).addOrder(Order.desc(sortBy));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
        }
        return criteria;
    }
}