package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.CustomerOrdersDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.CustomerOrders;
import com.example.butcherbuddy.pojo.Orders;
import javafx.scene.chart.PieChart;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerOrderTable implements CustomerOrdersDAO {
    Database db = Database.getInstance();

    ArrayList<CustomerOrders> customerOrders;

    @Override
    public ArrayList<CustomerOrders> getAllCustomerOrders() {
        String query = "SELECT * FROM " + DBConst.TABLE_CUSTOMER_ORDERS;

        customerOrders = new ArrayList<>();
        try{
            Statement getCustomerOrders = db.getConnection().createStatement();
            ResultSet data = getCustomerOrders.executeQuery(query);

            while(data.next()){
                customerOrders.add(new CustomerOrders(
                        data.getInt(DBConst.CUSTOMER_ITEMS_COLUMN_ID),
                        data.getDate(DBConst.CUSTOMER_ORDER_COLUMN_DATE)
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return customerOrders;
    }

    @Override
    public CustomerOrders getCustomerOrder(int id) {
        String query = "SELECT * FROM " + DBConst.TABLE_CUSTOMER_ORDERS + " WHERE " + DBConst.CUSTOMER_ORDER_COLUMN_ID  + " = " + id;

        try{
            Statement getCustomerOrder = db.getConnection().createStatement();
            ResultSet data = getCustomerOrder.executeQuery(query);
            if (data.next()){
                CustomerOrders customerOrders = new CustomerOrders(
                        data.getInt(DBConst.CUSTOMER_ORDER_COLUMN_ID),
                        data.getDate(DBConst.CUSTOMER_ORDER_COLUMN_DATE)
                );
                return customerOrders;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int createCustomerOrder(CustomerOrders customerOrders) {
        String query = "INSERT INTO " + DBConst.TABLE_CUSTOMER_ORDERS +
                "(" + DBConst.CUSTOMER_ORDER_COLUMN_DATE + ") VALUES ('" +
                customerOrders.getDate() + "')";

        //executes the query then creates a resultSet of keys(Auto-Incremented ids)
        //If there are more than one row in the table we get and return the value of the first column(id)
        try{
            Statement createNewCustomerOrder = db.getConnection().createStatement();
            createNewCustomerOrder.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet returnedKeys = createNewCustomerOrder.getGeneratedKeys();
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
    public void updateCustomerOrder(CustomerOrders CustomerOrders) {

    }

    @Override
    public void deleteCustomerOrder(CustomerOrders CustomerOrders) {

    }
}
