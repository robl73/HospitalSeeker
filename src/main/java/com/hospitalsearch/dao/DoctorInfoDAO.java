package com.hospitalsearch.dao;

import com.hospitalsearch.dto.DoctorDTO;
import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.util.Page;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by deplague on 5/10/16.
 */

@Component
public interface DoctorInfoDAO extends GenericDAO<DoctorInfo,Long>{

    List<DoctorDTO> findByDepartmentId(Long id);

    List<DoctorDTO> findByManagerId(Long id);

    Long getIdByUserDetail(Long userDetailId);

    Page<DoctorInfo> advancedDoctorSearch(String query) throws ParseException, InterruptedException;

    DoctorInfo getByEmail(String email);

}
