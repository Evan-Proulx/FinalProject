package com.example.butcherbuddy.dao;

import com.example.butcherbuddy.pojo.Inventory;

import java.util.ArrayList;

public interface InventoryDAO {
    public ArrayList<Inventory> getAllInventories();
    public Inventory getInventory(int id);
    public void createInventory(Inventory inventory);
    public void updateInventory(Inventory inventory);
    public void deleteInventory(Inventory inventory);
}
