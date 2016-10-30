package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.Laboratory;
import com.hospitalsearch.entity.Test;

@Transactional
public interface DiagnosisPanelService {
	
	List<DiagnosisPanel> getAll();

	DiagnosisPanel getById(long id);

	List<DiagnosisPanel> getByLaboratory(Laboratory laboratory);

	void update(DiagnosisPanel diagnosisPanel);

	void save(DiagnosisPanel diagnosisPanel);

	void delete(DiagnosisPanel diagnosisPanel);
	
	List<Test> getTests(DiagnosisPanel diagnosisPanel);

}
