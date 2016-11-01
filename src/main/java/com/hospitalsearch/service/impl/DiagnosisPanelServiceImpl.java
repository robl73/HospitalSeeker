package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.DiagnosisPanelDAO;
import com.hospitalsearch.dao.TestDAO;
import com.hospitalsearch.entity.DiagnosisPanel;
import com.hospitalsearch.entity.Laboratory;
import com.hospitalsearch.entity.Test;
import com.hospitalsearch.service.DiagnosisPanelService;

@Service
public class DiagnosisPanelServiceImpl implements DiagnosisPanelService{
	
	@Autowired(required=true)
	DiagnosisPanelDAO dao;
	
	@Autowired(required=true)
	TestDAO daoTest;

	@Override
	public List<DiagnosisPanel> getAll() {
		return dao.getAll();
	}

	@Override
	public DiagnosisPanel getById(long id) {
		return dao.getById(id);
	}

	@Override
	public List<DiagnosisPanel> getByLaboratory(Laboratory laboratory) {
		return dao.getByLaboratory(laboratory);
	}

	@Override
	public void update(DiagnosisPanel diagnosisPanel) {
		dao.update(diagnosisPanel);
	}

	@Override
	public void save(DiagnosisPanel diagnosisPanel) {
		dao.save(diagnosisPanel);
	}

	@Override
	public void delete(DiagnosisPanel diagnosisPanel) {
		dao.delete(diagnosisPanel);
	}

	@Override
	public List<Test> getTests(DiagnosisPanel diagnosisPanel) {
		return daoTest.getByPanel(diagnosisPanel);
	}
	
}
