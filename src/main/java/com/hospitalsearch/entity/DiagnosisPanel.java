package com.hospitalsearch.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;

@Entity
@Table(name = "diagnosisPanel")
public class DiagnosisPanel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availabletest_gen")
	@SequenceGenerator(name = "availabletest_gen", sequenceName = "availabletest_gen", initialValue = 1, allocationSize = 1)
	private long id;
	
	@ManyToOne
	private Laboratory laboratory;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Laboratory getLaboratory() {
		return laboratory;
	}

	public void setLaboratory(Laboratory laboratory) {
		this.laboratory = laboratory;
	}
	
	@Override
	public String toString() {
		return "DiagnosisPanel: {" + "laboratory = " + laboratory + ", id = " + id + "}";
	}
	
}
