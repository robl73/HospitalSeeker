package com.hospitalsearch.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "testresult")
public class TestResult {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private PatientCard patientCard;
	
	@ManyToOne
	private Test test;
	
	private LocalDate dateTest;
	
	private String result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PatientCard getPatientCard() {
		return patientCard;
	}

	public void setPatientCard(PatientCard patientCard) {
		this.patientCard = patientCard;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public LocalDate getDateTest() {
		return dateTest;
	}

	public void setDateTest(LocalDate dateTest) {
		this.dateTest = dateTest;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
