package com.example.butcherbuddy.panes;

import com.example.butcherbuddy.tabs.AddProductTab;
import com.example.butcherbuddy.tabs.FormTab;
import com.example.butcherbuddy.tabs.InventoryTab;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TabHost extends BorderPane {

    public TabHost() {
        TabPane tabPane = new TabPane();
        this.getStylesheets().add("com.example.butcherbuddy/style.css");

        InventoryTab inventoryTab = InventoryTab.getInstance();
        FormTab formTab = FormTab.getInstance();
        AddProductTab addProductTab = AddProductTab.getInstance();
        inventoryTab.setClosable(false);
        formTab.setClosable(false);
        addProductTab.setClosable(false);

        tabPane.getTabs().addAll(formTab, inventoryTab, addProductTab);

        this.setCenter(tabPane);

    }
}