package uk.ac.tees.w9544151.Models;

public class PassengerModel {
    String userId,name,userName,password,userMobile;

    public PassengerModel(String userId, String name, String userName, String password, String userMobile) {
        this.userId = userId;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.userMobile = userMobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
