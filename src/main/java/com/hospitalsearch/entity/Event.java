package com.hospitalsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hospitalsearch.util.CustomLocalDateTimeDeserializer;
import com.hospitalsearch.util.CustomLocalDateTimeSerializer;
import com.hospitalsearch.util.LocalDateTimeTimestampConverter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by vanytate on 9/9/16.
 */

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id")
    @SequenceGenerator(name = "event_id", sequenceName = "event_id_seq", initialValue = 1, allocationSize = 1)
    @JsonIgnore
    private Long id;

    /**
     * id for DhtmlX scheduler
     */
    @JsonProperty("id")
    @Column(name = "id_dhtmlx")
    private Long idEvent;

    @JsonProperty("start_date")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "start_date")
    @Convert(converter = LocalDateTimeTimestampConverter.class)
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "end_date")
    @Convert(converter = LocalDateTimeTimestampConverter.class)
    private LocalDateTime endDate;

    private String text;

    @JsonProperty("event_pid")
    private Long pid;

    @JsonProperty("event_length")
    private Long length;

    @JsonProperty("rec_pattern")
    private String pattern;

    @JsonProperty("rec_type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "id = " + getId() + ", startDate: " + getStartDate() + ", endDate: " + getEndDate() + ", text: "
                + getText() + ", pid: " + getPid() + ", length: " + getLength() + ", pattern: " + getPattern() + ", type: " + getType();
    }
}
