package com.hospitalsearch.dao;

import com.hospitalsearch.dto.DoctorDTO;
import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.dto.ViewForManagerDTO;
import com.hospitalsearch.entity.DoctorInfo;
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

    List<DoctorSearchDTO> findByManagerAndHospitalId(Long hospitalId, Long managerId, ViewForManagerDTO wievForManagerDTO);

    List<DoctorSearchDTO> searchDoctorsForManager(ViewForManagerDTO viewForManagerDTO, Long hospitalId, Long managerId);

    Long getIdByUserDetail(Long userDetailId);

    Page<DoctorInfo> advancedDoctorSearch(String query) throws ParseException, InterruptedException;

    DoctorInfo getByEmail(String email);

    DoctorInfo getByUserDetailId(Long userDetailId);

}
