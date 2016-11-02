package com.hospitalsearch.controller;

import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.dto.ViewForManagerDTO;
import com.hospitalsearch.entity.Hospital;
import com.google.gson.Gson;
import com.hospitalsearch.dto.NameDepartmensByHospitalDTO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.*;
import com.hospitalsearch.dto.NewDoctorRegistrationDTO;
import com.hospitalsearch.entity.*;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.ConnectException;
import java.util.*;

/**
 * Created by igortsapyak on 24.05.16.
 *
 * Continued Lesia Koval
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

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/**/manage/doctor/edit", method = RequestMethod.GET)
    public ModelAndView editDoctor(@RequestParam("id") Long userId,
                                   @RequestParam("hospitalId") String hospitalId) {
        NewDoctorRegistrationDTO newDoctorRegistrationDTO  = new NewDoctorRegistrationDTO();
        newDoctorRegistrationDTO.setEmail(userService.getById(userId).getEmail());
        newDoctorRegistrationDTO.setEnabled(userService.getById(userId).getEnabled());
        newDoctorRegistrationDTO.setFirstName(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getFirstName());
        newDoctorRegistrationDTO.setLastName(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getLastName());
        newDoctorRegistrationDTO.setGender(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getGender());
        newDoctorRegistrationDTO.setBirthDate(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getBirthDate());
        newDoctorRegistrationDTO.setAddress(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getAddress());
        newDoctorRegistrationDTO.setImagePath(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getImagePath());
        newDoctorRegistrationDTO.setCategory(doctorInfoService.getById(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getId()).getCategory());
        newDoctorRegistrationDTO.setPhone(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getPhone());
        newDoctorRegistrationDTO.setSpecialization(doctorInfoService.getById(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getId()).getSpecialization());
        newDoctorRegistrationDTO.setNameHospitalId(Long.valueOf(hospitalId));
        newDoctorRegistrationDTO.setEducation(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getEducation());
        newDoctorRegistrationDTO.setUserId(userId);
        ModelAndView modelAndView = new ModelAndView("/editDoctor");
        modelAndView.addObject("newDoctorDto", newDoctorRegistrationDTO);
        modelAndView.addObject("edit", true);
        return modelAndView;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/**/manage/doctor/view", method = RequestMethod.GET)
    public ModelAndView viewDoctor(@RequestParam("id") Long userId) {
        NewDoctorRegistrationDTO newDoctorRegistrationDTO  = new NewDoctorRegistrationDTO();
        newDoctorRegistrationDTO.setEmail(userService.getById(userId).getEmail());
        newDoctorRegistrationDTO.setEnabled(userService.getById(userId).getEnabled());
        newDoctorRegistrationDTO.setFirstName(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getFirstName());
        newDoctorRegistrationDTO.setLastName(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getLastName());
        newDoctorRegistrationDTO.setGender(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getGender());
        newDoctorRegistrationDTO.setBirthDate(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getBirthDate());
        newDoctorRegistrationDTO.setAddress(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getAddress());
        newDoctorRegistrationDTO.setImagePath(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getImagePath());
        newDoctorRegistrationDTO.setCategory(doctorInfoService.getById(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getId()).getCategory());
        newDoctorRegistrationDTO.setPhone(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getPhone());
        newDoctorRegistrationDTO.setSpecialization(doctorInfoService.getById(userDetailService.getById(userService.getById(userId).getUserDetails().getId()).getId()).getSpecialization());
        newDoctorRegistrationDTO.setUserId(userId);
        ModelAndView modelAndView = new ModelAndView("/viewDoctor");
        modelAndView.addObject("newDoctorDto", newDoctorRegistrationDTO);
        modelAndView.addObject("edit", false);
        return modelAndView;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/**/manage/doctor/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("newDoctorDto") NewDoctorRegistrationDTO newDoctorRegistrationDTO,
                           BindingResult result, ModelMap model,
                            Locale locale, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "redirect:/manage/doctor/edit";
        }
        userService.update(newDoctorRegistrationDTO);
        return "redirect:/manage/hospitals/" + newDoctorRegistrationDTO.getNameHospitalId() + "/manageDoctors/";
    }

    @RequestMapping("/manage/hospitals")
    public String getHospitalsByManager(ModelMap model){
        List<Hospital> hospitals = managerService.getHospitalsByManager();
        if((hospitals.isEmpty())||hospitals==null){
            model.put("messageError", true);
            return "manager/hospitals";
        }
        if(hospitals.size() > 1){
            model.put("hospitals", hospitals);
            return "manager/hospitals";
        }
        return "redirect:/manage/hospitals/"+hospitals.get(0).getId()+"/manageDoctors";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/manage/hospitals/{id}/manageDoctors", method = RequestMethod.GET)
    public String getDoctorsByManager(ModelMap model,@PathVariable ("id") Long hospitalId,
                                      @ModelAttribute ViewForManagerDTO dto,
                                      @RequestParam(value = "hospitalName", defaultValue = "") String hospitalName,
                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "sort", defaultValue = "specialization") String sort,
                                      @RequestParam(value = "asc", defaultValue = "true") Boolean asc) {
        dto.setCurrentPage(page);
        dto.setAsc(asc);
        dto.setSort(sort);
        dto.setPageSize(10);
        List<DoctorSearchDTO> doctors =  managerService.getDoctorsByManagerAndHospital(hospitalId, dto);
        if((doctors.isEmpty())||doctors==null){
            model.put("emptyList", Boolean.TRUE);
            return "manager/manageDoctors";
        }
        if (dto.getTotalPage() > 1) {
            model.addAttribute("pagination", "pagination");
        }
        if (hospitalName.isEmpty()){
            hospitalName = hospitalService.getById(hospitalId).getName();
        }
        dto.setHospitalName(hospitalName);
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("viewForManagerDTO", dto);
        model.addAttribute("doctors", doctors);
        return "manager/manageDoctors";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/manage/hospitals/{id}/manageDoctors/search", method = RequestMethod.GET)
    public String searchDoctorForManager(@PathVariable ("id") Long hospitalId,
                               @ModelAttribute ("viewForManagerDTO") ViewForManagerDTO dto,
                               ModelMap model) throws Exception {
        List <DoctorSearchDTO> doctors = managerService.searchDoctors(dto, hospitalId);
        if (dto.getTotalPage() > 1) {
            model.addAttribute("pagination", "pagination");
        }
        model.addAttribute("search", "search");
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("viewForManagerDTO", dto);
        model.addAttribute("doctors", doctors);
        return "manager/manageDoctors";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/doctor/{d_id}/manage", method = RequestMethod.GET)
    public String getManage(
            @PathVariable("d_id") Long doctorId, ModelMap model) {
        DoctorInfo doctorInfo = doctorInfoService.getById(doctorId);
        UserDetail userDetail = userDetailService.getById(doctorInfo.getUserDetails().getId());
        model.addAttribute("id", doctorInfoService.getIdByUserDetail(userDetail.getId()));
        model.addAttribute("doctor", userDetailService.getById(userDetail.getId()));
        return "manager/manage";
    }


    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/newDoctor", method = RequestMethod.GET)
    public String getRegistration(@ModelAttribute("newDoctorDto") NewDoctorRegistrationDTO newDoctorRegistrationDTO,
                                  ModelMap model) {
        newDoctorRegistrationDTO.setNameHospitals(hospitalService
                .getAllNameHospitalsByManager(userService.getByEmail(PrincipalConverter.getPrincipal()).getId()));
        if(hospitalService
                .getAllNameHospitalsByManager(userService.getByEmail(PrincipalConverter.getPrincipal()).getId()).size() >= 1){
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
        List<NameDepartmensByHospitalDTO> nameDepartmensByHospitalDTOs =
                hospitalService.getAllNameDepartmentsByHospitals(hospitalId);
        String json = null;
        json = new Gson().toJson(nameDepartmensByHospitalDTOs);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    @RequestMapping(value = "/newDoctor", method = RequestMethod.POST)
    public String newDoctorRegistration(@Valid @ModelAttribute("newDoctorDto") NewDoctorRegistrationDTO newDoctorRegistrationDTO,
                                        BindingResult result, ModelMap model, Locale locale) {
        if (result.hasErrors()){
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

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/manage/hospitals/{id}/manageDoctors/delete/{doctorId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable ("id") Long hospitalId,
                             @PathVariable ("doctorId") Long doctorId,
                             @ModelAttribute ("viewForManagerDTO") ViewForManagerDTO dto,
                             Locale locale) throws ConnectException {
        DoctorInfo doctorInfo = doctorInfoService.getById(doctorId);
        UserDetail userDetail = userDetailService.getById(doctorInfo.getUserDetails().getId());
        userService.changeStatus(userDetail.getUser().getId());
        sendBannedMessageToUserById(userDetail.getUser().getId(), locale);
        return "redirect:/manage/hospitals/"+hospitalId+"/manageDoctors";
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

    //utility methods
    private void sendBannedMessageToUserById(Long id, Locale locale) throws ConnectException {
        User user = userService.getById(id);
        if (!user.getEnabled()) {
            String bannedMessage = mailService.createBannedMessage(user, locale);
            try {
                mailService.sendMessage(user, messageSource.getMessage("mail.message.banned.title", null, locale), bannedMessage, emailTemplate);
            }catch (Exception e){
                //write log
            }

        }
    }
}
