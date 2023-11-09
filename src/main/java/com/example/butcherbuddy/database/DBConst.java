package com.example.butcherbuddy.database;

public class DBConst {

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
    public static final String ORDER_ITEMS_COLUMN_PRICE = "price";

    //Inventory table
    public static final String TABLE_INVENTORY = "inventory";
    public static final String INVENTORY_COLUMN_ID = "id";
    public static final String INVENTORY_COLUMN_PRODUCT_ID = "product_id";
    public static final String INVENTORY_COLUMN_QUANTITY = "quantity";
    public static final String INVENTORY_COLUMN_TOTAL_PRICE = "total_price";
    public static final String INVENTORY_COLUMN_EXPIRY_DATE = "expiry_date";



    //Create TABLES
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
                    " REFERENCES " + TABLE_CATEGORY + "(" + CATEGORY_COLUMN_ID +"))";

    public static final String CREATE_TABLE_ORDERS =
            "CREATE TABLE " + TABLE_ORDERS + " (" +
            ORDERS_COLUMN_ID + " int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            ORDERS_COLUMN_DATE + " DATE)";

    public static final String CREATE_TABLE_ORDER_ITEMS =
            "CREATE TABLE " + TABLE_ORDER_ITEMS + " (" +
            ORDER_ITEMS_COLUMN_ID + " int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            ORDER_ITEMS_COLUMN_ORDER_ID + " int NOT NULL, " +
            ORDER_ITEMS_COLUMN_PRODUCT_ID + " int NOT NULL, " +
            ORDER_ITEMS_COLUMN_QUANTITY + " int NOT NULL, " +
            ORDER_ITEMS_COLUMN_PRICE + " DECIMAL(7,2), " +
            "FOREIGN KEY (" + ORDER_ITEMS_COLUMN_ORDER_ID + ")" +
                    " REFERENCES " + TABLE_ORDERS + "(" + ORDERS_COLUMN_ID +"),"+
            "FOREIGN KEY (" + ORDER_ITEMS_COLUMN_PRODUCT_ID + ")" +
                    " REFERENCES " + TABLE_PRODUCT + "("  + PRODUCT_COLUMN_ID +"))";

    public static final String CREATE_TABLE_INVENTORY =
            "CREATE TABLE " + TABLE_INVENTORY + " (" +
            INVENTORY_COLUMN_ID + " int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            INVENTORY_COLUMN_PRODUCT_ID + " int NOT NULL, " +
            INVENTORY_COLUMN_QUANTITY + " int NOT NULL, " +
            INVENTORY_COLUMN_TOTAL_PRICE + " DECIMAL(7,2), " +
            INVENTORY_COLUMN_EXPIRY_DATE + " DATE, " +
            "FOREIGN KEY (" + INVENTORY_COLUMN_PRODUCT_ID + ")" +
                    " REFERENCES " + TABLE_PRODUCT + "(" + PRODUCT_COLUMN_ID +"))";
}
