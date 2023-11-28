package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.InventoryDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.OrderItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryTable implements InventoryDAO {

    private static InventoryTable instance;

    Database db = Database.getInstance();
    ArrayList<Inventory> pieData;
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
        int inventoryId = newInventory.getProductId();
        int inventoryQuantity = newInventory.getQuantity();
        double inventoryTotalPrice = newInventory.getTotalPrice();

        //Update record with new values
        String updateQuery = "UPDATE " + DBConst.TABLE_INVENTORY + " SET " +
                DBConst.INVENTORY_COLUMN_QUANTITY + " = " + inventoryQuantity + ", " +
                DBConst.INVENTORY_COLUMN_TOTAL_PRICE + " = " + inventoryTotalPrice +
                " WHERE " + DBConst.INVENTORY_COLUMN_PRODUCT_ID + " = " + inventoryId;
        try {
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update Query unsuccessful");
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


    public void syncWithOrders() {
        OrderItemsTable orderItemsTable = new OrderItemsTable();
        InventoryTable inventoryTable = new InventoryTable();

        //Arraylist of orderItems for each record in the OrderItems table
        ArrayList<OrderItem> orderItems = orderItemsTable.getAllOrderItems();
        ArrayList<Inventory> inventoryItems = inventoryTable.getAllInventories();

        //hashmaps that stores Integer id as key and
        //Integer quantity as value
        Map<Integer, Integer> productMap = new HashMap<>();
        Map<Integer, Integer> inventoryMap = new HashMap<>();
        //add all existing Inventory records into the map
        for (Inventory inventoryItem : inventoryItems) {
            int productId = inventoryItem.getProductId();
            int quantity = inventoryItem.getQuantity();
            inventoryMap.put(productId, quantity);
        }

        //If an item in the arraylist matches a value
        // in the map add their quantities together.
        // Update the product map with new quantity
        for (OrderItem orderItem : orderItems) {
            int productId = orderItem.getProductId();
            int quantity = orderItem.getQuantity();

            if (productMap.containsKey(productId)) {
                quantity += productMap.get(productId);
            }
            productMap.put(productId, quantity);
        }

        //If an entry in the productmap has a matching key in the inventorymap
        //check if they are the same value. If so keep teh inventory item as it was
        //otherwise add their quantities together
        for (Map.Entry<Integer, Integer> entry : productMap.entrySet()) {
            int productKey = entry.getKey();
            int productQuantity = entry.getValue();

            if (inventoryMap.containsKey(productKey)) {
                int inventoryQuantity = inventoryMap.get(productKey);

                if (productQuantity != inventoryQuantity) {
                    inventoryQuantity += productQuantity - inventoryQuantity;
                }
                inventoryMap.put(productKey, inventoryQuantity);
            } else {
                inventoryMap.put(productKey, productQuantity);
            }
        }

        //If the entry in the inventory map exists in the table
        // update it otherwise create a new value
        for (Map.Entry<Integer, Integer> entry : inventoryMap.entrySet()) {
            int inventoryKey = entry.getKey();
            int inventoryQuantity = entry.getValue();
            double totalPrice = orderItemsTable.getProductPrice(inventoryKey) * inventoryQuantity;

            Inventory inventory = new Inventory(inventoryKey, inventoryQuantity, totalPrice);

            if (getInventory(inventoryKey) != null) {
                updateInventory(inventory);
            } else {
                createInventory(inventory);
            }
        }
    }


    public static InventoryTable getInstance() {
        if (instance == null) {
            instance = new InventoryTable();
        }
        return instance;
    }
}
