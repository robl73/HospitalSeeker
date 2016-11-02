package com.hospitalsearch.entity;

import java.io.Serializable;
import java.util.List;


import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.*;


@Entity
@Table(name = "department")
public class Department implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
	@SequenceGenerator(name = "department_gen", sequenceName = "department_id_seq", initialValue = 1, allocationSize = 1)
	private Long id;
        
        @Embedded
        @IndexedEmbedded
        private DepartmentInfo departmentinfo;

        public DepartmentInfo getDepartmentinfo() {
            return departmentinfo;
        }

        public void setDepartmentinfo(DepartmentInfo departmentinfo) {
            this.departmentinfo = departmentinfo;
        }

	private String name;


	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "DEPARTMENT_DOCTORINFO", joinColumns = {
			@JoinColumn(name = "DEPARTMENTS_ID", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "DOCTORS_ID",
					nullable = false, updatable = false) })
	private List<DoctorInfo> doctors;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
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
