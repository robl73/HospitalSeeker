package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.DepartmentDAO;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by deplague on 5/11/16.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    
    private final Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);
    
    @Autowired
    private DepartmentDAO departmentdao;

    @Override
    public void save(Department newDepartment) {
        departmentdao.save(newDepartment);
    }

    @Override
    public void delete(Department department) {
        departmentdao.delete(department);
    }

    @Override
    public void update(Department updatedDepartment) {
        departmentdao.update(updatedDepartment);
    }

    @Override
    public Department getById(Long id) {
        return departmentdao.getById(id);
    }
 
    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        try{
            departments=departmentdao.getAll();
            logger.info("Get all departments!");           
        } catch(Exception e){
            logger.error("Error getting all departments", e);
        }
        return departments;
    }

    
    @Override
    public List<Department> findByHospitalId(Long id) {
        return departmentdao.findByHospitalId(id);
    }
}
