package com.hospitalsearch.entity;

import java.util.List;


import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.*;


@Entity
@Table(name = "department")
public class Department{

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
	@SequenceGenerator(name = "department_gen", sequenceName = "department_id_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngram"))
	private String name;

	@ManyToMany
	@JoinTable(name="DEPARTMENT_DOCTORINFO", joinColumns = @JoinColumn(name="DEPARTMENTS_ID"), inverseJoinColumns = @JoinColumn(name="DOCTORS_ID"))
	@Fetch(FetchMode.SUBSELECT)
	private List<DoctorInfo> doctors;

	@ManyToOne
	@ContainedIn
	private Hospital hospital;

	@Column(name="imagepath")
	private String imagePath;

	public Department() { 	}

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

	public List<DoctorInfo> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<DoctorInfo> doctors) {
		this.doctors = doctors;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

//	@Override
//	public String toString() {
//		return "Department{" +
//				"id=" + id +
//				", name='" + name + '\'' +
//				", doctors=" + doctors +
//				", hospital=" + hospital +
//				'}';
//	}
}
