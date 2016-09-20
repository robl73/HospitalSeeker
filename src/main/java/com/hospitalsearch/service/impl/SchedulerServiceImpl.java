package com.hospitalsearch.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dao.SchedulerDAO;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.Scheduler;
import com.hospitalsearch.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created by vanytate on 9/9/16.
 */

@Service
@Transactional
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private SchedulerDAO schedulerDAO;

    @Autowired
    private DoctorInfoDAO doctorInfoDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Scheduler getByDoctorId(Long doctorId) {
        return schedulerDAO.getByDoctorId(doctorId);
    }

    @Override
    public void saveScheduler(String schedulerString, Long doctorId) throws IOException {
        Scheduler scheduler = schedulerDAO.getByDoctorId(doctorId);
        ObjectMapper mapper = new ObjectMapper();
        Scheduler newScheduler = mapper.readValue(schedulerString, Scheduler.class);
        newScheduler.setDoctorInfo(doctorInfoDAO.getById(doctorId));
        if (scheduler != null) {
            schedulerDAO.delete(scheduler);
        }
        schedulerDAO.save(newScheduler);

    }

    @Override
    public String getByDoctorEmail(String doctorEmail) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(schedulerDAO.getByDoctorId(userDAO.getByEmail(doctorEmail).getUserDetails().getUser().getId()));
        return json;
    }
}
