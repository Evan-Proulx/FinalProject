package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.OrderLogic;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.NamedInventory;
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
    TableView<NamedInventory> tableView;
    OrderLogic orderLogic = new OrderLogic();
    ArrayList<NamedInventory> namedInventory = orderLogic.getNamedInventory();


    private InventoryTab() {
        BorderPane root = new BorderPane();

        System.out.println(namedInventory);

        tableView = new TableView<>();

        refreshTable();

        ObservableList<NamedInventory> inventoryData = FXCollections.observableArrayList();

        TableColumn<NamedInventory, String> productIdColumn = new TableColumn<>("Product Name");
        productIdColumn.setCellValueFactory(new PropertyValueFactory<NamedInventory, String>("productName"));
        TableColumn<NamedInventory, Integer> productQuantityColumn = new TableColumn<>("Quantity");
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<NamedInventory, Integer>("quantity"));
        TableColumn<NamedInventory, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<NamedInventory, Double>("totalPrice"));

        tableView.getColumns().addAll(productIdColumn, productQuantityColumn, priceColumn);

        inventoryData.addAll(namedInventory);
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
        ArrayList<NamedInventory> updatedInventory = orderLogic.getNamedInventory();
        System.out.println("Refreshing table......");

        if (!isInventoryEqual(updatedInventory)) {
            tableView.setItems(FXCollections.observableArrayList(updatedInventory));
            System.out.println("Table refreshed");
        }
    }

    //checks if the new inventory is equal to inventoryItems if not return false
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



}
