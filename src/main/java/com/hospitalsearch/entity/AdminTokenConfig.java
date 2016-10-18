package com.hospitalsearch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.hospitalsearch.util.Token;


@Entity
@Table(name = "admintokenconfig")
public class AdminTokenConfig {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@NotNull(message = "Token`s name cannot be empty")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "token")
	private Token token;

	@NotNull(message = "Token`s value cannot be empty")
	@Size(min = 1, max = 70, message = "Please enter at least 1 symbols and not more than 70 symbols.")
	@Column(name = "value")
	private String value;

	public AdminTokenConfig() {
	}

	public AdminTokenConfig(Token token, String value) {
		this.token = token;
		this.value = value;
	}

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "\ntoken = " + token + ", value = " + value;
	}

}