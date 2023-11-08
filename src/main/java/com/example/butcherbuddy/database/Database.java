package com.example.butcherbuddy.database;


import java.sql.*;
public class Database {

    private static Database instance;

    private Connection connection = null;

    private Database(){
            try{
                Class.forName("com.mysql.jbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://php.scweb.ca/"+ DBConfig.DB_NAME + "?useSSL=false", DBConfig.DB_USER, DBConfig.DB_PASS);
                System.out.println("Connection Successfully created");
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                createTable(DBConst.TABLE_CATEGORY, DBConst.CREATE_TABLE_CATEGORY, connection);
                createTable(DBConst.TABLE_PRODUCT, DBConst.CREATE_TABLE_PRODUCT, connection);
                createTable(DBConst.TABLE_ORDER, DBConst.CREATE_TABLE_ORDER, connection);
                createTable(DBConst.TABLE_ORDER_ITEMS, DBConst.CREATE_TABLE_ORDER_ITEMS, connection);
                createTable(DBConst.TABLE_INVENTORY, DBConst.CREATE_TABLE_INVENTORY, connection);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Takes database metadata and creates the table if it does not already exist
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
