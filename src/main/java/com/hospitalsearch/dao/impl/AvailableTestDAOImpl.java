package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.AvailableTestDAO;
import com.hospitalsearch.entity.AvailableTest;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.User;

@Repository
public class AvailableTestDAOImpl extends GenericDAOImpl<AvailableTest, Long> implements AvailableTestDAO{
	
	@Autowired
	public AvailableTestDAOImpl(SessionFactory factory) {
		super();
		this.setSessionFactory(factory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AvailableTest> getById(long id) {
		String sql = "SELECT * FROM AvailableTest where id = " + id;
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(AvailableTest.class);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AvailableTest> getByPanel(DiagnosisPanel diagnosisPanel) {
		String sql = "SELECT * FROM AvailableTest where diagnosisPanel_id = " + diagnosisPanel.getId();
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(AvailableTest.class);
		return query.list();
		//return (List<AvailableTest>) getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(AvailableTest.class).add(Restrictions.eq("diagnosisPanel_id", diagnosisPanel.getId())));
	}
	
	@Override
	public List<AvailableTest> getAll(){
		return super.getAll();
	}
	
	@Override
	public void update(AvailableTest availableTest){
		super.update(availableTest);
	}
	
	@Override
	public void save (AvailableTest availableTest){
		super.save(availableTest);
	}
	
	@Override
	public void delete (AvailableTest availableTest){
		super.delete(availableTest);
	}

}
