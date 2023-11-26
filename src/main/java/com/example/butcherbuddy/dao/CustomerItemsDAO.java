package com.example.butcherbuddy.dao;

import com.example.butcherbuddy.pojo.CustomerItem;

import java.util.ArrayList;

public interface CustomerItemsDAO {
    public ArrayList<CustomerItem> getAllCustomerItems();
    public CustomerItem getCustomerItem(int id);
    public void createCustomerItem(CustomerItem CustomerItem);
    public void updateCustomerItem(CustomerItem CustomerItem);
    public void deleteCustomerItem(CustomerItem CustomerItem);
}
