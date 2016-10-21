package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.DiagnosisPanelInfoDAO;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.DiagnosisPanelInfo;
import com.hospitalsearch.entity.Language;
import com.hospitalsearch.service.DiagnosisPanelInfoService;

@Service
public class DiagnosisPanelInfoServiceImpl implements DiagnosisPanelInfoService{
	
	@Autowired//(required=true)
	DiagnosisPanelInfoDAO dao;

	@Override
	public List<DiagnosisPanelInfo> getAll() {
		return dao.getAll();
	}

	@Override
	public DiagnosisPanelInfo getById(long id) {
		return dao.getById(id);
	}

	@Override
	public List<DiagnosisPanelInfo> getByLanguage(Language language) {
		return dao.getByLanguage(language);
	}

	@Override
	public List<DiagnosisPanelInfo> getByDiagnosticPanel(DiagnosisPanel diagnosisPanel) {
		return dao.getByDiagnosticPanel(diagnosisPanel);
	}

	@Override
	public DiagnosisPanelInfo getByDiagnosticPanelAndLanguage(DiagnosisPanel diagnosisPanel, Language language) {
		return dao.getByDiagnosticPanelAndLanguage(diagnosisPanel, language);
	}

	@Override
	public void save(DiagnosisPanelInfo diagnosisPanelInfo) {
		dao.save(diagnosisPanelInfo);
	}

	@Override
	public void update(DiagnosisPanelInfo diagnosisPanelInfo) {
		dao.update(diagnosisPanelInfo);
	}

	@Override
	public void delete(DiagnosisPanelInfo diagnosisPanelInfo) {
		dao.delete(diagnosisPanelInfo);
	}
	
	

}
