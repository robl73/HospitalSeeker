package com.hospitalsearch.service;

import com.hospitalsearch.dto.DoctorDTO;
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

    List<DoctorDTO> getDoctorsByManager();

    void deleteHospitalManager(Long hospitalId);

}
