package com.example.butcherbuddy.dao;

import com.example.butcherbuddy.pojo.CustomerOrders;

import java.util.ArrayList;

public interface CustomerOrdersDAO {
    public ArrayList<CustomerOrders> getAllCustomerOrders();
    public CustomerOrders getCustomerOrder(int id);
    public int createCustomerOrder(CustomerOrders customerOrders);
    public void updateCustomerOrder(CustomerOrders customerOrders);
    public void deleteCustomerOrder(CustomerOrders customerOrders);
}
