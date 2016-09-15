package com.hospitalsearch.dto;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.constraints.NotEmpty;

import com.hospitalsearch.entity.HospitalAddress;


public class HospitalDTO {

	private Long id;

	@NotEmpty
	@Size(min = 5, max = 50)
	private String name;

	@NotNull
	@Min(-90)
	@Max(90)
	private Double latitude;

	@NotNull
	@Min(-180)
	@Max(180)
	private Double longitude;

	@Embedded
	@Valid
	private HospitalAddress address = new HospitalAddress();
	
	@NotEmpty 
	@Size(min = 1, max = 150)
	private String addressGeo;
	
	public String getAddressGeo() {
		return addressGeo;
	}

	public void setAddressGeo(String addressGeo) {
		this.addressGeo = addressGeo;
	}

	
	private String imagePath;

	
	@Size(max = 150)
	private String description;



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


	public HospitalAddress getAddress() {
		return address;
	}

	public void setAddress(HospitalAddress address) {
		this.address = address;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
	
}

