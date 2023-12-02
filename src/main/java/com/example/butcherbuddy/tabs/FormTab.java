package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.OrderLogic;
import com.example.butcherbuddy.UpdateTables;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.NamedInventory;
import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.InventoryTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Map;

public class FormTab extends Tab {
    OrderLogic orderLogic = new OrderLogic();
    Text alertText = new Text("");

    private static FormTab instance;
    UpdateTables updateTables = new UpdateTables();

    TableView<NamedInventory> tableView;

    ArrayList<NamedInventory> namedInventory = orderLogic.getNamedInventory();


    private FormTab() {
        tableView = new TableView<>();

        ObservableList<NamedInventory> inventoryData = FXCollections.observableArrayList();

        TableColumn<NamedInventory, String> productIdColumn = new TableColumn<>("Product Name");
        productIdColumn.setCellValueFactory(new PropertyValueFactory<NamedInventory, String>("productName"));
        TableColumn<NamedInventory, Integer> productQuantityColumn = new TableColumn<>("Quantity");
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<NamedInventory, Integer>("quantity"));
        TableColumn<NamedInventory, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<NamedInventory, Double>("totalPrice"));

        tableView.getColumns().addAll(productIdColumn, productQuantityColumn, priceColumn);
        tableView.setPrefHeight(500);
        tableView.setPrefWidth(300);

        inventoryData.addAll(namedInventory);
        tableView.setItems(inventoryData);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label tableViewNameLabel = new Label("Inventory");
        tableViewNameLabel.getStyleClass().add("label-text");
        VBox tableViewVbox = new VBox();
        tableViewVbox.getChildren().addAll(tableViewNameLabel, tableView);
        tableViewVbox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("vbox");

        HBox buttonHbox = new HBox();
        Button newItem = new Button("Add Item");
        Button submit = new Button("Submit Order");
        newItem.getStyleClass().add("button-style");
        submit.getStyleClass().add("button-style");
        buttonHbox.getChildren().addAll(newItem, submit);
        buttonHbox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(buttonHbox);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("scroll-pane");

        VBox container = new VBox(buttonHbox,alertText, scrollPane);
        container.setSpacing(20);
        container.setAlignment(Pos.CENTER);

        HBox root = new HBox(110);
        root.getChildren().addAll(tableViewVbox, container);
        root.setAlignment(Pos.CENTER);

        alertText.setVisible(false);

        //sets new item to the screen on each button click
        newItem.setOnMouseClicked(event -> {
            orderLogic.addNewItem(vBox);
        });

        //Takes all items and sorts them into a Hashmap
        //Items in Hashmap are updated into the tables
        submit.setOnMouseClicked(event -> {
            Map<Product, Integer> itemMap = orderLogic.accessInputValues();
            updateTables.updateTables(itemMap);
            itemMap.clear();
            refreshTable();
        });

        this.setText("Store Order Form");
        this.setContent(root);
    }

    /**
     * Refreshes the TableView with the latest data in the inventory table.
     * Retrieves the updated NamedInventory from the OrderLogic class, compares it with
     * the current data in the TableView, and updates the table if changes are detected.
     * @author Evan Proulx
     */
    public void refreshTable() {
        ArrayList<NamedInventory> updatedInventory = orderLogic.getNamedInventory();
        System.out.println("Refreshing table......");

        if (!isInventoryEqual(updatedInventory)) {
            tableView.setItems(FXCollections.observableArrayList(updatedInventory));
            System.out.println("Table refreshed");
        }
    }

    /**
     * Checks whether the new inventory in refreshTable() is equal to the current inventory
     * @return false if the lists are equal and true if they are
     * @author Evan Proulx
     */
    private boolean isInventoryEqual(ArrayList<NamedInventory> updatedInventory) {
        if (namedInventory.size() != updatedInventory.size()) {
            return false;
        }

        for (int i = 0; i < namedInventory.size(); i++) {
            if (!namedInventory.get(i).equals(updatedInventory.get(i))) {
                return false;
            }
        }
        return true;
    }


    public static FormTab getInstance() {
        if (instance == null) {
            instance = new FormTab();
        }
        return instance;
    }

}

