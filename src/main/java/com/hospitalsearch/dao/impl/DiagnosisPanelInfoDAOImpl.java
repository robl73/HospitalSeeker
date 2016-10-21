package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.DiagnosisPanelInfoDAO;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.DiagnosisPanelInfo;
import com.hospitalsearch.entity.Language;

@Repository
public class DiagnosisPanelInfoDAOImpl extends GenericDAOImpl<DiagnosisPanelInfo, Long> implements DiagnosisPanelInfoDAO{
	
	@Autowired
	public DiagnosisPanelInfoDAOImpl(SessionFactory factory) {
		super();
		this.setSessionFactory(factory);
	}

	@Override
	public DiagnosisPanelInfo getById(long id) {
		return super.getById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisPanelInfo> getByLanguage(Language language) {
		String sql = "SELECT * FROM DiagnosisPanelInfo where language_id = " + language.getId();
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(DiagnosisPanelInfo.class);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisPanelInfo> getByDiagnosticPanel(DiagnosisPanel diagnosisPanel) {
		String sql = "SELECT * FROM DiagnosisPanelInfo where diagnosisPanel_id = " + diagnosisPanel.getId();
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(DiagnosisPanelInfo.class);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public DiagnosisPanelInfo getByDiagnosticPanelAndLanguage(DiagnosisPanel diagnosisPanel, Language language) {
		String sql = "SELECT * FROM DiagnosisPanelInfo where diagnosisPanel_id = " + diagnosisPanel.getId() + "and language_id = " + language.getId();
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(DiagnosisPanelInfo.class);
		return (DiagnosisPanelInfo) query.uniqueResult();
	}
	
	@Override
	public void save(DiagnosisPanelInfo diagnosisPanelInfo){
		super.save(diagnosisPanelInfo);
	}
	
	@Override
	public void update(DiagnosisPanelInfo diagnosisPanelInfo){
		super.update(diagnosisPanelInfo);
	}
	
	@Override
	public void delete(DiagnosisPanelInfo diagnosisPanelInfo){
		super.delete(diagnosisPanelInfo);
	}
	
	@Override
	public List<DiagnosisPanelInfo> getAll(){
		return super.getAll();
	}

}
