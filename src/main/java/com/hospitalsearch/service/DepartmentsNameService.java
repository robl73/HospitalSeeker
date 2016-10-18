/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.service;

import com.hospitalsearch.entity.DepartmentsName;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Transactional
public interface DepartmentsNameService {
    
    void save(DepartmentsName newDepartmentsName);
    void delete(DepartmentsName departmentsName);
    void update(DepartmentsName updatedDepartmentsName);
    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    DepartmentsName getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<DepartmentsName> getAll();
    
     
    
}
