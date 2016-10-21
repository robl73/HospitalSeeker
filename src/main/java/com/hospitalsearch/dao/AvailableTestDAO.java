package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.AvailableTest;
import com.hospitalsearch.entity.DiagnosisPanel;

@Component
public interface AvailableTestDAO extends GenericDAO<AvailableTest, Long>{
	
	List<AvailableTest> getAll();
	
	List<AvailableTest> getByPanel(DiagnosisPanel diagnosisPanel);
	
	void update(AvailableTest availableTest);
	
	void save (AvailableTest availableTest);
	
	void delete (AvailableTest availableTest);

	List<AvailableTest> getById(long id);

}
