package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.InventoryDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.OrderItem;
import com.example.butcherbuddy.tabs.InventoryTab;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventories;
    }


    @Override
    public Inventory getInventory(int id) {
        String query = "SELECT * FROM " + DBConst.TABLE_INVENTORY + " WHERE " + DBConst.INVENTORY_COLUMN_PRODUCT_ID + " = " + id;

        try {
            Statement getInventory = db.getConnection().createStatement();
            ResultSet data = getInventory.executeQuery(query);
            if (data.next()) {
                Inventory inventory = new Inventory(
                        data.getInt(DBConst.INVENTORY_COLUMN_ID),
                        data.getInt(DBConst.INVENTORY_COLUMN_PRODUCT_ID),
                        data.getInt(DBConst.INVENTORY_COLUMN_QUANTITY),
                        data.getInt(DBConst.INVENTORY_COLUMN_TOTAL_PRICE),
                        data.getString(DBConst.INVENTORY_COLUMN_EXPIRY_DATE)
                );
                return inventory;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void createInventory(Inventory inventory) {
        String query = "INSERT INTO " + DBConst.TABLE_INVENTORY +
                "(" + DBConst.INVENTORY_COLUMN_PRODUCT_ID + ", " +
                DBConst.INVENTORY_COLUMN_QUANTITY + ", " +
                DBConst.INVENTORY_COLUMN_TOTAL_PRICE + ") VALUES ('" +
                inventory.getProductId() + "','" +
                inventory.getQuantity() + "','" +
                inventory.getTotalPrice() + "')";

        try {
            db.getConnection().createStatement().execute(query);
            System.out.println("Inserted Record");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateInventory(Inventory newInventory) {
        Inventory oldInventory = getInventory(newInventory.getProductId());
        System.out.println("Old inventory: " + oldInventory);
        int oldQuantity = oldInventory.getQuantity();
        double oldTotalPrice = oldInventory.getTotalPrice();

        //Add values from old record and new together
        int newQuantity = oldQuantity + (newInventory.getQuantity() - oldQuantity);
        double newTotalPrice = oldTotalPrice + (newInventory.getTotalPrice() - oldQuantity);

        //Update record with new values
        String updateQuery = "UPDATE " + DBConst.TABLE_INVENTORY + " SET " +
                DBConst.INVENTORY_COLUMN_QUANTITY + " = " + newQuantity + ", " +
                DBConst.INVENTORY_COLUMN_TOTAL_PRICE + " = " + newTotalPrice +
                " WHERE " + DBConst.INVENTORY_COLUMN_PRODUCT_ID + " = " + oldInventory.getProductId();
        try {
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteInventory(Inventory inventory) {
        int productId = inventory.getProductId();
        String query = "DELETE * FROM " + DBConst.TABLE_INVENTORY + " WHERE " + DBConst.INVENTORY_COLUMN_PRODUCT_ID + " = " + productId;

        try {
            db.getConnection().createStatement().execute(query);
            System.out.println("Inserted Record");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getItemName(Inventory inventory) {
        int productId = inventory.getProductId();
        String query = "SELECT * FROM " + DBConst.TABLE_PRODUCT + " WHERE " + DBConst.PRODUCT_COLUMN_ID + " = " + productId;

        try {
            Statement getInventory = db.getConnection().createStatement();
            ResultSet data = getInventory.executeQuery(query);
            return data.getString(DBConst.PRODUCT_COLUMN_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<Integer, Inventory> extractItemTableData() {
        OrderItemsTable orderItemsTable = new OrderItemsTable();
        InventoryTable inventoryTable = new InventoryTable();

        //Arraylist of orderItems for each record in the OrderItems table
        ArrayList<OrderItem> orderItems = orderItemsTable.getAllOrderItems();
        ArrayList<Inventory> inventoryItems = inventoryTable.getAllInventories();

        //hashmap that stores Integer id as key and
        //Inventory with prodID, quantity, and totalprice as value
        Map<Integer, Inventory> productMap = new HashMap<>();
        Map<Integer, Inventory> inventoryMap = new HashMap<>();
        //add all existing Inventory records into the map
        for (Inventory inventoryItem : inventoryItems) {
            int productId = inventoryItem.getProductId();
            productMap.put(productId, inventoryItem);
            inventoryMap.put(productId, inventoryItem);
        }

        //loop arraylist and set values
        //if there is more than item with the same id we add their prices and quantity
        //if the item is new we create a new instance of the item in the map
        for (OrderItem orderItem : orderItems) {
            int productId = orderItem.getProductId();
            int quantity = orderItem.getQuantity();
            double itemPrice = orderItemsTable.getProductPrice(orderItem) * quantity;

            if (!productMap.containsKey(productId)) {
                Inventory inventoryItem = new Inventory(productId, quantity, itemPrice);
                productMap.put(productId, inventoryItem);
            } else {
                Inventory inventoryItem = productMap.get(productId);
                inventoryItem.setQuantity(inventoryItem.getQuantity() + quantity);
                inventoryItem.setTotalPrice(inventoryItem.getTotalPrice() + itemPrice);

                //if record exists in the table update it otherwise update the value in the map
                if (inventoryTable.getInventory(productId) != null) {
                    inventoryTable.updateInventory(inventoryItem);
                    productMap.remove(productId);
                } else {
                    productMap.put(productId, inventoryItem);
                }
            }
        }

        //Removes any records in the product map that exist in the inventory map (prevents existing records from being added)
        for (Map.Entry<Integer, Inventory> entry : inventoryMap.entrySet()) {
            int productId = entry.getKey();
            productMap.remove(productId);
        }

        System.out.println("Product map" + productMap);
        return productMap;
    }
}




//public void extractItemTableData() {
//        OrderItemsTable orderItemsTable = new OrderItemsTable();
//        InventoryTable inventoryTable = new InventoryTable();
//
//        // ArrayList of orderItems for each record in the OrderItems table
//        ArrayList<OrderItem> orderItems = orderItemsTable.getAllOrderItems();
//        ArrayList<Inventory> inventoryItems = inventoryTable.getAllInventories();
//
//        // HashMap that stores Integer id as key and
//        // Inventory with prodID, quantity, and total price as value
//        Map<Integer, Inventory> inventoryMap = new HashMap<>();
//
//        // Populate the inventoryMap with existing Inventory records
//        for (Inventory inventoryItem : inventoryItems) {
//            int productId = inventoryItem.getProductId();
//            inventoryMap.put(productId, inventoryItem);
//        }
//
//        // Loop through orderItems and update quantities and prices
//        for (OrderItem orderItem : orderItems) {
//            int productId = orderItem.getProductId();
//            int quantity = orderItem.getQuantity();
//            double price = orderItem.getPrice();
//
//            // Check if the productId exists in the orderItems map
//            if (inventoryMap.containsKey(productId)) {
//                Inventory inventoryItem = inventoryMap.get(productId);
//
//                // Update quantities and prices
//                inventoryItem.setQuantity(inventoryItem.getQuantity() + quantity);
//                inventoryItem.setTotalPrice(inventoryItem.getTotalPrice() + price);
//
//                // Remove the processed orderItem
//                orderItems.remove(orderItem);
//            }
//        }
//
//        // Process remaining orderItems
//        for (OrderItem orderItem : orderItems) {
//            int productId = orderItem.getProductId();
//            int quantity = orderItem.getQuantity();
//            double price = orderItem.getPrice();
//
//            // Create a new Inventory item
//            Inventory inventoryItem = new Inventory(productId, quantity, price);
//
//            // Check if the productId exists in the inventoryMap
//            if (inventoryMap.containsKey(productId)) {
//                // Check if the existing inventory is different from the new one
//                if (!inventoryMap.get(productId).equals(inventoryItem)) {
//                    // Delete the existing inventory and create a new one
//                    inventoryTable.deleteInventory(inventoryMap.get(productId));
//                    inventoryTable.createInventory(inventoryItem);
//                }
//            } else {
//                // If productId doesn't exist, create a new inventory
//                inventoryTable.createInventory(inventoryItem);
//            }
//        }
//    }
//}

