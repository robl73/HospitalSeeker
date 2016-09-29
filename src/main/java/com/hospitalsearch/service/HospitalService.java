package com.hospitalsearch.service;

import com.hospitalsearch.dto.Bounds;
import com.hospitalsearch.dto.HospitalDTO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.Page;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @author Pavlo Kuz
 * edited by Oleksandr Mukonin
 */
@Component
@Transactional
public interface HospitalService {
    void save(Hospital newHospital);
    void save(HospitalDTO hospitalDTO);
    void delete(Hospital hospital);
    void update(Hospital updatedHospital);
    void update(HospitalDTO updatedHospitalDTO);
    HospitalDTO toHospitalDTO(Hospital hospital);
    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    Hospital getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<Hospital> getAll();
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<Hospital> getAllByBounds(Bounds bounds);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    Page<Hospital> advancedHospitalSearch(String args) throws ParseException, InterruptedException;
}