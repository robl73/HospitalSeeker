package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.RelativesInfoDAO;
import com.hospitalsearch.entity.RelativesInfo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Asus on 30.10.2016.
 */
@Repository
public class RelativesInfoDAOImpl extends GenericDAOImpl<RelativesInfo, Long> implements RelativesInfoDAO{
    @Autowired
    public RelativesInfoDAOImpl(SessionFactory factory) {
        this.setSessionFactory(factory);
    }

    @Override
    public void saveRelativesInfo(RelativesInfo instance) {

    }

    @Override
    public void deleteRelativesInfo(RelativesInfo instance) {

    }
    @Override
    public void updateRelativesInfo(RelativesInfo instance) {
        this.getSessionFactory().getCurrentSession().merge(instance);
    }

    @Override
    public RelativesInfo getByRelativesInfoId(Long id) {
        return null;
    }

    @Override
    public List<RelativesInfo> getAllRelativesInfo() {
        return null;
    }
}
