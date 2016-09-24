package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.AdminTokenConfig;

import antlr.Token;

@Transactional
public interface AdminTokenConfigService {

	/*
	 * @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	 * AdminTokenConfig getTokens();
	 * 
	 * Integer VERIFICATION_TOKEN_EXPIRATION();
	 * 
	 * Integer RESET_PASSWORD_TOKEN_EXPIRATION();
	 * 
	 * Integer REMEMBER_ME_TOKEN_EXPIRATION();
	 * 
	 * void updateTokenConfig(Integer resetPasswordToken, Integer
	 * verificationToken, Integer rememberMeToken);
	 */

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	List<AdminTokenConfig> getAll();

	void update(AdminTokenConfig adminTokenConfig);

	void add(AdminTokenConfig adminTokenConfig);

	void delete(AdminTokenConfig adminTokenConfig);

	Integer VERIFICATION_TOKEN_EXPIRATION();

	Integer RESET_PASSWORD_TOKEN_EXPIRATION();

	Integer REMEMBER_ME_TOKEN_EXPIRATION();

}
