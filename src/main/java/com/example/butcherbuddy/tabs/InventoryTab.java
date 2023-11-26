package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.OrderItem;
import com.example.butcherbuddy.tables.InventoryTable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class InventoryTab extends Tab {
    private static InventoryTab instance;

    InventoryTable inventoryTable = new InventoryTable();
    TableView<Inventory> tableView;
    ArrayList<Inventory> inventoryItems = inventoryTable.getAllInventories();


    private InventoryTab() {
        BorderPane root = new BorderPane();

        tableView = new TableView<>();

        refreshTable();

        ObservableList<Inventory> inventoryData = FXCollections.observableArrayList();

        TableColumn<Inventory, Integer> productIdColumn = new TableColumn<>("PRODUCTID");
        productIdColumn.setCellValueFactory(new PropertyValueFactory<Inventory, Integer>("productId"));
        TableColumn<Inventory, Integer> productQuantityColumn = new TableColumn<>("QUANTITY");
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<Inventory, Integer>("quantity"));
        TableColumn<Inventory, Double> priceColumn = new TableColumn<>("PRICE");
        priceColumn.setCellValueFactory(new PropertyValueFactory<Inventory, Double>("totalPrice"));

        tableView.getColumns().addAll(productIdColumn, productQuantityColumn, priceColumn);

        inventoryData.addAll(inventoryItems);
        tableView.setItems(inventoryData);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root.setCenter(tableView);


        this.setText("Inventory");
        this.setContent(root);
    }

    public static InventoryTab getInstance() {
        if (instance == null) {
            instance = new InventoryTab();
        }
        return instance;
    }

    //Checks if the old inventory and new are the same. If not we update the table
    public void refreshTable() {
        ArrayList<Inventory> updatedInventory = inventoryTable.getAllInventories();
        System.out.println("Refreshing table......");

        if (!isInventoryEqual(updatedInventory)) {
            tableView.setItems(FXCollections.observableArrayList(updatedInventory));
            System.out.println("Table refreshed");
        }
    }

    //checks if the new inventory is equal to inventoryItems if not return false
    private boolean isInventoryEqual(ArrayList<Inventory> updatedInventory) {
        if (inventoryItems.size() != updatedInventory.size()) {
            return false;
        }

        for (int i = 0; i < inventoryItems.size(); i++) {
            if (!inventoryItems.get(i).equals(updatedInventory.get(i))) {
                return false;
            }
        }
        return true;
    }
}
