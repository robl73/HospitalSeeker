/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.service;

import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.DepartmentInfo;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.Language;
import com.hospitalsearch.entity.Translator;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Transactional
public interface TranslatorService {
    
    void save(Translator newTranslator);
    void delete(Translator translator);
    void update(Translator updatedTranslator);
    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    Translator getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<Translator> getAll();
    
    Translator createByIds(Long hid , Long did ,Long lid) ;
    Translator create(Hospital hospital, Department department, Language language, DepartmentInfo departmentInfo);
    Translator create(Hospital hospital, Department department, Language language);
    Translator create(Hospital hospital, Department department, DepartmentInfo departmentInfo);
    Translator create(Hospital hospital, Department department);
    
    Translator createByDepInfoIds(Long hid , Long did ,Long lid ,DepartmentInfo departmentInfo) ;
}
