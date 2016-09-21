package com.hospitalsearch.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
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

    @Autowired
    AppointmentService appointmentService;

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static final int TIME_ZONE_OFFSET = 3;

    private ObjectMapper mapper = new ObjectMapper();

    private Map<String, String> data;

    public Map<String, Object> validateAppointment(String appointmentString) {
        Appointment appointment = toAppointment(appointmentString);
        List<Appointment> appointments = appointmentService.getAllByPatientEmail(data.get("principal"));
       
        Map<String, Object> validationInfo = new HashMap<>();
        
        validationInfo.put("result", validate(appointment, appointments));
        validationInfo.put("nextAvailableTime", getFirstAvailibleTime(appointments, appointment));
        return validationInfo;
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

    private String getFirstAvailibleTime(final List<Appointment> appointments, final Appointment appointment) {
        List<Appointment> sortedAppointments = appointments.stream().sorted((o1, o2) -> o2.getStart_date()
                .compareTo(o1.getStart_date())).collect(Collectors.toList());
       
        Collections.reverse(sortedAppointments);
        Appointment appointment2 = sortedAppointments.get(sortedAppointments.size() - 1);
        return appointment2.getEnd_date().toString();

    }
}
