package uk.ac.tees.w9544151.Models;

public class FeedbackModel {
    String feedbackId,userName,feedback,ratingValue;

    public FeedbackModel(String feedbackId, String userName, String feedback, String ratingValue) {
        this.feedbackId = feedbackId;
        this.userName = userName;
        this.feedback = feedback;
        this.ratingValue = ratingValue;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }
}
