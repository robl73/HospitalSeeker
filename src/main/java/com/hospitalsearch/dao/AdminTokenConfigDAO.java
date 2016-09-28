package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.AdminTokenConfig;
import com.hospitalsearch.entity.Token;



@Component
public interface AdminTokenConfigDAO extends GenericDAO<AdminTokenConfig, Long> {
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	List<AdminTokenConfig> getAll();

	void update(AdminTokenConfig instance);

	void save(AdminTokenConfig instance);
	
	void delete(AdminTokenConfig instance);
	
	AdminTokenConfig getByToken(Token token);

}
