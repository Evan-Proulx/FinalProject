package com.example.butcherbuddy.pojo;

public class NamedCategory {

    private int category;
    private String productName;
    private double totalPrice;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
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

    public NamedCategory(int category, String productName, double totalPrice) {
        this.category = category;
        this.productName = productName;
        this.totalPrice = totalPrice;
    }
}
