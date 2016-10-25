package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.DiagnosisPanelLocalizationDAO;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.DiagnosisPanelLocalization;
import com.hospitalsearch.entity.Language;
import com.hospitalsearch.service.DiagnosisPanelLocalizationService;

@Service
public class DiagnosisPanelLocalizationServiceImpl implements DiagnosisPanelLocalizationService{
	
	@Autowired
	DiagnosisPanelLocalizationDAO dao;

	@Override
	public List<DiagnosisPanelLocalization> getAll() {
		return dao.getAll();
	}

	@Override
	public DiagnosisPanelLocalization getById(long id) {
		return dao.getById(id);
	}

	@Override
	public List<DiagnosisPanelLocalization> getByLanguage(Language language) {
		return dao.getByLanguage(language);
	}

	@Override
	public List<DiagnosisPanelLocalization> getByDiagnosticPanel(DiagnosisPanel diagnosisPanel) {
		return dao.getByDiagnosticPanel(diagnosisPanel);
	}

	@Override
	public DiagnosisPanelLocalization getByDiagnosticPanelAndLanguage(DiagnosisPanel diagnosisPanel, Language language) {
		return dao.getByDiagnosticPanelAndLanguage(diagnosisPanel, language);
	}

	@Override
	public void save(DiagnosisPanelLocalization diagnosisPanelLocalization) {
		dao.save(diagnosisPanelLocalization);
	}

	@Override
	public void update(DiagnosisPanelLocalization diagnosisPanelLocalization) {
		dao.update(diagnosisPanelLocalization);
	}

	@Override
	public void delete(DiagnosisPanelLocalization diagnosisPanelLocalization) {
		dao.delete(diagnosisPanelLocalization);
	}
	
	

}
