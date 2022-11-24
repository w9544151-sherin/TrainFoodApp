package uk.ac.tees.w9544151.Models;

public class CartModel {
    String userid,username,image, itemName, itemQuantity, itemPrice, totalAmount;

    public CartModel(String userid,String username, String image, String itemName, String itemQuantity, String itemPrice, String totalAmount) {
        this.userid = userid;
        this.username = username;
        this.image = image;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.totalAmount = totalAmount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return userid;
    }
    public void setUsername(String userid) {
        this.userid = userid;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
