package com.hospitalsearch.controller;

import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.FeedbackService;
import com.hospitalsearch.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public String doctorInfo(@PathVariable(value = "id") Long doctorId, ModelMap model) {
        String principal = PrincipalConverter.getPrincipal();
        DoctorInfo doctorInfo = doctorInfoService.getById(doctorId);
        boolean allowAddFeedback = true;
        if (principal.equals("anonymousUser")) {
            allowAddFeedback = false;
        } else {
            Long producerId = userService.getByEmail(principal).getId();
            if (producerId.equals(doctorInfo.getUserDetails().getUser().getId())) {
                allowAddFeedback = false;
            } else {
                if (feedbackService.isUserCreateFeedback(producerId, doctorId)) {
                    allowAddFeedback = false;
                }
            }
        }
        List<Feedback> feedbacks = feedbackService.getByDoctorId(doctorId);
        model.put("allowAddFeedback", allowAddFeedback);
        model.put("doctor", doctorInfo);
        model.put("formatter", DateTimeFormatter.ofPattern("d MMM uuuu hh:mm"));
        model.put("feedbacks", feedbacks);
        return "doctorInfo";
    }

}
