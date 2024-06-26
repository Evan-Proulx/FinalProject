package com.example.butcherbuddy.pojo;

public class Product {
    private int id;
    private String name;
    private double price;
    private int category;


    public Product(int id, String name, double price, int category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }
    public Product(String name, double price, int category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }
}
