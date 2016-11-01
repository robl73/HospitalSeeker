package com.hospitalsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "patientinfo")
public class PatientInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientinfo_gen")
	@SequenceGenerator(name = "patientinfo_gen", sequenceName = "patientinfo_id_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@OneToOne
	@JoinColumn(name = "userdetail_id")
	@JsonIgnore
	UserDetail userDetail;

	@OneToOne(cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="patientcard_id")
	@JsonIgnore
	private PatientCard patientCard;

	@Column(name = "blood_group")
	private String bloodGroup;

	@Column(name="height")
	// @Pattern(regexp = "d{1,3}", message = "Not valid. Only numbers")
	private String height;

	@Column(name="weight")
	private String weight;

	@Column(name="eyeColor")
	private String eyeColor;

	@Column(name="hairColor")
	private String hairColor;

	@Column(name="allergies")
	private String allergies;

	@Column(name="currentMedication")
	private String currentMedication;

	@Column(name="heartProblems")
	private Boolean heartProblems;

	@Column(name="diabetes")
	private Boolean diabetes;

	@Column(name="epilepsy")
	private Boolean epilepsy;

	@Column(name="restrictions")
	private String restrictions;

	@OneToMany(mappedBy = "patientInfo", fetch = FetchType.EAGER)
	private List<RelativesInfo> relativesInfos;

	public PatientInfo(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public PatientInfo() {}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public PatientCard getPatientCard() {
		return patientCard;
	}

	public void setPatientCard(PatientCard patientCard) {
		this.patientCard = patientCard;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}

	public String getHairColor() {
		return hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getCurrentMedication() {
		return currentMedication;
	}

	public void setCurrentMedication(String currentMedication) {
		this.currentMedication = currentMedication;
	}

	public Boolean getHeartProblems() {
		return heartProblems;
	}

	public void setHeartProblems(Boolean heartProblems) {
		this.heartProblems = heartProblems;
	}

	public Boolean getDiabetes() {
		return diabetes;
	}

	public void setDiabetes(Boolean diabetes) {
		this.diabetes = diabetes;
	}

	public Boolean getEpilepsy() {
		return epilepsy;
	}

	public void setEpilepsy(Boolean epilepsy) {
		this.epilepsy = epilepsy;
	}

	public String getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}

	public List<RelativesInfo> getRelativesInfos() {
		return relativesInfos;
	}

	public void setRelativesInfos(List<RelativesInfo> relativesInfos) {
		this.relativesInfos = relativesInfos;
	}

}

