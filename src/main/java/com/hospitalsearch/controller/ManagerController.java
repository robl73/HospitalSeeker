package com.hospitalsearch.controller;

import com.google.gson.Gson;
import com.hospitalsearch.dto.NameDepartmensByHospitalDTO;
import com.hospitalsearch.service.*;

import com.hospitalsearch.dto.NewDoctorRegistrationDTO;
import com.hospitalsearch.dto.UserRegisterDTO;
import com.hospitalsearch.entity.*;
import com.hospitalsearch.util.Category;
import com.hospitalsearch.util.Gender;
import com.hospitalsearch.util.PrincipalConverter;
import com.hospitalsearch.util.Specialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.ConnectException;
import java.util.*;

/**
 * Created by igortsapyak on 24.05.16.
 */
@Controller
public class ManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private DoctorInfoService doctorInfoService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    private static String emailTemplate = "emailTemplate.vm";

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/newDoctor", method = RequestMethod.GET)
    public String getRegistration(@ModelAttribute("newDoctorDto") NewDoctorRegistrationDTO newDoctorRegistrationDTO,
                                  ModelMap model) {
        newDoctorRegistrationDTO.setNameHospitals(hospitalService.getAllNameHospitalsByManager(
                userService.getByEmail(PrincipalConverter.getPrincipal()).getId()));
        newDoctorRegistrationDTO.setCategorys(Arrays.asList( Category.values()));
        newDoctorRegistrationDTO.setSpecializations(Arrays.asList(Specialization.values()));
        model.addAttribute("newDoctorDto", newDoctorRegistrationDTO);;
        model.addAttribute("edit", true);
        return "/newDoctor";
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public void getDepartmentsNameByHospital(HttpServletRequest request,
                                             HttpServletResponse response) throws IOException {
        Long hospitalId = Long.valueOf(request.getParameter("hospitalId"));
        List<NameDepartmensByHospitalDTO> nameDepartmensByHospitalDTOs =
                hospitalService.getAllNameDepartmentsByHospitals(hospitalId);
        String json = null;;
        json = new Gson().toJson(nameDepartmensByHospitalDTOs);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/newDoctor", method = RequestMethod.POST)
    public String newDoctorRegistration(@Valid @ModelAttribute("newDoctorDto") NewDoctorRegistrationDTO newDoctorRegistrationDTO, ModelMap model,
                                        HttpServletRequest request, BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        if (result.hasErrors()) {
            return "/newDoctor";
        }
        UserRegisterDTO userDto = new UserRegisterDTO();
        userDto.setEmail(newDoctorRegistrationDTO.getEmail());
        userDto.setUserRoles(new HashSet<>(Collections.singletonList(roleService.getByType("DOCTOR"))));
        userDto.setPassword("111111");
        userDto.setConfirmPassword("111111");
        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName(newDoctorRegistrationDTO.getFirstName());
        userDetail.setLastName(newDoctorRegistrationDTO.getLastName());
        userDetail.setAddress(newDoctorRegistrationDTO.getAddress());
        userDetail.setBirthDate(newDoctorRegistrationDTO.getBirthDate());
        if(request.getParameter("gender").equals(Gender.MALE)){
            userDetail.setGender(Gender.MALE);
        } else {
            userDetail.setGender(Gender.FEMALE);
        }
        userDetail.setImagePath(newDoctorRegistrationDTO.getImagePath());
        User user = userService.register(userDto);
        userDetail.setUser(user);
        userDetail = userDetailService.add(userDetail);
        DoctorInfo doctorInfo = new DoctorInfo();
        doctorInfo.setCategory(newDoctorRegistrationDTO.getCategory());
        doctorInfo.setSpecialization(newDoctorRegistrationDTO.getSpecialization());
        List<Department> departments = new ArrayList<>();
        departments.add(departmentService.getById(newDoctorRegistrationDTO.getNameDepartmentsId()));
        doctorInfo.setDepartments(departments);
        doctorInfo.setUserDetails(userDetail);
        doctorInfoService.save(doctorInfo);
        String token = getRandomToken();
        verificationTokenService.createToken(token, user);
        try {
            String confirmationMessage = mailService.createRegisterMessage(user, token, locale);
            mailService.sendMessage(user, messageSource.getMessage("mail.message.registration.confirm", null, locale), confirmationMessage, emailTemplate);
            model.addAttribute("emailSuccess", userDto.getEmail());
            return "/user/endRegistration";
        } catch (MailException | ConnectException e) {
            model.addAttribute("emailError", userDto.getEmail());
            verificationTokenService.deleteTokenByUser(user);
            userService.changeStatus(user.getId());
            userService.delete(user.getId());
            return "/error/emailMessage";
        }
    }

    private String getRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
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
        model.addAttribute("id", doctorInfoService.getIdByUserDetail(userDetail.getId()));
        model.addAttribute("doctor", userDetailService.getById(doctorId));
        return "manage";
    }

    @RequestMapping(value = "/docs", method = RequestMethod.GET)
    public String docs(Map<String, Object> model){
        model.put("doctors", userService.getByRole("DOCTOR"));
        return "doctorsTwo";
    }


}
