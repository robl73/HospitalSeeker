package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.FeedbackDAO;
import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService{

	@Autowired
	private FeedbackDAO dao;
	
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

}