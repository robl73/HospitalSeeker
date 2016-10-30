package com.hospitalsearch.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "diagnosisPanel")
public class DiagnosisPanel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diagnosisPanel_gen")
	@SequenceGenerator(name = "diagnosisPanel_gen", sequenceName = "diagnosisPanel_gen", initialValue = 1, allocationSize = 1)
	private long id;
	
	@ManyToOne
	private Laboratory laboratory;
	
	@OneToMany(mappedBy="diagnosisPanel", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Test> tests;
	
	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

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
