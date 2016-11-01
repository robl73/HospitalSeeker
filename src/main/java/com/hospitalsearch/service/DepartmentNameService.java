/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.service;

import com.hospitalsearch.entity.DepartmentName;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Transactional
public interface DepartmentNameService {
    
    void save(DepartmentName newDepartmentName);
    void delete(DepartmentName departmentName);
    void update(DepartmentName updatedDepartmentName);
    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    DepartmentName getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<DepartmentName> getAll();
    
     
    
}
