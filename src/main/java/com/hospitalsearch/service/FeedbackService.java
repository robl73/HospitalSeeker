package com.hospitalsearch.service;

import java.util.List;

import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.FeedbackDTOForAll;
import com.hospitalsearch.util.FeedbackDTOForManager;
import com.hospitalsearch.util.FeedbackStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;
@Transactional
public interface FeedbackService {

	void save(Feedback newFeedback);

	void delete(Feedback feedback);

	void update(Feedback updatedFeedback);

	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	Feedback getById(Long id);

	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	List<Feedback> getAll();

	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	List<Feedback> getByDoctorId(Long id);

    boolean isUserCreatedFeedback(Long producerId, Long consumerId);

	List<Feedback> getFeedbacks(Long doctorId, int pageNumber, int pageSize);

	List<FeedbackDTOForAll> nextFeedbacks(Long doctorId, int rowNumber, int count);

	boolean canWrite(String emailProducer, DoctorInfo doctorInfo);

	List<FeedbackDTOForManager> getFeedbacksByManager(Long managerId, int firstFeedback, int countPage, FeedbackStatus[] statuses);

	List<Hospital> getHospitalsByManagerId(Long managerId);

	boolean isMoreFeedbacksByConsumer(Long doctorId, int pageNumber, int pageSize);

	void changeStatus(Long feedbackId, FeedbackStatus status);

}

