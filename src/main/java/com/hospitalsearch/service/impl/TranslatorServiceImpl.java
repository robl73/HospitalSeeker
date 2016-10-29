/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.TranslatorDAO;
import com.hospitalsearch.entity.Translator;
import com.hospitalsearch.service.TranslatorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Admin
 */
public class TranslatorServiceImpl implements TranslatorService{
    
    @Autowired
    private TranslatorDAO dao;

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
    
}
