package uk.ac.tees.w9544151.Models;

public class OrderModel {
    String orderId,itemName, itemPrice, itemQty, username,userid, mobile, trainNumber,
            seatNumber,coachNumber, totalAmount,itemImage,Address,status;

    public OrderModel(String orderId, String itemName, String itemPrice, String itemQty, String username, String userid, String mobile, String trainNumber, String seatNumber, String coachNumber, String totalAmount, String itemImage, String address, String status) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.username = username;
        this.userid = userid;
        this.mobile = mobile;
        this.trainNumber = trainNumber;
        this.seatNumber = seatNumber;
        this.coachNumber = coachNumber;
        this.totalAmount = totalAmount;
        this.itemImage = itemImage;
        Address = address;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCoachNumber() {
        return coachNumber;
    }

    public void setCoachNumber(String coachNumber) {
        this.coachNumber = coachNumber;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}