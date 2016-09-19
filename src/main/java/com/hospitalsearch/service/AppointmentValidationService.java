package com.hospitalsearch.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.service.AppointmentService;

/**
 * Created by Volodymyr Paladiuk on 17.09.16.
 */
@Transactional
public interface AppointmentValidationService {
	
	boolean validateAppointment(String appointmentString);
	
	Appointment toAppointment(String appointmentString);
		
	boolean validate(Appointment newAppointment, List<Appointment> patientAppointments);

}
