package com.hospitalsearch.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.FeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.FeedbackDAO;
import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackDAO dao;

	@Autowired
	private UserService userService;
	
	@Override
	public void save(Feedback newFeedback) {
		dao.save(newFeedback);
	}

	@Override
	public void delete(Feedback feedback) {
		dao.delete(feedback);
	}

	@Override
	public void update(Feedback updatedFeedback) {
		dao.update(updatedFeedback);	
	}

	@Override
	public Feedback getById(Long id) {
		return dao.getById(id);
	}

	@Override
	public List<Feedback> getAll() {
		
		return dao.getAll(); 
	}
	@Override
	public List<Feedback> getByDoctorId(Long id) {
		return dao.getByDoctorId(id);
	}

	@Override
	public User getByUserEmail(String email) {
		return dao.getByUserEmail(email);
	}

	@Override
	public Feedback getByProducer(User user) {
		return dao.getByProducer(user);
	}

	@Override
	public boolean isUserCreatedFeedback(Long producerId, Long consumerId) {
		return dao.isUserCreatedFeedback(producerId, consumerId);
	}

	@Override
	public List<Feedback> getFeedbacks(Long doctorId, int pageNumber, int pageSize) {
		return dao.getFeedbacks(doctorId, pageNumber, pageSize);
	}

	private boolean isMoreFeedbacksByConsumer(Long doctorId, int pageNumber, int pageSize) {
		long count = dao.countOfFeedbacksByConsumer(doctorId);
		return count > pageNumber * pageSize;
	}

	@Override
	public Map<String, Object> nextFeedbacks(Long doctorId, int rowNumber, int count) {
		List<Feedback> feedbacks = getFeedbacks(doctorId, rowNumber, count);
		List<FeedbackDTO> feedbackDTOs = new ArrayList<>(feedbacks.size());
		feedbacks.forEach(feedback -> feedbackDTOs.add(new FeedbackDTO(feedback)));
		boolean isMore = isMoreFeedbacksByConsumer(doctorId, rowNumber, count);
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("feedbacks", feedbackDTOs);
		data.put("isMore", isMore);
		return data;
	}

	@Override
	public boolean canWrite(String emailProducer, DoctorInfo doctorInfo) {
		boolean allowAddFeedback = true;
		if (emailProducer.equals("anonymousUser")) {
			allowAddFeedback = false;
		} else {
			Long producerId = userService.getByEmail(emailProducer).getId();
			if (producerId.equals(doctorInfo.getUserDetails().getUser().getId())) {
				allowAddFeedback = false;
			} else {
				if (isUserCreatedFeedback(producerId, doctorInfo.getId())) {
					allowAddFeedback = false;
				}
			}
		}
		return allowAddFeedback;
	}
}

