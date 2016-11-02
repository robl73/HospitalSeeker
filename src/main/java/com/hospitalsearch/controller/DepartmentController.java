/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.controller;

import com.hospitalsearch.entity.Department;
import com.hospitalsearch.service.DepartmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping(value="manager/manageDepartments")
public class DepartmentController {
    
    @Autowired
    DepartmentService departmentService;

//---------------------------------------------------------------    

//--------------------------------------------------------------- 
    @RequestMapping(value ="", method = RequestMethod.GET)
    public String departments(){
        return "manager/manageDepartments";
    }
    
    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value ="/{hospitalid}", method = RequestMethod.GET )
    public String departments(@PathVariable("hospitalid") Long hospitalid , 
                              Model model){
        List<Department> lst = departmentService.findByHospitalId(hospitalid);
        model.addAttribute("departments", lst);
        model.addAttribute("hospitalid", hospitalid);
        return "manager/manageDepartments";
    }
    

 //---------------------------------------------------------------------
    

 //--------------------------------------------------------------------  
    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/deleteDepartment", method = RequestMethod.GET, params="departmentID")
    public String deleteDepartment(@RequestParam Long departmentID){    
        departmentService.delete(departmentService.getById(departmentID));
        return "redirect:/";
    } 
}
