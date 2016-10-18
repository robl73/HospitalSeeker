package com.hospitalsearch.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;


@Entity
@Table(name = "doctorinfo")
@Indexed
@AnalyzerDef(name = "ngramD",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = StopFilterFactory.class,params = {
                        @Parameter(name="ignoreCase",value="true")
                }),
                @TokenFilterDef(factory = NGramFilterFactory.class,params={
                        @Parameter(name="minGramSize",value="3"),
                        @Parameter(name="maxGramSize",value="10")
                })
        })

public class DoctorInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctorinfo_gen")
    @SequenceGenerator(name = "doctorinfo_gen", sequenceName = "doctorinfo_id_seq", initialValue = 1, allocationSize = 1)
    @DocumentId
    private Long id;

    private String specialization;

    private String category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userdetails_id")
    @Fetch(FetchMode.SELECT)
    @ContainedIn
    @JsonIgnore
    private UserDetail userDetails;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "doctors")
    private List<Department> departments;

    public DoctorInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

	public UserDetail getUserDetails() {
		return userDetails;
	}

    public void setUserDetails(UserDetail userDetails) {
		this.userDetails = userDetails;
	}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "DoctorInfo{" +
                "id=" + id +
                ", specialization='" + specialization + '\'' +
                ", category='" + category + '\'' +
                ", userDetails=" + userDetails +
                '}';
    }
}