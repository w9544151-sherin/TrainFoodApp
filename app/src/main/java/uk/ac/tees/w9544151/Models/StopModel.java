package uk.ac.tees.w9544151.Models;

public class StopModel {
    String stopId,stopName,stopNumber,trainNumber;

    public StopModel(String stopId, String stopName, String stopNumber, String trainNumber) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.stopNumber = stopNumber;
        this.trainNumber = trainNumber;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(String stopNumber) {
        this.stopNumber = stopNumber;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
}
