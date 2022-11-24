package uk.ac.tees.w9544151.Models;

public class DBoyModel {
    String boyId,boyName,boyMobile,stop,boyImage,username,password,type;

    public DBoyModel(String boyId, String boyName, String boyMobile, String stop, String boyImage, String username, String password, String type) {
        this.boyId = boyId;
        this.boyName = boyName;
        this.boyMobile = boyMobile;
        this.stop = stop;
        this.boyImage = boyImage;
        this.username = username;
        this.password = password;
        this.type = type;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
