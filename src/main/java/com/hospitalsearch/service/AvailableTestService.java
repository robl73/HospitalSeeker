package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.AvailableTest;
import com.hospitalsearch.entity.DiagnosisPanel;
@Transactional
public interface AvailableTestService {

	List<AvailableTest> getAll();

	List<AvailableTest> getByPanel(DiagnosisPanel diagnosisPanel);

	void update(AvailableTest availableTest);

	void save(AvailableTest availableTest);

	void delete(AvailableTest availableTest);

	List<AvailableTest> getById(long id);

}
