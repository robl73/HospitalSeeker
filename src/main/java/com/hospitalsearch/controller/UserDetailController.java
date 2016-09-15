package com.hospitalsearch.controller;

import com.hospitalsearch.controller.advice.HospitalControllerAdvice;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.UserDetailService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.Gender;
import com.hospitalsearch.util.Page;
import com.hospitalsearch.util.PageConfigDTO;
import com.hospitalsearch.util.PrincipalConverter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserDetailController {
    @Autowired
    UserDetailService userDetailService;

    @Autowired
    UserService userService;

    @Autowired
    DoctorInfoService doctorInfoService;

    private Page pageableContent;

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @RequestMapping(value = {"/save/detail"}, method = RequestMethod.POST)
    public String saveUserDetail(@Valid UserDetail userDetail, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("userDetail", userDetail);
            model.addAttribute("gender", Gender.values());
            model.addAttribute("email", PrincipalConverter.getPrincipal());
            return "user/detail";
        }
        userDetail.setPatientCard(userDetailService.getById(userDetail.getId()).getPatientCard());
        userDetailService.update(userDetail);
        model.addAttribute("edit", false);
        model.addAttribute("userDetail", userDetail);
        model.addAttribute("gender", Gender.values());
        model.addAttribute("email", PrincipalConverter.getPrincipal());
        return "user/detail";
    }

    @PreAuthorize("hasRole('DOCTOR')")
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

public void initializeModel(Map<String,Object> model,Integer page){
    model.put("doctorsdList", this.pageableContent.getDoctorPageList(page));
    model.put("pagination", this.pageableContent.isPaginated());
    model.put("pageCount", this.pageableContent.getPageCount());
    model.put("pageSize", this.pageableContent.getPageSize());
    model.put("currentPage", page);
    model.put("itemNumber", this.pageableContent.getResultListCount());
    model.put("pageConfig",new PageConfigDTO());
    model.put("sortType",this.pageableContent.getSortType());
}
}
