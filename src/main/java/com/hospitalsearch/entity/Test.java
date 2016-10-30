package com.hospitalsearch.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "test")
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_gen")
	@SequenceGenerator(name = "test_gen", sequenceName = "test_gen", initialValue = 1, allocationSize = 1)
	private Long id;
	
	private String name;
	
	@ManyToOne
	private DiagnosisPanel diagnosisPanel;
	
	private String description;
	
	private String workSchedule;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWorkSchedule() {
		return workSchedule;
	}

	public void setWorkSchedule(String workSchedule) {
		this.workSchedule = workSchedule;
	}

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
		return "Test {" +
				"id=" + id +
				", name='" + name +
				", diagnosisPanel=" + diagnosisPanel +
				'}';
	}

}
