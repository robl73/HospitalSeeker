package com.hospitalsearch.service;

import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.util.Page;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by deplague on 5/10/16.
 */
@Transactional
public interface DoctorInfoService {

    void save(DoctorInfo newDoctor);

    void delete(DoctorInfo doctor);

    void update(DoctorInfo updatedDoctor);

    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    DoctorInfo getById(Long id);

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<DoctorInfo> getAll();

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<UserDetail> findByDepartmentId(Long id);

    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    Page<DoctorSearchDTO> advancedDoctorSearch(String query) throws ParseException, InterruptedException;
}