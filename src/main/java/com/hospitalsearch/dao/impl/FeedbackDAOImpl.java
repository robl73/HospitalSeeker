package com.hospitalsearch.dao.impl;

import java.util.List;

import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.FeedbackStatus;
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
	public boolean isUserCreatedFeedback(Long producerId, Long consumerId) {
		Query query = getSessionFactory().getCurrentSession().createQuery("select count(*) from Feedback f where f.producer.id = :producerId and f.consumer.id = :consumerId");
		query.setParameter("producerId", producerId);
		query.setParameter("consumerId", consumerId);
		return ((Long) query.uniqueResult()) > 0;
	}

	@Override
	public List<Feedback> getFeedbacks(Long doctorId, int pageNumber, int pageSize) {
		Query query = getSessionFactory().getCurrentSession().createQuery("select f from Feedback f where f.consumer.id = :doctorId and f.status = 'OK' order by f.date desc");
		query.setParameter("doctorId", doctorId);
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public long countOfFeedbacksByConsumer(Long doctorId) {
		Query query = getSessionFactory().getCurrentSession().createQuery("select count(*) from Feedback f where f.consumer.id = :doctorId");
		query.setParameter("doctorId", doctorId);
		return (long) query.uniqueResult();
	}

	public List<Feedback> getFeedbacksByManager(Long managerId, int firstFeedback, int pageSize, FeedbackStatus[] statuses) {
		Query query = getSessionFactory().getCurrentSession().createQuery(
		        "select f from Feedback f " +
				"join f.consumer doc " +
				"join doc.departments dep " +
				"join dep.hospital h " +
                "join h.managers manager " +
				"where manager.id = :managerId " +
                "and f.status in :statuses " +
                "order by f.date ");
        query.setFirstResult(firstFeedback - 1);
        query.setMaxResults(pageSize);
        query.setParameter("managerId", managerId);
        query.setParameterList("statuses", statuses);
        return query.list();
	}

	@Override
	public List<Hospital> getHospitalsByManagerId(Long managerId) {
		Query query = getSessionFactory().getCurrentSession().createQuery("select h from Hospital h where managers.id = :managerId");
		return query.list();
	}

	@Override
	public void changeStatus(Long feedbackId, FeedbackStatus status) {
		Query query = getSessionFactory().getCurrentSession().createQuery("update Feedback f set f.status = :status where f.id = :feedbackId");
		query.setParameter("feedbackId", feedbackId);
		query.setParameter("status", status);
		query.executeUpdate();
	}

}
