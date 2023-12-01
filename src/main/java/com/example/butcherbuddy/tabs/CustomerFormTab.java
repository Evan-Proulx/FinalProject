package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.OrderLogic;
import com.example.butcherbuddy.UpdateTables;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Map;

public class CustomerFormTab extends Tab {
    private static CustomerFormTab instance;
    OrderLogic orderLogic = new OrderLogic();
    UpdateTables updateTables = new UpdateTables();
    Text alertText = new Text("");
    Map<Product, Integer> itemMap;
    ArrayList<String> names;
    ArrayList<Double> values;
    InventoryTable inventoryTable = InventoryTable.getInstance();
    ArrayList<Inventory> inventoryItems = inventoryTable.getAllInventories();


    private CustomerFormTab() {

        VBox vBox = new VBox();
        vBox.setBackground(new Background(new BackgroundFill(Color.web("#18191a"), CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setSpacing(30);
        vBox.setPrefHeight(Const.SCREEN_HEIGHT);
        vBox.setPrefWidth(Const.SCREEN_WIDTH);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("vbox");

        VBox ordersVBox = new VBox();
        ordersVBox.setAlignment(Pos.CENTER);
        ordersVBox.setSpacing(40);

        HBox buttonHbox = new HBox();
        Button newItem = new Button("Add Item");
        Button submit = new Button("Submit Order");
        buttonHbox.getChildren().addAll(newItem, submit);
        buttonHbox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(ordersVBox);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.getStyleClass().add("scroll-pane");

        VBox vBox1 = new VBox(buttonHbox, alertText, scrollPane);
        vBox1.setBackground(new Background(new BackgroundFill(Color.web("#18191a"), CornerRadii.EMPTY, Insets.EMPTY)));
        vBox1.setAlignment(Pos.CENTER);

        alertText.setVisible(false);
        //sets new item to the screen on each button click
        newItem.setOnMouseClicked(event -> {
            orderLogic.addNewItem(ordersVBox);
       });

        //Takes all items and sorts them into a Hashmap
        //Items in Hashmap are updated into the tables
        submit.setOnMouseClicked(event -> {
            submitOrder();
            itemMap.clear();
        });

        this.setText("Customer Order Form");
        this.setContent(vBox1);
    }



    //Takes all items and sorts them into a Hashmap
    //Items in Hashmap are updated into the tables
    private void submitOrder(){
        itemMap = orderLogic.accessInputValues();
        String statusName = updateTables.updateCustomerTables(itemMap);
        if (alertText.isVisible()){alertText.setVisible(false);}
        if (statusName != null){
            orderLogic.alert("error", "You've ordered more " + statusName + "'s than we have!", alertText);
            alertText.setVisible(true);
        }
    }

    public static CustomerFormTab getInstance() {
        if (instance == null) {
            instance = new CustomerFormTab();
        }
        return instance;
    }

}
