package com.hospitalsearch.entity;

import java.util.List;


import javax.persistence.*;
import org.hibernate.search.annotations.*;

/**
 * Annotations @Field
 * is used only for hibernate search
 * */
@Entity
@Table(name = "department")
public class Department{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
	@SequenceGenerator(name = "department_gen", sequenceName = "department_id_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngram")) //annotation for hibernate search
	private String name;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="department_doctorinfo", joinColumns={@JoinColumn(name="departments_id")},
		inverseJoinColumns={@JoinColumn(name="doctors_id")})
	private List<DoctorInfo> doctors;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hospital_id")
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

	@Override
	public String toString() {
		return "Department{" +
				"name='" + name + '\'' +
				'}';
	}
}
