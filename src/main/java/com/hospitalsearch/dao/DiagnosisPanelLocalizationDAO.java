package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.DiagnosisPanelLocalization;
import com.hospitalsearch.entity.Language;

@Component
public interface DiagnosisPanelLocalizationDAO extends GenericDAO<DiagnosisPanelLocalization, Long>{

	List<DiagnosisPanelLocalization> getAll();
	
	DiagnosisPanelLocalization getById(long id);
	
	List<DiagnosisPanelLocalization> getByLanguage(Language language);
	
	List<DiagnosisPanelLocalization> getByDiagnosticPanel(DiagnosisPanel diagnosisPanel);
	
	DiagnosisPanelLocalization getByDiagnosticPanelAndLanguage(DiagnosisPanel diagnosisPanel, Language language);
	
	void save(DiagnosisPanelLocalization diagnosisPanelLocalization);
	
	void update(DiagnosisPanelLocalization diagnosisPanelLocalization);
	
	void delete(DiagnosisPanelLocalization diagnosisPanelLocalization);
	
}
