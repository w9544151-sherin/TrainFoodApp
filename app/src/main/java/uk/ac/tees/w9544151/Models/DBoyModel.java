package uk.ac.tees.w9544151.Models;

public class DBoyModel {
    String boyId,boyName,boyMobile,stop,boyImage;

    public DBoyModel(String boyId, String boyName, String boyMobile, String stop, String boyImage) {
        this.boyId = boyId;
        this.boyName = boyName;
        this.boyMobile = boyMobile;
        this.stop = stop;
        this.boyImage = boyImage;
    }

    public String getBoyId() {
        return boyId;
    }

    public void setBoyId(String boyId) {
        this.boyId = boyId;
    }

    public String getBoyName() {
        return boyName;
    }

    public void setBoyName(String boyName) {
        this.boyName = boyName;
    }

    public String getBoyMobile() {
        return boyMobile;
    }

    public void setBoyMobile(String boyMobile) {
        this.boyMobile = boyMobile;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getBoyImage() {
        return boyImage;
    }

    public void setBoyImage(String boyImage) {
        this.boyImage = boyImage;
    }
}
