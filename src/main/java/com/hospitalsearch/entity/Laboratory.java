package com.hospitalsearch.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "laboratory")
public class Laboratory {

	@Id
	@GeneratedValue
	private long id;
	
	
	private String description;
	
	@OneToOne
	private Hospital hospital;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	@Override
	public String toString() {
		return "Laboratory{" + "id=" + id + ", hospital=" + hospital + "description" + description + "}";
	}
}
