package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.Laboratory;

@Component
public interface LaboratoryDAO extends GenericDAO<Laboratory, Long>{

	List<Laboratory> getAll();
	
	Laboratory getById(long id);
	
	Laboratory getByHospital(Hospital hospital);
	
	void update (Laboratory laboratory);
	
	void save(Laboratory laboratory);
	
	void delete (Laboratory laboratory);
	
	
}
