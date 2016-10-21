package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.DiagnosisPanelInfo;
import com.hospitalsearch.entity.Language;

@Component
public interface DiagnosisPanelInfoDAO extends GenericDAO<DiagnosisPanelInfo, Long>{

	List<DiagnosisPanelInfo> getAll();
	
	DiagnosisPanelInfo getById(long id);
	
	List<DiagnosisPanelInfo> getByLanguage(Language language);
	
	List<DiagnosisPanelInfo> getByDiagnosticPanel(DiagnosisPanel diagnosisPanel);
	
	DiagnosisPanelInfo getByDiagnosticPanelAndLanguage(DiagnosisPanel diagnosisPanel, Language language);
	
	void save(DiagnosisPanelInfo diagnosisPanelInfo);
	
	void update(DiagnosisPanelInfo diagnosisPanelInfo);
	
	void delete(DiagnosisPanelInfo diagnosisPanelInfo);
	
}
