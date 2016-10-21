package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.LaboratoryDAO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.Laboratory;
import com.hospitalsearch.service.LaboratoryService;

@Service
public class LaboratoryServiceImpl implements LaboratoryService{
	
	@Autowired
	LaboratoryDAO dao;

	@Override
	public List<Laboratory> getAll() {
		return dao.getAll();
	}

	@Override
	public Laboratory getByHospital(Hospital hospital) {
		return dao.getByHospital(hospital);
	}

	@Override
	public void update(Laboratory laboratory) {
		dao.update(laboratory);
	}

	@Override
	public void save(Laboratory laboratory) {
		dao.save(laboratory);
	}

	@Override
	public void delete(Laboratory laboratory) {
		dao.delete(laboratory);
	}

	@Override
	public Laboratory getById(long id) {
		return dao.getById(id);
	}

}
