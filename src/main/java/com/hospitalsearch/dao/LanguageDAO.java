package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.Language;

@Component
public interface LanguageDAO extends GenericDAO<Language, Long>{
	
	List<Language> getAll();
	
	Language getById(long id);
	
	void save(Language language);
	
	void update(Language language);
	
	void delete(Language language);

}
