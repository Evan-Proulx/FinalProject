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
                        data.getDate(DBConst.ORDERS_COLUMN_DATE)
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
                        data.getDate(DBConst.ORDERS_COLUMN_DATE)
                );
                return orders;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int createOrder(Orders orders) {
        String query = "INSERT INTO " + DBConst.TABLE_ORDERS +
                "(" + DBConst.ORDERS_COLUMN_DATE + ") VALUES ('" +
                orders.getDate() + "')";

        //executes the query then creates a resultSet of keys(Auto-Incremented ids)
        //If there are more than one row in the table we get and return the value of the first column(id)
        try{
            Statement createNewOrder = db.getConnection().createStatement();
            createNewOrder.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet returnedKeys = createNewOrder.getGeneratedKeys();
            if (returnedKeys.next()){
                int generatedId = returnedKeys.getInt(1);

                System.out.println("Inserted Record with id: " + generatedId);
                return generatedId;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void updateOrder(Orders orders) {

    }

    @Override
    public void deleteOrder(Orders orders) {

    }
}
