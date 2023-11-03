package com.example.butcherbuddy.dao;

import com.example.butcherbuddy.pojo.Order;

import java.util.ArrayList;

public interface OrderDAO {
    public ArrayList<Order> getAllOrders();
    public Order getOrder(int id);
    public void createOrder(Order order);
    public void updateOrder(Order order);
    public void deleteOrder(Order order);
}
