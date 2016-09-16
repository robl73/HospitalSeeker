package com.hospitalsearch.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;

import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class HospitalAddress {


	@NotEmpty(message = "This field is required.")
	@Size(min = 2, max = 30, message = "Please enter at least 2 symbols and not more than 30 symbols.")
	@Pattern(regexp = "^[a-zA-Zà-ÿÀ-ß¸¨³²¿¯ºªÛûÚú'\\-\\s]+$", message = "Contain only letters.")
	@Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngram"))	
	private String country;

	@NotEmpty(message = "This field is required.")
	@Size(max = 30, message = "Please enter not more than 30 symbols.")
	@Pattern(regexp = "^[a-zA-Zà-ÿÀ-ß¸¨³²¿¯ºªÛûÚú'\\-\\s]+$", message = "Contain only letters.")
	@Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngram"))	
	private String city;

	@NotEmpty(message = "This field is required.")
	@Size(max = 30, message = "Please enter not more than 30 symbols.")
	@Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngram"))	
	private String street;

	@NotEmpty(message = "This field is required.")
	@Size(max = 10, message = "Please enter not more than 10 symbols.")
    @Field(analyze = Analyze.YES,analyzer = @Analyzer(definition = "ngram"))
	private String building;

	public HospitalAddress(){}


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuilding() {
		return building;
	}


	public void setBuilding(String building) {
		this.building = building;
	}


	@Override
	public String toString() {
		
			return new StringBuilder()
				.append(street).append(", ")
				.append(building).append(", ")
				.append(city).append(", ")
				.append(country).toString();
	}

}
