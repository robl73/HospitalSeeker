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

    private Page pageableContent;

        @RequestMapping("/doctors")
    public String renderSearchDoctors(Map<String, Object> model,
                                  @RequestParam(value = "q", required = false) String query) throws ParseException, InterruptedException {
        if (query != null && !query.isEmpty()) {
            this.pageableContent = doctorInfoService.advancedDoctorSearch(query);
            this.pageableContent.setPageSize(3);
        }
            this.initializeModel(model, 1, query);
            if (this.pageableContent.getResultListCount() == 0){
                User user = userService.getByEmail(PrincipalConverter.getPrincipal());
                ModelAndView view = new ModelAndView("error/emptyList");
                view.addObject("userName", user);
                return "error/emptyList";
            }
        return "paginatedLayout";
    }

    @RequestMapping("/doctors/page/{page}/prams")
    public String renderHospitalsByPage(Map<String, Object> model,
                                        @PathVariable("page") Integer currentPage,
                                        @RequestParam("type") String type,
                                        @RequestParam("itemsPerPage") Integer pageSize,
                                        @RequestParam("currentSearchQuery") String currentSearchQuery
    ) throws ParseException, InterruptedException {
        this.pageableContent = doctorInfoService.advancedDoctorSearch(currentSearchQuery);
        this.pageableContent.setPageSize(pageSize);
        this.initializeModel(model, currentPage, currentSearchQuery);
        return "paginatedLayout";
    }

    public void initializeModel(Map<String,Object> model,Integer page, String query){

        model.put("currentSearchQuery", query);
        model.put("pagedList",  this.doctorInfoService.converToDoctorSearchDTO((List<DoctorInfo>)this.pageableContent.getPageList(page)));
        model.put("pagination", this.pageableContent.isPaginated());
        model.put("pageCount", this.pageableContent.getPageCount());
        model.put("itemsPerPage", this.pageableContent.getPageSize());
        model.put("currentPage", page);
        model.put("itemsNumber", this.pageableContent.getResultListCount());
        model.put("type", "doctors");
}
}
