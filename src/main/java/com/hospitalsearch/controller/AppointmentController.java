package com.hospitalsearch.controller;

import com.hospitalsearch.dao.AppointmentDAO;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.*;

//import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MailService emailService;

    @Autowired
    private DoctorInfoService doctorInfoService;

    @ResponseBody
    @RequestMapping(value = "/**/getAppointments", method = RequestMethod.GET)
    public List<Appointment> listAllAppointments(@RequestParam("id") Long id) {
        return appointmentService.getAllbyDoctorId(id);
    }

    @RequestMapping(value = "/doctor/{d_id}/scheduler", method = RequestMethod.GET)
    public String getDashboard(@PathVariable("d_id") Long doctorId, ModelMap model) {
        DoctorInfo doctorInfo = doctorInfoService.getById(doctorId);
        model.addAttribute("doctorInfoId", doctorInfo.getId());
        model.addAttribute("doctor", doctorInfo.getUserDetails());
        return "start";
    }

    @RequestMapping(value = "/**/supplyAppointment", method = RequestMethod.POST)
    public String eventProcessor(HttpServletRequest request,
                                 @RequestParam("id") Long id, @RequestParam("principal") String principal) {
        appointmentService.actionControl(request.getParameterMap(), id, principal);
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping(value = "/**/getAppointmentsByPatient", method = RequestMethod.GET)
    public List<Appointment> patientsAppointments(@RequestParam("patient") String patient) {
        return appointmentService.getAllByPatientEmail(patient);
    }

    @ResponseBody
    @RequestMapping(value = "/getAppointmentsByDoctor", method = RequestMethod.GET)
    public List<Appointment> doctorsAppointments(@RequestParam("doctor") String doctor) {
        return appointmentService.getAllByDoctorEmail(doctor);
    }

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public String getAppointments() {
        return "appointments";
    }


    @RequestMapping(value = "/appointmentId", method = RequestMethod.GET)
    public String getCardByapointmentId(@RequestParam("appointmentId") Long appointmentId) {
        return "redirect:/card/items?userId="+appointmentDAO.getById(appointmentId).getUserDetail().getId();
    }


    @RequestMapping(value = "/**/sendMassage", method = RequestMethod.POST)
    public String sendMassageToEmail(@RequestBody Map<String, String> massageData, Locale locale) throws ConnectException {
        emailService.sendMassageFromUserToUser(massageData, locale);
        return "redirect:/";

    }
    
    @ResponseBody
    @RequestMapping(value = "/**/validateAppointment", method = RequestMethod.GET)
    public String validateAppointment(){
    	System.out.println("hgfyf");
    	return "appointments";
    }
    


}
