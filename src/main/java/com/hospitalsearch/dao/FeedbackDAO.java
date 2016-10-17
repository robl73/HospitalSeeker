package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;

@Component
public interface FeedbackDAO extends GenericDAO<Feedback, Long>{

	List<Feedback> getByDoctorId(Long id);

	User getByUserEmail(String email);

	Feedback getByProducer(User user);

	boolean isUserCreateFeedback(Long producerId, Long consumerId);

}
