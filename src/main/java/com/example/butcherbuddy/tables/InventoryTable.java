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
            }catch(Exception e){
                e.printStackTrace();
            }
        return inventories;
    }


    @Override
    public Inventory getInventory(int id) {
        String query = "SELECT * FROM " + DBConst.TABLE_INVENTORY + " WHERE " + DBConst.INVENTORY_COLUMN_PRODUCT_ID  + " = " + id;

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
    public void updateInventory(Inventory newInventory) {
        Inventory oldInventory = getInventory(newInventory.getProductId());
        System.out.println("Old inventory: "+oldInventory);
        int oldQuantity = oldInventory.getQuantity();
        double oldTotalPrice = oldInventory.getTotalPrice();

        //Add values from old record and new together
        int newQuantity = oldQuantity + newInventory.getQuantity();
        double newTotalPrice = oldTotalPrice + newInventory.getTotalPrice();

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

    }

    public Map<Integer, Inventory> extractItemTableData(){
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
        for (Inventory inventoryItem : inventoryItems){
            int productId = inventoryItem.getProductId();
            productMap.put(productId, inventoryItem);
            inventoryMap.put(productId, inventoryItem);
        }
        System.out.println("Items in inventory: " + productMap);

        // productMap = [3, Inventory1, 7, inventory2, 9, Inventory3]
        // orderitems = [item1 (id = 8), item2 (id = 4), item3 (id = 3]
        //loop through order items. item1s id is not 3,7 or 9
        //create new inventory object with its values
        //loops again and does tehe same for item2
        //loops again and item 3 matches inventory1
        //create new inventory item and set it to the object of the matching one in the map
        //set its quantity and price
        //update inventory
        //how do we know the item in productmap is a record in the inventory table
        //ejejalk;jdf;jeajkljklfwed jklasdfjklfejklw ijoefjio
        //before updating the inventory check if the row exists in the table otherwise create it?
        //is the best way creating another map that just hold the values in inventory tablee
        //should product map only contain new values? probabys


        //loop arraylist and set values
        //if there is more than item with the same id we add their prices and quantity
        //if the item is new we create a new instance of the item in the map
        for (OrderItem orderItem : orderItems){
            int productId = orderItem.getProductId();
            int quantity = orderItem.getQuantity();
            double price = orderItem.getPrice();

            if (!productMap.containsKey(productId)){
                
                Inventory inventoryItem = new Inventory(productId, quantity, quantity*price);
                productMap.put(productId, inventoryItem);
                System.out.println("Inventory map: " + productMap);
                
            }else{
                
                Inventory inventoryItem = productMap.get(productId);
                inventoryItem.setQuantity(inventoryItem.getQuantity()+quantity);
                inventoryItem.setTotalPrice(inventoryItem.getTotalPrice()+price);

                //if record exists in the table update it otherwise update the value in the map
                if (inventoryMap.containsKey(productId)) {
                    System.out.println("Inventory item: " + inventoryItem + "     (This is what is getting passed to update inventory(old inventory)");
                    updateInventory(inventoryItem);
                }else{
                    productMap.put(productId, inventoryItem);
                }
            }

        }
        return productMap;
    }
}

