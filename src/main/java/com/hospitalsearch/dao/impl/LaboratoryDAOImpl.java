package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.LaboratoryDAO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.Laboratory;

@Repository
public class LaboratoryDAOImpl extends GenericDAOImpl<Laboratory, Long> implements LaboratoryDAO{

	@Autowired
	public LaboratoryDAOImpl(SessionFactory factory) {
		this.setSessionFactory(factory);
	}

	@Override
	public Laboratory getById(long id) {
		return super.getById(id);
	}

	@Override
	public Laboratory getByHospital(Hospital hospital) {
		String sql = "SELECT * FROM Laboratory where Hospital_id = " + hospital.getId();
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(Laboratory.class);
		return (Laboratory) query.uniqueResult();
	}

	@Override
	public void save(Laboratory laboratory) {
		super.save(laboratory);
	}
	
	@Override
	public List<Laboratory> getAll(){
		return super.getAll();
	}
	
	@Override
	public void update (Laboratory laboratory){
		super.update(laboratory);
	}
	
	@Override
	public void delete (Laboratory laboratory){
		super.delete(laboratory);
	}
	
	
}
