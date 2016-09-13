package com.hospitalsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by vanytate on 9/8/16.
 */

@Entity
@Table(name = "scheduler")
public class Scheduler {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheduler_id")
    @SequenceGenerator(name = "scheduler_id", sequenceName = "scheduler_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @JsonProperty("day_start")
    @Column(name = "day_start")
    private Byte dayStart;

    @JsonProperty("day_end")
    @Column(name = "day_end")
    private Byte dayEnd;

    @JsonProperty("app_size")
    @Column(name = "app_size")
    private Byte appSize;


    @JsonProperty("week_size")
    @Column(name = "week_size")
    private Byte weekSize;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "doctorinfo_id")
    private DoctorInfo doctorInfo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduler_id")
    private List<Event> events;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getDayStart() {
        return dayStart;
    }

    public void setDayStart(Byte dayStart) {
        this.dayStart = dayStart;
    }

    public Byte getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(Byte dayEnd) {
        this.dayEnd = dayEnd;
    }

    public Byte getAppSize() {
        return appSize;
    }

    public void setAppSize(Byte appSize) {
        this.appSize = appSize;
    }

    public DoctorInfo getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Byte getWeekSize() {
        return weekSize;
    }

    public void setWeekSize(Byte weekSize) {
        this.weekSize = weekSize;
    }

    @Override
    public String toString() {
        return "id = " + getId() + ", dayStart = " + getDayStart() + ", dayEnd = " + getDayEnd() + ", appSize = " + getAppSize() + ", events: " ;//+ getEvents().toString();
    }
}
