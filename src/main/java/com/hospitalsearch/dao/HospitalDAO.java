package com.hospitalsearch.dao;

import java.util.List;

import com.hospitalsearch.dto.NameDepartmensByHospitalDTO;
import com.hospitalsearch.dto.NameHospitalsByManagerDTO;
import com.hospitalsearch.entity.Department;
import org.apache.lucene.queryparser.classic.ParseException;

import com.hospitalsearch.dto.Bounds;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.Page;


/**
 *
 * @author Oleksandr Mukonin,Pavel Kuz
 *
 */
public interface HospitalDAO extends GenericDAO<Hospital, Long> {

    List<Hospital> getAllByBounds(Bounds bounds);

    List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo);

    List<Hospital> getHospitalsByManagerId(Long id);

    Page<Hospital> advancedHospitalSearch(String args) throws ParseException, InterruptedException;

    List<Hospital> getAllHospitalsByManager(Long id);
}
