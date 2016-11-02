package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.Laboratory;
import com.hospitalsearch.entity.Test;

@Component
//@Qualifier("DiagnosisPanelDAO")
public interface DiagnosisPanelDAO extends GenericDAO<DiagnosisPanel, Long> {

	List<DiagnosisPanel> getAll();

	DiagnosisPanel getById(long id);

	List<DiagnosisPanel> getByLaboratory(Laboratory laboratory);

	void update(DiagnosisPanel diagnosisPanel);

	void save(DiagnosisPanel diagnosisPanel);

	void delete(DiagnosisPanel diagnosisPanel);
	
}
