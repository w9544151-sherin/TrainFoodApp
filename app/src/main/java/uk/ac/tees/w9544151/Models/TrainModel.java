package uk.ac.tees.w9544151.Models;

public class TrainModel {
    String trainId, trainNumber, trainName, startPoint, endPoint, path;

    public TrainModel(String trainId, String trainNumber, String trainName, String startPoint, String endPoint, String path) {
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.path = path;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}