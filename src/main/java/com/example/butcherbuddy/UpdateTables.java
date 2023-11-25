package com.example.butcherbuddy;

import com.example.butcherbuddy.pojo.OrderItem;
import com.example.butcherbuddy.pojo.Orders;
import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.InventoryTable;
import com.example.butcherbuddy.tables.OrderItemsTable;
import com.example.butcherbuddy.tables.OrdersTable;

import java.sql.Date;
import java.util.Map;

public class UpdateTables {

    OrderItemsTable orderItemsTable = new OrderItemsTable();
    OrdersTable ordersTable = new OrdersTable();
    InventoryTable inventoryTable = new InventoryTable();
    /**
     * Create the order table
     * The createOrder method returns the tables id
     * loop through the itemMap and set values for the key and value
     * We create a new order item for each map entry
     */ 
    public void updateTables(Map<Product, Integer> itemMap) {
        long dateInMillis = System.currentTimeMillis();
        Date todayDate = new Date(dateInMillis);
        Orders order = new Orders(todayDate);
        int orderId = ordersTable.createOrder(order);

        for (Map.Entry<Product, Integer> entry : itemMap.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            double price = quantity * product.getPrice();
            System.out.println(orderId + " " + product.getId() + " " + quantity + " " + price);

            OrderItem orderItem = new OrderItem(
                    orderId,
                    product.getId(),
                    quantity
            );
            orderItemsTable.createOrderItem(orderItem);
        }

        inventoryTable.syncWithOrders();
    }
}
