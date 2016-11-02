package com.hospitalsearch.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.PatientInfo;
import com.hospitalsearch.entity.RelativesInfo;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.AppointmentService;
import com.hospitalsearch.service.PatientInfoService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Volodymyr Paladiuk on 17.09.16.
 */
@Service
public class AppointmentValidationService {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static final int TIME_ZONE_OFFSET = 2;

    private static final String NEXT_AVAILABLE_TIME = "nextAvailableTime";

    private static final String RESULT = "result";

    private static final String FULL_USER_DETAIL_PRESENT = "fullUserDetailPresent";

    private static final String PRINCIPAL = "principal";

    private static final String BLANK_LINE = "";

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    UserService userService;

    @Autowired
    PatientInfoService patientInfoService;

    private ObjectMapper mapper = new ObjectMapper();

    private Map<String, String> data;

    public Map<String, Object> validateAppointment(String appointmentString) {
        Appointment appointment = toAppointment(appointmentString);
        List<Appointment> appointments = appointmentService.getAllByPatientEmail(data.get(PRINCIPAL));

        Map<String, Object> validationInfo = new HashMap<>();
        validationInfo.put(RESULT, validate(appointment, appointments));
        //check if patient has appointment
        validationInfo.put(NEXT_AVAILABLE_TIME, getFirstAvailableTime(appointments, appointment));
        PatientInfo patientInfo = patientInfoService.getByUserDetailId(userService.getByEmail(data.get(PRINCIPAL)).getUserDetails().getId());
        validationInfo.put(FULL_USER_DETAIL_PRESENT,
                checkFullUserDetailPresent(patientInfo));
        return validationInfo;
    }

    private boolean checkFullUserDetailPresent(PatientInfo patientInfo) {
        UserDetail userDetails = patientInfo.getUserDetail();
        if (userDetails.getFirstName() == null ||
                userDetails.getBirthDate() == null ||
                userDetails.getPhone() == null ||
                userDetails.getAddress() == null ||
                userDetails.getGender() == null ||
                patientInfo.getHeight() == null ||
                patientInfo.getWeight() == null ||
                patientInfo.getBloodGroup() == null ||
                patientInfo.getEyeColor() == null ||
                patientInfo.getHairColor() == null ||
                patientInfo.getAllergies() == null ||
                patientInfo.getCurrentMedication() == null ||
                patientInfo.getHeartProblems() == null ||
                patientInfo.getDiabetes() == null ||
                patientInfo.getEpilepsy() == null
                ){
            return false;
        }
        return true;
    }

    private boolean validateRelativesInfos(List<RelativesInfo> relativesInfos) {
        for (RelativesInfo relativesInfo: relativesInfos) {
            if (!validateRelativesInfo(relativesInfo)){
                return false;
            }
        }
        return true;
    }

    private boolean validateRelativesInfo(RelativesInfo relativesInfo) {
        if (relativesInfo.getName() == null ||
                relativesInfo.getPhone() == null ||
                relativesInfo.getRelation() == null){
            return false;
        }
        return true;
    }


    private Appointment toAppointment(String appointmentString) {
        try {
            data = mapper.readValue(appointmentString, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        Appointment appointment = new Appointment();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDateTime startDate = LocalDateTime.parse(data.get("start_date"), formatter).plusHours(TIME_ZONE_OFFSET);
        LocalDateTime endDate = LocalDateTime.parse(data.get("end_date"), formatter).plusHours(TIME_ZONE_OFFSET);
        appointment.setStart_date(startDate);
        appointment.setEnd_date(endDate);
        return appointment;
    }

    private boolean validate(Appointment newAppointment, List<Appointment> patientAppointments) {
        if(patientAppointments.isEmpty()){
            return true;
        }
        for (Appointment appointment : patientAppointments) {
            if (newAppointment.getStart_date().equals(appointment.getStart_date()) || newAppointment.getEnd_date()
                    .equals(appointment.getEnd_date())) {
                return false;
            }
            if (newAppointment.getStart_date().isAfter(appointment.getStart_date()) && newAppointment.getStart_date()
                    .isBefore(appointment.getEnd_date())) {

                return false;
            }
            if (newAppointment.getEnd_date().isBefore(appointment.getEnd_date()) && newAppointment.getEnd_date()
                    .isAfter(appointment.getStart_date())) {
                return false;
            }
        }
        return true;
    }

    private String getFirstAvailableTime(final List<Appointment> appointments, final Appointment appointment) {
        if (appointments.isEmpty()){
            return BLANK_LINE;
        }
        List<Appointment> sortedAppointments = appointments.stream().sorted((o1, o2) -> o2.getStart_date()
                .compareTo(o1.getStart_date())).collect(Collectors.toList());

        Collections.reverse(sortedAppointments);
        Appointment appointment2 = sortedAppointments.get(sortedAppointments.size() - 1);//return 0
        return appointment2.getEnd_date().toString();

    }
}
