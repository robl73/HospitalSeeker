package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.Test;
import com.hospitalsearch.entity.DiagnosisPanel;

@Component
public interface TestDAO extends GenericDAO<Test, Long>{
	
	List<Test> getAll();
	
	List<Test> getByPanel(DiagnosisPanel diagnosisPanel);
	
	void update(Test availableTest);
	
	void save (Test availableTest);
	
	void delete (Test availableTest);

	List<Test> getById(long id);

}
