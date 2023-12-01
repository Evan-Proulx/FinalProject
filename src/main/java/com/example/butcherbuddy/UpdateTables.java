package com.example.butcherbuddy;

import com.example.butcherbuddy.pojo.*;
import com.example.butcherbuddy.tables.*;

import java.sql.Date;
import java.util.Map;

public class UpdateTables {

    CustomerItem customerItem = null;
    OrderItem orderItem = null;
    OrderItemsTable orderItemsTable = OrderItemsTable.getInstance();
    OrdersTable ordersTable = OrdersTable.getInstance();
    InventoryTable inventoryTable = InventoryTable.getInstance();
    CustomerOrderTable customerOrderTable = CustomerOrderTable.getInstance();
    CustomerItemsTable customerItemsTable = CustomerItemsTable.getInstance();
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

            orderItem = new OrderItem(
                    orderId,
                    product.getId(),
                    quantity
            );
            orderItemsTable.createOrderItem(orderItem);
        }

        inventoryTable.syncWithOrders();
    }

    //from the data given in the itemMap this function creates
    // a new order and adds it to customerOrder table
    //It then calls the removeOrderFromInventory and updateInventory methods
    // to remove the products from the inventory
    public String updateCustomerTables(Map<Product, Integer> itemMap) {
        long dateInMillis = System.currentTimeMillis();
        Date todayDate = new Date(dateInMillis);

        CustomerOrders customerOrder = new CustomerOrders(todayDate);
        int orderId = customerOrderTable.createCustomerOrder(customerOrder);

        for (Map.Entry<Product, Integer> entry : itemMap.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            int productId = product.getId();
            String productName = product.getName();
            double price = quantity * product.getPrice();
            System.out.println(orderId + " " + productId + " " + quantity + " " + price);

            customerItem  = new CustomerItem(
                    orderId,
                    product.getId(),
                    quantity
            );
            customerItemsTable.createCustomerItem(customerItem);
            Inventory newInventory = customerItemsTable.removeOrderFromInventory(
                    inventoryTable.getInventory(productId), customerItem);
            if (newInventory == null){
                return productName;
            }else {
                inventoryTable.updateInventory(newInventory);

                System.out.println("Product: " + productName + " Quantity: " + quantity + " removed from inventory");
            }
        }
        return null;
    }
}
