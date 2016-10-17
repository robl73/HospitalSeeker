package com.hospitalsearch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.hospitalsearch.dto.NameDepartmensByHospitalDTO;
import com.hospitalsearch.dto.NameHospitalsByManagerDTO;
import org.apache.lucene.queryparser.classic.ParseException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.dto.Bounds;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.Page;


/**
 *
 * @author Pavlo Kuz edited by Oleksandr Mukonin,Pavlo Kuz and others
 */
@Repository
public class HospitalDAOImpl extends GenericDAOImpl<Hospital, Long> implements HospitalDAO {

    @Autowired
    public HospitalDAOImpl(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<Hospital> getAllByBounds(Bounds bounds) {
        org.hibernate.Query query = this.currentSession().getNamedQuery(Hospital.GET_LIST_BY_BOUNDS)
                .setDouble("nelat", bounds.getNorthEastLat())
                .setDouble("swlat", bounds.getSouthWestLat())
                .setDouble("nelng", bounds.getNorthEastLon())
                .setDouble("swlng", bounds.getSouthWestLon());
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo) {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(Hospital.class).createAlias("departments", "dep");
        Criterion nameCriterion = Restrictions.like("name", filterInfo.getName(), MatchMode.ANYWHERE);
        Criterion countryCriterion = Restrictions.like("dep.name", filterInfo.getCountry(), MatchMode.ANYWHERE);
        Criterion cityCriterion = Restrictions.like("address.city", filterInfo.getCity(), MatchMode.ANYWHERE);

        return criteria.add(Restrictions.and(nameCriterion, countryCriterion, cityCriterion)).list();
    }

    public static final String[] HOSPITAL_PROJECTION = new String[]{"name", "address.city", "address.street", "address.building", "description", "departments.name"};

    @Override
    public Page<Hospital> advancedHospitalSearch(String args) throws ParseException, InterruptedException {
        FullTextSession session = Search.getFullTextSession(this.getSessionFactory().openSession());
        session.createIndexer(Hospital.class).startAndWait();
        session.close();
        
        return new Page<Hospital>(getSessionFactory(),args,HOSPITAL_PROJECTION, Hospital.class);
    }

    @Override
    public List<Hospital> getAllHospitalsByManager(Long manager_id) {
        Criteria crit = this.getSessionFactory().getCurrentSession().createCriteria(Hospital.class);
        crit.createAlias("managers", "managers");
        crit.add(Restrictions.eq("managers.id", manager_id));
        return crit.list();
    }
}
