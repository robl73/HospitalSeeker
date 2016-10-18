package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.AdminTokenConfig;
import com.hospitalsearch.util.Token;

@Component
public interface AdminTokenConfigDAO extends GenericDAO<AdminTokenConfig, Long> {
	
	List<AdminTokenConfig> getAll();

	void update(AdminTokenConfig instance);

	void save(AdminTokenConfig instance);
	
	void delete(AdminTokenConfig instance);
	
	AdminTokenConfig getByToken(Token token);

}
