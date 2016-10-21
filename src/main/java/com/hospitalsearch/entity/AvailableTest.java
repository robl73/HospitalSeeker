package com.hospitalsearch.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "availabletest")
public class AvailableTest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availabletest_gen")
	@SequenceGenerator(name = "availabletest_gen", sequenceName = "availabletest_gen", initialValue = 1, allocationSize = 1)
	private Long id;
	
	private String name;
	
	@ManyToOne
	private DiagnosisPanel diagnosisPanel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DiagnosisPanel getDiagnosisPanel() {
		return diagnosisPanel;
	}

	public void setDiagnosisPanel(DiagnosisPanel diagnosisPanel) {
		this.diagnosisPanel = diagnosisPanel;
	}
	
	@Override
	public String toString() {
		return "AvailableTest {" +
				"id=" + id +
				", name='" + name +
				", diagnosisPanel=" + diagnosisPanel +
				'}';
	}

}
