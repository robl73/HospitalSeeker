package com.hospitalsearch.service;

import java.util.List;
import java.util.Map;

import com.hospitalsearch.entity.DoctorInfo;
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

	User getByUserEmail(String email);

	Feedback getByProducer(User user);

    boolean isUserCreatedFeedback(Long producerId, Long consumerId);

	List<Feedback> getFeedbacks(Long doctorId, int pageNumber, int pageSize);

	Map<String, Object> nextFeedbacks(Long doctorId, int rowNumber, int count);

	boolean canWrite(String emailProducer, DoctorInfo doctorInfo);

}

