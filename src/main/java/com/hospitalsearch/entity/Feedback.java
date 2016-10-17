package com.hospitalsearch.entity;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "feedback")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_gen")
	@SequenceGenerator(name = "feedback_gen", sequenceName = "feedback_id_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@NotNull
	@Size(min = 3, max = 30)
	private String message;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private User producer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private DoctorInfo consumer;

	@OrderColumn
	private LocalDateTime date;

	public Feedback(String message, User producer, DoctorInfo consumer, LocalDateTime date) {
		this.message = message;
		this.producer = producer;
		this.consumer = consumer;
		this.date = date;
	}

	public Feedback() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getProducer() {
		return producer;
	}

	public void setProducer(User producer) {
		this.producer = producer;
	}

	public DoctorInfo getConsumer() {
		return consumer;
	}

	public void setConsumer(DoctorInfo consumer) {
		this.consumer = consumer;
	}

	public LocalDateTime getDate() {
		
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
}
