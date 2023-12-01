package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.OrderLogic;
import com.example.butcherbuddy.UpdateTables;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.NamedInventory;
import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.*;
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
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Map;

public class CustomerFormTab extends Tab {
    private static CustomerFormTab instance;
    OrderLogic orderLogic = new OrderLogic();
    UpdateTables updateTables = new UpdateTables();
    Text alertText = new Text("You've ordered more statusName's than we have!");
    Map<Product, Integer> itemMap;
    ArrayList<String> names;
    ArrayList<Double> values;
    InventoryTable inventoryTable = InventoryTable.getInstance();
    ArrayList<Inventory> inventoryItems = inventoryTable.getAllInventories();

    TableView<NamedInventory> tableView;

    ArrayList<NamedInventory> namedInventory = orderLogic.getNamedInventory();



    private CustomerFormTab() {

        alertText.setTextAlignment(TextAlignment.CENTER);


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


        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("vbox");

        VBox ordersVBox = new VBox();
        ordersVBox.setAlignment(Pos.CENTER);
        ordersVBox.setSpacing(20);

        HBox buttonHbox = new HBox();
        Button newItem = new Button("Add Item");
        Button submit = new Button("Submit Order");
        newItem.getStyleClass().add("button-style");
        submit.getStyleClass().add("button-style");
        buttonHbox.getChildren().addAll(newItem, submit);
        buttonHbox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(ordersVBox);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.getStyleClass().add("scroll-pane");

        VBox vBox1 = new VBox(buttonHbox, alertText, scrollPane);
        vBox1.setSpacing(20);
//        vBox1.setBackground(new Background(new BackgroundFill(Color.web("#18191a"), CornerRadii.EMPTY, Insets.EMPTY)));
        vBox1.setAlignment(Pos.CENTER);

        HBox test = new HBox(50);
        test.getChildren().addAll(tableViewVbox, vBox1);
        test.setAlignment(Pos.CENTER);

        alertText.setOpacity(0);
        //sets new item to the screen on each button click
        newItem.setOnMouseClicked(event -> {
            orderLogic.addNewItem(ordersVBox);
       });

        //Takes all items and sorts them into a Hashmap
        //Items in Hashmap are updated into the tables
        submit.setOnMouseClicked(event -> {
            alertText.setOpacity(0);
            submitOrder();
            itemMap.clear();
            refreshTable();
        });

        this.setText("Customer Order Form");
        this.setContent(test);
    }



    //Takes all items and sorts them into a Hashmap
    //Items in Hashmap are updated into the tables
    private void submitOrder(){
        itemMap = orderLogic.accessInputValues();
        String statusName = updateTables.updateCustomerTables(itemMap);
        if (alertText.getOpacity() == 1){alertText.setOpacity(0);}
        if (statusName != null){
            orderLogic.alert("error", "You've ordered more " + statusName + "'s than we have!", alertText);
            alertText.setOpacity(1);
        }
    }

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

    public static CustomerFormTab getInstance() {
        if (instance == null) {
            instance = new CustomerFormTab();
        }
        return instance;
    }

}
