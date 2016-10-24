package com.hospitalsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalsearch.entity.Scheduler;
import com.hospitalsearch.service.SchedulerService;
import com.hospitalsearch.service.impl.AppointmentValidationService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by vanytate on 9/9/16.
 */

@Controller
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private AppointmentValidationService appointmentValidationService;

    @Transactional
    @RequestMapping(value = "/**/supplyWorkScheduler", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void supplyScheduler(@RequestBody String data, @RequestParam Long doctorId) throws IOException {
        schedulerService.saveScheduler(data, doctorId);
    }

    @ResponseBody
    @RequestMapping(value = "/getWorkSchedulerByPrincipal", method = RequestMethod.GET)
    public String getWorkSchedulerByDoctor() throws JsonProcessingException {
        String doctorEmail = PrincipalConverter.getPrincipal();
        return schedulerService.getByDoctorEmail(doctorEmail);
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/**/getWorkScheduler", method = RequestMethod.GET)
    public String getWorkScheduler(@RequestParam("id") Long id) throws JsonProcessingException {
        Scheduler scheduler = schedulerService.getByDoctorId(id);
        if (scheduler != null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(scheduler);
        }
        return null;
    }


    @RequestMapping(value = "/workscheduler", method = RequestMethod.GET)
    public String getAppointments() {
        return "workscheduler";
    }

    @ResponseBody
    @RequestMapping(value = "/**/validate", method = RequestMethod.GET)
    public Map<String, Object> validate(@RequestParam(value = "ev", required = false) String appointment) {
        return appointmentValidationService.validateAppointment(appointment);
    }
}
