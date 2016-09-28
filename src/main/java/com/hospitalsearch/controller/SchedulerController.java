package com.hospitalsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalsearch.entity.Scheduler;
import com.hospitalsearch.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by vanytate on 9/9/16.
 */

@Controller
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @Transactional
    @RequestMapping(value = "/**/supplyWorkScheduler", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void supplyScheduler(@RequestBody String data, @RequestParam Long doctorId) {
        try {
            schedulerService.saveScheduler(data, doctorId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/**/getWorkScheduler", method = RequestMethod.GET)
    public String getWorkScheduler(@RequestParam("id") Long id) throws JsonProcessingException {
        Scheduler scheduler = schedulerService.getByUserDetailId(id); //is not null
        if (scheduler != null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(scheduler);
        }
        return null;
    }


}
