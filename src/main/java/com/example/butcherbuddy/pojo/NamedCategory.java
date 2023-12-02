package com.example.butcherbuddy.pojo;

public class NamedCategory {

    private String category;
    private String productName;
    private double totalPrice;

    public NamedCategory(String category, String productName, double totalPrice) {
        this.category = category;
        this.productName = productName;
        this.totalPrice = totalPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
