package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.dto.ViewForManagerDTO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.ManagerService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by igortsapyak on 06.06.16.
 */
@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {


    @Autowired
    private DoctorInfoDAO doctorInfoDAO;
    @Autowired
    private HospitalDAO hospitalDAO;
    @Autowired
    private UserDAO userDAO;

    @Override
    public void applyManager(Map<String, Long> hospitalData) {
        User user = userDAO.getById(hospitalData.get("userId"));
        Hospital hospital = hospitalDAO.getById(hospitalData.get("hospitalId"));
        List<User> managers = new ArrayList<>();
        managers.add(user);
        hospital.setManagers(managers);
        hospitalDAO.update(hospital);
    }

    @Override
    public List<Hospital> getHospitalsByManager() {
        return hospitalDAO.getHospitalsByManagerId(userDAO.getByEmail(PrincipalConverter.getPrincipal()).getId());
    }

    @Override
    public  List<DoctorSearchDTO> getDoctorsByManagerAndHospital(Long hospitalId, ViewForManagerDTO viewForManagerDTO) {
        return doctorInfoDAO.findByManagerAndHospitalId(hospitalId, userDAO.getByEmail(PrincipalConverter.getPrincipal()).getId(), viewForManagerDTO);
    }

    @Override
    public List<DoctorSearchDTO> searchDoctors(ViewForManagerDTO viewForManagerDTO, Long hospitalId){
        return doctorInfoDAO.searchDoctorsForManager(viewForManagerDTO, hospitalId, userDAO.getByEmail(PrincipalConverter.getPrincipal()).getId());
    }

    @Override
    public void deleteHospitalManager(Long hospitalId) {
        Hospital hospital = hospitalDAO.getById(hospitalId);
        hospital.getManagers().clear();
        hospitalDAO.update(hospital);
    }

}
