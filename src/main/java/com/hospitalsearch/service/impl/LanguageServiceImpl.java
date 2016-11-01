package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.LanguageDAO;
import com.hospitalsearch.entity.Language;
import com.hospitalsearch.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService{
	
	@Autowired
	LanguageDAO dao;

	@Override
	public List<Language> getAll() {
		return dao.getAll();
	}

	@Override
	public Language getById(long id) {
		return dao.getById(id);
	}

	@Override
	public void save(Language language) {
		dao.save(language);
	}

	@Override
	public void update(Language language) {
		dao.update(language);
	}

	@Override
	public void delete(Language language) {
		dao.delete(language);
	}
	

}
