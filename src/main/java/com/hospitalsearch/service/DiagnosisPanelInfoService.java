package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.DiagnosisPanelInfo;
import com.hospitalsearch.entity.Language;

@Transactional
public interface DiagnosisPanelInfoService {

	List<DiagnosisPanelInfo> getAll();

	DiagnosisPanelInfo getById(long id);

	List<DiagnosisPanelInfo> getByLanguage(Language language);

	List<DiagnosisPanelInfo> getByDiagnosticPanel(DiagnosisPanel diagnosisPanel);

	DiagnosisPanelInfo getByDiagnosticPanelAndLanguage(DiagnosisPanel diagnosisPanel, Language language);

	void save(DiagnosisPanelInfo diagnosisPanelInfo);

	void update(DiagnosisPanelInfo diagnosisPanelInfo);

	void delete(DiagnosisPanelInfo diagnosisPanelInfo);
}
