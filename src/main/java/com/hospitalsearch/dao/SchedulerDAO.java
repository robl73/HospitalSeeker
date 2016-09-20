package com.hospitalsearch.dao;

import com.hospitalsearch.entity.Scheduler;
import org.springframework.stereotype.Component;

/**
 * Created by vanytate on 9/9/16.
 */

@Component
public interface SchedulerDAO extends GenericDAO<Scheduler, Long> {

    Scheduler getByDoctorId(Long doctorId);
}
