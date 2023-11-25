package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.OrderLogic;
import com.example.butcherbuddy.UpdateTables;
import com.example.butcherbuddy.pojo.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
        vBox.setLayoutX(Const.SCREEN_WIDTH);

        HBox buttonHbox = new HBox();
        Button newItem = new Button("Add Item");
        Button submit = new Button("Submit Order");
        buttonHbox.getChildren().addAll(newItem, submit);
        buttonHbox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(buttonHbox);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToHeight(true);

        HBox hbox = new HBox(scrollPane);
        hbox.setBackground(new Background(new BackgroundFill(Color.web("#18191a"), CornerRadii.EMPTY, Insets.EMPTY)));
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);

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
        this.setContent(hbox);
    }


    public static FormTab getInstance() {
        if (instance == null) {
            instance = new FormTab();
        }
        return instance;
    }

}

