/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.DepartmentsNameDAO;
import com.hospitalsearch.entity.DepartmentsName;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public class DepartmentsNameDAOImpl extends GenericDAOImpl<DepartmentsName,Long> implements DepartmentsNameDAO{
    @Autowired(required = true)
    public DepartmentsNameDAOImpl(SessionFactory factory){
        this.setSessionFactory(factory);
    }
}
