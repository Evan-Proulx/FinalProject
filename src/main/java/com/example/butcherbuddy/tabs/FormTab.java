package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.OrderLogic;
import com.example.butcherbuddy.UpdateTables;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.InventoryTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Map;

public class FormTab extends Tab {
    OrderLogic orderLogic = new OrderLogic();
    private static FormTab instance;
    UpdateTables updateTables = new UpdateTables();


    private FormTab() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.web("#18191a"), CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setSpacing(30);
        vBox.setPrefHeight(Const.SCREEN_HEIGHT);
        vBox.setPrefWidth(Const.SCREEN_WIDTH);
        vBox.getStyleClass().add("vbox");

        HBox buttonHbox = new HBox();
        Button newItem = new Button("Add Item");
        Button submit = new Button("Submit Order");
        buttonHbox.getChildren().addAll(newItem, submit);
        buttonHbox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(buttonHbox);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("scroll-pane");

        VBox container = new VBox(buttonHbox,scrollPane);
        container.setBackground(new Background(new BackgroundFill(Color.web("#18191a"), CornerRadii.EMPTY, Insets.EMPTY)));
        container.setAlignment(Pos.CENTER);

        //sets new item to the screen on each button click
        newItem.setOnMouseClicked(event -> {
            orderLogic.addNewItem(vBox);
        });


        //Takes all items and sorts them into a Hashmap
        //Items in Hashmap are updated into the tables
        submit.setOnMouseClicked(event -> {
            Map<Product, Integer> itemMap = orderLogic.accessInputValues();
            updateTables.updateTables(itemMap);
        });

        this.setText("Order Form");
        this.setContent(container);
    }



    public static FormTab getInstance() {
        if (instance == null) {
            instance = new FormTab();
        }
        return instance;
    }

}

