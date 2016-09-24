package com.hospitalsearch.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Andrew Jasinskiy on 23.06.16
 */

/*
 @NamedQueries({
@NamedQuery(query = AdminTokenConfigDTO.GET_CONFIG_BY_ID, name = "GET_CONFIG_BY_ID")
// @NamedQuery(name = "GET_RESET_PASSWORD_TOKEN_BY_USER", query = "SELECT p FROM PasswordResetToken p WHERE p.user = :user"),
})
*/
@Entity
@Table(name = "adminTokenConfig")
public class AdminTokenConfig {

	// public static final String GET_CONFIG_BY_ID = "SELECT t FROM adminTokenConfig t WHERE t.id = :id";
	// public static String GET_CONFIG_BY_ID = "SELECT t FROM adminTokenConfig WHERE t.id = :id";
	//from Hospital h where 

		@Id
		@GeneratedValue
		@Column(name = "id")
		private Long id;
		
		@NotNull(message = "Token`s name cannot be empty")
		@Enumerated(value = EnumType.STRING)
		@Column(name = "token")
		private Token token;
		
		@NotNull(message = "Token`s value cannot be empty")
		@Digits(integer = 3, fraction = 0, message = "The value must only contain digits.")
		@Min(value = 24, message = "Token should be at least 24 hours")
		@Max(value = 168, message = "Maximum token duration is 168 hours")
		@Column(name = "value")
		private Integer value;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Token getToken() {
			return token;
		}

		public void setToken(Token token) {
			this.token = token;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return "\ntoken = " + token + ", value = " + value;
		}
		

	/*	@NotNull(message = "Token cannot be empty")
		@Digits(integer = 3, fraction = 0, message = "The value must only contain digits.")
		@Min(value = 24, message = "Token should be at least 24 hours")
		@Max(value = 168, message = "Maximum token duration is 168 hours")
		@Column(name = "resetPasswordToken")
		private Integer resetPasswordToken;

		@NotNull(message = "Token cannot be empty")
		@Digits(integer = 3, fraction = 0, message = "The value must only contain digits.")
		@Min(value = 24, message = "Token should be at least 24 hours")
		@Max(value = 168, message = "Maximum token duration is 168 hours")
		@Column(name = "verificationToken")
		private Integer verificationToken;

		@NotNull(message = "Token cannot be empty")
		@Digits(integer = 3, fraction = 0, message = "The value must only contain digits.")
		@Min(value = 24, message = "Token should be at least 24 hours")
		@Max(value = 168, message = "Maximum token duration is 168 hours")
		@Column(name = "rememberMeToken")
		private Integer rememberMeToken;
*/
		/*
		 * public AdminTokenConfig() { super(); this.resetPasswordToken =
		 * RESET_PASSWORD_TOKEN_EXPIRATION; this.verificationToken =
		 * VERIFICATION_TOKEN_EXPIRATION; this.rememberMeToken =
		 * REMEMBER_ME_TOKEN_EXPIRATION; }
		 */
	
		//TODO constructor 
		/*public AdminTokenConfig(){
			this.rememberMeToken = configService.getById(1).
		}
		*/

	/*	public int getResetPasswordToken() {
			return resetPasswordToken;
		}

		public void setResetPasswordToken(int resetPasswordToken) {
			this.resetPasswordToken = resetPasswordToken;
		}

		public int getVerificationToken() {
			return verificationToken;
		}

		public void setVerificationToken(int verificationToken) {
			this.verificationToken = verificationToken;
		}

		public int getRememberMeToken() {
			return rememberMeToken;
		}

		public void setRememberMeToken(int rememberMeToken) {
			this.rememberMeToken = rememberMeToken;
		}

		@Override
		public String toString() {
			return "AdminTokenConfig{" + "resetPasswordToken=" + resetPasswordToken + ", verificationToken="
					+ verificationToken + ", rememberMeToken=" + rememberMeToken + '}';
		}
		*/
	}

	
