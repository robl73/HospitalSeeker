package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.Test;
import com.hospitalsearch.entity.DiagnosisPanel;
@Transactional
public interface TestService {

	List<Test> getAll();

	List<Test> getByPanel(DiagnosisPanel diagnosisPanel);

	void update(Test test);

	void save(Test test);

	void delete(Test test);

	List<Test> getById(long id);

}
