package com.hospitalsearch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "diagnosispanellocalization")
public class DiagnosisPanelLocalization {
	
	@Id
	private long id;
	
	@ManyToOne
	private DiagnosisPanel diagnosisPanel;
	
	@ManyToOne
	private Language language;
	
	private String name;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DiagnosisPanel getDiagnosisPanel() {
		return diagnosisPanel;
	}

	public void setDiagnosisPanel(DiagnosisPanel diagnosisPanel) {
		this.diagnosisPanel = diagnosisPanel;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "DiagnosisPanelLocalization: {name = " + name + ", language = " + language + "}";
	}

}
