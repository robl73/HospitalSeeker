package com.hospitalsearch.controller;

import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.Page;
import com.hospitalsearch.util.PageConfigDTO;
import com.hospitalsearch.util.PrincipalConverter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by lesia on 19.09.2016.
 */
@Controller
public class DoctorController {

    @Autowired
    UserService userService;
    @Autowired
    DoctorInfoService doctorInfoService;

     @RequestMapping("/doctors")
    public String renderSearchDoctors(Map<String, Object> model,
                                  @RequestParam(value = "d", required = false) String query) throws ParseException, InterruptedException {
         Page pageableContent = null;
        if (query != null && !query.isEmpty()) {
            pageableContent = doctorInfoService.advancedDoctorSearch(query);
            pageableContent.setPageSize(3);
        }
            this.initializeModel(model, pageableContent, 1, query);
            if (pageableContent.getResultListCount() == 0){
                User user = userService.getByEmail(PrincipalConverter.getPrincipal());
                ModelAndView view = new ModelAndView("error/emptyList");
                view.addObject("userName", user);
                return "error/emptyList";
            }
        return "paginatedLayout";
    }

    @RequestMapping("/doctors/page/{page}/params")
    public String renderHospitalsByPage(Map<String, Object> model,
                                        @PathVariable("page") Integer currentPage,
                                        @RequestParam("itemsPerPage") Integer pageSize,
                                        @RequestParam("currentSearchQuery") String currentSearchQuery
    ) throws ParseException, InterruptedException {
        Page pageableContent = doctorInfoService.advancedDoctorSearch(currentSearchQuery);
        pageableContent.setPageSize(pageSize);
        this.initializeModel(model, pageableContent, currentPage, currentSearchQuery);
        return "paginatedLayout";
    }

    public void initializeModel(Map<String,Object> model,Page pageableContent, Integer page, String query){

        model.put("currentSearchQuery", query);
        model.put("pagedList",  this.doctorInfoService.converToDoctorSearchDTO((List<DoctorInfo>) pageableContent.getPageList(page)));
        model.put("pagination", pageableContent.isPaginated());
        model.put("pageCount", pageableContent.getPageCount());
        model.put("itemsPerPage", pageableContent.getPageSize());
        model.put("currentPage", page);
        model.put("itemsNumber", pageableContent.getResultListCount());
        model.put("type", "doctors");
}
}
