package com.hospitalsearch.dao;

import com.hospitalsearch.entity.RelativesInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Asus on 30.10.2016.
 */
@Component
public interface RelativesInfoDAO extends GenericDAO <RelativesInfo,Long> {

    void saveRelativesInfo(RelativesInfo instance);

    void deleteRelativesInfo(RelativesInfo instance);

    void updateRelativesInfo(RelativesInfo instance);

    RelativesInfo getByRelativesInfoId(Long id);

    List<RelativesInfo> getAllRelativesInfo();
}
