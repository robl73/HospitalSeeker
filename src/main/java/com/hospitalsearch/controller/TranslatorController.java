/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.controller;

import com.hospitalsearch.dto.TranslatorDTO;
import com.hospitalsearch.entity.DepartmentInfo;
import com.hospitalsearch.entity.Language;
import com.hospitalsearch.entity.Translator;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalsearch.service.LanguageService;
import com.hospitalsearch.service.TranslatorService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
public class TranslatorController {
    
    @Autowired
    TranslatorService translatorService;
    
    @Autowired
    LanguageService languageService;
    
    @Autowired
    DepartmentService departmentService;
    
    @ModelAttribute
    public void populateLanguage(Model model){
        List<Language> lstlan = languageService.getAll();
        model.addAttribute("languages", lstlan);
    return;
    }
    
    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value ="manager/translator" , method = RequestMethod.GET , params={"departmentid","hospitalid"})
    public String translator(@RequestParam(name="departmentid") Long departmentid ,
                             @RequestParam(name="hospitalid") Long hospitalid ,
                             Model model){
        
         model.addAttribute("hospitalid", hospitalid) ;     
         model.addAttribute("departmentid", departmentid);
         
        TranslatorDTO translatorDTO = new TranslatorDTO(hospitalid , departmentid , new Long(1) , new DepartmentInfo());
        model.addAttribute("translatorDTO", translatorDTO);
          
    return "manager/translator";
    }
    
    
    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value="manager/translator" , method = RequestMethod.POST )
    public String translatorPost(@Valid @ModelAttribute(value="translatorDTO") TranslatorDTO translatorDTO,
                                 BindingResult result,
                                 Model model ){
        
        model.addAttribute("translatorDTO", translatorDTO);
        model.addAttribute("hospitalid", translatorDTO.getHospitalid()) ;     
        model.addAttribute("departmentid", translatorDTO.getDepartmentid());
        
        if(result.hasErrors()){
        //return "redirect:translator";
        return "translator";
        }
        
        Translator translator =  translatorService.createByIds(translatorDTO.getHospitalid() ,
                                      translatorDTO.getDepartmentid() ,
                                      translatorDTO.getLanguageid());
        translator.setDepartmentinfo(translatorDTO.getDepartmentinfo());
        
        translatorService.save(translator);
        
        return "redirect:translator";

    }

}

