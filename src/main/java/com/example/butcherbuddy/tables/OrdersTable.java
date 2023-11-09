package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.OrdersDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.Orders;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OrdersTable implements OrdersDAO {
    Database db = Database.getInstance();

    ArrayList<Orders> orders;
    @Override
    public ArrayList<Orders> getAllOrders() {
        String query = "SELECT * FROM " + DBConst.TABLE_ORDERS;

        orders = new ArrayList<>();
        try{
            Statement getOrders = db.getConnection().createStatement();
            ResultSet data = getOrders.executeQuery(query);

            while(data.next()){
                orders.add(new Orders(
                        data.getInt(DBConst.ORDERS_COLUMN_ID),
                        data.getString(DBConst.ORDERS_COLUMN_DATE)
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Orders getOrder(int id) {
        String query = "SELECT * FROM " + DBConst.TABLE_ORDERS + " WHERE " + DBConst.ORDERS_COLUMN_ID  + " = " + id;

        try{
            Statement getOrders = db.getConnection().createStatement();
            ResultSet data = getOrders.executeQuery(query);
            if (data.next()){
                Orders orders = new Orders(
                        data.getInt(DBConst.ORDERS_COLUMN_ID),
                        data.getString(DBConst.ORDERS_COLUMN_DATE)
                );
                return orders;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createOrder(Orders orders) {

    }

    @Override
    public void updateOrder(Orders orders) {

    }

    @Override
    public void deleteOrder(Orders orders) {

    }
}
