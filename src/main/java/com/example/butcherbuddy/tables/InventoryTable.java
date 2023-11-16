package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.InventoryDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.OrderItem;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        String query = "INSERT INTO " + DBConst.TABLE_INVENTORY+
                "(" + DBConst.INVENTORY_COLUMN_PRODUCT_ID + ", " +
                DBConst.INVENTORY_COLUMN_QUANTITY + ", " +
                DBConst.INVENTORY_COLUMN_TOTAL_PRICE + ") VALUES ('" +
                inventory.getProductId() + "','" +
                inventory.getQuantity() + "','" +
                inventory.getTotalPrice() + "')";

        try{
            db.getConnection().createStatement().execute(query);
            System.out.println("Inserted Record");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateInventory(Inventory inventory) {

    }

    @Override
    public void deleteInventory(Inventory inventory) {

    }

    public void extractItemTableData(){
        OrderItemsTable orderItemsTable = new OrderItemsTable();

        //Arraylist of orderItems for each record in the OrderItems table
        ArrayList<OrderItem> orderItems = orderItemsTable.getAllOrderItems();

        //hashmap that stores Integer id as key and
        //Inventory with prodID, quantity, and totalprice as value
        Map<Integer, Inventory> inventoryMap = new HashMap<>();

        //loop arraylist and set values
        //if there is more than item with the same id we add their prices and quantity
        //if the item is new we create a new instance of the item in the map
        for (OrderItem orderItem : orderItems){
            int productId = orderItem.getProductId();
            int quantity = orderItem.getQuantity();
            double price = orderItem.getPrice();

            if (inventoryMap.containsKey(productId)){
                Inventory inventoryItem = inventoryMap.get(productId);
                inventoryItem.setQuantity(inventoryItem.getQuantity()+quantity);
                inventoryItem.setTotalPrice(inventoryItem.getTotalPrice()+price);
            }else {
                Inventory inventoryItem = new Inventory(productId, quantity, quantity*price);
                inventoryMap.put(productId, inventoryItem);
            }
        }

        //Loop through the map and create inventory item for each entry
        for (Map.Entry<Integer, Inventory> entry : inventoryMap.entrySet()){
            int productId = entry.getKey();
            Inventory inventoryItem = entry.getValue();

            Inventory inventory = new Inventory(
                    productId,
                    inventoryItem.getQuantity(),
                    inventoryItem.getTotalPrice()
            );
            createInventory(inventory);
        }
    }
}
