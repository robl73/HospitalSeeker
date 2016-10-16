package com.hospitalsearch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;


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
	@Digits(integer = 3, fraction = 0, message = "The value must only contain digits.")
	@Column(name = "value")
	private Integer value;

	public AdminTokenConfig() {
	}

	public AdminTokenConfig(Token token, Integer value) {
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

}
