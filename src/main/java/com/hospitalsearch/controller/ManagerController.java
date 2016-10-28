package com.hospitalsearch.controller;

import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.dto.ViewForManagerDTO;
import com.hospitalsearch.entity.Hospital;
import com.google.gson.Gson;
import com.hospitalsearch.dto.NameDepartmensByHospitalDTO;
import com.hospitalsearch.dto.NameHospitalsByManagerDTO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.UserDetail;
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

import java.util.List;
import java.util.Map;
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

    private static String emailTemplate = "emailTemplate.vm";


    private Integer doctorsPerPage = 10;

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping("/attachedHospitals")
    public String getHospitalsByManager(ModelMap model){
        List<Hospital> hospitals = managerService.getHospitalsByManager();
        if((hospitals.isEmpty())||hospitals==null){
            model.put("messageError", true);
            return "manager/attachedHospitals";
        }
        if(hospitals.size() > 1){
            model.put("hospitals", hospitals);
            return "manager/attachedHospitals";
        }
        return "redirect:/attachedHospitals/"+hospitals.get(0).getId()+"/manageDoctors";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/attachedHospitals/{id}/manageDoctors", method = RequestMethod.GET)
    public String getDoctorsByManager(ModelMap model,@PathVariable ("id") Long hospitalId,
                                      @ModelAttribute ViewForManagerDTO dto,
                                      @RequestParam(value = "hospitalName", defaultValue = "") String hospitalName,
                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "sort", defaultValue = "specialization") String sort,
                                      @RequestParam(value = "asc", defaultValue = "true") Boolean asc) {
        dto.setCurrentPage(page);
        dto.setAsc(asc);
        dto.setSort(sort);
        dto.setPageSize(doctorsPerPage);
        List<DoctorSearchDTO> doctors =  managerService.getDoctorsByManagerAndHospital(hospitalId, dto);
        if (dto.getTotalPage() > 1) {
            model.addAttribute("pagination", "pagination");
        }
        if (hospitalName.isEmpty()){
            hospitalName = hospitalService.getById(hospitalId).getName();
        }
        model.addAttribute("hospitalName", hospitalName);
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("viewForManagerDTO", dto);
        model.addAttribute("pageSize", dto.getPageSize());
        model.addAttribute("doctors", doctors);
        return "manager/manageDoctors";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/attachedHospitals/{id}/manageDoctors/search", method = RequestMethod.GET)
    public String searchUser(@PathVariable ("id") Long hospitalId,
                             @RequestParam(value = "hospitalName") String hospitalName,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @ModelAttribute ViewForManagerDTO dto,
                             ModelMap model) throws Exception {
        dto.setPageSize(doctorsPerPage);
        List <DoctorSearchDTO> doctors = managerService.searchDoctors(dto, hospitalId);
        if (dto.getTotalPage() > 1) {
            model.addAttribute("pagination", "pagination");
        }
        model.addAttribute("search", "search");
        model.addAttribute("hospitalName", hospitalName);
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("viewForManagerDTO", dto);
        model.addAttribute("doctors", doctors);
        return "manager/manageDoctors";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/doctor/{d_id}/manage", method = RequestMethod.GET)
    public String getManage(
            @PathVariable("d_id") Long doctorId, ModelMap model) {
        UserDetail userDetail = userDetailService.getById(doctorId);
        model.addAttribute("id", doctorInfoService.getIdByUserDetail(userDetail.getId()));
        model.addAttribute("doctor", userDetailService.getById(doctorId));
        return "manager/manage";
    }

    @ResponseBody
    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/attachedHospitals/{id}/manageDoctors/setItemsPerPage/{value}", method = RequestMethod.GET)
    public String setItemsPerPage(@PathVariable int value) {
        doctorsPerPage = value;
        return "done";
    }

    @RequestMapping(value = "/newDoctor", method = RequestMethod.GET)
    public String getRegistration(@ModelAttribute("newDoctorDto") NewDoctorRegistrationDTO newDoctorRegistrationDTO,
                                  ModelMap model) {
        newDoctorRegistrationDTO.setNameHospitals(hospitalService
                .getAllNameHospitalsByManager(userService.getByEmail(PrincipalConverter.getPrincipal()).getId()));
        if(hospitalService
                .getAllNameHospitalsByManager(userService.getByEmail(PrincipalConverter.getPrincipal()).getId()).size() == 1){
            newDoctorRegistrationDTO.setNameDepartment(hospitalService.getAllNameDepartmentsByHospitals(hospitalService
                    .getAllNameHospitalsByManager(userService.getByEmail(PrincipalConverter.getPrincipal()).getId()).get(0).getId()));
        }
        model.addAttribute("newDoctorDto", newDoctorRegistrationDTO);
        model.addAttribute("edit", true);
        return "/newDoctor";
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public void getDepartmentsNameByHospital(HttpServletRequest request,
                                             HttpServletResponse response) throws IOException {
        Long hospitalId = Long.valueOf(request.getParameter("hospitalId"));
        List<NameDepartmensByHospitalDTO> nameDepartmensByHospitalDTOs = hospitalService.getAllNameDepartmentsByHospitals(hospitalId);
        String json = null;;
        json = new Gson().toJson(nameDepartmensByHospitalDTOs);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    @RequestMapping(value = "/newDoctor", method = RequestMethod.POST)
    public String newDoctorRegistration(@Valid @ModelAttribute("newDoctorDto") NewDoctorRegistrationDTO newDoctorRegistrationDTO,
                                        BindingResult result, ModelMap model, Locale locale) {
        if (result.hasFieldErrors("firstName") || result.hasFieldErrors("lastName")
                || result.hasFieldErrors("email")|| result.hasFieldErrors("education")
                || result.hasFieldErrors("address")|| result.hasFieldErrors("birthDate")){
            model.addAttribute("edit", true);
            return "/newDoctor";
        }
        User user = userService.register(newDoctorRegistrationDTO);
        String token = getRandomToken();
        verificationTokenService.createToken(token, user);
        try {
            String confirmationMessage = mailService.createRegisterMessage(user, token, locale);
            mailService.sendMessage(user, messageSource.getMessage("mail.message.registration.confirm", null, locale), confirmationMessage, emailTemplate);
            model.addAttribute("emailSuccess", user.getEmail());
            return "/user/endRegistration";
        } catch (MailException | ConnectException e) {
            model.addAttribute("emailError", user.getEmail());
            verificationTokenService.deleteTokenByUser(user);
            userService.changeStatus(user.getId());
            userService.delete(user.getId());
            return "/error/emailMessage";
        }
    }

    private String getRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
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


    @RequestMapping(value = "/docs", method = RequestMethod.GET)
    public String docs(Map<String, Object> model){
        model.put("doctors", userService.getByRole("DOCTOR"));
        return "doctorsTwo";
    }


}
