package com.hospitalsearch.service.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.hospitalsearch.dto.AdminTokenConfigDTO;
import com.hospitalsearch.entity.AdminTokenConfig;
import com.hospitalsearch.util.Token;

public class TokensValidValidator implements ConstraintValidator<TokensValid, AdminTokenConfigDTO> {

	private Integer value_rememberMe;
	private Integer value_resetPassword;
	private Integer value_verification;
	private Integer value_file_max_size;

	@Override
	public void initialize(TokensValid constraintAnnotation) {
	}

	@Override
	public boolean isValid(AdminTokenConfigDTO configs, ConstraintValidatorContext context) {
		if (!isValidType(configs) || !isValidValue(configs)) {
			return false;
		}
		return true;
	}

	private boolean isValidType(AdminTokenConfigDTO configs) {
		for (AdminTokenConfig config : configs.getConfigs()) {
			if (config.getToken().equals(Token.REMEMBER_ME_TOKEN_EXPIRATION)
					|| config.getToken().equals(Token.RESET_PASSWORD_TOKEN_EXPIRATION)
					|| config.getToken().equals(Token.VERIFICATION_TOKEN_EXPIRATION)
					|| config.getToken().equals(Token.FILE_MAX_SIZE)) {
				try {
					Integer.parseInt(config.getValue().toString());
					if (config.getToken().equals(Token.REMEMBER_ME_TOKEN_EXPIRATION)) {
						value_rememberMe = Integer.parseInt(config.getValue());
					}
					if (config.getToken().equals(Token.RESET_PASSWORD_TOKEN_EXPIRATION)) {
						value_resetPassword = Integer.parseInt(config.getValue());
					}
					if (config.getToken().equals(Token.VERIFICATION_TOKEN_EXPIRATION)) {
						value_verification = Integer.parseInt(config.getValue());
					}
					if (config.getToken().equals(Token.FILE_MAX_SIZE)) {
						value_file_max_size = Integer.parseInt(config.getValue());
					}
					
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;

	}

	private boolean isValidValue(AdminTokenConfigDTO configs) {
		for (AdminTokenConfig config : configs.getConfigs()) {
			if (config.getToken().equals(Token.REMEMBER_ME_TOKEN_EXPIRATION)) {
				if (value_rememberMe < 24 || value_rememberMe > 168) {
					return false;
				}
			}
			if (config.getToken().equals(Token.RESET_PASSWORD_TOKEN_EXPIRATION)) {
				if (value_resetPassword < 12 || value_resetPassword > 48) {
					return false;
				}

			}
			if (config.getToken().equals(Token.VERIFICATION_TOKEN_EXPIRATION)) {
				if (value_verification < 24 || value_verification > 72) {
					return false;
				}
			}
			if (config.getToken().equals(Token.FILE_MAX_SIZE)) {
				if (value_file_max_size < 102400 || value_verification > 9999999) {
					return false;
				}
			}
		}
		return true;
	}

}