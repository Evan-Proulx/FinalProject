package com.example.butcherbuddy.dao;

import com.example.butcherbuddy.pojo.Orders;

import java.util.ArrayList;

public interface OrdersDAO {
    public ArrayList<Orders> getAllOrders();
    public Orders getOrder(int id);
    public void createOrder(Orders orders);
    public void updateOrder(Orders orders);
    public void deleteOrder(Orders orders);
}
