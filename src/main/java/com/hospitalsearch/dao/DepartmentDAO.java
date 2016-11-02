package com.hospitalsearch.dao;

import com.hospitalsearch.entity.Department;

import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Created by deplague on 5/11/16.
 */
//@Component
@Repository
public interface DepartmentDAO extends GenericDAO<Department,Long>{

    List<Department> findByHospitalId(Long id);

    List<Department> findDepartmentByDoctorId(Long id);
}
