package com.hospitalsearch.controller;

import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.dto.ViewForManagerDTO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Autowired
    private DoctorInfoService doctorInfoService;

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
