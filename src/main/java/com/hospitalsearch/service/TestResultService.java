package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.TestResult;

@Transactional
public interface TestResultService {
	
List<TestResult> getAll();
	
	void update(TestResult testResult);
	
	void delete(TestResult testResult);
	
	void save(TestResult testResult);
	
	TestResult getById(long id);

}
