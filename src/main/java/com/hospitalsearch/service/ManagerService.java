package com.hospitalsearch.service;

import com.hospitalsearch.dto.DoctorDTO;
import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.dto.ViewForManagerDTO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.UserDetail;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by igortsapyak on 06.06.16.
 */

@Component
@Transactional
public interface ManagerService {

    void applyManager(Map<String, Long> hospitalData);

    List<Hospital> getHospitalsByManager();

    List<DoctorSearchDTO> getDoctorsByManagerAndHospital(Long hospitalId, ViewForManagerDTO viewForManagerDTO);

    List<DoctorSearchDTO> searchDoctors(ViewForManagerDTO viewForManagerDTO, Long hospitalId);

    void deleteHospitalManager(Long hospitalId);

}
