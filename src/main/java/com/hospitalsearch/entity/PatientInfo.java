package com.hospitalsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "patientinfo")
public class PatientInfo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientinfo_gen")
	@SequenceGenerator(name = "patientinfo_gen", sequenceName = "patientinfo_id_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@OneToOne
	UserDetail userDetail;

	@OneToOne(cascade= CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="patientcard_id")
	@JsonIgnore
	private PatientCard patientCard;

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
}
