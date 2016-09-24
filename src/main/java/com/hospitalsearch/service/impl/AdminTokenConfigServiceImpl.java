package com.hospitalsearch.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.AdminTokenConfigDAO;
import com.hospitalsearch.entity.AdminTokenConfig;
import com.hospitalsearch.service.AdminTokenConfigService;

import antlr.Token;

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
	public Integer VERIFICATION_TOKEN_EXPIRATION() {
		// TODO !!!!! if token_name equalse VERIFICATION_TOKEN_EXPIRATION - return it
		return null;
	}

	@Override
	public Integer RESET_PASSWORD_TOKEN_EXPIRATION() {
		// TODO !!!!! if token_name equalse VERIFICATION_TOKEN_EXPIRATION - return it
		return null;
	}

	@Override
	public Integer REMEMBER_ME_TOKEN_EXPIRATION() {
		// TODO !!!!! if token_name equalse VERIFICATION_TOKEN_EXPIRATION - return it
		return null;
	}


	
	
/*
	@Override
	public AdminTokenConfig getTokens() {
		return configDAO.getTokens();
	}
	
	@Override
	public void updateTokenConfig(Integer resetPasswordToken, Integer verificationToken, Integer rememberMeToken) {
		configDAO.updateTokenConfig(resetPasswordToken, verificationToken, rememberMeToken);
	}

	@Override
	public Integer VERIFICATION_TOKEN_EXPIRATION(){
		AdminTokenConfig adminTokenConfig = configDAO.getTokens();
		return adminTokenConfig.getVerificationToken();
	}

	@Override
	public Integer RESET_PASSWORD_TOKEN_EXPIRATION() {
		AdminTokenConfig adminTokenConfig = configDAO.getTokens();
		return adminTokenConfig.getResetPasswordToken();
	}

	@Override
	public Integer REMEMBER_ME_TOKEN_EXPIRATION() {
		AdminTokenConfig adminTokenConfig = configDAO.getTokens();
		return adminTokenConfig.getRememberMeToken();
	}

/*	@Override
	public void delete() {
		configDAO.delete();
	}*/
	
/*	public void add(Integer resetPasswordToken, Integer verificationToken, Integer rememberMeToken){
		configDAO.add(resetPasswordToken, verificationToken, rememberMeToken);
	}*/
    
    
    
}
