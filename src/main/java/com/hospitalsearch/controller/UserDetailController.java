package com.hospitalsearch.controller;


import com.hospitalsearch.entity.*;
import com.hospitalsearch.service.*;

import com.hospitalsearch.util.Gender;
import com.hospitalsearch.util.Page;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@Controller
public class UserDetailController {
    @Autowired
    UserDetailService userDetailService;

    @Autowired
    UserService userService;

    @Autowired
    DoctorInfoService doctorInfoService;

    @Autowired
    PatientInfoService patientInfoService;

    @Autowired
    PatientCardService patientCardService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @RequestMapping(value = {"/user/detail"}, method = RequestMethod.GET, consumes = "application/json")
    public String userDetail(@RequestParam(value = "edit", defaultValue = "false") Boolean edit, ModelMap model) {
        User user = userService.getByEmail(PrincipalConverter.getPrincipal());
        UserDetail userDetail = user.getUserDetails();
        model.addAttribute("edit", edit);
        model.addAttribute("userDetail", userDetail);
        model.addAttribute("gender", Gender.values());
        model.addAttribute("email", user.getEmail());
        return "user/detail";

    }


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @RequestMapping(value = {"/save/detail"}, method = RequestMethod.POST)
    public String saveUserDetail(@Valid @ModelAttribute("userDetail") UserDetail userDetail, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("userDetail", userDetail);
            model.addAttribute("gender", Gender.values());
            model.addAttribute("email", PrincipalConverter.getPrincipal());
            return "user/detail";
        }
        User user = userService.getByEmail(PrincipalConverter.getPrincipal());
        userDetail.setUser(user);
        userDetailService.update(userDetail);
        model.addAttribute("edit", false);
        model.addAttribute("userDetail", userDetail);
        model.addAttribute("gender", Gender.values());
        model.addAttribute("email", PrincipalConverter.getPrincipal());
        return "user/detail";
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @RequestMapping(value = {"/user/profile"}, method = RequestMethod.GET)
    public String userProfile(@RequestParam("userId") Long userId, ModelMap model) {
        User user = userService.getById(userId);
        UserDetail userDetail = user.getUserDetails();
        model.addAttribute("edit", false);
        model.addAttribute("userDetail", userDetail);
        model.addAttribute("gender", Gender.values());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("read", true);
        return "user/detail";
    }

}
