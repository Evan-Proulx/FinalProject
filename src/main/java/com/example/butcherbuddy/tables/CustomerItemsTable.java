package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.CustomerItemsDAO;
import com.example.butcherbuddy.dao.OrderItemsDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.CustomerItem;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.OrderItem;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerItemsTable implements CustomerItemsDAO {
    Database db = Database.getInstance();
    
    ArrayList<CustomerItem> customerItems;
    @Override
    public ArrayList<CustomerItem> getAllCustomerItems() {
        String query = "SELECT * FROM " + DBConst.TABLE_CUSTOMER_ITEMS;

        customerItems = new ArrayList<>();
        try{
            Statement getCustomerItems = db.getConnection().createStatement();
            ResultSet data = getCustomerItems.executeQuery(query);

            while(data.next()){
                customerItems.add(new CustomerItem(
                        data.getInt(DBConst.CUSTOMER_ITEMS_COLUMN_ORDER_ID),
                        data.getInt(DBConst.CUSTOMER_ITEMS_COLUMN_PRODUCT_ID),
                        data.getInt(DBConst.CUSTOMER_ITEMS_COLUMN_QUANTITY)
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return customerItems;
    }

    @Override
    public CustomerItem getCustomerItem(int id) {
        String query = "SELECT * FROM " + DBConst.TABLE_CUSTOMER_ITEMS + " WHERE " + DBConst.CUSTOMER_ORDER_COLUMN_ID  + " = " + id;

        try{
            Statement getCustomerItems = db.getConnection().createStatement();
            ResultSet data = getCustomerItems.executeQuery(query);

            if(data.next()){
                CustomerItem customerItem = new CustomerItem(
                        data.getInt(DBConst.CUSTOMER_ITEMS_COLUMN_ORDER_ID),
                        data.getInt(DBConst.CUSTOMER_ITEMS_COLUMN_PRODUCT_ID),
                        data.getInt(DBConst.CUSTOMER_ITEMS_COLUMN_QUANTITY)
                );
                return customerItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createCustomerItem(CustomerItem customerItem) {
        String query = "INSERT INTO " + DBConst.TABLE_CUSTOMER_ITEMS+
                "(" + DBConst.CUSTOMER_ITEMS_COLUMN_ORDER_ID + ", " +
                DBConst.CUSTOMER_ITEMS_COLUMN_PRODUCT_ID + ", " +
                DBConst.ORDER_ITEMS_COLUMN_QUANTITY + ") VALUES ('" +
                customerItem.getOrderId() + "','" +
                customerItem.getProductId() + "','" +
                customerItem.getQuantity() + "')";
        try{
            db.getConnection().createStatement().execute(query);
            System.out.println("Inserted Record");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Takes the customer item and subtracts its quantity from the products record in the inventory
    //If the order's quantity is larger than the quantity in the table we return null
    public Inventory removeOrderFromInventory(Inventory inventory, CustomerItem customerItem){
        OrderItemsTable orderItemsTable = new OrderItemsTable();

        int productId = inventory.getProductId();
        int inventoryQuantity = inventory.getQuantity();
        int customerItemQuantity = customerItem.getQuantity();

        if (inventoryQuantity < customerItemQuantity){return null;}

        int newQuantity = inventoryQuantity - customerItemQuantity;
        double totalPrice = orderItemsTable.getProductPrice(productId) * newQuantity;

        Inventory newInventory = new Inventory(productId, newQuantity, totalPrice);
        return newInventory;
    }

    @Override
    public void updateCustomerItem(CustomerItem CustomerItem) {

    }

    @Override
    public void deleteCustomerItem(CustomerItem CustomerItem) {

    }
}
