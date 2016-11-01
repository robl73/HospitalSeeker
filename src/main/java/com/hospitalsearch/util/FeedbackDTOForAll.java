package com.hospitalsearch.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hospitalsearch.entity.Feedback;

import java.time.LocalDateTime;

/**
 * Created by vanytate on 10/24/16.
 */
public class FeedbackDTOForAll {

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime date;

    private String message;

    private String producerFirstName;

    private String producerLastName;

    public FeedbackDTOForAll(Feedback feedback) {
        this.date = feedback.getDate();
        this.message = feedback.getMessage();
        this.producerFirstName = feedback.getProducer().getUserDetails().getFirstName();
        this.producerLastName = feedback.getProducer().getUserDetails().getLastName();
    }

    public FeedbackDTOForAll() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProducerFirstName() {
        return producerFirstName;
    }

    public void setProducerFirstName(String producerFirstName) {
        this.producerFirstName = producerFirstName;
    }

    public String getProducerLastName() {
        return producerLastName;
    }

    public void setProducerLastName(String producerLastName) {
        this.producerLastName = producerLastName;
    }

}
