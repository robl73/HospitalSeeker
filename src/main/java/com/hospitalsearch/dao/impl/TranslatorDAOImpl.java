/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.TranslatorDAO;
import com.hospitalsearch.entity.DepartmentName;
import com.hospitalsearch.entity.Translator;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public class TranslatorDAOImpl  extends GenericDAOImpl<Translator,Long> implements TranslatorDAO {
    @Autowired(required = true)
        public TranslatorDAOImpl(SessionFactory factory){
         this.setSessionFactory(factory);
    }

    
}
