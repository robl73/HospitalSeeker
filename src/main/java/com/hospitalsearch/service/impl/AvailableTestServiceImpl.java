package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.AvailableTestDAO;
import com.hospitalsearch.entity.AvailableTest;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.service.AvailableTestService;

@Service
public class AvailableTestServiceImpl implements AvailableTestService{
	
	@Autowired
	AvailableTestDAO dao;

	@Override
	public List<AvailableTest> getAll() {
		return dao.getAll();
	}

	@Override
	public List<AvailableTest> getById(long id) {
		return dao.getById(id);
	}

	@Override
	public List<AvailableTest> getByPanel(DiagnosisPanel diagnosisPanel) {
		return dao.getByPanel(diagnosisPanel);
	}

	@Override
	public void update(AvailableTest availableTest) {
		dao.update(availableTest);
	}

	@Override
	public void save(AvailableTest availableTest) {
		dao.save(availableTest);
	}

	@Override
	public void delete(AvailableTest availableTest) {
		dao.delete(availableTest);
	}
	

}
