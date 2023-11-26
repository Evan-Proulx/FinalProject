package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.OrderLogic;
import com.example.butcherbuddy.UpdateTables;
import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Map;

public class CustomerFormTab extends Tab {

    OrderLogic orderLogic = new OrderLogic();
    private static CustomerFormTab instance;
    UpdateTables updateTables = new UpdateTables();

    Text alertText = new Text("");

    private CustomerFormTab() {

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

        alertText.setVisible(false);
        hbox.getChildren().add(alertText);

        //sets new item to the screen on each button click
        newItem.setOnMouseClicked(event -> {
            orderLogic.addNewItem(vBox);
        });

        submit.setOnMouseClicked(event -> {
            submitOrder();
        });

        this.setText("Customer Order Form");
        this.setContent(hbox);
    }

    //Takes all items and sorts them into a Hashmap
    //Items in Hashmap are updated into the tables
    private void submitOrder(){
        Map<Product, Integer> itemMap = orderLogic.accessInputValues();
        String statusName = updateTables.updateCustomerTables(itemMap);
        if (alertText.isVisible()){alertText.setVisible(false);}
        if (statusName != null){
            alertText.setText("Item: " + statusName + " [More items ordered than in inventory!]");
            alertText.setFill(Color.RED);
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
