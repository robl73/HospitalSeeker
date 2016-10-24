package com.hospitalsearch.dao.impl;

import java.util.List;

import com.hospitalsearch.entity.DoctorInfo;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.FeedbackDAO;
import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;

@Repository
public class FeedbackDAOImpl extends GenericDAOImpl<Feedback, Long> implements FeedbackDAO {

	@Autowired
	public FeedbackDAOImpl(SessionFactory factory) {
		this.setSessionFactory(factory);
	}

	@Override
	public List<Feedback> getByDoctorId(Long id) {
		return (List<Feedback>) getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Feedback.class).add(Restrictions.eq("consumer.id", id)).addOrder(Order.desc("date")));
	}

	@Override
	public User getByUserEmail(String email) {
		return (User) getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(User.class).add(Restrictions.eq("email", email))).get(0);
	}

	@Override
	public Feedback getByProducer(User user) {
		return (Feedback) getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Feedback.class).add(Restrictions.eq("producer.id", user.getId()))).get(0);
	}

	@Override
	public boolean isUserCreatedFeedback(Long producerId, Long consumerId) {
		Query query = getSessionFactory().getCurrentSession().createQuery("select count(*) from Feedback f where f.producer.id = :producerId and f.consumer.id = :consumerId");
		query.setParameter("producerId", producerId);
		query.setParameter("consumerId", consumerId);
		return ((Long) query.uniqueResult()) > 0;
	}

	@Override
	public List<Feedback> getFeedbacks(Long doctorId, int pageNumber, int pageSize) {
		Query query = getSessionFactory().getCurrentSession().createQuery("select f from Feedback f where f.consumer.id = :doctorId order by f.date");
		query.setParameter("doctorId", doctorId);
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

}
