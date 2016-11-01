package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.TestResultDAO;
import com.hospitalsearch.entity.TestResult;
import com.hospitalsearch.service.TestResultService;

@Service
public class TestResultServiceImpl implements TestResultService{
	
	@Autowired
	private TestResultDAO dao;

	@Override
	public List<TestResult> getAll() {
		return dao.getAll();
	}

	@Override
	public void update(TestResult testResult) {
		dao.update(testResult);
	}

	@Override
	public void delete(TestResult testResult) {
		dao.delete(testResult);
	}

	@Override
	public void save(TestResult testResult) {
		dao.save(testResult);
	}

	@Override
	public TestResult getById(long id) {
		return dao.getById(id);
	}
	
}
