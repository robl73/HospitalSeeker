package com.hospitalsearch.service.impl;

import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.dto.Bounds;
import com.hospitalsearch.dto.HospitalDTO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.Page;

/**
 *
 * @author Pavlo Kuz edited by Oleksandr Mukonin
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalDAO dao;

    @Override
    public void save(Hospital newHospital) {
        dao.save(newHospital);
    }
    
    @Override
    public void delete(Hospital hospital) {
        dao.delete(hospital);
    }

    @Override
    public void update(Hospital updatedHospital) {
        dao.update(updatedHospital);
    }

    @Override
    public Hospital getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<Hospital> getAll() {
        return dao.getAll();
    }

    @Override
    public List<Hospital> getAllByBounds(Bounds bounds) {
        return dao.getAllByBounds(bounds);
    }

    @Override
    public List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo) {
        return dao.filterHospitalsByAddress(filterInfo);
    }

    @Override
    public Page<Hospital> advancedHospitalSearch(String args) throws ParseException, InterruptedException {
        return dao.advancedHospitalSearch(args);
    }

	@Override
	public void save(HospitalDTO hospitalDTO) {
		Hospital newHospital = new Hospital();
    	newHospital.setAddress(hospitalDTO.getAddress());
    	newHospital.setDescription(hospitalDTO.getDescription());
    	newHospital.setImagePath(hospitalDTO.getImagePath());
    	newHospital.setLatitude(hospitalDTO.getLatitude());
    	newHospital.setLongitude(hospitalDTO.getLongitude());
    	newHospital.setName(hospitalDTO.getName());
		save(newHospital);
	}

	@Override
	public void update(HospitalDTO updatedHospitalDTO) {
		Hospital newHospital = getById(updatedHospitalDTO.getId());
    	newHospital.setAddress(updatedHospitalDTO.getAddress());
    	newHospital.setDescription(updatedHospitalDTO.getDescription());
    	newHospital.setImagePath(updatedHospitalDTO.getImagePath());
    	newHospital.setLatitude(updatedHospitalDTO.getLatitude());
    	newHospital.setLongitude(updatedHospitalDTO.getLongitude());
    	newHospital.setName(updatedHospitalDTO.getName());
    	update(newHospital);
	}

	@Override
	public HospitalDTO toHospitalDTO(Hospital hospital) {
		HospitalDTO hospitalDTO = new HospitalDTO();
		hospitalDTO.setId(hospital.getId());
		hospitalDTO.setAddress(hospital.getAddress());
		hospitalDTO.setDescription(hospital.getDescription());
		hospitalDTO.setImagePath(hospital.getImagePath());
		hospitalDTO.setLatitude(hospital.getLatitude());
		hospitalDTO.setLongitude(hospital.getLongitude());
		hospitalDTO.setName(hospital.getName());
		return hospitalDTO;
	}
}
