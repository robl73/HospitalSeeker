package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.TestResultDAO;
import com.hospitalsearch.entity.TestResult;

@Repository
public class TestResultDAOImpl extends GenericDAOImpl<TestResult, Long> implements TestResultDAO{
	
	@Autowired
	public TestResultDAOImpl(SessionFactory factory) {
		this.setSessionFactory(factory);
	}

	@Override
	public List<TestResult> getAll(){
		return super.getAll();
	}
	
	@Override
	public void update(TestResult testResult){
		super.update(testResult);
	}
	
	@Override
	public void delete(TestResult testResult){
		super.delete(testResult);
	}
	
	@Override
	public void save(TestResult testResult){
		super.save(testResult);
	}
	
	@Override
	public TestResult getById(long id){
		return super.getById(id);
	}
}
