package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.CardItemDAO;
import com.hospitalsearch.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository ("cardItemDAO")
public class CardItemDAOImpl extends GenericDAOImpl<CardItem,Long> implements CardItemDAO {

    @Autowired
    public CardItemDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }


    @Override
    public List<CardItem> getCardItemList(User user, int pageNumber, int pageSize) {
        UserDetail userDetail = user.getUserDetails();
        Query patientQuery = getSessionFactory().getCurrentSession().createQuery("select patient from PatientInfo patient where patient.userDetail.id = :userDetailId");
        patientQuery.setParameter("userDetailId", userDetail.getId());
        PatientInfo patientInfo = (PatientInfo) patientQuery.uniqueResult();
        PatientCard patientCard = patientInfo.getPatientCard();
        Query cardItemQuery = getSessionFactory().getCurrentSession().createQuery("select item from CardItem item where item.patientCard.id = :patientCardId order by item.date desc");
        cardItemQuery.setParameter("patientCardId", patientCard.getId());
//        Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(CardItem.class, "item")
//                .add(Restrictions.eq("item.patientCard", patientCard))
//                .addOrder(Order.desc(" "));
        cardItemQuery.setFirstResult((pageNumber - 1) * pageSize);
        cardItemQuery.setMaxResults(pageSize);
        return cardItemQuery.list();
    }

    @Override
    public Long countOfItems(PatientCard patientCard) {
        Query countQuery = getSessionFactory().getCurrentSession().createQuery("select count(*) from CardItem item where item.patientCard.id = :patientCardId");
        countQuery.setParameter("patientCardId", patientCard.getId());
//        Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(CardItem.class,"item")
//                .add(Restrictions.eq("item.patientCard", patientCard));
//        criteria.setProjection(Projections.rowCount());
//        Long count = (Long) criteria.uniqueResult();
//        return count;
        return (Long) countQuery.uniqueResult();
    }
}
