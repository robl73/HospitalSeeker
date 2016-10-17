package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.Appointment;

/**
 * Created by Volodymyr Paladiuk on 17.09.16.
 */
@Transactional
public interface AppointmentValidationService {
	
	boolean validateAppointment(String appointmentString);
	
	Appointment toAppointment(String appointmentString);
		
	boolean validate(Appointment newAppointment, List<Appointment> patientAppointments);

}
