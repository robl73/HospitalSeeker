package com.hospitalsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Vladimir Paladiuk on 13.10.2016.
 */
@Entity
@Table(name = "relativesInfo")
public class RelativesInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "relativesInfo_gen")
    @SequenceGenerator(name = "relativesInfo_gen", sequenceName = "relativesInfo_id_seq", initialValue = 1, allocationSize = 1)
    @JsonIgnore
    private Long id;

    @Column(name="name")
    @Size(max = 50, message = "Not valid, not more 50 symbols")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Solomon")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String name = "";

    @Column(name="phone")
    @Pattern(regexp = "^\\+38 \\(\\d{3}\\) \\d{3}-\\d{4}", message = "Not valid. Ex: +38 (095) 435-7132")
    private String phone  = "";


    @Column(name="relation")
    @Size(max = 50, message = "Not valid, not more 50 symbols")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Solomon")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngramD"))
    private String relation  = "";

    public RelativesInfo() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
