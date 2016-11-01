/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.dao.impl;

import com.hospitalsearch.entity.DepartmentName;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.hospitalsearch.dao.DepartmentNameDAO;

/**
 *
 * @author Admin
 */
@Repository
public class DepartmentNameDAOImpl extends GenericDAOImpl<DepartmentName,Long> implements DepartmentNameDAO{
    @Autowired(required = true)
    public DepartmentNameDAOImpl(SessionFactory factory){
        this.setSessionFactory(factory);
    }
}
