package com.hospitalsearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.FeedbackDTOForAll;
import com.hospitalsearch.util.FeedbackDTOForManager;
import com.hospitalsearch.util.FeedbackStatus;
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
	public boolean isUserCreatedFeedback(Long producerId, Long consumerId) {
		return dao.isUserCreatedFeedback(producerId, consumerId);
	}

	@Override
	public List<Feedback> getFeedbacks(Long doctorId, int pageNumber, int pageSize) {
		return dao.getFeedbacks(doctorId, pageNumber, pageSize);
	}

	@Override
	public boolean isMoreFeedbacksByConsumer(Long doctorId, int pageNumber, int pageSize) {
		long count = dao.countOfFeedbacksByConsumer(doctorId);
		return count > pageNumber * pageSize;
	}

	@Override
	public List<FeedbackDTOForAll> nextFeedbacks(Long doctorId, int rowNumber, int count) {
		List<Feedback> feedbacks = getFeedbacks(doctorId, rowNumber, count);
		List<FeedbackDTOForAll> feedbackDTOs = new ArrayList<>(feedbacks.size());
		feedbacks.forEach(feedback -> feedbackDTOs.add(new FeedbackDTOForAll(feedback)));
		return feedbackDTOs;
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

	@Override
	public List<FeedbackDTOForManager> getFeedbacksByManager(Long managerId, int firstFeedback, int countPage, FeedbackStatus[] statuses) {
		List<Feedback> feedbacks = dao.getFeedbacksByManager(managerId, firstFeedback, countPage, statuses);
        List<FeedbackDTOForManager> feedbackDTOForManagers = new ArrayList<>(feedbacks.size());
        feedbacks.forEach(feedback -> feedbackDTOForManagers.add(new FeedbackDTOForManager(feedback)));
        return feedbackDTOForManagers;
    }

	@Override
	public List<Hospital> getHospitalsByManagerId(Long managerId) {
		return dao.getHospitalsByManagerId(managerId);
	}

	@Override
	public void changeStatus(Long feedbackId, FeedbackStatus status) {
/*		Feedback feedback = dao.getById(feedbackId);
		feedback.setStatus(status);
		dao.update(feedback);*/

		dao.changeStatus(feedbackId, status);
	}

}
