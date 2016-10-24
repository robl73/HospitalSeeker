package com.hospitalsearch.dao;

import com.hospitalsearch.dto.NameDepartmensByHospitalDTO;
import com.hospitalsearch.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Component
public interface DepartmentDAO extends GenericDAO<Department,Long>{

    List<Department> findByHospitalId(Long id);

    List<Department> findDepartmentByDoctorId(Long id);
}
