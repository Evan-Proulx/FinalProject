package com.example.butcherbuddy.database;

public class DBConst {

    //Logins table
    public static final String TABLE_LOGINS = "logins";
    public static final String LOGINS_COLUMN_ID = "id";
    public static final String LOGINS_COLUMN_NAME = "username";
    public static final String LOGINS_COLUMN_PASSWORD = "password";

    //Category table
    public static final String TABLE_CATEGORY = "category";
    public static final String CATEGORY_COLUMN_ID = "id";
    public static final String CATEGORY_COLUMN_NAME = "name";

    //Product table
    public static final String TABLE_PRODUCT = "product";
    public static final String PRODUCT_COLUMN_ID = "id";
    public static final String PRODUCT_COLUMN_NAME = "name";
    public static final String PRODUCT_COLUMN_PRICE = "price";
    public static final String PRODUCT_COLUMN_CATEGORY = "category";

    //Order Table
    public static final String TABLE_ORDERS = "orders";
    public static final String ORDERS_COLUMN_ID = "id";
    public static final String ORDERS_COLUMN_DATE = "date";

    //Order_items table
    public static final String TABLE_ORDER_ITEMS = "order_items";
    public static final String ORDER_ITEMS_COLUMN_ID = "id";
    public static final String ORDER_ITEMS_COLUMN_ORDER_ID = "order_id";
    public static final String ORDER_ITEMS_COLUMN_PRODUCT_ID = "product_id";
    public static final String ORDER_ITEMS_COLUMN_QUANTITY = "quantity";

    //Customer_Orders Table
    public static final String TABLE_CUSTOMER_ORDERS = "customer_orders";
    public static final String CUSTOMER_ORDER_COLUMN_ID = "id";
    public static final String CUSTOMER_ORDER_COLUMN_DATE = "date";

    //Customer_items table
    public static final String TABLE_CUSTOMER_ITEMS = "customer_items";
    public static final String CUSTOMER_ITEMS_COLUMN_ID = "id";
    public static final String CUSTOMER_ITEMS_COLUMN_ORDER_ID = "customer_id";
    public static final String CUSTOMER_ITEMS_COLUMN_PRODUCT_ID = "product_id";
    public static final String CUSTOMER_ITEMS_COLUMN_QUANTITY = "quantity";

    //Inventory table
    public static final String TABLE_INVENTORY = "inventory";
    public static final String INVENTORY_COLUMN_ID = "id";
    public static final String INVENTORY_COLUMN_PRODUCT_ID = "product_id";
    public static final String INVENTORY_COLUMN_QUANTITY = "quantity";
    public static final String INVENTORY_COLUMN_TOTAL_PRICE = "total_price";


    //Create TABLES

    public static final String CREATE_TABLE_LOGINS =
            "CREATE TABLE " + TABLE_LOGINS + " (" +
                    LOGINS_COLUMN_ID + " int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    LOGINS_COLUMN_NAME + " VARCHAR(30)," +
                    LOGINS_COLUMN_PASSWORD + " VARCHAR(50));";

    public static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE " + TABLE_CATEGORY + " (" +
                    CATEGORY_COLUMN_ID + " int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    CATEGORY_COLUMN_NAME + " VARCHAR(30));";

    public static final String CREATE_TABLE_PRODUCT =
            "CREATE TABLE " + TABLE_PRODUCT + " (" +
                    PRODUCT_COLUMN_ID + " int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    PRODUCT_COLUMN_NAME + " VARCHAR(30), " +
                    PRODUCT_COLUMN_PRICE + " DECIMAL(7,2)," +
                    PRODUCT_COLUMN_CATEGORY + " int NOT NULL, " +
                    "FOREIGN KEY (" + PRODUCT_COLUMN_CATEGORY + ")" +
                    " REFERENCES " + TABLE_CATEGORY + "(" + CATEGORY_COLUMN_ID +"));";

    public static final String CREATE_TABLE_ORDERS =
            "CREATE TABLE " + TABLE_ORDERS + " (" +
            ORDERS_COLUMN_ID + " int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            ORDERS_COLUMN_DATE + " DATE )";

    public static final String CREATE_TABLE_ORDER_ITEMS =
            "CREATE TABLE " + TABLE_ORDER_ITEMS + " (" +
                    ORDER_ITEMS_COLUMN_ORDER_ID + " int NOT NULL, " +
                    ORDER_ITEMS_COLUMN_PRODUCT_ID + " int NOT NULL, " +
                    ORDER_ITEMS_COLUMN_QUANTITY + " int NOT NULL, " +
                    "CONSTRAINT " + ORDER_ITEMS_COLUMN_ID + " PRIMARY KEY (" + ORDER_ITEMS_COLUMN_ORDER_ID + ", " + ORDER_ITEMS_COLUMN_PRODUCT_ID + "), " +
                    "FOREIGN KEY (" + ORDER_ITEMS_COLUMN_ORDER_ID + ")" +
                    " REFERENCES " + TABLE_ORDERS + "(" + ORDERS_COLUMN_ID +"),"+
                    "FOREIGN KEY (" + ORDER_ITEMS_COLUMN_PRODUCT_ID + ")" +
                    " REFERENCES " + TABLE_PRODUCT + "("  + PRODUCT_COLUMN_ID +"));";

    public static final String CREATE_TABLE_CUSTOMER_ORDERS =
            "CREATE TABLE " + TABLE_CUSTOMER_ORDERS + " (" +
                    CUSTOMER_ORDER_COLUMN_ID + " int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    CUSTOMER_ORDER_COLUMN_DATE + " DATE )";

    public static final String CREATE_TABLE_CUSTOMER_ITEMS =
            "CREATE TABLE " + TABLE_CUSTOMER_ITEMS + " (" +
                    CUSTOMER_ITEMS_COLUMN_ORDER_ID + " int NOT NULL, " +
                    CUSTOMER_ITEMS_COLUMN_PRODUCT_ID + " int NOT NULL, " +
                    CUSTOMER_ITEMS_COLUMN_QUANTITY + " int NOT NULL, " +
                    "CONSTRAINT " + CUSTOMER_ITEMS_COLUMN_ID + " PRIMARY KEY (" + CUSTOMER_ITEMS_COLUMN_ORDER_ID + ", " + CUSTOMER_ITEMS_COLUMN_PRODUCT_ID + "), " +
                    "FOREIGN KEY (" + CUSTOMER_ITEMS_COLUMN_ORDER_ID + ")" +
                    " REFERENCES " + TABLE_CUSTOMER_ORDERS + "(" + CUSTOMER_ORDER_COLUMN_ID +"),"+
                    "FOREIGN KEY (" + CUSTOMER_ITEMS_COLUMN_PRODUCT_ID + ")" +
                    " REFERENCES " + TABLE_PRODUCT + "("  + PRODUCT_COLUMN_ID +"));";
    public static final String CREATE_TABLE_INVENTORY =
            "CREATE TABLE " + TABLE_INVENTORY + " (" +
                    INVENTORY_COLUMN_ID + " int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    INVENTORY_COLUMN_PRODUCT_ID + " int NOT NULL, " +
                    INVENTORY_COLUMN_QUANTITY + " int NOT NULL, " +
                    INVENTORY_COLUMN_TOTAL_PRICE + " DECIMAL(7,2), " +
                    "FOREIGN KEY (" + INVENTORY_COLUMN_PRODUCT_ID + ")" +
                    " REFERENCES " + TABLE_PRODUCT + "(" + PRODUCT_COLUMN_ID +"));";
}
