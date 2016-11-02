/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.TranslatorDAO;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.DepartmentInfo;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.Language;
import com.hospitalsearch.entity.Translator;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.service.LanguageService;
import com.hospitalsearch.service.TranslatorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class TranslatorServiceImpl implements TranslatorService{
    
    @Autowired
    private TranslatorDAO dao;
    
    @Autowired
    private HospitalService hospitalService;
    
    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private LanguageService languageService;

    @Override
    public void save(Translator newTranslator) {
        dao.save(newTranslator);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Translator translator) {
        dao.delete(translator);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Translator updatedTranslator) {
        dao.update(updatedTranslator);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Translator getById(Long id) {
       return dao.getById(id);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Translator> getAll() {
        return dao.getAll();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Translator createByIds(Long hid, Long did, Long lid) {
        
        Hospital hospital = hospitalService.getById(hid);
        Department department = departmentService.getById(did); 
        Language language = languageService.getById(lid);      
        Translator translator = new Translator(hospital, department, language);
        
        return translator;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Translator create(Hospital hospital, Department department, Language language, DepartmentInfo departmentInfo) {
        
        Translator translator = new Translator(hospital, department, language, departmentInfo);
        return translator;
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Translator create(Hospital hospital, Department department, Language language) {
        Translator translator = new Translator(hospital, department, language);
        return translator;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Translator create(Hospital hospital, Department department, DepartmentInfo departmentInfo) {
        Translator translator = new Translator(hospital, department, departmentInfo);
        return translator;
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Translator create(Hospital hospital, Department department) {
        Translator translator = new Translator(hospital, department);
        return translator;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Translator createByDepInfoIds(Long hid, Long did, Long lid, DepartmentInfo departmentInfo) {
        Hospital hospital = hospitalService.getById(hid);
        Department department = departmentService.getById(did); 
        Language language = languageService.getById(lid);      
        Translator translator = new Translator(hospital, department, language, departmentInfo);   
        return translator; 
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
