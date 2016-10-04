package com.hospitalsearch.dto;

import java.util.List;

import com.hospitalsearch.entity.AdminTokenConfig;
import com.hospitalsearch.service.annotation.TokensValid;

@TokensValid(message = "Tokens should be only numbers in valid margin. Tokens are not update")
public class AdminTokenConfigDTO {

	private List<AdminTokenConfig> configs;
	

	public List<AdminTokenConfig> getConfigs() {
		return configs;
	}
	

	public void setConfigs(List<AdminTokenConfig> configs) {
		this.configs = configs;
	}
	
	
	@Override
	public String toString() {
		String str = "";
		for (AdminTokenConfig config : configs){
			str += config.toString();
		}
		return "\n" + str + " ";
	}
}
