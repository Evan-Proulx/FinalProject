package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.pojo.Inventory;
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

    private InventoryTab() {
        BorderPane root = new BorderPane();

        ArrayList<Inventory> inventoryItems = inventoryTable.getAllInventories();

        ObservableList<Inventory> inventoryData = FXCollections.observableArrayList();

        TableView<Inventory> tableView = new TableView<>();

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
}
