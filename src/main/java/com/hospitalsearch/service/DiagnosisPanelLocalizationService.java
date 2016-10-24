package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.DiagnosisPanelLocalization;
import com.hospitalsearch.entity.Language;

@Transactional
public interface DiagnosisPanelLocalizationService {

	List<DiagnosisPanelLocalization> getAll();

	DiagnosisPanelLocalization getById(long id);

	List<DiagnosisPanelLocalization> getByLanguage(Language language);

	List<DiagnosisPanelLocalization> getByDiagnosticPanel(DiagnosisPanel diagnosisPanel);

	DiagnosisPanelLocalization getByDiagnosticPanelAndLanguage(DiagnosisPanel diagnosisPanel, Language language);

	void save(DiagnosisPanelLocalization diagnosisPanelLocalization);

	void update(DiagnosisPanelLocalization diagnosisPanelLocalization);

	void delete(DiagnosisPanelLocalization diagnosisPanelLocalization);
}
