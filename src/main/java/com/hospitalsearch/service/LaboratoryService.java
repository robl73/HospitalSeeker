package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.Laboratory;

@Transactional
public interface LaboratoryService {

    List<Laboratory> getAll();
	
	Laboratory getById(long id);
	
	Laboratory getByHospital(Hospital hospital);
	
	void update (Laboratory laboratory);
	
	void save(Laboratory laboratory);
	
	void delete (Laboratory laboratory);
	
}
