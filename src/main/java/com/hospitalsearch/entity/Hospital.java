package com.hospitalsearch.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

/**
 * @author Oleksandr Mukonin
 *
 * Continued Lesia Koval
 * Implemented hibernate search for hospitals
 */

/**
 * Such annotations as:
 * - @Indexed
 * - @AnalyzerDef
 * - @DocumentId
 * - @Field
 * - @IndexedEmbedded
 * are used only for hibernate search and YOU SHOULD NOT USE THEM for
 * other field or entity if it`s not necessary
* */
@Entity
@Table(name = "hospital")
@Indexed  //annotation for hibernate search
@NamedQueries({
	@NamedQuery(name = Hospital.GET_LIST_BY_BOUNDS, query = Hospital.GET_LIST_BY_BOUNDS_QUERY)
})
@AnalyzerDef(name = "ngram",
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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class Hospital implements Serializable {

	static final String GET_LIST_BY_BOUNDS_QUERY = "from Hospital h where "
			+ "(latitude < :nelat) and (latitude > :swlat) and "
			+ "(longitude < :nelng) and (longitude > :swlng)";
	public static final String GET_LIST_BY_BOUNDS = "GET_LIST_BY_BOUNDS";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospital_gen")
	@SequenceGenerator(name = "hospital_gen", sequenceName = "hospital_id_seq", initialValue = 1, allocationSize = 1)
	@DocumentId  //annotation for hibernate search
	private Long id;

	@NotEmpty(message = "This field is required.")
	@Size(min = 5, max = 70, message = "Please enter at least 5 symbols and not more than 70 symbols.")
	@Column(nullable = false)
	@Field(analyze = Analyze.YES, analyzer = @Analyzer(definition = "ngram"))  //annotation for hibernate search
	private String name;

	@NotNull
	@Min(-90)
	@Max(90)
	@Column(nullable = false)
	private Double latitude;

	@NotNull
	@Min(-180)
	@Max(180)
	@Column(nullable = false)
	private Double longitude;

	@Embedded
	@Valid
	@IndexedEmbedded  //annotation for hibernate search
	@AttributeOverrides({
		@AttributeOverride(name = "city", column = @Column(name = "city")),
		@AttributeOverride(name = "country", column = @Column(name = "country")),
		@AttributeOverride(name = "street", column = @Column(name = "street")),
		@AttributeOverride(name = "building", column = @Column(name = "building"))
	})
	private HospitalAddress address = new HospitalAddress();
	
	@Size(max = 150, message = "Please enter not more than 150 symbols.")
	@Column(nullable = false)
	@Field(analyze = Analyze.YES, analyzer = @Analyzer(definition = "ngram"))  //annotation for hibernate search
	private String description;

	@Column(name = "imagepath")
	private String imagePath;

	@JsonIgnore
	@OneToMany(mappedBy="hospital", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	@Cache(region="entityCache",usage=CacheConcurrencyStrategy.READ_ONLY)
	@IndexedEmbedded  //annotation for hibernate search
	private List<Department> departments;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<User> managers;

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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public HospitalAddress getAddress() {
		return address;
	}

	public void setAddress(HospitalAddress address) {
		this.address = address;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
	public List<User> getManagers() {
		return managers;
	}

	public void setManagers(List<User> managers) {
		this.managers = managers;
	}

	@Override
	public String toString() {
		return "Hospital{" +
				"managers=" + managers +
				", imagePath='" + imagePath + '\'' +
				", description='" + description + '\'' +
				", address=" + address +
				", longitude=" + longitude +
				", latitude=" + latitude +
				", name='" + name + '\'' +
				", id=" + id +
				'}';
	}
}
