package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.LanguageDAO;
import com.hospitalsearch.entity.Language;

@Repository
public class LanguageDAOImpl extends GenericDAOImpl<Language, Long> implements LanguageDAO{

	@Autowired
	public LanguageDAOImpl(SessionFactory factory) {
		this.setSessionFactory(factory);
	}

	@Override
	public Language getById(long id) {
		return super.getById(id);
	}

	@Override
	public List<Language> getAll(){
		return super.getAll();
	}
	
	@Override
	public void save(Language language){
		super.save(language);
	}
	
	@Override
	public void update(Language language){
		super.update(language);
	}
	
	@Override
	public void delete(Language language){
		super.delete(language);
	}
	
}
