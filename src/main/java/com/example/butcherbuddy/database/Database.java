package com.example.butcherbuddy.database;


import com.example.butcherbuddy.database.DBConst;

import java.io.*;
import java.sql.*;


public class Database {

    private static Database instance;

    private Connection connection = null;


    private Database() {

    }

    private Database(String host, String location, String username, String password) {

        try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + host + "/"+ location +
                                        "?serverTimezone=UTC",
                                username,
                                password);                System.out.println("Connection Successfully created");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void setLoginInfo(String host, String location, String username, String password) {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + host + "/"+ location +
                                        "?serverTimezone=UTC",
                                username,
                                password);
                System.out.println("Connection Successfully created");



                createTable(DBConst.TABLE_CATEGORY, DBConst.CREATE_TABLE_CATEGORY, connection);
                createTable(DBConst.TABLE_PRODUCT, DBConst.CREATE_TABLE_PRODUCT, connection);
                createTable(DBConst.TABLE_ORDERS, DBConst.CREATE_TABLE_ORDERS, connection);
                createTable(DBConst.TABLE_ORDER_ITEMS, DBConst.CREATE_TABLE_ORDER_ITEMS, connection);
                createTable(DBConst.TABLE_CUSTOMER_ORDERS, DBConst.CREATE_TABLE_CUSTOMER_ORDERS, connection);
                createTable(DBConst.TABLE_CUSTOMER_ITEMS, DBConst.CREATE_TABLE_CUSTOMER_ITEMS, connection);
                createTable(DBConst.TABLE_INVENTORY, DBConst.CREATE_TABLE_INVENTORY, connection);
                createTable(DBConst.TABLE_LOGINS, DBConst.CREATE_TABLE_LOGINS, connection);
            }catch (Exception e){
                System.out.println(e);
            }
        }




    public static Database getInstance() {
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     *
     * @param tableName
     * @param tableQuery
     * @param connection
     * @throws SQLException
     */
    public void createTable(String tableName, String tableQuery, Connection connection) throws SQLException{
        Statement createTable;
        DatabaseMetaData md = connection.getMetaData();
        ResultSet resultSet = md.getTables("eproulxproject", null, tableName, null);
        if (resultSet.next()){
            System.out.println(tableName + " Table already exists");
        }else{
            createTable = connection.createStatement();
            createTable.execute(tableQuery);
            System.out.println("The " + tableName + " table has been created");
        }
    }


}



