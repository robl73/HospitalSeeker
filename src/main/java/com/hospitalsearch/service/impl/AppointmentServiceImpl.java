package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.AppointmentDAO;
import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dao.UserDetailDAO;
import com.hospitalsearch.dto.AppointmentDto;
import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by igortsapyak on 04.05.16.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserDetailDAO userDetailDAO;
    @Autowired
    private AppointmentDAO appointmentDAO;
    @Autowired
    private DoctorInfoDAO doctorInfoDAO;


    @Override
    public void actionControl(Map<String, String[]> appointmentParams, Long doctorId, String principal) {
        UserDetail userDetail = userDetailDAO.getById(userDAO.getByEmail(principal).getId());
        DoctorInfo doctorInfo = null;
        if (doctorId != null) {
            doctorInfo = doctorInfoDAO.getById(doctorId);
        }
        AppointmentDto appointmentDto = AppointmentDtoService.createAppointmentDto(appointmentParams, doctorInfo);
        Appointment appointment = appointmentDto.getAppointment();
        appointment.setDoctorInfo(doctorInfo);
        appointment.setUserDetail(userDetail);
        switch (appointmentDto.getStatus()) {
            case "inserted":
                saveAppointment(appointment);
                break;
            case "deleted":
                deleteInterval(appointment);
                break;
            case "updated":
                updateAppointment(appointment);
                break;
        }
    }

    @Override
    public List<Appointment> getAllByPatientEmail(String patientEmail) {
        List<Appointment> appointments = appointmentDAO.getAllByPatient(userDAO.getByEmail(patientEmail).getId());
        for (Appointment appointment : appointments) {
            appointment.setText(appointment.getDoctorInfo().getUserDetails().getFirstName()
                    + " " + appointment.getDoctorInfo().getUserDetails().getLastName());
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAllByDoctorEmail(String doctorEmail) {
        Long userDetailId = userDAO.getByEmail(doctorEmail).getUserDetails().getId();
        List<Appointment> appointments = appointmentDAO.getAllbyDoctorId(doctorInfoDAO.getIdByUserDetail(userDetailId));
        for (Appointment appointment : appointments) {
            appointment.setText(appointment.getUserDetail().getFirstName() + " " + appointment.getUserDetail().getLastName()
                    + " - " + appointment.getText());
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAllbyDoctorId(Long doctorId) {
        return appointmentDAO.getAllbyDoctorId(doctorId);
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        appointmentDAO.save(appointment);
    }

    @Override
    public void deleteInterval(Appointment appointment) {
        appointmentDAO.delete(appointment);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        appointmentDAO.update(appointment);
    }

    @Override
    public List<Appointment> getByProducer(Long userdetailId) {
        return appointmentDAO.getByProducer(userdetailId);
    }

}
