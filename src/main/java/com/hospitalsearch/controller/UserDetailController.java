package com.hospitalsearch.controller;


import com.hospitalsearch.entity.*;
import com.hospitalsearch.service.*;
import com.hospitalsearch.util.Gender;
import com.hospitalsearch.util.Disease;
import com.hospitalsearch.util.Page;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserDetailController {
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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/user/detail"}, method = RequestMethod.GET, consumes = "application/json")
    public String userDetail(@RequestParam(value = "edit", defaultValue = "false") Boolean edit, ModelMap model) {
        User user = userService.getByEmail(PrincipalConverter.getPrincipal());
        UserDetail userDetail = user.getUserDetails();
        createRelatieves(userDetail);
      //  RelativesInfo relativesInfo = userDetail.getRelativesInfo();
        model.addAttribute("edit", edit);
        model.addAttribute("userDetail", userDetail);
      //  model.addAttribute("relativesInfo", relativesInfo);
        model.addAttribute("gender", Gender.values());
        model.addAttribute("heartProblems", Disease.values());
        model.addAttribute("diabetes", Disease.values());
        model.addAttribute("epilepsy", Disease.values());
        model.addAttribute("email", user.getEmail());
        return "user/detail";
    }

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @RequestMapping(value = {"/save/detail"}, method = RequestMethod.POST)
    public String saveUserDetail(@Valid @ModelAttribute("userDetail") UserDetail userDetail, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("userDetail", userDetail);
            model.addAttribute("gender", Gender.values());
            model.addAttribute("heartProblems", Disease.values());
            model.addAttribute("diabetes", Disease.values());
            model.addAttribute("epilepsy", Disease.values());
            model.addAttribute("email", PrincipalConverter.getPrincipal());
            return "user/detail";
        }
        User user = userService.getByEmail(PrincipalConverter.getPrincipal());
        userDetail.setUser(user);
        PatientInfo patientInfo = patientInfoService.getByUserDetailId(userDetail.getId());
        PatientCard patientCard;
        if (patientInfo == null) {
            patientInfo = new PatientInfo(userDetail);
            patientCard = new PatientCard(patientInfo);
        } else {
            patientCard = patientInfo.getPatientCard();
        }
        patientInfo.setPatientCard(patientCard);
        patientCard.setPatientInfo(patientInfo);
        userDetailService.update(userDetail);
        patientInfo.setUserDetail(userDetail);
        patientInfoService.add(patientInfo);
        model.addAttribute("edit", false);
        model.addAttribute("userDetail", userDetail);
     //   model.addAttribute("relativesInfo", relativesInfo);
        model.addAttribute("gender", Gender.values());
        model.addAttribute("heartProblems", Disease.values());
        model.addAttribute("diabetes", Disease.values());
        model.addAttribute("epilepsy", Disease.values());
        model.addAttribute("email", PrincipalConverter.getPrincipal());
        return "user/detail";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @RequestMapping(value = {"/user/profile"}, method = RequestMethod.GET)
    public String userProfile(@RequestParam("userId") Long userId, ModelMap model) {
        User user = userService.getById(userId);
        UserDetail userDetail = user.getUserDetails();
     //   RelativesInfo relativesInfo = userDetail.getRelativesInfo();
        model.addAttribute("edit", false);
        model.addAttribute("userDetail", userDetail);

      //  model.addAttribute("relativesInfo", relativesInfo);
        model.addAttribute("gender", Gender.values());
     //   model.addAttribute("heartProblems", Disease.values());
     //   model.addAttribute("diabetes", Disease.values());
      //  model.addAttribute("epilepsy", Disease.values());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("read", true);
        return "user/detail";
    }

//    @RequestMapping("/doctors")
//    public String renderSearchDoctors(Map<String, Object> model,
//                                  @RequestParam(value = "d", required = false) String query) throws ParseException, InterruptedException, HospitalControllerAdvice.FilterHospitalListEmptyException {
//        if (query != null && !query.isEmpty()) {
//            this.pageableContent = doctorInfoService.advancedDoctorSearch(query);
//        }
//        this.initializeModel(model, 1);
////        if(this.pageableContent.getResultListCount() == 0){
////            throw new HospitalControllerAdvice.FilterHospitalListEmptyException("Empty list");
////        }
//        return "";
//    }

    private void createRelatieves(UserDetail userDetail){
        if (userDetail.getRelativesInfos().isEmpty()) {
            List<RelativesInfo> relativesInfos = new ArrayList<>();
            relativesInfos.add(new RelativesInfo());
            relativesInfos.add(new RelativesInfo());
            relativesInfos.add(new RelativesInfo());
            userDetail.setRelativesInfos(relativesInfos);
        }
    }

}
