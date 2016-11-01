package com.hospitalsearch.entity;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hospitalsearch.util.CustomLocalDateTimeSerializer;
import com.hospitalsearch.util.FeedbackStatus;
import com.hospitalsearch.util.LocalDateTimeTimestampConverter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "feedbacks")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_gen")
	@SequenceGenerator(name = "feedback_gen", sequenceName = "feedback_id_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@NotNull
	@Size(min = 4, max = 150)
	private String message;
	
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private User producer;
	
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private DoctorInfo consumer;

	@Convert(converter = LocalDateTimeTimestampConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	private LocalDateTime date;

	public Feedback(String message, User producer, DoctorInfo consumer, LocalDateTime date, FeedbackStatus status) {
		this.message = message;
		this.producer = producer;
		this.consumer = consumer;
		this.date = date;
        this.status = status;
	}

	@Column(columnDefinition = "varchar(3) default 'NEW'")
    @Enumerated(value = EnumType.STRING)
    private FeedbackStatus status;

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

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }

}

