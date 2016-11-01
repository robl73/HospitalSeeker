package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.DiagnosisPanelDAO;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.Laboratory;
import com.hospitalsearch.entity.Test;

@Repository
public class DiagnosisPanelDAOImpl extends GenericDAOImpl<DiagnosisPanel, Long> implements DiagnosisPanelDAO{
	
	@Autowired
	public DiagnosisPanelDAOImpl(SessionFactory factory) {
		this.setSessionFactory(factory);
	}
	
	@Override
	public List<DiagnosisPanel> getAll(){
		return super.getAll();
	}

	@Override
	public DiagnosisPanel getById(long id){
		return super.getById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisPanel> getByLaboratory(Laboratory laboratory){
		String sql = "SELECT * FROM DiagnosisPanel where laboratory_id = " + laboratory.getId();
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(DiagnosisPanel.class);
		return query.list();
	}

	@Override
	public void update(DiagnosisPanel diagnosisPanel){
		super.update(diagnosisPanel);
	}

	@Override
	public void save(DiagnosisPanel diagnosisPanel){
		super.save(diagnosisPanel);
	}

	@Override
	public void delete(DiagnosisPanel diagnosisPanel){
		super.delete(diagnosisPanel);
	}

}
