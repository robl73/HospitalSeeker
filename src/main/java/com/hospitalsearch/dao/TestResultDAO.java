package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.TestResult;

@Component
public interface TestResultDAO extends GenericDAO<TestResult, Long>{

	List<TestResult> getAll();
	
	void update(TestResult testResult);
	
	void delete(TestResult testResult);
	
	void save(TestResult testResult);
	
	TestResult getById(long id);
	
}
