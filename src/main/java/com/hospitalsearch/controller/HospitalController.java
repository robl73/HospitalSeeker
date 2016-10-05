package com.hospitalsearch.controller;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.hospitalsearch.service.UserService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospitalsearch.controller.advice.HospitalControllerAdvice;
import com.hospitalsearch.controller.advice.HospitalControllerAdvice.FilterHospitalListEmptyException;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.Page;

@Controller
public class HospitalController {

    @Autowired
    private HospitalService service;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorInfoService doctorInfoService;

    @Autowired
    UserService userService;
    
    @RequestMapping("/")
    public String renderIndex(Map<String, Object> model) {
        return "layout";
    }

    @RequestMapping("/hospitals")
    public String renderHospitals(Map<String, Object> model,
            @RequestParam(value = "q", required = false) String query) throws ParseException, InterruptedException, FilterHospitalListEmptyException {
        Page pageableContent = null;
        if (query != null && !query.isEmpty()) {
                pageableContent = service.advancedHospitalSearch(query);
                pageableContent.setPageSize(3);
        }
        this.initializeModel(model,pageableContent, 1, query);
        if(pageableContent.getResultListCount() == 0){
        	throw new HospitalControllerAdvice.FilterHospitalListEmptyException("Empty list");
        }
        return "paginatedLayout";
    }

    @RequestMapping(value = "/hospitals/filter", method = RequestMethod.POST)
    public String renderFilteredHospitalsByPage(
            @Valid
            @ModelAttribute("filter") HospitalFilterDTO dto,
            BindingResult results,
            Map<String, Object> model) throws Exception {

        List<Hospital> hospitals = service.filterHospitalsByAddress(dto);
        if (hospitals.isEmpty()) {
            throw new HospitalControllerAdvice.FilterHospitalListEmptyException("Problem");
        } else {
            model.put("pagination", true);
            if (results.hasErrors()) {
                return "paginatedLayout";
            }
            return "paginatedLayout";
        }
    }

    @RequestMapping("/hospitals/page/{page}/params")
    public String renderHospitalsByPage(Map<String, Object> model,
            @PathVariable("page") Integer currentPage,
            @RequestParam("itemsPerPage") Integer pageSize,
            @RequestParam("currentSearchQuery") String currentSearchQuery
    ) throws ParseException, InterruptedException {
            Page pageableContent = service.advancedHospitalSearch(currentSearchQuery);
            pageableContent.setPageSize(pageSize);
            initializeModel(model, pageableContent, currentPage, currentSearchQuery);
            return "paginatedLayout";
    }

    @RequestMapping("/hospital/{id}")
    public String renderDepartments(Map<String, Object> model,
            @PathVariable Long id
    ) {
        List<Department> lst = departmentService.findByHospitalId(id);

        model.put("departments", lst);
        model.put("hospital", service.getById(id));
        model.put("hid", id);
        return "departments";
    }
    @RequestMapping("/hospital/{hid}/department/{id}")
    public String renderDoctors(Map<String, Object> model,
            @PathVariable Long hid,
            @PathVariable Long id
    ) {
        Department d = departmentService.getById(id);
        model.put("doctors", doctorInfoService.findByDepartmentId(id));
        model.put("department", d);
        model.put("hospital", d.getHospital());

        model.put("hid", hid);
        model.put("id", id);

        return "doctors";
    }
    
    public void initializeModel(Map<String,Object> model, Page pageableContent, Integer page, String query){
        model.put("currentSearchQuery", query);
    	model.put("pagedList", pageableContent.getPageList(page));
        model.put("pagination", pageableContent.isPaginated());
        model.put("pageCount", pageableContent.getPageCount());
        model.put("itemsPerPage", pageableContent.getPageSize());
        model.put("currentPage", page);
        model.put("itemsNumber", pageableContent.getResultListCount());
        model.put("type", "hospitals");
    }
}
