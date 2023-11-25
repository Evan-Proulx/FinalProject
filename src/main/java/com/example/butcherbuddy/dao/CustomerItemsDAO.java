package com.example.butcherbuddy.dao;

import com.example.butcherbuddy.pojo.CustomerItem;

import java.util.ArrayList;

public interface CustomerItemsDAO {
    public ArrayList<CustomerItem> getAllCustomerItems();
    public CustomerItem getCustomerItem(int id);
    public void createCustomerItems(CustomerItem CustomerItem);
    public void updateCustomerItems(CustomerItem CustomerItem);
    public void deleteCustomerItems(CustomerItem CustomerItem);
}
