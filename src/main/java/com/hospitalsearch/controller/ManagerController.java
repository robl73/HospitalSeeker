package com.hospitalsearch.controller;

import com.hospitalsearch.dto.NewDoctorRegistrationDTO;
import com.hospitalsearch.dto.UserRegisterDTO;
import com.hospitalsearch.entity.*;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.service.ManagerService;
import com.hospitalsearch.service.UserDetailService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.net.ConnectException;
import java.util.Locale;
import java.util.Map;

/**
 * Created by igortsapyak on 24.05.16.
 */
@Controller
public class ManagerController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private UserService userService;


    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserDetailService userDetailService;

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/newDoctor", method = RequestMethod.GET)
    public String getRegistration(@ModelAttribute("newDoctorDto") NewDoctorRegistrationDTO newDoctorRegistrationDTO,
                                  ModelMap model) {
        model.addAttribute("newDoctorDto", newDoctorRegistrationDTO);
        model.addAttribute("edit", true);
        return "/newDoctor";
    }

    @RequestMapping(value = "/newDoctor", method = RequestMethod.POST)
    public String newDoctorRegistration(@ModelAttribute("newDoctorDto") NewDoctorRegistrationDTO newDoctorRegistrationDTO, ModelMap model) {
        System.out.println(newDoctorRegistrationDTO);
        return "/manageDoctors";
    }

    @RequestMapping(value = "/manageDoctors", method = RequestMethod.GET)
    public String getDoctorsByManager(Map<String, Object> model) {
        model.put("doctors", managerService.getDoctorsByManager());
        return "manageDoctors";
    }

    @RequestMapping(value = "/editHospitalsManagers", method = RequestMethod.GET)
    public String getManagersAndHospitals(Map<String, Object> model) {
        model.put("hospitals", hospitalService.getAll());
        model.put("users", userService.getByRole("MANAGER"));
        return "editHospitalsManagers";
    }

    @RequestMapping(value = "/applyManager", method = RequestMethod.POST)
    public String applyManager(@RequestBody Map<String, Long> hospitalData) {
        managerService.applyManager(hospitalData);
        return "redirect:/editHospitalsManagers";
    }

    @RequestMapping(value = "/deleteManager", method = RequestMethod.POST)
    public String deleteManager(@RequestParam Long hospitalId) {
        managerService.deleteHospitalManager(hospitalId);
        return "redirect: /editHospitalsManagers";
    }

    @RequestMapping(value = "/doctor/{d_id}/manage", method = RequestMethod.GET)
    public String getManage(
            @PathVariable("d_id") Long doctorId, ModelMap model) {
        UserDetail userDetail = userDetailService.getById(doctorId);
        model.addAttribute("id", userDetail.getDoctorInfo().getId());
        model.addAttribute("doctor", userDetailService.getById(doctorId));
        return "manage";
    }

    @RequestMapping(value = "/docs", method = RequestMethod.GET)
    public String docs(Map<String, Object> model){
        model.put("doctors", userService.getByRole("DOCTOR"));
        return "doctorsTwo";
    }


}
