package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.TestDAO;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.Test;

@Repository
public class TestDAOImpl extends GenericDAOImpl<Test, Long> implements TestDAO{
	
	@Autowired
	public TestDAOImpl(SessionFactory factory) {
		super();
		this.setSessionFactory(factory);
	}

	@Override
	public Test getById(long id) {
		String sql = "SELECT * FROM Test where id = " + id;
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(Test.class);
		return (Test) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Test> getByPanel(DiagnosisPanel diagnosisPanel) {
		String sql = "SELECT * FROM Test where diagnosisPanel_id = " + diagnosisPanel.getId();
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(Test.class);
		return query.list();
	}
	
	@Override
	public List<Test> getAll(){
		return super.getAll();
	}
	
	@Override
	public void update(Test test){
		super.update(test);
	}
	
	@Override
	public void save (Test test){
		super.save(test);
	}
	
	@Override
	public void delete (Test test){
		super.delete(test);
	}

}
