package com.hospitalsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hospitalsearch.entity.Scheduler;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created by vanytate on 9/9/16.
 */

@Transactional
public interface SchedulerService {

    Scheduler getByDoctorId(Long doctorId);

    void saveScheduler(String schedulerString, Long doctorId) throws IOException;

    String getByDoctorEmail(String doctorEmail) throws JsonProcessingException;

}
