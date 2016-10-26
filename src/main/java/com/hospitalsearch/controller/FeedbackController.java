package com.hospitalsearch.controller;

import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.FeedbackService;
import com.hospitalsearch.service.UserService;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class FeedbackController {

    @SuppressWarnings("unused")
	@Autowired
    private UserService userService;

    @Autowired
    private DoctorInfoService doctorInfoService;
    
    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value="/doctor/feedback", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void profile(@RequestParam("doctorId") Long doctorId, @RequestParam("message") String message) {
        String principal = PrincipalConverter.getPrincipal();
        User producer = userService.getByEmail(principal);
        DoctorInfo consumer = doctorInfoService.getById(doctorId);
        Feedback feedback = new Feedback(message, producer, consumer, LocalDateTime.now());
        feedbackService.save(feedback);
    }

    @RequestMapping("/doctor/{id}")
    public String doctorInfo(@PathVariable(value = "id") Long doctorId,
                             @RequestParam(value = "rowCount", required = false, defaultValue = "6") Integer count, ModelMap model) {
//        String principal = PrincipalConverter.getPrincipal();
        DoctorInfo doctorInfo = doctorInfoService.getById(doctorId);
        Map<String, Object> data = feedbackService.nextFeedbacks(doctorId, 1, count);
        model.putAll(data);
        model.put("allowAddFeedback", true);
//        model.put("allowAddFeedback", feedbackService.canWrite(principal, doctorInfo));
        model.put("doctor", doctorInfo);
        model.put("formatter", "d MMM yyyy hh:mm");
        return "doctorInfo";
    }

    @RequestMapping(value = "/doctor/showMoreFeedbacks")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> feedbacks(@RequestParam(value = "doctorId") Long doctorId, @RequestParam(value = "rowNumber") Integer rowNumber,
                          @RequestParam(value = "rowCount") Integer count) {
        Map<String, Object> data = feedbackService.nextFeedbacks(doctorId, rowNumber, count);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
