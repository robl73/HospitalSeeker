package com.hospitalsearch.util;

import com.hospitalsearch.entity.Feedback;

/**
 * Created by vanytate on 10/31/16.
 */
public class FeedbackDTOForManager {

    private Long id;

    private FeedbackDTOForAll feedbackDTOForAll;

    private String consumerFirstName;

    private String consumerLastName;

    private FeedbackStatus status;

    public FeedbackDTOForManager(Feedback feedback) {
        feedbackDTOForAll = new FeedbackDTOForAll(feedback);
        this.id = feedback.getId();
        this.status = feedback.getStatus();
        this.consumerFirstName = feedback.getConsumer().getUserDetails().getFirstName();
        this.consumerLastName = feedback.getConsumer().getUserDetails().getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FeedbackDTOForAll getFeedbackDTOForAll() {
        return feedbackDTOForAll;
    }

    public void setFeedbackDTOForAll(FeedbackDTOForAll feedbackDTOForAll) {
        this.feedbackDTOForAll = feedbackDTOForAll;
    }

    public String getConsumerFirstName() {
        return consumerFirstName;
    }

    public void setConsumerFirstName(String consumerFirstName) {
        this.consumerFirstName = consumerFirstName;
    }

    public String getConsumerLastName() {
        return consumerLastName;
    }

    public void setConsumerLastName(String consumerLastName) {
        this.consumerLastName = consumerLastName;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }
}
