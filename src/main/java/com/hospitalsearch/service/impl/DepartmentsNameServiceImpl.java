/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.DepartmentsNameDAO;
import com.hospitalsearch.entity.DepartmentsName;
import com.hospitalsearch.service.DepartmentsNameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */

@Service
public class DepartmentsNameServiceImpl implements DepartmentsNameService{
    
    @Autowired
    private DepartmentsNameDAO dao;

    @Override
    public void save(DepartmentsName newDepartmentsName) {
        dao.save(newDepartmentsName);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(DepartmentsName departmentsName) {
        dao.delete(departmentsName);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(DepartmentsName updatedDepartmentsName) {
        dao.update(updatedDepartmentsName);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DepartmentsName getById(Long id) {
        return dao.getById(id);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DepartmentsName> getAll() {
        return dao.getAll();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
