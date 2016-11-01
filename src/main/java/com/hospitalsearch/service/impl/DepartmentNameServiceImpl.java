/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.service.impl;

import com.hospitalsearch.entity.DepartmentName;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hospitalsearch.service.DepartmentNameService;
import com.hospitalsearch.dao.DepartmentNameDAO;

/**
 *
 * @author Admin
 */

@Service
public class DepartmentNameServiceImpl implements DepartmentNameService{
    
    @Autowired
    private DepartmentNameDAO dao;

    @Override
    public void save(DepartmentName newDepartmentName) {
        dao.save(newDepartmentName);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(DepartmentName departmentName) {
        dao.delete(departmentName);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(DepartmentName updatedDepartmentName) {
        dao.update(updatedDepartmentName);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DepartmentName getById(Long id) {
        return dao.getById(id);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DepartmentName> getAll() {
        return dao.getAll();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
