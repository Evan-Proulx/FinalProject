package com.example.butcherbuddy.dao;

import com.example.butcherbuddy.pojo.OrderItem;

import java.util.ArrayList;

public interface OrderItemsDAO {
    public ArrayList<OrderItem> getAllOrderItems();
    public OrderItem getOrderItem(int id);
    public void createOrderItem(OrderItem orderItem);
    public void updateOrderItem(OrderItem orderItem);
    public void deleteOrderItem(OrderItem orderItem);
}
