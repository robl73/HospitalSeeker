package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.Language;

@Transactional
public interface LanguageService {

	List<Language> getAll();

	Language getById(long id);

	void save(Language language);

	void update(Language language);

	void delete(Language language);

}
