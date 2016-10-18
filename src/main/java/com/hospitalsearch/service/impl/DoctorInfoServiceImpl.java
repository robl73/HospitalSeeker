package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.DepartmentDAO;
import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dto.DoctorDTO;
import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.util.Page;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deplague on 5/10/16.
 */
@Service
public class DoctorInfoServiceImpl implements DoctorInfoService {
    @Autowired
    private DoctorInfoDAO doctorInfoDAO;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Override
    public void save(DoctorInfo newDoctor) {
        doctorInfoDAO.save(newDoctor);
    }

    @Override
    public void delete(DoctorInfo doctor) {
        doctorInfoDAO.delete(doctor);
    }

    @Override
    public void update(DoctorInfo updatedDoctor) {
        doctorInfoDAO.update(updatedDoctor);
    }

    @Override
    public Long getIdByUserDetail(Long userDetailId) {
        return doctorInfoDAO.getIdByUserDetail(userDetailId);
    }

    @Override
    public DoctorInfo getById(Long id) {
        return doctorInfoDAO.getById(id);
    }

    @Override
    public List<DoctorInfo> getAll() {
        return doctorInfoDAO.getAll();
    }

    @Override
    public List<DoctorDTO> findByDepartmentId(Long id) {
        return doctorInfoDAO.findByDepartmentId(id);
    }

    @Override
    public List<DoctorSearchDTO> converToDoctorSearchDTO(List<DoctorInfo> doctorInfoList){
        List<DoctorSearchDTO> resultList = new ArrayList<>();
        for(DoctorInfo docInfo: doctorInfoList) {
          //  List<Department> doctorDepartments = departmentDAO.findDepartmentByDoctorId(docInfo.getId());
            List<Department> doctorDepartments = docInfo.getDepartments();
            List<String> doctorHospitals = new ArrayList<>();
            for (Department department : doctorDepartments) {
                doctorHospitals.add(department.getHospital().getName());
            }
            resultList.add(new DoctorSearchDTO(docInfo.getId(), docInfo.getUserDetails().getImagePath(),
                    docInfo.getUserDetails().getFirstName(),
                    docInfo.getUserDetails().getLastName(),
                    docInfo.getUserDetails().getUser().getEmail(),
                    docInfo.getSpecialization(),
                    docInfo.getCategory(),
                    doctorHospitals));
        }
        return resultList;
    }

    @Override
    public Page<DoctorInfo> advancedDoctorSearch(String query) throws ParseException, InterruptedException{
        return doctorInfoDAO.advancedDoctorSearch(query);
    }

    public DoctorInfo getByUserDetailId(Long userDetailId) {
        return doctorInfoDAO.getByUserDetailId(userDetailId);
    }

}
