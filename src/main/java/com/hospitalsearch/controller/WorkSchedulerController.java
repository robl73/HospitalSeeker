package com.hospitalsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hospitalsearch.service.SchedulerService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by igortsapyak on 29.05.16.
 */
@Controller
public class WorkSchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @RequestMapping(value = "/workscheduler", method = RequestMethod.GET)
    public String getAppointments() {
        return "workscheduler";
    }

}
