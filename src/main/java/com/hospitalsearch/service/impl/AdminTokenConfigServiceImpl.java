package com.hospitalsearch.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.AdminTokenConfigDAO;
import com.hospitalsearch.entity.AdminTokenConfig;
import com.hospitalsearch.entity.Token;
import com.hospitalsearch.service.AdminTokenConfigService;


@Service
public class AdminTokenConfigServiceImpl implements AdminTokenConfigService{

	 private final Logger logger = LogManager.getLogger(VerificationTokenServiceImpl.class);
	
    @Autowired
    private AdminTokenConfigDAO configDAO;

	@Override
	public List<AdminTokenConfig> getAll() {
		return configDAO.getAll();
	}

	@Override
	public void update(AdminTokenConfig adminTokenConfig) {
		configDAO.update(adminTokenConfig);
	}

	@Override
	public void add(AdminTokenConfig adminTokenConfig) {
		configDAO.save(adminTokenConfig);
	}

	@Override
	public void delete(AdminTokenConfig adminTokenConfig) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public AdminTokenConfig getByToken(Token token) {
		return configDAO.getByToken(token);
	}

	

	@Override
	public Integer VERIFICATION_TOKEN_EXPIRATION() {
		return configDAO.getByToken(Token.VERIFICATION_TOKEN_EXPIRATION).getValue();
	}

	@Override
	public Integer RESET_PASSWORD_TOKEN_EXPIRATION() {
		return configDAO.getByToken(Token.RESET_PASSWORD_TOKEN_EXPIRATION).getValue();
	}

	@Override
	public Integer REMEMBER_ME_TOKEN_EXPIRATION() {
		return configDAO.getByToken(Token.REMEMBER_ME_TOKEN_EXPIRATION).getValue();
	}


}
