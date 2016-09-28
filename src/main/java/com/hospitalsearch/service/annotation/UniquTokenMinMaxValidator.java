package com.hospitalsearch.service.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.hospitalsearch.dto.AdminTokenConfigDTO;
import com.hospitalsearch.entity.AdminTokenConfig;
import com.hospitalsearch.entity.Token;

public class UniquTokenMinMaxValidator implements ConstraintValidator<UniquTokenMinMax, AdminTokenConfigDTO> {

	
	@Override
	public void initialize(UniquTokenMinMax constraintAnnotation) {
	}

	@Override
	public boolean isValid(AdminTokenConfigDTO configs, ConstraintValidatorContext context) {
		System.out.println("\nVALIDATOR FOR CONFIG.DTO");
		if(!isValidType(configs) || !isValidNumber(configs)){
			return false;
		}
		return true;
	}
	

	private boolean isValidType(AdminTokenConfigDTO configs){
		for (AdminTokenConfig config : configs.getConfigs()) {
			try{
				Integer.parseInt(config.getValue().toString());
			}catch (Exception e) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isValidNumber(AdminTokenConfigDTO configs){
		for (AdminTokenConfig config : configs.getConfigs()) {
			if (config.getToken().equals(Token.REMEMBER_ME_TOKEN_EXPIRATION)) {
				if (config.getValue() < 24 || config.getValue() > 168) {
					return false;
				}
			}
			if (config.getToken().equals(Token.RESET_PASSWORD_TOKEN_EXPIRATION)) {
				if (config.getValue() < 12 || config.getValue() > 48) {
					return false;
				}

			}
			if (config.getToken().equals(Token.VERIFICATION_TOKEN_EXPIRATION)) {
				if (config.getValue() < 24 || config.getValue() > 168) {
					return false;
				}
			}
		}
		return true;
	}
	

}
