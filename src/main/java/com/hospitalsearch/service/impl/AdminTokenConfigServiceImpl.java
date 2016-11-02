package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.AdminTokenConfigDAO;
import com.hospitalsearch.entity.AdminTokenConfig;
import com.hospitalsearch.service.AdminTokenConfigService;
import com.hospitalsearch.util.Token;


@Service
public class AdminTokenConfigServiceImpl implements AdminTokenConfigService{

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
		configDAO.delete(adminTokenConfig);
	}
	
	@Override
	public Integer getByToken(Token token) {
		return Integer.valueOf(configDAO.getByToken(token).getValue());
	}

	@Override
	public Integer getVerificationToken() {
		return Integer.valueOf(configDAO.getByToken(Token.VERIFICATION_TOKEN_EXPIRATION).getValue());
	}

	@Override
	public Integer getResetPasswordToken() {
		return Integer.valueOf(configDAO.getByToken(Token.RESET_PASSWORD_TOKEN_EXPIRATION).getValue());
	}

	@Override
	public Integer getRememberMeToken() {
		return Integer.valueOf(configDAO.getByToken(Token.REMEMBER_ME_TOKEN_EXPIRATION).getValue());
	}

	
}
