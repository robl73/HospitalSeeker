package com.hospitalsearch.controller;

import com.hospitalsearch.dto.PatientDetailDTO;
import com.hospitalsearch.entity.*;
import com.hospitalsearch.service.*;
import com.hospitalsearch.util.Gender;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladimir Paladiuk on 19.10.2016.
 */

@Controller
public class PatientController {

    @Autowired
    UserDetailService userDetailService;

   /* @Autowired
    RelativesInfo relativesInfo;*/

    @Autowired
    UserService userService;

    @Autowired
    DoctorInfoService doctorInfoService;

    @Autowired
    PatientInfoService patientInfoService;

    @Autowired
    PatientCardService patientCardService;

    @PreAuthorize("hasRole('PATIENT')")
    @RequestMapping(value = {"/user/patientProfile"}, method = RequestMethod.GET, consumes = "application/json")
    public String patientProfile(@RequestParam(value = "edit", defaultValue = "false") Boolean edit, ModelMap model) {

        PatientDetailDTO patientDetailDTO = new PatientDetailDTO();
        model.addAttribute("edit", edit);
              model.addAttribute("patientDetailDTO", patientInfoService.getPatientProfileByEmail(userService.getByEmail(PrincipalConverter.getPrincipal()).getEmail(), patientDetailDTO));

        return "/user/patientProfile";
    }


    @PreAuthorize("hasRole('PATIENT')")
    @RequestMapping(value = {"/user/patientProfile"}, method = RequestMethod.POST)
    public String savePatientInfo(@Valid @ModelAttribute("patientDetailDTO") PatientDetailDTO patientDetailDTO, BindingResult bindingResult, ModelMap model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("patientDetailDTO", patientDetailDTO);
            return "/user/patientProfile";
        }
        patientInfoService.updatePatientProfile(patientDetailDTO);
        model.addAttribute("edit", false);
        model.addAttribute("patientDetailDTO", patientDetailDTO);
        return "user/patientProfile";
    }



}