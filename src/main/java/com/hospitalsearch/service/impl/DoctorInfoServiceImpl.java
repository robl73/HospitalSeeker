package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.DepartmentDAO;
import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dto.DoctorSearchDTO;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.UserDetail;
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
    private DoctorInfoDAO dao;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Override
    public void save(DoctorInfo newDoctor) {
        dao.save(newDoctor);
    }

    @Override
    public void delete(DoctorInfo doctor) {
        dao.delete(doctor);
    }

    @Override
    public void update(DoctorInfo updatedDoctor) {
        dao.update(updatedDoctor);
    }

    @Override
    public DoctorInfo getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<DoctorInfo> getAll() {
        return dao.getAll();
    }

    @Override
    public List<UserDetail> findByDepartmentId(Long id) {
        return dao.findByDepartmentId(id);
    }

    @Override
    public List<DoctorSearchDTO> converToDoctorSearchDTO(List<DoctorInfo> doctorInfoList){
        List<DoctorSearchDTO> resultList = new ArrayList<>();
        System.out.println("+++++++"+doctorInfoList+"++++++++++++");
        for(DoctorInfo docInfo: doctorInfoList) {
          //  List<Department> doctorDepartments = docInfo.getDepartments();
            List<Department> doctorDepartments = departmentDAO.findDepartmentByDoctorId(docInfo.getId());
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
        return dao.advancedDoctorSearch(query);
    }
}
