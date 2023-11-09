package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.OrderItemsDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.Category;
import com.example.butcherbuddy.pojo.OrderItem;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderItemsTable implements OrderItemsDAO {
    Database db = Database.getInstance();

    ArrayList<OrderItem> orderItems;
    @Override
    public ArrayList<OrderItem> getAllOrderItems() {
        String query = "SELECT * FROM " + DBConst.TABLE_ORDER_ITEMS;

        orderItems = new ArrayList<>();
        try{
            Statement getOrderItems = db.getConnection().createStatement();
            ResultSet data = getOrderItems.executeQuery(query);

            while(data.next()){
                orderItems.add(new OrderItem(
                        data.getInt(DBConst.ORDER_ITEMS_COLUMN_ID),
                        data.getInt(DBConst.ORDER_ITEMS_COLUMN_ORDER_ID),
                        data.getInt(DBConst.ORDER_ITEMS_COLUMN_PRODUCT_ID),
                        data.getInt(DBConst.ORDER_ITEMS_COLUMN_QUANTITY),
                        data.getDouble(DBConst.ORDER_ITEMS_COLUMN_PRICE)
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return orderItems;
    }

    @Override
    public OrderItem getOrderItem(int id) {
        String query = "SELECT * FROM " + DBConst.TABLE_ORDER_ITEMS + " WHERE " + DBConst.ORDER_ITEMS_COLUMN_ID  + " = " + id;

        try{
            Statement getOrderItems = db.getConnection().createStatement();
            ResultSet data = getOrderItems.executeQuery(query);

            if(data.next()){
                OrderItem orderItem = new OrderItem(
                        data.getInt(DBConst.ORDER_ITEMS_COLUMN_ID),
                        data.getInt(DBConst.ORDER_ITEMS_COLUMN_ORDER_ID),
                        data.getInt(DBConst.ORDER_ITEMS_COLUMN_PRODUCT_ID),
                        data.getInt(DBConst.ORDER_ITEMS_COLUMN_QUANTITY),
                        data.getDouble(DBConst.ORDER_ITEMS_COLUMN_PRICE)
                );
                return orderItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createOrderItem(OrderItem orderItem) {

    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {

    }

    @Override
    public void deleteOrderItem(OrderItem orderItem) {

    }
}
