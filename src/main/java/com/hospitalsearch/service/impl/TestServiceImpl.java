package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.TestDAO;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.Test;
import com.hospitalsearch.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	
	@Autowired
	TestDAO dao;

	@Override
	public List<Test> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Test> getById(long id) {
		return dao.getById(id);
	}

	@Override
	public List<Test> getByPanel(DiagnosisPanel diagnosisPanel) {
		return dao.getByPanel(diagnosisPanel);
	}

	@Override
	public void update(Test test) {
		dao.update(test);
	}

	@Override
	public void save(Test test) {
		dao.save(test);
	}

	@Override
	public void delete(Test test) {
		dao.delete(test);
	}
	

}
