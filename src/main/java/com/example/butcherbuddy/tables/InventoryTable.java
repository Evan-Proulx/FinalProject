package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.InventoryDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.Inventory;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class InventoryTable implements InventoryDAO {

    Database db = Database.getInstance();

    ArrayList<Inventory> inventories;
    @Override
    public ArrayList<Inventory> getAllInventories() {
        String query = "SELECT * FROM " + DBConst.TABLE_INVENTORY;

        inventories = new ArrayList<>();
        try {
            Statement getInventory = db.getConnection().createStatement();
            ResultSet data = getInventory.executeQuery(query);
            while (data.next()) {
                inventories.add(new Inventory(
                        data.getInt(DBConst.INVENTORY_COLUMN_ID),
                        data.getInt(DBConst.INVENTORY_COLUMN_PRODUCT_ID),
                        data.getInt(DBConst.INVENTORY_COLUMN_QUANTITY),
                        data.getInt(DBConst.INVENTORY_COLUMN_TOTAL_PRICE),
                        data.getString(DBConst.INVENTORY_COLUMN_EXPIRY_DATE)
                ));
            }
            }catch(Exception e){
                e.printStackTrace();
            }
        return inventories;
    }

    @Override
    public Inventory getInventory(int id) {
        String query = "SELECT * FROM " + DBConst.TABLE_INVENTORY + " WHERE " + DBConst.INVENTORY_COLUMN_ID  + " = " + id;

        try{
            Statement getInventory = db.getConnection().createStatement();
            ResultSet data = getInventory.executeQuery(query);
            if (data.next()){
                Inventory inventory = new Inventory(
                        data.getInt(DBConst.INVENTORY_COLUMN_ID),
                        data.getInt(DBConst.INVENTORY_COLUMN_PRODUCT_ID),
                        data.getInt(DBConst.INVENTORY_COLUMN_QUANTITY),
                        data.getInt(DBConst.INVENTORY_COLUMN_TOTAL_PRICE),
                        data.getString(DBConst.INVENTORY_COLUMN_EXPIRY_DATE)
                );
                return inventory;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createInventory(Inventory inventory) {

    }

    @Override
    public void updateInventory(Inventory inventory) {

    }

    @Override
    public void deleteInventory(Inventory inventory) {

    }
}
