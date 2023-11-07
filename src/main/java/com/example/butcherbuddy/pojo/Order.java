package com.example.butcherbuddy.pojo;

public class Order {

    private int id;
    private String date;


    public Order(int id, String date) {
        this.id = id;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }
}
