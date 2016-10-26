package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.DiagnosisPanelLocalizationDAO;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.DiagnosisPanelLocalization;
import com.hospitalsearch.entity.Language;

@Repository
public class DiagnosisPanelLocalizationDAOImpl extends GenericDAOImpl<DiagnosisPanelLocalization, Long> implements DiagnosisPanelLocalizationDAO{
	
	@Autowired
	public DiagnosisPanelLocalizationDAOImpl(SessionFactory factory) {
		super();
		this.setSessionFactory(factory);
	}

	@Override
	public DiagnosisPanelLocalization getById(long id) {
		return super.getById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisPanelLocalization> getByLanguage(Language language) {
		String sql = "SELECT * FROM DiagnosisPanelLocalization where language_id = " + language.getId();
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(DiagnosisPanelLocalization.class);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisPanelLocalization> getByDiagnosticPanel(DiagnosisPanel diagnosisPanel) {
		String sql = "SELECT * FROM DiagnosisPanelLocalization where diagnosisPanel_id = " + diagnosisPanel.getId();
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(DiagnosisPanelLocalization.class);
		return query.list();
	}

	@Override
	public DiagnosisPanelLocalization getByDiagnosticPanelAndLanguage(DiagnosisPanel diagnosisPanel, Language language) {
		String sql = "SELECT * FROM DiagnosisPanelLocalization where diagnosisPanel_id = " + diagnosisPanel.getId() + " and language_id = " + language.getId();
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(DiagnosisPanelLocalization.class);
		return (DiagnosisPanelLocalization) query.uniqueResult();
	}

	
	@Override
	public void save(DiagnosisPanelLocalization diagnosisPanelLocalization){
		super.save(diagnosisPanelLocalization);
	}
	
	@Override
	public void update(DiagnosisPanelLocalization diagnosisPanelLocalization){
		super.update(diagnosisPanelLocalization);
	}
	
	@Override
	public void delete(DiagnosisPanelLocalization diagnosisPanelLocalization){
		super.delete(diagnosisPanelLocalization);
	}
	
	@Override
	public List<DiagnosisPanelLocalization> getAll(){
		return super.getAll();
	}

}
