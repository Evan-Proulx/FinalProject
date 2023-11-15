package com.example.butcherbuddy.tabs;

import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class InventoryTab extends Tab {
    private static InventoryTab instance;

    private InventoryTab(){
        BorderPane root = new BorderPane();

        this.setText("Inventory");
        this.setContent(root);
    }
    public static InventoryTab getInstance(){
        if (instance == null){
            instance = new InventoryTab();
        }return instance;
    }
}
