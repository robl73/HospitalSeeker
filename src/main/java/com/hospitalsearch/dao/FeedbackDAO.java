package com.hospitalsearch.dao;

import java.util.List;

import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.FeedbackStatus;
import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;

@Component
public interface FeedbackDAO extends GenericDAO<Feedback, Long>{

	List<Feedback> getByDoctorId(Long id);

	boolean isUserCreatedFeedback(Long producerId, Long consumerId);

	List<Feedback> getFeedbacks(Long doctorId, int pageNumber, int pageSize);

	long countOfFeedbacksByConsumer(Long doctorId);

	List<Feedback> getFeedbacksByManager(Long managerId, int firstFeedback, int countPage, FeedbackStatus[] statuses);

	List<Hospital> getHospitalsByManagerId(Long managerId);

	void changeStatus(Long feedbackId, FeedbackStatus status);

}
