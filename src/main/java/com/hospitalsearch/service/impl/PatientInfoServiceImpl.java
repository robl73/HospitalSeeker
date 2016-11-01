package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.PatientInfoDAO;
import com.hospitalsearch.dao.RelativesInfoDAO;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dao.UserDetailDAO;
import com.hospitalsearch.dto.PatientDetailDTO;
import com.hospitalsearch.entity.*;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.PatientInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ruslan on 13.09.16.
 */
@Service
@Transactional
public class PatientInfoServiceImpl implements PatientInfoService {

    final static Logger log = Logger.getLogger(PatientInfoService.class);

    @Autowired
    private PatientInfoDAO patientInfoDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserDetailDAO userDetailDAO;

    @Autowired
    private RelativesInfoDAO relativesInfoDAO;

    @Override
    public void save(PatientInfo patientInfo) {
        patientInfoDAO.save(patientInfo);
    }

    @Override
    public void delete(PatientInfo patientInfo) {
        patientInfoDAO.delete(patientInfo);
    }

    @Override
    public void update(PatientInfo patientInfo) {
        patientInfoDAO.update(patientInfo);
    }

    @Override
    public PatientInfo getById(Long id) {
        return patientInfoDAO.getById(id);
    }

    @Override
    public PatientInfo getByUserDetailId(Long id) {
        return patientInfoDAO.getByUserDetailId(id);
    }

    @Override
    public PatientDetailDTO getPatientProfileByEmail(String email, PatientDetailDTO patientDetailDTO){
        User  user = userDAO.getByEmail(email);
        UserDetail userDetail = user.getUserDetails();
        PatientInfo patientInfo = patientInfoDAO.getByUserDetailId(userDetail.getId());
        patientDetailDTO.setId(patientInfo.getId());
        patientDetailDTO.setImagePath(userDetail.getImagePath());
        patientDetailDTO.setFirstName(userDetail.getFirstName());
        patientDetailDTO.setLastName(userDetail.getLastName());
        patientDetailDTO.setBirthDate(userDetail.getBirthDate());
        patientDetailDTO.setPhone(userDetail.getPhone());
        patientDetailDTO.setAddress(userDetail.getAddress());
        patientDetailDTO.setGender(userDetail.getGender());
        patientDetailDTO.setHeight(patientInfo.getHeight());
        patientDetailDTO.setWeight(patientInfo.getWeight());
        patientDetailDTO.setBloodType(patientInfo.getBloodGroup());
        patientDetailDTO.setEyeColor(patientInfo.getEyeColor());
        patientDetailDTO.setHairColor(patientInfo.getHairColor());
        patientDetailDTO.setEmail(user.getEmail());
        patientDetailDTO.setAllergies(patientInfo.getAllergies());
        patientDetailDTO.setCurrentMedication(patientInfo.getCurrentMedication());
        patientDetailDTO.setHeartProblems(patientInfo.getHeartProblems());
        patientDetailDTO.setDiabetes(patientInfo.getDiabetes());
        patientDetailDTO.setEpilepsy(patientInfo.getEpilepsy());
        patientDetailDTO.setRestrictions(patientInfo.getRestrictions());
        patientDetailDTO.setRelativesInfos(patientInfo.getRelativesInfos());

        return patientDetailDTO;
    }

    @Override
    public void updatePatientProfile(PatientDetailDTO patientDetailDTO) {
        User  user = userDAO.getByEmail(patientDetailDTO.getEmail());
        user.setEmail(patientDetailDTO.getEmail());
        userDAO.updateUser(user);
        UserDetail userDetail = user.getUserDetails();
        userDetail.setImagePath(patientDetailDTO.getImagePath());
        userDetail.setFirstName(patientDetailDTO.getFirstName());
        userDetail.setLastName(patientDetailDTO.getLastName());
        userDetail.setBirthDate(patientDetailDTO.getBirthDate());
        userDetail.setPhone(patientDetailDTO.getPhone());
        userDetail.setAddress(patientDetailDTO.getAddress());
        userDetail.setGender(patientDetailDTO.getGender());
        userDetail.setUser(user);
        userDetailDAO.update(userDetail);
        PatientInfo patientInfo = patientInfoDAO.getByUserDetailId(userDetail.getId());
        patientInfo.setHeight(patientDetailDTO.getHeight());
        patientInfo.setWeight(patientDetailDTO.getWeight());
        patientInfo.setBloodGroup(patientDetailDTO.getBloodType());
        patientInfo.setEyeColor(patientDetailDTO.getEyeColor());
        patientInfo.setHairColor(patientDetailDTO.getHairColor());
        patientInfo.setAllergies(patientDetailDTO.getAllergies());
        patientInfo.setCurrentMedication(patientDetailDTO.getCurrentMedication());
        patientInfo.setHeartProblems(patientDetailDTO.getHeartProblems());
        patientInfo.setDiabetes(patientDetailDTO.getDiabetes());
        patientInfo.setEpilepsy(patientDetailDTO.getEpilepsy());
        patientInfo.setRestrictions(patientDetailDTO.getRestrictions());
        List<RelativesInfo> relativesInfoList = patientDetailDTO.getRelativesInfos();
        for (RelativesInfo relativesInfo: relativesInfoList){
            relativesInfo.setPatientInfo(patientInfo);
            relativesInfoDAO.updateRelativesInfo(relativesInfo);
        }
       patientInfo.setRelativesInfos(relativesInfoList);
        patientInfo.setUserDetail(userDetail);
        update(patientInfo);

    }

     @Override
    public List<PatientInfo> getAll() {
        return patientInfoDAO.getAll();
    }

    @Override
    public PatientInfo add(PatientInfo patientInfo) {
        try{
            patientInfo = patientInfoDAO.add(patientInfo);
            log.info("Save patientinfo" + patientInfo);
        }catch (Exception e){
            log.error("Saving patientinfo" + patientInfo);
        }
        return patientInfo;
    }

}
