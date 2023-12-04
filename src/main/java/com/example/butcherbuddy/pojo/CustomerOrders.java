package com.example.butcherbuddy.pojo;

import java.sql.Date;

public class CustomerOrders {

    private int id;
    private Date date;


    public CustomerOrders(int id, Date date) {
        this.id = id;
        this.date = (Date) date;
    }
    public CustomerOrders(Date date){
        this.date = (Date) date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }

}
