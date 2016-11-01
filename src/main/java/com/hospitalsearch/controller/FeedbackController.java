package com.hospitalsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.SchedulerService;
import com.hospitalsearch.util.FeedbackDTOForAll;
import com.hospitalsearch.util.FeedbackDTOForManager;
import com.hospitalsearch.util.FeedbackStatus;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.FeedbackService;
import com.hospitalsearch.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FeedbackController {

    @SuppressWarnings("unused")
	@Autowired
    private UserService userService;

    @Autowired
    private DoctorInfoService doctorInfoService;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value="/doctor/feedback", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addFeedback(@RequestParam("doctorId") Long doctorId, @RequestParam("message") String message) {
        String principal = PrincipalConverter.getPrincipal();
        User producer = userService.getByEmail(principal);
        DoctorInfo consumer = doctorInfoService.getById(doctorId);
        Feedback feedback = new Feedback(message, producer, consumer, LocalDateTime.now(), FeedbackStatus.NEW);
        feedbackService.save(feedback);
    }

    @RequestMapping("/doctor/{id}")
    public String doctorInfo(@PathVariable(value = "id") Long doctorId,
                             @RequestParam(value = "rowCount", required = false, defaultValue = "6") Integer count, ModelMap model) throws JsonProcessingException {
        DoctorInfo doctorInfo = doctorInfoService.getWithDepartments(doctorId);
        List<FeedbackDTOForAll> feedbackDTOs = feedbackService.nextFeedbacks(doctorId, 1, count);
        ObjectMapper mapper = new ObjectMapper();
        model.put("feedbacks", mapper.writeValueAsString(feedbackDTOs));
        model.put("isMore", feedbackService.isMoreFeedbacksByConsumer(doctorId, 1, count));
        model.put("allowAddFeedback", feedbackService.canWrite(PrincipalConverter.getPrincipal(), doctorInfo));
        model.put("doctor", doctorInfo);
        model.put("isScheduler", schedulerService.isScheduler(doctorId));
        return "doctorInfo";
    }

    @RequestMapping(value = "/doctor/showMoreFeedbacks")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> feedbacks(@RequestParam(value = "doctorId") Long doctorId, @RequestParam(value = "rowNumber") Integer rowNumber,
                          @RequestParam(value = "rowCount") Integer count) {
        Map<String, Object> data = new LinkedHashMap<>(2);
        data.put("feedbacks", feedbackService.nextFeedbacks(doctorId, rowNumber, count));
        data.put("isMore", feedbackService.isMoreFeedbacksByConsumer(doctorId, rowNumber, count));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/moderationFeedbacks")
    public String moderationFeedbacks(@RequestParam(value = "statuses", required = false, defaultValue = "NEW") FeedbackStatus[] statuses,
                                      @RequestParam(value = "firstPage", required = false, defaultValue = "1") Integer firstPage,
                                      @RequestParam(value = "countOfPage", required = false, defaultValue = "10") Integer countOfPage,
                                      ModelMap model) throws JsonProcessingException {
        String principal = PrincipalConverter.getPrincipal();
        User manager = userService.getByEmail(principal);
        List<FeedbackDTOForManager> feedbacks = feedbackService.getFeedbacksByManager(manager.getId(), firstPage, countOfPage, statuses);
        ObjectMapper mapper = new ObjectMapper();
        model.put("feedbacks", mapper.writeValueAsString(feedbacks));
        return "manager/moderationFeedbacks";
    }

    @RequestMapping(value = "/showMoreFeedbacks")
    @ResponseBody
    public ResponseEntity<List<FeedbackDTOForManager>> moreFeebacksForManager(@RequestParam(value = "statuses[]") FeedbackStatus[] statuses, @RequestParam(value = "firstPage") Integer firstPage,
                                      @RequestParam(value = "countOfPage") Integer countOfPage) {
        User manager = userService.getByEmail(PrincipalConverter.getPrincipal());
        List<FeedbackDTOForManager> feedbacks = feedbackService.getFeedbacksByManager(manager.getId(), firstPage, countOfPage, statuses);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @RequestMapping(value = "/changeFeedbackStatus", method = RequestMethod.POST)
    @ResponseBody
    public void changeStatus(@RequestParam(value = "feedbackId") Long feedbackId,
                             @RequestParam(value = "status") FeedbackStatus status) {
        feedbackService.changeStatus(feedbackId, status);
    }

}
